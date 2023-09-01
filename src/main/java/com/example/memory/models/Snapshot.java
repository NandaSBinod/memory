package com.example.memory.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@DynamoDBTable(tableName = "Snapshot")
@DynamoDbBean
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Snapshot {

    @DynamoDBHashKey(attributeName = "username")
    @Getter(onMethod_={@DynamoDbPartitionKey})
    private String username;

    @Getter(onMethod_ = {@DynamoDbSortKey})
    private String id;


    @DynamoDBAttribute
    private String timestamp;

    @DynamoDBAttribute
    private String videoUrl;

    @DynamoDBAttribute
    private String imageUrl;

    @DynamoDBAttribute
    private String content;

    /*@DynamoDbSortKey
    public String getId() {
        return id;
    }*/
}
