package com.chd.hrp.hr.serviceImpl.attendancemanagement.attendresult;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.attendancemanagement.attendresult.HrAttendResultManageMapper;
import com.chd.hrp.hr.dao.attendancemanagement.attendresult.HrAttendResultMapper;
import com.chd.hrp.hr.service.attendancemanagement.attendresult.HrAttendResultManageService;
import com.github.pagehelper.PageInfo;
/**
 * 考勤数据上报
 * 
 * @author Administrator
 *
 */
@Service("hrAttendResultManageService")
public class HrAttendResultManageServiceImpl implements HrAttendResultManageService {
	
	private static Logger logger = Logger.getLogger(HrAttendResultManageServiceImpl.class);
	
	@Resource(name = "hrAttendResultManageMapper")
	private final HrAttendResultManageMapper hrAttendResultManageMapper = null;
	@Resource(name = "hrAttendResultMapper")
	private final HrAttendResultMapper hrAttendResultMapper = null;
	
	
	/**
	 * 查询表头
	 */
	@Override
	public Map<String, Object> queryResultManageHead(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			//获取考勤项目
			List<Map<String,Object>> itemList = hrAttendResultManageMapper.queryAttendItemCol(entityMap);
			boolean is_first = true;
			StringBuilder jsonHead = new StringBuilder();  //用于表头显示
			jsonHead.append("[{");
			StringBuilder selectSql = new StringBuilder();  //记录查询列

			if(itemList != null && itemList.size() > 0){
				for(Map<String, Object> map : itemList){
					if(!is_first){
						jsonHead.append("},{");	
					}
					jsonHead.append("\"display\": \"").append(map.get("ATTEND_SHORTNAME").toString()).append("\"");
					jsonHead.append(", \"name\": \"").append(map.get("ATTEND_ITEM")).append("\"");
					jsonHead.append(", \"attend_code\": \"").append(map.get("ATTEND_CODE")).append("\"");
					jsonHead.append(", \"attend_types\": \"").append(map.get("ATTEND_TYPES")).append("\"");
	
					selectSql.append("a.").append(map.get("ATTEND_ITEM").toString()).append(" as \"").append(map.get("ATTEND_ITEM").toString()).append("\", ");
					
					is_first = false;
				}
	
				jsonHead.append("}]");
				retMap.put("jsonHead", jsonHead.toString());
				retMap.put("selectSql", selectSql.substring(0, selectSql.length() - 2).toString());
			}
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		
		return retMap;
	}
	
