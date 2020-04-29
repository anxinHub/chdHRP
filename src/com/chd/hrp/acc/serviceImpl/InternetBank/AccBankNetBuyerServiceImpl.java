/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.InternetBank;
 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.InternetBank.icbc.EnterpriseQueryData;
import com.chd.base.InternetBank.icbc.EnterpriseSendData;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.dao.InternetBank.AccBankNetBuyerMapper;
import com.chd.hrp.acc.dao.autovouch.AccAutoVouchMaintainMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchMapper;
import com.chd.hrp.acc.service.InternetBank.AccBankNetBuyerService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description<BR>
 * @Author: LiuYingDuo 
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accBankNetBuyerService")
public class AccBankNetBuyerServiceImpl implements AccBankNetBuyerService {

	private static Logger logger = Logger.getLogger(AccBankNetBuyerServiceImpl.class);

	@Resource(name = "accBankNetBuyerMapper")
	private final AccBankNetBuyerMapper accBankNetBuyerMapper = null;
	
	@Resource(name = "accAutoVouchMaintainMapper")
	private final AccAutoVouchMaintainMapper accAutoVouchMaintainMapper = null;
	
	@Resource(name = "accVouchMapper")
	private final AccVouchMapper accVouchMapper = null;
	
	Integer num = 0;

	@Override
	public String queryMatPayMain(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String, Object>> list = accBankNetBuyerMapper.queryMatPayMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = accBankNetBuyerMapper.queryMatPayMain(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}

	}

