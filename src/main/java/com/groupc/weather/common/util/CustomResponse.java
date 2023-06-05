package com.groupc.weather.common.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.groupc.weather.dto.ResponseDto;

public class CustomResponse {

    public static ResponseEntity<ResponseDto> success() {
        ResponseDto body = new ResponseDto("SU", "SUCCESS");
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static ResponseEntity<ResponseDto> databaseError() {

        ResponseDto errorBody = new ResponseDto("DE", "Database Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> validationError() {

        ResponseDto errorBody = new ResponseDto("VF", "Request Parameter Validation Failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }


    public static ResponseEntity<ResponseDto> existUserEmail() {
        
        ResponseDto errorBody = new ResponseDto("EU","Existent User Email");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> existUserNickname() {

        ResponseDto errorBody = new ResponseDto("EN", "Existent User Nickname");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> alreadyLikeBoard() {

        ResponseDto errorBody = new ResponseDto("AL", "Already LikeBoard");
        return ResponseEntity.status(HttpStatus.OK).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> undifindeUsername() {

        ResponseDto errorBody = new ResponseDto("EN", "Existent Undifinde Name");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> undifindephonenumber() {

        ResponseDto errorBody = new ResponseDto("EP", "Undifinde Phonenumber");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> undifindeEmail() {

        ResponseDto errorBody = new ResponseDto("EM", "Undifinde Email");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    
    public static ResponseEntity<ResponseDto> undifindUserNumber() {

        ResponseDto errorBody = new ResponseDto("EP", "Undifinde User Number");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> existUserPhoneNumber() {

        ResponseDto errorBody = new ResponseDto("EP", "Existent User PhoneNumber");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> notExistBoardNumber() {

        ResponseDto errorBody = new ResponseDto("NB", "Not-Existent Board Number");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> notExistChattingRoomNumber() {

        ResponseDto errorBody = new ResponseDto("NC", "Not-Existent Chatting Room Number");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> signInFailed() {

        ResponseDto errorBody = new ResponseDto("SF", "Sign In Failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }


    public static ResponseEntity<ResponseDto> signInFailedEmail() {

        ResponseDto errorBody = new ResponseDto("SF", "Sign In Failed Undefinde Email");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> signInFailedPassword() {

        ResponseDto errorBody = new ResponseDto("SF", "Sign In Failed Undefinde Password");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> notExistUserNumber() {

        ResponseDto errorBody = new ResponseDto("NU", "Not-Existent User Number");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody); // 너가 누구인지 모른다
    }

    public static ResponseEntity<ResponseDto> notExistManagerNumber() {

        ResponseDto errorBody = new ResponseDto("NM", "Not-Existent Manager Number");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> notExistQnaCommentNumber() {
        
        ResponseDto errorBody = new ResponseDto("NQC", "Not-Existent Qna Comment Number");        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody); //너가 누구인지 모른다
    }

    public static ResponseEntity<ResponseDto> notLikeBoard() {

        ResponseDto errorBody = new ResponseDto("NB", "Not-Like Board");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
    }
    public static ResponseEntity<ResponseDto> signUpFailed() {

        ResponseDto errorBody = new ResponseDto("SF", "Sign Up Failed");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> noPermissions() {

        ResponseDto errorBody = new ResponseDto("NP", "No Permissions");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorBody); // 너가 누구인지는 알지만 권한이없음
    }

    public static ResponseEntity<ResponseDto> notExistCommentNumber() {

        ResponseDto errorBody = new ResponseDto("NC", "Not-Existent Comment Number");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }
   

}