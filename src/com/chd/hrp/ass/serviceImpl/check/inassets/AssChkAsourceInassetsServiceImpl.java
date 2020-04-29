﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.check.inassets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.check.inassets.AssChkAsourceInassetsMapper;
import com.chd.hrp.ass.entity.check.inassets.AssChkAsourceInassets;
import com.chd.hrp.ass.service.check.inassets.AssChkAsourceInassetsService;
import com.github.pagehelper.PageInfo;

/**
 * 	
 * @Description: 050805 资产盘亏申请单资金来源(无形资产)
 * @Table: ASS_CHK_A_SOURCE_INASSETS
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
@Service("assChkAsourceInassetsService")
public class AssChkAsourceInassetsServiceImpl implements AssChkAsourceInassetsService {

	private static Logger logger = Logger.getLogger(AssChkAsourceInassetsServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assChkAsourceInassetsMapper")
	private final AssChkAsourceInassetsMapper assChkAsourceInassetsMapper = null;

	/**
	 * @Description 添加050805 资产盘亏申请单资金来源(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050805 资产盘亏申请单资金来源(无形资产)
		AssChkAsourceInassets assChkAsourceInassets = queryByCode(entityMap);

		if (assChkAsourceInassets != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assChkAsourceInassetsMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050805 资产盘亏申请单资金来源(无形资产)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assChkAsourceInassetsMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050805 资产盘亏申请单资金来源(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assChkAsourceInassetsMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050805 资产盘亏申请单资金来源(无形资产)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assChkAsourceInassetsMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050805 资产盘亏申请单资金来源(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assChkAsourceInassetsMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050805 资产盘亏申请单资金来源(无形资产)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assChkAsourceInassetsMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}
	}

	/**
	 * @Description 添加050805 资产盘亏申请单资金来源(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		/**
		 * 备注 先判断是否存在 存在即更新不存在则添加<br>
		 * 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		 * 判断是否名称相同 判断是否 编码相同 等一系列规则
		 */
		// 判断是否存在对象050805 资产盘亏申请单资金来源(无形资产)
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<AssChkAsourceInassets> list = (List<AssChkAsourceInassets>) assChkAsourceInassetsMapper
				.queryExists(mapVo);

		if (list.size() > 0) {

			int state = assChkAsourceInassetsMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = assChkAsourceInassetsMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}

	}

	/**
	 * @Description 查询结果集050805 资产盘亏申请单资金来源(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssChkAsourceInassets> list = (List<AssChkAsourceInassets>) assChkAsourceInassetsMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssChkAsourceInassets> list = (List<AssChkAsourceInassets>) assChkAsourceInassetsMapper
					.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050805 资产盘亏申请单资金来源(无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assChkAsourceInassetsMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050805 资产盘亏申请单资金来源(无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssChkAsourceInassets
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assChkAsourceInassetsMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050805 资产盘亏申请单资金来源(无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssChkAsourceInassets>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assChkAsourceInassetsMapper.queryExists(entityMap);
	}

}