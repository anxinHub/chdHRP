/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.cost.dao.CostDeptPeopleMapper;
import com.chd.hrp.cost.entity.CostClinicWork;
import com.chd.hrp.cost.entity.CostDeptPeople;
import com.chd.hrp.cost.service.CostDeptPeopleService;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 科室人员表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costDeptPeopleService")
public class CostDeptPeopleServiceImpl implements CostDeptPeopleService {

	private static Logger logger = Logger.getLogger(CostDeptPeopleServiceImpl.class);
	
	@Resource(name = "costDeptPeopleMapper")
	private final CostDeptPeopleMapper costDeptPeopleMapper = null;
	
	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;
    
	/**
	 * @Description 
	 * 科室人员表<BR> 添加CostDeptPeople
	 * @param CostDeptPeople entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostDeptPeople(Map<String,Object> entityMap)throws DataAccessException{
		
		CostDeptPeople costDeptPeople = queryCostDeptPeopleByCode(entityMap);

		if (costDeptPeople != null) {

			//return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";
			
			return "{\"error\":\"" + "" +costDeptPeople.getAcc_year()+costDeptPeople.getAcc_month() +" ,"+ costDeptPeople.getDept_name() + "重复.\"}";

		}
		
		try {
			
			costDeptPeopleMapper.addCostDeptPeople(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostDeptPeople\"}";

		}

	}
	
	/**
	 * @Description 
	 * 科室人员表<BR> 批量添加CostDeptPeople
	 * @param  CostDeptPeople entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostDeptPeople(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costDeptPeopleMapper.addBatchCostDeptPeople(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostDeptPeople\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室人员表<BR> 查询CostDeptPeople分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostDeptPeople(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostDeptPeople> list = costDeptPeopleMapper.queryCostDeptPeople(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostDeptPeople> list = costDeptPeopleMapper.queryCostDeptPeople(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostDeptPeoplePrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costDeptPeopleMapper.queryCostDeptPeoplePrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 科室人员表<BR> 查询CostDeptPeopleByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostDeptPeople queryCostDeptPeopleByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costDeptPeopleMapper.queryCostDeptPeopleByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 科室人员表<BR> 批量删除CostDeptPeople
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostDeptPeople(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costDeptPeopleMapper.deleteBatchCostDeptPeople(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostDeptPeople\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 科室人员表<BR> 删除CostDeptPeople
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostDeptPeople(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costDeptPeopleMapper.deleteCostDeptPeople(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostDeptPeople\"}";

		}
    }
	
	/**
	 * @Description 
	 * 科室人员表<BR> 更新CostDeptPeople
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostDeptPeople(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costDeptPeopleMapper.updateCostDeptPeople(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostDeptPeople\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室人员表<BR> 批量更新CostDeptPeople
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostDeptPeople(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costDeptPeopleMapper.updateBatchCostDeptPeople(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostDeptPeople\"}";

		}
		
	}

	@Override
	public String addPeopleCollect(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			//清除已存在数据
			costDeptPeopleMapper.deleteCostDeptPeople(entityMap);
			//添加数据
			costDeptPeopleMapper.addPeopleCollect(entityMap);

			return "{\"msg\":\"采集成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"采集失败\"}");

		}
		
	}
	 
	@Override
	public String extendCostDeptPeople(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
	        String b_year_month = entityMap.get("end_year").toString() + entityMap.get("end_month").toString();
	        
	        String e_year_month = entityMap.get("end_year").toString() + entityMap.get("end_month").toString();
	        
	        entityMap.put("b_year_month", b_year_month);
	        
	        entityMap.put("e_year_month", e_year_month);
	        
	        costDeptPeopleMapper.deleteMonthlyCostDeptPeople(entityMap);
			
			costDeptPeopleMapper.extendCostDeptPeople(entityMap);
			
			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"继承失败\"}");
		}
	}
	
	
   /**
    * 导入
    * @param mapVo
    * @return
    */
	public String readAssFinaDictFiles(Map<String, Object> map) {
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(map);
		if (list.size() == 0 || list == null) {
			return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
		}
		for (Map<String, List<String>> map2 : list) {
			if (map2.get("acc_year").get(1) == null || map2.get("acc_year").get(1).equals("")) {
			
				return "{\"msg\":\"" + map2.get("acc_year").get(0)+ "，统计年度为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("acc_year").get(0)+ "\"}";
			
			} else if (map2.get("acc_month").get(1) == null || map2.get("acc_month").get(1).equals("")) {
				
				return "{\"msg\":\"" + map2.get("acc_month").get(0)+ "，统计月份为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("acc_month").get(0)+ "\"}";
			
			} else if (map2.get("dept_id").get(1) == null || map2.get("dept_id").get(1).equals("")) {
			
				return "{\"msg\":\"" + map2.get("dept_id").get(0)+ "，科室编码为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("dept_id").get(0) + "\"}";
			
			} 
			
			
			
			Map<String, Object> mapVo1 = new HashMap<String, Object>();

			mapVo1.put("group_id", SessionManager.getGroupId());

			mapVo1.put("hos_id", SessionManager.getHosId());

			mapVo1.put("copy_code", SessionManager.getCopyCode());
			
			mapVo1.put("acc_year", map2.get("acc_year").get(1));
			
			mapVo1.put("acc_month", ( map2.get("acc_month").get(1).length() == 1) ? ('0' + map2.get("acc_month").get(1).toString()) : map2.get("acc_month").get(1).toString());
			
			mapVo1.put("dept_id", map2.get("dept_id").get(1));
			
			mapVo1.put("doctor_num", ( map2.get("doctor_num").get(1)== null) ? 0 : map2.get("doctor_num").get(1));
			
			mapVo1.put("nurse_num", ( map2.get("nurse_num").get(1)== null) ? 0 : map2.get("nurse_num").get(1));
			
			mapVo1.put("technician_num", ( map2.get("technician_num").get(1)== null) ? 0 : map2.get("technician_num").get(1));

			mapVo1.put("pharmacist_num", ( map2.get("pharmacist_num").get(1)== null) ? 0 : map2.get("pharmacist_num").get(1));

			mapVo1.put("manager_num", ( map2.get("manager_num").get(1)== null) ? 0 : map2.get("manager_num").get(1));

			mapVo1.put("supportor_num", ( map2.get("supportor_num").get(1)== null) ? 0 : map2.get("supportor_num").get(1));

			mapVo1.put("floater_num", ( map2.get("floater_num").get(1)== null) ? 0 : map2.get("floater_num").get(1));

			mapVo1.put("out_employ_num", ( map2.get("out_employ_num").get(1)== null) ? 0 : map2.get("out_employ_num").get(1));

			mapVo1.put("clearner_num", ( map2.get("clearner_num").get(1)== null) ? 0 : map2.get("clearner_num").get(1));

			//mapVo1.put("alldept_num", ( map2.get("alldept_num").get(1)== null) ? 0 : map2.get("alldept_num").get(1));

			
			//判断执行科室是否存在
			Map<String, Object> mapVo_dept = new HashMap<String, Object>();
			mapVo_dept.put("group_id", mapVo1.get("group_id"));
			mapVo_dept.put("hos_id", mapVo1.get("hos_id"));
			mapVo_dept.put("copy_code", mapVo1.get("copy_code"));
			mapVo_dept.put("dept_code", mapVo1.get("dept_id"));
			
			DeptDict  deptDict1 = deptDictMapper.queryDeptDictByCodeDept(mapVo_dept);
			
			if(deptDict1 != null){
				
				mapVo1.put("dept_no", deptDict1.getDept_no());
				
				mapVo1.put("dept_id", deptDict1.getDept_id());
				
			}else{
				throw new SysException(map2.get("dept_id").get(0) + ",科室不存在！");							
				
			}
			
			costDeptPeopleMapper.addCostDeptPeople(mapVo1);
	   	}
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		
		
		}
	}

			@Override
			public String deleteMonthlyCostDeptPeople(Map<String, Object> entityMap)
					throws DataAccessException {
				// TODO Auto-generated method stub
			     try {
						
			    	 costDeptPeopleMapper.deleteMonthlyCostDeptPeople(entityMap);
					
					return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

				}
				catch (Exception e) {

					// TODO: handle exception
		            logger.error(e.getMessage(), e);
					
					throw new SysException("{\"error\":\"删除失败\"}");

				}
			     
			}

}
