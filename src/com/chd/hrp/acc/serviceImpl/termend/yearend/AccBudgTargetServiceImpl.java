/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.termend.yearend;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.termend.yearend.AccBudgTargetMapper;
import com.chd.hrp.acc.service.termend.yearend.AccBudgTargetService;

/**
 * @Title. @Description. 
*  年度授权/直接支出预算下达数<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accBudgTargetService")
public class AccBudgTargetServiceImpl implements AccBudgTargetService {

	private static Logger logger = Logger.getLogger(AccBudgTargetServiceImpl.class);

	@Resource(name = "accBudgTargetMapper")
	private final AccBudgTargetMapper accBudgTargetMapper = null;

	/**
	 * @Description 添加
	 * @param AccBudgTarget entityMap
	 * @return Map<String, Object>
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> addAccBudgTarget(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "fasle");
		try {
			//校验数据是否重复
			int flag = accBudgTargetMapper.existsAccBudgTarget(entityMap);
			
			if(flag > 0){
				retMap.put("error", "本年度已存在预算指标不能重复添加！");
				return retMap;
			}

			entityMap.put("target_id", UUIDLong.absStringUUID());
			entityMap.put("dircet_money", 0);
			entityMap.put("grant_money", 0);
			entityMap.put("state", 0);
			
			accBudgTargetMapper.addAccBudgTarget(entityMap);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功");
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}
		
		return retMap;
	}

	/**
	 * @Description 修改
	 * @param AccBudgTarget entityMap
	 * @return Map<String, Object>
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> updateAccBudgTarget(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "fasle");
		try {
			//校验数据是否重复
			int flag = accBudgTargetMapper.existsAccBudgTarget(entityMap);
			
			if(flag > 0){
				retMap.put("error", "本年度已存在预算指标不能重复添加！");
				return retMap;
			}
			
			accBudgTargetMapper.updateAccBudgTarget(entityMap);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功");
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}
		
		return retMap;
	}

	/**
	 * @Description 查询
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccBudgTarget(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = accBudgTargetMapper.queryAccBudgTarget(entityMap);
		return ChdJson.toJson(list);
	}

	/**
	 * @Description 精准查询
	 * @param  entityMap
	 * @return Map<String, Object>
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> queryAccBudgTargetByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return accBudgTargetMapper.queryAccBudgTargetByCode(entityMap);
	}

	/**
	 * @Description 删除
	 * @param  Map
	 * @return Map<String, Object>
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> deleteAccBudgTarget(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "fasle");
		try {
			//获取数据状态
			int flag = accBudgTargetMapper.queryAccBudgTargetState(entityMap);
			if(flag != 0){
				retMap.put("error", "本年度预算指标已审核不能删除！");
				return retMap;
			}
			//删除数据
			accBudgTargetMapper.deleteAccBudgTarget(entityMap);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}
		
		return retMap;
	}

	/**
	 * @Description 审核/消审
	 * @param  Map
	 * @return Map<String, Object>
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> auditAccBudgTarget(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "fasle");
		try {
			//获取数据状态
			int flag = accBudgTargetMapper.queryAccBudgTargetState(entityMap);
			//如果是消审操作则需判断本年度是否已生成凭证
			if(entityMap.get("state") != null && "0".equals(entityMap.get("state").toString())){
				if(flag != 1){
					retMap.put("error", "本年度预算指标未审核不能消审！");
					return retMap;
				}
				flag = accBudgTargetMapper.existsVouchByTemplateYear(entityMap);
				
				if(flag > 0){
					retMap.put("error", "本年度已生成转财政拨款收入凭证不能消审！");
					return retMap;
				}
				entityMap.put("checker", null);
				entityMap.put("check_date", null);
			}else{
				if(flag != 0){
					retMap.put("error", "本年度预算指标已审核不能重复审核！");
					return retMap;
				}
				entityMap.put("checker", SessionManager.getUserId());
				entityMap.put("check_date", new Date());
			}
			
			//审核/消审
			accBudgTargetMapper.auditAccBudgTarget(entityMap);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}
		
		return retMap;
	}

	/**
	 * @Description 获取实际支出金额
	 * @param  Map
	 * @return Map<String, Object>
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> updateAccBudgTargetForMoney(Map<String, Object> entityMap)throws DataAccessException{ 
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "fasle");
		try {
			//获取数据状态
			Map<String, Object> accBudgTarget = accBudgTargetMapper.queryAccBudgTargetByCode(entityMap);
			if(accBudgTarget.get("state") != null && "1".equals(accBudgTarget.get("state").toString())){
				retMap.put("error", "本年度预算指标已审核不能获取直接支付数！");
				return retMap;
			}
			
			entityMap.put("acc_year", accBudgTarget.get("acc_year").toString());
			
			//获取年度授权支付实际发生额
			Double grant_money = accBudgTargetMapper.querySumDebitBySubj1011(entityMap);
			grant_money = grant_money == null ? 0 : grant_money;
			
			//获取年度财政拨款收入贷方累计发送额
			Double sum_money = accBudgTargetMapper.querySumCreditBySubj4001(entityMap);
			sum_money = sum_money == null ? 0 : sum_money;
			
			//获取年度直接支付实际发生额
			Double dircet_money = sum_money - grant_money;
			
			accBudgTarget.put("grant_money", grant_money);
			accBudgTarget.put("dircet_money", dircet_money);
			
			//存入数据
			accBudgTargetMapper.updateAccBudgTarget(accBudgTarget);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}
		
		return retMap;
	}
}
