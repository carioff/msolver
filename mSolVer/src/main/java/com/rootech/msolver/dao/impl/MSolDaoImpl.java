package com.rootech.msolver.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rootech.msolver.common.util.MybatisCustom;
import com.rootech.msolver.dao.MSolDao;

@Repository("MSolDao")
public class MSolDaoImpl extends MybatisCustom implements MSolDao {

	private static final Logger logger = LoggerFactory.getLogger(MSolDaoImpl.class);
	
	public MSolDaoImpl() {
		super("MSolDao");
	}
	
	@Override
	public List<?> getSiteList(Map<?, ?> paramMap) throws Exception {
		try {
			return queryForList(nameSpace + ".getSiteList", paramMap);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to getSiteList", e);
			}
			throw e;
		}
	}

	@Override
	public String getSiteCnt(Map<?, ?> paramMap) throws Exception {
		try {
			return queryForStr(nameSpace + ".getSiteCnt", paramMap);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to getSiteCnt", e);
			}
			throw e;
		}
	}

	@Override
	public List<?> getSolVersionList(Map<?, ?> paramMap) throws Exception {
		try {
			return queryForList(nameSpace + ".getSolVersionList", paramMap);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to getSolVersionList", e);
			}
			throw e;
		}
	}

	@Override
	public String getSolVersionCnt(Map<?, ?> paramMap) throws Exception {
		try {
			return queryForStr(nameSpace + ".getSolVersionCnt", paramMap);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to getSolVersionCnt", e);
			}
			throw e;
		}
	}
	
	@Override
	public List<?> getSiteSolVerList(Map<?, ?> paramMap) throws Exception {
		try {
			return queryForList(nameSpace + ".getSiteSolVerList", paramMap);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to getSiteSolVerList", e);
			}
			throw e;
		}
	}

	@Override
	public String getSiteSolVerCnt(Map<?, ?> paramMap) throws Exception {
		try {
			return queryForStr(nameSpace + ".getSiteSolVerCnt", paramMap);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to getSiteSolVerCnt", e);
			}
			throw e;
		}
	}
	
	@Override
	public String getAddedSiteAvail(HashMap<?, ?> rowData) throws Exception {
		try {
			return queryForStr(nameSpace + ".getAddedSiteAvail", rowData);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to getAddedSiteAvail", e);
			}
			throw e;
		}
	}

	@Override
	public void insertAddedSite(HashMap<?, ?> rowData) throws Exception {
		try {
			insert(nameSpace + ".insertAddedSite", rowData);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to insertAddedSite", e);
			}
			throw e;
		}
	}

	@Override
	public void updateSiteName(HashMap<?, ?> rowData) throws Exception {
		try {
			update(nameSpace + ".updateSiteName", rowData);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to updateSiteName", e);
			}
			throw e;
		}
	}

	@Override
	public String getAddedSolVersionAvail(HashMap<?, ?> rowData) throws Exception {
		try {
			return queryForStr(nameSpace + ".getAddedSolVersionAvail", rowData);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to getAddedSolVersionAvail", e);
			}
			throw e;
		}
	}

	@Override
	public void insertAddedSolVersion(HashMap<?, ?> rowData) throws Exception {
		try {
			insert(nameSpace + ".insertAddedSolVersion", rowData);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to insertAddedSolVersion", e);
			}
			throw e;
		}
		
	}

	@Override
	public void updateSolName(HashMap<?, ?> rowData) throws Exception {
		try {
			update(nameSpace + ".updateSolName", rowData);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to updateSolName", e);
			}
			throw e;
		}
		
	}

	@Override
	public void deleteSite(HashMap<?, ?> rowData) throws Exception {
		try {
			delete(nameSpace + ".deleteSite", rowData);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to deleteSite", e);
			}
			throw e;
		}
		
	}

	@Override
	public void deleteSolVersion(HashMap<?, ?> rowData) throws Exception {
		try {
			delete(nameSpace + ".deleteSolVersion", rowData);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to deleteSolVersion", e);
			}
			throw e;
		}
		
	}

	@Override
	public List<?> getSiteForComb(Map<?, ?> paramMap) throws Exception {
		try {
			return queryForList(nameSpace + ".getSiteForComb", paramMap);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to getSiteForComb", e);
			}
			throw e;
		}
	}

	@Override
	public List<?> getSolVersionForComb(Map<?, ?> paramMap) throws Exception {
		try {
			return queryForList(nameSpace + ".getSolVersionForComb", paramMap);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to getSolVersionForComb", e);
			}
			throw e;
		}
	}

	@Override
	public String getAddedSiteSolVersionAvail(HashMap<?, ?> rowData) throws Exception {
		try {
			return queryForStr(nameSpace + ".getAddedSiteSolVersionAvail", rowData);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to getAddedSiteSolVersionAvail", e);
			}
			throw e;
		}
	}

	@Override
	public void insertAddedSiteSolVersion(HashMap<?, ?> rowData) throws Exception {
		try {
			insert(nameSpace + ".insertAddedSiteSolVersion", rowData);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to insertAddedSiteSolVersion", e);
			}
			throw e;
		}
		
	}

	@Override
	public void updateSiteSolVersion(HashMap<?, ?> rowData) throws Exception {
		try {
			update(nameSpace + ".updateSiteSolVersion", rowData);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to updateSiteSolVersion", e);
			}
			throw e;
		}
		
	}

	@Override
	public void deleteSiteSolVersion(HashMap<?, ?> rowData) throws Exception {
		try {
			delete(nameSpace + ".deleteSiteSolVersion", rowData);
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error("fail to deleteSiteSolVersion", e);
			}
			throw e;
		}
		
	}


}
