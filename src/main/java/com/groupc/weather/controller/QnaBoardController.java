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

import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.qnaBoard2.PatchQnaBoardRequestDto2;
import com.groupc.weather.dto.request.qnaBoard2.PostQnaBoardRequestDto2;
import com.groupc.weather.dto.response.qnaBoard.GetQnaBoardListResponseDto;
import com.groupc.weather.dto.response.qnaBoard.GetQnaBoardResponseDto;
import com.groupc.weather.service.QnaBoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/qnaBoard")
@RequiredArgsConstructor
public class QnaBoardController {

    private final QnaBoardService qnaBoardService;

    //* 1. 게시물 등록 */
    //! isManager 필요하다면 이거 사용 
    @PostMapping("")
    public ResponseEntity<ResponseDto> postBoard(
        @AuthenticationPrincipal AuthenticationObject authenticationObject,
        @Valid @RequestBody PostQnaBoardRequestDto2 requestBody
    ){
        ResponseEntity<ResponseDto> response =
            qnaBoardService.postQnaBoard(authenticationObject, requestBody);
        return response;
    }
    
    //* 2. 특정 게시물 조회 */
    @GetMapping("/{qnaBoardNumber}")
    public ResponseEntity<? super GetQnaBoardResponseDto> getQnaBoard(
        @AuthenticationPrincipal AuthenticationObject authenticationObject,
        @PathVariable Integer qnaBoardNumber 
    ) {
        ResponseEntity<? super GetQnaBoardResponseDto> response =
            qnaBoardService.getQnaBoard(authenticationObject, qnaBoardNumber);
        return response;
    }

    //* 3. 게시물 목록 조회 */
    @GetMapping("list")
    public ResponseEntity<? super GetQnaBoardListResponseDto> getQnaBoardList() {
        ResponseEntity<? super GetQnaBoardListResponseDto> response =
            qnaBoardService.getQnaBoardList();
        return response;
    }

    //* 4. 특정 게시물 수정(본인만) */
    @PatchMapping("")
    public ResponseEntity<ResponseDto> patchQnaBoard(
        @AuthenticationPrincipal AuthenticationObject authenticationObject,
        @Valid @RequestBody PatchQnaBoardRequestDto2 requestBody
    ) {
        ResponseEntity<ResponseDto> response =
            qnaBoardService.patchQnaBoard(authenticationObject, requestBody);
        return response;
    }

    //* 5. 특정 게시물 삭제(본인, 관리자만) */
    @DeleteMapping("/{qnaBoardNumber}")
    public ResponseEntity<ResponseDto> deleteQnaBoard(
        @AuthenticationPrincipal AuthenticationObject authenticationObject,
        @PathVariable("qnaBoardNumber") Integer qnaBoardNumber
    ) {
        ResponseEntity<ResponseDto> response =
            qnaBoardService.deleteQnaBoard(authenticationObject, qnaBoardNumber);
        return response;
    }

    //* 6. 특정 게시물 검색 */
    @GetMapping("search/{searchWord}")
    public ResponseEntity<? super GetQnaBoardListResponseDto> searchQnaBoard(
        @PathVariable("searchWord") String searchWord
    ) {
        ResponseEntity<? super GetQnaBoardListResponseDto> response =
            qnaBoardService.getSearchQnaBoardList(searchWord);
        return response;
    }


    
}
