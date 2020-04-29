/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.serviceImpl.sysstruc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.Parameter;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.quartz.HrJob;
import com.chd.base.quartz.QuartzManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.entity.AphiFun;
import com.chd.hrp.hpm.entity.AphiFunParaMethod;
import com.chd.hrp.hpm.entity.AphiFunType;
import com.chd.hrp.hr.dao.sysstruc.HrCaltransMapper;
import com.chd.hrp.hr.dao.sysstruc.HrColStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFunMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFunTypeMapper;
import com.chd.hrp.hr.entity.base.HrColumn;
import com.chd.hrp.hr.entity.record.HosEmpKind;
import com.chd.hrp.hr.entity.sysstruc.HrCaltrans;
import com.chd.hrp.hr.entity.sysstruc.HrFun;
import com.chd.hrp.hr.entity.sysstruc.HrFunType;
import com.chd.hrp.hr.service.sysstruc.HrCaltransService;
import com.chd.task.ass.AssMobileInventory;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 计算事务
 * @Colle: HR_CALTRANS
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("hrCaltransService")
public class HrCaltransServiceImpl implements HrCaltransService {

	private static Logger logger = Logger.getLogger(HrCaltransServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "hrCaltransMapper")
	private final HrCaltransMapper hrCaltransMapper = null;
	@Resource(name = "hrFunMapper")
	private final HrFunMapper hrFunMapper = null;
	@Resource(name = "hrFunTypeMapper")
	private final HrFunTypeMapper hrFunTypeMapper = null;
	@Resource(name = "hrColStrucMapper")
	private final HrColStrucMapper hrColStrucMapper = null;

