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
import com.chd.hrp.ass.dao.accessory.AssAccessoryInitGeneralMapper;
import com.chd.hrp.ass.dao.card.AssCardInitGeneralMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccInitGeneralMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageInitGeneralMapper;
import com.chd.hrp.ass.dao.file.AssFileInitGeneralMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInitGeneralMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoInitGeneralMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInitGeneralMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInitGeneralMapper;
import com.chd.hrp.ass.entity.card.AssCardInitGeneral;
import com.chd.hrp.ass.entity.file.AssFileInitGeneral;
import com.chd.hrp.ass.entity.photo.AssPhotoInitGeneral;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.card.AssCardInitGeneralService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 资产卡片维护_一般设备
 * @Table: ASS_CARD_GENERAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("assCardInitGeneralService")
public class AssCardInitGeneralServiceImpl implements AssCardInitGeneralService {

	private static Logger logger = Logger.getLogger(AssCardInitGeneralServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assCardInitGeneralMapper")
	private final AssCardInitGeneralMapper assCardInitGeneralMapper = null;

	@Resource(name = "assResourceInitGeneralMapper")
	private final AssResourceInitGeneralMapper assResourceInitGeneralMapper = null;

	@Resource(name = "assShareDeptInitGeneralMapper")
	private final AssShareDeptInitGeneralMapper assShareDeptInitGeneralMapper = null;

	@Resource(name = "assFileInitGeneralMapper")
	private final AssFileInitGeneralMapper assFileInitGeneralMapper = null;

	@Resource(name = "assPhotoInitGeneralMapper")
	private final AssPhotoInitGeneralMapper assPhotoInitGeneralMapper = null;

	@Resource(name = "assAccessoryInitGeneralMapper")
	private final AssAccessoryInitGeneralMapper assAccessoryInitGeneralMapper = null;

	@Resource(name = "assDepreAccInitGeneralMapper")
	private final AssDepreAccInitGeneralMapper assDepreAccInitGeneralMapper = null;

	@Resource(name = "assDepreManageInitGeneralMapper")
	private final AssDepreManageInitGeneralMapper assDepreManageInitGeneralMapper = null;

	@Resource(name = "assPayStageInitGeneralMapper")
	private final AssPayStageInitGeneralMapper assPayStageInitGeneralMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 添加资产卡片维护_一般设备<BR>
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

			assShareDeptInitGeneralMapper.delete(delMap);
			assResourceInitGeneralMapper.delete(delMap);
			assFileInitGeneralMapper.delete(delMap);
			assPhotoInitGeneralMapper.delete(delMap);
			assAccessoryInitGeneralMapper.delete(delMap);
			assPayStageInitGeneralMapper.delete(delMap);
			assDepreAccInitGeneralMapper.delete(delMap);
			assDepreManageInitGeneralMapper.delete(delMap);

			assCardInitGeneralMapper.delete(delMap);
			int state = assCardInitGeneralMapper.add(entityMap);

			entityMap.put("source_id", 1);
			assResourceInitGeneralMapper.add(entityMap);

			if (entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("")
					&& entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")) {
				entityMap.put("area", 1);
				assShareDeptInitGeneralMapper.add(entityMap);
			}

			String basePath = "ass";
			String group_id = entityMap.get("group_id").toString();
			String hos_id = entityMap.get("hos_id").toString();
			String copy_code = entityMap.get("copy_code").toString();
			String assTwoPath = "assbartwo";
			String assOnePath = "assbarone";
			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/03/";// 资产性质为01
			String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assOnePath + "/03/";// 资产性质为01

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

			assBaseService.updateAssBillNoMaxNo("ASS_CARD_INIT_GENERAL");
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"ass_card_no\":\"" + entityMap.get("ass_card_no") + "\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	/**
	 * @Description 批量添加资产卡片维护_一般设备<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assCardInitGeneralMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新资产卡片维护_一般设备<BR>
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
			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/03/";// 资产性质为01
			String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assOnePath + "/03/";// 资产性质为01

			AssCardInitGeneral assCardInitGeneral = assCardInitGeneralMapper.queryByCode(entityMap);
			if (assCardInitGeneral.getBar_url() != null && !assCardInitGeneral.getBar_url().equals("")) {
				String file_name = assCardInitGeneral.getBar_url().substring(
						assCardInitGeneral.getBar_url().lastIndexOf("/") + 1, assCardInitGeneral.getBar_url().length());
				String path = assCardInitGeneral.getBar_url().substring(0,
						assCardInitGeneral.getBar_url().lastIndexOf("/"));
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
			int state = assCardInitGeneralMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量更新资产卡片维护_一般设备<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assCardInitGeneralMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除资产卡片维护_一般设备<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {
			List<Map<String, Object>> entityList = assCardInitGeneralMapper.queryCardByMap(entityMap);
			// 删除条码文件
			for (Map<String, Object> map : entityList) {
				AssCardInitGeneral assCardInitGeneral = assCardInitGeneralMapper.queryByCode(map);
				if (assCardInitGeneral.getBar_url() != null && !assCardInitGeneral.getBar_url().equals("")) {
					String file_name = assCardInitGeneral.getBar_url().substring(
							assCardInitGeneral.getBar_url().lastIndexOf("/") + 1,
							assCardInitGeneral.getBar_url().length());
					String path = assCardInitGeneral.getBar_url().substring(0,
							assCardInitGeneral.getBar_url().lastIndexOf("/"));
					FtpUtil.deleteFile(path, file_name);
				}
			}

			// 删除资产文档
			for (Map<String, Object> fileMap : entityList) {
				List<AssFileInitGeneral> assFileInitGeneralList = (List<AssFileInitGeneral>) assFileInitGeneralMapper
						.queryExists(fileMap);
				if (assFileInitGeneralList.size() > 0 && assFileInitGeneralList != null) {
					for (AssFileInitGeneral assFileInitGeneral : assFileInitGeneralList) {
						if (assFileInitGeneral.getFile_url() != null && !assFileInitGeneral.getFile_url().equals("")) {
							String file_name = assFileInitGeneral.getFile_url().substring(
									assFileInitGeneral.getFile_url().lastIndexOf("/") + 1,
									assFileInitGeneral.getFile_url().length());
							String path = assFileInitGeneral.getFile_url().substring(0,
									assFileInitGeneral.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0, path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assFileInitGeneral.getAss_card_no(), path);
						}
					}
				}
			}

			// 删除资产照片
			for (Map<String, Object> photoMap : entityList) {
				List<AssPhotoInitGeneral> assPhotoInitGeneralList = (List<AssPhotoInitGeneral>) assPhotoInitGeneralMapper
						.queryExists(photoMap);
				if (assPhotoInitGeneralList.size() > 0 && assPhotoInitGeneralList != null) {
					for (AssPhotoInitGeneral assPhotoInitGeneral : assPhotoInitGeneralList) {
						if (assPhotoInitGeneral.getFile_url() != null
								&& !assPhotoInitGeneral.getFile_url().equals("")) {
							String file_name = assPhotoInitGeneral.getFile_url().substring(
									assPhotoInitGeneral.getFile_url().lastIndexOf("/") + 1,
									assPhotoInitGeneral.getFile_url().length());
							String path = assPhotoInitGeneral.getFile_url().substring(0,
									assPhotoInitGeneral.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0, path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assPhotoInitGeneral.getAss_card_no(), path);
						}
					}
				}
			}

			int state = assCardInitGeneralMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量删除资产卡片维护_一般设备<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			// 删除条码文件
			for (Map<String, Object> map : entityList) {
				AssCardInitGeneral assCardInitGeneral = assCardInitGeneralMapper.queryByCode(map);
				if (assCardInitGeneral.getBar_url() != null && !assCardInitGeneral.getBar_url().equals("")) {
					String file_name = assCardInitGeneral.getBar_url().substring(
							assCardInitGeneral.getBar_url().lastIndexOf("/") + 1,
							assCardInitGeneral.getBar_url().length());
					String path = assCardInitGeneral.getBar_url().substring(0,
							assCardInitGeneral.getBar_url().lastIndexOf("/"));
					FtpUtil.deleteFile(path, file_name);
				}
			}

			// 删除资产文档
			for (Map<String, Object> fileMap : entityList) {
				List<AssFileInitGeneral> assFileInitGeneralList = (List<AssFileInitGeneral>) assFileInitGeneralMapper
						.queryExists(fileMap);
				if (assFileInitGeneralList.size() > 0 && assFileInitGeneralList != null) {
					for (AssFileInitGeneral assFileInitGeneral : assFileInitGeneralList) {
						if (assFileInitGeneral.getFile_url() != null && !assFileInitGeneral.getFile_url().equals("")) {
							String file_name = assFileInitGeneral.getFile_url().substring(
									assFileInitGeneral.getFile_url().lastIndexOf("/") + 1,
									assFileInitGeneral.getFile_url().length());
							String path = assFileInitGeneral.getFile_url().substring(0,
									assFileInitGeneral.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0, path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assFileInitGeneral.getAss_card_no(), path);
						}
					}
				}
			}

			// 删除资产照片
			for (Map<String, Object> photoMap : entityList) {
				List<AssPhotoInitGeneral> assPhotoInitGeneralList = (List<AssPhotoInitGeneral>) assPhotoInitGeneralMapper
						.queryExists(photoMap);
				if (assPhotoInitGeneralList.size() > 0 && assPhotoInitGeneralList != null) {
					for (AssPhotoInitGeneral assPhotoInitGeneral : assPhotoInitGeneralList) {
						if (assPhotoInitGeneral.getFile_url() != null
								&& !assPhotoInitGeneral.getFile_url().equals("")) {
							String file_name = assPhotoInitGeneral.getFile_url().substring(
									assPhotoInitGeneral.getFile_url().lastIndexOf("/") + 1,
									assPhotoInitGeneral.getFile_url().length());
							String path = assPhotoInitGeneral.getFile_url().substring(0,
									assPhotoInitGeneral.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0, path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assPhotoInitGeneral.getAss_card_no(), path);
						}
					}
				}
			}

			assShareDeptInitGeneralMapper.deleteBatch(entityList);
			assResourceInitGeneralMapper.deleteBatch(entityList);
			assFileInitGeneralMapper.deleteBatch(entityList);
			assPhotoInitGeneralMapper.deleteBatch(entityList);
			assAccessoryInitGeneralMapper.deleteBatch(entityList);
			assPayStageInitGeneralMapper.deleteBatch(entityList);
			assDepreAccInitGeneralMapper.deleteBatch(entityList);
			assDepreManageInitGeneralMapper.deleteBatch(entityList);
			assCardInitGeneralMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 添加资产卡片维护_一般设备<BR>
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
		// 判断是否存在对象资产卡片维护_一般设备
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<AssCardInitGeneral> list = (List<AssCardInitGeneral>) assCardInitGeneralMapper.queryExists(mapVo);

		if (list.size() > 0) {

			int state = assCardInitGeneralMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = assCardInitGeneralMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集资产卡片维护_一般设备<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssCardInitGeneral> list = (List<AssCardInitGeneral>) assCardInitGeneralMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssCardInitGeneral> list = (List<AssCardInitGeneral>) assCardInitGeneralMapper.query(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象资产卡片维护_一般设备<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assCardInitGeneralMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取资产卡片维护_一般设备<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssCardGeneral
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assCardInitGeneralMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取资产卡片维护_一般设备<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssCardGeneral>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assCardInitGeneralMapper.queryExists(entityMap);
	}

	@Override
	public List<Map<String,Object>> queryPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		    entityMap.put("user_id", SessionManager.getUserId());
			List<Map<String,Object>> list = (List<Map<String,Object>>) assCardInitGeneralMapper.queryPrint(entityMap);
			return list;
	}

}
