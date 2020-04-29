package com.chd.hrp.acc.serviceImpl.tell;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.acc.dao.tell.AccTellVeriMapper;
import com.chd.hrp.acc.entity.AccLederCheck;
import com.chd.hrp.acc.entity.AccTellVeri;
import com.chd.hrp.acc.entity.AccVouchCheck;
import com.chd.hrp.acc.service.tell.AccTellVeriService;
import com.github.pagehelper.PageInfo;
@Service("accTellVeriService")
public class AccTellVeriServiceImpl implements AccTellVeriService{
	
private static Logger logger = Logger.getLogger(AccTellVeriServiceImpl.class);
	     
	@Resource(name = "accTellVeriMapper")
	private final AccTellVeriMapper accTellVeriMapper = null;
	
	/**
	 * 查凭证数据
	 */
	@Override
	public String queryAccVouchCheck(Map<String, Object> entityMap) throws DataAccessException {
		
		StringBuffer direct = new StringBuffer();
		if(entityMap.get("subj_dire").equals("0")){
			direct.append(" and (nvl(avc.debit,0) > 0)");
		}else{
			direct.append(" and (nvl(avc.credit,0) > 0)");
		}
		entityMap.put("direct", direct.toString());
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccVouchCheck> list = accTellVeriMapper.queryAccVouchCheck(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	/**
	 * 出纳数据
	 */
	@Override
	public String queryAccTellDatas(Map<String, Object> entityMap) throws DataAccessException {
		StringBuffer direct = new StringBuffer();
		if(entityMap.get("subj_dire").equals("0")){
			direct.append(" and (nvl(att.debit,0) > 0)");
		}else{
			direct.append(" and (nvl(att.credit,0) > 0)");
		}
		entityMap.put("direct", direct.toString());
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(accTellVeriMapper.queryAccTellDatas(entityMap, rowBounds));
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	/**
	 * 手工对账
	 */
	@Override  
	public String addAccTellVeri(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			Map<String,Map<String,Object>> leftMap = new HashMap<String,Map<String,Object>>();//存放处理的左侧结果集
			Map<String,Map<String,Object>> rightMap = new HashMap<String,Map<String,Object>>();//存放处理的右侧结果集
			
			List<Map<String,Object>> veriList = new ArrayList<Map<String,Object>>();//存放对应关系
			List<Map<String,Object>> tellList = new ArrayList<Map<String,Object>>();
			
			JSONArray jsonL = JSONArray.parseArray((String)entityMap.get("check_dataL"));
			JSONArray jsonR = JSONArray.parseArray((String)entityMap.get("check_dataR"));
			
			// 处理左侧json串
			int lnum = 0;
			
			Iterator itL = jsonL.iterator();
			while (itL.hasNext()) {
				JSONObject jsonObjL = JSONObject.parseObject(itL.next().toString());
				Map<String, Object> mapL = new HashMap<String, Object>();
				
				StringBuffer conL = new StringBuffer();
				conL.append(String.valueOf(lnum) + '@');
				conL.append(jsonObjL.get("group_id").toString()+jsonObjL.get("hos_id").toString()+jsonObjL.get("copy_code").toString()
						+jsonObjL.get("acc_year").toString()).append("@").append(jsonObjL.get("price"));

				mapL.put("group_id", jsonObjL.get("group_id"));
				mapL.put("hos_id", jsonObjL.get("hos_id"));
				mapL.put("copy_code", jsonObjL.get("copy_code"));
				mapL.put("acc_year", jsonObjL.get("acc_year"));
				mapL.put("tell_id", jsonObjL.get("tell_id"));
				mapL.put("price", jsonObjL.get("price"));
				mapL.put("veri_check_id", "0");
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
				
				StringBuffer conR = new StringBuffer();
				conR.append(String.valueOf(rnum) + '@');
				conR.append(jsonObjR.get("group_id").toString()+jsonObjR.get("hos_id").toString()+jsonObjR.get("copy_code").toString()
						+jsonObjR.get("acc_year").toString()).append("@").append(jsonObjR.get("price"));

				mapR.put("group_id", jsonObjR.get("group_id"));
				mapR.put("hos_id", jsonObjR.get("hos_id"));
				mapR.put("copy_code", jsonObjR.get("copy_code"));
				mapR.put("acc_year", jsonObjR.get("acc_year"));
				mapR.put("vouch_id", jsonObjR.get("vouch_id"));
				mapR.put("vouch_check_id", jsonObjR.get("vouch_check_id"));
				mapR.put("price", jsonObjR.get("price"));
				mapR.put("veri_check_id", "0");
				mapR.put("isCheck", "0");
				
				String key = conR.toString();
				rightMap.put(key, mapR);
				rnum++;
			}
			
			//处理对应关系
			int i=0;

			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateString = formatter.format(new Date());
			int a = 0;
			Iterator itl = leftMap.keySet().iterator();
			while(itl.hasNext()){
				
				i++;
				
				String key1 = String.valueOf(itl.next());
				String keyL = key1.split("@")[1];
				
				Iterator itr = rightMap.keySet().iterator();
				while(itr.hasNext()){
					String key2 = String.valueOf(itr.next());
					String keyR = key2.split("@")[1];
					
					if(keyR.equals(keyL)){
						Map<String,Object> lMap = leftMap.get(key1);
						Map<String,Object> rMap = rightMap.get(key2);
						
						if ("0".equals(lMap.get("isCheck").toString())) {
							if ("0".equals(rMap.get("isCheck").toString())) {
								Map<String, Object> vmap = new HashMap<String, Object>();// 对应关系map
								Map<String, Object> tellMap = new HashMap<String, Object>();// 更新map
								Long veri_check_id = Long.parseLong(dateString+a);
								
								if("0".equals(lMap.get("veri_check_id").toString()) && "0".equals(rMap.get("veri_check_id").toString())){
									vmap.put("veri_check_id", veri_check_id);
								}else{
									vmap.put("veri_check_id", lMap.get("veri_check_id").toString()=="0" ? rMap.get("veri_check_id").toString() : lMap.get("veri_check_id").toString());
								}
								
								vmap.put("group_id", entityMap.get("group_id"));
								vmap.put("hos_id", entityMap.get("hos_id"));
								vmap.put("copy_code", entityMap.get("copy_code"));
								vmap.put("acc_year", entityMap.get("acc_year"));
								vmap.put("is_auto", "0");
								vmap.put("create_user", SessionManager.getUserId());
								vmap.put("create_date", date);
								vmap.put("veri_money_w", "0");
								
								tellMap.put("group_id", entityMap.get("group_id"));
								tellMap.put("hos_id", entityMap.get("hos_id"));
								tellMap.put("copy_code", entityMap.get("copy_code"));
								tellMap.put("acc_year", entityMap.get("acc_year"));
								tellMap.put("is_check", "1");
								tellMap.put("check_user", entityMap.get("user_id"));
								tellMap.put("check_date", date);
								
								
								// 若借方金额 >= 贷方金额 借方金额=借方金额-贷方金额
								if (Double.parseDouble(lMap.get("price").toString()) >= Double.parseDouble(rMap.get("price").toString())) {
									double num = Double.parseDouble(lMap.get("price").toString())- Double.parseDouble(rMap.get("price").toString());
									vmap.put("veri_money", rMap.get("price").toString());
									vmap.put("tell_id", lMap.get("tell_id"));
									vmap.put("vouch_id", rMap.get("vouch_id"));
									vmap.put("vouch_check_id", rMap.get("vouch_check_id"));
									
									lMap.put("price", num);
									lMap.put("veri_check_id", vmap.get("veri_check_id"));
									rMap.put("price", 0);
									rMap.put("isCheck", "1");
									
									
									if(num==0){
										lMap.put("isCheck", "1");
										lMap.put("price", num);
										tellMap.put("tell_id", lMap.get("tell_id"));
										tellList.add(tellMap);
									}
									
									veriList.add(vmap);
								}else{
									double num = Double.parseDouble(rMap.get("price").toString())- Double.parseDouble(lMap.get("price").toString());
									vmap.put("veri_money", lMap.get("price").toString());
									vmap.put("tell_id", lMap.get("tell_id"));
									vmap.put("vouch_id", rMap.get("vouch_id"));
									vmap.put("vouch_check_id", rMap.get("vouch_check_id"));
									
									rMap.put("price", num);
									rMap.put("veri_check_id", vmap.get("veri_check_id"));
									lMap.put("price", 0);
									lMap.put("isCheck", "1");
									
									tellMap.put("tell_id", lMap.get("tell_id"));
									tellList.add(tellMap);
									
									if(num==0){
										rMap.put("isCheck", "1");
										rMap.put("price", num);
									}
									
									veriList.add(vmap);
								}
							}
						}
						
					}
				}
			}
			
			if(veriList.size()>0){
				//插入对账关系表
				accTellVeriMapper.addBatchAccTellVeri(veriList);
				//更新状态
				if(tellList.size()>0){
					accTellVeriMapper.updateAccTellCheckState(tellList);
				}
				
				Map<String,Object> logMap = new HashMap<String,Object>();
				logMap.put("group_id", entityMap.get("group_id"));
				logMap.put("hos_id", entityMap.get("hos_id"));
				logMap.put("copy_code", entityMap.get("copy_code"));
				logMap.put("acc_year", entityMap.get("acc_year"));
				logMap.put("create_user", SessionManager.getUserId());
				logMap.put("create_date", new Date());
				logMap.put("note", "手工对账");
				
				//插入日志表
				accTellVeriMapper.addAccTellVeriLog(logMap);
			}
			
			return "{\"msg\":\"对账成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"对账失败 数据库异常 请联系管理员! 错误编码 addAccTellVeri\"}";
		}
	}
	
	/**
	 * 批量对账
	 */
	@Override
	public String addBatchAccTellVeri(Map<String, Object> entityMap) throws DataAccessException {
		try {
			//1、根据条件查询出纳数据(借方)
			List<Map<String,Object>> accTellList = JsonListMapUtil.toListMapLower(accTellVeriMapper.queryAccTellData(entityMap));
			List<Map<String,Object>> tellList = new ArrayList<Map<String,Object>>();//出纳数据集合
			
			Map<String,Map<String,Object>> tellMap = new HashMap<String,Map<String,Object>>();//存放比较键 
			int lnum = 1;//用于处理重复的添加不上去问题；
			
			for(Map<String,Object> map : accTellList){
				StringBuffer condition = new StringBuffer();
				condition.append(String.valueOf(lnum)+'@');
				//条件判断  帐套+年
				condition.append((map.get("group_id").toString()+map.get("hos_id").toString()+map.get("copy_code").toString()+map.get("acc_year").toString()));
				condition.append(map.get("cash_subj_code").toString()+map.get("direct"));//科目编码+金额方向
				
				//结算类型
				if(entityMap.get("pay_type_code").equals("1")){
					condition.append(map.get("pay_type_code")); 
				}
				//票据号
				if(entityMap.get("check_no").equals("1")){
					condition.append(map.get("check_no")); 
				}
				//发生日期
				if(entityMap.get("occur_date").equals("1")){
					 condition.append(map.get("occur_date").toString()); 
				}
				//摘要
				if(entityMap.get("summary").equals("1")){
					condition.append(map.get("summary")); 
				}
				//金额
				if(entityMap.get("is_money").equals("1")){
					condition.append(map.get("wbal"));  
				}
				
				String key  = condition.toString();
				tellMap.put(key, map);
				
				tellList.add(map);
				
				lnum++;
			}
			
			//2、根绝条件查询辅助核算数据
			List<Map<String,Object>> accVouchCheckList = JsonListMapUtil.toListMapLower(accTellVeriMapper.queryAccVouchCheckData(entityMap));
			List<Map<String,Object>> checkList = new ArrayList<Map<String,Object>>();//出纳数据集合
			
			Map<String,Map<String,Object>> checkMap = new HashMap<String,Map<String,Object>>();//存放比较键 
			
			int vnum = 1;//用于处理重复的添加不上去问题；
			
			for(Map<String,Object> map : accVouchCheckList){
				StringBuffer condition = new StringBuffer();
				condition.append(String.valueOf(vnum)+'@');
				//条件判断  帐套+年
				condition.append((map.get("group_id").toString()+map.get("hos_id").toString()+map.get("copy_code").toString()+map.get("acc_year").toString()));
				condition.append(map.get("subj_code").toString()+map.get("direct"));//科目编码+金额方向
				
				//结算类型
				if(entityMap.get("pay_type_code").equals("1")){
					condition.append(map.get("pay_type_code")); 
				}
				//票据号
				if(entityMap.get("check_no").equals("1")){
					condition.append(map.get("check_no")); 
				}
				//发生日期
				if(entityMap.get("occur_date").equals("1")){
					 condition.append(map.get("occur_date").toString()); 
				}
				//摘要
				if(entityMap.get("summary").equals("1")){
					condition.append(map.get("summary")); 
				}
				
				//金额
				if(entityMap.get("is_money").equals("1")){
					condition.append(map.get("wbal"));  
				}
				
				String key  = condition.toString();
				checkMap.put(key, map);
				checkList.add(map);
				
				vnum++;
			}
			
			List<Map<String,Object>> veriList =new ArrayList<Map<String,Object>>();//存放对应关系
			Date date = new Date();
			//自动勾兑开始
		  	boolean flag = false;
		  	Iterator tell = tellMap.keySet().iterator();
		  	int i = 0;
			while(tell.hasNext()){
				i++;
				String key = String.valueOf(tell.next());
				String keyl = key.split("@")[1];
				
				Iterator check = checkMap.keySet().iterator();
				while(check.hasNext()){
					String key2 = String.valueOf(check.next());
					String keyr = key2.split("@")[1];
					if(keyr.equals(keyl)){
						Map<String,Object> tmap = tellMap.get(key);
						Map<String,Object> cmap = checkMap.get(key2);
						
						while(Double.parseDouble(tmap.get("wbal").toString()) > 0){
							Map<String,Object> vmap = new HashMap<String,Object>();
							
							SimpleDateFormat formatter =  new SimpleDateFormat("yyyyMMddHHmmss");  
							String dateString = formatter.format(new Date());  
					        Long veri_check_id = Long.parseLong(dateString+i);
					        
							vmap.put("veri_check_id", veri_check_id);
							vmap.put("group_id", entityMap.get("group_id"));
							vmap.put("hos_id", entityMap.get("hos_id"));
							vmap.put("copy_code", entityMap.get("copy_code"));
							vmap.put("acc_year", entityMap.get("acc_year"));
							vmap.put("create_user", entityMap.get("user_id"));
							vmap.put("create_date", date);
							vmap.put("is_auto", "1");
							
							vmap.put("is_check", "1");
							vmap.put("check_user", entityMap.get("user_id"));
							vmap.put("check_date", date);
							
							//若出纳金额 >= 辅助账账金额  出纳金额=出纳金额-辅助账金额
							if(Double.parseDouble(tmap.get("wbal").toString()) >= Double.parseDouble(cmap.get("wbal").toString())){
								double num = Double.parseDouble(tmap.get("wbal").toString())-Double.parseDouble(cmap.get("wbal").toString());
								tmap.put("wbal", num);
								cmap.put("wbal", 0);
								vmap.put("veri_money", cmap.get("wbal").toString());
							}else{
								double num = Double.parseDouble(cmap.get("wbal").toString())-Double.parseDouble(tmap.get("wbal").toString());
								tmap.put("wbal", 0);
								cmap.put("wbal", num);
								vmap.put("veri_money", tmap.get("wbal").toString());
							}
							vmap.put("vouch_id", cmap.get("vouch_id"));
							vmap.put("vouch_check_id", cmap.get("vouch_check_id"));
							vmap.put("tell_id", tmap.get("tell_id"));
							
							veriList.add(vmap); 
						}
					}
				}
			}
			
			
			if(veriList.size() > 0){
				//插入对账表  acc_tell_veri
				accTellVeriMapper.addBatchAccTellVeri(veriList);
				//回写 acc_tell 更新is_check,check_user,check_date字段
				accTellVeriMapper.updateAccTellCheckState(veriList);
				
				//插入日志表
				Map<String,Object> logMap = new HashMap<String,Object>();
				logMap.put("group_id", entityMap.get("group_id"));
				logMap.put("hos_id", entityMap.get("hos_id"));
				logMap.put("copy_code", entityMap.get("copy_code"));
				logMap.put("acc_year", entityMap.get("acc_year"));
				logMap.put("create_user", entityMap.get("user_id"));
				logMap.put("create_date", date);
				logMap.put("note", "批量对账！");
				
				accTellVeriMapper.addAccTellVeriLog(logMap);
			}
			
			return "{\"msg\":\"批量对账成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"批量对账失败!\"}");
			//return "{\"error\":\"批量对账失败 数据库异常 请联系管理员! 错误编码 addBatchAccTellVeri\"}";
		}
	}

	/**
	 * 勾选取消对账
	 */
	@Override
	public String deleteAccTellVeri(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String,Object>> veriList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> tellList = new ArrayList<Map<String,Object>>();
			JSONArray jsonL = JSONArray.parseArray((String)entityMap.get("check_dataL"));
			JSONArray jsonR = JSONArray.parseArray((String)entityMap.get("check_dataR"));
			
			Map<String,Double> left = new HashMap<String,Double>();	//要比较的对象
			Map<String,Double> right = new HashMap<String,Double>();	//要比较的对象
			
			Map<String,Object> logMap = new HashMap<String,Object>();
			
			// 处理左侧json串
			int a=0;
			Iterator itL = jsonL.iterator();
			while (itL.hasNext()) {
				JSONObject jsonObjL = JSONObject.parseObject(itL.next().toString());
				Map<String, Object> mapL = new HashMap<String, Object>();
				Map<String, Object> tellMap = new HashMap<String, Object>();
				if(a==0){
					logMap.put("group_id", jsonObjL.get("group_id"));
					logMap.put("hos_id", jsonObjL.get("hos_id"));
					logMap.put("copy_code", jsonObjL.get("copy_code"));
					logMap.put("acc_year", jsonObjL.get("acc_year"));
					logMap.put("create_user", SessionManager.getUserId());
					logMap.put("create_date", new Date());
					logMap.put("note", "取消对账");
				}
				mapL.put("group_id", jsonObjL.get("group_id"));
				mapL.put("hos_id", jsonObjL.get("hos_id"));
				mapL.put("copy_code", jsonObjL.get("copy_code"));
				mapL.put("acc_year", jsonObjL.get("acc_year"));
				mapL.put("veri_check_id", jsonObjL.get("veri_check_id"));
				mapL.put("tell_id", jsonObjL.get("tell_id"));
				mapL.put("price", jsonObjL.get("price"));
				
				mapL.put("vouch_id", "");
				mapL.put("vouch_check_id", "");
				
				mapL.put("is_check", "0");
				mapL.put("check_user", "");
				mapL.put("check_date", "");
				
				if(left.get(mapL.get("veri_check_id").toString())==null){
					left.put(mapL.get("veri_check_id").toString(),Double.parseDouble(mapL.get("price").toString()));
				}else{
					left.put(mapL.get("veri_check_id").toString(),Double.parseDouble(left.get(mapL.get("veri_check_id").toString()).toString())+Double.parseDouble(mapL.get("price").toString()));
				}
				
				tellMap.putAll(mapL);
				tellList.add(tellMap);
				veriList.add(mapL);
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
				mapR.put("vouch_id", jsonObjR.get("vouch_id"));
				mapR.put("vouch_check_id", jsonObjR.get("vouch_check_id"));
				mapR.put("price", jsonObjR.get("price"));
				mapR.put("veri_check_id", jsonObjR.get("veri_check_id"));
				mapR.put("tell_id", "");
				mapR.put("is_check", "");
				mapR.put("check_user", "");
				mapR.put("check_date", "");
				
				if(right.get(mapR.get("veri_check_id").toString())==null){
					right.put(mapR.get("veri_check_id").toString(),Double.parseDouble(mapR.get("price").toString()));
				}else{
					right.put(mapR.get("veri_check_id").toString(),Double.parseDouble(left.get(mapR.get("veri_check_id").toString()).toString())+Double.parseDouble(mapR.get("price").toString()));
				}
				
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
				return "{\"msg\":\"取消核销失败，请勾选相对应的借贷方.\",\"state\":\"false\"}";
			}else{
				if(countC!=0){
					return "{\"msg\":\"取消核销失败，金额不相等.\",\"state\":\"false\"}";
				}else{
					
					if(veriList.size()>0){
						//1、删除对应关系表
						accTellVeriMapper.deleteBatchAccTellVeri(veriList);
						//2、acc_Tell 更新状态
						if(tellList.size() > 0){
							accTellVeriMapper.updateAccTellCheckState(tellList);
						}
						//3、插入日志表
						accTellVeriMapper.addAccTellVeriLog(logMap);
						
						return "{\"msg\":\"取消对账成功.\",\"state\":\"true\"}";
					}else{
						return "{\"msg\":\"此期间没有需要取消的对账数据.\",\"state\":\"false\"}";
					}
				}
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"批量对账失败!\"}");
			//return "{\"error\":\"取消对账失败 数据库异常 请联系管理员! 错误编码  deleteAccTellVeri\"}";
		}
	}

