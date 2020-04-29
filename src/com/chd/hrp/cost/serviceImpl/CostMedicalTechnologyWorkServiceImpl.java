/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

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
import com.chd.hrp.cost.dao.CostMedicalTechnologyWorkMapper;
import com.chd.hrp.cost.entity.CostMedicalTechnologyWork;
import com.chd.hrp.cost.service.CostMedicalTechnologyWorkService;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

@Service("costMedicalTechnologyWorkService")
public class CostMedicalTechnologyWorkServiceImpl implements CostMedicalTechnologyWorkService {

	private static Logger logger = Logger.getLogger(CostMedicalTechnologyWorkServiceImpl.class);

	@Resource(name = "costMedicalTechnologyWorkMapper")
	private final CostMedicalTechnologyWorkMapper costMedicalTechnologyWorkMapper = null;

	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;
	@Override
	public String addCostMedicalTechnologyWork(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			CostMedicalTechnologyWork costMedicalTechnologyWork = costMedicalTechnologyWorkMapper.queryCostMedicalTechnologyWorkByCode(mapVo);
			
			if(null != costMedicalTechnologyWork){
				
				return "{\"error\":\"当月科室已经存在.\",\"state\":\"false\"}";
			}
			
			costMedicalTechnologyWorkMapper.addCostMedicalTechnologyWork(mapVo);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		
	}

	@Override
	public String queryCostMedicalTechnologyWork(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
		 sysPage = (SysPage) mapVo.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostMedicalTechnologyWork> list = costMedicalTechnologyWorkMapper.queryCostMedicalTechnologyWork(mapVo);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostMedicalTechnologyWork> list = costMedicalTechnologyWorkMapper.queryCostMedicalTechnologyWork(mapVo, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteCostMedicalTechnologyWork(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			costMedicalTechnologyWorkMapper.deleteCostMedicalTechnologyWork(mapVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String deleteBatchCostMedicalTechnologyWork(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
		
         try {
			
        	 costMedicalTechnologyWorkMapper.deleteBatchCostMedicalTechnologyWork(list);
        	 
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public CostMedicalTechnologyWork queryCostMedicalTechnologyWorkByCode(
			Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return costMedicalTechnologyWorkMapper.queryCostMedicalTechnologyWorkByCode(mapVo);
	}

	@Override
	public String updateCostMedicalTechnologyWork(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
	    try {
	    	
       	    costMedicalTechnologyWorkMapper.updateCostMedicalTechnologyWork(mapVo);
       	 
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String ImpCostMedicalTechnologyWork(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
			
		try {
			
            List<Map<String, List<String>>> list = ReadFiles.readFiles(mapVo);
			
			List<Map<String,Object>> resultSet = new ArrayList<Map<String,Object>>();
			
			for (Map<String, List<String>> map : list) {
				
				Map<String, Object>  resultMap = new HashMap<String, Object>();
				
				resultMap.put("group_id", SessionManager.getGroupId());
				
				resultMap.put("hos_id", SessionManager.getHosId());
				
				resultMap.put("copy_code", SessionManager.getCopyCode());
				
				resultMap.put("acc_year", map.get("acc_year").get(1));
				
				resultMap.put("acc_month", map.get("acc_month").get(1));
				
				//判断科室是否存在
				Map<String, Object> deptDictMap = new HashMap<String, Object>();
				deptDictMap.put("group_id", SessionManager.getGroupId());
				deptDictMap.put("hos_id", SessionManager.getHosId());
				deptDictMap.put("copy_code", SessionManager.getCopyCode());
				deptDictMap.put("dept_code", map.get("dept_code").get(1));
				deptDictMap.put("is_stop", 0);
				DeptDict deptDict = deptDictMapper.queryDeptDictByDeptCode(deptDictMap);
				
				if(null == deptDict){
					
					return "{\"error\":\""+ map.get("dept_code").get(0)+"科室:"+map.get("dept_code").get(1)+"不存在.\",\"state\":\"false\"}";
				
				}else {
					resultMap.put("dept_id", deptDict.getDept_id());
					resultMap.put("dept_no", deptDict.getDept_no());
				}
				
				resultMap.put("medical_num", map.get("medical_num").get(1));
				
				CostMedicalTechnologyWork costMedicalTechnologyWork = costMedicalTechnologyWorkMapper.queryCostMedicalTechnologyWorkByCode(resultMap);
				
				if(null != costMedicalTechnologyWork){
					
					return "{\"error\":\""+ map.get("dept_code").get(0)+"科室:"+map.get("dept_code").get(1)+"数据已存在.\",\"state\":\"false\"}";
				}
				
 
				costMedicalTechnologyWorkMapper.addCostMedicalTechnologyWork(resultMap);
			 }
		
		     return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		     
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

	
}
