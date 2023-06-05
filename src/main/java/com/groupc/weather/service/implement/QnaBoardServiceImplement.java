package com.groupc.weather.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.common.util.CustomResponse;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.qnaBoard.PatchQnaBoardRequestDto;
import com.groupc.weather.dto.request.qnaBoard.PostQnaBoardRequestDto;
import com.groupc.weather.dto.request.qnaBoard2.PatchQnaBoardRequestDto2;
import com.groupc.weather.dto.request.qnaBoard2.PostQnaBoardRequestDto2;
import com.groupc.weather.dto.response.qnaBoard.GetQnaBoardListResponseDto;
import com.groupc.weather.dto.response.qnaBoard.GetQnaBoardResponseDto;
import com.groupc.weather.entity.QnaBoardEntity;
import com.groupc.weather.entity.QnaCommentEntity;
import com.groupc.weather.entity.UserEntity;
import com.groupc.weather.entity.resultSet.QnaBoardListResultSet;
import com.groupc.weather.repository.ManagerRepository;
import com.groupc.weather.repository.QnaBoardRepository;
import com.groupc.weather.repository.QnaCommentRepository;
import com.groupc.weather.repository.UserRepository;
import com.groupc.weather.service.QnaBoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaBoardServiceImplement implements QnaBoardService {
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final QnaBoardRepository qnaBoardRepository;
    private final QnaCommentRepository qnaCommentRepository;


    @Override
    public ResponseEntity<ResponseDto> postQnaBoard(PostQnaBoardRequestDto dto) {
        int userNumber = dto.getUserNumber();

        try {
            // 존재하지 않는 유저 번호 반환 
            boolean existedUserNumber = userRepository.existsByUserNumber(userNumber);
            if (!existedUserNumber) {
                ResponseDto errorBody = new ResponseDto("NU", "Non-Existent User Number");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
            }

            QnaBoardEntity qnaBoardEntity = new QnaBoardEntity(dto);
            qnaBoardRepository.save(qnaBoardEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();
    }

    // version2
    @Override
    public ResponseEntity<ResponseDto> postQnaBoard(AuthenticationObject authenticationObject,
        PostQnaBoardRequestDto2 dto
    ) {
        String email = authenticationObject.getEmail();
        boolean isManager = authenticationObject.isManagerFlag();

        try {

            // 작성 권한 없음 (유저나 관리자가 아님)
            boolean isExistUserEmail = userRepository.existsByEmail(email);
            Integer userNumber = userRepository.findByEmail(email).getUserNumber();
            if (!isExistUserEmail && !isManager) return CustomResponse.noPermissions();

            QnaBoardEntity qnaBoardEntity = new QnaBoardEntity(dto, userNumber);
            qnaBoardRepository.save(qnaBoardEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();
    }

    @Override
    public ResponseEntity<? super GetQnaBoardResponseDto> getQnaBoard(Integer boardNumber) {
        GetQnaBoardResponseDto body = null;

        try {
            // 매개변수 검증 작업
            if (boardNumber == null) return CustomResponse.validationError();

            // 존재하지 않는 게시물 번호 반환
            QnaBoardEntity qnaBoardEntity = qnaBoardRepository.findByBoardNumber(boardNumber);
            if (qnaBoardEntity == null) return CustomResponse.notExistBoardNumber();

            int userNumber = qnaBoardEntity.getUserNumber();
            UserEntity userEntity = userRepository.findByUserNumber(userNumber);
            List<QnaCommentEntity> qnaCommentEntities = qnaCommentRepository.findByQnaBoardNumber(boardNumber);

            body = new GetQnaBoardResponseDto(qnaBoardEntity, userEntity, qnaCommentEntities);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        
        return ResponseEntity.status(HttpStatus.OK).body(body);

    }

    // version2
    @Override
    public ResponseEntity<? super GetQnaBoardResponseDto> getQnaBoard(AuthenticationObject authenticationObject,
            Integer boardNumber
    ) {
        String email = authenticationObject.getEmail();
        boolean isManager = authenticationObject.isManagerFlag();

        GetQnaBoardResponseDto body = null;

        try {
            // 매개변수 검증 작업
            if (boardNumber == null) return CustomResponse.validationError();

            // 존재하지 않는 게시물 번호 반환
            QnaBoardEntity qnaBoardEntity = qnaBoardRepository.findByBoardNumber(boardNumber);
            if (qnaBoardEntity == null) return CustomResponse.notExistBoardNumber();
            
            // 권한 없음 (글 작성한 유저나 관리자가 아님)
            Integer userNumber = userRepository.findByEmail(email).getUserNumber();
            Integer writerNumber = qnaBoardEntity.getUserNumber();
            boolean isMyQna = userNumber.equals(writerNumber);
            if (!isMyQna && !isManager) return CustomResponse.noPermissions();

            UserEntity userEntity = userRepository.findByUserNumber(userNumber);
            List<QnaCommentEntity> qnaCommentEntities = qnaCommentRepository.findByQnaBoardNumber(boardNumber);

            body = new GetQnaBoardResponseDto(qnaBoardEntity, userEntity, qnaCommentEntities);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);


    }

    @Override
    public ResponseEntity<? super GetQnaBoardListResponseDto> getQnaBoardList() {
        GetQnaBoardListResponseDto body = null;

        try {
            List<QnaBoardListResultSet> resultSet = qnaBoardRepository.getQnaBoardList();
            body = new GetQnaBoardListResponseDto(resultSet);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);

    }

    @Override
    public ResponseEntity<ResponseDto> patchQnaBoard(PatchQnaBoardRequestDto dto) {

        Integer userNumber = dto.getUserNumber();
        Integer qnaBoardNumber = dto.getQnaBoardNumber();
        String title = dto.getTitle();
        String content = dto.getContent();
        String imageUrl = dto.getImageUrl();

        try {
            // 존재하지 않는 게시물 번호 반환
            QnaBoardEntity qnaBoardEntity =
                qnaBoardRepository.findByBoardNumber(qnaBoardNumber);
            if (qnaBoardEntity == null) return CustomResponse.notExistBoardNumber();

            // 존재하지 않는 유저 번호 반환
            boolean existedUserNumber =
                userRepository.existsByUserNumber(userNumber);
            if (!existedUserNumber) return CustomResponse.notExistUserNumber();
            
            // 권한 없음
            boolean equalWriter = qnaBoardEntity.getUserNumber() == userNumber;
            if (!equalWriter) return CustomResponse.noPermissions();

            qnaBoardEntity.setTitle(title);
            qnaBoardEntity.setContent(content);
            qnaBoardEntity.setImageUrl(imageUrl);

            qnaBoardRepository.save(qnaBoardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();

    }

    // version2
    @Override
    public ResponseEntity<ResponseDto> patchQnaBoard(AuthenticationObject authenticationObject, PatchQnaBoardRequestDto2 dto) {

        String email = authenticationObject.getEmail();

        Integer qnaBoardNumber = dto.getQnaBoardNumber();
        String title = dto.getTitle();
        String content = dto.getContent();
        String imageUrl = dto.getImageUrl();

        try {
            // 존재하지 않는 게시물 번호 반환
            QnaBoardEntity qnaBoardEntity =
                qnaBoardRepository.findByBoardNumber(qnaBoardNumber);
            if (qnaBoardEntity == null) return CustomResponse.notExistBoardNumber();
            
            // 권한 없음 (해당 게시물을 작성한 회원이 아님)
            Integer userNumber = userRepository.findByEmail(email).getUserNumber();
            boolean equalWriter = qnaBoardEntity.getUserNumber() == userNumber;
            if (!equalWriter) return CustomResponse.noPermissions();

            qnaBoardEntity.setTitle(title);
            qnaBoardEntity.setContent(content);
            qnaBoardEntity.setImageUrl(imageUrl);

            qnaBoardRepository.save(qnaBoardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();
    }

    @Override
    public ResponseEntity<ResponseDto> deleteQnaBoard(Integer userNumber, Integer boardNumber) {

        try {
            if (boardNumber == null) return CustomResponse.validationError();

            // 존재하지 않는 게시물 번호 반환 
            QnaBoardEntity qnaBoardEntity = qnaBoardRepository.findByBoardNumber(boardNumber);
            if (qnaBoardEntity == null) return CustomResponse.notExistBoardNumber();

            // 존재하지 않는 특정 번호(유저, 관리자) 반환 
            boolean existedUserNumber =
                userRepository.existsByUserNumber(userNumber) ||
                managerRepository.existsByManagerNumber(userNumber);
            if (!existedUserNumber) return CustomResponse.notExistUserNumber();

            // 권한 없음(작성한 유저나 관리자가 아님)
            boolean equalWriter = qnaBoardEntity.getUserNumber() == userNumber;
            boolean isManager = managerRepository.existsByManagerNumber(userNumber);
            if (!equalWriter && !isManager) return CustomResponse.noPermissions();

            qnaBoardRepository.deleteByBoardNumber(boardNumber);
            qnaCommentRepository.deleteByQnaBoardNumber(boardNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();

    }

    // version2
    @Override
    public ResponseEntity<ResponseDto> deleteQnaBoard(AuthenticationObject authenticationObject, Integer boardNumber) {

        String email = authenticationObject.getEmail();
        boolean isManager = authenticationObject.isManagerFlag();

        try {
            if (boardNumber == null) return CustomResponse.validationError();

            // 존재하지 않는 게시물 번호 반환 
            QnaBoardEntity qnaBoardEntity = qnaBoardRepository.findByBoardNumber(boardNumber);
            if (qnaBoardEntity == null) return CustomResponse.notExistBoardNumber();

            // 권한 없음(작성한 유저나 관리자가 아님)
            Integer userNumber = userRepository.findByEmail(email).getUserNumber();
            Integer writerNumber = qnaBoardEntity.getUserNumber();
            boolean isMyQna = userNumber.equals(writerNumber);
            if (!isMyQna && !isManager) return CustomResponse.noPermissions();

            qnaBoardRepository.deleteByBoardNumber(boardNumber);
            qnaCommentRepository.deleteByQnaBoardNumber(boardNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();
    }


    @Override
    public ResponseEntity<? super GetQnaBoardListResponseDto> getSearchQnaBoardList(String searchWord) {
        GetQnaBoardListResponseDto body = null;

        try {
            if (searchWord.isBlank()) return CustomResponse.validationError();
            
            List<QnaBoardListResultSet> resultSet = qnaBoardRepository.getQnaBoardSearchList(searchWord);
            body = new GetQnaBoardListResponseDto(resultSet);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }



}
