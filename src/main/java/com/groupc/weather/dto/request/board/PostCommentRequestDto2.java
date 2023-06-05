package com.groupc.weather.dto.request.board;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostCommentRequestDto2 { 

    @NotNull
    private Integer boardNumber;
    @NotBlank
    private String commentContent;
    
}

