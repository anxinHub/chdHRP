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
import com.chd.hrp.htc.dao.info.basic.HtcChargeKindDictMapper;
import com.chd.hrp.htc.entity.info.basic.HtcChargeKindDict;
import com.chd.hrp.htc.service.info.basic.HtcChargeKindDictService;
import com.github.pagehelper.PageInfo;

/**
 * 2015-3-18 author:alfred
 */

@Service("htcChargeKindDictService")
public class HtcChargeKindDictServiceImpl implements HtcChargeKindDictService {

	private static Logger logger = Logger.getLogger(HtcChargeKindDictServiceImpl.class);

	@Resource(name = "htcChargeKindDictMapper")
	private final HtcChargeKindDictMapper htcChargeKindDictMapper = null;

	@Override
	public String addHtcChargeKindDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			HtcChargeKindDict htcChargeKindDict = queryHtcChargeKindDictByCode(entityMap);
			
			if(null != htcChargeKindDict){
				return "{\"error\":\"编码：" + entityMap.get("charge_kind_code").toString() + "重复.\"}";
			}
			
			htcChargeKindDictMapper.addHtcChargeKindDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addHtcChargeKindDict\"}";
		}
	}

	@Override
	public String queryHtcChargeKindDict(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcChargeKindDict> list = htcChargeKindDictMapper.queryHtcChargeKindDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcChargeKindDict> list = htcChargeKindDictMapper.queryHtcChargeKindDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}

	@Override
	public HtcChargeKindDict queryHtcChargeKindDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return htcChargeKindDictMapper.queryHtcChargeKindDictByCode(entityMap);
		
	}


	@Override
	public String deleteHtcChargeKindDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			htcChargeKindDictMapper.deleteHtcChargeKindDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteHtcChargeKindDict\"}";
		}

	}

	@Override
	public String deleteBatchHtcChargeKindDict(List<Map<String, Object>> entityList) throws DataAccessException{

		try {
		
			htcChargeKindDictMapper.deleteBatchHtcChargeKindDict(entityList);
			  
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchHtcChargeKindDict\"}";
	
		}
		
	}
	
	@Override
	public String updateHtcChargeKindDict(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			htcChargeKindDictMapper.updateHtcChargeKindDict(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHtcChargeKindDict\"}";
		}
	}
}
