/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.serviceImpl.training.setting;

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
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.training.setting.HrGenreTypeMapper;
import com.chd.hrp.hr.entity.training.setting.HrGenreType;
import com.chd.hrp.hr.service.training.setting.HrGenreTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 培训类别
 * @Table: HR_Train
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("hrGenreTypeService")
public class HrGenreTypeServiceImpl implements HrGenreTypeService {

	private static Logger logger = Logger.getLogger(HrGenreTypeServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "hrGenreTypeMapper")
	private final HrGenreTypeMapper hrGenreTypeMapper = null;

	/**
	 * @Description 添加培训类别<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addGenreType(Map<String, Object> entityMap) throws DataAccessException {


		//获取对象培训类别
	List<HrGenreType> list = hrGenreTypeMapper.queryGenreTypeById(entityMap);

	if (list.size() > 0) {

		for (HrGenreType hrDutyLevel : list) {
			if (hrDutyLevel.getType_code().equals(entityMap.get("type_code"))) {
				return "{\"error\":\"编码：" + hrDutyLevel.getType_code().toString() + "已存在.\"}";
			}
			if (hrDutyLevel.getType_name().equals(entityMap.get("type_name"))) {
				return "{\"error\":\"名称：" + hrDutyLevel.getType_name().toString() + "已存在.\"}";
			}
		}
	}
	

		try {

			int state = hrGenreTypeMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新培训类别<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateGenreType(Map<String, Object> entityMap) throws DataAccessException {
		//获取对象培训类别
	List<HrGenreType> list = hrGenreTypeMapper.queryGenreTypeByIdName(entityMap);

	if (list.size() > 0) {

		for (HrGenreType hrDutyLevel : list) {
			
			if (hrDutyLevel.getType_name().equals(entityMap.get("type_name"))) {
				return "{\"error\":\"名称：" + hrDutyLevel.getType_name().toString() + "已存在.\"}";
			}
		}
	}
	
		try {

			int state = hrGenreTypeMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集培训类别<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryGenreType(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrGenreType> list = (List<HrGenreType>) hrGenreTypeMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrGenreType> list = (List<HrGenreType>) hrGenreTypeMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 删除培训类别
	 */
	@Override
	public String deleteGenreType(List<HrGenreType> entityList) throws DataAccessException {

		try {

			hrGenreTypeMapper.deleteGenreType(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 修改页面跳转查询
	 */
	@Override
	public HrGenreType queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrGenreTypeMapper.queryByCode(entityMap);
	}

	@Override
	public String importDate(Map<String, Object> entityMap)
			throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		int failureNum1 = 0;
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
					for (Map<String, Object> ma : saveList) {
						if(ma.get("type_code").toString().equals(map.get("type_code").get(1).toString())){
							failureMsg.append("<br/>培训类别编码 "+map.get("type_code")+" 存在重复数据; ");
							failureNum++;
							failureNum1++;
							continue;
							
						}
						if(ma.get("type_name").toString().equals(map.get("type_name").get(1).toString())){
							failureMsg.append("<br/>培训类别名称 "+map.get("type_name")+" 存在重复数据; ");
							failureNum++;
							failureNum1++;
							continue;
						}
						
						
					}
					if(failureNum1>0){
						
						continue;
					}
					
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					saveMap.put("type_code", map.get("type_code").get(1));
					saveMap.put("type_name", map.get("type_name").get(1));
					saveMap.put("spell_code", StringTool.toPinyinShouZiMu(map.get("type_name").get(1)));
					saveMap.put("wbx_code",StringTool.toWuBi(map.get("type_name").get(1)));
					saveMap.put("is_stop", whetherMap.get(map.get("is_stop").get(1)));
					saveMap.put("note", map.get("note").get(1));
					
					 List<HrGenreType> type = hrGenreTypeMapper.queryByCodeName(saveMap);
					
					if(type.size()>0){
						for (HrGenreType hrGenreType : type) {
							if(hrGenreType.getType_code().equals(map.get("type_code").get(1).toString())){
								failureMsg.append("<br/>培训类别编码 "+map.get("type_code")+" 已存在; ");
							}else{
							failureMsg.append("<br/>培训类别名称 "+map.get("type_name")+" 已存在; ");
							}
						
						}
						failureNum++;
						continue;
					}
				
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrGenreTypeMapper.addBatch(saveList);
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

	@Override
	public int queryUseGenreType(HrGenreType hrGenreType)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return hrGenreTypeMapper.queryUseGenreType(hrGenreType);
	}

}
