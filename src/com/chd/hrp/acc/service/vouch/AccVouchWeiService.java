/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.vouch;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccVouchWei;

/**
* @Title. @Description.
* 凭证分册表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccVouchWeiService {

	/**
	 * @Description 
	 * 凭证分册表<BR> 添加AccVouchWei
	 * @param AccVouchWei entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccVouchWei(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证分册表<BR> 修改AccVouchWei
	 * @param AccVouchWei entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccVouchWei(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证分册表<BR> 删除AccVouchWei
	 * @param AccVouchWei entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccVouchWei(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证分册表<BR> 查询AccVouchWei分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccVouchWei(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 凭证分册表<BR> 查询AccVouchWeiByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccVouchWei queryAccVouchWeiByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证分册表<BR> 
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccSubjByVouchWei(Map<String,Object> entityMap)throws DataAccessException;
	public List<Map<String,Object>> queryAccSubjByVouchWeiPrint(Map<String,Object> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 凭证分册表<BR> 查询表头
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccSubjByVouchBT(Map<String,Object> entityMap)throws DataAccessException;
	
}
