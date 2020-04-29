package com.chd.hrp.acc.serviceImpl.autovouch.acccoodeptcost;
/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

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
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.acc.dao.AccFundaAnalysisMapper;
import com.chd.hrp.acc.dao.autovouch.acccoodeptcost.AccCooProjDetailMapper;
import com.chd.hrp.acc.dao.autovouch.acccoodeptcost.AccCooProjMapper;
import com.chd.hrp.acc.dao.autovouch.acccoodeptcost.AccCoopCostDetailMapper;
import com.chd.hrp.acc.dao.autovouch.acccoodeptcost.AccCoopCostMapper;
import com.chd.hrp.acc.service.AccFundaAnalysisService;
import com.chd.hrp.acc.service.autovouch.acccoodeptcost.AccCooProjService;
import com.chd.hrp.acc.service.verification.AccBudgLederService;
import com.chd.hrp.sys.dao.UnitMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

@Service("accCooProjService")
public class AccCooProjServiceImpl implements AccCooProjService{

	private static Logger logger = Logger.getLogger(AccCooProjServiceImpl.class);
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	@Resource(name = "accCooProjMapper")
	private final AccCooProjMapper accCooProjMapper = null;
	
	@Resource(name = "accCooProjDetailMapper")
	private final AccCooProjDetailMapper accCooProjDetailMapper = null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String saveAccCooProj(Map<String, Object> mapVo) {
		try {
			
			Map<String,Object> accProjMap = accCooProjMapper.queryAccProjByCode(mapVo);
			
			if(accProjMap != null){
				return "{\"error\":\"项目编码：" + accProjMap.get("proj_code").toString() + "的数据已存在.\"}";
			}

			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();

			if (mapVo.get("leftRow") != null) {
				List<Map> leftRows = JSON.parseArray(mapVo.get("leftRow").toString(), Map.class);
				for (Map<String, Object> map : leftRows) {
					if (map.get("dept_id") != null && !"".equals(map.get("dept_id").toString())) {
						Map<String, Object> saveMap = new HashMap<String, Object>();
						saveMap.put("group_id", SessionManager.getGroupId());
						saveMap.put("hos_id", SessionManager.getHosId());
						saveMap.put("copy_code", SessionManager.getCopyCode());
						saveMap.put("dept_id", Integer.parseInt(map.get("dept_id").toString()));
						if (map.get("ft_bl") == null || "".equals(map.get("ft_bl").toString())) {
							saveMap.put("ft_bl", 0.00);
						} else {
							saveMap.put("ft_bl", map.get("ft_bl"));
						}
						saveMap.put("note", map.get("note"));
						saveMap.put("coop_obj", 0);
						saveMap.put("cus_id", 0);
						saveMap.put("sup_id", 0);
						saveMap.put("proj_code", mapVo.get("proj_code"));
						detailList.add(saveMap);
					}
				}
			}

			if (mapVo.get("rightRow") != null) {
				List<Map> rightRows = JSON.parseArray(mapVo.get("rightRow").toString(), Map.class);

				for (Map<String, Object> map : rightRows) {
					if (map.get("coop_obj") != null && !"".equals(map.get("coop_obj").toString())) {
						
						Map<String, Object> saveMap = new HashMap<String, Object>();
						saveMap.put("group_id", SessionManager.getGroupId());
						saveMap.put("hos_id", SessionManager.getHosId());
						saveMap.put("copy_code", SessionManager.getCopyCode());
						saveMap.put("dept_id", 0);
						if (map.get("ft_bl") == null || "".equals(map.get("ft_bl").toString())) {
							saveMap.put("ft_bl", 0.00);
						} else {
							saveMap.put("ft_bl", map.get("ft_bl"));
						}
						saveMap.put("coop_obj", map.get("coop_obj"));
						saveMap.put("note", map.get("note"));
						saveMap.put("proj_code", mapVo.get("proj_code"));
						String coop_obj = map.get("coop_obj").toString();
						if ("1".equals(coop_obj)) {
							if(map.get("cus_id") == null || "".equals(saveMap.get("cus_id"))){
								return "{\"error\":\"外单位不能为空.\"}";
							}
							saveMap.put("cus_id", Integer.parseInt(map.get("cus_id").toString()));
							saveMap.put("sup_id", 0);
						} else if ("2".equals(coop_obj)) {
							if(map.get("sup_id") == null || "".equals(saveMap.get("sup_id"))){
								return "{\"error\":\"外单位不能为空.\"}";
							}
							saveMap.put("sup_id", Integer.parseInt(map.get("sup_id").toString()));
							saveMap.put("cus_id", 0);
						}

//						if("".equals(saveMap.get("cus_id").toString()) || "".equals(saveMap.get("sup_id").toString())){
//							return "{\"error\":\"外单位不能为空.\"}";
//						}
						
						detailList.add(saveMap);
					}
				}
			}
//			if ("add".equals(mapVo.get("page_type").toString())) {
//				accCooProjMapper.addAccCooProj(mapVo);
//			} else {
//				accCooProjMapper.updateAccCooProj(mapVo);
//				accCooProjDetailMapper.deleteAccCooProjDetailBatch(mapVo);
//			}
			if (!detailList.isEmpty()) {
				accCooProjMapper.addAccCooProj(mapVo);
				accCooProjDetailMapper.addAccCooProjDetail(detailList);
			} else{
				return "{\"error\":\"详细数据不能为空.\"}";
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String updateAccCooProj(Map<String, Object> mapVo) {
		try {

			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();

			if (mapVo.get("leftRow") != null) {
				List<Map> leftRows = JSON.parseArray(mapVo.get("leftRow").toString(), Map.class);
				for (Map<String, Object> map : leftRows) {
					if (map.get("dept_id") != null && !"".equals(map.get("dept_id").toString())) {
						Map<String, Object> saveMap = new HashMap<String, Object>();
						saveMap.put("group_id", SessionManager.getGroupId());
						saveMap.put("hos_id", SessionManager.getHosId());
						saveMap.put("copy_code", SessionManager.getCopyCode());
						saveMap.put("dept_id", Integer.parseInt(map.get("dept_id").toString()));
						if (map.get("ft_bl") == null || "".equals(map.get("ft_bl").toString())) {
							saveMap.put("ft_bl", 0.00);
						} else {
							saveMap.put("ft_bl", map.get("ft_bl"));
						}
						saveMap.put("note", map.get("note"));
						saveMap.put("coop_obj", 0);
						saveMap.put("cus_id", 0);
						saveMap.put("sup_id", 0);
						saveMap.put("proj_code", mapVo.get("proj_code"));
						detailList.add(saveMap);
					}
				}
			}

			if (mapVo.get("rightRow") != null) {
				List<Map> rightRows = JSON.parseArray(mapVo.get("rightRow").toString(), Map.class);

				for (Map<String, Object> map : rightRows) {
					if (map.get("coop_obj") != null && !"".equals(map.get("coop_obj").toString())) {
						
						Map<String, Object> saveMap = new HashMap<String, Object>();
						saveMap.put("group_id", SessionManager.getGroupId());
						saveMap.put("hos_id", SessionManager.getHosId());
						saveMap.put("copy_code", SessionManager.getCopyCode());
						saveMap.put("dept_id", 0);
						if (map.get("ft_bl") == null || "".equals(map.get("ft_bl").toString())) {
							saveMap.put("ft_bl", 0.00);
						} else {
							saveMap.put("ft_bl", map.get("ft_bl"));
						}
						saveMap.put("coop_obj", map.get("coop_obj"));
						saveMap.put("note", map.get("note"));
						saveMap.put("proj_code", mapVo.get("proj_code"));
						String coop_obj = map.get("coop_obj").toString();
						if ("1".equals(coop_obj)) {
							if(map.get("cus_id") == null || "".equals(saveMap.get("cus_id"))){
								return "{\"error\":\"外单位不能为空.\"}";
							}
							saveMap.put("cus_id", Integer.parseInt(map.get("cus_id").toString()));
							saveMap.put("sup_id", 0);
						} else if ("2".equals(coop_obj)) {
							if(map.get("sup_id") == null || "".equals(saveMap.get("sup_id"))){
								return "{\"error\":\"外单位不能为空.\"}";
							}
							saveMap.put("sup_id", Integer.parseInt(map.get("sup_id").toString()));
							saveMap.put("cus_id", 0);
						}
						
						detailList.add(saveMap);
					}
				}
			}
			if (!detailList.isEmpty()) {
				accCooProjMapper.updateAccCooProj(mapVo);
				accCooProjDetailMapper.deleteAccCooProjDetailBatch(mapVo);
				accCooProjDetailMapper.addAccCooProjDetail(detailList);
			} else{
				return "{\"error\":\"详细数据不能为空.\"}";
			}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	
	@Override
	public String queryAccCooProjDetail(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = accCooProjDetailMapper.queryAccCooProjDetail(mapVo);
		return ChdJson.toJson(list);
	}
	
	@Override
	public String queryAccCooProjObj(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = accCooProjDetailMapper.queryAccCooProjObj(mapVo);
		return ChdJson.toJson(list);
	}
	
	@Override
	public String queryAccCooProj(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = accCooProjMapper.queryAccCooProj(entityMap);
			
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = accCooProjMapper.queryAccCooProj(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public String deleteAccCooProj(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			List<Map<String, Object>> accFirstList = accCooProjMapper.queryAccProjUse(list,map);
			if(!accFirstList.isEmpty()){
				String str = new String();
				for(int i=0;i<accFirstList.size();i++){
					Map<String, Object> mapList = accFirstList.get(i);
					str = str + mapList.get("proj_code").toString()+',';
				}
				return "{\"error\":\"项目编码：" + str + "的数据已被使用,无法删除.\"}";
			}else{
				accCooProjDetailMapper.deleteAccCooProjDetail(list);
				accCooProjMapper.deleteAccCooProj(list);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccCooProj\"}";
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public Map<String, Object> queryAccProjByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		Map<String,Object> accProjMap = accCooProjMapper.queryAccProjByCode(entityMap);
		return accProjMap;
	}
	
	@Override
	public List<Map<String, Object>> queryAccProjPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
			
			List<Map<String, Object>> list = accCooProjMapper.queryAccCooProj(entityMap);
			
			return list;
		
	}
	
	@Override
	public Map<String, Object> queryAccCooProjDetailPrint(Map<String, Object> entityMap) {
		try {
			Map<String, Object> reMap = new HashMap<String, Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AccCooProjDetailMapper accCooProjDetailMapper = (AccCooProjDetailMapper) context.getBean("accCooProjDetailMapper");
			AccCooProjMapper accCooProjMapper = (AccCooProjMapper) context.getBean("accCooProjMapper");

			entityMap.put("hos_name", SessionManager.getHosName());
			if (entityMap.get("print_title") == null || "".equals(entityMap.get("print_title"))) {
				entityMap.put("print_title", "合作项目单据");
			}

			// 查询入库主表
			Map<String, Object> map = accCooProjMapper.queryAccProjByCode(entityMap);
			// 查询入库明细表
			List<Map<String, Object>> list = accCooProjDetailMapper.queryAccCooProjDetailPrint(entityMap);
			reMap.put("main", map);

			reMap.put("detail", list);

			return reMap;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}
	
	@Override
	public String importAccCooProj(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		try {
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
			Map<String, Map<String, Object>> deptCacheMap = new HashMap<String, Map<String, Object>>();

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
			if (set.size() > 6) {
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
			List<Map<String, Object>> list = accCooProjMapper.queryDeptAllInfoDict(mapVo);
			if (list == null || list.isEmpty()) {
				return "{\"error\":\"科室数据错误，请确定所输入的科室！\",\"state\":\"false\"}";
			}

			Map<String, String> deptIdMap = new HashMap<String, String>();
			for (Map<String, Object> map : list) {
				//deptIdMap.put(map.get("dept_name").toString(), map.get("dept_id").toString());
				deptIdMap.put(map.get("dept_code").toString(), map.get("dept_id").toString());
			}

			int ftBlSum = 0;
			// 拼装要保存的信息
			for (Map<String, List<String>> map : liData) {
				String proj_code = map.get("项目编码").get(1);
				String proj_name = map.get("项目名称").get(1);
				String dept_code = map.get("科室编码").get(1);
				Integer ft_bl = Integer.parseInt(map.get("分摊比例").get(1));
				String note = map.get("备注").get(1);
				if (proj_code == null || "".equals(proj_code)) {
					return "{\"warn\":\"项目编码为空！\",\"state\":\"false\",\"row_cell\":\"" + map.get("项目编码").get(0) + "\"}";
				}
				if (proj_name == null || "".equals(proj_name)) {
					return "{\"warn\":\"项目名称为空！\",\"state\":\"false\",\"row_cell\":\"" + map.get("项目名称").get(0) + "\"}";
				}
				if (dept_code == null || "".equals(dept_code)) {
					return "{\"warn\":\"科室编码为空！\",\"state\":\"false\",\"row_cell\":\"" + map.get("科室编码").get(0) + "\"}";
				}

				//组装明细表数据
				Map<String, Object> deptDetailMap = new HashMap<String, Object>();
				deptDetailMap.put("group_id", SessionManager.getGroupId());
				deptDetailMap.put("hos_id", SessionManager.getHosId());
				deptDetailMap.put("copy_code", SessionManager.getCopyCode());
				deptDetailMap.put("proj_code", proj_code);
				deptDetailMap.put("coop_obj", 0);
				deptDetailMap.put("dept_id", deptIdMap.get(dept_code).toString());
				deptDetailMap.put("cus_id", 0);
				deptDetailMap.put("sup_id", 0);
				deptDetailMap.put("note", note);
				if(ft_bl == null || "".equals(ft_bl)){
					deptDetailMap.put("ft_bl", 0);
				}else{
					deptDetailMap.put("ft_bl", ft_bl);
				}
				detailList.add(deptDetailMap);
				
				//组装主表数据
				Map<String, Object> deptMap = deptCacheMap.get(proj_code);
				if(deptMap == null || "".equals(deptMap)){
					deptMap = new HashMap<String, Object>();
					deptMap.put("group_id", SessionManager.getGroupId());
					deptMap.put("hos_id", SessionManager.getHosId());
					deptMap.put("copy_code", SessionManager.getCopyCode());
					deptMap.put("proj_code", proj_code);
					deptMap.put("proj_name", proj_name);
					deptMap.put("coop_type", 1);
					deptMap.put("is_stop", 0);
					deptMap.put("note", "");
					
					deptCacheMap.put(proj_code, deptMap);
					ftBlSum = ft_bl;
				}else{
					ftBlSum = ftBlSum + ft_bl;
					if(ftBlSum > 100){
						return "{\"error\":\"项目编码：" + map.get(proj_code).toString() + "的分摊比例值大于100.\"}";
					}
				}
			}
			
			List<Map<String, Object>> mainList = new ArrayList<Map<String, Object>>(deptCacheMap.values());

			if (!mainList.isEmpty()) {
				// 主表信息
				Map<String, Object> saveMap = new HashMap<String, Object>();
				saveMap.put("group_id", SessionManager.getGroupId());
				saveMap.put("hos_id", SessionManager.getHosId());
				saveMap.put("copy_code", SessionManager.getCopyCode());
				List<Map<String, Object>> accProjlist = accCooProjMapper.queryAccProjByList(mainList,saveMap);
				if(!accProjlist.isEmpty()){
					String str = new String();
					for(int i=0;i<accProjlist.size();i++){
						Map<String, Object> mapList = accProjlist.get(i);
						str = str + mapList.get("proj_code").toString()+',';
					}
					return "{\"error\":\"项目编码：" + str + "的数据已被使用,无法删除.\"}";
				}else{
					accCooProjMapper.insertAccProjMainByImport(mainList);
					accCooProjDetailMapper.insertAccProjDetailByImport(detailList);
				}
			}
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
