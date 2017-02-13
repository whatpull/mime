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
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.whatpull.mime.annotation.ScheduledAnnotator;
import org.whatpull.mime.job.CrawlingLinkDataJob;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * AWS Settings
 * AWS 셋팅
 * Created by yeonsu on 2017-02-07.
 * since 2017-02-07
 */
public class AWS {

    private static final String LINK_TABLE_NAME = "LINK";
    private static final String META_TABLE_NAME = "META";
    private static final String INDEX_TABLE_NAME = "INDEX";
    private static DynamoDB dynamoDB = null;

    /**
     * DynamoDB 생성자
     */
    public static void configDynamoDB() {
        if(dynamoDB == null) {
            dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.standard().withCredentials(new ProfileCredentialsProvider()).withRegion(Regions.AP_NORTHEAST_2).build());
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