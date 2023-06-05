package com.groupc.weather.service.implement;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.groupc.weather.common.util.CustomResponse;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.follow.DeleteFollowRequestDto;
import com.groupc.weather.dto.request.follow.FollowRequestDto;
import com.groupc.weather.dto.request.user.DeleteUserRequestDto;
import com.groupc.weather.dto.request.user.FindByEmailRequestDto;
import com.groupc.weather.dto.request.user.FindByPasswordRequestDto;
import com.groupc.weather.dto.request.user.LoginUserRequestDto;
import com.groupc.weather.dto.request.user.PatchUserRequestDto;
import com.groupc.weather.dto.request.user.PostUserRequestDto;
import com.groupc.weather.dto.response.user.FindByEmailResponseDto;
import com.groupc.weather.dto.response.user.FindByPasswordResponseDto;
import com.groupc.weather.dto.response.user.FollowerUserResponseDto;
import com.groupc.weather.dto.response.user.FollowingUserResponseDto;
import com.groupc.weather.dto.response.user.GetTop5FollowerResponseDto;
import com.groupc.weather.dto.response.user.GetUserResponseDto;
import com.groupc.weather.dto.response.user.LoginUserResponseDto;
import com.groupc.weather.entity.BoardEntity;
import com.groupc.weather.entity.CommentEntity;
import com.groupc.weather.entity.FollowingEntity;
import com.groupc.weather.entity.ManagerEntity;
import com.groupc.weather.entity.UserEntity;
import com.groupc.weather.entity.resultSet.GetFollowerListResultSet;
import com.groupc.weather.entity.resultSet.GetFollowingListResultSet;
import com.groupc.weather.entity.resultSet.GetTop5FollowerListResult;
import com.groupc.weather.provider.JwtProvider;
import com.groupc.weather.repository.BoardRepository;
import com.groupc.weather.repository.CommentRepository;
import com.groupc.weather.repository.FollowRepository;
import com.groupc.weather.repository.ManagerRepository;
import com.groupc.weather.repository.UserRepository;

