/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.allot.in;

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
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.accessory.AssAccessoryHouseMapper;
import com.chd.hrp.ass.dao.allot.in.AssAllotInDetailHouseMapper;
import com.chd.hrp.ass.dao.allot.in.AssAllotInDetailHouseMapper;
import com.chd.hrp.ass.dao.allot.in.AssAllotInHouseMapper;
import com.chd.hrp.ass.dao.card.AssCardHouseMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccHouseMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageHouseMapper;
import com.chd.hrp.ass.dao.file.AssFileHouseMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageHouseMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoHouseMapper;
import com.chd.hrp.ass.dao.resource.AssResourceHouseMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptHouseMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRHouseMapper;
import com.chd.hrp.ass.entity.allot.in.AssAllotInDetailHouse;
import com.chd.hrp.ass.entity.allot.in.AssAllotInDetailHouse;
import com.chd.hrp.ass.entity.file.AssFileHouse;
import com.chd.hrp.ass.entity.photo.AssPhotoHouse;
import com.chd.hrp.ass.service.allot.in.AssAllotInDetailHouseService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050901 资产无偿调拨入库明细（房屋及建筑物）
 * @Table:
 * ASS_ALLOT_IN_DETAIL_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assAllotInDetailHouseService")
public class AssAllotInDetailHouseServiceImpl implements AssAllotInDetailHouseService {

