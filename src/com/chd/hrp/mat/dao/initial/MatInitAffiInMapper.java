/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.dao.initial;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 代销材料期初入库
 * @Table:
 * MAT_IN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatInitAffiInMapper extends SqlMapper{
	
	/**
	 * 查询明细信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryDetailByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量入库确认<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int confirmMatInitAffiInBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取材料<BR> 
	 * @param  entityMap <BR>
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryInvListForImport(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取货位<BR> 
	 * @param  entityMap <BR>
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryLocationListForImport(Map<String,Object> entityMap)throws DataAccessException;
	
	//入库主表模板打印
public Map<String, Object> queryMatInPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
public List<Map<String, Object>>  queryMatInPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
		//入库明细表模板打印
public List<Map<String, Object>> queryMatInPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
		
}
