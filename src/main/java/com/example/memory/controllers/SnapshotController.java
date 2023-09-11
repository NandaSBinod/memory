package com.example.memory.controllers;

import com.example.memory.models.dtos.SnapshotDTO;
import com.example.memory.services.SnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SnapshotController {

    @Autowired
    SnapshotService snapshotService;

    @PostMapping("/snapshots")
    public void save(@RequestPart("file") MultipartFile file, @RequestPart("snapshot") SnapshotDTO snapshotDTO ) {
        System.out.println(snapshotDTO.getId());
        System.out.println(snapshotDTO.getTime());
        System.out.println(file.getSize());
        snapshotService.saveSnapshotDto(snapshotDTO);
    }


}
