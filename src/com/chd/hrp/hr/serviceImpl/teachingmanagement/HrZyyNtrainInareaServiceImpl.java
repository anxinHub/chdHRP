package com.chd.hrp.hr.serviceImpl.teachingmanagement;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.dao.teachingmanagement.HrZyyNtrainInareaMapper;
import com.chd.hrp.hr.dao.teachingmanagement.standardset.HrZyyNtrainInareaSetMapper;
import com.chd.hrp.hr.entity.teachingmanagement.HrZyyNtrainIcu;
import com.chd.hrp.hr.entity.teachingmanagement.HrZyyNtrainInarea;
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainIcuSet;
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainInareaSet;
import com.chd.hrp.hr.service.teachingmanagement.HrZyyNtrainInareaService;
import com.github.pagehelper.PageInfo;

@Service("hrZyyNtrainInareaService")
public class HrZyyNtrainInareaServiceImpl implements HrZyyNtrainInareaService {
	private static Logger logger = Logger.getLogger(HrZyyNtrainInareaServiceImpl.class);
	
	
	@Resource(name = "hrZyyNtrainInareaMapper")
	private final HrZyyNtrainInareaMapper hrZyyNtrainInareaMapper = null;
	@Resource(name = "hrZyyNtrainInareaSetMapper")
	private final HrZyyNtrainInareaSetMapper hrZyyNtrainInareaSetMapper = null;
	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	


