package com.rootech.msolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.rootech.msolver.common.dto.IListData;
import com.rootech.msolver.common.dto.ListDataImpl;
import com.rootech.msolver.common.util.JsonDataHandlerImpl;
import com.rootech.msolver.service.LoginService;
import com.rootech.msolver.vo.UserVo;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Resource
	private JsonDataHandlerImpl jsonDataHandlerImpl;

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/")
	public String goLoginPage() throws Exception {
		return "login/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String goLoginPage2() throws Exception {
		return "login/login";
	}
	
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String getBody(HttpServletRequest request) throws IOException {
//		Map<String, String> map = new HashMap<String, String>();
//
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            map.put(key, value);
//        }
//
//        String header = map.toString();
//        String body = null;
//        StringBuilder stringBuilder = new StringBuilder();
//        BufferedReader bufferedReader = null;
// 
//        try {
//            InputStream inputStream = request.getInputStream();
//            if (inputStream != null) {
//                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                char[] charBuffer = new char[128];
//                int bytesRead = -1;
//                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
//                    stringBuilder.append(charBuffer, 0, bytesRead);
//                }
//            } else {
//                stringBuilder.append("");
//            }
//        } catch (IOException ex) {
//            throw ex;
//        } finally {
//            if (bufferedReader != null) {
//                try {
//                    bufferedReader.close();
//                } catch (IOException ex) {
//                    throw ex;
//                }
//            }
//        }
// 
//        body = stringBuilder.toString();
//        return body;
//    }

	
	@RequestMapping(value = "/loginFailure", method = RequestMethod.GET)
	public String loginError(Model model, UserVo loginVo) {

//		model.addAttribute("error", error );
		return "login/loginError";
	}

	@RequestMapping(value = "/reqAcnt", method = RequestMethod.GET)
	public ModelAndView getReqAcntView(HashMap<String, Object> modelMap, HttpServletRequest req) throws Exception {
		return new ModelAndView("login/reqAcnt");
	}

	@RequestMapping(value = "/reqAcnt", method = RequestMethod.POST)
	public void postReqAcnt(@RequestBody(required = false) HashMap<String, Object> reqBodyMap, 
			HttpServletRequest req, HttpServletResponse res, 
			BindingResult bindingResult) throws Exception {
		
		IListData listData = jsonDataHandlerImpl.convertToIListData(reqBodyMap);
		Map<?, ?> paramMap = listData.getParameterMap();
		String svcId = (String) listData.getParameterMap().get("SVC_ID");
		
		IListData resultListData = new ListDataImpl();
		
		if ("addUser".equals(svcId)) {
			resultListData.addVariable("userId", loginService.addUser(listData));
		} else if ("chkIdDup".equals(svcId)) {
			resultListData.addVariable("userId", loginService.chkDupUser(listData));
		}

		jsonDataHandlerImpl.flushSuccessJSONResponse(res, jsonDataHandlerImpl.convertToJSONObject(resultListData));
	}

}
