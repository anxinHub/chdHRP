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
import com.chd.hrp.hip.dao.HipRefHrpSourceMapper;
import com.chd.hrp.hip.entity.HipRefHrpSource;
import com.chd.hrp.hip.service.HipRefHrpSourceService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hipRefHrpSourceService")
public class HipRefHrpSourceServiceImpl implements HipRefHrpSourceService {

	private static Logger logger = Logger.getLogger(HipRefHrpSourceServiceImpl.class);

	@Resource(name = "hipRefHrpSourceMapper")
	private final HipRefHrpSourceMapper hipRefHrpSourceMapper = null;

	@Override
	public String queryHipRefHrpSource(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HipRefHrpSource> list = hipRefHrpSourceMapper.queryHipRefHrpSource(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HipRefHrpSource> list = hipRefHrpSourceMapper.queryHipRefHrpSource(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HipRefHrpSource queryHipRefHrpSourceByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hipRefHrpSourceMapper.queryHipRefHrpSourceByCode(entityMap);
	}

	@Override
	public String updateHipRefHrpSource(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipRefHrpSourceMapper.updateHipRefHrpSource(entityMap);

			return "{\"msg\":\"更改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更改失败 数据库异常 请联系管理员! 方法 updateHipRefHrpSource\"}";

		}
	}

	@Override
	public String addHipRefHrpSource(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String saveFlag = (String) entityMap.get("saveFlag");

			if (!"1".equals(saveFlag)) {

				HipRefHrpSource apt = hipRefHrpSourceMapper.queryHipRefHrpSourceByCode(entityMap);

				if (apt != null) {

					return "{\"msg\":\"编码重复 请修改编码.\",\"state\":\"error\"}";

				}

			}

			if ("1".equals(saveFlag)) {

				hipRefHrpSourceMapper.updateHipRefHrpSource(entityMap);

			} else {

				hipRefHrpSourceMapper.addHipRefHrpSource(entityMap);

			}

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHipRefHrpSource\"}";

		}
	}

	@Override
	public String delHipRefHrpSource(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipRefHrpSourceMapper.deleteHipRefHrpSource(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHipRefHrpSource\"}";

		}
	}

}
