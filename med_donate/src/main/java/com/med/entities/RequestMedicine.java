package com.med.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestMedicine {

    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private String address;
    @Column(length = 1000)
    private String description;


    @ManyToOne
    @JsonIgnore
    private User user;

}