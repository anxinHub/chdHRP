package com.chd.hrp.ass.entity.inspection;
import java.io.Serializable;


public class AssInspectionDetail implements Serializable{
    private static final long serialVersionUID = 5454155825314635342L;
	
	/**
	 * 集体ID
	 */
	private Long group_id;
	
	/**
	 * 医院ID
	 */
	private Long hos_id;
	
	/**
	 * 账套编码
	 */
	private String copy_code;
    
	/**
	 * 巡检ID
	 */
	private Long ins_id;
	
	/**
	 * 明细ID
	 */
	private Long detail_id;
	
	private String ass_card_no;
	
	private String ass_code;
	
	private String ass_name ;
	
	private String ass_spec;
	
	private String ass_mondl;
	
	private String fac_name ;
	
	private Integer state;
	
	private Integer is_rep;
	
	private Integer is_main;
	
	private String is_result;
	
	private String ins_result;
	
	private String note;
	
	private String ass_seq_no;
	
	
	public String getAss_seq_no() {
		return ass_seq_no;
	}

	public void setAss_seq_no(String ass_seq_no) {
		this.ass_seq_no = ass_seq_no;
	}

	public String getIns_result() {
		return ins_result;
	}

	public void setIns_result(String ins_result) {
		this.ins_result = ins_result;
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

	public Long getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Long detail_id) {
		this.detail_id = detail_id;
	}

	public String getAss_card_no() {
		return ass_card_no;
	}

	public void setAss_card_no(String ass_card_no) {
		this.ass_card_no = ass_card_no;
	}
	
	public String getAss_code() {
		return ass_code;
	}

	public void setAss_code(String ass_code) {
		this.ass_code = ass_code;
	}

	public String getAss_name() {
		return ass_name;
	}

	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}

	public String getAss_spec() {
		return ass_spec;
	}

	public void setAss_spec(String ass_spec) {
		this.ass_spec = ass_spec;
	}

	public String getAss_mondl() {
		return ass_mondl;
	}

	public void setAss_mondl(String ass_mondl) {
		this.ass_mondl = ass_mondl;
	}

	public String getFac_name() {
		return fac_name;
	}

	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getIs_rep() {
		return is_rep;
	}

	public void setIs_rep(Integer is_rep) {
		this.is_rep = is_rep;
	}

	public Integer getIs_main() {
		return is_main;
	}

	public void setIs_main(Integer is_main) {
		this.is_main = is_main;
	}

	public String getIs_result() {
		return is_result;
	}

	public void setIs_result(String is_result) {
		this.is_result = is_result;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getIns_id() {
		return ins_id;
	}

	public void setIns_id(Long ins_id) {
		this.ins_id = ins_id;
	}
}
