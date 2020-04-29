package com.chd.hrp.acc.serviceImpl.tell;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.commonbuilder.AccCashierTypeMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccVouchTypeMapper;
import com.chd.hrp.acc.dao.tell.AccTellMapper;
import com.chd.hrp.acc.dao.termend.AccTermendTemplateMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.entity.AccCashierType;
import com.chd.hrp.acc.entity.AccTell;
import com.chd.hrp.acc.service.tell.AccCashAccountService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;

@Service("accCashAccountService")
public class AccCashAccountServiceImpl implements AccCashAccountService {  
	
	@Resource(name = "accTellMapper")
	private final AccTellMapper accTellMapper = null;
	
	@Resource(name = "accTermendTemplateMapper")
	private final AccTermendTemplateMapper accTermendTemplateMapper = null;
	
	@Resource(name = "accVouchTypeMapper")
	private final AccVouchTypeMapper accVouchTypeMapper = null;
	
	@Resource(name = "accSubjMapper")
	private final AccSubjMapper accSubjMapper = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	
	@Resource(name = "accCashierTypeMapper")
	private final AccCashierTypeMapper accCashierTypeMapper = null;
	
	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;
	
	private static Logger logger = Logger.getLogger(AccTellServiceImpl.class);
	
	
	/**
	 * @Description 
	 * 现金出纳账<BR> 添加AccTell
	 * @param AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccCashAccount(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			if("01".equals(entityMap.get("tell_type_code").toString().split(" ")[0])){
				
				if(Double.parseDouble(entityMap.get("debit").toString())>0){  
					
					entityMap.put("debit", entityMap.get("debit"));
					
					entityMap.put("credit", "0");
				} else if(Double.parseDouble(entityMap.get("credit").toString())>0){
					
					entityMap.put("credit", entityMap.get("credit"));
					
					entityMap.put("debit", "0");
					
				}
				
			}else if("02".equals(entityMap.get("tell_type_code").toString().split(" ")[0])){
				
				if(null!=entityMap.get("debit")&&!"".equals(entityMap.get("debit").toString())){
					
					entityMap.put("debit", entityMap.get("debit"));
					
					entityMap.put("credit", "0");
					
				} else if(Double.parseDouble(entityMap.get("credit").toString())>0){
					
					entityMap.put("credit", entityMap.get("credit"));
					
					entityMap.put("debit", "0");
					
				}
				
			}else{
				
				/*if(Double.parseDouble(entityMap.get("debit").toString())>0){
					
					entityMap.put("debit", entityMap.get("debit"));
					
					entityMap.put("credit", "0");
				} else if(Double.parseDouble(entityMap.get("credit").toString())>0){*/
					
					entityMap.put("debit", entityMap.get("debit"));
					
					entityMap.put("credit", entityMap.get("credit"));
					
