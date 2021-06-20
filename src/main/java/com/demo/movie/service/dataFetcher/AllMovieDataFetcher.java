package com.demo.movie.service.dataFetcher;

import com.demo.movie.model.Movie;
import com.demo.movie.repository.MovieRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllMovieDataFetcher implements DataFetcher<List<Movie>> {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return movieRepository.findAll();
    }
}
