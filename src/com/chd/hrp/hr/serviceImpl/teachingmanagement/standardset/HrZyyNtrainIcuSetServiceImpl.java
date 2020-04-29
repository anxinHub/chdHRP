package com.chd.hrp.hr.serviceImpl.teachingmanagement.standardset;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.hrp.hr.dao.teachingmanagement.standardset.HrZyyNtrainIcuSetMapper;
import com.chd.hrp.hr.entity.teachingmanagement.HrZyyNtrainIcu;
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainIcuSet;
import com.chd.hrp.hr.service.teachingmanagement.standardset.HrZyyNtrainIcuSetService;
import com.github.pagehelper.PageInfo;

@Service("hrZyyNtrainIcuSetService")
public class HrZyyNtrainIcuSetServiceImpl implements HrZyyNtrainIcuSetService {
	private static Logger logger = Logger.getLogger(HrZyyNtrainIcuSetServiceImpl.class);
	//导入dao
	@Resource(name = "hrZyyNtrainIcuSetMapper")
	private final HrZyyNtrainIcuSetMapper hrZyyNtrainIcuSetMapper = null;
	
	
	/**
	 * 删除
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hrZyyNtrainIcuSetMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	/**
	 * 批量删除
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			hrZyyNtrainIcuSetMapper.deleteBatch(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}
	/**
	 * 页面更新数据
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<HrZyyNtrainIcuSet> alllistVo = JSONArray
				.parseArray(String.valueOf(entityMap.get("paramVo")),HrZyyNtrainIcuSet.class);
			for (HrZyyNtrainIcuSet hrZyyNtrainIcuSet : alllistVo) {
				hrZyyNtrainIcuSet.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				hrZyyNtrainIcuSet.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				// 获取实体类的所有属性，返回Field数组    
			    Field[] field = hrZyyNtrainIcuSet.getClass().getDeclaredFields();
			    double sum=0;
				// 遍历所有属性    
			    for (int j = 0; j < field.length; j++) {
		            // 获取属性的名字    
		            String name = field[j].getName();
		            if("group_id".equalsIgnoreCase(name)||"hos_id".equalsIgnoreCase(name)||"tot_score".equalsIgnoreCase(name)||"year".equalsIgnoreCase(name)||"note".equalsIgnoreCase(name)){
		            	continue;
		            }
		            // 将属性的首字符大写，方便构造get，set方法    
		            name = name.substring(0, 1).toUpperCase() + name.substring(1);    
		            // 获取属性的类型    
		            String type = field[j].getGenericType().toString();    
		            // 如果type是类类型，则前面包含"class "，后面跟类名    
		            
		            if (type.equals("class java.lang.Double")) {    
	                    Method m = hrZyyNtrainIcuSet.getClass().getMethod("get" + name);    
	                    Double value = (Double) m.invoke(hrZyyNtrainIcuSet);    
	                    //System.out.println("数据类型为：Double");    
	                    if (value != null) {
	                    	sum+=value; 
	                    } else { 
	                    	continue;
	                    }    
		            }    
			           
			    }
			    hrZyyNtrainIcuSet.setTot_score(sum);
			}   
		
			hrZyyNtrainIcuSetMapper.deleteHrZyyNtrainIcuSet(alllistVo);
			hrZyyNtrainIcuSetMapper.addBatch(alllistVo);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());


		}
	}
	/**
	 * 查询所有
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryHrZyyNtrainIcuSet(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrZyyNtrainIcuSet> list = (List<HrZyyNtrainIcuSet>) hrZyyNtrainIcuSetMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrZyyNtrainIcuSet> list = (List<HrZyyNtrainIcuSet>) hrZyyNtrainIcuSetMapper
					.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	/**
	 * 多条件查询,保存时先查询
	 */
	@Override
	public HrZyyNtrainIcuSet queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return hrZyyNtrainIcuSetMapper.queryByCode(entityMap);
	}
	@Override
	public String deleteHrZyyNtrainIcuSet(List<HrZyyNtrainIcuSet> listVo) {
		
		try {
			hrZyyNtrainIcuSetMapper.deleteHrZyyNtrainIcuSet(listVo);
			return "{\"msg\":\"删除成功\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	/**
	 * 计算
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@Override
	public String math(Map<String, Object> entityMap) throws DataAccessException, Exception{
		
try {
			
			
			List<HrZyyNtrainIcuSet> alllistVo = JSONArray.parseArray(
				String.valueOf(entityMap.get("paramVo")),
				HrZyyNtrainIcuSet.class);
			for (HrZyyNtrainIcuSet hrZyyNtrainIcuSet : alllistVo) {
				  // 获取实体类的所有属性，返回Field数组    
			    Field[] field = hrZyyNtrainIcuSet.getClass().getDeclaredFields();
			    double sum=0;
				// 遍历所有属性    
			    for (int j = 0; j < field.length; j++) {    
			            // 获取属性的名字    
			            String name = field[j].getName();
			            if("group_id".equalsIgnoreCase(name)||"hos_id".equalsIgnoreCase(name)||"tot_score".equalsIgnoreCase(name)||"year".equalsIgnoreCase(name)||"note".equalsIgnoreCase(name)){
			            	continue;
			            }
			            // 将属性的首字符大写，方便构造get，set方法    
			            name = name.substring(0, 1).toUpperCase() + name.substring(1);    
			            // 获取属性的类型    
			            String type = field[j].getGenericType().toString();    
			            // 如果type是类类型，则前面包含"class "，后面跟类名    
			            
			            if (type.equals("class java.lang.Double")) {    
			                    Method m = hrZyyNtrainIcuSet.getClass().getMethod("get" + name);    
			                    Double value = (Double) m.invoke(hrZyyNtrainIcuSet);    
			                    //System.out.println("数据类型为：Double");    
			                    if (value != null) {
			                    	sum+=value; 
			                    } else { 
			                    	continue;
			                    }    
			            }    
			           
			    }
			    hrZyyNtrainIcuSet.setTot_score(sum);
			}   
			    
		if (alllistVo != null && alllistVo.size() > 0) {
			for (HrZyyNtrainIcuSet hrZyyNtrainIcuSet : alllistVo) {
				hrZyyNtrainIcuSet.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				hrZyyNtrainIcuSet.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				/*hrZyyNtrainIcuSet.setNote("");*/
			}

		}
		
		hrZyyNtrainIcuSetMapper.deleteHrZyyNtrainIcuSet(alllistVo);
		hrZyyNtrainIcuSetMapper.addBatch(alllistVo);
			return "{\"msg\":\"已计算\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());


		}
	}
	@Override
	public HrZyyNtrainIcuSet queryIcuSet(Map<String, Object> entityMap)
			throws DataAccessException {

		return hrZyyNtrainIcuSetMapper.queryIcuSet(entityMap);
	}
	@Override
	public String importZyyNtrainIcuSet(Map<String, Object> entityMap)
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
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("year", map.get("year").get(1));
					saveMap.put("meet_sign", map.get("meet_sign").get(1));
					saveMap.put("skill_train", map.get("skill_train").get(1));
					saveMap.put("skill_eval", map.get("skill_eval").get(1));
					saveMap.put("drg_eval", map.get("drg_eval").get(1));
					saveMap.put("dept_work", map.get("dept_work").get(1));
					saveMap.put("on_duty", map.get("on_duty").get(1));
					saveMap.put("mmeet_case", map.get("mmeet_case").get(1));
					saveMap.put("mini_cex", map.get("mini_cex").get(1));
					saveMap.put("physique", map.get("physique").get(1));
					saveMap.put("ade", map.get("ade").get(1));
					saveMap.put("online_reg", map.get("online_reg").get(1));
					saveMap.put("core_class_eval", map.get("core_class_eval").get(1));
					saveMap.put("core_class_test", map.get("core_class_test").get(1));
					saveMap.put("dept_eval", map.get("dept_eval").get(1));
					saveMap.put("accessory", map.get("accessory").get(1));
					saveMap.put("tot_score", map.get("tot_score").get(1));
					saveMap.put("note", map.get("note").get(1));
				
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrZyyNtrainIcuSetMapper.addBatch(saveList);
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 "+successNum+"条"+failureMsg+"\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}}
	@Override
	public List<Map<String, Object>> queryZyyNtrainIcuSetByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrZyyNtrainIcuSetMapper.queryZyyNtrainIcuSetByPrint(entityMap));
		 return list;
	}

	
	

	

}
