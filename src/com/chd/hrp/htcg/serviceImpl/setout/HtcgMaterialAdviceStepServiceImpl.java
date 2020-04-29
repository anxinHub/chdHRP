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
import com.chd.hrp.htcg.dao.setout.HtcgMaterialAdviceStepMapper;
import com.chd.hrp.htcg.entity.setout.HtcgMaterialAdviceStep;
import com.chd.hrp.htcg.service.setout.HtcgMaterialAdviceStepService;
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

@Service("htcgMaterialAdviceStepService")
public class HtcgMaterialAdviceStepServiceImpl implements HtcgMaterialAdviceStepService {

	private static Logger logger = Logger.getLogger(HtcgMaterialAdviceStepServiceImpl.class);
	
	@Resource(name = "htcgMaterialAdviceStepMapper")
	private final HtcgMaterialAdviceStepMapper htcgMaterialAdviceStepMapper = null;

	@Override
	public String initHtcgMaterialAdviceStep(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcgMaterialAdviceStepMapper.initHtcgMaterialAdviceStep(entityMap);
		     return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"划分失败\"}");
		}
	}

	@Override
	public String queryHtcgMaterialAdviceStep(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HtcgMaterialAdviceStep> list = htcgMaterialAdviceStepMapper.queryHtcgMaterialAdviceStep(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgMaterialAdviceStep> list = htcgMaterialAdviceStepMapper.queryHtcgMaterialAdviceStep(entityMap,rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String deleteBatchHtcgMaterialAdviceStep(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgMaterialAdviceStepMapper.deleteBatchHtcgMaterialAdviceStep(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
    
}
