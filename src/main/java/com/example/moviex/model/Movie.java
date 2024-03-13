package com.example.moviex.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rating;
    private String name;
    private String url;
    private Integer numOfLikes;

    public Movie(  Long id,
             Double rating,
             String name,
             String url,
             Integer numOfLikes){
        this.id=id;
        this.rating = rating;
        this.name = name;
        this.url = url;
        this.numOfLikes = numOfLikes;
    }
}
