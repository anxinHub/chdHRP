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
import com.chd.hrp.ass.entity.measure.AssMeasurePlanAss;
import com.chd.hrp.ass.entity.measure.AssMeasureRecDetail;
/**
 * 
 * @Description:
 * 051204 检测计量记录明细
 * @Table:
 * ASS_MEASURE_REC_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssMeasureRecDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加051204 检测计量记录明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssMeasureRecDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051204 检测计量记录明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssMeasureRecDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051204 检测计量记录明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssMeasureRecDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051204 检测计量记录明细<BR> 
	 * @param  entityMap
	 * @return AssMeasureRecDetail
	 * @throws DataAccessException
	*/
	public int updateBatchAssMeasureRecDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除051204 检测计量记录明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssMeasureRecDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除051204 检测计量记录明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssMeasureRecDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051204 检测计量记录明细<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasureRecDetail> queryAssMeasureRecDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051204 检测计量记录明细<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasureRecDetail> queryAssMeasureRecDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取051204 检测计量记录明细<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssMeasureRecDetail
	 * @throws DataAccessException
	*/
	public AssMeasureRecDetail queryAssMeasureRecDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051204 检测计量记录明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMeasureRecDetail
	 * @throws DataAccessException
	*/
	public AssMeasureRecDetail queryAssMeasureRecDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051204 检测计量记录明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMeasureRecDetail>
	 * @throws DataAccessException
	*/
	public List<AssMeasureRecDetail> queryAssMeasureRecDetailExists(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划项目明细<BR>带分页  (专用设备查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasureRecDetail> queryAssMeasuerRecDetailSpecial(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划项目明细<BR>带分页  (一般设备查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasureRecDetail> queryAssMeasuerRecDetailGeneral(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划项目明细<BR>带分页  (房屋及建筑查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasureRecDetail> queryAssMeasuerRecDetailHouse(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划项目明细<BR>带分页  (其他固定资产查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasureRecDetail> queryAssMeasuerRecDetailOther(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<AssMeasureRecDetail> queryByAssMeasureRecId(Map<String,Object> entityMap)throws DataAccessException;
	
}
