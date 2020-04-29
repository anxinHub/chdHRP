/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.autovouch.his;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.autovouch.his.HisAccMedStoreRefMapper;
import com.chd.hrp.acc.entity.autovouch.HisAccMedStoreRef;
import com.chd.hrp.acc.service.autovouch.his.HisAccMedStoreRefService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hisAccMedStoreRefService")
public class HisAccMedStoreRefServiceImpl implements HisAccMedStoreRefService {

	private static Logger logger = Logger.getLogger(HisAccMedStoreRefServiceImpl.class);

	@Resource(name = "hisAccMedStoreRefMapper")
	private final HisAccMedStoreRefMapper hisAccMedStoreRefMapper = null;

	@Override
	public String queryHisAccMedStoreRef(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = hisAccMedStoreRefMapper.queryHisAccMedStoreRef(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = hisAccMedStoreRefMapper.queryHisAccMedStoreRef(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HisAccMedStoreRef queryHisAccMedStoreRefByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hisAccMedStoreRefMapper.queryHisAccMedStoreRefByCode(entityMap);
	}

	@Override
	public String updateHisAccMedStoreRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hisAccMedStoreRefMapper.updateHisAccMedStoreRef(entityMap);

			return "{\"msg\":\"更改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更改失败 数据库异常 请联系管理员! 方法 updateHisAccMedStoreRef\"}";

		}
	}

	@Override
	public String addHisAccMedStoreRef(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			hisAccMedStoreRefMapper.deleteHisAccMedStoreRef(entityMap);

			hisAccMedStoreRefMapper.addHisAccMedStoreRef(entityMap);
				
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHisAccMedStoreRef\"}";

		}
	}

	@Override
	public String delHisAccMedStoreRef(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			hisAccMedStoreRefMapper.deleteHisAccMedStoreRef(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHisAccMedStoreRef\"}";

		}
	}

	@Override
	public List<Map<String, Object>> queryHisAccMedStoreRefPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = hisAccMedStoreRefMapper.queryHisAccMedStoreRef(entityMap);

		return list;
	}

}
