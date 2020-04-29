package com.chd.hrp.prm.service.customconf;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

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
public interface PrmCustomTemplateReportConfService {
	
	
	/** 
	 * 查询
	 @param Map<String,Object> entityMap
	 @return List<Map<String,Object>>
	 @throws DataAccessException
	 */
	public String queryPrmCustomTemplateReportConf(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/** 
	 * 按编码查询
	 @param Map<String,Object> entityMap
	 @return PrmCustomTemplateReportConf
	 @throws DataAccessException
	 */
	public PrmCustomTemplateReportConf queryPrmCustomTemplateReportConfByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
	/** 
	 * 按编码查询
	 @param Map<String,Object> entityMap
	 @return List<Map<String,Object>>
	 @throws DataAccessException
	 */
	public List<PrmCustomTemplateReportConf> queryPrmCustomTemplateReportConfListByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 查询菜单
	 @param Map<String,Object> entityMap
	 @return List<Map<String,Object>>
	 @throws DataAccessException
	 */
	public String queryPrmCustomTemplateReportTree(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 添加
	 @param Map<String,Object> entityMap
	 @return int
	 @throws DataAccessException
	 */
	public String addPrmCustomTemplateReportConf(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 修改
	 @param Map<String,Object> entityMap
	 @return int
	 @throws DataAccessException
	 */
	public String updatePrmCustomTemplateReportConf(Map<String,Object> entityMap) throws DataAccessException;
	
	/** 
	 * 删除
	 @param Map<String,Object> entityMap
	 @return int
	 @throws DataAccessException
	 */
	public String deletePrmCustomTemplateReportConf(Map<String,Object> entityMap) throws DataAccessException;


	public String queryPrmTemplateKind(Map<String, Object> mapVo);
	
}
