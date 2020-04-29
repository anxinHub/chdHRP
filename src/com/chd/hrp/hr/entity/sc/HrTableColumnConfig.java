package com.chd.hrp.hr.entity.sc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HrTableColumnConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5784603183728695334L;

	private List<HrTableColumnGridConfig> gridSetData;
	private List<HrTableColumnFormConfig> searchSetData;;
	private List<HrTableColumnFormConfig> formSetData;
	
	public HrTableColumnConfig(){
		this.gridSetData = new ArrayList<HrTableColumnGridConfig>();
		this.searchSetData = new ArrayList<HrTableColumnFormConfig>();
		this.formSetData = new ArrayList<HrTableColumnFormConfig>();
	}
	
	public List<HrTableColumnGridConfig> getGridSetData() {
		return gridSetData;
	}
	public void setGridSetData(List<HrTableColumnGridConfig> gridSetData) {
		this.gridSetData = gridSetData;
	}
	public List<HrTableColumnFormConfig> getSearchSetData() {
		return searchSetData;
	}
	public void setSearchSetData(List<HrTableColumnFormConfig> searchSetData) {
		this.searchSetData = searchSetData;
	}
	public List<HrTableColumnFormConfig> getFormSetData() {
		return formSetData;
	}
	public void setFormSetData(List<HrTableColumnFormConfig> formSetData) {
		this.formSetData = formSetData;
	}
	
}
