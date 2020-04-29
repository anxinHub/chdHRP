/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.serviceImpl.record;

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
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.record.HrEmpTypeMapper;
import com.chd.hrp.hr.entity.record.HrEmpType;
import com.chd.hrp.hr.service.record.HrEmpTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 人员类别
 * @Table: HR_EMP_TYPE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("hrEmpTypeService")
public class HrEmpTypeServiceImpl implements HrEmpTypeService {

	private static Logger logger = Logger.getLogger(HrEmpTypeServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "hrEmpTypeMapper")
	private final HrEmpTypeMapper hrEmpTypeMapper = null;

	/**
	 * @Description 添加人员类别<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象人员类别

		List<HrEmpType> list = hrEmpTypeMapper.queryEmpTypeById(entityMap);

		if (list.size() > 0) {

			for (HrEmpType hrDutyLevel : list) {
				if (hrDutyLevel.getType_code().equals(entityMap.get("type_code").toString())) {
					return "{\"error\":\"编码：" + hrDutyLevel.getType_code().toString() + "已存在.\"}";
				}
				if (hrDutyLevel.getType_name().equals(entityMap.get("type_name").toString())) {
					return "{\"error\":\"名称：" + hrDutyLevel.getType_name().toString() + "已存在.\"}";
				}
			}
		}

		try {

			int state = hrEmpTypeMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 更新人员类别<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			if("1".equals(entityMap.get("is_change").toString())){
				Map<String,Object> empType = hrEmpTypeMapper.queryEmpTypeByName(entityMap);
				if(empType != null){
					if(empType.get("type_name").toString().equals(entityMap.get("type_name").toString())){
						return "{\"error\":\"名称：" + entityMap.get("type_name").toString() + "已存在.\"}";
					}
				}
			}
			
			int state = hrEmpTypeMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";
		}

	}

	/**
	 * @Description 查询结果集人员类别<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrEmpType> list = (List<HrEmpType>) hrEmpTypeMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrEmpType> list = (List<HrEmpType>) hrEmpTypeMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象人员类别<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public HrEmpType queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrEmpTypeMapper.queryByCode(entityMap);
	}

	/**
	 * 删除人员类别
	 */

	@Override
	public String deleteHrEmpType(List<HrEmpType> entityList) throws DataAccessException {

		try {

			hrEmpTypeMapper.deleteHrEmpType(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}
	}

	/**
	 * 左侧树形
	 */
	@Override
	public String queryHosEmpTypeTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrEmpType> storeTypeList = (List<HrEmpType>) hrEmpTypeMapper.query(entityMap);
		for (HrEmpType storeType : storeTypeList) {
			treeJson.append(
					"{'id':'" + storeType.getType_code() + "', 'pId':'0', 'name':'" + storeType.getType_name() + "'},");

		}
		treeJson.append("]");
		return treeJson.toString();
	}
	/**
	 * 导入数据
	 */
	@Override
	public String importDate(Map<String, Object> entityMap)
			throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					
					//过滤空行
					if("".equals(map.get("type_code").get(1)) || "".equals(map.get("type_name").get(1))){
						continue;
					}
					
					if("null".equals(map.get("type_code").get(1)) || "null".equals(map.get("type_name").get(1))){
						continue;
					}
					
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					saveMap.put("type_code", map.get("type_code").get(1));
					saveMap.put("type_name", map.get("type_name").get(1));
					saveMap.put("is_stop", whetherMap.get(map.get("is_stop").get(1)));
					saveMap.put("spell_code", StringTool.toPinyinShouZiMu(map.get("type_name").get(1)));
					saveMap.put("wbx_code", StringTool.toWuBi(map.get("type_name").get(1)));
					saveMap.put("note", map.get("note").get(1));
					
					HrEmpType type = hrEmpTypeMapper.queryByCodequeryByCodeName(saveMap);
					
					if(type != null){
						if(type.getType_code().equals(map.get("type_code").get(1).toString())){
							failureMsg.append("<br/>分类编码 "+map.get("type_code")+" 已存在; ");
							
						}else{
							failureMsg.append("<br/>分类名称 "+map.get("type_name")+" 已存在; ");
						}
						
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrEmpTypeMapper.addBatch(saveList);
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 "+successNum+"条"+failureMsg+"\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}

}
