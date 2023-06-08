package com.example.memory.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@DynamoDBTable(tableName = "Snapshot")
@DynamoDbBean
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Snapshot {

    @DynamoDBHashKey(attributeName = "id")
    @Getter(onMethod_={@DynamoDbPartitionKey})
    private String id;

    @DynamoDBAttribute
    private String timestamp;

    @DynamoDBAttribute
    private String videoUrl;

    @DynamoDBAttribute
    private String imageUrl;
}
