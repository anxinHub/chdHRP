package com.chd.hrp.sys.serviceImpl.portal;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.hrp.sys.dao.portal.SysPortalTitleSetMapper;
import com.chd.hrp.sys.entity.SysPortalTitleSet;
import com.chd.hrp.sys.service.portal.SysPortalTitleSetService;

@Service("sysPortalTitleSetService")
public class SysPortalTitleSetServiceImpl implements SysPortalTitleSetService{
	
	private static Logger logger = Logger.getLogger(SysPortalTitleSetServiceImpl.class);
	
	@Resource (name="sysPortalTitleSetMapper")
	private final SysPortalTitleSetMapper  sysPortalTitleSetMapper = null ;
	
	@Override
	public String addSysPortalTitleSet(Map<String, Object> entityMap) throws DataAccessException {
		
		sysPortalTitleSetMapper.delete(entityMap);
		
		try {

			sysPortalTitleSetMapper.addSysPortalTitleSet(entityMap);

			return "{\"msg\":\"设置成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"设置失败 数据库异常 请联系管理员! 错误编码 addSysPortalTitleSet\"}";

		}

	}

	@Override
	public String addBatchSysPortalTitleSet(List<Map<String,Object>> list) throws DataAccessException {
		
		try {

			sysPortalTitleSetMapper.addBatchSysPortalTitleSet(list);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败!\"}");

		}
	}

	@Override
	public String deleteSysPortalTitleSet(Map<String, Object> mapVo) throws DataAccessException {
		try {
			
			sysPortalTitleSetMapper.delete(mapVo);
		

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败!\"}");
			
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 addSysPortalTitleSet\"}";
	
		}
	}


}
