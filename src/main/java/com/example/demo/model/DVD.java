package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.*;

import java.time.LocalDate;

enum Genre {Horror, Comedy, Action }

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dvd")
public class DVD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String title;

//    private Genre genre;
    private String genre;

//    @LastModifiedDate
//    private LocalDate releaseDate;

    private String releaseDate;

    private double price;

    private String isAvailable;

    private Long rentedBy;
}
