/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.serviceImpl.training.plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chd.base.util.UUIDLong;
import com.chd.hrp.hr.dao.training.plant.HrPlantMapper;
import com.chd.hrp.hr.entity.training.plant.HrPlant;
import com.chd.hrp.hr.service.training.plant.HrPlantService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 培训计划
 * @Table: HR_way
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("hrPlantService")
public class HrPlantServiceImpl implements HrPlantService {

	private static Logger logger = Logger.getLogger(HrPlantServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "hrPlantMapper")
	private final HrPlantMapper hrPlantMapper = null;

	/**
	 * @Description 添加培训计划<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPlant(Map<String, Object> entityMap) throws DataAccessException {


		//获取对象培训计划
	List<HrPlant> hrPlant = hrPlantMapper.queryPlantByName(entityMap);

	if (hrPlant.size() > 0) {

		for (HrPlant hrDutyLevel : hrPlant) {
			
			if (hrDutyLevel.getTrain_title().equals(entityMap.get("train_title"))) {
				return "{\"error\":\"培训主题：" + hrDutyLevel.getTrain_title().toString() + "已存在.\"}";
			}
		}
	}
	
      String  plant_id=UUIDLong.absStringUUID(); 
      entityMap.put("plan_id", plant_id);
      entityMap.put("year", SessionManager.getAcctYear());
		try {
			//获取对象培训计划
			List<HrPlant> list = hrPlantMapper.queryPlantById(entityMap);
			if(list.size() > 0){
				return "{\"error\":\"存在同样的培训计划.\"}";
			}
			int state = hrPlantMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新培训计划<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePlant(Map<String, Object> entityMap) throws DataAccessException {
		//获取对象培训计划
	List<HrPlant> list = hrPlantMapper.queryPlantByIdName(entityMap);

	if (list.size() > 0) {

		
				return "{\"error\":\"存在相同的培训计划.\"}";
		
	}

	//获取对象培训计划
List<HrPlant> hrPlant = hrPlantMapper.queryPlantByIdOrName(entityMap);

if (hrPlant.size() > 0) {

	for (HrPlant hrDutyLevel : hrPlant) {
		
		if (hrDutyLevel.getTrain_title().equals(entityMap.get("train_title"))) {
			return "{\"error\":\"培训主题：" + hrDutyLevel.getTrain_title().toString() + "已存在.\"}";
		}
	}
}
//查询是否存在培训证书

		try {
			 entityMap.put("year", SessionManager.getAcctYear());
			int state = hrPlantMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集培训计划<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPlant(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrPlant> list = (List<HrPlant>) hrPlantMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrPlant> list = (List<HrPlant>) hrPlantMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 删除培训计划
	 */
	@Override
	public String deletePlant(List<HrPlant> entityList) throws DataAccessException {

		try {

			hrPlantMapper.deletePlant(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 修改页面跳转查询
	 */
	@Override
	public HrPlant queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrPlantMapper.queryByCode(entityMap);
	}

	@Override
	public String importDate(Map<String, Object> entityMap)
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
				Map<String, Boolean> trainTitleMap = new HashMap<String, Boolean>();
				for (Map<String, List<String>> map : list) {
					//过滤空行
					if("".equals(map.get("train_type_code").get(1)) || "".equals(map.get("dept_id").get(1))|| "".equals(map.get("year").get(1))
							|| "".equals(map.get("month").get(1))|| "".equals(map.get("train_title").get(1))|| "".equals(map.get("train_way_code").get(1))
							|| "".equals(map.get("train_object").get(1))|| "".equals(map.get("is_exam").get(1))|| "".equals(map.get("is_cert").get(1))){
						continue;
					}
					
					if("null".equals(map.get("train_type_code").get(1)) || "null".equals(map.get("dept_id").get(1))|| "null".equals(map.get("year").get(1))
							|| "null".equals(map.get("month").get(1))|| "null".equals(map.get("train_title").get(1))|| "null".equals(map.get("train_way_code").get(1))
							|| "null".equals(map.get("train_object").get(1))|| "null".equals(map.get("is_exam").get(1))|| "null".equals(map.get("is_cert").get(1))){
						continue;
					}
					
//					for (Map<String, Object> ma : saveList) {
//						if(ma.get("train_title").toString().equals(map.get("train_title").get(1).toString())){
//							failureMsg.append("<br/>培训主题 "+map.get("train_title")+" 存在重复数据; ");
//							failureNum++;
//							continue;
//						}
//					}
					
					if (trainTitleMap.containsKey(map.get("train_title").get(1).toString())) {
						if(trainTitleMap.get(map.get("train_title").get(1).toString())){
							failureMsg.append("<br/>培训主题 " + map.get("train_title") + " 存在重复数据; ");
							trainTitleMap.put(map.get("train_title").get(1).toString(), false);
						}
						failureNum++;
					} else {
						trainTitleMap.put(map.get("train_title").get(1).toString(), true);
					}
					
					
					if(failureNum>0){
						continue;
					}
					
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					
					 String  plant_id=UUIDLong.absStringUUID(); 
					 saveMap.put("plan_id", plant_id);
					/**
					 * 科室信息
					 */
					List<Map<String, Object>> deptList = hrPlantMapper.queryDeptInfo(saveMap);
					Map<String, String> deptMap = new HashMap<String, String>();
					for(Map<String, Object> dept : deptList){
						//deptMap.put(dept.get("DEPT_ID").toString(), dept.get("DEPT_ID").toString());
						deptMap.put(dept.get("DEPT_CODE").toString(), dept.get("DEPT_ID").toString());
						deptMap.put(dept.get("DEPT_NAME").toString(), dept.get("DEPT_ID").toString());
						//deptMap.put(dept.get("DEPT_NO").toString(), dept.get("DEPT_NO").toString());
					}
					/**
					 * 培训类别
					 */
					List<Map<String, Object>> trainTypeList = hrPlantMapper.queryTrainTypeInfo(saveMap);
					Map<String, String> trainTypeMap = new HashMap<String, String>();
					for(Map<String, Object> trainType : trainTypeList){
						trainTypeMap.put(trainType.get("TYPE_CODE").toString(), trainType.get("TYPE_CODE").toString());
						trainTypeMap.put(trainType.get("TYPE_NAME").toString(), trainType.get("TYPE_CODE").toString());
					}
					/**
					 * 培训方式
					 */
					List<Map<String, Object>> trainWayList = hrPlantMapper.queryTrainWayInfo(saveMap);
					Map<String, String> trainWayMap = new HashMap<String, String>();
					for(Map<String, Object> trainWay : trainWayList){
						trainWayMap.put(trainWay.get("WAY_CODE").toString(), trainWay.get("WAY_CODE").toString());
						trainWayMap.put(trainWay.get("WAY_NAME").toString(), trainWay.get("WAY_CODE").toString());
					}
					if("".equals(deptMap.get( map.get("dept_id").get(1))) || deptMap.get( map.get("dept_id").get(1)) ==null){
						failureMsg.append("<br/>科室信息: "+ map.get("dept_id").get(1)+" 不存在; ");
						failureNum++;
						continue;
					}else{
						
						saveMap.put("dept_id", deptMap.get(map.get("dept_id").get(1)));
						String dept_no =hrPlantMapper.queryDeptNo(saveMap);
						saveMap.put("dept_no", dept_no);
						
					}
					if("".equals(trainTypeMap.get( map.get("train_type_code").get(1))) || trainTypeMap.get( map.get("train_type_code").get(1)) ==null){
						failureMsg.append("<br/>培训类别: "+ map.get("train_type_code").get(1)+" 不存在; ");
						failureNum++;
						continue;
					}else{	
						saveMap.put("train_type_code",trainTypeMap.get( map.get("train_type_code").get(1)));
						
					}
					if("".equals(trainWayMap.get( map.get("train_way_code").get(1))) || trainWayMap.get( map.get("train_way_code").get(1)) ==null){
						failureMsg.append("<br/>培训方式: "+ map.get("train_way_code").get(1)+" 不存在; ");
						failureNum++;
						continue;
					}else{
						saveMap.put("train_way_code", trainWayMap.get( map.get("train_way_code").get(1)));
					}
					
					saveMap.put("year", map.get("year").get(1).toString().replaceAll("\\D", ""));
					int month = Integer.valueOf(map.get("month").get(1).toString().replaceAll("\\D", ""));
					if(month <1 || month > 12){
						failureMsg.append("<br/>培训月份: "+ map.get("train_way_code").get(1)+" 错误; ");
						failureNum++;
						continue;
					}
					saveMap.put("month", String.format("%02d", month));
//					if(map.get("month").get(1).length()==1){
//						saveMap.put("month", 0+map.get("month").get(1));
//					}else{
//						saveMap.put("month", map.get("month").get(1));
//					}
					
					saveMap.put("train_title", map.get("train_title").get(1));
					saveMap.put("train_object",map.get("train_object").get(1));
					saveMap.put("is_exam",whetherMap.get(map.get("is_exam").get(1)));
					if(whetherMap.get(map.get("is_exam").get(1)).toString().equals("0")){
						saveMap.put("is_cert", "0");
					}else{
						saveMap.put("is_cert", whetherMap.get(map.get("is_cert").get(1)));
					}
					
					//获取对象培训计划
					List<HrPlant> list1 = hrPlantMapper.queryPlantById(saveMap);
					if(list1.size() > 0){
						failureMsg.append("<br/>已经存在相同的培训计划; ");
						failureNum++;
						continue;
					}
					//获取对象培训计划
					List<HrPlant> hrPlant = hrPlantMapper.queryPlantByName(saveMap);

					if (hrPlant.size() > 0) {

						for (HrPlant hrDutyLevel : hrPlant) {
							
							if (hrDutyLevel.getTrain_title().equals(map.get("train_title").get(1))) {
								failureMsg.append("<br/>已经存在相同的培训主题; ");
								
								//return "{\"error\":\"培训主题：" + hrDutyLevel.getTrain_title().toString() + "已存在.\"}";
							}
						}
						failureNum++;
						continue;
					}
					saveMap.put("credit_hour", map.get("credit_hour").get(1));
					saveMap.put("note", map.get("note").get(1));					
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrPlantMapper.addBatch(saveList);
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 "+successNum+"条"+failureMsg+"\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}

	@Override
	public int queryUsePlant(HrPlant hrPlant)
			throws DataAccessException {
		return hrPlantMapper.queryUsePlant(hrPlant);
	}

	@Override
	public int queryByCodeCert(Map<String, Object> mapVo)
			throws DataAccessException {
		return hrPlantMapper.queryByCodeCert(mapVo);
	}

	@Override
	public int queryByCodeExam(Map<String, Object> mapVo)
			throws DataAccessException {
		return hrPlantMapper.queryByCodeExam(mapVo);
	}

	@Override
	public int queryUserPlan(List<HrPlant> listVo) throws DataAccessException {
		return hrPlantMapper.queryUserPlan(listVo);
	}

	@Override
	public String deletePlantBach(List<HrPlant> listVo)
			throws DataAccessException {

		try {

			hrPlantMapper.deletePlantBach(listVo);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

}
