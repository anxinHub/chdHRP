
package com.chd.hrp.htc.serviceImpl.task.basic;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.task.basic.HtcIassetCostRelaMapper;
import com.chd.hrp.htc.entity.task.basic.HtcIassetCostRela;
import com.chd.hrp.htc.service.task.basic.HtcIassetCostRelaService;
import com.github.pagehelper.PageInfo;

/** 
* 2015-3-18 
* author:alfred
*/ 


@Service("htcIassetCostRelaService")
public class HtcIassetCostRelaServiceImpl implements HtcIassetCostRelaService {

	private static Logger logger = Logger.getLogger(HtcIassetCostRelaServiceImpl.class);
	
	@Resource(name = "htcIassetCostRelaMapper")
	private final HtcIassetCostRelaMapper htcIassetCostRelaMapper = null;
    
	@Override
	public String addHtcIassetCostRela(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			HtcIassetCostRela htcIassetCostRela = queryHtcIassetCostRelaByCode(entityMap);
			
			if(null != htcIassetCostRela){
				return "{\"error\":\"该无形资产与成本项目已存在关系.\",\"state\":\"false\"}";
			}
			
			htcIassetCostRelaMapper.addHtcIassetCostRela(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addHtcIassetCostRela\"}";
		}
	}

	@Override
	public String queryHtcIassetCostRela(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcIassetCostRela> list = htcIassetCostRelaMapper.queryHtcIassetCostRela(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcIassetCostRela> list = htcIassetCostRelaMapper.queryHtcIassetCostRela(entityMap, rowBounds);
			
			PageInfo<HtcIassetCostRela> page = new PageInfo<HtcIassetCostRela>(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}	
	}

	@Override
	public HtcIassetCostRela queryHtcIassetCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return htcIassetCostRelaMapper.queryHtcIassetCostRelaByCode(entityMap);
		
	}
	
	@Override
    public String deleteHtcIassetCostRela(Map<String, Object> entityMap) throws DataAccessException {

		try {
			htcIassetCostRelaMapper.deleteHtcIassetCostRela(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteHtcIassetCostRela\"}";
		}
    }
	
	@Override
	public String deleteBatchHtcIassetCostRela(List<Map<String, Object>> listVo) throws DataAccessException{
		try {
			htcIassetCostRelaMapper.deleteBatchHtcIassetCostRela(listVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteBatchHtcIassetCostRela\"}";
		}
	}

	@Override
	public String updateHtcIassetCostRela(Map<String,Object> entityMap)throws DataAccessException{

		try {
			htcIassetCostRelaMapper.updateHtcIassetCostRela(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码 updateHtcIassetCostRela\"}";
		}
	}

}
