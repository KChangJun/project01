package com.groupc.weather.dto.request.qnaBoard;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchQnaCommentRequestDto2 {

    @NotNull
    private Integer qnaCommentNumber;
    @NotBlank
    private String qnaCommentContent;
}
