/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.serviceImpl.base;

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
import com.chd.hrp.hr.dao.base.HrTitleLevelMapper;
import com.chd.hrp.hr.entity.base.HrTitleLevel;
import com.chd.hrp.hr.service.base.HrTitleLevelService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 职称等级
 * @Table: HR_TITLE_LEVEL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("hrTitleLevelService")
public class HrTitleLevelServiceImpl implements HrTitleLevelService {

	private static Logger logger = Logger.getLogger(HrTitleLevelServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "hrTitleLevelMapper")
	private final HrTitleLevelMapper hrTitleLevelMapper = null;

	/**
	 * @Description 添加职称等级<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addTitleLevel(Map<String, Object> entityMap) throws DataAccessException {


		//获取对象职务等级
	List<HrTitleLevel> list = hrTitleLevelMapper.queryTitleLevelById(entityMap);

	if (list.size() > 0) {

		for (HrTitleLevel hrDutyLevel : list) {
			if (hrDutyLevel.getTitle_level_code().equals(entityMap.get("title_level_code"))) {
				return "{\"error\":\"编码：" + hrDutyLevel.getTitle_level_code().toString() + "已存在.\"}";
			}
			if (hrDutyLevel.getTitle_level_name().equals(entityMap.get("title_level_name"))) {
				return "{\"error\":\"名称：" + hrDutyLevel.getTitle_level_name().toString() + "已存在.\"}";
			}
		}
	}
	

		try {

			int state = hrTitleLevelMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新职称等级<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateTitleLevel(Map<String, Object> entityMap) throws DataAccessException {
		//获取对象职务等级
	List<HrTitleLevel> list = hrTitleLevelMapper.queryTitleLevelByIdName(entityMap);

	if (list.size() > 0) {

		for (HrTitleLevel hrDutyLevel : list) {
			
			if (hrDutyLevel.getTitle_level_name().equals(entityMap.get("title_level_name"))) {
				return "{\"error\":\"名称：" + hrDutyLevel.getTitle_level_name().toString() + "已存在.\"}";
			}
		}
	}
	
		try {

			int state = hrTitleLevelMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集职称等级<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryTitleLevel(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrTitleLevel> list = (List<HrTitleLevel>) hrTitleLevelMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrTitleLevel> list = (List<HrTitleLevel>) hrTitleLevelMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 删除职称等级
	 */
	@Override
	public String deleteTitleLevel(List<HrTitleLevel> entityList) throws DataAccessException {

		try {

			hrTitleLevelMapper.deleteTitleLevel(entityList);

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
	public HrTitleLevel queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrTitleLevelMapper.queryByCode(entityMap);
	}

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
					if("".equals(map.get("title_level_code").get(1)) || "".equals(map.get("title_level_name").get(1))){
						continue;
					}
					
					if("null".equals(map.get("title_level_code").get(1)) || "null".equals(map.get("title_level_name").get(1))){
						continue;
					}
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					saveMap.put("title_level_code", map.get("title_level_code").get(1));
					saveMap.put("title_level_name", map.get("title_level_name").get(1));
					saveMap.put("spell_code", StringTool.toPinyinShouZiMu(map.get("title_level_name").get(1)));
					saveMap.put("wbx_code", StringTool.toWuBi(map.get("title_level_name").get(1)));
					saveMap.put("is_stop", whetherMap.get(map.get("is_stop").get(1)));
					saveMap.put("note", map.get("note").get(1));
					
					 HrTitleLevel type = hrTitleLevelMapper.queryByCode(saveMap);
					
					if(type != null){
						if(type.getTitle_level_code().equals(map.get("title_level_code").get(1).toString())){
							failureMsg.append("<br/>职称等级编码 "+map.get("title_level_code")+" 已存在; ");
						}else{
						failureMsg.append("<br/>职称等级名称 "+map.get("title_level_name")+" 已存在; ");
						}
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrTitleLevelMapper.addBatch(saveList);
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
