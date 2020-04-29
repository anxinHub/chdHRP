/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.account.report;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.account.report.MedAccountReportInvStockToFimMapper;
import com.chd.hrp.med.service.account.report.MedAccountReportInvStockToFimService;

/**
 * 
 * @Description:
 * 药品库存汇总表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medAccountReportInvStockToFimService")
public class MedAccountReportInvStockToFimServiceImpl implements MedAccountReportInvStockToFimService {

	private static Logger logger = Logger.getLogger(MedAccountReportInvStockToFimServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "medAccountReportInvStockToFimMapper")
	private final MedAccountReportInvStockToFimMapper medAccountReportInvStockToFimMapper = null;
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectMedAccountReportInvStockTofim(Map<String, Object> mapVo) {
				
		List<Map<String, Object>> list = null;
		try {
			medAccountReportInvStockToFimMapper.queryMedAccountReportInvStockToFim(mapVo);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			list = (List<Map<String, Object>>) mapVo.get("resultData");
			
		} catch (NoTransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}
		return ChdJson.toJson(list);
		
	}


	@Override
	public String queryMedAccountReportInvStockToFimColumns(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = medAccountReportInvStockToFimMapper.queryMedAccountReportInvStockToFimColumns(mapVo);
		return ChdJson.toJsonLower(list);
	}

}
