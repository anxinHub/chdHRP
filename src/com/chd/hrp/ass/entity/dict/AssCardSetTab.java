/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;


public class AssCardSetTab implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	
	private Long hos_id;
	
	private String copy_code;
	
	private String ass_naturs;
	
	private String tab_name;
	
	private String tab_id;
	
	private String tab_url;
	
	private Integer seq_no;
	
	private String naturs_name;
	
	private Integer is_view;
	
	private String init_tab_url;
	
	
	

	public String getInit_tab_url() {
		return init_tab_url;
	}

	public void setInit_tab_url(String init_tab_url) {
		this.init_tab_url = init_tab_url;
	}

	public Integer getIs_view() {
		return is_view;
	}

	public void setIs_view(Integer is_view) {
		this.is_view = is_view;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getAss_naturs() {
		return ass_naturs;
	}

	public void setAss_naturs(String ass_naturs) {
		this.ass_naturs = ass_naturs;
	}

	public String getTab_name() {
		return tab_name;
	}

	public void setTab_name(String tab_name) {
		this.tab_name = tab_name;
	}

	public String getTab_id() {
		return tab_id;
	}

	public void setTab_id(String tab_id) {
		this.tab_id = tab_id;
	}

	public String getTab_url() {
		return tab_url;
	}

	public void setTab_url(String tab_url) {
		this.tab_url = tab_url;
	}

	public Integer getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(Integer seq_no) {
		this.seq_no = seq_no;
	}

	public String getNaturs_name() {
		return naturs_name;
	}

	public void setNaturs_name(String naturs_name) {
		this.naturs_name = naturs_name;
	}
	
}