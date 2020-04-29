/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.measure;
import java.util.*;

import org.springframework.dao.DataAccessException;

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
 

public interface AssMeasureRecService { 

	/**
	 * @Description 
	 * 添加051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssMeasureRec(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssMeasureRec(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssMeasureRec(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051204 检测计量记录<BR> (审核)
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssMeasureRec(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051204 检测计量记录<BR> (消审)
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssMeasureRecBack(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssMeasureRec(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssMeasureRec(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssMeasureRec(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssMeasureRec(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象051204 检测计量记录<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
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
    /**
     * 卡片查询计量记录
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	public String queryAssMeasureRecByCard(Map<String, Object> entityMap)throws DataAccessException;

	public String addOrUpdateMeasureRec(Map<String, Object> mapVo);

	public Long queryCurrentSequence() throws DataAccessException;

	Map<String, Object> queryAssMeasureRecPrint(Map<String, Object> entityMap)
			throws DataAccessException;

	public List<String> queryAssMeasureRecState(Map<String, Object> mapVo);

}