	/**
	 * 保存
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		
try {
			
			List<HrZyyNtrainInarea> alllistVo = JSONArray.parseArray(
				String.valueOf(entityMap.get("paramVo")),
				HrZyyNtrainInarea.class);
			for (HrZyyNtrainInarea hrZyyNtrainInarea : alllistVo) {
				hrZyyNtrainInarea.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				hrZyyNtrainInarea.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				  // 获取实体类的所有属性，返回Field数组    
			    Field[] field = hrZyyNtrainInarea.getClass().getDeclaredFields();
			    double sum=0;
			   
				// 遍历所有属性    
			    for (int j = 0; j < field.length; j++) {
			    	    Double value1=null;
					    Double value=null;
					    String name1=null;
					    String name=null;
			            // 获取属性的名字    
			            name = field[j].getName();
			            if("group_id".equalsIgnoreCase(name)||"hos_id".equalsIgnoreCase(name)||"year".equalsIgnoreCase(name)||"dept_id".equalsIgnoreCase(name)||"tot_score".equalsIgnoreCase(name)||"month".equalsIgnoreCase(name)||"teacher".equalsIgnoreCase(name)||"note".equalsIgnoreCase(name)||"emp_id".equalsIgnoreCase(name)){
			            	continue;
			            }
			            // 将属性的首字符大写，方便构造get，set方法    
			            name = name.substring(0, 1).toUpperCase() + name.substring(1);    
			            // 获取属性的类型    
			            String type = field[j].getGenericType().toString();
			            //查询满分标准开始
			            Map<String, Object> entityMap1=new HashMap<String,Object>();
			            entityMap1.put("hos_id", Double.parseDouble(SessionManager.getHosId()));
			            entityMap1.put("group_id", Double.parseDouble(SessionManager.getGroupId()));
			            entityMap1.put("year", hrZyyNtrainInarea.getYear());
			            List<HrZyyNtrainInareaSet> list=(List<HrZyyNtrainInareaSet>) hrZyyNtrainInareaSetMapper.query(entityMap1);
			            for (HrZyyNtrainInareaSet hrZyyNtrainInareaSet : list) {
							  // 获取实体类的所有属性，返回Field数组    
						    Field[] field1 = hrZyyNtrainInareaSet.getClass().getDeclaredFields();
							// 遍历所有属性    
						    for (int k = 0; k < field1.length; k++) {    
						            // 获取属性的名字    
						            name1 = field1[k].getName();
						            
						            if("group_id".equalsIgnoreCase(name1)||"hos_id".equalsIgnoreCase(name1)||"year".equalsIgnoreCase(name1)||"note".equalsIgnoreCase(name1)){
						            	continue;
						            }
						            // 将属性的首字符大写，方便构造get，set方法    
						            name1 = name1.substring(0, 1).toUpperCase() + name1.substring(1);    
						            // 获取属性的类型    
						            String type1 = field1[k].getGenericType().toString();    
						            // 如果type是类类型，则前面包含"class "，后面跟类名    
						            
						            if (type1.equals("class java.lang.Double")) {    
						                    Method m = hrZyyNtrainInareaSet.getClass().getMethod("get" + name);    
						                    value1 = (Double) m.invoke(hrZyyNtrainInareaSet);    
						                    //System.out.println("数据类型为：Double");    
						                      
						            }
						            if(name.equalsIgnoreCase(name1)){
						            	break;
						            }
						           
						    }
						} //查询满分标准结束  
			            // 如果type是类类型，则前面包含"class "，后面跟类名 
			            if (type.equals("class java.lang.Double")) {    
			                    Method m = hrZyyNtrainInarea.getClass().getMethod("get" + name);
			                    //得到实际分数
			                    value = (Double) m.invoke(hrZyyNtrainInarea);    
			                    //System.out.println("数据类型为：Double");
			                    if(value==null||value1==null){
			                    continue;
			                    }else{
			                    	if (value.compareTo(value1)>0) {
				                    	sum+=value1;
				                    } else { 
				                    	sum+=value;
				                    }
			                    }    
			            }    
			           
			    }
			    hrZyyNtrainInarea.setTot_score(sum);
			}

			hrZyyNtrainInareaMapper.deleteHrZyyNtrainInarea(alllistVo);
			hrZyyNtrainInareaMapper.addBatch(alllistVo);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}
	/**
	 * 查询
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrZyyNtrainInarea> list = (List<HrZyyNtrainInarea>) hrZyyNtrainInareaMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrZyyNtrainInarea> list = (List<HrZyyNtrainInarea>) hrZyyNtrainInareaMapper
					.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	/**
	 *  查询专业信息 下拉框
	 */
	@Override
	public String queryProfessionalComboBox(Map<String,Object> entityMap) throws DataAccessException {
		return JSONArray.toJSONString(hrZyyNtrainInareaMapper.queryProfessionalComboBox(entityMap));
	}
	/**
	 * 查询学历信息 下拉框
	 */
	@Override
	public String queryEducationComboBox(Map<String,Object> entityMap) throws DataAccessException {
		return JSONArray.toJSONString(hrZyyNtrainInareaMapper.queryEducationComboBox(entityMap));
	}
	/**
	 * 查询轮转科室信息  下拉框
	 */
	@Override
	public String queryDeptComboBox(Map<String,Object> entityMap) throws DataAccessException {
		return JSONArray.toJSONString(hrZyyNtrainInareaMapper.queryDeptComboBox(entityMap));
	}
	/**
	 * 查询  双击工号出现下拉框
	 */
	@Override
	public String queryComboBox(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return ChdJson.toJson(hrZyyNtrainInareaMapper.queryComboBox(entityMap));
	}
	/**
	 * 删除
	 */
	@Override
	public String deleteHrZyyNtrainInarea(List<HrZyyNtrainInarea> entityList)
			throws DataAccessException {
		try {
			hrZyyNtrainInareaMapper.deleteHrZyyNtrainInarea(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String math(Map<String, Object> entityMap) throws DataAccessException {
		try {
			String msg = "";
			List<HrZyyNtrainInarea> alllistVo = JSONArray
				.parseArray(String.valueOf(entityMap.get("paramVo")), HrZyyNtrainInarea.class);
			for (HrZyyNtrainInarea hrZyyNtrainInarea : alllistVo) {
				hrZyyNtrainInarea.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				hrZyyNtrainInarea.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				// 获取实体类的所有属性，返回Field数组
				Field[] field = hrZyyNtrainInarea.getClass().getDeclaredFields();
				double sum = 0;

				// 遍历所有属性
				for (int j = 0; j < field.length; j++) {
					Double value1 = null;
					Double value = null;
					String name1 = null;
					String name = null;
					// 获取属性的名字
					name = field[j].getName();
					if ("group_id".equalsIgnoreCase(name) || "hos_id".equalsIgnoreCase(name)
							|| "year".equalsIgnoreCase(name) || "dept_id".equalsIgnoreCase(name)
							|| "tot_score".equalsIgnoreCase(name) || "month".equalsIgnoreCase(name)
							|| "teacher".equalsIgnoreCase(name) || "note".equalsIgnoreCase(name)
							|| "emp_id".equalsIgnoreCase(name)) {
						continue;
					}
					// 将属性的首字符大写，方便构造get，set方法
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					// 获取属性的类型
					String type = field[j].getGenericType().toString();
					// 查询满分标准开始
					Map<String, Object> entityMap1 = new HashMap<String, Object>();
					entityMap1.put("hos_id", Double.parseDouble(SessionManager.getHosId()));
					entityMap1.put("group_id", Double.parseDouble(SessionManager.getGroupId()));
					entityMap1.put("year", hrZyyNtrainInarea.getYear());

					List<HrZyyNtrainInareaSet> list = (List<HrZyyNtrainInareaSet>) hrZyyNtrainInareaSetMapper
							.query(entityMap1);
					if (list != null && list.size() > 0) {
						for (HrZyyNtrainInareaSet hrZyyNtrainInareaSet : list) {
							// 获取实体类的所有属性，返回Field数组
							Field[] field1 = hrZyyNtrainInareaSet.getClass().getDeclaredFields();
							// 遍历所有属性
							for (int k = 0; k < field1.length; k++) {
								// 获取属性的名字
								name1 = field1[k].getName();

								if ("group_id".equalsIgnoreCase(name1) || "hos_id".equalsIgnoreCase(name1)
										|| "year".equalsIgnoreCase(name1) || "note".equalsIgnoreCase(name1)) {
									continue;
								}
								// 将属性的首字符大写，方便构造get，set方法
								name1 = name1.substring(0, 1).toUpperCase() + name1.substring(1);
								// 获取属性的类型
								String type1 = field1[k].getGenericType().toString();

								// 如果type是类类型，则前面包含"class "，后面跟类名
								if (type1.equals("class java.lang.Double")) {
									Method m = hrZyyNtrainInareaSet.getClass().getMethod("get" + name);
									value1 = (Double) m.invoke(hrZyyNtrainInareaSet);
								}
								if (name.equalsIgnoreCase(name1)) {
									break;
								}
							}
						}

						// 如果type是类类型，则前面包含"class "，后面跟类名
						if (type.equals("class java.lang.Double")) {
							Method m = hrZyyNtrainInarea.getClass().getMethod("get" + name);
							// 得到实际分数
							value = (Double) m.invoke(hrZyyNtrainInarea);
							if (value == null || value1 == null) {
								continue;
							} else {
								if (value.compareTo(value1) > 0) {
									Method mSet = hrZyyNtrainInarea.getClass().getMethod("set" + name, Double.class);
									mSet.invoke(hrZyyNtrainInarea, value1);
									sum += value1;
								} else {
									sum += value;
								}
							}
						}
					} else {
						msg = "{\"error\":\"年份 " + hrZyyNtrainInarea.getYear() + " 没有设置满分标准.\",\"state\":\"false\"}";
						return msg;
					}
				}
				hrZyyNtrainInarea.setTot_score(sum);
			}
			if (msg.equals("")) {
				hrZyyNtrainInareaMapper.deleteHrZyyNtrainInarea(alllistVo);
				hrZyyNtrainInareaMapper.addBatch(alllistVo);
				msg = "{\"msg\":\"已计算.\",\"state\":\"true\"}";
			}
			return msg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryZyyNtrainInareaByPrint(
			Map<String, Object> entityMap) throws DataAccessException {

		 List<Map<String,Object>> list = ChdJson.toListLower(hrZyyNtrainInareaMapper.queryZyyNtrainInareaByPrint(entityMap));
		 return list;
	}

	@Override
	public String importZyyNtrainInarea(Map<String, Object> entityMap)
			throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					// 过滤空行
					if (map.get("emp_id").get(1) == null || map.get("month").get(1) == null || map.get("year").get(1) == null) {
						continue;
					}

					if ("".equals(map.get("emp_id").get(1)) || "".equals(map.get("month").get(1)) || "".equals(map.get("year").get(1))) {
						continue;
					}

					if ("null".equals(map.get("emp_id").get(1)) || "null".equals(map.get("month").get(1)) ||"null".equals(map.get("year").get(1))) {
						continue;
					}
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("emp_code", map.get("emp_id").get(1));
					Map<String, Object> empMap = hrSelectMapper.queryEmpById(saveMap);
					if(empMap==null){
						failureMsg.append("<br/>员工 "+map.get("emp_id" ).get(1)+" 不存在; ");
						failureNum++; 
						continue; 
					}
					saveMap.put("emp_id",empMap.get("emp_id"));
					
					saveMap.put("dept_id", map.get("dept_id").get(1));
					if(map.get("dept_id").get(1) != null && map.get("dept_id").get(1) != ""){
					    Map<String, Object>  dept = hrZyyNtrainInareaMapper.queryZyyDept(saveMap);
						if( dept==null){
						    failureMsg.append("<br/>科室 "+map.get("dept_id" ).get(1)+" 不存在; ");
						    failureNum++; 
							continue; 
						}
						saveMap.put("dept_id", dept.get("dept_id"));
					}
					saveMap.put("year", map.get("year").get(1));
					saveMap.put("month", map.get("month").get(1));
					HrZyyNtrainInarea hrZyyNtrainInarea=hrZyyNtrainInareaMapper.queryByCode(saveMap);
					if(hrZyyNtrainInarea !=null){
						failureMsg.append("<br/> "+"年份"+map.get("year" ).get(1)+"月份"+map.get("month" ).get(1)+"员工"+map.get("emp_id" ).get(1)+" 已存在; ");
						failureNum++; 
						continue; 
					}
					saveMap.put("teacher", map.get("teacher").get(1));
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
				if(saveList.size() > 0){
					hrZyyNtrainInareaMapper.addBatch(saveList);
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 "+successNum+"条"+failureMsg+"\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return "{\"error\":\"导入失败！\"}";
		}}


}
