package com.chd.hrp.hr.entity.sc;

import java.io.Serializable;
import java.util.List;

public class HrTableDesignQueryColumn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2024037789277098619L;

	private List<HrTableDesignQueryColumnTable> tableData;
	private List<HrTableDesignQueryColumnCondition> conditionData;
	private List<HrTableDesignQueryColumnGroup> groupData;
	private List<HrTableDesignQueryColumnSort> sortData;

	public List<HrTableDesignQueryColumnTable> getTableData() {
		return tableData;
	}

	public void setTableData(List<HrTableDesignQueryColumnTable> tableData) {
		this.tableData = tableData;
	}

	public List<HrTableDesignQueryColumnCondition> getConditionData() {
		return conditionData;
	}

	public void setConditionData(List<HrTableDesignQueryColumnCondition> conditionData) {
		this.conditionData = conditionData;
	}

	public List<HrTableDesignQueryColumnGroup> getGroupData() {
		return groupData;
	}

	public void setGroupData(List<HrTableDesignQueryColumnGroup> groupData) {
		this.groupData = groupData;
	}

	public List<HrTableDesignQueryColumnSort> getSortData() {
		return sortData;
	}

	public void setSortData(List<HrTableDesignQueryColumnSort> sortData) {
		this.sortData = sortData;
	}

}
