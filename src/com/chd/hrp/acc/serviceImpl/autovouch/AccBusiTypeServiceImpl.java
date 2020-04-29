/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.autovouch;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.autovouch.AccBusiTypeMapper;
import com.chd.hrp.acc.entity.autovouch.AccBusiType;
import com.chd.hrp.acc.service.autovouch.AccBusiTypeService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 客户字典属性表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accBusiTypeService")
public class AccBusiTypeServiceImpl implements AccBusiTypeService {

	private static Logger logger = Logger.getLogger(AccBusiTypeServiceImpl.class);

	@Resource(name = "accBusiTypeMapper")
	private final AccBusiTypeMapper accBusiTypeMapper = null;

	@Override
	public String queryAccBusiType(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AccBusiType> list = accBusiTypeMapper.queryAccBusiType(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AccBusiType> list = accBusiTypeMapper.queryAccBusiType(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String updateAccBusiTypeForIsVouch(Map<String, Object> entityMap) throws DataAccessException {
		
		try {

			accBusiTypeMapper.updateAccBusiTypeForIsVouch(entityMap);

			return "{\"msg1\":\"更新成功.\",\"state\":\"true1\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"自动凭证 数据库异常 请联系管理员! 方法 updateAccBusiTypeForIsVouch\"}";

		}
		
	}

}
