package com.rootech.msolver.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rootech.msolver.common.dto.CustomUserDetails;
import com.rootech.msolver.dao.UserDao;
import com.rootech.msolver.service.UserService;
import com.rootech.msolver.vo.UserVo;

@Service("UserService")
public class UserServiceImpl implements UserService{

	@Resource(name="UserDao")
	private UserDao userDao;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;


	@Override
	public int insertUser(UserVo userVo) {
		userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
		return userDao.insertUser(userVo);
	}

	@Override
	public CustomUserDetails getUser(UserVo loginVo) {
		return userDao.selectUser(loginVo);
	}

	@Override
	public UserVo getUser(String userId) {
		UserVo reqVo = new UserVo();
		reqVo.setUserId(userId);
		return userDao.selectLoginUser(reqVo);
	}

}
