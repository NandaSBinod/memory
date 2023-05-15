package com.example.memory.models.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Value
@Builder
public class SnapshotDTO {
    long id;
    LocalDateTime time;
    // content

}
