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
import com.chd.hrp.htc.dao.task.basic.HtcIassetCostRelaMapper;
import com.chd.hrp.htc.dao.task.basic.HtcIassetDictMapper;
import com.chd.hrp.htc.dao.task.basic.HtcIassetTypeDictMapper;
import com.chd.hrp.htc.entity.task.basic.HtcIassetCostRela;
import com.chd.hrp.htc.entity.task.basic.HtcIassetDict;
import com.chd.hrp.htc.entity.task.basic.HtcIassetTypeDict;
import com.chd.hrp.htc.service.task.basic.HtcIassetTypeDictService;
import com.github.pagehelper.PageInfo;

/**
 * 2015-3-18 author:alfred
 */

@Service("htcIassetTypeDictService")
public class HtcIassetTypeDictServiceImpl implements HtcIassetTypeDictService {

	private static Logger logger = Logger.getLogger(HtcIassetTypeDictServiceImpl.class);

	@Resource(name = "htcIassetTypeDictMapper")
	private final HtcIassetTypeDictMapper htcIassetTypeDictMapper = null;
	
	@Override
	public String addHtcIassetTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			HtcIassetTypeDict htcIassetTypeDict = queryHtcIassetTypeDictByCode(entityMap);
			
			if(null != htcIassetTypeDict){
				return "{\"error\":\"资产分类编码重复.\",\"state\":\"false\"}";
			}
			
			htcIassetTypeDictMapper.addHtcIassetTypeDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcIassetTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcIassetTypeDict> list = htcIassetTypeDictMapper.queryHtcIassetTypeDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcIassetTypeDict> list = htcIassetTypeDictMapper.queryHtcIassetTypeDict(entityMap, rowBounds);
	
			PageInfo<HtcIassetTypeDict> page = new PageInfo<HtcIassetTypeDict>(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcIassetTypeDict queryHtcIassetTypeDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return htcIassetTypeDictMapper.queryHtcIassetTypeDictByCode(entityMap);
		
	}

	@Override
	public String deleteHtcIassetTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			htcIassetTypeDictMapper.deleteHtcIassetTypeDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteHtcIassetTypeDict\"}";
		}
	}

	@Override
	public String deleteBatchHtcIassetTypeDict(List<Map<String, Object>> entityList) throws DataAccessException{
		
		try {
			
             htcIassetTypeDictMapper.deleteBatchHtcIassetTypeDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
	
	@Override
	public String updateHtcIassetTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			htcIassetTypeDictMapper.updateHtcIassetTypeDict(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

}
