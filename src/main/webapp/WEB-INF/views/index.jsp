<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="dev.qus0in.springfromscratch.model.dto.MovieInfoDTO" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="영화진흥위원회 API 기반 일일 박스오피스 순위와 AI 영화 추천 서비스">
    <meta name="keywords" content="영화, 추천, 박스오피스, 영화 순위, 한국 영화, AI 추천">
    <meta name="author" content="영화 추천 서비스">
    <meta name="robots" content="index, follow">

    <!-- Open Graph / 소셜 미디어 -->
    <meta property="og:title" content="오늘의 영화 추천 | 박스오피스 순위">
    <meta property="og:description" content="한국 박스오피스 기준 일일 영화 순위와 AI 추천 서비스">
    <meta property="og:type" content="website">
    <meta property="og:locale" content="ko_KR">

    <!-- 기타 메타 태그 -->
    <meta name="theme-color" content="#6a11cb">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <title>오늘의 영화 추천 서비스</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* 기존 웹폰트 보존 */
        @font-face {
            font-family: 'MaruBuri';
            src: url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-Regular.eot);
            src: url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-Regular.eot?#iefix) format("embedded-opentype"), url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-Regular.woff2) format("woff2"), url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-Regular.woff) format("woff"), url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-Regular.ttf) format("truetype");
        }

        @font-face {
            font-family: 'MaruBuriSemiBold';
            src: url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-SemiBold.eot);
            src: url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-SemiBold.eot?#iefix) format("embedded-opentype"), url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-SemiBold.woff2) format("woff2"), url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-SemiBold.woff) format("woff"), url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-SemiBold.ttf) format("truetype");
        }

        @font-face {
            font-family: 'MaruBuriBold';
            src: url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-Bold.eot);
            src: url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-Bold.eot?#iefix) format("embedded-opentype"), url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-Bold.woff2) format("woff2"), url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-Bold.woff) format("woff"), url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-Bold.ttf) format("truetype");
        }

        @font-face {
            font-family: 'MaruBuriLight';
            src: url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-Light.eot);
            src: url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-Light.eot?#iefix) format("embedded-opentype"), url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-Light.woff2) format("woff2"), url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-Light.woff) format("woff"), url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-Light.ttf) format("truetype");
        }

        @font-face {
            font-family: 'MaruBuriExtraLight';
            src: url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-ExtraLight.eot);
            src: url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-ExtraLight.eot?#iefix) format("embedded-opentype"), url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-ExtraLight.woff2) format("woff2"), url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-ExtraLight.woff) format("woff"), url(https://hangeul.pstatic.net/hangeul_static/webfont/MaruBuri/MaruBuri-ExtraLight.ttf) format("truetype");
        }

        /* 커스텀 스타일 */
        body {
            font-family: 'MaruBuri', serif;
            background-color: #EEEEEE;
            color: #222831;
        }

        .card {
            transition: transform 0.3s ease;
            margin-bottom: 20px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            height: 100%;
            border: none;
        }

        .card:hover {
            transform: translateY(-5px);
        }

        .movie-rank {
            display: inline-block;
            background-color: #00ADB5;
            color: white;
            border-radius: 4px;
            padding: 3px 8px;
            font-family: 'MaruBuriBold', serif;
            margin-right: 8px;
        }

        .movie-title {
            font-family: 'MaruBuriSemiBold', serif;
            font-size: 1.2rem;
            margin-bottom: 10px;
            display: flex;
            align-items: center;
        }

        .movie-info {
            font-family: 'MaruBuriLight', serif;
            font-size: 0.9rem;
            color: #393E46;
        }

        .movie-audience {
            font-family: 'MaruBuriSemiBold', serif;
            color: #00ADB5;
        }

        .recommendation-section {
            background-color: #393E46;
            color: #EEEEEE;
            border-radius: 10px;
            padding: 25px;
            margin-top: 30px;
            margin-bottom: 40px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .recommendation-title {
            font-family: 'MaruBuriBold', serif;
            color: #00ADB5;
            margin-bottom: 20px;
            border-bottom: 2px solid #00ADB5;
            padding-bottom: 10px;
        }

        .recommendation-content {
            font-family: 'MaruBuri', serif;
            line-height: 1.8;
            white-space: pre-line;
        }

        .header {
            background: #222831;
            color: #EEEEEE;
            padding: 40px 0;
            margin-bottom: 40px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
        }

        .header h1 {
            font-family: 'MaruBuriBold', serif;
            color: #00ADB5;
        }

        .header p {
            font-family: 'MaruBuriLight', serif;
            opacity: 0.9;
        }

        .badge {
            margin-right: 5px;
            margin-bottom: 5px;
        }

        .badge-genre {
            background-color: #393E46;
        }

        .badge-nation {
            background-color: #00ADB5;
        }

        .badge-director {
            background-color: #00ADB5;
            opacity: 0.8;
        }

        .badge-actor {
            background-color: #393E46;
            color: #EEEEEE !important;
        }

        footer {
            background-color: #222831;
            color: #EEEEEE;
            padding: 20px 0;
            margin-top: 40px;
            font-family: 'MaruBuriLight', serif;
        }

        .section-title {
            color: #222831;
            font-family: 'MaruBuriBold', serif;
            margin-bottom: 1.5rem;
            border-left: 5px solid #00ADB5;
            padding-left: 15px;
        }

        @media (max-width: 768px) {
            .header {
                padding: 20px 0;
            }

            .movie-title {
                font-size: 1rem;
                min-height: 40px;
            }

            .recommendation-section {
                padding: 15px;
            }
        }
    </style>
</head>
<body>
<!-- Header -->
<header class="header text-center">
    <div class="container">
        <h1>오늘의 영화 추천</h1>
        <p>한국 박스오피스 기준 일일 영화 순위와 AI 추천</p>
    </div>
</header>

<div class="container">
    <!-- 영화 추천 섹션 -->
    <div class="recommendation-section">
        <h2 class="recommendation-title">AI의 영화 추천</h2>
        <div class="recommendation-content">
            <%= request.getAttribute("recommendation") %>
        </div>
    </div>

    <!-- 영화 목록 섹션 -->
    <h2 class="section-title mb-4">박스오피스 순위</h2>
    <div class="row">
        <%
            List<MovieInfoDTO> movies = (List<MovieInfoDTO>) request.getAttribute("movies");
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);

            if (movies != null) {
                for (MovieInfoDTO movie : movies) {
        %>
        <div class="col-md-6 col-lg-4 mb-4">
            <div class="card position-relative">
                <div class="card-body">
                    <h5 class="movie-title">
                        <span class="movie-rank"><%= movie.movie().rank() %></span>
                        <%= movie.movie().name() %>
                    </h5>

                    <!-- 개봉일 및 상영시간 -->
                    <p class="movie-info">
                        <strong>개봉일:</strong> <%= movie.movie().openDate() %><br>
                        <strong>상영시간:</strong> <%= movie.time() %>분
                    </p>

                    <!-- 국가 -->
                    <p class="movie-info">
                        <strong>국가:</strong>
                        <% for (String nation : movie.nations()) { %>
                        <span class="badge bg-success badge-nation"><%= nation %></span>
                        <% } %>
                    </p>

                    <!-- 장르 -->
                    <p class="movie-info">
                        <strong>장르:</strong>
                        <% for (String genre : movie.genres()) { %>
                        <span class="badge bg-secondary badge-genre"><%= genre %></span>
                        <% } %>
                    </p>

                    <!-- 감독 -->
                    <p class="movie-info">
                        <strong>감독:</strong>
                        <% for (String director : movie.directors()) { %>
                        <span class="badge bg-info badge-director"><%= director %></span>
                        <% } %>
                    </p>

                    <!-- 배우 (최대 3명만 표시) -->
                    <p class="movie-info">
                        <strong>출연:</strong><br>
                        <%
                            List<String> actors = movie.actors();
                            int actorCount = Math.min(actors.size(), 3);
                            for (int i = 0; i < actorCount; i++) {
                        %>
                        <span class="badge bg-warning text-dark badge-actor"><%= actors.get(i) %></span>
                        <% }
                            if (actors.size() > 3) {
                        %>
                        <span class="badge bg-light text-dark">외 <%= actors.size() - 3 %>명</span>
                        <% } %>
                    </p>

                    <!-- 누적 관객 수 -->
                    <p class="movie-audience mt-3">
                        <strong>누적 관객수:</strong> <%= numberFormat.format(movie.movie().audience()) %>명
                    </p>
                </div>
            </div>
        </div>
        <%
                }
            }
        %>
    </div>
</div>

<!-- Footer -->
<footer class="text-center">
    <div class="container">
        <p>© <%= java.time.Year.now().getValue() %> 영화 추천 서비스 | 영화진흥위원회 오픈API 활용</p>
    </div>
</footer>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>