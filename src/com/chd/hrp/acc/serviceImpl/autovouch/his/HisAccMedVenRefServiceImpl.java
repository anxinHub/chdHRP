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
import com.chd.hrp.acc.dao.autovouch.his.HisAccMedVenRefMapper;
import com.chd.hrp.acc.entity.autovouch.HisAccMedVenRef;
import com.chd.hrp.acc.service.autovouch.his.HisAccMedVenRefService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hisAccMedVenRefService")
public class HisAccMedVenRefServiceImpl implements HisAccMedVenRefService {

	private static Logger logger = Logger.getLogger(HisAccMedVenRefServiceImpl.class);

	@Resource(name = "hisAccMedVenRefMapper")
	private final HisAccMedVenRefMapper hisAccMedVenRefMapper = null;

	@Override
	public String queryHisAccMedVenRef(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HisAccMedVenRef> list = hisAccMedVenRefMapper.queryHisAccMedVenRef(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HisAccMedVenRef> list = hisAccMedVenRefMapper.queryHisAccMedVenRef(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HisAccMedVenRef queryHisAccMedVenRefByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hisAccMedVenRefMapper.queryHisAccMedVenRefByCode(entityMap);
	}

	@Override
	public String updateHisAccMedVenRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hisAccMedVenRefMapper.updateHisAccMedVenRef(entityMap);

			return "{\"msg\":\"更改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更改失败 数据库异常 请联系管理员! 方法 updateHisAccMedVenRef\"}";

		}
	}

	@Override
	public String addHisAccMedVenRef(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {
			
			hisAccMedVenRefMapper.deleteHisAccMedVenRef(entityMap);
				
			hisAccMedVenRefMapper.addHisAccMedVenRef(entityMap);
				
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHisAccMedVenRef\"}";

		}
	}

	@Override
	public String delHisAccMedVenRef(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			hisAccMedVenRefMapper.deleteHisAccMedVenRef(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHisAccMedVenRef\"}";

		}
	}

}
