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
import com.chd.hrp.hip.dao.HipMedTypeRefMapper;
import com.chd.hrp.hip.entity.HipMedTypeRef;
import com.chd.hrp.hip.service.HipMedTypeRefService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hipMedTypeRefService")
public class HipMedTypeRefServiceImpl implements HipMedTypeRefService {

	private static Logger logger = Logger.getLogger(HipMedTypeRefServiceImpl.class);

	@Resource(name = "hipMedTypeRefMapper")
	private final HipMedTypeRefMapper hipMedTypeRefMapper = null;

	@Override
	public String queryHipMedTypeRef(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HipMedTypeRef> list = hipMedTypeRefMapper.queryHipMedTypeRef(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HipMedTypeRef> list = hipMedTypeRefMapper.queryHipMedTypeRef(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HipMedTypeRef queryHipMedTypeRefByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hipMedTypeRefMapper.queryHipMedTypeRefByCode(entityMap);
	}

	@Override
	public String addHipMedTypeRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String saveFlag = (String) entityMap.get("saveFlag");

			HipMedTypeRef apt = hipMedTypeRefMapper.queryHipMedTypeRefByCode(entityMap);

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

				hipMedTypeRefMapper.deleteHipMedTypeRef(map);

			}

			hipMedTypeRefMapper.addHipMedTypeRef(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHipMedTypeRef\"}";

		}
	}

	@Override
	public String delHipMedTypeRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipMedTypeRefMapper.deleteHipMedTypeRef(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHipMedTypeRef\"}";

		}
	}

	@Override
	public String addBatchHipMedTypeRef(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hipMedTypeRefMapper.addBatchHipMedTypeRef(entityList);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addBatchHipMedTypeRef\"}";

		}
	}

	@Override
	public String deleteBatchHipMedTypeRef(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hipMedTypeRefMapper.deleteBatchHipMedTypeRef(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchHipMedTypeRef\"}";

		}

	}

}
