package dev.qus0in.springfromscratch.controller;

import dev.qus0in.springfromscratch.model.dto.MovieInfoDTO;
import dev.qus0in.springfromscratch.service.GeminiService;
import dev.qus0in.springfromscratch.service.MovieService;
import dev.qus0in.springfromscratch.util.NowDate;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class MovieController {
    final MovieService movieService;
    final GeminiService geminiService;

    public MovieController(MovieService movieService, GeminiService geminiService) {
        this.movieService = movieService;
        this.geminiService = geminiService;
    }


    @GetMapping("/")
    public String index(Model model, HttpSession session) throws Exception {
//        List<MovieDTO> movies = movieService.getMovies();
        String nowDateStr = NowDate.str();
        List<MovieInfoDTO> movies = movieService.getMovieInfos();

        if (session.getAttribute("lastUpdateDate") == null || !session.getAttribute("lastUpdateDate").equals(nowDateStr)) {
            String prompt = "%s, 앞의 데이터를 바탕으로 영화를 추천하고 그 이유를 작성. 생각의 과정을 노출하지 않고 결과만. no markdown, just plain-text, in korean language".formatted(movies.toString());
            String recommendation = geminiService.callGemini(prompt);
            session.setAttribute("lastUpdateDate", nowDateStr);
            session.setAttribute("recommendation", recommendation);
        }
        model.addAttribute("movies", movies);
        model.addAttribute("recommendation", session.getAttribute("recommendation"));
        return "index";
    }
}
