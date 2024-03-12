package com.example.wineshop.wineshopbackend.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
public class Wine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private double price;
    private String description;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] image;

}