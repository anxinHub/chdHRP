/**
 * 2015-1-9 SysUserServiceImpl.java author:pengjin
 */
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

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiDeptEmpMapMapper;
import com.chd.hrp.hpm.entity.AphiDeptEmpMap;
import com.chd.hrp.hpm.service.AphiDeptEmpMapService;

@Service("aphiDeptEmpMapService")
public class AphiDeptEmpMapServiceImpl implements AphiDeptEmpMapService {

	private static Logger logger = Logger.getLogger(AphiDeptEmpMapServiceImpl.class);

	@Resource(name = "aphiDeptEmpMapMapper")
	private AphiDeptEmpMapMapper aphiDeptEmpMapMapper = null;

	@Override
	public String queryDeptEmpMap(Map<String, Object> mapVo) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) mapVo.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		return JsonListBeanUtil.listToJson(aphiDeptEmpMapMapper.queryDeptEmpMap(mapVo, rowBounds), sysPage.getTotal());

	}

	@Override
	public String addDeptEmpMap(Map<String, Object> mapVo) throws DataAccessException {

		AphiDeptEmpMap ii = aphiDeptEmpMapMapper.queryDeptEmpMapByCode(mapVo);

		if (ii != null) {

			return "{\"error\":\"数据重复.\"}";

		}

		try {

			aphiDeptEmpMapMapper.addDeptEmpMap(mapVo);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addItem\"}";

		}
	}

	@Override
	public AphiDeptEmpMap queryDeptEmpMapByCode(Map<String, Object> mapVo) throws DataAccessException {

		return aphiDeptEmpMapMapper.queryDeptEmpMapByCode(mapVo);
	}

	@Override
	public String updateDeptEmpMap(Map<String, Object> mapVo) throws DataAccessException {

		try {

			aphiDeptEmpMapMapper.updateDeptEmpMap(mapVo);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateItem\"}";

		}

	}

	@Override
	public String deleteDeptEmpMap(Map<String, Object> mapVo, String item_codes) throws DataAccessException {

		try {

			if (item_codes != null && !item_codes.equals("")) {

				String[] item_codeArray = item_codes.split(",");

				for (String item_code : item_codeArray) {

					mapVo.put("item_code", item_code);

					aphiDeptEmpMapMapper.deleteDeptEmpMap(mapVo);
				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteIncomeItem\"}";

		}

	}

	@Override
	public String queryHpmItem(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> itemList = aphiDeptEmpMapMapper.queryHpmItem(entityMap);
		return ChdJson.toJson(itemList);
	}

	@Override
	public String queryHpmEmpItem(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> itemList = aphiDeptEmpMapMapper.queryHpmEmpItem(entityMap);
		return ChdJson.toJson(itemList);
	}

	@Override
	public String saveDeptEmpMap(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			aphiDeptEmpMapMapper.deleteDeptEmpMap(entityMap);
			
			List<Map> list = JSONArray.parseArray(entityMap.get("detail_data").toString(),Map.class);
			
			List<Map<String,Object>> savelist = new ArrayList<Map<String,Object>>();
			
			for (Map map : list) {
				if(map.get("dept_item_code") != null && map.get("emp_item_code") != null){
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", entityMap.get("group_id"));
					saveMap.put("hos_id", entityMap.get("hos_id"));
					saveMap.put("copy_code", entityMap.get("copy_code"));
					saveMap.put("dept_item_code", map.get("dept_item_code"));
					saveMap.put("emp_item_code", map.get("emp_item_code"));
					savelist.add(saveMap);
				}
			}
			
			if(savelist != null && savelist.size() >0){
				aphiDeptEmpMapMapper.addBatchDeptEmpMap(savelist);
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}

}
