package com.example.memory.models.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Value
@Builder
public class SnapshotDTO {
    long id;
    Instant time; // convert to OffsetDateTime for storage



}
