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
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hip.dao.HipDblinkSourceMapper;
import com.chd.hrp.hip.entity.HipDblinkSource;
import com.chd.hrp.hip.service.HipDblinkSourceService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hipDblinkSourceService")
public class HipDblinkSourceServiceImpl implements HipDblinkSourceService {

	private static Logger logger = Logger.getLogger(HipDblinkSourceServiceImpl.class);

	@Resource(name = "hipDblinkSourceMapper")
	private final HipDblinkSourceMapper hipDblinkSourceMapper = null;

	@Override
	public String queryHipDblinkSource(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HipDblinkSource> list = hipDblinkSourceMapper.queryHipDblinkSource(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HipDblinkSource> list = hipDblinkSourceMapper.queryHipDblinkSource(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HipDblinkSource queryHipDblinkSourceByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hipDblinkSourceMapper.queryHipDblinkSourceByCode(entityMap);
	}

	@Override
	public String updateHipDblinkSource(Map<String, Object> entityMap) throws DataAccessException {
		try {
			//无该业务
			//hipDblinkSourceMapper.updateHipDblinkSource(entityMap);

			return "{\"msg\":\"更改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更改失败 数据库异常 请联系管理员! 方法 updateHipDblinkSource\"}";

		}
	}

	@Override
	public String addHipDblinkSource(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String saveFlag = (String) entityMap.get("saveFlag");

			if (!"1".equals(saveFlag)) {

				HipDblinkSource hds = hipDblinkSourceMapper.queryHipDblinkSourceByCode(entityMap);

				if (hds != null) {

					return "{\"msg\":\"科室已维护对应关系，请重新选择.\",\"state\":\"error\"}";
				}
			}

			if ("1".equals(saveFlag)) {

				hipDblinkSourceMapper.updateHipDblinkSource(entityMap);
			} else {

				hipDblinkSourceMapper.addHipDblinkSource(entityMap);
			}

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败\"}");
		}
	}

	@Override
	public String delHipDblinkSource(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipDblinkSourceMapper.deleteHipDblinkSource(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

}
