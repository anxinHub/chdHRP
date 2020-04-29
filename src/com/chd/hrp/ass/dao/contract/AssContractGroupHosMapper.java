package com.chd.hrp.ass.dao.contract;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.contract.AssContractGroupHos;

public interface AssContractGroupHosMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050501 集团与医院合同关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssContractGroupHos(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050501 集团与医院合同关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssContractGroupHos(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050501 集团与医院合同关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssContractGroupHos(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050501 集团与医院合同关系表<BR> 
	 * @param  entityMap
	 * @return AssBidGroupHos
	 * @throws DataAccessException
	*/
	public int updateBatchAssContractGroupHos(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050501 集团与医院合同关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssContractGroupHos(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050501 集团与医院合同关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssContractGroupHos(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050501 集团与医院合同关系表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssContractGroupHos> queryAssContractGroupHos(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050501 集团与医院合同关系表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssContractGroupHos> queryAssContractGroupHos(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050501 集团与医院合同关系表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssBidGroupHos
	 * @throws DataAccessException
	*/
	public AssContractGroupHos queryAssContractGroupHosByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050501 集团与医院合同关系表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBidGroupHos
	 * @throws DataAccessException
	*/
	public AssContractGroupHos queryAssContractGroupHosByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050501 集团与医院合同关系表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBidGroupHos>
	 * @throws DataAccessException
	*/
	public List<AssContractGroupHos> queryAssContractGroupHosExists(Map<String,Object> entityMap)throws DataAccessException;
}
