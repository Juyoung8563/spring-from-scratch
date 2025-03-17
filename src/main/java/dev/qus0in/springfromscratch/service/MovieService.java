package dev.qus0in.springfromscratch.service;

import dev.qus0in.springfromscratch.model.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
}
