package com.chd.hrp.hr.serviceImpl.teachingmanagement;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.chd.hrp.hr.dao.teachingmanagement.HrZyyNtrainTecMapper;
import com.chd.hrp.hr.dao.teachingmanagement.standardset.HrZyyNtrainTecSetMapper;
import com.chd.hrp.hr.entity.teachingmanagement.HrZyyNtrainTec;
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainTecSet;
import com.chd.hrp.hr.service.teachingmanagement.HrZyyNtrainTecService;
import com.github.pagehelper.PageInfo;

@Service("hrZyyNtrainTecService")
public class HrZyyNtrainTecServiceImpl implements HrZyyNtrainTecService {
	private static Logger logger = Logger.getLogger(HrZyyNtrainIcuServiceImpl.class);
	
	@Resource(name = "hrZyyNtrainTecMapper")
	private final HrZyyNtrainTecMapper hrZyyNtrainTecMapper = null;
	@Resource(name = "hrZyyNtrainTecSetMapper")
	private final HrZyyNtrainTecSetMapper hrZyyNtrainTecSetMapper = null;
	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	@Resource(name = "hrZyyNtrainInareaMapper")
	private final HrZyyNtrainInareaMapper hrZyyNtrainInareaMapper = null;



	/**
	 * 保存
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
try {
	         String msg="";
			List<HrZyyNtrainTec> alllistVo = JSONArray.parseArray(
				String.valueOf(entityMap.get("paramVo")),
				HrZyyNtrainTec.class);
			for (HrZyyNtrainTec hrZyyNtrainTec : alllistVo) {
				  // 获取实体类的所有属性，返回Field数组    
			    Field[] field = hrZyyNtrainTec.getClass().getDeclaredFields();
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
			            entityMap1.put("year", hrZyyNtrainTec.getYear());
			            List<HrZyyNtrainTecSet> list=(List<HrZyyNtrainTecSet>) hrZyyNtrainTecSetMapper.query(entityMap1);
			            if (list!=null&&list.size()>0) {
			            for (HrZyyNtrainTecSet hrZyyNtrainTecSet : list) {
							  // 获取实体类的所有属性，返回Field数组    
						    Field[] field1 = hrZyyNtrainTecSet.getClass().getDeclaredFields();
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
						                    Method m = hrZyyNtrainTecSet.getClass().getMethod("get" + name);    
						                    value1 = (Double) m.invoke(hrZyyNtrainTecSet);    
						                    //System.out.println("数据类型为：Double");    
						                      
						            }
						            if(name.equalsIgnoreCase(name1)){
						            	break;
						            }
						           
						    }
						} //查询满分标准结束  
			            // 如果type是类类型，则前面包含"class "，后面跟类名 
			            if (type.equals("class java.lang.Double")) {    
			                    Method m = hrZyyNtrainTec.getClass().getMethod("get" + name);
			                    //得到实际分数
			                    value = (Double) m.invoke(hrZyyNtrainTec);    
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
			    }else{
	        		 msg= "{\"error\":\"hrZyyNtrainTec.getYear()没有设置满分标准.\",\"state\":\"false\"}";
	        		 break;
	           }   
			    }
			    hrZyyNtrainTec.setTot_score(sum);
			}

		if (alllistVo != null && alllistVo.size() > 0) {
			for (HrZyyNtrainTec hrZyyNtrainTec : alllistVo) {
				hrZyyNtrainTec.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				hrZyyNtrainTec.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				/*hrZyyNtrainInarea.setYear(mapVo.get("year")
						.toString());*/
				/*hrZyyNtrainInarea.setNote("");*/
			}

		
		
			hrZyyNtrainTecMapper.deleteHrZyyNtrainTec(alllistVo);
			hrZyyNtrainTecMapper.addBatch(alllistVo);
		}
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

			List<HrZyyNtrainTec> list = (List<HrZyyNtrainTec>) hrZyyNtrainTecMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrZyyNtrainTec> list = (List<HrZyyNtrainTec>) hrZyyNtrainTecMapper
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
	public String queryProfessionalComboBox(Map<String, Object> entityMap)
			throws DataAccessException {
		return JSONArray.toJSONString(hrZyyNtrainTecMapper.queryProfessionalComboBox(entityMap));
	}
	/**
	 * 查询学历信息 下拉框
	 */
	@Override
	public String queryEducationComboBox(Map<String, Object> entityMap)
			throws DataAccessException {
		return JSONArray.toJSONString(hrZyyNtrainTecMapper.queryEducationComboBox(entityMap));
	}
	/**
	 * 查询轮转科室信息  下拉框
	 */
	@Override
	public String queryDeptComboBox(Map<String, Object> entityMap)
			throws DataAccessException {
		return JSONArray.toJSONString(hrZyyNtrainTecMapper.queryDeptComboBox(entityMap));
	}
	/**
	 * 查询  双击工号出现下拉框
	 */
	@Override
	public String queryComboBox(Map<String, Object> entityMap)
			throws DataAccessException {
		return ChdJson.toJson(hrZyyNtrainTecMapper.queryComboBox(entityMap));
	}
	/**
	 * 删除数据
	 */
	@Override
	public String deleteHrZyyNtrainTec(List<HrZyyNtrainTec> entityList)
			throws DataAccessException {
		try {
			hrZyyNtrainTecMapper.deleteHrZyyNtrainTec(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}
	/**
	 * 计算
	 */
	@Override
	public String math(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<HrZyyNtrainTec> alllistVo = JSONArray
				.parseArray(String.valueOf(entityMap.get("paramVo")), HrZyyNtrainTec.class);
			Set<String> yearSet = new HashSet<String>();
			for (HrZyyNtrainTec hrZyyNtrainTec : alllistVo) {
				yearSet.add(hrZyyNtrainTec.getYear());
				hrZyyNtrainTec.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				hrZyyNtrainTec.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				// 获取实体类的所有属性，返回Field数组
				Field[] field = hrZyyNtrainTec.getClass().getDeclaredFields();
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
					entityMap1.put("year", hrZyyNtrainTec.getYear());
					List<HrZyyNtrainTecSet> list = (List<HrZyyNtrainTecSet>) hrZyyNtrainTecSetMapper.query(entityMap1);
					if (list.size() > 0) {
						for (HrZyyNtrainTecSet hrZyyNtrainTecSet : list) {
							// 获取实体类的所有属性，返回Field数组
							Field[] field1 = hrZyyNtrainTecSet.getClass().getDeclaredFields();
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
									Method m = hrZyyNtrainTecSet.getClass().getMethod("get" + name);
									value1 = (Double) m.invoke(hrZyyNtrainTecSet);
								}
								if (name.equalsIgnoreCase(name1)) {
									break;
								}
							}
						} // 查询满分标准结束
						
						// 如果type是类类型，则前面包含"class "，后面跟类名
						if (type.equals("class java.lang.Double")) {
							Method m = hrZyyNtrainTec.getClass().getMethod("get" + name);
							// 得到实际分数
							value = (Double) m.invoke(hrZyyNtrainTec);
							if (value == null || value1 == null) {
								continue;
							} else {
								if (value.compareTo(value1) > 0) {
									Method mset = hrZyyNtrainTec.getClass().getMethod("set" + name, Double.class);
									mset.invoke(hrZyyNtrainTec, value1);
									sum += value1;
								} else {
									sum += value;
								}
							}
						}
					} else {
						return "{\"error\":\"年份 " + hrZyyNtrainTec.getYear() + " 没有设置满分标准.\",\"state\":\"false\"}";
					}

				}
				hrZyyNtrainTec.setTot_score(sum);
			}

			hrZyyNtrainTecMapper.deleteHrZyyNtrainTec(alllistVo);
			hrZyyNtrainTecMapper.addBatch(alllistVo);
			return "{\"msg\":\"已计算.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String importZyyNtrainTec(Map<String, Object> entityMap) throws DataAccessException {
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
					// 过滤空行
					if (map.get("emp_id").get(1) == null || map.get("month").get(1) == null
							|| map.get("year").get(1) == null) {
						continue;
					}
					if ("".equals(map.get("emp_id").get(1)) || "".equals(map.get("month").get(1))
							|| "".equals(map.get("year").get(1))) {
						continue;
					}
					if ("null".equals(map.get("emp_id").get(1)) || "null".equals(map.get("month").get(1))
							|| "null".equals(map.get("year").get(1))) {
						continue;
					}
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("emp_code", map.get("emp_id").get(1));
					Map<String, Object> empMap = hrSelectMapper.queryEmpById(saveMap);
					if (empMap == null) {
						failureMsg.append("<br/>员工 " + map.get("emp_id").get(1) + " 不存在; ");
						failureNum++;
						continue;
					}
					saveMap.put("emp_id", empMap.get("emp_id"));
					saveMap.put("dept_id", map.get("dept_id").get(1));
					if (StringUtils.isNotEmpty(map.get("dept_id").get(1))) {
						Map<String, Object> dept = hrZyyNtrainInareaMapper.queryZyyDept(saveMap);
						if (dept == null) {
							failureMsg.append("<br/>科室 " + map.get("dept_id").get(1) + " 不存在; ");
							failureNum++;
							continue;
						}
						saveMap.put("dept_id", dept.get("dept_id"));
					}
					saveMap.put("year", map.get("year").get(1));
					saveMap.put("month", map.get("month").get(1));
					HrZyyNtrainTec hrZyyNtrainTec = hrZyyNtrainTecMapper.queryByCode(saveMap);
					if (hrZyyNtrainTec != null) {
						failureMsg.append("<br/> " + "年份" + map.get("year").get(1) + "月份" + map.get("month").get(1)
								+ "员工" + map.get("emp_id").get(1) + " 已存在; ");
						failureNum++;
						continue;
					}
					saveMap.put("teacher", map.get("teacher").get(1));
					saveMap.put("meet_sign", map.get("meet_sign").get(1));
					saveMap.put("skill_train", map.get("skill_train").get(1));
					saveMap.put("skill_eval", map.get("skill_eval").get(1));
					saveMap.put("drg_eval", map.get("drg_eval").get(1));
					saveMap.put("ade", map.get("ade").get(1));
					saveMap.put("online_reg", map.get("online_reg").get(1));
					saveMap.put("core_class_test", map.get("core_class_test").get(1));
					saveMap.put("dept_eval", map.get("dept_eval").get(1));
					saveMap.put("tot_score", map.get("tot_score").get(1));
					saveMap.put("note", map.get("note").get(1));

					successNum++;
					saveList.add(saveMap);
				}
				if (saveList.size() > 0) {
					hrZyyNtrainTecMapper.addBatch(saveList);
				}
				if (failureNum > 0) {
					failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 " + successNum + "条" + failureMsg + "\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}}

	@Override
	public List<Map<String, Object>> queryZyyNtrainTecByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrZyyNtrainTecMapper.queryZyyNtrainTecByPrint(entityMap));
		 return list;
	}

}
