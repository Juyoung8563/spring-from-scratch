package dev.qus0in.springfromscratch.model.repository;

import dev.qus0in.springfromscratch.model.dto.MovieDTO;
import dev.qus0in.springfromscratch.model.dto.MovieParam;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository {
    final String url = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";

    public MovieDTO getMovie(MovieParam param) {
        return new MovieDTO();
    }
}
