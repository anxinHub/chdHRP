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
import com.chd.hrp.htc.dao.info.basic.HtcChargeItemDictMapper;
import com.chd.hrp.htc.entity.info.basic.HtcChargeItemDict;
import com.chd.hrp.htc.service.info.basic.HtcChargeItemDictService;
import com.github.pagehelper.PageInfo;

/**
 * 2015-3-18 author:alfred
 */

@Service("htcChargeItemDictService")
public class HtcChargeItemDictServiceImpl implements HtcChargeItemDictService {

	private static Logger logger = Logger.getLogger(HtcChargeItemDictServiceImpl.class);

	@Resource(name = "htcChargeItemDictMapper")
	private final HtcChargeItemDictMapper htcChargeItemDictMapper = null;
	
	@Override
	public String addHtcChargeItemDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			HtcChargeItemDict htcChargeItemDict = queryHtcChargeItemDictByCode(entityMap);
			
			if(null != htcChargeItemDict){
				return "{\"error\":\"编码：" + entityMap.get("charge_item_code").toString() + "重复.\"}";
			}
			
			htcChargeItemDictMapper.addHtcChargeItemDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addHtcChargeItemDict\"}";
		}
	}

	@Override
	public String queryHtcChargeItemDict(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			List<HtcChargeItemDict> list = htcChargeItemDictMapper.queryHtcChargeItemDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcChargeItemDict> list = htcChargeItemDictMapper.queryHtcChargeItemDict(entityMap,rowBounds);
			
			PageInfo<HtcChargeItemDict> page = new PageInfo<HtcChargeItemDict>(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcChargeItemDict queryHtcChargeItemDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return htcChargeItemDictMapper.queryHtcChargeItemDictByCode(entityMap);
		
	}

	@Override
	public String deleteHtcChargeItemDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			
			htcChargeItemDictMapper.deleteHtcChargeItemDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteHtcChargeItemDict\"}";
		}
	}

	@Override
	public String deleteBatchHtcChargeItemDict(List<Map<String, Object>> entityList) throws DataAccessException{
		try{
			htcChargeItemDictMapper.deleteBatchHtcChargeItemDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchHtcChargeItemDict\"}";
		}
	}
	
	@Override
	public String updateHtcChargeItemDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			htcChargeItemDictMapper.updateHtcChargeItemDict(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHtcChargeItemDict\"}";
		}
	}

	@Override
	public String addBatchHtcChargeItemDicts(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		int state = htcChargeItemDictMapper.addBatchHtcChargeItemDict(entityMap);
		if(state>0){
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		}
		return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
	}

	@Override
	public String queryHtcChargeItemDicts(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
}
