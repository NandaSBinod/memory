package com.example.memory.services;

import com.example.memory.models.Snapshot;
import com.example.memory.models.dtos.SnapshotDTO;
import com.example.memory.repositories.SnapshotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SnapshotService {

    @Autowired
    SnapshotRepository snapshotRepository;

    public void saveSnapshotDto(SnapshotDTO snapshotDTO) {
        //snapshotRepository.putItem();
        Snapshot snapshot = Snapshot.builder()
                .id("") // generate id
                .timestamp(snapshotDTO.getTime().toString())
                .content(snapshotDTO.getContent())
                .build();
        snapshotRepository.putItem(snapshot);
    }

    public void saveMultiPartFile(MultipartFile file) {

    }
}
