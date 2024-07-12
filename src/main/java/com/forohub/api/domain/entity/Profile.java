package com.forohub.api.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Entity(name = "Profile")
@Table(name = "profiles")
@NoArgsConstructor
@Getter
public class Profile {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
