package com.groupc.weather.dto.request.qnaBoard;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostQnaCommentRequestDto {
    @NotNull
    private int writerNumber;
    @NotNull
    private int qnaBoardNumber;
    @NotBlank
    private String qnaCommentContent;
    
}
