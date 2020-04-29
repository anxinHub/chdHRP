/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.accept;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.accept.AssAcceptDetail;
import com.chd.hrp.ass.entity.accept.AssAcceptMain;
/**
 * 
 * @Description:
 * 050601 资产验收明细
 * @Table:
 * ASS_ACCEPT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssAcceptDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050601 资产验收明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssAcceptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050601 资产验收明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssAcceptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050601 资产验收明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssAcceptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050601 资产验收明细<BR> 
	 * @param  entityMap
	 * @return AssAcceptDetail
	 * @throws DataAccessException
	*/
	public int updateBatchAssAcceptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050601 资产验收明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssAcceptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050601 资产验收明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssAcceptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050601 资产验收明细<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssAcceptDetail> queryAssAcceptDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集050601 资产验收明细<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssAcceptDetail> queryAssAcceptDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050601 资产验收明细<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssAcceptDetail
	 * @throws DataAccessException
	*/
	public AssAcceptDetail queryAssAcceptDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050601 资产验收明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssAcceptDetail
	 * @throws DataAccessException
	*/
	public AssAcceptDetail queryAssAcceptDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	public List<Map<String,Object>> queryAssAcceptDetailByUpdate(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryAssAcceptDetailIn(Map<String,Object> entityMap) throws DataAccessException;

	public List<AssAcceptDetail> queryAssAcceptDetailExists(
			Map<String, Object> entityMap)throws DataAccessException;

	public int initAssAcceptDetail(Map<String, Object> entityMap)throws DataAccessException;

	public int initInstallAssAcceptDetail(Map<String, Object> entityMap)throws DataAccessException;

	public int initAssAcceptDetailBid(Map<String, Object> entityMap)throws DataAccessException;

	public int initInstallAssAcceptDetailBid(Map<String, Object> entityMap)throws DataAccessException;

	public List<AssAcceptDetail> queryByAssAcceptId(
			Map<String, Object> entityMap)throws DataAccessException;
	
	public int deleteBatchAssAcceptInsMap(List<Map<String, Object>> entityMap)throws DataAccessException;

	public int deleteBatchAssAcceptContractMap(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<AssAcceptDetail> queryAssAcceptDetailByUpdateBuild(Map<String, Object> entityMap)throws DataAccessException;
	
}
