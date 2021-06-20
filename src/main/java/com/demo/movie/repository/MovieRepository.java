package com.demo.movie.repository;


import com.demo.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Movie findByName(String name);

}
