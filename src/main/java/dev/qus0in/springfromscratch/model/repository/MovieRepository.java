package dev.qus0in.springfromscratch.model.repository;

import dev.qus0in.springfromscratch.model.dto.MovieDTO;
import dev.qus0in.springfromscratch.model.dto.MovieParam;
import dev.qus0in.springfromscratch.model.dto.MovieResponse;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Repository
public class MovieRepository implements APIClientRepository {
    final String url = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";

    public List<MovieDTO> getMovies(MovieParam param) throws Exception {
        final String key = dotenv.get("MOVIE_KEY");
        HttpRequest request = HttpRequest.newBuilder()
                // targetDt : yyyymmdd
                .uri(URI.create("%s?key=%s&targetDt=%s".formatted(url, key, param.targetDate())))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            MovieResponse movieResponse = objectMapper.readValue(response.body(), MovieResponse.class);
            return movieResponse.boxOfficeResult().dailyBoxOfficeList()
                    .stream().map((v) -> new MovieDTO(Long.parseLong(v.rank()), v.movieCd(), v.movieNm(), v.openDt(), Long.parseLong(v.audiAcc()))).toList();
//            return objectMapper.readValue(response.body(), MovieDTO.class);
//            System.out.println(response.body());
        }
        throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
    }
}
