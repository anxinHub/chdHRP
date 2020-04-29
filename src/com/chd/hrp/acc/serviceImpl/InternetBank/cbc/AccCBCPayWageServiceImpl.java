package com.chd.hrp.acc.serviceImpl.InternetBank.cbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.InternetBank.cbc.CBCEnterpriseSendData;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.dao.InternetBank.cbc.AccCBCPayWageMapper;
import com.chd.hrp.acc.service.InternetBank.cbc.AccCBCPayWageService;

 
@Service("accCBCPayWageService")
public class AccCBCPayWageServiceImpl implements AccCBCPayWageService{
	public static Logger logger = Logger.getLogger(AccCBCPayWageServiceImpl.class);
	@Resource(name="accCBCPayWageMapper")
	private final AccCBCPayWageMapper accCBCPayWageMapper = null;
	
 
	@Override
	public String queryAccCBCPayWageTree(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String,Object>> wagePayList = accCBCPayWageMapper.queryAccWagePayTree(entityMap);

		return JSONArray.toJSONString(wagePayList);

	}
	@Override
	public String accNetPayWageToCBC(Map<String, Object> mapVo) {
		// 生成选项 第一步 查询要组装的数据
		try {
			/**
			 * payFlag 获取支付页面信息
			 * 1、0 从工资支付界面支付 
			 * 2、1从指令状态支付
			 * */
			String payFlag = mapVo.get("payFlag").toString();					
			
			List<Map<String,Object>> wageList = new ArrayList<Map<String,Object>>();
			//请求交易的用途，分为：工资、借款、报销、物流材料付款、固定资产付款
			mapVo.put("use_name", "工资");
			//请求交易码 6W8040:批量转账
			mapVo.put("tx_code", "6W8040");
			
			if("0".equals(payFlag)){
				
				String sql = " nvl("+mapVo.get("item_code")+",0) > 0 ";
				
				mapVo.put("sql", sql);
				
				wageList = accCBCPayWageMapper.queryAccWagePay(mapVo);
				
				if(wageList.size()==0){
					
					return "{\"msg\":\"当前会计期间内 没有支付数据.\",\"state\":\"true\"}";
					
				}
				
			}
			
			Double amount = 0.0;
			//获取请求序列码
			String request_sn=getOrderIdByUUId();
			
			mapVo.put("request_sn", request_sn);
			
			mapVo.put("opt_user", SessionManager.getUserId());
			
			if(wageList.size()>800){
				
				List<Map<String,Object>> wagePayList = new  ArrayList<Map<String,Object>>();
				
				int j=0;
				
				for (int i = 0; i < wageList.size(); i++) {
					
					wageList.get(i).put("count", ++j);
					
					wagePayList.add(wageList.get(i));
					
					wageList.get(i).put("request_sn", request_sn);
					
					amount = amount + Double.parseDouble(wageList.get(i).get("payamt").toString()) ;
					
					if(wagePayList.size()==800){
						
						Map<String,Object> rspMap = addAccCBCWag(mapVo,wagePayList,amount);
						
					}
				}
				
				if(wagePayList.size()>0){
					
					Map<String,Object> rspMap = addAccCBCWag(mapVo,wagePayList,amount);
					
				}
				
			}else{
				
				for (Map<String, Object> map : wageList) {
					
					amount = amount + Double.parseDouble(map.get("payamt").toString());
					
				}
				
				Map<String,Object> rspMap = addAccCBCWag(mapVo,wageList,amount);
				
			}
			
		} catch (DataAccessException e) {

			throw new SysException(e);
		}
		
		return null;
	}
	
	public  Map<String,Object> addAccCBCWag(Map<String,Object> mapVo, List<Map<String,Object>> wagePayList ,Double amount ) {
		//组装报文
		Map<String,Object> rspMap;
		try {
			CBCEnterpriseSendData sd =  CBCEnterpriseSendData.getInstance();
			mapVo.put("amount", amount);
			mapVo.put("count", wagePayList.size());
			//行内转账收款账户户名校验 1:校验 0：不校验
			mapVo.put("chnl_type", 1);
			//1:超级网银（即：小于等于5万的跨行转账走超级网银通道，5万以上走人行大额系统。）0:默认
			mapVo.put("chk_recvname", 0);
			//测试数据调试，调通后删除  两行
			mapVo.put("hrp_state", 0);
			mapVo.put("cbc_no1", "330000000");
			accCBCPayWageMapper.addAccCBCWag(mapVo);
			accCBCPayWageMapper.addAccCBCWagRd(wagePayList);
			rspMap = sd.getCCB(mapVo, wagePayList);
			List<Map<String,Object>>responseList =(List<Map<String, Object>>) rspMap.get("responseList");
			accCBCPayWageMapper.updateAccCBCWagRd(responseList);
			accCBCPayWageMapper.updateAccCBCWag(rspMap);
		} catch (Exception e) {
			logger.debug(e);
			throw new SysException(e);
		}
		return rspMap ; 
	}
	 public static String getOrderIdByUUId() {
         int machineId = 1;//最大支持1-9个集群机器部署
         int hashCodeV = UUID.randomUUID().toString().hashCode();
         if(hashCodeV < 0) {//有可能是负数
             hashCodeV = - hashCodeV;
         }
         // 0 代表前面补充0     
         // 4 代表长度为4     
         // d 代表参数为正数型
         return machineId + String.format("%015d", hashCodeV);
     }

		@Override
		public String queryAccWagePay(Map<String, Object> entityMap) throws DataAccessException {
				List<Map<String, Object>> list = accCBCPayWageMapper.queryAccWagePay(entityMap);
				return ChdJson.toJson(list);
		}
		@Override
		public String queryAccBankForInternet(Map<String, Object> mapVo) {
			List<Map<String,Object>> list = accCBCPayWageMapper.queryAccBankForInternet(mapVo);
			return ChdJson.toJsonLower(list);
		}
		@Override
		public String queryAccCBCWageRd(Map<String, Object> mapVo) {
			List<Map<String,Object>> list =accCBCPayWageMapper.queryAccCBCPayWageRd(mapVo);
			return ChdJson.toJsonLower(list);
		}
		@Override
		public String updateAccCBCPayWage(Map<String, Object> mapVo) {
			CBCEnterpriseSendData sd =  CBCEnterpriseSendData.getInstance();
			
			return null;
		}

}
