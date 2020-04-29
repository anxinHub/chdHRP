/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.account.report;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.med.entity.MedShowSet;
/**
 * 
 * @Description:
 * 081101 药品库存汇总表设置
 * @Table:
 * MED_SHOW_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedAccountReportInvStockSetService {
	
	/**
	 * @Description 
	 * 添加081101 药品库存汇总表设置<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMedShowSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新081101 药品库存汇总表设置<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String updateMedShowSet(Map<String,Object> entityMap)throws DataAccessException;
		
	/**
	 * @Description 
	 * 删除081101 药品库存汇总表设置<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMedShowSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除081101 药品库存汇总表设置<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMedShowSetBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集081101 药品库存汇总表设置<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedShowSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象081101 药品库存汇总表设置<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public MedShowSet queryMedShowSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加081101 药品库存汇总表设置明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMedShowDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集081101 药品库存汇总表设置明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedShowDetailByCode(Map<String,Object> entityMap) throws DataAccessException;
	
}
