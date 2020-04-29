package com.chd.hrp.mat.serviceImpl.purchase.make;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.mat.dao.base.MatApplyPurRelaMapper;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.purchase.make.MatDeptPurRelaMapper;
import com.chd.hrp.mat.dao.purchase.make.MatPurMainMapper;
import com.chd.hrp.mat.dao.purchase.make.MatStorePurRelaMapper;
import com.chd.hrp.mat.service.purchase.make.MatPurMainService;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * 04114 采购计划编制
 * @Table:
 * MAT_PUR_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matPurMainService")
public class MatPurMainServiceImpl implements MatPurMainService {
	
	private static Logger logger = Logger.getLogger(MatPurMainServiceImpl.class);
	
	@Resource(name = "matPurMainMapper")
	private MatPurMainMapper matPurMainMapper = null;
	
	@Resource(name = "matStorePurRelaMapper")
	private MatStorePurRelaMapper matStorePurRelaMapper=null;
	
	@Resource(name = "matDeptPurRelaMapper")
	private MatDeptPurRelaMapper matDeptPurRelaMapper=null;
	
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;
	
	@Resource(name = "matApplyPurRelaMapper")
	private final MatApplyPurRelaMapper matApplyPurRelaMapper = null;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>添加
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		try{
			List<Map<String,Object>> allDataList = new ArrayList<Map<String,Object>>();//添加数据参数集合
			List<Map<String,Object>> relaList = new ArrayList<Map<String,Object>>();//添加数据参数集合	
			
					
			String pur_code;		
			String make_date = entityMap.get("make_date").toString();		
			entityMap.put("year",make_date.substring(0, 4));		
			entityMap.put("month", make_date.substring(5, 7));	
			entityMap.put("day", make_date.substring(8, 10));
			entityMap.put("come_from", entityMap.get("come_from"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			//自动生成需求单号单据号
			entityMap.put("pur_code", getNextReq_code(entityMap));
			
			//采购单位、请购单位、付款单位：如果计划类型为自购计划，则隐藏，默认保存为当前医院ID。
			if("1".equals(entityMap.get("pur_type").toString())){
				entityMap.put("pur_hos_id", SessionManager.getHosId());				
				entityMap.put("req_hos_id", SessionManager.getHosId());				
				entityMap.put("pay_hos_id", SessionManager.getHosId());	
			}
			//如果计划类型是统购计划，则显示，并且必填；其中采购单位只能取当前单位的上级，请购单位取当前医院且不可编辑，付款单位取当前单位或者它的上级
			if("2".equals(entityMap.get("pur_type").toString())){			
				entityMap.put("pur_hos_id", entityMap.get("pur_hos_id"));
				entityMap.put("req_hos_id", entityMap.get("hos_id"));
				entityMap.put("pay_hos_id", entityMap.get("pay_hos_id"));
			}	
			entityMap.put("maker", SessionManager.getUserId());//制单人取当前用户id		
			entityMap.put("make_date", sdf.format(new Date()));		
			entityMap.put("is_collect", "0");
			
			String stateValue = MyConfig.getSysPara("04046");
			if("0".equals(stateValue)){
				entityMap.put("state", "2");
			}
			if("1".equals(stateValue)){
				entityMap.put("state", "1");
			}
			
			entityMap.put("pur_id", matPurMainMapper.queryMatPurNextId());
			
			
			JSONArray allDataJson = JSONArray.parseArray((String) entityMap.get("allData"));//获取要添加的数据		
			Iterator allDataIt = allDataJson.iterator();		
			while(allDataIt.hasNext()){//不为空再遍历存取数据			
				Map<String,Object> map = defaultDetailValue();	
				JSONObject jsonObj = JSONObject.parseObject(allDataIt.next().toString());		
				map.put("group_id",Long.parseLong(entityMap.get("group_id").toString()));		
				map.put("hos_id", Long.parseLong(entityMap.get("hos_id").toString()));			
				map.put("copy_code", entityMap.get("copy_code"));			
				map.put("pur_id", entityMap.get("pur_id"));//将主表ID取出来放在明细数据中	
				map.put("pur_code", entityMap.get("pur_code"));
				map.put("pur_detail_id", matPurMainMapper.queryMatPurDetailNextId());
				
				if(!"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
					map.put("inv_id", jsonObj.get("inv_id"));			
					map.put("inv_no",jsonObj.get("inv_no"));				
					map.put("amount",jsonObj.get("amount"));			
					map.put("price",jsonObj.get("price"));	

					if(validateJSON(String.valueOf(jsonObj.get("advise_num")))){
						map.put("advise_num",jsonObj.get("advise_num"));
					}
					
					if(validateJSON(String.valueOf(jsonObj.get("num")))){
						map.put("num",jsonObj.get("num"));
					}
					if(validateJSON(String.valueOf(jsonObj.get("pack_code")))){
						map.put("pack_code",jsonObj.get("pack_code"));
					}			
					
					if(validateJSON(String.valueOf(jsonObj.get("sup_id")))){//如果在下拉列表修改了供应商 值为sup_id+","+sup_no
						if(jsonObj.get("sup_id").toString().contains(",")){
							map.put("sup_id",jsonObj.get("sup_id").toString().split(",")[0]);
							map.put("sup_no",jsonObj.get("sup_id").toString().split(",")[1]);						
						}else{
							//没有选供应商 格式为  ","
							if("".equals(jsonObj.get("sup_id").toString().split(",")[0])){
								map.put("sup_id","");
								map.put("sup_no","");
							}else{
								map.put("sup_id",jsonObj.get("sup_id").toString());
								map.put("sup_no",jsonObj.get("sup_no").toString());
							}
							
						}
					}
					
					if(validateJSON(String.valueOf(jsonObj.get("req_amount")))){
						map.put("req_amount",jsonObj.get("req_amount"));
					}
					if(validateJSON(String.valueOf(jsonObj.get("memo")))){
						map.put("memo",jsonObj.get("memo"));
					}
					if(validateJSON(String.valueOf(jsonObj.get("num_exchange")))){
						map.put("num_exchange",jsonObj.get("num_exchange"));
					}
					
					if(validateJSON(String.valueOf(jsonObj.get("sup_id")))){//如果在下拉列表修改了供应商 值为sup_id+","+sup_no
						if(jsonObj.get("sup_id").toString().contains(",")){
							map.put("sup_id",jsonObj.get("sup_id").toString().split(",")[0]);
							map.put("sup_no",jsonObj.get("sup_id").toString().split(",")[1]);						
						}else{
							//没有选供应商 格式为  ","
							if("".equals(jsonObj.get("sup_id").toString().split(",")[0])){
								map.put("sup_id","");
								map.put("sup_no","");
							}else{
								map.put("sup_id",jsonObj.get("sup_id").toString());
								map.put("sup_no",jsonObj.get("sup_no").toString());
							}
							
						}
					}
					
					if(validateJSON(String.valueOf(jsonObj.get("app_dept_id")))){
						if(jsonObj.get("app_dept_id").toString().contains(",") && !",".equals(jsonObj.get("app_dept_id"))){
							map.put("app_dept_id",jsonObj.get("app_dept_id").toString().split(",")[0]);
							map.put("app_dept_no",jsonObj.get("app_dept_id").toString().split(",")[1]);						
						}else{
							if("".equals(jsonObj.get("app_dept_id").toString()) || ",".equals(jsonObj.get("app_dept_id").toString())){
								map.put("app_dept_id","");
								map.put("app_dept_no","");
							}else{
								map.put("app_dept_id",jsonObj.get("app_dept_id").toString());
								map.put("app_dept_no",jsonObj.get("app_dept_no").toString());
							}
						}
					}
					
					
					if(validateJSON(String.valueOf(jsonObj.get("app_date")))){
						map.put("app_date", DateUtil.stringToDate(jsonObj.get("app_date").toString(),"yyyy-MM-dd"));
					}
					
					/*科室需求对应关系-------begin--*/
					if(ChdJson.validateJSON(String.valueOf(jsonObj.get("req_rela")))){
						for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("req_rela").toString())){
							Map<String,Object> relaMap = new HashMap<String,Object>();
							relaMap.put("group_id", entityMap.get("group_id"));
							relaMap.put("hos_id", entityMap.get("hos_id"));
							relaMap.put("copy_code", entityMap.get("copy_code"));
							relaMap.put("pur_id", map.get("pur_id"));
							relaMap.put("pur_detail_id", map.get("pur_detail_id"));
							
							relaMap.put("req_id", m.get("req_id"));
							relaMap.put("req_detail_id", m.get("req_detail_id"));
							relaMap.put("req_amount", m.get("req_amount"));
							relaMap.put("pur_amount", m.get("pur_amount"));
							relaList.add(relaMap);
						}
					}
					/*科室需求对应关系-------end--*/
					allDataList.add(map);
				}
			}
			
			
			//添加明细数据
			if(allDataList.size() > 0 ){		
				matPurMainMapper.add(entityMap);
				matPurMainMapper.addBatchMatPurDetail(allDataList);
			}
			//添加对应关系
			if(relaList.size()>0){
				//更新仓库需求计划状态
				if(entityMap.containsKey("is_dept")){
					if(entityMap.get("is_dept").equals("0")){
						matPurMainMapper.updateStoreReqState(relaList);
						matStorePurRelaMapper.addBatch(relaList);
					}
					if(entityMap.get("is_dept").equals("1")){
						matDeptPurRelaMapper.addBatch(relaList);
						matPurMainMapper.updateDeptReqState(relaList);
					}
				}
				
			}
			
			return "{\"pur_id\":"+"\""+entityMap.get("pur_id")+"\","+"\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
			//throw new SysException("{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatCommonOutApply\"}");
		}
			
	}
	
	//验证
	public boolean validateJSON(String str) {
		if (str != null && str != "null" && str != "" && str != "0") {
			return true;
		}
		return false;
	}
	
	/**
	 * @Description 
	 * 采购计划编制<BR>按需求计划生成 添加采购计划
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String addProdMatPurMain(Map<String, Object> entityMap) throws DataAccessException {
		try{
			String pur_code;
			String make_date = entityMap.get("make_date").toString();	
			entityMap.put("make_year",make_date.substring(0, 4));		
			entityMap.put("make_month", make_date.substring(5, 7));		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//是否存在于表中
			int flag =  matPurMainMapper.queryIsExists(entityMap);
			entityMap.put("prefixe", "CG");
			entityMap.put("table_code", "MAT_PUR_MAIN");
			boolean codeFlag;
			if(flag > 0){			
				String maxCode = matPurMainMapper.queryMaxCode(entityMap);				
				entityMap.put("max_code", (Integer.parseInt(maxCode))+1);				
				//更新单据号表
				int state1 = matPurMainMapper.updateMatNoMatch(entityMap);				
				String code = "00000" + entityMap.get("max_code").toString();				
				code = code.substring(code.length()-5,code.length());			
				pur_code = "CG"+entityMap.get("make_year")+entityMap.get("make_month")+code;				
				entityMap.put("pur_code", pur_code);//计划单号				
				codeFlag = true;				
			}else{			
				pur_code  = "CG"+entityMap.get("make_year")+entityMap.get("make_month")+"00001";							
				entityMap.put("pur_code", pur_code);				
				codeFlag = false;			
			}
			
			if("1".equals(entityMap.get("pur_type").toString())){			
				entityMap.put("pur_hos_id", SessionManager.getHosId());			
				entityMap.put("req_hos_id", SessionManager.getHosId());			
				entityMap.put("pay_hos_id", SessionManager.getHosId());			
			}
			entityMap.put("maker", SessionManager.getUserId());//制单人取当前用户id		
			entityMap.put("make_date", sdf.format(new Date()));	
			entityMap.put("is_collect", "0");		
			String stateValue = MyConfig.getSysPara("04046");
			if("0".equals(stateValue)){
				entityMap.put("state", "2");
			}
			if("1".equals(stateValue)){
				entityMap.put("state", "1");
			}
			
			entityMap.put("pur_id", matPurMainMapper.queryMatPurNextId());
			matPurMainMapper.add(entityMap);
			
			if(codeFlag == false){				
				//更新单据序号表
				entityMap.put("max_no", 1);
				int state = matPurMainMapper.addMatNoMatch(entityMap);
					
			}
			
			StringBuffer detail_ids = new StringBuffer();
			List<Map<String,Object>> relaList = new ArrayList<Map<String,Object>>();//保存对应关系表集合			
			List<Map<String,Object>> allDataList = new ArrayList<Map<String,Object>>();//添加数据参数集合		
			JSONArray allDataJson = JSONArray.parseArray((String) entityMap.get("allData"));//获取要添加的数据		
			Iterator iterator = allDataJson.iterator();
			while(iterator.hasNext()){			
				Map<String,Object> map = defaultDetailValue();				
				JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());				
				if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					map.put("pur_id", entityMap.get("pur_id"));//计划单ID
					map.put("pur_code", entityMap.get("pur_code"));//计划单code
					
					map.put("inv_id", jsonObj.get("inv_id"));//材料ID
					map.put("inv_no", jsonObj.get("inv_no"));//材料变更ID
					
					if (validateJSON(String.valueOf(jsonObj.get("amount")))) {
						map.put("amount", jsonObj.get("amount"));
					}//数量
					if (validateJSON(String.valueOf(jsonObj.get("price")))) {
						map.put("price", jsonObj.get("price"));
					}//计划单价
					if(validateJSON(String.valueOf(jsonObj.get("advise_num")))){
						map.put("advise_num",jsonObj.get("advise_num"));
					}//建议采购量
					if (validateJSON(String.valueOf(jsonObj.get("pack_code")))) {
						map.put("pack_code", jsonObj.get("pack_code"));
					}//包装单位
					if (validateJSON(String.valueOf(jsonObj.get("num")))) {
						map.put("num", jsonObj.get("num"));
					}//包装数量
					if (validateJSON(String.valueOf(jsonObj.get("num_exchange")))) {
						map.put("num_exchange", jsonObj.get("num_exchange"));
					}//包装换算量
					if (validateJSON(String.valueOf(jsonObj.get("req_amount")))) {
						map.put("req_amount", jsonObj.get("req_amount"));
					}//数量
					
					if(validateJSON(String.valueOf(jsonObj.get("app_dept_id")))){
						if(jsonObj.get("app_dept_id").toString().contains(",")){
							map.put("app_dept_id",jsonObj.get("app_dept_id").toString().split(",")[0]);
							map.put("app_dept_no",jsonObj.get("app_dept_id").toString().split(",")[1]);						
						}else{
							if("".equals(jsonObj.get("app_dept_id").toString().split(",")[0])){
								map.put("app_dept_id","");
								map.put("app_dept_no","");
							}else{
								map.put("app_dept_id",jsonObj.get("app_dept_id").toString());
								map.put("app_dept_no",jsonObj.get("app_dept_no").toString());
							}
						}
					}
					
					//传递过来的值有两种可能   160,   ; 1,1
					if(validateJSON(String.valueOf(jsonObj.get("sup_id")))){//如果在下拉列表修改了供应商 值为sup_id+","+sup_no
						if(jsonObj.get("sup_id").toString().contains(",")){
							map.put("sup_id",jsonObj.get("sup_id").toString().split(",")[0]);
							map.put("sup_no",jsonObj.get("sup_id").toString().split(",")[1]);						
						}else{
							//没有选供应商 格式为  ","
							if("".equals(jsonObj.get("sup_id").toString().split(",")[0])){
								map.put("sup_id","");
								map.put("sup_no","");
							}else{
								map.put("sup_id",jsonObj.get("sup_id").toString());
								map.put("sup_no",jsonObj.get("sup_no").toString());
							}
							
						}
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("memo")))) {
						map.put("memo", jsonObj.get("memo"));
					}//备注
					
					//明细表ID
					if(jsonObj.get("pur_detail_id") == null || "".equals(jsonObj.get("pur_detail_id"))){
						map.put("pur_detail_id", matPurMainMapper.queryMatPurDetailNextId());
						allDataList.add(map);
					}else{
						detail_ids.append(jsonObj.get("pur_detail_id").toString()).append(",");
						map.put("pur_detail_id", jsonObj.get("pur_detail_id"));
						allDataList.add(map);
					}
					
					/*科室需求对应关系-------begin--*/
					if(ChdJson.validateJSON(String.valueOf(jsonObj.get("req_rela")))){
						for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("req_rela").toString())){
							Map<String,Object> relaMap = new HashMap<String,Object>();
							relaMap.put("group_id", entityMap.get("group_id"));
							relaMap.put("hos_id", entityMap.get("hos_id"));
							relaMap.put("copy_code", entityMap.get("copy_code"));
							relaMap.put("pur_id", map.get("pur_id"));
							relaMap.put("pur_detail_id", map.get("pur_detail_id"));
						
							relaMap.put("req_id", m.get("req_id"));
							relaMap.put("req_detail_id", m.get("req_detail_id"));
							relaMap.put("req_amount", m.get("req_amount"));
							relaMap.put("pur_amount", m.get("amount"));
							relaList.add(relaMap);
						}
					}
					/*科室需求对应关系-------end--*/
					
				}
			}
			
			if(detail_ids.length() > 0){
				Map<String,Object> deleteMap = new HashMap<String,Object>();
				deleteMap.put("group_id", entityMap.get("group_id"));
				deleteMap.put("hos_id", entityMap.get("hos_id"));
				deleteMap.put("copy_code", entityMap.get("copy_code"));
				deleteMap.put("pur_id", entityMap.get("pur_id"));//主表ID
				deleteMap.put("detail_ids", detail_ids.substring(0,detail_ids.length()-1));//明细IDS
				//首先删除明细表中前台删除的明细数据
				matPurMainMapper.deleteForUpdate(deleteMap);
			}
			
			if(allDataList.size() > 0 ){				
				matPurMainMapper.addBatchMatPurDetail(allDataList);				
				//添加对应关系表数据
				matPurMainMapper.addBatchMatReqPurRela(relaList);		
			}
			
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatRequireMain\"}";
		}	
		
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
	
	/**
	 * @Description 
	 * 采购计划编制<BR>删除
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		
		return null;
	}
	
	/**
	 * @Description 
	 * 采购计划编制<BR>批量删除
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try{
			List<Map<String,Object>> deptList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> storeList = new ArrayList<Map<String,Object>>();
			
			for(Map<String,Object> map : entityList){
				Map<String,Object> deptMap = new HashMap<String,Object>();
				Map<String,Object> storeMap = new HashMap<String,Object>();
				if(map.get("come_from").equals("2")){//仓库需求计划
					storeMap.put("group_id", map.get("group_id"));
					storeMap.put("hos_id", map.get("hos_id"));
					storeMap.put("copy_code", map.get("copy_code"));
					storeMap.put("pur_id", map.get("pur_id"));
					storeList.add(storeMap);
				}
				if(map.get("come_from").equals("3")){//科室需求计划
					deptMap.put("group_id", map.get("group_id"));
					deptMap.put("hos_id", map.get("hos_id"));
					deptMap.put("copy_code", map.get("copy_code"));
					deptMap.put("pur_id", map.get("pur_id"));
					deptList.add(deptMap);
				}
			}
			//查询科室与采购的对应关系
			if(deptList.size()>0){
				List<Map<String,Object>> deptRelaList =JsonListMapUtil.toListMapLower(matPurMainMapper.queryMatDeptReqPurRela(deptList));//查询科室需求采购计划对应关系
				if(deptRelaList.size()>0){
					matPurMainMapper.updateDeptReqStateZero(deptRelaList);//更新科室需求计划is_close=0
					matDeptPurRelaMapper.deleteBatch(deptRelaList);//删除关系表
				}
			}
			
			//查询仓库与采购的对应关系
			if(storeList.size()>0){
				List<Map<String,Object>> storeRelaList =JsonListMapUtil.toListMapLower(matPurMainMapper.queryMatStoreReqPurRela(storeList));//查询科室需求采购计划对应关系
				if(storeRelaList.size() > 0){//如果对应关系存在
					matPurMainMapper.updateStoreReqStateZero(storeRelaList);//更新仓库需求计划is_close=0
					matStorePurRelaMapper.deleteBatch(storeRelaList);//删除关系表
				}
			}
			
			
			//删除申请单与采购计划对应关系
			matApplyPurRelaMapper.deleteMatApplyPurRelaBatch(entityList);
				
			matPurMainMapper.deleteMatPurDetail(entityList);//删除明细数据
			matPurMainMapper.deleteMatPurMain(entityList);//删除主表数据
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败！\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatRequireMain\"}";
		}	
		
	}
	
	
	//PageOffice批量打印凭证-返回Map
		@Override
		public Map<String,Object> queryMatPurMain(Map<String, Object> map)throws DataAccessException {
			
			try{
				Map<String,Object> reMap=new HashMap<String,Object>();
				WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
				MatPurMainMapper matPurMainMapper = (MatPurMainMapper)context.getBean("matPurMainMapper");
				
				//查询凭证主表
				List<Map<String,Object>> mainList=matPurMainMapper.queryMatMakePrintTemlateByMainBatch(map);
						
				//查询凭证明细表
				List<Map<String,Object>> detailList=matPurMainMapper.queryMatMakePrintTemlateByDetail(map);
				
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
				
				return reMap;
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				throw new SysException(e.getMessage());
			}
			
		}
	
	/**
	 * @Description 
	 * 采购计划编制<BR>修改主表、更新或添加明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		try{
			JSONArray reqRelaJson = null;		
			List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();			
			List<Map<String,Object>> insertList = new ArrayList<Map<String,Object>>();//添加数据参数集合			
			List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>();//获取删除的数据List			
			List<Map<String,Object>> reqRelaList = new ArrayList<Map<String,Object>>();//需求科室采购计划对应关系LIST	
			List<Map<String,Object>> velList = new ArrayList<Map<String,Object>>();//需求科室采购计划对应关系LIST
			JSONArray allDataJson = JSONArray.parseArray((String) entityMap.get("allData"));//获取要添加的数据			
			JSONArray deletedDataJson = JSONArray.parseArray((String) entityMap.get("deletedData"));//获取要删除的数据			
			Iterator iterator = allDataJson.iterator();	
			
			//采购单位、请购单位、付款单位：如果计划类型为自购计划，则隐藏，默认保存为当前医院ID。
			if("1".equals(entityMap.get("pur_type").toString())){//判断是自购计划还是统购计划				
				entityMap.put("pur_hos_id", SessionManager.getHosId());				
				entityMap.put("req_hos_id", SessionManager.getHosId());				
				entityMap.put("pay_hos_id", SessionManager.getHosId());				
			}
			
			//如果计划类型是统购计划，则显示，并且必填；其中采购单位只能取当前单位的上级，请购单位取当前医院且不可编辑，付款单位取当前单位或者它的上级
			if("2".equals(entityMap.get("pur_type").toString())){			
				entityMap.put("pur_hos_id", entityMap.get("pur_hos_id"));
				entityMap.put("req_hos_id", entityMap.get("hos_id"));
				entityMap.put("pay_hos_id", entityMap.get("pay_hos_id"));
			}	
			
			velList.add(entityMap);
			
			StringBuffer detail_ids = new StringBuffer();
			//获取所有数据
			while(iterator.hasNext()){//遍历所有数据				
				Map<String,Object> map = defaultDetailValue();		
				JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());			
				if(!"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
					map.put("group_id",Long.parseLong(entityMap.get("group_id").toString()));				
					map.put("hos_id", Long.parseLong(entityMap.get("hos_id").toString()));				
					map.put("copy_code", entityMap.get("copy_code"));				
					map.put("pur_id", entityMap.get("pur_id"));
					map.put("pur_code", entityMap.get("pur_code"));
					
					map.put("inv_id", jsonObj.get("inv_id"));			
					map.put("inv_no",jsonObj.get("inv_no"));			
					map.put("amount",jsonObj.get("amount"));
					map.put("price",jsonObj.get("price"));		

					if(validateJSON(String.valueOf(jsonObj.get("advise_num")))){
						map.put("advise_num",jsonObj.get("advise_num"));
					}
					
					if(validateJSON(String.valueOf(jsonObj.get("num")))){
						map.put("num",jsonObj.get("num"));
					}
					if(validateJSON(String.valueOf(jsonObj.get("pack_code")))){
						map.put("pack_code",jsonObj.get("pack_code"));
					}
					
					if(validateJSON(String.valueOf(jsonObj.get("sup_id")))){//如果在下拉列表修改了供应商 值为sup_id+","+sup_no
						if(jsonObj.get("sup_id").toString().contains(",")){
							map.put("sup_id",jsonObj.get("sup_id").toString().split(",")[0]);
							map.put("sup_no",jsonObj.get("sup_id").toString().split(",")[1]);						
						}else{
							//没有选供应商 格式为  ","
							if("".equals(jsonObj.get("sup_id").toString().split(",")[0])){
								map.put("sup_id","");
								map.put("sup_no","");
							}else{
								map.put("sup_id",jsonObj.get("sup_id").toString());
								map.put("sup_no",jsonObj.get("sup_no").toString());
							}
							
						}
					}
					
					if(validateJSON(String.valueOf(jsonObj.get("num_exchange")))){				
						map.put("num_exchange", jsonObj.get("num_exchange"));
					}					
					if(validateJSON(String.valueOf(jsonObj.get("req_amount")))){					
						map.put("req_amount",jsonObj.get("req_amount"));
					}				
					if(validateJSON(String.valueOf(jsonObj.get("memo")))){					
						map.put("memo", jsonObj.get("memo"));
					}
					
					if(validateJSON(String.valueOf(jsonObj.get("app_dept_id")))){
						if(jsonObj.get("app_dept_id").toString().contains(",")){
							map.put("app_dept_id",jsonObj.get("app_dept_id").toString().split(",")[0]);
							map.put("app_dept_no",jsonObj.get("app_dept_id").toString().split(",")[1]);						
						}else{
							//没有选供应商 格式为  ","
							if("".equals(jsonObj.get("app_dept_id").toString().split(",")[0])){
								map.put("app_dept_id","");
								map.put("app_dept_no","");
							}else{
								map.put("app_dept_id",jsonObj.get("app_dept_id").toString());
								map.put("app_dept_no",jsonObj.get("app_dept_no").toString());
							}
						}
					}
					
					if(validateJSON(String.valueOf(jsonObj.get("app_date")))){
						map.put("app_date", DateUtil.stringToDate(jsonObj.get("app_date").toString(),"yyyy-MM-dd"));
					}
					
					//明细表ID
					if(jsonObj.get("pur_detail_id") == null || "".equals(jsonObj.get("pur_detail_id"))){
						map.put("pur_detail_id", matPurMainMapper.queryMatPurDetailNextId());
						insertList.add(map);
					}else{
						detail_ids.append(jsonObj.get("pur_detail_id").toString()).append(",");
						map.put("pur_detail_id", jsonObj.get("pur_detail_id"));
						updateList.add(map);
					}
					
					reqRelaJson = JSONArray.parseArray((String) jsonObj.get("req_rela"));//获取科室需求采购计划对应关系				
					if(reqRelaJson != null && !"".equals(reqRelaJson)){				
						Iterator reqRelaiterator = reqRelaJson.iterator();					
						while(reqRelaiterator.hasNext()){//遍历对应关系JSON						
							Map<String,Object> reqRelaMap = new HashMap<String,Object>();//需求科室采购计划对应关系MAP						
							JSONObject reqRelaJsonObj = JSONObject.parseObject(reqRelaiterator.next().toString());						
							reqRelaMap.put("group_id",Long.parseLong(entityMap.get("group_id").toString()));						
							reqRelaMap.put("hos_id", Long.parseLong(entityMap.get("hos_id").toString()));						
							reqRelaMap.put("copy_code", entityMap.get("copy_code"));					
							reqRelaMap.put("pur_id", entityMap.get("pur_id"));
							reqRelaMap.put("req_id", reqRelaJsonObj.get("req_id"));	
							reqRelaMap.put("req_detail_id", reqRelaJsonObj.get("req_detail_id"));						
							reqRelaMap.put("req_amount", reqRelaJsonObj.get("req_amount"));						
							reqRelaMap.put("pur_amount", reqRelaJsonObj.get("pur_amount"));					
							reqRelaList.add(reqRelaMap);
						}				
					}
				}
			}
				
			if(detail_ids.length() > 0){
				Map<String,Object> deleteMap = new HashMap<String,Object>();
				deleteMap.put("group_id", entityMap.get("group_id"));
				deleteMap.put("hos_id", entityMap.get("hos_id"));
				deleteMap.put("copy_code", entityMap.get("copy_code"));
				deleteMap.put("pur_id", entityMap.get("pur_id"));//主表ID
				deleteMap.put("detail_ids", detail_ids.substring(0,detail_ids.length()-1));//明细IDS
				//首先删除明细表中前台删除的明细数据
				matPurMainMapper.deleteForUpdate(deleteMap);
			}
			
			
			matPurMainMapper.update(entityMap);//修改采购主表数据
			if(insertList.size() > 0 ){//没有数据就添加				
				matPurMainMapper.addBatchMatPurDetail(insertList);//添加采购明细数据
			}			
			if(updateList.size() > 0 ){//有数据就修改			
				matPurMainMapper.updateBatchMatPurDetail(updateList);//添加采购明细数据
			}
			
			if(reqRelaList.size() > 0){
				//更新对应关系
				matPurMainMapper.updateBatchMatReqPurRela(reqRelaList);//修改采购计划对应关系				
			}			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"更新失败！\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatRequireMain\"}";
		}
					
	}
	
	/**
	 * @Description 
	 * 采购计划编制<BR>查询
	 * @param  entityMap<BR>
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {		
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage)entityMap.get("sysPage");		
		if(sysPage.getTotal() == -1){			
			List<Map<String,Object>> list = matPurMainMapper.queryMain(entityMap);			
			return ChdJson.toJson(JsonListMapUtil.toListMapLower(list));			
		}else{			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());			
			List<Map<String,Object>> list = matPurMainMapper.queryMain(entityMap, rowBounds);			
			PageInfo page = new PageInfo(list);	
			
			return ChdJson.toJson(JsonListMapUtil.toListMapLower(list),page.getTotal());
		}
	}
	//明细查询
	@Override
	public String queryDetails(Map<String, Object> entityMap) throws DataAccessException {		
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage)entityMap.get("sysPage");		
		if(sysPage.getTotal() == -1){			
			List<Map<String,Object>> list = matPurMainMapper.queryDetails(entityMap);			
			return ChdJson.toJson(JsonListMapUtil.toListMapLower(list));			
		}else{			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());			
			List<Map<String,Object>> list = matPurMainMapper.queryDetails(entityMap, rowBounds);			
			PageInfo page = new PageInfo(list);	
			
			return ChdJson.toJson(JsonListMapUtil.toListMapLower(list),page.getTotal());
		}
	}
	/**
	 * @Description 
	 * 采购计划编制<BR> 根据计划单ID查询
	 * @param  entityMap<BR>
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {		
		return matPurMainMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 返回用用于保存的默认值
		public Map<String, Object> defaultDetailValue() {
			Map<String, Object> mapDetailVo = new HashMap<String, Object>();
			mapDetailVo.put("price",0);
			mapDetailVo.put("amount",0);			
			mapDetailVo.put("num", 0);			
			mapDetailVo.put("pack_code", "");			
			mapDetailVo.put("req_amount", "");
			
			mapDetailVo.put("memo", "");			
			mapDetailVo.put("num_exchange", 0);			
			mapDetailVo.put("sup_no","");			
			mapDetailVo.put("sup_id","");
			mapDetailVo.put("app_dept_id","");			
			mapDetailVo.put("app_date","");
			return mapDetailVo;
		}
		
		/**
		 * @Description 
		 * 采购计划编制<BR>中止采购计划
		 * @param entityMap
		 * @return int
		 * @throws DataAccessException
		*/
		@Override
		public String updateMatPurPlanState(List<Map<String, Object>> entityMap) throws DataAccessException {			
			try {				
				matPurMainMapper.updateMatPurPlanState(entityMap);				
				return "{\"msg\":\"中止成功.\",\"state\":\"true\"}";				
			} catch (Exception e) {				
				logger.error(e.getMessage(),e);
				throw new SysException("{\"error\":\"中止失败！\"}");
				//return "{\"error\":\"中止失败 数据库异常 请联系管理员! 错误编码 updateMatPurPlanState\"}";
			}
		}
		
		
		/**
		 * @Description 
		 * 采购计划编制<BR>查询 按采购计划主表ID查询明细
		 * @param entityMap
		 * @return String
		 * @throws DataAccessException
		*/
		@Override
		public String queryMatPurDetail(Map<String, Object> entityMap) throws DataAccessException {
			SysPage sysPage = new SysPage();			
			sysPage = (SysPage)entityMap.get("sysPage");			
			if(sysPage.getTotal() == -1){				
				List<?> list = matPurMainMapper.queryMatPurDetail(entityMap);				
				return ChdJson.toJson(list);				
			}else{				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());				
				List<?> list = matPurMainMapper.queryMatPurDetail(entityMap, rowBounds);				
				PageInfo page = new PageInfo(list);				
				return ChdJson.toJson(list,page.getTotal());
			}
		}
		
		/**
		 * 查询科室需求计划主表
		 * @param entityMap
		 * @return String 
		 * @throws DataAccessException
		 */
		@Override
		public String queryMatRequireMain(Map<String, Object> entityMap) throws DataAccessException {
			SysPage sysPage = new SysPage();			
			sysPage = (SysPage)entityMap.get("sysPage");			
			if(sysPage.getTotal() == -1){				
				List<?> list = matPurMainMapper.queryMatRequireMain(entityMap);				
				return ChdJson.toJson(list);				
			}else{				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());				
				List<?> list = matPurMainMapper.queryMatRequireMain(entityMap, rowBounds);				
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
			List<?> list = matPurMainMapper.queryMatInvCurAmountDetail(entityMap);			
			return ChdJson.toJson(list);
		}
		
		/**
		 * 按科室计划单ID 查询科室需求计划主表
		 * @param entityMap
		 * @return Map<String,Object>
		 * @throws DataAccessException
		 */
		@Override
		public Map<String, Object> queryMatRequireMainByCode(Map<String, Object> entityMap) throws DataAccessException {			
			return matPurMainMapper.queryMatRequireMainByCode(entityMap);
		}
		
		/**
		 * 按科室计划单ID 查询科室计划单明细 
		 * @param entityMap
		 * @return String
		 * @throws DataAccessException
		 */
		@Override
		public String queryMatRequireDetailByCode(Map<String, Object> entityMap) throws DataAccessException {			
			StringBuffer req_ids = new StringBuffer();			
			if(entityMap.get("para") != null && !"".equals(entityMap.get("para").toString())){				
				for(String id:entityMap.get("para").toString().split(",")){					
					String[] ids = id.split("@");				
					req_ids.append(ids[3]);					
					req_ids.append(",");					
				}				
				entityMap.put("req_ids",req_ids.substring(0,req_ids.length()-1));
			}
			SysPage sysPage = new SysPage();			
			sysPage = (SysPage)entityMap.get("sysPage");			
			if(sysPage.getTotal() == -1){				
				List<?> list = matPurMainMapper.queryMatRequireDetailByCode(entityMap);				
				return ChdJson.toJson(list);				
			}else{				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());								
				List<?> list = matPurMainMapper.queryMatRequireDetailByCode(entityMap, rowBounds);				
				PageInfo page = new PageInfo(list);				
				return ChdJson.toJson(list,page.getTotal());
			}
		}
		
		/**
		 * 查询 计划数量明细
		 * @param entityMap
		 * @return String
		 * @throws DataAccessException
		 */
		@Override
		public String queryMatRequireAmountDetail(Map<String, Object> entityMap) throws DataAccessException {			
			JSONArray req_rela_json = JSONArray.parseArray((String)entityMap.get("req_rela"));			
			Iterator iterator = req_rela_json.iterator();			
			while (iterator.hasNext()) {				
				JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());				
				entityMap.put("inv_id", jsonObj.get("inv_id"));				
				entityMap.put("req_id", jsonObj.get("req_id"));				
			}			
			List<?> list = matPurMainMapper.queryReqAmountDetail(entityMap);				
			return ChdJson.toJson(list);			
		}
		
		/**
		 * 按需求计划单ID查询 科室需求计划明细
		 * @param entityMap
		 * @return String
		 * @throws DataAccessException
		 */
		@Override
		public String queryMatReqByCode(Map<String, Object> entityMap) throws DataAccessException {			
			SysPage sysPage = new SysPage();			
			sysPage = (SysPage)entityMap.get("sysPage");			
			if(sysPage.getTotal() == -1){				
				List<?> list = matPurMainMapper.queryMatReqByCode(entityMap);				
				return ChdJson.toJson(list);				
			}else{				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());			
				List<?> list = matPurMainMapper.queryMatReqByCode(entityMap, rowBounds);				
				PageInfo page = new PageInfo(list);				
				return ChdJson.toJson(list,page.getTotal());
			}
		}

		/**
		 * 库存安全导入查询
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		@Override
		public String queryMatPurSafe(Map<String, Object> entityMap) throws DataAccessException {
			List<?> list = matPurMainMapper.queryMatPurSafe(entityMap);
			return ChdJson.toJson(list);
		}

		//入库模板打印（包含主从表）
		@Resource(name = "superPrintService")
		private final SuperPrintService superPrintService = null;
		@Override
		public String queryMatMakeByPrintTemlate(Map<String, Object> entityMap)throws DataAccessException {
			// TODO Auto-generated method stub
			//查询入库主表
			try {
				//查询入库主表
				if("1".equals(String.valueOf(entityMap.get("p_num")))){
					List<Map<String,Object>> map=matPurMainMapper.queryMatMakePrintTemlateByMainBatch(entityMap);
					List<Map<String,Object>> list=matPurMainMapper.queryMatMakePrintTemlateByDetail(entityMap);
					return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
				}else{
					Map<String,Object> map=matPurMainMapper.queryMatMakePrintTemlateByMain(entityMap);
					
					//查询入库明细表
					List<Map<String,Object>> list=matPurMainMapper.queryMatMakePrintTemlateByDetail(entityMap);
					return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
				}
									
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				throw new SysException(e.getMessage());
			}
			
		}

		//采购模板打印（按供应商汇总明细打印）
		@Override
		public Map<String, Object> queryMatPurMainDetail(Map<String, Object> entityMap)throws DataAccessException {
			// TODO Auto-generated method stub
			//查询入库主表
			try {
				Map<String,Object> reMap=new HashMap<String,Object>();
				WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
				MatPurMainMapper matPurMainMapper = (MatPurMainMapper)context.getBean("matPurMainMapper");
				
/*				Map<String,Object> map= new HashMap<String, Object>();
				map.put("ID", 1);
				map.put("TITLE", "明细按供应商汇总打印");
				List<Map<String, Object>> listMain = new ArrayList<Map<String,Object>>();
				listMain.add(map);*/
				//查询明细表
				List<Map<String,Object>> listDetail =matPurMainMapper.queryMatMakeDetailPrintTemlate(entityMap);
				reMap.put("detail", listDetail);
				return reMap;
				} catch (Exception e) {
				logger.error(e.getMessage(),e);
				throw new SysException(e.getMessage());
			}
		}
		
		/**
		 * 查询 仓库需求计划
		 * @param entityMap
		 * @return String
		 * @throws DataAccessException
		 */
		@Override
		public String queryMatReqStoreMain(Map<String, Object> entityMap) throws DataAccessException {
			SysPage sysPage = new SysPage();		
			sysPage = (SysPage)entityMap.get("sysPage");
			if(sysPage.getTotal() == -1){		
				List<Map<String,Object>> list = matPurMainMapper.queryMatReqStoreMain(entityMap);	
				return ChdJson.toJson(JsonListMapUtil.toListMapLower(list));				
			}else{				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());	
				List<Map<String,Object>> list = matPurMainMapper.queryMatReqStoreMain(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);		
				return ChdJson.toJson(JsonListMapUtil.toListMapLower(list),page.getTotal());
			}
		}
		
		
		/**
		 * 组装汇总的仓库明细数据
		 */
		@Override
		public String queryStoreCollectData(Map<String, Object> entityMap) throws DataAccessException {
			List<Map<String, Object>> detailList= new ArrayList<Map<String,Object>>();
			
			JSONArray json = JSONArray.parseArray((String)entityMap.get("allData"));
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
					Map<String, Object> detailMap = new HashMap<String, Object>();
				
					detailMap.put("group_id", entityMap.get("group_id"));
					detailMap.put("hos_id", entityMap.get("hos_id"));
					detailMap.put("copy_code", entityMap.get("copy_code"));
					detailMap.put("inv_id", jsonObj.get("inv_id"));
					detailMap.put("inv_no", jsonObj.get("inv_no"));
					detailMap.put("req_id", jsonObj.get("req_id"));
					detailMap.put("req_detail_id", jsonObj.get("req_detail_id"));	
					detailMap.put("req_amount", jsonObj.get("req_amount"));	
					detailMap.put("pur_amount", jsonObj.get("pur_amount"));	
					detailList.add(detailMap);
				}
			}
			
			List<Map<String, Object>> list= matPurMainMapper.queryStoreNewDetail(detailList);
			return ChdJson.toJsonLower(list);
			
		}
		
		/**
		 * 查询 科室需求计划
		 * @param entityMap
		 * @return String
		 * @throws DataAccessException
		 */
		@Override
		public String queryMatReqDeptMain(Map<String, Object> entityMap) throws DataAccessException {
			SysPage sysPage = new SysPage();		
			sysPage = (SysPage)entityMap.get("sysPage");
			if(sysPage.getTotal() == -1){		
				List<Map<String,Object>> list = matPurMainMapper.queryMatReqDeptMain(entityMap);	
				return ChdJson.toJson(JsonListMapUtil.toListMapLower(list));				
			}else{				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());	
				List<Map<String,Object>> list = matPurMainMapper.queryMatReqDeptMain(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);		
				return ChdJson.toJson(JsonListMapUtil.toListMapLower(list),page.getTotal());
			}
		}
		
		/**
		 * 组装汇总的仓库明细数据
		 */
		@Override
		public String queryDeptCollectData(Map<String, Object> entityMap) throws DataAccessException {
			List<Map<String, Object>> detailList= new ArrayList<Map<String,Object>>();
			
			JSONArray json = JSONArray.parseArray((String)entityMap.get("allData"));
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
					Map<String, Object> detailMap = new HashMap<String, Object>();
				
					detailMap.put("inv_id", jsonObj.get("inv_id"));
					detailMap.put("inv_no", jsonObj.get("inv_no"));
					detailMap.put("req_id", jsonObj.get("req_id"));
					detailMap.put("req_detail_id", jsonObj.get("req_detail_id"));
					detailMap.put("dept_id", jsonObj.get("dept_id"));
					detailMap.put("dept_no", jsonObj.get("dept_no"));
					detailMap.put("make_date", jsonObj.get("make_date"));
					detailMap.put("req_amount", jsonObj.get("req_amount"));	
					detailMap.put("pur_amount", jsonObj.get("pur_amount"));	
					detailList.add(detailMap);
				}
			}
			
			List<Map<String, Object>> list= matPurMainMapper.queryDeptNewDetail(entityMap, detailList);
			return ChdJson.toJsonLower(list);
		}
		
		/**
		 * 获取需要入库单号
		 * @param entityMap
		 * map参数必须包含(group_id, hos_id, copy_code, store_id, year, month, bus_type_code)这六个键值
		 * @return
		 */
		public String getNextReq_code(Map<String, Object> entityMap){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			
			map.put("table_code", "MAT_PUR_MAIN");
			map.put("year", entityMap.get("year"));
			map.put("month", entityMap.get("month"));
			map.put("day", entityMap.get("day"));
			map.put("prefixe", "CG");
			map.put("store_alias", "");
			map.put("bus_type", "");
			//判断是否存在该业务流水码
			int flag = matNoManageMapper.queryIsExists(map);
			String max_no = "";
			if(flag == 0){
				//如不存在则流水码为1，并插入流水码表中
				max_no = "1";
				map.put("max_no", 1);
				matNoManageMapper.add(map);
			}else{
				//更新该业务流水码+1
				matNoManageMapper.updateMaxNo(map);
				//取出该业务更新后的流水码
				max_no = matNoManageMapper.queryMaxCode(map);
			}
			
			//补流水码前缀0
			for (int i = max_no.length() ; i < 4; i++) {
				max_no = "0" + max_no;
			}
			//组装流水码
			String req_code = "";
			req_code = "CG" + 
			entityMap.get("year").toString().substring(2, 4) + entityMap.get("month") + entityMap.get("day") +max_no;
			
			return req_code;
		}
		
		@Override
		public String querySupDetails(Map<String, Object> entityMap) throws DataAccessException {
			SysPage sysPage = new SysPage();		
			sysPage = (SysPage)entityMap.get("sysPage");
			if(sysPage.getTotal() == -1){		
				List<Map<String,Object>> list = matPurMainMapper.querySupDetails(entityMap);	
				return ChdJson.toJson(JsonListMapUtil.toListMapLower(list));				
			}else{				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());	
				List<Map<String,Object>> list = matPurMainMapper.querySupDetails(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);		
				return ChdJson.toJson(JsonListMapUtil.toListMapLower(list),page.getTotal());
			}
		}
		
		//组装数据
		@Override
		public String queryPurDetailCollectData(Map<String, Object> entityMap) throws DataAccessException {
			List<Map<String, Object>> detailList= new ArrayList<Map<String,Object>>();
			
			JSONArray json = JSONArray.parseArray((String)entityMap.get("allData"));
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
					Map<String, Object> detailMap = new HashMap<String, Object>();
				
					detailMap.put("group_id", entityMap.get("group_id"));
					detailMap.put("hos_id", entityMap.get("hos_id"));
					detailMap.put("copy_code", entityMap.get("copy_code"));
					detailMap.put("inv_id", jsonObj.get("inv_id"));
					detailMap.put("inv_no", jsonObj.get("inv_no"));
					detailMap.put("price", jsonObj.get("price"));
					detailMap.put("pur_id", jsonObj.get("pur_id"));	
					detailMap.put("pur_detail_id", jsonObj.get("pur_detail_id"));
					detailMap.put("amount", jsonObj.get("amount"));		
					detailList.add(detailMap);
				}
			}
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			if("1".equals(MyConfig.getSysPara("04041"))){
				//汇总材料
				list= matPurMainMapper.queryPurDetailCollectData(detailList);
			}else{
				//不汇总材料
				list= matPurMainMapper.queryPurDetailNotCollectData(detailList);
			}
			
			return ChdJson.toJsonLower(list);
		}
		/**
		 * 关闭材料
		 */
		@Override
		public String updateMatPurCloseInv(List<Map<String, Object>> entityList) throws DataAccessException {
			try {	
				//批量关闭材料
				matPurMainMapper.updateMatPurCloseInv(entityList);
				//关闭申请单
				List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(matPurMainMapper.queryMatApplyDetailList(entityList));
				if(list.size() > 0){
					matPurMainMapper.updateMatApplyCloseInv(list);
				}
				
			}catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"操作失败\"}");
			}	
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		/**
		 * 取消关闭材料
		 */
		@Override
		public String updateMatPurCancleCloseInv(List<Map<String, Object>> entityList) throws DataAccessException {
			try {	
				//批量关闭材料
				matPurMainMapper.updateMatPurCancleCloseInv(entityList);
				//关闭申请单
				List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(matPurMainMapper.queryMatApplyDetailList(entityList));
				if(list.size() > 0){
					matPurMainMapper.updateMatApplyCancleCloseInv(list);
				}
			}catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"操作失败\"}");
			}	
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		
		/**
		 * 查看关闭的材料
		 */
		@Override
		public String queryMatPurCloseInvInfo(Map<String, Object> entityMap) throws DataAccessException {
				
			List<Map<String, Object>> list = matPurMainMapper.queryMatPurCloseInvInfo(entityMap);
			return ChdJson.toJson(JsonListMapUtil.toListMapLower(list));
			
		}
		
	/**
	 * 采购数量来源查询
	 * @param entityMap
	 * @return  String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatPurAmountRela(Map<String, Object> entityMap) throws DataAccessException{
		
		List<Map<String,Object>> list = matPurMainMapper.queryMatPurAmountRela(entityMap);
		return ChdJson.toJsonLower(list);
	}
	
	/**
	 * 修改材料数量对应关系
	 * @param entityMap
	 * @return  
	 * @throws DataAccessException
	 */
	public String updateMatPurAmountRela(Map<String, Object> entityMap) throws DataAccessException{
		//来源分为：科室需求计划、仓库需求计划、科室申请即涉及3个对应关系表
		try {	
			List<Map<String, Object>> reqDeptRelaList = new ArrayList<Map<String, Object>>();  //科室需求计划对应关系
			List<Map<String, Object>> reqStoreRelaList = new ArrayList<Map<String, Object>>();  //仓库需求计划对应关系
			List<Map<String, Object>> appRelaList = new ArrayList<Map<String, Object>>();  //科室申请对应关系
			List<Map<String, Object>> purDetailList = new ArrayList<Map<String, Object>>();  //用于修改采购计划数量
			Map<String, Double> purAmountMap = new HashMap<String, Double>();  //记录明细总数量
			
			JSONArray json = JSONArray.parseArray((String)entityMap.get("relaData"));
			Iterator it = json.iterator();

			String group_id = entityMap.get("group_id").toString();
			String hos_id = entityMap.get("hos_id").toString(); 
			String copy_code = entityMap.get("copy_code").toString();
			String detail_id = "";
			Double pur_amount = null;
			
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				Map<String, Object> reqDeptRelaMap = new HashMap<String, Object>();
				Map<String, Object> reqStoreRelaMap = new HashMap<String, Object>();
				Map<String, Object> appRelaMap = new HashMap<String, Object>();
				
				if(jsonObj.getString("pur_detail_id") != null && !"".equals(jsonObj.getString("pur_detail_id"))){
					//处理明细数据
					detail_id = jsonObj.getInteger("pur_id") + "," + jsonObj.getString("pur_detail_id");
					pur_amount = jsonObj.getDouble("pur_amount");
					if(purAmountMap.containsKey(detail_id)){
						purAmountMap.put(detail_id, NumberUtil.add(purAmountMap.get(detail_id), pur_amount));
					}else{
						purAmountMap.put(detail_id, pur_amount);
					} 
					//处理对应关系
					if(jsonObj.getString("rela_type") != null){ 
						if("reqDept".equals(jsonObj.getString("rela_type"))){
							//科室需求计划
							reqDeptRelaMap.put("group_id", group_id);
							reqDeptRelaMap.put("hos_id", hos_id);
							reqDeptRelaMap.put("copy_code", copy_code);
							reqDeptRelaMap.put("pur_id", jsonObj.getInteger("pur_id"));
							reqDeptRelaMap.put("pur_detail_id", jsonObj.getInteger("pur_detail_id"));
							reqDeptRelaMap.put("req_id", jsonObj.getInteger("rela_id"));
							reqDeptRelaMap.put("req_detail_id", jsonObj.getInteger("rela_detail_id"));
							reqDeptRelaMap.put("req_amount", jsonObj.getDouble("rela_amount"));
							reqDeptRelaMap.put("pur_amount", pur_amount);
							
							reqDeptRelaList.add(reqDeptRelaMap);
						}else if("reqStore".equals(jsonObj.getString("rela_type"))){
							//仓库需求计划
							reqStoreRelaMap.put("group_id", group_id);
							reqStoreRelaMap.put("hos_id", hos_id);
							reqStoreRelaMap.put("copy_code", copy_code);
							reqStoreRelaMap.put("pur_id", jsonObj.getInteger("pur_id"));
							reqStoreRelaMap.put("pur_detail_id", jsonObj.getInteger("pur_detail_id"));
							reqStoreRelaMap.put("req_id", jsonObj.getInteger("rela_id"));
							reqStoreRelaMap.put("req_detail_id", jsonObj.getInteger("rela_detail_id"));
							reqStoreRelaMap.put("req_amount", jsonObj.getDouble("rela_amount"));
							reqStoreRelaMap.put("pur_amount", pur_amount); 
							
							reqStoreRelaList.add(reqStoreRelaMap);
						}else if("app".equals(jsonObj.getString("rela_type"))){
							//科室申请
							appRelaMap.put("group_id", group_id);
							appRelaMap.put("hos_id", hos_id);
							appRelaMap.put("copy_code", copy_code);
							appRelaMap.put("rela_type", 1);
							appRelaMap.put("rela_id", jsonObj.getInteger("pur_id"));
							appRelaMap.put("rela_detail_id", jsonObj.getInteger("pur_detail_id"));
							appRelaMap.put("apply_id", jsonObj.getInteger("rela_id"));
							appRelaMap.put("app_detail_id", jsonObj.getInteger("rela_detail_id"));
							appRelaMap.put("app_amount", jsonObj.getDouble("rela_amount"));
							appRelaMap.put("rela_amount", pur_amount);
							
							appRelaList.add(appRelaMap);
						}
					}
				}
			}
			
			for(String key : purAmountMap.keySet()){
				Map<String, Object> detailMap = new HashMap<String, Object>();
				detailMap.put("group_id", group_id);
				detailMap.put("hos_id", hos_id);
				detailMap.put("copy_code", copy_code);
				String keys[] = key.split(",");
				detailMap.put("pur_id", keys[0]);
				detailMap.put("pur_detail_id", keys[1]);
				detailMap.put("amount", purAmountMap.get(key));
				
				purDetailList.add(detailMap);
			}
			
			if(purDetailList.size() > 0){
				matPurMainMapper.updateBatchMatPurDetail(purDetailList);
			}

			if(reqDeptRelaList.size() > 0){
				matDeptPurRelaMapper.updateBatch(reqDeptRelaList);
			}

			if(reqStoreRelaList.size() > 0){
				matStorePurRelaMapper.updateBatch(reqStoreRelaList);
			}
			
			if(appRelaList.size() > 0){
				matApplyPurRelaMapper.updateMatApplyPurRelaBatch(appRelaList);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
		return "{\"msg\":\"操作成功\",\"state\":\"true\"}";
	}

	@Override
	public String queryMatMakeByDetailPrintTemlate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override 
	public String addMatPurPlanBySecuLimit(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			String make_date = entityMap.get("make_date").toString();		
			entityMap.put("year",make_date.substring(0, 4));		
			entityMap.put("month", make_date.substring(5, 7));	
			entityMap.put("day", make_date.substring(8, 10));
			entityMap.put("come_from", 5);//计划来源
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			//自动生成需求单号单据号
			entityMap.put("pur_code", getNextReq_code(entityMap));
			
			//计划类型
			entityMap.put("pur_type", 1);
			
			//是否定向
			entityMap.put("is_dir", "0");
			
			//采购单位、请购单位、付款单位：如果计划类型为自购计划，则隐藏，默认保存为当前医院ID。
			//if("1".equals(entityMap.get("pur_type").toString())){
				entityMap.put("pur_hos_id", SessionManager.getHosId());				
				entityMap.put("req_hos_id", SessionManager.getHosId());				
				entityMap.put("pay_hos_id", SessionManager.getHosId());	
			//}
			//如果计划类型是统购计划，则显示，并且必填；其中采购单位只能取当前单位的上级，请购单位取当前医院且不可编辑，付款单位取当前单位或者它的上级
			/*if("2".equals(entityMap.get("pur_type").toString())){			
				entityMap.put("pur_hos_id", entityMap.get("pur_hos_id"));
				entityMap.put("req_hos_id", entityMap.get("hos_id"));
				entityMap.put("pay_hos_id", entityMap.get("pay_hos_id"));
			}	*/
			entityMap.put("maker", SessionManager.getUserId());//制单人取当前用户id		
			entityMap.put("make_date", sdf.format(new Date()));		
			entityMap.put("is_collect", "0");
			
			String stateValue = MyConfig.getSysPara("04046");
			if("0".equals(stateValue)){
				entityMap.put("state", "2");
			}
			if("1".equals(stateValue)){
				entityMap.put("state", "1");
			}
			
			entityMap.put("pur_id", matPurMainMapper.queryMatPurNextId());
			
			//添加采购计划
			matPurMainMapper.add(entityMap);
			//根据安全库存添加明细
			matPurMainMapper.addMatPurPlanBySecuLimit(entityMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}
		return "{\"msg\":\"操作成功\",\"state\":\"true\"}";
	}
	
	/**
	 * 删除明细
	 */
	@Override
	public String deleteMatPurDetailInv(List<Map<String, Object>> entityList) throws DataAccessException {
		try {	
			//查看是否是科室申请生成的采购单
			List<Map<String,Object>> applyList = JsonListMapUtil.toListMapLower(matPurMainMapper.queryMatApplyDetailList(entityList));
			if(applyList.size() > 0){
				matPurMainMapper.deleteMatApplyDetailInv(entityList);
			}
			
			//查看是否是仓库需求计划生成的采购单
			List<Map<String,Object>> storeList =JsonListMapUtil.toListMapLower(matPurMainMapper.queryMatStoreReqPurRela(entityList));//查询科室需求采购计划对应关系
			if(storeList.size() > 0){//如果对应关系存在
				matPurMainMapper.updateStoreReqStateZero(storeList);//更新仓库需求计划is_close=0
				matStorePurRelaMapper.deleteBatch(storeList);//删除关系表
			}
			
			//查看是否是科室需求计划生成的采购单
			List<Map<String,Object>> deptRelaList =JsonListMapUtil.toListMapLower(matPurMainMapper.queryMatDeptReqPurRela(entityList));//查询科室需求采购计划对应关系
			if(deptRelaList.size()>0){
				matPurMainMapper.updateDeptReqStateZero(deptRelaList);//更新科室需求计划is_close=0
				matDeptPurRelaMapper.deleteBatch(deptRelaList);//删除关系表
			}
			
			matPurMainMapper.deleteMatPurDetail(entityList);//删除明细数据
			
			List<Map<String,Object>> purList =JsonListMapUtil.toListMapLower(matPurMainMapper.queryMatPurExistsNoDetails(entityList));
			if(purList.size()>0){
				matPurMainMapper.deleteMatPurMain(purList);//删除主表数据
			}
			
			
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		
		
	}
	
	
	@Override
	public String updateMatPurRelaTables(Map<String, Object> entityMap) throws DataAccessException {
		try {	
			//查看是否是科室申请生成的采购单
			List<Map<String,Object>> applyList = JsonListMapUtil.toListMapLower(matPurMainMapper.queryMatApplyDetailListById(entityMap));
			if(applyList.size() > 0){
				matPurMainMapper.deleteMatApplyDetailInv(applyList);
			}
			
			//查看是否是仓库需求计划生成的采购单
			List<Map<String,Object>> storeList =JsonListMapUtil.toListMapLower(matPurMainMapper.queryMatStoreReqPurRelaById(entityMap));//查询科室需求采购计划对应关系
			if(storeList.size() > 0){//如果对应关系存在
				matPurMainMapper.updateStoreReqStateZero(storeList);//更新仓库需求计划is_close=0
				matStorePurRelaMapper.deleteBatch(storeList);//删除关系表
			}
			
			//查看是否是科室需求计划生成的采购单
			List<Map<String,Object>> deptRelaList =JsonListMapUtil.toListMapLower(matPurMainMapper.queryMatDeptReqPurRelaById(entityMap));//查询科室需求采购计划对应关系
			if(deptRelaList.size()>0){
				matPurMainMapper.updateDeptReqStateZero(deptRelaList);//更新科室需求计划is_close=0
				matDeptPurRelaMapper.deleteBatch(deptRelaList);//删除关系表
			}
			
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		
	}
	
	
	public Map<String, Object> queryMatPurMakeByPrintTemlateNewPrint(Map<String, Object> mapVo){
		if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		Map<String, Object> rsltMap=new HashMap<String, Object>();
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		MatPurMainMapper matPurMainMapper=(MatPurMainMapper) context.getBean("matPurMainMapper");
		//查询凭证主表 
		List<Map<String,Object>> mainList=matPurMainMapper.queryMatMakePrintTemlateByMainBatch(mapVo);
		//查询从表数据
		String purDetailIds=(String) mapVo.get("purDetailIds");
		String[] purDetailIdArr = purDetailIds.split(",");
		mapVo.put("purDetailIds", purDetailIdArr);
		List<Map<String, Object>> detaiList=matPurMainMapper.queryPurDetailForTemplatePrint(mapVo);
		rsltMap.put("main", mainList);
		rsltMap.put("detail", detaiList);
		return rsltMap;
	}
	
	
	/**
	 * @Description 
	 * 采购计划修改 查询材料近三个月,六个月入出库数量
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 * @throws ParseException 
	*/
	@Override
	public String queryMatInvRecentInOutAmount(Map<String, Object> entityMap) throws DataAccessException, ParseException {	
		String end_date=entityMap.get("end_date").toString();
		entityMap.put("three_start_date", DateUtil.calculate(end_date, Calendar.MONTH, -3, "yyyy-MM-dd"));
		entityMap.put("six_start_date", DateUtil.calculate(end_date, Calendar.MONTH, -6, "yyyy-MM-dd"));
		List<?> list = matPurMainMapper.queryMatInvRecentInOutAmount(entityMap);			
		return ChdJson.toJson(list);
	}
}
