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
import com.chd.hrp.hr.dao.training.setting.HrSignModeMapper;
import com.chd.hrp.hr.entity.training.setting.HrSignMode;
import com.chd.hrp.hr.service.training.setting.HrSignModeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 签到方式
 * @Table: HR_way
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("hrSignModeService")
public class HrSignModeServiceImpl implements HrSignModeService {

	private static Logger logger = Logger.getLogger(HrSignModeServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "hrSignModeMapper")
	private final HrSignModeMapper hrSignModeMapper = null;

	/**
	 * @Description 添加签到方式<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addSignMode(Map<String, Object> entityMap) throws DataAccessException {


		//获取对象签到方式
	List<HrSignMode> list = hrSignModeMapper.querySignModeById(entityMap);

	if (list.size() > 0) {

		for (HrSignMode hrDutyLevel : list) {
			if (hrDutyLevel.getWay_code().equals(entityMap.get("way_code"))) {
				return "{\"error\":\"编码：" + hrDutyLevel.getWay_code().toString() + "已存在.\"}";
			}
			if (hrDutyLevel.getWay_name().equals(entityMap.get("way_name"))) {
				return "{\"error\":\"名称：" + hrDutyLevel.getWay_name().toString() + "已存在.\"}";
			}
		}
	}
	

		try {

			int state = hrSignModeMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新签到方式<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateSignMode(Map<String, Object> entityMap) throws DataAccessException {
		//获取对象签到方式
	List<HrSignMode> list = hrSignModeMapper.querySignModeByIdName(entityMap);

	if (list.size() > 0) {

		for (HrSignMode hrDutyLevel : list) {
			
			if (hrDutyLevel.getWay_name().equals(entityMap.get("way_name"))) {
				return "{\"error\":\"名称：" + hrDutyLevel.getWay_name().toString() + "已存在.\"}";
			}
		}
	}
	
		try {

			int state = hrSignModeMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集签到方式<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String querySignMode(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrSignMode> list = (List<HrSignMode>) hrSignModeMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrSignMode> list = (List<HrSignMode>) hrSignModeMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 删除签到方式
	 */
	@Override
	public String deleteSignMode(List<HrSignMode> entityList) throws DataAccessException {

		try {

			hrSignModeMapper.deleteSignMode(entityList);

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
	public HrSignMode queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrSignModeMapper.queryByCode(entityMap);
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
					if("".equals(map.get("way_code").get(1)) || "".equals(map.get("way_name").get(1))){
						continue;
					}
					
					if("null".equals(map.get("way_code").get(1)) || "null".equals(map.get("way_name").get(1))){
						continue;
					}
					for (Map<String, Object> ma : saveList) {
						if(ma.get("way_code").toString().equals(map.get("way_code").get(1).toString())){
							failureMsg.append("<br/>签到方式编码 "+map.get("way_code")+" 存在重复数据; ");
							failureNum++;
							continue;
						}
						if(ma.get("way_name").toString().equals(map.get("way_name").get(1).toString())){
							failureMsg.append("<br/>签到方式名称 "+map.get("way_name")+" 存在重复数据; ");
							failureNum++;
							continue;
							
						}
						
					}
					if(failureNum>0){
						
						continue;
					}
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					saveMap.put("way_code", map.get("way_code").get(1));
					saveMap.put("way_name", map.get("way_name").get(1));
					saveMap.put("spell_code", StringTool.toPinyinShouZiMu(map.get("way_name").get(1)));
					saveMap.put("wbx_code",StringTool.toWuBi(map.get("way_name").get(1)));
					saveMap.put("is_stop", whetherMap.get(map.get("is_stop").get(1)));
					saveMap.put("note", map.get("note").get(1));
					
					 List<HrSignMode> Mode = hrSignModeMapper.queryByCodeName(saveMap);
					
					if(Mode.size()>0){
						for (HrSignMode hrSignMode : Mode) {
							if(hrSignMode.getWay_code().equals(map.get("way_code").get(1).toString())){
								failureMsg.append("<br/>签到方式编码 "+map.get("way_code")+" 已存在; ");
							}else{
							failureMsg.append("<br/>签到方式名称 "+map.get("way_name")+" 已存在; ");
							}
							
						}
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrSignModeMapper.addBatch(saveList);
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
	public int queryUseSignMode(HrSignMode hrSignMode)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return hrSignModeMapper.queryUseSignMode(hrSignMode);
	}

}
