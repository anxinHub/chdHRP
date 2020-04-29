package com.chd.hrp.acc.serviceImpl.accBankVeri;

/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.AccYearMonthMapper;
import com.chd.hrp.acc.dao.accBankVeri.AccUnitBankCheckMapper;
import com.chd.hrp.acc.dao.tell.AccTellDayMapper;
import com.chd.hrp.acc.dao.tell.AccTellMapper;
import com.chd.hrp.acc.entity.AccBankCheck;
import com.chd.hrp.acc.entity.AccLederCheck;
import com.chd.hrp.acc.entity.AccVouchCheck;
import com.chd.hrp.acc.service.accBankVeri.AccUnitBankCheckService;
import com.github.pagehelper.PageInfo;

@Service("accUnitBankCheckService")
public class AccUnitBankCheckServiceImpl implements AccUnitBankCheckService {

	private static Logger logger = Logger.getLogger(AccUnitBankCheckServiceImpl.class);

	@Resource(name = "accTellMapper")
	private final AccTellMapper accTellMapper = null;

	@Resource(name = "accTellDayMapper")
	private final AccTellDayMapper accTellDayMapper = null;

	@Resource(name = "accYearMonthMapper")
	private final AccYearMonthMapper accYearMonthMapper = null;

	@Resource(name = "accUnitBankCheckMapper")
	private final AccUnitBankCheckMapper accUnitBankCheckMapper = null;

