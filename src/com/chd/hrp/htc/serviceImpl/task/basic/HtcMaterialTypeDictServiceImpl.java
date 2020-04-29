package com.chd.hrp.htc.serviceImpl.task.basic;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.task.basic.HtcMaterialTypeDictMapper;
import com.chd.hrp.htc.entity.task.basic.HtcMaterialTypeDict;
import com.chd.hrp.htc.service.task.basic.HtcMaterialTypeDictService;
import com.github.pagehelper.PageInfo;

/**
 * 2015-3-18 author:alfred
 */

@Service("htcMaterialTypeDictService")
public class HtcMaterialTypeDictServiceImpl implements HtcMaterialTypeDictService {

	private static Logger logger = Logger.getLogger(HtcMaterialTypeDictServiceImpl.class);

	@Resource(name = "htcMaterialTypeDictMapper")
	private final HtcMaterialTypeDictMapper htcMaterialTypeDictMapper = null;
	
	@Override
	public String addHtcMaterialTypeDict(Map<String, Object> entityMap) throws DataAccessException {

		try {
			HtcMaterialTypeDict htcMaterialTypeDict = queryHtcMaterialTypeDictByCode(entityMap);
			
			if(null != htcMaterialTypeDict){
				return "{\"error\":\"材料分类编码重复.\",\"state\":\"false\"}";
			}
			
			htcMaterialTypeDictMapper.addHtcMaterialTypeDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcMaterialTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();	
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcMaterialTypeDict> list = htcMaterialTypeDictMapper.queryHtcMaterialTypeDict(entityMap);
		
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcMaterialTypeDict> list = htcMaterialTypeDictMapper.queryHtcMaterialTypeDict(entityMap, rowBounds);
	
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}

	@Override
	public HtcMaterialTypeDict queryHtcMaterialTypeDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return htcMaterialTypeDictMapper.queryHtcMaterialTypeDictByCode(entityMap);
		
	}

	@Override
	public String deleteHtcMaterialTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			htcMaterialTypeDictMapper.deleteHtcMaterialTypeDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteHtcMaterialTypeDict\"}";
		}
	}

	
	@Override
	public String deleteBatchHtcMaterialTypeDict(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {
			
			htcMaterialTypeDictMapper.deleteBatchHtcMaterialTypeDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
	
	@Override
	public String updateHtcMaterialTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		try {
			htcMaterialTypeDictMapper.updateHtcMaterialTypeDict(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

	@Override
	public String synchroHtcMaterialTypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcMaterialTypeDictMapper.synchroHtcMaterialTypeDict(entityMap);
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"同步失败\"}");
		}
	}


}
