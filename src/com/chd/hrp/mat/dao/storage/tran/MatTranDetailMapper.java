/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.storage.tran;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatTranDetail;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MAT_TRAN_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatTranDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 从表查询序列<BR>
	 * @return Long
	 * @throws DataAccessException
	 */
	public Long queryMatTranDetailSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_tran_main 表  不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatTranDetailByTranID(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryMatTranDetailByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_tran_main 表  分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatTranDetailByTranID(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 组装整单调拨材料数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatTranDetailBySingle(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询选择单据中的所有出库材料<BR> 
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public List<MatTranDetail> queryMatTranDetailForOutConfirm(List<Map<String,Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询选择单据中的所有入库材料<BR> 
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public List<MatTranDetail> queryMatTranDetailForInConfirm(List<Map<String,Object>> entityList) throws DataAccessException;

	public List<Map<String,Object>> queryTranDetails(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String,Object>> queryTranDetails(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryMatTranOutRela(Map<String, Object> entityMap) throws DataAccessException;
	
	public void deleteMatTranRela(List<Map<String, Object>> relaList) throws DataAccessException;
	/**
	 * 查看出库单是否是科室申领生成
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Integer queryMatTranMainIsApply(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 材料调拨 材料条码打印数据查询
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryMatTranDetailBarcodeByPrintTemlate(
			Map<String, Object> entityMap);  
}
