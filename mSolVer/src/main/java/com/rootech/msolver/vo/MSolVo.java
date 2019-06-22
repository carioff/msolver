package com.rootech.msolver.vo;

import java.io.Serializable;

/**
 * tb_ms_site
 * tb_ms_sol_version
 * tb_ms_site_sol_version
 * @author Rootech
 *
 */
public class MSolVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8247672903667345166L;

	/* tb_ms_site_sol_version */
	private String solSiteId;
	private String siteId;
	private String siteName;
	private String solVersion;
	private String solName;
	private String applyDate;
	private String applyContents;
	private String applyWorker;
	private String rgstDate;
	private String rgstId;
	private String updDate;
	private String updId;
	
	public String getSolSiteId() {
		return solSiteId;
	}
	public void setSolSiteId(String solSiteId) {
		this.solSiteId = solSiteId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSolVersion() {
		return solVersion;
	}
	public void setSolVersion(String solVersion) {
		this.solVersion = solVersion;
	}
	public String getSolName() {
		return solName;
	}
	public void setSolName(String solName) {
		this.solName = solName;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getApplyContents() {
		return applyContents;
	}
	public void setApplyContents(String applyContents) {
		this.applyContents = applyContents;
	}
	public String getApplyWorker() {
		return applyWorker;
	}
	public void setApplyWorker(String applyWorker) {
		this.applyWorker = applyWorker;
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
	
}
