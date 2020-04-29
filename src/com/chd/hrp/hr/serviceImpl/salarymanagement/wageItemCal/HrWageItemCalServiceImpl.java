package com.chd.hrp.hr.serviceImpl.salarymanagement.wageItemCal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.acc.dao.report.SuperReportDesignMapper;
import com.chd.hrp.acc.entity.report.RepDefineEle;
import com.chd.hrp.hr.dao.salarymanagement.wageItemCal.HrWageItemCalMapper;
import com.chd.hrp.hr.dao.salarymanagement.wagePlanManage.HrWagePlanManageMapper;
import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWageItem;
import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWageItemCal;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.salarymanagement.wageItemCal.HrWageItemCalService;
import com.github.pagehelper.PageInfo;
/**
 * 【薪资管理-工资项取值方法】
 * @author yang
 *
 */
@Service("hrWageItemCalService")
public class HrWageItemCalServiceImpl implements HrWageItemCalService {

	private static Logger logger = Logger.getLogger(HrWageItemCalServiceImpl.class);
	
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	
	@Resource(name = "hrWageItemCalMapper")
	private final HrWageItemCalMapper hrWageItemCalMapper = null;
	
	@Resource(name = "hrWagePlanManageMapper")
	private final HrWagePlanManageMapper hrWagePlanManageMapper = null;
	
	@Resource(name = "superReportDesignMapper")
	private final SuperReportDesignMapper superReportDesignMapper = null;
	
