/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.info.basic;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatVenCertType;
/**
 * 
 * @Description:
 * 04604 供应商证件类型
 * @Table:
 * MAT_VEN_CERT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatVenCertTypeMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加04604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatVenCertType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatVenCertType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatVenCertType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return MatVenCertType
	 * @throws DataAccessException
	*/
	public int updateBatchMatVenCertType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除04604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatVenCertType(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除04604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatVenCertType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04604 供应商证件类型<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatVenCertType> queryMatVenCertType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04604 供应商证件类型<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatVenCertType> queryMatVenCertType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取04604 供应商证件类型<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatVenCertType
	 * @throws DataAccessException
	*/
	public MatVenCertType queryMatVenCertTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04604 供应商证件类型<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatVenCertType
	 * @throws DataAccessException
	*/
	public MatVenCertType queryMatVenCertTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 查询 与 输入证件类型编码、证件类型名称 相同的记录(判断证件类型编码、证件类型名称是否已存在)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MatVenCertType> queryMatVenCertTypeByID(Map<String, Object> entityMap)throws DataAccessException;
	
	
	
}
