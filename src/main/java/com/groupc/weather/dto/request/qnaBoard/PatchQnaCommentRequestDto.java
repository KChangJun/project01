package com.groupc.weather.dto.request.qnaBoard;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchQnaCommentRequestDto {
    @NotNull
    private int writerNumber;
    @NotBlank
    private int qnaBoardNumber;
    @NotBlank
    private int qnaCommentNumber;
    @NotBlank
    private String qnaCommentContent;
}
