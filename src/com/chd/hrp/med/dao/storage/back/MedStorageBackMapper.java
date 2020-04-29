/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.storage.back;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 常备药品退货
 * @Table:
 * MED_IN_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedStorageBackMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 入库单结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryIn(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 入库单结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryIn(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 入库单药品结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryInDetail(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 入库单药品结果集<BR>全部 
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
	public List<Map<String,Object>> queryMedStorageBackDetailByCode(Map<String,Object> entityMap) throws DataAccessException;

	 /**
	 * @Description 
	 * 校验退货即时库存<BR> 
	 * @param  List<Map<String, Object>>
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMedBackStockInvIsEnough(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询明细用于复制<BR> 
	 * @param  entityMap
	 * @return List<Map<String,Object>>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedInDetailForCopy(Map<String,Object> entityMap) throws DataAccessException;
	
	//入库主表模板打印
	public Map<String, Object> queryMedInPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedInPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;

	//入库明细表模板打印
	public List<Map<String, Object>> queryMedInPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;

}
