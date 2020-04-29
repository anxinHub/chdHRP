/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostConstituteChangeAnalysisMapper;
import com.chd.hrp.cost.service.CostConstituteChangeAnalysisService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 科室收入数据明细表_开单收入趋势分析<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costConstituteChangeAnalysisService")
public class CostConstituteChangeAnalysisServiceImpl implements CostConstituteChangeAnalysisService {

	private static Logger logger = Logger.getLogger(CostConstituteChangeAnalysisServiceImpl.class);

	@Resource(name = "costConstituteChangeAnalysisMapper")
	private final CostConstituteChangeAnalysisMapper costConstituteChangeAnalysisMapper = null;

	/**
	 * @Description 成本构成变化趋势<BR>
	 *              查询CostExecIncomeTrend分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryCostConstituteChange(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costConstituteChangeAnalysisMapper.queryCostConstituteChange(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = costConstituteChangeAnalysisMapper.queryCostConstituteChange(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}

	}

}
