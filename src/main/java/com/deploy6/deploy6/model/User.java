package com.deploy6.deploy6.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama")
    private String nama;

    @Column(name = "nim")
    private String nim;

    @Column(name = "jenis_kelamin")
    private String jenisKelamin;
}