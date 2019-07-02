package com.rootech.msolver.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rootech.msolver.LoginController;
import com.rootech.msolver.common.dto.IListData;
import com.rootech.msolver.common.util.JsonDataHandlerImpl;
import com.rootech.msolver.dao.UserDao;
import com.rootech.msolver.service.LoginService;
import com.rootech.msolver.vo.UserVo;

@Service("LoginService")
public class LoginServiceImpl implements LoginService{

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	@Resource(name="UserDao")
	private UserDao userDao;
	
	@Autowired
	private JsonDataHandlerImpl jsonDataHandlerImpl;

	@Override
	public int updateLoginDateBy(String userId) {
		UserVo userVo = new UserVo();
		return 0;
	}

	@Override
	public String chkDupUser(IListData listData) {
		UserVo userVo = new UserVo();
		Map<?, ?> userInfoMap = listData.getDataList("layer_input").get(0);
		return userDao.chkDupUser((UserVo) jsonDataHandlerImpl.convertMapToObject(userInfoMap, userVo));
	}

	@Override
	public String addUser(IListData listData) {
		UserVo userVo = new UserVo();
		Map<?, ?> userInfoMap = listData.getDataList("layer_input").get(0);
		userVo = (UserVo) jsonDataHandlerImpl.convertMapToObject(userInfoMap, userVo);
		userVo.setPassword(new BCryptPasswordEncoder().encode(userVo.getPassword()));
		
		int ret =  userDao.insertUser(userVo);
		if(ret != 1) {
			return("Error");
		} else {
			// 관리자 권한관리 화면이 없어 임의로 'ROLE_USER' 권한 부여 190702
			int ret2 =  userDao.insertUserAuth(userVo);
			logger.debug("insertUserAuth Success: {}", ret2);
			return userVo.getUserId();
		}
		
	}

}
