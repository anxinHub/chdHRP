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

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.commonbuilder.AccVouchTypeMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchMapper;
import com.chd.hrp.acc.entity.AccEcoType;
import com.chd.hrp.acc.entity.AccVouch;
import com.chd.hrp.acc.entity.AccVouchType;
import com.chd.hrp.acc.service.commonbuilder.AccVouchTypeService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 凭证类型<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accVouchTypeService")
public class AccVouchTypeServiceImpl implements AccVouchTypeService {

	private static Logger logger = Logger.getLogger(AccVouchTypeServiceImpl.class);

	@Resource(name = "accVouchTypeMapper")
	private final AccVouchTypeMapper accVouchTypeMapper = null;

	@Resource(name = "accVouchMapper")
	private final AccVouchMapper accVouchMapper = null;

	/**
	 * @Description 凭证类型<BR>
	 *              添加AccVouchType
	 * @param AccVouchType
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAccVouchType(Map<String, Object> entityMap) throws DataAccessException {
		int i = accVouchTypeMapper.existsByTypeCode(entityMap);
		if (i > 0) {
			return "{\"error\":\"凭证类型编码：" + entityMap.get("vouch_type_code").toString() + "重复.\"}";
		}
		i = accVouchTypeMapper.existsByTypeName(entityMap);
		if (i > 0) {
			return "{\"error\":\"凭证类型名称：" + entityMap.get("vouch_type_name").toString() + "重复.\"}";
		}
		
		try {

			accVouchTypeMapper.addAccVouchType(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccVouchType\"}";

		}

	}

	/**
	 * @Description 凭证类型<BR>
	 *              批量添加AccVouchType
	 * @param AccVouchType
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAccVouchType(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			accVouchTypeMapper.addBatchAccVouchType(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccVouchType\"}";

		}
	}

	/**
	 * @Description 凭证类型<BR>
	 *              查询AccVouchType分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccVouchType(Map<String, Object> entityMap) throws DataAccessException {

		List<AccVouchType> list = accVouchTypeMapper.queryAccVouchType(entityMap);
		
		return ChdJson.toJson(list);
	}

	/**
	 * @Description 凭证类型<BR>
	 *              查询AccVouchTypeByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AccVouchType queryAccVouchTypeByCode(Map<String, Object> entityMap) throws DataAccessException {

		return accVouchTypeMapper.queryAccVouchTypeByCode(entityMap);

	}

	/**
	 * @Description 凭证类型<BR>
	 *              批量删除AccVouchType
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAccVouchType(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			int is_flag = 0;
			for (Map<String, Object> m : entityList) {
				is_flag = accVouchTypeMapper.existsVouchByType(m);
				if (is_flag > 1) {
					return "{\"error\":\"编码【" + m.get("vouch_type_code") + "】凭证类型已被使用\",\"state\":\"true\"}";
				}
				is_flag = 0;
			}

			accVouchTypeMapper.deleteBatchAccVouchType(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccVouchType\"}";
		}
	}

	/**
	 * @Description 凭证类型<BR>
	 *              删除AccVouchType
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAccVouchType(Map<String, Object> entityMap) throws DataAccessException {

		try {
			accVouchTypeMapper.deleteAccVouchType(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccVouchType\"}";

		}
	}

	/**
	 * @Description 凭证类型<BR>
	 *              更新AccVouchType
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAccVouchType(Map<String, Object> entityMap) throws DataAccessException {

		try {

			accVouchTypeMapper.updateAccVouchType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccVouchType\"}";

		}
	}

	/**
	 * @Description 凭证类型<BR>
	 *              批量更新AccVouchType
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAccVouchType(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			accVouchTypeMapper.updateBatchAccVouchType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccVouchType\"}";

		}

	}

	/**
	 * @Description 凭证类型<BR>
	 *              导入AccVouchType
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importAccVouchType(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String extendAccVouchType(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {

			List<AccVouchType> avtList = accVouchTypeMapper.queryAccVouchType(entityMap);

			if (avtList.size() > 0) {

				return "{\"msg\":\"数据已存在，无法继承.\",\"state\":\"false\"}";

			}

			List<AccVouchType> accVouchList = accVouchTypeMapper.queryAccVouchTypeByExtend(entityMap);

			if (accVouchList.size() > 0) {

				for (AccVouchType accVouchType : accVouchList) {

					Map<String, Object> map = new HashMap<String, Object>();

					map.put("vouch_type_code", accVouchType.getVouch_type_code());

					map.put("group_id", SessionManager.getGroupId());

					map.put("hos_id", SessionManager.getHosId());

					map.put("copy_code", SessionManager.getCopyCode());

					map.put("vouch_type_name", accVouchType.getVouch_type_name());

					map.put("vouch_type_short", accVouchType.getVouch_type_short());

					map.put("spell_code", accVouchType.getSpell_code());

					map.put("wbx_code", accVouchType.getWbx_code());

					map.put("is_stop", accVouchType.getIs_stop());

					list.add(map);

				}

				accVouchTypeMapper.addBatchAccVouchType(list);

				return "{\"msg\":\"继承数据成功.\",\"state\":\"true\"}";

			}

			return "{\"msg\":\"没有可继承数据.\",\"state\":\"false\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"继承数据失败 数据库异常 请联系管理员! 错误编码  insertBatchAccVouchType{className}\"}";
		}

	}
}
