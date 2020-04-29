/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.back.rest;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050701 资产其他退货单主表(其他无形资产)
 * @Table:
 * ASS_BACK_REST_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackRestInassetsMapper extends SqlMapper{
	
	public int updateBackMoney(Map<String,Object> entityMap)throws DataAccessException;

	public int updateBatchConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;

	public Map<String,Object> collectAssBackInassets(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * 批量打印 其他无形资产 其他退货  主表数据查询（主页面）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssBackInassetsPrintTemlateByMainBatch(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 批量打印 其他无形资产 其他退货  ，明细表数据查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssBackInassetsPrintTemlateByDetail(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 批量打印 其他无形资产 其他退货  主表数据查询 （修改页面）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssBackInassetsPrintTemlateByMain(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 其他无形资产其他退货 退货单状态查询 (批量打印 校验数据用)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryAssBackRestInassetsState(Map<String, Object> mapVo)throws DataAccessException;

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);
}
