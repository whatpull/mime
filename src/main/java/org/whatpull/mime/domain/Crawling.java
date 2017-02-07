package org.whatpull.mime.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Created by user on 2017-02-07.
 */
@DynamoDBTable(tableName = "CRAWLING")
public class Crawling {

    private String cSeq;

    @DynamoDBHashKey(attributeName = "c_seq")
    public String getcSeq() {
        return cSeq;
    }

    public void setcSeq(String cSeq) {
        this.cSeq = cSeq;
    }
}
