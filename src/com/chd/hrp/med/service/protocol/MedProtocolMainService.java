/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.protocol;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.med.entity.MedProtocolMain;
/**
 * 
 * @Description:
 * state：
0：新建
1：审核
2：确认

 * @Table:
 * MED_PROTOCOL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedProtocolMainService {

	/**
	 * @Description 
	 * 添加state：
0：新建
1：审核
2：确认
<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMedProtocolMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加state：
0：新建
1：审核
2：确认
<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMedProtocolMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新state：
0：新建
1：审核
2：确认
<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMedProtocolMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新state：
0：新建
1：审核
2：确认
<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMedProtocolMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除state：
0：新建
1：审核
2：确认
<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMedProtocolMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除state：
		0：新建
		1：审核
		2：确认
		<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMedProtocolMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新state：
0：新建
1：审核
2：确认
<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateMedProtocolMain(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集state：
		0：新建
		1：审核
		2：确认
		<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedProtocolMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象state：
0：新建
1：审核
2：确认
<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public MedProtocolMain queryMedProtocolMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取state：
0：新建
1：审核
2：确认
<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedProtocolMain
	 * @throws DataAccessException
	*/
	public MedProtocolMain queryMedProtocolMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String queryMedProtocolMainPur(Map<String,Object> entityMap)throws DataAccessException;
}
