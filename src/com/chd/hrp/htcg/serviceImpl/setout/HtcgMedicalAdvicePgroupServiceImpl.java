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
import com.chd.hrp.htcg.dao.setout.HtcgMedicalAdvicePgroupMapper;
import com.chd.hrp.htcg.entity.setout.HtcgMedicalAdvicePgroup;
import com.chd.hrp.htcg.service.setout.HtcgMedicalAdvicePgroupService;
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


@Service("htcgMedicalAdvicePgroupService")
public class HtcgMedicalAdvicePgroupServiceImpl implements HtcgMedicalAdvicePgroupService {

	private static Logger logger = Logger.getLogger(HtcgMedicalAdvicePgroupServiceImpl.class);
	
	@Resource(name = "htcgMedicalAdvicePgroupMapper")
	private final HtcgMedicalAdvicePgroupMapper htcgMedicalAdvicePgroupMapper = null;

	@Override
	public String initHtcgMedicalAdvicePgroup(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
			  htcgMedicalAdvicePgroupMapper.initHtcgMedicalAdvicePgroup(entityMap);
			  
				return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\""+entityMap.get("msg")+"失败\"}");
			}
	}

	@Override
	public String queryHtcgMedicalAdvicePgroup(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HtcgMedicalAdvicePgroup> list = htcgMedicalAdvicePgroupMapper.queryHtcgMedicalAdvicePgroup(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgMedicalAdvicePgroup> list = htcgMedicalAdvicePgroupMapper.queryHtcgMedicalAdvicePgroup(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String calculateHtcgMedicalAdvicePgroup(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			  htcgMedicalAdvicePgroupMapper.calculateHtcgMedicalAdvicePgroup(entityMap);
			  
				return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\""+entityMap.get("msg")+"失败\"}");
			}
	}

	@Override
	public String admittanceHtcgMedicalAdvicePgroup(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
			    htcgMedicalAdvicePgroupMapper.admittanceHtcgMedicalAdvicePgroup(entityMap);
				return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\""+entityMap.get("msg")+"失败\"}");
			}
	}

	@Override
	public String deleteBatchHtcgMedicalAdvicePgroup(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
			    htcgMedicalAdvicePgroupMapper.deleteBatchHtcgMedicalAdvicePgroup(list);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}
    
}
