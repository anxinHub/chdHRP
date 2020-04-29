package com.chd.hrp.mat.serviceImpl.account.report;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.mat.dao.account.report.MatAccountReportInvTranMapper;
import com.chd.hrp.mat.dao.account.report.MatAccountReportReducedBudgMapper;
import com.chd.hrp.mat.service.account.report.MatAccountReportInvTranService;
import com.chd.hrp.mat.service.account.report.MatAccountReportReducedBudgService;

/**
 * 
 * @Description:
 * 预算降本报表
 * @Table:
 * @Author: weixiaofeng
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matAccountReportReducedBudgService")
public class MatAccountReportReducedBudgServiceImpl implements MatAccountReportReducedBudgService {

	private static Logger logger = Logger.getLogger(MatAccountReportReducedBudgServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matAccountReportReducedBudgMapper")
	private final MatAccountReportReducedBudgMapper matAccountReportReducedBudgMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryReducedBudg(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = matAccountReportReducedBudgMapper.queryReducedBudg(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
	/**
	 * 报表打印
	 */
	@Override
	public List<Map<String, Object>> queryReducedBudgPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		List<Map<String, Object>> list = JsonListMapUtil.toListMapLower(matAccountReportReducedBudgMapper.queryReducedBudg(entityMap));		
		
		return list;
	}
	
}