import com.groupc.weather.service.UserService2;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement2 implements UserService2 {

    private UserRepository userRepository;
    private ManagerRepository managerRepository;
    private FollowRepository followRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplement2(
            UserRepository userRepository,
            FollowRepository followRepository,
            ManagerRepository managerRepository,
            JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.followRepository = followRepository;
        this.managerRepository = managerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // 유저 service 관리자 service 똑같은 기능 따로 만들어서 해도 된다하심.
    // 유저 등록
    @Override
    public ResponseEntity<ResponseDto> postUser(PostUserRequestDto dto) {

        String email = dto.getUserEmail();
        String nickname = dto.getUserNickname();
        String phoneNumber = dto.getUserPhoneNumber();
        String password = dto.getUserPassword();

        try {
            // 이메일 중복 반환.
            boolean hasEmail = userRepository.existsByEmail(email);
            if (hasEmail)
                return CustomResponse.existUserEmail();
            // 닉네임 중복 반환
            boolean hasNickname = userRepository.existsByNickname(nickname);
            if (hasNickname)
                return CustomResponse.existUserNickname();
            // 핸드폰 번호 중복 반환
            boolean hasPhoneNumber = userRepository.existsByPhoneNumber(phoneNumber);
            if (hasPhoneNumber)
                return CustomResponse.existUserPhoneNumber();

            // 패스워드 암호화
            String encodedPassword = passwordEncoder.encode(password);
            dto.setUserPassword(encodedPassword);

            // 유저 레코드 삽입
            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);

        } catch (Exception exception) {
            // 데이터베이스 오류
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();

    }

    // 유저 로그인
    @Override
    public ResponseEntity<? super LoginUserResponseDto> LoginUser(LoginUserRequestDto dto) {

        LoginUserResponseDto body = null;

        String email = dto.getUserEmail();
        String password = dto.getUserPassword();

        try {
            // 일반적인 유저 로그인
            // 로그인 실패 반환. ( 이메일 )
            UserEntity userEntity = userRepository.findByEmail(email);
            ManagerEntity managerEntity = managerRepository.findByEmail(email);
            if (userEntity == null){         
            //관리자 로그인
            if(managerEntity == null) return CustomResponse.signInFailedEmail();
            if(managerEntity.getEmail().equals(email)){
                String encordedPassword = managerEntity.getPassword();
                boolean equaledPassword = passwordEncoder.matches(password, encordedPassword);
                if (!equaledPassword)
                    return CustomResponse.signInFailedPassword();
                if(!managerEntity.isActive())
                    return CustomResponse.noPermissions();

                String jwt = jwtProvider.create(email, true);
                body = new LoginUserResponseDto(jwt);
                    return ResponseEntity.status(HttpStatus.OK).body(body);
                }
            }

            // 로그인 실패 반환. ( 패스워드 )

                String encordedPassword = userEntity.getPassword();
                boolean equaledPassword = passwordEncoder.matches(password, encordedPassword);
                if (!equaledPassword)
                    return CustomResponse.signInFailedPassword();

                String jwt = jwtProvider.create(email, false);
                body = new LoginUserResponseDto(jwt);


        }catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 유저 이메일 찾기
    @Override
    public ResponseEntity<? super FindByEmailResponseDto> FindByEmail(FindByEmailRequestDto dto) {

        FindByEmailResponseDto body = null;

        String name = dto.getUserName();
        String phoneNumber = dto.getUserPhoneNumber();

        try {
            // 존재하지 않는 이름 반환.
            UserEntity userEntity = userRepository.findByName(name);
            if (userEntity == null)
                return CustomResponse.undifindeUsername();

            // 존재하지 않는 폰 번호 반환.
            boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(phoneNumber);
            if (!existsByPhoneNumber)
                return CustomResponse.undifindephonenumber();

            // 해당하는 이메일 반환.
            String userEmail = userEntity.getEmail();
            body = new FindByEmailResponseDto(userEmail);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 유저 비밀번호 찾기 (encoding 복호화 기능)
    @Override
    public ResponseEntity<? super FindByPasswordResponseDto> FindByPassword(FindByPasswordRequestDto dto) {

        FindByPasswordResponseDto body = null;

        String email = dto.getUserEmail();
        String phoneNumber = dto.getUserPhoneNumber();

        try {
            // 존재하지 않는 이메일 반환.
            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity == null)
                return CustomResponse.undifindeEmail();

            // 존재하지 않는 폰 번호 반환.
            boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(phoneNumber);
            if (!existsByPhoneNumber)
                return CustomResponse.undifindephonenumber();

            // 해당하는 비밀번호 반환.
            String userPassword = userEntity.getPassword();
            body = new FindByPasswordResponseDto(userPassword);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 유저 정보 수정
    @Override
    public ResponseEntity<ResponseDto> patchUser(PatchUserRequestDto dto) {

        Integer userNumber = dto.getUserNumber();
        String userEmail = dto.getUserEmail();
        String userPassword = dto.getUserPassword();
        String userNickname = dto.getUserNickname();
        String userPhoneNumber = dto.getUserPhoneNumber();
        String userAddress = dto.getUserPassword();
        String userProfileImageUrl = dto.getUserProfileImageUrl();
        String userGender = dto.getUserGender();
        String userBirthDay = dto.getUserBirthDay();

        try {
            // 존재하지 않는 유저 번호 반환
            UserEntity userEntity = userRepository.findByUserNumber(userNumber);
            if (userEntity == null)
                return CustomResponse.undifindUserNumber();

            // 권한 없음
            boolean equalWriter = userEntity.getEmail().equals(userEmail);
            if (!equalWriter)
                return CustomResponse.noPermissions();

            userEntity.setEmail(userEmail);
            String encodedPassword = passwordEncoder.encode(userPassword);
            userEntity.setPassword(encodedPassword);
            userEntity.setNickname(userNickname);
            userEntity.setPhoneNumber(userPhoneNumber);
            userEntity.setAddress(userAddress);
            userEntity.setProfileImageUrl(userProfileImageUrl);
            userEntity.setGender(userGender);
            userEntity.setBirthday(userBirthDay);

            userRepository.save(userEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return CustomResponse.success();
    }

    // 유저 정보 삭제
    @Override
    public ResponseEntity<ResponseDto> deleteUser(DeleteUserRequestDto dto) {

        Integer userNumber = dto.getUserNumber();
        String password = dto.getUserPassword();

        try {

            // 존재하지 않는 유저 번호 반환.
            UserEntity userEntity = userRepository.findByUserNumber(userNumber);
            if (userEntity == null)
                return CustomResponse.undifindUserNumber();

            // 일치하지 않는 비밀번호 반환.
            String encordedPassword = userEntity.getPassword();
            boolean equaledPassword = passwordEncoder.matches(password, encordedPassword);
            if (!equaledPassword)
                return CustomResponse.signInFailedPassword();

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();
    }

    // 특정 유저 조회
    public ResponseEntity<? super GetUserResponseDto> getUser(Integer userNumber) {

        GetUserResponseDto body = null;

        try {
            // 존재하지 않는 유저 반환.
            UserEntity userEntity = userRepository.findByUserNumber(userNumber);
            if (userEntity == null)
                return CustomResponse.notExistUserNumber();

            /// board / comment 는 아직 연결 안함.
            // List<FollowerEntity> followerEntities =
            // followerRepository.findFollowerList(userNumber);
            // List<FollowEntity> followingEntities =
            // followingRepository.findFollowingList(userNumber);
            // BoardEntity boardEntity = boardRepository.findByUserNumber(userNumber);
            // CommentEntity commentEntity = commentRepository.findByUserNumber(userNumber);

            userEntity = userRepository.findByUserNumber(userNumber); // 이거 왜 썼지

            List<GetFollowerListResultSet> followerList = followRepository.getFollowerUserList(userNumber);
            List<GetFollowingListResultSet> followingList = followRepository.getFollowingUserList(userNumber);

            body = new GetUserResponseDto(userEntity, followerList, followingList);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 특정 유저 팔로우
    @Override
    public ResponseEntity<ResponseDto> followUser(FollowRequestDto dto) {

        ResponseDto responseBody = null;

        Integer followerNumber = dto.getFollowerNumber();
        Integer followingNumber = dto.getFollowingNumber();

        try {

            // 존재하지 않는 유저 반환.
            UserEntity userEntity = userRepository.findByUserNumber(followingNumber);
            if (userEntity == null)
                return CustomResponse.undifindUserNumber();

            // follow Entity에 입력한 데이터 저장.
            FollowingEntity followEntity = new FollowingEntity(dto);
            followRepository.save(followEntity);

            responseBody = new ResponseDto("SU", "Success");

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return CustomResponse.success();
    }

    // 팔로우 해제
    @Override
    public ResponseEntity<ResponseDto> deleteFollow(DeleteFollowRequestDto dto) {

        Integer userNumber = dto.getUserNumber();
        Integer followingUserNumber = dto.getFollowingUserNumber();

        try {

            // 존재하지 않는 유저 반환
            UserEntity userEntity = userRepository.findByUserNumber(userNumber);
            if (userEntity == null)
                return CustomResponse.undifindUserNumber();

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return CustomResponse.success();
    }

    // Top5 팔로우 유저 조회
    @Override
    public ResponseEntity<? super GetTop5FollowerResponseDto> getFollowerTop5() {

        GetTop5FollowerResponseDto body = null;

        try {

            List<GetTop5FollowerListResult> resultSet = userRepository.getTop5ListBy();
            body = new GetTop5FollowerResponseDto(resultSet);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 본인 follower한 유저 조회
    @Override
    public ResponseEntity<? super FollowerUserResponseDto> getFollowerUser(Integer followingNumber) {

        FollowerUserResponseDto body = null;

        try {

            boolean existedFollowerNumber = userRepository.existsByUserNumber(followingNumber);
            if (!existedFollowerNumber)
                return CustomResponse.notExistUserNumber();

            List<GetFollowerListResultSet> followerList = followRepository.getFollowerUserList(followingNumber);

            body = new FollowerUserResponseDto(followerList);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 본인 followeing한 유저 조회
    @Override
    public ResponseEntity<? super FollowingUserResponseDto> getFollowingUser(Integer followerNumber) {

        FollowingUserResponseDto body = null;

        try {
            boolean existedFollowingNumber = userRepository.existsByUserNumber(followerNumber);
            if (!existedFollowingNumber)
                return CustomResponse.notExistUserNumber();

            List<GetFollowingListResultSet> followingList = followRepository.getFollowingUserList(followerNumber);

            body = new FollowingUserResponseDto(followingList);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
