package com.chd.hrp.hpm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.service.AphiEmpDutyService;
import com.chd.hrp.hpm.dao.AphiEmpDutyMapper;
import com.chd.hrp.hpm.entity.AphiEmpDuty;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("aphiEmpDutyService")
public class AphiEmpDutyServiceImpl implements AphiEmpDutyService {

	private static Logger logger = Logger.getLogger(AphiEmpDutyServiceImpl.class);
	
	@Resource(name = "aphiEmpDutyMapper")
	private final AphiEmpDutyMapper aphiEmpDutyMapper = null;
    
	/**
	 * 
	 */
	@Override
	public String addPrmEmpDuty(Map<String,Object> entityMap)throws DataAccessException{
		
			try{
				
				int state = aphiEmpDutyMapper.addPrmEmpDuty(entityMap);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
				
			}catch (Exception e) {
				
				logger.error(e.getMessage(), e);
				
				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addItem\"}";
							
			}
	}
	
	/**
	 * 
	 */
	@Override
	public String queryEmpDuty(Map<String,Object> entityMap) throws DataAccessException{
		
		
             SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AphiEmpDuty> list = aphiEmpDutyMapper.queryPrmEmpDuty(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AphiEmpDuty> list = aphiEmpDutyMapper.queryPrmEmpDuty(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}


	@Override
	public String deleteEmpDuty(Map<String, Object> entityMap, String duty_code)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateEmpDuty(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String deleteBatchPrmEmpDuty(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			aphiEmpDutyMapper.deleteBatchPrmEmpDuty(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法deleteBatchPrmEmpDuty\"}";

		}
	}
	
	/**
	 * @Description 获取对象8803 职务字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AphiEmpDuty queryPrmEmpDutyByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return aphiEmpDutyMapper.queryPrmEmpDutyByCode(entityMap);
	}


	/**
	 * 
	 */
	@Override
	public String queryHpmSysEmpDuty(Map<String,Object> entityMap) throws DataAccessException{
		
		
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = aphiEmpDutyMapper.queryHpmSysEmpDuty(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = aphiEmpDutyMapper.queryHpmSysEmpDuty(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
}
