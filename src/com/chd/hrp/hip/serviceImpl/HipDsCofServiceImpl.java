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
import com.chd.hrp.hip.dao.HipDsCofMapper;
import com.chd.hrp.hip.entity.HipDsCof;
import com.chd.hrp.hip.service.HipDsCofService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hipDsCofService")
public class HipDsCofServiceImpl implements HipDsCofService {

	private static Logger logger = Logger.getLogger(HipDsCofServiceImpl.class);

	@Resource(name = "hipDsCofMapper")
	private final HipDsCofMapper hipDsCofMapper = null;

	@Override
	public String queryHipDsCof(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HipDsCof> list = hipDsCofMapper.queryHipDsCof(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HipDsCof> list = hipDsCofMapper.queryHipDsCof(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HipDsCof queryHipDsCofByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hipDsCofMapper.queryHipDsCofByCode(entityMap);
	}

	@Override
	public String updateHipDsCof(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipDsCofMapper.updateHipDsCof(entityMap);

			return "{\"msg\":\"更改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更改失败 数据库异常 请联系管理员! 方法 updateHipDsCof\"}";

		}
	}

	@Override
	public String addHipDsCof(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String saveFlag = (String) entityMap.get("saveFlag");

			if (!"1".equals(saveFlag)) {

				HipDsCof apt = hipDsCofMapper.queryHipDsCofByCode(entityMap);

				if (apt != null) {

					return "{\"msg\":\"编码重复 请修改编码.\",\"state\":\"error\"}";

				}

			}

			if ("1".equals(saveFlag)) {

				hipDsCofMapper.updateHipDsCof(entityMap);

			} else {

				hipDsCofMapper.addHipDsCof(entityMap);

			}

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHipDsCof\"}";

		}
	}

	@Override
	public String delHipDsCof(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipDsCofMapper.deleteHipDsCof(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHipDsCof\"}";

		}
	}

}
