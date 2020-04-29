package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AphiTemplateDataService {
	
	/**
	 * 添加
	 */
	public String addTemplateData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 树菜单
	 */
	public String queryAphiTemplateDataTree(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 按编码查询 主表
	 */
	public String queryAphiTemplateDataByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 按模板编码查询 明细表
	 */
	public String queryAphiTemplateDetailDataByTemplateCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除 模板主表
	 */
	public String deleteAphiTemplateData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除 模板明细表
	 */
	public String deleteBatchAphiTemplateDetailData(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 查询 动态列
	 */
	public String queryAphiTemplateDataColumnHeads(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 自定义采集数据查询
	 */
	public String queryHpmTemplateDataForParseData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 自定义采集数据修改
	 */
	public String updateHpmTemplateDataForParseData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 自定义采集数据删除
	 */
	public String deleteHpmTemplateDataForParseData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 自定义采集数据生成
	 */
	public String initHpmTemplateDataForParseData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 自定义采集数据查询 - 打印
	 */
	public List<Map<String, Object>> queryHpmTemplateDataForParseDataPrint(Map<String, Object> entityMap) throws DataAccessException;
}
