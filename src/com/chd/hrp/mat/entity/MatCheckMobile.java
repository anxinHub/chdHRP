package com.chd.hrp.mat.entity;
import java.io.Serializable;
import java.util.Date;
 
public class MatCheckMobile implements Serializable{
	
	 /** serialVersionUID*/  
	private static final long serialVersionUID = 3565735923031568375L;
	
	private Long hos_id;
	private Long group_id;
	private String copy_code;
	private Integer is_com;
	private Long check_id;
	private String check_code;
	private Long detail_id;
	private Long store_id;
	private String store_no;
	private String store_name;
	private Date create_date;
	private Date upload_date;
	private Long inv_id;
	private String inv_no;
	private String inv_code;
	private String inv_name;
	private String inv_model;
	private String unit_name;
	private String fac_name;
	private String sup_name;
	private String batch_no;
	private String bar_code;
	private String location_name;
	private Double price;
	private Double cur_amount;
	private Double chk_amount;
	private Date disinfect_date;
	private Date inva_date;
	private Integer state;
	private String note;
	private Long emp_id;
	public Long getHos_id() {
		return hos_id;
	}
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	public Long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public Integer getIs_com() {
		return is_com;
	}
	public void setIs_com(Integer is_com) {
		this.is_com = is_com;
	}
	public Long getCheck_id() {
		return check_id;
	}
	public void setCheck_id(Long check_id) {
		this.check_id = check_id;
	}
	public String getCheck_code() {
		return check_code;
	}
	public void setCheck_code(String check_code) {
		this.check_code = check_code;
	}
	public Long getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(Long detail_id) {
		this.detail_id = detail_id;
	}
	public Long getStore_id() {
		return store_id;
	}
	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}
	public String getStore_no() {
		return store_no;
	}
	public void setStore_no(String store_no) {
		this.store_no = store_no;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}
	public Long getInv_id() {
		return inv_id;
	}
	public void setInv_id(Long inv_id) {
		this.inv_id = inv_id;
	}
	public String getInv_no() {
		return inv_no;
	}
	public void setInv_no(String inv_no) {
		this.inv_no = inv_no;
	}
	public String getInv_code() {
		return inv_code;
	}
	public void setInv_code(String inv_code) {
		this.inv_code = inv_code;
	}
	public String getInv_name() {
		return inv_name;
	}
	public void setInv_name(String inv_name) {
		this.inv_name = inv_name;
	}
	public String getInv_model() {
		return inv_model;
	}
	public void setInv_model(String inv_model) {
		this.inv_model = inv_model;
	}
	public String getUnit_name() {
		return unit_name;
	}
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}
	public String getFac_name() {
		return fac_name;
	}
	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}
	public String getSup_name() {
		return sup_name;
	}
	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
	}
	public String getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public String getBar_code() {
		return bar_code;
	}
	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}
	public String getLocation_name() {
		return location_name;
	}
	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getCur_amount() {
		return cur_amount;
	}
	public void setCur_amount(Double cur_amount) {
		this.cur_amount = cur_amount;
	}
	public Double getChk_amount() {
		return chk_amount;
	}
	public void setChk_amount(Double chk_amount) {
		this.chk_amount = chk_amount;
	}
	public Date getDisinfect_date() {
		return disinfect_date;
	}
	public void setDisinfect_date(Date disinfect_date) {
		this.disinfect_date = disinfect_date;
	}
	public Date getInva_date() {
		return inva_date;
	}
	public void setInva_date(Date inva_date) {
		this.inva_date = inva_date;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(Long emp_id) {
		this.emp_id = emp_id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bar_code == null) ? 0 : bar_code.hashCode());
		result = prime * result + ((batch_no == null) ? 0 : batch_no.hashCode());
		result = prime * result + ((check_code == null) ? 0 : check_code.hashCode());
		result = prime * result + ((check_id == null) ? 0 : check_id.hashCode());
		result = prime * result + ((chk_amount == null) ? 0 : chk_amount.hashCode());
		result = prime * result + ((copy_code == null) ? 0 : copy_code.hashCode());
		result = prime * result + ((create_date == null) ? 0 : create_date.hashCode());
		result = prime * result + ((cur_amount == null) ? 0 : cur_amount.hashCode());
		result = prime * result + ((detail_id == null) ? 0 : detail_id.hashCode());
		result = prime * result + ((disinfect_date == null) ? 0 : disinfect_date.hashCode());
		result = prime * result + ((emp_id == null) ? 0 : emp_id.hashCode());
		result = prime * result + ((fac_name == null) ? 0 : fac_name.hashCode());
		result = prime * result + ((group_id == null) ? 0 : group_id.hashCode());
		result = prime * result + ((hos_id == null) ? 0 : hos_id.hashCode());
		result = prime * result + ((inv_code == null) ? 0 : inv_code.hashCode());
		result = prime * result + ((inv_id == null) ? 0 : inv_id.hashCode());
		result = prime * result + ((inv_model == null) ? 0 : inv_model.hashCode());
		result = prime * result + ((inv_name == null) ? 0 : inv_name.hashCode());
		result = prime * result + ((inv_no == null) ? 0 : inv_no.hashCode());
		result = prime * result + ((inva_date == null) ? 0 : inva_date.hashCode());
		result = prime * result + ((is_com == null) ? 0 : is_com.hashCode());
		result = prime * result + ((location_name == null) ? 0 : location_name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((store_id == null) ? 0 : store_id.hashCode());
		result = prime * result + ((store_name == null) ? 0 : store_name.hashCode());
		result = prime * result + ((store_no == null) ? 0 : store_no.hashCode());
		result = prime * result + ((sup_name == null) ? 0 : sup_name.hashCode());
		result = prime * result + ((unit_name == null) ? 0 : unit_name.hashCode());
		result = prime * result + ((upload_date == null) ? 0 : upload_date.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatCheckMobile other = (MatCheckMobile) obj;
		if (bar_code == null) {
			if (other.bar_code != null)
				return false;
		} else if (!bar_code.equals(other.bar_code))
			return false;
		if (batch_no == null) {
			if (other.batch_no != null)
				return false;
		} else if (!batch_no.equals(other.batch_no))
			return false;
		if (check_code == null) {
			if (other.check_code != null)
				return false;
		} else if (!check_code.equals(other.check_code))
			return false;
		if (check_id == null) {
			if (other.check_id != null)
				return false;
		} else if (!check_id.equals(other.check_id))
			return false;
		if (chk_amount == null) {
			if (other.chk_amount != null)
				return false;
		} else if (!chk_amount.equals(other.chk_amount))
			return false;
		if (copy_code == null) {
			if (other.copy_code != null)
				return false;
		} else if (!copy_code.equals(other.copy_code))
			return false;
		if (create_date == null) {
			if (other.create_date != null)
				return false;
		} else if (!create_date.equals(other.create_date))
			return false;
		if (cur_amount == null) {
			if (other.cur_amount != null)
				return false;
		} else if (!cur_amount.equals(other.cur_amount))
			return false;
		if (detail_id == null) {
			if (other.detail_id != null)
				return false;
		} else if (!detail_id.equals(other.detail_id))
			return false;
		if (disinfect_date == null) {
			if (other.disinfect_date != null)
				return false;
		} else if (!disinfect_date.equals(other.disinfect_date))
			return false;
		if (emp_id == null) {
			if (other.emp_id != null)
				return false;
		} else if (!emp_id.equals(other.emp_id))
			return false;
		if (fac_name == null) {
			if (other.fac_name != null)
				return false;
		} else if (!fac_name.equals(other.fac_name))
			return false;
		if (group_id == null) {
			if (other.group_id != null)
				return false;
		} else if (!group_id.equals(other.group_id))
			return false;
		if (hos_id == null) {
			if (other.hos_id != null)
				return false;
		} else if (!hos_id.equals(other.hos_id))
			return false;
		if (inv_code == null) {
			if (other.inv_code != null)
				return false;
		} else if (!inv_code.equals(other.inv_code))
			return false;
		if (inv_id == null) {
			if (other.inv_id != null)
				return false;
		} else if (!inv_id.equals(other.inv_id))
			return false;
		if (inv_model == null) {
			if (other.inv_model != null)
				return false;
		} else if (!inv_model.equals(other.inv_model))
			return false;
		if (inv_name == null) {
			if (other.inv_name != null)
				return false;
		} else if (!inv_name.equals(other.inv_name))
			return false;
		if (inv_no == null) {
			if (other.inv_no != null)
				return false;
		} else if (!inv_no.equals(other.inv_no))
			return false;
		if (inva_date == null) {
			if (other.inva_date != null)
				return false;
		} else if (!inva_date.equals(other.inva_date))
			return false;
		if (is_com == null) {
			if (other.is_com != null)
				return false;
		} else if (!is_com.equals(other.is_com))
			return false;
		if (location_name == null) {
			if (other.location_name != null)
				return false;
		} else if (!location_name.equals(other.location_name))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (store_id == null) {
			if (other.store_id != null)
				return false;
		} else if (!store_id.equals(other.store_id))
			return false;
		if (store_name == null) {
			if (other.store_name != null)
				return false;
		} else if (!store_name.equals(other.store_name))
			return false;
		if (store_no == null) {
			if (other.store_no != null)
				return false;
		} else if (!store_no.equals(other.store_no))
			return false;
		if (sup_name == null) {
			if (other.sup_name != null)
				return false;
		} else if (!sup_name.equals(other.sup_name))
			return false;
		if (unit_name == null) {
			if (other.unit_name != null)
				return false;
		} else if (!unit_name.equals(other.unit_name))
			return false;
		if (upload_date == null) {
			if (other.upload_date != null)
				return false;
		} else if (!upload_date.equals(other.upload_date))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MatCheckMobile [hos_id=" + hos_id + ", group_id=" + group_id + ", copy_code=" + copy_code + ", is_com="
				+ is_com + ", check_id=" + check_id + ", check_code=" + check_code + ", detail_id=" + detail_id
				+ ", store_id=" + store_id + ", store_no=" + store_no + ", store_name=" + store_name + ", create_date="
				+ create_date + ", upload_date=" + upload_date + ", inv_id=" + inv_id + ", inv_no=" + inv_no
				+ ", inv_code=" + inv_code + ", inv_name=" + inv_name + ", inv_model=" + inv_model + ", unit_name="
				+ unit_name + ", fac_name=" + fac_name + ", sup_name=" + sup_name + ", batch_no=" + batch_no
				+ ", bar_code=" + bar_code + ", location_name=" + location_name + ", price=" + price + ", cur_amount="
				+ cur_amount + ", chk_amount=" + chk_amount + ", disinfect_date=" + disinfect_date + ", inva_date="
				+ inva_date + ", state=" + state + ", note=" + note + ", emp_id=" + emp_id + "]";
	}
	
	
		
}
