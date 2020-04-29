
package com.chd.hrp.htc.serviceImpl.task.projectcost;

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
import com.chd.hrp.htc.dao.task.projectcost.HtcChargeCoopMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcChargeCoop;
import com.chd.hrp.htc.service.task.projectcost.HtcChargeCoopService;
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


@Service("htcChargeCoopService")
public class HtcChargeCoopServiceImpl implements HtcChargeCoopService {

	private static Logger logger = Logger.getLogger(HtcChargeCoopServiceImpl.class);
	
	@Resource(name = "htcChargeCoopMapper")
	private final HtcChargeCoopMapper htcChargeCoopMapper = null;

	@Override
	public String saveHtcChargeCoop(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	    try {
			
	    	HtcChargeCoop htcChargeCoop = htcChargeCoopMapper.queryHtcChargeCoopByCode(entityMap);
	    	
	    	if(null == htcChargeCoop){
	    		htcChargeCoopMapper.addHtcChargeCoop(entityMap);
	    	}else {
	    		htcChargeCoopMapper.updateHtcChargeCoop(entityMap);
	    	}
			return "{\"msgmsg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");	
		}
	}

	@Override
	public String queryHtcChargeCoop(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcChargeCoop> list = htcChargeCoopMapper.queryHtcChargeCoop(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcChargeCoop> list = htcChargeCoopMapper.queryHtcChargeCoop(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcChargeCoop(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
	    try {
			
	    	    htcChargeCoopMapper.deleteBatchHtcChargeCoop(list);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");	
			}
	}

	@Override
	public String queryHtcChargeCoopCharge(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcChargeCoop> list = htcChargeCoopMapper.queryHtcChargeCoopCharge(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcChargeCoop> list = htcChargeCoopMapper.queryHtcChargeCoopCharge(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryHtcChargeCoopTitle(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcChargeCoop> list = htcChargeCoopMapper.queryHtcChargeCoopTitle(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcChargeCoop> list = htcChargeCoopMapper.queryHtcChargeCoopTitle(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
}
