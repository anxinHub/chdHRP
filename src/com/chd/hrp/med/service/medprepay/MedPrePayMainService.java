/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.medprepay;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.med.entity.MedPrePayMain;
/**
 * 
 * @Description:
 * 保存采购预付款单的主要信息
 * @Table:
 * MED_BILL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedPrePayMainService {

	/**
	 * @Description 
	 * 添加保存采购预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMedPrePayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加保存采购预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMedPrePayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新保存采购预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMedPrePayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新保存采购预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMedPrePayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除保存采购预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMedPrePayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除保存采购预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMedPrePayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新保存采购预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateMedPrePayMain(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集保存采购预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedPrePayMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象保存采购预付款单的主要信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public MedPrePayMain queryMedPrePayMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取保存采购预付款单的主要信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedPrePayMain
	 * @throws DataAccessException
	*/
	public MedPrePayMain queryMedPrePayMainByUniqueness(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询预付款单主表的当前Id
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedPrePayMainMaxId() throws DataAccessException;
	/**
	 * 修改页面  回值查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedPrePayMainReturnUpdate(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 入库单查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedCommonIn(Map<String, Object> page) throws DataAccessException;
	/**
	 * 入库单明细查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedInDetail(Map<String, Object> page) throws DataAccessException;
	/**
	 * 审核、消审
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateMedPrePayMainState(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 生成付款单流水号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String setMedPrePayMainNo(Map<String, Object> mapVo) throws DataAccessException;
	
	//入库模板打印（包含主从表）
	public String queryPrePayByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
		
}
