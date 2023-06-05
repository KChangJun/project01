package com.groupc.weather.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.qnaBoard.PatchQnaCommentRequestDto2;
import com.groupc.weather.dto.request.qnaBoard.PostQnaCommentRequestDto2;
import com.groupc.weather.service.QnaCommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/qnaComment")
public class QnaCommentController {
    private final QnaCommentService qnaCommentService2;
    
    @PostMapping("/post")
    public ResponseEntity<ResponseDto> postQnaComment(@AuthenticationPrincipal AuthenticationObject authenticationObject,
        @Valid @RequestBody PostQnaCommentRequestDto2 requestBody

    ){
        ResponseEntity<ResponseDto> response = qnaCommentService2.postQnaComment(authenticationObject,requestBody);
        return response;
    }

    @PatchMapping("/patch")
    public ResponseEntity<ResponseDto> patchQnaComment(@AuthenticationPrincipal AuthenticationObject authenticationObject,
        @Valid @RequestBody PatchQnaCommentRequestDto2 requestBody
    )
    {
        ResponseEntity<ResponseDto> response = qnaCommentService2.patchQnaComment(authenticationObject,requestBody);
        return response;
    }

    @DeleteMapping("/delete/{qnaCommentNumber}")
    public ResponseEntity<ResponseDto> deleteQnaComment(
        @AuthenticationPrincipal AuthenticationObject authenticationObject,
        @PathVariable("qnaCommentNumber") Integer qnaCommentNumber
    )
    {
        ResponseEntity<ResponseDto> response = qnaCommentService2.deleteQnaComment(authenticationObject,qnaCommentNumber);
        return response;
    }


}
