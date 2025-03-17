package dev.qus0in.springfromscratch.model.dto;

import java.util.List;

public record MovieCacheDTO(String boxOfficeDate, List<MovieInfoDTO> data) {
}
