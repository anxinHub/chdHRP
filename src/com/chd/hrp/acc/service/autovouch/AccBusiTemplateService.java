/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.service.autovouch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.autovouch.AccBusiMap;
import com.chd.hrp.acc.entity.autovouch.AccBusiMeta;
import com.chd.hrp.acc.entity.autovouch.AccBusiTemplate;
import com.chd.hrp.acc.entity.autovouch.SysBusiTable;

/**
 * @Title. @Description. 账龄区间表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AccBusiTemplateService {

	/**
	 * 查询自动凭证模板 不分页
	 * 
	 * */
	public String queryAccBusiTemplate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询自动凭证模板 不分页
	 * 
	 * */
	public String queryAccBusiTypeTree(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 添加自动凭证模板
	 * 
	 * */
	public String addAccBusiTemplate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除自动凭证模板
	 * 
	 * */
	public String delBatchAccBusiTemplate(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 查询自动凭证模板 不分页
	 * 
	 * */
	public String queryAccBusiRelaForMetaCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询自动凭证模板 不分页
	 * 
	 * */
	public String queryAccBusiTemplateDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询自动凭证模板 不分页
	 * 
	 * */
	public AccBusiTemplate queryAccBusiTemplateByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询科目 不分页
	 * 
	 * */
	public String queryAccBusiRelaForAccSubj(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询自动凭证模板 不分页
	 * 
	 * */
	public AccBusiMeta queryAccBusiMetaByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 添加自动凭证元素科目配置表
	 * 
	 * */
	public String saveAccBusiMap(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 添加自动凭证元素科目配置表
	 * 
	 * */
	public String delAccBusiMap(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 自动凭证元素科目配置表
	 * 
	 * */
	public AccBusiMap queryAccBusiMapByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 自动凭证元素科目配置表
	 * 
	 * */
	public SysBusiTable querySysBusiTableByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 自动凭证元素科目配置表
	 * 
	 * */
	public String querySysBusiTable(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 自动凭证元素科目配置表
	 * 
	 * */
	public String querySelectSql(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 自动凭证元素科目配置表
	 * 
	 * */
	public Integer maxTypeLevel(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询仓库
	 * 
	 * */
	public String queryAccBusiHosStore(Map<String, Object> entityMap) throws DataAccessException;

	public String queryAccBusiHosResource(Map<String, Object> entityMap) throws DataAccessException;
	
	//带仓库的科目自动设置页面的查询
	public String queryAccBusiRelaForStoreAutoSet(Map<String, Object> map) throws DataAccessException;

	//自动设置 获取科目列表
	public String queryAccSubjForAutoSet(Map<String, Object> map) throws DataAccessException;
	
	//保存类别库房科目对应关系
	public Map<String, Object> saveAccBusiMapByStore(Map<String, Object> map) throws DataAccessException;
	
	//批量保存类别库房科目对应关系
	public Map<String, Object> saveAccBusiMapByStoreBatch(Map<String, Object> map) throws DataAccessException;
}
