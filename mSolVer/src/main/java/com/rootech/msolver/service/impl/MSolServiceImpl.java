package com.rootech.msolver.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rootech.msolver.common.dto.IListData;
import com.rootech.msolver.common.dto.ListDataImpl;
import com.rootech.msolver.common.exception.CustomedExceptionImpl;
import com.rootech.msolver.dao.MSolDao;
import com.rootech.msolver.service.MSolService;
import com.rootech.msolver.service.PushService;

@Service("MSolService")
public class MSolServiceImpl implements MSolService{

	@Resource(name="MSolDao")
	private MSolDao mSolDao;
	
	@Autowired 
	private PushService pushService;
	
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
	public IListData saveSiteSolVer(IListData listData) throws Exception {
		List<?> list = listData.getDataList("ss_chg");
		HashMap rowData = null;	
		String rowStatus = "";
		IListData resultListData = new ListDataImpl();
		for (int i = 0; i < list.size(); i++) {
			rowData = (HashMap) list.get(i);
			rowStatus = String.valueOf(rowData.get("ROW_STATUS"));
			rowData.put("userId", listData.getParameter("userId"));
			
			String siteId = String.valueOf(rowData.get("siteId"));
			String solVersion = String.valueOf(rowData.get("solVersion"));
			String applyDate = String.valueOf(rowData.get("applyDate"));
			String applyWorker = String.valueOf(rowData.get("applyWorker"));
			String applyContents = String.valueOf(rowData.get("applyContents"));
			
			StringBuffer msg = new StringBuffer();

			msg.append(siteId);
			msg.append(" ");
			msg.append(solVersion);
			msg.append(" ");
			msg.append(applyDate);
			msg.append(" ");
			msg.append(applyWorker);
			msg.append(" ");
			msg.append(applyContents);
			msg.append(" ! ");
			int ret = 0;
			if ("I".equals(rowStatus)) {
				
				String avail_yn = mSolDao.getAddedSiteSolVersionAvail(rowData);
				if ("N".equals(avail_yn)) {
					throw new CustomedExceptionImpl(4, msg.toString()
							+ " Solution Version On Site already exist. "
							+ "Fail to save the Solution Version On Site.");
				}
				ret = mSolDao.insertAddedSiteSolVersion(rowData);
			} else if ("U".equals(rowStatus)) {
				ret = mSolDao.updateSiteSolVersion(rowData);
			} else if ("D".equals(rowStatus)) {
				ret = mSolDao.deleteSiteSolVersion(rowData);
			}
			if(ret > 0) {
				
				List returnPushMsg = new ArrayList<>();
				HashMap pushMsg = new HashMap<String, String>();
				
				pushMsg.put("siteId", siteId);
				pushMsg.put("solVersion", solVersion);
				pushMsg.put("applyDate", applyDate);
				pushMsg.put("applyWorker", applyWorker);
				pushMsg.put("applyContents", applyContents);
				pushMsg.put("exeCategory", getExeCategory(rowStatus));
				returnPushMsg.add(pushMsg);
				resultListData.setDataList("pushMsg", returnPushMsg);
			}
			
		}	

		return resultListData;
	}

	/**
	 * 최신 등록 Msg전달 
	 * @param rowStatus
	 * @return
	 */
	private String getExeCategory(String rowStatus) {
		if ("I".equals(rowStatus)) {
			return "Registed";
		} else if ("U".equals(rowStatus)) {
			return "Updated";
		} else if ("D".equals(rowStatus)) {
			return "Deleted";
		}
		return "ERROR";
	}
	
}
