
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
import com.chd.hrp.htc.dao.task.basic.HtcFassetDictMapper;
import com.chd.hrp.htc.entity.task.basic.HtcFassetDict;
import com.chd.hrp.htc.service.task.basic.HtcFassetDictService;
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


@Service("htcFassetDictService")
public class HtcFassetDictServiceImpl implements HtcFassetDictService {

	private static Logger logger = Logger.getLogger(HtcFassetDictServiceImpl.class);
	
	@Resource(name = "htcFassetDictMapper")
	private final HtcFassetDictMapper htcFassetDictMapper = null;
    
	@Override
	public String addHtcFassetDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			HtcFassetDict htcFassetDict = queryHtcFassetDictByCode(entityMap);
			
			if(null != htcFassetDict){
				
				return "{\"error\":\"卡片编码重复.\",\"state\":\"false\"}";
			}
			
			htcFassetDictMapper.addHtcFassetDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
	
	@Override
	public String queryHtcFassetDict(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcFassetDict> list = htcFassetDictMapper.queryHtcFassetDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcFassetDict> list = htcFassetDictMapper.queryHtcFassetDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcFassetDict queryHtcFassetDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcFassetDictMapper.queryHtcFassetDictByCode(entityMap);
	}

	@Override
    public String deleteHtcFassetDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			htcFassetDictMapper.deleteHtcFassetDict(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteHtcFassetDict\"}";
		}
    }
	
	@Override
	public String deleteBatchHtcFassetDict(List<Map<String, Object>> entityList)throws DataAccessException{
		
		try {
			
			htcFassetDictMapper.deleteBatchHtcFassetDict(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}		
	}
	
	@Override
	public String updateHtcFassetDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			htcFassetDictMapper.updateHtcFassetDict(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}
}
