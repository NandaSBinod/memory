package com.example.memory.models.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Value
@Builder
public class SnapshotDTO {
    String id; //keep? username? sort key?
    Instant time; // convert to OffsetDateTime for storage // Instant?? // generate at front or back end?

    String content;



}
