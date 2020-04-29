/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.hip.serviceImpl;

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
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hip.dao.HipDeptRefCostMapper;
import com.chd.hrp.hip.dao.HrpHipSelectMapper;
import com.chd.hrp.hip.entity.HipDeptRef;
import com.chd.hrp.hip.service.HipDeptRefCostService;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hipDeptRefCostService")
public class HipDeptRefCostServiceImpl implements HipDeptRefCostService {

	private static Logger logger = Logger.getLogger(HipDeptRefCostServiceImpl.class);

	@Resource(name = "hipDeptRefCostMapper")
	private final HipDeptRefCostMapper hipDeptRefCostMapper = null;

	@Resource(name = "hrpHipSelectMapper")
	private final HrpHipSelectMapper hrpHipSelectMapper = null;
	
	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;

	@Override
	public String queryHipDeptRef(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		// 查询是否存在同名的dblink，如果存在下拉列表优先从dblink中读取数据
		int is_exists = hrpHipSelectMapper.existsDblink(entityMap);

		if (is_exists > 0) {
			entityMap.put("hip_view_code", "HIP_DEPT_DICT");

			String view_name = hrpHipSelectMapper.queryDblinkViewName(entityMap);

			entityMap.put("view_name", view_name);
			entityMap.put("col_code", "dept_code");
			entityMap.put("col_name", "dept_name");
		}

		if (sysPage.getTotal() == -1) {

			List<HipDeptRef> list = hipDeptRefCostMapper.queryHipDeptRef(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HipDeptRef> list = hipDeptRefCostMapper.queryHipDeptRef(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HipDeptRef queryHipDeptRefByCode(Map<String, Object> entityMap) throws DataAccessException {

		// 查询是否存在同名的dblink，如果存在下拉列表优先从dblink中读取数据
		int is_exists = hrpHipSelectMapper.existsDblink(entityMap);

		if (is_exists > 0) {
			entityMap.put("hip_view_code", "HIP_DEPT_DICT");

			String view_name = hrpHipSelectMapper.queryDblinkViewName(entityMap);

			entityMap.put("view_name", view_name);
			entityMap.put("col_code", "dept_code");
			entityMap.put("col_name", "dept_name");
		}

		return hipDeptRefCostMapper.queryHipDeptRefByCode(entityMap);
	}

	@Override
	public String addHipDeptRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String saveFlag = (String) entityMap.get("saveFlag");

			if ("1".equals(saveFlag)) {

				String old_data = (String) entityMap.get("old_data");

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", entityMap.get("group_id"));

				map.put("hos_id", entityMap.get("hos_id"));

				String[] data_spl = old_data.split("@");

				map.put("ds_code", data_spl[0]);

				map.put("hip_dept_code", data_spl[1]);

				map.put("hrp_dept_code", data_spl[2]);

				map.put("doc_type", data_spl[3]);

				hipDeptRefCostMapper.deleteHipDeptRef(map);

			}

			HipDeptRef apt = hipDeptRefCostMapper.queryHipDeptRefByCode(entityMap);

			if (apt != null) {

				return "{\"msg\":\"编码重复 请修改编码.\",\"state\":\"error\"}";

			}

			hipDeptRefCostMapper.addHipDeptRef(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHipDeptRef\"}";

		}
	}

	@Override
	public String delHipDeptRef(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hipDeptRefCostMapper.deleteHipDeptRef(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHipDeptRef\"}";

		}
	}

	@Override
	public String addBatchHipDeptRef(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hipDeptRefCostMapper.addBatchHipDeptRef(entityList);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addBatchHipDeptRef\"}";

		}
	}

	@Override
	public String deleteBatchHipDeptRef(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			hipDeptRefCostMapper.deleteBatchHipDeptRef(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchHipDeptRef\"}";

		}

	}

	// 导入
	@Override
	public String readFiles(Map<String, Object> map) throws DataAccessException {
		String dataJson = null;
		Map<String, Object> is_naturs_map = new HashMap<String, Object>();
		is_naturs_map.put("门诊", "0");
		is_naturs_map.put("住院", "1");
		is_naturs_map.put("0", "0");
		is_naturs_map.put("1", "1");

		try {

			List<Map<String, List<String>>> list = ReadFiles.readFiles(map);
			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}
			for (Map<String, List<String>> map2 : list) {

				if (map2.get("ds_code").get(1) == null || map2.get("ds_code").get(1) == null || map2.get("ds_code").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("ds_code").get(0) + "，数据源为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("ds_code").get(0)
					        + "\"}";
				} else if (map2.get("hip_dept_code").get(1) == null || map2.get("hip_dept_code").get(1) == null
				        || map2.get("hip_dept_code").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("hip_dept_code").get(0) + "，HIS科室编码为空！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("hip_dept_code").get(0) + "\"}";
				} else if (map2.get("hip_dept_name").get(1) == null || map2.get("hip_dept_name").get(1) == null
				        || map2.get("hip_dept_name").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("hrp_dept_code").get(0) + "，HIS科室名称为空！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("hip_dept_name").get(0) + "\"}";
				} else if (map2.get("hrp_dept_code").get(1) == null || map2.get("hrp_dept_code").get(1) == null
				        || map2.get("hrp_dept_code").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("hip_dept_code").get(0) + "，HRP科室编码为空！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("hrp_dept_code").get(0) + "\"}";
				} else if (map2.get("hrp_dept_name").get(1) == null || map2.get("hrp_dept_name").get(1) == null
				        || map2.get("hrp_dept_name").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("hrp_dept_name").get(0) + "，HRP科室名称为空！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("hrp_dept_name").get(0) + "\"}";
				} else if (map2.get("doc_type").get(1) == null || map2.get("doc_type").get(1) == null || map2.get("doc_type").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("doc_type").get(0) + "，科室性质为空！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("doc_type").get(0) + "\"}";
				}

				if(is_naturs_map.get(map2.get("doc_type").get(1))==null){
					return "{\"msg\":\"" + map2.get("doc_type").get(0) + "，科室性质不存在！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("doc_type").get(0) + "\"}";
				}
				
				
				Map<String, Object> mapVo1 = new HashMap<String, Object>();

				mapVo1.put("group_id", SessionManager.getGroupId());

				mapVo1.put("hos_id", SessionManager.getHosId());

				mapVo1.put("copy_code", SessionManager.getCopyCode());
				mapVo1.put("ds_code", map2.get("ds_code").get(1));
				mapVo1.put("hip_dept_code", map2.get("hip_dept_code").get(1));
				mapVo1.put("hrp_dept_code", map2.get("hrp_dept_code").get(1));
				mapVo1.put("doc_type", is_naturs_map.get(map2.get("doc_type").get(1)));

				// 判断唯一性 编码

				Map<String, Object> map_code = new HashMap<String, Object>();
				map_code.put("group_id", mapVo1.get("group_id"));
				map_code.put("hos_id", mapVo1.get("hos_id"));
				map_code.put("copy_code", mapVo1.get("copy_code"));
				map_code.put("hip_dept_code", mapVo1.get("hip_dept_code"));
				map_code.put("hrp_dept_code", mapVo1.get("hrp_dept_code"));

				HipDeptRef data_exc_extis_code = hipDeptRefCostMapper.queryHipDeptRefByCode(map_code);

				if (data_exc_extis_code != null) {
					return "{\"warn\":\"" + map2.get("hip_dept_code").get(0) + ",编码已经存在！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("hip_dept_code").get(0) + "\"}";

				}
				
				Map<String, Object> map_dept_code = new HashMap<String, Object>();
				map_dept_code.put("group_id", mapVo1.get("group_id"));
				map_dept_code.put("hos_id", mapVo1.get("hos_id"));
				map_dept_code.put("dept_code", map2.get("hrp_dept_code").get(1));
				map_dept_code.put("is_stop", "0");
				
				DeptDict  dd_code=	deptDictMapper.queryDeptDictByDeptCode(map_dept_code);
				
				
				if(dd_code ==null){
					return "{\"warn\":\"" + map2.get("hrp_dept_code").get(0) + ",编码不存在！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("hrp_dept_code").get(0) + "\"}";
				}
				
				Map<String, Object> map_dept_name = new HashMap<String, Object>();
				map_dept_name.put("group_id", mapVo1.get("group_id"));
				map_dept_name.put("hos_id", mapVo1.get("hos_id"));
				map_dept_name.put("dept_name", map2.get("hrp_dept_name").get(1));
				map_dept_name.put("is_stop", "0");
				
				DeptDict  dd_name=	deptDictMapper.queryDeptDictByDeptCode(map_dept_name);
				if(dd_name ==null){
					return "{\"warn\":\"" + map2.get("hrp_dept_name").get(0) + ",名称不存在！\",\"state\":\"false\",\"row_cell\":\""
							+ map2.get("hrp_dept_name").get(0) + "\"}";
				}
				dataJson = addHipDeptRef(mapVo1);
			}

			return dataJson;

		}
		catch (Exception e) {

			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());

		}
	}
}
