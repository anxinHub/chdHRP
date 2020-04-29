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
import com.chd.hrp.htc.dao.task.basic.HtcMaterialDictMapper;
import com.chd.hrp.htc.entity.task.basic.HtcMaterialDict;
import com.chd.hrp.htc.service.task.basic.HtcMaterialDictService;
import com.github.pagehelper.PageInfo;

/**
 * 2015-3-18 author:alfred
 */

@Service("htcMaterialDictService")
public class HtcMaterialDictServiceImpl implements HtcMaterialDictService {

	private static Logger logger = Logger.getLogger(HtcMaterialDictServiceImpl.class);

	@Resource(name = "htcMaterialDictMapper")
	private final HtcMaterialDictMapper htcMaterialDictMapper = null;

	@Override
	public String addHtcMaterialDict(Map<String, Object> entityMap) throws DataAccessException {

		try {
			HtcMaterialDict htcMaterialDict = queryHtcMaterialDictByCode(entityMap);
			
			if(null != htcMaterialDict){
				return "{\"error\":\"物资编码重复.\",\"state\":\"false\"}";
			}
			
			htcMaterialDictMapper.addHtcMaterialDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcMaterialDict(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcMaterialDict> list = htcMaterialDictMapper.queryHtcMaterialDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcMaterialDict> list = htcMaterialDictMapper.queryHtcMaterialDict(entityMap, rowBounds);
			
			PageInfo<HtcMaterialDict> page = new PageInfo<HtcMaterialDict>(list);
		
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcMaterialDict queryHtcMaterialDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return htcMaterialDictMapper.queryHtcMaterialDictByCode(entityMap);
		
	}

	@Override
	public String deleteHtcMaterialDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			htcMaterialDictMapper.deleteHtcMaterialDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteHtcMaterialDict\"}";
		}
		
	}

	@Override
	public String deleteBatchHtcMaterialDict(List<Map<String, Object>> entityList) throws DataAccessException{

		try{
			htcMaterialDictMapper.deleteBatchHtcMaterialDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
	
	@Override
	public String updateHtcMaterialDict(Map<String, Object> entityMap) throws DataAccessException {

		try{
			htcMaterialDictMapper.updateHtcMaterialDict(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

	@Override
	public String synchroHtcMaterialDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			
			htcMaterialDictMapper.synchroHtcMaterialDict(entityMap);
			
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"同步失败\"}");
		}
	}
}
