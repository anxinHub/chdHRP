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
import com.chd.hrp.htc.dao.task.basic.HtcMateCostRelaMapper;
import com.chd.hrp.htc.entity.task.basic.HtcMateCostRela;
import com.chd.hrp.htc.service.task.basic.HtcMateCostRelaService;
import com.github.pagehelper.PageInfo;

/**
 * 2015-3-18 author:alfred
 */

@Service("htcMateCostRelaService")
public class HtcMateCostRelaServiceImpl implements HtcMateCostRelaService {

	private static Logger logger = Logger.getLogger(HtcMateCostRelaServiceImpl.class);

	@Resource(name = "htcMateCostRelaMapper")
	private final HtcMateCostRelaMapper htcMateCostRelaMapper = null;

	@Override
	public String addHtcMateCostRela(Map<String, Object> entityMap) throws DataAccessException {

		try {
			HtcMateCostRela htcMateCostRela = queryHtcMateCostRelaByCode(entityMap);
			
			if(null != htcMateCostRela){
				return "{\"error\":\"该材料已经和成本项目存在关系.\",\"state\":\"false\"}";
			}
			
			htcMateCostRelaMapper.addHtcMateCostRela(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcMateCostRela(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			List<HtcMateCostRela> list = htcMateCostRelaMapper.queryHtcMateCostRela(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcMateCostRela> list = htcMateCostRelaMapper.queryHtcMateCostRela(entityMap, rowBounds);
			
			PageInfo<HtcMateCostRela> page = new PageInfo<HtcMateCostRela>(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcMateCostRela queryHtcMateCostRelaByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return htcMateCostRelaMapper.queryHtcMateCostRelaByCode(entityMap);
		
	}

	@Override
	public String deleteHtcMateCostRela(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			htcMateCostRelaMapper.deleteHtcMateCostRela(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteHtcMateCostRela\"}";
		}
		
	}

	@Override
	public String deleteBatchHtcMateCostRela(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {
			htcMateCostRelaMapper.deleteBatchHtcMateCostRela(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
	
	@Override
	public String updateHtcMateCostRela(Map<String, Object> entityMap) throws DataAccessException {

		try {
			htcMateCostRelaMapper.updateHtcMateCostRela(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

}
