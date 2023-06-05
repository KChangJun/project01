
package com.groupc.weather.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.follow.DeleteFollowRequestDto;
import com.groupc.weather.dto.request.follow.FollowRequestDto;
import com.groupc.weather.dto.request.user.DeleteUserRequestDto;
import com.groupc.weather.dto.request.user.FindByEmailRequestDto;
import com.groupc.weather.dto.request.user.FindByPasswordRequestDto;
import com.groupc.weather.dto.request.user.LoginUserRequestDto;
import com.groupc.weather.dto.request.user.PatchUserRequestDto;
import com.groupc.weather.dto.request.user.PostUserRequestDto;
import com.groupc.weather.dto.response.user.FindByEmailResponseDto;
import com.groupc.weather.dto.response.user.FindByPasswordResponseDto;
import com.groupc.weather.dto.response.user.FollowerUserResponseDto;
import com.groupc.weather.dto.response.user.FollowingUserResponseDto;
import com.groupc.weather.dto.response.user.GetTop5FollowerResponseDto;
import com.groupc.weather.dto.response.user.GetUserResponseDto;
import com.groupc.weather.dto.response.user.LoginUserResponseDto;
import com.groupc.weather.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 유저 등록
    @PostMapping("sign-up")
    public ResponseEntity<ResponseDto> postUser(
            @Valid @RequestBody PostUserRequestDto requestBody) {
        ResponseEntity<ResponseDto> resposne = userService.postUser(requestBody);
        return resposne;
    }

    // 유저 로그인
    @PostMapping("sign-in")
    public ResponseEntity<? super LoginUserResponseDto> LoginUser(
            @Valid @RequestBody LoginUserRequestDto requestBody) {
        ResponseEntity<? super LoginUserResponseDto> response = userService.LoginUser(requestBody);
        return response;
    }

    // 유저 이메일 찾기
    @PostMapping("find-email")
    public ResponseEntity<? super FindByEmailResponseDto> FindByemail(
            @Valid @RequestBody FindByEmailRequestDto requestBody) {
        ResponseEntity<? super FindByEmailResponseDto> response = userService.FindByEmail(requestBody);
        return response;
    }

    // 유저 비밀번호 찾기
    @PostMapping("find-password")
    public ResponseEntity<? super FindByPasswordResponseDto> FindByPassword(
            @Valid @RequestBody FindByPasswordRequestDto requestBody) {
        ResponseEntity<? super FindByPasswordResponseDto> response = userService.FindByPassword(requestBody);
        return response;
    }

    // 유저 정보 수정
    @PatchMapping("user-update")
    public ResponseEntity<ResponseDto> patchUser(
            @Valid @RequestBody PatchUserRequestDto requestBody) {
        ResponseEntity<ResponseDto> response = userService.patchUser(requestBody);
        return response;
    }

    // 유저 정보 삭제
    @DeleteMapping("user-delete")
    public ResponseEntity<ResponseDto> deleteByUser(
            @Valid @RequestBody DeleteUserRequestDto requestBody) {
        ResponseEntity<ResponseDto> response = userService.deleteUser(requestBody);
        return response;
    }

    // 특정 유저 조회.
    @GetMapping("/{userNumber}")
    public ResponseEntity<? super GetUserResponseDto> getUser(
            @PathVariable("userNumber") Integer userNumber) {
        ResponseEntity<? super GetUserResponseDto> response = userService.getUser(userNumber);
        return response;
    }

    // 특정 유저 Follow
    @PostMapping("follow")
    public ResponseEntity<ResponseDto> followUser(
            @Valid @RequestBody FollowRequestDto requestBody) {
        ResponseEntity<ResponseDto> response = userService.followUser(requestBody);
        return response;
    }

    // 유저 Follow 해제
    @DeleteMapping("follow-delete")
    public ResponseEntity<ResponseDto> deleteFollow(
            @Valid @RequestBody DeleteFollowRequestDto requestBody) {
        ResponseEntity<ResponseDto> response = userService.deleteFollow(requestBody);
        return response;

    }

    // Top5 팔로워 조회
    @GetMapping("top5-follow")
    public ResponseEntity<? super GetTop5FollowerResponseDto> followerTop5() {
        ResponseEntity<? super GetTop5FollowerResponseDto> response = userService.getFollowerTop5();
        return response;
    }

    // follower 유저 조회(나를 팔로우 한사람)
    @GetMapping("/follower/{followerUser}")
    public ResponseEntity<? super FollowerUserResponseDto> followerUser(
            @PathVariable("followerUser") Integer followerUser) {

        ResponseEntity<? super FollowerUserResponseDto> response = userService.getFollowerUser(followerUser);

        return response;
    }

    // following 유저 조회(내가 팔로우 한사람)
    @GetMapping("/following/{followingUser}")
    public ResponseEntity<? super FollowingUserResponseDto> followingUser(
            @PathVariable("followingUser") Integer followingUser) {

        ResponseEntity<? super FollowingUserResponseDto> response = userService.getFollowingUser(followingUser);

        return response;
    }
}
