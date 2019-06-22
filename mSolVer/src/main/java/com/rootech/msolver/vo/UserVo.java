package com.rootech.msolver.vo;

import java.io.Serializable;

/**
 * tb_ms_user
 * tb_ms_group
 * tb_ms_authority
 * tb_ms_user_login
 * @author Rootech
 *
 */
public class UserVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5785841287554957075L;

	/* tb_ms_user */
	private String userId;
	private String password;
	private String userName;
	private String email;
	private boolean enabled;
	private String rgstDate;
	private String rgstId;
	private String updDate;
	private String updId;
	
	/* tb_ms_group */
	private String groupId;
	private String groupName;
	
	/* tb_ms_authority */
	private String authority;
	private String authorityName;
	
	/* tb_ms_user_login */
	private String series;
	private String token;
	private String lastUsed;
	private String lastPushSolSiteId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getRgstDate() {
		return rgstDate;
	}
	public void setRgstDate(String rgstDate) {
		this.rgstDate = rgstDate;
	}
	public String getRgstId() {
		return rgstId;
	}
	public void setRgstId(String rgstId) {
		this.rgstId = rgstId;
	}
	public String getUpdDate() {
		return updDate;
	}
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}
	public String getUpdId() {
		return updId;
	}
	public void setUpdId(String updId) {
		this.updId = updId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getAuthorityName() {
		return authorityName;
	}
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLastUsed() {
		return lastUsed;
	}
	public void setLastUsed(String lastUsed) {
		this.lastUsed = lastUsed;
	}
	public String getLastPushSolSiteId() {
		return lastPushSolSiteId;
	}
	public void setLastPushSolSiteId(String lastPushSolSiteId) {
		this.lastPushSolSiteId = lastPushSolSiteId;
	}
	
}
