package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

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

public interface AphiCustomTemplateReportMapper extends SqlMapper {
	
	
	/** 
	 * 查询
	 @param Map<String,Object> entityMap
	 @return List<Map<String,Object>>
	 @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAphiCustomTemplateReportForParseSql(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 分页 查询
	 @param Map<String,Object> entityMap
	 @return List<Map<String,Object>>
	 @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAphiCustomTemplateReportForParseSql(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/** 
	 * 生成
	 @param Map<String,Object> entityMap
	 @return int
	 @throws DataAccessException
	 */
	public int initAphiCustomTemplateReportForParseSql(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 修改
	 @param Map<String,Object> entityMap
	 @return int
	 @throws DataAccessException
	 */
	public int updateAphiCustomTemplateReportForParseSql(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 审核
	 @param Map<String,Object> entityMap
	 @return int
	 @throws DataAccessException
	 */
	public int auditAphiCustomTemplateReportForParseSql(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 反审核
	 @param Map<String,Object> entityMap
	 @return int
	 @throws DataAccessException
	 */
	public int reAuditAphiCustomTemplateReportForParseSql(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 删除
	 @param Map<String,Object> entityMap
	 @return int
	 @throws DataAccessException
	 */
	public int deleteAphiCustomTemplateReportForParseSql(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 添加
	 @param Map<String,Object> entityMap
	 @return int
	 @throws DataAccessException
	 */
	public int addAphiCustomTemplateReportForParseSql(Map<String,Object> entityMap) throws DataAccessException;
}
