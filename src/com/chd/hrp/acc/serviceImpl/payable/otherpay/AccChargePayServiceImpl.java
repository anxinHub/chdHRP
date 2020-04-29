package com.chd.hrp.acc.serviceImpl.payable.otherpay;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.payable.otherpay.BudgChargeApplyDetMapper;
import com.chd.hrp.acc.dao.payable.otherpay.BudgUnitMapper;
import com.chd.hrp.acc.service.payable.otherpay.AccChargePayService;

@Service("accChargePayService")
public class AccChargePayServiceImpl implements AccChargePayService {

	private static Logger logger = Logger.getLogger(AccChargePayServiceImpl.class);
	
	@Resource(name = "budgUnitMapper")
	private final BudgUnitMapper budgUnitMapper = null;
	
	@Resource(name = "budgChargeApplyDetMapper")
	private final BudgChargeApplyDetMapper budgChargeApplyDetMapper = null;
	
	/**
	 * 更新收款单位
	 */
	@Override
	public String updateBudgUnit(Map<String, Object> paraMap) throws DataAccessException {
		try{
			List<Map> rowList = ChdJson.fromJsonAsList(Map.class, paraMap.get("paramVo").toString());
			for(Map<String, Object> row : rowList){
				if(!row.get("card_no").toString().matches("\\d+")){
					return "{\"error\":\"银行账号只能是数字.\",\"state\":\"false\"}";
				}
			}
			
			budgUnitMapper.updateBatch(rowList);
			budgChargeApplyDetMapper.updateBatch(rowList);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

}
