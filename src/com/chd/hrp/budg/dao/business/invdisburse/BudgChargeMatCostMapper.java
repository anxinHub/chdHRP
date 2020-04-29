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
 * 科室收费材料支出
 * @Table:
 * BUDG_CHARGE_MAT_COST
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgChargeMatCostMapper extends SqlMapper{
	
	/**
	 * 查询 物质材料基本信息(根据编码 匹配ID用) 导入用
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatTypeData(Map<String, Object> map)throws DataAccessException;
	
	/**
	 * 校验数据 是否存在
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 删除所选年度 全年数据
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public int deleteYearAllData(Map<String, Object> mapVo) throws DataAccessException;
	 
	/**
	 * 采集同时添加数据
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public int collectData(Map<String, Object> mapVo) throws DataAccessException;
	
}
