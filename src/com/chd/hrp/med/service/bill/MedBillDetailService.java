/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.bill;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.med.entity.MedBillDetail;
/**
 * 
 * @Description:
 * 保存一个发票对应的入库单，及金额
 * @Table:
 * MED_BILL_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedBillDetailService {

	/**
	 * @Description 
	 * 添加保存一个发票对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMedBillDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加保存一个发票对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMedBillDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新保存一个发票对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMedBillDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新保存一个发票对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMedBillDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除保存一个发票对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMedBillDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除保存一个发票对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMedBillDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新保存一个发票对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateMedBillDetail(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集保存一个发票对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedBillDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象保存一个发票对应的入库单，及金额<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public MedBillDetail queryMedBillDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取保存一个发票对应的入库单，及金额<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedBillDetail
	 * @throws DataAccessException
	*/
	public MedBillDetail queryMedBillDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 *根据 发票ID 查询其明细中的 入库单ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryIn_id(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改页面  查询发票明细信息
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedBillIn(Map<String, Object> page) throws DataAccessException ;
}
