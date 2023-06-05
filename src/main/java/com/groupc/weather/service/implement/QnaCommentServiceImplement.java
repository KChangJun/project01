package com.groupc.weather.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.common.util.CustomResponse;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.qnaBoard.PatchQnaCommentRequestDto2;
import com.groupc.weather.dto.request.qnaBoard.PostQnaCommentRequestDto2;
import com.groupc.weather.entity.ManagerEntity;
import com.groupc.weather.entity.QnaBoardEntity;
import com.groupc.weather.entity.QnaCommentEntity;

import com.groupc.weather.entity.UserEntity;
import com.groupc.weather.repository.ManagerRepository;
import com.groupc.weather.repository.QnaBoardRepository;
import com.groupc.weather.repository.QnaCommentRepository;
import com.groupc.weather.repository.UserRepository;
import com.groupc.weather.service.QnaCommentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaCommentServiceImplement implements QnaCommentService {
    private final QnaBoardRepository qnaBoardRepository;
    private final UserRepository userRepositry;
    private final QnaCommentRepository qnaCommentRepository;
    private final ManagerRepository managerRepository;



    //qna 게시물 댓글 생성
    @Override
    public ResponseEntity<ResponseDto> postQnaComment(AuthenticationObject authenticationObject,PostQnaCommentRequestDto2 dto) {
 
        // qna 보드 특정
        Integer qnaBoardNumber = dto.getQnaBoardNumber();

        // qna 보드 작성자 특정
        QnaBoardEntity qnaBoardEntity = qnaBoardRepository.findByBoardNumber(qnaBoardNumber);
        Integer qnaBoardWriterNumber = qnaBoardEntity.getUserNumber();

        try {          
            
            boolean existedQnaBoardNumber = qnaBoardRepository.existsByBoardNumber(qnaBoardNumber);

            // TODO: QnaBoard 존재 유무
            if (!existedQnaBoardNumber) {
                return CustomResponse.notExistBoardNumber();
            }
    
        
            
            boolean isManager = authenticationObject.isManagerFlag();


            // TODO: 일반 유저일 경우
            if (!isManager) {
            
                boolean existedWriterUserNumber = userRepositry.existsByEmail(authenticationObject.getEmail());
                if (!existedWriterUserNumber) return CustomResponse.notExistUserNumber();
                
                // qna 댓글 작성자하고 싶은 사람 특정
                int qnaCommentWriterNumber = userRepositry.findByEmail(authenticationObject.getEmail()).getUserNumber();
                
                // qna 보드 작성자와 댓글 달고 싶은 유저가 동일하지않음
                if(!(qnaBoardWriterNumber==qnaCommentWriterNumber)) return CustomResponse.noPermissions();
                
                // qna 보드 작성자와 댓글 달고 싶은 유저가 동일할때 댓글이 등록됨
                UserEntity userEntity = userRepositry.findByUserNumber(qnaCommentWriterNumber);

                QnaCommentEntity qnaCommentEntity = new QnaCommentEntity(dto, userEntity);
              
                qnaCommentRepository.save(qnaCommentEntity);

                return CustomResponse.success();
                
            }

            // TODO: 관리자일 경우
            boolean existedManagerNumber = managerRepository.existsByEmail(authenticationObject.getEmail());
            if (!existedManagerNumber) return CustomResponse.notExistUserNumber();

            ManagerEntity managerEntity = managerRepository.findByEmail(authenticationObject.getEmail());

            QnaCommentEntity qnaCommentEntity = new QnaCommentEntity(dto, managerEntity);
            qnaBoardEntity.setReplyComplete(true);
            qnaBoardRepository.save(qnaBoardEntity);
            qnaCommentRepository.save(qnaCommentEntity);
        
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();
    }



    //qna 게시물 댓글 수정
    @Override
    public ResponseEntity<ResponseDto> patchQnaComment(AuthenticationObject authenticationObject, PatchQnaCommentRequestDto2 dto) {
        
        boolean isManager = authenticationObject.isManagerFlag();
        Integer commentNumber = dto.getQnaCommentNumber();
        String commentContent = dto.getQnaCommentContent();

    
        // 유저일때랑 관리자일때랑 나눠서 진행
        
        try {            
            //수정하려는 댓글이 존재하는지 확인
            boolean existedQnaComment = qnaCommentRepository.existsByQnaCommentNumber(commentNumber);
            if(!existedQnaComment) return CustomResponse.notExistQnaCommentNumber();
            QnaCommentEntity qnaCommentEntity = qnaCommentRepository.findByQnaCommentNumber(commentNumber);

            //유저일 경우
            if (!isManager) {
     
                //댓글 수정자가 존재하는 유저인지 확인
                boolean existedWriterUser = userRepositry.existsByEmail(authenticationObject.getEmail());
                if (!existedWriterUser) return CustomResponse.notExistUserNumber();
                Integer qnaCommentPatcherNumber = userRepositry.findByEmail(authenticationObject.getEmail()).getUserNumber();                


                //해당 댓글 작성자와 수정하려는 사람이 동일 인물인지 확인
                Integer qnaCommentWriter = qnaCommentEntity.getUserNumber();
                //동일하지 않다면 접근 권한 없음
                if(!(qnaCommentPatcherNumber==qnaCommentWriter)) return CustomResponse.noPermissions();

                //동일하다면 코멘트 내용이 수정됨
                qnaCommentEntity.setContent(commentContent);
                qnaCommentRepository.save(qnaCommentEntity);
                return CustomResponse.success();
                
            }

            //관리자일 경우


             //댓글 수정자가 존재하는 관리자인지 확인
             boolean existedWriterUser = managerRepository.existsByEmail(authenticationObject.getEmail());
             if (!existedWriterUser) return CustomResponse.notExistUserNumber();
             Integer qnaCommentPatcherNumber = managerRepository.findByEmail(authenticationObject.getEmail()).getManagerNumber();                


             //해당 댓글 작성자와 수정하려는 사람이 동일 인물인지 확인
             Integer qnaCommentWriter = qnaCommentEntity.getManagerNumber();
             //동일하지 않다면 접근 권한 없음
             if(!(qnaCommentPatcherNumber==qnaCommentWriter)) return CustomResponse.noPermissions();

             //동일하다면 코멘트 내용이 수정됨
             qnaCommentEntity.setContent(commentContent);
             qnaCommentRepository.save(qnaCommentEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();
    }





    // 댓글 삭제
    @Override
    public ResponseEntity<ResponseDto> deleteQnaComment(AuthenticationObject authenticationObject, Integer qnaCommentNumber) {
       
        boolean isManager = authenticationObject.isManagerFlag();
        
        try {

            //TODO : 존재하는 코멘트 넘버인지
            boolean existedQnaCommentNumber = qnaCommentRepository.existsByQnaCommentNumber(qnaCommentNumber);
            if (!existedQnaCommentNumber) return CustomResponse.notExistQnaCommentNumber();

            QnaCommentEntity qnaCommentEntity = qnaCommentRepository.findByQnaCommentNumber(qnaCommentNumber);

            //TODO : 유저랑 관리자로 나누어서 작업

            // 유저일 경우
            if(!isManager){
                //댓글 삭제자가 존재하는 유저인지 확인
                boolean existedWriterUser = userRepositry.existsByEmail(authenticationObject.getEmail());
                if (!existedWriterUser) return CustomResponse.notExistUserNumber();
                Integer qnaCommentPatcherNumber = userRepositry.findByEmail(authenticationObject.getEmail()).getUserNumber();                


                //해당 댓글 작성자와 삭제하려는 사람이 동일 인물인지 확인
                Integer qnaCommentWriter = qnaCommentEntity.getUserNumber();
                //동일하지 않다면 접근 권한 없음
                if(!(qnaCommentPatcherNumber==qnaCommentWriter)) return CustomResponse.noPermissions();

                //동일하다면 코멘트 내용이 수정됨
                qnaCommentRepository.deleteByQnaCommentNumber(qnaCommentNumber);
                return CustomResponse.success();

            }

            // 관리자일경우

            //댓글 삭제자가 존재하는 관리자인지 확인
            boolean existedWriterUser = managerRepository.existsByEmail(authenticationObject.getEmail());
            if (!existedWriterUser) return CustomResponse.notExistUserNumber();                


            //관리자라면 바로 삭제 가능함
            qnaCommentRepository.deleteByQnaCommentNumber(qnaCommentNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();

        }
        return CustomResponse.success();
    }

}
