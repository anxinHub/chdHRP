/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.serviceImpl.requrie.collect;

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
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.dao.base.MedNoManageMapper;
import com.chd.hrp.med.dao.requrie.MedRequireDetailMapper;
import com.chd.hrp.med.dao.requrie.MedRequireMainMapper;
import com.chd.hrp.med.dao.requrie.collect.MedRequireCollectMapper;
import com.chd.hrp.med.entity.MedRequireMain;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.requrie.collect.MedRequireCollectService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 
 * 科室购置计划汇总
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Transactional
@Service("medRequireCollectService")
public class MedRequireCollectServiceImpl implements MedRequireCollectService {

	private static Logger logger = Logger.getLogger(MedRequireCollectServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "medRequireMainMapper")
	private final MedRequireMainMapper medRequireMainMapper = null;

	@Resource(name = "medRequireDetailMapper")
	private final MedRequireDetailMapper medRequireDetailMapper = null;
	
	@Resource(name = "medNoManageMapper")
	private final MedNoManageMapper medNoManageMapper = null;
	
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;
	
	@Resource(name = "medRequireCollectMapper")
	private final MedRequireCollectMapper medRequireCollectMapper = null;
	
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	// 返回用用于保存的默认值
	public Map<String, Object> defaultValue(Map<String, Object> mapVo) {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", mapVo.get("group_id"));
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", mapVo.get("hos_id"));
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", mapVo.get("copy_code"));
		}

		if (mapVo.get("brif") == null) {
			mapVo.put("brif", "");
		}

		if (mapVo.get("dept_no") == null) {
			mapVo.put("dept_no", "");
		}

		if (mapVo.get("dept_id") == null) {
			mapVo.put("dept_id", "");
		}

		if (mapVo.get("stock_no") == null) {
			mapVo.put("stock_no", "");
		}

		if (mapVo.get("stock_id") == null) {
			mapVo.put("stock_id", "");
		}

		if (mapVo.get("make_date") == null) {
			mapVo.put("make_date", "");
		}

		if (mapVo.get("maker") == null) {
			mapVo.put("maker", mapVo.get("user_id"));
		}

		if (mapVo.get("checker") == null) {
			mapVo.put("checker", "");
		}

		if (mapVo.get("check_date") == null) {
			mapVo.put("check_date", "");
		}

		if (mapVo.get("state") == null) {
			mapVo.put("state", "1");
		}

		if (mapVo.get("req_type") == null) {
			mapVo.put("req_type", "1");
		}

		if (mapVo.get("is_collect") == null) {
			mapVo.put("is_collect", "0");
		}

		if (mapVo.get("is_submit") == null) {
			mapVo.put("is_submit", "0");
		}

		if (mapVo.get("is_return") == null) {
			mapVo.put("is_return", "0");
		}

		if (mapVo.get("return_reason") == null) {
			mapVo.put("return_reason", "");
		}

		if (mapVo.get("other_inv") == null) {
			mapVo.put("other_inv", "");
		}

