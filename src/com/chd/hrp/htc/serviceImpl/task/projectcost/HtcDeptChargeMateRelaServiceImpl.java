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
import com.chd.hrp.htc.dao.task.projectcost.HtcDeptChargeMateRelaMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptChargeMateRela;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptChargeMateRelaService;
import com.github.pagehelper.PageInfo;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("htcDeptChargeMateRelaService")
public class HtcDeptChargeMateRelaServiceImpl implements HtcDeptChargeMateRelaService {

	private static Logger logger = Logger.getLogger(HtcDeptChargeMateRelaServiceImpl.class);

	@Resource(name = "htcDeptChargeMateRelaMapper")
	private final HtcDeptChargeMateRelaMapper htcDeptChargeMateRelaMapper = null;

	@Override
	public String saveHtcDeptChargeMateRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcDeptChargeMateRela htcDeptChargeMateRela = htcDeptChargeMateRelaMapper.queryHtcDeptChargeMateRelaByCode(entityMap);
			
			if(null == htcDeptChargeMateRela){	
				htcDeptChargeMateRelaMapper.addHtcDeptChargeMateRela(entityMap);
			}else {
				htcDeptChargeMateRelaMapper.updateHtcDeptChargeMateRela(entityMap);
			}
			return "{\"msg1\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");	
		}
	}

	@Override
	public String queryHtcDeptChargeMateRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptChargeMateRela> list = htcDeptChargeMateRelaMapper.queryHtcDeptChargeMateRela(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptChargeMateRela> list = htcDeptChargeMateRelaMapper.queryHtcDeptChargeMateRela(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcDeptChargeMateRela(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
	  try {
			
			htcDeptChargeMateRelaMapper.deleteBatchHtcDeptChargeMateRela(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");	
		}
	}

	@Override
	public String queryHtcDeptChargeMateRelaCharge(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	    SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptChargeMateRela> list = htcDeptChargeMateRelaMapper.queryHtcDeptChargeMateRelaCharge(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptChargeMateRela> list = htcDeptChargeMateRelaMapper.queryHtcDeptChargeMateRelaCharge(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryHtcDeptChargeMateRelaMate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptChargeMateRela> list = htcDeptChargeMateRelaMapper.queryHtcDeptChargeMateRelaMate(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptChargeMateRela> list = htcDeptChargeMateRelaMapper.queryHtcDeptChargeMateRelaMate(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
}
