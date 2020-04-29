package com.chd.hrp.htc.serviceImpl.info.basic;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.info.basic.HtcDeptDictMapper;
import com.chd.hrp.htc.entity.info.basic.HtcProDeptDict;
import com.chd.hrp.htc.service.info.basic.HtcDeptDictService;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

@Service("htcDeptDictService")
public class HtcDeptDictServiceImpl implements HtcDeptDictService{

private static Logger logger = Logger.getLogger(HtcDeptDictServiceImpl.class);
	
	@Resource(name = "htcDeptDictMapper")
	private final HtcDeptDictMapper htcDeptDictMapper = null;

	@Override
	public String addHtcDeptAttrDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	  try {
			
		  
		    Map<String,Object> map =  htcDeptDictMapper.queryHtcDeptAttrByCode(entityMap);
		    
		    if(map == null){
		    	
		    	htcDeptDictMapper.addHtcDeptAttrDict(entityMap);
		    	
		    	return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		    	
		    }else {
		    	
		    	htcDeptDictMapper.updateHtcDeptAttrDict(entityMap);
		    	
		    	return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		    }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 错误编码 addHtcDeptAttrDict\"}";
		}
	}

	@Override
	public String updateHtcDeptAttrDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcDeptDictMapper.updateHtcDeptAttrDict(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码 updateHtcDeptAttrDict\"}";
		}
	}
	
	@Override
	public String htcDeptDictManage(List<Map<String, Object>> listVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
	try {
			for (Map<String, Object> mapVo :listVo) {
				   Map<String,Object> map =  htcDeptDictMapper.queryHtcDeptAttrByCode(mapVo);
					   if(map == null){
					   
					    	htcDeptDictMapper.addHtcDeptAttrDict(mapVo);
					    	
					    }else {
					    	
					    	htcDeptDictMapper.updateHtcDeptAttrDict(mapVo);

				   }
				}
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码 htcDeptDictManage\"}";
		}

	}

	@Override
	public List<?> queryHtcDeptDictByTree(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<?> l_map = htcDeptDictMapper.queryHtcDeptDictByTree(entityMap);
		return l_map;
	}

	@Override
	public String queryHtcDeptDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String,Object>> list = htcDeptDictMapper.queryHtcDeptDict(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public Map<String, Object> queryHtcDeptAttrByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcDeptDictMapper.queryHtcDeptAttrByCode(entityMap);
	}

	@Override
	public Map<String, Object> queryHtcDeptDictByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcDeptDictMapper.queryHtcDeptDictByCode(entityMap);
	}
	
}
