package com.example.memory.repositories;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SnapshotRepositoryTest {

    @Test
    public void createATable() {
        SnapshotRepository snapshotRepository = new SnapshotRepository();
        assertEquals("Snapshot", SnapshotRepository.createTable(snapshotRepository.getDdb(), "Snapshot", "Id"));
    }

}