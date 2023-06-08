package com.example.memory.repositories;

import com.example.memory.models.Snapshot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SnapshotRepositoryTest {

    @Autowired
    SnapshotRepository snapshotRepository;
    @Test
    public void createATable() {
        assertEquals("Snapshot", SnapshotRepository.createTable());
    }

    @Test
    public void putAnItemAndDeleteIfSuccessful() {
        try {
            snapshotRepository.putItem(Snapshot.builder()
                    .id("2")
                    .build());
            snapshotRepository.deleteItemFromKey("id", "2");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void putADocumentAndDeleteIfSuccessful() {
        try {
            snapshotRepository.putItem(EnhancedDocument.builder()
                    .putString("id", "52")
                    .putString("firstName", "Shirley")
                    .putString("lastName", "Temple")
                    .build());

            //snapshotRepository.deleteItemFromKey("id", "50");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getItem() {
        try {
            snapshotRepository.getItem("id", "50");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}