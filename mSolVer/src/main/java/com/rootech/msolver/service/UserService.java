package com.rootech.msolver.service;

import com.rootech.msolver.common.dto.CustomUserDetails;
import com.rootech.msolver.vo.UserVo;

public interface UserService {
	
	public CustomUserDetails getUser(UserVo userVo);
	
	public UserVo getUser(String userId);
	
	public int insertUser(UserVo userVo);

}
