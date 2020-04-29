package com.chd.hrp.htcg.serviceImpl.calculation;
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
import com.chd.hrp.htcg.dao.calculation.HtcgHosPdrgsCostMapper;
import com.chd.hrp.htcg.entity.calculation.HtcgHosPdrgsCost;
import com.chd.hrp.htcg.service.calculation.HtcgHosPdrgsCostService;
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


@Service("htcgHosPdrgsCostService")
public class HtcgHosPdrgsCostServiceImpl implements HtcgHosPdrgsCostService {

	private static Logger logger = Logger.getLogger(HtcgHosPdrgsCostServiceImpl.class);
	
	@Resource(name = "htcgHosPdrgsCostMapper")
	private final HtcgHosPdrgsCostMapper htcgHosPdrgsCostMapper = null;

	@Override
	public String initHtcgHosPdrgsCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			 htcgHosPdrgsCostMapper.initHtcgHosPdrgsCost(entityMap);
			    
			    return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}

	@Override
	public String queryHtcgHosPdrgsCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<HtcgHosPdrgsCost> list = htcgHosPdrgsCostMapper.queryHtcgHosPdrgsCost(entityMap) ;
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgHosPdrgsCost> list = htcgHosPdrgsCostMapper.queryHtcgHosPdrgsCost(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcgHosPdrgsCost(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
	    try {
			
	    	htcgHosPdrgsCostMapper.deleteBatchHtcgHosPdrgsCost(list);
			 return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
}
