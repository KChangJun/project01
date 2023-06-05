package com.groupc.weather.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.groupc.weather.common.model.AuthenticationObject;
import com.groupc.weather.common.util.CustomResponse;
import com.groupc.weather.dto.ResponseDto;
import com.groupc.weather.dto.request.manager.ActiveManagerDto;
import com.groupc.weather.dto.request.manager.PostManagerRequestDto;
import com.groupc.weather.entity.ManagerEntity;
import com.groupc.weather.provider.JwtProvider;
import com.groupc.weather.repository.LikeyRepository;
import com.groupc.weather.repository.ManagerRepository;
import com.groupc.weather.repository.UserRepository;
import com.groupc.weather.service.ManagerService;


@Service
public class ManagerServiceImplement implements ManagerService {

    private ManagerRepository managerRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public ManagerServiceImplement(
            UserRepository userRepository,ManagerRepository managerRepository,LikeyRepository likeyRepository,
            JwtProvider jwtProvider) {
        this.managerRepository = managerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public ResponseEntity<ResponseDto> activeManager(AuthenticationObject authenticationObject,
            ActiveManagerDto dto) {
                
            boolean isManager = authenticationObject.isManagerFlag();
            Integer managerNumber = dto.getManagerNumber();
        try {
            //권한이 있는지 확인
            if(!isManager) return CustomResponse.noPermissions();
            
            //관리자 권한을 활성화 시키고 싶은 사람이 있는지 확인
            boolean existedManagerNumber = managerRepository.existsByManagerNumber(managerNumber);
            if(!existedManagerNumber) return CustomResponse.notExistManagerNumber();

            //존재할경우 기존에 있던 유저의 정보를 이용해서 매니저 entity를 생성하고
            //유저 테이블에서 삭제함


            ManagerEntity managerEntity= managerRepository.findByManagerNumber(managerNumber);
            managerEntity.setActive(true);
            managerRepository.save(managerEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();

        }

        return CustomResponse.success();
    }


    @Override
    public ResponseEntity<ResponseDto> postManager(PostManagerRequestDto dto) { 
        String email = dto.getEmail();
        String nickname = dto.getNickname();
        String phoneNumber = dto.getPhoneNumber();
        String password = dto.getPassword();

        try {
            // 이메일 중복 반환.
            boolean hasEmail = managerRepository.existsByEmail(email);
            if (hasEmail)
                return CustomResponse.existUserEmail();
            // 닉네임 중복 반환
            boolean hasNickname = managerRepository.existsByNickname(nickname);
            if (hasNickname)
                return CustomResponse.existUserNickname();
            // 핸드폰 번호 중복 반환
            boolean hasPhoneNumber = managerRepository.existsByPhoneNumber(phoneNumber);
            if (hasPhoneNumber)
                return CustomResponse.existUserPhoneNumber();

            // 패스워드 암호화
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            // 유저 레코드 삽입
            ManagerEntity mangerEntity = new ManagerEntity(dto); // 생성자 없음
            managerRepository.save(mangerEntity);

        } catch (Exception exception) {
            // 데이터베이스 오류
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }

        return CustomResponse.success();

        
    }



}
