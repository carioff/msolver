package com.rootech.msolver.common.util;

public class CommonMessage {
	
	// error 없을때
	public static final int CODE_NO_ERROR = 0;
	public static final String MSG_OK = "OK";

	// ID=PW 동일로 초기화된 사용자 로그인 체크 172010
	public static final int CODE_LOGIN_INIT = 11;
	public static final String MSG_LOGIN_INIT = "초기화된 계정 로그인입니다.<br>비밀번호 재입력이 필요합니다.";
	
	// db 오류
	public static final int CODE_ERROR_DB = -2;
	public static final String ERRORMSG_DB_ERROR = "DB 오류가 발생했습니다.<br>관리자에게 문의하세요."; // "DB_ERROR";

	// 알수 없는 오류
	public static final int CODE_ERROR_UNDEFINED = -3;
	public static final String ERRORMSG_UNDEFINED = "정상처리 되지 않았습니다. <br> 중복된 데이터 이거나 입력데이터에 문제가 있을 수 있습니다. <br> 문제가 지속되는 경우 관리자에게 연락하시기 바랍니다. "; // "UNDEFINED";
	
	
	public static final int CODE_LOGIN_ERROR = 640;
	public static final String ERRORMSG_LOGIN_ERROR = "로그인 되지 않았습니다."; 
	
	public static final int CODE_INCORRECT_PASSWORD = 640;
	public static final String ERRORMSG_INCORRECT_PASSWORD = "아이디 또는 비밀번호를 다시 확인하세요. <br> 등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력하셨습니다."; 
	//로그인 실패시 계정 및 비밀번호 일치 여부에 대한 일괄적인 오류메세지를 작성하여 정보노출을 최소화 해야 합니다.
	
	public static final int CODE_UNRESISTED_ID = 640;
	public static final String ERRORMSG_UNRESISTED_ID  = "아이디 또는 비밀번호를 다시 확인하세요. <br> 등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력하셨습니다."; 
	//로그인 실패시 계정 및 비밀번호 일치 여부에 대한 일괄적인 오류메세지를 작성하여 정보노출을 최소화 해야 합니다.
	
	public static final int CODE_LOGIN_AUTH_FAIL = 640;
	public static final String ERRORMSG_LOGIN_AUTH_FAIL = "로그인 5회 실패시 일정시간 접속불가합니다."; 
	
	public static final String RESULT = "RESULT";
	public static final String ERRORCODE = "ERRORCODE";
	public static final String ERRORMSG = "ERRORMSG";
	

}
