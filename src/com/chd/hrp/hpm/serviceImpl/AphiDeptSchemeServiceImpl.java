package com.chd.hrp.hpm.serviceImpl;

import java.util.ArrayList;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiDeptSchemeMapper;
import com.chd.hrp.hpm.entity.AphiDeptScheme;
import com.chd.hrp.hpm.entity.AphiEmpBonusData;
import com.chd.hrp.hpm.service.AphiDeptSchemeService;
import com.github.pagehelper.PageInfo;

/**
 * alfred
 */

@Service("aphiDeptSchemeService")
public class AphiDeptSchemeServiceImpl implements AphiDeptSchemeService {

	private static Logger logger = Logger.getLogger(AphiDeptSchemeServiceImpl.class);

	@Resource(name = "aphiDeptSchemeMapper")
	private final AphiDeptSchemeMapper aphiDeptSchemeMapper = null;

	/**
	 * 
	 */
	@Override
	public String addDeptScheme(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		AphiDeptScheme ds = queryDeptSchemeByCode(entityMap);

		if (ds != null) {

			if (ds.getItem_code().equals(entityMap.get("item_code"))) {

				return "{\"error\":\"奖金项目编码：" + entityMap.get("item_code").toString() + "重复.\"}";

			}

			if (ds.getDept_id().equals(entityMap.get("dept_id"))) {

				return "{\"error\":\"医疗单元代码：" + entityMap.get("dept_id").toString() + "重复.\"}";

			}

		}

		try {

			aphiDeptSchemeMapper.addDeptScheme(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码  addDeptScheme\"}";
		}

	}

	/**
	 * 
	 */
	@Override
	public String queryDeptScheme(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiDeptScheme> list = aphiDeptSchemeMapper.queryDeptScheme(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiDeptScheme> list = aphiDeptSchemeMapper.queryDeptScheme(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}

	}

	/**
	 * 
	 */
	@Override
	public AphiDeptScheme queryDeptSchemeByCode(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		return aphiDeptSchemeMapper.queryDeptSchemeByCode(entityMap);
	}

	/**
	 * 
	 */
	@Override
	public String deleteDeptScheme(Map<String, Object> entityMap, String codes) throws DataAccessException {

		try {

			if (codes != null && !codes.equals("")) {

				String[] code_array = codes.split(",");

				for (int i = 0; i < code_array.length; i++) {

					String[] code_1 = code_array[i].split(";");

					entityMap.put("dept_id", code_1[0]);

					entityMap.put("dept_no", code_1[1]);

					entityMap.put("item_code", code_1[2]);

					aphiDeptSchemeMapper.deleteDeptScheme(entityMap);

				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("删除失败 ");
		}
	}

	/**
	 * 
	 */
	@Override
	public String updateDeptScheme(Map<String, Object> entityMap) throws DataAccessException {

		try {

			aphiDeptSchemeMapper.updateDeptScheme(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDeptScheme1\"}";
		}
	}

	@Override
	public String fastDeptScheme(Map<String, Object> entityMap) throws DataAccessException {
		try {
			if (entityMap.get("user_id") == null) {
				entityMap.put("user_id", SessionManager.getUserId());
			}
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> deleteList = new ArrayList<Map<String,Object>>();
			
			// 生成数据
			List<AphiDeptScheme> allDeptScheme = aphiDeptSchemeMapper.queryDeptSchemeFast(entityMap);
			if(allDeptScheme.size() == 0){
				return "{\"warn\":\"快速填充失败,源科室中没有数据.\",\"state\":\"false\"}";
			}
			
			
			if(entityMap.get("depts") == null || "".equals(entityMap.get("depts"))){
				return "{\"warn\":\"快速填充失败,目标科室为空.\",\"state\":\"false\"}";
			}
			
			String[] dept_ids = String.valueOf(entityMap.get("depts")).split(";");
			
			for(String obj: dept_ids){
				String[] dept = obj.split(",");
				String dept_id = dept[0];
				String dept_no = dept[1];
				
				Map<String,Object> deleteMap = new HashMap<String,Object>();
				
				deleteMap.put("group_id",entityMap.get("group_id"));
				
				deleteMap.put("hos_id",entityMap.get("hos_id"));
				
				deleteMap.put("copy_code",entityMap.get("copy_code"));
				
				deleteMap.put("dept_id",dept_id);
				
				deleteMap.put("dept_no",dept_no);
				
				deleteList.add(deleteMap);
				
				for (int i = 0; i < allDeptScheme.size(); i++) {

					AphiDeptScheme deptScheme = allDeptScheme.get(i);
					
					Map<String,Object> deptSchemeMap = new HashMap<String,Object>();
					
					deptSchemeMap.put("group_id",entityMap.get("group_id"));
					
					deptSchemeMap.put("hos_id",entityMap.get("hos_id"));
					
					deptSchemeMap.put("copy_code",entityMap.get("copy_code"));

					deptSchemeMap.put("dept_id", dept_id);
					
					deptSchemeMap.put("dept_no", dept_no);

					deptSchemeMap.put("item_code", deptScheme.getItem_code());
					
					deptSchemeMap.put("method_code", deptScheme.getMethod_code() == null ? "" : deptScheme.getMethod_code());

					deptSchemeMap.put("formula_code",deptScheme.getFormula_code() == null ? "" :deptScheme.getFormula_code());
					
					deptSchemeMap.put("fun_code", deptScheme.getFun_code() == null ? "" : deptScheme.getFun_code());

					list.add(deptSchemeMap);
				}
			}
			
			
			if(list.size() > 0){
				
				aphiDeptSchemeMapper.deleteBatchDeptScheme(deleteList);
				
				aphiDeptSchemeMapper.addBatchDeptScheme(list);
			}

			return "{\"msg\":\"快速填充成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"快速填充失败.\",\"state\":\"false\"}");

		}

	}

	@Override
	public String addBatchDeptScheme(List<Map<String, Object>> entityMap) throws DataAccessException {
		int state = aphiDeptSchemeMapper.addBatchDeptScheme(entityMap);
		if (state > 0) {
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		}
		return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
	}
}
