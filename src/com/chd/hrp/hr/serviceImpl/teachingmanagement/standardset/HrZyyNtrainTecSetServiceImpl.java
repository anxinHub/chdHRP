package com.chd.hrp.hr.serviceImpl.teachingmanagement.standardset;

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
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.dao.teachingmanagement.standardset.HrZyyNtrainTecSetMapper;
import com.chd.hrp.hr.entity.teachingmanagement.HrZyyNtrainTec;
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainIcuSet;
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainTecSet;
import com.chd.hrp.hr.service.teachingmanagement.standardset.HrZyyNtrainTecSetService;
import com.github.pagehelper.PageInfo;

@Service("hrZyyNtrainTecSetService")
public class HrZyyNtrainTecSetServiceImpl implements HrZyyNtrainTecSetService {
	
	private static Logger logger=Logger.getLogger(HrZyyNtrainTecSetServiceImpl.class);
	
	@Resource(name="hrZyyNtrainTecSetMapper")
	private final HrZyyNtrainTecSetMapper hrZyyNtrainTecSetMapper=null;
	
	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	
	/**
	 * 保存
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
try {
			
			
			List<HrZyyNtrainTecSet> alllistVo = JSONArray.parseArray(
				String.valueOf(entityMap.get("paramVo")),
				HrZyyNtrainTecSet.class);
			for (HrZyyNtrainTecSet hrZyyNtrainTecSet : alllistVo) {
				hrZyyNtrainTecSet.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				hrZyyNtrainTecSet.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				  // 获取实体类的所有属性，返回Field数组    
			    Field[] field = hrZyyNtrainTecSet.getClass().getDeclaredFields();
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
			                    Method m = hrZyyNtrainTecSet.getClass().getMethod("get" + name);    
			                    Double value = (Double) m.invoke(hrZyyNtrainTecSet);    
			                    //System.out.println("数据类型为：Double");    
			                    if (value != null) {
			                    	sum+=value; 
			                    } else { 
			                    	continue;
			                    }    
			            }    
			           
			    }
			    hrZyyNtrainTecSet.setTot_score(sum);
			}   
			hrZyyNtrainTecSetMapper.deleteHrZyyNtrainTecSet(alllistVo);
			hrZyyNtrainTecSetMapper.addBatch(alllistVo);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());


		}
	}
	/**
	 * 查询数据
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrZyyNtrainTecSet> list = (List<HrZyyNtrainTecSet>) hrZyyNtrainTecSetMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			
			List<HrZyyNtrainTecSet> list = (List<HrZyyNtrainTecSet>) hrZyyNtrainTecSetMapper
					.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	
	@Override
	public String deleteHrZyyNtrainTecSet(List<HrZyyNtrainTecSet> entityList)
			throws DataAccessException {
		try {
			hrZyyNtrainTecSetMapper.deleteHrZyyNtrainTecSet(entityList);
			return "{\"msg\":\"删除成功\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
/**
 * 计算
 */
	@Override
	public String mathHrZyyNtrainTecSet(Map<String, Object> entityMap) throws DataAccessException {
try {
						
			List<HrZyyNtrainTecSet> alllistVo = JSONArray.parseArray(
				String.valueOf(entityMap.get("paramVo")),
				HrZyyNtrainTecSet.class);
			for (HrZyyNtrainTecSet hrZyyNtrainTecSet : alllistVo) {
				hrZyyNtrainTecSet.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				hrZyyNtrainTecSet.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				  // 获取实体类的所有属性，返回Field数组    
			    Field[] field = hrZyyNtrainTecSet.getClass().getDeclaredFields();
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
			                    Method m = hrZyyNtrainTecSet.getClass().getMethod("get" + name);    
			                    Double value = (Double) m.invoke(hrZyyNtrainTecSet);    
			                    //System.out.println("数据类型为：Double");    
			                    if (value != null) {
			                    	sum+=value; 
			                    } else { 
			                    	continue;
			                    }    
			            }    
			           
			    }
			    hrZyyNtrainTecSet.setTot_score(sum);
			}   
			    
			hrZyyNtrainTecSetMapper.deleteHrZyyNtrainTecSet(alllistVo);
			hrZyyNtrainTecSetMapper.addBatch(alllistVo);
			return "{\"msg\":\"已计算.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

@Override
public HrZyyNtrainTecSet queryTecSet(Map<String, Object> entityMap)
		throws DataAccessException {
	return hrZyyNtrainTecSetMapper.queryTecSet(entityMap);
}

@Override
public String importZyyNtrainTecSet(Map<String, Object> entityMap)
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
				  saveMap.put("dept_name", map.get("dept_id").get(1));
				  
				  Map<String, Object>  dept = hrSelectMapper.queryDeptById(saveMap);
			  if( dept==null){
			  failureMsg.append("<br/>科室 "+map.get("dept_id" ).get(1)+" 不存在; ");
			  failureNum++; 
			  continue; 
			  }
				saveMap.put("teacher", map.get("teacher").get(1));
				saveMap.put("meet_sign", map.get("meet_sign").get(1));
				saveMap.put("skill_train", map.get("skill_train").get(1));
				saveMap.put("skill_eval", map.get("skill_eval").get(1));
				saveMap.put("drg_eval", map.get("drg_eval").get(1));
			
				//saveMap.put("physique", map.get("physique").get(1));
				saveMap.put("ade", map.get("ade").get(1));
				saveMap.put("online_reg", map.get("online_reg").get(1));
				//saveMap.put("core_class_eval", map.get("core_class_eval").get(1));
				saveMap.put("core_class_test", map.get("core_class_test").get(1));
				saveMap.put("dept_eval", map.get("dept_eval").get(1));
				saveMap.put("tot_score", map.get("tot_score").get(1));
				saveMap.put("note", map.get("note").get(1));
			
				successNum++;
				saveList.add(saveMap);
			}
			if(saveList.size() > 0){
				hrZyyNtrainTecSetMapper.addBatch(saveList);
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
public List<Map<String, Object>> queryZyyNtrainTecSetByPrint(
		Map<String, Object> entityMap) throws DataAccessException {
	 List<Map<String,Object>> list = ChdJson.toListLower(hrZyyNtrainTecSetMapper.queryZyyNtrainTecSetByPrint(entityMap));
	 return list;
}

}
