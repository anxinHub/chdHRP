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
import com.chd.hrp.acc.dao.InternetBank.AccBankNetWageMapper;
import com.chd.hrp.acc.dao.wagedata.AccWagePayMapper;
import com.chd.hrp.acc.entity.AccWagePay;
import com.chd.hrp.acc.service.InternetBank.AccBankNetWageService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accBankNetWageService")
public class AccBankNetWageServiceImpl implements AccBankNetWageService {

	private static Logger logger = Logger.getLogger(AccBankNetWageServiceImpl.class);

	@Resource(name = "accBankNetWageMapper")
	private final AccBankNetWageMapper accBankNetWageMapper = null;

	@Resource(name = "accWagePayMapper")
	private final AccWagePayMapper accWagePayMapper = null;

	@Override
	public String queryAccBankNetWage(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String,Object>> list = accBankNetWageMapper.queryAccBankNetWage(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = accBankNetWageMapper.queryAccBankNetWage(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}

	@Override
	public String queryAccBankNetWageTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder jsonResult = new StringBuilder();

		jsonResult.append("{Rows:[");

		//jsonResult.append("{'id':'0','pId':'0','name':'工资套'},");

		List<AccWagePay> wagePayList = accWagePayMapper.queryAccWagePayTree(entityMap);

		for (int i = 0; i < wagePayList.size(); i++) {

			AccWagePay accWagePay = wagePayList.get(i);

			if ((i + 1) == wagePayList.size()) {

				jsonResult.append("{'id':'" + accWagePay.getWage_code() + "','pId':'0','name':'" + accWagePay.getWage_code() + " " + accWagePay.getWage_name()
						+ "'}");

			} else {

				jsonResult.append("{'id':'" + accWagePay.getWage_code() + "','pId':'0','name':'" + accWagePay.getWage_code() + " " + accWagePay.getWage_name()
						+ "'},");

			}

		}

		jsonResult.append("]}");

		return jsonResult.toString();

	}

	@Override
	public String addAccBankNetWage(Map<String, Object> entityMap) throws DataAccessException {

		try {

			// 生成选项 第一步 查询要组装的数据
			String payFlag = (String)entityMap.get("payFlag");
			
			//payFlag =0 从工资支付界面支付 =1从指令状态支付
			List<Map<String,Object>> wageList = new ArrayList<Map<String,Object>>();

			if("0".equals(payFlag)){
				
				String sql = " nvl("+entityMap.get("item_code")+",0) > 0 ";
				
				entityMap.put("sql", sql);
				
				wageList = accBankNetWageMapper.queryAccWagePay(entityMap);
				
				if(wageList.size()==0){
					
					return "{\"msg\":\"当前会计期间内 没有支付数据.\",\"state\":\"true\"}";
					
				}
				
			}else if("1".equals(payFlag)){
				
				wageList = accBankNetWageMapper.queryAccBankNetWageRdBySeqNo(entityMap);

			}else if("2".equals(payFlag)){
				
				String paramVo = String.valueOf(entityMap.get("paramVo"));
				
				String[] paraSpl = paramVo.split(",");
				
				StringBuffer paraSb = new StringBuffer();
				
				for(int i = 0 ;i < paraSpl.length; i++){
					
					if(i != 0){
						
						paraSb.append(",");
					}
					
					paraSb.append("'"+paraSpl[i]+"'");
					
				}
				
				 String sql = " and iseqno in ("+paraSb.toString()+") ";
				 
				 entityMap.put("sql", sql);
				 
				 wageList = accBankNetWageMapper.queryAccBankNetWageRdBySeqNo(entityMap);

			}else if ("3".equals(payFlag)) {

				String paramVo = String.valueOf(entityMap.get("paramVo"));

				String[] paraSpl = paramVo.split(",");

				StringBuffer paraISb = new StringBuffer();
				
				StringBuffer paraFSb = new StringBuffer();
				
				StringBuffer paraIDSb = new StringBuffer();
				
				StringBuffer paraNOSb = new StringBuffer();

				for (int i = 0; i < paraSpl.length; i++) {

					if (i != 0) {

						paraISb.append(",");
						
						paraFSb.append(",");
						
						paraIDSb.append(",");
						
						paraNOSb.append(",");
					}

					paraISb.append("'" + paraSpl[i].split(";")[0] + "'");
					
					paraFSb.append("'" + paraSpl[i].split(";")[1] + "'");
					
					paraIDSb.append("'" + paraSpl[i].split(";")[2] + "'");
					
					paraNOSb.append("'" + paraSpl[i].split(";")[3] + "'");

				}

				String sql = " and fseqno in (" + paraFSb.toString() + ") and iseqno in (" + paraISb.toString() + ") "
						+ " and abnwr.erpsqn in (" + paraIDSb.toString() + ") and abnwr.buscode in (" + paraNOSb.toString() + ") ";

				entityMap.put("sql", sql);

				wageList = accBankNetWageMapper.queryAccBankNetWageRdBySeqNo(entityMap);

			}
			
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
			List<Map<String,List>> icbcList = new ArrayList<Map<String,List>>();//存库工商银行数据
			
			long totalamt = 0;

			for(int i =0 ;i<wageList.size();i++){
				
				Map<String,Object> map = wageList.get(i);
				
				BigDecimal sumAmountDetail= new BigDecimal(map.get("payamt").toString());
				
				BigDecimal transAmtDetail = sumAmountDetail.multiply(new BigDecimal(100));
				
				totalamt = totalamt+transAmtDetail.intValue();

				list.add(map);
				
				long totalamtNext = 0;
				
				if(i+1 < wageList.size()){
					
					Map<String,Object> totalamtNextMap = wageList.get(i+1);
					
					BigDecimal sumTotalamtNext= new BigDecimal(totalamtNextMap.get("payamt").toString());
					
					BigDecimal transTotalamtNext = sumTotalamtNext.multiply(new BigDecimal(100));
					
					transTotalamtNext.intValue();
				}

				if((i+1)%500 == 0){
					
					entityMap.put("totalamt", totalamt);
		
					Map<String,List> paymap = payMap(entityMap,list);
					
					accBankNetWageMapper.addBatchAccBankNetWage(paymap.get("payList"));
					
					accBankNetWageMapper.addBatchAccBankNetWageRd(paymap.get("rdList"));
					
					icbcList.add(paymap);//
	
					list.removeAll(list);
					
					totalamt = 0;
					
				}
				
			}
			
			if(totalamt > 0){//此判断解决数为整数的时候错误
				
				// 生成选项 第三步插入新的数据
				entityMap.put("totalamt", totalamt);
				
				Map<String,List> paymap = payMap(entityMap,list);
							
				accBankNetWageMapper.addBatchAccBankNetWage(paymap.get("payList"));
							
				accBankNetWageMapper.addBatchAccBankNetWageRd(paymap.get("rdList"));
				
				icbcList.add(paymap);//
				
			}
			
			
			//开始提交ICBC数据
			EnterpriseSendData esd = EnterpriseSendData.getInstance();
			
			for(int i=0;i<icbcList.size();i++){
				
				Map<String,List> icbcMap = icbcList.get(i);
				
				Map reMap = esd.getICBC(icbcMap.get("payList"), icbcMap.get("rdList"));
				
				Map<String,Object> mapUpdate = (Map<String, Object>)icbcMap.get("payList").get(0);
				
				mapUpdate.put("retcode", reMap.get("RetCode"));
				
				mapUpdate.put("retmsg",  reMap.get("RetMsg"));
				
				accBankNetWageMapper.updateAccBankNetWage(mapUpdate);
				
				Thread.sleep(2000);
				
			}
			
			return "{\"msg\":\"支付数据提交成功 请到指令查询查看具体状态.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"支付失败 , 请重新尝试\"}");

		}
	}
	
