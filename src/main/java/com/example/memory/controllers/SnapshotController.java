package com.example.memory.controllers;

import com.example.memory.models.dtos.SnapshotDTO;
import org.springframework.web.bind.annotation.*;

@RestController
public class SnapshotController {

    @PostMapping("/snapshots")
    public void save(@RequestBody SnapshotDTO snapshotDTO ) {
        System.out.println(snapshotDTO.getId());
    }

    @GetMapping("/snapshots")
    public void idk() {
        System.out.println("testing");
    }

}
