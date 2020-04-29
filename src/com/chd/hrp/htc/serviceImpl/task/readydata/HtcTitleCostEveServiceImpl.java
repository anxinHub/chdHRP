package com.chd.hrp.htc.serviceImpl.task.readydata;

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
import com.chd.hrp.htc.dao.task.readydata.HtcTitleCostEveMapper;
import com.chd.hrp.htc.entity.task.readydata.HtcTitleCostEve;
import com.chd.hrp.htc.service.task.readydata.HtcTitleCostEveService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("htcTitleCostEveService")
public class HtcTitleCostEveServiceImpl implements HtcTitleCostEveService {

	private static Logger logger = Logger
			.getLogger(HtcTitleCostEveServiceImpl.class);

	@Resource(name = "htcTitleCostEveMapper")
	private final HtcTitleCostEveMapper htcTitleCostEveMapper = null;
	

	@Override
	public String queryHtcTitleCostEve(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcTitleCostEve> list = htcTitleCostEveMapper.queryHtcTitleCostEve(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcTitleCostEve> list = htcTitleCostEveMapper.queryHtcTitleCostEve(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}


	/**
	 * 
	 */
	@Override
	public String deleteBatchHtcTitleCostEve(List<Map<String, Object>> entityList)
			throws DataAccessException {

		try{
			
			htcTitleCostEveMapper.deleteBatchHtcTitleCostEve(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String averageHtcWageReckon(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {
			
			htcTitleCostEveMapper.averageHtcWageReckon(entityMap);
			return "{\"msg\":\"平均工资测算成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"平均工资测算失败\"}");
		}
	}
}
