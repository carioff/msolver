package com.rootech.msolver.common.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;


public interface DataHandler {

	public static final String PARAM_KEY = "PARAM_MAP";

	/**
	 * with Session
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public IListData convertToIListDataWithAuth(HashMap<String, Object> obj, Authentication authentication) throws Exception;
	
	/**
	 * without Session
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public IListData convertToIListData(HashMap<String, Object> obj) throws Exception;

	public Object convertMapToObject(Map<?, ?> map, Object objClass);
	
	public Map<?, ?> ConverObjectToMap(Object obj);
}
