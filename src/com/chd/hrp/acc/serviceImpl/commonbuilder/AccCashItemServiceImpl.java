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
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.commonbuilder.AccCashItemMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccCashTypeMapper;
import com.chd.hrp.acc.entity.AccCashItem;
import com.chd.hrp.acc.entity.AccCashType;
import com.chd.hrp.acc.service.commonbuilder.AccCashItemService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 现金流量项目<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accCashItemService")
public class AccCashItemServiceImpl implements AccCashItemService {

	private static Logger logger = Logger.getLogger(AccCashItemServiceImpl.class);

	@Resource(name = "accCashItemMapper")
	private final AccCashItemMapper accCashItemMapper = null;

	@Resource(name = "accCashTypeMapper")
	private final AccCashTypeMapper accCashTypeMapper = null;
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	/**
	 * @Description 现金流量项目<BR>
	 *              添加AccCashItem
	 * @param AccCashItem
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAccCashItem(Map<String, Object> entityMap) throws DataAccessException {

		AccCashItem accCashItem = accCashItemMapper.queryAccCashItemByCodeName(entityMap);
		if (accCashItem != null) {
			return "{\"error\":\"编码：" + entityMap.get("cash_item_code").toString() + "重复.\"}";
		}
		
		AccCashItem item = accCashItemMapper.queryAccCashItemByName(entityMap);
		if (item != null) {
			return "{\"error\":\"名称：" + entityMap.get("cash_item_name").toString() + "重复.\"}";
		}

		try {

			accCashItemMapper.addAccCashItem(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccCashItem\"}";

		}

	}

	/**
	 * @Description 现金流量项目<BR>
	 *              批量添加AccCashItem
	 * @param AccCashItem
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAccCashItem(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			accCashItemMapper.addBatchAccCashItem(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccCashItem\"}";

		}
	}

	/**
	 * @Description 现金流量项目<BR>
	 *              查询AccCashItem分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccCashItem(Map<String, Object> entityMap) throws DataAccessException {

		/*SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccCashItem> list = accCashItemMapper.queryAccCashItem(entityMap, rowBounds);*/
		List<AccCashItem> list = accCashItemMapper.queryAccCashItem(entityMap);
		/*PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());*/
		return ChdJson.toJson(list);

	}

	/**
	 * @Description 现金流量项目<BR>
	 *              查询AccCashItem分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccCashItemByVouch(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {

			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));

		}

		return JSON.toJSONString(accCashItemMapper.queryAccCashItemByVouch(entityMap, rowBounds));

	}

	/**
	 * @Description 现金流量项目<BR>
	 *              查询AccCashItemByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AccCashItem queryAccCashItemByCode(Map<String, Object> entityMap) throws DataAccessException {

		return accCashItemMapper.queryAccCashItemByCode(entityMap);

	}

	/**
	 * @Description 现金流量项目<BR>
	 *              批量删除AccCashItem
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAccCashItem(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			String storeIdStr = "";// 删除时，判断业务使用
			String reStr = "";
			String superCode = "";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dict_code", "ACC_CASH_ITEM");
			map.put("group_id", entityList.get(0).get("group_id"));
			map.put("hos_id", entityList.get(0).get("hos_id"));
			map.put("copy_code", "");
			map.put("acc_year", "");
			map.put("p_flag", "");// 包括子科目
			if (entityList != null && entityList.size() > 0) {
				for (Map<String, Object> mapVo : entityList) {
					storeIdStr += mapVo.get("cash_item_id") + ",";

					map.put("dict_id_str", storeIdStr.substring(0, storeIdStr.length() - 1));
					// 删除时，判断业务表是否使用
					// if(LoadSystemInfo.getHrpMap().get("hrpDatatype").toString().equalsIgnoreCase("oracle")){

					sysFunUtilMapper.querySysDictDelCheck(map);

					storeIdStr = "";
					// }
					if (map.get("reNote") != null)
						reStr += map.get("reNote");

				}
			}
			if (reStr != null && !reStr.equals("")) {
				return "{\"error\":\"删除失败，选择的现金流量类别被以下业务使用：【" + reStr.substring(0, reStr.length() - 1)
						+ "】。\",\"state\":\"false\"}";
			}

			int state = accCashItemMapper.deleteBatchAccCashItem(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccCashItem\"}";

		}

	}

	/**
	 * @Description 现金流量项目<BR>
	 *              删除AccCashItem
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAccCashItem(Map<String, Object> entityMap) throws DataAccessException {

		try {
			accCashItemMapper.deleteAccCashItem(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccCashItem\"}";

		}
	}

	/**
	 * @Description 现金流量项目<BR>
	 *              更新AccCashItem
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAccCashItem(Map<String, Object> entityMap) throws DataAccessException {

		AccCashItem accCashItem = accCashItemMapper.queryAccCashItemByName(entityMap);

		if (accCashItem != null) {
			if (!(accCashItem.getCash_item_id() == Long.parseLong((String) entityMap.get("cash_item_id")))) {
				return "{\"error\":\"核算项名称" + entityMap.get("cash_item_name").toString()
						+ "重复..\",\"state\":\"disable\"}";
			}
		}
		try {

			accCashItemMapper.updateAccCashItem(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCashItem\"}";

		}
	}

	/**
	 * @Description 现金流量项目<BR>
	 *              批量更新AccCashItem
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAccCashItem(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			accCashItemMapper.updateBatchAccCashItem(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCashItem\"}";

		}

	}

	/**
	 * @Description 现金流量项目<BR>
	 *              导入AccCashItem
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importAccCashItem(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String insertBatchAccCashItem(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		try {

			List<AccCashType> ackList = accCashTypeMapper.queryAccCashType(entityMap);

			List<AccCashItem> aciList = accCashItemMapper.queryAccCashItem(entityMap);

			if (ackList.size() > 0 || aciList.size() > 0) {

				return "{\"msg\":\"数据已存在，无法继承.\",\"state\":\"false\"}";

			}

			List<AccCashType> accCheckTypeList = accCashTypeMapper.queryAccCashTypeByExtend(entityMap);

			if (accCheckTypeList.size() > 0) {

				Long maxId = accCashTypeMapper.queryMaxId().getCash_type_id();

				for (AccCashType accCashType : accCheckTypeList) {

					Long cash_type_id = ++maxId;

					Map<String, Object> map = new HashMap<String, Object>();

					map.put("cash_type_id", cash_type_id);

					map.put("group_id", SessionManager.getGroupId());

					map.put("hos_id", SessionManager.getHosId());

					map.put("copy_code", SessionManager.getCopyCode());

					map.put("cash_type_code", accCashType.getCash_type_code());

					map.put("cash_type_name", accCashType.getCash_type_name());

					map.put("spell_code", StringTool.toPinyinShouZiMu(accCashType.getCash_type_name()));

					map.put("wbx_code", StringTool.toWuBi(accCashType.getCash_type_name()));

					map.put("is_stop", accCashType.getIs_stop());

					mapList.add(map);

					List<AccCashItem> itemList = accCashItemMapper.queryAccCashItemExtend(accCashType);

					if (itemList.size() > 0) {

						for (AccCashItem accCashItem : itemList) {

							Map<String, Object> itemMap = new HashMap<String, Object>();

							itemMap.put("cash_item_id", accCashItem.getCash_item_id());

							itemMap.put("cash_type_id", cash_type_id);

							itemMap.put("group_id", SessionManager.getGroupId());

							itemMap.put("hos_id", SessionManager.getHosId());

							itemMap.put("copy_code", SessionManager.getCopyCode());

							itemMap.put("cash_dire", accCashItem.getCash_dire());

							itemMap.put("cash_item_code", accCashItem.getCash_item_code());

							itemMap.put("cash_item_name", accCashItem.getCash_item_name());

							itemMap.put("spell_code", StringTool.toPinyinShouZiMu(accCashItem.getCash_item_name()));

							itemMap.put("wbx_code", StringTool.toWuBi(accCashItem.getCash_item_name()));

							itemMap.put("is_stop", accCashItem.getIs_stop());

							itemMap.put("cash_dire", accCashItem.getCash_dire());

							list.add(itemMap);
						}
					}
				}
				accCashTypeMapper.insertBatchAccCashType(mapList);

				accCashItemMapper.addBatchAccCashItem(list);

			} else {

				return "{\"msg\":\"没有可继承数据.\",\"state\":\"false\"}";

			}

			return "{\"msg\":\"继承数据成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage(), e);
		}

	}

}
