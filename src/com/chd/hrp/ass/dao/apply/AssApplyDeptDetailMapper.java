/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.apply;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.apply.AssApplyDept;
import com.chd.hrp.ass.entity.apply.AssApplyDeptDetail;
import com.chd.hrp.ass.entity.plan.AssPlanDeptDetail;
/**
 * 
 * @Description:
 * 050201 资产购置申请明显表
 * @Table:
 * ASS_APPLY_DEPT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssApplyDeptDetailMapper extends SqlMapper{
	
	public List<AssApplyDeptDetail> queryByAssApplyDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 添加050201 资产购置申请明显表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssApplyDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050201 资产购置申请明显表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssApplyDeptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050201 资产购置申请明显表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssApplyDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050201 资产购置申请明显表<BR> 
	 * @param  entityMap
	 * @return AssApplyDeptDetail
	 * @throws DataAccessException
	*/
	public int updateBatchAssApplyDeptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050201 资产购置申请明显表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssApplyDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050201 资产购置申请明显表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssApplyDeptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集050201 资产购置申请明显表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssApplyDeptDetail> queryAssApplyDeptDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050201 资产购置申请明显表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssApplyDeptDetail> queryAssApplyDeptDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050201 资产购置申请明显表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssApplyDeptDetail
	 * @throws DataAccessException
	*/
	public AssApplyDeptDetail queryAssApplyDeptDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050201 资产购置申请明显表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssApplyDeptDetail
	 * @throws DataAccessException
	*/
	public AssApplyDeptDetail queryAssApplyDeptDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryAssApplyDeptDetailByUpdate(
			Map<String, Object> entityMap)throws DataAccessException;
	
	public List<AssApplyDeptDetail> queryAssApplyDeptDetailExists(Map<String,Object> entityMap)throws DataAccessException;
	public List<AssApplyDept> queryByAssApplyId(
			Map<String, Object> entityMap)throws DataAccessException;
	
	public List<AssApplyDeptDetail>queryByAssApplyDeptDetailByPlanDept(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 查询  明细Id
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryAssApplyDeptDetailSequence() throws DataAccessException;
	/**
	 * 新增 明细（明细id 需要明确传入）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addAssApplyDeptDetailNew(Map<String, Object> entityMap) throws DataAccessException;
	
}
