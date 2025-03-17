package dev.qus0in.springfromscratch.service;

import dev.qus0in.springfromscratch.model.dto.MovieCacheDTO;
import dev.qus0in.springfromscratch.model.dto.MovieDTO;
import dev.qus0in.springfromscratch.model.dto.MovieInfoDTO;
import dev.qus0in.springfromscratch.model.dto.MovieParam;
import dev.qus0in.springfromscratch.model.repository.CacheRepository;
import dev.qus0in.springfromscratch.model.repository.MovieRepository;
import dev.qus0in.springfromscratch.model.repository.ImageRepository;
import dev.qus0in.springfromscratch.util.NowDate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class MovieService {
    final Logger logger = Logger.getLogger(MovieService.class.getSimpleName());
    final MovieRepository movieRepository;
    final CacheRepository cacheRepository;
    final ImageRepository imageRepository;

    public MovieService(MovieRepository movieRepository, CacheRepository cacheRepository, ImageRepository imageRepository) {
        this.movieRepository = movieRepository;
        this.cacheRepository = cacheRepository;
        this.imageRepository = imageRepository;
    }

    public List<MovieDTO> getMovies(String date) throws Exception {
        logger.info("getMovies called");
        // yyyymmdd
//        LocalDate nowDate = LocalDate.now();
//        nowDate = nowDate.minusDays(1);
//        String nowDateStr = nowDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        MovieParam param = new MovieParam(nowDateStr);
        MovieParam param = new MovieParam(date);
        return movieRepository.getMovies(param);
    }

    public List<MovieInfoDTO> getMovieInfos() throws Exception {
        logger.info("getMovieInfos called");
//        LocalDate nowDate = LocalDate.now();
//        nowDate = nowDate.minusDays(1);
//        String nowDateStr = nowDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String nowDateStr = NowDate.str();
        try {
            MovieCacheDTO cache = cacheRepository.getCache(nowDateStr);
            logger.info("캐싱 불러오기 성공");
            return cache.data();
        } catch (Exception e) {
            logger.warning("캐싱 불러오기 실패");
            List<MovieInfoDTO> data = getMovies(nowDateStr).stream().map((v) -> {
                try {
                    String imageUrl = imageRepository.getImage(v);
                    return movieRepository.getMovieInfo(v, imageUrl);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }).toList();
            cacheRepository.save(new MovieCacheDTO(nowDateStr, data));
            return data;
        }
    }
}