	/**
	 * 查询
	 */
	@Override
	public String queryResultManage(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("user_id", SessionManager.getUserId());
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String,Object>> list = hrAttendResultManageMapper.queryResultManage(entityMap, rowBounds);
		
		@SuppressWarnings("rawtypes")
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	/**
	 * 休假查询
	 */
	@Override
	public String queryResultManageXj(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String,Object>> list = hrAttendResultManageMapper.queryResultManageXj(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	/**
	 * 加班查询
	 */
	@Override
	public String queryResultManageJb(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String,Object>> list = hrAttendResultManageMapper.queryResultManageJb(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	/**
	 * 计算
	 * 
	 */
	@Override
	public Map<String, Object> addBatchAttendResult(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			//校验是否有期间参数
			if(entityMap.get("year_month") == null || "".equals(entityMap.get("year_month").toString())){
				retMap.put("state", "false");
				retMap.put("error", "入参缺少考勤周期");
				return retMap;
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("user_id", SessionManager.getUserId());
			
			//用于重复职工的存放
			Map<String, Map<String, Object>> empMap = new HashMap<String, Map<String,Object>>();
			String empKey = "";
			
			//获取考勤项目对应的列item
			List<Map<String, Object>> itemList = hrAttendResultManageMapper.queryAttendItemCol(entityMap);
			Map<String, Object> itemMap = new HashMap<String, Object>();
			StringBuilder itemSql = new StringBuilder();
			StringBuilder itemSqlVal = new StringBuilder();
			//解析考勤项目得到公共的考勤Map用于计算
			for(Map<String, Object> map : itemList){
				itemMap.put(map.get("ATTEND_ITEM").toString(), 0);

				itemSql.append(map.get("ATTEND_ITEM").toString()).append(", ");
				
				itemSqlVal.append("#{item.").append(map.get("ATTEND_ITEM").toString()).append(",jdbcType=NUMERIC} ").append(map.get("ATTEND_ITEM").toString()).append(", ");
			}
			entityMap.put("state", 0);
			entityMap.put("oper", SessionManager.getUserId());
			entityMap.put("oper_date", new Date());
			
			entityMap.put("itemSql", itemSql.substring(0, itemSql.length() - 2).toString());
			entityMap.put("itemSqlVal", itemSqlVal.substring(0, itemSqlVal.length() - 2).toString());
			
			//获取考勤结果
			List<Map<String, Object>> resultList = hrAttendResultManageMapper.queryResultData(entityMap);
			Map<String, Object> vMap = null;
			for(Map<String, Object> map : resultList){
				empKey = map.get("dept_id_c").toString() + "@" + map.get("emp_id").toString();
				
				if(empMap.containsKey(empKey)){
					vMap = empMap.get(empKey);
				}else{
					entityMap.put("dept_id_c", map.get("dept_id_c").toString());
					entityMap.put("emp_id", map.get("emp_id").toString());
					 int state1=hrAttendResultMapper.queryState(entityMap);
						
						if(state1==0){

							vMap = new HashMap<String, Object>();
							vMap.put("dept_id_c", map.get("dept_id_c").toString());
							vMap.put("emp_id", map.get("emp_id").toString());
							vMap.put("dept_id_b", map.get("dept_id_b").toString());
							vMap.putAll(itemMap);
				}else{
					continue;
				}
					
				}
				
				vMap.put(map.get("attend_item").toString(), map.get("attend_val"));
				
				empMap.put(empKey, vMap);
		
			}
			
			//循环empMap得到增加数据的List
			List<Map<String, Object>> manageList = new ArrayList<Map<String,Object>>();
			for(Entry<String, Map<String, Object>> map : empMap.entrySet()){
				manageList.add(map.getValue());
				if(manageList.size()>0||manageList.size() == 300&&manageList.size() - 1==0){
					//删除数据
					hrAttendResultManageMapper.deleteResultManage(entityMap, manageList);
					//插入数据
					hrAttendResultManageMapper.addBatchResultManage(entityMap, manageList);
					//清空List
					manageList = new ArrayList<Map<String,Object>>();
				}
			}
			
		
			
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		
		return retMap;
	}

	/**
	 * 删除
	 * 
	 */
	@Override
	public Map<String, Object> deleteResultManage(Map<String, Object> entityMap)throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {

			if(entityMap.get("ids") == null || "".equals(entityMap.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择删除的数据！");
				return retMap;
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			String ids = entityMap.get("ids").toString();
			List<Map<String, Object>> list = JSON.parseObject(ids, new TypeReference<List<Map<String,Object>>>(){});
			//状态判断
			entityMap.put("exists_state", "0");
			int flag = hrAttendResultManageMapper.existsByState(entityMap, list);
			if(flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "存在考勤数据已提交或已审核的职工，请重新选择！");
				return retMap;
			}
			//删除数据
			hrAttendResultManageMapper.deleteResultManage(entityMap, list);
			retMap.put("msg", "删除成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		return retMap;
	}
	
	
	/**
	 * 提交、取消提交
	 * 
	 */
	@Override
	public Map<String, Object> submitOrUnSubmitResultManage(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {

			if(entityMap.get("ids") == null || "".equals(entityMap.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择操作数据！");
				return retMap;
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			String ids = entityMap.get("ids").toString();
			List<Map<String, Object>> list = JSON.parseObject(ids, new TypeReference<List<Map<String,Object>>>(){});
			//状态判断
			if(entityMap.get("state") != null && "1".equals(entityMap.get("state").toString())){

				entityMap.put("exists_state", "1");
				int flag = hrAttendResultManageMapper.existsByState(entityMap, list);
				if(flag > 0){
					retMap.put("state", "false");
					retMap.put("error", "存在考勤数据状态不是已审核的职工，请重新选择！");
					return retMap;
				}
				entityMap.put("state", 0);
				entityMap.put("oper", null);
				entityMap.put("oper_date", null);
			}else{

				entityMap.put("exists_state", "0");
				int flag = hrAttendResultManageMapper.existsByState(entityMap, list);
				if(flag > 0){
					retMap.put("state", "false");
					retMap.put("error", "存在考勤数据状态不是已提交的职工，请重新选择！");
					return retMap;
				}
				entityMap.put("state", 1);
				entityMap.put("oper", SessionManager.getUserId());
				entityMap.put("oper_date", new Date());
			}
			//提交或去掉提交
			hrAttendResultManageMapper.updateStateBySubmit(entityMap, list);

			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		return retMap;
	}
	
	
	/**
	 * 审核、消审
	 * 
	 */
	@Override
	public Map<String, Object> checkOrUnCheckResultManage(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {

			if(entityMap.get("ids") == null || "".equals(entityMap.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择删除的数据！");
				return retMap;
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			String ids = entityMap.get("ids").toString();
			List<Map<String, Object>> list = JSON.parseObject(ids, new TypeReference<List<Map<String,Object>>>(){});
			//状态判断
			if(entityMap.get("state") != null && "2".equals(entityMap.get("state").toString())){

				entityMap.put("exists_state", "1");
				int flag = hrAttendResultManageMapper.existsByState(entityMap, list);
				if(flag > 0){
					retMap.put("state", "false");
					retMap.put("error", "存在考勤数据状态不是未审核的职工，请重新选择！");
					return retMap;
				}
				entityMap.put("checker", SessionManager.getUserId());
				entityMap.put("check_date", new Date());
			}else{

				entityMap.put("exists_state", "2");
				int flag = hrAttendResultManageMapper.existsByState(entityMap, list);
				if(flag > 0){
					retMap.put("state", "false");
					retMap.put("error", "存在考勤数据状态不是已审核的职工，请重新选择！");
					return retMap;
				}
				entityMap.put("checker", null);
				entityMap.put("check_date", null);
			}
			//审核、消审
			hrAttendResultManageMapper.updateStateByCheck(entityMap, list); 

			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		return retMap;
	}
	
	/**
	 * 导入
	 * 
	 */
	@Override
	public Map<String, Object> importResultManage(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> empQMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			//从para参数中获取考勤周期
			Map<String, Object> paraMap = JsonListMapUtil.getMap(entityMap.get("para").toString());
			if(paraMap.get("year_month") == null || "".equals(paraMap.get("year_month").toString())){
				retMap.put("state", "false");
				retMap.put("error", "入参缺少考勤周期");
				return retMap;
			}
	
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			Map<String, Object> commonMap = new HashMap<String, Object>();
			List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
			commonMap.put("group_id", SessionManager.getGroupId());
			commonMap.put("hos_id", SessionManager.getHosId());
			commonMap.put("acc_year", MyConfig.getCurAccYear());
			commonMap.put("year_month", paraMap.get("year_month").toString());
			
			/**
			 * 科室信息
			 */
			List<Map<String, Object>> deptList = hrAttendResultMapper.queryDeptInfo(commonMap);
			Map<String, String> deptMap = new HashMap<String, String>();
			for(Map<String, Object> dept : deptList){
				deptMap.put(dept.get("DEPT_ID").toString(), dept.get("DEPT_ID").toString());
				deptMap.put(dept.get("DEPT_CODE").toString(), dept.get("DEPT_ID").toString());
				deptMap.put(dept.get("DEPT_NAME").toString(), dept.get("DEPT_ID").toString());
			}
			
			/**
			 * 职工信息
			 */
			List<Map<String, Object>> empList = hrAttendResultMapper.queryEmpInfo(commonMap);
			Map<String, String> empMap = new HashMap<String, String>();
			for(Map<String, Object> emp : empList){
				empMap.put(emp.get("EMP_ID").toString(), emp.get("EMP_ID").toString() + "@" + emp.get("DEPT_ID").toString());
				empMap.put(emp.get("EMP_CODE").toString(), emp.get("EMP_ID").toString() + "@" + emp.get("DEPT_ID").toString());
				empMap.put(emp.get("EMP_NAME").toString(), emp.get("EMP_ID").toString() + "@" + emp.get("DEPT_ID").toString());
			}
			
			/**
			 * 获取考勤项
			 */
			Map<String, Object> cycleMap = queryResultManageHead(commonMap);
			if(cycleMap.get("jsonHead") == null || "[{}]".equals(cycleMap.get("jsonHead").toString())){
				retMap.put("state", "false");
				retMap.put("error", "入参缺少考勤周期");
				return retMap;
			}
			List<Map<String, Object>> cycleList = JsonListMapUtil.getListMap(cycleMap.get("jsonHead").toString());
			
			Map<String, Object> dataMap = null;
			Map<String, String> repeatMap = new HashMap<String,String>();
			String repeatKey = "";
			List<String> rowList = null;
			String dept_id_c = "", emp_id = "";
			StringBuilder itemSql = new StringBuilder();
			StringBuilder itemSqlVal = new StringBuilder();
			boolean is_empty = false;
			boolean is_frist = true;
		
			List<Map<String, List<String>>> fieldList = ReadFiles.readFiles(entityMap);
			
			if(fieldList == null || fieldList.size() == 0){

				retMap.put("state", "false");
				retMap.put("error", "导入数据为空");
				return retMap;
			}
			
			for (Map<String, List<String>> map : fieldList) {
				//map为空 跳出循环
				if(map == null){
					continue;
				}
				dataMap = new HashMap<String, Object>();
				//判断科室是否存在
				rowList = map.get("dept_code");
				if("".equals(deptMap.get(rowList.get(1))) || deptMap.get(rowList.get(1)) ==null){
					failureMsg.append("<br/>科室信息: "+rowList.get(1)+" 不存在; ");
					failureNum++;
					continue;
				}else{
					dept_id_c = deptMap.get(rowList.get(1));
				}
				dataMap.put("dept_id_c", dept_id_c);
				
				//判断职工是否存在
				rowList = map.get("emp_code");
				if("".equals(empMap.get(rowList.get(1))) || empMap.get(rowList.get(1)) == null){
					failureMsg.append("<br/>职工信息: "+rowList.get(1)+" 不存在; ");
					failureNum++;
					continue;
				}else{
					emp_id = empMap.get(rowList.get(1)).split("@")[0];
				}
				dataMap.put("emp_id", emp_id);
				dataMap.put("dept_id_b", empMap.get(rowList.get(1)).split("@")[1]);
		
				//判断是否重复
				repeatKey = dept_id_c+emp_id;
				if(repeatMap.containsKey(repeatKey)){
					failureMsg.append("<br/>职工信息: "+rowList.get(1)+" 重复导入; ");
					failureNum++;
					continue;
				}else{
					repeatMap.put(repeatKey, repeatKey);
				}
				empQMap.put("year_month", paraMap.get("year_month").toString());
				empQMap.put("emp_id", emp_id);
				empQMap.put("hos_id", SessionManager.getHosId());
				empQMap.put("group_id", SessionManager.getGroupId());
				Map<String, Object> emp=hrAttendResultManageMapper.queryEmp(empQMap);
				if(emp!=null){
				failureMsg.append("<br/>职工信息: "+rowList.get(1)+" 数据已经审核或上报; ");
					failureNum++;
					continue;
				}
				//循环解析考勤考勤项列
				for(Map<String, Object> vMap : cycleList){
					rowList = map.get(vMap.get("name").toString());
					
					is_empty = rowList.get(1) != null && !"".equals(rowList.get(1));
					dataMap.put(vMap.get("name").toString(), is_empty ? rowList.get(1) : "0");
					
					if(is_frist){
						itemSql.append(vMap.get("name").toString()).append(", ");
						itemSqlVal.append("#{item.").append(vMap.get("name").toString()).append(",jdbcType=NUMERIC} ").append(vMap.get("name").toString()).append(", ");
					}
				} 
				
				dataList.add(dataMap);
				is_frist = false;
			}
			
			//存在错误信息
			if (failureNum>0){
				retMap.put("state", "false");
				retMap.put("error", failureMsg.toString());
				return retMap;
			}
			
			commonMap.put("state", 0);
			commonMap.put("oper", SessionManager.getUserId());
			commonMap.put("oper_date", new Date());
			
			commonMap.put("itemSql", itemSql.substring(0, itemSql.length() - 2).toString());
			commonMap.put("itemSqlVal", itemSqlVal.substring(0, itemSqlVal.length() - 2).toString());

			//保存数据
			List<Map<String, Object>> tList = new ArrayList<Map<String,Object>>();
			if(dataList.size() > 0){
				//更新主表
				if(dataList.size() <= 300){
					//删除已存在的职工
					hrAttendResultManageMapper.deleteResultManage(commonMap, dataList);
					//添加导入数据
					successNum = hrAttendResultManageMapper.addBatchResultManage(commonMap, dataList);
				}else{
					for(Map<String, Object> tMap : dataList){
						tList.add(tMap);
						if(tList.size() == 300){
							//删除已存在的职工
							hrAttendResultManageMapper.deleteResultManage(commonMap, dataList);
							//添加导入数据
							successNum += hrAttendResultManageMapper.addBatchResultManage(commonMap, dataList);
							tList = new ArrayList<Map<String,Object>>();
						}
					}
					if(tList.size() > 0){
						//删除已存在的职工
						hrAttendResultManageMapper.deleteResultManage(commonMap, dataList);
						//添加导入数据
						successNum += hrAttendResultManageMapper.addBatchResultManage(commonMap, dataList);
					}
				}
			}
			
			retMap.put("msg", "导入成功");
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		return retMap;
	}
    /**
     * 打印
     */
	@Override
	public List<Map<String, Object>> queryAttendResultManagePrint(Map<String, Object> entityMap) throws DataAccessException {
		return hrAttendResultManageMapper.queryAttendResultManagePrint(entityMap);
	}

}
