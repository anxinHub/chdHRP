/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.cost.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostParaNaturMapper;
import com.chd.hrp.cost.dao.CostParaSetMapper;
import com.chd.hrp.cost.entity.CostParaNatur;
import com.chd.hrp.cost.entity.CostParaSet;
import com.chd.hrp.cost.service.CostParaSetService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 成本_分摊参数设置
 * @Table: COST_PARA_SET
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costParaSetService")
public class CostParaSetServiceImpl implements CostParaSetService {

	private static Logger logger = Logger.getLogger(CostParaSetServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "costParaSetMapper")
	private final CostParaSetMapper costParaSetMapper = null;

	// 引入DAO操作
	@Resource(name = "costParaNaturMapper")
	private final CostParaNaturMapper costParaNaturMapper = null;

	/**
	 * @Description 添加成本_分摊参数设置<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> mv = new HashMap<String, Object>();
		mv.putAll(entityMap);
		mv.put("para_code", entityMap.get("natur_code"));

		// 获取单据类型
		CostParaNatur cpn = costParaNaturMapper.queryByCode(mv);
		if (cpn != null) {
			entityMap.put("bill_type", cpn.getBill_type());
			entityMap.put("bill_name", cpn.getPara_name());
		}
		// 获取对象成本_分摊参数设置
		CostParaSet costParaSet = queryByCode(entityMap);

		if (costParaSet != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {
			// 获取对象成本_分摊参数设置
			CostParaSet costParaSetTree = costParaSetMapper.queryByCodeTree(entityMap);
			if (costParaSetTree == null) {
				addTree(entityMap);
			}
			int state = costParaSetMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	public String addTree(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象成本_分摊参数设置
		CostParaSet costParaSet = costParaSetMapper.queryByCodeTree(entityMap);

		if (costParaSet != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		Map<String, Object> mv = new HashMap<String, Object>();
		mv.putAll(entityMap);
		mv.put("para_code", entityMap.get("natur_code"));

		// 获取单据类型
		CostParaNatur cpn = costParaNaturMapper.queryByCode(mv);
		if (cpn != null) {
			entityMap.put("bill_type", cpn.getBill_type());
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m = queryMaxNo(entityMap);
		int count = 0;
		if (null != m) {
			count = Integer.valueOf(m.get("maxno").toString());
		}
		StringBuilder sb = new StringBuilder();
		sb.append(entityMap.get("bill_type").toString());
		sb.append(Strings.alignRight(count + 1, 2, '0'));
		entityMap.put("p_code", sb.toString());
		try {

			int state = costParaSetMapper.addTree(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加成本_分摊参数设置<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costParaSetMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新成本_分摊参数设置<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {
			// 获取对象成本_分摊参数设置
			CostParaSet costParaSetTree = costParaSetMapper.queryByCodeTree(entityMap);
			if (costParaSetTree == null) {
				Map<String, Object> mv = new HashMap<String, Object>();
				mv.putAll(entityMap);
				mv.put("para_code", entityMap.get("natur_code"));

				// 获取单据类型
				CostParaNatur cpn = costParaNaturMapper.queryByCode(mv);
				if (cpn != null) {
					entityMap.put("bill_type", cpn.getBill_type());
					entityMap.put("bill_name", cpn.getPara_name());
				}
				addTree(entityMap);
			}
			int state = costParaSetMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 更新成本_分摊参数设置<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String updateTree(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = costParaSetMapper.updateTree(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新成本_分摊参数设置<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costParaSetMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除成本_分摊参数设置<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = costParaSetMapper.delete(entityMap);

			if (null == entityMap.get("type_code")) {
				deleteTree(entityMap);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 删除成本_分摊参数设置<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String deleteTree(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = costParaSetMapper.deleteTree(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除成本_分摊参数设置<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costParaSetMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}
	}

	/**
	 * @Description 添加成本_分摊参数设置<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		/**
		 * 备注 先判断是否存在 存在即更新不存在则添加<br>
		 * 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		 * 判断是否名称相同 判断是否 编码相同 等一系列规则
		 */
		// 判断是否存在对象成本_分摊参数设置
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<CostParaSet> list = (List<CostParaSet>) costParaSetMapper.queryExists(mapVo);

