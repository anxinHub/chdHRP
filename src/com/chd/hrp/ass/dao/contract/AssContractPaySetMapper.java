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
import com.chd.hrp.ass.entity.contract.AssContractPaySet;
/**
 * 
 * @Description:
 * 050502 资产合同分期付款设置
 * @Table:
 * ASS_CONTRACT_PAY_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssContractPaySetMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050502 资产合同分期付款设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssContractPaySet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050502 资产合同分期付款设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssContractPaySet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050502 资产合同分期付款设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssContractPaySet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050502 资产合同分期付款设置<BR> 
	 * @param  entityMap
	 * @return AssContractPaySet
	 * @throws DataAccessException
	*/
	public int updateBatchAssContractPaySet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050502 资产合同分期付款设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssContractPaySet(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050502 资产合同分期付款设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssContractPaySet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050502 资产合同分期付款设置<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssContractPaySet> queryAssContractPaySet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050502 资产合同分期付款设置<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssContractPaySet> queryAssContractPaySet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050502 资产合同分期付款设置<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssContractPaySet
	 * @throws DataAccessException
	*/
	public AssContractPaySet queryAssContractPaySetByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050502 资产合同分期付款设置<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssContractPaySet
	 * @throws DataAccessException
	*/
	public AssContractPaySet queryAssContractPaySetByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050502 资产合同分期付款设置<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssContractPaySet>
	 * @throws DataAccessException
	*/
	public List<AssContractPaySet> queryAssContractPaySetExists(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
