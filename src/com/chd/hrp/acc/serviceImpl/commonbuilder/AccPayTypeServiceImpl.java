/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.commonbuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.commonbuilder.AccPayTypeMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchCheckMapper;
import com.chd.hrp.acc.entity.AccPayType;
import com.chd.hrp.acc.entity.AccVouchCheck;
import com.chd.hrp.acc.service.commonbuilder.AccPayTypeService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 结算方式<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accPayTypeService")
public class AccPayTypeServiceImpl implements AccPayTypeService {

	private static Logger logger = Logger.getLogger(AccPayTypeServiceImpl.class);

	@Resource(name = "accPayTypeMapper")
	private final AccPayTypeMapper accPayTypeMapper = null;

	@Resource(name = "accVouchCheckMapper")
	private final AccVouchCheckMapper accVouchCheckMapper = null;

	/**
	 * @Description 结算方式<BR>
	 *              添加AccPayType
	 * @param AccPayType
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAccPayType(Map<String, Object> entityMap) throws DataAccessException {

		AccPayType accPayType = queryAccPayTypeByCode(entityMap);

		if (accPayType != null) {
			return "{\"error\":\"编码：" + entityMap.get("pay_code").toString() + "重复.\"}";
		}

		int i = accPayTypeMapper.queryAccPayTypeByName(entityMap);
		if (i > 0) {
			return "{\"error\":\"名称：" + entityMap.get("pay_name").toString() + "重复.\"}";
		}

		try {
			accPayTypeMapper.addAccPayType(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccPayType\"}";
		}
	}

	/**
	 * @Description 结算方式<BR>
	 *              批量添加AccPayType
	 * @param AccPayType
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAccPayType(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			accPayTypeMapper.addBatchAccPayType(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccPayType\"}";

		}
	}

	/**
	 * @Description 结算方式<BR>
	 *              查询AccPayType分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccPayType(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccPayType> list = accPayTypeMapper.queryAccPayType(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());

	}

	/**
	 * @Description 结算方式<BR>
	 *              查询AccPayTypeByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AccPayType queryAccPayTypeByCode(Map<String, Object> entityMap) throws DataAccessException {

		return accPayTypeMapper.queryAccPayTypeByCode(entityMap);

	}

	/**
	 * @Description 结算方式<BR>
	 *              批量删除AccPayType
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAccPayType(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			String error = "";
			for (Map<String, Object> m : entityList) {
				List<AccVouchCheck> accVouchCheck = accVouchCheckMapper.queryAccVouchCheck(m);

				if (accVouchCheck.size() > 0) {
					error += m.get("pay_code") + ",";
				}
			}
			if (error != "") {
				return "{\"error\":\"结算方式" + error.substring(0, error.length() - 1)
						+ "已被引用,不许删除.\",\"state\":\"true\"}";
			} else {
				int state = accPayTypeMapper.deleteBatchAccPayType(entityList);

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccPayType\"}";

		}

	}

	/**
	 * @Description 结算方式<BR>
	 *              删除AccPayType
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAccPayType(Map<String, Object> entityMap) throws DataAccessException {

		try {
			accPayTypeMapper.deleteAccPayType(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccPayType\"}";

		}
	}

	/**
	 * @Description 结算方式<BR>
	 *              更新AccPayType
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAccPayType(Map<String, Object> entityMap) throws DataAccessException {

		try {

			accPayTypeMapper.updateAccPayType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccPayType\"}";

		}
	}

	/**
	 * @Description 结算方式<BR>
	 *              批量更新AccPayType
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAccPayType(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			accPayTypeMapper.updateBatchAccPayType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccPayType\"}";

		}

	}

	/**
	 * @Description 结算方式<BR>
	 *              导入AccPayType
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importAccPayType(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String extendAccPayType(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {

			List<AccPayType> aptList = accPayTypeMapper.queryAccPayType(entityMap);

			if (aptList.size() > 0) {

				return "{\"msg\":\"数据已存在，无法继承.\",\"state\":\"false\"}";

			}

			List<AccPayType> accPayList = accPayTypeMapper.queryAccPayTypeByExtend(entityMap);

			if (accPayList.size() > 0) {

				for (AccPayType accPayType : accPayList) {

					Map<String, Object> map = new HashMap<String, Object>();

					map.put("pay_code", accPayType.getPay_code());

					map.put("group_id", SessionManager.getGroupId());

					map.put("hos_id", SessionManager.getHosId());

					map.put("copy_code", SessionManager.getCopyCode());

					map.put("acc_year", SessionManager.getAcctYear());

					map.put("pay_name", accPayType.getPay_name());

					map.put("pay_type", accPayType.getPay_type());

					map.put("subj_id", accPayType.getSubj_id());

					map.put("spell_code", accPayType.getSpell_code());

					map.put("wbx_code", accPayType.getWbx_code());

					map.put("is_stop", accPayType.getIs_stop());

					list.add(map);

				}
				accPayTypeMapper.addBatchAccPayType(list);

				return "{\"msg\":\"继承数据成功.\",\"state\":\"true\"}";

			}

			return "{\"msg\":\"没有可继承数据.\",\"state\":\"false\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"继承数据失败 数据库异常 请联系管理员! 错误编码  ExtendBatchAccPayType{className}\"}";
		}

	}

	@Override
	public String queryAccPayTypeByVouch(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));

		}

		return JSON.toJSONString(accPayTypeMapper.queryAccPayTypeByVouch(entityMap, rowBounds));

	}
}
