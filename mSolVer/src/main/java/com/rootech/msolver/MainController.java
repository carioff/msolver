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
import com.rootech.msolver.service.MenuService;
import com.rootech.msolver.service.UserService;
import com.rootech.msolver.vo.UserVo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

//	@Autowired 
//	private PushService pushService;
	
	@Autowired 
	private UserService userService;
	
	@Autowired 
	private MenuService menuService;
	
	@Resource
	private JsonDataHandlerImpl jsonDataHandlerImpl;

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView goMain(HashMap<String, Object> modelMap) throws Exception {

		return new ModelAndView("main");
	}

	@RequestMapping(value = "/main", method = RequestMethod.POST)
	public void postMain(@RequestBody(required = false) HashMap<String, Object> reqBodyMap, 
			HttpServletRequest req, HttpServletResponse res, 
			BindingResult bindingResult, Authentication auth) throws Exception {

		IListData listData = jsonDataHandlerImpl.convertToIListData(reqBodyMap);
		Map<?, ?> paramMap = listData.getParameterMap();
		
		IListData resultListData = new ListDataImpl();
		
		String svc_id = (String) paramMap.get("SVC_ID");
		String userId = (String) auth.getPrincipal();
		
		if("getMenu".equals(svc_id)) { 
			
			resultListData = menuService.getMenuList(userId);
			UserVo userVo = userService.getUser(userId);
			resultListData.addVariable("userId", userId);
			resultListData.addVariable("password", userVo.getPassword());
			resultListData.addVariable("userName", userVo.getUserName());
			resultListData.addVariable("email", userVo.getEmail());
			resultListData.addVariable("enabled", userVo.getEnabled().toString());
			resultListData.addVariable("auth", userVo.getAuthority());
			resultListData.addVariable("authName", userVo.getAuthorityName());
			logger.debug(">>>>> >>>>> >>>>> userId: " + resultListData.getVariableMap().get("userId")); 
//			if(resultListData.getVariableMap().get("userId").equals(userVo.getUserId())) {
//				pushService.initPush(userVo.getUserId());
//			}
//			resultListData.addVariable("retainedMsg", pushService.initPush());
//			logger.debug(">>>>> >>>>> >>>>> retainedMsg: " + resultListData.getVariableMap().get("retainedMsg")); 
		} else if("getUsrAcnt".equals(svc_id)) { 
//			resultListData = reqAcntServiceImpl.getUsrAcnt(paramMap); 
		} else if("updateUsrAcnt".equals(svc_id)) { 
//			resultListData.addVariable("USR_ID", reqAcntServiceImpl.updateUsrAcnt(listData));
//			reqAcntServiceImpl.updateUsrAcnt(paramMap); 
		}
		
		jsonDataHandlerImpl.flushSuccessJSONResponse(res, jsonDataHandlerImpl.convertToJSONObject(resultListData)); 

	}
	
}
