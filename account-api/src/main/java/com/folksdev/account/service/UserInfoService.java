package com.folksdev.account.service;

import java.util.List;

import com.folksdev.account.model.UserInfo;
import com.folksdev.account.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class UserInfoService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public UserInfo getUserInfoByUserName(String userName) {
        short enabled = 1;
        return userDetailsRepository.findByUserNameAndEnabled(userName, enabled);
    }

    public List<UserInfo> getAllActiveUserInfo() {
        return userDetailsRepository.findAllByEnabled((short) 1);
    }

    public UserInfo getUserInfoById(Integer id) {
        return userDetailsRepository.findById(id);
    }

    public UserInfo addUser(UserInfo userInfo) {
        userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
        return userDetailsRepository.save(userInfo);
    }

    public UserInfo updateUser(Integer id, UserInfo userRecord) {
        UserInfo userInfo = userDetailsRepository.findById(id);
        userInfo.setUserName(userRecord.getUserName());
        userInfo.setPassword(userRecord.getPassword());
        userInfo.setRole(userRecord.getRole());
        userInfo.setEnabled(userRecord.getEnabled());
        return userDetailsRepository.save(userInfo);
    }

    public void deleteUser(Integer id) {
        userDetailsRepository.deleteById(id);
    }

    public UserInfo updatePassword(Integer id, UserInfo userRecord) {
        UserInfo userInfo = userDetailsRepository.findById(id);
        userInfo.setPassword(userRecord.getPassword());
        return userDetailsRepository.save(userInfo);
    }

    public UserInfo updateRole(Integer id, UserInfo userRecord) {
        UserInfo userInfo = userDetailsRepository.findById(id);
        userInfo.setRole(userRecord.getRole());
        return userDetailsRepository.save(userInfo);
    }
}
