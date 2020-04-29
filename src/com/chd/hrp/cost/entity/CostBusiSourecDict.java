/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.entity;

import java.io.Serializable;

/**
 * @Title. @Description. 科室成本明细数据表_医辅分摊<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class CostBusiSourecDict implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

    private long busi_data_source_type;
    
    private String busi_data_source_code;
    
    private String busi_data_source_name;
    
    private long is_sys;

	public long getBusi_data_source_type() {
		return busi_data_source_type;
	}

	public String getBusi_data_source_code() {
		return busi_data_source_code;
	}

	public String getBusi_data_source_name() {
		return busi_data_source_name;
	}

	public void setBusi_data_source_type(long busi_data_source_type) {
		this.busi_data_source_type = busi_data_source_type;
	}

	public void setBusi_data_source_code(String busi_data_source_code) {
		this.busi_data_source_code = busi_data_source_code;
	}

	public void setBusi_data_source_name(String busi_data_source_name) {
		this.busi_data_source_name = busi_data_source_name;
	}

	public long getIs_sys() {
		return is_sys;
	}

	public void setIs_sys(long is_sys) {
		this.is_sys = is_sys;
	}
}