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
import com.chd.hrp.htcg.dao.setout.HtcgMedicalAdviceGroupMapper;
import com.chd.hrp.htcg.entity.setout.HtcgMedicalAdviceGroup;
import com.chd.hrp.htcg.service.setout.HtcgMedicalAdviceGroupService;
import com.github.pagehelper.PageInfo;

@Service("htcgMedicalAdviceGroupService")
public class HtcgMedicalAdviceGroupServiceImpl implements HtcgMedicalAdviceGroupService {

	private static Logger logger = Logger.getLogger(HtcgMedicalAdviceGroupServiceImpl.class);

	@Resource(name = "htcgMedicalAdviceGroupMapper")
	private final HtcgMedicalAdviceGroupMapper htcgMedicalAdviceGroupMapper = null;

	@Override
	public String initHtcgMedicalAdviceGroup(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	   try {
			
		    htcgMedicalAdviceGroupMapper.initHtcgMedicalAdviceGroup(entityMap);
		    
			return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\""+entityMap.get("msg")+"失败\"}");
		}
	}

	@Override
	public String queryHtcgMedicalAdviceGroup(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HtcgMedicalAdviceGroup> list = htcgMedicalAdviceGroupMapper.queryHtcgMedicalAdviceGroup(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgMedicalAdviceGroup> list = htcgMedicalAdviceGroupMapper.queryHtcgMedicalAdviceGroup(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcgMedicalAdviceGroup(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
			    htcgMedicalAdviceGroupMapper.deleteBatchHtcgMedicalAdviceGroup(list);
			    
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

}
