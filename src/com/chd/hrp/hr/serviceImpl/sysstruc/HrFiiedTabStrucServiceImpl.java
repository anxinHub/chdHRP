package com.chd.hrp.hr.serviceImpl.sysstruc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.base.BaseCRUDMapper;
import com.chd.hrp.hr.dao.sysstruc.HrColStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFiiedDataMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFiiedTabStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFiiedViewMapper;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedData;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedTabStruc;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedTabType;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedView;
import com.chd.hrp.hr.service.sysstruc.HrFiiedTabStrucService;
import com.chd.hrp.hr.util.ParameterHandler;
@Service("hrFiiedTabStrucService")
public class HrFiiedTabStrucServiceImpl implements HrFiiedTabStrucService{
	

	private static Logger logger = Logger.getLogger(HrFiiedTabStrucServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "hrFiiedTabStrucMapper")
	private final HrFiiedTabStrucMapper hrFiiedTabStrucMapper = null;
	
	@Resource(name = "hrFiiedViewMapper")
	private final HrFiiedViewMapper hrFiiedViewMapper = null;
	
	@Resource(name = "baseCRUDMapper")
	private final BaseCRUDMapper baseCRUDMapper = null;
	
	@Resource(name = "hrFiiedDataMapper")
	private final HrFiiedDataMapper hrFiiedDataMapper = null;
	

