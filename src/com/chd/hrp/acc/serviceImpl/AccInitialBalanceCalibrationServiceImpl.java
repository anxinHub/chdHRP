/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccInitialBalanceCalibrationMapper;
import com.chd.hrp.acc.dao.AccParaMapper;
import com.chd.hrp.acc.entity.AccInitialBalanceCalibration;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.acc.service.AccInitialBalanceCalibrationService;

/**
* @Title. @Description.
* 系统参数<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accInitialBalanceCalibrationService")
public class AccInitialBalanceCalibrationServiceImpl implements AccInitialBalanceCalibrationService {

	private static Logger logger = Logger.getLogger(AccInitialBalanceCalibrationServiceImpl.class);
	
	@Resource(name = "accInitialBalanceCalibrationMapper")
	private final AccInitialBalanceCalibrationMapper accInitialBalanceCalibrationMapper = null;

	@Resource(name = "accParaMapper")
	private final AccParaMapper accParaMapper = null;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectInitialBalanceCalibration(Map<String, Object> entityMap)
			throws DataAccessException {
		
		
		entityMap.put("para_flag", MyConfig.getSysPara("018"));
		
		accInitialBalanceCalibrationMapper.collectInitialBalanceCalibration(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJson((List<AccInitialBalanceCalibration>)entityMap.get("objdata"));
		
	}
    
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectBalanceAdjust(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("para_code", "018");
		
		entityMap.put("para_flag", MyConfig.getSysPara("018"));
		
		//entityMap.put("subj_code", "124124");
		
		accInitialBalanceCalibrationMapper.collectBalanceAdjust(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJson((List<AccInitialBalanceCalibration>)entityMap.get("objdata"));
		
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectInitialBalanceCalibrationPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("mod_code", "01");
		
		entityMap.put("para_code", "018");
		
		
		entityMap.put("para_flag", MyConfig.getSysPara("018"));
		
		accInitialBalanceCalibrationMapper.collectInitialBalanceCalibrationPrint(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return (List<Map<String, Object>>)entityMap.get("objdata");
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectBalanceAdjustByPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("para_code", "018");
		
		entityMap.put("para_flag", MyConfig.getSysPara("018"));
		accInitialBalanceCalibrationMapper.collectBalanceAdjustByPrint(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return (List<Map<String,Object>>)entityMap.get("objdata");
	}
	
}
