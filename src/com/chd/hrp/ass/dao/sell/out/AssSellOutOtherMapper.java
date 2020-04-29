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
import com.chd.hrp.ass.entity.sell.out.AssSellOutOther;
/**
 * 
 * @Description:
 * 050901 资产有偿调拨出库单主表(其他固定资产)
 * @Table:
 * ASS_SELL_OUT_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssSellOutOtherMapper extends SqlMapper{
	public int updateSellOutMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateReAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;

	public Map<String,Object> collectAssSellOutOther(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssSellOutOther> queryBySellInImport(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public List<AssSellOutOther> queryBySellInImport(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssSellOutOther> queryBySellInImportView(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public List<AssSellOutOther> queryBySellInImportView(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * 其他固定资产 资产调拨出库 主表数据查询 （主页面）打印 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssSellOutOtherPrintTemlateByMainBatch(	Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 其他固定资产 资产调拨出库 明细表数据查询  打印 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssSellOutOtherPrintTemlateByDetail(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 其他固定资产 资产调拨出库 主表数据查询 （修改页面） 打印 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssSellOutOtherPrintTemlateByMain(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 其他固定资产  资产调拨出库 出库单状态查询 （ 打印校验数据用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryAssSellOutOtherState(Map<String, Object> mapVo)throws DataAccessException;

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);
}
