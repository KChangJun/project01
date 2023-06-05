package com.groupc.weather.dto.request.board;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteCommentRequestDto2 {
    
    @NotNull
    private Integer commentNumber;

}