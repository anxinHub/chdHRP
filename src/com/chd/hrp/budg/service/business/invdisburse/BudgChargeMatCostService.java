/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.invdisburse;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface BudgChargeMatCostService extends SqlService {
	
	/**
	 * 查询 物质材料基本信息(根据编码 匹配ID用) 导入用
	 * @param paraMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatTypeData(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 * 校验数据 是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 科室收费材料支出 数据采集（系统内部数据采集）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectData(Map<String, Object> mapVo) throws DataAccessException;

}
