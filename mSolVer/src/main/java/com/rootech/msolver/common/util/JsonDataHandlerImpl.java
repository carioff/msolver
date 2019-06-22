package com.rootech.msolver.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rootech.msolver.common.dto.DataHandler;
import com.rootech.msolver.common.dto.IListData;
import com.rootech.msolver.common.dto.ListDataImpl;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Component("jsonDataHandlerImpl")
public class JsonDataHandlerImpl implements DataHandler {

	protected final Logger log = LoggerFactory.getLogger(JsonDataHandlerImpl.class);

	@SuppressWarnings({ "unchecked" })
	public IListData convertToIListDataWithAuth(HashMap<String, Object> jObj, Authentication authentication)
			throws Exception {

		String sessionUserId = (String) authentication.getCredentials();
		if(StringUtils.isEmpty(sessionUserId)) {
			sessionUserId = (String) authentication.getPrincipal();
		}

		log.debug(">>>>> >>>>> >>>>> convertToIListData sessionUserId : {}", sessionUserId);

		Map<String, Object> paramMap = new HashMap<String, Object>();

		if (jObj.get(PARAM_KEY) != null) {
			paramMap = (Map<String, Object>) jObj.get(PARAM_KEY);
		}

		if (sessionUserId != null) {
			paramMap.put("userId", sessionUserId);
		}

		return convertToIListData(jObj, paramMap);
	}

	@Override
	public IListData convertToIListData(HashMap<String, Object> jObj) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		if(jObj.get(PARAM_KEY) != null) {
			paramMap = (Map) jObj.get(PARAM_KEY);
		}
		
