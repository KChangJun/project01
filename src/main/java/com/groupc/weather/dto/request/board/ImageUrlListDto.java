package com.groupc.weather.dto.request.board;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageUrlListDto {
    @NotNull
    private String imageUrl;
    
    // public ImageUrlListDto(ImageUrlListDto imageListResult){
    //     this.imageUrl = imageListResult.getImageUrl();
    // }
}
