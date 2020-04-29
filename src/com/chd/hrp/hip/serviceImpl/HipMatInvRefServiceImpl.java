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
import com.chd.hrp.hip.dao.HipMatInvRefMapper;
import com.chd.hrp.hip.entity.HipMatInvRef;
import com.chd.hrp.hip.service.HipMatInvRefService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hipMatInvRefService")
public class HipMatInvRefServiceImpl implements HipMatInvRefService {

	private static Logger logger = Logger.getLogger(HipMatInvRefServiceImpl.class);

	@Resource(name = "hipMatInvRefMapper")
	private final HipMatInvRefMapper hipMatInvRefMapper = null;

	@Override
	public String queryHipMatInvRef(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HipMatInvRef> list = hipMatInvRefMapper.queryHipMatInvRef(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HipMatInvRef> list = hipMatInvRefMapper.queryHipMatInvRef(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HipMatInvRef queryHipMatInvRefByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hipMatInvRefMapper.queryHipMatInvRefByCode(entityMap);
	}

	@Override
	public String addHipMatInvRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String saveFlag = (String) entityMap.get("saveFlag");

			HipMatInvRef apt = hipMatInvRefMapper.queryHipMatInvRefByCode(entityMap);

			if (apt != null) {

				return "{\"msg\":\"编码重复 请修改编码.\",\"state\":\"error\"}";

			}

			if ("1".equals(saveFlag)) {

				String old_data = (String) entityMap.get("old_data");

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", entityMap.get("group_id"));

				map.put("hos_id", entityMap.get("hos_id"));

				String[] data_spl = old_data.split("@");
				
				map.put("copy_code", data_spl[0]);

				map.put("ds_code", data_spl[1]);

				map.put("hip_inv_code", data_spl[2]);

				map.put("hrp_inv_code", data_spl[3]);

				hipMatInvRefMapper.deleteHipMatInvRef(map);

			}

			hipMatInvRefMapper.addHipMatInvRef(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHipMatInvRef\"}";

		}
	}

	@Override
	public String delHipMatInvRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipMatInvRefMapper.deleteHipMatInvRef(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHipMatInvRef\"}";

		}
	}

	@Override
	public String addBatchHipMatInvRef(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hipMatInvRefMapper.addBatchHipMatInvRef(entityList);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addBatchHipMatInvRef\"}";

		}
	}

	@Override
	public String deleteBatchHipMatInvRef(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hipMatInvRefMapper.deleteBatchHipMatInvRef(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchHipMatInvRef\"}";

		}

	}

}
