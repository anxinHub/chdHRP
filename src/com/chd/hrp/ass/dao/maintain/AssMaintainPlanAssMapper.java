/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.maintain;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanAss;
/**
 * 
 * @Description:
 * 051202 保养计划资产明细
 * @Table:
 * ASS_MAINTAIN_PLAN_ASS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssMaintainPlanAssMapper extends SqlMapper{ 
	/**
	 * @Description 
	 * 添加051202 保养计划资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssMaintainPlanAss(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051202 保养计划资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssMaintainPlanAss(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051202 保养计划资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssMaintainPlanAss(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051202 保养计划资产明细<BR> 
	 * @param  entityMap
	 * @return AssMaintainPlanAss
	 * @throws DataAccessException
	*/
	public int updateBatchAssMaintainPlanAss(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除051202 保养计划资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssMaintainPlanAss(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除051202 保养计划资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssMaintainPlanAss(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划资产明细<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMaintainPlanAss> queryAssMaintainPlanAss(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划资产明细<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMaintainPlanAss> queryAssMaintainPlanAss(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取051202 保养计划资产明细<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssMaintainPlanAss
	 * @throws DataAccessException
	*/
	public AssMaintainPlanAss queryAssMaintainPlanAssByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051202 保养计划资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMaintainPlanAss
	 * @throws DataAccessException
	*/
	public AssMaintainPlanAss queryAssMaintainPlanAssByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051202 保养计划资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMaintainPlanAss>
	 * @throws DataAccessException
	*/
	public List<AssMaintainPlanAss> queryAssMaintainPlanAssExists(Map<String,Object> entityMap)throws DataAccessException;
	
	public Long queryCurrentSequence()throws DataAccessException;
	
	public List<AssMaintainPlanAss> queryByAssMaintainPlanId(Map<String,Object> entityMap)throws DataAccessException;

	public void addOrUpdateAssMaintainDetail(Map<String, Object> detailVo);

	public List<Map<String, Object>> queryAssMaintainPlanAssByUpdate(
			Map<String, Object> buildMapVo1);

	public List<Map<String, Object>> queryAssMaintainPlanAssRec(Map<String, Object> entityMap,
			RowBounds rowBounds);
}
