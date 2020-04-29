package com.chd.hrp.cost.service.report;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface CostSuperReportDesignService {

	// 根据报表编码查询报表
	public String querySuperReportByCode(Map<String, Object> map) throws DataAccessException;

	// 根据报表编码查询报表内容
	public String querySuperReportContentByCode(Map<String, Object> map) throws DataAccessException;

	// 根据系统编码查询报表
	public String querySuperReportByMod(Map<String, Object> map) throws DataAccessException;

	// 保存报表
	public String saveSuperReport(Map<String, Object> map) throws DataAccessException;

	// 保存报表内容，大字段
	public String updateSuperReportContent(Map<String, Object> map) throws DataAccessException;

	// 根据报表编码删除报表
	public String deleteSuperReportByCode(Map<String, Object> map) throws DataAccessException;

	// 根据系统编码查询报表元素
	public String querySuperReportEleByMod(Map<String, Object> map) throws DataAccessException;

	// 根据报表元素查询报表元素参数
	public String querySuperReportParaByEle(Map<String, Object> map) throws DataAccessException;

	// 根据报表数据集查询报表数据集参数
	public String querySuperReportParaByDs(Map<String, Object> map) throws DataAccessException;

	// 参数下拉框数据初始化，报表定义通用
	public String querySuperReportParaSelectData(Map<String, Object> map) throws DataAccessException;

	// 查询报表定义中的数据集列表
	public String queryMakeSuperReportDsManager(Map<String, Object> mapVo) throws DataAccessException;

	// 查询报表定义中数据集参数列表
	public List<Map<String,Object>> queryDetailParamForMakeReport(Map<String, Object> mapVo);

	public String updateRepRepDSPara(Map<String, Object> map);
}
