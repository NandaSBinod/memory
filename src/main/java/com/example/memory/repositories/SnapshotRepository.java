package com.example.memory.repositories;

import com.example.memory.models.Snapshot;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import java.net.URI;


@Component
public class SnapshotRepository {

    private DynamoDbEnhancedClient enhancedClient;

    private DynamoDbClient ddb;


    private static DynamoDbTable<Snapshot> snapshotTable;
    private static DynamoDbTable<EnhancedDocument> documentDynamoDbTable;

    public DynamoDbEnhancedClient getEnhancedClient() {
        return enhancedClient;
    }

    public DynamoDbTable<Snapshot> getSnapshotTable() {
        return snapshotTable;
    }

    public DynamoDbClient getDdb() {
        return ddb;
    }

    public SnapshotRepository() {

        ddb = DynamoDbClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create())
                .region(Region.US_WEST_2)
                .endpointOverride(URI.create("http://localhost:8000"))
                .build();

        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();

        snapshotTable = enhancedClient.table("Snapshot", TableSchema.fromBean(Snapshot.class));
        documentDynamoDbTable = enhancedClient.table("Snapshot",
                TableSchema.documentSchemaBuilder()
                        // Specify the primary key attributes.
                        .addIndexPartitionKey(TableMetadata.primaryIndexName(),"id", AttributeValueType.S)
                        // Specify attribute converter providers. Minimally add the default one.
                        .attributeConverterProviders(AttributeConverterProvider.defaultProvider())
                        .build());

        //String result = createTable(ddb, "Snapshot", "Id");
        //System.out.println(result);
        //ddb.close();

    }

    public static String createTable() {
        DynamoDbClient ddb = DynamoDbClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create())
                .region(Region.US_WEST_2)
                .endpointOverride(URI.create("http://localhost:8000"))
                .build();
        return createTable(ddb, "Snapshot", "id");
    }

    public static String createTable(DynamoDbClient ddb, String tableName, String key) {
        DynamoDbWaiter dbWaiter = ddb.waiter();
        CreateTableRequest request = CreateTableRequest.builder()
                .attributeDefinitions(AttributeDefinition.builder()
                        .attributeName(key)
                        .attributeType(ScalarAttributeType.S)
                        .build())
                .keySchema(KeySchemaElement.builder()
                        .attributeName(key)
                        .keyType(KeyType.HASH)
                        .build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(10L)
                        .writeCapacityUnits(10L)
                        .build())
                .tableName(tableName)
                .build();

        String newTable ="";
        try {
            CreateTableResponse response = ddb.createTable(request);
            DescribeTableRequest tableRequest = DescribeTableRequest.builder()
                    .tableName(tableName)
                    .build();

            // Wait until the Amazon DynamoDB table is created.
            WaiterResponse<DescribeTableResponse> waiterResponse = dbWaiter.waitUntilTableExists(tableRequest);
            waiterResponse.matched().response().ifPresent(System.out::println);
            newTable = response.tableDescription().tableName();
            return newTable;

        } catch (DynamoDbException e) {
            DescribeTableRequest request2 = DescribeTableRequest.builder()
                    .tableName(tableName)
                    .build();
            TableDescription tableInfo = ddb.describeTable(request2).table();
            if (tableInfo != null) {
                newTable = tableInfo.tableName();
                System.out.format("Table name  : %s\n", newTable);
                return newTable;
            } else {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
        return "";
    }



    public void getItem(String key, String keyVal) {

        HashMap<String,AttributeValue> keyToGet = new HashMap<>();
        keyToGet.put(key, AttributeValue.builder()
                .s(keyVal)
                .build());

        GetItemRequest request = GetItemRequest.builder()
                .key(keyToGet)
                .tableName("Snapshot")
                .build();

        try {
            Map<String,AttributeValue> returnedItem = ddb.getItem(request).item();
            if (returnedItem != null) {
                Set<String> keys = returnedItem.keySet();
                System.out.println("Amazon DynamoDB table attributes: \n");

                for (String key1 : keys) {
                    System.out.format("%s: %s\n", key1, returnedItem.get(key1).toString());
                }
            } else {
                System.out.format("No item found with the key %s!\n", key);
            }

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void putItem(Snapshot snapshot) {
        snapshotTable.putItem(snapshot);
    }

    public void putItem(EnhancedDocument enhancedDocument) {
        documentDynamoDbTable.putItem(enhancedDocument);
    }


    public void deleteItemFromKey(String key, String keyVal) {
        HashMap<String,AttributeValue> keyToGet = new HashMap<>();
        keyToGet.put(key, AttributeValue.builder()
                .s(keyVal)
                .build());

        DeleteItemRequest deleteReq = DeleteItemRequest.builder()
                .tableName("Snapshot")
                .key(keyToGet)
                .build();

        try {
            ddb.deleteItem(deleteReq);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }



}
