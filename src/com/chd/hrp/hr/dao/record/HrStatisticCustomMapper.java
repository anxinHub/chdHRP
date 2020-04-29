/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.record;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 简单统计表设置
 * @Table:
 * DIC_MARRIAGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrStatisticCustomMapper extends SqlMapper{
	/**
	 * 查询简单统计表设置
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryHrStatisticCustomSet(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据统计表编码查询统计表设置
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryHrStatisticCustomSetByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 添加简单统计表设置
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int insertHrStatisticCustomSet(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 修改简单统计表设置
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateHrStatisticCustomSet(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除简单统计表设置
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteHrStatisticCustomSet(Map<String, Object> entityMap) throws DataAccessException;
    /**
     * 统计表名称校验唯一值
     * @param entityMap
     * @return
     */
	public List<Map<String, Object>> queryHrStatisticCustomSetByName(Map<String, Object> entityMap);
}
