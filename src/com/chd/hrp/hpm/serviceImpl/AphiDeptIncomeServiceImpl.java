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
import com.chd.hrp.hpm.dao.AphiDeptIncomeMapper;
import com.chd.hrp.hpm.entity.AphiDeptIncome;
import com.chd.hrp.hpm.service.AphiDeptIncomeService;
import com.github.pagehelper.PageInfo;

@Service("aphiDeptIncomeService")
public class AphiDeptIncomeServiceImpl implements AphiDeptIncomeService {

	private static Logger logger = Logger.getLogger(AphiDeptIncomeServiceImpl.class);

	@Resource(name = "aphiDeptIncomeMapper")
	private AphiDeptIncomeMapper aphiDeptIncomeMapper = null;

	@Override
	public String queryDeptIncomeMain(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiDeptIncome> list = aphiDeptIncomeMapper.queryDeptIncomeMain(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiDeptIncome> list = aphiDeptIncomeMapper.queryDeptIncomeMain(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public String deleteBatchDeptIncomeMaping(List<Map<String, Object>> dataAddedBatch) {
		try {

			aphiDeptIncomeMapper.deleteBatchDeptIncomeMaping(dataAddedBatch);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptMaping\"}";

		}

	}

	@Override
	public String addBatchDeptIncomeMaping(List<Map<String, Object>> dataAddedBatch) throws DataAccessException {
		try {

			aphiDeptIncomeMapper.addBatchPrmDeptMaping(dataAddedBatch);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptMaping\"}";

		}
	}

	@Override
	public String deleteHpmDeptIncome(List<Map<String, Object>> listVo) throws DataAccessException {
		try {

			aphiDeptIncomeMapper.deleteBatchDeptIncomeMaping(listVo);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptMaping\"}";

		}
	}

}
