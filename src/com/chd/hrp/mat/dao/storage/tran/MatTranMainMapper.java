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
import com.chd.hrp.mat.entity.MatTranMain;
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
 

public interface MatTranMainMapper extends SqlMapper{
	
	/**
	 * @Description 主表<BR>
	 *              查询序列
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public Long queryMatTranMainSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_tran_main 表  不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatTranMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_tran_main 表  分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatTranMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
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
	public List<Map<String,Object>> queryMatTranMainByMatInv(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_Inv_Dict 表 库存盘点返回 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatTranMainByMatInv(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 获取对象<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssInDetail
	 * @throws DataAccessException
	*/
	public MatTranMain queryMatTranMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 主表<BR>
	 *              审核
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateAuditBatch(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 主表<BR>
	 *              确认
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateConfirmBatch(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	//入库主表模板打印
	public Map<String, Object> queryMatTranPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
		
	public List<Map<String, Object>> queryMatTranPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
		//入库明细表模板打印
	public List<Map<String, Object>> queryMatTranPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 确认前单据状态校验<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int existsMatTranStateForConfirm(List<Map<String, Object>> entityList) throws DataAccessException;
	
	
	
	public List<Map<String, Object>> queryMatTranMainForDura(List<Map<String,Object>> entityList) throws DataAccessException;
}
