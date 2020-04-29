package com.chd.hrp.sys.serviceImpl;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.htc.entity.task.basic.HtcPeopleDict;
import com.chd.hrp.htc.entity.task.basic.HtcPeopleTitleDict;
import com.chd.hrp.htc.entity.task.basic.HtcPeopleTypeDict;
import com.chd.hrp.sys.dao.SysHisDeptMapper;
import com.chd.hrp.sys.service.SysHisDeptService;
import com.github.pagehelper.PageInfo;

@Service("sysHisDeptService")
public class SysHisDeptServiceImpl implements SysHisDeptService{
	
	private static Logger logger = Logger.getLogger(SysHisDeptServiceImpl.class);

	@Resource(name = "sysHisDeptMapper")
	private final SysHisDeptMapper sysHisDeptMapper = null;
	
	@Override
	public String querySysHisDept(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	    SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = sysHisDeptMapper.querySysHisDept(entityMap);
			
			return ChdJson.toJson(list);
			
		
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = sysHisDeptMapper.querySysHisDept(entityMap, rowBounds);
			
	        PageInfo page = new PageInfo(list); 
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public String deleteBatchSysHisDept(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			sysHisDeptMapper.deleteBatchSysHisDept(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"error\":\"删除失败 \"}";
		}
	}

	@Override
	public String importSysHisDept(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	try {
			
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
			
			 if (list.size() == 0 || list == null) {
	 				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
	 			 }
				for (Map<String, List<String>> map : list) {
					
					Map<String, Object> mapVo = new HashMap<String, Object>();
					
	                mapVo.put("his_dept_code", map.get("his_dept_code").get(1));
					
					mapVo.put("his_dept_name", map.get("his_dept_name").get(1));
					
					mapVo.put("his_dept_id", null);
					
					mapVo.put("his_dept_note", null);
					
					Map<String,Object> hisDeptMap = sysHisDeptMapper.querySysHisDeptByCode(mapVo);
					
					if(null != hisDeptMap){
						
						return "{\"error\":\""+ map.get("his_dept_code").get(0)+"HIS科室编码:"+map.get("his_dept_code").get(1)+"已经存在.\",\"state\":\"false\"}";
					}
					
					resultList.add(mapVo);
				}
			
				if(resultList.size() > 0){
					sysHisDeptMapper.addBatchSysHisDept(resultList);
				}
			
			     return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

}
