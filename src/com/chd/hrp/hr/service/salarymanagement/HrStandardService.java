package com.chd.hrp.hr.service.salarymanagement;

import java.util.List;
import java.util.Map;

public interface HrStandardService {

	List<Map<String,Object>> queryStandardTabCodeOption(Map<String, Object> mapVo);

	List<Map<String,Object>> queryStandardColCodeOption(Map<String, Object> mapVo);

	String addStandard(Map<String, Object> mapVo);

	String queryStandard(Map<String, Object> mapVo);

	String deleteStandard(Map<String, Object> mapVo);

	/**
	 * 启用 or 停用--接口停止使用
	 * @param mapVo -- arrid=主键id数组
	 * @return
	 */
	String qiyongStandard(Map<String, Object> mapVo);

	//修改数据回显
	Map<String, Object> queryStandardUpdate(Map<String, Object> mapVo);

	//薪资标准修改
	String updateStandard(Map<String, Object> mapVo);

	//修改明细查询
	String queryStandardCtd(Map<String, Object> mapVo);

	//薪资标准复制
	String addCopyStandard(Map<String, Object> mapVo);

	//岗位级别下拉框加载
	List<Map<String,Object>> queryStandardRankodeOption(Map<String, Object> mapVo);

	//查询表头
	List<Map<String,Object>> queryStandardMaintain(Map<String, Object> mapVo);

	//维护数据新增
	String addMaintain(Map<String, Object> mapVo);

	//维护数据查询
	String queryMaintain(Map<String, Object> mapVo);

	//表数据加载-只查询人员基本情况
	List<Map<String, Object>> queryStandardTabCodeOptionEditable(Map<String, Object> mapVo);

	//关联字段下拉加载--只查询人员基本情况相关数据
	List<Map<String, Object>> queryStandardColCodeOptionEditable(Map<String, Object> mapVo);

}
