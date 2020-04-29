/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.disposal;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.disposal.AssDisposalDetail;
import com.chd.hrp.ass.entity.maintain.AssMaintainRecAss;
import com.chd.hrp.ass.entity.maintain.AssMaintainRecItem;
/**
 * 
 * @Description:
 * 051001 资产处置明细
 * @Table:
 * ASS_DISPOSAL_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssDisposalDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加051001 资产处置明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssDisposalDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051001 资产处置明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssDisposalDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051001 资产处置明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssDisposalDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051001 资产处置明细<BR> 
	 * @param  entityMap
	 * @return AssDisposalDetail
	 * @throws DataAccessException
	*/
	public int updateBatchAssDisposalDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除051001 资产处置明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssDisposalDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除051001 资产处置明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssDisposalDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051001 资产处置明细<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDisposalDetail> queryAssDisposalDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051001 资产处置明细<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDisposalDetail> queryAssDisposalDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取051001 资产处置明细<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssDisposalDetail
	 * @throws DataAccessException
	*/
	public AssDisposalDetail queryAssDisposalDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051001 资产处置明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssDisposalDetail
	 * @throws DataAccessException
	*/
	public AssDisposalDetail queryAssDisposalDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051001 资产处置明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssDisposalDetail>
	 * @throws DataAccessException
	*/
	public List<AssDisposalDetail> queryAssDisposalDetailExists(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202 资产处置明细<BR>带分页  (专用设备查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDisposalDetail> queryDisposalDetailSpecial(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 资产处置明细<BR>带分页  (一般设备查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDisposalDetail> queryDisposalDetailGeneral(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202 资产处置明细<BR>带分页  (房屋及建筑查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDisposalDetail> queryDisposalDetailHouse(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202 资产处置明细<BR>带分页  (专用设备查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDisposalDetail> queryDisposalDetailSpecial(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 资产处置明细<BR>带分页  (一般设备查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDisposalDetail> queryDisposalDetailGeneral(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202 资产处置明细<BR>带分页  (房屋及建筑查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDisposalDetail> queryDisposalDetailHouse(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 查询结果集051202 资产处置明细<BR>带分页  (其他固定资产查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDisposalDetail> queryDisposalDetailOther(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 资产处置明细<BR>带分页  (其他固定资产查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDisposalDetail> queryDisposalDetailOther(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AssDisposalDetail> queryByAssDisposalDetailId(Map<String,Object> entityMap)throws DataAccessException;

	public void updateAssDisposalApproveAudit(
			Map<String, Object> entityMap)throws DataAccessException;

	public int initAssDisposalDetail(Map<String, Object> entityMap)throws DataAccessException;
	
	
	
}
