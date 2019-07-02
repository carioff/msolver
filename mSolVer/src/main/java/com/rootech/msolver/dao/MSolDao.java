package com.rootech.msolver.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MSolDao {

	List<?> getSiteList(Map<?, ?> paramMap) throws Exception;

	String getSiteCnt(Map<?, ?> paramMap) throws Exception;

	List<?> getSolVersionList(Map<?, ?> paramMap) throws Exception;

	String getSolVersionCnt(Map<?, ?> paramMap) throws Exception;
	
	List<?> getSiteSolVerList(Map<?, ?> paramMap) throws Exception;

	String getSiteSolVerCnt(Map<?, ?> paramMap) throws Exception;

	String getAddedSiteAvail(HashMap<?, ?> rowData) throws Exception;

	void insertAddedSite(HashMap<?, ?> rowData) throws Exception;

	void updateSiteName(HashMap<?, ?> rowData) throws Exception;

	String getAddedSolVersionAvail(HashMap<?, ?> rowData) throws Exception;

	void insertAddedSolVersion(HashMap<?, ?> rowData) throws Exception;

	void updateSolName(HashMap<?, ?> rowData) throws Exception;

	void deleteSite(HashMap<?, ?> rowData) throws Exception;

	void deleteSolVersion(HashMap<?, ?> rowData) throws Exception;

	List<?> getSiteForComb(Map<?, ?> paramMap) throws Exception;

	List<?> getSolVersionForComb(Map<?, ?> paramMap) throws Exception;

	String getAddedSiteSolVersionAvail(HashMap<?, ?> rowData) throws Exception;

	int insertAddedSiteSolVersion(HashMap<?, ?> rowData) throws Exception;

	int updateSiteSolVersion(HashMap<?, ?> rowData) throws Exception;

	int deleteSiteSolVersion(HashMap<?, ?> rowData) throws Exception;

}
