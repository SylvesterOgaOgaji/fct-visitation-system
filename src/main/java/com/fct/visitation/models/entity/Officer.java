package com.fct.visitation.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "officers")
public class Officer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "officer_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String department;

    // Proper getFullName implementation
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    // Compatibility methods
    public String getName() {
        return getFullName();
    }
    
    public void setName(String name) {
        if (name != null && name.contains(" ")) {
            this.firstName = name.substring(0, name.indexOf(" "));
            this.lastName = name.substring(name.indexOf(" ") + 1);
        } else {
            this.firstName = name;
            this.lastName = "";
        }
    }
}