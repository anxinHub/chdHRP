/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.hpm.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.hpm.dao.AphiDeptHipMapper;
import com.chd.hrp.hpm.entity.AphiDeptHip;
import com.chd.hrp.hpm.service.AphiDeptHipService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 8801 科室字典表
 * @Table: Prm_DEPT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiDeptHipService")
public class AphiDeptHipServiceImpl implements AphiDeptHipService {

	private static Logger logger = Logger.getLogger(AphiDeptHipServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "aphiDeptHipMapper")
	private final AphiDeptHipMapper aphiDeptHipMapper = null;

	/**
	 * @Description 添加8801 科室字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAphiDeptHip(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			AphiDeptHip dept = aphiDeptHipMapper.queryAphiDeptHipByDeptCode(entityMap);
			if(dept != null ){
				return "{\"error\":\"科室编码已存在 \"}";
			}
			
			Integer dept_id = aphiDeptHipMapper.queryAphiDeptHipNextval();
			entityMap.put("dept_id", dept_id);
			entityMap.put("user_code", SessionManager.getUserCode());
			entityMap.put("create_date", new Date());
			entityMap.put("is_stop", 0);
			entityMap.put("spell_code",StringTool.toPinyinShouZiMu(entityMap.get("dept_name").toString()));
			entityMap.put("wbx_code",StringTool.toWuBi(entityMap.get("dept_name").toString()));
			entityMap.put("note", "新增");
			
			aphiDeptHipMapper.addAphiDeptHip(entityMap);//添加主表数据
			

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败 \"}");

		}

	}

	/**
	 * @Description 批量添加8801 科室字典表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAphiDeptHip(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			aphiDeptHipMapper.addBatchAphiDeptHip(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchAphiDeptHip\"}";

		}

	}

	/**
	 * @Description 更新8801 科室字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAphiDeptHip(Map<String, Object> entityMap) throws DataAccessException {

		try {
			//修改主表
			aphiDeptHipMapper.updateAphiDeptHip(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException ("{\"error\":\"操作失败 \"}");

		}

	}

	/**
	 * @Description 批量更新8801 科室字典表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAphiDeptHip(List<Map<String, Object>> entityList) throws DataAccessException {

		List<Map<String, Object>> listDeptforUpdate = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> listDeptDictforUpdate = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> listDeptDictforInsert = new ArrayList<Map<String, Object>>();

		try {
			List<Map<String, Object>> list = entityList;

			for (Map<String, Object> map : list) {

				Map<String, Object> mapDeptforUpdate = map;

				mapDeptforUpdate.put("is_stop", '0');

				listDeptforUpdate.add(mapDeptforUpdate);

				Map<String, Object> mapDeptDictforUpdate = new HashMap<String, Object>();

				mapDeptDictforUpdate.put("group_id", map.get("group_id"));

				mapDeptDictforUpdate.put("hos_id", map.get("hos_id"));

				mapDeptDictforUpdate.put("copy_code", map.get("copy_code"));

				mapDeptDictforUpdate.put("dept_id", map.get("dept_id"));

				mapDeptDictforUpdate.put("is_stop", '1');

				listDeptDictforUpdate.add(mapDeptDictforUpdate);

				Map<String, Object> mapDeptDictforInsert = map;

				mapDeptDictforInsert.put("is_stop", '0');

				listDeptDictforInsert.add(mapDeptDictforInsert);

			}

			aphiDeptHipMapper.updateBatchAphiDeptHip(listDeptforUpdate);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchAphiDeptHip\"}";

		}

	}

	/**
	 * @Description 删除8801 科室字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAphiDeptHip(Map<String, Object> entityMap) throws DataAccessException {

		try {

			aphiDeptHipMapper.deleteAphiDeptHip(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteAphiDeptHip\"}";

		}

	}

	/**
	 * @Description 批量删除8801 科室字典表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAphiDeptHip(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			//删除非变更表记录
			aphiDeptHipMapper.deleteBatchAphiDeptHip(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 \"}");

		}
	}

	/**
	 * @Description 查询结果集8801 科室字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAphiDeptHip(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiDeptHip> list = aphiDeptHipMapper.queryAphiDeptHip(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiDeptHip> list = aphiDeptHipMapper.queryAphiDeptHip(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象8801 科室字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AphiDeptHip queryAphiDeptHipByCode(Map<String, Object> entityMap) throws DataAccessException {

		return aphiDeptHipMapper.queryAphiDeptHipByCode(entityMap);

	}


	@Override
	public int queryAphiDeptHipNextval(Map<String, Object> entityMap) throws DataAccessException {

		return aphiDeptHipMapper.queryAphiDeptHipNextval();

	}

	//导入
	@Override
	public String importAphiDeptHip(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			String columns=map.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);	
			if(jsonColumns==null || jsonColumns.size()==0){
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}
			
			String content=map.get("content").toString();
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			//new Map查询导入时对应数据信息
			Map<String, Object> entityMap=new HashMap<String,Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
			//1.查询	非变更科室 List
			List<AphiDeptHip> deptList = aphiDeptHipMapper.queryAphiDeptHipAll(entityMap);
			
			//非变更科室 Map 以键值对的形式存储,用于判断科室是否重复  键:dept_code 值:AphiDept
			Map<String,AphiDeptHip> deptCodeMap = new HashMap<String, AphiDeptHip>();
			//非变更科室 Map 以键值对的形式存储,用于判断科室是否重复  键:dept_name 值:AphiDept
			Map<String,AphiDeptHip> deptNameMap = new HashMap<String, AphiDeptHip>();
			
			for(AphiDeptHip aphiDeptHip : deptList){
				deptCodeMap.put(aphiDeptHip.getDept_code(), aphiDeptHip);
				deptNameMap.put(aphiDeptHip.getDept_name(), aphiDeptHip);
			}
			
			//用于存储传的数据值,判断所导入的数据是否重复
			Map<String,String> exitMap = new HashMap<String,String>();
			
			//科室保存List
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			
			//用于记录返回的错误信息
			StringBuffer err_sb = new StringBuffer();
			
			for(Map<String, List<String>> item:liData){
				List<String> dept_code = item.get("科室编码");
				List<String> dept_name = item.get("科室名称");
				List<String> dept_note = item.get("科室描述");
				List<String> is_avg = item.get("是否参与人均奖金");
					
				if (dept_code.get(1) == null) {
					return "{\"warn\":\"科室编码为空！\",\"state\":\"false\",\"row_cell\":\"" + dept_code.get(0) + "\"}";
				}else{
					if(deptCodeMap.get(dept_code.get(1)) != null){
						return "{\"warn\":\"科室编码已存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_code.get(0) + "\"}";
					}
				}

				if(dept_name.get(1) == null){
					return "{\"warn\":\"科室名称为空！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
				}else{
					if(deptNameMap.get(dept_name.get(1)) != null){
						return "{\"warn\":\"科室名称已存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
					}
				}
				
				
				
				if(is_avg.get(1) != null){
					if(!"是".equals(is_avg.get(1)) && !"否".equals(is_avg.get(1)) && !"0".equals(is_avg.get(1)) && !"1".equals(is_avg.get(1))){
						return "{\"warn\":\"是否参与人均奖金值错误 \",\"state\":\"false\",\"row_cell\":\"" + is_avg.get(0) + "\"}";
					}
				}
					
				//判断导入数据是否重复
				String key = dept_code.get(1)+dept_name.get(1);
				
				if(exitMap.get(key) != null ){
					err_sb.append(dept_code.get(1)+"科室编码," + dept_name.get(1)+"科室名称");
				}else{
					exitMap.put(key, key);
				}
					
				Integer dept_id = aphiDeptHipMapper.queryAphiDeptHipNextval();
				//添加数据Map中add到addList
				Map<String,Object> addMap = new HashMap<String,Object>();
				addMap.put("group_id", SessionManager.getGroupId());
				addMap.put("hos_id", SessionManager.getHosId());
				addMap.put("copy_code", SessionManager.getCopyCode());
				addMap.put("dept_id", dept_id);
				addMap.put("dept_code", dept_code.get(1));
				addMap.put("dept_name", dept_name.get(1));
				addMap.put("dept_note", dept_note.get(1) == null ? "" : dept_note.get(1));
				
				
				if("是".equals(is_avg.get(1)) || "否".equals(is_avg.get(1))){
					addMap.put("is_avg",is_avg.get(1).equals("是")?"1":"0");
				}
				
				if("0".equals(is_avg.get(1)) || "1".equals(is_avg.get(1))){
					addMap.put("is_avg",is_avg.get(1).toString());
				}
				
				addMap.put("dept_kind_code", "");
				addMap.put("nature_code", "");
				addMap.put("is_stop", 0);
				addMap.put("spell_code", StringTool.toPinyinShouZiMu(dept_name.get(1)));
				addMap.put("wbx_code", StringTool.toWuBi(dept_name.get(1)));
				addList.add(addMap);
				
			}
			
			if (err_sb.toString().length() > 0 ) {
				return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}
			
			aphiDeptHipMapper.addBatchAphiDeptHip(addList);
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
		
	}

}
