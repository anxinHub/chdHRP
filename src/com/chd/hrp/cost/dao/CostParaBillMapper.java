/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.cost.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description: 成本_分摊定向单据
 * @Table: COST_PARA_BILL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface CostParaBillMapper extends SqlMapper {

	/**
	 * 获取最大值
	 * 
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMaxNo(Map<String, Object> map) throws DataAccessException;

	/**
	 * @Description 科室分摊参数设置树形展示
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryByTree(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 获取当前的序列号
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public Long querySequence() throws DataAccessException;

	/**
	 * @Description 分摊参数需要的科室列表
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryParaDeptDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 分摊参数需要的科室列表
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public int addinheritance(Map<String, Object> entityMap) throws DataAccessException;
}
