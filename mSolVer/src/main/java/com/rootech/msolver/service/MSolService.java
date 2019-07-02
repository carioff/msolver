package com.rootech.msolver.service;

import java.util.Map;

import com.rootech.msolver.common.dto.IListData;

public interface MSolService {

	IListData getSiteList(Map<?, ?> paramMap) throws Exception;

	IListData getSolVersionList(Map<?, ?> paramMap) throws Exception;
	
	IListData getSiteSolVerList(Map<?, ?> paramMap) throws Exception;

	IListData getSelectedCmbs() throws Exception;

	void saveSite(IListData listData) throws Exception;

	void saveSolVersion(IListData listData) throws Exception;

	IListData saveSiteSolVer(IListData listData) throws Exception;

}
