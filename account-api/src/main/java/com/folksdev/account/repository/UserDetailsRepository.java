package com.folksdev.account.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.folksdev.account.model.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface UserDetailsRepository extends CrudRepository<UserInfo, String> {
    public UserInfo findByUserNameAndEnabled(String userName, short enabled);

    public List<UserInfo> findAllByEnabled(short enabled);

    public UserInfo findById(Integer id);
//
//	@Override
//	public UserInfo save(UserInfo userInfo);

    public void deleteById(Integer id);
}