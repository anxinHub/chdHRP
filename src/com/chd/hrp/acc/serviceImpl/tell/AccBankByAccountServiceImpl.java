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
import com.chd.hrp.acc.dao.commonbuilder.AccCashierTypeMapper;
import com.chd.hrp.acc.dao.tell.AccTellMapper;
import com.chd.hrp.acc.entity.AccCashierType;
import com.chd.hrp.acc.entity.AccTell;
import com.chd.hrp.acc.service.tell.AccBankByAccountService;

@Service("accBankByAccountService")
public class AccBankByAccountServiceImpl implements AccBankByAccountService {

	@Resource(name = "accTellMapper")
	private final AccTellMapper accTellMapper = null;
	
	@Resource(name = "accCashierTypeMapper")
	private final AccCashierTypeMapper accCashierTypeMapper = null;
	
	private static Logger logger = Logger.getLogger(AccTellServiceImpl.class);
	
	
	/**
	 * @Description 
	 * 银行出纳账<BR> 添加AccTell
	 * @param AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccBankByAccount(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			if(entityMap.get("debit").toString().equals("")){
				
				entityMap.put("debit", "0");
			}
			
			if(entityMap.get("credit").toString().equals("")){
				
				entityMap.put("credit", "0");
				
			}
			if("".equals(entityMap.get("check_no").toString())){
				
				entityMap.put("check_no", "");
				
			}
			
			if(!entityMap.containsKey("att_num")){
				
				entityMap.put("att_num", "0");
				
			}
			entityMap.put("is_check", "0");
			
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
			mapVoMap.put("subj_nature_code", "03");
			
			mapVoMap.put("tell_type_code", entityMap.get("tell_type_code").toString().split(" ")[0]);
			mapVoMap.put("cash_subj_code", entityMap.get("cash_subj_code"));
			mapVoMap.put("other_subj_code", entityMap.get("other_subj_code"));
			
			long  accTell = accTellMapper.queryAccTellMaxTellNumber(mapVoMap);
			accTell++;
			String tell_number = "";
			if (0 != accTell) {
				tell_number = String.valueOf(accTell);
				if(tell_number.length() < 4){
					tell_number = String.format("%04d", accTell);
				}
				entityMap.put("tell_number", tell_number);
				
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
			}
			*/
			
			return "{\"\":\"添加成功.\",\"state\":\"true\",\"occur_date\":\""+entityMap.get("occur_date")+"\",\"tell_id\":\""+entityMap.get("tell_id")+"\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccCashAccount\"}";
		}
	}
	
	/**
	 * @Description 
	 * 银行出纳账<BR> 删除AccTell
	 * @param AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteAccBankByAccount(Map<String, Object> entityMap) throws DataAccessException {
		
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
	 * 银行出纳账<BR> 批量删除AccTell
	 * @param AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccBankByAccount(List<Map<String,Object>> entityList) throws DataAccessException {
		
		long  accTell=0;
		
		String err_msg="";
		
		try {
			
			//accTellMapper.deleteBatchAccTell(entityList);
			
			for (int i = 0; i < entityList.size(); i++) {
				
				Map<String, Object> mapVo= entityList.get(i);
				
				accTell = accTellMapper.queryAccTellMaxTellNumber(mapVo);
				
				if(accTell==Long.parseLong(mapVo.get("tell_number").toString())&&"".equals(err_msg)){
					
					accTellMapper.deleteAccTell(mapVo);
					
				}else{
					
					err_msg="只能删除最大号登记！！";
					
				}
				
			}
			
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
			
			if("".equals(err_msg)){
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
			
			return "{\"msg\":\""+err_msg+".\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccCashAccount\"}";

		}
	}
	
	/**
	 * @Description 
	 * 银行出纳账<BR> 修改AccTell
	 * @param AccTell entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccBankByAccount(Map<String, Object> entityMap) throws DataAccessException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		int num=0;
		
		String occur_date="";
		
		try {
			if("".equals(entityMap.get("debit").toString())){
				
				entityMap.put("debit", "0");
			}
			
			if("".equals(entityMap.get("credit").toString())){
				
				entityMap.put("credit", "0");
				
			}
			
			if("".equals(entityMap.get("check_no"))){
				
				entityMap.put("check_no", " ");
			}
			
			AccTell accTell= accTellMapper.queryAccTellDetail(entityMap);
			
			if(null!=accTell&&!sdf.format(accTell.getOccur_date()).substring(0,7).equals(entityMap.get("occur_date").toString().substring(0,7))
					||!accTell.getSubj_code().equals(entityMap.get("other_subj_code"))||!accTell.getTell_type_code().equals(entityMap.get("tell_type_code"))){
				
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
			
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccBankByAccount\"}";
		}
		
	}
	
	/**
	 * @Description 
	 * 银行出纳账<BR> 查询AccTell
	 * @param entityMap
	 * @return AccTell
	 * @throws DataAccessException
	*/
	@Override
	public AccTell queryAccBankByAccountByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return accTellMapper.queryAccTellByCode(entityMap);
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
		
		String occur_date = entityMap.get("occur_date").toString();

		String year = occur_date.substring(0, 4);
		
		String month = occur_date.substring(5, 7);
		
		String day = occur_date.substring(8, 10);
		
		String tell_number = year + month + day + "0001";
		
		Long occur_date_resetting = Long.parseLong(tell_number);
		
		entityMap.put("subj_nature_code","03");
		
		List<AccTell> list_accTells =  accTellMapper.queryAccTellMaxTellResettingNumber(entityMap);
		
		if(list_accTells.size() > 0){
			
			for (AccTell accTell : list_accTells) {
					
				 if(Long.parseLong(accTell.getTell_number()) != occur_date_resetting){
					 
					 Map<String, Object> map = new HashMap<String, Object>();
					 
					 map.put("group_id",entityMap.get("group_id"));
					 
					 map.put("hos_id",entityMap.get("hos_id"));
					 
					 map.put("copy_code",entityMap.get("copy_code"));
					 
					 map.put("occur_date",entityMap.get("occur_date"));
					 
					 map.put("tell_id",accTell.getTell_id());
					 
					 map.put("tell_number",occur_date_resetting);
					 
					 accTellMapper.update(map);
					 
				 }
					
				 occur_date_resetting ++;

		     }
		}
		 
	}

	@Override
	public String addBatchAccBankAccount(String paramVo)
			throws DataAccessException {
		
		String group_id = SessionManager.getGroupId();
		
		String hos_id = SessionManager.getHosId();
		
		String copy_code = SessionManager.getCopyCode();
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> list= new ArrayList<Map<String,Object>>();
		
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
				mapVoMap.put("subj_nature_code", "03");
				
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
					
					String occur_date = mapVo.get("occur_date").toString();

					String year = occur_date.substring(0, 4);
					
					String month = occur_date.substring(5, 7);
					
					String day = occur_date.substring(8, 10);
					
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
				
	            listVo.add(mapVo);
	            
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
				
				list.add(mapVo);
			}
            
            
        }
		
		try {
			
			if(listVo.size()>0){
				
				accTellMapper.addBatchAccTell(listVo);
			}
			
			if(list.size()>0){
				
				accTellMapper.updateBatchAccTell(list);
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception

			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"操作失败\"}";
		}
	}
	
}