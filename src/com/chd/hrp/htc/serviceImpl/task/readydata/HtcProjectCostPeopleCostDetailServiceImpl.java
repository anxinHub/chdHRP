
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
import com.chd.hrp.htc.dao.task.readydata.HtcProjectCostPeopleCostDetailMapper;
import com.chd.hrp.htc.entity.task.readydata.HtcProjectCostPeopleCostDetail;
import com.chd.hrp.htc.service.task.readydata.HtcProjectCostPeopleCostDetailService;
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


@Service("htcProjectCostPeopleCostDetailService")
public class HtcProjectCostPeopleCostDetailServiceImpl implements HtcProjectCostPeopleCostDetailService {

	private static Logger logger = Logger.getLogger(HtcProjectCostPeopleCostDetailServiceImpl.class);
	
	@Resource(name = "htcProjectCostPeopleCostDetailMapper")
	private final HtcProjectCostPeopleCostDetailMapper htcProjectCostPeopleCostDetailMapper = null;

	@Override
	public String disposeHtcTaskProjectCostDeptPeopleCostDetail(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcProjectCostPeopleCostDetailMapper.disposeHtcTaskProjectCostDeptPeopleCostDetail(entityMap);
			return "{\"msg\":\"采集成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"采集失败\"}");
		}
	}

	@Override
	public String queryHtcTaskProjectCostDeptPeopleCostDetail(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcProjectCostPeopleCostDetail> list = htcProjectCostPeopleCostDetailMapper.queryHtcTaskProjectCostDeptPeopleCostDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcProjectCostPeopleCostDetail> list = htcProjectCostPeopleCostDetailMapper.queryHtcTaskProjectCostDeptPeopleCostDetail(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
    

}
