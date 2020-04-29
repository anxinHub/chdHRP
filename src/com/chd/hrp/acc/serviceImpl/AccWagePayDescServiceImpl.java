/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.acc.serviceImpl;

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
import com.chd.hrp.acc.dao.AccWagePayDescMapper;
import com.chd.hrp.acc.entity.AccWagePayDesc;
import com.chd.hrp.acc.service.AccWagePayDescService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table: ACC_WAGE_PAY_DESC
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accWagePayDescService")
public class AccWagePayDescServiceImpl implements AccWagePayDescService {

	private static Logger logger = Logger.getLogger(AccWagePayDescServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "accWagePayDescMapper")
	private final AccWagePayDescMapper accWagePayDescMapper = null;

	/**
	 * @Description 添加<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAccWagePayDesc(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		AccWagePayDesc accWagePayDesc = queryAccWagePayDescByCode(entityMap);

		if (accWagePayDesc != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = accWagePayDescMapper.addAccWagePayDesc(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}");

		}

	}

	/**
	 * @Description 批量添加<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAccWagePayDesc(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			accWagePayDescMapper.addBatchAccWagePayDesc(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}");
		}

	}

	/**
	 * @Description 更新<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAccWagePayDesc(Map<String, Object> entityMap) throws DataAccessException {
		try {
			accWagePayDescMapper.updateAccWagePayDesc(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}");
		}
	}

	/**
	 * @Description 批量更新<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAccWagePayDesc(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			accWagePayDescMapper.updateBatchAccWagePayDesc(entityList);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}");
		}

	}

	/**
	 * @Description 删除<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAccWagePayDesc(Map<String, Object> entityMap) throws DataAccessException {
		try {
			accWagePayDescMapper.deleteAccWagePayDesc(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}");
		}

	}

	/**
	 * @Description 批量删除<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAccWagePayDesc(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			accWagePayDescMapper.deleteBatchAccWagePayDesc(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}");
		}
	}

	/**
	 * @Description 查询结果集<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccWagePayDesc(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AccWagePayDesc> list = (List<AccWagePayDesc>) accWagePayDescMapper.queryAccWagePayDesc(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AccWagePayDesc> list = (List<AccWagePayDesc>) accWagePayDescMapper.queryAccWagePayDesc(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public AccWagePayDesc queryAccWagePayDescByCode(Map<String, Object> entityMap) throws DataAccessException {

		return accWagePayDescMapper.queryAccWagePayDescByCode(entityMap);
	}

}
