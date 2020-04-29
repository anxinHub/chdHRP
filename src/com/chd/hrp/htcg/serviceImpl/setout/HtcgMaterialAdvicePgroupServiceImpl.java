package com.chd.hrp.htcg.serviceImpl.setout;

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
import com.chd.hrp.htcg.dao.setout.HtcgMaterialAdvicePgroupMapper;
import com.chd.hrp.htcg.entity.setout.HtcgMaterialAdvicePgroup;
import com.chd.hrp.htcg.service.setout.HtcgMaterialAdvicePgroupService;
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


@Service("htcgMaterialAdvicePgroupService")
public class HtcgMaterialAdvicePgroupServiceImpl implements HtcgMaterialAdvicePgroupService {

	private static Logger logger = Logger.getLogger(HtcgMaterialAdvicePgroupServiceImpl.class);
	
	@Resource(name = "htcgMaterialAdvicePgroupMapper")
	private final HtcgMaterialAdvicePgroupMapper htcgMaterialAdvicePgroupMapper = null;

	@Override
	public String initHtcgMaterialAdvicePgroup(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
			  htcgMaterialAdvicePgroupMapper.initHtcgMaterialAdvicePgroup(entityMap);
			  
				return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\""+entityMap.get("msg")+"失败\"}");
			}
	}

	@Override
	public String queryHtcgMaterialAdvicePgroup(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HtcgMaterialAdvicePgroup> list = htcgMaterialAdvicePgroupMapper.queryHtcgMaterialAdvicePgroup(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgMaterialAdvicePgroup> list = htcgMaterialAdvicePgroupMapper.queryHtcgMaterialAdvicePgroup(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String calculateHtcgMaterialAdvicePgroup(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
			  htcgMaterialAdvicePgroupMapper.calculateHtcgMaterialAdvicePgroup(entityMap);
			  
				return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\""+entityMap.get("msg")+"失败\"}");
			}
	}

	@Override
	public String admittanceHtcgMaterialAdvicePgroup(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			  htcgMaterialAdvicePgroupMapper.admittanceHtcgMaterialAdvicePgroup(entityMap);
			  
				return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\""+entityMap.get("msg")+"失败\"}");
			}
	}

	@Override
	public String deleteBatchHtcgMaterialAdvicePgroup(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgMaterialAdvicePgroupMapper.deleteBatchHtcgMaterialAdvicePgroup(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
	
}
