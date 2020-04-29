/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.affi.tran;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedAffiTranMain;
/**
 * 
 * @Description:
 * 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
 * @Table:
 * MED_TRAN_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedAffiTranMainMapper extends SqlMapper{
	
	/**
	 * @Description 主表<BR>
	 *              查询序列
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public Long queryMedAffiTranMainSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_tran_main 表  不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedAffiTranMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_tran_main 表  分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedAffiTranMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MedInMain 表 返回材料 用来计算库存 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedInMainBySingle(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MedInMain 表 返回材料 用来计算库存 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedInMainBySingle(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MedInDetail 表 返回材料 用来计算库存 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedInDetailBySingle(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MedInDetail 表 返回材料 用来计算库存 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedInDetailBySingle(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_Inv_Dict 表 库存盘点返回 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedAffiTranMainByMedInv(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_Inv_Dict 表 库存盘点返回 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedAffiTranMainByMedInv(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
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
	public <T> T queryMedAffiTranMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	
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
		public Map<String, Object> queryMedAffiTranPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
		public List<Map<String, Object>>  queryMedAffiTranPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
			//入库明细表模板打印
		public List<Map<String, Object>> queryMedAffiTranPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
		
}
