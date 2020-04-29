package com.chd.hrp.med.dao.purchase.check;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 04114 采购计划审核
 * @Table:
 * MED_PUR_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedPurMainCheckMapper extends SqlMapper{
	/**
	 * @Description 
	 * 采购计划审核<BR>将采购计划状态修改为审核状态
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateToCheckState(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划审核<BR>将采购计划状态修改为未审核状态
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateToUnCheckState(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划审核<BR>根据主表ID查询采购明细
	 * @param entityMap
	 * @return <T>
	 * @throws DataAccessException
	*/
	public <T> T queryMedPurMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划审核<BR>查询 按采购计划主表ID查询明细
	 * @param entityMap
	 * @return List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryMedPurMainDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划审核<BR>分页查询 按采购计划主表ID查询明细
	 * @param entityMap
	 * @return List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryMedPurMainDetail(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;

	//入库主表模板打印
		public Map<String, Object> queryMedCheckPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
		
		//入库明细表模板打印
		public List<Map<String, Object>> queryMedCheckPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
		/**
		 * 已经生成订单的采购单不能销审
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String existsInMedOrder(List<Map<String, Object>> entityMap) throws DataAccessException;
		
		
}
