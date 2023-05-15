package com.example.memory.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PERSON")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;



}
