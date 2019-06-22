package com.rootech.msolver.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rootech.msolver.common.dto.IListData;
import com.rootech.msolver.common.dto.ListDataImpl;
import com.rootech.msolver.service.MenuService;

@Service("MenuService")
public class MenuServiceImpl implements MenuService {

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public IListData getMenuList(String userId) {
	Map<String, String> paramMap = new HashMap<String, String>();
		
		IListData resultListData = new ListDataImpl();
		paramMap.put("userId", userId);
		
		List upperMenuList = getUpperMenuList(userId);
		resultListData.setDataList("upperMenuList", upperMenuList);
		
		List subMenuList = getSubMenuList(userId);
		resultListData.setDataList("subMenuList", subMenuList);
		
		return resultListData;
	}

	/**
	 * Sub Menu Mock
	 * @param userId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<?> getUpperMenuList(String userId) {
		
		List upperMenuList = new ArrayList<>();
		Map<String, String> upperMenuMap = new HashMap<String, String>();
		upperMenuMap.put("UPPER_MENU_CD", "");
		upperMenuMap.put("MENU_CD", "msol");
		upperMenuMap.put("MENU_KOR_NM", "현장솔루션버전관리");
		upperMenuMap.put("MENU_URL", "/msol");
		upperMenuList.add(upperMenuMap);
		return upperMenuList;
	}

	/**
	 * Upper Menu Mock
	 * @param userId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<?> getSubMenuList(String userId) {
		List subMenuList = new ArrayList<>();
		Map<String, String> subMenuMap = new HashMap<String, String>();
		subMenuMap.put("UPPER_MENU_CD", "msol");
		subMenuMap.put("MENU_CD", "msol");
		subMenuMap.put("MENU_KOR_NM", "현장 및 솔루션버전 관리");
		subMenuMap.put("MENU_URL", "msol/mngSiteSolVer");
		subMenuList.add(subMenuMap);
		
		Map<String, String> subMenuMap2 = new HashMap<String, String>();
		subMenuMap2.put("UPPER_MENU_CD", "msol");
		subMenuMap2.put("MENU_CD", "mss");
		subMenuMap2.put("MENU_KOR_NM", "구축현장 솔루션버전관리");
		subMenuMap2.put("MENU_URL", "msol/mSS");
		subMenuList.add(subMenuMap2);
		
		return subMenuList;
	}

	
}
