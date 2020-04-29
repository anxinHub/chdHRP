/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.bid;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.bid.AssBidDetail;
import com.chd.hrp.ass.entity.bid.AssBidMain;
/**
 * 
 * @Description:
 * 050401 招标资产明细
 * @Table:
 * ASS_BID_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssBidDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssBidDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssBidDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssBidDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return AssBidDetail
	 * @throws DataAccessException
	*/
	public int updateBatchAssBidDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssBidDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssBidDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050401 招标资产明细<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBidDetail> queryAssBidDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050401 招标资产明细<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBidDetail> queryAssBidDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050401 招标资产明细<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssBidDetail
	 * @throws DataAccessException
	*/
	public AssBidDetail queryAssBidDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050401 招标资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBidDetail
	 * @throws DataAccessException
	*/
	public AssBidDetail queryAssBidDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取050401 招标资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBidDetail>
	 * @throws DataAccessException
	*/
	public List<AssBidDetail> queryAssBidDetailExists(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssBidDetail> queryAssBidDetailByList(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssBidMain> queryAssBidMainByID(Map<String,Object> entityMap)throws DataAccessException;
	
}
