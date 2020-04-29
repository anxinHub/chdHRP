/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.dict;

import java.util.HashMap;
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
import com.chd.hrp.ass.dao.dict.AssCardSetTabMapper;
import com.chd.hrp.ass.entity.dict.AssCardSetTab;
import com.chd.hrp.ass.service.dict.AssCardSetTabService;
import com.github.pagehelper.PageInfo;

@Service("assCardSetTabService")
public class AssCardSetTabServiceImpl implements AssCardSetTabService {

	private static Logger logger = Logger.getLogger(AssCardSetTabServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assCardSetTabMapper")
	private final AssCardSetTabMapper assCardSetTabMapper = null;

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050117 资产卡片显示表
		AssCardSetTab assCardSetTab = queryByCode(entityMap);

		if (assCardSetTab != null) {
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}

		try {
			int state = assCardSetTabMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			assCardSetTabMapper.addBatch(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {
			int state = assCardSetTabMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}
	/**
	 * 批量保存
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			assCardSetTabMapper.updateBatch(entityList);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {
			int state = assCardSetTabMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			assCardSetTabMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		/**
		 * 备注 先判断是否存在 存在即更新不存在则添加<br>
		 * 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		 * 判断是否名称相同 判断是否 编码相同 等一系列规则
		 */
		// 判断是否存在对象050117 资产卡片显示表
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<?> list = assCardSetTabMapper.queryExists(mapVo);
		if (list.size() > 0) {
			int state = assCardSetTabMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}

		try {
			int state = assCardSetTabMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<?> list = assCardSetTabMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<?> list = assCardSetTabMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assCardSetTabMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assCardSetTabMapper.queryByUniqueness(entityMap);
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assCardSetTabMapper.queryExists(entityMap);
	}

	@Override
	public List<?> queryAll(Map<String, Object> entityMap) throws DataAccessException {
		List<AssCardSetTab> list = (List<AssCardSetTab>) assCardSetTabMapper.query(entityMap);

		return list;
	}

}
