package com.forohub.api.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "Topic")
@Table(name = "topics")
@NoArgsConstructor
@Getter
@Setter
public class Topic {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(unique = true)
    private String title;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime creationDate;

    private Boolean status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<Response> responses;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id" ,nullable = true)
    private Course course;

    public Topic(String title, String message){
        this.id = UUID.randomUUID();
        this.title = title;
        this.message = message;
    }
    @PrePersist
    protected void onCreate() {

       this.creationDate = LocalDateTime.now();
    }
}
