package com.demo.movie.service.dataFetcher;

import com.demo.movie.model.Movie;
import com.demo.movie.repository.MovieRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieDataFetcher implements DataFetcher<Movie> {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie get(DataFetchingEnvironment dataFetchingEnvironment) {

        final String name = dataFetchingEnvironment.getArgument("name");
        return movieRepository.findByName(name);
    }
}
