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
import com.chd.hrp.cost.dao.CostClinicWorkMapper;
import com.chd.hrp.cost.dao.CostInhosWorkMapper;
import com.chd.hrp.cost.dao.CostItemDictNoMapper;
import com.chd.hrp.cost.entity.CostClinicWork;
import com.chd.hrp.cost.entity.CostInhosWork;
import com.chd.hrp.cost.service.CostInhosWorkService;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 住院工作量表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costInhosWorkService")
public class CostInhosWorkServiceImpl implements CostInhosWorkService {

	private static Logger logger = Logger.getLogger(CostInhosWorkServiceImpl.class);
	
	@Resource(name = "costInhosWorkMapper")
	private final CostInhosWorkMapper costInhosWorkMapper = null;
	
	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;
	
	@Resource(name = "costClinicWorkMapper")
	private final CostClinicWorkMapper costClinicWorkMapper = null;
    
	/**
	 * @Description 
	 * 住院工作量表<BR> 添加CostInhosWork
	 * @param CostInhosWork entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostInhosWork(Map<String,Object> entityMap)throws DataAccessException{
		
		CostInhosWork costInhosWork = queryCostInhosWorkByCode(entityMap);

		if (costInhosWork != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}
		
		try {
			
			costInhosWorkMapper.addCostInhosWork(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostInhosWork\"}";

		}

	}
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 批量添加CostInhosWork
	 * @param  CostInhosWork entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostInhosWork(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costInhosWorkMapper.addBatchCostInhosWork(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostInhosWork\"}";

		}
	}
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 查询CostInhosWork分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostInhosWork(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostInhosWork> list = costInhosWorkMapper.queryCostInhosWork(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostInhosWork> list = costInhosWorkMapper.queryCostInhosWork(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostInhosWorkPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costInhosWorkMapper.queryCostInhosWorkPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 住院工作量表<BR> 查询CostInhosWorkByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostInhosWork queryCostInhosWorkByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costInhosWorkMapper.queryCostInhosWorkByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 批量删除CostInhosWork
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostInhosWork(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costInhosWorkMapper.deleteBatchCostInhosWork(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostInhosWork\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 删除CostInhosWork
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostInhosWork(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costInhosWorkMapper.deleteCostInhosWork(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostInhosWork\"}";

		}
    }
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 更新CostInhosWork
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostInhosWork(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costInhosWorkMapper.updateCostInhosWork(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostInhosWork\"}";

		}
	}
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 批量更新CostInhosWork
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostInhosWork(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costInhosWorkMapper.updateBatchCostInhosWork(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostInhosWork\"}";

		}
		
	}

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
				
				} else if (map2.get("patient_type_code").get(1) == null || map2.get("patient_type_code").get(1).equals("")) {
					
					return "{\"msg\":\"" + map2.get("patient_type_code").get(0) + "，患者类别编码为空！\",\"state\":\"false\",\"row_cell\":\""+ map2.get("patient_type_code").get(0) + "\"}";
				
				} else if (map2.get("in_hos_num").get(1) == null || map2.get("in_hos_num").get(1).equals("")) {
					
					return "{\"msg\":\"" + map2.get("in_hos_num").get(0) + "，入院人次为空！\",\"state\":\"false\",\"row_cell\":\""+ map2.get("in_hos_num").get(0) + "\"}";
				
				}else if (map2.get("out_hos_num").get(1) == null || map2.get("out_hos_num").get(1).equals("")) {
					
					return "{\"msg\":\"" + map2.get("out_hos_num").get(0) + "，出院人次为空！\",\"state\":\"false\",\"row_cell\":\""+ map2.get("out_hos_num").get(0) + "\"}";
				
				} else if (map2.get("set_bed_num").get(1) == null || map2.get("set_bed_num").get(1).equals("")) {
					
					return "{\"msg\":\"" + map2.get("set_bed_num").get(0) + "，编制床位数！\",\"state\":\"false\",\"row_cell\":\""+ map2.get("set_bed_num").get(0) + "\"}";
				
				} else if (map2.get("open_bed_num").get(1) == null || map2.get("open_bed_num").get(1).equals("")) {
					
					return "{\"msg\":\"" + map2.get("open_bed_num").get(0) + "，实际开放床位数为空！\",\"state\":\"false\",\"row_cell\":\""+ map2.get("open_bed_num").get(0) + "\"}";
				
//				}else if (map2.get("bed_use_rate").get(1) == null || map2.get("bed_use_rate").get(1).equals("")) {
//					
//					return "{\"msg\":\"" + map2.get("bed_use_rate").get(0) + "，床位使用率为空！\",\"state\":\"false\",\"row_cell\":\""+ map2.get("bed_use_rate").get(0) + "\"}";
//				
				}else if (map2.get("bed_use_day_num").get(1) == null || map2.get("bed_use_day_num").get(1).equals("")) {
					
					return "{\"msg\":\"" + map2.get("bed_use_day_num").get(0) + "，实际占用床日数为空！\",\"state\":\"false\",\"row_cell\":\""+ map2.get("bed_use_day_num").get(0) + "\"}";
				
				}
//				else if (map2.get("bed_out_day_num").get(1) == null || map2.get("bed_out_day_num").get(1).equals("")) {
//					
//					return "{\"msg\":\"" + map2.get("bed_out_day_num").get(0) + "，超出床日数为空！\",\"state\":\"false\",\"row_cell\":\""+ map2.get("bed_out_day_num").get(0) + "\"}";
//				
//				}
				Map<String, Object> mapVo1 = new HashMap<String, Object>();

				mapVo1.put("group_id", SessionManager.getGroupId());

				mapVo1.put("hos_id", SessionManager.getHosId());

				mapVo1.put("copy_code", SessionManager.getCopyCode());
				
				mapVo1.put("acc_year", map2.get("acc_year").get(1));
				
				mapVo1.put("acc_month", ( map2.get("acc_month").get(1).length() == 1) ? ('0' + map2.get("acc_month").get(1).toString()) : map2.get("acc_month").get(1).toString());
				
				mapVo1.put("dept_id", map2.get("dept_id").get(1));
				
				mapVo1.put("patient_type_code", map2.get("patient_type_code").get(1));
				
				mapVo1.put("in_hos_num", ( map2.get("in_hos_num").get(1)== null) ? 0 : map2.get("in_hos_num").get(1));

				mapVo1.put("out_hos_num", ( map2.get("out_hos_num").get(1)== null) ? 0 : map2.get("out_hos_num").get(1));

				mapVo1.put("set_bed_num", ( map2.get("set_bed_num").get(1)== null) ? 0 : map2.get("set_bed_num").get(1));

				mapVo1.put("open_bed_num", ( map2.get("open_bed_num").get(1)== null) ? 0 : map2.get("open_bed_num").get(1));

//				mapVo1.put("bed_use_rate", ( map2.get("bed_use_rate").get(1)== null) ? 0 : map2.get("bed_use_rate").get(1));

				mapVo1.put("bed_use_day_num", ( map2.get("bed_use_day_num").get(1)== null) ? 0 : map2.get("bed_use_day_num").get(1));
//				mapVo1.put("bed_out_day_num", ( map2.get("bed_out_day_num").get(1)== null) ? 0 : map2.get("bed_out_day_num").get(1));
				
				
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
				
			
                //判断病人类别是否存在
				Map<String, Object> mapVo_Arrt = new HashMap<String, Object>();
				mapVo_Arrt.put("group_id", mapVo1.get("group_id"));
				mapVo_Arrt.put("hos_id", mapVo1.get("hos_id"));
				mapVo_Arrt.put("copy_code", mapVo1.get("copy_code"));
				mapVo_Arrt.put("patient_code", mapVo1.get("patient_type_code"));
				
				
				CostClinicWork 	chargeKindArrt =costClinicWorkMapper.queryCostPatientType(mapVo_Arrt);
			
					if(chargeKindArrt != null){
						
						mapVo1.put("patient_type_code", chargeKindArrt.getPatient_type_code());
						
					}else{
						
						throw new SysException(map2.get("patient_type_code").get(0) + ",病人类别不存在！");
					}
					
					
					costInhosWorkMapper.addCostInhosWork(mapVo1);
				
			}
			
			
			
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		
		}
	}

	@Override
	public String deleteMonthlyCostInhosWork(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	     try {
			
	    	 costInhosWorkMapper.deleteMonthlyCostInhosWork(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			// TODO: handle exception
            logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败\"}");

		}
	}
}
