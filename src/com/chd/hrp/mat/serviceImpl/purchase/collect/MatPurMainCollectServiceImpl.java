package com.chd.hrp.mat.serviceImpl.purchase.collect;

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
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.dao.purchase.collect.MatPurMainCollectMapper;
import com.chd.hrp.mat.service.purchase.collect.MatPurMainCollectService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 04113 统购采购计划汇总
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matPurMainCollectService")
public class MatPurMainCollectServiceImpl implements MatPurMainCollectService {
	
	private static Logger logger = Logger.getLogger(MatPurMainCollectServiceImpl.class);
	
	@Resource(name = "matPurMainCollectMapper")
	private MatPurMainCollectMapper matPurMainCollectMapper = null;
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @Description 
	 * 统购采购计划汇总 汇总生成采购计划明细页面 更新
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {

		if(entityMap.get("group_id") == null || "".equals(entityMap.get("group_id").toString())){
			
			return "{\"error\":\"保存失败 group_id不能为空! 错误编码 addOrUpdate\"}";
		}
		
		if(entityMap.get("hos_id") == null || "".equals(entityMap.get("hos_id").toString())){
			
			return "{\"error\":\"保存失败 hos_id不能为空! 错误编码 addOrUpdate\"}";
		}

		if(entityMap.get("copy_code") == null || "".equals(entityMap.get("copy_code").toString())){
			
			return "{\"error\":\"保存失败 copy_code不能为空! 错误编码 addOrUpdate\"}";
		}
		
		if(entityMap.get("dept_id") == null || "".equals(entityMap.get("dept_id").toString())){
			
			return "{\"error\":\"保存失败 dept_id不能为空! 错误编码 addOrUpdate\"}";
		}
		
		if(entityMap.get("dept_no") == null || "".equals(entityMap.get("dept_no").toString())){
			
			return "{\"error\":\"保存失败 dept_no不能为空! 错误编码 addOrUpdate\"}";
		}
		
		if(entityMap.get("creat_date") == null || "".equals(entityMap.get("creat_date").toString())){
			
			return "{\"error\":\"保存失败 creat_date不能为空! 错误编码 addOrUpdate\"}";
		}
		
		if(entityMap.get("pur_type") == null || "".equals(entityMap.get("pur_type").toString())){
			
			return "{\"error\":\"保存失败 pur_type不能为空! 错误编码 addOrUpdate\"}";
		}
		
		if(entityMap.get("allData") == null || "".equals(entityMap.get("allData").toString())){
			
			return "{\"error\":\"保存失败 allData不能为空! 错误编码 addOrUpdate\"}";
		}
		
		
		JSONArray purRelaJson = null;
		
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
			
		List<Map<String,Object>> allDataList = new ArrayList<Map<String,Object>>();//添加数据参数集合
			
		List<Map<String,Object>> deletedDataList = new ArrayList<Map<String,Object>>();//获取删除的数据List
			
		List<Map<String,Object>> relaList = new ArrayList<Map<String,Object>>();//采购计划对应关系LIST
			
		JSONArray allDataJson = JSONArray.parseArray((String) entityMap.get("allData"));//获取要添加的数据
			
		JSONArray deletedDataJson = JSONArray.parseArray((String) entityMap.get("deletedData"));//获取要删除的数据
			
		Iterator iterator = allDataJson.iterator();
			
		//获取所有数据
		while(iterator.hasNext()){//遍历所有数据
				
			Map<String,Object> map = defaultDetailValue();
			
			JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
				
			if(jsonObj.get("inv_id") == null || "".equals(jsonObj.get("inv_id").toString())){
					
				return "{\"error\":\"添加失败 inv_id不能为空! 错误编码 addOrUpdate\"}";
			}
				
			if(jsonObj.get("inv_no") == null || "".equals(jsonObj.get("inv_no").toString())){
					
				return "{\"error\":\"添加失败 inv_no不能为空! 错误编码 addOrUpdate\"}";
			}
				
			if(jsonObj.get("amount") == null || "".equals(jsonObj.get("amount").toString())){
					
				return "{\"error\":\"添加失败 amount不能为空! 错误编码 addOrUpdate\"}";
			}
				
			if(jsonObj.get("price") == null || "".equals(jsonObj.get("price").toString())){
					
				return "{\"error\":\"添加失败 price不能为空! 错误编码 addOrUpdate\"}";
			}
				
			/*if(jsonObj.get("num") == null || "".equals(jsonObj.get("num").toString())){
					
				return "{\"error\":\"添加失败 num不能为空! 错误编码 addOrUpdate\"}";
			}
				*/
			if(jsonObj.get("pack_code") == null || "".equals(jsonObj.get("pack_code").toString())){
					
				return "{\"error\":\"添加失败 pack_code不能为空! 错误编码 addOrUpdate\"}";
			}
				
			if(jsonObj.get("sup_id") == null || "".equals(jsonObj.get("sup_id").toString())){
					
				return "{\"error\":\"添加失败 sup_id不能为空! 错误编码 addOrUpdate\"}";
			}
				
			if(jsonObj.get("num_exchange") == null || "".equals(jsonObj.get("num_exchange").toString())){
					
				return "{\"error\":\"添加失败 num_exchange不能为空! 错误编码 addOrUpdate\"}";
			}
				
			map.put("group_id",Long.parseLong(entityMap.get("group_id").toString()));
				
			map.put("hos_id", Long.parseLong(entityMap.get("hos_id").toString()));
				
			map.put("copy_code", entityMap.get("copy_code"));
				
			map.put("pur_id", entityMap.get("pur_id"));
				
			map.put("inv_id", jsonObj.get("inv_id"));
				
			map.put("inv_no",jsonObj.get("inv_no"));
				
			map.put("amount",jsonObj.get("amount"));
				
			map.put("price",jsonObj.get("price"));
				
			map.put("pack_num",jsonObj.get("num"));
				
			map.put("pack_code", jsonObj.get("pack_code"));
			
			if(jsonObj.get("sup_id").toString().split(",").length > 1){//如果修改了供应商 值为sup_id+","+sup_no
				
				map.put("sup_id",jsonObj.get("sup_id").toString().split(",")[0]);
				
				map.put("sup_no",jsonObj.get("sup_id").toString().split(",")[1]);
				
			}else{//如果没修改供应商
				
				map.put("sup_id",jsonObj.get("sup_id").toString());
				
				map.put("sup_no",jsonObj.get("sup_no").toString());
			}
				
				
			map.put("num_exchange", jsonObj.get("num_exchange"));
				
			if(jsonObj.get("arrive_date") != null){
					
				map.put("arrive_date",DateUtil.dateToString(DateUtil.stringToDate(jsonObj.get("arrive_date").toString(), "yyyy-MM-dd")));
			}
					
			if(jsonObj.get("req_amount") != null){
					
				map.put("req_amount",jsonObj.get("req_amount"));
			}
				
			if(jsonObj.get("stocker") != null && !"".equals(jsonObj.get("stocker").toString())) {
					
				map.put("stocker",jsonObj.get("stocker").toString().split(",")[0]);
			}
				
			if(jsonObj.get("memo") != null){
					
				map.put("memo", jsonObj.get("memo"));
			}
				
			purRelaJson = JSONArray.parseArray((String) jsonObj.get("pur_rela"));//获取科室需求采购计划对应关系
				
			if(purRelaJson != null && !"".equals(purRelaJson)){
				
				Iterator reqRelaiterator = purRelaJson.iterator();
					
				while(reqRelaiterator.hasNext()){//遍历对应关系JSON
						
					Map<String,Object> purRelaMap = new HashMap<String,Object>();//采购计划对应关系MAP
						
					JSONObject reqRelaJsonObj = JSONObject.parseObject(reqRelaiterator.next().toString());
						
					purRelaMap.put("group_id",Long.parseLong(entityMap.get("group_id").toString()));
						
					purRelaMap.put("hos_id", Long.parseLong(entityMap.get("hos_id").toString()));
						
					purRelaMap.put("copy_code", entityMap.get("copy_code"));
						
					purRelaMap.put("pur_id", entityMap.get("pur_id"));
						
					purRelaMap.put("total_id", reqRelaJsonObj.get("total_id"));
						
					purRelaMap.put("inv_id", reqRelaJsonObj.get("inv_id"));
						
					purRelaMap.put("pur_amount", reqRelaJsonObj.get("pur_amount"));
						
					purRelaMap.put("exec_amount", reqRelaJsonObj.get("exec_amount"));
					
					relaList.add(purRelaMap);
				}
				
			}
			allDataList.add(map);
		}
			
		if(deletedDataJson != null){
				
			Iterator deletedDataIterator = deletedDataJson.iterator();
				
			//遍历删除的数据
			while(deletedDataIterator.hasNext()){
				
				JSONObject deletedDataJsonObj = JSONObject.parseObject(deletedDataIterator.next().toString());
					
				purRelaJson = JSONArray.parseArray((String) deletedDataJsonObj.get("pur_rela"));//获取科室需求采购计划对应关系
					
				Iterator purRelaiterator = purRelaJson.iterator();
					
				while(purRelaiterator.hasNext()){//遍历删除数据的对应关系
						
					Map<String,Object> deletedDataMap = new HashMap<String,Object>();//获取删除的数据Map
						
					JSONObject reqRelaJsonObj = JSONObject.parseObject(purRelaiterator.next().toString());
						
					deletedDataMap.put("group_id",Long.parseLong(entityMap.get("group_id").toString()));
						
					deletedDataMap.put("hos_id", Long.parseLong(entityMap.get("hos_id").toString()));
						
					deletedDataMap.put("copy_code", entityMap.get("copy_code"));
						
					deletedDataMap.put("pur_id", entityMap.get("pur_id"));
						
					deletedDataMap.put("total_id", reqRelaJsonObj.get("total_id"));
						
					deletedDataMap.put("inv_id", reqRelaJsonObj.get("inv_id"));
						
					deletedDataList.add(deletedDataMap);
				}
			}
		}
				
		matPurMainCollectMapper.update(entityMap);//修改采购主表数据
			
		list.add(entityMap);
		
		if(list.size() > 0 ){
			
			matPurMainCollectMapper.deleteMatPurDetail(list);//删除采购明细数据
		}
			
		if(allDataList.size() > 0 ){//有数据、就添加
				
			matPurMainCollectMapper.addBatchMatPurDetail(allDataList);//添加采购明细数据
		}
			
		if(deletedDataList.size() > 0 ){
				
			matPurMainCollectMapper.updateMatPurMainState(deletedDataList);//还原采购计划单状态到已审核
				
			matPurMainCollectMapper.deleteBatchMatPurRela(deletedDataList);//根据ID删除某个计划的对应关系
		}
			
		if(relaList.size() > 0){
			//更新对应关系
			matPurMainCollectMapper.updateBatchMatPurRela(relaList);//修改采购计划对应关系
				
		}
			
		return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 采购计划
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<?> list = matPurMainCollectMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			
			List<?> list = matPurMainCollectMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>根据计划单ID 查询采购计划 
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return matPurMainCollectMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按采购计划主表ID查询明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatPurDetail(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<?> list = matPurMainCollectMapper.queryMatPurDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			
			List<?> list = matPurMainCollectMapper.queryMatPurDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按材料ID 查询材料当前库存明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatInvCurAmountDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		List<?> list = matPurMainCollectMapper.queryMatInvCurAmountDetail(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按采购计划汇总
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatPurMainByCollect(Map<String, Object> entityMap) throws DataAccessException {
		
		List<?> list = matPurMainCollectMapper.queryMatPurMainByCollect(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>汇总生成 采购计划
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String genByCollectMatPurMain(List<Map<String,Object>> entityMap) throws DataAccessException{
		
		StringBuffer pur_ids = new StringBuffer();//用于存ID
		
		Map<String,Object> matPurMainMap;//主表map
		List<Map<String,Object>> matPurMainList = new ArrayList<Map<String,Object>>();//主表集合
		
		Map<String,Object> matPurDetailMap;//明细表map
		List<Map<String,Object>> matPurDetailList = new ArrayList<Map<String,Object>>();//明细表集合
		
		Map<String,Object> matPurRelaMap;//对应关系
		List<Map<String,Object>> matPurRelaList = new ArrayList<Map<String,Object>>();//对应关系集合
		
		Map<String,Object> reDetailMap = new HashMap<String,Object>();//去重明细表
		
		String pur_code;//采购主表采购计划单号
		
		boolean codeFlag = false;
		
		if(entityMap.size() > 0 ){
			
			for(Map<String,Object> map: entityMap){
				
				pur_ids.append(map.get("pur_id").toString()).append(",");//拼接ID字符串用来查询明细
				
			}
			
		}
		
		//按ID分组查询采购单位、请购单位、付款单位,用于判断汇总后生成几条采购计划
		List<Map<String,Object>> unitList = (List<Map<String, Object>>) matPurMainCollectMapper.queryMatPurUnit(entityMap);
		
		if(unitList.size() > 0 ){
			
			for(int x = 0 ;x < unitList.size(); x++){
				
				matPurMainMap =defaultValue();//主表数据Map
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				//获取主表数据
				Map<String,Object> map = entityMap.get(0);
					
				matPurMainMap.put("group_id", map.get("group_id"));
							
				matPurMainMap.put("hos_id", map.get("hos_id"));
							
				matPurMainMap.put("copy_code", map.get("copy_code"));
							
				matPurMainMap.put("pur_hos_id", unitList.get(x).get("pur_hos_id").toString());
							
				matPurMainMap.put("req_hos_id", unitList.get(x).get("req_hos_id").toString());
							
				matPurMainMap.put("pay_hos_id", unitList.get(x).get("pay_hos_id").toString());
					
				matPurMainMap.put("maker", SessionManager.getUserId());
					
				matPurMainMap.put("make_date", sdf.format(new Date()));
				matPurMainMap.put("checker", SessionManager.getUserId());
				matPurMainMap.put("check_date", matPurMainMap.get("make_date"));
					
				matPurMainMap.put("pur_type", "2");
					
				matPurMainMap.put("is_collect", "1");
					
				matPurMainMap.put("state","2");
				
				String curDate = sdf.format(new Date());
						
				matPurMainMap.put("creat_date", curDate);
				//System.out.println("************* creat_date:"+curDate);	
				matPurMainMap.put("make_year", curDate.substring(0, 4));
						
				matPurMainMap.put("make_month", curDate.substring(5, 7));
				
				int flag =  matPurMainCollectMapper.queryIsExists(matPurMainMap);//查询计划单号是否存在
				
				if(flag > 0){
					
					String maxCode = matPurMainCollectMapper.queryMaxCode(matPurMainMap);
					
					matPurMainMap.put("max_code", (Integer.parseInt(maxCode))+1);
					
					//更新单据号表
					int state = matPurMainCollectMapper.updateMatNoMatch(matPurMainMap);
					
					String code = "00000" + matPurMainMap.get("max_code").toString();
					
					code = code.substring(code.length()-5,code.length());
					
					pur_code = "CG"+matPurMainMap.get("make_year")+matPurMainMap.get("make_month")+code;
					
					matPurMainMap.put("pur_code", pur_code);//计划单号
					
					codeFlag = true;
					
				}else{
				
					pur_code  = "CG"+matPurMainMap.get("make_year")+matPurMainMap.get("make_month")+"00001";
					
					matPurMainMap.put("pur_code", pur_code);
					
					codeFlag = false;
					
				}
				
				if(codeFlag == false){
					
					//更新单据序号表
					matPurMainMap.put("prefixe", "CG");
					matPurMainMap.put("table_code", "MAT_PUR_MAIN");
					matPurMainMap.put("max_no", 1);
					int state = matPurMainCollectMapper.addMatNoMatch(matPurMainMap);
					
				}
				
				int pur_id = matPurMainCollectMapper.queryMatPurMainPurId();//汇总生成采购单ID
				
				matPurMainMap.put("pur_id", pur_id);
				
				if(pur_ids.length() > 0 ){
					
					matPurMainMap.put("pur_ids", pur_ids.substring(0,pur_ids.length()-1));
				}
				//查询明细数据
				List<Map<String,Object>> list = (List<Map<String, Object>>) matPurMainCollectMapper.queryMatPurMainDetail(matPurMainMap);
				
				if(list.size() > 0 ){
					
					String inv_id = null;
					for(int i = 0 ;i<list.size();i++){
						
						String purDetailStr = list.get(i).get("group_id").toString()+list.get(i).get("hos_id").toString()+
								list.get(i).get("copy_code").toString()+list.get(i).get("pur_id").toString()+list.get(i).get("inv_id").toString();
						if(reDetailMap.get(purDetailStr) == null){
							
							reDetailMap.put(purDetailStr, purDetailStr);
							
							matPurDetailMap = defaultDetailValue();
							
							matPurDetailMap.put("group_id", list.get(i).get("group_id"));
							
							matPurDetailMap.put("hos_id", list.get(i).get("hos_id"));
							
							matPurDetailMap.put("copy_code", list.get(i).get("copy_code"));
							
							matPurDetailMap.put("pur_id", pur_id);
							matPurDetailMap.put("pur_code", matPurMainMap.get("pur_code"));
							
							matPurDetailMap.put("inv_id", list.get(i).get("inv_id"));
							
							matPurDetailMap.put("inv_no", list.get(i).get("inv_no"));
							
							matPurDetailMap.put("sup_no", list.get(i).get("sup_no"));
							
							matPurDetailMap.put("sup_id", list.get(i).get("sup_id"));
							
							matPurDetailMap.put("price", list.get(i).get("price"));
							
							matPurDetailMap.put("req_amount", list.get(i).get("sum_amount"));
							matPurDetailMap.put("amount",list.get(i).get("sum_amount"));
							//手动筛选材料如果与上一条材料一致则不添加到list中
							if(inv_id != null){
								if(!inv_id.equals(String.valueOf(matPurDetailMap.get("inv_id")))){
									
									inv_id = String.valueOf(matPurDetailMap.get("inv_id"));
									matPurDetailList.add(matPurDetailMap);
								}
							}else{
								inv_id = String.valueOf(matPurDetailMap.get("inv_id"));
								matPurDetailList.add(matPurDetailMap);
							}
						}
						matPurRelaMap = new HashMap<String,Object>();
						
						//添加对应关系
						matPurRelaMap.put("group_id", list.get(i).get("group_id"));
						
						matPurRelaMap.put("hos_id", list.get(i).get("hos_id"));
						
						matPurRelaMap.put("copy_code", list.get(i).get("copy_code"));
						
						matPurRelaMap.put("total_id", pur_id);
						
						matPurRelaMap.put("pur_id", list.get(i).get("pur_id"));
						
						matPurRelaMap.put("inv_id", list.get(i).get("inv_id"));
						
						matPurRelaMap.put("pur_amount", list.get(i).get("amount"));
						
						matPurRelaMap.put("exec_amount", list.get(i).get("sum_amount"));
						
						matPurRelaMap.put("state", 3);//用于修改采购计划为已执行
						
						matPurRelaList.add(matPurRelaMap);
					}
				}
				matPurMainList.add(matPurMainMap);
			}
		}
		
		if(matPurMainList.size() ==0 ){
			
			return "{\"error\":\"汇总失败 主表数据不能为空! 错误编码 genByCollectMatPurMain\"}";
			
		}
		
		if(matPurDetailList.size() ==0 ){
			
			return "{\"error\":\"汇总失败 明细数据不能为空! 错误编码 genByCollectMatPurMain\"}";
			
		}
		
		if(matPurRelaList.size() ==0 ){
			
			return "{\"error\":\"汇总失败 对应关系不存在! 错误编码 genByCollectMatPurMain\"}";
			
		}
		
		matPurMainCollectMapper.addBatch(matPurMainList);//添加主表数据
		
		matPurMainCollectMapper.addBatchMatPurDetail(matPurDetailList);//添加明细表数据
		
		matPurMainCollectMapper.addMatPurRela(matPurRelaList);//添加对应关系
		
		matPurMainCollectMapper.updateBatchPurState(matPurRelaList);//修改采购计划状态为已执行
		
		return "{\"msg\":\"汇总成功.\",\"state\":\"true\"}";
				
	}
	

	@Override
	public String queryMatPurMainAmountDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		List<?> list = matPurMainCollectMapper.queryMatPurMainAmountDetail(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	//返回用于保存主表的默认值
	public Map<String, Object> defaultValue() {
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		
		mapVo.put("brif", "");
		
		mapVo.put("pur_type", "");
		
		mapVo.put("dept_id", "");
		
		mapVo.put("dept_no", "");
		
		mapVo.put("pur_hos_id", "");
		
		mapVo.put("req_hos_id", "");
		
		mapVo.put("pay_hos_id", "");
		
		mapVo.put("creat_date", "");
		
		mapVo.put("maker", "");
		
		mapVo.put("make_date", "");
		
		mapVo.put("checker", "");
		
		mapVo.put("check_date", "");
		
		mapVo.put("execor", "");
		
		mapVo.put("exec_date", "");
		
		mapVo.put("is_collect", "");
		
		mapVo.put("state", "");
		
		return mapVo;
	}
	
	// 返回用于保存明细表的默认值
	public Map<String, Object> defaultDetailValue() {

		Map<String, Object> mapDetailVo = new HashMap<String, Object>();
				
			mapDetailVo.put("pur_detail_id","");
				
			mapDetailVo.put("inv_no", "");

			mapDetailVo.put("amount","");
				
			mapDetailVo.put("price","");
				
			mapDetailVo.put("num", "");
				
			mapDetailVo.put("num_exchange", "");
				
			mapDetailVo.put("pack_code", "");
				
			mapDetailVo.put("arrive_date", "");
				
			mapDetailVo.put("sup_no", "");
				
			mapDetailVo.put("sup_id","");
				
			mapDetailVo.put("req_amount","");
				
			mapDetailVo.put("stocker","");
				
			mapDetailVo.put("memo","");
				
		return mapDetailVo;
	}
}
