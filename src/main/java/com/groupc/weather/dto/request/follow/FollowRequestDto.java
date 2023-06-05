package com.groupc.weather.dto.request.follow;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowRequestDto {
    @NotNull
    private Integer followerNumber;
    @NotNull
    private Integer followingNumber;
}
