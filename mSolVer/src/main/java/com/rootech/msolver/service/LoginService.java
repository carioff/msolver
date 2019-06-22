package com.rootech.msolver.service;

import com.rootech.msolver.common.dto.IListData;

public interface LoginService {

	public int updateLoginDateBy(String userId);
	
	public String chkDupUser(IListData listData);

	public String addUser(IListData listData);
}
