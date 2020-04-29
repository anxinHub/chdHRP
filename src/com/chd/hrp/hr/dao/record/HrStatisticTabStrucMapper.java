/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.record;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.Parameter;
import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 简单统计表构建设置
 * @Table:
 * DIC_MARRIAGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrStatisticTabStrucMapper extends SqlMapper{
	/**
	 * 查询简单统计表设置表构建
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryHrStatisticSetTab(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量添加简单统计表设置表构建
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int insertHrStatisticSetTab(List<Parameter> entityMapList) throws DataAccessException;
	
	/**
	 * 删除简单统计表设置表构建
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteHrStatisticSetTab(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量删除简单统计表设置表构建
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteBatchHrStatisticSetTab(List<Map<String, Object>> entityMapList) throws DataAccessException;
}
