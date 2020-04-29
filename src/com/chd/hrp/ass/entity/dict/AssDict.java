
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050102 资产字典
 * @Table:
 * ASS_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
 

public class AssDict implements Serializable {

	
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
	 * 资产ID
	 */
	private Long ass_id;
	
	/**
	 * 资产编码
	 */
	private String ass_code;
	private String note;
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	/**
	 * 资产名称
	 */
	private String ass_name;
	
	/**
	 * 类别编码
	 */
	private String ass_type_id;
	
	private String ass_type_code;
	/**
	 * 类别名称
	 */
	private String ass_type_name;
	
	/**
	 * 财务分类编码
	 */
	private String acc_type_code;
	/**
	 * 财务分类名称
	 */
	private String acc_type_name;
	
	/**
	 * 单位
	 */
	private String ass_unit;
	/**
	 * 单位名称
	 */
	private String ass_unit_name;
	
	/**
	 * 是否计量
	 */
	private Integer is_measure;
	
	/**
	 * 是否折旧
	 */
	private Integer is_depre;
	
	/**
	 * 折旧方法编码
	 */
	private String ass_depre_code;
	/**
	 * 折旧方法名称
	 */
	private String ass_depre_name;
	
	/**
	 * 折旧年限
	 */
	private Integer depre_years;
	private Double price;
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}


	/**
	 * 是否停用
	 */
	private Integer is_stop;
	private Integer is_ins;
	private Integer is_accept;
	private Integer is_check;
	
	/**
	 * 规格
	 */
	private String ass_spec;
	
	/**
	 * 型号
	 */
	private String ass_model;
	
	/**
	 * 生产厂商ID
	 */
	private String fac_id;
	/**
	 * 生产厂商名称
	 */
	private String fac_id_name;
	
	/**
	 * 生产厂商NO
	 */
	private String fac_no;
	/**
	 * 生产厂商变更名称
	 */
	private String fac_no_name;
	
	private String fac_code;
	
	private String fac_name;
	
	/**
	 * 主要供应商ID
	 */
	private String ven_id;
	
	/**
	 * 主要供应商NO
	 */
	private String ven_no;
	
	private String ven_code;
	
	private String ven_name;
	/**
	 * 主要供应商名称
	 */
	private String ven_id_name;
	
	/**
	 * 主要供应商变更名称
	 */
	private String ven_no_name;
	
	/**
	 * 资产用途
	 */
	private String usage_code;
	/**
	 * 资产用途
	 */
	private String usage_name;
	
	/**
	 * 国标码
	 */
	private String gb_code;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	
	private String ass_brand;
	
	private String is_bar;
	
	private String bar_type;
	
	private String is_manage_depre;
	
	private String manage_depr_method;
	
	private String manage_depre_amount;
	
	private String manage_depr_method_name;
	
	private String reg_no;
	
	private String gb_name;
	
	
	private Integer measure_type;
	
	
	private Integer is_s_measure;
	
	private String measure_king_code;
	
	private String measure_king_name;
	
	private Integer is_fae;
	
	private String common_name;
	
	private String type_code;
	
	private String type_name;
	
	
	

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public String getCommon_name() {
		return common_name;
	}

	public void setCommon_name(String common_name) {
		this.common_name = common_name;
	}
	

	public Integer getIs_fae() {
		return is_fae;
	}

	public void setIs_fae(Integer is_fae) {
		this.is_fae = is_fae;
	}

	public String getMeasure_king_code() {
		return measure_king_code;
	}

	public void setMeasure_king_code(String measure_king_code) {
		this.measure_king_code = measure_king_code;
	}

	public String getMeasure_king_name() {
		return measure_king_name;
	}

	public void setMeasure_king_name(String measure_king_name) {
		this.measure_king_name = measure_king_name;
	}

	public Integer getMeasure_type() {
		return measure_type;
	}

	public void setMeasure_type(Integer measure_type) {
		this.measure_type = measure_type;
	}

	public Integer getIs_s_measure() {
		return is_s_measure;
	}

	public void setIs_s_measure(Integer is_s_measure) {
		this.is_s_measure = is_s_measure;
	}

	public String getGb_name() {
		return gb_name;
	}

	public void setGb_name(String gb_name) {
		this.gb_name = gb_name;
	}

	public String getReg_no() {
		return reg_no;
	}

	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}

	public String getManage_depr_method_name() {
		return manage_depr_method_name;
	}

	public void setManage_depr_method_name(String manage_depr_method_name) {
		this.manage_depr_method_name = manage_depr_method_name;
	}

	public String getAss_brand() {
		return ass_brand;
	}

	public void setAss_brand(String ass_brand) {
		this.ass_brand = ass_brand;
	}

	public String getIs_bar() {
		return is_bar;
	}

	public void setIs_bar(String is_bar) {
		this.is_bar = is_bar;
	}

	public String getBar_type() {
		return bar_type;
	}

	public void setBar_type(String bar_type) {
		this.bar_type = bar_type;
	}

	public String getIs_manage_depre() {
		return is_manage_depre;
	}

	public void setIs_manage_depre(String is_manage_depre) {
		this.is_manage_depre = is_manage_depre;
	}

	public String getManage_depr_method() {
		return manage_depr_method;
	}

	public void setManage_depr_method(String manage_depr_method) {
		this.manage_depr_method = manage_depr_method;
	}

	public String getManage_depre_amount() {
		return manage_depre_amount;
	}

	public void setManage_depre_amount(String manage_depre_amount) {
		this.manage_depre_amount = manage_depre_amount;
	}


	/**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	public Integer getIs_ins() {
		return is_ins;
	}

	public void setIs_ins(Integer is_ins) {
		this.is_ins = is_ins;
	}

	public Integer getIs_accept() {
		return is_accept;
	}

	public void setIs_accept(Integer is_accept) {
		this.is_accept = is_accept;
	}

	public Integer getIs_check() {
		return is_check;
	}

	public void setIs_check(Integer is_check) {
		this.is_check = is_check;
	}

	public String getFac_code() {
		return fac_code;
	}

	public void setFac_code(String fac_code) {
		this.fac_code = fac_code;
	}

	public String getFac_name() {
		return fac_name;
	}

	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}

	public String getVen_code() {
		return ven_code;
	}

	public void setVen_code(String ven_code) {
		this.ven_code = ven_code;
	}

	public String getVen_name() {
		return ven_name;
	}

	public void setVen_name(String ven_name) {
		this.ven_name = ven_name;
	}

	public String getAss_type_code() {
	return ass_type_code;
	}
	
	public void setAss_type_code(String ass_type_code) {
		this.ass_type_code = ass_type_code;
	}

	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 医院ID
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 医院ID
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 资产ID
	* @param value 
	*/
	public void setAss_id(Long value) {
		this.ass_id = value;
	}
	
	/**
	* 获取 资产ID
	* @return Long
	*/
	public Long getAss_id() {
		return this.ass_id;
	}
	/**
	* 设置 资产编码
	* @param value 
	*/
	public void setAss_code(String value) {
		this.ass_code = value;
	}
	
	/**
	* 获取 资产编码
	* @return String
	*/
	public String getAss_code() {
		return this.ass_code;
	}
	/**
	* 设置 资产名称
	* @param value 
	*/
	public void setAss_name(String value) {
		this.ass_name = value;
	}
	
	/**
	* 获取 资产名称
	* @return String
	*/
	public String getAss_name() {
		return this.ass_name;
	}
	/**
	* 设置 类别编码
	* @param value 
	*/
	public void setAss_type_id(String value) {
		this.ass_type_id = value;
	}
	
	/**
	* 获取 类别编码
	* @return String
	*/
	public String getAss_type_id() {
		return this.ass_type_id;
	}
	/**
	* 设置 财务分类编码
	* @param value 
	*/
	public void setAcc_type_code(String value) {
		this.acc_type_code = value;
	}
	
	/**
	* 获取 财务分类编码
	* @return String
	*/
	public String getAcc_type_code() {
		return this.acc_type_code;
	}
	/**
	* 设置 单位
	* @param value 
	*/
	public void setAss_unit(String value) {
		this.ass_unit = value;
	}
	
	/**
	* 获取 单位
	* @return String
	*/
	public String getAss_unit() {
		return this.ass_unit;
	}
	/**
	* 设置 是否计量
	* @param value 
	*/
	public void setIs_measure(Integer value) {
		this.is_measure = value;
	}
	
	/**
	* 获取 是否计量
	* @return Integer
	*/
	public Integer getIs_measure() {
		return this.is_measure;
	}
	/**
	* 设置 是否折旧
	* @param value 
	*/
	public void setIs_depre(Integer value) {
		this.is_depre = value;
	}
	
	/**
	* 获取 是否折旧
	* @return Integer
	*/
	public Integer getIs_depre() {
		return this.is_depre;
	}
	/**
	* 设置 折旧方法编码
	* @param value 
	*/
	public void setAss_depre_code(String value) {
		this.ass_depre_code = value;
	}
	
	/**
	* 获取 折旧方法编码
	* @return String
	*/
	public String getAss_depre_code() {
		return this.ass_depre_code;
	}
	/**
	* 设置 折旧年限
	* @param value 
	*/
	public void setDepre_years(Integer value) {
		this.depre_years = value;
	}
	
	/**
	* 获取 折旧年限
	* @return Integer
	*/
	public Integer getDepre_years() {
		return this.depre_years;
	}
	/**
	* 设置 是否停用
	* @param value 
	*/
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	
	/**
	* 获取 是否停用
	* @return Integer
	*/
	public Integer getIs_stop() {
		return this.is_stop;
	}
	/**
	* 设置 规格
	* @param value 
	*/
	public void setAss_spec(String value) {
		this.ass_spec = value;
	}
	
	/**
	* 获取 规格
	* @return String
	*/
	public String getAss_spec() {
		return this.ass_spec;
	}
	/**
	* 设置 型号
	* @param value 
	*/
	public void setAss_model(String value) {
		this.ass_model = value;
	}
	
	/**
	* 获取 型号
	* @return String
	*/
	public String getAss_model() {
		return this.ass_model;
	}
	/**
	* 设置 生产厂商ID
	* @param value 
	*/
	public void setFac_id(String value) {
		this.fac_id = value;
	}
	
	/**
	* 获取 生产厂商ID
	* @return String
	*/
	public String getFac_id() {
		return this.fac_id;
	}
	/**
	* 设置 生产厂商NO
	* @param value 
	*/
	public void setFac_no(String value) {
		this.fac_no = value;
	}
	
	/**
	* 获取 生产厂商NO
	* @return String
	*/
	public String getFac_no() {
		return this.fac_no;
	}
	/**
	* 设置 主要供应商ID
	* @param value 
	*/
	public void setVen_id(String value) {
		this.ven_id = value;
	}
	
	/**
	* 获取 主要供应商ID
	* @return String
	*/
	public String getVen_id() {
		return this.ven_id;
	}
	/**
	* 设置 主要供应商NO
	* @param value 
	*/
	public void setVen_no(String value) {
		this.ven_no = value;
	}
	
	/**
	* 获取 主要供应商NO
	* @return String
	*/
	public String getVen_no() {
		return this.ven_no;
	}
	/**
	* 设置 资产用途
	* @param value 
	*/
	public void setUsage_code(String value) {
		this.usage_code = value;
	}
	
	/**
	* 获取 资产用途
	* @return String
	*/
	public String getUsage_code() {
		return this.usage_code;
	}
	/**
	* 设置 国标码
	* @param value 
	*/
	public void setGb_code(String value) {
		this.gb_code = value;
	}
	
	/**
	* 获取 国标码
	* @return String
	*/
	public String getGb_code() {
		return this.gb_code;
	}
	/**
	* 设置 拼音码
	* @param value 
	*/
	public void setSpell_code(String value) {
		this.spell_code = value;
	}
	
	/**
	* 获取 拼音码
	* @return String
	*/
	public String getSpell_code() {
		return this.spell_code;
	}
	/**
	* 获取 五笔码
	* @return String
	*/
    public String getWbx_code() {
    	return wbx_code;
    }

    /**
	* 设置 五笔码
	* @param value 
	*/
    public void setWbx_code(String wbx_code) {
    	this.wbx_code = wbx_code;
    }
	
	/**
	 * 设置 导入验证信息
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	/**
	 * 获取 导入验证信息
	 */
	public String getError_type() {
		return error_type;
	}

	
    /**
     * 获取 ass_type_name
     * @return ass_type_name
     */
    public String getAss_type_name() {
    	return ass_type_name;
    }

	
    /**
     * 设置 ass_type_name
     * @param ass_type_name 
     */
    public void setAss_type_name(String ass_type_name) {
    	this.ass_type_name = ass_type_name;
    }

	
    /**
     * 获取 acc_type_name
     * @return acc_type_name
     */
    public String getAcc_type_name() {
    	return acc_type_name;
    }

	
    /**
     * 设置 acc_type_name
     * @param acc_type_name 
     */
    public void setAcc_type_name(String acc_type_name) {
    	this.acc_type_name = acc_type_name;
    }

	
    /**
     * 获取 ass_unit_name
     * @return ass_unit_name
     */
    public String getAss_unit_name() {
    	return ass_unit_name;
    }

	
    /**
     * 设置 ass_unit_name
     * @param ass_unit_name 
     */
    public void setAss_unit_name(String ass_unit_name) {
    	this.ass_unit_name = ass_unit_name;
    }

	
    /**
     * 获取 ass_depre_name
     * @return ass_depre_name
     */
    public String getAss_depre_name() {
    	return ass_depre_name;
    }

	
    /**
     * 设置 ass_depre_name
     * @param ass_depre_name 
     */
    public void setAss_depre_name(String ass_depre_name) {
    	this.ass_depre_name = ass_depre_name;
    }

	
    /**
     * 获取 fac_id_name
     * @return fac_id_name
     */
    public String getFac_id_name() {
    	return fac_id_name;
    }

	
    /**
     * 设置 fac_id_name
     * @param fac_id_name 
     */
    public void setFac_id_name(String fac_id_name) {
    	this.fac_id_name = fac_id_name;
    }

	
    /**
     * 获取 fac_no_name
     * @return fac_no_name
     */
    public String getFac_no_name() {
    	return fac_no_name;
    }

	
    /**
     * 设置 fac_no_name
     * @param fac_no_name 
     */
    public void setFac_no_name(String fac_no_name) {
    	this.fac_no_name = fac_no_name;
    }

	
    /**
     * 获取 ven_id_name
     * @return ven_id_name
     */
    public String getVen_id_name() {
    	return ven_id_name;
    }

	
    /**
     * 设置 ven_id_name
     * @param ven_id_name 
     */
    public void setVen_id_name(String ven_id_name) {
    	this.ven_id_name = ven_id_name;
    }

	
    /**
     * 获取 ven_no_name
     * @return ven_no_name
     */
    public String getVen_no_name() {
    	return ven_no_name;
    }

	
    /**
     * 设置 ven_no_name
     * @param ven_no_name 
     */
    public void setVen_no_name(String ven_no_name) {
    	this.ven_no_name = ven_no_name;
    }

	
    /**
     * 获取 usage_name
     * @return usage_name
     */
    public String getUsage_name() {
    	return usage_name;
    }

	
    /**
     * 设置 usage_name
     * @param usage_name 
     */
    public void setUsage_name(String usage_name) {
    	this.usage_name = usage_name;
    }
	
}