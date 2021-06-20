package com.demo.movie.service;

import com.demo.movie.model.Movie;
import com.demo.movie.repository.MovieRepository;
import com.demo.movie.service.dataFetcher.AllMovieDataFetcher;
import com.demo.movie.service.dataFetcher.MovieDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.stream.Stream;

@Service
public class GraphQLService {

    @Value(value = "classpath:movie.graphql")
    Resource resource;

    private GraphQL graphQL;

    @Autowired
    private AllMovieDataFetcher allMovieDataFetcher;

    @Autowired
    private MovieDataFetcher movieDataFetcher;

    @Autowired
    private MovieRepository movieRepository;

    @PostConstruct
    private void loadSchema() throws Exception {

        addDataInToHSQL();

        File schemaFile = resource.getFile();

        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schemaFile);

        RuntimeWiring runtimeWiring = buildRuntimeWiring();

        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private void addDataInToHSQL() {

        Stream.of(
                new Movie(0, "Titanic", "James Cameron", new String[]{"Leonardo DiCaprio", "Kate Winslett", "Billy Zane"}, 1997),
                new Movie(0, "Jurassic World", "Colin Trevor", new String[]{"Chris Pratt", "Bryce Dallas Howard", "Vincent D'Onofrio"}, 2015),
                new Movie(0, "Frozen II", "Chris Buck", new String[]{"Kristen Bell", "Idina Menzel"}, 2019),
                new Movie(0, "Black Panther", "Ryan Coogler", new String[]{"Colin Trevorrow", "Michael B. Jordan", "Danai Gurira"}, 2018),
                new Movie(0, "Minions", "Pierre Coffin", new String[]{"Sandra Bullock", "Jon Hamm", "Michael Keaton"}, 2015)
        ).forEach(movie -> movieRepository.save(movie));

    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allMovies", allMovieDataFetcher)
                        .dataFetcher("movie", movieDataFetcher))
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}