				//}
				
			}
			
			if(!entityMap.containsKey("att_num")){
				
				entityMap.put("att_num", "0");
				
			}
			
			if("".equals(entityMap.get("tell_number").toString())){
			
			/*根据制单日期生成流水号*/	
			Map<String, Object> mapVoMap = new HashMap<String, Object>();
			
			mapVoMap.put("group_id", entityMap.get("group_id"));
			
			mapVoMap.put("hos_id", entityMap.get("hos_id"));
			
			mapVoMap.put("copy_code", entityMap.get("copy_code"));
			
			String number=MyConfig.getSysPara("034").toString();
			
			if("1".equals(number)){
				
				String occur_date = entityMap.get("occur_date").toString();

				String year = occur_date.substring(0, 4);
				
				String month = occur_date.substring(5, 7);
				
				mapVoMap.put("year_month", year+month);
				
			}else if("0".equals(number)){
				
				mapVoMap.put("occur_date", entityMap.get("occur_date"));
			}			
			
			/*02:科目性质编码 现金*/
			mapVoMap.put("subj_nature_code", "02");
			
			mapVoMap.put("tell_type_code", entityMap.get("tell_type_code").toString().split(" ")[0]);
			
			mapVoMap.put("cash_subj_code", entityMap.get("cash_subj_code"));
			
			long  accTell = accTellMapper.queryAccTellMaxTellNumber(mapVoMap);
			
			String tell_number = "";
			
			if(0 != accTell){
				
				tell_number = String.valueOf(accTell + 1);
				
				for (int i = tell_number.length(); i < 4; i++) {
					
					tell_number="0"+tell_number;
				}
				
				entityMap.put("tell_number",tell_number);
				
			}else {
				
				if("1".equals(number)){
					
					tell_number = "0001";
					
				}else if("0".equals(number)){
					
					tell_number = "0001";
					
				}
				
				for (int i = tell_number.length(); i < 4; i++) {
					
					tell_number="0"+tell_number;
				}
				
				entityMap.put("tell_number",tell_number);
				
			}
			
			}
			
			entityMap.put("pay_type_code", "");
			
			entityMap.put("tell_type_code", entityMap.get("tell_type_code").toString().split(" ")[0]);
			
			entityMap.put("is_init", 0);
			
			 accTellMapper.addAccTell(entityMap);
			
			/*String number=SessionManager.getAccParaMap().get("037").toString();
		
			if("1".equals(number)){
				
				entityMap.put("occur_time", entityMap.get("occur_date").toString().substring(0, 7));
				
				List<AccTell> list = accTellMapper.queryAccBrokenTell(entityMap);
				
				if(list.size()>0){
					
					entityMap.put("next_tell_number", list.get(0).getTell_number());
					
					String tell_number = "";
					
					if(list.size()>0){
						
						List<AccTell> tell_list=accTellMapper.queryAccBrokenTellList(entityMap);
						
						for (int i = 0; i < tell_list.size(); i++) {
							
							AccTell acc_Tell=tell_list.get(i);
							
							Map<String,Object> mapVo = new HashMap<String, Object>();
							
							mapVo.put("group_id", acc_Tell.getGroup_id());
							
							mapVo.put("hos_id", acc_Tell.getHos_id());
							
							mapVo.put("copy_code", acc_Tell.getCopy_code());
							
							mapVo.put("tell_id", acc_Tell.getTell_id());
							
							tell_number=String.valueOf(Integer.parseInt(acc_Tell.getTell_number())-list.get(0).getIs_check()+1);
							
							for (int j = String.valueOf(Integer.parseInt(acc_Tell.getTell_number())).length(); j < 4; j++) {
								
								tell_number="0"+tell_number;
								
							}
							
							mapVo.put("tell_number", tell_number);

							accTellMapper.updateAccTell(mapVo);
						}
						
					}
					
				}
			}*/
			
			return "{\"\":\"添加成功.\",\"state\":\"true\",\"occur_date\":\""+entityMap.get("occur_date")+"\",\"tell_id\":\""+entityMap.get("tell_id")+"\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccCashAccount\"}";
		}
	}
	
	/**
	 * @Description 
	 * 现金出纳账<BR> 删除AccTell
	 * @param AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteAccCashAccount(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accTellMapper.deleteAccTell(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteAccCashAccount\"}";
		}

	}
	
	/**
	 * @Description 
	 * 现金出纳账<BR> 批量删除AccTell
	 * @param AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccCashAccount(List<Map<String,Object>> entityList) throws DataAccessException {
		
		long  accTell=0;
		
		String err_msg="";
		
		try {

			//accTellMapper.deleteBatchAccTell(entityList);
			
		   /* 删除后从新排流水号*/
			/*String number=SessionManager.getAccParaMap().get("037").toString();
			
			if("1".equals(number)){
			
				Map<String, Object>  entityMap =entityList.get(0);
				
				entityMap.put("occur_time", entityMap.get("occur_date").toString().substring(0, 7));
				
				List<AccTell> list = accTellMapper.queryAccBrokenTell(entityMap);
				
				if(list.size()>0){
					
					entityMap.put("next_tell_number", list.get(0).getTell_number());
					
					String tell_number = "";
					
					if(list.size()>0){
						
						List<AccTell> tell_list=accTellMapper.queryAccBrokenTellList(entityMap);
						
						for (int i = 0; i < tell_list.size(); i++) {
							
							AccTell acc_Tell=tell_list.get(i);
							
							Map<String,Object> mapVo = new HashMap<String, Object>();
							
							mapVo.put("group_id", acc_Tell.getGroup_id());
							
							mapVo.put("hos_id", acc_Tell.getHos_id());
							
							mapVo.put("copy_code", acc_Tell.getCopy_code());
							
							mapVo.put("tell_id", acc_Tell.getTell_id());
							
							tell_number=String.valueOf(Integer.parseInt(acc_Tell.getTell_number())-list.get(0).getIs_check()+1);
							
							for (int j = String.valueOf(Integer.parseInt(acc_Tell.getTell_number())).length(); j < 4; j++) {
								
								tell_number="0"+tell_number;
								
							}
							
							mapVo.put("tell_number", tell_number);
	
							accTellMapper.updateAccTell(mapVo);
						}
						
					}
					
				}
			}*/
			
			for (int i = 0; i < entityList.size(); i++) {
				
				Map<String, Object> mapVo= entityList.get(i);
				
				accTell = accTellMapper.queryAccTellMaxTellNumber(mapVo);
				
				if(accTell==Long.parseLong(mapVo.get("tell_number").toString())&&"".equals(err_msg)){
					
					accTellMapper.deleteAccTell(mapVo);
					
				}else{
					
					err_msg="只能删除最大号登记！！";
					
				}
				
			}
			
			if("".equals(err_msg)){
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
			
			return "{\"msg\":\""+err_msg+".\",\"state\":\"true\"}";


		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccCashAccount\"}";

		}
	}

	@Override
	public String updateAccCashAccount(Map<String, Object> entityMap) throws DataAccessException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		int num=0;
		
		String occur_date="";
		
		try {
			
			if("01".equals(entityMap.get("tell_type_code").toString().split(" ")[0])){
				
				if(Double.parseDouble(entityMap.get("debit").toString())>0){
					
					entityMap.put("debit", entityMap.get("debit"));
					
					entityMap.put("credit", "0");
				} else if(Double.parseDouble(entityMap.get("credit").toString())>0){ 
					
					entityMap.put("credit", entityMap.get("credit"));
					
					entityMap.put("debit", "0");
					
				}
				
			}else if("02".equals(entityMap.get("tell_type_code").toString().split(" ")[0])){
				
				if(Double.parseDouble(entityMap.get("debit").toString())>0){
					
					entityMap.put("debit", entityMap.get("debit"));
					
					entityMap.put("credit", "0");
				} else if(Double.parseDouble(entityMap.get("credit").toString())>0){
					
					entityMap.put("credit", entityMap.get("credit"));
					
					entityMap.put("debit", "0");
					
				}
				
			}
			
			if("".equals(entityMap.get("check_no"))){
				
				entityMap.put("check_no", " ");
			}
			
			AccTell accTell= accTellMapper.queryAccTellDetail(entityMap);
			
			if(null!=accTell&&!sdf.format(accTell.getOccur_date()).substring(0,7).equals(entityMap.get("occur_date").toString().substring(0,7))
					||!accTell.getCash_subj_code().equals(entityMap.get("cash_subj_code"))||!accTell.getTell_type_code().equals(entityMap.get("tell_type_code"))){
				
				String number=MyConfig.getSysPara("034").toString();
				
				if("1".equals(number)){
					
					occur_date = entityMap.get("occur_date").toString();

					String year = occur_date.substring(0, 4);
					
					String month = occur_date.substring(5, 7);
					
					entityMap.put("year_month", year+month);
					
					entityMap.remove("occur_date");
					
				}
				
				long  tell_num = accTellMapper.queryAccTellMaxTellNumber(entityMap);
				
				String tell_number = "";
				
				if(0 != tell_num){
					
					tell_number = String.valueOf(tell_num+num + 1);
					
					for (int i = tell_number.length(); i < 4; i++) {
						
						tell_number="0"+tell_number;
					}
					
					entityMap.put("tell_number",tell_number);
					
				}else {
					
					if("1".equals(number)){
						
						tell_number = "0001";
						
					}else if("0".equals(number)){
						
						tell_number = "0001";
						
					}
					
					for (int i = tell_number.length(); i < 4; i++) {
						
						tell_number="0"+tell_number;
					}
					
					entityMap.put("tell_number",tell_number);
					
				}
			}
			
			if(null!=occur_date&&!"".equals(occur_date)){
				
				entityMap.put("occur_date",occur_date);
			}
			
			accTellMapper.updateAccTell(entityMap);
			
			/*String number=SessionManager.getAccParaMap().get("037").toString();
			
			if("1".equals(number)){
			
				entityMap.put("occur_time", accTell.getOccur_date().toString().substring(0, 7));
				
				List<AccTell> list = accTellMapper.queryAccBrokenTell(entityMap);
				
				if(list.size()>0){
					
					entityMap.put("next_tell_number", list.get(0).getTell_number());
					
					String tell_number = "";
					
					if(list.size()>0){
						
						List<AccTell> tell_list=accTellMapper.queryAccBrokenTellList(entityMap);
						
						for (int i = 0; i < tell_list.size(); i++) {
							
							AccTell acc_Tell=tell_list.get(i);
							
							Map<String,Object> mapVo = new HashMap<String, Object>();
							
							mapVo.put("group_id", acc_Tell.getGroup_id());
							
							mapVo.put("hos_id", acc_Tell.getHos_id());
							
							mapVo.put("copy_code", acc_Tell.getCopy_code());
							
							mapVo.put("tell_id", acc_Tell.getTell_id());
							
							tell_number=String.valueOf(Integer.parseInt(acc_Tell.getTell_number())-list.get(0).getIs_check()+1);
							
							for (int j = String.valueOf(Integer.parseInt(acc_Tell.getTell_number())).length(); j < 4; j++) {
								
								tell_number="0"+tell_number;
								
							}
							
							mapVo.put("tell_number", tell_number);
	
							accTellMapper.updateAccTell(mapVo);
						}
						
					}
					
				}
			
			}*/
			return "{\"\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCashAccount\"}";
		}
	}

	@Override
	public String updateAccTellMaxTellNumber(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			accTellMapper.updateAccTellMaxTellNumber(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception

			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccTellMaxTellNumber\"}";
		}

	}

	@Override
	public String updateBatchAccTell(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			accTellMapper.updateBatchAccTell(list);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception

			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchAccTell\"}";
		}
	}
	
	
	public void queryAccTellMaxTellResettingNumber(Map<String, Object> entityMap){
		
		String tell_number ="0001";
		
		int occur_date_resetting = Integer.parseInt(tell_number);
		
		entityMap.put("subj_nature_code","02");
		
		List<AccTell> list_accTells =  accTellMapper.queryAccTellMaxTellResettingNumber(entityMap);
		
		if(list_accTells.size() > 0){
			
			for (AccTell accTell : list_accTells) {
				
					String num="";
					
				 if(Long.parseLong(accTell.getTell_number()) != occur_date_resetting){
					 
					 Map<String, Object> map = new HashMap<String, Object>();
					 
					 map.put("group_id",entityMap.get("group_id"));
					 
					 map.put("hos_id",entityMap.get("hos_id"));
					 
					 map.put("copy_code",entityMap.get("copy_code"));
					 
					 map.put("occur_date",entityMap.get("occur_date"));
					 
					 map.put("tell_id",accTell.getTell_id());
					 
					 for (int i = String.valueOf(occur_date_resetting).length(); i < 4; i++) {
							
						  num="0"+num;
					 }
					 
					 map.put("tell_number",num+""+occur_date_resetting);
					 
					 accTellMapper.update(map);
					 
				 }
					
				 occur_date_resetting ++;

		}
			
		}
		 
	}

	@Override
	public String addAccTellVouch(Map<String,Object> entityMap) throws DataAccessException {
		
		try {
			
			List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();//存放凭证分录信息
			
			List<Map<String, Object>> logList = new ArrayList<Map<String,Object>>();//存放日志信息
			
			String paramVo = entityMap.get("ParamVo").toString();
			
			String tellId= "";
			
			for (String tell_id : paramVo.split(",")) {
				tellId+=tell_id.split("@")[0]+",";
			}
			
			//1.查询是否生成过凭证
			Map<String, Object> mapVo = new HashMap<String, Object>();
			
			mapVo.put("template_type_code", "Z011");
			mapVo.put("tell_id", tellId.substring(0, tellId.length()-1));
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			int flag = accTellMapper.queryAccTellByInit(mapVo);
			
			if(flag > 0){
				return "{\"error\":\"本期间已生成凭证，不能重复生成!\"}";
			}
			
			
			
			Date date = new Date();
			Long group_id = Long.parseLong(SessionManager.getGroupId());
			Long hos_id = Long.parseLong(SessionManager.getHosId());
			String copy_code = SessionManager.getCopyCode();
			String acc_year = SessionManager.getAcctYear();
			String user_id = SessionManager.getUserId();
			String template_type_code = "Z011";//凭证业务类型:出纳
			int vouch_row = 1;//分录行号
			String vouch_id = UUIDLong.absStringUUID();
			
			
			
			
			//存放凭证主表信息
			Map<String, Object> vouchMap = new HashMap<String, Object>();
			
			//------------------------处理主表信息 BEGIN-----------------------
			vouchMap.put("group_id",group_id);
			vouchMap.put("hos_id", hos_id);
			vouchMap.put("copy_code",copy_code);
			vouchMap.put("vouch_id", vouch_id);
			vouchMap.put("vouch_type_code",entityMap.get("vouch_type_code"));
			vouchMap.put("acc_year", acc_year);
			vouchMap.put("vouch_date",DateUtil.stringToDate(String.valueOf(entityMap.get("vouch_date")), "yyyy-MM-dd"));  //凭证日期
			vouchMap.put("vouch_att_num", 0);
			vouchMap.put("busi_type_code", template_type_code);  //业务类型
			vouchMap.put("create_date", date);  //制单日期
			
			//------------------------ 处理主表信息 END-------------------------
			
			
			
			
			//------------------------ 凭证分录信息 END-------------------------
			String [] detail=paramVo.split(",");
			Map<String, Object> debitMap =null;
			Map<String, Object> logMap=null;
			for (int i = 0; i<detail.length;i++) {
				
				String tell= detail[i];
				String[] data = tell.split("@");//数据中字段的数组
				
				String tell_id = data[0];
				String occur_date = data[1];
				String summary = data[2];
				//String att_num = data[3];
				//String other_subj_code = data[4];
				String debit = data[5];
				String credit = data[6];
				String subj_code = data[7];
				//String nature_code = data[8];
				
				
				vouchMap.put("subj_code", subj_code);
				//List<AccSubj> accSubj=accSubjMapper.queryAccSubj(vouchMap);
				
				/*增加空行做对方科目*/
				debitMap = new HashMap<String, Object>();
				debitMap.put("group_id", group_id);
				debitMap.put("hos_id", hos_id);
				debitMap.put("copy_code", copy_code);
				debitMap.put("vouch_id", vouch_id);
				debitMap.put("vouch_row", vouch_row);
				debitMap.put("summary", summary);
				debitMap.put("debit", credit);//反方向
				debitMap.put("credit",debit);//反方向
				debitMap.put("debit_w", 0);
				debitMap.put("credit_w",0);
				debitMap.put("subj_code","-");
				
				detailList.add(debitMap);
				vouch_row ++;
				
				
				debitMap = new HashMap<String, Object>();
				debitMap.put("group_id", group_id);
				debitMap.put("hos_id", hos_id);
				debitMap.put("copy_code", copy_code);
				debitMap.put("vouch_id", vouch_id);
				debitMap.put("vouch_row", vouch_row);
				debitMap.put("summary", summary);
				debitMap.put("debit", debit);
				debitMap.put("credit",credit);
				debitMap.put("debit_w", 0);
				debitMap.put("credit_w",0);
				debitMap.put("subj_code",subj_code);
				
				detailList.add(debitMap);
				vouch_row ++;
				//------------------------ 凭证分录信息 END-------------------------
				
				
				
				//------------------------ 日志信息 BEGIN-------------------------
				//存放日志信息
				logMap = new HashMap<String, Object>();
				
				logMap.put("group_id", group_id);
				logMap.put("hos_id", hos_id);
				logMap.put("copy_code", copy_code);
				logMap.put("acc_year", occur_date.substring(0, 4));
				logMap.put("acc_month", occur_date.substring(5, 7));
				logMap.put("vouch_id", vouch_id);  
				logMap.put("business_no", tell_id);
				logMap.put("busi_type_code",template_type_code);
				logMap.put("template_code",template_type_code);
				logMap.put("create_date", date);
				logMap.put("create_user", user_id);
				logList.add(logMap);
				
				//------------------------ 日志信息 END-------------------------
			}
			
			//转换分录数据-分录合并金额
			detailList = superVouchMapper.querySaveAutoVouchDetail(detailList);
			
			superVouchMapper.saveAutoVouch(vouchMap);//保存凭证主表
			superVouchMapper.saveAutoVouchDetail(detailList);//保存分录
			accTellMapper.saveAutoVouchLog(logList);//保存日志
			
			
			return"{\"state\":\"true\",\"vouch_id\":\""+vouch_id+"\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败.\"}";
		}
	}

	@Override
	public String addBatchAccCashAccount(String paramVo) throws DataAccessException {
		
		String group_id = SessionManager.getGroupId();
		
		String hos_id = SessionManager.getHosId();
		
		String copy_code = SessionManager.getCopyCode();
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		int num=0;
		
		for ( String id: paramVo.split(",")) {
			
			String[] ids = id.split("@");
			
			if(ids.length==9){
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				mapVo.put("group_id", group_id);
	            
	            mapVo.put("hos_id", hos_id);
	            
	            mapVo.put("copy_code", copy_code);
	            
	            mapVo.put("acc_year", ids[4].substring(0, 4));
				
	            if(null!= ids[0]&&!"".equals(ids[0])){
	            	
	            	mapVo.put("debit", ids[0]);//实际实体类变量
	            }else{
	            	
	            	mapVo.put("debit", 0);
	            }
	            
	            if(null!= ids[1]&&!"".equals(ids[1])){
	            	
	            	mapVo.put("credit", ids[1]);//实际实体类变量
	            }else{
	            	
	            	mapVo.put("credit", 0);
	            }
	            
	            mapVo.put("check_no", ids[2]);
	            
	            mapVo.put("att_num", ids[3]);
	            
	            mapVo.put("occur_date", ids[4]);
	            
	            mapVo.put("other_subj_code", ids[5]);
	            
	            mapVo.put("summary", ids[6]);
	            
	            mapVo.put("pay_type_code", "");
	            
	            mapVo.put("tell_type_code", ids[7]);
	            
	            mapVo.put("cash_subj_code", ids[8]);//实际实体类变量
	            
	            mapVo.put("create_user", SessionManager.getUserId());
	            
				mapVo.put("create_date", sdf.format(new Date()));
	            
	            Map<String, Object> mapVoMap = new HashMap<String, Object>();
				
				mapVoMap.put("group_id", mapVo.get("group_id"));
				
				mapVoMap.put("hos_id", mapVo.get("hos_id"));
				
				mapVoMap.put("copy_code", mapVo.get("copy_code"));
				
				String number=MyConfig.getSysPara("034").toString();
				
				if("1".equals(number)){
					
					String occur_date = mapVo.get("occur_date").toString();

					String year = occur_date.substring(0, 4);
					
					String month = occur_date.substring(5, 7);
					
					mapVoMap.put("year_month", year+month);
					
				}else if("0".equals(number)){
					
					mapVoMap.put("occur_date", mapVo.get("occur_date"));
				}			
				
				/*02:科目性质编码 现金*/
				mapVoMap.put("subj_nature_code", "02");
				
				mapVoMap.put("tell_type_code", mapVo.get("tell_type_code"));
				
				mapVoMap.put("cash_subj_code", mapVo.get("cash_subj_code"));
				
				long  accTell = accTellMapper.queryAccTellMaxTellNumber(mapVoMap);
				
				String tell_number = "";
				
				if(0 != accTell){
					
					tell_number = String.valueOf(accTell+num + 1);
					
					for (int i = tell_number.length(); i < 4; i++) {
						
						tell_number="0"+tell_number;
					}
					
					mapVo.put("tell_number",tell_number);
					
				}else {
					
					if("1".equals(number)){
						
						tell_number = "0001";
						
					}else if("0".equals(number)){
						
						tell_number = "0001";
						
					}
					
					for (int i = tell_number.length(); i < 4; i++) {
						
						tell_number="0"+tell_number;
					}
					
					mapVo.put("tell_number",tell_number);
					
				}
				
				mapVo.put("is_init",0);
				
				if(ExcelReader.validExceLColunm(ids,9)){
					
					if(null!= ids[9]&&!"".equals(ids[9])){
						
						updateList.add(mapVo);
					}
					
				}else{
					
					 listVo.add(mapVo);
					 
				}
				
	            num+=1;
				
			}else{
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				mapVo.put("group_id", group_id);
	            
	            mapVo.put("hos_id", hos_id);
	            
	            mapVo.put("copy_code", copy_code);
	            
	            mapVo.put("acc_year", ids[4].substring(0, 4));
				
	            if(null!= ids[0]&&!"".equals(ids[0])){
	            	
	            	mapVo.put("debit", ids[0]);//实际实体类变量
	            }else{
	            	
	            	mapVo.put("debit", 0);
	            }
	            
	            if(null!= ids[1]&&!"".equals(ids[1])){
	            	
	            	mapVo.put("credit", ids[1]);//实际实体类变量
	            }else{
	            	
	            	mapVo.put("credit", 0);
	            }
	            
	            mapVo.put("check_no", ids[2]);
	            
	            mapVo.put("att_num", ids[3]);
	            
	            mapVo.put("occur_date", ids[4]);
	            
	            mapVo.put("other_subj_code", ids[5]);
	            
	            mapVo.put("summary", ids[6]);
	            
	            mapVo.put("pay_type_code", "");
	            
	            mapVo.put("tell_type_code", ids[7]);
	            
	            mapVo.put("cash_subj_code", ids[8]);
	            
	            mapVo.put("tell_id", ids[9]);
	            
	            mapVo.put("create_user", SessionManager.getUserId());
	            
				mapVo.put("create_date", sdf.format(new Date()));
				
				updateList.add(mapVo);
			}
        }
		
		try {
			
			if(listVo.size()>0){
				
				accTellMapper.addBatchAccTell(listVo);
				
			}
			
			if(updateList.size()>0){
				
				accTellMapper.updateBatchAccTell(updateList);
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception

			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"操作失败\"}";
		}
	}
	
	@Override
	public String queryAccTellVouchState(String paramVo)
			throws DataAccessException {
		
		//存放凭证主表信息
		Map<String, Object> vouchMap = new HashMap<String, Object>();
		//凭证主表信息
		vouchMap.put("group_id", SessionManager.getGroupId());
		
		vouchMap.put("hos_id", SessionManager.getHosId());
		
		vouchMap.put("copy_code", SessionManager.getCopyCode());
		
		vouchMap.put("acc_year", paramVo.split(",")[0].split("@")[1].substring(0, 4));
		
		vouchMap.put("acc_month", paramVo.split(",")[0].split("@")[1].substring(5, 7));
		
		vouchMap.put("vouch_date", DateUtil.dateToString(accTermendTemplateMapper.queryAccVouchDateByYearMonth(vouchMap), "yyyy-MM-dd"));  //凭证日期
		
		vouchMap.put("tell_type_code",paramVo.split(",")[0].split("@")[8]);
		
		/**
		 * 根据出纳类型查询与之相对应的凭证类型
		 * 有对应关系则凭证类型则默认显示对应关系
		 * 否则弹出窗口，手工选择凭证类型
		 * */
		AccCashierType accCashierType = accCashierTypeMapper.queryAccCashierTypeByCode(vouchMap);
		
		if(accCashierType.getVouch_type_code()!=null){
			
			return "{\"state\":\"true\",\"vouch_type_code\":\""+accCashierType.getVouch_type_code()+"\"}";
			
		}else{
			
			return "{\"state\":\"false\"}";
			
		}
		
	}
	
	@Override
	public String addBatchAccCash(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
			Map<String,Object> numMap = new HashMap<String,Object>();
			String tell_number = "";
			
			for(Map<String,Object> map : entityList){
				if("".equals(map.get("tell_number").toString())){
					/*根据制单日期生成流水号*/	
					Map<String, Object> mapVoMap = new HashMap<String, Object>();
					mapVoMap.put("group_id", map.get("group_id"));
					mapVoMap.put("hos_id", map.get("hos_id"));
					mapVoMap.put("copy_code", map.get("copy_code"));
					String number=MyConfig.getSysPara("034").toString();
					
					if("1".equals(number)){
						String occur_date = map.get("occur_date").toString();
						String year = occur_date.substring(0, 4);
						String month = occur_date.substring(5, 7);
						mapVoMap.put("year_month", year+month);
					}else if("0".equals(number)){
						mapVoMap.put("occur_date", map.get("occur_date"));
					}			
					
					/*02:科目性质编码 现金*/
					mapVoMap.put("subj_nature_code", "02");
					mapVoMap.put("tell_type_code", map.get("tell_type_code"));
					mapVoMap.put("cash_subj_code", map.get("cash_subj_code"));
					
					String key = "";
					long  accTell;
					
					if("1".equals(number)){
						key = map.get("cash_subj_code").toString()+"-"+map.get("tell_type_code").toString()+"-"+number+"-"+mapVoMap.get("year_month").toString();
					}else if("0".equals(number)){
						key = map.get("cash_subj_code").toString()+"-"+map.get("tell_type_code").toString()+"-"+number+"-"+mapVoMap.get("occur_date").toString();
					}
					
					if(!numMap.containsKey(key)){
						
						accTell = accTellMapper.queryAccTellMaxTellNumber(mapVoMap);
						
						
						if(0 != accTell){
							tell_number = String.valueOf(accTell + 1);
							numMap.put(key, Integer.parseInt(tell_number)+1);
							
							for (int i = tell_number.length(); i < 4; i++) {
								tell_number="0"+tell_number;
							}
							map.put("tell_number",tell_number);
							
						}else {
							if("1".equals(number)){
								tell_number = "0001";
							}else if("0".equals(number)){
								tell_number = "0001";
							}
							
							numMap.put(key, 2);
							
							for (int i = tell_number.length(); i < 4; i++) {
								tell_number="0"+tell_number;
							}
							
							map.put("tell_number",tell_number);
						}
					}else{
						String num = String.valueOf(Integer.parseInt(numMap.get(key).toString()));
						for (int i = num.length(); i < 4; i++) {
							num="0"+num;
						}
						
						numMap.put(key, Integer.parseInt(num)+1);
						map.put("tell_number",num);
					}
				}
				
				//accTellMapper.updateAccTellMaxTellNumber(map);
				mapList.add(map);
			}
			
			accTellMapper.addBatchAccTell(mapList);
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 addBatchAccCash\"}";
		}
	}

	@Override
	public Long queryAccTellNextSeq() throws DataAccessException {
		return accTellMapper.queryAccTellNextSeq();
	}
	
}
