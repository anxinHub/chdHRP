/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.measure;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanItem;
import com.chd.hrp.ass.entity.measure.AssMeasureRec;
/**
 * 
 * @Description:
 * 051204 检测计量记录
 * @Table:
 * ASS_MEASURE_REC
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssMeasureRecMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssMeasureRec(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssMeasureRec(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssMeasureRec(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051204 检测计量记录<BR> (审核)
	 * @param  entityMap
	 * @return AssMeasureRec
	 * @throws DataAccessException
	*/
	public int updateBatchAssMeasureRec(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051204 检测计量记录<BR> (消审)
	 * @param  entityMap
	 * @return AssMeasureRec
	 * @throws DataAccessException
	*/
	public int updateBatchAssMeasureRecBack(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 删除051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssMeasureRec(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssMeasureRec(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051204 检测计量记录<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasureRec> queryAssMeasureRec(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051204 检测计量记录<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasureRec> queryAssMeasureRec(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取051204 检测计量记录<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssMeasureRec
	 * @throws DataAccessException
	*/
	public AssMeasureRec queryAssMeasureRecByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051204 检测计量记录<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMeasureRec
	 * @throws DataAccessException
	*/
	public AssMeasureRec queryAssMeasureRecByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051204 检测计量记录<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMeasureRec>
	 * @throws DataAccessException
	*/
	public List<AssMeasureRec> queryAssMeasureRecExists(Map<String,Object> entityMap)throws DataAccessException;
	
	public Long queryCurrentSequence()throws DataAccessException;

	public List<AssMaintainPlanItem> queryAssMaintainPlanItem(
			Map<String, Object> entityMap);

	public List<AssMaintainPlanItem> queryAssMaintainPlanItemSpecial(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<AssMaintainPlanItem> queryAssMaintainPlanItemGeneral(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<AssMaintainPlanItem> queryAssMaintainPlanItemHouse(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<AssMaintainPlanItem> queryAssMaintainPlanItemOther(
			Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * @Description 
	 * 查询结果集卡片检测计量记录<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasureRec> queryAssMeasureRecByCard(Map<String, Object> entityMap);
	/**
	 * @Description 
	 * 查询结果集卡片 检测计量记录<BR>带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasureRec> queryAssMeasureRecByCard(Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryAssMeasureRecByMainBatch(
			Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAssMeasureRecByDetail(
			Map<String, Object> entityMap);

	public Map<String, Object> queryAssMeasureRecByMain(
			Map<String, Object> entityMap);

	public List<String> queryAssMeasureRecState(Map<String, Object> mapVo);
	
}
