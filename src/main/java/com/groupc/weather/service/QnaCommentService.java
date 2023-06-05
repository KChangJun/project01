package com.groupc.weather.service;

import org.springframework.http.ResponseEntity;

import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.qnaBoard.PatchQnaCommentRequestDto2;
import com.groupc.weather.dto.request.qnaBoard.PostQnaCommentRequestDto2;



public interface QnaCommentService {
    public ResponseEntity<ResponseDto> postQnaComment(AuthenticationObject authenticationObject,PostQnaCommentRequestDto2 dto);
    public ResponseEntity<ResponseDto> patchQnaComment(AuthenticationObject authenticationObject,PatchQnaCommentRequestDto2 dto);
    public ResponseEntity<ResponseDto> deleteQnaComment(AuthenticationObject authenticationObject, Integer qnaCommentNumber);

    //public ResponseEntity<? super GetBoardListResponseDto> getBoard(Integer BoardNumber);
}