	@Override
	public String addHrCaltrans(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		HrCaltrans hrCaltrans = hrCaltransMapper.queryByTabCode(entityMap);

		if (hrCaltrans != null) {
			return "{\"error\":\"数据表或数据列重复,请重新添加.\"}";
		}
		List<Map<String, Object>> para = hrCaltransMapper.queryParaMethod(entityMap);
		String exec_func = entityMap.get("exec_func").toString();
//		int indexb = exec_func.lastIndexOf(",");
//		exec_func = exec_func.substring(0, indexb) + exec_func.substring(indexb + 1, exec_func.length());
		try {
			List<Parameter> detail = ChdJson.fromJsonAsList(Parameter.class, entityMap.get("exec_func_val").toString());
			// 替换参数
			for (Parameter map : detail) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					exec_func = exec_func.replace(entry.getKey(), entry.getValue().toString());
				}
			}
			entityMap.put("exec_func", exec_func);
			hrCaltransMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String updateHrCaltrans(Map<String, Object> entityMap) throws DataAccessException {

		try {
			if(entityMap.get("is_system").toString().equals("0")){
				String exec_func = entityMap.get("exec_func").toString();
				int indexb = exec_func.lastIndexOf(",");
				exec_func = exec_func.substring(0, indexb) +","+ exec_func.substring(indexb + 1, exec_func.length());
				List<Parameter> detail = ChdJson.fromJsonAsList(Parameter.class, entityMap.get("exec_func_val").toString());
				// 替换参数
				for (Parameter map : detail) {
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						exec_func = exec_func.replace(entry.getKey(), entry.getValue().toString());
					}
				}
				entityMap.put("exec_func", exec_func);
			}
			hrCaltransMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String deleteHrCaltrans(Map<String, Object> entityMap) throws DataAccessException {

		try {
			hrCaltransMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String deleteBatchHrCaltrans(List<HrCaltrans> listVo) throws DataAccessException {

		try {
			hrCaltransMapper.deleteBatchHrCaltrans(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryHrCaltrans(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrCaltrans> list = (List<HrCaltrans>) hrCaltransMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrCaltrans> list = (List<HrCaltrans>) hrCaltransMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public HrCaltrans queryHrCaltransByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrCaltransMapper.queryByCode(entityMap);
	}

	@Override
	public String startFuncHrCaltrans(List<Map> listVo) {
		try {
			Class t=null;
			for (Map map : listVo) {
				HrCaltrans hrCaltrans = (HrCaltrans) hrCaltransMapper.queryByCode(map);
				if (hrCaltrans != null) {
					if (hrCaltrans.getTran_state() != 1) {
						if(hrCaltrans.getFunc_type()==1)
							t=AssMobileInventory.class;
						else
							t=HrJob.class;
						QuartzManager.addJob(hrCaltrans.getExec_func(), hrCaltrans.getGroup_id(), hrCaltrans.getTran_id().toString(), hrCaltrans.getGroup_id(), t,hrCaltrans.getExec_time(),null );
						hrCaltransMapper.startFuncHrCaltrans(map);
					} else {
						continue;
					}
				}
			}
			return "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String stopFuncHrCaltrans(List<Map> listVo) {
		try {
			for (Map map : listVo) {
				HrCaltrans hrCaltrans = (HrCaltrans) hrCaltransMapper.queryByCode(map);
				if (hrCaltrans != null) {
					if (hrCaltrans.getTran_state() != 0) {
						hrCaltransMapper.stopFuncHrCaltrans(map);
						QuartzManager.removeJob(hrCaltrans.getExec_func(), hrCaltrans.getGroup_id(), hrCaltrans.getTran_id().toString(), hrCaltrans.getGroup_id());
					} else {
						continue;
					}

				}
			}
			return "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String queryCaltransFunTypeTree(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> tree = hrCaltransMapper.queryCaltransFunTypeTree(entityMap);
		return JSONArray.toJSONString(tree);
	}

	@Override
	public String queryHrFun(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrFun> list = (List<HrFun>) hrFunMapper.queryPrmFun(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrFun> list = (List<HrFun>) hrFunMapper.queryPrmFun(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public String queryPrmFunByCode(Map<String, Object> entityMap) throws DataAccessException {
		// 组件（下拉框、文本框等）
		List<Map<String, Object>> comTypeList = hrColStrucMapper.queryHrComType(entityMap);
		Map<String, Object> comTypeMap = new HashMap<String, Object>();
		if (comTypeList != null && comTypeList.size() > 0) {
			for (Map<String, Object> map : comTypeList) {
				String key = String.valueOf(map.get("com_type_code"));
				String val = String.valueOf(map.get("com_type_nature"));
				comTypeMap.put(key, val);
			}
		}
		Map<String, Object> formMap = new HashMap<String, Object>();
		formMap.put("colNum", 2);// 页面form表单列的个数
		List<Map<String, Object>> fieldItems = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> hrFunList = hrCaltransMapper.queryPrmFunByCode(entityMap);
		for (Map<String, Object> map : hrFunList) {
			Map<String, Object> fieldItem = new HashMap<String, Object>();
			String type = "text";
			if (comTypeMap != null && comTypeMap.size() > 0 && comTypeMap.get(map.get("com_type_code")) != null) {
				type = String.valueOf(comTypeMap.get(map.get("com_type_code")));
			}
			fieldItem.put("type", type);
			fieldItem.put("id", map.get("para_code"));
			fieldItem.put("name", map.get("para_name"));
			fieldItem.put("url", map.get("para_sql"));
			fieldItem.put("width", "180px");
			fieldItem.put("place", 1);
			fieldItem.put("required", true);
			// 代码表数据
			Map<String, Object> optionsMap = new HashMap<String, Object>();

			entityMap.put("sql", map.get("para_sql").toString().replace("{", "#{"));
			List<Map<String, Object>> options = hrCaltransMapper.queryHrData(entityMap);
			optionsMap.put("options", options);
			optionsMap.put("defaultValue", "none");

			fieldItem.put("OPTIONS", optionsMap);
			fieldItems.add(fieldItem);
		}
		formMap.put("fieldItems", fieldItems);

		return JSONArray.toJSONString(formMap);
	}

	@Override
	public String queryHrFunParaByDict(Map<String, Object> mapVo) throws DataAccessException {

		if (mapVo.get("") != null) {

			mapVo.put("sql", mapVo.get("").toString().replace("{", "#{"));

			return JSON.toJSONString(hrCaltransMapper.queryHrFunParaByDict(mapVo));
		} else {
			return "{\"error\":\"该函数编码没有配置部件类型\"}";

		}

	}

}
