/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.storage.tran;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface MatTranMainService extends SqlService {
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatTranMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatInMainBySingle(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatInDetailBySingle(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 组装整单调拨材料数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatTranDetailBySingle(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 调出确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmOutMatTranMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 取消调出确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unConfirmOutMatTranMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 调入确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmInMatTranMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 冲账<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String offsetMatTranMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询材料结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatTranMainByMatInv(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MatTranMain结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public MatTranMain queryMatTranMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询材料结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatTranDetailByTranID(Map<String, Object> entityMap) throws DataAccessException;
	public String queryMatTranDetailByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	
	
	//入库模板打印（包含主从表）
	public String queryMatTranByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询明细结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatTranDetails(Map<String,Object> entityMap) throws DataAccessException;

	Map<String, Object> queryMatTranByPrintTemlateMain(Map<String, Object> map) throws DataAccessException;

	public void updateMatTranOutRela(Map<String, Object> entityMap) throws DataAccessException;
	public int queryMatTranMainIsApply(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 材料调拨材料条形码打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */  
	Map<String, Object> queryMatTranDetailBarcodeByPrintTemlate(
			Map<String, Object> entityMap) throws DataAccessException;

}
