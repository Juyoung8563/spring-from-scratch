package dev.qus0in.springfromscratch.service;

import dev.qus0in.springfromscratch.model.dto.MovieDTO;
import dev.qus0in.springfromscratch.model.dto.MovieInfoDTO;
import dev.qus0in.springfromscratch.model.dto.MovieParam;
import dev.qus0in.springfromscratch.model.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MovieService {
    final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieDTO> getMovies() throws Exception {
        // yyyymmdd
        LocalDate nowDate = LocalDate.now();
        nowDate = nowDate.minusDays(1);
        String nowDateStr = nowDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        MovieParam param = new MovieParam(nowDateStr);
        return movieRepository.getMovies(param);
    }

    public List<MovieInfoDTO> getMovieInfos() throws Exception {
        return getMovies().stream().map((v) -> {
            try {
                return movieRepository.getMovieInfo(v);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }
}
