/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.serviceImpl.requrie.dept;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.requrie.MatReqStoreRelaMapper;
import com.chd.hrp.mat.dao.requrie.MatRequireDetailMapper;
import com.chd.hrp.mat.dao.requrie.MatRequireMainMapper;
import com.chd.hrp.mat.dao.requrie.dept.MatRequireConfirmMapper;
import com.chd.hrp.mat.entity.MatRequireDetail;
import com.chd.hrp.mat.entity.MatRequireMain;
import com.chd.hrp.mat.service.requrie.dept.MatRequireConfirmService;
import com.github.pagehelper.PageInfo;

/**
 * 科室购置计划审核
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Transactional
@Service("matRequireConfirmService")
public class MatRequireConfirmServiceImpl implements MatRequireConfirmService {

	private static Logger logger = Logger.getLogger(MatRequireConfirmServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "matRequireMainMapper")
	private final MatRequireMainMapper matRequireMainMapper = null;

	@Resource(name = "matRequireDetailMapper")
	private final MatRequireDetailMapper matRequireDetailMapper = null;
	
	@Resource(name = "matRequireConfirmMapper")
	private final MatRequireConfirmMapper matRequireConfirmMapper = null;
	
	@Resource(name = "matReqStoreRelaMapper")
	private final MatReqStoreRelaMapper matReqStoreRelaMapper = null;

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		try {
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
		}
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		try {
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 update\"}";
		}
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatch\"}";
		}
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {	
		try {
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatRequireMain\"}";
		}
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityList)	throws DataAccessException {
		try {
			matRequireMainMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		
		// 获取对象MAT_REQUIRE_MAIN
		MatRequireMain matRequireMain = queryByCode(entityMap);
		if (matRequireMain != null) {
			matRequireMainMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		try {
			matRequireMainMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatRequireMain\"}";
		}

	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return (T) matRequireMainMapper.queryByCode(entityMap);
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 审核页面  查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryConfirm(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<?> list = (List<?>) matRequireConfirmMapper.query(entityMap);
			return ChdJson.toJson(list);

		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			List<?> list = (List<?>) matRequireConfirmMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 审核页面  主表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return matRequireMainMapper.queryByCode(entityMap);
	}
	
	/**
	 * 审核页面  明细表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryConfirmDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");		
		if (sysPage.getTotal()==-1){			
			List<?> list = (List<?>) matRequireDetailMapper.queryDetailByCode(entityMap);			
			return ChdJson.toJson(list);			
		}else{			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());			
			List<?> list = (List<?>) matRequireDetailMapper.queryDetailByCode(entityMap, rowBounds);			
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJson(list, page.getTotal());			
		}
	}

	
	/**
	 * 审核页面  审核
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String updateAudit(List<Map<String, Object>> detailList) throws DataAccessException {
		try {
			matRequireConfirmMapper.updateAudit(detailList);
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"审核失败 数据库异常 请联系管理员! 错误编码  updateAudit\"}";

		}
	}
	/**
	 * 审核页面  取消审核
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String updateAuditCancle(List<Map<String, Object>> detailList) throws DataAccessException {
		try {
			//判断是否已生成仓库需求计划
			int is_exists = matReqStoreRelaMapper.existsByDeptList(detailList);
			if(is_exists > 0){
				return "{\"error\":\"存在已生成仓库需求计划的单据，不能取消提交.\",\"state\":\"false\"}";
			}
			matRequireConfirmMapper.updateAuditCancle(detailList);
			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"销审失败 数据库异常 请联系管理员! 错误编码  updateAuditCancle\"}";

		}
	}
	
	/**
	 * 退回科室
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String updateReturn(List<Map<String, Object>> detailList) throws DataAccessException {
		try {
			matRequireConfirmMapper.updateReturn(detailList);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 错误编码  updateReturn\"}";

		}
	}	
}
