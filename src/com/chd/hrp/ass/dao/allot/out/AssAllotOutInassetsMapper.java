/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.allot.out;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutInassets;
/**
 * 
 * @Description:
 * 050901 资产无偿调拨出库单主表(无形资产)
 * @Table:
 * ASS_ALLOT_OUT_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssAllotOutInassetsMapper extends SqlMapper{
	public int updateAllotOutMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateReAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;

	public Map<String,Object> collectAssAllotOutInassets(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssAllotOutInassets> queryByAllotInImport(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public List<AssAllotOutInassets> queryByAllotInImport(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssAllotOutInassets> queryByAllotInImportView(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public List<AssAllotOutInassets> queryByAllotInImportView(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 *  其他无形资产 资产调剂 出库 打印 主表数据查询（主页面 打印）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssAllotOutInassetsPrintTemlateByMainBatch(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 其他无形资产 资产调剂 出库 打印 明细表数据查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssAllotOutInassetsPrintTemlateByDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 其他无形资产 资产调剂 出库 打印 主表数据查询（修改页面）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssAllotOutInassetsPrintTemlateByMain(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 其他无形资产 资产调剂出口库 出库单状态查询（打印校验数据用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryAssAllotOutInassetsState(Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);
}
