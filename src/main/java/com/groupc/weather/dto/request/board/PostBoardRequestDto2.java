package com.groupc.weather.dto.request.board;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostBoardRequestDto2 {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private List<String> imageUrlList;
    private List<String> hashtagList;
    private String location;
}
