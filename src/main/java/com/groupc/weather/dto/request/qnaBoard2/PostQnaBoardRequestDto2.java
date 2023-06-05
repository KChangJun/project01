package com.groupc.weather.dto.request.qnaBoard2;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostQnaBoardRequestDto2 {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String imageUrl;
}
