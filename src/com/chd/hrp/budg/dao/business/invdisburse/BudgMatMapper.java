/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.invdisburse;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室材料支出预算
 * @Table:
 * BUDG_MAT
 * @Author: slient
 * @email:  slient@e-tonggroup.com
 * @Version: 1.0
 */
public interface BudgMatMapper extends SqlMapper{

	/**
	 * 查询科室列表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHosDeptDict(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 查询物资分类列表
	 * 根据年度从BUDG_MAT_TYPE_SUBJ中取
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMatTypeSubj(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 获取汇总数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> getCollectData(Map<String, Object> mapVo)throws DataAccessException;
	
	public int saveOrUpdateBatch(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 校验数据 是否存在
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> map) throws DataAccessException;
	
	
}
