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
import com.chd.hrp.hip.dao.HipPaymentItemRefMapper;
import com.chd.hrp.hip.entity.HipPaymentItemRef;
import com.chd.hrp.hip.service.HipPaymentItemRefService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hipPaymentItemRefService")
public class HipPaymentItemRefServiceImpl implements HipPaymentItemRefService {

	private static Logger logger = Logger.getLogger(HipPaymentItemRefServiceImpl.class);

	@Resource(name = "hipPaymentItemRefMapper")
	private final HipPaymentItemRefMapper hipPaymentItemRefMapper = null;

	@Override
	public String queryHipPaymentItemRef(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HipPaymentItemRef> list = hipPaymentItemRefMapper.queryHipPaymentItemRef(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HipPaymentItemRef> list = hipPaymentItemRefMapper.queryHipPaymentItemRef(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HipPaymentItemRef queryHipPaymentItemRefByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hipPaymentItemRefMapper.queryHipPaymentItemRefByCode(entityMap);
	}

	@Override
	public String addHipPaymentItemRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String saveFlag = (String) entityMap.get("saveFlag");

			HipPaymentItemRef apt = hipPaymentItemRefMapper.queryHipPaymentItemRefByCode(entityMap);

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

				map.put("hip_payment_item_code", data_spl[2]);

				map.put("hrp_payment_item_code", data_spl[3]);

				hipPaymentItemRefMapper.deleteHipPaymentItemRef(map);

			}

			hipPaymentItemRefMapper.addHipPaymentItemRef(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHipPaymentItemRef\"}";

		}
	}

	@Override
	public String delHipPaymentItemRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipPaymentItemRefMapper.deleteHipPaymentItemRef(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHipPaymentItemRef\"}";

		}
	}

	@Override
	public String addBatchHipPaymentItemRef(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hipPaymentItemRefMapper.addBatchHipPaymentItemRef(entityList);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addBatchHipPaymentItemRef\"}";

		}
	}

	@Override
	public String deleteBatchHipPaymentItemRef(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hipPaymentItemRefMapper.deleteBatchHipPaymentItemRef(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchHipPaymentItemRef\"}";

		}

	}

}
