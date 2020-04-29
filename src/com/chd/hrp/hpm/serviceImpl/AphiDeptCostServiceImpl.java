package com.chd.hrp.hpm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiDeptCostMapper;
import com.chd.hrp.hpm.entity.AphiDeptCost;
import com.chd.hrp.hpm.service.AphiDeptCostService;
import com.github.pagehelper.PageInfo;

@Service("aphiDeptCostService")
public class AphiDeptCostServiceImpl implements AphiDeptCostService {

	private static Logger logger = Logger.getLogger(AphiDeptCostServiceImpl.class);

	@Resource(name = "aphiDeptCostMapper")
	private AphiDeptCostMapper aphiDeptCostMapper = null;

	@Override
	public String queryDeptCostMain(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiDeptCost> list = aphiDeptCostMapper.queryDeptCostMain(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiDeptCost> list = aphiDeptCostMapper.queryDeptCostMain(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String deleteBatchDeptCostMaping(List<Map<String, Object>> dataAddedBatch) throws DataAccessException {
		try {

			aphiDeptCostMapper.deleteBatchDeptCostMaping(dataAddedBatch);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptMaping\"}";

		}

	}

	@Override
	public String addBatchDeptCostMaping(List<Map<String, Object>> dataAddedBatch) throws DataAccessException {
		try {

			aphiDeptCostMapper.addBatchPrmDeptMaping(dataAddedBatch);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptMaping\"}";

		}
	}

	@Override
	public String deleteHpmDeptCost(List<Map<String, Object>> listVo) throws DataAccessException {
		try {

			aphiDeptCostMapper.deleteBatchDeptCostMaping(listVo);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptMaping\"}";

		}
	}

}
