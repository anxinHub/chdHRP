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
import com.chd.hrp.htcg.dao.setout.HtcgMaterialAdviceGroupMapper;
import com.chd.hrp.htcg.entity.setout.HtcgMaterialAdviceGroup;
import com.chd.hrp.htcg.service.setout.HtcgMaterialAdviceGroupService;
import com.github.pagehelper.PageInfo;

@Service("htcgMaterialAdviceGroupService")
public class HtcgMaterialAdviceGroupServiceImpl implements HtcgMaterialAdviceGroupService {

	private static Logger logger = Logger.getLogger(HtcgMaterialAdviceGroupServiceImpl.class);

	@Resource(name = "htcgMaterialAdviceGroupMapper")
	private final HtcgMaterialAdviceGroupMapper htcgMaterialAdviceGroupMapper = null;

	@Override
	public String initHtcgMaterialAdviceGroup(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
			    htcgMaterialAdviceGroupMapper.initHtcgMaterialAdviceGroup(entityMap);
				return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\""+entityMap.get("msg")+"失败\"}");
			}
	}

	@Override
	public String queryHtcgMaterialAdviceGroup(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HtcgMaterialAdviceGroup> list = htcgMaterialAdviceGroupMapper.queryHtcgMaterialAdviceGroup(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgMaterialAdviceGroup> list = htcgMaterialAdviceGroupMapper.queryHtcgMaterialAdviceGroup(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcgMaterialAdviceGroup(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			    htcgMaterialAdviceGroupMapper.deleteBatchHtcgMaterialAdviceGroup(list);
			    
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}
	
	
}
