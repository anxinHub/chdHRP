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
import com.chd.hrp.hip.dao.HipSourceRefMapper;
import com.chd.hrp.hip.entity.HipSourceRef;
import com.chd.hrp.hip.service.HipSourceRefService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hipSourceRefService")
public class HipSourceRefServiceImpl implements HipSourceRefService {

	private static Logger logger = Logger.getLogger(HipSourceRefServiceImpl.class);

	@Resource(name = "hipSourceRefMapper")
	private final HipSourceRefMapper hipSourceRefMapper = null;

	@Override
	public String queryHipSourceRef(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HipSourceRef> list = hipSourceRefMapper.queryHipSourceRef(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HipSourceRef> list = hipSourceRefMapper.queryHipSourceRef(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HipSourceRef queryHipSourceRefByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hipSourceRefMapper.queryHipSourceRefByCode(entityMap);
	}

	@Override
	public String addHipSourceRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String saveFlag = (String) entityMap.get("saveFlag");

			HipSourceRef apt = hipSourceRefMapper.queryHipSourceRefByCode(entityMap);

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

				map.put("hip_source_code", data_spl[1]);

				map.put("hrp_source_code", data_spl[2]);

				hipSourceRefMapper.deleteHipSourceRef(map);

			}

			hipSourceRefMapper.addHipSourceRef(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHipSourceRef\"}";

		}
	}

	@Override
	public String delHipSourceRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipSourceRefMapper.deleteHipSourceRef(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHipSourceRef\"}";

		}
	}

	@Override
	public String addBatchHipSourceRef(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hipSourceRefMapper.addBatchHipSourceRef(entityList);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addBatchHipSourceRef\"}";

		}
	}

	@Override
	public String deleteBatchHipSourceRef(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hipSourceRefMapper.deleteBatchHipSourceRef(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchHipSourceRef\"}";

		}

	}

}
