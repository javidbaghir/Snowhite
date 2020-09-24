package com.Snowhite.domain;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(length = 45)
    @Size(min = 2, message = "{user.register.username.length}")
    private String username;


    @Size(min = 4, message = "{user.register.password.length}")
    @ToString.Exclude
    @NotNull
    private String password;

    @Transient
    @ToString.Exclude
    private String passwordConfirm;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
