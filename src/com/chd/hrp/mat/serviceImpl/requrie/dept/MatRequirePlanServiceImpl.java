/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.serviceImpl.requrie.dept;

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
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.mat.dao.base.MatApplyOutRelaMapper;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.purchase.make.MatPurMainMapper;
import com.chd.hrp.mat.dao.requrie.MatReqStoreRelaMapper;
import com.chd.hrp.mat.dao.requrie.MatRequireDetailMapper;
import com.chd.hrp.mat.dao.requrie.MatRequireMainMapper;
import com.chd.hrp.mat.dao.requrie.dept.MatRequirePlanMapper;
import com.chd.hrp.mat.entity.MatReqStoreRela;
import com.chd.hrp.mat.entity.MatRequireDetail;
import com.chd.hrp.mat.entity.MatRequireMain;
import com.chd.hrp.mat.service.requrie.dept.MatRequirePlanService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: MAT_REQUIRE_MAIN
 * @Table: MAT_REQUIRE_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Transactional
@Service("matRequirePlanService")
public class MatRequirePlanServiceImpl implements MatRequirePlanService {

	private static Logger logger = Logger.getLogger(MatRequirePlanServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "matRequireMainMapper")
	private final MatRequireMainMapper matRequireMainMapper = null;
	@Resource(name = "matRequireDetailMapper")
	private final MatRequireDetailMapper matRequireDetailMapper = null;
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "matRequirePlanMapper")
	private final MatRequirePlanMapper matRequirePlanMapper = null;
	@Resource(name = "matApplyOutRelaMapper")
	private final MatApplyOutRelaMapper matApplyOutRelaMapper = null;
	@Resource(name = "matReqStoreRelaMapper")
	private final MatReqStoreRelaMapper matReqStoreRelaMapper = null;
	
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
		
		if (mapVo.get("rdate") == null) {
			mapVo.put("rdate", "");
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

		if (mapVo.get("come_from") == null) {
			mapVo.put("come_from","1");
		}
		
		if (mapVo.get("is_dir") == null) {
			mapVo.put("is_dir", "0");
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
		
		detailMap.put("inv_no", 0);
		detailMap.put("price", "0");
		detailMap.put("amount", "0");
		detailMap.put("pack_code", "");
		detailMap.put("num", "0");
		detailMap.put("num_exchange", "0");
		detailMap.put("sup_no", "0");
		detailMap.put("sup_id", "0");
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
	
	/**
	 * @Description 添加
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//用于保存的主表数据
			entityMap = defaultValue(entityMap);
			
			if(ChdJson.validateJSON(String.valueOf(entityMap.get("make_date")))){
				entityMap.put("make_date", DateUtil.stringToDate(entityMap.get("make_date").toString(), "yyyy-MM-dd"));
			}
			if(ChdJson.validateJSON(String.valueOf(entityMap.get("rdate")))){
				entityMap.put("rdate", DateUtil.stringToDate(entityMap.get("rdate").toString(), "yyyy-MM-dd"));
			}
			
			
			//自动生成需求单号单据号
			entityMap.put("req_code", getNextReq_code(entityMap));
			//获得自增ID
			entityMap.put("req_id", matRequireMainMapper.getNextReqId(entityMap));
			
			//插入明细数据
			List<Map<String, Object>> detailList= new ArrayList<Map<String,Object>>();
			JSONArray json = JSONArray.parseArray((String)entityMap.get("allData"));
			Iterator it = json.iterator();
			//try{
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					Map<String, Object> detailMap = defaultDetailValue();
					
					if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("req_id", entityMap.get("req_id"));//计划单ID
						detailMap.put("req_detail_id", matRequireDetailMapper.getNextDetailReqId());
						detailMap.put("req_code", entityMap.get("req_code"));//计划单code
						detailMap.put("inv_id", jsonObj.get("inv_id"));//材料ID
						detailMap.put("inv_no", jsonObj.get("inv_no"));//材料变更ID
						
						if (validateJSON(String.valueOf(jsonObj.get("amount")))) {
							detailMap.put("amount", jsonObj.get("amount"));
						}//数量
						if (validateJSON(String.valueOf(jsonObj.get("price")))) {
							detailMap.put("price", jsonObj.get("price"));
						}//计划单价
						if (validateJSON(String.valueOf(jsonObj.get("pack_code")))) {
							detailMap.put("pack_code", jsonObj.get("pack_code"));
						}//包装单位
						if (validateJSON(String.valueOf(jsonObj.get("num")))) {
							detailMap.put("num", jsonObj.get("num"));
						}//包装数量
						if (validateJSON(String.valueOf(jsonObj.get("num_exchange")))) {
							detailMap.put("num_exchange", jsonObj.get("num_exchange"));
						}//包装换算量
						
						if(validateJSON(String.valueOf(jsonObj.get("sup_id")))){//如果在下拉列表修改了供应商 值为sup_id+","+sup_no
							if(jsonObj.get("sup_id").toString().contains(",")){
								detailMap.put("sup_id",jsonObj.get("sup_id").toString().split(",")[0]);
								detailMap.put("sup_no",jsonObj.get("sup_id").toString().split(",")[1]);						
							}else{
								//没有选供应商 格式为  ","
								if("".equals(jsonObj.get("sup_id").toString().split(",")[0])){
									detailMap.put("sup_id","");
									detailMap.put("sup_no","");
								}else{
									detailMap.put("sup_id",jsonObj.get("sup_id").toString());
									detailMap.put("sup_no",jsonObj.get("sup_no").toString());
								}
								
							}
						}
						
						if (validateJSON(String.valueOf(jsonObj.get("memo")))) {
							detailMap.put("memo", jsonObj.get("memo"));
						}//备注
						
						detailList.add(detailMap);
					}
				}
			/*}catch (DataAccessException e) {
				return "{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}";
			}*/
				
			//插入主表
			matRequireMainMapper.add(entityMap);
			//插入明细
			if(detailList.size() > 0){
				matRequireDetailMapper.addBatch(detailList);
			}
			return "{\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("req_id").toString()+","+
			entityMap.get("req_code").toString()
			+"\"}";	
			//return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";		
		}catch (DataAccessException e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatRequireMain\"}";
		}	
	}

	/**
	 * @Description 批量添加MAT_REQUIRE_MAIN<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
		}
	}

	/**
	 * @Description 更新MAT_REQUIRE_MAIN<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {	
		try {
			//修改入库主表数据
			if(ChdJson.validateJSON(String.valueOf(entityMap.get("make_date")))){
				entityMap.put("make_date", DateUtil.stringToDate(entityMap.get("make_date").toString(), "yyyy-MM-dd"));
			}
			if(ChdJson.validateJSON(String.valueOf(entityMap.get("rdate")))){
				entityMap.put("rdate", DateUtil.stringToDate(entityMap.get("rdate").toString(), "yyyy-MM-dd"));
			}
			
			int state = matRequireMainMapper.update(entityMap);
			if(state > 0){
				//更新明细表
				List<Map<String, Object>> delUpdateList= new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
				StringBuffer req_detail_ids = new StringBuffer();
				JSONArray json = JSONArray.parseArray((String)entityMap.get("allData"));
				Iterator it = json.iterator();
				
					while (it.hasNext()) {
						JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
						if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
							Map<String, Object> detailMap = defaultDetailValue();
							
							detailMap.put("group_id", entityMap.get("group_id"));
							detailMap.put("hos_id", entityMap.get("hos_id"));
							detailMap.put("copy_code", entityMap.get("copy_code"));
							detailMap.put("req_id", entityMap.get("req_id"));//计划单ID
							detailMap.put("req_code", entityMap.get("req_code"));//计划s单ID
							detailMap.put("inv_id", jsonObj.get("inv_id"));//材料ID
							detailMap.put("inv_no", jsonObj.get("inv_no"));//材料变更ID
							
							if (validateJSON(String.valueOf(jsonObj.get("amount")))) {
								detailMap.put("amount", jsonObj.get("amount"));
							}//数量
							if (validateJSON(String.valueOf(jsonObj.get("price")))) {
								detailMap.put("price", jsonObj.get("price"));
							}//计划单价
							if (validateJSON(String.valueOf(jsonObj.get("pack_code")))) {
								detailMap.put("pack_code", jsonObj.get("pack_code"));
							}//包装单位
							if (validateJSON(String.valueOf(jsonObj.get("num")))) {
								detailMap.put("num", jsonObj.get("num"));
							}//包装数量
							if (validateJSON(String.valueOf(jsonObj.get("num_exchange")))) {
								detailMap.put("num_exchange", jsonObj.get("num_exchange"));
							}//包装换算量
							
							if(validateJSON(String.valueOf(jsonObj.get("sup_id")))){//如果在下拉列表修改了供应商 值为sup_id+","+sup_no
								if(jsonObj.get("sup_id").toString().contains(",")){
									detailMap.put("sup_id",jsonObj.get("sup_id").toString().split(",")[0]);
									detailMap.put("sup_no",jsonObj.get("sup_id").toString().split(",")[1]);						
								}else{
									//没有选供应商 格式为  ","
									if("".equals(jsonObj.get("sup_id").toString().split(",")[0])){
										detailMap.put("sup_id","");
										detailMap.put("sup_no","");
									}else{
										detailMap.put("sup_id",jsonObj.get("sup_id").toString());
										detailMap.put("sup_no",jsonObj.get("sup_no").toString());
									}
									
								}
							}
							if (validateJSON(String.valueOf(jsonObj.get("memo")))) {
								detailMap.put("memo", jsonObj.get("memo"));
							}//备注
							
							//明细表ID
							if(jsonObj.get("req_detail_id") == null || "".equals(jsonObj.get("req_detail_id"))){
								detailMap.put("req_detail_id", matRequireDetailMapper.getNextDetailReqId());
								addList.add(detailMap);
							}else{
								req_detail_ids.append(jsonObj.get("req_detail_id").toString()).append(",");
								detailMap.put("req_detail_id", jsonObj.get("req_detail_id"));
								delUpdateList.add(detailMap);
							}
							
						}
						
					}
				
				if(req_detail_ids.length() > 0){
				
					Map<String,Object> deleteMap = new HashMap<String,Object>();
					deleteMap.put("group_id", entityMap.get("group_id"));
					deleteMap.put("hos_id", entityMap.get("hos_id"));
					deleteMap.put("copy_code", entityMap.get("copy_code"));
					deleteMap.put("req_id", entityMap.get("req_id"));//主表ID
					deleteMap.put("req_detail_id", req_detail_ids.substring(0,req_detail_ids.length()-1));//明细IDS
					//System.out.println("*********** req_detail_ids: "+req_detail_ids);
					//首先删除明细表中前台删除的明细数据
					matRequireDetailMapper.delete(deleteMap);
					
				}
				//更新明细表中数据
				if(delUpdateList.size() > 0){
					matRequireDetailMapper.updateBatch(delUpdateList);
				}
				//插入明细表中没有的数据
				if(addList.size()>0){
					matRequireDetailMapper.addBatch(addList);
				} 
				
			}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatRequireMain\"}";
		}
	}

	/**
	 * @Description 批量更新MAT_REQUIRE_MAIN<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			matRequireMainMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatRequireMain\"}";
		}
	}

	/**
	 * @Description 删除MAT_REQUIRE_MAIN<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		try {
			int state = matRequireMainMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatRequireMain\"}";
		}
	}

	/**
	 * @Description 批量删除MAT_REQUIRE_MAIN<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList)	throws DataAccessException {
		try {
			//删除申请单与出库单对应关系
			matApplyOutRelaMapper.deleteMatApplyOutRelaBatch(entityList);
			matRequireMainMapper.deleteBatch(entityList);
			matRequireDetailMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatRequireMain\"}";
		}
	}

	
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			// 获取对象MAT_REQUIRE_MAIN
			MatRequireMain matRequireMain = queryByCode(entityMap);
			
			if (matRequireMain != null) {
				
				matRequireMainMapper.update(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
			}
			matRequireMainMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatRequireMain\"}";
		}
	}
	
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}
	
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return (T) matRequirePlanMapper.queryByUniqueness(entityMap);
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return matRequireMainMapper.queryByCode(entityMap);
	}

	
	/**
	 * 科室需求计划编制--查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryPlan(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<?> list = matRequirePlanMapper.queryPlan(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			List<?> list = matRequirePlanMapper.queryPlan(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 编制页面   中止计划
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String updateAbortMatDeptPlan(List<Map<String, Object>> detailList) 	throws DataAccessException {
		
		try {
			int state = matRequirePlanMapper.updateAbortMatDeptPlan(detailList);
			return "{\"msg\":\"中止计划成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"中止计划失败 数据库异常 请联系管理员! 错误编码  updateAbortMatDeptPlan\"}";

		}
	}
	/**
	 * 编制页面  提交单据
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String updateSubmitMatDeptPlan(List<Map<String, Object>> detailList) throws DataAccessException {
		try {
			matRequirePlanMapper.updateSubmitMatDeptPlan(detailList);
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"提交失败 数据库异常 请联系管理员! 错误编码  updateSubmitMatDeptPlan\"}";

		}
	}
	
	/**
	 * 取消提交
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String updateUnSubmitMatDeptPlan(List<Map<String, Object>> detailList) throws DataAccessException {
		try {
			//判断是否已生成仓库需求计划
			int is_exists = matReqStoreRelaMapper.existsByDeptList(detailList);
			if(is_exists > 0){
				return "{\"error\":\"存在已生成仓库需求计划的单据，不能取消提交.\",\"state\":\"false\"}";
			}
			matRequirePlanMapper.updateUnSubmitMatDeptPlan(detailList);
			return "{\"msg\":\"取消提交成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  updateCancleSubmitMatDeptPlan\"}";

		}
	}
	/**
	 * 删除购置计划
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String deleteMatDeptPlan(List<Map<String, Object>> detailList) throws DataAccessException {
		try {
			//删除明细表数据
			matRequireDetailMapper.deleteBatch(detailList);
			//删除主表信息
			matRequireMainMapper.deleteBatch(detailList);
			//删除对应关系表
			List<Map<String,Object>> relaList = JsonListMapUtil.toListMapLower(matRequireMainMapper.queryMatApplyRelaReq(detailList));
			if(relaList.size() > 0){
				matRequireMainMapper.deleteMatApplyRelaReq(relaList);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteMatDeptPlan\"}";

		}
	}
	/**
	 * 复制 购置计划
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String copyMatDeptPlan(List<Map<String, Object>> entityList) throws DataAccessException {
		try{
			Map<String,Object> mainMap = null; //被复制主表Map
			Map<String,Object> detailMap = null; //被复制明细表map
			
			Map<String,Object> mapVo = null;//查询对象
			
			List<Map<String,Object>> mainList = new ArrayList<Map<String,Object>>(); //主表集合
			List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>(); //从表集合
			
			for(Map<String, Object> main : entityList){
				
				mapVo = new HashMap<String,Object>();
				
				mapVo.put("group_id", main.get("group_id"));
				mapVo.put("hos_id", main.get("hos_id"));
				mapVo.put("copy_code", main.get("copy_code"));
				mapVo.put("req_id", main.get("req_id"));
				mapVo.put("req_code", main.get("req_code"));
				mapVo.put("year", main.get("year"));
				mapVo.put("month", main.get("month"));
				mapVo.put("day", main.get("day"));
				mapVo.put("make_date", main.get("make_date"));
				mainMap = matRequireMainMapper.queryByCode(mapVo);
				
				//修改摘要
				mainMap.put("brif", "复制 "+mainMap.get("req_code").toString()+" 计划单！");
				//修改入库单号
				mainMap.put("req_code", getNextReq_code(mapVo));
				//查询最大的单号ID
				mainMap.put("req_id", matRequireMainMapper.getNextReqId(mapVo));
				//System.out.println("********:"+mapVo.get("make_date").toString());
				//System.out.println("********make_date:"+DateUtil.stringToDate(mapVo.get("make_date").toString(), "yyyy-MM-dd"));
				//System.out.println("********make_date:"+DateUtil.stringToDate(mainMap.get("make_date").toString(), "yyyy-MM-dd"));
				mainMap.put("make_date", DateUtil.stringToDate(mapVo.get("make_date").toString(), "yyyy-MM-dd"));
				mainMap.put("maker", main.get("maker"));
				mainMap.put("rdate", DateUtil.stringToDate(mapVo.get("make_date").toString(), "yyyy-MM-dd"));
				mainMap.put("checker", "");
				mainMap.put("check_date", "");
				mainMap.put("state", "1");
				mainMap.put("req_type", "1");
				mainMap.put("come_from", "1");
				mainMap.put("is_dir", "0");
				mainMap.put("return_reason", "");

				mainList.add(mainMap);
				
				//处理明细数据
				List<Map<String,Object>> detailOldList = new  ArrayList<Map<String,Object>>();
				detailOldList = matRequireDetailMapper.queryDetailByID(mapVo);
				for(Map<String, Object> detail : detailOldList){
					detailMap = defaultDetailValue();
					detailMap.put("group_id", detail.get("group_id"));
					detailMap.put("hos_id", detail.get("hos_id"));
					detailMap.put("copy_code", detail.get("copy_code"));
					detailMap.put("req_id", mainMap.get("req_id"));
					detailMap.put("req_code", mainMap.get("req_code"));
					detailMap.put("inv_id", detail.get("inv_id"));
					detailMap.put("inv_no", detail.get("inv_no"));
					detailMap.put("price", detail.get("price"));
					detailMap.put("amount", detail.get("amount"));
					detailMap.put("pack_code", detail.get("pack_code"));
					detailMap.put("num", detail.get("num"));
					detailMap.put("num_exchange", detail.get("num_exchange"));
					detailMap.put("sup_no", detail.get("sup_no"));
					detailMap.put("sup_id", detail.get("sup_id"));
					detailMap.put("memo", detail.get("memo"));
					detailList.add(detailMap);
				}
			}	
			//System.out.println("**************** mainList:"+mainList.size());
			//System.out.println("**************** detailList:"+detailList.size());
			
			if(mainList.size() > 0){
				matRequireMainMapper.addBatch(mainList);
			}
			if(detailList.size() > 0){
				matRequireDetailMapper.addBatch(detailList);
			}
			
			return "{\"msg\":\"复制成功.\",\"state\":\"true\"}";
			
		}catch(Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"复制失败 数据库异常 请联系管理员! 错误编码  copyMatDeptPlan\"}";
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
		//String store_alias =  matCommonMapper.queryStoreAliasById(map);
		map.put("table_code", "MAT_REQUIRE_MAIN");
		map.put("year", entityMap.get("year"));
		map.put("month", entityMap.get("month"));
		map.put("day", entityMap.get("day"));
		map.put("prefixe", "XQ");
		map.put("store_alias", "");
		map.put("bus_type", "");
		//System.out.println("************** year:"+entityMap.get("year"));
		//System.out.println("************** month:"+entityMap.get("month"));
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
		req_code = "XQ" + 
		entityMap.get("year").toString().substring(2, 4) + entityMap.get("month") + entityMap.get("day") + max_no;
		
		return req_code;
	}

	/**
	 * 科室消耗导入查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryDeptExpend(Map<String, Object> entityMap) throws DataAccessException{
		List<?> list = matRequirePlanMapper.queryDeptExpend(entityMap);
		return ChdJson.toJson(list);
	}
	
	/**
	 * 科室消耗导入查询明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){			
			
			List<?> list = matRequireDetailMapper.queryDetailByCode(entityMap);		
			
			return ChdJson.toJson(list);			
		}else{			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<?> list = matRequireDetailMapper.queryDetailByCode(entityMap, rowBounds);	
			
			PageInfo page = new PageInfo(list);
			//System.out.println("********:"+ChdJson.toJson(list, page.getTotal()));
			return ChdJson.toJson(list, page.getTotal());	
		}
	}

	/**
	 * 库房配套导入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryDeptSupport(Map<String, Object> entityMap) throws DataAccessException{
		List<?> list = matRequirePlanMapper.queryDeptSupport(entityMap);
		return ChdJson.toJson(list);
	}
	
	/**
	 * 库存安全导入查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryStoreSafe(Map<String, Object> entityMap) throws DataAccessException {
		List<?> list = matRequirePlanMapper.queryStoreSafe(entityMap);
		return ChdJson.toJson(list);
	}
	
	
	
	@Override
	public Map<String,Object> printMatRequireMainData(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MatRequireMainMapper matRequireMainMapper = (MatRequireMainMapper)context.getBean("matRequireMainMapper");
			
			//查询凭证主表
			List<Map<String,Object>> mainList=matRequireMainMapper.queryMatRequireMainByReqCode(map);
					
			//查询凭证明细表
			List<Map<String,Object>> detailList=matRequireMainMapper.queryMatRequireMainDetailByReqCode(map);
			
			reMap.put("main", mainList);
			reMap.put("detail", detailList);
			
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}

	@Override
	public void updateMatApplyRelaTables(Map<String, Object> entityMap) throws DataAccessException {
		//查看是否是科室申请生成的科室计划单
		List<Map<String,Object>> applyList = JsonListMapUtil.toListMapLower(matRequireMainMapper.queryMatApplyRelaReqById(entityMap));
		if(applyList.size() > 0){
			matRequireMainMapper.deleteMatApplyRelaReq(applyList);
		}
		
	}
	
}
