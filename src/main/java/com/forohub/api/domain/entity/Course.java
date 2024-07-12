package com.forohub.api.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@Entity(name = "Course")
@Table(name = "courses")
@NoArgsConstructor
@Getter
public class Course {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(nullable = false)
    private String name;
    private String category;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Topic> topics;
}
