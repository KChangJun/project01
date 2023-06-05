package com.groupc.weather.service;

import org.springframework.http.ResponseEntity;


import com.groupc.weather.dto.response.board.GetBoardListResponseDto;
import com.groupc.weather.dto.response.board.GetBoardListResponsetop5Dto;
import com.groupc.weather.dto.response.board.GetBoardResponseDto;
import com.groupc.weather.dto.response.board.GetBoardFirstViewDto;
import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.board.LikeRequestDto;
import com.groupc.weather.dto.request.board.PatchBoardRequestDto;
import com.groupc.weather.dto.request.board.PostBoardRequestDto2;


public interface BoardService {
    
    public ResponseEntity<ResponseDto> postBoard(AuthenticationObject authenticationObject, PostBoardRequestDto2 dto);
    
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);
    public ResponseEntity<? super GetBoardListResponseDto> getBoardMyList(AuthenticationObject authenticationObject); //게시물 조회(본인 작성)
    public ResponseEntity<? super GetBoardListResponsetop5Dto> getBoardTop5();
    public ResponseEntity<? super GetBoardListResponseDto> getBoardList();
    public ResponseEntity<? super GetBoardFirstViewDto> getBoardFirstView();

    public ResponseEntity<ResponseDto> patchBoard(AuthenticationObject authenticationObject, PatchBoardRequestDto dto); 

    public ResponseEntity<ResponseDto> deleteBoard(Integer userNumber, Integer boardNumber);

    public ResponseEntity<ResponseDto> likeBoard(AuthenticationObject authenticationObject, LikeRequestDto dto);
    public ResponseEntity<ResponseDto> likeDeleteBoard(AuthenticationObject authenticationObject, LikeRequestDto dto);
    
    public ResponseEntity<? super GetBoardListResponseDto> getLikeBoardList(AuthenticationObject authenticationObject,Integer userNumber);
    
    public ResponseEntity<? super GetBoardListResponseDto> getSearchListByWord(String searchWord);
    public ResponseEntity<? super GetBoardListResponseDto> getSearchListByHashtag(String hashtag);
   
}
