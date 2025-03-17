package dev.qus0in.springfromscratch.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ImageResponse(List<Item> items) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Item(String link) {}
}
