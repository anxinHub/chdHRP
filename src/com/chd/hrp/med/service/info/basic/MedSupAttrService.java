package com.chd.hrp.med.service.info.basic;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.dict.AssSupAttr;
import com.chd.hrp.med.entity.HosSupBank;

/**
 * 
 * @Description:
 * 061013 供应商附属表
 * @Table:
 * ASS_Sup_ATTR
 * @Author: linuxxu
 * @email:  linuxxu@s-chd.com
 * @Version: 1.0
 */
public interface MedSupAttrService {

	/**
	 * @Description 
	 * 添加061013 供应商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMedSupAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加061013 供应商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMedSupAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新061013 供应商附属表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMedSupAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新061013 供应商附属表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMedSupAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除061013 供应商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMedSupAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除061013 供应商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMedSupAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集061013 供应商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedSupAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象061013 供应商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public AssSupAttr queryMedSupAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询供应商银行信息表<BR> 
	 * @param  entityMap
	 * @return List<HosSupBank>
	 * @throws DataAccessException
	*/
	public String queryHosSupBank(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加银行信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addHosSupBank(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 根据 供应商编码 查询 供应商ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Long querySupIdByCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 *  根据 科室编码 查询 科室ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryDeptIdByCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 供应商类型树  数据查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHosSupTypeByMenu(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据供应商类别编码 查询该供应商类别是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int querySupTypeExist(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询 所属系统模块编码是否存在 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryModExist(Map<String, Object> mapVo) throws DataAccessException;
}
