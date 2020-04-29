
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.service.dict;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.ass.entity.dict.AssNaturs;
/**
 * 
 * @Description:
 * 050103 资产性质
 * @Table:
 * ASS_NATURS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AssNatursService {

	/**
	 * @Description 
	 * 添加050103 资产性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssNaturs(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050103 资产性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssNaturs(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050103 资产性质<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssNaturs(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050103 资产性质<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssNaturs(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050103 资产性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssNaturs(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050103 资产性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssNaturs(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集050103 资产性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssNaturs(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象050103 资产性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public AssNaturs queryAssNatursByCode(Map<String,Object> entityMap)throws DataAccessException;
   /**
    * 查询仓库
    * @param mapVo
    * @return
    * @throws DataAccessException
    */
	public String queryHosStoreDict(Map<String, Object> entityMap)throws DataAccessException;

    public String updateAssNatursStore(ArrayList<Map<String, Object>> listVo)throws DataAccessException;

	//public String queryHosStoreDictByNaturs(Map<String, Object> entityMap)throws DataAccessException;




	
}
