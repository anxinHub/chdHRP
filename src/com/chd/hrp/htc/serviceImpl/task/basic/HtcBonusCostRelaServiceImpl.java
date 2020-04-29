
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
import com.chd.hrp.htc.dao.task.basic.HtcBonusCostRelaMapper;
import com.chd.hrp.htc.entity.task.basic.HtcBonusCostRela;
import com.chd.hrp.htc.service.task.basic.HtcBonusCostRelaService;
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


@Service("htcBonusCostRelaService")
public class HtcBonusCostRelaServiceImpl implements HtcBonusCostRelaService {

	private static Logger logger = Logger.getLogger(HtcBonusCostRelaServiceImpl.class);
	
	@Resource(name = "htcBonusCostRelaMapper") 
	private final HtcBonusCostRelaMapper htcBonusCostRelaMapper = null;
    
	@Override
	public String addHtcBonusCostRela(Map<String,Object> entityMap)throws DataAccessException{
	   
		try{
			HtcBonusCostRela htcBonusCostRela = htcBonusCostRelaMapper.queryHtcBonusCostRelaByCode(entityMap);
		
			if(null != htcBonusCostRela){
				return "{\"error\":\"奖金与成本项目已存在关系.\",\"state\":\"false\"}";
			}
			
			htcBonusCostRelaMapper.addHtcBonusCostRela(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
	
	@Override
	public String queryHtcBonusCostRela(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			List<HtcBonusCostRela> list = htcBonusCostRelaMapper.queryHtcBonusCostRela(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcBonusCostRela> list = htcBonusCostRelaMapper.queryHtcBonusCostRela(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public HtcBonusCostRela queryHtcBonusCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcBonusCostRelaMapper.queryHtcBonusCostRelaByCode(entityMap);
	}
	
	@Override
	public String deleteBatchHtcBonusCostRela(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			
			htcBonusCostRelaMapper.deleteBatchHtcBonusCostRela(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
	@Override
    public String deleteHtcBonusCostRela(Map<String, Object> entityMap) throws DataAccessException {
		
        try {
			
			htcBonusCostRelaMapper.deleteHtcBonusCostRela(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
    }

	@Override
	public String updateHtcBonusCostRela(Map<String,Object> entityMap)throws DataAccessException{
		try {
			
			htcBonusCostRelaMapper.updateHtcBonusCostRela(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

}
