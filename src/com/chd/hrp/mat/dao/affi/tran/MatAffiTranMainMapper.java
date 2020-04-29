/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.affi.tran;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatAffiTranMain;
/**
 * 
 * @Description:
 * 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
 * @Table:
 * MAT_TRAN_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatAffiTranMainMapper extends SqlMapper{
	
	/**
	 * @Description 主表<BR>
	 *              查询序列
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public Long queryMatAffiTranMainSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_tran_main 表  不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiTranMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_tran_main 表  分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiTranMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MatInMain 表 返回材料 用来计算库存 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatInMainBySingle(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MatInMain 表 返回材料 用来计算库存 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatInMainBySingle(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MatInDetail 表 返回材料 用来计算库存 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatInDetailBySingle(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MatInDetail 表 返回材料 用来计算库存 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatInDetailBySingle(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_Inv_Dict 表 库存盘点返回 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiTranMainByMatInv(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_Inv_Dict 表 库存盘点返回 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiTranMainByMatInv(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 主表<BR>
	 *              批量修改状态
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateBatchState(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 查询主表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public <T> T queryMatAffiTranMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量入库确认
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateConfirmBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 主表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	
	//入库主表模板打印
	public Map<String, Object> queryMatAffiTranPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>>  queryMatAffiTranPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
	//入库明细表模板打印
	public List<Map<String, Object>> queryMatAffiTranPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
	//查询明细
	public List<Map<String, Object>> queryMatAffiTranMainDetails(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatAffiTranMainDetails(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryMatAffiTranRela(Map<String, Object> entityMap) throws DataAccessException;	
	
	public void deleteMatAffiTranRela(List<Map<String, Object>> relaList) throws DataAccessException;
	
	public Integer queryMatAffiTranMainIsApply(Map<String, Object> entityMap) throws DataAccessException;
	
	
}
