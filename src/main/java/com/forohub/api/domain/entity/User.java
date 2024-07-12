package com.forohub.api.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "User")
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Profile> profiles = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Response> responses= new ArrayList<>();;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Topic> topics= new ArrayList<>();;

    public User(UUID id, String name, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;

    }

//    public static User createUser(String name, String email, String password){
//        return new User(
//                id,
//                name,
//                email,
//                password
//        );
//    }

    public void addProfile(Profile profile){

    }
}