	private static Logger logger = Logger.getLogger(AssAllotInDetailHouseServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assAllotInDetailHouseMapper")
	private final AssAllotInDetailHouseMapper assAllotInDetailHouseMapper = null;

	@Resource(name = "assAllotInHouseMapper")
	private final AssAllotInHouseMapper assAllotInHouseMapper = null;

	@Resource(name = "assCardHouseMapper")
	private final AssCardHouseMapper assCardHouseMapper = null;

	@Resource(name = "assResourceHouseMapper")
	private final AssResourceHouseMapper assResourceHouseMapper = null;

	@Resource(name = "assShareDeptHouseMapper")
	private final AssShareDeptHouseMapper assShareDeptHouseMapper = null;

	@Resource(name = "assShareDeptRHouseMapper")
	private final AssShareDeptRHouseMapper assShareDeptRHouseMapper = null;

	@Resource(name = "assFileHouseMapper")
	private final AssFileHouseMapper assFileHouseMapper = null;

	@Resource(name = "assPhotoHouseMapper")
	private final AssPhotoHouseMapper assPhotoHouseMapper = null;

	@Resource(name = "assAccessoryHouseMapper")
	private final AssAccessoryHouseMapper assAccessoryHouseMapper = null;

	@Resource(name = "assDepreAccHouseMapper")
	private final AssDepreAccHouseMapper assDepreAccHouseMapper = null;

	@Resource(name = "assDepreManageHouseMapper")
	private final AssDepreManageHouseMapper assDepreManageHouseMapper = null;

	@Resource(name = "assPayStageHouseMapper")
	private final AssPayStageHouseMapper assPayStageHouseMapper = null;

	/**
	 * @Description 添加050901 资产无偿调拨入库明细(房屋及建筑)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050901 资产无偿调拨入库明细(房屋及建筑)
		AssAllotInDetailHouse assAllotInDetailHouse = queryByCode(entityMap);

		if (assAllotInDetailHouse != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assAllotInDetailHouseMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050901 资产无偿调拨入库明细(房屋及建筑)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assAllotInDetailHouseMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050901 资产无偿调拨入库明细(房屋及建筑)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assAllotInDetailHouseMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050901 资产无偿调拨入库明细(房屋及建筑)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assAllotInDetailHouseMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050901 资产无偿调拨入库明细(房屋及建筑)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assAllotInDetailHouseMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050901 资产无偿调拨入库明细(房屋及建筑)<BR>
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
			inMapVo.put("allot_in_no", entityList.get(0).get("allot_in_no"));
			inMapVo.put("ass_in_no", entityList.get(0).get("ass_in_no"));

			delCard(entityList);

			assAllotInDetailHouseMapper.deleteBatch(entityList);

			List<AssAllotInDetailHouse> list = (List<AssAllotInDetailHouse>) assAllotInDetailHouseMapper
					.queryExists(inMapVo);

			double price = 0;
			double add_depre = 0;
			double cur_money = 0;
			double fore_money = 0;

			if (list != null) {
				for (AssAllotInDetailHouse temp : list) {
					price += temp.getPrice();
					add_depre += temp.getAdd_depre();
					cur_money += temp.getCur_money();
					fore_money += temp.getFore_money();
				}
			}
			inMapVo.put("price", price + "");
			inMapVo.put("add_depre", add_depre + "");
			inMapVo.put("cur_money", cur_money + "");
			inMapVo.put("fore_money", fore_money + "");
			assAllotInHouseMapper.updateInMoney(inMapVo);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"price\":\"" + price + "\",\"price\":\"" + add_depre
					+ "\",\"add_depre\":\"" + cur_money + "\",\"fore_money\":\"" + fore_money + "\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	private void delCard(List<Map<String, Object>> entityList) {
		try {
			Map<String, Object> inMapVo = new HashMap<String, Object>();
			inMapVo.put("group_id", entityList.get(0).get("group_id"));
			inMapVo.put("hos_id", entityList.get(0).get("hos_id"));
			inMapVo.put("copy_code", entityList.get(0).get("copy_code"));
			inMapVo.put("ass_in_no", entityList.get(0).get("ass_in_no"));
			inMapVo.put("ass_id", entityList.get(0).get("ass_id"));
			inMapVo.put("ass_no", entityList.get(0).get("ass_no"));
			List<Map<String, Object>> cardList = assCardHouseMapper.queryByAssInNo(inMapVo);
			if (cardList.size() > 0) {

				// 删除资产文档
				for (Map<String, Object> fileMap : cardList) {
					List<AssFileHouse> assFileHouseList = (List<AssFileHouse>) assFileHouseMapper
							.queryExists(fileMap);
					if (assFileHouseList.size() > 0 && assFileHouseList != null) {
						for (AssFileHouse assFileHouse : assFileHouseList) {
							if (assFileHouse.getFile_url() != null && !assFileHouse.getFile_url().equals("")) {
								String file_name = assFileHouse.getFile_url().substring(
										assFileHouse.getFile_url().lastIndexOf("/") + 1,
										assFileHouse.getFile_url().length());
								String path = assFileHouse.getFile_url().substring(0,
										assFileHouse.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0, path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assFileHouse.getAss_card_no(), path);
							}
						}
					}
				}
				// 删除资产照片
				for (Map<String, Object> photoMap : cardList) {
					List<AssPhotoHouse> assPhotoHouseList = (List<AssPhotoHouse>) assPhotoHouseMapper
							.queryExists(photoMap);
					if (assPhotoHouseList.size() > 0 && assPhotoHouseList != null) {
						for (AssPhotoHouse assPhotoHouse : assPhotoHouseList) {
							if (assPhotoHouse.getFile_url() != null && !assPhotoHouse.getFile_url().equals("")) {
								String file_name = assPhotoHouse.getFile_url().substring(
										assPhotoHouse.getFile_url().lastIndexOf("/") + 1,
										assPhotoHouse.getFile_url().length());
								String path = assPhotoHouse.getFile_url().substring(0,
										assPhotoHouse.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0, path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assPhotoHouse.getAss_card_no(), path);
							}
						}
					}
				}
				assShareDeptRHouseMapper.deleteBatch(cardList);
				assShareDeptHouseMapper.deleteBatch(cardList);
				assResourceHouseMapper.deleteBatch(cardList);
				assFileHouseMapper.deleteBatch(cardList);
				assPhotoHouseMapper.deleteBatch(cardList);
				assAccessoryHouseMapper.deleteBatch(cardList);
				assPayStageHouseMapper.deleteBatch(cardList);
				assDepreAccHouseMapper.deleteBatch(cardList);
				assDepreManageHouseMapper.deleteBatch(cardList);
				assCardHouseMapper.deleteBatchByAssInNo(cardList);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	/**
	 * @Description 添加050901 资产无偿调拨入库明细(房屋及建筑)<BR>
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
		// 判断是否存在对象050901 资产无偿调拨入库明细(房屋及建筑)
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<AssAllotInDetailHouse> list = (List<AssAllotInDetailHouse>) assAllotInDetailHouseMapper
				.queryExists(mapVo);

		if (list.size() > 0) {

			int state = assAllotInDetailHouseMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = assAllotInDetailHouseMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}

	}

	/**
	 * @Description 查询结果集050901 资产无偿调拨入库明细(房屋及建筑)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssAllotInDetailHouse> list = (List<AssAllotInDetailHouse>) assAllotInDetailHouseMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssAllotInDetailHouse> list = (List<AssAllotInDetailHouse>) assAllotInDetailHouseMapper
					.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050901 资产无偿调拨入库明细(房屋及建筑)<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInDetailHouseMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050901 资产无偿调拨入库明细(房屋及建筑)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssAllotInDetailHouse
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInDetailHouseMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050901 资产无偿调拨入库明细(房屋及建筑)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssAllotInDetailHouse>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInDetailHouseMapper.queryExists(entityMap);
	}

	@Override
	public List<AssAllotInDetailHouse> queryByAssInNo(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInDetailHouseMapper.queryByAssInNo(entityMap);
	}

	@Override
	public List<Map<String, Object>> queryByInit(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInDetailHouseMapper.queryByInit(entityMap);
	}

}
