/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.hip.serviceImpl;

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
import com.chd.hrp.hip.dao.HipSupRefMapper;
import com.chd.hrp.hip.entity.HipSupRef;
import com.chd.hrp.hip.service.HipSupRefService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hipSupRefService")
public class HipSupRefServiceImpl implements HipSupRefService {

	private static Logger logger = Logger.getLogger(HipSupRefServiceImpl.class);

	@Resource(name = "hipSupRefMapper")
	private final HipSupRefMapper hipSupRefMapper = null;

	@Override
	public String queryHipSupRef(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HipSupRef> list = hipSupRefMapper.queryHipSupRef(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HipSupRef> list = hipSupRefMapper.queryHipSupRef(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HipSupRef queryHipSupRefByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hipSupRefMapper.queryHipSupRefByCode(entityMap);
	}

	@Override
	public String addHipSupRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String saveFlag = (String) entityMap.get("saveFlag");

			HipSupRef apt = hipSupRefMapper.queryHipSupRefByCode(entityMap);

			if (apt != null) {

				return "{\"msg\":\"编码重复 请修改编码.\",\"state\":\"error\"}";

			}

			if ("1".equals(saveFlag)) {

				String old_data = (String) entityMap.get("old_data");

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", entityMap.get("group_id"));

				map.put("hos_id", entityMap.get("hos_id"));

				String[] data_spl = old_data.split("@");

				map.put("ds_code", data_spl[0]);

				map.put("hip_sup_code", data_spl[1]);

				map.put("hrp_sup_code", data_spl[2]);

				hipSupRefMapper.deleteHipSupRef(map);

			}

			hipSupRefMapper.addHipSupRef(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHipSupRef\"}";

		}
	}

	@Override
	public String delHipSupRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipSupRefMapper.deleteHipSupRef(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHipSupRef\"}";

		}
	}

	@Override
	public String addBatchHipSupRef(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hipSupRefMapper.addBatchHipSupRef(entityList);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addBatchHipSupRef\"}";

		}
	}

	@Override
	public String deleteBatchHipSupRef(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hipSupRefMapper.deleteBatchHipSupRef(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchHipSupRef\"}";

		}

	}

}
