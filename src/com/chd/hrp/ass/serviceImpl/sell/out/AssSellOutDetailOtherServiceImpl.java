/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.sell.out;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.sell.out.AssSellOutDetailOtherMapper;
import com.chd.hrp.ass.dao.sell.out.AssSellOutDetailOtherMapper;
import com.chd.hrp.ass.dao.sell.out.AssSellOutOtherMapper;
import com.chd.hrp.ass.dao.sell.out.source.AssSellOutSourceOtherMapper;
import com.chd.hrp.ass.entity.sell.out.AssSellOutDetailOther;
import com.chd.hrp.ass.entity.sell.out.AssSellOutDetailOther;
import com.chd.hrp.ass.service.sell.out.AssSellOutDetailOtherService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050901 资产有偿调拨出库单明细(其他固定资产)
 * @Table:
 * ASS_SELL_OUT_DETAIL_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assSellOutDetailOtherService")
public class AssSellOutDetailOtherServiceImpl implements AssSellOutDetailOtherService {

	private static Logger logger = Logger.getLogger(AssSellOutDetailOtherServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assSellOutDetailOtherMapper")
	private final AssSellOutDetailOtherMapper assSellOutDetailOtherMapper = null;

	@Resource(name = "assSellOutSourceOtherMapper")
	private final AssSellOutSourceOtherMapper assSellOutSourceOtherMapper = null;

	@Resource(name = "assSellOutOtherMapper")
	private final AssSellOutOtherMapper assSellOutOtherMapper = null;

	/**
	 * @Description 添加050901 资产有偿调拨出库单明细(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050901 资产有偿调拨出库单明细(专用设备)
		AssSellOutDetailOther assSellOutDetailOther = queryByCode(entityMap);

		if (assSellOutDetailOther != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assSellOutDetailOtherMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050901 资产有偿调拨出库单明细(专用设备)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assSellOutDetailOtherMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050901 资产有偿调拨出库单明细(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assSellOutDetailOtherMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050901 资产有偿调拨出库单明细(专用设备)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assSellOutDetailOtherMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050901 资产有偿调拨出库单明细(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assSellOutDetailOtherMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050901 资产有偿调拨出库单明细(专用设备)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			Map<String, Object> inMapVo = new HashMap<String, Object>();
			inMapVo.put("group_id", entityList.get(0).get("group_id"));
			inMapVo.put("hos_id", entityList.get(0).get("hos_id"));
			inMapVo.put("copy_code", entityList.get(0).get("copy_code"));
			inMapVo.put("allot_out_no", entityList.get(0).get("allot_out_no"));

			assSellOutSourceOtherMapper.deleteBatch(entityList);
			assSellOutDetailOtherMapper.deleteBatch(entityList);

			List<AssSellOutDetailOther> list = (List<AssSellOutDetailOther>) assSellOutDetailOtherMapper
					.queryExists(inMapVo);

			double price = 0;
			double add_depre = 0;
			double cur_money = 0;
			double fore_money = 0;
			double sell_price = 0;
			double dispose_income = 0;
			double dispose_tax = 0;

			if (list != null) {
				for (AssSellOutDetailOther temp : list) {
					price += temp.getPrice();
					add_depre += temp.getAdd_depre();
					cur_money += temp.getCur_money();
					fore_money += temp.getFore_money();
					if(temp.getSell_price()!=null && "".equals(temp.getSell_price())){
						sell_price += temp.getSell_price();
					}
					
					dispose_income += temp.getDispose_income();
					dispose_tax += temp.getDispose_tax();
				}
			}
			inMapVo.put("price", price + "");
			inMapVo.put("add_depre", add_depre + "");
			inMapVo.put("cur_money", cur_money + "");
			inMapVo.put("fore_money", fore_money + "");
			inMapVo.put("sell_price", sell_price + "");
			inMapVo.put("dispose_income", dispose_income + "");
			inMapVo.put("dispose_tax", dispose_tax + "");

			assSellOutOtherMapper.updateSellOutMoney(inMapVo);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"price\":\"" + price + "\",\"price\":\"" + add_depre
					+ "\",\"add_depre\":\"" + cur_money + "\",\"fore_money\":\"" + fore_money + "\",\"sell_price\":\""
					+ sell_price + "\",\"dispose_income\":\"" + dispose_income + "\",\"dispose_tax\":\"" + dispose_tax + "\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 添加050901 资产有偿调拨出库单明细(专用设备)<BR>
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
		// 判断是否存在对象050901 资产有偿调拨出库单明细(专用设备)
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<AssSellOutDetailOther> list = (List<AssSellOutDetailOther>) assSellOutDetailOtherMapper
				.queryExists(mapVo);

		if (list.size() > 0) {

			int state = assSellOutDetailOtherMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = assSellOutDetailOtherMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}

	}

	/**
	 * @Description 查询结果集050901 资产有偿调拨出库单明细(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssSellOutDetailOther> list = (List<AssSellOutDetailOther>) assSellOutDetailOtherMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssSellOutDetailOther> list = (List<AssSellOutDetailOther>) assSellOutDetailOtherMapper
					.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050901 资产有偿调拨出库单明细(专用设备)<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assSellOutDetailOtherMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050901 资产有偿调拨出库单明细(专用设备)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssSellOutDetailOther
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assSellOutDetailOtherMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050901 资产有偿调拨出库单明细(专用设备)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssSellOutDetailOther>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assSellOutDetailOtherMapper.queryExists(entityMap);
	}

	@Override
	public List<AssSellOutDetailOther> queryByAssSellOutNo(Map<String, Object> entityMap) throws DataAccessException {
		return assSellOutDetailOtherMapper.queryBySellOutNo(entityMap);
	}

}
