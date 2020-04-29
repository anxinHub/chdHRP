package com.chd.hrp.cost.serviceImpl;

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
import com.chd.hrp.cost.dao.CostDoctorWorkMapper;
import com.chd.hrp.cost.entity.CostDoctorWork;
import com.chd.hrp.cost.service.CostDoctorWorkService;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.dao.EmpDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.entity.EmpDict;
import com.github.pagehelper.PageInfo;


@Service("costDoctorWorkService")
public class CostDoctorWorkServiceImpl implements CostDoctorWorkService{

	
	private static Logger logger = Logger.getLogger(CostDoctorWorkServiceImpl.class);
	
	
	@Resource(name = "costDoctorWorkMapper")
	private final CostDoctorWorkMapper costDoctorWorkMapper = null;
	
	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;
	
	@Resource(name = "empDictMapper")
	private final EmpDictMapper empDictMapper = null;
	
	@Override
	public String addCostDoctorWork(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			CostDoctorWork costDoctorWork = costDoctorWorkMapper.queryCostDoctorWorkByCode(entityMap);
			
			if(null !=costDoctorWork){
				
				return "{\"error\":\"科室医生已存在.\",\"state\":\"false\"}";
			}
			
			costDoctorWorkMapper.addCostDoctorWork(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			
		}
	}

	@Override
	public String queryCostDoctorWork(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostDoctorWork> list = costDoctorWorkMapper.queryCostDoctorWork(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostDoctorWork> list = costDoctorWorkMapper.queryCostDoctorWork(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public CostDoctorWork queryCostDoctorWorkByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return costDoctorWorkMapper.queryCostDoctorWorkByCode(entityMap);
	}

	@Override
	public String deleteBatchCostDoctorWork(List<Map<String, Object>> map)
			throws DataAccessException {
		// TODO Auto-generated method stub
	   try {
			
			costDoctorWorkMapper.deleteBatchCostDoctorWork(map);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");
			
		}
	}

	@Override
	public String updateCostDoctorWork(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		   try {
				
				costDoctorWorkMapper.updateCostDoctorWork(entityMap);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);

				throw new SysException("{\"error\":\"修改失败\"}");
				
			}
	}

	@Override
	public String impCostDoctorWork(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	try {
			
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			
			List<Map<String,Object>> resultSet = new ArrayList<Map<String,Object>>();
			
			 if (list.size() == 0 || list == null) {
	 				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
	 			 }
	
			for (Map<String, List<String>> map : list) {
				
					Map<String, Object> mapVo = new HashMap<String, Object>();
					
	                mapVo.put("group_id", SessionManager.getGroupId());
					
					mapVo.put("hos_id", SessionManager.getHosId());
					
					mapVo.put("copy_code", SessionManager.getCopyCode());
					
	                mapVo.put("acc_year", map.get("year_month").get(1).substring(0, 4));
	                
	                mapVo.put("acc_month", map.get("year_month").get(1).substring(4, 6));
	                
					mapVo.put("dept_code", map.get("dept_code").get(1));
					
					mapVo.put("dept_name", map.get("dept_name").get(1));
					
					mapVo.put("emp_code", map.get("emp_code").get(1));
					
					mapVo.put("emp_name", map.get("emp_name").get(1));
					
					mapVo.put("doctor_num", map.get("doctor_num").get(1));
					
					//判断科室是否存在
					Map<String, Object> deptMapVo = new HashMap<String, Object>();
					
					deptMapVo.put("group_id", mapVo.get("group_id"));
					
					deptMapVo.put("hos_id", mapVo.get("hos_id"));
					
					deptMapVo.put("dept_code", mapVo.get("dept_code"));
					
					DeptDict deptDict = deptDictMapper.queryDeptDictByCode(deptMapVo);
					
					if(null == deptDict){
						
						return "{\"error\":\""+ map.get("dept_code").get(0)+"科室:"+map.get("dept_code").get(1)+"不存在!.\",\"state\":\"false\"}";
						
					}else {
						
						mapVo.put("dept_id", deptDict.getDept_id());
						
						mapVo.put("dept_no", deptDict.getDept_no());
					}
					
					//判断人员是否存在
					Map<String, Object> empMapVo = new HashMap<String, Object>();
					
					empMapVo.put("group_id", mapVo.get("group_id"));
					
					empMapVo.put("hos_id", mapVo.get("hos_id"));
					
					empMapVo.put("emp_code", mapVo.get("emp_code"));
					
					EmpDict empDict = empDictMapper.queryEmpDictByCode(empMapVo);
					
	                if(null == empDict){
						
						return "{\"error\":\""+ map.get("emp_code").get(0)+"人员:"+map.get("emp_code").get(1)+"不存在!.\",\"state\":\"false\"}";
						
					}else {
						
						mapVo.put("emp_id", empDict.getEmp_id());
						
						mapVo.put("emp_no", empDict.getEmp_no());
					}
	
	                
	                CostDoctorWork costDoctorWork = costDoctorWorkMapper.queryCostDoctorWorkByCode(mapVo);
	                
	                if(null != costDoctorWork){
	                	
	                	return "{\"error\":\""+ map.get("year_month").get(0)+"核算月份:"+map.get("year_month").get(1)+"已经存在.\",\"state\":\"false\"}";
	                }
					
					resultSet.add(mapVo);
				  
			    }
			
			     if(resultSet.size() >0){
			    	 
			    	 System.out.println(resultSet);
			    	 costDoctorWorkMapper.addBatchCostDoctorWork(resultSet);
			     }
			    
			     return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

}