	public Map<String,List> payMap(Map<String, Object> entityMap ,List<Map<String,Object>> wageList){
		
		String iseqno = DateUtil.dateToString(new Date(), "yyyyMMdd");
		
		Map<String,List> paymap =new HashMap<String,List>();

		List<Map<String, Object>> payList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> payMain = new HashMap<String, Object>();

		if(wageList.size()>1){//判断是否需要压缩数据
			
			payMain.put("zipFlag", "&zipFlag=1");
			
		}else{
			
			payMain.put("zipFlag", "&zipFlag=0");
			
		}
		
		String fSeqNo = getFSeqNo(entityMap);
		
		payMain.put("PackageID", fSeqNo);
		
		payMain.put("TransCode", "PAYPER");
		
		payMain.put("Version", "0.0.0.1");

		//构建 acc_bank_net_wage

		payMain.put("group_id", entityMap.get("group_id"));payMain.put("hos_id", entityMap.get("hos_id"));
		
		payMain.put("copy_code", entityMap.get("copy_code"));payMain.put("acc_year", entityMap.get("acc_year"));
		
		payMain.put("acc_month", entityMap.get("acc_month"));

		payMain.put("fseqno", fSeqNo);
		
		payMain.put("serialno", "");payMain.put("retcode", "");payMain.put("retmsg", "");
		
		payMain.put("trandate", DateUtil.getCurrenDate("yyyyMMdd"));payMain.put("trantime", DateUtil.getCurrenDate("HHmmssSSS"));
		
		payMain.put("onlbatf", "1");
		
		payMain.put("settlemode", entityMap.get("settlemode"));
		
		payMain.put("totalnum", wageList.size());
		
		payMain.put("totalamt", entityMap.get("totalamt"));
		
		payMain.put("reqreserved1", "");payMain.put("reqreserved2", "");
		
		payList.add(payMain);
		
		List<Map<String, Object>> rdList = new ArrayList<Map<String, Object>>();
		
		for(int i = 0; i < wageList.size(); i++){
			
			Map<String,Object> rdMap = new HashMap<String,Object>();
			
			Map<String,Object> map = wageList.get(i);
			
			rdMap.put("group_id", entityMap.get("group_id"));rdMap.put("hos_id", entityMap.get("hos_id"));
			
			rdMap.put("copy_code", entityMap.get("copy_code"));rdMap.put("acc_year", entityMap.get("acc_year"));
			
			rdMap.put("acc_month", entityMap.get("acc_month"));
			
			rdMap.put("fseqno", fSeqNo);rdMap.put("iseqno", iseqno+getISeqNo(i));
			
			rdMap.put("orderno", "");rdMap.put("reimburseno", "");rdMap.put("reimbursenum", "1");
			
			rdMap.put("startdate", "");rdMap.put("starttime", "");
			
			rdMap.put("paytype", entityMap.get("paytype"));rdMap.put("payaccno", entityMap.get("payaccno"));
			
			rdMap.put("payaccnamecn", entityMap.get("payaccnamecn"));rdMap.put("payaccnameen", "");
			
			rdMap.put("recaccno", map.get("recaccno"));rdMap.put("recaccnameen", "");
			
			rdMap.put("recaccnamecn", map.get("recaccnamecn"));
			
			if(map.get("is_city_same") != null && "1".equals(String.valueOf(map.get("is_city_same")))){
				
				rdMap.put("issamecity", String.valueOf(map.get("is_city_same")));
				
			}else{
				
				rdMap.put("issamecity", "1");
			}
			
			if (map.get("is_bank_same") != null && "1".equals(String.valueOf(map.get("is_bank_same")))) {
				
				rdMap.put("sysioflg", String.valueOf(map.get("is_bank_same")));
				
			}else{
				
				
				rdMap.put("sysioflg", "1");
				
			}
			
			rdMap.put("recicbccode", "");rdMap.put("reccityname", "");
			
			rdMap.put("recbankno", "");rdMap.put("recbankname", "");
			
			rdMap.put("currtype", entityMap.get("currtype"));
			
			BigDecimal sumAmountDetail= new BigDecimal(map.get("payamt").toString());BigDecimal transAmtDetail = sumAmountDetail.multiply(new BigDecimal(100));
			
			rdMap.put("payamt", transAmtDetail.intValue());rdMap.put("usecode", "");
			
			rdMap.put("usecn", "工资");rdMap.put("ensummary", "");
			
			rdMap.put("postscript", entityMap.get("postscript"));rdMap.put("summary", entityMap.get("summary"));
			
			rdMap.put("ref", entityMap.get("item_code"));rdMap.put("oref", entityMap.get("wage_code"));
			
			rdMap.put("erpsqn", map.get("emp_id"));rdMap.put("buscode", map.get("emp_no"));
			
			rdMap.put("erpcheckno", "");rdMap.put("crvouhtype", "");rdMap.put("represerved4", "");
			
			rdMap.put("crvouhname", "");rdMap.put("crvouhno", "");rdMap.put("represerved3", "");
			
			rdMap.put("result", "");rdMap.put("iretcode", "");rdMap.put("iretmsg", "");

			rdList.add(rdMap);

		}
		
		paymap.put("payList", payList);
		
		paymap.put("rdList", rdList);
		
		return paymap;
	}
	