	// 主查询
	@Override
	public String queryHrWageItemCal(Map<String, Object> paramMap) throws DataAccessException {
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
				.toListLower((List<Map<String, Object>>) hrWageItemCalMapper.query(paramMap));
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = ChdJson
				.toListLower((List<Map<String, Object>>) hrWageItemCalMapper.query(paramMap, rowBounds));
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	// 添加
	@Override
	public String addHrWageItemCal(Map<String, Object> paramMap) throws DataAccessException {
		if(paramMap.get("group_id") == null){
			paramMap.put("group_id", SessionManager.getGroupId());
		}
		if(paramMap.get("hos_id") == null){
			paramMap.put("hos_id", SessionManager.getHosId());
		}
		if(paramMap.get("copy_code") == null){
			paramMap.put("copy_code", SessionManager.getCopyCode());
		}
		try{
			if("全部".equals(paramMap.get("kind_code").toString())){
				paramMap.remove("kind_code");
			}
			
			List<HrWageItemCal> existsList = (List<HrWageItemCal>) hrWageItemCalMapper.queryExists(paramMap);
			if(existsList.size() > 0){
				return "{\"warn\":\"不可重复设置取值方法.\",\"state\":\"false\"}";
			}
			
			// 计算公式转译成英文(sql语句)
			if("2".equals(paramMap.get("item_cal").toString())){
				paramMap = calTranslation(paramMap);
				if (paramMap.get("error") != null) {
					return paramMap.get("error").toString();
				}
			}
			
			// 保存
			hrWageItemCalMapper.add(paramMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败.\",\"state\":\"false\"}";
		}
	}

	// 删除
	@Override
	public String deleteHrWageItemCal(Map<String, Object> paramMap) throws DataAccessException {
		try{
			List<HrWageItemCal> list = JSONArray.parseArray(String.valueOf(paramMap.get("paramVo")), HrWageItemCal.class);
			List<Map<String, Object>> delList = JsonListMapUtil.beanToListMap(list);
			
			// 根据主键批量删除
			hrWageItemCalMapper.deleteBatch(delList);
			return "{\"msg\":\"删除成功\", \"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败\", \"state\":\"false\"}";
		}
	}

	// 取一个工资项取值方法
	@Override
	public HrWageItemCal getHrWageItemCalByPK(Map<String, Object> paramMap) throws DataAccessException {
		return hrWageItemCalMapper.findHrWageItemCalByPK(paramMap);
	}

	// 更新
	@Override
	public String updateHrWageItemCal(Map<String, Object> paramMap) throws DataAccessException {
		if(paramMap.get("group_id") == null){
			paramMap.put("group_id", SessionManager.getGroupId());
		}
		if(paramMap.get("hos_id") == null){
			paramMap.put("hos_id", SessionManager.getHosId());
		}
		if(paramMap.get("copy_code") == null){
			paramMap.put("copy_code", SessionManager.getCopyCode());
		}
		try{
			if("全部".equals(paramMap.get("kind_code").toString())){
				paramMap.remove("kind_code");
			}
			
			List<HrWageItemCal> existsList = (List<HrWageItemCal>) hrWageItemCalMapper.queryExists(paramMap);
			if(existsList.size() > 0){
				return "{\"warn\":\"不可重复设置取值方法.\",\"state\":\"false\"}";
			}
			
			// 计算公式转译成英文(sql语句)
			if("2".equals(paramMap.get("item_cal").toString())){
				paramMap = calTranslation(paramMap);
				if(paramMap.get("error") != null){
					return paramMap.get("error").toString();
				}
			}
			
			// 更新
			hrWageItemCalMapper.update(paramMap);
			return "{\"msg\":\"更新成功\", \"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败\", \"state\":\"false\"}";
		}
	}

	// 职分类下拉选（带“全部”选项）
	@Override
	public String empKindSelect(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		return JSON.toJSONString(hrWageItemCalMapper.selectEmpKind(paramMap));
	}

	// 编辑计算公式时的左侧树
	@Override
	public String queryHrWageItemCalTree(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		List<Map<String, Object>> treeList = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> top = new HashMap<String, Object>();
		top.put("id",0);
		top.put("pId", "");
		top.put("name", "工资项目");
		Map<String, Object> top1 = new HashMap<String, Object>();
		top1.put("id", 1);
		top1.put("pId", "");
		top1.put("name", "取值函数");
		List<HrWageItem> itemList = hrWagePlanManageMapper.queryHrWageItemsByPlanCode(paramMap);
		for (HrWageItem item : itemList) {
			Map<String, Object> itemMap=new HashMap<String, Object>();
			itemMap.put("id", item.getItem_code());
			itemMap.put("pId",0);
			itemMap.put("name", item.getItem_name());
			treeList.add(itemMap);//append(",{id=" + item.getItem_code() + ",pId='0',name=" + item.getItem_name() + "}");
		}
		treeList.add(top1);	
		paramMap.put("copy_code", SessionManager.getCopyCode());
		paramMap.put("mod_code", "0601");
		List<RepDefineEle> eleList = superReportDesignMapper.querySuperReportEleByMod(paramMap);
		for (RepDefineEle ele : eleList) {
			Map<String, Object> eleMap=new HashMap<String, Object>();
			eleMap.put("id",  ele.getEle_code() );
			eleMap.put("pId",1);
			eleMap.put("name", ele.getEle_name());
			treeList.add(eleMap);//append(",{id=" + ele.getEle_code() + ",pId='1',name=" + ele.getEle_name()+"}");
		}
		treeList.add(top);	
		
		return JSON.toJSONString(treeList);
	}
	
	/**
	 * 公式转译
	 * @param paramMap
	 * @return
	 */
	@Override
	public Map<String, Object> calTranslation(Map<String, Object> paramMap){
		String cal = paramMap.get("cal_name").toString();
		String select_cal = paramMap.get("cal_name").toString();
		List<String> list = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\\{.*?\\}");
		Matcher matcher = pattern.matcher(cal);
		while (matcher.find()) {
			list.add(matcher.group());
		}
		
		for(String group : list){
			group = group.replaceAll("\\{", "").replaceAll("\\}", "");
			if(group.indexOf("'") > -1){
				paramMap.put("ele_name", group.substring(0, group.indexOf("(")).toString());
				paramMap.put("mod_code", "0601");
				List<RepDefineEle> ele = superReportDesignMapper.querySuperReportEleByMod(paramMap);
				cal=cal.replaceFirst(group.substring(0, group.indexOf("'")-1).toString(),ele.get(0).getEle_code());
			}else{
				paramMap.put("item_name", group);
				List<HrWageItem> itemList = hrWagePlanManageMapper.queryHrWageItem(paramMap);
				if(itemList.size() > 0){
					cal = cal.replaceFirst(itemList.get(0).getItem_name(),  "to_number("+itemList.get(0).getColumn_item().toString()+")");
					//cal = cal.replaceFirst(group, "to_number(" + itemList.get(0).getColumn_item() + ")");
				}
			}
		}
		cal = cal.replaceAll("'本集团'", "group_id").replaceAll("'本医院'", "hos_id").replaceAll("'本职工'", "emp_id")
				 .replaceAll("如果完", " end ") .replaceAll("如果", " case when ").replaceAll("否则", " else ")
				 .replaceAll("则", " then ").replaceAll("或者", " or ").replaceAll("并且", " and ")
				 .replaceAll("取整", " round").replaceAll("是", " 1").replaceAll("否", " 0")
				 /*.replaceAll("\\{", "").replaceAll("\\}", "")*/;
		select_cal = cal.replaceAll("'本集团'", "group_id").replaceAll("'本医院'", "hos_id").replaceAll("'本职工'", "emp_id")
				 .replaceAll("如果完", " end ") .replaceAll("如果", " case when ").replaceAll("否则", " else ")
				 .replaceAll("则", " then ").replaceAll("或者", " or ").replaceAll("并且", " and ")
				 .replaceAll("取整", " round").replaceAll("是", " 1").replaceAll("否", " 0")
				 .replaceAll("\\{", "").replaceAll("\\}", "");
		try {
			paramMap.put("select_cal", select_cal);
			hrWageItemCalMapper.queryFun(paramMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			paramMap.put("error", "{\"error\":\"公式不合法.\",\"state\":\"false\"}");
			return paramMap;
		}
		paramMap.put("cal_eng", cal);
		return paramMap;
	}
}
