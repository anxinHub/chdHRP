/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.affi.tran;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface MatAffiTranMainService extends SqlService {
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatAffiTranMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 整单调拨--查询主表数据
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatInMainBySingle(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 *整单调拨--查询明细数据
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatInDetailBySingle(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 整单调拨-组装明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiTranDetailBySingle(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 调出确认 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String outConfirmMatAffiTranMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 取消调出确认 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unOutConfirmMatAffiTranMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 调入确认
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String inConfirmMatAffiTranMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 冲账
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String balanceConfirmMatAffiTranMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询材料结果集
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatAffiTranMainByMatInv(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 修改页面 明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiTranDetailByTranID(Map<String, Object> entityMap) throws DataAccessException;
	public String queryMatAffiTranDetailByCode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 修改页面 主表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public MatAffiTranMain queryMatAffiTranMainByCode(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 获取主表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	
	//入库模板打印（包含主从表）
	public String queryMatAffiTranByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;

	Map<String, Object> queryMatAffiTranPrintTemlateMain(Map<String, Object> map) throws DataAccessException;
	/**
	 * 查询明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiTranMainDetails(Map<String, Object> entityMap) throws DataAccessException;

	void updateMatAffiTranRela(Map<String, Object> entityMap) throws DataAccessException;

	public int queryMatAffiTranMainIsApply(Map<String, Object> mapVo) throws DataAccessException;

	
}
