/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.serviceImpl.storage.checkMobile;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.quartz.QuartzManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.storage.checkMobile.MatCheckMobileMainMapper;
import com.chd.hrp.mat.entity.MatCheckMobile;
import com.chd.hrp.mat.service.storage.checkMobile.MatCheckMobileMainService;
import com.chd.task.ass.MatMobileInventory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table: MAT_CHECK_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("matCheckMobileMainService")
@Transactional
public class MatCheckMobileMainServiceImpl implements MatCheckMobileMainService {

	private static Logger logger = Logger.getLogger(MatCheckMobileMainServiceImpl.class);

	// 引入DAO操作
	@Autowired
	private MatCheckMobileMainMapper matCheckMobileMainMapper;
//	@Autowired
//	private MatCheckMobileJob matCheckMobileJob;

	@Override
	public String existMatCheckMobileByCreateDate(Map<String, Object> mapVo) throws DataAccessException {

		String result = "";
		try {
			int existCount = matCheckMobileMainMapper.existMatCheckMobileByCheckDate(mapVo);
			if (existCount == 0) {
				result = "{\"isExist\":\"0\"}";
			} else {
				result = "{\"isExist\":\"1\"}";
			}

		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
		return result;
	}

	@Override
	@Transactional
	public String saveMatCheckMobileMain(Map<String, Object> entityMap) throws DataAccessException {
		try {
			// 根据盘点日期和仓库id查看移动盘点
			int countCheckByCheckDate = matCheckMobileMainMapper.countCheckByCheckDate(entityMap);
			if (countCheckByCheckDate == 0) {
				return "{\"warn\":\"日期无高值材料盘点数据\"}";
			}
			
			MatMobileInventory job = new MatMobileInventory();

			// 查询盘点日期是否已经生成过 已经生成过 则 删除
			int isExist = matCheckMobileMainMapper.existMatCheckMobileByCheckDate(entityMap);
			if (isExist > 0) {
				matCheckMobileMainMapper.deleteByCreateDate(entityMap);
			}

			// 查询库存盘点
			List<Map<String, Object>> list = matCheckMobileMainMapper.queryMatCheckByCheckDate(entityMap);
			// 查询代销品盘点
			List<Map<String, Object>> affiCheckList = matCheckMobileMainMapper.queryMatAFFICheckByCheckDate(entityMap);
			// 合并
			list.addAll(affiCheckList);
			
			// 将任务单所有状态改为结束
			matCheckMobileMainMapper.updateStateToEnd(entityMap);

			// 如果盘点数量是null  则赋值为0
			list.forEach(m -> m.put("CHK_AMOUNT", null == m.get("CHK_AMOUNT") ? 0 : m.get("CHK_AMOUNT")));
			
			matCheckMobileMainMapper.addMatCheckMobileMain(list);
			
			// 生成完任务单 将db数据库文件做更新(删除之前的数据 新增数据)
			job.autoCreateCardFile(list);
			
			if (!QuartzManager.hasJob("matCheckMobileJob", "matCheckMobileGroup", "matCheckMobileTrigger", "matCheckMobileTriggerGroup")) {
				// 启动定时任务 将监视上传
				QuartzManager.addJob("matCheckMobileJob", "matCheckMobileGroup", "matCheckMobileTrigger", "matCheckMobileTriggerGroup", MatMobileInventory.class , "0 */5 * * * ?", null);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
		return "{\"msg\":\"完成\"}";
	}

	@Override
	public String queryMatCheckMobileMain(Map<String, Object> mapVo) throws DataAccessException {

		List<MatCheckMobile> matCheckMobileList;

		try {
			SysPage sysPage = (SysPage) mapVo.get("sysPage");

			if (sysPage.getTotal() == -1) {

				matCheckMobileList = matCheckMobileMainMapper.queryMatCheckMobileMain(mapVo);

				return ChdJson.toJson(matCheckMobileList);

			} else {

				PageHelper.startPage(sysPage.getPage(), sysPage.getPagesize(),
						mapVo.get("sortname") + " " + mapVo.get("sortorder"));
				matCheckMobileList = matCheckMobileMainMapper.queryMatCheckMobileMain(mapVo);

				PageInfo<MatCheckMobile> page = new PageInfo<>(matCheckMobileList);

				return ChdJson.toJson(matCheckMobileList, page.getTotal());

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"查询失败\"}");
		}
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			// 删除主表
			matCheckMobileMainMapper.deleteBatch(entityList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");
		}

		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}


	@Override
	public String check(List<Map<String, Object>> checkedList) throws DataAccessException {
		
		try {
			matCheckMobileMainMapper.check(checkedList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");
		}

		return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
	}

	@Override
	public List<Map<String, Object>> queryCheckMobilesByCheckId(Map<String, Object> entityMap) throws DataAccessException {
		return matCheckMobileMainMapper.queryCheckMobilesByCheckId(entityMap);
	}

}