/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.card;

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
import com.chd.base.exception.SysException;
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.accessory.AssAccessoryInitOtherMapper;
import com.chd.hrp.ass.dao.card.AssCardInitOtherMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccInitOtherMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageInitOtherMapper;
import com.chd.hrp.ass.dao.file.AssFileInitOtherMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInitOtherMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoInitOtherMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInitOtherMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInitOtherMapper;
import com.chd.hrp.ass.entity.card.AssCardInitOther;
import com.chd.hrp.ass.entity.file.AssFileInitOther;
import com.chd.hrp.ass.entity.photo.AssPhotoInitOther;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.card.AssCardInitOtherService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 资产卡片维护_其他固定资产
 * @Table: ASS_CARD_OTHER
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("assCardInitOtherService")
public class AssCardInitOtherServiceImpl implements AssCardInitOtherService {

	private static Logger logger = Logger.getLogger(AssCardInitOtherServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assCardInitOtherMapper")
	private final AssCardInitOtherMapper assCardInitOtherMapper = null;

	@Resource(name = "assResourceInitOtherMapper")
	private final AssResourceInitOtherMapper assResourceInitOtherMapper = null;

	@Resource(name = "assShareDeptInitOtherMapper")
	private final AssShareDeptInitOtherMapper assShareDeptInitOtherMapper = null;

	@Resource(name = "assFileInitOtherMapper")
	private final AssFileInitOtherMapper assFileInitOtherMapper = null;

	@Resource(name = "assPhotoInitOtherMapper")
	private final AssPhotoInitOtherMapper assPhotoInitOtherMapper = null;

	@Resource(name = "assAccessoryInitOtherMapper")
	private final AssAccessoryInitOtherMapper assAccessoryInitOtherMapper = null;

	@Resource(name = "assDepreAccInitOtherMapper")
	private final AssDepreAccInitOtherMapper assDepreAccInitOtherMapper = null;

	@Resource(name = "assDepreManageInitOtherMapper")
	private final AssDepreManageInitOtherMapper assDepreManageInitOtherMapper = null;

	@Resource(name = "assPayStageInitOtherMapper")
	private final AssPayStageInitOtherMapper assPayStageInitOtherMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 添加资产卡片维护_其他固定资产<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		try {
			if (entityMap.get("ven_id") == null || entityMap.get("ven_id").equals("")
					|| entityMap.get("ven_id").equals("undefined")) {
				entityMap.put("ven_id", "");
				entityMap.put("ven_no", "");
			}

			if(entityMap.get("fac_id") == null || entityMap.get("fac_id").equals("") || entityMap.get("fac_id").equals("undefined")){
				entityMap.put("fac_id","");
				entityMap.put("fac_no","");
			}
			
			Map<String, Object> delMap = new HashMap<String, Object>();
			delMap.put("group_id", entityMap.get("group_id"));
			delMap.put("hos_id", entityMap.get("hos_id"));
			delMap.put("copy_code", entityMap.get("copy_code"));
			delMap.put("ass_card_no", entityMap.get("ass_card_no_old"));

			assShareDeptInitOtherMapper.delete(delMap);
			assResourceInitOtherMapper.delete(delMap);
			assFileInitOtherMapper.delete(delMap);
			assPhotoInitOtherMapper.delete(delMap);
			assAccessoryInitOtherMapper.delete(delMap);
			assPayStageInitOtherMapper.delete(delMap);
			assDepreAccInitOtherMapper.delete(delMap);
			assDepreManageInitOtherMapper.delete(delMap);

			assCardInitOtherMapper.delete(delMap);
			int state = assCardInitOtherMapper.add(entityMap);

			entityMap.put("source_id", 1);

			assResourceInitOtherMapper.add(entityMap);

			if (entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("")
					&& entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")) {
				entityMap.put("area", 1);
				assShareDeptInitOtherMapper.add(entityMap);
			}

			String basePath = "ass";
			String group_id = entityMap.get("group_id").toString();
			String hos_id = entityMap.get("hos_id").toString();
			String copy_code = entityMap.get("copy_code").toString();
			String assTwoPath = "assbartwo";
			String assOnePath = "assbarone";
			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/04/";// 资产性质为01
			String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assOnePath + "/04/";// 资产性质为01

			if (entityMap.get("is_bar") != null && !entityMap.get("is_bar").equals("")) {
				if (entityMap.get("is_bar").toString().equals("1")) {
					if (entityMap.get("bar_type") != null && !entityMap.get("bar_type").equals("")) {
						if (entityMap.get("bar_type").toString().equals("1")) {
							FtpUtil.getBarcodeWriteFile(entityMap.get("ass_card_no").toString(), "", oneFilePath,
									entityMap.get("ass_card_no") + ".png");// 一维码
							entityMap.put("bar_url", oneFilePath + entityMap.get("ass_card_no") + ".png");

						} else if (entityMap.get("bar_type").toString().equals("2")) {
							FtpUtil.getQRWriteFile(entityMap.get("ass_card_no").toString(), "", twoFilePath,
									entityMap.get("ass_card_no") + ".png");// 二维码
							entityMap.put("bar_url", twoFilePath + entityMap.get("ass_card_no") + ".png");
						}
					}
				}
			}

			assBaseService.updateAssBillNoMaxNo("ASS_CARD_OTHER");
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"ass_card_no\":\"" + entityMap.get("ass_card_no") + "\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量添加资产卡片维护_其他固定资产<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assCardInitOtherMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新资产卡片维护_其他固定资产<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {
			String basePath = "ass";
			String group_id = entityMap.get("group_id").toString();
			String hos_id = entityMap.get("hos_id").toString();
			String copy_code = entityMap.get("copy_code").toString();
			String assTwoPath = "assbartwo";
			String assOnePath = "assbarone";
			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/04/";// 资产性质为01
			String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assOnePath + "/04/";// 资产性质为01

			AssCardInitOther assCardInitOther = assCardInitOtherMapper.queryByCode(entityMap);
			if (assCardInitOther.getBar_url() != null && !assCardInitOther.getBar_url().equals("")) {
				String file_name = assCardInitOther.getBar_url().substring(
						assCardInitOther.getBar_url().lastIndexOf("/") + 1, assCardInitOther.getBar_url().length());
				String path = assCardInitOther.getBar_url().substring(0,
						assCardInitOther.getBar_url().lastIndexOf("/"));
				FtpUtil.deleteFile(path, file_name);
			}

			if (entityMap.get("is_bar") != null && !entityMap.get("is_bar").equals("")) {
				if (entityMap.get("is_bar").toString().equals("1")) {
					if (entityMap.get("bar_type") != null && !entityMap.get("bar_type").equals("")) {
						if (entityMap.get("bar_type").toString().equals("1")) {
							FtpUtil.getBarcodeWriteFile(entityMap.get("ass_card_no").toString(), "", oneFilePath,
									entityMap.get("ass_card_no") + ".png");// 一维码
							entityMap.put("bar_url", oneFilePath + entityMap.get("ass_card_no") + ".png");

						} else if (entityMap.get("bar_type").toString().equals("2")) {
							FtpUtil.getQRWriteFile(entityMap.get("ass_card_no").toString(), "", twoFilePath,
									entityMap.get("ass_card_no") + ".png");// 二维码
							entityMap.put("bar_url", twoFilePath + entityMap.get("ass_card_no") + ".png");
						}
					}
				}
			}
			int state = assCardInitOtherMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量更新资产卡片维护_其他固定资产<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assCardInitOtherMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除资产卡片维护_其他固定资产<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {
			List<Map<String, Object>> entityList = assCardInitOtherMapper.queryCardByMap(entityMap);
			// 删除条码文件
			for (Map<String, Object> map : entityList) {
				AssCardInitOther assCardInitOther = assCardInitOtherMapper.queryByCode(map);
				if (assCardInitOther.getBar_url() != null && !assCardInitOther.getBar_url().equals("")) {
					String file_name = assCardInitOther.getBar_url().substring(
							assCardInitOther.getBar_url().lastIndexOf("/") + 1, assCardInitOther.getBar_url().length());
					String path = assCardInitOther.getBar_url().substring(0,
							assCardInitOther.getBar_url().lastIndexOf("/"));
					FtpUtil.deleteFile(path, file_name);
				}
			}

			// 删除资产文档
			for (Map<String, Object> fileMap : entityList) {
				List<AssFileInitOther> assFileInitOtherList = (List<AssFileInitOther>) assFileInitOtherMapper
						.queryExists(fileMap);
				if (assFileInitOtherList.size() > 0 && assFileInitOtherList != null) {
					for (AssFileInitOther assFileInitOther : assFileInitOtherList) {
						if (assFileInitOther.getFile_url() != null && !assFileInitOther.getFile_url().equals("")) {
							String file_name = assFileInitOther.getFile_url().substring(
									assFileInitOther.getFile_url().lastIndexOf("/") + 1,
									assFileInitOther.getFile_url().length());
							String path = assFileInitOther.getFile_url().substring(0,
									assFileInitOther.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0, path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assFileInitOther.getAss_card_no(), path);
						}
					}
				}
			}

			// 删除资产照片
			for (Map<String, Object> photoMap : entityList) {
				List<AssPhotoInitOther> assPhotoInitOtherList = (List<AssPhotoInitOther>) assPhotoInitOtherMapper
						.queryExists(photoMap);
				if (assPhotoInitOtherList.size() > 0 && assPhotoInitOtherList != null) {
					for (AssPhotoInitOther assPhotoInitOther : assPhotoInitOtherList) {
						if (assPhotoInitOther.getFile_url() != null && !assPhotoInitOther.getFile_url().equals("")) {
							String file_name = assPhotoInitOther.getFile_url().substring(
									assPhotoInitOther.getFile_url().lastIndexOf("/") + 1,
									assPhotoInitOther.getFile_url().length());
							String path = assPhotoInitOther.getFile_url().substring(0,
									assPhotoInitOther.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0, path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assPhotoInitOther.getAss_card_no(), path);
						}
					}
				}
			}

			int state = assCardInitOtherMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量删除资产卡片维护_其他固定资产<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			// 删除条码文件
			for (Map<String, Object> map : entityList) {
				AssCardInitOther assCardInitOther = assCardInitOtherMapper.queryByCode(map);
				if (assCardInitOther.getBar_url() != null && !assCardInitOther.getBar_url().equals("")) {
					String file_name = assCardInitOther.getBar_url().substring(
							assCardInitOther.getBar_url().lastIndexOf("/") + 1, assCardInitOther.getBar_url().length());
					String path = assCardInitOther.getBar_url().substring(0,
							assCardInitOther.getBar_url().lastIndexOf("/"));
					FtpUtil.deleteFile(path, file_name);
				}
			}

			// 删除资产文档
			for (Map<String, Object> fileMap : entityList) {
				List<AssFileInitOther> assFileInitOtherList = (List<AssFileInitOther>) assFileInitOtherMapper
						.queryExists(fileMap);
				if (assFileInitOtherList.size() > 0 && assFileInitOtherList != null) {
					for (AssFileInitOther assFileInitOther : assFileInitOtherList) {
						if (assFileInitOther.getFile_url() != null && !assFileInitOther.getFile_url().equals("")) {
							String file_name = assFileInitOther.getFile_url().substring(
									assFileInitOther.getFile_url().lastIndexOf("/") + 1,
									assFileInitOther.getFile_url().length());
							String path = assFileInitOther.getFile_url().substring(0,
									assFileInitOther.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0, path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assFileInitOther.getAss_card_no(), path);
						}
					}
				}
			}

			// 删除资产照片
			for (Map<String, Object> photoMap : entityList) {
				List<AssPhotoInitOther> assPhotoInitOtherList = (List<AssPhotoInitOther>) assPhotoInitOtherMapper
						.queryExists(photoMap);
				if (assPhotoInitOtherList.size() > 0 && assPhotoInitOtherList != null) {
					for (AssPhotoInitOther assPhotoInitOther : assPhotoInitOtherList) {
						if (assPhotoInitOther.getFile_url() != null && !assPhotoInitOther.getFile_url().equals("")) {
							String file_name = assPhotoInitOther.getFile_url().substring(
									assPhotoInitOther.getFile_url().lastIndexOf("/") + 1,
									assPhotoInitOther.getFile_url().length());
							String path = assPhotoInitOther.getFile_url().substring(0,
									assPhotoInitOther.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0, path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assPhotoInitOther.getAss_card_no(), path);
						}
					}
				}
			}

			assShareDeptInitOtherMapper.deleteBatch(entityList);
			assResourceInitOtherMapper.deleteBatch(entityList);
			assFileInitOtherMapper.deleteBatch(entityList);
			assPhotoInitOtherMapper.deleteBatch(entityList);
			assAccessoryInitOtherMapper.deleteBatch(entityList);
			assPayStageInitOtherMapper.deleteBatch(entityList);
			assDepreAccInitOtherMapper.deleteBatch(entityList);
			assDepreManageInitOtherMapper.deleteBatch(entityList);
			assCardInitOtherMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 添加资产卡片维护_其他固定资产<BR>
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
		// 判断是否存在对象资产卡片维护_其他固定资产
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<AssCardInitOther> list = (List<AssCardInitOther>) assCardInitOtherMapper.queryExists(mapVo);

		if (list.size() > 0) {

			int state = assCardInitOtherMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = assCardInitOtherMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集资产卡片维护_其他固定资产<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssCardInitOther> list = (List<AssCardInitOther>) assCardInitOtherMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssCardInitOther> list = (List<AssCardInitOther>) assCardInitOtherMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象资产卡片维护_其他固定资产<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assCardInitOtherMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取资产卡片维护_其他固定资产<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssCardOther
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assCardInitOtherMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取资产卡片维护_其他固定资产<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssCardOther>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assCardInitOtherMapper.queryExists(entityMap);
	}

	@Override
	public List<Map<String, Object>> queryPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("user_id", SessionManager.getUserId());
		List<Map<String,Object>> list = (List<Map<String,Object>>) assCardInitOtherMapper.queryPrint(entityMap);
		return list;
	}

}
