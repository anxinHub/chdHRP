package com.chd.hrp.sys.serviceImpl.portal;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.sys.dao.portal.SysPortalNoticeMapper;
import com.chd.hrp.sys.dao.portal.SysPortalTitleUserMapper;
import com.chd.hrp.sys.service.portal.SysPortalTitleUserService;

@Service("sysPortalTitleUserService")
public class SysPortalTitleUserServiceImpl implements SysPortalTitleUserService{
	
	private static Logger logger = Logger.getLogger(SysPortalTitleUserServiceImpl.class);
	
	@Resource (name="sysPortalTitleUserMapper")
	private final SysPortalTitleUserMapper  sysPortalTitleUserMapper = null ;
	
	
	@Resource (name="sysPortalNoticeMapper")
	private final SysPortalNoticeMapper  sysPortalNoticeMapper = null ;
	
	
	@Override
	public String addSysPortalTitleUser(Map<String, Object> entityMap) throws DataAccessException {
		try {
				sysPortalTitleUserMapper.addSysPortalTitleUser(entityMap);
			

			return "{\"msg\":\"设置成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"设置失败 数据库异常 请联系管理员! 错误编码 addSysPortalTitleSet\"}";

		}

	}

	@Override
	public String addBatchSysPortalTitleUser(List<Map<String,Object>> list) throws DataAccessException {
		
		try {

			sysPortalTitleUserMapper.addBatchSysPortalTitleUser(list);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败!\"}");
			//return "{\"error\":\"设置失败 数据库异常 请联系管理员! 错误编码 addSysPortalTitleSet\"}";

		}
	}

	@Override
	public String updateSysPortalTitleUser(Map<String, Object> entityMap) throws DataAccessException {
		try {

			sysPortalTitleUserMapper.updateSysPortalTitleUser(entityMap);

			return "{\"msg\":\"设置成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"设置失败 数据库异常 请联系管理员! 错误编码 addSysPortalTitleSet\"}";

		}
	}

	@Override
	public String deleteSysPortalTitleUser(Map<String, Object> entityMap) throws DataAccessException {
		try {
			sysPortalTitleUserMapper.deleteSysPortalTitleUser(entityMap);
		

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败!\"}");
			
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 addSysPortalTitleSet\"}";
	
		}
	}
	
	@Override
	public String deleteBatch(List<Map<String, Object>> list) throws DataAccessException {
		try {
			sysPortalTitleUserMapper.deleteBatch(list);
		

		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败!\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 addSysPortalTitleSet\"}";
	
		}
	}

	@Override
	public String addOrUpdateSysNotice(Map<String, Object> mapVo) throws DataAccessException {
		
		try {
			
			int count  = sysPortalNoticeMapper.queryDataExist(mapVo);
			
			if(count > 0){
				
				 sysPortalNoticeMapper.update(mapVo);
				
			}else{
				 sysPortalNoticeMapper.add(mapVo);
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败!\"}");
		}
		
	}
	
	
	@Override
	public String updateSysNoticeState(Map<String, Object> mapVo) throws DataAccessException {
		
		try {
			
			sysPortalNoticeMapper.updateSysNoticeState(mapVo);
				
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败!\"}");
		}
		
	}
	
	/**
	 * 根据主键 查询公告数据
	 */
	@Override
	public Map<String,Object> querySysPortalNoticeByCode(Map<String, Object> mapVo) throws DataAccessException {
		
			
		return sysPortalNoticeMapper.queryByCode(mapVo);
		
	}
	
	/**
	 * 根据主键 查询公告数据是否存在
	 */
	@Override
	public int querySysNoticeExist(Map<String, Object> mapVo) {
		
		return sysPortalNoticeMapper.queryDataExist(mapVo);
	}
}
