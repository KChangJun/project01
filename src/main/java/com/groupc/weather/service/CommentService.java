package com.groupc.weather.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.board.DeleteCommentRequestDto2;
import com.groupc.weather.dto.request.board.PatchCommentRequestDto2;
import com.groupc.weather.dto.request.board.PostCommentRequestDto2;

public interface CommentService {

    public ResponseEntity<ResponseDto> postComment(AuthenticationObject authenticationObject,PostCommentRequestDto2 requestBody);
    public ResponseEntity<ResponseDto> patchComment(AuthenticationObject authenticationObject, PatchCommentRequestDto2 requestBody);
    public ResponseEntity<ResponseDto> deleteComment(AuthenticationObject authenticationObject, DeleteCommentRequestDto2 requestBody);
    
}