		if (list.size() > 0) {

			int state = costParaSetMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = costParaSetMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}

	}

	/**
	 * @Description 查询结果集成本_分摊参数设置<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<CostParaSet> list = (List<CostParaSet>) costParaSetMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<CostParaSet> list = (List<CostParaSet>) costParaSetMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象成本_分摊参数设置<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return costParaSetMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取成本_分摊参数设置<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return CostParaSet
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return costParaSetMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取成本_分摊参数设置<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return List<CostParaSet>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return costParaSetMapper.queryExists(entityMap);
	}

	/**
	 * @Description 分摊参数设置树形展示
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryByTree(Map<String, Object> entityMap) throws DataAccessException {
		return costParaSetMapper.queryByTree(entityMap);
	}

	/**
	 * @Description 分摊参数设置生成
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String generate(Map<String, Object> entityMap) throws DataAccessException {
		// 查询初始化数据
		List<Map<String, Object>> list = (List<Map<String, Object>>) costParaSetMapper.queryGenerate(entityMap);
		if (list.size() > 0) {
			List<Map<String, Object>> detail = new ArrayList<Map<String, Object>>();
			Map<String, Map<String, Object>> main = new HashMap<String, Map<String, Object>>();
			for (Map<String, Object> map : list) {

				if (null == main.get("natur_code")) {
					map.put("bill_name", map.get("natur_name"));
					main.put(map.get("natur_code").toString(), map);
				}
			}

			// 删除当前月数据
			try {
				delete(entityMap);
				deleteTree(entityMap);
				for (String v : main.keySet()) {
					addTree(main.get(v));
				}
				for (Map<String, Object> map : list) {
					add(map);
				}
				List<Map<String, Object>> dept = (List<Map<String, Object>>) queryParaDeptDetail(entityMap);
				if (dept.size() > 0) {
					deleteParaDeptDetail(entityMap);
					addParaDeptDetail(dept);
				}

				List<Map<String, Object>> item = (List<Map<String, Object>>) queryParaItemDetail(entityMap);
				if (item.size() > 0) {
					deleteParaItemDetail(entityMap);
					addParaItemDetail(item);
				}
				return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			}
			catch (Exception ex) {
				logger.debug(ex);
				throw new SysException("{\"error\":\"生成失败\"}");
			}
		} else {
			return "{\"msg\":\"没有可生成的数据.\",\"state\":\"true\"}";
		}

	}

	/**
	 * @Description 分摊参数设置继承
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	@Override
	public String inheritance(Map<String, Object> entityMap) throws DataAccessException {
		//生成数据
		Map<String, Object> endMap=new HashMap<String, Object>();
		endMap.putAll(entityMap);
		endMap.put("acc_year", entityMap.get("end_year"));
		endMap.put("acc_month", entityMap.get("end_month"));
		// 查询某个月的数据进行继承
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) costParaSetMapper.queryCostParaSet(entityMap);
		if (list.size() > 0) {
			try {
				List<Map<String, Object>> listNew=new ArrayList<Map<String,Object>>();
				delete(endMap);
				//添加菜单TREE
				costParaSetMapper.addBatchTree(entityMap);
				
				for (Map<String, Object> map : list) {
					map.put("acc_year", endMap.get("acc_year"));
					map.put("acc_month", endMap.get("acc_month"));
					listNew.add(map);
                }
				addBatch(listNew);

				List<Map<String, Object>> dept = (List<Map<String, Object>>) queryParaDeptDetail(entityMap);
				if (dept.size() > 0) {
					List<Map<String, Object>> deptNew=new ArrayList<Map<String,Object>>();
					for (Map<String, Object> map : dept) {
						map.put("acc_year", endMap.get("acc_year"));
						map.put("acc_month", endMap.get("acc_month"));
						deptNew.add(map);
	                }
					deleteParaDeptDetail(endMap);
					addParaDeptDetail(deptNew);
				}

				List<Map<String, Object>> item = (List<Map<String, Object>>) queryParaItemDetail(entityMap);
				if (item.size() > 0) {
					List<Map<String, Object>> itemNew=new ArrayList<Map<String,Object>>();
					for (Map<String, Object> map : item) {
						map.put("acc_year", endMap.get("acc_year"));
						map.put("acc_month", endMap.get("acc_month"));
						itemNew.add(map);
	                }
					
					deleteParaItemDetail(endMap);
					addParaItemDetail(itemNew);
				}

				return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
			}
			catch (Exception ex) {
				logger.debug(ex);
				throw new SysException("{\"error\":\"继承失败\"}");
			}
		} else {
			return "{\"msg\":\"没有可继承的数据.\",\"state\":\"true\"}";
		}
	}

	@Override
	public String addParaDeptDetail(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		int count = costParaSetMapper.addParaDeptDetail(entityMap);
		if (count > 0) {
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"操作失败.\",\"state\":\"true\"}";
		}
	}

	@Override
	public String addParaItemDetail(List<Map<String, Object>> entityMap) throws DataAccessException {
		int count = costParaSetMapper.addParaItemDetail(entityMap);
		if (count > 0) {
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"操作失败.\",\"state\":\"true\"}";
		}
	}

	@Override
	public String deleteParaDeptDetail(Map<String, Object> entityMap) throws DataAccessException {
		int count = costParaSetMapper.deleteParaDeptDetail(entityMap);
		if (count > 0) {
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"操作失败.\",\"state\":\"true\"}";
		}
	}

	@Override
	public String deleteParaItemDetail(Map<String, Object> entityMap) throws DataAccessException {
		int count = costParaSetMapper.deleteParaItemDetail(entityMap);
		if (count > 0) {
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"操作失败.\",\"state\":\"true\"}";
		}
	}

	@Override
	public List<?> queryParaDeptDetail(Map<String, Object> entityMap) throws DataAccessException {
		return costParaSetMapper.queryParaDeptDetail(entityMap);
	}

	@Override
	public List<?> queryParaItemDetail(Map<String, Object> entityMap) throws DataAccessException {
		return costParaSetMapper.queryParaItemDetail(entityMap);
	}

	/**
	 * 获取最大值
	 * 
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> queryMaxNo(Map<String, Object> mapVo) throws DataAccessException {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}
		return costParaSetMapper.queryMaxNo(mapVo);
	}
}
