/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.project.balanceadjust;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

 

public interface BudgProjReAdjDetMapper extends SqlMapper{
	
	/**
	 * 查询可用余额 作为调整资金回显
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryUsableAmount(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 审核/销审  更改数据
	 * @param listVo
	 * @return
	 */
	public int updateAdjSate(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 根据主表主键单号  查询明细数据
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryForDetail(Map<String, Object> map) throws DataAccessException;
	
}
