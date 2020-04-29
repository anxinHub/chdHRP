package com.chd.hrp.hr.serviceImpl.teachingmanagement.standardset;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.teachingmanagement.standardset.HrZyyNtrainInareaSetMapper;
import com.chd.hrp.hr.entity.teachingmanagement.HrZyyNtrainInarea;
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainIcuSet;
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainInareaSet;
import com.chd.hrp.hr.service.teachingmanagement.standardset.HrZyyNtrainInareaSetService;
import com.github.pagehelper.PageInfo;

@Service("hrZyyNtrainInareaSetService")
public class HrZyyNtrainInareaSetServiceImpl implements HrZyyNtrainInareaSetService {
	
	private static Logger logger = Logger.getLogger(HrZyyNtrainInareaSetServiceImpl.class);
	
	@Resource(name = "hrZyyNtrainInareaSetMapper")
	private final HrZyyNtrainInareaSetMapper hrZyyNtrainInareaSetMapper = null;
	
	/**
	 * 保存
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		try {
			String data = "";
			try {
				data = IsDouble(entityMap);
			} catch (Exception e) {
				return "{\"warn\":\"" + e.getMessage() + "\",\"state\":\"false\"}";
			}
			
			List<HrZyyNtrainInareaSet> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")), HrZyyNtrainInareaSet.class);
			for (HrZyyNtrainInareaSet hrZyyNtrainInareaSet : alllistVo) {
				hrZyyNtrainInareaSet.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				hrZyyNtrainInareaSet.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				// 获取实体类的所有属性，返回Field数组
				Field[] field = hrZyyNtrainInareaSet.getClass().getDeclaredFields();
				double sum = 0;
				// 遍历所有属性
				for (int j = 0; j < field.length; j++) {
					// 获取属性的名字
					String name = field[j].getName();
					if ("group_id".equalsIgnoreCase(name) || "hos_id".equalsIgnoreCase(name) || "tot_score".equalsIgnoreCase(name)
							|| "year".equalsIgnoreCase(name) || "note".equalsIgnoreCase(name)) {
						continue;
					}
					// 将属性的首字符大写，方便构造get，set方法
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					// 获取属性的类型
					String type = field[j].getGenericType().toString();
					// 如果type是类类型，则前面包含"class "，后面跟类名
					
					if (type.equals("class java.lang.Double")) {
						Method m = hrZyyNtrainInareaSet.getClass().getMethod("get" + name);
						Double value = (Double) m.invoke(hrZyyNtrainInareaSet);
						// System.out.println("数据类型为：Double");
						if (value != null) {
							sum += value;
						} else {
							continue;
						}
					}
					
				}
				hrZyyNtrainInareaSet.setTot_score(sum);
			}
			
			hrZyyNtrainInareaSetMapper.deleteHrZyyNtrainInareaSet(alllistVo);
			hrZyyNtrainInareaSetMapper.addBatch(alllistVo);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			
		}
	}
	
	private String IsDouble(Map<String, Object> entityMap) {
		//
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("meet_sign", "会计签到");
		hm.put("skill_train", "技能培训");
		hm.put("skill_eval", "技能考核");
		hm.put("case_num", "份数");
		hm.put("case_dept_num", "科室病例");
		hm.put("case_eval", "病例考核");
		hm.put("drg_num", "病种数量");
		hm.put("drg_eval", "病种考核");
		hm.put("dept_work", "科室基本能力");
		hm.put("bed", "床位管理");
		hm.put("on_duty", "值班");
		hm.put("mmeet_case", "晨会病例");
		hm.put("dept_advice", "科室");
		hm.put("advice_eval", "考核");
		hm.put("mini_cex", "mini-CEX");
		hm.put("physique", "体格");
		hm.put("consultation", "会诊");
		hm.put("ade", "出科考试");
		hm.put("online_reg", "网络登记");
		hm.put("core_class_eval", "考核");
		hm.put("core_class_test", "前后测");
		hm.put("dept_eval", "科室评价");
		hm.put("tot_score", "总得分");
		
		List<Map> dd = ChdJson.fromJsonAsList(Map.class, entityMap.get("paramVo").toString());
		for (int i = 0; i < dd.size(); i++) {
			Map<String, String> dataMap = dd.get(i);
			for (Entry<String, String> ent : dataMap.entrySet()) {
				if ("group_id".equalsIgnoreCase(ent.getKey()) || "hos_id".equalsIgnoreCase(ent.getKey()) || "tot_score".equalsIgnoreCase(ent.getKey())
						|| "_rowIndx".equalsIgnoreCase(ent.getKey()) || "_rowIndxPage".equalsIgnoreCase(ent.getKey())
						|| "pq_cellselect".equalsIgnoreCase(ent.getKey()) || "pq_cellcls".equalsIgnoreCase(ent.getKey())
						|| "et_checkBox".equalsIgnoreCase(ent.getKey()) || "pq_rowselect".equalsIgnoreCase(ent.getKey())
						|| "year".equalsIgnoreCase(ent.getKey()) || "_status".equalsIgnoreCase(ent.getKey()) || "note".equalsIgnoreCase(ent.getKey())) {
					continue;
				}
				try {
					if (ent.getValue() == null || "".equals(ent.getValue())) {
						continue;
					}
					Double.valueOf(String.valueOf(ent.getValue()));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new SysException(hm.get(ent.getKey()) + "数据类型错误，请检查！（应该为数字）");
				}
				
			}
		}
		return "";
		
	}
	
	/**
	 * 查询
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<HrZyyNtrainInareaSet> list = (List<HrZyyNtrainInareaSet>) hrZyyNtrainInareaSetMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HrZyyNtrainInareaSet> list = (List<HrZyyNtrainInareaSet>) hrZyyNtrainInareaSetMapper.query(entityMap, rowBounds);
			
			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	/**
	 * 删除数据
	 */
	@Override
	public String deleteHrZyyNtrainInareaSet(List<HrZyyNtrainInareaSet> listVo) {
		try {
			hrZyyNtrainInareaSetMapper.deleteHrZyyNtrainInareaSet(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	@Override
	public String math(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			List<HrZyyNtrainInareaSet> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")), HrZyyNtrainInareaSet.class);
			for (HrZyyNtrainInareaSet hrZyyNtrainInareaSet : alllistVo) {
				// 获取实体类的所有属性，返回Field数组
				Field[] field = hrZyyNtrainInareaSet.getClass().getDeclaredFields();
				double sum = 0;
				// 遍历所有属性
				for (int j = 0; j < field.length; j++) {
					// 获取属性的名字
					String name = field[j].getName();
					if ("group_id".equalsIgnoreCase(name) || "hos_id".equalsIgnoreCase(name) || "tot_score".equalsIgnoreCase(name)
							|| "year".equalsIgnoreCase(name) || "note".equalsIgnoreCase(name)) {
						continue;
					}
					// 将属性的首字符大写，方便构造get，set方法
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					// 获取属性的类型
					String type = field[j].getGenericType().toString();
					// 如果type是类类型，则前面包含"class "，后面跟类名
					
					if (type.equals("class java.lang.Double")) {
						Method m = hrZyyNtrainInareaSet.getClass().getMethod("get" + name);
						Double value = (Double) m.invoke(hrZyyNtrainInareaSet);
						// System.out.println("数据类型为：Double");
						if (value != null) {
							sum += value;
						} else {
							continue;
						}
					}
					
				}
				hrZyyNtrainInareaSet.setTot_score(sum);
			}
			
			if (alllistVo != null && alllistVo.size() > 0) {
				for (HrZyyNtrainInareaSet hrZyyNtrainInareaSet : alllistVo) {
					hrZyyNtrainInareaSet.setHos_id(Double.parseDouble(SessionManager.getHosId()));
					hrZyyNtrainInareaSet.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					/* hrZyyNtrainIcuSet.setNote(""); */
				}
				
			}
			
			hrZyyNtrainInareaSetMapper.deleteHrZyyNtrainInareaSet(alllistVo);
			hrZyyNtrainInareaSetMapper.addBatch(alllistVo);
			return "{\"msg\":\"已计算.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			
		}
	}
	
	/**
	 * 查询住院医病区满分标准
	 */
	@Override
	public HrZyyNtrainInareaSet queryInareaSet(Map<String, Object> entityMap) throws DataAccessException {
		return hrZyyNtrainInareaSetMapper.queryInareaSet(entityMap);
	}
	
	@Override
	public String importZyyNtrainInareaSet(Map<String, Object> entityMap) throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list != null && list.size() > 0) {
				for (Map<String, List<String>> map : list) {
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("year", map.get("year").get(1));
					saveMap.put("meet_sign", map.get("meet_sign").get(1));
					saveMap.put("skill_train", map.get("skill_train").get(1));
					saveMap.put("skill_eval", map.get("skill_eval").get(1));
					saveMap.put("case_num", map.get("case_num").get(1));
					saveMap.put("case_dept_num", map.get("case_dept_num").get(1));
					saveMap.put("case_eval", map.get("case_eval").get(1));
					saveMap.put("drg_num", map.get("drg_num").get(1));
					saveMap.put("drg_eval", map.get("drg_eval").get(1));
					saveMap.put("dept_work", map.get("dept_work").get(1));
					saveMap.put("bed", map.get("bed").get(1));
					saveMap.put("on_duty", map.get("on_duty").get(1));
					saveMap.put("mmeet_case", map.get("mmeet_case").get(1));
					saveMap.put("dept_advice", map.get("dept_advice").get(1));
					saveMap.put("advice_eval", map.get("advice_eval").get(1));
					saveMap.put("mini_cex", map.get("mini_cex").get(1));
					saveMap.put("physique", map.get("physique").get(1));
					saveMap.put("consultation", map.get("consultation").get(1));
					saveMap.put("ade", map.get("ade").get(1));
					saveMap.put("online_reg", map.get("online_reg").get(1));
					saveMap.put("core_class_eval", map.get("core_class_eval").get(1));
					saveMap.put("core_class_test", map.get("core_class_test").get(1));
					saveMap.put("dept_eval", map.get("dept_eval").get(1));
					saveMap.put("tot_score", map.get("tot_score").get(1));
					saveMap.put("note", map.get("note").get(1));
					
					successNum++;
					saveList.add(saveMap);
				}
				if (saveList.size() > 0) {
					hrZyyNtrainInareaSetMapper.addBatch(saveList);
				}
				if (failureNum > 0) {
					failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 " + successNum + "条" + failureMsg + "\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}
	
	@Override
	public List<Map<String, Object>> queryZyyNtrainInareaSetByPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = ChdJson.toListLower(hrZyyNtrainInareaSetMapper.queryZyyNtrainInareaByPrint(entityMap));
		return list;
	}
	
}
