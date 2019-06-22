package com.rootech.msolver.dao;

import com.rootech.msolver.common.dto.CustomUserDetails;
import com.rootech.msolver.vo.UserVo;

public interface UserDao {
	
	public CustomUserDetails selectUser(UserVo userVo);
	
	public String chkDupUser(UserVo userVo);
	
	public int insertUser(UserVo userVo);
	
	public int updateUser(UserVo userVo);
	
	public int deleteUser(UserVo userVo);

	public UserVo selectLoginUser(UserVo userVo);
}
