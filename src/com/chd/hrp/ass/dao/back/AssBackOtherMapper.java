/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.back;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050701 资产退货单主表(其他固定资产)
 * @Table:
 * ASS_BACK_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackOtherMapper extends SqlMapper{
	
	public int updateBackMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateBatchConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public Map<String,Object> collectAssBackOther(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * 其他固定资产 采购退货  退货主表查询（主页面）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssBackOtherPrintTemlateByMainBatch(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 其他固定资产 采购退货  退货明细表查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssBackOtherPrintTemlateByDetail(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 其他固定资产 采购退货  退货主表查询 （修改页面）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssBackOtherPrintTemlateByMain(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 其他固定资产 采购退货  退货单状态查询  （打印时校验数据专用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryAssBackOtherState(Map<String, Object> mapVo)throws DataAccessException;

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public void updateAssBackOtherMoney(Map<String, Object> inMapVo);
	
	int updateAssBackMainBillNo(Map<String,Object> entityMap) throws DataAccessException;
	
}