	public String getISeqNo(int i){// 1.流水号年月日6位流水
		String no=null;
		if(i<10){no = "000000"+i+1;
		}else if(i<100 && i>=10){no = "00000"+i+1;
		}else if(i<1000 && i>=100){no = "0000"+i+1;
		}else if(i<10000 && i>=1000){no = "000"+i+1;
		}else if(i<100000 && i>=10000){no = "00"+i+1;
		}else if(i<1000000 && i>=100000){no = "0"+i+1;
		}else if(i<10000000 && i>=1000000){no = ""+i+1;}
		return no;
	}
	
	public String getFSeqNo(Map<String, Object> entityMap){//生成指令包序列号
		
		Date settle_date = DateUtil.getCurrenDate("yyyy-MM-dd HH:mm:ss:SSS"); //DateUtil.dateToString(, "yyyy-MM-dd HH:mm:ss:SSS");
		
		//计算指令包序列号
		String fseqNo = accBankNetWageMapper.queryMaxFSeqNo(entityMap);
		
		fseqNo = fseqNo.substring(0,fseqNo.length()-1);
		
		String day = DateUtil.dateToString(new Date(), "yyyyMMdd");
		
		if(!"0".equals(fseqNo)){
			
			String fseqNoSub = fseqNo.substring(fseqNo.length()-14, fseqNo.length()-6);

			if(fseqNoSub.equals(day)){
				
				String str =fseqNo.substring(fseqNo.length()-14, fseqNo.length());
				
				long seq_no = Long.parseLong(str)+1;
				
				fseqNo = entityMap.get("group_id").toString()+entityMap.get("hos_id") +seq_no;
				
			}else{
				
				fseqNo = entityMap.get("group_id").toString()+entityMap.get("hos_id")+day+"000001";
				
			}
			
		}else{
			
			fseqNo = entityMap.get("group_id").toString()+entityMap.get("hos_id")+day+"000001";
		}
		
		return fseqNo+"W";
	}

