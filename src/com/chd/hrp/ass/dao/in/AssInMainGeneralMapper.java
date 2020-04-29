/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.in;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.in.AssInMainGeneral;
import com.chd.hrp.ass.entity.in.AssInMainSpecial;
/**
 * 
 * @Description:
 * 资产入库主表(一般设备)
 * @Table:
 * ASS_IN_MAIN_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssInMainGeneralMapper extends SqlMapper{     
public int updateInMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateReAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateAssInMainBillNo(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateIsPrint(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * 一般设备 采购入库   入库主表数据查询（主页面）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssInGeneralPrintTemlateByMainBatch(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 一般设备 采购入库    入库明细表数据查询 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssInGeneralPrintTemlateByDetail(	Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 一般设备 采购入库   入库主表数据查询（修改页面）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssInGeneralPrintTemlateByMain(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 一般设备 采购入库  入库单状态查询 （打印时校验数据专用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryAssInGeneralState(Map<String, Object> mapVo)throws DataAccessException;

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap)throws DataAccessException;

	public List<AssInMainGeneral> queryAssBackInMainGenerals(
			Map<String, Object> entityMap);

	public List<AssInMainGeneral> queryAssBackInMainGenerals(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<AssInMainGeneral> queryAssBackInMainGeneral(
			Map<String, Object> entityMap);

	public List<AssInMainGeneral> queryAssBackInMainGeneral(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public Integer querycountStore(Map<String, Object> vStoreMap);

	public Integer querycountVen(Map<String, Object> vVenMap);
	
	public List<AssInMainGeneral> queryByInitOut(
			Map<String, Object> entityMap, RowBounds rowBounds);
	
	public List<AssInMainGeneral> queryByInitOut(
			Map<String, Object> entityMap);
	
	public List<AssInMainGeneral> queryInMainByOutNo(
			Map<String, Object> entityMap, RowBounds rowBounds);
	
	public List<AssInMainGeneral> queryInMainByOutNo(
			Map<String, Object> entityMap);

	public List<AssInMainGeneral> queryByInitBack(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAssBackGeneral(
			Map<String, Object> inMap);

	public List<Map<String, Object>> queryAssBackDetailByIds(
			Map<String, Object> inMap);
	
	public List<AssInMainGeneral> queryByInvoiceNo(
			Map<String, Object> entityMap);
	
	public List<Map<String, Object>> queryAssGenerateBillGeneral(
			Map<String, Object> inMap);
	
	
	public List<Map<String, Object>> queryAssGenerateBillCard(
			Map<String, Object> inMap);
	
	
	public List<Map<String, Object>> queryAssGenerateBillStage(
			Map<String, Object> inMap);
}