	/**
	 * 批量取消
	 */
	@Override
	public String deleteAccTellVeriBySubjAndDate(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			List<Map<String,Object>> vList = JsonListMapUtil.toListMapLower(accTellVeriMapper.queryAccTellVeriDataBySubjCode(entityMap));
			List<Map<String,Object>> veriList = new ArrayList<Map<String,Object>>();
			if(vList.size() > 0){
				for(Map<String,Object> map : vList){
					Map<String,Object> vMap = new HashMap<String,Object>();
					vMap.putAll(map);	
					vMap.put("is_check", "0");
					vMap.put("check_user", "");
					vMap.put("check_date", "");
					veriList.add(vMap);
				}
				
				accTellVeriMapper.updateAccTellCheckState(veriList);
				
				entityMap.put("log_id", "");
				entityMap.put("note", "批量取消对账");
				
				accTellVeriMapper.addAccTellVeriLog(entityMap);
				accTellVeriMapper.deleteAccTellVeriBySubjAndDate(entityMap);
				
				return "{\"msg\":\"批量取消对账成功.\",\"state\":\"true\"}";
			}else{
				return "{\"msg\":\"此期间没有需要取消的对账数据.\",\"state\":\"false\"}";
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"批量对账失败!\"}");
			//return "{\"error\":\"取消对账失败 数据库异常 请联系管理员! 错误编码  deleteAccTellVeriBySubjAndDate\"}";
		}
	}
	
	@Override
	public String queryAccTellVeri(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccTellVeri> list = accTellVeriMapper.queryAccTellVeri(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public AccTellVeri queryAccTellVeriByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		return accTellVeriMapper.queryAccTellVeriByCode(entityMap);
	}
	
	@Override
	public String deleteBatchAccTellVeri(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {

			accTellVeriMapper.deleteBatchAccTellVeri(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccTellVeri\"}";
	
		}
	}
	
	

}
