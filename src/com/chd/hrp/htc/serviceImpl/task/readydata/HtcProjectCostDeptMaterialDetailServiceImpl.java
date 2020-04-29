
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
import com.chd.hrp.htc.dao.task.readydata.HtcProjectCostDeptMaterialDetailMapper;
import com.chd.hrp.htc.entity.task.readydata.HtcProjectCostDeptMaterialDetail;
import com.chd.hrp.htc.service.task.readydata.HtcProjectCostDeptMaterialDetailService;
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


@Service("htcProjectCostDeptMaterialDetailService")
public class HtcProjectCostDeptMaterialDetailServiceImpl implements HtcProjectCostDeptMaterialDetailService {

	private static Logger logger = Logger.getLogger(HtcProjectCostDeptMaterialDetailServiceImpl.class);
	
	@Resource(name = "htcProjectCostDeptMaterialDetailMapper")
	private final HtcProjectCostDeptMaterialDetailMapper htcProjectCostDeptMaterialDetailMapper = null;

	@Override
	public String disposeHtcTaskProjectCostDeptMaterialCostDetail(
			Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			htcProjectCostDeptMaterialDetailMapper.disposeHtcTaskProjectCostDeptMaterialCostDetail(entityMap);
			
			return "{\"msg\":\"采集成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"采集失败\"}");
		}
	}

	@Override
	public String queryHtcTaskProjectCostDeptMaterialCostDetail(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcProjectCostDeptMaterialDetail> list = htcProjectCostDeptMaterialDetailMapper.queryHtcTaskProjectCostDeptMaterialCostDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcProjectCostDeptMaterialDetail> list = htcProjectCostDeptMaterialDetailMapper.queryHtcTaskProjectCostDeptMaterialCostDetail(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
    
	
}