		@Resource(name = "hrColStrucMapper")
		private final HrColStrucMapper hrColStrucMapper = null;
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {	
		//获取对象如：国家标准、地方标准、医院标准
		HrFiiedTabStruc hrFiiedTabStruc = hrFiiedTabStrucMapper.queryByCodeByName(entityMap);

		if (hrFiiedTabStruc != null) {
       if(hrFiiedTabStruc.getField_tab_code().equals(entityMap.get("field_tab_code").toString())){
    	   return "{\"error\":\"代码表编码重复,请重新添加.\"}";
       }
       if(hrFiiedTabStruc.getField_tab_name().equals(entityMap.get("field_tab_name").toString())){
    	   return "{\"error\":\"代码表名称重复,请重新添加.\"}";
       }

		}
		//校验编码是否重复
		//queryHrFiiedTabStrucTree
		//{isCheck=false, field_tab_code=zzb_abcd, field_tab_name=测试231231, type_filed_code=99, is_innr=0, is_cite=0, note=, group_id=18, hos_id=20, copy_code=001}
		HashMap<String,Object> hm = new HashMap<String, Object>();
		hm.put("group_id", entityMap.get("group_id"));
		hm.put("hos_id", entityMap.get("hos_id"));
		hm.put("id", entityMap.get("field_tab_code"));
		List<Map<String,Object>> ListTree = hrFiiedTabStrucMapper.queryHrFiiedTabStrucTree(hm);
		if(ListTree.size()!=0) {
			return "{\"error\":\"代码表编码重复,请重新添加.\"}";
		}
		
		try {
			
			int state = hrFiiedTabStrucMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {
			
			hrFiiedTabStrucMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		
		try {
			HrFiiedTabStruc hrFiiedTabStruc=hrFiiedTabStrucMapper.queryByCode(entityMap);
			if(hrFiiedTabStruc.getIs_cite()!=null && "".equals(hrFiiedTabStruc.getIs_cite().toString())){
				if("1".equals(hrFiiedTabStruc.getIs_cite().toString())){
					return "{\"error\":\"更新失败 所修改数据为外部引用数据，无法修改\"}";
				}
			}
		  int state = hrFiiedTabStrucMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {
			
		  hrFiiedTabStrucMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		  try {
			  	hrFiiedViewMapper.delete(entityMap);
			  	hrFiiedDataMapper.delete(entityMap);
			  	hrColStrucMapper.deleteTabCode(entityMap);
				int state = hrFiiedTabStrucMapper.delete(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

			}	
			
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
	try {
			
			hrFiiedTabStrucMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象如：国家标准、地方标准、医院标准
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<HrFiiedTabType> list = (List<HrFiiedTabType>)hrFiiedTabStrucMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = hrFiiedTabStrucMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = hrFiiedTabStrucMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

			List<HrFiiedTabType> list = (List<HrFiiedTabType>)hrFiiedTabStrucMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {	
		return hrFiiedTabStrucMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return hrFiiedTabStrucMapper.queryByUniqueness(entityMap);
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return hrFiiedTabStrucMapper.queryExists(entityMap);
	}

	@Override
	public String queryHrFiiedTabStrucTree(Map<String, Object> entityMap) {
		List<Map<String,Object>> ListTree = hrFiiedTabStrucMapper.queryHrFiiedTabStrucTree(entityMap);
		return JSONArray.toJSONString(ListTree);
	}

	@Override
	public String queryHrFiiedData(Map<String, Object> entityMap) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		HrFiiedTabStruc fts = hrFiiedTabStrucMapper.queryByCode(entityMap);
		if(fts != null){
			if(fts.getIs_cite() != null && fts.getIs_cite() == 1){
				HrFiiedView hrFiiedView = hrFiiedViewMapper.queryByCode(entityMap);
				if(hrFiiedView != null && hrFiiedView.getQuery_sql() != null){
					String sql = hrFiiedView.getQuery_sql();
					if(entityMap.get("field_col_code") != null && !"".equals(entityMap.get("field_col_code").toString())){
						sql = "SELECT * FROM (" + sql + ") WHERE field_col_code = '" + entityMap.get("field_col_code").toString() + "' or field_col_name like '%" + entityMap.get("field_col_code").toString() + "%'";	
					}
					ParameterHandler parameterHandler = new ParameterHandler();
					sql = parameterHandler.setParameters(sql);
					list = hrFiiedViewMapper.queryDictCustomSql(sql);                                                     
				}
			}else{
				list = hrFiiedTabStrucMapper.queryHrFiiedData(entityMap);
			}
			
		}
	    return ChdJson.toJsonLower(list);
		
	}

	@Override
	public String saveHrFiiedData(Map<String, Object> entityMap) {
		
		/*try {
		List<Map> addlistVo=JSONArray.parseArray(entityMap.get("addData").toString(),Map.class);
		List<Map> updatelistVo=JSONArray.parseArray(entityMap.get("modData").toString(),Map.class);
		for (Map map : addlistVo) {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("spell_code", StringTool.toPinyinShouZiMu(map.get("field_col_name").toString()));
			map.put("wbx_code",StringTool.toWuBi(map.get("field_col_name").toString()));
		}
		for (Map map1 : updatelistVo) {
			map1.put("group_id", SessionManager.getGroupId());
			map1.put("hos_id", SessionManager.getHosId());
			map1.put("copy_code", SessionManager.getCopyCode());
			map1.put("spell_code", StringTool.toPinyinShouZiMu(map1.get("field_col_name").toString()));
			map1.put("wbx_code",StringTool.toWuBi(map1.get("field_col_name").toString()));
		}
				if (updatelistVo.size()>0) {
					int state = hrFiiedTabStrucMapper.updateHrFiiedData(updatelistVo);
					falg="{\"msg\":\"修改成功.\",\"state\":\"true\"}";
				}else{
					int state = hrFiiedTabStrucMapper.saveHrFiiedData(addlistVo);
					falg="{\"msg\":\"添加成功.\",\"state\":\"true\"}";
				}
			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);
				falg="{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 add\"}";
				return falg;

			

		}*/
		
		List<String> codeList = new ArrayList<String>();
		List<String> lastList = new ArrayList<String>();
		List<String> nameList = new ArrayList<String>();
		
		List<HrFiiedData> listVo=JSONArray.parseArray(entityMap.get("allData").toString(),HrFiiedData.class);
		for (HrFiiedData data : listVo) {
			if(codeList.contains(data.getField_col_code())){
				throw new SysException("代码表编码不允许重复");
			}
			if(nameList.contains(data.getField_col_name())){
				throw new SysException("代码项名称不允许重复");
			}
			if(lastList.contains(data.getSuper_col_code())){
				throw new SysException(data.getField_col_code()+"上级代码为末级");
			}
			data.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
			data.setHos_id(Long.parseLong(SessionManager.getHosId()));
			//data.setCopy_code(SessionManager.getCopyCode());
			data.setSpell_code(StringTool.toPinyinShouZiMu(data.getField_col_name()));
			data.setWbx_code(StringTool.toWuBi(data.getField_col_name()));
			codeList.add(data.getField_col_code());
			nameList.add(data.getField_col_name());
			if(data.getIs_last().toString().equals("1")){
				lastList.add(data.getField_col_code());
			}
			
		}
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		hrFiiedTabStrucMapper.deleteHrFiiedDataByTabCode(entityMap,listVo);
		
		int state = hrFiiedTabStrucMapper.saveHrFiiedData(listVo);
		
		return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	}

	@Override
	public String deleteHrFiiedData(List<Map> listVO) throws Exception {
		try {
			int state = hrFiiedTabStrucMapper.deleteHrFiiedData(listVO);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage()) ;
		}
	}

	@Override
	public String queryColAndTabName(Map<String, Object> entityMap) {
		List<Map<String,Object>> list = hrFiiedTabStrucMapper.queryColAndTabName(entityMap);
		return ChdJson.toJson(list);
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
		//重复map
		Map<String,Object> repeateMap = new HashMap<String,Object>();
		String key = "";
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					saveMap.put("field_tab_code", entityMap.get("field_tab_code"));
					saveMap.put("field_col_code", map.get("field_col_code").get(1));
					saveMap.put("field_col_name", map.get("field_col_name").get(1));
					
					saveMap.put("super_col_code", map.get("super_col_name").get(1));
					saveMap.put("spell_code", StringTool.toPinyinShouZiMu(map.get("field_col_name").get(1)));
					saveMap.put("wbx_code", StringTool.toWuBi(map.get("field_col_name").get(1)));
					saveMap.put("is_innr", whetherMap.get(map.get("is_innr_text").get(1)));
					saveMap.put("is_stop", whetherMap.get(map.get("is_stop_text").get(1)));
					saveMap.put("is_last", whetherMap.get(map.get("is_last_text").get(1)));
					saveMap.put("note", map.get("note").get(1));
					HrFiiedData type = hrFiiedDataMapper.queryByCode(saveMap);
					
					if(type != null){
						failureMsg.append("<br/>类别代码 "+map.get("field_col_code")+" 已存在; ");
						failureNum++;
						continue;
					}
	        HrFiiedData name = hrFiiedDataMapper.queryByCodeName(saveMap);
					
					if(name != null){
						failureMsg.append("<br/>类别代码名称 "+map.get("field_col_name")+" 已存在; ");
						failureNum++;
						continue;
					}
					key= saveMap.get("field_tab_code").toString()+"-"+saveMap.get("field_col_code").toString()+"-"+saveMap.get("field_col_name");
					if(repeateMap.get(key)!=null){
						failureMsg.append("<br/>类别代码 "+map.get("field_col_name")+" 已存在; ");
						failureNum++;
						continue;
					}else{
						repeateMap.put(key, saveMap);
					}
								
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrFiiedDataMapper.addBatch(saveList);
					hrFiiedDataMapper.updateBatchLast(saveList);
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
	/**
	 * 打印
	 */
	@Override
	public List<Map<String, Object>> queryHrFiiedDataByPrint(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrFiiedTabStrucMapper.queryHrFiiedDataByPrint(entityMap);
		return list ;
	}

			

}