		return convertToIListData(jObj, paramMap);
	}

	private IListData convertToIListData(HashMap<String, Object> jObj, Map<String, Object> paramMap)
			throws IOException, JsonParseException, JsonMappingException {

		IListData listData = new ListDataImpl();
		listData.setParameterMap(paramMap);

		Iterator iterator = jObj.entrySet().iterator();
		Entry entry = null;
		String keyName = "";
		List<Map<String, Object>> gridList = null;
		Map<String, Object> eachRowData;
		// 기존 맵 재사용 안함 새로운 필터링된 맵 생성 후 listData에 넣어줌 170208 ckim
		Map<String, Object> filteredEachRowData = new HashMap<String, Object>();// 새로운 필터링된 맵

		Gson gson = new GsonBuilder().create();

		while (iterator.hasNext()) {
			entry = (Entry) iterator.next();
			keyName = String.valueOf(entry.getKey());

			if (!PARAM_KEY.equals(keyName)) {

				// grid 변경 데이터가 아닐때(grid 변경은 화면에서 chg로 끝나는 형태로 넘겨야 한다)
				if (!"chg".equals(keyName.substring(keyName.length() - 3, keyName.length()))) {

					// Value 가 List 인 경우만 Type Casting 함 2016.11.22 bigzero
					if (jObj.get(keyName) instanceof List) {

						// Lucy XssFilter 적용 2017.02.02 ckim
						List tmpList = (List) jObj.get(keyName);// 기존 오브젝트 가져옴
						List<Map<String, Object>> filteredTmpList = new ArrayList<Map<String, Object>>();// New Filtered Obj
						for (int i = 0, n = tmpList.size(); i < n; i++) {
							// 기존 맵 재사용 안함 새로운 필터링된 맵 생성 후 listData에 넣어줌 170208 ckim
							Map<String, Object> tmpMap = (Map<String, Object>) tmpList.get(i);// 기존 오브젝트 맵
							Map<String, Object> filteredTmpMap = new HashMap<String, Object>();// 새로운 필터링된 맵
							String tmpVal = "";
							for (String mapkey : tmpMap.keySet()) {
								tmpVal = "";
//								log.debug(">>>>> key: " + mapkey + " , before lucy value: " + tmpMap.get(mapkey));
								tmpVal = XSSFilterByLucy.xssFilter(String.valueOf(tmpMap.get(mapkey)));
//								log.debug(">>>>> key: " + mapkey + " , after lucy value: " + tmpVal);
								filteredTmpMap.put(mapkey, tmpVal);
							}
							filteredTmpList.add(i, filteredTmpMap);
						}
						listData.setDataList(keyName, filteredTmpList);

//						listData.setDataList(keyName, (List) jObj.get(keyName));
					} else {
						log.warn("jObj value object is not Type of List");
					}

				} else {
					if (!"{}".equals(String.valueOf(entry.getValue()))) {
						ObjectMapper om = new ObjectMapper();
						Map<String, Object> m2 = om.readValue(gson.toJson(entry.getValue()),
								new TypeReference<Map<String, Object>>() {
								});

						gridList = new ArrayList<Map<String, Object>>();

						int i = 0;
						for (String key : m2.keySet()) {
							eachRowData = (Map<String, Object>) m2.get(String.valueOf(key));

							// Lucy XssFilter 적용 2017.02.02 ckim
//							String tmpType = eachRowData.getClass().getName();
							Map<String, Object> tmpMap = (Map<String, Object>) eachRowData;
							String tmpVal = "";
							for (String mapkey : tmpMap.keySet()) {
//								log.debug(">>>>> key: " + mapkey + " , before lucy value: " + tmpMap.get(mapkey));
								tmpVal = XSSFilterByLucy.xssFilter(String.valueOf(tmpMap.get(mapkey)));
//								log.debug(">>>>> key: " + mapkey + " , after lucy value: " + tmpVal);
								filteredEachRowData.put(mapkey, tmpVal);
							}

							gridList.add(i, filteredEachRowData);
							i++;
						}

						listData.setDataList(keyName, gridList);

					} else {
						listData.setDataList(keyName, null);
					}
				}
			}
		}

		return listData;
	}

	@SuppressWarnings({ "static-access" })
	public JSONObject convertToJSONObject(IListData listData) throws Exception {

		log.debug(">>>>> >>>>> >>>>> convertToJSONObject");

		JSONObject jsonObj = new JSONObject();

		if (null != listData) {
			Iterator<Entry<Object, Object>> entryIter = listData.entrySet().iterator();
			while (entryIter.hasNext()) {
				Entry<Object, Object> entry = entryIter.next();
				log.debug(">>>>> >>>>> >>>>> model.put [" + entry.getKey() + "]");
				JSONSerializer js = new JSONSerializer();
				if (js != null) {
					jsonObj.put(entry.getKey(), js.toJSON(entry.getValue()));
				}
//				jsonObj.put(entry.getKey(), new Gson().toJson(entry.getValue()));
			}
		}

		return jsonObj;
	}

	public void flushErrorJSONResponse(HttpServletResponse res, int msgNo, String msg) throws Exception {
		flushJSONResponse(res, null, msgNo, msg);
	}

	public void flushNoticeJSONResponse(HttpServletResponse res, int msgNo, String msg) throws Exception {
		flushJSONResponse(res, null, msgNo, msg);
	}

	public void flushSuccessJSONResponse(HttpServletResponse res, JSONObject jsonObj) throws Exception {
		flushJSONResponse(res, jsonObj, CommonMessage.CODE_NO_ERROR, CommonMessage.MSG_OK);
	}

	public void flushJSONResponse(HttpServletResponse res, int msgNo, String msg) throws Exception {
		flushJSONResponse(res, null, msgNo, msg);
	}

	public void flushJSONResponse(HttpServletResponse res, JSONObject jsonObj, int msgNo, String msg) throws Exception {
		JSONObject jObj = jsonObj;
		if (jObj == null) {
			jObj = new JSONObject();
		}

		res.setContentType("text/html;charset=UTF-8");

		PrintWriter pw = res.getWriter();
		jObj.put("RESULT", "{ERRORCODE:" + msgNo + "; ERRORMSG:'" + msg + "';}");
		pw.print(jObj);
		pw.flush();
	}


	@SuppressWarnings({ "static-access" })
	public Object convertMapToObject(Map map, Object objClass) {
		String keyAttribute = null;
		String setMethodString = "set";
		String methodString = null;
		Iterator itr = map.keySet().iterator();
		while (itr.hasNext()) {
			keyAttribute = (String) itr.next();
			methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
			try {
				Method[] methods = objClass.getClass().getDeclaredMethods();
				for (int i = 0; i <= methods.length - 1; i++) {
					if (methodString.equals(methods[i].getName())) {
						log.debug("convertMapToObject invoke : {}",  methodString);
						methods[i].invoke(objClass, map.get(keyAttribute));
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return objClass;

	}

	public Map<?, ?> ConverObjectToMap(Object obj) {
		try {
			// Field[] fields = obj.getClass().getFields(); //private field는 나오지 않음.
			Field[] fields = obj.getClass().getDeclaredFields();
			Map resultMap = new HashMap<Object, Object>();
			for (int i = 0; i <= fields.length - 1; i++) {
				fields[i].setAccessible(true);
				resultMap.put(fields[i].getName(), fields[i].get(obj));
			}
			return resultMap;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
