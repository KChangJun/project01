package com.groupc.weather.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.groupc.weather.common.util.CustomResponse;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.user.LoginUserRequestDto;
import com.groupc.weather.dto.request.user.PostUserRequestDto;
import com.groupc.weather.dto.response.LoginUserResponseDto;
import com.groupc.weather.entity.UserEntity;
import com.groupc.weather.provider.JwtProvider;
import com.groupc.weather.repository.UserRepository;
import com.groupc.weather.service.UserService;

@Service
public class UserServiceImplement implements UserService {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplement(
            UserRepository userRepository,
            JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // 유저 service 관리자 service 똑같은 기능 따로 만들어서 해도 된다하심.
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

    @Override
    public ResponseEntity<? super LoginUserResponseDto> LoginUser(LoginUserRequestDto dto) {

        LoginUserResponseDto body = null;

        String email = dto.getUserEmail();
        String password = dto.getUserPassword();

        try {
            // 로그인 실패 반환. ( 이메일 )
            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity == null)
                return CustomResponse.signInFailedEmail();

            // 로그인 실패 반환. ( 패스워드 )
            String encordedPassword = userEntity.getPassword();
            boolean equaledPassword = passwordEncoder.matches(password, encordedPassword);
            if (!equaledPassword)
                return CustomResponse.signInFailedPassword();

            String jwt = jwtProvider.create(email);
            body = new LoginUserResponseDto(jwt);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 유저 정보 삭제
    @Override
    public ResponseEntity<ResponseDto> deleteUser(PostUserRequestDto dto) {

        String userEmail = dto.getUserEmail();
        String userPassword = dto.getUserPassword();

        try {
            
            //존재하는 이메일인지
            boolean existsUserEmail = userRepository.existsByEmail(userEmail);

            //저 이메일이랑 맞는 비밀번호인지 어떻게 알수있는가??
            //밑에 있는 로직은 유저레포지토리에 있는 존재하는 수많은 패스워드중 하나 일텐데 어떻게 이메일에 쓸때
            //쓰는 비밀번호랑 같은지 알 수 있는가??? 이것도 mysql쿼리문으로 해야 하는가???
            boolean existsUserPassword = userRepository.existsByPassword(userPassword);
            
            if(!existsUserEmail) return CustomResponse.signInFailed();
            if(!existsUserPassword) return CustomResponse.signInFailed();
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();
    }

    
}
