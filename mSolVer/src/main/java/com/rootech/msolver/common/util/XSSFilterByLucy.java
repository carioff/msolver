package com.rootech.msolver.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nhncorp.lucy.security.xss.XssFilter;
import com.nhncorp.lucy.security.xss.XssPreventer;

public class XSSFilterByLucy {

	final static Logger log = LoggerFactory.getLogger(XSSFilterByLucy.class);
	
	/**
	 * tag 전체 escape
	 */
    public static String xssPreventer(String dirty) {
    	return XssPreventer.escape(dirty);
    }
    
    /**
	 * html tag 제외 및 lucy-xss-superset.xml 설정된 tag들만 escape 
	 */
    public static String xssFilter(String dirty) {
    	XssFilter filter = XssFilter.getInstance("lucy-xss-superset.xml",true);
    	return filter.doFilter(dirty);
    }
}
