/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.sell.out;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.sell.out.AssSellOutHouse;
/**
 * 
 * @Description:
 * 050901 资产有偿调拨出库单主表（房屋及建筑物）
 * @Table:
 * ASS_SELL_OUT_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssSellOutHouseMapper extends SqlMapper{
	public int updateSellOutMoney(Map<String, Object> entityMap) throws DataAccessException;

	public int updateAudit(List<Map<String, Object>> entityMap) throws DataAccessException;

	public int updateReAudit(List<Map<String, Object>> entityMap) throws DataAccessException;

	public int updateConfirm(List<Map<String, Object>> entityMap) throws DataAccessException;

	public Map<String, Object> collectAssSellOutHouse(Map<String, Object> entityMap) throws DataAccessException;

	public List<AssSellOutHouse> queryBySellInImport(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	public List<AssSellOutHouse> queryBySellInImport(Map<String, Object> entityMap) throws DataAccessException;

	public List<AssSellOutHouse> queryBySellInImportView(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	public List<AssSellOutHouse> queryBySellInImportView(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAssSellOutHouseByAssInNo(Map<String, Object> map);

	public List<Map<String, Object>> queryAssSellOutHouseDetailByAssInNo(Map<String, Object> map);

	public List<String> queryAssSellOutStates(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);
}
