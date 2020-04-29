package com.chd.hrp.ass.serviceImpl.tongJiReports;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.tongJiReports.AssDepreExpireMapper;
import com.chd.hrp.ass.entity.tongJiReports.AssDepreExpire;
import com.chd.hrp.ass.service.tongJiReports.AssDepreExpireService;
import com.github.pagehelper.PageInfo;

@Service("assDepreExpireService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AssDepreExpireServiceImpl implements AssDepreExpireService {
	
	private static Logger logger = Logger.getLogger(AssDepreExpireServiceImpl.class);
	
	@Resource(name = "assDepreExpireMapper")
	private final AssDepreExpireMapper assDepreExpireMapper = null;

	@Override
	public String queryAssDepreExpire(Map<String, Object> entityMap) throws DataAccessException {
		logger.debug("AssDepreExpireServiceImpl.queryAssDepreExpire");
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssDepreExpire> list = assDepreExpireMapper.queryAssDepreExpire(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssDepreExpire> list = assDepreExpireMapper.queryAssDepreExpire(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}

	@Override
	public String queryAssDepreTerm(Map<String, Object> entityMap) throws DataAccessException {
		logger.debug("AssDepreExpireServiceImpl.queryAssDepreTerm");
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssDepreExpire> list = assDepreExpireMapper.queryAssDepreTerm(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssDepreExpire> list = assDepreExpireMapper.queryAssDepreTerm(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public List<Map<String, Object>> queryAssDepreExpirePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assDepreExpireMapper.queryAssDepreExpirePrint(entityMap);
	
		return list;
	}

	@Override
	public List<Map<String, Object>> queryAssDepreTermPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assDepreExpireMapper.queryAssDepreTermPrint(entityMap);
	
		return list;
	}

}
