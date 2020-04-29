package com.chd.hrp.hr.serviceImpl.salarymanagement.wagePlanManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.report.SuperReportDesignMapper;
import com.chd.hrp.acc.dao.wagedata.AccWagePayMapper;
import com.chd.hrp.hr.dao.salarymanagement.HrStandardMapper;
import com.chd.hrp.hr.dao.salarymanagement.wagePlanManage.HrWagePlanManageMapper;
import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWageItem;
import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWagePlan;
import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWagePlanKind;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.salarymanagement.wagePlanManage.HrWagePlanManageService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author yang
 *
 */
@Service("hrWagePlanManageService")
public class HrWagePlanManageServiceImpl implements HrWagePlanManageService {

	private static Logger logger = Logger.getLogger(HrWagePlanManageServiceImpl.class);
	
	// 引入DAO
	@Resource(name = "hrWagePlanManageMapper")
	private final HrWagePlanManageMapper hrWagePlanManageMapper = null;
	
	@Resource(name = "hrStandardMapper")
	private final HrStandardMapper hrStandardMapper = null;
	
	@Resource(name = "superReportDesignMapper")
	private final SuperReportDesignMapper superReportDesignMapper = null;
	
	@Resource(name = "accWagePayMapper")
	private final AccWagePayMapper accWagePayMapper = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	// 引入service
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	
	// 薪资方案主查询
	@Override
	public String queryWagePlan(Map<String, Object> paramMap) throws DataAccessException {
		if(paramMap.get("group_id") == null){
			paramMap.put("group_id", SessionManager.getGroupId());
		}
		if(paramMap.get("hos_id") == null){
			paramMap.put("hos_id", SessionManager.getHosId());
		}
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<Map<String, Object>> list = ChdJson
				.toListLower((List<Map<String, Object>>) hrWagePlanManageMapper.query(paramMap));
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = ChdJson
				.toListLower((List<Map<String, Object>>) hrWagePlanManageMapper.query(paramMap, rowBounds));
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	// 薪资方案添加
	@Override
	public String addWagePlan(Map<String, Object> paramMap) throws DataAccessException {
		try{
			paramMap.put("group_id", SessionManager.getGroupId());
			paramMap.put("hos_id", SessionManager.getHosId());
			
			// 先查编号与名称是否已经存在
			List<Map<String, Object>> resultMapList = (List<Map<String, Object>>) hrWagePlanManageMapper
					.queryExists(paramMap);
			// 不存在：保存
			if (resultMapList.size() == 0) {
				hrWagePlanManageMapper.add(paramMap);// 保存薪资方案
				// 保存薪资方案与职工分类关联关系
				hrWagePlanManageMapper.deleteHrWagePlanKindByFK(paramMap);
				List<String> empKinds = JSONArray.parseArray(String.valueOf(paramMap.get("emp_kinds")), String.class);
				if(empKinds.size() > 0){
					List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
					for(String empKind : empKinds){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("group_id", SessionManager.getGroupId());
						map.put("hos_id", SessionManager.getHosId());
						map.put("plan_code", paramMap.get("plan_code"));
						map.put("emp_kind_code", empKind);
						addList.add(map);
					}
					hrWagePlanManageMapper.addHrWagePlanKindBatch(addList);
				}
				// 存在：返回提示
			}else{
				String planCode = paramMap.get("plan_code").toString();
				String planName = paramMap.get("plan_name").toString();
				for (Map<String, Object> resultMap : resultMapList) {
					if (planCode.equals(resultMap.get("plan_code"))) {
						return "{\"msg\":\"薪资方案编码" + planCode + "被占用.\",\"state\":\"false\"}";
					}
					if (planName.equals(resultMap.get("plan_name"))) {
						return "{\"msg\":\"薪资方案名称" + planName + "被占用.\",\"state\":\"false\"}";
					}
				}
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败.\",\"state\":\"false\"}";
		}
	}
	
	// 薪资方案删除
	@Override
	public String deleteWagePlan(Map<String, Object> paramMap) throws DataAccessException {
		try{
			List<HrWagePlan> list = JSONArray.parseArray(String.valueOf(paramMap.get("paramVo")), HrWagePlan.class);
			List<Map<String, Object>> delList = JsonListMapUtil.beanToListMap(list);
			
			// 删除验证
			StringBuilder planCodes = new StringBuilder();
			for(HrWagePlan plan : list){
				planCodes.append(",").append(plan.getPlan_code()).append("");
			}
			// 判断是否被使用
			String reStr = "";
			Map<String, Object> map = new HashMap<String, Object>();
			// 需要验证的表
			map.put("dict_code", "HR_WAGE_PLAN");
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			// 需要验证的数据主键id，多id用逗号分隔
			map.put("dict_id_str", planCodes.substring(1));
			map.put("acc_year", "");
			map.put("p_flag", "1");
			sysFunUtilMapper.querySysDictDelCheck(map);
			reStr = (String) map.get("reNote");
			if (StringUtils.isNotEmpty(reStr)) {
				return "{\"error\":\"删除失败，选择的变动类型被以下业务使用：【" + reStr.substring(0, reStr.length() - 1) + "】。\",\"state\":\"false\"}";
			}
			// 删除薪资方案与职工分类关联关系
			hrWagePlanManageMapper.deleteHrWagePlanKindByFKBatch(delList);
			// 根据主键批量删除
			hrWagePlanManageMapper.deleteBatch(delList);
			
			return "{\"msg\":\"删除成功\", \"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败\", \"state\":\"false\"}";
		}
	}
	
	// 薪资方案更新
	@Override
	public String updateWagePlan(Map<String, Object> paramMap) throws DataAccessException{
		try{
			paramMap.put("group_id", SessionManager.getGroupId());
			paramMap.put("hos_id", SessionManager.getHosId());
			int count = hrWagePlanManageMapper.queryPlanNameOccupy(paramMap);
			if(count != 0){
				return "{\"msg\":\"方案名称被占用.\",\"state\":\"false\"}";
			}else{
				hrWagePlanManageMapper.updateWagePlan(paramMap);
				// 保存薪资方案与职工分类关联关系
				hrWagePlanManageMapper.deleteHrWagePlanKindByFK(paramMap);
				List<String> empKinds = JSONArray.parseArray(String.valueOf(paramMap.get("emp_kinds")), String.class);
				if(empKinds.size() > 0){
					List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
					for(String empKind : empKinds){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("group_id", SessionManager.getGroupId());
						map.put("hos_id", SessionManager.getHosId());
						map.put("plan_code", paramMap.get("plan_code"));
						map.put("emp_kind_code", empKind);
						addList.add(map);
					}
					hrWagePlanManageMapper.addHrWagePlanKindBatch(addList);
				}
			}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败.\",\"state\":\"false\"}";
		}
	}
	
	// 取一个薪资方案
	@Override
	public HrWagePlan findHrWagePlan(Map<String, Object> paramMap) throws DataAccessException {
		return hrWagePlanManageMapper.findHrWagePlanByPK(paramMap);
	}
	
	// 工资项主查询
	@Override
	public String queryWageItem(Map<String, Object> paramMap) throws DataAccessException {
		if(paramMap.get("group_id") == null){
			paramMap.put("group_id", SessionManager.getGroupId());
		}
		if(paramMap.get("hos_id") == null){
			paramMap.put("hos_id", SessionManager.getHosId());
		}
		if(paramMap.get("copy_code") == null){
			paramMap.put("copy_code", SessionManager.getCopyCode());
		}
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<Map<String, Object>> list = ChdJson
					.toListLower((List<Map<String, Object>>) hrWagePlanManageMapper.queryWageItem(paramMap));
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = ChdJson
					.toListLower((List<Map<String,Object>>) hrWagePlanManageMapper.queryWageItem(paramMap, rowBounds));
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	// 工薪项删除
	@Override
	public String deleteWageItem(Map<String, Object> paramMap) throws DataAccessException {
		try{
			List<HrWageItem> list = JSONArray.parseArray(String.valueOf(paramMap.get("paramVo")), HrWageItem.class);
			// 删除验证
			StringBuilder itemCodes = new StringBuilder();
			for(HrWageItem item : list){
				itemCodes.append(",'").append(item.getItem_code()).append("'");
			}
			//判断是否被使用
			String reStr = "";
			Map<String, Object> map = new HashMap<String, Object>();
			// 需要验证的表
			map.put("dict_code", "HR_WAGE_ITEM");
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			// 需要验证的数据主键id，多id用逗号分隔
			map.put("dict_id_str", itemCodes.substring(1));
			map.put("acc_year", "");
			map.put("p_flag", "1");
			sysFunUtilMapper.querySysDictDelCheck(map);
			reStr = (String) map.get("reNote");
			if (StringUtils.isNotEmpty(reStr)) {
				return "{\"error\":\"删除失败，选择的变动类型被以下业务使用：【" + reStr.substring(0, reStr.length() - 1) + "】。\",\"state\":\"false\"}";
			}
			
			// 根据主键删除(批量)
			hrWagePlanManageMapper.deleteHrWageItemBatch(list);
			return "{\"msg\":\"删除成功\", \"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败\", \"state\":\"false\"}";
		}
	}

	// 复制薪资方案
	@Override
	public String copyWagePlan(Map<String, Object> paramMap) throws DataAccessException {
		try{
			paramMap.put("group_id", SessionManager.getGroupId());
			paramMap.put("hos_id", SessionManager.getHosId());
			List<Map<String, Object>> occupyList = hrWagePlanManageMapper.queryCopyHrWageItemBefore(paramMap);
			if(occupyList.size() > 0){
				StringBuilder warn = new StringBuilder();
				for(Map<String, Object> item : occupyList){
					warn.append(",").append(item.get("item_code").toString());
				}
				return "{\"warn\":\"工资项编码" + warn.substring(1) + "在目标薪资方案中被占用\",\"state\":\"false\"}";
			}
			hrWagePlanManageMapper.copyHrWageItem(paramMap);
			return "{\"msg\":\"复制成功\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}


	/*
	 *  // 生成编号
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("bill_code", "HR_WAGE_FORMULA");// 表名
		map.put("prm_perfixe", "WF");// 前缀
		String codeNum = hrBaseService.QueryHrBillNo(map);
		hrBaseService.updateAndQueryHrBillNo(map);
	*/
	
	// 工资项添加
	@Override
	public String addWageItem(Map<String, Object> paramMap) throws DataAccessException {
		try{
			if(paramMap.get("group_id") == null){
				paramMap.put("group_id", SessionManager.getGroupId());
			}
			if(paramMap.get("hos_id") == null){
				paramMap.put("hos_id", SessionManager.getHosId());
			}
			if(paramMap.get("copy_code") == null){
				paramMap.put("copy_code", SessionManager.getCopyCode());
			}
			List<HrWageItem> allList = hrWagePlanManageMapper.queryHrWageItemsByPlanCode(paramMap);
			// 保存工资项
			// 先查工资项是否已存在
			List<Map<String, Object>> reusltMapList = hrWagePlanManageMapper.queryItemCodeExists(paramMap);
			if(reusltMapList.size() > 0){
				// 存在：返回提示
				String itemCode = paramMap.get("item_code").toString();
				String itemName = paramMap.get("item_name").toString();
				for(Map<String, Object> reusltMap : reusltMapList){
					if(itemCode.equals(reusltMap.get("item_code"))){
						return "{\"error\":\"工资项编码：" + itemCode + "已存在.\"}";
					
					}
					if(itemName.equals(reusltMap.get("item_name"))){
						return "{\"error\":\"工资项名称：" + itemName + "已存在.\"}";
					}
				}
			}else{
				// 不存在,则生成工资项目字段值，最后保存
				String columnItem = "ITEM";
				int i = 1;
				boolean flag = false;
				do{
					flag = false;
					for(HrWageItem item : allList){
						if(item.getColumn_item().equals(columnItem + i)){
							flag = true;
							break;
						}
					}
					if(flag){
						i++;
					}
				}while(flag);
				paramMap.put("column_item", columnItem + i);
				
				paramMap.put("spell_code", StringTool.toPinyinShouZiMu(paramMap.get("item_name").toString()));
				paramMap.put("wbx_code", StringTool.toWuBi(paramMap.get("item_name").toString()));
			}
			
			hrWagePlanManageMapper.addWageItem(paramMap);// 保存工资项
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	// 工资项更新
	@Override
	public String updateHrWageItem(Map<String, Object> paramMap) throws DataAccessException {
		try{
			boolean flag = false;
			if(paramMap.get("group_id") == null){
				paramMap.put("group_id", SessionManager.getGroupId());
			}
			if(paramMap.get("hos_id") == null){
				paramMap.put("hos_id", SessionManager.getHosId());
			}
			if(paramMap.get("copy_code") == null){
				paramMap.put("copy_code", SessionManager.getCopyCode());
			}
			
			// 更新工资项
			// 先查工资项名称是否已存在
			List<Map<String, Object>> reusltMapList = hrWagePlanManageMapper.queryWageItemUpdateBefore(paramMap);
			HrWageItem hrWageItem = null;
			if(reusltMapList.size() == 0){// 不被占用：可以更新
				paramMap.put("spell_code", StringTool.toPinyinShouZiMu(paramMap.get("item_name").toString()));
				paramMap.put("wbx_code", StringTool.toWuBi(paramMap.get("item_name").toString()));
				hrWageItem = hrWagePlanManageMapper.findHrWageItemByPK(paramMap);
				flag = true;
			}else{
				// 被占用：返回提示
				String itemName = paramMap.get("item_name").toString();
				for(Map<String, Object> reusltMap : reusltMapList){
					if(itemName.equals(reusltMap.get("item_name"))){
						return "{\"error\":\"工资项名称：" + itemName + "被占用.\"}";
						//return "{\"msg\":\"工资项名称\"" + itemName + "\"被占用.\",\"state\":\"false\"}";
					}
				}
			}
			
			if(flag){
				hrWagePlanManageMapper.updateHrWageItem(paramMap);// 更新工资项
			}
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	// 提供薪资方案下拉选数据
	@Override
	public String wagePlanSelect(Map<String, Object> paramMap) throws DataAccessException {
		if(paramMap.get("group_id") == null){
			paramMap.put("group_id", SessionManager.getGroupId());
		}
		if(paramMap.get("hos_id") == null){
			paramMap.put("hos_id", SessionManager.getHosId());
		}
		return JSON.toJSONString(hrWagePlanManageMapper.selectWagePlan(paramMap));
	}

	// 取一个工资项
	@Override
	public HrWageItem findHrWageItem(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		return hrWagePlanManageMapper.findHrWageItemByPK(paramMap);
	}

	// 提供薪资标准表下拉选数据
	@Override
	public String selectHrWageStan(Map<String, Object> paramMap) throws DataAccessException {
		if(paramMap.get("group_id") == null){
			paramMap.put("group_id", SessionManager.getGroupId());
		}
		if(paramMap.get("hos_id") == null){
			paramMap.put("hos_id", SessionManager.getHosId());
		}
		return JSON.toJSONString(hrStandardMapper.selectHrWageStan(paramMap));
	}

	// 取薪资方案关联的职工分类
	@Override
	public List<HrWagePlanKind> findHrWagePlanKindByFK(Map<String, Object> paramMap) throws DataAccessException {
		return hrWagePlanManageMapper.findHrWagePlanKindByFK(paramMap);
	}

	// 提供工资项类型下拉选
	@Override
	public String accWageItemTypeSelect(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		return JSON.toJSONString(hrWagePlanManageMapper.selectAccWageItemType(paramMap));
	}

	// 提供一个薪资方案下的工资项下拉选
	@Override
	public String wageItemSelect(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		return JSON.toJSONString(hrWagePlanManageMapper.selectWageItem(paramMap));
	}

	// 导入工资项
	@Override
	public String importHrWageItemData(Map<String, Object> paramMap) throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String, Object>> allist = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
		Map<String, Object> isDicMap = new HashMap<String, Object>(){
			{
				put("是", "1");
				put("否", "0");
				put("1", "1");
				put("0", "0");
			}
		};
		Map<String, Object> typeMap = new HashMap<String, Object>(){
			{
				put("数值", "0");
				put("文本", "1");
				put("0", "0");
				put("1", "1");
			}
		};
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(paramMap);
			Map<String, Object> paraMap = JSONObject.parseObject(paramMap.get("para").toString());
			String planCode = paraMap.get("plan_code").toString();
			if(list != null && list.size() > 0){
				for(Map<String, List<String>> map : list){
					// 过滤空行
					Set<String> keySet = map.keySet();
					for(String key : keySet){
						if(!"note".equals(key) && StringUtils.isEmpty(map.get(key).get(1).toString())){
							failureNum++;
							continue;
						}
					}
					
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("plan_code", planCode);
					saveMap.put("item_code", map.get("item_code").get(1));
					saveMap.put("item_name", map.get("item_name").get(1));
					saveMap.put("item_nature", map.get("item_nature").get(1));
					saveMap.put("item_type", map.get("item_type").get(1));
					saveMap.put("data_type", 
							typeMap.get(StringUtils.isEmpty(map.get("data_type").get(1)) ? "0" : map.get("data_type").get(1)));
					saveMap.put("is_sum", 
						isDicMap.get(StringUtils.isEmpty(map.get("is_sum").get(1)) ? "1" : map.get("is_sum").get(1)));
					saveMap.put("is_stop", 
						isDicMap.get(StringUtils.isEmpty(map.get("is_stop").get(1)) ? "0" : map.get("is_stop").get(1)));
					saveMap.put("note", map.get("note").get(1));
					saveMap.put("spell_code", StringTool.toPinyinShouZiMu(saveMap.get("item_name").toString()));
					saveMap.put("wbx_code", StringTool.toWuBi(saveMap.get("item_name").toString()));
					
					allist.add(saveMap);
				}
				if(failureNum > 0){
					failureMsg.append("<br/>" + failureNum + "条数据缺少必填项");
				}
			}
			
			Map<String, Object> queryMap = new HashMap<String, Object>(){
				{
					put("group_id", SessionManager.getGroupId());
					put("hos_id", SessionManager.getHosId());
					put("plan_code", planCode);
					put("copy_code", SessionManager.getCopyCode());
				}
			};
			List<HrWageItem> exists = hrWagePlanManageMapper.queryHrWageItemsExists(allist);// 查已存在的
			List<Map<String, Object>> itemNatureList = hrWagePlanManageMapper.queryWageItemNature(queryMap);// 工资项性质
			List<Map<String, Object>> itemTypeList = hrWagePlanManageMapper.queryWageItemType(queryMap);// 工资项类型
			List<HrWageItem> wageItemList = hrWagePlanManageMapper.queryHrWageItemsByPlanCode(queryMap);// 此方案下所有的工资项
			itemNatureList = JsonListMapUtil.toListMapLower(itemNatureList);
			itemTypeList = JsonListMapUtil.toListMapLower(itemTypeList);
			String colItem = "ITEM";
			int i = 1;
			boolean flag = false;
			boolean itemFlag = false;
			boolean natureFlag = false;
			boolean typeFlag = false;
			for(Map<String, Object> save : allist){
				// 工资项性质匹配
				for(Map<String, Object> nature : itemNatureList){
					if(save.get("item_nature").toString().equals(nature.get("nature_code").toString())
						|| save.get("item_nature").toString().equals(nature.get("nature_name").toString())){
						save.put("item_nature", nature.get("nature_code"));
						natureFlag = true;
						break;
					}else{
						flag = true;
					}
				}
				if(!natureFlag){
					failureMsg.append("<br/>找不到工资项性质 [" + save.get("item_nature").toString() + "]");
				}
				
				// 工资项类型匹配
				for(Map<String, Object> type : itemTypeList){
					if(save.get("item_type").toString().equals(type.get("type_code").toString())
						|| save.get("item_type").toString().equals(type.get("type_name").toString())){
						save.put("item_type", type.get("type_code"));
						typeFlag = true;
						break;
					}else{
						flag = true;
					}
				}
				if(!typeFlag){
					failureMsg.append("<br/>找不到工资项类型 [" + save.get("item_type").toString() + "]");
				}
				
				// 工资项编号、名称占用检查
				if(exists.size() > 0){
					flag = true;
					boolean f = false;
					for(HrWageItem e : exists){
						if(save.get("item_code").toString().equals(e.getItem_code())){
							f = true;
							failureMsg.append("<br/>工资项编号 [" + e.getItem_code() + " ] 被占用");
						}
						if(save.get("item_name").toString().equals(e.getItem_name())){
							f = true;
							failureMsg.append("<br/>工资项名称 [" + e.getItem_code() + "] 被占用");
						}
						if(f){
							break;
						}
					}
				}
				
				if(flag){
					failureNum++;// 记录失败条件
				}else{
					// 生成表hr_wage_item的column_item字段值
					do{
						itemFlag = false;
						for(HrWageItem item : wageItemList){
							if(item.getColumn_item().equals(colItem + i)){
								itemFlag = true;
								break;
							}
						}
						if(itemFlag){
							i++;
						}
					}while(itemFlag);
					save.put("column_item", colItem + i);
					saveList.add(save);
				}
				flag = false;
				itemFlag = false;
				natureFlag = false;
				typeFlag = false;
			}
			if(failureNum > 0){
				failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
			}
			if(saveList.size() > 0){
				hrWagePlanManageMapper.addHrWageItemBatch(saveList);
			}
			return "{\"msg\":\"已成功导入 " + saveList.size() + "条" + failureMsg + "\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return "{\"error\":\"导入失败！\"}";
		}
	}

}
