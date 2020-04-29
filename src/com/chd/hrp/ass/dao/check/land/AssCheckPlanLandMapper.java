/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.check.land;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 051101 盘点任务单(土地)
 * @Table:
 * ASS_CHECK_PLAN_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCheckPlanLandMapper extends SqlMapper{
	/**
	 * @Description 
	 * 生成数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addGenerate(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 生成数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addGenerateDetail(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 盘点单树形展示
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryByTree(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加盘点单主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addAssCheckLand(Map<String, Object> entityMap) throws DataAccessException;
}
