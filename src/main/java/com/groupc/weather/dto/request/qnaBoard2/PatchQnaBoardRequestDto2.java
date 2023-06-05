package com.groupc.weather.dto.request.qnaBoard2;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchQnaBoardRequestDto2 {
    @NotNull
    private Integer qnaBoardNumber;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String imageUrl;
}
