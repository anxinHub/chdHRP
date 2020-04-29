
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
import com.chd.hrp.htc.dao.task.basic.HtcFassetCostRelaMapper;
import com.chd.hrp.htc.entity.task.basic.HtcFassetCostRela;
import com.chd.hrp.htc.service.task.basic.HtcFassetCostRelaService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("htcFassetCostRelaService")
public class HtcFassetCostRelaServiceImpl implements HtcFassetCostRelaService {

	private static Logger logger = Logger.getLogger(HtcFassetCostRelaServiceImpl.class);
	
	@Resource(name = "htcFassetCostRelaMapper")
	private final HtcFassetCostRelaMapper htcFassetCostRelaMapper = null;

	@Override
	public String addHtcFassetCostRela(Map<String,Object> entityMap)throws DataAccessException{
		
		try{
			HtcFassetCostRela htcFassetCostRela = htcFassetCostRelaMapper.queryHtcFassetCostRelaByCode(entityMap);
		
			if(null != htcFassetCostRela){
				return "{\"error\":\"资产与成本项目已存在关系.\",\"state\":\"false\"}";
			}
			
			htcFassetCostRelaMapper.addHtcFassetCostRela(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}	
	}
	
	@Override
	public String queryHtcFassetCostRela(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcFassetCostRela> list = htcFassetCostRelaMapper.queryHtcFassetCostRela(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcFassetCostRela> list = htcFassetCostRelaMapper.queryHtcFassetCostRela(entityMap, rowBounds);
	
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public HtcFassetCostRela queryHtcFassetCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcFassetCostRelaMapper.queryHtcFassetCostRelaByCode(entityMap);
	}
	
	@Override
    public String deleteHtcFassetCostRela(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			htcFassetCostRelaMapper.deleteHtcFassetCostRela(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteHtcFassetCostRela\"}";
		}
    }
	
	@Override
	public String deleteBatchHtcFassetCostRela(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			htcFassetCostRelaMapper.deleteBatchHtcFassetCostRela(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
	
	@Override
	public String updateHtcFassetCostRela(Map<String,Object> entityMap)throws DataAccessException{
		try {
			htcFassetCostRelaMapper.updateHtcFassetCostRela(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}


}
