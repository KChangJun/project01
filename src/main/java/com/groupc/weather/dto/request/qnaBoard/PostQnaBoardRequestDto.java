package com.groupc.weather.dto.request.qnaBoard;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostQnaBoardRequestDto {
    @NotNull
    private int userNumber;
    @NotNull
    private String title;
    @NotNull
    private String content;

    private String imageUrl;
}
