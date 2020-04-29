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

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.base.HrTitleMapper;
import com.chd.hrp.hr.entity.base.HrTitle;
import com.chd.hrp.hr.service.base.HrTitleService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 职称信息
 * @Table: HR_TITLE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("hrTitleService")
public class HrTitleServiceImpl implements HrTitleService {

	private static Logger logger = Logger.getLogger(HrTitleServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "hrTitleMapper")
	private final HrTitleMapper hrTitleMapper = null;

	/**
	 * @Description 添加职称信息<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addHrTitle(Map<String, Object> entityMap) throws DataAccessException {


		//获取对象职称信息
		List<HrTitle> list = hrTitleMapper.queryTitleById(entityMap);

		if (list.size() > 0) {

			for (HrTitle hrDutyLevel : list) {
				if (hrDutyLevel.getTitle_code().equals(entityMap.get("title_code").toString())) {
					return "{\"error\":\"编码：" + hrDutyLevel.getTitle_code().toString() + "已存在.\"}";
				}
				if (hrDutyLevel.getTitle_name().equals(entityMap.get("title_name").toString())) {
					return "{\"error\":\"名称：" + hrDutyLevel.getTitle_name().toString() + "已存在.\"}";
				}
			}
		}
		try {
			 if(!entityMap.get("sup_code").toString().equals("0")){
	        	  //修改上级未非末级
				 hrTitleMapper.updateIsLast(entityMap); 
	          }
			int state = hrTitleMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新职称信息<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateHrTitle(Map<String, Object> entityMap) throws DataAccessException {

		try {

			//获取对象职称信息
			List<HrTitle> list = hrTitleMapper.queryTitleByIdName(entityMap);

			if (list.size() > 0) {

				for (HrTitle hrDutyLevel : list) {
					
					if (hrDutyLevel.getTitle_name().equals(entityMap.get("title_name").toString())) {
						return "{\"error\":\"名称：" + hrDutyLevel.getTitle_name().toString() + "已存在.\"}";
					}
				}
			}
		    if(entityMap.get("is_last").toString().equals("1")){
            	List<HrTitle> listDept=	hrTitleMapper.queryBySuperCode(entityMap);
            	if(listDept.size()>0){
            		return "{\"error\":\"名称：" + entityMap.get("title_name").toString() + "已存在下级,不允许修改是否末级为是.\"}";
            	}
            }
			if(entityMap.get("sup_code").toString().equals("0")){
	    		List<HrTitle> listDept=	hrTitleMapper.querySuperCode(entityMap);
					
					if(listDept.size()>0){
						//修改是否停用
						hrTitleMapper.updateBatchStop(entityMap,listDept);
					}
				}
			int state = hrTitleMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集职称信息<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryHrTitle(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrTitle> list = (List<HrTitle>) hrTitleMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrTitle> list = (List<HrTitle>) hrTitleMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象职称信息<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public HrTitle queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrTitleMapper.queryByCode(entityMap);
	}

	/**
	 * 删除职称信息
	 */
	@Override
	public String deleteBatchTitle(List<HrTitle> entityList) throws DataAccessException {
		try {
			String superCode = "";
			for (HrTitle hrTitle : entityList) {
				int count = hrTitleMapper.querySupCodeBy(hrTitle);
				if (count > 0) {
					superCode += "已经存在下级职称,请先删除下级职称!";
					break;
				}
			}
			if (superCode != null && !superCode.equals("")) {
				return "{\"error\":\"删除失败。" + superCode + "\",\"state\":\"false\"}";
			}
			hrTitleMapper.deleteBatchTitle(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 左侧树形
	 */
	@Override
	public String queryTitleInfoTree(Map<String, Object> entityMap) {
		List<Map<String, Object>> list = hrTitleMapper.queryTitleInfoTree(entityMap);

		return JSON.toJSONString(list);
	}

	@Override
	public String querySupCode(Map<String, Object> entityMap) {
		return JSON.toJSONString(hrTitleMapper.querySupCode(entityMap));
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
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					saveMap.put("title_code", map.get("title_code").get(1));
					saveMap.put("title_name", map.get("title_name").get(1));
					saveMap.put("is_stop", whetherMap.get(map.get("is_stop").get(1)));
					saveMap.put("sup_code", map.get("sup_name").get(1));
					saveMap.put("is_last", whetherMap.get(map.get("is_last").get(1)));
					saveMap.put("spell_code", StringTool.toPinyinShouZiMu(map.get("title_name").get(1)));
					saveMap.put("wbx_code", StringTool.toWuBi(map.get("title_name").get(1)));
					saveMap.put("note", map.get("note").get(1));
					
					HrTitle type = hrTitleMapper.queryByCode(saveMap);
					
					if(type != null){
						if(type.getTitle_code().equals(map.get("title_code").get(1).toString())){
							failureMsg.append("<br/>职称信息编码 "+map.get("title_code")+" 已存在; ");
						}else{
						failureMsg.append("<br/>职称信息名称 "+map.get("title_name")+" 已存在; ");
						}
						failureNum++;
						continue;
					}
					
					//上级编码是否存在
					Map<String,Object> supMap = new HashMap<String,Object>();
					supMap.put("group_id", SessionManager.getGroupId());
					supMap.put("hos_id", SessionManager.getHosId());
					supMap.put("copy_code", SessionManager.getCopyCode());
					supMap.put("sup_code", map.get("sup_name").get(1));
					Map<String,Object> mapS = hrTitleMapper.queryBySupCode(supMap);
					if(mapS != null){
						saveMap.put("sup_code", mapS.get("TITLE_CODE").toString());
					}else{
						saveMap.put("sup_code", "TOP");
					}
					
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrTitleMapper.addBatch(saveList);
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
