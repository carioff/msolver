package com.rootech.msolver.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rootech.msolver.common.dto.IListData;
import com.rootech.msolver.common.dto.ListDataImpl;
import com.rootech.msolver.common.exception.CustomedExceptionImpl;
import com.rootech.msolver.dao.MSolDao;
import com.rootech.msolver.service.MSolService;

@Service("MSolService")
public class MSolServiceImpl implements MSolService{

	@Resource(name="MSolDao")
	private MSolDao mSolDao;
	
	@Override
	public IListData getSiteList(Map<?, ?> paramMap) throws Exception {
		IListData resultListData = new ListDataImpl();
		
		List returnList = mSolDao.getSiteList(paramMap);
		resultListData.setDataList("siteList", returnList);
		
		String siteCnt = mSolDao.getSiteCnt(paramMap);
		resultListData.addVariable("siteCnt", siteCnt);
		
		return resultListData;
	}

	@Override
	public IListData getSolVersionList(Map<?, ?> paramMap) throws Exception {
		IListData resultListData = new ListDataImpl();
		
		List returnList = mSolDao.getSolVersionList(paramMap);
		resultListData.setDataList("solVersionList", returnList);
		
		String solVersionCnt = mSolDao.getSolVersionCnt(paramMap);
		resultListData.addVariable("solVersionCnt", solVersionCnt);
		
		return resultListData;
	}

	@Override
	public IListData getSiteSolVerList(Map<?, ?> paramMap) throws Exception {
		IListData resultListData = new ListDataImpl();
		
		List returnList = mSolDao.getSiteSolVerList(paramMap);
		resultListData.setDataList("siteSolVerList", returnList);
		
		String siteSolVerCnt = mSolDao.getSiteSolVerCnt(paramMap);
		resultListData.addVariable("siteSolVerCnt", siteSolVerCnt);
		
		return resultListData;
	}

	@Override
	public IListData getSelectedCmbs() throws Exception {
		IListData resultListData = new ListDataImpl();
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("site_id", "");
		paramMap.put("siteName", "");
		paramMap.put("solVersion", "");
		paramMap.put("solName", "");
		List returnListSite = mSolDao.getSiteForComb(paramMap);
		resultListData.setDataList("siteForComb", returnListSite);
		List returnListSolVersion = mSolDao.getSolVersionForComb(paramMap);
		resultListData.setDataList("solVersionForComb", returnListSolVersion);
		return resultListData;
		
	}
	
	@Override
	public void saveSite(IListData listData) throws Exception {
		List<?> list = listData.getDataList("site_chg");
		HashMap<?, ?> rowData = null;	
		String rowStatus = "";
		for (int i = 0; i < list.size(); i++) {
			rowData = (HashMap<?, ?>) list.get(i);
			rowStatus = String.valueOf(rowData.get("ROW_STATUS"));
			if ("I".equals(rowStatus)) {
				String avail_yn = mSolDao.getAddedSiteAvail(rowData);
				if ("N".equals(avail_yn)) {
					throw new CustomedExceptionImpl(4, String.valueOf(rowData.get("siteId")) 
							+ " Site already exist. Fail to save the site.");
				}
				mSolDao.insertAddedSite(rowData);
				
			} else if ("U".equals(rowStatus)) {
				mSolDao.updateSiteName(rowData);
			} else if ("D".equals(rowStatus)) {
				mSolDao.deleteSite(rowData);
			}
		}
		
	}

	@Override
	public void saveSolVersion(IListData listData) throws Exception {
		List<?> list = listData.getDataList("solVersion_chg");
		HashMap<?, ?> rowData = null;	
		String rowStatus = "";
		for (int i = 0; i < list.size(); i++) {
			rowData = (HashMap<?, ?>) list.get(i);
			rowStatus = String.valueOf(rowData.get("ROW_STATUS"));
			if ("I".equals(rowStatus)) {
				String avail_yn = mSolDao.getAddedSolVersionAvail(rowData);
				if ("N".equals(avail_yn)) {
					throw new CustomedExceptionImpl(4, String.valueOf(rowData.get("solVersion")) 
							+ " Solution Version already exist. Fail to save the Solution Version.");
				}
				mSolDao.insertAddedSolVersion(rowData);
				
			} else if ("U".equals(rowStatus)) {
				mSolDao.updateSolName(rowData);
			} else if ("D".equals(rowStatus)) {
				mSolDao.deleteSolVersion(rowData);
			}
		}	
	}

	@Override
	public void saveSiteSolVer(IListData listData) throws Exception {
		List<?> list = listData.getDataList("ss_chg");
		HashMap rowData = null;	
		String rowStatus = "";
		for (int i = 0; i < list.size(); i++) {
			rowData = (HashMap) list.get(i);
			rowStatus = String.valueOf(rowData.get("ROW_STATUS"));
			rowData.put("userId", listData.getParameter("userId"));
			if ("I".equals(rowStatus)) {
				String avail_yn = mSolDao.getAddedSiteSolVersionAvail(rowData);
				if ("N".equals(avail_yn)) {
					throw new CustomedExceptionImpl(4, String.valueOf(rowData.get("siteSolVersion")) 
							+ " Solution Version On Site already exist. "
							+ "Fail to save the Solution Version On Site.");
				}
				mSolDao.insertAddedSiteSolVersion(rowData);
				
			} else if ("U".equals(rowStatus)) {
				mSolDao.updateSiteSolVersion(rowData);
			} else if ("D".equals(rowStatus)) {
				mSolDao.deleteSiteSolVersion(rowData);
			}
		}	
		
	}
	
}
