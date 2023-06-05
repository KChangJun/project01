package com.groupc.weather.service;

import org.springframework.http.ResponseEntity;

import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.follow.DeleteFollowRequestDto;
import com.groupc.weather.dto.request.follow.FollowRequestDto;
import com.groupc.weather.dto.request.user.DeleteUserRequestDto;
import com.groupc.weather.dto.request.user.FindByEmailRequestDto;
import com.groupc.weather.dto.request.user.FindByPasswordRequestDto;
import com.groupc.weather.dto.request.user.LoginUserRequestDto;
import com.groupc.weather.dto.request.user.PatchUserRequestDto;
//import com.groupc.weather.dto.request.user.PatchUserRequestDto2;
import com.groupc.weather.dto.request.user.PostUserRequestDto;
import com.groupc.weather.dto.response.user.FindByEmailResponseDto;
import com.groupc.weather.dto.response.user.FindByPasswordResponseDto;
import com.groupc.weather.dto.response.user.FollowerUserResponseDto;
import com.groupc.weather.dto.response.user.FollowingUserResponseDto;
import com.groupc.weather.dto.response.user.GetTop5FollowerResponseDto;
import com.groupc.weather.dto.response.user.GetUserResponseDto;
import com.groupc.weather.dto.response.user.LoginUserResponseDto;

public interface UserService {
    public ResponseEntity<ResponseDto> postUser(PostUserRequestDto dto);

    public ResponseEntity<? super LoginUserResponseDto> LoginUser(LoginUserRequestDto dto);

    public ResponseEntity<? super FindByEmailResponseDto> FindByEmail(FindByEmailRequestDto dto);

    public ResponseEntity<? super FindByPasswordResponseDto> FindByPassword(FindByPasswordRequestDto dto);

    public ResponseEntity<ResponseDto> patchUser(PatchUserRequestDto dto);

    // public ResponseEntity<ResponseDto> patchUser(String userEmail,
    // PatchUserRequestDto2 requestBody);
    public ResponseEntity<ResponseDto> deleteUser(DeleteUserRequestDto dto);

    public ResponseEntity<? super GetUserResponseDto> getUser(Integer userNumber);

    public ResponseEntity<ResponseDto> followUser(FollowRequestDto dto);

    public ResponseEntity<ResponseDto> deleteFollow(DeleteFollowRequestDto dto);

    public ResponseEntity<? super GetTop5FollowerResponseDto> getFollowerTop5();

    public ResponseEntity<? super FollowerUserResponseDto> getFollowerUser(Integer followerNumber);

    public ResponseEntity<? super FollowingUserResponseDto> getFollowingUser(Integer followingNumber);
}
