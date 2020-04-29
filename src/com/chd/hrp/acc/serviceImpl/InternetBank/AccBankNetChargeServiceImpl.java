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
import com.chd.hrp.acc.dao.InternetBank.AccBankNetChargeMapper;
import com.chd.hrp.acc.dao.payable.otherpay.BudgChargeApplyMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchMapper;
import com.chd.hrp.acc.service.InternetBank.AccBankNetChargeService;
import com.chd.hrp.acc.service.InternetBank.AccBankNetCommonService;
import com.github.pagehelper.PageInfo;
 
/**
 * @Title. @Description<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accBankNetChargeService")
public class AccBankNetChargeServiceImpl implements AccBankNetChargeService {

	private static Logger logger = Logger.getLogger(AccBankNetChargeServiceImpl.class);

	@Resource(name = "accBankNetChargeMapper")
	private final AccBankNetChargeMapper accBankNetChargeMapper = null;
	
	@Resource(name = "accVouchMapper")
	private final AccVouchMapper accVouchMapper = null;
	
	@Resource(name = "budgChargeApplyMapper")
	private final BudgChargeApplyMapper budgChargeApplyMapper = null;
	
	@Resource(name = "accBankNetCommonService")
	private final AccBankNetCommonService accBankNetCommonService = null;
	

	@Override
	public String queryAccChargeApply(Map<String, Object> entityMap) throws DataAccessException {

		//主页面查询是取主表数据 并且未支付过的数据
		entityMap.put("sql", " and a.state = '03'");	
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = accBankNetChargeMapper.queryAccChargeApply(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = accBankNetChargeMapper.queryAccChargeApply(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}

	}

	@Override
	public String queryAccBankNetCharge(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = accBankNetChargeMapper.queryAccBankNetCharge(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = accBankNetChargeMapper.queryAccBankNetCharge(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public String addAccBankNetCharge(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			//存支付单据的状态更新数据
			List<Map<String,Object>> stateList = new ArrayList<Map<String,Object>>();
			//存库工商银行支付数据
			List<Map<String,List>> icbcList = new ArrayList<Map<String,List>>();
			
 			String paramVo = String.valueOf(entityMap.get("paramVo"));
			
			String[] paramVoArr = paramVo.split(",");
			
			//组装数据
			for (int i = 0; i< paramVoArr.length; i++) {
				
				String[] para = paramVoArr[i].split("@");
				//验证支付但单号
				if(para[0] == null || "".equals(para[0]) || "null".equals(para[0])){
					return "{\"error\":\"未获取到支付单号,请核对\"}";
				}
				//根据code提取收款人姓名 卡号
				StringBuffer sql = new StringBuffer();
				sql.append(" and a.apply_code = ").append("'"+para[0]+"'");

				//支付标识 0从费用网银支付 1 从费用指令状态查询支付
				String payFlag = (String)entityMap.get("payFlag");
				if("1".equals(payFlag)){
					//如果从费用指令状态查询支付 则 unit_id必填 否则支付不成功
					if(para[2] == null || "".equals(para[2]) || "null".equals(para[2])){
						return "{\"error\":\"单据号"+para[0]+" 未获取到明细数据\"}";
					}
					sql.append(" and b.unit_id = "+para[2]);
				}
				entityMap.put("sql", sql);
				entityMap.put("apply_code", para[0]);
				
				entityMap.put("det", "true");
				List<Map<String,Object>> list = accBankNetChargeMapper.queryAccChargeApply(entityMap);

				//验证页面总金额是否与后台查询语句总金额相符 保持数据一致性 也为确保支付准确的最后一次验证
				long amountFromTable = 0;//来自后台表的金额汇总
				
				for(Map<String,Object> amountMap : list){
					
					BigDecimal tmpBig= new BigDecimal(amountMap.get("det_pay_amount").toString());
					BigDecimal tmpBigDetail = tmpBig.multiply(new BigDecimal(100));
					amountFromTable = amountFromTable+tmpBigDetail.longValue();;

				}
				//比较金额
				BigDecimal tmpBig= new BigDecimal(para[1].toString());
				
				BigDecimal tmpBigDetail = tmpBig.multiply(new BigDecimal(100));
				
				if((amountFromTable - tmpBigDetail.longValue()) != 0){
					return "{\"error\":\"单据号"+para[0]+" 支付失败 , 单据金额不相等!\"}";
				}
				
				//拆包组装批量支付 如果数据大于100条做分包处理 
				long totalamt = 0;

				List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
				
				for(int k =0 ;k<list.size();k++){
					
					BigDecimal sumAmountDetail= new BigDecimal(list.get(k).get("det_pay_amount").toString());
					
					BigDecimal transAmtDetail = sumAmountDetail.multiply(new BigDecimal(100));
					
					totalamt = totalamt+transAmtDetail.longValue();
					
					dataList.add(list.get(k));
					
					if((k+1)%100 == 0){
						
						entityMap.put("totalamt", totalamt);
			
						Map<String,List> paymap = payMap(entityMap,dataList);
						
						accBankNetChargeMapper.addBatchAccBankNetCharge(paymap.get("payList"));
						
						accBankNetChargeMapper.addBatchAccBankNetChargeRd(paymap.get("rdList"));
						
						icbcList.add(paymap);//
		
						dataList.removeAll(dataList);
						
						totalamt = 0;
	
					}
					
				}
				
				//此判断解决数为整数的时候错误
				if(totalamt > 0){
					// 生成选项 第三步插入新的数据
					entityMap.put("totalamt", totalamt);
					
					Map<String,List> paymap = payMap(entityMap,dataList);
								
					accBankNetChargeMapper.addBatchAccBankNetCharge(paymap.get("payList"));
								
					accBankNetChargeMapper.addBatchAccBankNetChargeRd(paymap.get("rdList"));
					
					icbcList.add(paymap);
					
				}
				//支付状态更新数据
				stateList.add(stateMap(entityMap));
			}

			//更新单据状态
			budgChargeApplyMapper.confirmBudgChargeApply(stateList);
			
			//开始提交ICBC数据
			EnterpriseSendData esd = EnterpriseSendData.getInstance();
			for(int k=0;k<icbcList.size();k++){
				
				Map<String,List> icbcMap = icbcList.get(k);
				
				Map reMap = esd.getICBC(icbcMap.get("payList"), icbcMap.get("rdList"));
				
				Map<String,Object> mapUpdate = (Map<String, Object>)icbcMap.get("payList").get(0);
				
				mapUpdate.put("retcode", reMap.get("RetCode"));
				
				mapUpdate.put("retmsg",  reMap.get("RetMsg"));
				
				accBankNetChargeMapper.updateAccBankNetCharge(mapUpdate);
				
				Thread.sleep(2000);
				
			}
			
			return "{\"msg\":\"支付数据提交成功 请到指令查询查看具体状态.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"支付失败 , 请重新尝试\"}");

		}

	}

	//支付状态Map
	public Map<String,Object> stateMap(Map<String, Object> entityMap){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		map.put("state", "04");
		map.put("payer", SessionManager.getUserId());
		map.put("pay_date",DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		map.put("pay_way", "003");
		map.put("apply_code", entityMap.get("apply_code"));
		
		return map;
	}
	public Map<String,List> payMap(Map<String, Object> entityMap ,List<Map<String,Object>> list) throws Exception{
		
		String transCode = "PAYENT";String version = "0.0.0.1";
		
		String iseqno = DateUtil.dateToString(new Date(), "yyyyMMdd");
		
		Map<String,List> paymap =new HashMap<String,List>();//支付主表数据、明细表数据
		
		List<Map<String, Object>> payList = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> rdList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> payMain = new HashMap<String, Object>();
		
		//构建 acc_bank_net
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

		payMain.put("totalnum", list.size());

		payMain.put("totalamt", entityMap.get("totalamt"));
		
		payMain.put("reqreserved1", "");payMain.put("reqreserved2", "");
		
		payMain.put("zipFlag", "&zipFlag=0");

		payList.add(payMain);
		
		//构建 acc_bank_net_buyer_rd
		for (int i = 0; i < list.size(); i++) {
			
			Map<String,Object> rdMap = new HashMap<String,Object>();

			rdMap.put("group_id", entityMap.get("group_id"));rdMap.put("hos_id", entityMap.get("hos_id"));
			
			rdMap.put("copy_code", entityMap.get("copy_code"));rdMap.put("acc_year", entityMap.get("acc_year"));
			
			rdMap.put("acc_month", entityMap.get("acc_month"));
			
			rdMap.put("fseqno", fSeqNo);rdMap.put("iseqno", iseqno+getISeqNo(i));
			
			rdMap.put("orderno", "");rdMap.put("reimburseno", "");rdMap.put("reimbursenum", "1");
			
			rdMap.put("startdate", "");rdMap.put("starttime", "");
			
			rdMap.put("paytype", entityMap.get("paytype"));rdMap.put("payaccno", entityMap.get("payaccno"));
			
			rdMap.put("payaccnamecn", entityMap.get("payaccnamecn"));rdMap.put("payaccnameen", "");
	
			rdMap.put("recaccno", list.get(i).get("card_no"));rdMap.put("recaccnameen", "");

			String recaccnamecn = list.get(i).get("unit_name").toString();
			//长度大于5 基本为企业账户 上送对公对私标志为企业 否则 为个人
			if(recaccnamecn.length() > 5){rdMap.put("prop", "0");}else{rdMap.put("prop", "1");}
			
			rdMap.put("recaccnamecn", recaccnamecn);
			
			//判断是否为工商银行的账户
			String recbankname = list.get(i).get("bank_name").toString();
			
			if(recbankname.indexOf("工商") != -1){rdMap.put("sysioflg", "1");}else{rdMap.put("sysioflg", "2");}
			
			rdMap.put("recbankname", recbankname);
			
			//判断是否同城
			String reccityname = list.get(i).get("bank_location").toString();
			
			if(reccityname.indexOf("石家庄") !=-1){rdMap.put("issamecity", "1");}else{rdMap.put("issamecity", "2");}
			
			rdMap.put("reccityname", reccityname);rdMap.put("recicbccode", "");rdMap.put("recbankno", "");
			
			rdMap.put("currtype", entityMap.get("currtype"));
			
			BigDecimal sumAmountDetailRd= new BigDecimal(list.get(i).get("det_pay_amount").toString());
			BigDecimal transAmtDetailRd = sumAmountDetailRd.multiply(new BigDecimal(100));
			
			rdMap.put("payamt", transAmtDetailRd.longValue());
			rdMap.put("usecode", "");
			
			String det_remark = String.valueOf(list.get(i).get("det_remark"));
			
			if(det_remark!= null && !"".equals(det_remark) && !"null".equals(det_remark)){
				if(accBankNetCommonService.getCharCount(det_remark)> 10 ){	
					det_remark = accBankNetCommonService.subChar(det_remark,10);	
				}
				rdMap.put("usecn", det_remark);
			}else{
				rdMap.put("usecn", "报销单");
			}
			
			rdMap.put("usecn", "费用单");
			
			rdMap.put("ensummary", "");
			
			if(entityMap.get("postscript") != null && !"".equals(entityMap.get("postscript"))){
				rdMap.put("postscript", entityMap.get("postscript"));
			}else{
				rdMap.put("postscript", list.get(i).get("remark"));
			}
			rdMap.put("summary", entityMap.get("summary"));
			
			rdMap.put("ref", entityMap.get("apply_code"));rdMap.put("oref", list.get(i).get("proj_id"));
			
			rdMap.put("erpsqn", list.get(i).get("emp_id"));rdMap.put("buscode", list.get(i).get("unit_id"));
			
			rdMap.put("erpcheckno", "");rdMap.put("crvouhtype", "");rdMap.put("represerved4", "");
			
			rdMap.put("crvouhname", "");rdMap.put("crvouhno", "");rdMap.put("represerved3", "");
			
			rdMap.put("result", "");rdMap.put("iretcode", "");rdMap.put("iretmsg", "");

			rdList.add(rdMap);
			
		}
		
		paymap.put("payList", payList);
		
		paymap.put("rdList", rdList);
		
		return paymap;
		
	}
	
	public long getTotalAmt(String[] paramVoArr) {// 获取总金额 转化成分为单位

		Map<String, Long> map = new HashMap<String, Long>();

		long totalamt = 0;

		for (int i = 0; i < paramVoArr.length; i++) {

			String[] ids = paramVoArr[i].split("@");

			BigDecimal sumAmountDetail = new BigDecimal(ids[1]);

			BigDecimal transAmtDetail = sumAmountDetail.multiply(new BigDecimal(100));

			totalamt = totalamt + transAmtDetail.longValue();

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

		Date settle_date = DateUtil.getCurrenDate("yyyy-MM-dd HH:mm:ss:SSS");// DateUtil.dateToString(,
																				// "yyyy-MM-dd HH:mm:ss:SSS");
		String fseqNo = accBankNetChargeMapper.queryMaxFSeqNo(entityMap);// 计算指令包序列号

		String day = DateUtil.dateToString(new Date(), "yyyyMMdd");

		if (!"0".equals(fseqNo)) {
			
			fseqNo = fseqNo.substring(0, fseqNo.length() - 1);

			String fseqNoSub = fseqNo.substring(fseqNo.length() - 14, fseqNo.length() - 6);

			if (fseqNoSub.equals(day)) {
				
				String str =fseqNo.substring(fseqNo.length()-14, fseqNo.length());
				
				long seq_no = 0;

				seq_no= Long.parseLong(str) + 1;
				
				fseqNo = entityMap.get("group_id").toString()+entityMap.get("hos_id") +seq_no;

			} else {

				fseqNo = entityMap.get("group_id").toString() + entityMap.get("hos_id") + day + "000001";

			}

		} else {

			fseqNo = entityMap.get("group_id").toString() + entityMap.get("hos_id") + day + "000001";
		}

		return fseqNo + "f";

	}

	@Override
	public String updateAccBankNetCharge(List<Map<String, Object>> entityMap) throws DataAccessException {

		try {
			
			//List<Map<String, Object>> vouchList = new ArrayList<Map<String,Object>>();

			EnterpriseQueryData esd = EnterpriseQueryData.getInstance();

			for (Map<String, Object> mapVo : entityMap) {
				
				mapVo.put("Version", "0.0.0.1");
				
				mapVo.put("TransCode", "QPAYENT");

				mapVo.put("PackageID", "Q" + mapVo.get("fseqno"));

				Map<String, Object> map = esd.getICBC(mapVo);

				map.put("group_id", mapVo.get("group_id"));

				map.put("hos_id", mapVo.get("hos_id"));

				map.put("copy_code", mapVo.get("copy_code"));

				map.put("acc_year", mapVo.get("acc_year"));

				map.put("fseqno", mapVo.get("fseqno"));
				
				//map.put("vouch_id", mapVo.get("vouch_id"));map.put("vouch_state", mapVo.get("vouch_state"));
				
				//map.put("state", "2");map.put("cash", "0");map.put("cash_user",  mapVo.get("user_id"));
				
				//map.put("cashe_date", new Date());map.put("log_table", mapVo.get("log_table"));

				accBankNetChargeMapper.updateAccBankNetCharge(map);// 更新主表

				List<Map<String, Object>> rdList = (List<Map<String, Object>>) map.get("rdList");
				
				if (rdList != null && rdList.size() > 0) {

					List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();

					for (Map<String, Object> rdMap : rdList) {

						rdMap.put("group_id", mapVo.get("group_id"));

						rdMap.put("hos_id", mapVo.get("hos_id"));

						rdMap.put("copy_code", mapVo.get("copy_code"));

						rdMap.put("acc_year", mapVo.get("acc_year"));

						rdMap.put("fseqno", mapVo.get("fseqno"));

						updateList.add(rdMap);
						
						}

					}

					accBankNetChargeMapper.updateBatchAccBankNetChargeRd(rdList);// 更新明细表

				}

			return "{\"msg\":\"更新指令状态成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新指令失败\"}");

		}

	}
	
	@Override
	public String updateAccBankNetState(List<Map<String, Object>> entityMap) throws DataAccessException {

		try {
			
			List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();

			for (Map<String, Object> mapVo : entityMap) {

				Map<String, Object> map = new HashMap<String,Object>();

				map.put("group_id", mapVo.get("group_id"));

				map.put("hos_id", mapVo.get("hos_id"));

				map.put("copy_code", mapVo.get("copy_code"));

				map.put("acc_year", mapVo.get("acc_year"));

				map.put("fseqno", mapVo.get("fseqno"));
				
				map.put("unit_id", mapVo.get("unit_id"));
				
				if("01".equals(mapVo.get("state"))){
					
					map.put("iretcode", "0");
					
					map.put("iretmsg", "手动标记为跨行支付失败");
					
					map.put("result", "6");
					
				}

				if("02".equals(mapVo.get("state"))){
					
					map.put("iretcode", "0");
					
					map.put("iretmsg", "手动标记为账户名支付失败");
					
					map.put("result", "6");
					
				}

				updateList.add(map);
			}
			accBankNetChargeMapper.updateBatchAccBankNetChargeRd(updateList);// 更新明细表
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");

		}

	}

	@Override
	public String queryAccBankNetChargeRd(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = accBankNetChargeMapper.queryAccBankNetChargeRd(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = accBankNetChargeMapper.queryAccBankNetChargeRd(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public double sumTotalAmtByDay(Map<String, Object> entityMap) throws DataAccessException {
		
		return accBankNetChargeMapper.sumTotalAmtByDay(entityMap);
		
	}
	
	public List<Map<String, Object>> queryAccChargeApplyPrint(Map<String, Object> entityMap) throws DataAccessException {
			
		if (entityMap.get("group_id") == null) {
			
			entityMap.put("group_id", SessionManager.getGroupId());
			
		}

		if (entityMap.get("hos_id") == null) {
			
			entityMap.put("hos_id", SessionManager.getHosId());
			
		}

		if (entityMap.get("copy_code") == null) {
			
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
		}

		List<Map<String, Object>> list = accBankNetChargeMapper.queryAccChargeApply(entityMap);

		return list;

	}
	@Override
	public List<Map<String, Object>> queryAccBankNetChargeRdPrint(Map<String, Object> entityMap) throws DataAccessException {

		if (entityMap.get("group_id") == null) {
			
			entityMap.put("group_id", SessionManager.getGroupId());
			
		}

		if (entityMap.get("hos_id") == null) {
			
			entityMap.put("hos_id", SessionManager.getHosId());
			
		}

		if (entityMap.get("copy_code") == null) {
			
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
		}

		List<Map<String, Object>> list = accBankNetChargeMapper.queryAccBankNetChargeRd(entityMap);

		return list;

	}

	@Override
	public String queryAccBankNetChargeNum(Map<String, Object> entityMap)
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

					payMain.put("apply_code", ids[0]);
					
					List<Map<String, Object>> rMap= accBankNetChargeMapper.queryAccBankNetChargeRd(payMain);
					
					if(rMap.size()>0&&("0".equals(rMap.get(0).get("result_state"))||"1".equals(rMap.get(0).get("result_state"))||"2".equals(rMap.get(0).get("result_state"))||"3".equals(rMap.get(0).get("result_state"))||"4".equals(rMap.get(0).get("result_state"))||"7".equals(rMap.get(0).get("result_state"))||"9".equals(rMap.get(0).get("result_state")))){
							
						sbuffer.append("费用单号【"+ids[0]+"】,");
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
