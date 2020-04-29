package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;

/**
 * 经费余额调整
 * @author Administrator
 *
 */
public class BudgProjReAdjDet implements Serializable{
	
	private static final long serialVersionUID = 5454155825314635342L;
   
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String adj_code;
	private Long proj_id;
	private Long source_id;
	private Double remain_adj;
	
	
	
	public Long getSource_id() {
		return source_id;
	}
	public void setSource_id(Long source_id) {
		this.source_id = source_id;
	}
	public Double getRemain_adj() {
		return remain_adj;
	}
	public void setRemain_adj(Double remain_adj) {
		this.remain_adj = remain_adj;
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
	public String getAdj_code() {
		return adj_code;
	}
	public void setAdj_code(String adj_code) {
		this.adj_code = adj_code;
	}
	public Long getProj_id() {
		return proj_id;
	}
	public void setProj_id(Long proj_id) {
		this.proj_id = proj_id;
	}
	
	public void setError_type(String string) {
		// TODO Auto-generated method stub
		
	}
	
}
