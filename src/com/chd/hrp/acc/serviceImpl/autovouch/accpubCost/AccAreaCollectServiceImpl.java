package com.chd.hrp.acc.serviceImpl.autovouch.accpubCost;
/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.acc.dao.AccFundaAnalysisMapper;
import com.chd.hrp.acc.dao.autovouch.accpubCost.AccAreaCollectMapper;
import com.chd.hrp.acc.dao.autovouch.accpubCost.AccPersonCollectMapper;
import com.chd.hrp.acc.service.AccFundaAnalysisService;
import com.chd.hrp.acc.service.autovouch.accpubCost.AccAreaCollectService;
import com.chd.hrp.acc.service.autovouch.accpubCost.AccPersonCollectService;
import com.chd.hrp.acc.service.verification.AccBudgLederService;
import com.chd.hrp.sys.dao.UnitMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

@Service("accAreaCollectService")
public class AccAreaCollectServiceImpl implements AccAreaCollectService{

	private static Logger logger = Logger.getLogger(AccAreaCollectServiceImpl.class);
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	@Resource(name = "accAreaCollectMapper")
	private final AccAreaCollectMapper accAreaCollectMapper = null;

	@Override
	public String addAccAreaCollect(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
				Map<String,Object> accFirstMap = accAreaCollectMapper.queryAccPersonByFirst(entityMap);
				if(accFirstMap != null && "2".equals(accFirstMap.get("state").toString())){
					return "{\"error\":\"日期：" + accFirstMap.get("year_month").toString() + "的数据已审核,无法修改.\"}";
				}else{
					Map<String,Object> accPersonMap = accAreaCollectMapper.queryAccPersonByCode(entityMap);
					if(accPersonMap != null){	
						return "{\"error\":\"科室：" + accPersonMap.get("dept_id").toString() + "的" + accPersonMap.get("year_month").toString() + "数据已存在.\"}";
					}
					/*
					 *自动生成排序号 
					 * */
					Map<String, Object> utilMap = new HashMap<String, Object>();
					utilMap.put("group_id", entityMap.get("group_id"));
					utilMap.put("hos_id", entityMap.get("hos_id"));
					utilMap.put("copy_code",entityMap.get("copy_code"));
					utilMap.put("field_table","acc_cost_emp");
					utilMap.put("field_sort", "sort_code");
					accAreaCollectMapper.addAccPerson(entityMap);
					accAreaCollectMapper.updateAccPersonFtBl(entityMap);
					return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
				}
			} catch (Exception e) {
				// TODO: handle exception
				
				logger.error(e.getMessage(), e);
				//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccPersonCollect\"}";
				throw new SysException(e.getMessage());
			}
	}
	
	@Override
	public String updateAccAreaCollect(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			Map<String,Object> accFirstMap = accAreaCollectMapper.queryAccPersonByFirst(entityMap);
			if(accFirstMap != null && "2".equals(accFirstMap.get("state").toString())){
				return "{\"error\":\"日期：" + accFirstMap.get("year_month").toString() + "的数据已审核,无法修改.\"}";
			}else{
				accAreaCollectMapper.updateAccPersonCollect(entityMap);
				accAreaCollectMapper.updateAccPersonFtBl(entityMap);
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			//return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccTarget\"}";
			throw new SysException(e.getMessage());
		}

	}
	
	@Override
	public String updateAccAreaDataFromLastMonth(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			Map<String,Object> accFirstMap = accAreaCollectMapper.queryAccPersonByFirst(entityMap);
			if(accFirstMap != null){
				return "{\"error\":\"日期:" + accFirstMap.get("year_month").toString() + "数据已存在.\"}";
			}else{
				List<Map<String, Object>> list = accAreaCollectMapper.queryAccPersonByLastMonth(entityMap);
				if(list.size() == 0){
					return "{\"error\":\"上月数据为空.\"}";
				}
				accAreaCollectMapper.updateAccPersonDataFromLastMonth(entityMap);
				return "{\"msg\":\"数据更新成功.\",\"state\":\"true\"}";
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccAreaDataFromLastMonth\"}";
		}

	}
	
	@Override
	public String updateAccAreaState(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			if("1".equals(entityMap.get("state").toString())){
				int count = accAreaCollectMapper.queryQuoteOrNot(entityMap);
				if(count > 0){
					return "{\"error\":\"" + entityMap.get("year_month").toString() + "的数据已被使用，无法取消审核.\"}";
				}
			}
			
			accAreaCollectMapper.updateAccPersonState(entityMap);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"审核失败 数据库异常 请联系管理员! 错误编码  updateAccAreaState\"}";
		}

	}
	
	@Override
	public String queryAccAreaCollect(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = accAreaCollectMapper.queryAccPersonCollect(entityMap);
			
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = accAreaCollectMapper.queryAccPersonCollect(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryTotalArea(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		Map<String,Object> accTotalMap = accAreaCollectMapper.queryTotalNum(entityMap);
		
		return "{\"data\":\""+accTotalMap.get("total").toString()+"\",\"state\":\"true\"}";
	}
	
	@Override
	public List<Map<String, Object>> queryAccAreaPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
			
			List<Map<String, Object>> list = accAreaCollectMapper.queryAccPersonCollect(entityMap);
			
			return list;
		
	}

	@Override
	public String deleteAreaCollect(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			Map<String, Object> entityMap = list.get(0);
			Map<String,Object> accFirstMap = accAreaCollectMapper.queryAccPersonByFirst(entityMap);
			if(accFirstMap != null && "2".equals(accFirstMap.get("state").toString())){
				return "{\"error\":\"日期：" + accFirstMap.get("year_month").toString() + "的数据已审核,无法修改.\"}";
			}else{
				accAreaCollectMapper.deletePersonCollect(list);
				accAreaCollectMapper.updateAccPersonFtBl(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deletePersonCollect\"}";
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public Map<String, Object> queryAccAreaById(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return accAreaCollectMapper.queryAccPersonById(entityMap);
	}

	@Override
	public String importAccAreaCollect(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		try {
			List<Map<String, Object>> deptList = new ArrayList<Map<String, Object>>();
			Map<String, String> deptCacheMap = new HashMap<String, String>();

			// 1.判断表头是否为空
			String columns = mapVo.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);
			if (jsonColumns == null || jsonColumns.size() == 0) {
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}

			// 2.判断数据是否为空
			String content = mapVo.get("content").toString();
			List<Map<String, List<String>>> liData = SpreadTableJSUtil.toListMap(content, 1);
			if (liData == null || liData.size() == 0) {
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}

			Set<String> set = liData.get(0).keySet();
			if (set.size() > 3) {
				return "{\"error\":\"模版错误！\",\"state\":\"false\"}";
			}
//			String my_head = null;
//			String dept_head = null;
//			for (String string : set) {
//				if (string.contains("金额")) {
//					my_head = string;
//				} else {
//					dept_head = string;
//				}
//			}
			// 查询出部门信息，如果仅有一条的话，直接查询出对应的信息。如果多条的话，就全部查询出
			if (liData.size() == 1) {
				mapVo.put("dept_code", liData.get(0).get("科室编码").get(1));
			}
			List<Map<String, Object>> list = accAreaCollectMapper.queryDeptAllInfoDict(mapVo);
			if (list == null || list.isEmpty()) {
				return "{\"error\":\"科室数据错误，请确定所输入的科室！\",\"state\":\"false\"}";
			}

			for (Map<String, Object> map : list) {
				deptCacheMap.put(map.get("dept_name").toString(), map.get("dept_id").toString() + "," + map.get("dept_no").toString());
				deptCacheMap.put(map.get("dept_code").toString(), map.get("dept_id").toString() + "," + map.get("dept_no").toString());
			}

			// 拼装要保存的信息
			for (Map<String, List<String>> map : liData) {
				String dept_code = map.get("科室编码").get(1);
				String emp_count = map.get("科室面积").get(1);
				if (dept_code == null || "".equals(dept_code)) {
					return "{\"warn\":\"科室编码为空！\",\"state\":\"false\",\"row_cell\":\"" + map.get("科室编码").get(0) + "\"}";
				}
				if (emp_count == null || "".equals(emp_count)) {
					return "{\"warn\":\"科室面积为空！\",\"state\":\"false\",\"row_cell\":\"" + map.get("科室人数").get(0) + "\"}";
				}

				Map<String, Object> deptSaveMap = new HashMap<String, Object>();
				deptSaveMap.put("group_id", SessionManager.getGroupId());
				deptSaveMap.put("hos_id", SessionManager.getHosId());
				deptSaveMap.put("copy_code", SessionManager.getCopyCode());
				deptSaveMap.put("year_month", mapVo.get("year_month"));
				deptSaveMap.put("dept_code", dept_code);
				deptSaveMap.put("emp_count", emp_count);
				deptSaveMap.put("ft_bl", 0.00);
				deptSaveMap.put("state", 1);

				deptList.add(deptSaveMap);
			}

			if (!deptList.isEmpty()) {
				// 主表信息
				Map<String, Object> saveMap = new HashMap<String, Object>();
				saveMap.put("group_id", SessionManager.getGroupId());
				saveMap.put("hos_id", SessionManager.getHosId());
				saveMap.put("copy_code", SessionManager.getCopyCode());
				saveMap.put("year_month", mapVo.get("year_month"));
				Map<String, Object> accFirstMap = accAreaCollectMapper.queryAccPersonByFirst(saveMap);
				if(accFirstMap != null && "2".equals(accFirstMap.get("state").toString())){
					return "{\"error\":\"日期：" + accFirstMap.get("year_month").toString() + "的数据已审核,无法修改.\"}";
				}else{
					accAreaCollectMapper.insertAccPersonByImport(deptList);
					accAreaCollectMapper.updateAccPersonFtBl(saveMap);
				}
			}
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
