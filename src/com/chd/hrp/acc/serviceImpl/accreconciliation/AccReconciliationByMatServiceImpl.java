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
import com.chd.hrp.acc.dao.accreconciliation.AccReconciliationByMatMapper;
import com.chd.hrp.acc.service.accreconciliation.AccReconciliationByMatService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 部门字典属性表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Service("accReconciliationByMatService")
public class AccReconciliationByMatServiceImpl implements AccReconciliationByMatService {

	private static Logger logger = Logger.getLogger(AccReconciliationByMatServiceImpl.class);

	@Resource(name = "accReconciliationByMatMapper")
	private final AccReconciliationByMatMapper accReconciliationByMatMapper = null;

	@Override
	public String queryAccReconciliationByMat(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Map<String, Object>> list = accReconciliationByMatMapper.queryAccReconciliationByMat(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
	}

}
