
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
import com.chd.hrp.htc.dao.task.projectcost.HtcDeptChargeWorkMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptChargeWork;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptChargeWorkService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.
 * 
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("htcDeptChargeWorkService")
public class HtcDeptChargeWorkServiceImpl implements HtcDeptChargeWorkService {

	private static Logger logger = Logger.getLogger(HtcDeptChargeWorkServiceImpl.class);

	@Resource(name = "htcDeptChargeWorkMapper")
	private final HtcDeptChargeWorkMapper htcDeptChargeWorkMapper = null;

	@Override
	public String saveHtcDeptChargeWork(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	   try {
			
		    HtcDeptChargeWork  htcDeptChargeWork = htcDeptChargeWorkMapper.queryHtcDeptChargeWorkByCode(entityMap);
		    
		    if(null == htcDeptChargeWork){
		    	   htcDeptChargeWorkMapper.addHtcDeptChargeWork(entityMap);
		    }else {
		    	htcDeptChargeWorkMapper.updateHtcDeptChargeWork(entityMap);
		    }
			return "{\"msg1\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcPlanDeptCharge(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String, Object>> list = htcDeptChargeWorkMapper.queryHtcPlanDeptCharge(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = htcDeptChargeWorkMapper.queryHtcPlanDeptCharge(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryHtcPlanDeptWork(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String, Object>> list = htcDeptChargeWorkMapper.queryHtcPlanDeptWork(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = htcDeptChargeWorkMapper.queryHtcPlanDeptWork(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryHtcDeptChargeWork(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
         SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptChargeWork> list = htcDeptChargeWorkMapper.queryHtcDeptChargeWork(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptChargeWork> list = htcDeptChargeWorkMapper.queryHtcDeptChargeWork(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcDeptChargeWork(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
			
			 htcDeptChargeWorkMapper.deleteBatchHtcDeptChargeWork(list);
			 return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
		
	}

}
