package com.chd.hrp.hpm.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiEmpSchemeMapper;
import com.chd.hrp.hpm.entity.AphiEmpScheme;
import com.chd.hrp.hpm.service.AphiEmpSchemeService;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiEmpSchemeService")
public class AphiEmpSchemeServiceImpl implements AphiEmpSchemeService {

	private static Logger logger = Logger.getLogger(AphiEmpSchemeServiceImpl.class);

	@Resource(name = "aphiEmpSchemeMapper")
	private final AphiEmpSchemeMapper aphiEmpSchemeMapper = null;

	/**
	 * 
	 */
	@Override
	public String addEmpScheme(Map<String, Object> entityMap) throws DataAccessException {

		try {

			aphiEmpSchemeMapper.addEmpScheme(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";
		}

	}

	/**
	 * 
	 */
	@Override
	public String queryEmpScheme(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		return JsonListBeanUtil.listToJson(aphiEmpSchemeMapper.queryEmpScheme(entityMap, rowBounds), sysPage.getTotal());
	} 

	/**
	 * 
	 */
	@Override
	public AphiEmpScheme queryEmpSchemeByCode(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		return aphiEmpSchemeMapper.queryEmpSchemeByCode(entityMap);
	}

	/**
	 * 
	 */

	public String deleteEmpScheme(List<Map<String, Object>> entityList) throws DataAccessException {
		String request = "";
		for (Map<String, Object> entityMap : entityList) {
			int state = aphiEmpSchemeMapper.deleteEmpScheme(entityMap);
			if (state > 0) {
				request = "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} else {
				request = "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
			}
		}

		return request;
	}

	@Override
	public String deleteEmpScheme(Map<String, Object> entityMap) throws DataAccessException {
		String request = "";
		int state = aphiEmpSchemeMapper.deleteEmpScheme(entityMap);
		if (state > 0) {
			request = "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} else {
			request = "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
		}
		return request;
	}

	/**
	 * 
	 */
	@Override
	public String deleteEmpSchemeById(String[] ids) throws DataAccessException {
		String request = "";
		if (ids != null && ids.length > 0) {
			for (String s : ids) {
				aphiEmpSchemeMapper.deleteEmpSchemeById(s);
			}
			request = "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} else {
			request = "{\"error\":\"没有要删除的数据.\",\"state\":\"false\"}";
		}
		return request;

	}

	/**
	 * 
	 */
	@Override
	public String updateEmpScheme(Map<String, Object> entityMap) throws DataAccessException {
		int state = aphiEmpSchemeMapper.updateEmpScheme(entityMap);
		if (state > 0) {
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";
		}
	}

	@Override
	public String fastEmpScheme(Map<String, Object> entityMap) throws DataAccessException {

		try {
			// 存储要填充的数据 List
			List<Map<String, Object>> initDataList = new ArrayList<Map<String, Object>>();

			// 填充状态
			String rbtnl = (String) entityMap.get("rbtnl");

			if ("all".equals(rbtnl)) {// 填充全部

				if (entityMap.get("user_id") == null) {
					entityMap.put("user_id", SessionManager.getUserId());
				}
				List<AphiEmpScheme> allEmpScheme = aphiEmpSchemeMapper.queryEmpSchemeFast(entityMap);
				for (int i = 0; i < allEmpScheme.size(); i++) {
					AphiEmpScheme empScheme = allEmpScheme.get(i);
					entityMap.put("dept_id", empScheme.getDept_id());
					entityMap.put("dept_no", empScheme.getDept_no());
					entityMap.put("duty_code", empScheme.getDuty_code());
					entityMap.put("formula_code", entityMap.get("formula_code"));
					initDataList.add(entityMap);
				}

			} else {// 填充选择
				String codes = (String) entityMap.get("checkIds");
				String[] code_array = codes.split(",");
				for (int i = 0; i < code_array.length; i++) {
					String[] code_arrays = code_array[i].split(";");
					entityMap.put("dept_id", code_arrays[0]);
					entityMap.put("dept_no", code_arrays[1]);
					entityMap.put("duty_code", code_arrays[2]);
					entityMap.put("formula_code", entityMap.get("formula_code"));
					initDataList.add(entityMap);
				}

			}
			
			if(initDataList != null && !"".equals(initDataList)){
				return "{\"error\":\"请先查询数据.\",\"state\":\"false\"}";
			}
			
			aphiEmpSchemeMapper.fastEmpScheme(initDataList);
			return "{\"msg\":\"快速填充成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}

	@Override
	public String deleteBatchEmpScheme(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			aphiEmpSchemeMapper.deleteBatchEmpScheme(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("操作失败 ");
		}
	}

	@Override
	public List<Map<String, Object>> queryEmpSchemePrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		if(MyConfig.getSysPara("09001") == null){
			entityMap.put("para_value", 0);
		}else{
			entityMap.put("para_value", MyConfig.getSysPara("09001"));
		}
		
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		List<Map<String, Object>> list = aphiEmpSchemeMapper.queryEmpSchemePrint(entityMap);
		
		return list;
	}
}
