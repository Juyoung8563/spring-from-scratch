package dev.qus0in.springfromscratch.service;

import dev.qus0in.springfromscratch.model.dto.MovieCacheDTO;
import dev.qus0in.springfromscratch.model.dto.MovieDTO;
import dev.qus0in.springfromscratch.model.dto.MovieInfoDTO;
import dev.qus0in.springfromscratch.model.dto.MovieParam;
import dev.qus0in.springfromscratch.model.repository.CacheRepository;
import dev.qus0in.springfromscratch.model.repository.MovieRepository;
import dev.qus0in.springfromscratch.util.NowDate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MovieService {
    final MovieRepository movieRepository;
    final CacheRepository cacheRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.cacheRepository = new CacheRepository();
    }

    public List<MovieDTO> getMovies(String date) throws Exception {
        // yyyymmdd
//        LocalDate nowDate = LocalDate.now();
//        nowDate = nowDate.minusDays(1);
//        String nowDateStr = nowDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        MovieParam param = new MovieParam(nowDateStr);
        MovieParam param = new MovieParam(date);
        return movieRepository.getMovies(param);
    }

    public List<MovieInfoDTO> getMovieInfos() throws Exception {
//        LocalDate nowDate = LocalDate.now();
//        nowDate = nowDate.minusDays(1);
//        String nowDateStr = nowDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String nowDateStr =NowDate.str();
        try {
            return cacheRepository.getCache(nowDateStr).data();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            List<MovieInfoDTO> data = getMovies(nowDateStr).stream().map((v) -> {
                try {
                    return movieRepository.getMovieInfo(v);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }).toList();
            cacheRepository.save(new MovieCacheDTO(nowDateStr, data));
            return data;
        }
    }
}
