/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.matprepay;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.mat.entity.MatPrePayMain;
/**
 * 
 * @Description:
 * 保存采购预付款单的主要信息
 * @Table:
 * MAT_BILL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatPrePayMainService {

	/**
	 * @Description 
	 * 添加保存采购预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMatPrePayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加保存采购预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMatPrePayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新保存采购预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMatPrePayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新保存采购预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMatPrePayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除保存采购预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMatPrePayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除保存采购预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMatPrePayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新保存采购预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateMatPrePayMain(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集保存采购预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatPrePayMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象保存采购预付款单的主要信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public MatPrePayMain queryMatPrePayMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取保存采购预付款单的主要信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatPrePayMain
	 * @throws DataAccessException
	*/
	public MatPrePayMain queryMatPrePayMainByUniqueness(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询预付款单主表的当前Id
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatPrePayMainMaxId() throws DataAccessException;
	/**
	 * 修改页面  回值查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatPrePayMainReturnUpdate(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 入库单查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatCommonIn(Map<String, Object> page) throws DataAccessException;
	/**
	 * 入库单明细查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatInDetail(Map<String, Object> page) throws DataAccessException;
	/**
	 * 审核、消审
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateMatPrePayMainState(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 生成付款单流水号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String setMatPrePayMainNo(Map<String, Object> mapVo) throws DataAccessException;
	
	//入库模板打印（包含主从表）
	public String queryPrePayByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
		
}
