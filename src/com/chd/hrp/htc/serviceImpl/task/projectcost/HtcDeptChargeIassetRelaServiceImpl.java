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
import com.chd.hrp.htc.dao.task.projectcost.HtcDeptChargeIassetRelaMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptChargeIassetRela;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptChargeIassetRelaService;
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

@Service("htcDeptChargeIassetRelaService")
public class HtcDeptChargeIassetRelaServiceImpl implements HtcDeptChargeIassetRelaService {

	private static Logger logger = Logger.getLogger(HtcDeptChargeIassetRelaServiceImpl.class);

	@Resource(name = "htcDeptChargeIassetRelaMapper")
	private final HtcDeptChargeIassetRelaMapper htcDeptChargeIassetRelaMapper = null;

	@Override
	public String saveHtcDeptChargeIassetRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcDeptChargeIassetRela htcDeptChargeIassetRela = htcDeptChargeIassetRelaMapper.queryHtcDeptChargeIassetRelaByCode(entityMap);
			
			if(null == htcDeptChargeIassetRela){
				htcDeptChargeIassetRelaMapper.addHtcDeptChargeIassetRela(entityMap);
			}else {
				htcDeptChargeIassetRelaMapper.updateHtcDeptChargeIassetRela(entityMap);
			}
			return "{\"msg1\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");	
		}
	}

	@Override
	public String queryHtcDeptChargeIassetRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	   SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptChargeIassetRela> list = htcDeptChargeIassetRelaMapper.queryHtcDeptChargeIassetRela(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptChargeIassetRela> list = htcDeptChargeIassetRelaMapper.queryHtcDeptChargeIassetRela(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcDeptChargeIassetRela(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
	   try {
			
			htcDeptChargeIassetRelaMapper.deleteBatchHtcDeptChargeIassetRela(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");	
		}
	}

	@Override
	public String queryHtcDeptChargeIassetRelaCharge(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
	   SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptChargeIassetRela> list = htcDeptChargeIassetRelaMapper.queryHtcDeptChargeIassetRelaCharge(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptChargeIassetRela> list = htcDeptChargeIassetRelaMapper.queryHtcDeptChargeIassetRelaCharge(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryHtcDeptChargeIassetRelaIasset(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptChargeIassetRela> list = htcDeptChargeIassetRelaMapper.queryHtcDeptChargeIassetRelaIasset(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptChargeIassetRela> list = htcDeptChargeIassetRelaMapper.queryHtcDeptChargeIassetRelaIasset(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
}