	@Override
	public String queryAccBankNetBuyer(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String, Object>> list = accBankNetBuyerMapper.queryAccBankNetBuyer(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = accBankNetBuyerMapper.queryAccBankNetBuyer(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}

	@Override
	public String addAccBankNetBuyer(Map<String, Object> entityMap) throws DataAccessException {

		String iseqno = DateUtil.dateToString(new Date(), "yyyyMMdd");//材料付款支付 只支持单笔支付

		try {
			
			String transCode = "PAYENT";String version = "0.0.0.1";
			
			EnterpriseSendData esd = EnterpriseSendData.getInstance();
			
			String paramVo = String.valueOf(entityMap.get("paramVo"));
			
			String[] paramVoArr = paramVo.split(",");

			for (int i = 0; i< paramVoArr.length; i++) {
				
				List<Map<String, Object>> payList = new ArrayList<Map<String, Object>>();

				List<Map<String, Object>> rdList = new ArrayList<Map<String, Object>>();
				
				String[] ids = paramVoArr[i].split("@");
				
				Map<String, Object> payMain = new HashMap<String, Object>();
				
				//构建 acc_bank_net_buyer

				payMain.put("group_id", entityMap.get("group_id"));payMain.put("hos_id", entityMap.get("hos_id"));
				
				payMain.put("copy_code", entityMap.get("copy_code"));payMain.put("acc_year", entityMap.get("acc_year"));

				String fSeqNo = getFSeqNo(entityMap);
				
				payMain.put("PackageID", fSeqNo);
				
				payMain.put("TransCode", transCode);payMain.put("Version", version);
				
				payMain.put("fseqno", fSeqNo);
				
				payMain.put("serialno", "");payMain.put("retcode", "");payMain.put("retmsg", "");
				
				payMain.put("trandate", DateUtil.getCurrenDate("yyyyMMdd"));payMain.put("trantime", DateUtil.getCurrenDate("HHmmssSSS"));
				
				payMain.put("onlbatf", "1");
				
				payMain.put("settlemode", entityMap.get("settlemode"));
				
				payMain.put("totalnum", "1");
				
				BigDecimal sumAmountDetail= new BigDecimal(ids[2]);BigDecimal transAmtDetail = sumAmountDetail.multiply(new BigDecimal(100));
				
				payMain.put("totalamt", transAmtDetail.intValue());
				
				payMain.put("reqreserved1", "");payMain.put("reqreserved2", "");
				
				payMain.put("zipFlag", "&zipFlag=0");
				
				payList.add(payMain);
				
				//构建 acc_bank_net_buyer_rd
				
				Map<String,Object> rdMap = new HashMap<String,Object>();

//				rdMap.put("vouch_id", ids[4]);rdMap.put("vouch_state", ids[5]);
//				
//				rdMap.put("state", "2");rdMap.put("cash", "0");
//				
//				rdMap.put("cash_user", SessionManager.getUserId());rdMap.put("cashe_date", new Date());
				
				rdMap.put("group_id", entityMap.get("group_id"));rdMap.put("hos_id", entityMap.get("hos_id"));
				
				rdMap.put("copy_code", entityMap.get("copy_code"));rdMap.put("acc_year", entityMap.get("acc_year"));
				
				rdMap.put("fseqno", fSeqNo);rdMap.put("iseqno", getISeqNo(i));
				
				rdMap.put("orderno", "");rdMap.put("reimburseno", "");rdMap.put("reimbursenum", "1");
				
				rdMap.put("startdate", "");rdMap.put("starttime", "");
				
				rdMap.put("paytype", entityMap.get("paytype"));rdMap.put("payaccno", entityMap.get("payaccno"));
				
				rdMap.put("payaccnamecn", entityMap.get("payaccnamecn"));rdMap.put("payaccnameen", "");
				
				rdMap.put("recaccno", entityMap.get("recaccno"));rdMap.put("recaccnameen", "");
				
				rdMap.put("recaccnamecn", entityMap.get("recaccnamecn"));
				
				rdMap.put("sysioflg", entityMap.get("sysioflg"));rdMap.put("issamecity", entityMap.get("issamecity"));rdMap.put("prop", "0");
				
				rdMap.put("recicbccode", entityMap.get("recicbccode"));rdMap.put("reccityname", entityMap.get("reccityname"));
				
				rdMap.put("recbankno", entityMap.get("recbankno"));rdMap.put("recbankname", entityMap.get("recbankname"));
				
				rdMap.put("currtype", entityMap.get("currtype"));
				
				rdMap.put("payamt", transAmtDetail.intValue());rdMap.put("usecode", "");
				
				rdMap.put("usecn", "采购款");rdMap.put("ensummary", "");
				
				rdMap.put("postscript", entityMap.get("postscript"));rdMap.put("summary", entityMap.get("summary"));
				
				rdMap.put("ref", "");rdMap.put("oref", "");
				
				rdMap.put("erpsqn", ids[0]);rdMap.put("buscode", "");

				rdMap.put("erpcheckno", "");rdMap.put("crvouhtype", "");rdMap.put("represerved4", "");
				
				rdMap.put("crvouhno", entityMap.get("new_sup_id").toString());//存支付供应商的ID和NO
				
				rdMap.put("crvouhname", "");rdMap.put("represerved3", "");
				
				rdMap.put("result", "");rdMap.put("iretcode", "");rdMap.put("iretmsg", "");

				rdList.add(rdMap);

				accBankNetBuyerMapper.addBatchAccBankNetBuyer(payList);
				
				accBankNetBuyerMapper.addBatchAccBankNetBuyerRd(rdList);
				
				Map reMap = esd.getICBC(payList, rdList);
				
				//更新主表
				Map<String,Object> mapUpdate = payList.get(0);
				
				mapUpdate.put("retcode", reMap.get("RetCode"));
				
				mapUpdate.put("retmsg",  reMap.get("RetMsg"));
				
				accBankNetBuyerMapper.updateAccBankNetBuyer(mapUpdate);
				
				//更新明细表
				List<Map<String, Object>> updateRdList = new ArrayList<Map<String, Object>>();
				
				rdMap.putAll(reMap);
				
				updateRdList.add(rdMap);
				
				accBankNetBuyerMapper.updateBatchAccBankNetBuyerRd(updateRdList);
				
			}
			
			return "{\"msg\":\"支付数据提交成功 请到指令查询查看具体支付状态.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"支付失败 , 请重新尝试\"}");

		}

	}

	public long getTotalAmt(String[] paramVoArr){//获取总金额 转化成分为单位
		
		Map<String,Long> map = new HashMap<String,Long>();
		
		long totalamt = 0;
		
		for (int i = 0; i< paramVoArr.length; i++) {

			String[] ids = paramVoArr[i].split("@");

			BigDecimal sumAmountDetail= new BigDecimal(ids[2]);
			
			BigDecimal transAmtDetail = sumAmountDetail.multiply(new BigDecimal(100));
			
			totalamt = totalamt+transAmtDetail.intValue();

		}
		
		
		return totalamt;
		
	}
	public String getISeqNo(int i) {// 1.流水号年月日6位流水
		String no = null;
		if (i < 10) {
			no = "000000" + i + 1;
		} else if (i < 100 && i >= 10) {
			no = "00000" + i + 1;
		} else if (i < 1000 && i >= 100) {
			no = "0000" + i + 1;
		} else if (i < 10000 && i >= 1000) {
			no = "000" + i + 1;
		} else if (i < 100000 && i >= 10000) {
			no = "00" + i + 1;
		} else if (i < 1000000 && i >= 100000) {
			no = "0" + i + 1;
		} else if (i < 10000000 && i >= 1000000) {
			no = "" + i + 1;
		}
		return no;
	}

	public String getFSeqNo(Map<String, Object> entityMap) {// 生成指令包序列号
		
		String feqNo="000000";

		Date settle_date = DateUtil.getCurrenDate("yyyy-MM-dd HH:mm:ss:SSS");// DateUtil.dateToString(, "yyyy-MM-dd HH:mm:ss:SSS");

		String fseqNo = accBankNetBuyerMapper.queryMaxFSeqNo(entityMap);// 计算指令包序列号

		String day = DateUtil.dateToString(new Date(), "yyyyMMdd");

		if (!"0".equals(fseqNo)) {
			
			fseqNo = fseqNo.substring(0, fseqNo.length() - 1);

			String fseqNoSub = fseqNo.substring(fseqNo.length() - 14, fseqNo.length() - 6);

			if (fseqNoSub.equals(day)) {

				String str =fseqNo.substring(fseqNo.length()-14, fseqNo.length());
				
				long seq_no = 0;
				
				if(entityMap.get("multiply") != null){

					seq_no= Long.parseLong(str) + Long.parseLong(entityMap.get("multiply").toString());

				}else{

					seq_no= Long.parseLong(str) + 1;
				}
				
				fseqNo = entityMap.get("group_id").toString()+entityMap.get("hos_id") +seq_no;

			} else {
				
				num+=1;

				fseqNo = entityMap.get("group_id").toString() + entityMap.get("hos_id") + day +feqNo.substring(0, 6-num.toString().length())+num;

			}

		} else {

			num+=1;
			
			fseqNo = entityMap.get("group_id").toString() + entityMap.get("hos_id") + day + feqNo.substring(0, 6-num.toString().length())+num;;
		}

		return fseqNo+"M";

	}

	@Override
	public String updateAccBankNetBuyer(List<Map<String, Object>> entityMap) throws DataAccessException {

		try {

			List<Map<String, Object>> vouchList = new ArrayList<Map<String,Object>>();
			
			EnterpriseQueryData esd =  EnterpriseQueryData.getInstance();
			
			for(Map<String, Object> mapVo :entityMap){
				
				mapVo.put("Version", "0.0.0.1");
				
				mapVo.put("TransCode", "QPAYENT");
				
				mapVo.put("PackageID", "Q"+mapVo.get("fseqno"));
				
				Map<String,Object> map = esd.getICBC(mapVo);
				
				map.put("group_id", mapVo.get("group_id"));
				
				map.put("hos_id", mapVo.get("hos_id"));
				
				map.put("copy_code", mapVo.get("copy_code"));
				
				map.put("acc_year", mapVo.get("acc_year"));
				
				map.put("fseqno", mapVo.get("fseqno"));
				
				map.put("vouch_id", mapVo.get("vouch_id"));map.put("vouch_state", mapVo.get("vouch_state"));
				
				map.put("state", "2");map.put("cash", "0");map.put("cash_user",  mapVo.get("user_id"));
				
				map.put("cashe_date", new Date());map.put("log_table", mapVo.get("log_table"));

				accBankNetBuyerMapper.updateAccBankNetBuyer(map);//更新主表

				List<Map<String, Object>> rdList = (List<Map<String, Object>>) map.get("rdList");
				
				if(rdList !=null && rdList.size()>0){
					
					List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
					
					for(Map<String, Object> rdMap:rdList){
						
						rdMap.put("group_id", mapVo.get("group_id"));
						
						rdMap.put("hos_id", mapVo.get("hos_id"));
						
						rdMap.put("copy_code", mapVo.get("copy_code"));
						
						rdMap.put("acc_year", mapVo.get("acc_year"));
						
						rdMap.put("fseqno", mapVo.get("fseqno"));
						
						updateList.add(rdMap);
						
						if("7".equals(rdMap.get("result").toString()) 
								&& mapVo.get("vouch_id")!=null 
								&& !"null".equals(mapVo.get("vouch_id").toString().toLowerCase()) 
								&& !"undefined".equals(mapVo.get("vouch_id").toString())){
							vouchList.add(map);
						}

					}
					
					accBankNetBuyerMapper.updateBatchAccBankNetBuyerRd(rdList);//更新明细表

				}

			}
			
			//更新凭证 出纳签字
			
			String vouchMsg = "";
			
			if(vouchList.size()>0){
			
				int count=accVouchMapper.queryAccVouchAuditCashByCheck(entityMap.get(0).get("group_id").toString(),entityMap.get(0).get("hos_id").toString(),entityMap.get(0).get("copy_code").toString(),2,vouchList);
				
				if(count>0){
					
					   return "{\"msg\":\"更新指令状态成功 当前状态不能签字。\",\"state\":\"false\"}";
								
				}
				
				accVouchMapper.updateBatchAccVouchCashier(vouchList);
				
				vouchMsg = " 凭证签字成功";
			}

			return "{\"msg\":\"更新指令状态成功"+vouchMsg+".\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\""+e.getMessage()+"\"}");

		}
	
	}

