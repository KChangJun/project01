package com.groupc.weather.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupc.weather.dto.response.board.GetBoardFirstViewDto;
import com.groupc.weather.dto.response.board.GetBoardListResponseDto;
import com.groupc.weather.dto.response.board.GetBoardListResponsetop5Dto;
import com.groupc.weather.dto.response.board.GetBoardResponseDto;
import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.board.LikeRequestDto;
import com.groupc.weather.dto.request.board.PatchBoardRequestDto;
import com.groupc.weather.dto.request.board.PostBoardRequestDto2;
import com.groupc.weather.service.BoardService;
import com.groupc.weather.service.implement.BoardServiceImplement;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/photoBoard")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardServiceImplement boardServiceImplement;
    
    // 1. 게시물 작성
    @PostMapping("/post")
    public ResponseEntity<ResponseDto> postBoard(
        @AuthenticationPrincipal AuthenticationObject authenticationObject,
            @Valid @RequestBody PostBoardRequestDto2 requestBody){
        ResponseEntity<ResponseDto> response = boardService.postBoard(authenticationObject, requestBody);
        return response;
    }

    // 2. 특정게시물 조회
    @GetMapping("/view/{boardNumber}")
    public ResponseEntity<? super GetBoardResponseDto> getBoard(
            @PathVariable("boardNumber") Integer boardNumber) {
        ResponseEntity<? super GetBoardResponseDto> response = boardService.getBoard(boardNumber);
        return response;
    }

    // 3. 게시물 목록 조회(본인 작성)
    @GetMapping("/myself")
    public ResponseEntity<? super GetBoardListResponseDto> getBoardMyList(
        @AuthenticationPrincipal AuthenticationObject authenticationObject
    ){
        ResponseEntity<? super GetBoardListResponseDto> response = 
        boardService.getBoardMyList(authenticationObject);
        return response;
    }

    // 4. TOP5 게시물 목록 조회
    @GetMapping("/top5")
    public ResponseEntity<? super GetBoardListResponsetop5Dto>getBoardtop5(){
        ResponseEntity<? super GetBoardListResponsetop5Dto> response = boardService.getBoardTop5();
        return response;
    }

    // 5. 일반게시물 목록 조회(최신순)
    @GetMapping("/list")
    public ResponseEntity<? super GetBoardListResponseDto> getBoard(){
        ResponseEntity<? super GetBoardListResponseDto> response = boardService.getBoardList();
        return response;
    }

    // 6. 첫화면 일반 게시물 목록
    @GetMapping("/firstImage")
    public ResponseEntity<? super GetBoardFirstViewDto> getBoardFirstView(){
        ResponseEntity<? super GetBoardFirstViewDto> response = boardService.getBoardFirstView();
        return response;
    }

    // 7. 특정 게시물 수정
    @PatchMapping("/patch")
    public ResponseEntity<ResponseDto> patchBoard(
        @AuthenticationPrincipal AuthenticationObject authenticationObject,
        @Valid @RequestBody PatchBoardRequestDto dto
    ){
        ResponseEntity<ResponseDto> response = boardService.patchBoard(authenticationObject, dto);
        return response;
    }

    // 8 . 특정 게시물 삭제
    @DeleteMapping("/delete/{userNumber}/{boardNumber}")
    public ResponseEntity<ResponseDto> deleteBoard(
            @PathVariable("userNumber") Integer userNumber,
            @PathVariable("boardNumber") Integer boardNumber
    ){
        ResponseEntity<ResponseDto> response = boardService.deleteBoard(userNumber, boardNumber);
        return response;
    }
   

    // 9. 특정 게시물 좋아요 등록
    @PostMapping("/like")
    public ResponseEntity<ResponseDto> likeBoard(
        @AuthenticationPrincipal AuthenticationObject authenticationObject,
        @Valid @RequestBody LikeRequestDto dto
    ){
        ResponseEntity<ResponseDto> response = boardService.likeBoard(authenticationObject, dto);
        return response;
    }

    
    // 10. 특정 게시물 좋아요 해제
    @DeleteMapping("/likeDelete")
    public ResponseEntity<ResponseDto> likeDeleteBoard(
        @AuthenticationPrincipal AuthenticationObject authenticationObject,
        @Valid @RequestBody LikeRequestDto dto
    ){
        ResponseEntity<ResponseDto> response = boardService.likeDeleteBoard(authenticationObject, dto);
        return response;
    }
    
    
    // 11. 특정 유저 좋아요 게시물 조회
    @GetMapping("/Likelist/{userNumber}") // like를 붙여야하나,,,?
    public ResponseEntity<? super GetBoardListResponseDto> getLikeBoardList(
        @AuthenticationPrincipal AuthenticationObject authenticationObject,
        @PathVariable("userNumber") Integer userNumber
        ) {
            ResponseEntity<? super GetBoardListResponseDto> response = boardService.getLikeBoardList(authenticationObject, userNumber);
            return response;
        }
        
        // 12-1. 특정 게시물 검색
        @GetMapping("/{searchWord}/{weatherInfo}/{temperature}") // 이렇게 쓰는 게 맞는 건지..
        public ResponseEntity<? super GetBoardListResponseDto> searchListByWord(
            @PathVariable("searchWord") String searchWord
            
            ) {
                ResponseEntity<? super GetBoardListResponseDto> response =
                boardService.getSearchListByWord(searchWord);
                return response;
            }
            
        // 12-2. 특정 게시물 검색 (검색어 + 날씨)
        @GetMapping("search/{searchWord}/{weather}")
        public ResponseEntity<? super GetBoardListResponseDto> searchListByWordAndWeather(
                @PathVariable("searchWord") String searchWord,
                @PathVariable("weather") String weather) {
            ResponseEntity<? super GetBoardListResponseDto> response = 
            boardServiceImplement.getSearchListByWord(searchWord, weather);
            return response;
        }
        
        // 12-3. 특정 게시물 검색 (검색어 + 기온)
        @GetMapping("search/{searchWord}/{minTemperature}/{maxTemperature}")
        public ResponseEntity<? super GetBoardListResponseDto> searchListByWordAndTemperature(
                @PathVariable("searchWord") String searchWord,
                @PathVariable("minTemperature") Integer minTemperature,
                @PathVariable("maxTemperature") Integer maxTemperature) {
            ResponseEntity<? super GetBoardListResponseDto> response = 
            boardServiceImplement.getSearchListByWord(searchWord, minTemperature, maxTemperature);
            return response;
        }
        
            // 12-4. 특정 게시물 검색 (검색어 + 날씨 + 기온)
            @GetMapping("search/{searchWord}/{weather}/{minTemperature}/{maxTemperature}")
            public ResponseEntity<? super GetBoardListResponseDto> searchListByWordAndAll(
                @PathVariable("searchWord") String searchWord,
                @PathVariable("weather") String weather,
                @PathVariable("minTemperature") Integer temperature,
                @PathVariable("maxTemperature") Integer maxTemperature
            ) {
                ResponseEntity<? super GetBoardListResponseDto> response =
                    boardServiceImplement.getSearchListByWord(searchWord, weather, temperature, maxTemperature);
                return response;
            }

    // 13-1. 특정 게시물 검색(해쉬태그)
    @GetMapping("/searchHashTag/{hashtag}")
    public ResponseEntity<? super GetBoardListResponseDto> hashtagSearch(
        @PathVariable("hashtag") String hashtag
    ){
        ResponseEntity<? super GetBoardListResponseDto> response =
            boardService.getSearchListByHashtag(hashtag);
        return response;
    }

    // 13-2. 특정 게시물 검색(해시태그 + 날씨)
    @GetMapping("searchHash/{hashtag}/{weather}")
    public ResponseEntity<? super GetBoardListResponseDto> getSearchListByHashtagAndWeather(
        @PathVariable("hashtag") String hashtag,
        @PathVariable("weather") String weather
    ) {
        ResponseEntity<? super GetBoardListResponseDto> response =
            boardServiceImplement.getSearchListByHashtag(hashtag, weather);
        return response;
    }

    // 13-3. 특정 게시물 검색(해시태그 + 기온)
    @GetMapping("searchHash/{hashtag}/{minTemperature}/{maxTemperature}")
    public ResponseEntity<? super GetBoardListResponseDto> getSearchListByHashtagAndTemperature(
        @PathVariable("hashtag") String hashtag,
        @PathVariable("minTemperature") Integer minTemperature,
        @PathVariable("maxTemperature") Integer maxTemperature
    ) {
        ResponseEntity<? super GetBoardListResponseDto> response =
            boardServiceImplement.getSearchListByHashtag(hashtag, minTemperature, maxTemperature);
        return response;
    }

    // 13-4. 특정 게시물 검색(해시태그 + 날씨 + 기온)
    @GetMapping("searchHash/{hashtag}/{weather}/{minTemperature}/{maxTemperature}")
    public ResponseEntity<? super GetBoardListResponseDto> getSearchListByHashtagAndAll(
        @PathVariable("hashtag") String hashtag,
        @PathVariable("weather") String weather,
        @PathVariable("minTemperature") Integer minTemperature,
        @PathVariable("maxTemperature") Integer maxTemperature
    ) {
        ResponseEntity<? super GetBoardListResponseDto> response =
            boardServiceImplement.getSearchListByHashtag(hashtag, weather, minTemperature, maxTemperature);
        return response;
    }
}
