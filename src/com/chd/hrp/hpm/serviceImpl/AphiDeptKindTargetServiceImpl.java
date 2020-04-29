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
import com.chd.hrp.hpm.dao.AphiDeptKindTargetMapper;
import com.chd.hrp.hpm.entity.AphiDeptKindTarget;
import com.chd.hrp.hpm.service.AphiDeptKindTargetService;

/**
 * alfred
 */

@Service("aphiDeptKindTargetService")
public class AphiDeptKindTargetServiceImpl implements AphiDeptKindTargetService {

	private static Logger logger = Logger.getLogger(AphiDeptKindTargetServiceImpl.class);

	@Resource(name = "aphiDeptKindTargetMapper")
	private final AphiDeptKindTargetMapper aphiDeptKindTargetMapper = null;

	/**
	 * 
	 */
	@Override
	public String addDeptKindTarget(Map<String, Object> entityMap) throws DataAccessException {
		
		AphiDeptKindTarget dkt = queryDeptKindTargetByCode(entityMap);
		
		if(dkt != null){
			
			return "{\"error\":\"指标编码：" + entityMap.get("target_code").toString() + "重复.\"}";
			
		}
		try{
			
			aphiDeptKindTargetMapper.addDeptKindTarget(entityMap);
			
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
	public String queryDeptKindTarget(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		return JsonListBeanUtil.listToJson(aphiDeptKindTargetMapper.queryDeptKindTarget(entityMap, rowBounds), sysPage.getTotal());
		
	}

	/**
	 * 
	 */
	@Override
	public AphiDeptKindTarget queryDeptKindTargetByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return aphiDeptKindTargetMapper.queryDeptKindTargetByCode(entityMap);
		
	}

	/**
	 * 
	 */
	@Override
	public String deleteDeptKindTarget(Map<String,Object> mapVo,String codes) throws DataAccessException {

		try{
			
			if (codes != null && !codes.equals("")) {
				
				String[] code_array = codes.split(",");
				
				for (String code : code_array) {
					
					mapVo.put("target_code", code);
					
					aphiDeptKindTargetMapper.deleteDeptKindTarget(mapVo);
				}
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} else {
				
				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
				
			}
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteDeptKindTarget\"}";
			
		}
		
	}

	/**
	 * 
	 */
	@Override
	public String updateDeptKindTarget(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			
			aphiDeptKindTargetMapper.updateDeptKindTarget(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDeptKindTarget\"}";
			
		}
	}

	@Override
	public String addBatchDeptKindTarget(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		int state =aphiDeptKindTargetMapper.addBatchDeptKindTarget(entityMap);
		if(state>0){
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		}
		return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
	}
}
