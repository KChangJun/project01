package com.groupc.weather.dto.request.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteUserRequestDto {
    @NotNull
    private Integer userNumber;
    @NotBlank
    protected String userPassword;
}
