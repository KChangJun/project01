package com.groupc.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupc.weather.entity.ManagerEntity;
@Repository
public interface ManagerRepository extends JpaRepository<ManagerEntity,Integer> {
    public boolean existsByEmail(String email);
    public boolean existsByManagerNumber(Integer managerNumber); //Integer userNumber 이렇게 되어있길래 managerNumber로 바꿈
    public ManagerEntity findByManagerNumber(Integer managerNumber);

    public ManagerEntity findByEmail(String email);
    public boolean existsByNickname(String nickname);

    public boolean existsByPhoneNumber(String phoneNumber);

}
