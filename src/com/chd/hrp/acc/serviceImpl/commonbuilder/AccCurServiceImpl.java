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
import com.chd.hrp.acc.dao.commonbuilder.AccCurMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjMapper;
import com.chd.hrp.acc.entity.AccCur;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.service.commonbuilder.AccCurService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 币种<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accCurService")
public class AccCurServiceImpl implements AccCurService {

	private static Logger logger = Logger.getLogger(AccCurServiceImpl.class);

	@Resource(name = "accCurMapper")
	private final AccCurMapper accCurMapper = null;
	@Resource(name = "accSubjMapper")
	private final AccSubjMapper accSubjMapper = null;

	/**
	 * @Description 币种<BR>
	 *              添加AccCur
	 * @param AccCur
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAccCur(Map<String, Object> entityMap) throws DataAccessException {

		int i = accCurMapper.queryAccCurExitsByCode(entityMap);

		if (i > 0) {
			return "{\"error\":\"编码：" + entityMap.get("cur_code").toString() + "重复.\"}";
		}

		i = accCurMapper.queryAccCurExitsByName(entityMap);
		if (i > 0) {
			return "{\"error\":\"名称：" + entityMap.get("cur_name").toString() + "重复.\"}";
		}

		try {
			accCurMapper.addAccCur(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccCur\"}";
		}
	}

	/**
	 * @Description 币种<BR>
	 *              批量添加AccCur
	 * @param AccCur
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAccCur(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			accCurMapper.addBatchAccCur(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccCur\"}";

		}
	}

	/**
	 * @Description 币种<BR>
	 *              查询AccCur分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccCur(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccCur> list = accCurMapper.queryAccCur(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	/**
	 * @Description 币种<BR>
	 *              查询AccCurByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AccCur queryAccCurByCode(Map<String, Object> entityMap) throws DataAccessException {

		return accCurMapper.queryAccCurByCode(entityMap);

	}

	/**
	 * @Description 币种<BR>
	 *              批量删除AccCur
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAccCur(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			for (Map<String, Object> m : entityList) {

				List<AccSubj> accCur = accSubjMapper.queryAccSubj(m);

				if (accCur.size() > 0) {

					return "{\"error\":\"币种已引用无法删除.\",\"state\":\"true\"}";

				}

			}
			int state = accCurMapper.deleteBatchAccCur(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccCur\"}";

		}

	}

	/**
	 * @Description 币种<BR>
	 *              删除AccCur
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAccCur(Map<String, Object> entityMap) throws DataAccessException {

		try {
			accCurMapper.deleteAccCur(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccCur\"}";

		}
	}

	/**
	 * @Description 币种<BR>
	 *              更新AccCur
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAccCur(Map<String, Object> entityMap) throws DataAccessException {

		try {

			accCurMapper.updateAccCur(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCur\"}";

		}
	}

	/**
	 * @Description 币种<BR>
	 *              批量更新AccCur
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAccCur(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			accCurMapper.updateBatchAccCur(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCur\"}";

		}

	}

	/**
	 * @Description 币种<BR>
	 *              导入AccCur
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importAccCur(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String extendAccCur(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {

			List<AccCur> acList = accCurMapper.queryAccCur(entityMap);

			if (acList.size() > 0) {

				return "{\"msg\":\"数据已存在，无法继承.\",\"state\":\"false\"}";

			}

			List<AccCur> accCurList = accCurMapper.queryAccCurByExtend(entityMap);

			if (accCurList.size() > 0) {

				for (AccCur accCur : accCurList) {

					Map<String, Object> map = new HashMap<String, Object>();

					map.put("cur_code", accCur.getCur_code());

					map.put("group_id", SessionManager.getGroupId());

					map.put("hos_id", SessionManager.getHosId());

					map.put("copy_code", SessionManager.getCopyCode());

					map.put("acc_year", SessionManager.getAcctYear());

					map.put("cur_name", accCur.getCur_name());

					map.put("cur_num", accCur.getCur_num());

					map.put("cur_rate", accCur.getCur_rate());

					map.put("cur_plan", accCur.getCur_plan());

					map.put("is_self", accCur.getIs_self());

					map.put("spell_code", accCur.getSpell_code());

					map.put("wbx_code", accCur.getWbx_code());

					map.put("is_stop", accCur.getIs_stop());

					list.add(map);

				}
				accCurMapper.addBatchAccCur(list);

				return "{\"msg\":\"继承数据成功.\",\"state\":\"true\"}";
			}

			return "{\"msg\":\"没有可继承数据.\",\"state\":\"false\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"继承数据失败 数据库异常 请联系管理员! 错误编码  extendBatchAccCur{className}\"}";
		}

	}

	@Override
	public List<AccCur> queryAccCurByList(Map<String, Object> entityMap) throws DataAccessException {
		List<AccCur> list = accCurMapper.queryAccCur(entityMap);
		return list;
	}
}
