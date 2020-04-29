package com.chd.task.mat;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

@Table("MAT_INV_DZBQ")
@PK({ "group_id","hos_id","copy_code","store_id","inv_id" })
public class MatBean {
	@Column("group_id")
	private int group_id;
	
	@Column("hos_id")
	private int hos_id;
	
	@Column("copy_code")
	@ColDefine(notNull = true, width = 50)
	private String copy_code;
	
	@Column("store_id")
	private int store_id;
	
	@Column("inv_id")
	private int inv_id;
	
	@Column("cur_amount")
	private double cur_amount;

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public int getHos_id() {
		return hos_id;
	}

	public void setHos_id(int hos_id) {
		this.hos_id = hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public int getInv_id() {
		return inv_id;
	}

	public void setInv_id(int inv_id) {
		this.inv_id = inv_id;
	}

	public double getCur_amount() {
		return cur_amount;
	}

	public void setCur_amount(double cur_amount) {
		this.cur_amount = cur_amount;
	}
	
	
	
}
