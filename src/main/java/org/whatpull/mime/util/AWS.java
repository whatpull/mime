package org.whatpull.mime.util;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * AWS Settings
 * AWS 셋팅
 * Created by yeonsu on 2017-02-07.
 * since 2017-02-07
 */
public class AWS {

    private static final String TABLE_NAME = "LINK";

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
     * Link URL 입력
     * @param link URL
     */
    public static void insertLink(String link) {
        Table table = dynamoDB.getTable("LINK");
        Item item = new Item();
        item.withPrimaryKey("Link", link);
        table.putItem(item);
    }

    /**
     *
     * @return
     */
    public static Queue<String> selectLink() {
        Table table = dynamoDB.getTable("LINK");

        ItemCollection<ScanOutcome> items = table.scan();
        Iterator<Item> iterator = items.iterator();

        Item item;
        Queue<String> queue = new LinkedList<String>();
        while (iterator.hasNext()) {
            item = iterator.next();
            queue.add(item.getString("Link"));
        }
        return queue;
    }

}
