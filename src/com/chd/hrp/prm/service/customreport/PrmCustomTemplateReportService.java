package com.chd.hrp.prm.service.customreport;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @Title.
 * 自定义报表
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public interface PrmCustomTemplateReportService {
	
	/** 
	 * 查询
	 @param Map<String,Object> entityMap
	 @return String
	 @throws DataAccessException
	 */
	public String queryPrmCustomTemplateReportForParseSql(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 生成
	 @param Map<String,Object> entityMap
	 @return String
	 @throws DataAccessException
	 */
	public String initPrmCustomTemplateReportForParseSql(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 修改
	 @param Map<String,Object> entityMap
	 @return String
	 @throws DataAccessException
	 */
	public String updatePrmCustomTemplateReportForParseSql(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 审核
	 @param Map<String,Object> entityMap
	 @return String
	 @throws DataAccessException
	 */
	public String auditPrmCustomTemplateReportForParseSql(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 反审核
	 @param Map<String,Object> entityMap
	 @return String
	 @throws DataAccessException
	 */
	public String reAuditPrmCustomTemplateReportForParseSql(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/** 
	 * 打印
	 @param Map<String,Object> entityMap
	 @return List<Map<String,Object>>
	 @throws DataAccessException
	 */
	public List<Map<String,Object>> queryPrmCustomTemplateReportForParseSqlPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/** 
	 * 导入
	 @param Map<String,Object> entityMap
	 @return String
	 @throws DataAccessException
	 */
	public String importPrmCustomTemplateReportForParseSql(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 删除
	 @param Map<String,Object> entityMap
	 @return String
	 @throws DataAccessException
	 */
	public String deletePrmCustomTemplateReportForParseSql(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 添加
	 @param Map<String,Object> entityMap
	 @return String
	 @throws DataAccessException
	 */
	public String addPrmCustomTemplateReportForParseSql(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryPrmCustomTemplateReportPrint(
			Map<String, Object> entityMap) throws DataAccessException;
	
}
