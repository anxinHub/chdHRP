/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.common;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

 

public interface BudgControlPlanExecService {
	
	/**
	 * 科室领用出库 返回消息查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryControlExecMatOutProcess(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 操作日期对应的年月的启始日期，结束日期，年启始日期，年结束日期，预算年，预算月
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryOperDateBeginEndDate(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 *  物资入库返回消息查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryControlExecMatInProcess(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 *  科室申领返回消息查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryControlExecMatApplyProcess(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 固定资产 入库确认环节控制  预算支出控制过程查看 返回消息查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryControlExecAssInProcess(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 物流采购计划 预算支出控制过程查看 返回消息查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryControlExecMatPurProcess(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 物流订单 预算支出控制过程查看 返回消息查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryControlExecMatOrderProcess(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 会计凭证 预算支出控制过程查看 返回消息查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryControlExecAccVouchProcess(Map<String, Object> mapVo) throws DataAccessException;
	
	
}
