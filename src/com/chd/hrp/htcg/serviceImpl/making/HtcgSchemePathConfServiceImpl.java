package com.chd.hrp.htcg.serviceImpl.making;
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
import com.chd.hrp.htcg.dao.making.HtcgSchemePathConfMapper;
import com.chd.hrp.htcg.entity.making.HtcgSchemePathConf;
import com.chd.hrp.htcg.service.making.HtcgSchemePathConfService;
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


@Service("htcgSchemePathConfService")
public class HtcgSchemePathConfServiceImpl implements HtcgSchemePathConfService {

	private static Logger logger = Logger.getLogger(HtcgSchemePathConfServiceImpl.class);
	
	@Resource(name = "htcgSchemePathConfMapper")
	private final HtcgSchemePathConfMapper htcgSchemePathConfMapper = null;

	@Override
	public String initHtcgSchemePathConf(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcgSchemePathConfMapper.initHtcgSchemePathConf(entityMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}

	@Override
	public String queryHtcgSchemePathConf(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<HtcgSchemePathConf> list = htcgSchemePathConfMapper.queryHtcgSchemePathConf(entityMap) ;
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgSchemePathConf> list = htcgSchemePathConfMapper.queryHtcgSchemePathConf(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcgSchemePathConf(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcgSchemePathConfMapper.deleteBatchHtcgSchemePathConf(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String saveHtcgSchemePathConf(List<Map<String,Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgSchemePathConfMapper.updateBatchHtcgSchemePathConf(list);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"保存失败\"}");
		}
		
	}

	
	 

	 
}
