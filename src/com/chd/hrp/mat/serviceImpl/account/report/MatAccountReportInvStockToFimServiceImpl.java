/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.account.report;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.account.report.MatAccountReportInvStockToFimMapper;
import com.chd.hrp.mat.service.account.report.MatAccountReportInvStockToFimService;

/**
 * 
 * @Description:
 * 材料库存汇总表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matAccountReportInvStockToFimService")
public class MatAccountReportInvStockToFimServiceImpl implements MatAccountReportInvStockToFimService {

	private static Logger logger = Logger.getLogger(MatAccountReportInvStockToFimServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "matAccountReportInvStockToFimMapper")
	private final MatAccountReportInvStockToFimMapper matAccountReportInvStockToFimMapper = null;
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectMatAccountReportInvStockTofim(Map<String, Object> mapVo) {
				
		List<Map<String, Object>> list = null;
		try {
			matAccountReportInvStockToFimMapper.queryMatAccountReportInvStockToFim(mapVo);
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
	public String queryMatAccountReportInvStockToFimColumns(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = matAccountReportInvStockToFimMapper.queryMatAccountReportInvStockToFimColumns(mapVo);
		return ChdJson.toJsonLower(list);
	}


	@Override
	public List<Map<String, Object>> collectMatAccountReportInvStockTofimPrint(Map<String, Object> mapVo) {
		
	List<Map<String, Object>> list = null;
	try {
		matAccountReportInvStockToFimMapper.queryMatAccountReportInvStockToFim(mapVo);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		list = (List<Map<String, Object>>) mapVo.get("resultData");
		
	} catch (NoTransactionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.error(e);
	}
	return list;
	}


	@Override
	public String collectMatStoreInvStock(Map<String, Object> mapVo) {
		//是否分库房结账
		Object isAccByStore = MyConfig.getSysPara("04045");
		//查询的月份总数 =(终止年-起始年)*12+终止月-起始月+1
		int qeury_month_amount= (Integer.parseInt(String.valueOf(mapVo.get("end_year")))-Integer.parseInt(String.valueOf(mapVo.get("begin_year"))))*12+Integer.parseInt(String.valueOf(mapVo.get("end_month")))-Integer.parseInt(String.valueOf(mapVo.get("begin_month")))+1;
		if (isAccByStore==null || "0".equals(isAccByStore.toString())) {
			//整体结账
			int acc_month_amount=matAccountReportInvStockToFimMapper.queryIsEntireAccByYearMonth(mapVo);//已经结账的月份数
			if (qeury_month_amount>acc_month_amount) {
				//包含未结账的月份
				return "{\"error\":\"当月未结账\"}";
			}
		}else{
			//分库房结账
			//是否按虚仓结账
			Object store_type = mapVo.get("store_type");
			if (store_type!=null && "1".equals(store_type.toString())) {
				//按虚仓结账
				List<Map<String, Object>> setStoresAccMonthAccount=matAccountReportInvStockToFimMapper.queryIsAccBySet(mapVo);//已经结账的月份数
				if (setStoresAccMonthAccount.size()==0) {
					return "{\"error\":\"当月未结账\"}";
				}else{
					for (Map<String, Object> map : setStoresAccMonthAccount) {
						if (qeury_month_amount>Integer.parseInt(String.valueOf(map.get("STORE_ACC_MONTH_AMOUNT")))) {
							//如果查询的月份大于实际存在的结账月份,继而判断 库房是不是在查询期间新启用的
							//库房开始启用时间到查询终止月份经历的月份总数
							int start_month_amount= (Integer.parseInt(String.valueOf(mapVo.get("end_year")))-Integer.parseInt(String.valueOf(map.get("START_YEAR"))))*12+Integer.parseInt(String.valueOf(mapVo.get("end_month")))-Integer.parseInt(String.valueOf(map.get("START_MONTH")))+1;
							if (start_month_amount>Integer.parseInt(String.valueOf(map.get("STORE_ACC_MONTH_AMOUNT")))) {
								//包含未结账的月份
								return "{\"error\":\"当月未结账\"}";
							}
						}
					}
				}
			}else{
				//按实仓结账
				int acc_month_amount=matAccountReportInvStockToFimMapper.queryIsAccByStore(mapVo);//已经结账的月份数
				if(qeury_month_amount>acc_month_amount){
					//包含未结账的月份
					return "{\"error\":\"当月未结账\"}";
				}
			}
		}
		
		
		List<Map<String, Object>> list = null;
		try {
			matAccountReportInvStockToFimMapper.collectMatStoreInvStock(mapVo);
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
	public List<Map<String, Object>> collectMatStoreInvStockPrint(Map<String, Object> mapVo) {
		//是否分库房结账
		Object isAccByStore = MyConfig.getSysPara("04045");
		//查询的月份总数 =(终止年-起始年)*12+终止月-起始月+1
		int qeury_month_amount= (Integer.parseInt(String.valueOf(mapVo.get("end_year")))-Integer.parseInt(String.valueOf(mapVo.get("begin_year"))))*12+Integer.parseInt(String.valueOf(mapVo.get("end_month")))-Integer.parseInt(String.valueOf(mapVo.get("begin_month")))+1;
		if (isAccByStore==null || "0".equals(isAccByStore.toString())) {
			//整体结账
			int acc_month_amount=matAccountReportInvStockToFimMapper.queryIsEntireAccByYearMonth(mapVo);//已经结账的月份数
			if (qeury_month_amount>acc_month_amount) {
				//包含未结账的月份
				return null;
			}
		}else{
			//分库房结账
			//是否按虚仓结账
			Object store_type = mapVo.get("store_type");
			if (store_type!=null && "1".equals(store_type.toString())) {
				//按虚仓结账
				List<Map<String, Object>> setStoresAccMonthAccount=matAccountReportInvStockToFimMapper.queryIsAccBySet(mapVo);//已经结账的月份数
				for (Map<String, Object> map : setStoresAccMonthAccount) {
					if (qeury_month_amount>Integer.parseInt(String.valueOf(map.get("STORE_ACC_MONTH_AMOUNT")))) {
						//如果查询的月份大于实际存在的结账月份,继而判断 库房是不是在查询期间新启用的
						//库房开始启用时间到查询终止月份经历的月份总数
						int start_month_amount= (Integer.parseInt(String.valueOf(mapVo.get("end_year")))-Integer.parseInt(String.valueOf(map.get("START_YEAR"))))*12+Integer.parseInt(String.valueOf(mapVo.get("end_month")))-Integer.parseInt(String.valueOf(map.get("START_MONTH")))+1;
						if (start_month_amount>Integer.parseInt(String.valueOf(map.get("STORE_ACC_MONTH_AMOUNT")))) {
							//包含未结账的月份
							return null;
						}
					}
				}
			}else{
				//按实仓结账
				int acc_month_amount=matAccountReportInvStockToFimMapper.queryIsAccByStore(mapVo);//已经结账的月份数
				if(qeury_month_amount>acc_month_amount){
					//包含未结账的月份
					return null;
				}
			}
		}
		List<Map<String, Object>> list = null;
		try {
			matAccountReportInvStockToFimMapper.collectMatStoreInvStock(mapVo);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			list = (List<Map<String, Object>>) mapVo.get("resultData");
		} catch (NoTransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}
		return list;
	}
}