	@Override
	public String queryAccBankNetBuyerRd(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String, Object>> list = accBankNetBuyerMapper.queryAccBankNetBuyerRd(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = accBankNetBuyerMapper.queryAccBankNetBuyerRd(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}

	@Override
	public double sumTotalAmtByDay(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("nowDate", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		
		double totalAmt = accBankNetBuyerMapper.sumTotalAmtByDay(entityMap);
		
		return totalAmt;
	}
	
	@Override
	public List<Map<String, Object>> queryMatPayMainPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		if (entityMap.get("group_id") == null) {
			
			entityMap.put("group_id", SessionManager.getGroupId());
			
		}

		if (entityMap.get("hos_id") == null) {
			
			entityMap.put("hos_id", SessionManager.getHosId());
			
		}

		if (entityMap.get("copy_code") == null) {
			
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
		}

		List<Map<String, Object>> list =accBankNetBuyerMapper.queryMatPayMain(entityMap);

		return list;

	}

	@Override
	public List<Map<String, Object>> queryAccBankNetBuyerRdPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		if (entityMap.get("group_id") == null) {
			
			entityMap.put("group_id", SessionManager.getGroupId());
			
		}

		if (entityMap.get("hos_id") == null) {
			
			entityMap.put("hos_id", SessionManager.getHosId());
			
		}

		if (entityMap.get("copy_code") == null) {
			
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
		}

		List<Map<String, Object>> list =accBankNetBuyerMapper.queryAccBankNetBuyerRd(entityMap);

		return list;

	}

	@Override
	public String queryAccBankNetBuyerNum(Map<String, Object> entityMap)
			throws DataAccessException {

		try {
			
			String result_msg="";
			
			StringBuffer sbuffer= new StringBuffer();
			
			String paramVo = String.valueOf(entityMap.get("paramVo"));
			
			String[] paramVoArr = paramVo.split(",");
			
				for (int i = 0; i< paramVoArr.length; i++) {
					
					String[] ids = paramVoArr[i].split("@");
					
					Map<String, Object> payMain = new HashMap<String, Object>();
					
					payMain.put("group_id", entityMap.get("group_id"));payMain.put("hos_id", entityMap.get("hos_id"));
					
					payMain.put("copy_code", entityMap.get("copy_code"));payMain.put("acc_year", entityMap.get("acc_year"));

					payMain.put("apply_code", ids[3]);
					
					List<Map<String, Object>> rMap= accBankNetBuyerMapper.queryAccBankNetBuyerRd(payMain);
					
					if(rMap.size()>0&&("0".equals(rMap.get(0).get("res_state"))||"1".equals(rMap.get(0).get("res_state"))||"2".equals(rMap.get(0).get("res_state"))||"3".equals(rMap.get(0).get("res_state"))||"4".equals(rMap.get(0).get("res_state"))||"7".equals(rMap.get(0).get("res_state"))||"9".equals(rMap.get(0).get("res_state")))){
						
						sbuffer.append("材料付款单号【"+ids[3]+"】,");
					}
				}
				
				if(sbuffer.length()>0){
					
					result_msg=sbuffer.substring(0, sbuffer.length()-1).toString();
				}
				
				
			return "{\"result_msg\":\""+result_msg+"\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"支付失败 , 请重新尝试\"}");

		}

	}
}
