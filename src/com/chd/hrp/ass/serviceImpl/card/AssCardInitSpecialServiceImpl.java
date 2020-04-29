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
import com.chd.hrp.ass.dao.accessory.AssAccessoryInitSpecialMapper;
import com.chd.hrp.ass.dao.card.AssCardInitSpecialMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccInitSpecialMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageInitSpecialMapper;
import com.chd.hrp.ass.dao.file.AssFileInitSpecialMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInitSpecialMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoInitSpecialMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInitSpecialMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInitSpecialMapper;
import com.chd.hrp.ass.entity.card.AssCardInitSpecial;
import com.chd.hrp.ass.entity.file.AssFileInitSpecial;
import com.chd.hrp.ass.entity.photo.AssPhotoInitSpecial;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.card.AssCardInitSpecialService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 资产卡片维护_专用设备
 * @Table: ASS_CARD_SPECIAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("assCardInitSpecialService")
public class AssCardInitSpecialServiceImpl implements AssCardInitSpecialService {

	private static Logger logger = Logger.getLogger(AssCardInitSpecialServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assCardInitSpecialMapper")
	private final AssCardInitSpecialMapper assCardInitSpecialMapper = null;

	@Resource(name = "assResourceInitSpecialMapper")
	private final AssResourceInitSpecialMapper assResourceInitSpecialMapper = null;

	@Resource(name = "assShareDeptInitSpecialMapper")
	private final AssShareDeptInitSpecialMapper assShareDeptInitSpecialMapper = null;

	@Resource(name = "assFileInitSpecialMapper")
	private final AssFileInitSpecialMapper assFileInitSpecialMapper = null;

	@Resource(name = "assPhotoInitSpecialMapper")
	private final AssPhotoInitSpecialMapper assPhotoInitSpecialMapper = null;

	@Resource(name = "assAccessoryInitSpecialMapper")
	private final AssAccessoryInitSpecialMapper assAccessoryInitSpecialMapper = null;

	@Resource(name = "assDepreAccInitSpecialMapper")
	private final AssDepreAccInitSpecialMapper assDepreAccInitSpecialMapper = null;

	@Resource(name = "assDepreManageInitSpecialMapper")
	private final AssDepreManageInitSpecialMapper assDepreManageInitSpecialMapper = null;

	@Resource(name = "assPayStageInitSpecialMapper")
	private final AssPayStageInitSpecialMapper assPayStageInitSpecialMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 添加资产卡片维护_专用设备<BR>
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

			assShareDeptInitSpecialMapper.delete(delMap);
			assResourceInitSpecialMapper.delete(delMap);
			assFileInitSpecialMapper.delete(delMap);
			assPhotoInitSpecialMapper.delete(delMap);
			assAccessoryInitSpecialMapper.delete(delMap);
			assPayStageInitSpecialMapper.delete(delMap);
			assDepreAccInitSpecialMapper.delete(delMap);
			assDepreManageInitSpecialMapper.delete(delMap);

			assCardInitSpecialMapper.delete(delMap);
			int state = assCardInitSpecialMapper.add(entityMap);

			entityMap.put("source_id", 1);
			assResourceInitSpecialMapper.add(entityMap);

			if (entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("")
					&& entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")) {
				entityMap.put("area", 1);
				assShareDeptInitSpecialMapper.add(entityMap);
			}

			String basePath = "ass";
			String group_id = entityMap.get("group_id").toString();
			String hos_id = entityMap.get("hos_id").toString();
			String copy_code = entityMap.get("copy_code").toString();
			String assTwoPath = "assbartwo";
			String assOnePath = "assbarone";
			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/02/";// 资产性质为01
			String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assOnePath + "/02/";// 资产性质为01

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

			assBaseService.updateAssBillNoMaxNo("ASS_CARD_SPECIAL");
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"ass_card_no\":\"" + entityMap.get("ass_card_no") + "\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量添加资产卡片维护_专用设备<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assCardInitSpecialMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新资产卡片维护_专用设备<BR>
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
			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/02/";// 资产性质为01
			String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assOnePath + "/02/";// 资产性质为01

			AssCardInitSpecial assCardInitSpecial = assCardInitSpecialMapper.queryByCode(entityMap);
			if (assCardInitSpecial.getBar_url() != null && !assCardInitSpecial.getBar_url().equals("")) {
				String file_name = assCardInitSpecial.getBar_url().substring(
						assCardInitSpecial.getBar_url().lastIndexOf("/") + 1, assCardInitSpecial.getBar_url().length());
				String path = assCardInitSpecial.getBar_url().substring(0,
						assCardInitSpecial.getBar_url().lastIndexOf("/"));
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
			int state = assCardInitSpecialMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量更新资产卡片维护_专用设备<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assCardInitSpecialMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除资产卡片维护_专用设备<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {
			List<Map<String, Object>> entityList = assCardInitSpecialMapper.queryCardByMap(entityMap);
			// 删除条码文件
			for (Map<String, Object> map : entityList) {
				AssCardInitSpecial assCardInitSpecial = assCardInitSpecialMapper.queryByCode(map);
				if (assCardInitSpecial.getBar_url() != null && !assCardInitSpecial.getBar_url().equals("")) {
					String file_name = assCardInitSpecial.getBar_url().substring(
							assCardInitSpecial.getBar_url().lastIndexOf("/") + 1,
							assCardInitSpecial.getBar_url().length());
					String path = assCardInitSpecial.getBar_url().substring(0,
							assCardInitSpecial.getBar_url().lastIndexOf("/"));
					FtpUtil.deleteFile(path, file_name);
				}
			}

			// 删除资产文档
			for (Map<String, Object> fileMap : entityList) {
				List<AssFileInitSpecial> assFileInitSpecialList = (List<AssFileInitSpecial>) assFileInitSpecialMapper
						.queryExists(fileMap);
				if (assFileInitSpecialList.size() > 0 && assFileInitSpecialList != null) {
					for (AssFileInitSpecial assFileInitSpecial : assFileInitSpecialList) {
						if (assFileInitSpecial.getFile_url() != null && !assFileInitSpecial.getFile_url().equals("")) {
							String file_name = assFileInitSpecial.getFile_url().substring(
									assFileInitSpecial.getFile_url().lastIndexOf("/") + 1,
									assFileInitSpecial.getFile_url().length());
							String path = assFileInitSpecial.getFile_url().substring(0,
									assFileInitSpecial.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0, path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assFileInitSpecial.getAss_card_no(), path);
						}
					}
				}
			}

			// 删除资产照片
			for (Map<String, Object> photoMap : entityList) {
				List<AssPhotoInitSpecial> assPhotoInitSpecialList = (List<AssPhotoInitSpecial>) assPhotoInitSpecialMapper
						.queryExists(photoMap);
				if (assPhotoInitSpecialList.size() > 0 && assPhotoInitSpecialList != null) {
					for (AssPhotoInitSpecial assPhotoInitSpecial : assPhotoInitSpecialList) {
						if (assPhotoInitSpecial.getFile_url() != null
								&& !assPhotoInitSpecial.getFile_url().equals("")) {
							String file_name = assPhotoInitSpecial.getFile_url().substring(
									assPhotoInitSpecial.getFile_url().lastIndexOf("/") + 1,
									assPhotoInitSpecial.getFile_url().length());
							String path = assPhotoInitSpecial.getFile_url().substring(0,
									assPhotoInitSpecial.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0, path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assPhotoInitSpecial.getAss_card_no(), path);
						}
					}
				}
			}

			int state = assCardInitSpecialMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量删除资产卡片维护_专用设备<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			// 删除条码文件
			for (Map<String, Object> map : entityList) {
				AssCardInitSpecial assCardInitSpecial = assCardInitSpecialMapper.queryByCode(map);
				if (assCardInitSpecial.getBar_url() != null && !assCardInitSpecial.getBar_url().equals("")) {
					String file_name = assCardInitSpecial.getBar_url().substring(
							assCardInitSpecial.getBar_url().lastIndexOf("/") + 1,
							assCardInitSpecial.getBar_url().length());
					String path = assCardInitSpecial.getBar_url().substring(0,
							assCardInitSpecial.getBar_url().lastIndexOf("/"));
					FtpUtil.deleteFile(path, file_name);
				}
			}

			// 删除资产文档
			for (Map<String, Object> fileMap : entityList) {
				List<AssFileInitSpecial> assFileInitSpecialList = (List<AssFileInitSpecial>) assFileInitSpecialMapper
						.queryExists(fileMap);
				if (assFileInitSpecialList.size() > 0 && assFileInitSpecialList != null) {
					for (AssFileInitSpecial assFileInitSpecial : assFileInitSpecialList) {
						if (assFileInitSpecial.getFile_url() != null && !assFileInitSpecial.getFile_url().equals("")) {
							String file_name = assFileInitSpecial.getFile_url().substring(
									assFileInitSpecial.getFile_url().lastIndexOf("/") + 1,
									assFileInitSpecial.getFile_url().length());
							String path = assFileInitSpecial.getFile_url().substring(0,
									assFileInitSpecial.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0, path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assFileInitSpecial.getAss_card_no(), path);
						}
					}
				}
			}

			// 删除资产照片
			for (Map<String, Object> photoMap : entityList) {
				List<AssPhotoInitSpecial> assPhotoInitSpecialList = (List<AssPhotoInitSpecial>) assPhotoInitSpecialMapper
						.queryExists(photoMap);
				if (assPhotoInitSpecialList.size() > 0 && assPhotoInitSpecialList != null) {
					for (AssPhotoInitSpecial assPhotoInitSpecial : assPhotoInitSpecialList) {
						if (assPhotoInitSpecial.getFile_url() != null
								&& !assPhotoInitSpecial.getFile_url().equals("")) {
							String file_name = assPhotoInitSpecial.getFile_url().substring(
									assPhotoInitSpecial.getFile_url().lastIndexOf("/") + 1,
									assPhotoInitSpecial.getFile_url().length());
							String path = assPhotoInitSpecial.getFile_url().substring(0,
									assPhotoInitSpecial.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0, path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assPhotoInitSpecial.getAss_card_no(), path);
						}
					}
				}
			}

			assShareDeptInitSpecialMapper.deleteBatch(entityList);
			assResourceInitSpecialMapper.deleteBatch(entityList);
			assFileInitSpecialMapper.deleteBatch(entityList);
			assPhotoInitSpecialMapper.deleteBatch(entityList);
			assAccessoryInitSpecialMapper.deleteBatch(entityList);
			assPayStageInitSpecialMapper.deleteBatch(entityList);
			assDepreAccInitSpecialMapper.deleteBatch(entityList);
			assDepreManageInitSpecialMapper.deleteBatch(entityList);
			assCardInitSpecialMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 添加资产卡片维护_专用设备<BR>
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
		// 判断是否存在对象资产卡片维护_专用设备
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<AssCardInitSpecial> list = (List<AssCardInitSpecial>) assCardInitSpecialMapper.queryExists(mapVo);

		if (list.size() > 0) {

			int state = assCardInitSpecialMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = assCardInitSpecialMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集资产卡片维护_专用设备<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssCardInitSpecial> list = (List<AssCardInitSpecial>) assCardInitSpecialMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssCardInitSpecial> list = (List<AssCardInitSpecial>) assCardInitSpecialMapper.query(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象资产卡片维护_专用设备<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assCardInitSpecialMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取资产卡片维护_专用设备<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssCardSpecial
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assCardInitSpecialMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取资产卡片维护_专用设备<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssCardSpecial>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assCardInitSpecialMapper.queryExists(entityMap);
	}

	@Override
	public List<Map<String, Object>> queryPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("user_id", SessionManager.getUserId());
		List<Map<String,Object>> list = (List<Map<String,Object>>) assCardInitSpecialMapper.queryPrint(entityMap);
		return list;
	}

}
