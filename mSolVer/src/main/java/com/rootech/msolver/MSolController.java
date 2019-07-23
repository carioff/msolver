package com.rootech.msolver;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.rootech.msolver.common.dto.IListData;
import com.rootech.msolver.common.dto.ListDataImpl;
import com.rootech.msolver.common.util.JsonDataHandlerImpl;
import com.rootech.msolver.service.MSolService;

@Controller
@RequestMapping("/msol")
public class MSolController {
	
	private static final Logger logger = LoggerFactory.getLogger(MSolController.class);

	@Autowired 
	private MSolService mSolService;
	
	@Resource
	private JsonDataHandlerImpl jsonDataHandlerImpl;
	
	@RequestMapping(value = "/mngSiteSolVer", method = RequestMethod.GET)
	public ModelAndView getMSiteSolVerView(HashMap<String, Object> modelMap) throws Exception {

		return new ModelAndView("msol/mSiteSolVer");
	}
	
	@RequestMapping(value = "/mSS", method = RequestMethod.GET)
	public ModelAndView getMSolView(HashMap<String, Object> modelMap) throws Exception {

		return new ModelAndView("msol/mSS");
	}
	
	@RequestMapping(value = "/mngSiteSolVer", method = RequestMethod.POST)
	public void postMSiteSolVer(@RequestBody(required=false) HashMap<String, Object> reqBodyMap, 
			HttpServletRequest req, HttpServletResponse res, BindingResult bindingResult) throws Exception {

		IListData listData = jsonDataHandlerImpl.convertToIListData(reqBodyMap);
		Map<?, ?> paramMap = listData.getParameterMap();
		IListData resultListData = new ListDataImpl();
		
		String svc_id = (String) paramMap.get("SVC_ID");
		if ("getSiteList".equals(svc_id)) {
			resultListData = mSolService.getSiteList(paramMap);
		} else if ("getSolVersionList".equals(svc_id)) {
			resultListData = mSolService.getSolVersionList(paramMap);
		} else if ("saveSite".equals(svc_id)) {
			mSolService.saveSite(listData);
		} else if ("saveSolVersion".equals(svc_id)) {
			mSolService.saveSolVersion(listData);
		} 
		jsonDataHandlerImpl.flushSuccessJSONResponse(res, jsonDataHandlerImpl.convertToJSONObject(resultListData));
	}
	
	@RequestMapping(value = "/mSS", method = RequestMethod.POST)
	public void postMSS(@RequestBody(required=false) HashMap<String, Object> reqBodyMap, 
			HttpServletRequest req, HttpServletResponse res, BindingResult bindingResult, 
			Authentication auth) throws Exception {

		IListData listData = jsonDataHandlerImpl.convertToIListDataWithAuth(reqBodyMap, auth);
		Map<?, ?> paramMap = listData.getParameterMap();
		IListData resultListData = new ListDataImpl();
		
		String svc_id = (String) paramMap.get("SVC_ID");
		if ("getSiteSolVerList".equals(svc_id)) {
			resultListData = mSolService.getSiteSolVerList(paramMap);
		} else if ("getSelectedCmbs".equals(svc_id)) {
			resultListData = mSolService.getSelectedCmbs();
		} else if ("saveSiteSolVer".equals(svc_id)) {
			resultListData = mSolService.saveSiteSolVer(listData);
		} 
		jsonDataHandlerImpl.flushSuccessJSONResponse(res, jsonDataHandlerImpl.convertToJSONObject(resultListData));
		//test push
	}
}
