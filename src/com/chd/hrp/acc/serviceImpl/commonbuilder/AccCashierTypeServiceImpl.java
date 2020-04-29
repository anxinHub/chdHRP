/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.commonbuilder;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.commonbuilder.AccCashierTypeMapper;
import com.chd.hrp.acc.entity.AccCashierType;
import com.chd.hrp.acc.service.commonbuilder.AccCashierTypeService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 出纳类型<BR>
 * 
 * @Version: 1.0
 */

@Service("accCashierTypeService")
public class AccCashierTypeServiceImpl implements AccCashierTypeService {

	private static Logger logger = Logger.getLogger(AccCashierTypeServiceImpl.class);

	@Resource(name = "accCashierTypeMapper")
	private final AccCashierTypeMapper accCashierTypeMapper = null;

	/**
	 * @Description 出纳类型<BR>
	 *              添加AccCashierType
	 * @param AccCashierType
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAccCashierType(Map<String, Object> entityMap) throws DataAccessException {

		int i = accCashierTypeMapper.queryAccCashierTypeExitsByCode(entityMap);
		if (i > 0) {
			return "{\"error\":\"出纳类型编码：" + entityMap.get("tell_type_code").toString() + "重复.\"}";
		}
		 i = accCashierTypeMapper.queryAccCashierTypeExitsByName(entityMap);
		if (i > 0) {
			return "{\"error\":\"出纳类型名称：" + entityMap.get("tell_type_name").toString() + "重复.\"}";
		}
		
		try {
			accCashierTypeMapper.addAccCashierType(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccCashierType\"}";
		}
	}

	/**
	 * @Description 出纳类型<BR>
	 *              批量添加AccVouchType
	 * @param AccVouchType
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAccCashierType(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			accCashierTypeMapper.addBatchAccCashierType(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccCashierType\"}";

		}
	}

	/**
	 * @Description 出纳类型<BR>
	 *              查询AccVouchType分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccCashierType(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccCashierType> list = accCashierTypeMapper.queryAccCashierType(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());

	}

	/**
	 * @Description 出纳类型<BR>
	 *              查询AccVouchTypeByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AccCashierType queryAccCashierTypeByCode(Map<String, Object> entityMap) throws DataAccessException {

		return accCashierTypeMapper.queryAccCashierTypeByCode(entityMap);
		// return null;

	}

	/**
	 * @Description 出纳类型<BR>
	 *              批量删除AccVouchType
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAccCashierType(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			int state = accCashierTypeMapper.deleteBatchAccCashierType(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccCashierType\"}";

		}

	}

	/**
	 * @Description 出纳类型<BR>
	 *              删除AccVouchType
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAccCashierType(Map<String, Object> entityMap) throws DataAccessException {

		try {
			accCashierTypeMapper.deleteAccCashierType(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccCashierType\"}";

		}
	}

	/**
	 * @Description 出纳类型<BR>
	 *              更新AccVouchType
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAccCashierType(Map<String, Object> entityMap) throws DataAccessException {

		try {

			accCashierTypeMapper.updateAccCashierType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCashierType\"}";

		}
	}

	/**
	 * @Description 出纳类型<BR>
	 *              批量更新AccVouchType
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAccCashierType(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			accCashierTypeMapper.updateBatchAccCashierType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCashierType\"}";

		}

	}

	/**
	 * @Description 出纳类型<BR>
	 *              导入AccVouchType
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importAccCashierType(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
	@Override
	public List<AccCashierType> queryAccCashierTypeByKindCode(Map<String,Object> entityMap) throws DataAccessException{
		
		return accCashierTypeMapper.queryAccCashierTypeByKindCode(entityMap);
	}

}
