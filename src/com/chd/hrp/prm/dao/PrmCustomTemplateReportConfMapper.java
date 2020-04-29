package com.chd.hrp.prm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.prm.entity.PrmCustomTemplateReportConf;

/**
 * 
 * @Title.
 * 自定义报表-模板配置
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface PrmCustomTemplateReportConfMapper extends SqlMapper {
	
	
	/** 
	 * 查询
	 @param Map<String,Object> entityMap
	 @return List<Map<String,Object>>
	 @throws DataAccessException
	 */
	public List<PrmCustomTemplateReportConf> queryPrmCustomTemplateReportConf(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/** 
	 * 根据编码查询
	 @param Map<String,Object> entityMap
	 @return List<Map<String,Object>>
	 @throws DataAccessException
	 */
	public PrmCustomTemplateReportConf queryPrmCustomTemplateReportConfByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 查询菜单
	 @param Map<String,Object> entityMap
	 @return List<Map<String,Object>>
	 @throws DataAccessException
	 */
	public List<Map<String,Object>> queryPrmCustomTemplateReportTree(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 添加
	 @param Map<String,Object> entityMap
	 @return int
	 @throws DataAccessException
	 */
	public int addPrmCustomTemplateReportConf(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 批量添加
	 @param Map<String,Object> entityMap
	 @return int
	 @throws DataAccessException
	 */
	public int addBatchPrmCustomTemplateReportConf(List<Map<String,Object>> entityList) throws DataAccessException;
	
	/** 
	 * 修改
	 @param Map<String,Object> entityMap
	 @return int
	 @throws DataAccessException
	 */
	public int updatePrmCustomTemplateReportConf(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 删除
	 @param Map<String,Object> entityMap
	 @return int
	 @throws DataAccessException
	 */
	public int deletePrmCustomTemplateReportConf(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 查询 模板分类
	 @param Map<String,Object> entityMap
	 @return int
	 @throws DataAccessException
	 */
	public List<Map<String,Object>> queryPrmTemplateKindList(Map<String,Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryPrmTemplateKind(Map<String, Object> entityMap);
}
