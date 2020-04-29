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
import com.chd.hrp.hip.dao.HipInitViewMapper;
import com.chd.hrp.hip.entity.HipInitView;
import com.chd.hrp.hip.service.HipInitViewService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hipInitViewService")
public class HipInitViewServiceImpl implements HipInitViewService {

	private static Logger logger = Logger.getLogger(HipInitViewServiceImpl.class);

	@Resource(name = "hipInitViewMapper")
	private final HipInitViewMapper hipInitViewMapper = null;

	@Override
	public String queryHipInitView(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HipInitView> list = hipInitViewMapper.queryHipInitView(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HipInitView> list = hipInitViewMapper.queryHipInitView(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HipInitView queryHipInitViewByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hipInitViewMapper.queryHipInitViewByCode(entityMap);
	}

	@Override
	public String updateHipInitView(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipInitViewMapper.updateHipInitView(entityMap);

			return "{\"msg\":\"更改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更改失败 数据库异常 请联系管理员! 方法 updateHipInitView\"}";

		}
	}

	@Override
	public String addHipInitView(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String saveFlag = (String) entityMap.get("saveFlag");

			if (!"1".equals(saveFlag)) {

				HipInitView apt = hipInitViewMapper.queryHipInitViewByCode(entityMap);

				if (apt != null) {

					return "{\"msg\":\"编码重复 请修改编码.\",\"state\":\"error\"}";

				}

			}

			if ("1".equals(saveFlag)) {

				hipInitViewMapper.updateHipInitView(entityMap);

			} else {

				hipInitViewMapper.addHipInitView(entityMap);

			}

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHipInitView\"}";

		}
	}

	@Override
	public String delHipInitView(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipInitViewMapper.deleteHipInitView(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHipInitView\"}";

		}
	}

}
