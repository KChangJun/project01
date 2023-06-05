package com.groupc.weather.dto.request.follow;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteFollowRequestDto {
    @NotNull
    private Integer userNumber;
    @NotNull
    private Integer followingUserNumber;
}
