package com.forohub.api.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity(name = "Response")
@Table(name = "responses")
@NoArgsConstructor
@Getter
@Setter
public class Response {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String message;
    @CreatedDate
    private LocalDateTime creationDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id")
    private  Topic topic;

    private String solution;





    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
    }
}
