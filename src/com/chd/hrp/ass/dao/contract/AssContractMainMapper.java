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
import com.chd.hrp.ass.entity.contract.AssContractMain;
/**
 * 
 * @Description:
 * 050501 资产合同主表
 * @Table:
 * ASS_CONTRACT_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssContractMainMapper extends SqlMapper{
	/**
	 * 获取当前的序列号
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryAssContractMainSequence()throws DataAccessException;
	/**
	 * @Description 
	 * 添加050501 资产合同主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssContractMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050501 资产合同主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssContractMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050501 资产合同主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssContractMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050501 资产合同主表<BR> 
	 * @param  entityMap
	 * @return AssContractMain
	 * @throws DataAccessException
	*/
	public int updateBatchAssContractMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050501 资产合同主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssContractMain(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050501 资产合同主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssContractMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050501 资产合同主表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssContractMain> queryAssContractMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050501 资产合同主表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssContractMain> queryAssContractMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050501 资产合同主表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssContractMain
	 * @throws DataAccessException
	*/
	public AssContractMain queryAssContractMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050501 资产合同主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssContractMain
	 * @throws DataAccessException
	*/
	public AssContractMain queryAssContractMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050501 资产合同主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssContractMain>
	 * @throws DataAccessException
	*/
	public List<AssContractMain> queryAssContractMainExists(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 审核<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateToExamine(List<Map<String,Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 消审<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateNotToExamine(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 履行<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateToPerform(List<Map<String,Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 取消履行<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateNotToPerform(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 归档<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateToFile(List<Map<String,Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 取消归档<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateNotToFile(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	
}
