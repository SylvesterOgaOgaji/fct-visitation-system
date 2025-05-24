package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Collection<String> roles;

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Collection<String> getRoles() {
        return this.roles;
    }
}