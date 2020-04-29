/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.hip.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hip.dao.HipDblinkMapper;
import com.chd.hrp.hip.entity.HipDblink;
import com.chd.hrp.hip.service.HipDblinkService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hipDblinkService")
public class HipDblinkServiceImpl implements HipDblinkService {

	private static Logger logger = Logger.getLogger(HipDblinkServiceImpl.class);

	@Resource(name = "hipDblinkMapper")
	private final HipDblinkMapper hipDblinkMapper = null;

	@Override
	public String queryHipDblink(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HipDblink> list = hipDblinkMapper.queryHipDblink(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HipDblink> list = hipDblinkMapper.queryHipDblink(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HipDblink queryHipDblinkByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hipDblinkMapper.queryHipDblinkByCode(entityMap);
	}

	@Override
	public String updateHipDblink(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipDblinkMapper.updateHipDblink(entityMap);

			return "{\"msg\":\"更改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更改失败 数据库异常 请联系管理员! 方法 updateHipDblink\"}";

		}
	}

	@Override
	public String addHipDblink(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String saveFlag = (String) entityMap.get("saveFlag");

			if (!"1".equals(saveFlag)) {

				HipDblink apt = hipDblinkMapper.queryHipDblinkByCode(entityMap);

				if (apt != null) {

					return "{\"msg\":\"编码重复 请修改编码.\",\"state\":\"error\"}";

				}

			}

			if ("1".equals(saveFlag)) {

				hipDblinkMapper.updateHipDblink(entityMap);

			} else {

				hipDblinkMapper.addHipDblink(entityMap);

			}

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHipDblink\"}";

		}
	}

	@Override
	public String delHipDblink(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipDblinkMapper.deleteHipDblink(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHipDblink\"}";

		}
	}

}
