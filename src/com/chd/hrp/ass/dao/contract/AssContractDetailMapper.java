/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.contract;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.apply.AssApplyDeptDetail;
import com.chd.hrp.ass.entity.contract.AssContractDetail;
/**
 * 
 * @Description:
 * 050501 资产合同明细
 * @Table:
 * ASS_CONTRACT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


public interface AssContractDetailMapper extends SqlMapper{
	
	public List<AssContractDetail> queryByAssContractDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssContractDetail> queryAssContractDetailList(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 添加050501 资产合同明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssContractDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050501 资产合同明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssContractDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050501 资产合同明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssContractDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050501 资产合同明细<BR> 
	 * @param  entityMap
	 * @return AssContractDetail
	 * @throws DataAccessException
	*/
	public int updateBatchAssContractDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050501 资产合同明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssContractDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050501 资产合同明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssContractDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050501 资产合同明细<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssContractDetail> queryAssContractDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050501 资产合同明细<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssContractDetail> queryAssContractDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050501 资产合同明细<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssContractDetail
	 * @throws DataAccessException
	*/
	public AssContractDetail queryAssContractDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050501 资产合同明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssContractDetail
	 * @throws DataAccessException
	*/
	public AssContractDetail queryAssContractDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050501 资产合同明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssContractDetail>
	 * @throws DataAccessException
	*/
	public List<AssContractDetail> queryAssContractDetailExists(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssContractDetail> queryAssContractDetailHosList(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAssContractDetailByUpdate(Map<String,Object> entityMap)throws DataAccessException;
}
