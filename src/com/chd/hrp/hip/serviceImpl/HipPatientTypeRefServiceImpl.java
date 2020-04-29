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
import com.chd.hrp.hip.dao.HipPatientTypeRefMapper;
import com.chd.hrp.hip.entity.HipPatientTypeRef;
import com.chd.hrp.hip.service.HipPatientTypeRefService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hipPatientTypeRefService")
public class HipPatientTypeRefServiceImpl implements HipPatientTypeRefService {

	private static Logger logger = Logger.getLogger(HipPatientTypeRefServiceImpl.class);

	@Resource(name = "hipPatientTypeRefMapper")
	private final HipPatientTypeRefMapper hipPatientTypeRefMapper = null;

	@Override
	public String queryHipPatientTypeRef(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HipPatientTypeRef> list = hipPatientTypeRefMapper.queryHipPatientTypeRef(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HipPatientTypeRef> list = hipPatientTypeRefMapper.queryHipPatientTypeRef(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HipPatientTypeRef queryHipPatientTypeRefByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hipPatientTypeRefMapper.queryHipPatientTypeRefByCode(entityMap);
	}

	@Override
	public String addHipPatientTypeRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String saveFlag = (String) entityMap.get("saveFlag");

			HipPatientTypeRef apt = hipPatientTypeRefMapper.queryHipPatientTypeRefByCode(entityMap);

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

				map.put("hip_patient_type_code", data_spl[1]);

				map.put("hrp_patient_type_code", data_spl[2]);

				hipPatientTypeRefMapper.deleteHipPatientTypeRef(map);

			}

			hipPatientTypeRefMapper.addHipPatientTypeRef(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHipPatientTypeRef\"}";

		}
	}

	@Override
	public String delHipPatientTypeRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipPatientTypeRefMapper.deleteHipPatientTypeRef(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHipPatientTypeRef\"}";

		}
	}

	@Override
	public String addBatchHipPatientTypeRef(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hipPatientTypeRefMapper.addBatchHipPatientTypeRef(entityList);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addBatchHipPatientTypeRef\"}";

		}
	}

	@Override
	public String deleteBatchHipPatientTypeRef(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hipPatientTypeRefMapper.deleteBatchHipPatientTypeRef(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchHipPatientTypeRef\"}";

		}

	}

}
