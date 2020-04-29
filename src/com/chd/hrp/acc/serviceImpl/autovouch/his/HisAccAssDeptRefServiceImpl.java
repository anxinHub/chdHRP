/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.autovouch.his;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.autovouch.his.HisAccAssDeptRefMapper;
import com.chd.hrp.acc.entity.autovouch.HisAccAssDeptRef;
import com.chd.hrp.acc.service.autovouch.his.HisAccAssDeptRefService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hisAccAssDeptRefService")
public class HisAccAssDeptRefServiceImpl implements HisAccAssDeptRefService {

	private static Logger logger = Logger.getLogger(HisAccAssDeptRefServiceImpl.class);

	@Resource(name = "hisAccAssDeptRefMapper")
	private final HisAccAssDeptRefMapper hisAccAssDeptRefMapper = null;

	@Override
	public String queryHisAccAssDeptRef(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = hisAccAssDeptRefMapper.queryHisAccAssDeptRef(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = hisAccAssDeptRefMapper.queryHisAccAssDeptRef(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HisAccAssDeptRef queryHisAccAssDeptRefByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hisAccAssDeptRefMapper.queryHisAccAssDeptRefByCode(entityMap);
	}

	@Override
	public String updateHisAccAssDeptRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hisAccAssDeptRefMapper.updateHisAccAssDeptRef(entityMap);

			return "{\"msg\":\"更改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更改失败 数据库异常 请联系管理员! 方法 updateHisAccAssDeptRef\"}";

		}
	}

	@Override
	public String addHisAccAssDeptRef(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			hisAccAssDeptRefMapper.deleteHisAccAssDeptRef(entityMap);

			hisAccAssDeptRefMapper.addHisAccAssDeptRef(entityMap);
				
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHisAccAssDeptRef\"}";

		}
	}

	@Override
	public String delHisAccAssDeptRef(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			hisAccAssDeptRefMapper.deleteHisAccAssDeptRef(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHisAccAssDeptRef\"}";

		}
	}

	@Override
	public List<Map<String, Object>> queryHisAccAssDeptRefPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = hisAccAssDeptRefMapper.queryHisAccAssDeptRef(entityMap);

		return list;
	}

}
