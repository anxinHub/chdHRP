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
import com.chd.hrp.htcg.dao.setout.HtcgDrugAdviceStepMapper;
import com.chd.hrp.htcg.entity.setout.HtcgDrugAdviceStep;
import com.chd.hrp.htcg.service.setout.HtcgDrugAdviceStepService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("htcgDrugAdviceStepService")
public class HtcgDrugAdviceStepServiceImpl implements HtcgDrugAdviceStepService {

	private static Logger logger = Logger.getLogger(HtcgDrugAdviceStepServiceImpl.class);
	@Resource(name = "htcgDrugAdviceStepMapper")
	private final HtcgDrugAdviceStepMapper htcgDrugAdviceStepMapper = null;

	@Override
	public String initHtcgDrugAdviceStep(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			 htcgDrugAdviceStepMapper.initHtcgDrugAdviceStep(entityMap);
		     return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"划分失败\"}");
		}
	}
	
	@Override
	public String queryHtcgDrugAdviceStep(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HtcgDrugAdviceStep> list = htcgDrugAdviceStepMapper.queryHtcgDrugAdviceStep(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HtcgDrugAdviceStep> list = htcgDrugAdviceStepMapper.queryHtcgDrugAdviceStep(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	public String deleteBatchHtcgDrugAdviceStep(List<Map<String, Object>> entityList)
			throws DataAccessException {

		try {

		    htcgDrugAdviceStepMapper.deleteBatchHtcgDrugAdviceStep(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");

		}

	}

}
