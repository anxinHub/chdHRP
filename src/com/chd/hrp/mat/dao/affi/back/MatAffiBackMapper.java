/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.affi.back;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 常备材料退货
 * @Table:
 * MAT_IN_MAIN 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatAffiBackMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 入库单结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAffiIn(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 入库单结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAffiIn(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	
	public List<?> queryMatAffiBackInSup(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 入库单结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryMatAffiBackInSup(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 入库单材料结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAffiInDetail(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 入库单材料结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryBackDetailByImp(Map<String,Object> entityMap) throws DataAccessException;

	 /**
	 * @Description 
	 * 审核或消审<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int auditOrUnAudit(Map<String, Object> entityMap)throws DataAccessException;

	 /**
	 * @Description 
	 * 批量审核或消审<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int auditOrUnAuditBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询退货单明细 不分页<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiBackDetailById(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryMatAffiBackDetailByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 校验库存是否充足<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMatAffiBackInvIsEnough(List<Map<String,Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询退货单明细用于复制<BR> 
	 * @param  entityMap
	 * @return List<Map<String,Object>>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiInDetailForCopy(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
}
