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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.autovouch.AccChargeTypeMapper;
import com.chd.hrp.acc.entity.autovouch.AccChargeType;
import com.chd.hrp.acc.service.autovouch.AccChargeTypeService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 客户字典属性表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accChargeTypeService")
public class AccChargeTypeServiceImpl implements AccChargeTypeService {

	private static Logger logger = Logger.getLogger(AccChargeTypeServiceImpl.class);

	@Resource(name = "accChargeTypeMapper")
	private final AccChargeTypeMapper accChargeTypeMapper = null;

	@Override
	public String queryAccChargeType(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AccChargeType> list = accChargeTypeMapper.queryAccChargeType(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AccChargeType> list = accChargeTypeMapper.queryAccChargeType(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public AccChargeType queryAccChargeTypeByCode(Map<String, Object> entityMap) throws DataAccessException {

		return accChargeTypeMapper.queryAccChargeTypeByCode(entityMap);
	}

	@Override
	public String updateAccChargeType(Map<String, Object> entityMap) throws DataAccessException {
		try {

			accChargeTypeMapper.updateAccChargeType(entityMap);

			return "{\"msg\":\"更改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更改失败 数据库异常 请联系管理员! 方法 updateAccChargeType\"}";

		}
	}

	@Override
	public String addAccChargeType(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String saveFlag = (String) entityMap.get("saveFlag");

			if (!"1".equals(saveFlag)) {

				AccChargeType apt = accChargeTypeMapper.queryAccChargeTypeByCode(entityMap);

				if (apt != null) {

					return "{\"msg\":\"编码重复 请修改编码.\",\"state\":\"error\"}";

				}

			}

			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("charge_type_name").toString()));

			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("charge_type_name").toString()));

			if ("1".equals(saveFlag)) {

				accChargeTypeMapper.updateAccChargeType(entityMap);

			} else {

				accChargeTypeMapper.addAccChargeType(entityMap);

			}

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addAccChargeType\"}";

		}
	}

	@Override
	public String delAccChargeType(Map<String, Object> entityMap) throws DataAccessException {
		try {

			accChargeTypeMapper.deleteAccChargeType(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delAccChargeType\"}";

		}
	}

}
