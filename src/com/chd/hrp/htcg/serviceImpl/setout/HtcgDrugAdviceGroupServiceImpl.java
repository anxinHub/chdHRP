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
import com.chd.hrp.htcg.dao.setout.HtcgDrugAdviceGroupMapper;
import com.chd.hrp.htcg.entity.setout.HtcgDrugAdviceGroup;
import com.chd.hrp.htcg.service.setout.HtcgDrugAdviceGroupService;
import com.github.pagehelper.PageInfo;

@Service("htcgDrugAdviceGroupService")
public class HtcgDrugAdviceGroupServiceImpl implements HtcgDrugAdviceGroupService {

	private static Logger logger = Logger.getLogger(HtcgDrugAdviceGroupServiceImpl.class);

	@Resource(name = "htcgDrugAdviceGroupMapper")
	private final HtcgDrugAdviceGroupMapper htcgDrugAdviceGroupMapper = null;

	@Override
	public String initHtcgDrugAdviceGroup(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
			  htcgDrugAdviceGroupMapper.initHtcgDrugAdviceGroup(entityMap);
			    
				return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\""+entityMap.get("msg")+"失败\"}");
			}
	}

	@Override
	public String queryHtcgDrugAdviceGroup(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HtcgDrugAdviceGroup> list = htcgDrugAdviceGroupMapper.queryHtcgDrugAdviceGroup(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgDrugAdviceGroup> list = htcgDrugAdviceGroupMapper.queryHtcgDrugAdviceGroup(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcgDrugAdviceGroup(
			List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			 htcgDrugAdviceGroupMapper.deleteBatchHtcgDrugAdviceGroup(entityMap);
			    
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

}
