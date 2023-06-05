package com.groupc.weather.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.groupc.weather.dto.response.board.BoardFirstViewDto;
import com.groupc.weather.dto.response.board.BoardListResultDto;
import com.groupc.weather.dto.response.board.BoardListResultTop5Dto;
import com.groupc.weather.dto.response.board.GetBoardFirstViewDto;
import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.common.util.CustomResponse;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.board.HashtagDto;
import com.groupc.weather.dto.request.board.LikeRequestDto;
import com.groupc.weather.dto.request.board.PatchBoardRequestDto;
import com.groupc.weather.dto.request.board.PostBoardRequestDto2;
import com.groupc.weather.dto.request.common.WeatherDto;
import com.groupc.weather.dto.response.board.GetBoardListResponseDto;
import com.groupc.weather.dto.response.board.GetBoardListResponsetop5Dto;
import com.groupc.weather.dto.response.board.GetBoardResponseDto;
import com.groupc.weather.dto.response.board.LikeyListDto;
import com.groupc.weather.entity.BoardEntity;
import com.groupc.weather.entity.CommentEntity;
import com.groupc.weather.entity.HashTagEntity;
import com.groupc.weather.entity.HashtagHasBoardEntity;
import com.groupc.weather.entity.ImageUrlEntity;
import com.groupc.weather.entity.LikeyEntity;
import com.groupc.weather.entity.UserEntity;
import com.groupc.weather.entity.primaryKey.HashPk;
import com.groupc.weather.entity.primaryKey.LikeyPk;
import com.groupc.weather.entity.resultSet.GetBoardListResult;
import com.groupc.weather.repository.BoardRepository;
import com.groupc.weather.repository.CommentRepository;
import com.groupc.weather.repository.HashtagHasBoardRepository;
import com.groupc.weather.repository.HashtagRepository;
import com.groupc.weather.repository.ImageUrlRepository;
import com.groupc.weather.repository.LikeyRepository;
import com.groupc.weather.repository.UserRepository;
import com.groupc.weather.service.BoardService;
import com.groupc.weather.service.WeatherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {

   private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    private final LikeyRepository likeyRepository;
    private final ImageUrlRepository imageUrlRepository;
    private final HashtagRepository hashtagRepository;
    private final HashtagHasBoardRepository hashtagHasBoardRepository;
    private final WeatherService weatherService;
    

    // 1.게시물 등록
    @Override
    public ResponseEntity<ResponseDto> postBoard(AuthenticationObject authenticationObject, PostBoardRequestDto2 dto) {
        String userEmail = authenticationObject.getEmail();
        UserEntity userEntity = userRepository.findByEmail(userEmail);
        Integer userNumber = userEntity.getUserNumber();
        try {
            // 존재하지 않는 유저 번호 지워도됨
            boolean isexistUsernumber = userRepository.existsByUserNumber(userNumber);
            if (!isexistUsernumber) {
                ResponseDto errorBody = new ResponseDto("NU", "Non-Existent User Number");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
            }

            WeatherDto weatherDto = weatherService.getWeatherData(dto.getLocation());

            BoardEntity boardEntity = new BoardEntity(dto, weatherDto, userNumber);
            boardRepository.save(boardEntity);
            int boardNumber = boardEntity.getBoardNumber();
            List<ImageUrlEntity> imageUrlLists = new ArrayList<>();

            for (String imageListResult : dto.getImageUrlList()) {
                ImageUrlEntity imageUrlEntity = new ImageUrlEntity(imageListResult, boardEntity.getBoardNumber());
                imageUrlLists.add(imageUrlEntity);
            }

            imageUrlRepository.saveAll(imageUrlLists);

            List<HashTagEntity> hashtagEntityList = new ArrayList<>();
            for (String hashtag : dto.getHashtagList()) {
                HashTagEntity hashtagEntity = new HashTagEntity(hashtag);
                hashtagEntityList.add(hashtagEntity);
            }
            hashtagRepository.saveAll(hashtagEntityList);

            List<HashtagHasBoardEntity> hashtagHasBoardEntityList = new ArrayList<>();
            for (HashTagEntity hashtagEntity : hashtagEntityList) {
                int hashtagNumber = hashtagEntity.getHashtagNumber();
                HashtagHasBoardEntity hashtagHasBoardEntity = new HashtagHasBoardEntity(hashtagNumber, boardNumber);
                hashtagHasBoardEntityList.add(hashtagHasBoardEntity);
            }
            hashtagHasBoardRepository.saveAll(hashtagHasBoardEntityList);

            // List<HashtagHasBoardEntity> hashtagHasBoardEntityList = new ArrayList<>();

            // for (String hashtag: dto.getHashtagList()){
            // HashtagEntity hashtagEntity = new HashtagEntity(hashtag);
            // hashtagRepository.save(hashtagEntity);

            // int hashtagNumber = hashtagEntity.getHashtagNumber();
            // HashtagHasBoardEntity hashtagHasBoardEntity = new
            // HashtagHasBoardEntity(hashtagNumber, boardNumber);

            // hashtagHasBoardEntityList.add(hashtagHasBoardEntity);
            // }

            // hashtagHasBoardRepository.saveAll(hashtagHasBoardEntityList);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return CustomResponse.success();
    }
    //POST 버전 2

    // 2.특정 게시물 조회 (게시물 번호) 
    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {
        GetBoardResponseDto body = null;

        try {
            // 매게변수 오류
            if (boardNumber == null)
                return CustomResponse.validationError();
            boolean existsByBoardNumber = boardRepository.existsByBoardNumber(boardNumber);
            // 존재하지 않는 게시물 번호
            if (!existsByBoardNumber)
                return CustomResponse.notExistBoardNumber();

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            int viewCount = boardEntity.getViewCount();
            boardEntity.setViewCount(++viewCount);
            Integer boardWriterNumber = boardEntity.getUserNumber();
            UserEntity userEntity = userRepository.findByUserNumber(boardWriterNumber);
            List<LikeyEntity> likeyEntities = likeyRepository.findByBoardNumberForLikeyList(boardNumber);
            List<LikeyListDto> likeyListDtos = new ArrayList<>();
            for (LikeyEntity likeyEntity : likeyEntities) {

                UserEntity likeUserEntity = userRepository.findByUserNumber(likeyEntity.getUserNumber());
                LikeyListDto likeyListDto = new LikeyListDto(likeyEntity, likeUserEntity);
                likeyListDtos.add(likeyListDto);
            }

            List<CommentEntity> commentEntities = commentRepository.findByBoardNumber(boardNumber);
            List<HashtagHasBoardEntity> hashtagHasBoardEntities = hashtagHasBoardRepository
                    .findByBoardNumber(boardNumber);

            List<HashTagEntity> hashListEntities = new ArrayList<>();
            for (HashtagHasBoardEntity hashtagHasBoardEntity : hashtagHasBoardEntities) {
                int hashtagNumber = hashtagHasBoardEntity.getHashtagNumber();
                HashTagEntity hashtagEntity = hashtagRepository.findByHashtagNumber(hashtagNumber);
                hashListEntities.add(hashtagEntity);
            }

            List<ImageUrlEntity> imageUrlEntities = imageUrlRepository.findByBoardNumber(boardNumber);
            body = new GetBoardResponseDto(boardEntity, userEntity, likeyListDtos, commentEntities, hashListEntities,
                    imageUrlEntities);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 3.본인 게시물 목록 조회
    @Override
    public ResponseEntity<? super GetBoardListResponseDto> getBoardMyList(AuthenticationObject authenticationObject) {
        String email = authenticationObject.getEmail();
        GetBoardListResponseDto body = null;
        UserEntity userEntity = userRepository.findByEmail(email);
        Integer userNumber = userEntity.getUserNumber();
        try {
            List<GetBoardListResult> resultSet = boardRepository.getMyBoardList(userNumber);
            List<BoardListResultDto> boardListResultDtos = new ArrayList<>();
            for (GetBoardListResult result : resultSet) {
                int boardNumber = result.getBoardNumber();
                List<HashtagHasBoardEntity> hashtagHasBoardEntities = hashtagHasBoardRepository
                        .findByBoardNumber(boardNumber);

                List<HashTagEntity> hashListEntities = new ArrayList<>();
                for (HashtagHasBoardEntity hashtagHasBoardEntity : hashtagHasBoardEntities) {
                    int hashtagNumber = hashtagHasBoardEntity.getHashtagNumber();
                    HashTagEntity hashtagEntity = hashtagRepository.findByHashtagNumber(hashtagNumber);
                    hashListEntities.add(hashtagEntity);
                }
                BoardListResultDto boardListResultDto = new BoardListResultDto(result, hashListEntities);
                boardListResultDtos.add(boardListResultDto);
            }

            body = new GetBoardListResponseDto(boardListResultDtos);
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 4.top5 조회 
    @Override
    public ResponseEntity<? super GetBoardListResponsetop5Dto> getBoardTop5() {
        GetBoardListResponsetop5Dto body = null;

        try {

            List<GetBoardListResult> resultSet = boardRepository.getBoardListTop5();
            List<BoardListResultTop5Dto> boardListResultTop5Dtos = new ArrayList<>();
            for (GetBoardListResult result : resultSet) {
                BoardListResultTop5Dto boardListResultTop5Dto = new BoardListResultTop5Dto(result);
                boardListResultTop5Dtos.add(boardListResultTop5Dto);
            }

            body = new GetBoardListResponsetop5Dto(boardListResultTop5Dtos);
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
    // return에 coustom.success , ResposeEntity 쓸수도잇음.
    // ResponseEntity는 , OK 코드랑 메세지에다가 + 원하는거 보여줌. // 이거는 따로 만들면 Custom으로 쓸수잇는데
    // 따로 안만들어서 이렇게쓰는거임....



    // 5.게시물 최신순 조회 
    @Override
    public ResponseEntity<? super GetBoardListResponseDto> getBoardList() {
        GetBoardListResponseDto body = null;
        try {
            List<GetBoardListResult> resultSet = boardRepository.getBoardList();
            // System.out.println(resultSet.size()); //게시물 목록 몇개 나오는지 보는건데 쓸까 말까~?

            List<BoardListResultDto> boardListResultDtos = new ArrayList<>();
            for (GetBoardListResult result : resultSet) {
                int boardNumber = result.getBoardNumber();
                List<HashtagHasBoardEntity> hashtagHasBoardEntities = hashtagHasBoardRepository
                        .findByBoardNumber(boardNumber);

                List<HashTagEntity> hashListEntities = new ArrayList<>();
                for (HashtagHasBoardEntity hashtagHasBoardEntity : hashtagHasBoardEntities) {
                    int hashtagNumber = hashtagHasBoardEntity.getHashtagNumber();
                    HashTagEntity hashtagEntity = hashtagRepository.findByHashtagNumber(hashtagNumber);
                    hashListEntities.add(hashtagEntity);
                }

                BoardListResultDto boardListResultDto = new BoardListResultDto(result, hashListEntities);
                boardListResultDtos.add(boardListResultDto);
            }

            body = new GetBoardListResponseDto(boardListResultDtos);
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 6.첫화면 게시물 8개 보기
    @Override
    public ResponseEntity<? super GetBoardFirstViewDto> getBoardFirstView() {
        GetBoardFirstViewDto body = null;
        try {

            List<GetBoardListResult> resultSet = boardRepository.getBoardFirstView();
            List<BoardFirstViewDto> boardFirstViewDtos = new ArrayList<>();
            for (GetBoardListResult result : resultSet) {
                BoardFirstViewDto BoardFirstViewDto = new BoardFirstViewDto(result);
                boardFirstViewDtos.add(BoardFirstViewDto);
            }

            body = new GetBoardFirstViewDto(boardFirstViewDtos);
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 7.게시물 수정
    @Override
    public ResponseEntity<ResponseDto> patchBoard(AuthenticationObject authenticationObject, PatchBoardRequestDto dto) {
        String userEmail = authenticationObject.getEmail();
        Integer boardNumbers = dto.getBoardNumber();
        String boardTitle = dto.getBoardTitle();
        String boardContent = dto.getBoardContent();
        List<ImageUrlEntity> modifyImageUrlLists = dto.getImageUrlList();
        List<HashtagDto> modifyHashTags = dto.getHashtagList();
        // List<String> modifyHashTags = dto.getBoardHashtag();
        List<String> addImageUrlList = dto.getAddImageUrlList(); // 추가할 이미지
        List<String> addHashtageContent = dto.getAddHashtageContent(); // 추가할 해시태그
        List<Integer> deleteImaglList = dto.getDeleteImageNumber(); // 삭제할 이미지
        List<Integer> deleteHashtageList = dto.getDeleteHashtageNumber(); // 삭제할 해시태그

        List<ImageUrlEntity> imageUrlEntities = new ArrayList<>();
        List<HashTagEntity> hashtagEntities = new ArrayList<>();
        // 로그인하면 토큰을 반환시켜주고 , 해당토큰을 헤더에 넣고 이걸 실행하면
        // 이메일이 받아와짐 왜냐면 컨트롤러에서 이메일을 받아오게 했기 때문에

        try {
            UserEntity userEntity = userRepository.findByEmail(userEmail); // 작성자유저넘버 불러오기
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumbers); // 게시물번호 불러오기
            Integer userNumbers = userEntity.getUserNumber();
            // 매게변수
            if (userNumbers == null || boardNumbers == null) {
                return CustomResponse.validationError();
            }

            // 게시물 번호 존재여부
            if (boardEntity == null)
                return CustomResponse.notExistBoardNumber();

        
            if (modifyImageUrlLists != null) {
                for (ImageUrlEntity imageList : modifyImageUrlLists) {
                    
                    ImageUrlEntity imageUrlEntity = 
                    imageUrlRepository.findByImageNumber(imageList.getImageNumber());
                    imageUrlEntity.setImageUrl(imageList.getImageUrl());
                    // 현재 들어가있는거는 imageNumber , url /  boardNumber X = > Null로잡힘
                    // 그래서 boardNumber까지 설정 해줘야 함. 
                    imageUrlRepository.save(imageUrlEntity);
                }
            }
            if (modifyHashTags != null) {
                for (HashtagDto hashTagDto : modifyHashTags) {
                    HashTagEntity hashtagEntity = new HashTagEntity(hashTagDto.getHashtagContent());
                    hashtagEntities.add(hashtagEntity);
                }
            }
            // 추가
            if (addImageUrlList != null) {
                for (String imageUrl : addImageUrlList) {
                    ImageUrlEntity imageUrlEntity = new ImageUrlEntity(imageUrl, boardNumbers);
                    imageUrlEntities.add(imageUrlEntity);
                }
            }
            if (addHashtageContent != null) {
                for (String hashtageContent : addHashtageContent) {
                    HashTagEntity hashtagEntity = new HashTagEntity(hashtageContent);
                    hashtagEntities.add(hashtagEntity);
                }
            }

            // 삭제
            List<ImageUrlEntity> forDelete = new ArrayList<>();
            if (deleteImaglList != null) {
                for (Integer imageNumber : deleteImaglList) {
                    ImageUrlEntity deleteNumberOfBoards = imageUrlRepository
                            .findByBoardNumberAndImageNumber(boardNumbers, imageNumber);
                    forDelete.add(deleteNumberOfBoards);
                }
            }
            
            for (Integer hashtagNumber : deleteHashtageList) {
                
                // HashtagHasBoardEntity forDeleteHashTag =
                // hashtagHasBoardRepository.findByHashtagNumber();

                // HashPk hashPk = new HashPk(hashtagNumber, boardNumbers);
                // hashtagHasBoardRepository.deleteById(hashPk);
                // 아래꺼랑 위에꺼랑 둘 중 하나 선택?

                hashtagHasBoardRepository.deleteByHashtagNumber(hashtagNumber);
                hashtagRepository.deleteByHashtagNumber(hashtagNumber);
            }
            if (forDelete != null) {
                imageUrlRepository.deleteAll(forDelete);
            }
            hashtagRepository.saveAll(hashtagEntities);
         
            boardEntity.setTitle(boardTitle);
            boardEntity.setContent(boardContent);
            boardRepository.save(boardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return CustomResponse.success();
    }

    // 8.게시물 삭제
    @Override
    public ResponseEntity<ResponseDto> deleteBoard(Integer userNumber, Integer boardNumber) {
        try {
            // TODO 존재하지 않는 게시물 번호 반환
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null)
                return CustomResponse.notExistBoardNumber();

            // TODO 존재 하지 않는 유저 번호 반환
            boolean existedUserNumber = userRepository.existsByUserNumber(userNumber);
            if (!existedUserNumber)
                return CustomResponse.notExistUserNumber();

            // TODO 권한 x
            boolean equalsWriter = boardEntity.getUserNumber().equals(userNumber);
            if (!equalsWriter)
                return CustomResponse.noPermissions();
            List<CommentEntity> commentEntitys = commentRepository.findByBoardNumber(boardNumber);
            List<HashtagHasBoardEntity> hashtagHasBoardEntities = hashtagHasBoardRepository
                    .findByBoardNumber(boardNumber);
            List<ImageUrlEntity> imageUrlEntities = imageUrlRepository.findByBoardNumber(boardNumber);
            List<LikeyEntity> likeyEntities = likeyRepository.findByBoardNumber(boardNumber);

            for (CommentEntity boardcommentNumber : commentEntitys) {
                Integer commentNumber = boardcommentNumber.getCommentNumber();
                commentRepository.deleteByCommentNumber(commentNumber);
            }

            for (HashtagHasBoardEntity hashtagHasBoard : hashtagHasBoardEntities) {

                Integer hashtagNumber = hashtagHasBoard.getHashtagNumber();
                HashPk hashPk = new HashPk(hashtagNumber, boardNumber);
                hashtagHasBoardRepository.deleteById(hashPk);
                hashtagRepository.deleteByHashtagNumber(hashtagNumber);
            }
            for (ImageUrlEntity imageUrlNumberByBoardNumber : imageUrlEntities) {

                Integer imageNumber = imageUrlNumberByBoardNumber.getImageNumber();
                imageUrlRepository.deleteById(imageNumber);

            }
            for (LikeyEntity likeyNumberByBoardNumber : likeyEntities) {
                Integer likeyUserNumber = likeyNumberByBoardNumber.getUserNumber();
                LikeyPk likeyPk = new LikeyPk(likeyUserNumber, boardNumber);
                likeyRepository.deleteById(likeyPk);
            }

            boardRepository.deleteById(boardNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return CustomResponse.success();
    }

    // 9.특정 게시물 좋아요 등록
    @Override
    public ResponseEntity<ResponseDto> likeBoard(AuthenticationObject authenticationObject, LikeRequestDto dto) {
        String email = authenticationObject.getEmail();
        UserEntity userEntity = userRepository.findByEmail(email);
        Integer userNumber = userEntity.getUserNumber();
        LikeyEntity findRepositoryInLikeyEntity = 
        likeyRepository.findByBoardNumberAndUserNumber(dto.getBoardNumber(), userNumber);
        
        try {
            
            // 존재하지 않는 게시물 번호
            boolean isexistBoardNumber = boardRepository.existsByBoardNumber(dto.getBoardNumber());
            if (!isexistBoardNumber)
            return CustomResponse.notExistBoardNumber();
            
            // 이미 좋아요가 등록된 경우
            if (findRepositoryInLikeyEntity != null)
            return CustomResponse.alreadyLikeBoard();
            
            // 이미 좋아요가 등록된 경우
            // LikeyEntity existingLikey = likeyRepository.findByBoardNumberAndUserNumber(likeyPk.getUserNumber(),
            //         likeyPk.getBoardNumber());
            // if (existingLikey != null)
            //     return CustomResponse.alreadyLikey();
            
            // 좋아요 생성 및 저장
            LikeyEntity likeyEntity = new LikeyEntity(dto.getBoardNumber(), userNumber);
           
            likeyRepository.save(likeyEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return CustomResponse.success();
    }

    // 10.특정 게시물 좋아요 해제
    @Override
    public ResponseEntity<ResponseDto> likeDeleteBoard(AuthenticationObject authenticationObject, LikeRequestDto dto) {
        Integer boardNumber = dto.getBoardNumber();
        String email = authenticationObject.getEmail();
        UserEntity userEntity = userRepository.findByEmail(email);
        Integer userNumber = userEntity.getUserNumber();
        LikeyEntity likeyEntity = 
        likeyRepository.findByBoardNumberAndUserNumber(boardNumber, userNumber);
        try {
            boolean isExistUsernumber = userRepository.existsByUserNumber(userNumber);
            if (!isExistUsernumber)
                return CustomResponse.notExistUserNumber();
            boolean isEixstBoardNumber = boardRepository.existsByBoardNumber(boardNumber);
            if (!isEixstBoardNumber)
                return CustomResponse.notExistBoardNumber();
           
            if (likeyEntity == null)
                return CustomResponse.notLikeBoard();
            LikeyPk likeyPk = new LikeyPk(boardNumber, userNumber);
            likeyRepository.deleteById(likeyPk);
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return CustomResponse.success();
    }

    // 11.특정 유저 좋아요 게시물 조회
    @Override
    public ResponseEntity<? super GetBoardListResponseDto> getLikeBoardList(
        AuthenticationObject authenticationObject, Integer userNumber) {
            String email = authenticationObject.getEmail();
            // email 은 로그인한사람 , userNumber 조회하려는 특정유저의 유저넘버
        GetBoardListResponseDto body = null;
        UserEntity confirmUserlogin = userRepository.findByEmail(email);
         
        try {
            //로그인한 상태인지 , 회원이 맞는지 확인
            if(confirmUserlogin == null){
                return CustomResponse.notExistUserNumber();
                // 로그인여부 확인 반환시 존재하지않는 유저번호
            }
            // 조회하려는 유저번호가 있는지
            boolean existsByUserNumber = userRepository.existsByUserNumber(userNumber);
            if(!existsByUserNumber) return CustomResponse.notExistUserNumber();
                                        // 조회하려는 유저번호확인
            List<GetBoardListResult> resultSet = boardRepository.getLikeBoardList(userNumber);
            List<BoardListResultDto> boardListResultDtos = new ArrayList<>();
            for (GetBoardListResult result : resultSet) {
                int boardNumber = result.getBoardNumber();

                List<HashtagHasBoardEntity> hashtagHasBoardEntities = hashtagHasBoardRepository
                        .findByBoardNumber(boardNumber);
                List<HashTagEntity> hashListEntities = new ArrayList<>();
                for (HashtagHasBoardEntity hashtagHasBoardEntity : hashtagHasBoardEntities) {
                    int hashtagNumber = hashtagHasBoardEntity.getHashtagNumber();
                    HashTagEntity hashtagEntity = hashtagRepository.findByHashtagNumber(hashtagNumber);
                    hashListEntities.add(hashtagEntity);
                }
                BoardListResultDto boardListResultDto = new BoardListResultDto(result, hashListEntities);
                boardListResultDtos.add(boardListResultDto);
            }

            body = new GetBoardListResponseDto(boardListResultDtos);
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 12.특정 게시물 검색
    @Override
    public ResponseEntity<? super GetBoardListResponseDto> getSearchListByWord(String searchWord) {
        GetBoardListResponseDto body = null;
        try {

            List<GetBoardListResult> resultSet = boardRepository.getSearchListByWord(searchWord);
            List<BoardListResultDto> boardListResultDtos = new ArrayList<>();
            for(GetBoardListResult result:resultSet){
                int boardNumber = result.getBoardNumber();

                List<HashtagHasBoardEntity> hashtagHasBoardEntities = hashtagHasBoardRepository.findByBoardNumber(boardNumber);
                List<HashTagEntity> hashListEntities = new ArrayList<>();
                for(HashtagHasBoardEntity hashtagHasBoardEntity : hashtagHasBoardEntities){
                    int hashtagNumber = hashtagHasBoardEntity.getHashtagNumber();
                    HashTagEntity hashtagEntity = hashtagRepository.findByHashtagNumber(hashtagNumber);
                    hashListEntities.add(hashtagEntity);
                    }
                BoardListResultDto boardListResultDto = new BoardListResultDto(result, hashListEntities);
                boardListResultDtos.add(boardListResultDto);
            }

            body = new GetBoardListResponseDto(boardListResultDtos);
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 특정 검색어 게시물 리스트 검색 (검색어 + 날씨)
    public ResponseEntity<? super GetBoardListResponseDto> getSearchListByWord(String searchWord, String weather) {
        GetBoardListResponseDto body = null;

        try {

            List<GetBoardListResult> resultSet = boardRepository.getSearchListByWordAndWeather(searchWord, weather);
            List<BoardListResultDto> boardListResultDtos = new ArrayList<>();
            for(GetBoardListResult result:resultSet){
                int boardNumber = result.getBoardNumber();

                List<HashtagHasBoardEntity> hashtagHasBoardEntities = hashtagHasBoardRepository.findByBoardNumber(boardNumber);
                List<HashTagEntity> hashListEntities = new ArrayList<>();
                for(HashtagHasBoardEntity hashtagHasBoardEntity : hashtagHasBoardEntities){
                    int hashtagNumber = hashtagHasBoardEntity.getHashtagNumber();
                    HashTagEntity hashtagEntity = hashtagRepository.findByHashtagNumber(hashtagNumber);
                    hashListEntities.add(hashtagEntity);
                    }
                BoardListResultDto boardListResultDto = new BoardListResultDto(result, hashListEntities);
                boardListResultDtos.add(boardListResultDto);
            }

            body = new GetBoardListResponseDto(boardListResultDtos);
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 특정 검색어 게시물 리스트 검색 (검색어 + 기온)
    public ResponseEntity<? super GetBoardListResponseDto> getSearchListByWord(String searchWord, Integer minTemperature, Integer maxTemperature) {
        GetBoardListResponseDto body = null;

        try {

            List<GetBoardListResult> resultSet = boardRepository.getSearchListByWordAndTemperatures(searchWord, minTemperature, maxTemperature);
            List<BoardListResultDto> boardListResultDtos = new ArrayList<>();
            for(GetBoardListResult result:resultSet){
                int boardNumber = result.getBoardNumber();

                List<HashtagHasBoardEntity> hashtagHasBoardEntities = hashtagHasBoardRepository.findByBoardNumber(boardNumber);
                List<HashTagEntity> hashListEntities = new ArrayList<>();
                for(HashtagHasBoardEntity hashtagHasBoardEntity : hashtagHasBoardEntities){
                    int hashtagNumber = hashtagHasBoardEntity.getHashtagNumber();
                    HashTagEntity hashtagEntity = hashtagRepository.findByHashtagNumber(hashtagNumber);
                    hashListEntities.add(hashtagEntity);
                    }
                BoardListResultDto boardListResultDto = new BoardListResultDto(result, hashListEntities);
                boardListResultDtos.add(boardListResultDto);
            }

            body = new GetBoardListResponseDto(boardListResultDtos);
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 특정 검색어 게시물 리스트 검색 (검색어 + 날씨 + 기온)
    public ResponseEntity<? super GetBoardListResponseDto> getSearchListByWord(String searchWord, String weather,
            Integer minTemperature, Integer maxTemperature) {
        GetBoardListResponseDto body = null;

        try {

            List<GetBoardListResult> resultSet = boardRepository.getSearchListByWordAndAll(searchWord, weather, minTemperature, maxTemperature);
            List<BoardListResultDto> boardListResultDtos = new ArrayList<>();
            for(GetBoardListResult result:resultSet){
                int boardNumber = result.getBoardNumber();

                List<HashtagHasBoardEntity> hashtagHasBoardEntities = hashtagHasBoardRepository.findByBoardNumber(boardNumber);
                List<HashTagEntity> hashListEntities = new ArrayList<>();
                for(HashtagHasBoardEntity hashtagHasBoardEntity : hashtagHasBoardEntities){
                    int hashtagNumber = hashtagHasBoardEntity.getHashtagNumber();
                    HashTagEntity hashtagEntity = hashtagRepository.findByHashtagNumber(hashtagNumber);
                    hashListEntities.add(hashtagEntity);
                    }
                BoardListResultDto boardListResultDto = new BoardListResultDto(result, hashListEntities);
                boardListResultDtos.add(boardListResultDto);
            }

            body = new GetBoardListResponseDto(boardListResultDtos);
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }


    // 13.특정 게시물 검색(해쉬태그)
    @Override
    public ResponseEntity<? super GetBoardListResponseDto> getSearchListByHashtag(String hashtag) {
        GetBoardListResponseDto body = null;
        try {

            List<GetBoardListResult> resultSet = boardRepository.getSearchHashtagByWord(hashtag);
            List<BoardListResultDto> boardListResultDtos = new ArrayList<>();
            for (GetBoardListResult result : resultSet) {
                int boardNumber = result.getBoardNumber();
                List<HashtagHasBoardEntity> hashtagHasBoardEntities = hashtagHasBoardRepository
                        .findByBoardNumber(boardNumber);

                List<HashTagEntity> hashListEntities = new ArrayList<>();
                for (HashtagHasBoardEntity hashtagHasBoardEntity : hashtagHasBoardEntities) {
                    int hashtagNumber = hashtagHasBoardEntity.getHashtagNumber();
                    HashTagEntity hashtagEntity = hashtagRepository.findByHashtagNumber(hashtagNumber);
                    hashListEntities.add(hashtagEntity);
                }
                BoardListResultDto boardListResultDto = new BoardListResultDto(result, hashListEntities);
                boardListResultDtos.add(boardListResultDto);
            }

            body = new GetBoardListResponseDto(boardListResultDtos);
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 특정 해시태그로 게시물 리스트 검색(해시태그 + 날씨)
    public ResponseEntity<? super GetBoardListResponseDto> getSearchListByHashtag(String hashtag, String weather) {
        GetBoardListResponseDto body = null;
        try {

            List<GetBoardListResult> resultSet = boardRepository.getSearchHashtagByWordAndWeather(hashtag, weather);
            List<BoardListResultDto> boardListResultDtos = new ArrayList<>();
            for(GetBoardListResult result:resultSet){
                int boardNumber = result.getBoardNumber();
                List<HashtagHasBoardEntity> hashtagHasBoardEntities = hashtagHasBoardRepository.findByBoardNumber(boardNumber);

                List<HashTagEntity> hashListEntities = new ArrayList<>();
                for(HashtagHasBoardEntity hashtagHasBoardEntity : hashtagHasBoardEntities){
                    int hashtagNumber = hashtagHasBoardEntity.getHashtagNumber();
                    HashTagEntity hashtagEntity = hashtagRepository.findByHashtagNumber(hashtagNumber);
                    hashListEntities.add(hashtagEntity);
                    }
                BoardListResultDto boardListResultDto = new BoardListResultDto(result, hashListEntities);
                boardListResultDtos.add(boardListResultDto);
            }

            body = new GetBoardListResponseDto(boardListResultDtos);
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 특정 해시태그로 게시물 리스트 검색(해시태그 + 기온)
    public ResponseEntity<? super GetBoardListResponseDto> getSearchListByHashtag(String hashtag, Integer minTemperature, Integer maxTemperature) {
        GetBoardListResponseDto body = null;
        try {

            List<GetBoardListResult> resultSet = boardRepository.getSearchHashtagByWordAndTemperatures(hashtag, minTemperature, maxTemperature);
            List<BoardListResultDto> boardListResultDtos = new ArrayList<>();
            for(GetBoardListResult result:resultSet){
                int boardNumber = result.getBoardNumber();
                List<HashtagHasBoardEntity> hashtagHasBoardEntities = hashtagHasBoardRepository.findByBoardNumber(boardNumber);

                List<HashTagEntity> hashListEntities = new ArrayList<>();
                for(HashtagHasBoardEntity hashtagHasBoardEntity : hashtagHasBoardEntities){
                    int hashtagNumber = hashtagHasBoardEntity.getHashtagNumber();
                    HashTagEntity hashtagEntity = hashtagRepository.findByHashtagNumber(hashtagNumber);
                    hashListEntities.add(hashtagEntity);
                    }
                BoardListResultDto boardListResultDto = new BoardListResultDto(result, hashListEntities);
                boardListResultDtos.add(boardListResultDto);
            }

            body = new GetBoardListResponseDto(boardListResultDtos);
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 특정 해시태그로 게시물 리스트 검색(해시태그 + 날씨 + 기온)
    public ResponseEntity<? super GetBoardListResponseDto> getSearchListByHashtag(String hashtag, String weather, Integer minTemperature, Integer maxTemperature) {
        GetBoardListResponseDto body = null;
        try {

            List<GetBoardListResult> resultSet = boardRepository.getSearchHashtagByWordAndAll(hashtag, weather, minTemperature, maxTemperature);
            List<BoardListResultDto> boardListResultDtos = new ArrayList<>();
            for(GetBoardListResult result:resultSet){
                int boardNumber = result.getBoardNumber();
                List<HashtagHasBoardEntity> hashtagHasBoardEntities = hashtagHasBoardRepository.findByBoardNumber(boardNumber);

                List<HashTagEntity> hashListEntities = new ArrayList<>();
                for(HashtagHasBoardEntity hashtagHasBoardEntity : hashtagHasBoardEntities){
                    int hashtagNumber = hashtagHasBoardEntity.getHashtagNumber();
                    HashTagEntity hashtagEntity = hashtagRepository.findByHashtagNumber(hashtagNumber);
                    hashListEntities.add(hashtagEntity);
                    }
                BoardListResultDto boardListResultDto = new BoardListResultDto(result, hashListEntities);
                boardListResultDtos.add(boardListResultDto);
            }

            body = new GetBoardListResponseDto(boardListResultDtos);
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

}
