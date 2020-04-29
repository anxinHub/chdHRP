package com.chd.hrp.htc.serviceImpl.info.basic;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.info.basic.HtcCostTypeDictMapper;
import com.chd.hrp.htc.entity.info.basic.HtcCostTypeDict;
import com.chd.hrp.htc.service.info.basic.HtcCostTypeDictService;
import com.github.pagehelper.PageInfo;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("htcCostTypeDictService")
public class HtcCostTypeDictServiceImpl implements HtcCostTypeDictService {

	private static Logger logger = Logger
			.getLogger(HtcCostTypeDictServiceImpl.class);

	@Resource(name = "htcCostTypeDictMapper")
	private final HtcCostTypeDictMapper htcCostTypeDictMapper = null;

	@Override
	public String addHtcCostTypeDict(Map<String, Object> entityMap)
			throws DataAccessException {

		try {

			HtcCostTypeDict htcCostTypeDict = htcCostTypeDictMapper.queryHtcCostTypeDictByCode(entityMap);

			if (null != htcCostTypeDict) {

				return "{\"error\":\"编码："+ entityMap.get("cost_type_code").toString() + "重复.\"}";
			}

			htcCostTypeDictMapper.addHtcCostTypeDict(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addHtcCostTypeDict\"}";
		}
	}

	@Override
	public String queryHtcCostTypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HtcCostTypeDict> list = htcCostTypeDictMapper
					.queryHtcCostTypeDict(entityMap);

			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HtcCostTypeDict> list = htcCostTypeDictMapper
					.queryHtcCostTypeDict(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcCostTypeDict queryHtcCostTypeDictByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		return htcCostTypeDictMapper.queryHtcCostTypeDictByCode(entityMap);
	}

	@Override
	public String deleteBatchHtcCostTypeDict(
			List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			htcCostTypeDictMapper.deleteBatchHtcCostTypeDict(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteBatchHtcCostTypeDict\"}";

		}
	}

	@Override
	public String updateHtcCostTypeDict(Map<String, Object> entityMap)
			throws DataAccessException {

		try {

			htcCostTypeDictMapper.updateHtcCostTypeDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHtcCostTypeDict\"}";
		}
	}
}
