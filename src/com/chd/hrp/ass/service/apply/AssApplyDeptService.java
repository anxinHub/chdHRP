/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.apply;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.apply.AssApplyDept;
/**
 * 
 * @Description:
 * 050201 购置申请单
 * @Table:
 * ASS_APPLY_DEPT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssApplyDeptService {

	/**
	 * 获取当前的序列号
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryAssApplyDeptSequence()throws DataAccessException;
	/**
	 * @Description 
	 * 添加050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssApplyDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssApplyDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssApplyDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssApplyDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	//审核
	public String updateToExamine(List<Map<String,Object>> entityMap)throws DataAccessException;
	//销审
	public String updateNotToExamine(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssApplyDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssApplyDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssApplyDept(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssApplyDept(Map<String,Object> entityMap) throws DataAccessException;
	public String queryAssApplyDepts(Map<String,Object> entityMap) throws DataAccessException;
	public String queryAssApplyDeptByPlanDept(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询对象050201 购置申请单<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public AssApplyDept queryAssApplyDeptByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050201 购置申请单<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssApplyDept
	 * @throws DataAccessException
	*/
	public AssApplyDept queryAssApplyDeptByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	//打印
	public List<AssApplyDept> queryAssApplyDeptExists(Map<String,Object> entityMap)throws DataAccessException;
	Map<String, Object> queryAssApplyDeptDY(Map<String, Object> map)
			throws DataAccessException;
	public List<String> queryAssApplyDeptState(Map<String, Object> mapVo)throws DataAccessException;
	
}