	/**
	 * 单位银行对账 单位查询
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccTellOrVouchData(Map<String, Object> entityMap) throws DataAccessException {

		JSONObject lastJson = new JSONObject();// 最终返回的json串

		StringBuffer stateSql = new StringBuffer();// 状态
		StringBuffer directSql = new StringBuffer();// 左侧方向
		StringBuffer occurSql = new StringBuffer();// 左侧发生额
		long a;

		// ************************查询条件 begin
		if(entityMap.get("directA") != null && !"".equals(entityMap.get("directA").toString())){
			if (entityMap.get("directA").equals("0")) {// 借方
				directSql.append(" and nvl(avc.debit,0) != 0 and nvl(avc.credit,0) = 0 ");
				occurSql.append(" and nvl(avc.debit,0) ");
			} else {
				directSql.append(" and nvl(avc.credit,0) != 0 and nvl(avc.debit,0) = 0 ");
				occurSql.append(" and nvl(avc.credit, 0)  ");
			}
		}else{
			occurSql.append(" and nvl(avc.debit,0) + nvl(avc.credit, 0)  ");
		}

		entityMap.put("occurSql", occurSql);
		entityMap.put("directSql", directSql);
		// ************************查询条件 end ***********************************

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String, Object>> leftList = new ArrayList<Map<String, Object>>();

		if ("0".equals(MyConfig.getSysPara("018").toString())) {
			// 出纳与银行对账
			if (entityMap.get("checkState").equals("2")) {
				stateSql.append(" and nvl(avc.is_check,0) = 1  ");/*and abv.veri_check_id is not null*/
			} else if (entityMap.get("checkState").equals("1")) {
				stateSql.append(" and nvl(avc.is_check,0) = 0 ");
			} else {
				stateSql.append("");
			}
			entityMap.put("stateSql", stateSql);

			leftList = JsonListMapUtil.toListMapLower(accUnitBankCheckMapper.queryAccBankTellData(entityMap, rowBounds));
			entityMap.remove("sortname");
			a=accUnitBankCheckMapper.queryAccBankTellDataCount(entityMap);
			
		} else {
			// 会计与银行对账
			if (entityMap.get("checkState").equals("2")) {
				stateSql.append(" and nvl(abv.veri_money,0) != 0 and abv.veri_check_id is not null ");
			} else if (entityMap.get("checkState").equals("1")) {
				stateSql.append(" and nvl(abv.veri_money,0) = 0 ");
			} else {
				stateSql.append("");
			}
			entityMap.put("stateSql", stateSql);
			
			leftList = JsonListMapUtil.toListMapLower(accUnitBankCheckMapper.queryAccBankVouchCheckData(entityMap, rowBounds));
			entityMap.remove("sortname");
			a=accUnitBankCheckMapper.queryAccBankVouchCheckDataCount(entityMap);
		}
		
		//PageInfo page = new PageInfo(leftList);
		return ChdJson.toJsonLower(leftList, a);
		// ChdJson.toJson(leftList, page.getTotal());
	}

	/**
	 * 单位银行对账 单位账查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccBankCheckData(Map<String, Object> entityMap) throws DataAccessException {
		JSONObject lastJson = new JSONObject();// 最终返回的json串

		StringBuffer stateSql = new StringBuffer();// 状态
		StringBuffer directSql = new StringBuffer();// 方向
		StringBuffer occurSql = new StringBuffer();// 发生额

		if(entityMap.get("directB") != null && !"".equals(entityMap.get("directB").toString())){
			if (entityMap.get("directB").equals("0")) {// 借方
				directSql.append(" and nvl(avc.debit,0)!=0 and nvl(avc.credit,0)=0 ");
				occurSql.append(" and nvl(avc.debit, 0)  ");
			} else {
				directSql.append(" and nvl(avc.credit,0)!=0 and nvl(avc.debit,0)=0 ");
				occurSql.append(" and nvl(avc.credit,0)  ");
			}
		}else{
			occurSql.append(" and nvl(avc.debit, 0) + nvl(avc.credit,0)  ");
		}

		if (entityMap.get("checkState").equals("2")) {
			stateSql.append(" and nvl(avc.is_check,0) = 1 ");
		} else if (entityMap.get("checkState").equals("1")) {
			stateSql.append(" and nvl(avc.is_check,0) = 0 ");
		} else {
			stateSql.append("");
		}

		entityMap.put("stateSql", stateSql);
		entityMap.put("occurSql", occurSql);
		entityMap.put("directSql", directSql);

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String, Object>> rightList = new ArrayList<Map<String, Object>>();
		rightList = JsonListMapUtil.toListMapLower(accUnitBankCheckMapper.queryAccBankCheckMain(entityMap, rowBounds));
		entityMap.remove("sortname");
		int a=accUnitBankCheckMapper.queryAccBankCheckMainCount(entityMap);
		PageInfo page = new PageInfo(rightList);
		return ChdJson.toJson(rightList, a);
	}

	/**
	 * 对账
	 */
	@Override
	public String addAccBankVeriMain(Map<String, Object> entityMap) throws DataAccessException {
		try {

			List<Map<String, Object>> veriList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> bankUList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> tellUList = new ArrayList<Map<String, Object>>();
			String cashYearMonth=MyConfig.getMyAccYearMonth().getCurYearMonthCash();//取出纳期间
			String accYear=null;
			String accMonth=null;
			if(cashYearMonth!=null && !"".equals(cashYearMonth)){
				accYear=cashYearMonth.substring(0,4);
				accMonth=cashYearMonth.substring(4,6);
			}else{
				accYear=MyConfig.getCurAccYearMonth().substring(0,4);
				accMonth=MyConfig.getCurAccYearMonth().substring(4,6);
			}
			
			JSONArray jsonL = JSONArray.parseArray((String) entityMap.get("check_dataJ"));
			JSONArray jsonR = JSONArray.parseArray((String) entityMap.get("check_dataD"));

			String veri_check_id = UUIDLong.absStringUUID();
			Date date = new Date();
			String user_id = SessionManager.getUserId();
			
			if((jsonL == null || jsonL.isEmpty() || jsonL.size() == 0) || (jsonR == null || jsonR.isEmpty() || jsonR.size() == 0)){
				//只勾选单位账或银行账
				Map<String, Object> vmap = null;// 对应关系map
				Map<String, Object> bankMap = null;// 更新map
				Map<String, Object> tellMap = null;// 更新map
				JSONObject jsonObj = null;
				if(jsonL.size() > 0){
					Iterator<Object> itL = jsonL.iterator();
					while (itL.hasNext()) {
						jsonObj = JSONObject.parseObject(itL.next().toString());
						vmap = new HashMap<String, Object>();  //对应关系Map
						tellMap = new HashMap<String, Object>();  //出纳Map
						
						//业务日期大于当前期间以业务日期为准
						String occurDate = jsonObj.get("occur_date").toString();
						occurDate = occurDate.substring(0,4) + occurDate.substring(5,7);
						if(Integer.parseInt(occurDate) > Integer.parseInt(accYear + accMonth)){
							accYear = occurDate.substring(0,4);
							accMonth = occurDate.substring(4,6);
						}
						
						//对应关系
						vmap.put("veri_check_id", veri_check_id);
						vmap.put("group_id", entityMap.get("group_id"));
						vmap.put("hos_id", entityMap.get("hos_id"));
						vmap.put("copy_code", entityMap.get("copy_code"));
						vmap.put("acc_year", accYear);
						vmap.put("acc_month", accMonth);
						vmap.put("create_user", user_id);
						vmap.put("create_date", date);
						vmap.put("bank_id", "0");  //自己和自己对账，对应银行ID为0
						vmap.put("is_auto", "0");
						vmap.put("veri_money", jsonObj.get("wcheck_money"));
						
						//判断对账方式
						if ("0".equals(entityMap.get("check_method").toString())) {
							//出纳与银行对账
							vmap.put("tell_id", jsonObj.get("tell_id"));
							vmap.put("vouch_id", "");
							vmap.put("vouch_check_id", "");
							
							//记录出纳信息以便更新状态
							tellMap.put("group_id", entityMap.get("group_id"));
							tellMap.put("hos_id", entityMap.get("hos_id"));
							tellMap.put("copy_code", entityMap.get("copy_code"));
							tellMap.put("acc_year", entityMap.get("acc_year"));
							tellMap.put("tell_id", jsonObj.get("tell_id"));
							tellMap.put("is_check", "1");
							tellMap.put("check_user", entityMap.get("user_id"));
							tellMap.put("check_date", date);
							
							tellUList.add(tellMap);
						}else{
							//会计与银行对账
							vmap.put("tell_id", "");
							vmap.put("vouch_id", jsonObj.get("vouch_id"));
							vmap.put("vouch_check_id", jsonObj.get("vouch_check_id"));
						}
						
						veriList.add(vmap);
					}
				}
				if(jsonR.size() > 0){
					Iterator<Object> itR = jsonR.iterator();
					while (itR.hasNext()) {
						jsonObj = JSONObject.parseObject(itR.next().toString());
						vmap = new HashMap<String, Object>();  //对应关系Map
						bankMap = new HashMap<String, Object>();  //出纳Map
						
						//业务日期大于当前期间以业务日期为准
						String occurDate = jsonObj.get("occur_date").toString();
						occurDate = occurDate.substring(0,4) + occurDate.substring(5,7);
						if(Integer.parseInt(occurDate) > Integer.parseInt(accYear + accMonth)){
							accYear = occurDate.substring(0,4);
							accMonth = occurDate.substring(4,6);
						}
						
						//对应关系
						vmap.put("veri_check_id", veri_check_id);
						vmap.put("group_id", entityMap.get("group_id"));
						vmap.put("hos_id", entityMap.get("hos_id"));
						vmap.put("copy_code", entityMap.get("copy_code"));
						vmap.put("acc_year", accYear);
						vmap.put("acc_month", accMonth);
						vmap.put("create_user", user_id);
						vmap.put("create_date", date);
						vmap.put("bank_id", jsonObj.get("bank_id"));  //自己和自己对账，对应银行ID为0
						vmap.put("is_auto", "0");
						vmap.put("veri_money", jsonObj.get("wcheck_money"));
						
						//判断对账方式
						if ("0".equals(entityMap.get("check_method").toString())) {
							//出纳与银行对账
							vmap.put("tell_id", "0");
							vmap.put("vouch_id", "");
							vmap.put("vouch_check_id", "");
						}else{
							//会计与银行对账
							vmap.put("tell_id", "");
							vmap.put("vouch_id", "0");
							vmap.put("vouch_check_id", "0");
						}
						
						veriList.add(vmap);
						
						//记录银行账信息以便更新状态
						bankMap.put("group_id", entityMap.get("group_id"));
						bankMap.put("hos_id", entityMap.get("hos_id"));
						bankMap.put("copy_code", entityMap.get("copy_code"));
						bankMap.put("acc_year", entityMap.get("acc_year"));
						bankMap.put("bank_id", jsonObj.get("bank_id"));
						bankMap.put("is_check", "1");
						bankMap.put("check_user", entityMap.get("user_id"));
						bankMap.put("check_date", date);
						
						bankUList.add(bankMap);
					}
				}
				
				//更新对应关系中的年月为最大的对账年月
				for(Map<String, Object> tmpMap : veriList){
					tmpMap.put("acc_year", accYear);
					tmpMap.put("acc_month", accMonth);
				}
			}else{
				Map<String, Map<String,Object>> leftMap = new HashMap<String,Map<String,Object>>();
				Map<String, Map<String,Object>> rightMap = new HashMap<String,Map<String,Object>>();
				
				int lnum = 0;
				Iterator itL = jsonL.iterator();
				while (itL.hasNext()) {
					JSONObject jsonObjL = JSONObject.parseObject(itL.next().toString());
					Map<String, Object> mapL = new HashMap<String, Object>();
	
					//业务日期大于当前期间以业务日期为准
					String occurDate=jsonObjL.get("occur_date").toString();
					occurDate=occurDate.substring(0,4)+occurDate.substring(5,7);
					if(Integer.parseInt(occurDate)>Integer.parseInt(accYear+accMonth)){
						accYear=occurDate.substring(0,4);
						accMonth=occurDate.substring(4,6);
					}
					
					StringBuffer conL = new StringBuffer();
					conL.append(String.valueOf(lnum) + '@');
					conL.append(jsonObjL.get("group_id").toString() + jsonObjL.get("hos_id").toString() + jsonObjL.get("copy_code").toString()
							+ jsonObjL.get("acc_year").toString());
					conL.append("@").append(jsonObjL.get("wcheck_money"));
					
					mapL.put("group_id", jsonObjL.get("group_id"));
					mapL.put("hos_id", jsonObjL.get("hos_id"));
					mapL.put("copy_code", jsonObjL.get("copy_code"));
					mapL.put("acc_year", jsonObjL.get("acc_year"));
					mapL.put("vouch_id", jsonObjL.get("vouch_id"));
					mapL.put("vouch_check_id", jsonObjL.get("vouch_check_id"));
					mapL.put("tell_id", jsonObjL.get("tell_id"));
					mapL.put("wcheck_money", jsonObjL.get("wcheck_money"));
					mapL.put("isCheck", "0");
					
					String key = conL.toString();
					leftMap.put(key, mapL);
					lnum++;
				}
	
				// 处理右侧json串
				int rnum = 0;
				Iterator itR = jsonR.iterator();
				while (itR.hasNext()) {
					JSONObject jsonObjR = JSONObject.parseObject(itR.next().toString());
					Map<String, Object> mapR = new HashMap<String, Object>();
	
					//业务日期大于当前期间以业务日期为准
					String occurDate=jsonObjR.get("occur_date").toString();
					occurDate=occurDate.substring(0,4)+occurDate.substring(5,7);
					if(Integer.parseInt(occurDate)>Integer.parseInt(accYear+accMonth)){
						accYear=occurDate.substring(0,4);
						accMonth=occurDate.substring(4,6);
					}
					
					StringBuffer conR = new StringBuffer();
					conR.append(String.valueOf(rnum) + '@');
					conR.append(jsonObjR.get("group_id").toString() + jsonObjR.get("hos_id").toString() + jsonObjR.get("copy_code").toString()
							+ jsonObjR.get("acc_year").toString());
					conR.append("@").append(jsonObjR.get("wcheck_money"));
	
					mapR.put("group_id", jsonObjR.get("group_id"));
					mapR.put("hos_id", jsonObjR.get("hos_id"));
					mapR.put("copy_code", jsonObjR.get("copy_code"));
					mapR.put("acc_year", jsonObjR.get("acc_year"));
					mapR.put("bank_id", jsonObjR.get("bank_id"));
					mapR.put("wcheck_money", jsonObjR.get("wcheck_money"));
					mapR.put("isCheck", "0");
	
					String key = conR.toString();
					rightMap.put(key, mapR);
					rnum++;
				}
	
				// 处理对应关系
				Iterator itl = leftMap.keySet().iterator();
				while (itl.hasNext()) {
					String key1 = String.valueOf(itl.next());
					String keyL = key1.split("@")[1];
	
					Iterator itr = rightMap.keySet().iterator();
					while (itr.hasNext()) {
						
						String key2 = String.valueOf(itr.next());
						String keyR = key2.split("@")[1];
						if (keyR.equals(keyL)) {
							Map<String, Object> lMap = leftMap.get(key1);
							Map<String, Object> rMap = rightMap.get(key2);
							//左右两侧均未对账
							if ("0".equals(lMap.get("isCheck").toString())) {
								if ("0".equals(rMap.get("isCheck").toString())) {
									Map<String, Object> vmap = new HashMap<String, Object>();// 对应关系map
									Map<String, Object> bankMap = new HashMap<String, Object>();// 更新map
									Map<String, Object> tellMap = new HashMap<String, Object>();// 更新map
																	
									vmap.put("veri_check_id", veri_check_id);
									vmap.put("group_id", entityMap.get("group_id"));
									vmap.put("hos_id", entityMap.get("hos_id"));
									vmap.put("copy_code", entityMap.get("copy_code"));
									vmap.put("acc_year", accYear);
									vmap.put("acc_month", accMonth);
									vmap.put("create_user", user_id);
									vmap.put("create_date", date);
									vmap.put("is_auto", "0");
	
									bankMap.put("group_id", entityMap.get("group_id"));
									bankMap.put("hos_id", entityMap.get("hos_id"));
									bankMap.put("copy_code", entityMap.get("copy_code"));
									bankMap.put("acc_year", entityMap.get("acc_year"));
									bankMap.put("is_check", "1");
									bankMap.put("check_user", entityMap.get("user_id"));
									bankMap.put("check_date", date);
									
									tellMap.putAll(bankMap);
	
									// 若单位账金额 >= 银行账金额 单位账金额=单位账金额-银行账金额
									if (Double.parseDouble(lMap.get("wcheck_money").toString()) >= Double.parseDouble(rMap.get("wcheck_money").toString())) {
										double num =Double.parseDouble(lMap.get("wcheck_money").toString())- Double.parseDouble(rMap.get("wcheck_money").toString());
										//保留两位小数
										String numd=String.format("%.2f", num);
										//num=(double)Math.round(num * 100) / 100;
										vmap.put("veri_money", rMap.get("wcheck_money").toString());
	
										lMap.put("wcheck_money", numd);
										rMap.put("wcheck_money", 0);
										rMap.put("isCheck", "1");
	
										vmap.put("bank_id", rMap.get("bank_id"));
										bankMap.put("bank_id", rMap.get("bank_id"));
										bankUList.add(bankMap);
										
										// 当单位账金额等于0后，修改isCheck标识符，若是出纳对账，记录tell_id;同时记录左右两侧的id,加入到vmap中
										if ("0".equals(entityMap.get("check_method").toString())) {
											vmap.put("tell_id", lMap.get("tell_id"));
											vmap.put("vouch_id", "");
											vmap.put("vouch_check_id", "");
											veriList.add(vmap);
	
											if (num == 0) {
												tellMap.put("tell_id", lMap.get("tell_id"));
												lMap.put("isCheck", "1");
												tellUList.add(tellMap);
												break;
											}
	
										} else {
											vmap.put("tell_id", "");
											vmap.put("vouch_id", lMap.get("vouch_id"));
											vmap.put("vouch_check_id", lMap.get("vouch_check_id"));
	
											veriList.add(vmap);
	
											if (num == 0) {
												lMap.put("isCheck", "1");
												break;
											}
										}
	
									} else {
										// 单位账金额<银行行金额 直接更新单位账isCheck,记录单位账id
										double num = Double.parseDouble(rMap.get("wcheck_money").toString())- Double.parseDouble(lMap.get("wcheck_money").toString());
										//double num =Double.parseDouble(lMap.get("wcheck_money").toString())- Double.parseDouble(rMap.get("wcheck_money").toString());
										//保留两位小数
										String numd=String.format("%.2f", num);
										
										vmap.put("veri_money", lMap.get("wcheck_money").toString());
										vmap.put("bank_id", rMap.get("bank_id"));
	
										lMap.put("wcheck_money", 0);
										lMap.put("isCheck", "1");
	
										rMap.put("wcheck_money", num);
	
										// 根据对账方式记录不同的id
										if ("0".equals(entityMap.get("check_method").toString())) {
											tellMap.put("tell_id", lMap.get("tell_id"));
											tellUList.add(tellMap);
											
											vmap.put("tell_id", lMap.get("tell_id"));
											vmap.put("vouch_id", "");
											vmap.put("vouch_check_id", "");
											
											
										} else {
											vmap.put("tell_id", "");
											vmap.put("vouch_id", lMap.get("vouch_id"));
											vmap.put("vouch_check_id", lMap.get("vouch_check_id"));
	
										}
	
										// 若num=0，则更新银行账标识
										if (numd.equals("0.00")) {
											rMap.put("isCheck", "1");
											bankMap.put("bank_id", rMap.get("bank_id"));
											bankUList.add(bankMap);
											
											veriList.add(vmap);
											break;
										} else {
											veriList.add(vmap);
										}
									}
								}
							}
						}
					}
				}
			}

			//logMap
			Map<String,Object> logMap = new HashMap<String,Object>();
			logMap.put("group_id", entityMap.get("group_id"));
			logMap.put("hos_id", entityMap.get("hos_id"));
			logMap.put("copy_code", entityMap.get("copy_code"));
			logMap.put("acc_year", entityMap.get("acc_year"));
			logMap.put("create_user", entityMap.get("user_id"));
			logMap.put("create_date", new Date());
			logMap.put("note", "记录 " + entityMap.get("subj_name").toString() + " 的对账记录！");
			
			//1、插入对应关系表 acc_bank_veri
			if(veriList.size() > 0){
				accUnitBankCheckMapper.addAccBankVeriMain(veriList);
			}
			//2、更新acc_tell表
			if(tellUList.size()>0){
				accUnitBankCheckMapper.updateBatchAccTellCheckState(tellUList);
			}
			//3、更新acc_bank_check表
			if(bankUList.size() > 0){
				accUnitBankCheckMapper.updateBatchAccBankCheckState(bankUList);
			}
			//4、插入日志表 acc_bank_veri_log
			accUnitBankCheckMapper.addAccBankVeriMainLog(logMap);
				
			return  "{\"msg\":\"对账成功！\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			// return "{\"error\":\"对账失败 数据库异常 请联系管理员! 错误编码 addAccTellVeri\"}";
		}
	}

	/**
	 * 取消对账
	 */
	@Override
	public String deleteAccBankVeriIsChecked(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String,Object>> bankList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> tellList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> veriList = new ArrayList<Map<String,Object>>();

			JSONArray jsonL = JSONArray.parseArray(entityMap.get("check_dataL").toString());
			JSONArray jsonR = JSONArray.parseArray(entityMap.get("check_dataR").toString());
			
			if((jsonL == null || jsonL.isEmpty() || jsonL.size() == 0) || (jsonR == null || jsonR.isEmpty() || jsonR.size() == 0)){
				//只勾选单位账或银行账
				Map<String, Object> vmap = null;// 对应关系map
				Map<String, Object> bankMap = null;// 更新map
				Map<String, Object> tellMap = null;// 更新map
				JSONObject jsonObj = null;
				if(jsonL.size() > 0){
					Iterator<Object> itL = jsonL.iterator();
					while (itL.hasNext()) {
						jsonObj = JSONObject.parseObject(itL.next().toString());
						vmap = new HashMap<String, Object>();  //对应关系Map
						tellMap = new HashMap<String, Object>();  //出纳Map
						
						//对应关系
						vmap.put("veri_check_id", jsonObj.get("veri_check_id"));
						vmap.put("group_id", entityMap.get("group_id"));
						vmap.put("hos_id", entityMap.get("hos_id"));
						vmap.put("copy_code", entityMap.get("copy_code"));
						vmap.put("acc_year", entityMap.get("acc_year"));
						vmap.put("tell_id", jsonObj.get("tell_id"));
						vmap.put("vouch_id", jsonObj.get("vouch_id"));
						vmap.put("vouch_check_id", jsonObj.get("vouch_check_id"));
						
						//判断对账方式
						if ("0".equals(entityMap.get("check_method").toString())) {
							//出纳与银行对账
							//记录出纳信息以便更新状态
							tellMap.put("group_id", entityMap.get("group_id"));
							tellMap.put("hos_id", entityMap.get("hos_id"));
							tellMap.put("copy_code", entityMap.get("copy_code"));
							tellMap.put("acc_year", entityMap.get("acc_year"));
							tellMap.put("tell_id", jsonObj.get("tell_id"));
							tellMap.put("is_check", "0");
							tellMap.put("check_user", null);
							tellMap.put("check_date", null);
							
							tellList.add(tellMap);
						}
						
						veriList.add(vmap);
					}
				}
				if(jsonR.size() > 0){
					Iterator<Object> itR = jsonR.iterator();
					while (itR.hasNext()) {
						jsonObj = JSONObject.parseObject(itR.next().toString());
						vmap = new HashMap<String, Object>();  //对应关系Map
						bankMap = new HashMap<String, Object>();  //出纳Map
						
						//对应关系
						vmap.put("veri_check_id", jsonObj.get("veri_check_id"));
						vmap.put("group_id", entityMap.get("group_id"));
						vmap.put("hos_id", entityMap.get("hos_id"));
						vmap.put("copy_code", entityMap.get("copy_code"));
						vmap.put("acc_year", entityMap.get("acc_year"));
						vmap.put("bank_id", jsonObj.get("bank_id"));  //自己和自己对账，对应银行ID为0
						
						veriList.add(vmap);
						
						//记录银行账信息以便更新状态
						bankMap.put("group_id", entityMap.get("group_id"));
						bankMap.put("hos_id", entityMap.get("hos_id"));
						bankMap.put("copy_code", entityMap.get("copy_code"));
						bankMap.put("acc_year", entityMap.get("acc_year"));
						bankMap.put("bank_id", jsonObj.get("bank_id"));
						bankMap.put("is_check", "0");
						bankMap.put("check_user", null);
						bankMap.put("check_date", null);
						
						bankList.add(bankMap);
					}
				}
			}else{
				Map<String,Double> left = new HashMap<String,Double>();	//要比较的对象
				Map<String,Double> right = new HashMap<String,Double>();//要比较的对象
				
				// 处理左侧json串
				int a=0;
				Iterator itL = jsonL.iterator();
				while (itL.hasNext()) {
					JSONObject jsonObjL = JSONObject.parseObject(itL.next().toString());
					Map<String, Object> mapL = new HashMap<String, Object>();
					
					mapL.put("group_id", jsonObjL.get("group_id"));
					mapL.put("hos_id", jsonObjL.get("hos_id"));
					mapL.put("copy_code", jsonObjL.get("copy_code"));
					mapL.put("acc_year", jsonObjL.get("acc_year"));
					mapL.put("veri_check_id", jsonObjL.get("veri_check_id"));
					mapL.put("tell_id", jsonObjL.get("tell_id"));
					mapL.put("vouch_id", jsonObjL.get("vouch_id"));
					mapL.put("vouch_check_id", jsonObjL.get("vouch_check_id"));
					mapL.put("ycheck_money", jsonObjL.get("ycheck_money"));
					
					mapL.put("is_check", "0");
					mapL.put("check_user", "");
					mapL.put("check_date", "");
					
					if(left.get(mapL.get("veri_check_id").toString())==null){
						Double num=Double.parseDouble(mapL.get("ycheck_money").toString());
						String numd=String.format("%.2f", num);
						left.put(mapL.get("veri_check_id").toString(),Double.parseDouble(numd));
					}else{
						Double num=Double.parseDouble(left.get(mapL.get("veri_check_id").toString()).toString())+Double.parseDouble(mapL.get("ycheck_money").toString());
						String numd=String.format("%.2f", num);
						left.put(mapL.get("veri_check_id").toString(),Double.parseDouble(numd));
					}
					
					tellList.add(mapL);
					veriList.add(mapL);
					a++;
				}
	
				// 处理右侧json串
				Iterator itR = jsonR.iterator();
				while (itR.hasNext()) {
					JSONObject jsonObjR = JSONObject.parseObject(itR.next().toString());
					Map<String, Object> mapR = new HashMap<String, Object>();
					
					mapR.put("group_id", jsonObjR.get("group_id"));
					mapR.put("hos_id", jsonObjR.get("hos_id"));
					mapR.put("copy_code", jsonObjR.get("copy_code"));
					mapR.put("acc_year", jsonObjR.get("acc_year"));
					mapR.put("bank_id", jsonObjR.get("bank_id"));
					mapR.put("ycheck_money", jsonObjR.get("ycheck_money"));
					mapR.put("veri_check_id", jsonObjR.get("veri_check_id"));
					
					mapR.put("is_check", "0");
					mapR.put("check_user", "");
					mapR.put("check_date", "");
					
					if(right.get(mapR.get("veri_check_id").toString())==null){
						Double num=Double.parseDouble(mapR.get("ycheck_money").toString());
						String numd=String.format("%.2f", num);
						right.put(mapR.get("veri_check_id").toString(),Double.parseDouble(numd));
					}else{
						Double num=Double.parseDouble(right.get(mapR.get("veri_check_id").toString()).toString())+Double.parseDouble(mapR.get("ycheck_money").toString());
						String numd=String.format("%.2f", num);
						right.put(mapR.get("veri_check_id").toString(),Double.parseDouble(numd));
					}
					
					bankList.add(mapR);
					veriList.add(mapR);
				}
				
				int count = 0;//查看是否勾选同一时间核销的ID
				int countC = 0;
				for (String key : left.keySet()) {
					if(right.get(key) ==null){
						count++;
					}else{
						if(!left.get(key).equals(right.get(key))){
							countC++;
						}
					}
				}
				
				if(count != 0){
					return "{\"msg\":\"取消对账失败，请勾选相对应的借贷方.\",\"state\":\"false\"}";
				}
				if(countC!=0){
					return "{\"msg\":\"取消对账失败，金额不相等.\",\"state\":\"false\"}";
				}
			}

			//logMap
			Map<String,Object> logMap = new HashMap<String,Object>();
			logMap.put("group_id", entityMap.get("group_id"));
			logMap.put("hos_id", entityMap.get("hos_id"));
			logMap.put("copy_code", entityMap.get("copy_code"));
			logMap.put("acc_year", entityMap.get("acc_year"));
			logMap.put("create_user", entityMap.get("user_id"));
			logMap.put("create_date", new Date());
			logMap.put("note", "勾选取消科目："+entityMap.get("subj_name").toString() + "的核销记录！");
			
			if(veriList.size()>0){
				//1、删除对应关系表
				accUnitBankCheckMapper.deleteAccBankVericationVeri(veriList);
				//2、更新acc_tell表
				if(tellList.size() > 0){
					accUnitBankCheckMapper.updateBatchAccTellCheckState(tellList);
				}
				//3、更新acc_bank_check表
				if(bankList.size() > 0){
					accUnitBankCheckMapper.updateBatchAccBankCheckState(bankList);
				}
				//4、插入日志表
				accUnitBankCheckMapper.addAccBankVeriMainLog(logMap);
				
				return "{\"msg\":\"取消对账成功.\",\"state\":\"true\"}";
			}else{
				return "{\"msg\":\"此期间没有需要取消的对账数据.\",\"state\":\"false\"}";
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"批量对账失败!\"}");
		}
	}

	/**
	 * 批量对账
	 * @param mapVo
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAccUnitBankVeri(Map<String, Object> entityMap) throws DataAccessException {
		try {

			List<Map<String, Object>> veriList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> bankUList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> tellUList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> leftList = new ArrayList<Map<String,Object>>();
			String cashYearMonth=MyConfig.getMyAccYearMonth().getCurYearMonthCash();//取出纳期间
			String accYear=null;
			String accMonth=null;
			if(cashYearMonth!=null && !"".equals(cashYearMonth)){
				accYear=cashYearMonth.substring(0,4);
				accMonth=cashYearMonth.substring(4,6);
			}else{
				accYear=MyConfig.getCurAccYearMonth().substring(0,4);
				accMonth=MyConfig.getCurAccYearMonth().substring(4,6);
			}
			
			//查询单位账数据,历史迁移导致bank_id为空，所以不能根据关系表直接匹配
			entityMap.put("stateSql", " and abv.veri_check_id is null and nvl(avc.is_check, 0)=0 ");
			entityMap.put("tsql", " and (nvl(avc.debit,0) > 0 or nvl(avc.credit,0) > 0)  ");
			if("0".equals(MyConfig.getSysPara("018").toString())){
				entityMap.put("stateSql", " and abv.veri_check_id is null and nvl(avc.is_check, 0)=0 ");
				leftList = JsonListMapUtil.toListMapLower(accUnitBankCheckMapper.queryAccBankTellData(entityMap));
			}else{
				entityMap.put("stateSql", " and abv.veri_check_id is null ");
				leftList = JsonListMapUtil.toListMapLower(accUnitBankCheckMapper.queryAccBankVouchCheckData(entityMap));
			}
			//查询银行账数据
			entityMap.put("stateSql", " and abv.veri_check_id is null and nvl(avc.is_check, 0)=0 ");
			List<Map<String,Object>> bankList = JsonListMapUtil.toListMapLower(accUnitBankCheckMapper.queryAccBankCheckMain(entityMap));
			
			Map<String, Map<String,Object>> leftMap = new LinkedHashMap<String,Map<String,Object>>();
			Map<String, Map<String,Object>> rightMap = new LinkedHashMap<String,Map<String,Object>>();
			
			//处理左侧list
			int lnum = 0;
			for(Map<String,Object> map : leftList){
				Map<String, Object> mapL = new HashMap<String, Object>();

				StringBuffer conL = new StringBuffer();
				conL.append(String.valueOf(lnum) + '@');
				conL.append(map.get("group_id").toString() + map.get("hos_id").toString() + map.get("copy_code").toString()
						+ map.get("acc_year").toString() + map.get("wcheck_money").toString());
				conL.append("@").append(map.get("directs").toString());
				conL.append("@").append(map.get("occur_date").toString());
				
				//结算方式
				if("1".equals(entityMap.get("pay_type_code"))){
					conL.append(map.get("pay_type_code"));
				}
				//票据号
				if("1".equals(entityMap.get("check_no"))){
					conL.append(map.get("check_no"));
				}
				
				mapL.put("group_id", map.get("group_id"));
				mapL.put("hos_id", map.get("hos_id"));
				mapL.put("copy_code", map.get("copy_code"));
				mapL.put("acc_year", map.get("acc_year"));
				mapL.put("vouch_id", map.get("vouch_id"));
				mapL.put("vouch_check_id", map.get("vouch_check_id"));
				mapL.put("tell_id", map.get("tell_id"));
				mapL.put("wcheck_money", map.get("wcheck_money"));
				mapL.put("isCheck", "0");
				
				
				String key = conL.toString();
				leftMap.put(key, mapL);
				lnum++;
			}

			//处理银行账数据
			int rnum = 0;
			for(Map<String,Object> map : bankList){
				Map<String, Object> mapR = new HashMap<String, Object>();

				StringBuffer conR = new StringBuffer();
				conR.append(String.valueOf(rnum) + '@');
				conR.append(map.get("group_id").toString() + map.get("hos_id").toString() + map.get("copy_code").toString()
						+ map.get("acc_year").toString() + map.get("wcheck_money").toString());
				conR.append("@").append(map.get("directs").toString());
				conR.append("@").append(map.get("occur_date").toString());
				//结算方式
				if("1".equals(entityMap.get("pay_type_code"))){
					conR.append(map.get("pay_type_code"));
				}
				//票据号
				if("1".equals(entityMap.get("check_no"))){
					conR.append(map.get("check_no"));
				}
				
				mapR.put("group_id", map.get("group_id"));
				mapR.put("hos_id", map.get("hos_id"));
				mapR.put("copy_code", map.get("copy_code"));
				mapR.put("acc_year", map.get("acc_year"));
				mapR.put("bank_id", map.get("bank_id"));
				mapR.put("wcheck_money", map.get("wcheck_money"));
				mapR.put("isCheck", "0");
				mapR.put("occur_date", map.get("occur_date"));

				String key = conR.toString();
				rightMap.put(key, mapR);
				rnum++;
			}
			
			// 处理对应关系
			int i = 0;
			Iterator itl = leftMap.keySet().iterator();
			while (itl.hasNext()) {
				i++;
				String key1 = String.valueOf(itl.next());
				String keyL = key1.split("@")[1];
				int dL = Integer.parseInt(key1.split("@")[2]);//左侧方向
				String datl=key1.split("@")[3];
				Iterator itr = rightMap.keySet().iterator();
				while (itr.hasNext()) {
					String key2 = String.valueOf(itr.next());
					String keyR = key2.split("@")[1];
					int dR = Integer.parseInt(key2.split("@")[2]);//右侧方向
					String datr=key2.split("@")[3];
					/*datl.equals(datr) &&*/ 
					
					if(keyR.equals(keyL) ){
						
				/*	if () {*/
						
						Map<String, Object> lMap = leftMap.get(key1);
						Map<String, Object> rMap = rightMap.get(key2);
						//不同方向才能比对
						if(dL != dR){
							if ("0".equals(rMap.get("isCheck").toString())) {
								while (Double.parseDouble(lMap.get("wcheck_money").toString()) > 0) {
									Map<String, Object> vmap = new HashMap<String, Object>();//对应关系map
									Map<String,Object> bankMap = new HashMap<String,Object>();//更新map
									Map<String,Object> tellMap = new HashMap<String,Object>();//更新map
									
									
									Date date = new Date();
									String veri_check_id = UUIDLong.absStringUUID();

									vmap.put("veri_check_id", veri_check_id);
									vmap.put("group_id", entityMap.get("group_id"));
									vmap.put("hos_id", entityMap.get("hos_id"));
									vmap.put("copy_code", entityMap.get("copy_code"));
									//业务日期大于当前期间以业务日期为准
									String occurDate = rMap.get("occur_date").toString();
									occurDate = occurDate.substring(0,4) + occurDate.substring(5,7);
									if(Integer.parseInt(occurDate) > Integer.parseInt(accYear + accMonth)){
										accYear = occurDate.substring(0,4);
										accMonth = occurDate.substring(4,6);
									}
									vmap.put("acc_year", accYear);
									vmap.put("acc_month", accMonth);
									vmap.put("create_user", SessionManager.getUserId());
									vmap.put("create_date", date);
									vmap.put("is_auto", "1");
									vmap.put("is_check", "1");
									vmap.put("check_user", SessionManager.getUserId());
									vmap.put("check_date", date);
									
									
									bankMap.put("group_id", entityMap.get("group_id"));
									bankMap.put("hos_id", entityMap.get("hos_id"));
									bankMap.put("copy_code", entityMap.get("copy_code"));
									bankMap.put("acc_year", entityMap.get("acc_year"));
									bankMap.put("is_check", "1");
									bankMap.put("check_user", entityMap.get("user_id"));
									bankMap.put("check_date", date);
									bankMap.put("occur_date", rMap.get("occur_date"));
									tellMap.putAll(bankMap);

									double num = Double.parseDouble(lMap.get("wcheck_money").toString()) - Double.parseDouble(rMap.get("wcheck_money").toString());
									vmap.put("veri_money", rMap.get("wcheck_money").toString());
									vmap.put("bank_id", rMap.get("bank_id"));
									bankMap.put("bank_id", rMap.get("bank_id"));
									bankUList.add(bankMap);

									lMap.put("wcheck_money", num);
									rMap.put("wcheck_money", 0);
									rMap.put("isCheck", "1");

									// 当单位账金额等于0后，修改isCheck标识符，若是出纳对账，记录tell_id;同时记录左右两侧的id,加入到vmap中
									if ("0".equals(MyConfig.getSysPara("018").toString())) {
										if (num == 0) {
											tellMap.put("tell_id", lMap.get("tell_id"));
											lMap.put("isCheck", "1");
											tellUList.add(tellMap);
										}
										vmap.put("tell_id", lMap.get("tell_id"));
										vmap.put("vouch_id", "");
										vmap.put("vouch_check_id", "");
									} else {
										if (num == 0) {
											lMap.put("isCheck", "1");
										}
										vmap.put("tell_id", "");
										vmap.put("vouch_id", lMap.get("vouch_id"));
										vmap.put("vouch_check_id", lMap.get("vouch_check_id"));
										
									}
									veriList.add(vmap);
									
								}
							}
						}
						// dL dR 比较
					/*}*/
				}
				}
			}

			//logMap
			Map<String,Object> logMap = new HashMap<String,Object>();
			logMap.put("group_id", entityMap.get("group_id"));
			logMap.put("hos_id", entityMap.get("hos_id"));
			logMap.put("copy_code", entityMap.get("copy_code"));
			logMap.put("acc_year", entityMap.get("acc_year"));
			logMap.put("create_user", entityMap.get("user_id"));
			logMap.put("create_date", new Date());
			logMap.put("note", "记录 " + entityMap.get("subj_name").toString() + " 的对账记录！");
			
			if(veriList.size() > 0){
			
				//1、插入对应关系表 acc_bank_veri
				accUnitBankCheckMapper.addAccBankVeriMain(veriList);
				//2、更新acc_tell表
				if(tellUList.size()>0){
					accUnitBankCheckMapper.updateBatchAccTellCheckState(tellUList);
				}
				//3、更新acc_bank_check表
				if(bankUList.size() > 0){
					accUnitBankCheckMapper.updateBatchAccBankCheckState(bankUList);
				}
				//4、插入日志表 acc_bank_veri_log
				accUnitBankCheckMapper.addAccBankVeriMainLog(logMap);
				
				return  "{\"msg\":\"对账成功！\",\"state\":\"true\"}";
				
			}else{
				return "{\"msg\":\"对账失败，没有要对账的数据.\",\"state\":\"false\"}";
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			// return "{\"error\":\"对账失败 数据库异常 请联系管理员! 错误编码 addAccTellVeri\"}";
		}
	}

	/**
	 * 批量取消对账
	 */
	@Override
	public String deleteBatchAccBankVeri(Map<String, Object> entityMap) throws DataAccessException {
		try {
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put("group_id", entityMap.get("group_id"));
			logMap.put("hos_id", entityMap.get("hos_id"));
			logMap.put("copy_code", entityMap.get("copy_code"));
			logMap.put("acc_year", entityMap.get("acc_year"));
			logMap.put("create_user", entityMap.get("user_id"));
			logMap.put("create_date", new Date());
			String message = "批量取消  科目：" + entityMap.get("subj_name").toString() + " 在  " + entityMap.get("begin_date").toString() + " 至 "
					+ entityMap.get("end_date").toString() + " 时间范围内 " + entityMap.get("user_name") + " 的核销记录！";
			logMap.put("note", message);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			// dateFormat.format(now)
			entityMap.put("begin_date", dateFormat.format(DateUtil.stringToDate(entityMap.get("begin_date").toString(), "yyyy-MM-dd")));
			entityMap.put("end_date", dateFormat.format(DateUtil.stringToDate(entityMap.get("end_date").toString(), "yyyy-MM-dd")));
			
			List<Map<String, Object>> list = JsonListMapUtil.toListMapLower(accUnitBankCheckMapper.queryAccBankVericationCount(entityMap));
			List<Map<String, Object>> veriList = new ArrayList<Map<String, Object>>();
			if (list.size() > 0) {
				for (Map<String, Object> map : list) {
					Map<String, Object> mapV = new HashMap<String, Object>();
					mapV.putAll(map);
					mapV.put("is_check", "0");
					mapV.put("check_user", "");
					mapV.put("check_date", "");
					veriList.add(mapV);
				}
			}

			if (veriList.size() > 0) {
				// 1、update acc_tell
				if ("0".equals(MyConfig.getSysPara("018").toString())) {
					accUnitBankCheckMapper.updateBatchAccTellCheckState(veriList);
				}
				// 2、update acc_bank_check
				accUnitBankCheckMapper.updateBatchAccBankCheckState(veriList);
				// 3、删除acc_bank_veri中的记录 借、贷
				accUnitBankCheckMapper.deteleBatchAccBankVeri(veriList);
				// 4、往acc_vouch_veri_log中插入记录
				accUnitBankCheckMapper.addAccBankVeriMainLog(logMap);
				return "{\"msg\":\"取消对账成功！\",\"state\":\"true\"}";
			} else {
				return "{\"warn\":\"此期间没有该会计科目的对账记录！\",\"state\":\"false\"}";
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			// return "{\"error\":\"对账失败 数据库异常 请联系管理员! 错误编码 addAccTellVeri\"}";
		}

	}

	/**
	 * 单位银行对账 查看单位账明细
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccBankVerDetailD(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccVouchCheck> list = new ArrayList<AccVouchCheck>();
		if (entityMap.get("check_method").equals("0")) {
			list = accUnitBankCheckMapper.queryAccBankVerAccTellDetail(entityMap, rowBounds);
		} else {
			list = accUnitBankCheckMapper.queryAccBankVerAccVouchCheckDetail(entityMap, rowBounds);
		}

		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	/**
	 * 单位银行对账 查看单位账明细
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccBankVerDetailB(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccBankCheck> list = accUnitBankCheckMapper.queryAccBankVerAccBankCheckDetail(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

}
