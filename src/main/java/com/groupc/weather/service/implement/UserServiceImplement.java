package com.groupc.weather.service.implement;

import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.groupc.weather.common.util.CustomResponse;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.user.FindByEmailRequestDto;
import com.groupc.weather.dto.request.user.FindByPasswordRequestDto;
import com.groupc.weather.dto.request.user.LoginUserRequestDto;
import com.groupc.weather.dto.request.user.PatchUserRequestDto;
import com.groupc.weather.dto.request.user.PatchUserRequestDto2;
import com.groupc.weather.dto.request.user.PostUserRequestDto;
import com.groupc.weather.dto.response.user.FindByEmailResponseDto;
import com.groupc.weather.dto.response.user.FindByPasswordResponseDto;
import com.groupc.weather.dto.response.user.LoginUserResponseDto;
import com.groupc.weather.entity.ManagerEntity;
import com.groupc.weather.entity.UserEntity;
import com.groupc.weather.provider.JwtProvider;
import com.groupc.weather.repository.ManagerRepository;
import com.groupc.weather.repository.UserRepository;
import com.groupc.weather.service.UserService;

@Service
public class UserServiceImplement implements UserService {

    private UserRepository userRepository;
    private ManagerRepository managerRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplement(
            UserRepository userRepository,
            ManagerRepository managerRepository,
            JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
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
            UserEntity userEntity = new UserEntity(dto); // 생성자 없음
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

            // 이거 반복문 돌려서 이름이랑 폰 번호 일치하는 사람의 이메일을 찾는 로직이 있어햐 하지 않나?
            // ==> DB 쿼리에서 WHRER 조건문 달아서 구분.
            String currentName = userEntity.getName(); // Entity 에 저장된 유저의 데이터 (name)
            String currentPhoneNumber = userEntity.getPhoneNumber();

            // 해당하는 이메일 반환.
            if (currentName == name && currentPhoneNumber == phoneNumber) { // name => 사용자가 입력한 유저의 데이터 (name)
                String userEmail = userEntity.getEmail();
                body = new FindByEmailResponseDto(userEmail);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 유저 비밀번호 찾기
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
            String currentEmail = userEntity.getEmail();
            String currentPoneNumber = userEntity.getPhoneNumber();

            if (currentEmail == email && currentPoneNumber == phoneNumber) {
                String userPassword = userEntity.getPassword();
                body = new FindByPasswordResponseDto(userPassword);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // 유저 정보 수정
    @Override
    public ResponseEntity<ResponseDto> patchUser(PatchUserRequestDto dto) {
        String userEmail = dto.getUserEmail();
        PatchUserRequestDto2 dto2 = new PatchUserRequestDto2(dto);

        ResponseEntity<ResponseDto> response = patchUser(userEmail, dto2);

        return response;
    }

    // 특정 유저 조회

    // 유저 정보 삭제
    @Override
    public ResponseEntity<ResponseDto> deleteUser(PostUserRequestDto dto) {

        String userEmail = dto.getUserEmail();
        String userPassword = dto.getUserPassword();

        try {

            // 존재하는 이메일인지
            boolean existsUserEmail = userRepository.existsByEmail(userEmail);

            // 저 이메일이랑 맞는 비밀번호인지 어떻게 알수있는가??
            // 밑에 있는 로직은 유저레포지토리에 있는 존재하는 수많은 패스워드중 하나 일텐데 어떻게 이메일에 쓸때
            // 쓰는 비밀번호랑 같은지 알 수 있는가??? 이것도 mysql쿼리문으로 해야 하는가???
            boolean existsUserPassword = userRepository.existsByPassword(userPassword);

            if (!existsUserEmail)
                return CustomResponse.signInFailed();
            if (!existsUserPassword)
                return CustomResponse.signInFailed();

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();
    }

    @Override
    public ResponseEntity<ResponseDto> patchUser(String userEmail, PatchUserRequestDto2 requestBody) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'patchUser'");
    }

}
