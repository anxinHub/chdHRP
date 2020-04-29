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
import com.chd.hrp.htcg.dao.calculation.HtcgHosDdrgsCostMapper;
import com.chd.hrp.htcg.entity.calculation.HtcgHosDdrgsCost;
import com.chd.hrp.htcg.service.calculation.HtcgHosDdrgsCostService;
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
@Service("htcgHosDdrgsCostService")
public class HtcgHosDdrgsCostServiceImpl implements HtcgHosDdrgsCostService {

	private static Logger logger = Logger.getLogger(HtcgHosDdrgsCostServiceImpl.class);
	
	@Resource(name = "htcgHosDdrgsCostMapper")
	private final HtcgHosDdrgsCostMapper htcgHosDdrgsCostMapper = null;

	@Override
	public String initHtcgHosDdrgsCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			 htcgHosDdrgsCostMapper.initHtcgHosDdrgsCost(entityMap);
			    
			    return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}

	@Override
	public String queryHtcgHosDdrgsCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<HtcgHosDdrgsCost> list = htcgHosDdrgsCostMapper.queryHtcgHosDdrgsCost(entityMap) ;
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgHosDdrgsCost> list = htcgHosDdrgsCostMapper.queryHtcgHosDdrgsCost(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcgHosDdrgsCost(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
       try {
			
    	   htcgHosDdrgsCostMapper.deleteBatchHtcgHosDdrgsCost(list);
			 return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
}
