/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.in.rest;

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
import com.chd.hrp.ass.dao.accessory.AssAccessoryInassetsMapper;
import com.chd.hrp.ass.dao.card.AssCardInassetsMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccInassetsMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageInassetsMapper;
import com.chd.hrp.ass.dao.file.AssFileInassetsMapper;
import com.chd.hrp.ass.dao.in.rest.AssInRestDetailInassetsMapper;
import com.chd.hrp.ass.dao.in.rest.AssInRestMainInassetsMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInassetsMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoInassetsMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInassetsMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRInassetsMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInassetsMapper;
import com.chd.hrp.ass.entity.card.AssCardInassets;
import com.chd.hrp.ass.entity.file.AssFileInassets;
import com.chd.hrp.ass.entity.in.rest.AssInRestDetailInassets;
import com.chd.hrp.ass.entity.photo.AssPhotoInassets;
import com.chd.hrp.ass.service.in.rest.AssInRestDetailInassetsService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 050701 资产其他入库明细(其他无形资产)
 * @Table: ASS_IN_REST_DETAIL_INASSETS
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("assInRestDetailInassetsService")
public class AssInRestDetailInassetsServiceImpl implements AssInRestDetailInassetsService {

	private static Logger logger = Logger.getLogger(AssInRestDetailInassetsServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assInRestDetailInassetsMapper")
	private final AssInRestDetailInassetsMapper assInRestDetailInassetsMapper = null;

	@Resource(name = "assInRestMainInassetsMapper")
	private final AssInRestMainInassetsMapper assInRestMainInassetsMapper = null;

	@Resource(name = "assCardInassetsMapper")
	private final AssCardInassetsMapper assCardInassetsMapper = null;

	@Resource(name = "assResourceInassetsMapper")
	private final AssResourceInassetsMapper assResourceInassetsMapper = null;

	@Resource(name = "assShareDeptInassetsMapper")
	private final AssShareDeptInassetsMapper assShareDeptInassetsMapper = null;

	@Resource(name = "assShareDeptRInassetsMapper")
	private final AssShareDeptRInassetsMapper assShareDeptRInassetsMapper = null;

	@Resource(name = "assFileInassetsMapper")
	private final AssFileInassetsMapper assFileInassetsMapper = null;

	@Resource(name = "assPhotoInassetsMapper")
	private final AssPhotoInassetsMapper assPhotoInassetsMapper = null;

	@Resource(name = "assAccessoryInassetsMapper")
	private final AssAccessoryInassetsMapper assAccessoryInassetsMapper = null;

	@Resource(name = "assDepreAccInassetsMapper")
	private final AssDepreAccInassetsMapper assDepreAccInassetsMapper = null;

	@Resource(name = "assDepreManageInassetsMapper")
	private final AssDepreManageInassetsMapper assDepreManageInassetsMapper = null;

	@Resource(name = "assPayStageInassetsMapper")
	private final AssPayStageInassetsMapper assPayStageInassetsMapper = null;

	/**
	 * @Description 添加050701 资产其他入库明细(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050701 资产其他入库明细(无形资产)
		AssInRestDetailInassets assInRestDetailInassets = queryByCode(entityMap);

		if (assInRestDetailInassets != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assInRestDetailInassetsMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050701 资产其他入库明细(无形资产)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assInRestDetailInassetsMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050701 资产其他入库明细(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assInRestDetailInassetsMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050701 资产其他入库明细(无形资产)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assInRestDetailInassetsMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050701 资产其他入库明细(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assInRestDetailInassetsMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050701 资产其他入库明细(无形资产)<BR>
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
			inMapVo.put("ass_in_no", entityList.get(0).get("ass_in_no"));
			delCard(entityList);

			assInRestDetailInassetsMapper.deleteBatch(entityList);

			List<AssInRestDetailInassets> list = (List<AssInRestDetailInassets>) assInRestDetailInassetsMapper
					.queryExists(inMapVo);

			double in_money = 0;
			if (list != null) {
				for (AssInRestDetailInassets temp : list) {
					in_money += temp.getPrice() * temp.getIn_amount();
				}
			}
			inMapVo.put("in_money", in_money + "");
			assInRestMainInassetsMapper.updateInMoney(inMapVo);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"in_money\":\"" + in_money + "\"}";

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
			List<Map<String, Object>> cardList = assCardInassetsMapper.queryByAssInNo(inMapVo);
			if (cardList.size() > 0) {

				// 删除资产文档
				for (Map<String, Object> fileMap : cardList) {
					List<AssFileInassets> assFileInassetsList = (List<AssFileInassets>) assFileInassetsMapper
							.queryExists(fileMap);
					if (assFileInassetsList.size() > 0 && assFileInassetsList != null) {
						for (AssFileInassets assFileInassets : assFileInassetsList) {
							if (assFileInassets.getFile_url() != null && !assFileInassets.getFile_url().equals("")) {
								String file_name = assFileInassets.getFile_url().substring(
										assFileInassets.getFile_url().lastIndexOf("/") + 1,
										assFileInassets.getFile_url().length());
								String path = assFileInassets.getFile_url().substring(0,
										assFileInassets.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0, path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assFileInassets.getAss_card_no(), path);
							}
						}
					}
				}
				// 删除资产照片
				for (Map<String, Object> photoMap : cardList) {
					List<AssPhotoInassets> assPhotoInassetsList = (List<AssPhotoInassets>) assPhotoInassetsMapper
							.queryExists(photoMap);
					if (assPhotoInassetsList.size() > 0 && assPhotoInassetsList != null) {
						for (AssPhotoInassets assPhotoInassets : assPhotoInassetsList) {
							if (assPhotoInassets.getFile_url() != null && !assPhotoInassets.getFile_url().equals("")) {
								String file_name = assPhotoInassets.getFile_url().substring(
										assPhotoInassets.getFile_url().lastIndexOf("/") + 1,
										assPhotoInassets.getFile_url().length());
								String path = assPhotoInassets.getFile_url().substring(0,
										assPhotoInassets.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0, path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assPhotoInassets.getAss_card_no(), path);
							}
						}
					}
				}
				assShareDeptRInassetsMapper.deleteBatch(cardList);
				assShareDeptInassetsMapper.deleteBatch(cardList);
				assResourceInassetsMapper.deleteBatch(cardList);
				assFileInassetsMapper.deleteBatch(cardList);
				assPhotoInassetsMapper.deleteBatch(cardList);
				assAccessoryInassetsMapper.deleteBatch(cardList);
				assPayStageInassetsMapper.deleteBatch(cardList);
				assDepreAccInassetsMapper.deleteBatch(cardList);
				assDepreManageInassetsMapper.deleteBatch(cardList);
				assCardInassetsMapper.deleteBatchByAssInNo(cardList);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	/**
	 * @Description 添加050701 资产其他入库明细(无形资产)<BR>
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
		// 判断是否存在对象050701 资产其他入库明细(无形资产)
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<AssInRestDetailInassets> list = (List<AssInRestDetailInassets>) assInRestDetailInassetsMapper
				.queryExists(mapVo);

		if (list.size() > 0) {

			int state = assInRestDetailInassetsMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = assInRestDetailInassetsMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}

	}

	/**
	 * @Description 查询结果集050701 资产其他入库明细(无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssInRestDetailInassets> list = (List<AssInRestDetailInassets>) assInRestDetailInassetsMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssInRestDetailInassets> list = (List<AssInRestDetailInassets>) assInRestDetailInassetsMapper
					.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050701 资产其他入库明细(无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assInRestDetailInassetsMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050701 资产其他入库明细(无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssInRestDetailInassets
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assInRestDetailInassetsMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050701 资产其他入库明细(无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssInRestDetailInassets>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assInRestDetailInassetsMapper.queryExists(entityMap);
	}

	@Override
	public List<AssInRestDetailInassets> queryByAssInNo(Map<String, Object> entityMap) throws DataAccessException {
		return assInRestDetailInassetsMapper.queryByAssInNo(entityMap);
	}

	@Override
	public List<Map<String, Object>> queryByInit(Map<String, Object> entityMap) throws DataAccessException {
		return assInRestDetailInassetsMapper.queryByInit(entityMap);
	}

}
