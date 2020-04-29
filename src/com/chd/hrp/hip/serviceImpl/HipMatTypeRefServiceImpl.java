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
import com.chd.hrp.hip.dao.HipMatTypeRefMapper;
import com.chd.hrp.hip.entity.HipMatTypeRef;
import com.chd.hrp.hip.service.HipMatTypeRefService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hipMatTypeRefService")
public class HipMatTypeRefServiceImpl implements HipMatTypeRefService {

	private static Logger logger = Logger.getLogger(HipMatTypeRefServiceImpl.class);

	@Resource(name = "hipMatTypeRefMapper")
	private final HipMatTypeRefMapper hipMatTypeRefMapper = null;

	@Override
	public String queryHipMatTypeRef(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HipMatTypeRef> list = hipMatTypeRefMapper.queryHipMatTypeRef(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HipMatTypeRef> list = hipMatTypeRefMapper.queryHipMatTypeRef(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HipMatTypeRef queryHipMatTypeRefByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hipMatTypeRefMapper.queryHipMatTypeRefByCode(entityMap);
	}

	@Override
	public String addHipMatTypeRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String saveFlag = (String) entityMap.get("saveFlag");

			HipMatTypeRef apt = hipMatTypeRefMapper.queryHipMatTypeRefByCode(entityMap);

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

				map.put("hip_mat_type_code", data_spl[2]);

				map.put("hrp_mat_type_code", data_spl[3]);

				hipMatTypeRefMapper.deleteHipMatTypeRef(map);

			}

			hipMatTypeRefMapper.addHipMatTypeRef(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHipMatTypeRef\"}";

		}
	}

	@Override
	public String delHipMatTypeRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipMatTypeRefMapper.deleteHipMatTypeRef(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHipMatTypeRef\"}";

		}
	}

	@Override
	public String addBatchHipMatTypeRef(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hipMatTypeRefMapper.addBatchHipMatTypeRef(entityList);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addBatchHipMatTypeRef\"}";

		}
	}

	@Override
	public String deleteBatchHipMatTypeRef(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hipMatTypeRefMapper.deleteBatchHipMatTypeRef(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchHipMatTypeRef\"}";

		}

	}

}
