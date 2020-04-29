/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.bid;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.bid.AssBidProjectDetail;
/**
 * 
 * @Description:
 * 050401 投标立项明细
 * @Table:
 * ASS_BID_DETAIL
 * @Author: brian
 * @email:  brian@e-tonggroup.com 
 * @Version: 1.0
 */
 

public interface AssBidProjectDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050401 投标立项明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssBidProjectDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050401 投标立项明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssBidProjectDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050401 投标立项明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssBidProjectDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050401 投标立项明细<BR> 
	 * @param  entityMap
	 * @return AssBidDetail
	 * @throws DataAccessException
	*/
	public int updateBatchAssBidProjectDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050401 投标立项明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssBidProjectDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050401 投标立项明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssBidProjectDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050401 投标立项明细<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBidProjectDetail> queryAssBidProjectDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050401 投标立项明细<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBidProjectDetail> queryAssBidProjectDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050401 投标立项明细<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssBidDetail
	 * @throws DataAccessException
	*/
	public AssBidProjectDetail queryAssBidProjectDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050401 投标立项明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBidDetail
	 * @throws DataAccessException
	*/
	public AssBidProjectDetail queryAssBidProjectDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取050401 投标立项明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBidDetail>
	 * @throws DataAccessException
	*/
	public List<AssBidProjectDetail> queryAssBidProjectDetailExists(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public List<AssBidProjectDetail> queryAssBidProjectByID(Map<String,Object> entityMap)throws DataAccessException;
	
}
