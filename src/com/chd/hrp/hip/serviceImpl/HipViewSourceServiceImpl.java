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
import com.chd.hrp.hip.dao.HipViewSourceMapper;
import com.chd.hrp.hip.entity.HipViewSource;
import com.chd.hrp.hip.service.HipViewSourceService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hipViewSourceService")
public class HipViewSourceServiceImpl implements HipViewSourceService {

	private static Logger logger = Logger.getLogger(HipViewSourceServiceImpl.class);

	@Resource(name = "hipViewSourceMapper")
	private final HipViewSourceMapper hipViewSourceMapper = null;

	@Override
	public String queryHipViewSource(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HipViewSource> list = hipViewSourceMapper.queryHipViewSource(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HipViewSource> list = hipViewSourceMapper.queryHipViewSource(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HipViewSource queryHipViewSourceByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hipViewSourceMapper.queryHipViewSourceByCode(entityMap);
	}

	@Override
	public String updateHipViewSource(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipViewSourceMapper.updateHipViewSource(entityMap);

			return "{\"msg\":\"更改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更改失败 数据库异常 请联系管理员! 方法 updateHipViewSource\"}";

		}
	}

	@Override
	public String addHipViewSource(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String saveFlag = (String) entityMap.get("saveFlag");

			if (!"1".equals(saveFlag)) {

				HipViewSource apt = hipViewSourceMapper.queryHipViewSourceByCode(entityMap);

				if (apt != null) {

					return "{\"msg\":\"编码重复 请修改编码.\",\"state\":\"error\"}";

				}

			}

			if ("1".equals(saveFlag)) {

				hipViewSourceMapper.updateHipViewSource(entityMap);

			} else {

				hipViewSourceMapper.addHipViewSource(entityMap);

			}

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHipViewSource\"}";

		}
	}

	@Override
	public String delHipViewSource(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipViewSourceMapper.deleteHipViewSource(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHipViewSource\"}";

		}
	}

}
