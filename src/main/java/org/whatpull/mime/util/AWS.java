package org.whatpull.mime.util;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanFilter;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.whatpull.mime.annotation.ScheduledAnnotator;
import org.whatpull.mime.job.CrawlingLinkDataJob;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.*;

/**
 * [유틸리티]AWS
 * [Project] mime
 * [Package] org.whatpull.mime.util
 * Created by yeonsu on 2017-03-10
 */
public class AWS {

    public static final String LINK_TABLE_NAME = "LINK";
    public static final String META_TABLE_NAME = "META";
    public static final String INDEX_TABLE_NAME = "INDEX";
    public static boolean isLinkTableExist=false, isIndexTableExist=false, isMetaTableExist=false;
    private static DynamoDB dynamoDB = null;

    /**
     * DynamoDB 연결시도
     */
    public static Map<String, Object> configDynamoDB() {
        Map<String, Object> result = new HashMap<String, Object>();
        String msg = "";
        boolean isReturn = true;
        try {
            if(dynamoDB == null) {
                dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.standard().withCredentials(new ProfileCredentialsProvider()).withRegion(Regions.AP_NORTHEAST_2).build());
                msg = "AWS DynamoDB Success connecting.";
            } else {
                msg = "AWS DynamoDB Already connecting.";
                isReturn = false;
            }
        } catch (Exception e) {
            msg = e.getMessage();
            isReturn = false;
        } finally {
            result.put("msg", msg);
            if(isReturn) {
                if(ObjectUtils.anyNotNull(dynamoDB)) {
                    result.put("tables", dynamoDB.listTables());
                } else {
                    result.put("tables", null);
                }
            }
            return result;
        }
    }

    /**
     * DynamoDB 연결해제
     */
    public static void shutdownDynamoDB() {
        if(ObjectUtils.anyNotNull(dynamoDB)) {
            dynamoDB.shutdown();
            dynamoDB = null;
        }
    }

    /**
     * 테이블 초기화
     * @param log 로그
     */
    public static void initTable(JTextArea log) {
        if(isLinkTableExist == false) {
            createTable(log, LINK_TABLE_NAME, "link");
        }
        if(isMetaTableExist==false) {
            createTable(log, META_TABLE_NAME, "link");
        }
        if(isIndexTableExist==false) {
            createTable(log, INDEX_TABLE_NAME, "index");
        }
    }

    /**
     * 테이블 생성
     * @param log 로그
     * @param tableName 테이블명
     * @param partitionKey 구분키
     */
    private static void createTable(JTextArea log, String tableName, String partitionKey) {
        try {
            Table table = dynamoDB.createTable(tableName,
                    Arrays.asList(
                            new KeySchemaElement(partitionKey, KeyType.HASH)), //Partition key
                    Arrays.asList(
                            new AttributeDefinition(partitionKey, ScalarAttributeType.S)),
                    new ProvisionedThroughput(1L, 1L));
            table.waitForActive();
            log.append("Success. Table status : " + table.getDescription().getTableStatus() + "\n");
        } catch (Exception e) {
            log.append("Unable to create table : " + e.getMessage() + "\n");
        }
    }

    /**
     * [Link]URL 입력(Create)
     * @param link URL
     */
    public static void insertLink(String link) {
        Table table = dynamoDB.getTable(LINK_TABLE_NAME);
        Item item = new Item();
        item.withPrimaryKey("link", link);
        item.withString("seeds", ScheduledAnnotator.seeds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        item.withString("date", sdf.format(new Date()));
        table.putItem(item);
    }

    /**
     * [Link]URL 조회(Read)
     * @return 관련 Link URL 목록
     */
    public static Queue<String> selectLink() {
        Table table = dynamoDB.getTable(LINK_TABLE_NAME);

        ScanFilter filter = new ScanFilter("seeds").eq(ScheduledAnnotator.seeds);
        ItemCollection<ScanOutcome> items = table.scan(filter);
        Iterator<Item> iterator = items.iterator();

        Item item;
        Queue<String> queue = new LinkedList<String>();
        while (iterator.hasNext()) {
            item = iterator.next();
            queue.add(item.getString("link"));
        }
        return queue;
    }

    /**
     * [Link]URL 삭제(Delete)
     * @param link URL
     */
    public static void deleteLink(String link) {
        Table table = dynamoDB.getTable(LINK_TABLE_NAME);
        DeleteItemSpec spec = new DeleteItemSpec().withPrimaryKey("link", link);
        table.deleteItem(spec);
    }

    /**
     * [Meta]Meta 입력(Create)
     * @param link URL
     * @param meta META 정보
     */
    public static void insertMeta(String link, String meta) {
        Table table = dynamoDB.getTable(META_TABLE_NAME);
        Item item = new Item();
        item.withPrimaryKey("link", link);
        item.withString("meta", meta);
        item.withString("seeds", ScheduledAnnotator.seeds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        item.withString("date", sdf.format(new Date()));
        table.putItem(item);
    }

    /**
     * [Index]Index 입력(Create)
     * @param index 단어
     * @param link 링크
     */
    public static void insertIndex(String index, String link) {
        Table table = dynamoDB.getTable(INDEX_TABLE_NAME);
        Item item = new Item();
        item.withPrimaryKey("index", index);
        List<String> list = new ArrayList<String>();
        list.addAll(selectIndex(index));
        if(list.indexOf(link) == -1) {
            list.add(link);
        }
        item.withList("link", list);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        item.withString("date", sdf.format(new Date()));
        table.putItem(item);
    }

    /**
     * [Index]Index 조회(Read)
     * @param index 단어
     * @return Link 목록
     */
    public static List<String> selectIndex(String index) {
        Table table = dynamoDB.getTable(INDEX_TABLE_NAME);
        ScanFilter filter = new ScanFilter("index").eq(index);
        ItemCollection<ScanOutcome> items = table.scan(filter);
        Iterator<Item> iterator = items.iterator();
        List<String> list = new ArrayList<String>();
        Item item;
        while (iterator.hasNext()) {
            item = iterator.next();
            list.addAll(item.<String>getList("link"));
        }
        return list;
    }
}