package com.groupc.weather.service;

import org.springframework.http.ResponseEntity;

import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.qnaBoard.PatchQnaBoardRequestDto;
import com.groupc.weather.dto.request.qnaBoard.PostQnaBoardRequestDto;
import com.groupc.weather.dto.request.qnaBoard2.PatchQnaBoardRequestDto2;
import com.groupc.weather.dto.request.qnaBoard2.PostQnaBoardRequestDto2;
import com.groupc.weather.dto.response.qnaBoard.GetQnaBoardListResponseDto;

import com.groupc.weather.dto.response.qnaBoard.GetQnaBoardResponseDto;

public interface QnaBoardService {
    public ResponseEntity<ResponseDto> postQnaBoard(PostQnaBoardRequestDto dto);
    public ResponseEntity<ResponseDto> postQnaBoard(AuthenticationObject authenticationObject, PostQnaBoardRequestDto2 dto);

    public ResponseEntity<? super GetQnaBoardResponseDto> getQnaBoard(Integer boardNumber);
    public ResponseEntity<? super GetQnaBoardResponseDto> getQnaBoard(AuthenticationObject authenticationObject, Integer boardNumber);
    public ResponseEntity<? super GetQnaBoardListResponseDto> getQnaBoardList();
    public ResponseEntity<? super GetQnaBoardListResponseDto> getSearchQnaBoardList(String searchWord);

    public ResponseEntity<ResponseDto> patchQnaBoard(PatchQnaBoardRequestDto dto);
    public ResponseEntity<ResponseDto> patchQnaBoard(AuthenticationObject authenticationObject, PatchQnaBoardRequestDto2 dto);
    
    public ResponseEntity<ResponseDto> deleteQnaBoard(Integer userNumber, Integer boardNumber);
    public ResponseEntity<ResponseDto> deleteQnaBoard(AuthenticationObject authenticationObject, Integer boardNumber);



    
}
