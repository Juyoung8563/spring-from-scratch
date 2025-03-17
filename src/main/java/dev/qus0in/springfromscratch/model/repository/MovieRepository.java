package dev.qus0in.springfromscratch.model.repository;

import dev.qus0in.springfromscratch.model.dto.MovieDTO;
import dev.qus0in.springfromscratch.model.dto.MovieParam;
import dev.qus0in.springfromscratch.model.dto.MovieResponse;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Repository
public class MovieRepository implements APIClientRepository {
    final String baseURL = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice";
    final String key = dotenv.get("MOVIE_KEY");

    public String callAPI(MovieParam param, String url, String action, String format) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url.formatted(
                        baseURL, action, format, key, param.targetDate())))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response.body();
        }
        throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
    }

    public List<MovieDTO> getMovies(MovieParam param) throws Exception {
        String action = "searchDailyBoxOfficeList";
        String format = "json";
        String url = "%s/%s.%s?key=%s&targetDt=%s";
        String responseBody = callAPI(param, url, action, format);
        MovieResponse movieResponse = objectMapper.readValue(responseBody, MovieResponse.class);
        return movieResponse.boxOfficeResult().dailyBoxOfficeList()
                .stream().map((v) -> new MovieDTO(Long.parseLong(v.rank()), v.movieCd(), v.movieNm(), v.openDt(), Long.parseLong(v.audiAcc()))).toList();
    }
}
