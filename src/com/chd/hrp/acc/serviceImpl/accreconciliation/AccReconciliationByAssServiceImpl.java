/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.accreconciliation;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.accreconciliation.AccReconciliationByAssMapper;
import com.chd.hrp.acc.service.accreconciliation.AccReconciliationByAssService;
import com.github.pagehelper.PageInfo;


@Service("accReconciliationByAssService")
public class AccReconciliationByAssServiceImpl implements AccReconciliationByAssService {

	private static Logger logger = Logger.getLogger(AccReconciliationByAssServiceImpl.class);

	@Resource(name = "accReconciliationByAssMapper")
	private final AccReconciliationByAssMapper accReconciliationByAssMapper = null;

	@Override
	public String queryAccReconciliationByAss(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Map<String, Object>> list = accReconciliationByAssMapper.queryAccReconciliationByAss(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
	}

}
