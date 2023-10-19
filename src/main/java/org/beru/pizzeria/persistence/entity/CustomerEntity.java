package org.beru.pizzeria.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class CustomerEntity {
    @Id
    @Column(nullable = false, length = 15)
    private String id;
    @Column(nullable = false, length = 60)
    private String name;
    @Column(length = 100)
    private String address;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(name = "phone_number", length = 13, nullable = false)
    private String phoneNumber;
}
