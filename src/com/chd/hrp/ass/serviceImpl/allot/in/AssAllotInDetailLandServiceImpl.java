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
import com.chd.hrp.ass.dao.accessory.AssAccessoryLandMapper;
import com.chd.hrp.ass.dao.allot.in.AssAllotInDetailLandMapper;
import com.chd.hrp.ass.dao.allot.in.AssAllotInLandMapper;
import com.chd.hrp.ass.dao.card.AssCardLandMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccLandMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageLandMapper;
import com.chd.hrp.ass.dao.file.AssFileLandMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageLandMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoLandMapper;
import com.chd.hrp.ass.dao.resource.AssResourceLandMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptLandMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRLandMapper;
import com.chd.hrp.ass.entity.allot.in.AssAllotInDetailLand;
import com.chd.hrp.ass.entity.file.AssFileLand;
import com.chd.hrp.ass.entity.photo.AssPhotoLand;
import com.chd.hrp.ass.service.allot.in.AssAllotInDetailLandService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 050901 资产无偿调拨入库明细(土地)
 * @Table: ASS_ALLOT_IN_DETAIL_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("assAllotInDetailLandService")
public class AssAllotInDetailLandServiceImpl implements AssAllotInDetailLandService {

	private static Logger logger = Logger.getLogger(AssAllotInDetailLandServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assAllotInDetailLandMapper")
	private final AssAllotInDetailLandMapper assAllotInDetailLandMapper = null;

	@Resource(name = "assAllotInLandMapper")
	private final AssAllotInLandMapper assAllotInLandMapper = null;

	@Resource(name = "assCardLandMapper")
	private final AssCardLandMapper assCardLandMapper = null;

	@Resource(name = "assResourceLandMapper")
	private final AssResourceLandMapper assResourceLandMapper = null;

	@Resource(name = "assShareDeptLandMapper")
	private final AssShareDeptLandMapper assShareDeptLandMapper = null;

	@Resource(name = "assShareDeptRLandMapper")
	private final AssShareDeptRLandMapper assShareDeptRLandMapper = null;

	@Resource(name = "assFileLandMapper")
	private final AssFileLandMapper assFileLandMapper = null;

	@Resource(name = "assPhotoLandMapper")
	private final AssPhotoLandMapper assPhotoLandMapper = null;

	@Resource(name = "assAccessoryLandMapper")
	private final AssAccessoryLandMapper assAccessoryLandMapper = null;

	@Resource(name = "assDepreAccLandMapper")
	private final AssDepreAccLandMapper assDepreAccLandMapper = null;

	@Resource(name = "assDepreManageLandMapper")
	private final AssDepreManageLandMapper assDepreManageLandMapper = null;

	@Resource(name = "assPayStageLandMapper")
	private final AssPayStageLandMapper assPayStageLandMapper = null;

	/**
	 * @Description 添加050901 资产无偿调拨入库明细(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050901 资产无偿调拨入库明细(无形资产)
		AssAllotInDetailLand assAllotInDetailLand = queryByCode(entityMap);

		if (assAllotInDetailLand != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assAllotInDetailLandMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050901 资产无偿调拨入库明细(无形资产)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assAllotInDetailLandMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050901 资产无偿调拨入库明细(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assAllotInDetailLandMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050901 资产无偿调拨入库明细(无形资产)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assAllotInDetailLandMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050901 资产无偿调拨入库明细(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assAllotInDetailLandMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050901 资产无偿调拨入库明细(无形资产)<BR>
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

			assAllotInDetailLandMapper.deleteBatch(entityList);

			List<AssAllotInDetailLand> list = (List<AssAllotInDetailLand>) assAllotInDetailLandMapper
					.queryExists(inMapVo);

			double price = 0;
			double add_depre = 0;
			double cur_money = 0;
			double fore_money = 0;

			if (list != null) {
				for (AssAllotInDetailLand temp : list) {
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
			assAllotInLandMapper.updateInMoney(inMapVo);

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
			List<Map<String, Object>> cardList = assCardLandMapper.queryByAssInNo(inMapVo);
			if (cardList.size() > 0) {

				// 删除资产文档
				for (Map<String, Object> fileMap : cardList) {
					List<AssFileLand> assFileLandList = (List<AssFileLand>) assFileLandMapper
							.queryExists(fileMap);
					if (assFileLandList.size() > 0 && assFileLandList != null) {
						for (AssFileLand assFileLand : assFileLandList) {
							if (assFileLand.getFile_url() != null && !assFileLand.getFile_url().equals("")) {
								String file_name = assFileLand.getFile_url().substring(
										assFileLand.getFile_url().lastIndexOf("/") + 1,
										assFileLand.getFile_url().length());
								String path = assFileLand.getFile_url().substring(0,
										assFileLand.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0, path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assFileLand.getAss_card_no(), path);
							}
						}
					}
				}
				// 删除资产照片
				for (Map<String, Object> photoMap : cardList) {
					List<AssPhotoLand> assPhotoLandList = (List<AssPhotoLand>) assPhotoLandMapper
							.queryExists(photoMap);
					if (assPhotoLandList.size() > 0 && assPhotoLandList != null) {
						for (AssPhotoLand assPhotoLand : assPhotoLandList) {
							if (assPhotoLand.getFile_url() != null && !assPhotoLand.getFile_url().equals("")) {
								String file_name = assPhotoLand.getFile_url().substring(
										assPhotoLand.getFile_url().lastIndexOf("/") + 1,
										assPhotoLand.getFile_url().length());
								String path = assPhotoLand.getFile_url().substring(0,
										assPhotoLand.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0, path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assPhotoLand.getAss_card_no(), path);
							}
						}
					}
				}
				assShareDeptRLandMapper.deleteBatch(cardList);
				assShareDeptLandMapper.deleteBatch(cardList);
				assResourceLandMapper.deleteBatch(cardList);
				assFileLandMapper.deleteBatch(cardList);
				assPhotoLandMapper.deleteBatch(cardList);
				assAccessoryLandMapper.deleteBatch(cardList);
				assPayStageLandMapper.deleteBatch(cardList);
				assDepreAccLandMapper.deleteBatch(cardList);
				assDepreManageLandMapper.deleteBatch(cardList);
				assCardLandMapper.deleteBatchByAssInNo(cardList);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	/**
	 * @Description 添加050901 资产无偿调拨入库明细(无形资产)<BR>
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
		// 判断是否存在对象050901 资产无偿调拨入库明细(无形资产)
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<AssAllotInDetailLand> list = (List<AssAllotInDetailLand>) assAllotInDetailLandMapper
				.queryExists(mapVo);

		if (list.size() > 0) {

			int state = assAllotInDetailLandMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = assAllotInDetailLandMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}

	}

	/**
	 * @Description 查询结果集050901 资产无偿调拨入库明细(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssAllotInDetailLand> list = (List<AssAllotInDetailLand>) assAllotInDetailLandMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssAllotInDetailLand> list = (List<AssAllotInDetailLand>) assAllotInDetailLandMapper
					.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050901 资产无偿调拨入库明细(无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInDetailLandMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050901 资产无偿调拨入库明细(无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssAllotInDetailLand
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInDetailLandMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050901 资产无偿调拨入库明细(无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssAllotInDetailLand>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInDetailLandMapper.queryExists(entityMap);
	}

	@Override
	public List<AssAllotInDetailLand> queryByAssInNo(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInDetailLandMapper.queryByAssInNo(entityMap);
	}

	@Override
	public List<Map<String, Object>> queryByInit(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInDetailLandMapper.queryByInit(entityMap);
	}

}