	@Override
	public String updateAccBankNetWage(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {

			EnterpriseQueryData esd =  EnterpriseQueryData.getInstance();
			
			for(Map<String, Object> mapVo :entityMap){
				
				mapVo.put("Version", "0.0.0.1");
				
				mapVo.put("TransCode", "QPAYPER");
				
				mapVo.put("PackageID", "Q"+mapVo.get("fseqno"));
				
				Map<String,Object> map = esd.getICBC(mapVo);
				
				map.put("group_id", mapVo.get("group_id"));
				
				map.put("hos_id", mapVo.get("hos_id"));
				
				map.put("copy_code", mapVo.get("copy_code"));
				
				map.put("acc_year", mapVo.get("acc_year"));
				
				map.put("acc_month", mapVo.get("acc_month"));
				
				map.put("fseqno", mapVo.get("fseqno"));
				
				accBankNetWageMapper.updateAccBankNetWage(map);//更新主表
				
				List<Map<String, Object>> rdList = (List<Map<String, Object>>) map.get("rdList");
				
				if(rdList !=null && rdList.size()>0){
					
					List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
					
					for(Map<String, Object> rdMap:rdList){
						
						rdMap.put("group_id", mapVo.get("group_id"));
						
						rdMap.put("hos_id", mapVo.get("hos_id"));
						
						rdMap.put("copy_code", mapVo.get("copy_code"));
						
						rdMap.put("acc_year", mapVo.get("acc_year"));
						
						rdMap.put("acc_month", mapVo.get("acc_month"));
						
						rdMap.put("fseqno", mapVo.get("fseqno"));
						
						updateList.add(rdMap);
						
					}
					
					accBankNetWageMapper.updateBatchAccBankNetWageRd(rdList);//更新明细表
					
				}

			}

			return "{\"msg\":\"更新指令状态成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"更新指令失败\"}");

		}
	}

	@Override
	public String queryAccWagePay(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String, Object>> list = accBankNetWageMapper.queryAccWagePay(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = accBankNetWageMapper.queryAccWagePay(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}

	@Override
	public String queryAccBankNetWageRd(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String, Object>> list = accBankNetWageMapper.queryAccBankNetWageRd(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = accBankNetWageMapper.queryAccBankNetWageRd(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}

	@Override
	public double sumTotalAmtByDay(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("nowDate", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		
		double totalAmt = accBankNetWageMapper.sumTotalAmtByDay(entityMap);
		
		return totalAmt;
	}

	@Override
	public double sumWage(Map<String, Object> entityMap) throws DataAccessException {
		
		if(entityMap.get("item_code") !=null && !"".equals("item_code")){
			entityMap.put("sql", entityMap.get("item_code") +" > 0");
		}
		
		double wageAmt = accBankNetWageMapper.sumWage(entityMap);

		return wageAmt;
	}

	@Override
	public double totalAmtByFSeqNo(Map<String, Object> entityMap) throws DataAccessException {
		
		double totalAmtByFSeqNo = accBankNetWageMapper.totalAmtByFSeqNo(entityMap);
		
		return totalAmtByFSeqNo;
	}

	@Override
	public double sumTotalAmtByISeqNo(Map<String, Object> entityMap) throws DataAccessException {
		
		double sumTotalAmtByISeqNo = accBankNetWageMapper.sumTotalAmtByISeqNo(entityMap);
		
		return sumTotalAmtByISeqNo;
	}

	@Override
	public String recaccnoNullWarring(Map<String, Object> entityMap) throws DataAccessException {
		
		// 生成选项 第一步 查询要组装的数据

		String payFlag = (String) entityMap.get("payFlag");

		// payFlag =0 从工资支付界面支付 =1从指令状态支付

		List<Map<String, Object>> wageList = new ArrayList<Map<String, Object>>();

		if ("0".equals(payFlag)) {

			String sql = " nvl(" + entityMap.get("item_code") + ",0) > 0 ";

			entityMap.put("sql", sql);

			wageList = accBankNetWageMapper.queryAccWagePay(entityMap);

			if (wageList.size() == 0) {

				return "{\"msg\":\"当前会计期间内 没有支付数据.\",\"state\":\"true\"}";

			}

		} else if ("1".equals(payFlag)) {

			wageList = accBankNetWageMapper.queryAccBankNetWageRdBySeqNo(entityMap);

		} else if ("2".equals(payFlag)) {

			String paramVo = String.valueOf(entityMap.get("paramVo"));

			String[] paraSpl = paramVo.split(",");

			StringBuffer paraSb = new StringBuffer();

			for (int i = 0; i < paraSpl.length; i++) {

				if (i != 0) {

					paraSb.append(",");
				}

				paraSb.append("'" + paraSpl[i] + "'");

			}

			String sql = " and iseqno in (" + paraSb.toString() + ") ";

			entityMap.put("sql", sql);

			wageList = accBankNetWageMapper.queryAccBankNetWageRdBySeqNo(entityMap);

		}else if ("3".equals(payFlag)) {

			String paramVo = String.valueOf(entityMap.get("paramVo"));

			String[] paraSpl = paramVo.split(",");

			StringBuffer paraISb = new StringBuffer();
			
			StringBuffer paraFSb = new StringBuffer();
			
			StringBuffer paraIDSb = new StringBuffer();
			
			StringBuffer paraNOSb = new StringBuffer();

			for (int i = 0; i < paraSpl.length; i++) {

				if (i != 0) {

					paraISb.append(",");
					
					paraFSb.append(",");
					
					paraIDSb.append(",");
					
					paraNOSb.append(",");
				}

				paraISb.append("'" + paraSpl[i].split(";")[0] + "'");
				
				paraFSb.append("'" + paraSpl[i].split(";")[1] + "'");
				
				paraIDSb.append("'" + paraSpl[i].split(";")[2] + "'");
				
				paraNOSb.append("'" + paraSpl[i].split(";")[3] + "'");

			}

			String sql = " and fseqno in (" + paraFSb.toString() + ") and iseqno in (" + paraISb.toString() + ") "
					+ " and abnwr.erpsqn in (" + paraIDSb.toString() + ") and abnwr.buscode in (" + paraNOSb.toString() + ") ";

			entityMap.put("sql", sql);

			wageList = accBankNetWageMapper.queryAccBankNetWageRdBySeqNo(entityMap);

		}

		StringBuffer recaccnoNullWarring = new StringBuffer();
		
		StringBuffer sameWarring = new StringBuffer();
		// 验证银行卡号 并且严重是否同行同城
		for (int i = 0; i < wageList.size(); i++) {

			Map<String, Object> map = wageList.get(i);

			if (map.get("recaccno") == null || "".equals(map.get("recaccno").toString().trim()) || "null".equals(map.get("recaccno").toString().trim())) {

				recaccnoNullWarring.append("  "+map.get("emp_code")+" "+map.get("emp_name") + " 的" + "银行卡号为空<br/>");

			}

			if (map.get("is_bank_same") == null || !"1".equals(String.valueOf(map.get("is_bank_same")))) {
				
				sameWarring.append("  "+map.get("emp_code")+" "+map.get("emp_name") + " " + "非同行 ");
				
			}
			if (map.get("is_city_same") == null || !"1".equals(String.valueOf(map.get("is_city_same")))) {
				
				sameWarring.append("、非同城<br/>");
				
			}
		}

		return recaccnoNullWarring.toString()+sameWarring.toString();
	}

	public List<Map<String, Object>> queryAccBankNetWagePrint(Map<String, Object> entityMap) throws DataAccessException {

		if (entityMap.get("group_id") == null) {
			
			entityMap.put("group_id", SessionManager.getGroupId());
			
		}

		if (entityMap.get("hos_id") == null) {
			
			entityMap.put("hos_id", SessionManager.getHosId());
			
		}

		if (entityMap.get("copy_code") == null) {
			
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
		}

		List<Map<String, Object>> list = accBankNetWageMapper.queryAccBankNetWage(entityMap);

		return list;
			
	}
	
	public List<Map<String, Object>> queryAccBankNetWageRdPrint(Map<String, Object> entityMap) throws DataAccessException {

		if (entityMap.get("group_id") == null) {
			
			entityMap.put("group_id", SessionManager.getGroupId());
			
		}

		if (entityMap.get("hos_id") == null) {
			
			entityMap.put("hos_id", SessionManager.getHosId());
			
		}

		if (entityMap.get("copy_code") == null) {
			
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
		}

		List<Map<String, Object>> list = accBankNetWageMapper.queryAccBankNetWageRd(entityMap);

		return list;
			
	}
}
