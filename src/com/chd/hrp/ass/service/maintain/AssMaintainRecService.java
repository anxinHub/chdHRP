/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.maintain;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.maintain.AssMaintainRec;
/**
 * 
 * @Description:
 * 051203 保养记录
 * @Table:
 * ASS_MAINTAIN_REC
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssMaintainRecService {  

	/**
	 * @Description 
	 * 添加051203 保养记录<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssMaintainRec(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051203 保养记录<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssMaintainRec(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051203 保养记录<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssMaintainRec(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051203 保养记录<BR> (审核)
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssMaintainRec(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051203 保养记录<BR> (消审)
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssMaintainRecBack(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051203 保养记录<BR> (终止)
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssMaintainRecStop(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除051203 保养记录<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssMaintainRec(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除051203 保养记录<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssMaintainRec(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新051203 保养记录<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssMaintainRec(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集051203 保养记录<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssMaintainRec(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象051203 保养记录<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public AssMaintainRec queryAssMaintainRecByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051203 保养记录<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMaintainRec
	 * @throws DataAccessException
	*/
	public AssMaintainRec queryAssMaintainRecByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051203 保养记录<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMaintainRec>
	 * @throws DataAccessException
	*/
	public List<AssMaintainRec> queryAssMaintainRecExists(Map<String,Object> entityMap)throws DataAccessException;
    /**
     * 卡片查询保养记录
     * @param page
     * @return
     * @throws DataAccessException
     */
	public String queryAssMaintainRecCard(Map<String, Object> entityMap)throws DataAccessException;

	public String addOrUpdateMainRec(Map<String, Object> mapVo);

	Long queryCurrentSequence() throws DataAccessException;

	public String saveAssMaintainItem(List<Map<String, Object>> listVo);

	public String deleteAssMaintainItem(List<Map<String, Object>> listVo);

	public String buildAssRecItem(Map<String, Object> mapVo);

	public String queryAssRecItem(Map<String, Object> page);

	public Map<String, Object> queryAssMainRecPrint(Map<String, Object> entityMap)
			throws DataAccessException;

	public List<String> queryAssMainRecState(Map<String, Object> mapVo);

	String buildAssMaintainItem(Map<String, Object> entityMap);

	public String queryDetails(Map<String, Object> page);

	public int queryAssMainRecStateList(List<Map<String, Object>> listVo) throws DataAccessException;
}
