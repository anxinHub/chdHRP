/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.cost.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chd.hrp.cost.dao.CostParaBillMapper;
import com.chd.hrp.cost.dao.CostParaDeptMapper;
import com.chd.hrp.cost.dao.CostParaSetverDeptMapper;
import com.chd.hrp.cost.entity.CostParaBill;
import com.chd.hrp.cost.service.CostParaBillService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 成本_分摊定向单据
 * @Table: COST_PARA_BILL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costParaBillService")
public class CostParaBillServiceImpl implements CostParaBillService {

	private static Logger logger = Logger.getLogger(CostParaBillServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "costParaBillMapper")
	private final CostParaBillMapper costParaBillMapper = null;

	// 引入DAO操作
	@Resource(name = "costParaSetverDeptMapper")
	private final CostParaSetverDeptMapper costParaSetverDeptMapper = null;

	// 引入DAO操作
	@Resource(name = "costParaDeptMapper")
	private final CostParaDeptMapper costParaDeptMapper = null;

	/**
	 * @Description 添加成本_分摊定向单据<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("bill_code") == null) {
			entityMap.put("bill_code", "0");
		}
		Map<String, Object> mv = new HashMap<String, Object>();

		mv.put("group_id", entityMap.get("group_id"));

		mv.put("hos_id", entityMap.get("hos_id"));

		mv.put("copy_code", entityMap.get("copy_code"));

		mv.put("bill_name", entityMap.get("bill_name"));

		mv.put("acc_year", entityMap.get("acc_year"));

		mv.put("acc_month", entityMap.get("acc_month"));

		// 获取对象成本_分摊定向单据
		CostParaBill costParaBill = costParaBillMapper.queryByUniqueness(mv);

		if (costParaBill != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		Map<String, Object> m = new HashMap<String, Object>();
		m = queryMaxNo(entityMap);
		int count = 0;
		if (null != m) {
			count = Integer.valueOf(m.get("maxno").toString());
		}
		StringBuilder sb = new StringBuilder();
		sb.append(entityMap.get("cost_type_code").toString());
		sb.append(Strings.alignRight(count + 1, 2, '0'));
		entityMap.put("p_code", sb.toString());
		try {

			int state = costParaBillMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加成本_分摊定向单据<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costParaBillMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新成本_分摊定向单据<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = costParaBillMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新成本_分摊定向单据<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costParaBillMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除成本_分摊定向单据<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = costParaBillMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除成本_分摊定向单据<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costParaBillMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}
	}

	/**
	 * @Description 添加成本_分摊定向单据<BR>
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
		// 判断是否存在对象成本_分摊定向单据
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));
		mapVo.put("acct_month", entityMap.get("acct_month"));

		List<CostParaBill> list = (List<CostParaBill>) costParaBillMapper.queryExists(mapVo);

		if (list.size() > 0) {

			int state = costParaBillMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = costParaBillMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}

	}

	/**
	 * @Description 查询结果集成本_分摊定向单据<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<CostParaBill> list = (List<CostParaBill>) costParaBillMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<CostParaBill> list = (List<CostParaBill>) costParaBillMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象成本_分摊定向单据<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return costParaBillMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取成本_分摊定向单据<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return CostParaBill
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return costParaBillMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取成本_分摊定向单据<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return List<CostParaBill>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return costParaBillMapper.queryExists(entityMap);
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
		return costParaBillMapper.queryMaxNo(mapVo);
	}

	@Override
	public List<?> queryByTree(Map<String, Object> entityMap) throws DataAccessException {
		return costParaBillMapper.queryByTree(entityMap);
	}

	@SuppressWarnings("unchecked")
    @Override
	public String generate(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> listNatur = new ArrayList<Map<String, Object>>();
		// 管理
		Map<String, Object> map01 = new HashMap<String, Object>();
		map01.put("group_id", entityMap.get("group_id"));
		map01.put("hos_id", entityMap.get("hos_id"));
		map01.put("copy_code", entityMap.get("copy_code"));
		map01.put("acc_year", entityMap.get("acc_year"));
		map01.put("acc_month", entityMap.get("acc_month"));
		map01.put("bill_name", "管理分摊配置");
		map01.put("cost_type_code", "01");

		listNatur.add(map01);
		// 普通医辅
		Map<String, Object> map0201 = new HashMap<String, Object>();
		map0201.put("group_id", entityMap.get("group_id"));
		map0201.put("hos_id", entityMap.get("hos_id"));
		map0201.put("copy_code", entityMap.get("copy_code"));
		map0201.put("acc_year", entityMap.get("acc_year"));
		map0201.put("acc_month", entityMap.get("acc_month"));
		map0201.put("bill_name", "其他医辅配置");
		map0201.put("cost_type_code", "02");

		listNatur.add(map0201);

		// 门诊医辅
		Map<String, Object> map0202 = new HashMap<String, Object>();
		map0202.put("group_id", entityMap.get("group_id"));
		map0202.put("hos_id", entityMap.get("hos_id"));
		map0202.put("copy_code", entityMap.get("copy_code"));
		map0202.put("acc_year", entityMap.get("acc_year"));
		map0202.put("acc_month", entityMap.get("acc_month"));
		map0202.put("bill_name", "门诊医辅配置");
		map0202.put("cost_type_code", "02");

		listNatur.add(map0202);

		// 住院医辅
		Map<String, Object> map0203 = new HashMap<String, Object>();
		map0203.put("group_id", entityMap.get("group_id"));
		map0203.put("hos_id", entityMap.get("hos_id"));
		map0203.put("copy_code", entityMap.get("copy_code"));
		map0203.put("acc_year", entityMap.get("acc_year"));
		map0203.put("acc_month", entityMap.get("acc_month"));
		map0203.put("bill_name", "住院医辅配置");
		map0203.put("cost_type_code", "02");

		listNatur.add(map0203);
		// 普通医技
		Map<String, Object> map0301 = new HashMap<String, Object>();
		map0301.put("group_id", entityMap.get("group_id"));
		map0301.put("hos_id", entityMap.get("hos_id"));
		map0301.put("copy_code", entityMap.get("copy_code"));
		map0301.put("acc_year", entityMap.get("acc_year"));
		map0301.put("acc_month", entityMap.get("acc_month"));
		map0301.put("bill_name", "其他医技配置");
		map0301.put("cost_type_code", "03");

		listNatur.add(map0301);
		// 药库
		Map<String, Object> map0302 = new HashMap<String, Object>();
		map0302.put("group_id", entityMap.get("group_id"));
		map0302.put("hos_id", entityMap.get("hos_id"));
		map0302.put("copy_code", entityMap.get("copy_code"));
		map0302.put("acc_year", entityMap.get("acc_year"));
		map0302.put("acc_month", entityMap.get("acc_month"));
		map0302.put("bill_name", "药库配置");
		map0302.put("cost_type_code", "03");
		listNatur.add(map0302);
		// 药房
		Map<String, Object> map0303 = new HashMap<String, Object>();
		map0303.put("group_id", entityMap.get("group_id"));
		map0303.put("hos_id", entityMap.get("hos_id"));
		map0303.put("copy_code", entityMap.get("copy_code"));
		map0303.put("acc_year", entityMap.get("acc_year"));
		map0303.put("acc_month", entityMap.get("acc_month"));
		map0303.put("bill_name", "药房配置");
		map0303.put("cost_type_code", "03");
		listNatur.add(map0303);
		// 药剂
		Map<String, Object> map0304 = new HashMap<String, Object>();
		map0304.put("group_id", entityMap.get("group_id"));
		map0304.put("hos_id", entityMap.get("hos_id"));
		map0304.put("copy_code", entityMap.get("copy_code"));
		map0304.put("acc_year", entityMap.get("acc_year"));
		map0304.put("acc_month", entityMap.get("acc_month"));
		map0304.put("bill_name", "药剂配置");
		map0304.put("cost_type_code", "03");
		listNatur.add(map0304);
		
		
		try {

			// 清除当月数据
			delete(entityMap);
			//清除当月明细数据
			costParaSetverDeptMapper.delete(entityMap);
			
			costParaDeptMapper.delete(entityMap);
			
			// 生成
			for (Map<String, Object> map : listNatur) {

				Map<String, Object> mdept = new HashMap<String, Object>();
				Map<String, Object> mserverdept = new HashMap<String, Object>();
				
				mdept = map;
				mserverdept = map;
				add(map);

				long bill_code = querySequence(); // 用于明细
				
				mdept.put("bill_code", bill_code);
				if (map.get("bill_name").equals("其他医辅配置")) {
					mdept.put("natur_code", "('10')");
					mdept.put("type_code", "('03')");
				}
				if (map.get("bill_name").equals("门诊医辅配置")) {
					mdept.put("natur_code", "('08')");
					mdept.put("type_code", "('03')");
				}
				if (map.get("bill_name").equals("住院医辅配置")) {
					mdept.put("natur_code", "('09')");
					mdept.put("type_code", "('03')");
				}
				if (map.get("bill_name").equals("其他医技配置")) {
					mdept.put("natur_code", "('07')");
					mdept.put("type_code", "('02')");
					
				}
				if (map.get("bill_name").equals("药库配置")) {
					mdept.put("natur_code", "('03')");
					mdept.put("type_code", "('02')");
				}
				if (map.get("bill_name").equals("药房配置")) {
					mdept.put("natur_code", "('04')");
					mdept.put("type_code", "('02')");
				}
				if (map.get("bill_name").equals("药剂配置")) {
					mdept.put("natur_code", "('05')");
					mdept.put("type_code", "('02')");
				}
				if (map.get("bill_name").equals("管理分摊配置")) {
					mdept.put("natur_code", "('11')");
					mdept.put("type_code", "('04')");
				}
				
				int count= costParaDeptMapper.addBatchByGenerate(mdept);
				
				// 添加数据
				if (count > 0) {

					mserverdept.put("bill_code", bill_code);
					if (map.get("bill_name").equals("管理分摊配置")) {
						mserverdept.put("type_code", "('01','02','03')");
						mserverdept.remove("natur_code");
					}
					if (map.get("bill_name").equals("其他医辅配置")) {
						mserverdept.put("type_code", "('01','02')");
						mserverdept.remove("natur_code");
					}
					if (map.get("bill_name").equals("门诊医辅配置")) {
						mserverdept.put("type_code", "('01','02')");
						mserverdept.put("natur_code", "('01')");
					}
					if (map.get("bill_name").equals("住院医辅配置")) {
						mserverdept.put("type_code", "('01','02')");
						mserverdept.put("natur_code", "('02')");
					}
					if (map.get("bill_name").equals("其他医技配置")) {
						mserverdept.put("type_code", "('01')");
						mserverdept.remove("natur_code");
					}
					if (map.get("bill_name").equals("药库配置")) {
						mserverdept.put("type_code", "('02')");
						mserverdept.put("natur_code", "('04')");
					}
					if (map.get("bill_name").equals("药房配置")) {
						mserverdept.put("type_code", "('01')");
						mserverdept.remove("natur_code");
					}
					if (map.get("bill_name").equals("药剂配置")) {
						mserverdept.put("type_code", "('01')");
						mserverdept.remove("natur_code");
					}
					
					costParaSetverDeptMapper.addBatchByGenerate(mserverdept);

				}
				
			}
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public String inheritance(Map<String, Object> entityMap) throws DataAccessException {

		try {
			// 查询某个月的数据进行继承
			List<Map<String, Object>> list = (List<Map<String, Object>>) costParaBillMapper.query(entityMap);
			if (list.size() > 0) {
				
				costParaBillMapper.addinheritance(entityMap);
				
				return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
				
			}else{
				return "{\"msg\":\"没有可继承的数据.\",\"state\":\"true\"}";
			}
			
			
        }
        catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	throw new SysException("{\"error\":\"继承失败\"}");
        }
		
	}

	@Override
	public Long querySequence() throws DataAccessException {
		return costParaBillMapper.querySequence();
	}

	@Override
	public List<?> queryParaDeptDict(Map<String, Object> entityMap) throws DataAccessException {

		return costParaBillMapper.queryParaDeptDict(entityMap);

	}

}
