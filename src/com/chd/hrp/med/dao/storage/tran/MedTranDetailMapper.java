/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.storage.tran;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedTranDetail;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MED_TRAN_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedTranDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 从表查询序列<BR>
	 * @return Long
	 * @throws DataAccessException
	 */
	public Long queryMedTranDetailSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_tran_main 表  不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedTranDetailByTranID(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_tran_main 表  分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedTranDetailByTranID(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 组装整单调拨药品数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedTranDetailBySingle(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询选择单据中的所有出库药品<BR> 
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public List<MedTranDetail> queryMedTranDetailForOutConfirm(List<Map<String,Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询选择单据中的所有入库药品<BR> 
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public List<MedTranDetail> queryMedTranDetailForInConfirm(List<Map<String,Object>> entityList) throws DataAccessException;

	public List<Map<String,Object>> queryTranDetails(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String,Object>> queryTranDetails(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryMedTranOutRela(Map<String, Object> entityMap) throws DataAccessException;
	
	public void deleteMedTranRela(List<Map<String, Object>> relaList) throws DataAccessException;
	/**
	 * 查看出库单是否是科室申领生成
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Integer queryMedTranMainIsApply(Map<String, Object> entityMap) throws DataAccessException;
}
