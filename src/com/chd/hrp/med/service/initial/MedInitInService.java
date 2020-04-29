/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.initial;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 04105 物资材料表
 * @Table:
 * MED_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MedInitInService extends SqlService {
	
	/**
	 * @Description 
	 * 根据主表ID查询明细数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryDetailByCode(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 入库确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmMedInitIn(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmMedInitInBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询材料信息用于导入<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryInvListForImport(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询货位信息用于导入<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryLocationListForImport(Map<String, Object> entityMap)throws DataAccessException;
	//入库模板打印（包含主从表）
	public String queryMedInByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
		
   //入库模板打印（包含主从表）  新版打印
   public Map<String, Object> queryMedInByPrintTemlateNewPrint(Map<String,Object> entityMap) throws DataAccessException;
}
