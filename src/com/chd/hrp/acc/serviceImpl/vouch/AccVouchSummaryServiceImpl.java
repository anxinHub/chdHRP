/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.vouch;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccParaMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchSummaryMapper;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.acc.entity.AccVouchSummary;
import com.chd.hrp.acc.service.vouch.AccVouchSummaryService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 凭证主表<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accVouchSummaryService")
public class AccVouchSummaryServiceImpl implements AccVouchSummaryService {

	private static Logger logger = Logger.getLogger(AccVouchSummaryServiceImpl.class);

	@Resource(name = "accVouchSummaryMapper")
	private final AccVouchSummaryMapper accVouchSummaryMapper = null;

	/**
	 * @Description 凭证主表<BR>
	 *              添加AccVouchSummary
	 * @param AccVouchSummary
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAccVouchSummary(Map<String, Object> entityMap) throws DataAccessException {

//		AccVouchSummary AccVouchSummary = queryAccVouchSummaryByCode(entityMap);
//
//		if (AccVouchSummary != null) {
//
//			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";
//
//		}

		try {

			accVouchSummaryMapper.addAccVouchSummary(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccVouchSummary\"}";

		}

	}

	/**
	 * @Description 凭证主表<BR>
	 *              批量添加AccVouchSummary
	 * @param AccVouchSummary
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAccVouchSummary(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			accVouchSummaryMapper.addBatchAccVouchSummary(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccVouchSummary\"}";

		}
	}

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouchSummary分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccVouchSummary(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}
		
		return JSON.toJSONString(accVouchSummaryMapper.queryAccVouchSummary(entityMap, rowBounds));
		
	}

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouchSummaryByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AccVouchSummary queryAccVouchSummaryByCode(Map<String, Object> entityMap) throws DataAccessException {

		return accVouchSummaryMapper.queryAccVouchSummaryByCode(entityMap);

	}

	/**
	 * @Description 凭证主表<BR>
	 *              批量删除AccVouchSummary
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAccVouchSummary(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccVouchSummary\"}";

		}
		return null;

	}

	/**
	 * @Description 凭证主表<BR>
	 *              删除AccVouchSummary
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAccVouchSummary(Map<String, Object> entityMap) throws DataAccessException {

		try {
			accVouchSummaryMapper.deleteAccVouchSummary(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccVouchSummary\"}";

		}
	}

	/**
	 * @Description 凭证主表<BR>
	 *              更新AccVouchSummary
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAccVouchSummary(Map<String, Object> entityMap) throws DataAccessException {

		try {

			accVouchSummaryMapper.updateAccVouchSummary(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccVouchSummary\"}";

		}
	}

	/**
	 * @Description 凭证主表<BR>
	 *              批量更新AccVouchSummary
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAccVouchSummary(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			accVouchSummaryMapper.updateBatchAccVouchSummary(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccVouchSummary\"}";

		}

	}

	/**
	 * @Description 凭证主表<BR>
	 *              导入AccVouchSummary
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importAccVouchSummary(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

}
