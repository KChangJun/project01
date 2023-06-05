package com.groupc.weather.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.common.util.CustomResponse;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.board.DeleteCommentRequestDto2;
import com.groupc.weather.dto.request.board.PatchCommentRequestDto2;
import com.groupc.weather.dto.request.board.PostCommentRequestDto2;
import com.groupc.weather.entity.CommentEntity;
import com.groupc.weather.entity.ManagerEntity;
import com.groupc.weather.entity.UserEntity;
import com.groupc.weather.repository.BoardRepository;
import com.groupc.weather.repository.CommentRepository;
import com.groupc.weather.repository.ManagerRepository;
import com.groupc.weather.repository.UserRepository;
import com.groupc.weather.service.CommentService;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CommentServiceImplement implements CommentService{

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ManagerRepository managerRepository;

//댓글 작성 로직
    @Override
    public ResponseEntity<ResponseDto> postComment(AuthenticationObject authenticationObject,PostCommentRequestDto2 dto) {
        
        
        // 댓글 작성유저
        
        //매니저 판단 여부

        boolean isManager = authenticationObject.isManagerFlag();
        
        // 게시물의 번호 
        int boardNumber = dto.getBoardNumber();
        

        try {

               // 존재하는 게시물인지!
            boolean existedBoardNumber = boardRepository.existsByBoardNumber(boardNumber);
            if(!existedBoardNumber) return CustomResponse.notExistBoardNumber();

            //isManager가 false = 유저임
            if(!isManager){
                Integer userNumber = userRepository.findByEmail(authenticationObject.getEmail()).getUserNumber();
                
                boolean existedWriterUserNumber = userRepository.existsByUserNumber(userNumber);
                
                // 댓글작성자가 존재하는가
                if(!existedWriterUserNumber) return CustomResponse.notExistUserNumber();
                
                UserEntity userEntity = userRepository.findByUserNumber(userNumber);

                CommentEntity commentEntity = new CommentEntity(dto, userEntity);
                commentRepository.save(commentEntity);
                return CustomResponse.success();
            }
            //isManager가 true = 관리자임

               Integer userNumber = managerRepository.findByEmail(authenticationObject.getEmail()).getManagerNumber();

               boolean existedWriterUserNumber = managerRepository.existsByManagerNumber(userNumber);
                
                if(!existedWriterUserNumber) return CustomResponse.notExistUserNumber();

                ManagerEntity managerEntity = managerRepository.findByManagerNumber(userNumber);

                CommentEntity commentEntity = new CommentEntity(dto, managerEntity);
                commentRepository.save(commentEntity);
                

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return CustomResponse.success();
    }

//댓글 수정 로직
    @Override
    public ResponseEntity<ResponseDto> patchComment(AuthenticationObject authenticationObject,PatchCommentRequestDto2 dto) {
            
           
            boolean isManager = authenticationObject.isManagerFlag();
            Integer commentNumber = dto.getCommentNumber();
            String commentContent = dto.getCommentContent();

            
            try {
                
                boolean existedCommentNumber = commentRepository.existsByCommentNumber(commentNumber);
                CommentEntity commentEntity = commentRepository.findByCommentNumber(commentNumber);
                
                

                //존재하지 않는 댓글
                if(!existedCommentNumber) return CustomResponse.notExistCommentNumber();
                
                //isManager가 true = 관리자임
                if(isManager){


                    Integer userNumber = managerRepository.findByEmail(authenticationObject.getEmail()).getManagerNumber();
                    
                    //존재 하는 매니저 인지 확인
                    boolean existedManagerNumber = managerRepository.existsByManagerNumber(userNumber);
                    if(!existedManagerNumber) return CustomResponse.notExistUserNumber();
                   
                    //작성자와 동일한지 확인
                    boolean managerNumberEqual = commentEntity.getManagerNumber() == userNumber;
                    if(!managerNumberEqual) return CustomResponse.noPermissions();

                    commentEntity.setContent(commentContent);
                    commentRepository.save(commentEntity);

                    return CustomResponse.success();

                }

                //isManager가 false = 유저임

                Integer userNumber = userRepository.findByEmail(authenticationObject.getEmail()).getUserNumber();

                //존재 하는 유저 인지 확인
                boolean existedUserNumber = userRepository.existsByUserNumber(userNumber);
                 if(!existedUserNumber) return CustomResponse.notExistUserNumber();
                 //작성자와 동일한지 확인
                 boolean userNumberEqual = commentEntity.getUserNumber() == userNumber;
                 if(!userNumberEqual) return CustomResponse.noPermissions();

                 commentEntity.setContent(commentContent);
                 commentRepository.save(commentEntity);

            } catch (Exception exception) {
                exception.printStackTrace();
                return CustomResponse.databaseError();
            }
            
            return CustomResponse.success();
    }



    //댓글 삭제 로직

    @Override
    public ResponseEntity<ResponseDto> deleteComment(AuthenticationObject authenticationObject, DeleteCommentRequestDto2 dto) {

        Integer commentNumber = dto.getCommentNumber();
        CommentEntity commentEntity = commentRepository.findByCommentNumber(commentNumber);
        boolean isManager = authenticationObject.isManagerFlag();

        try {
            //존재하지 않는 댓글
            boolean existedCommentNumber = commentRepository.existsByCommentNumber(commentNumber);
            if(!existedCommentNumber) return CustomResponse.notExistCommentNumber();


            //관리자가 아닌 유저일때
            
            if(!isManager){
                Integer userNumber = userRepository.findByEmail(authenticationObject.getEmail()).getUserNumber();
                
                boolean existedWriterUserNumber = userRepository.existsByUserNumber(userNumber);
                
                // 댓글작성자가 존재하는가
                if(!existedWriterUserNumber) return CustomResponse.notExistUserNumber();
                
                UserEntity userEntity = userRepository.findByUserNumber(userNumber);

                //댓글 작성자와 댓글 삭제자가 동일한 유저인지 확인
                boolean userNumberEqual = commentEntity.getUserNumber() == userNumber;
                 if(!userNumberEqual) return CustomResponse.noPermissions();

                //동일하다면

                commentRepository.deleteByCommentNumber(commentNumber);
                return CustomResponse.success();
            }

            //관리자 일때

            Integer userNumber = managerRepository.findByEmail(authenticationObject.getEmail()).getManagerNumber();
                    
            //존재 하는 매니저 인지 확인
            boolean existedManagerNumber = managerRepository.existsByManagerNumber(userNumber);
            if(!existedManagerNumber) return CustomResponse.notExistUserNumber();
            commentRepository.deleteByCommentNumber(commentNumber);


        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
         
        }

        return CustomResponse.success();
    }   


    
}
    
