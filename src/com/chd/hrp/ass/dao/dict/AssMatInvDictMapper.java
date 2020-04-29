/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.dict;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 04106 物资材料变更表
 * @Table:
 * MAT_INV_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssMatInvDictMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 查询物资材料表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryByCodes(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加变更表删除记录<BR> 
	 * @param  entityMap<BR>
	 * @return MatInvDict
	 * @throws DataAccessException
	*/
	public int addForDelete(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量停用<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInvDict
	 * @throws DataAccessException
	*/
	public int updateIsStop(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * 
	 * @param entityMap
	 * @return  审核
	 * @throws DataAccessException
	 */
	public int auditMatInvDict(List<Map<String, Object>> entityMap) throws DataAccessException;
	/**
	 * 根据fac_id查材料字典表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatInvIdByFacId(Map<String, Object> entityMap) throws DataAccessException;

	public String queryMatInvIdByFacIds(Map<String, Object> entityMap) throws DataAccessException;
}
