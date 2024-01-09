package com.interview.demo.userregistration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.stereotype.Component;


import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity(name = "registered_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String userName;
    private String password;
    private String ipAddress;

    private String country;

    private String countryCode;

    private String regionName;

    private String city;

}