		return mapVo;
	}
		
	// 返回用用于保存的默认值
	public Map<String, Object> defaultDetailValue() {
		Map<String, Object> detailMap = new HashMap<String, Object>();
		
		detailMap.put("group_id", 0);
		detailMap.put("hos_id", 0);
		detailMap.put("copy_code", "");
		detailMap.put("amount", "0");
		detailMap.put("price", "0");
		detailMap.put("pack_code", "");
		detailMap.put("num", "0");
		detailMap.put("num_exchange", "0");
		detailMap.put("rdate", "");
		
		detailMap.put("sup_no", "");
		detailMap.put("sup_id", "");
		detailMap.put("memo", "");
		
		return detailMap;
	}
	
	//验证
	public boolean validateJSON(String str) {
		if (str != null && str != "null" && str != "" && str != "0") {
			return true;
		}
		return false;
	}
		
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		try {
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
		}
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		try {
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 update\"}";
		}
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatch\"}";
		}
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {	
		try {
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedRequireMain\"}";
		}
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityList)	throws DataAccessException {
		try {
			medRequireMainMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		
		// 获取对象MED_REQUIRE_MAIN
		MedRequireMain medRequireMain = queryByCode(entityMap);
		if (medRequireMain != null) {
			medRequireMainMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		try {
			medRequireMainMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedRequireMain\"}";
		}

	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return medRequireMainMapper.queryByCode(entityMap);
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
	
		return medRequireMainMapper.queryByCode(entityMap);
	}

	/**
	 * 科室购置计划汇总--查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryCollect(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		List<?> list = null;
		if (sysPage.getTotal() == -1) {
			list = (List<?>) medRequireCollectMapper.queryCollect(entityMap);
			return ChdJson.toJson(list);

		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			list = (List<?>) medRequireCollectMapper.queryCollect(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * 科室购置计划汇总--明细查看
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryCollectDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		//System.out.println(ChdJson.toJson(medRequireCollectMapper.queryCollectDetail(entityMap)));
		
		return ChdJson.toJson(medRequireCollectMapper.queryCollectDetail(entityMap));
		/*SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<?> list = (List<?>) medRequireCollectMapper.queryCollectDetail(entityMap);
			return ChdJson.toJson(list);

		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			List<?> list = (List<?>) medRequireCollectMapper.queryCollectDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}*/
	}

	/**
	 * 汇总明细 --查看计划单
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryCollectDetailPlan(Map<String, Object> entityMap) throws DataAccessException {
		List<MedRequireMain> list = (List<MedRequireMain>) medRequireDetailMapper.query(entityMap);
		return ChdJson.toJson(list);
	}
	
	/**
	 * 保存&提交   汇总单据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String addCollectNotDir(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//1、组装主表数据
			//截取期间
			String[] date = entityMap.get("make_date").toString().split("-");
			entityMap.put("year", date[0]);
			entityMap.put("month", date[1]);
			
			//汇总生成审核状态的汇总需求计划单（其中 ：DEPT_ID=当前库房；STOCK_ID=当前库房；STATE=2 ；IS_COLLECT=1)
			//用于保存的主表数据
			entityMap = defaultValue(entityMap);
			//自动生成需求单号单据号
			entityMap.put("req_code", getNextReq_code(entityMap));
			//获得自增ID
			entityMap.put("req_id", medRequireMainMapper.getNextReqId(entityMap));
			entityMap.put("state", "2");
			entityMap.put("dept_id", entityMap.get("dept_id"));
			entityMap.put("dept_no", entityMap.get("dept_no"));
			entityMap.put("stock_id", entityMap.get("store_id"));
			entityMap.put("stock_no", entityMap.get("store_no"));
			entityMap.put("is_dir", "0");
			entityMap.put("is_collect", "1");
			entityMap.put("is_submit", "1");
			//System.out.println("******* make_date:"+entityMap.get("make_date"));
			if(ChdJson.validateJSON(String.valueOf(entityMap.get("make_date")))){
				entityMap.put("rdate", DateUtil.stringToDate( entityMap.get("make_date").toString(), "yyyy-MM-dd"));//需求日期
				entityMap.put("make_date", DateUtil.stringToDate( entityMap.get("make_date").toString(), "yyyy-MM-dd"));//制单日期
				entityMap.put("check_date", new Date());//审核日期
			}
			entityMap.put("maker", entityMap.get("user_id"));
			entityMap.put("checker", entityMap.get("user_id"));
			System.out.println("entityMap="+entityMap);
			//2、组装明细数据和对应关系数据
			List<Map<String, Object>> detailList= new ArrayList<Map<String,Object>>();//明细List
			List<Map<String, Object>> relaList= new ArrayList<Map<String,Object>>();//对应关系List
			JSONArray json = JSONArray.parseArray((String)entityMap.get("allData"));
			System.out.println("allData="+json);
			
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				Map<String, Object> detailMap = defaultDetailValue();//明细Map
				
				
				if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
					detailMap.put("group_id", entityMap.get("group_id"));
					detailMap.put("hos_id", entityMap.get("hos_id"));
					detailMap.put("copy_code", entityMap.get("copy_code"));
					detailMap.put("req_id", entityMap.get("req_id"));
					detailMap.put("req_code", entityMap.get("req_code"));
					detailMap.put("inv_id", jsonObj.get("inv_id"));
					detailMap.put("inv_no", jsonObj.get("inv_no"));
					
					if (validateJSON(String.valueOf(jsonObj.get("price")))) {//计划单价
						detailMap.put("price", jsonObj.get("price"));
					}
					if (validateJSON(String.valueOf(jsonObj.get("exec_amount")))) {//数量
						detailMap.put("amount", jsonObj.get("exec_amount"));
					}
					if (validateJSON(String.valueOf(jsonObj.get("pack_code")))) {//包装单位
						detailMap.put("pack_code", jsonObj.get("pack_code"));
					}
					if(ChdJson.validateJSON(String.valueOf(jsonObj.get("num_exchange"))) && !"0".equals(jsonObj.get("num_exchange").toString())){
						detailMap.put("num_exchange", jsonObj.get("num_exchange"));
						detailMap.put("num", Float.parseFloat(detailMap.get("amount").toString())/Float.parseFloat(detailMap.get("num_exchange").toString()));
					}else{
						detailMap.put("num_exchange", "0");
						detailMap.put("num", "0");
					}
					/*if (validateJSON(String.valueOf(jsonObj.get("num_exchange")))) {//包装换算率
						detailMap.put("num_exchange", jsonObj.get("num_exchange"));
						//包装数量 = 数量/包装换算率
						detailMap.put("num", Integer.parseInt(jsonObj.get("exec_amount").toString())/Integer.parseInt(jsonObj.get("num_exchange").toString()));
					}*/

					
					if (validateJSON(String.valueOf(jsonObj.get("sup_no")))) {//供应商变更ID
						detailMap.put("sup_no", jsonObj.get("sup_no"));
					}
					if (validateJSON(String.valueOf(jsonObj.get("sup_id")))) {//供应商ID
						detailMap.put("sup_id", jsonObj.get("sup_id"));
					}
					if (validateJSON(String.valueOf(jsonObj.get("memo")))) {//备注
						detailMap.put("memo", jsonObj.get("memo"));
					}
					
					detailList.add(detailMap);
					
					//组装对应关系表
					if (validateJSON(String.valueOf(jsonObj.get("detail_data")))) {
						
						for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("detail_data").toString())){
							//没材料ID视为空行
							if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
								Map<String,Object> relaMap = new HashMap<String,Object>();//对应关系map
								relaMap.put("group_id", entityMap.get("group_id"));
								relaMap.put("hos_id", entityMap.get("hos_id"));
								relaMap.put("copy_code", entityMap.get("copy_code"));
								relaMap.put("total_id", entityMap.get("req_id"));
								relaMap.put("req_id", m.get("req_id"));
								relaMap.put("inv_id", m.get("inv_id"));
								
								if (validateJSON(String.valueOf(m.get("exec_amount")))) {
									relaMap.put("exec_amount", m.get("exec_amount").toString());
								}
								if (validateJSON(String.valueOf(m.get("req_amount")))) {
									relaMap.put("req_amount", m.get("req_amount").toString());
								}
								
								relaList.add(relaMap);
							}
						}
					}
					
				}
			}
			
			//3、插入主表
			medRequireMainMapper.add(entityMap);
			//4、插入明细表
			if(detailList.size()> 0 ){
				medRequireDetailMapper.addBatch(detailList);
			}
			//5、插入对应关系表
			if(relaList.size()>0){
				medRequireCollectMapper.addBatchReal(relaList);
				//更新单据状态为 state = 3
				medRequireCollectMapper.updateBatchCollect(relaList);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+entityMap.get("req_code").toString()+","+"\"}";
			}
			
			return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedRequireMain\"}";
		}
	}
	
	/**
	 * 定向汇总
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String addCollect(Map<String, Object> entityMap) throws DataAccessException {
		
		try{	
				//截取期间
				String[] date = entityMap.get("make_date").toString().split("-");
				entityMap.put("year", date[0]);
				entityMap.put("month", date[1]);
				
				//汇总生成审核状态的汇总需求计划单（其中 ：DEPT_ID=当前库房；STOCK_ID=当前库房；STATE=2 ；IS_COLLECT=1)
				//用于保存的主表数据
				entityMap = defaultValue(entityMap);
				//自动生成需求单号单据号
				entityMap.put("req_code", getNextReq_code(entityMap));
				//获得自增ID
				entityMap.put("req_id", medRequireMainMapper.getNextReqId(entityMap));
				entityMap.put("state", "2");
				entityMap.put("dept_id", entityMap.get("dept_id"));
				entityMap.put("dept_no", entityMap.get("dept_no"));
				entityMap.put("stock_id", entityMap.get("store_id"));
				entityMap.put("stock_no", entityMap.get("store_no"));
				entityMap.put("is_dir", entityMap.get("is_dir"));
				entityMap.put("is_collect", "1");
				entityMap.put("is_submit", "1");
				//System.out.println("******* make_date:"+entityMap.get("make_date"));
				if(ChdJson.validateJSON(String.valueOf(entityMap.get("make_date")))){
					entityMap.put("rdate", DateUtil.stringToDate( entityMap.get("make_date").toString(), "yyyy-MM-dd"));//需求日期
					entityMap.put("make_date", DateUtil.stringToDate( entityMap.get("make_date").toString(), "yyyy-MM-dd"));//制单日期
					entityMap.put("check_date", new Date());//审核日期
				}
				entityMap.put("maker", entityMap.get("user_id"));
				entityMap.put("checker", entityMap.get("user_id"));
				
				
				//组装明细数据
				List<Map<String, Object>> detailLists= medRequireCollectMapper.queryMedCollectDetail(entityMap);
				List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
				for (Map<String, Object> m : detailLists){  
					Map<String, Object> detailsMap = defaultDetailValue();
					
					detailsMap.put("group_id", m.get("group_id"));
					detailsMap.put("hos_id", m.get("hos_id"));
					detailsMap.put("copy_code", m.get("copy_code"));
					detailsMap.put("inv_id", m.get("inv_id"));
					detailsMap.put("inv_no", m.get("inv_no"));
					detailsMap.put("req_id", entityMap.get("req_id"));
					detailsMap.put("req_code", entityMap.get("req_code"));
					detailsMap.put("price", m.get("price"));
					detailsMap.put("amount", m.get("amount"));
					
					if(ChdJson.validateJSON(String.valueOf(m.get("pack_code")))){
						detailsMap.put("pack_code", m.get("pack_code"));
					}
					if(ChdJson.validateJSON(String.valueOf(m.get("sup_id")))){
						detailsMap.put("sup_id", m.get("sup_id"));
					}
					if(ChdJson.validateJSON(String.valueOf(m.get("sup_no")))){
						detailsMap.put("sup_no", m.get("sup_no"));
					}
					if(ChdJson.validateJSON(String.valueOf(m.get("num_exchange"))) && !"0".equals(m.get("num_exchange").toString())){
						detailsMap.put("num_exchange", m.get("num_exchange"));
						detailsMap.put("num", Float.parseFloat(m.get("amount").toString())/Float.parseFloat(m.get("num_exchange").toString()) );
					}else{
						detailsMap.put("num_exchange", "0");
						detailsMap.put("num", "0");
					}
					
					addList.add(detailsMap);
				}
				
				
				//插入对应关系
				List<Map<String, Object>> detailList= new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> detailList1= new ArrayList<Map<String,Object>>();//对应关系
				//查询数据
				detailList = medRequireDetailMapper.queryDetail(entityMap);
				for (Map<String, Object> m : detailList){  
					Map<String, Object> detailMap = defaultDetailValue();
					
					for (String k : m.keySet()){ 
						if(k.equals("req_id")){
							detailMap.put("req_id", m.get("req_id"));
						}
						if(k.equals("inv_id")){
							detailMap.put("inv_id", m.get("inv_id"));
						}
						if(k.equals("amount")){
							detailMap.put("req_amount", m.get("amount"));
							detailMap.put("exec_amount", m.get("amount"));
						}
					}  
					detailMap.put("total_id", entityMap.get("req_id"));
					detailMap.put("hos_id", entityMap.get("hos_id"));
					detailMap.put("group_id", entityMap.get("group_id"));
					detailMap.put("copy_code", entityMap.get("copy_code"));
					detailList1.add(detailMap);
				} 
				ChdJson.toJson("****** addList:"+addList);
				//2、插入主表
				medRequireMainMapper.add(entityMap);
				//3、插入明细表
				if(addList.size()> 0 ){
					medRequireDetailMapper.addBatch(addList);
				}
				//4、插入对应关系表
				if(detailList1.size()>0){
					medRequireCollectMapper.addBatchReal(detailList1);
					//更新单据状态为 state = 3
					medRequireCollectMapper.updateBatchCollect(detailList1);
					return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+entityMap.get("req_code").toString()+","+"\"}";
				}
				return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";
				
						
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addCollect\"}";
		}
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
		//map.put("store_id", entityMap.get("store_id"));
		//获取仓库别名store_alias
		//String store_alias =  medCommonMapper.queryStoreAliasById(map);
		map.put("table_code", "MED_REQUIRE_MAIN");
		map.put("year", entityMap.get("year"));
		map.put("month", entityMap.get("month"));
		map.put("prefixe", "XQ");
		map.put("store_alias", "");
		map.put("bus_type", "");
		//判断是否存在该业务流水码
		int flag = medNoManageMapper.queryIsExists(map);
		String max_no = "";
		if(flag == 0){
			//如不存在则流水码为1，并插入流水码表中
			max_no = "1";
			map.put("max_no", 1);
			medNoManageMapper.add(map);
		}else{
			//更新该业务流水码+1
			medNoManageMapper.updateMaxNo(map);
			//取出该业务更新后的流水码
			max_no = medNoManageMapper.queryMaxCode(map);
		}		
		//补流水码前缀0
		for (int i = max_no.length() ; i < 5; i++) {
			max_no = "0" + max_no;
		}
		//组装流水码
		String req_code = "";
		req_code = "XQ" + entityMap.get("year") + entityMap.get("month") + max_no;		
		return req_code;
	}
	
	/**
	 * 查看明细
	 */
	@Override
	public String queryConfirmDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");		
		if (sysPage.getTotal()==-1){			
			List<?> list = (List<?>) medRequireDetailMapper.queryDetailByCode(entityMap);			
			return ChdJson.toJson(list);			
		}else{			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());			
			List<?> list = (List<?>) medRequireDetailMapper.queryDetailByCode(entityMap, rowBounds);			
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJson(list, page.getTotal());			
		}
	}
	
	/**
	 * 定向汇总查询
	 */
	@Override
	public String queryDirCollect(Map<String, Object> entityMap) throws DataAccessException {		
		return ChdJson.toJson(medRequireCollectMapper.queryDirCollect(entityMap));	
	}
	
	/**
	 * 根据系统参数查询未审核科室需求 汇总查询
	 */
	@Override
	public String queryCollectNotCheck(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		List<?> list = null;
		
		if (sysPage.getTotal() == -1) {
			
			list = (List<?>) medRequireCollectMapper.queryCollectNotCheck(entityMap);
			
			return ChdJson.toJson(list);

		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			
			list = (List<?>) medRequireCollectMapper.queryCollectNotCheck(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 根据系统参数查询未审核科室需求 定向汇总查询
	 */
	@Override
	public String queryDirCollectNotCheck(Map<String, Object> entityMap) throws DataAccessException {
		
		return ChdJson.toJson(medRequireCollectMapper.queryDirCollectNotCheck(entityMap));
	}
}
