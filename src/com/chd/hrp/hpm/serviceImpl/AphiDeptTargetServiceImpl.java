package com.chd.hrp.hpm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiDeptTargetMapper;
import com.chd.hrp.hpm.entity.AphiDeptTarget;
import com.chd.hrp.hpm.service.AphiDeptTargetService;

/**
 * alfred
 */


@Service("aphiDeptTargetService")
public class AphiDeptTargetServiceImpl implements AphiDeptTargetService {

	private static Logger logger = Logger.getLogger(AphiDeptTargetServiceImpl.class);
	
	@Resource(name = "aphiDeptTargetMapper")
	private final AphiDeptTargetMapper aphiDeptTargetMapper = null;
    
	/**
	 * 
	 */
	@Override
	public String addDeptTarget(Map<String,Object> entityMap)throws DataAccessException{

		AphiDeptTarget dkt = queryDeptTargetByCode(entityMap);
		
		if(dkt != null){
			
			return "{\"error\":\"指标编码：" + entityMap.get("target_code").toString() + "重复.\"}";
			
		}
		try{
			
			aphiDeptTargetMapper.addDeptTarget(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addDeptKindTarget\"}";
						
		}
	}
	
	/**
	 * 
	 */
	@Override
	public String queryDeptTarget(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		return JsonListBeanUtil.listToJson(aphiDeptTargetMapper.queryDeptTarget(entityMap, rowBounds), sysPage.getTotal());
		
	}
	
	/**
	 * 
	 */
	@Override
	public AphiDeptTarget queryDeptTargetByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return aphiDeptTargetMapper.queryDeptTargetByCode(entityMap);
		
	}
	
	/**
	 * 
	 */
	@Override
	public String deleteDeptTarget(Map<String,Object> entityMap,String codes)throws DataAccessException{
		
		try{
			
			if (codes != null && !codes.equals("")) {
				
				String[] code_codeArray = codes.split(",");
				
				for (String code : code_codeArray) {	
					
					String[] code_array = code.split(";");
					
					entityMap.put("target_code", code_array[0]);
					
					entityMap.put("dept_kind_code", code_array[1]);
					
					aphiDeptTargetMapper.deleteDeptTarget(entityMap);
				}
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} else {
				
				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
				
			}
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteDeptTarget\"}";
			
		}
	}
	
	/**
	 * 
	 */
	@Override
	public String updateDeptTarget(Map<String,Object> entityMap)throws DataAccessException{
		
		try{
			
			aphiDeptTargetMapper.updateDeptTarget(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDeptTarget\"}";
			
		}
	}

	@Override
	public String addBatchDeptTarget(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		int state =aphiDeptTargetMapper.addBatchDeptTarget(entityMap);
		if(state>0){
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		}
		return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
	}
}
