package com.chd.hrp.hr.serviceImpl.sc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.base.BaseCRUDMapper;
import com.chd.hrp.hr.dao.sc.HrFiledDataMapper;
import com.chd.hrp.hr.dao.sc.HrFiledTabStrucMapper;
import com.chd.hrp.hr.dao.sc.HrFiledTabTypeMapper;
import com.chd.hrp.hr.dao.sc.HrTableDesignMapper;
import com.chd.hrp.hr.dao.sysstruc.HrColStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFiiedTabStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFiiedTabTypeMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFiiedViewMapper;
import com.chd.hrp.hr.entity.sc.HrFiledData;
import com.chd.hrp.hr.entity.sc.HrFiledTabStruc;
import com.chd.hrp.hr.entity.sc.HrFiledTabType;
import com.chd.hrp.hr.entity.sc.HrTableColumn;
import com.chd.hrp.hr.entity.sc.HrTableDesign;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
import com.chd.hrp.hr.entity.sc.HrTableType;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedTabStruc;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedTabType;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedView;
import com.chd.hrp.hr.service.sc.HrFiledTabStrucService;
import com.chd.hrp.hr.util.FileUtil;
import com.chd.hrp.hr.util.ParameterHandler;
import com.chd.hrp.hr.util.StringUtils;
@Service("hrFiledTabStrucService")
public class HrFiledTabStrucServiceImpl implements HrFiledTabStrucService{
	

	private static Logger logger = Logger.getLogger(HrFiledTabStrucServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "hrFiledTabStrucMapper")
	private final HrFiledTabStrucMapper hrFiledTabStrucMapper = null;
	
	//引入DAO操作 新版代码表类型
	@Resource(name = "hrFiledTabTypeMapper")
	private final HrFiledTabTypeMapper hrFiledTabTypeMapper = null;
	
	//引入DAO操作 旧版代码表类型
	@Resource(name = "hrFiiedTabTypeMapper")
	private final HrFiiedTabTypeMapper hrFiiedTabTypeMapper = null;
	
	//引入DAO操作 旧版本代码表列
	@Resource(name = "hrFiiedTabStrucMapper")
	private final HrFiiedTabStrucMapper hrFiiedTabStrucMapper = null;
	
	@Resource(name = "hrFiiedViewMapper")
	private final HrFiiedViewMapper hrFiiedViewMapper = null;
	

	// 引入DAO操作
	@Resource(name = "hrTableDesignMapper")
	private final HrTableDesignMapper hrTableDesignMapper = null;

	
	@Resource(name = "baseCRUDMapper")
	private final BaseCRUDMapper baseCRUDMapper = null;
	
	@Resource(name = "hrFiledDataMapper")
	private final HrFiledDataMapper hrFiledDataMapper = null;
	

		@Resource(name = "hrColStrucMapper")
		private final HrColStrucMapper hrColStrucMapper = null;
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {	
		//获取对象如：国家标准、地方标准、医院标准
		HrFiledTabStruc hrFiledTabStruc = hrFiledTabStrucMapper.queryByCodeByName(entityMap);

		if (hrFiledTabStruc != null) {
       if(hrFiledTabStruc.getField_tab_code().equals(entityMap.get("field_tab_code").toString())){
    	   return "{\"error\":\"代码表编码重复,请重新添加.\"}";
       }
       if(hrFiledTabStruc.getField_tab_name().equals(entityMap.get("field_tab_name").toString())){
    	   return "{\"error\":\"代码表名称重复,请重新添加.\"}";
       }

		}
		//校验编码是否重复
		//queryHrFiledTabStrucTree
		//{isCheck=false, field_tab_code=zzb_abcd, field_tab_name=测试231231, type_filed_code=99, is_innr=0, is_cite=0, note=, group_id=18, hos_id=20, copy_code=001}
		HashMap<String,Object> hm = new HashMap<String, Object>();
		hm.put("group_id", entityMap.get("group_id"));
		hm.put("hos_id", entityMap.get("hos_id"));
		hm.put("id", entityMap.get("field_tab_code"));
		List<Map<String,Object>> ListTree = hrFiledTabStrucMapper.queryHrFiledTabStrucTree(hm);
		if(ListTree.size()!=0) {
			return "{\"error\":\"代码表编码重复,请重新添加.\"}";
		}
		
		try {
			
			int state = hrFiledTabStrucMapper.add(entityMap);
			
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
			
			hrFiledTabStrucMapper.addBatch(entityList);
			
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
			HrFiledTabStruc hrFiledTabStruc=hrFiledTabStrucMapper.queryByCode(entityMap);
			if(hrFiledTabStruc.getIs_cite()!=null || "".equals(hrFiledTabStruc.getIs_cite().toString())){
				if("1".equals(hrFiledTabStruc.getIs_cite().toString())){
//					return "{\"error\":\"更新失败 所修改数据为外部引用数据，无法修改\"}";
					entityMap.put("related_sql", "select  field_col_code   as id , field_col_name as text from HR_FILED_DATA where group_id = [group_id] and hos_id = [hos_id]  and field_tab_code  = '"+entityMap.get("field_tab_code")+"'");
					entityMap.put("query_sql", "select fd.group_id, fd.hos_id, fd.field_tab_code, fd.field_col_code, fd.field_col_name , fd.super_col_code , case    when fd.super_col_code is null then 'TOP' else sfd.field_col_name end as super_col_name, fd.spell_code, fd.wbx_code, fd.is_innr ,fd.is_stop, fd.is_last ,fd.note"
            			+" from HR_FILED_DATA fd  left join HR_FILED_DATA sfd on fd.group_id = sfd.group_id and fd.hos_id = sfd.hos_id and fd.field_tab_code = sfd.field_tab_code and fd.super_col_code = sfd.field_col_code "
            			+"where fd.group_id = [group_id] and fd.hos_id = [hos_id]  and fd.field_tab_code = '"+entityMap.get("field_tab_code")
            			+"' order by fd.field_col_code ");
					entityMap.put("cite_json","");
					
				}
			}
			
			
		  int state = hrFiledTabStrucMapper.update(entityMap);
			
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
			
		  hrFiledTabStrucMapper.updateBatch(entityList);
			
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
			  //	hrFiledViewMapper.delete(entityMap);
			  	hrFiledDataMapper.delete(entityMap);
			  	hrColStrucMapper.deleteTabCode(entityMap);
				int state = hrFiledTabStrucMapper.delete(entityMap);
				
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
			
			hrFiledTabStrucMapper.deleteBatch(entityList);
			
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
		
		List<HrFiledTabType> list = (List<HrFiledTabType>)hrFiledTabStrucMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = hrFiledTabStrucMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = hrFiledTabStrucMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

			List<HrFiledTabType> list = (List<HrFiledTabType>)hrFiledTabStrucMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {	
		return hrFiledTabStrucMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return hrFiledTabStrucMapper.queryByUniqueness(entityMap);
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return hrFiledTabStrucMapper.queryExists(entityMap);
	}

	@Override
	public String queryHrFiledTabStrucTree(Map<String, Object> entityMap) {
		List<Map<String,Object>> ListTree = hrFiledTabStrucMapper.queryHrFiledTabStrucTree(entityMap);
		return JSONArray.toJSONString(ListTree);
	}

	@Override
	public String queryHrFiledData(Map<String, Object> entityMap) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		HrFiledTabStruc fts = hrFiledTabStrucMapper.queryByCode(entityMap);
		if(fts!=null){
		if(fts.getQuery_sql() != null){
		
			
					String sql = fts.getQuery_sql();
					if(entityMap.get("field_tab_code") != null && fts.getIs_cite()==1){
						sql = "SELECT * FROM (" + sql + ") WHERE field_tab_code = '" + entityMap.get("field_tab_code").toString() + "' ";	
					}
					if(entityMap.get("field_col_code") != null && entityMap.get("field_col_code") != ""){
						
						if(fts.getIs_cite()==1){
							sql += " and (field_col_code like '%"+ entityMap.get("field_col_code").toString() +"%' or field_col_name like '%"+entityMap.get("field_col_code").toString()+"%')";
						}else{
							String str  = " and (fd.field_col_code like '%"+ entityMap.get("field_col_code").toString() +"%' or fd.field_col_name like '%"+entityMap.get("field_col_code").toString()+"%')";
							StringBuilder sb = new StringBuilder() ;
							sb.append(sql);
							sb.insert(sb.indexOf(" order"),str);
							sql  = sb.toString();
						}
						
					}
			  Map<String, Object> sqlMap=new HashMap<String, Object>();
              sqlMap  = matchAndReplaceSql(sql,entityMap);//匹配替换
					
					
              entityMap.put("sql",sqlMap.get("result").toString());
			  list = hrFiledTabStrucMapper.queryHrFiledDataSql(entityMap);                                                     
			
			
		}}
	    return ChdJson.toJsonLower(list);
		
	}

	@Override
	public String saveHrFiledData(Map<String, Object> entityMap) {
		
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
					int state = hrFiledTabStrucMapper.updateHrFiledData(updatelistVo);
					falg="{\"msg\":\"修改成功.\",\"state\":\"true\"}";
				}else{
					int state = hrFiledTabStrucMapper.saveHrFiledData(addlistVo);
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
		
		List<HrFiledData> listVo=JSONArray.parseArray(entityMap.get("allData").toString(),HrFiledData.class);
		for (HrFiledData data : listVo) {
			if(codeList.size() != 0){
				if(codeList.contains(data.getField_col_code())){
					throw new SysException("代码表编码不允许重复");
				}
			}
			if(nameList.size() != 0){
				if(nameList.contains(data.getField_col_name())){
					throw new SysException("代码项名称不允许重复");
				}
			}
			
			if(lastList.size() != 0){
				if(lastList.contains(data.getSuper_col_code())){
					throw new SysException(data.getField_col_code()+"上级代码为末级");
				}
			}
			
			data.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
			data.setHos_id(Long.parseLong(SessionManager.getHosId()));
			//data.setCopy_code(SessionManager.getCopyCode());
			data.setSpell_code(StringTool.toPinyinShouZiMu(data.getField_col_name()));
			data.setWbx_code(StringTool.toWuBi(data.getField_col_name()));
			codeList.add(data.getField_col_code());
			nameList.add(data.getField_col_name());
//			if(data.getIs_last().toString().equals("1")){
//				lastList.add(data.getField_col_code());
//			}
			
		}
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		hrFiledTabStrucMapper.deleteHrFiledDataByTabCode(entityMap,listVo);
		
		int state = hrFiledTabStrucMapper.saveHrFiledData(listVo);
		
		return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
	}

	@Override
	public String deleteHrFiledData(List<Map> listVO) throws Exception {
		try {
			int state = hrFiledTabStrucMapper.deleteHrFiledData(listVO);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage()) ;
		}
	}

	@Override
	public String queryColAndTabName(Map<String, Object> entityMap) {
		List<Map<String,Object>> list = hrFiledTabStrucMapper.queryColAndTabName(entityMap);
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
					HrFiledData type = hrFiledDataMapper.queryByCode(saveMap);
					
					if(type != null){
						failureMsg.append("<br/>类别代码 "+map.get("field_col_code")+" 已存在; ");
						failureNum++;
						continue;
					}
	        HrFiledData name = hrFiledDataMapper.queryByCodeName(saveMap);
					
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
					hrFiledDataMapper.addBatch(saveList);
					hrFiledDataMapper.updateBatchLast(saveList);
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
	public List<Map<String, Object>> queryHrFiledDataByPrint(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrFiledTabStrucMapper.queryHrFiledDataByPrint(entityMap);
		return list ;
	}

	@Override
	public String queryFiledTypeSelect(Map<String, Object> mapVo)
			throws DataAccessException {
		List<Map<String, Object>> list = hrFiledTabStrucMapper.queryFiledTypeSelect(mapVo);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String updateHrfiledView(Map<String, Object> entityMap)
			throws DataAccessException {

		
		try {
			
		  int state = hrFiledTabStrucMapper.updateHrfiledView(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage());
			return "{\"error\":\"更新失败！\"}";
		}	
	}

	@Override
	public HrFiledTabStruc queryHrFiledTabStruc(Map<String, Object> entityMap)
			throws DataAccessException {

		
		return hrFiledTabStrucMapper.queryHrFiledTabStruc(entityMap);
		
	
}

	@Override
	public String copyHrTableStrucByOld(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			//hrTableStrucMapper.copyTableTypeByOld(entityMap);
			//判断旧版代码表类型是否在新版代码表类型中存在
			List<HrFiiedTabType> oldtypeList = (List<HrFiiedTabType>) hrFiiedTabTypeMapper.query(entityMap);
			for(HrFiiedTabType oldtype:oldtypeList){
				entityMap.put("type_tab_code", oldtype.getType_filed_code());
				HrFiledTabType tableType = hrFiledTabTypeMapper.queryByCode(entityMap);
				if(tableType == null){
					throw new SysException("内置代码表分类"+ oldtype.getType_filed_code()+"未维护");
				}
			}
			
			List<Map<String, Object>> oldTableList = hrFiledTabStrucMapper.queryOldFiledTabStruc(entityMap);
			if(oldTableList.size() > 0){
				for (Map<String, Object> tab : oldTableList) {
					String field_tab_code = tab.get("FIELD_TAB_CODE").toString();
					entityMap.put("field_tab_code", field_tab_code);
					//查询旧代码表数据
					List<Map<String, Object>> fts = hrFiiedTabStrucMapper.queryHrFiiedData(entityMap);
					List<HrFiledData> listVo = new ArrayList<HrFiledData>();
					//批量添加至新代码表数据中
					for(Map<String, Object> col:fts){
						HrFiledData hfd = new HrFiledData();
						//判断是否存在
						List<Map<String, Object>> isExist = hrFiledTabStrucMapper.queryHrFiledData(col);
						if(isExist.size()>0) break;
						hfd.setGroup_id((long) Integer.parseInt(col.get("group_id").toString()));
						hfd.setHos_id((long)Integer.parseInt(col.get("hos_id").toString()));
						hfd.setField_tab_code((String) col.get("field_tab_code"));
						hfd.setField_col_code((String) col.get("field_col_code"));
						hfd.setField_col_name((String) col.get("field_col_name"));
						hfd.setSuper_col_code((String) col.get("super_col_code"));
						hfd.setSpell_code((String) col.get("spell_code"));
						hfd.setWbx_code((String) col.get("wbx_code"));
						if("是".equals(col.get("is_innr_text").toString())){
							hfd.setIs_innr(1);
						}else{
							hfd.setIs_innr(0);
						}
						
						if("是".equals(col.get("is_last_text").toString())){
							hfd.setIs_last(1);
						}else{
							hfd.setIs_last(0);
						}
						
						if("是".equals(col.get("is_stop_text").toString())){
							hfd.setIs_stop(1);
						}else{
							hfd.setIs_stop(0);
						}
						listVo.add(hfd);
						
					}	
					if(listVo.size()>0){
						hrFiledTabStrucMapper.saveHrFiledData(listVo);
					}
					
					tab.put("CITE_JSON", "");
					String related_sql = "";
					String query_sql = "";
					if(Integer.parseInt(tab.get("IS_CITE").toString()) == 1){
						HrFiiedView hrFiiedView = hrFiiedViewMapper.queryByCode(entityMap);
						if(hrFiiedView != null && hrFiiedView.getQuery_sql() != null){
							query_sql = hrFiiedView.getQuery_sql().replace("from",",'"+tab.get("FIELD_TAB_CODE")+"' field_tab_code from").replace("@group_id", "[group_id]").replace("@hos_id", "[hos_id]");
							String cite_sql =hrFiiedView.getCite_sql();
							cite_sql = cite_sql.replace("field_col_code", "id");  
							cite_sql = cite_sql.replace("field_col_name", "text");  
							cite_sql = cite_sql.replace("from",",'"+tab.get("FIELD_TAB_CODE")+"' field_tab_code from");
							cite_sql = cite_sql.replace("@group_id", "[group_id]");  
							cite_sql = cite_sql.replace("@hos_id", "[hos_id]");  
							related_sql =   cite_sql;                                            
						}
					}else{
						related_sql ="select  field_col_code   as id , field_col_name as text from HR_FILED_DATA where group_id =  [group_id] and hos_id = [hos_id]  and field_tab_code  = '"+field_tab_code+"'";
						query_sql =
								" select fd.group_id, fd.hos_id, fd.field_tab_code, fd.field_col_code, fd.field_col_name , fd.super_col_code , case    when fd.super_col_code is null then 'TOP' else sfd.field_col_name end as super_col_name, fd.spell_code, fd.wbx_code, fd.is_innr ,fd.is_stop, fd.is_last ,fd.note"
		            			+" from HR_FILED_DATA fd  left join HR_FILED_DATA sfd on fd.group_id = sfd.group_id and fd.hos_id = sfd.hos_id and fd.field_tab_code = sfd.field_tab_code and fd.super_col_code = sfd.field_col_code "          			 
		            			+"where fd.group_id = [group_id] and fd.hos_id = [hos_id] and fd.field_tab_code = '"+field_tab_code
		            			+"' order by fd.field_col_code ";
					}
					
					tab.put("RELATED_SQL", related_sql);
					
					
					tab.put("QUERY_SQL", query_sql);
					if(null == tab.get("NOTE")){
						tab.put("NOTE", "");
					}
					System.out.println(tab.get("GROUP_ID")+""+tab.get("HOS_ID")+tab.get("FIELD_TAB_CODE")+tab.get("type_tab_code")+tab.get("FIELD_TAB_NAME")+tab.get("IS_INNR")+tab.get("IS_CITE")+tab.get("CITE_JSON")+tab.get("QUERY_SQL")+tab.get("RELATED_SQL")+tab.get("NOTE"));
					
				}
				
				hrFiledTabStrucMapper.addBatch(oldTableList);	
				
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateFiledTabStrucSQL(Map<String, Object> entityMap)
			throws DataAccessException {

		try {
			HrFiledTabStruc hrFiledTabStruc=hrFiledTabStrucMapper.queryByCode(entityMap);
			
			entityMap.put("group_id", hrFiledTabStruc.getGroup_id());
			entityMap.put("hos_id", hrFiledTabStruc.getHos_id());
			entityMap.put("field_tab_code", hrFiledTabStruc.getField_tab_code());
			entityMap.put("field_tab_name", hrFiledTabStruc.getField_tab_name());
			entityMap.put("type_tab_code", hrFiledTabStruc.getType_tab_code());
			entityMap.put("is_innr", hrFiledTabStruc.getIs_innr());
			entityMap.put("is_cite", hrFiledTabStruc.getIs_cite());
			if(!"".equals(hrFiledTabStruc.getNote()) && null == hrFiledTabStruc.getNote()){
				entityMap.put("note", hrFiledTabStruc.getNote());
			}
			
			String cite_json;
			
			List<Map> allData = JSONArray.parseArray(entityMap.get("allData").toString(), Map.class);
			
			Map<String,String> filedColMap = new HashMap<String,String>();
			
			for(Map<String,Object> itemMap: allData){
				if("field_col_code".equals(itemMap.get("codeItem"))){
					if(filedColMap.get("field_col_code") !=null){
						filedColMap.put("field_col_code", filedColMap.get("field_col_code")+"||'@'||"+itemMap.get("colCode"));
					}else{
						filedColMap.put("field_col_code", itemMap.get("colCode").toString());
					}
					
				}
				if("field_col_name".equals(itemMap.get("codeItem"))){
					if(filedColMap.get("field_col_name") !=null){
						filedColMap.put("field_col_name", filedColMap.get("field_col_name")+"||' '||"+itemMap.get("colCode"));
					}else{
						filedColMap.put("field_col_name", "," + itemMap.get("colCode").toString());
					}
				}
				if("super_col_name".equals(itemMap.get("codeItem"))){
					if(filedColMap.get("colCode") !=null){
						filedColMap.put("super_col_name", ","+ itemMap.get("colCode").toString());
					}
					
				}
				if("is_last_text".equals(itemMap.get("codeItem"))){
					if(filedColMap.get("colCode") !=null){
						filedColMap.put("is_last_text", ","+"case " +itemMap.get("colCode")+"  when 1 then  '是'  when 0 then  '否' else '否' end");
					}
				}
				if("is_innr_text".equals(itemMap.get("codeItem"))){
					if(filedColMap.get("colCode") !=null){
						filedColMap.put("is_innr_text",  ","+"case " + itemMap.get("colCode")+" when 1 then  '是'  when 0 then  '否' else '否' end");
					}
				}
				if("is_stop_text".equals(itemMap.get("codeItem"))){
					if(filedColMap.get("colCode") !=null){
						filedColMap.put("is_stop_text",  ","+"case " + itemMap.get("colCode")+" when 1 then  '是'  when 0 then  '否' else '否' end");
					}
				}
				if("note".equals(itemMap.get("codeItem"))){
					if(filedColMap.get("colCode") !=null){
						filedColMap.put("note", ","+itemMap.get("colCode"));
					}
				}
			}
			filedColMap.put("field_tab_code",",'"+entityMap.get("field_tab_code")+"'");
			
			
			StringBuffer query_sql = new StringBuffer();
			StringBuffer related_sql = new StringBuffer();
			
			//拼接sql语句
			related_sql.append("select ");
			related_sql.append(filedColMap.get("field_col_code") +" id");
			related_sql.append(filedColMap.get("field_col_name") +" text");
			related_sql.append(" from "+entityMap.get("table_code")+" where group_id = [group_id] and hos_id = [hos_id] order by id");
			
			query_sql.append("select ");
			query_sql.append(filedColMap.get("field_col_code")+" field_col_code");
			query_sql.append(filedColMap.get("field_col_name")+" field_col_name");
			if(filedColMap.get("super_col_name") != null && filedColMap.get("super_col_name") != "")
			{
				query_sql.append(filedColMap.get("super_col_name")+" super_col_name");
			}else{
				query_sql.append(",'TOP' super_col_name");
			}
			if(filedColMap.get("is_last_text") != null && filedColMap.get("is_last_text") != "")
			{
				query_sql.append(filedColMap.get("is_last_text")+" is_last_text");
			}
			if(filedColMap.get("is_innr_text") != null && filedColMap.get("is_innr_text") != "")
			{
				query_sql.append(filedColMap.get("is_innr_text")+" is_innr_text");
			}else{
				query_sql.append(",'否' is_innr_text");
			}
			if(filedColMap.get("is_stop_text") != null && filedColMap.get("is_stop_text") != "")
			{
				query_sql.append(filedColMap.get("is_stop_text")+" is_stop_text");
			}else{
				query_sql.append(",'否' is_stop_text");
			}
			if(filedColMap.get("note") != null && filedColMap.get("note") != "")
			{
				query_sql.append(filedColMap.get("note")+" note");
			}
			query_sql.append(filedColMap.get("field_tab_code")+" field_tab_code");
			
			query_sql.append(" from "+entityMap.get("table_code")+" where group_id = [group_id] and hos_id = [hos_id] order by field_col_code");
			

			
			cite_json=(String) entityMap.get("allData");
			entityMap.put("cite_json", cite_json);
			
			
			if(entityMap.get("related_sql") == null || entityMap.get("related_sql") == "" ){
				entityMap.put("related_sql", related_sql.toString());
			}
			if(entityMap.get("query_sql")  == null || entityMap.get("query_sql") == ""  ){
				entityMap.put("query_sql", query_sql.toString());
		    }
		    int state = hrFiledTabStrucMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 updateFiledTabStrucSQL\"}";

		}	
	}

	@Override
	public String queryFiledColsByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		HrFiledTabStruc hrFiledTabStruc = hrFiledTabStrucMapper.queryHrFiledTabStruc(entityMap);
		if (hrFiledTabStruc != null) {
			List<HrTableColumn> data = hrFiledTabStruc.getTableColumn();
			return ChdJson.toJson(data);
		}
		return "{\"Total\":0,\"Rows\":[]}";
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public String queryDBTableTree(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String,Object>> tree = hrTableDesignMapper.queryDBTableTree(entityMap);
	
		if(!"null".equals(entityMap.get("tab_code"))&& !"".equals(entityMap.get("tab_code"))){
			for (Map<String, Object> t : tree) {
					if(t.get("tab_code").equals(entityMap.get("tab_code"))){
						t.put("ischecked", true);
					}
			}
		}
		
		return JSONArray.toJSONString(tree);
	}

	public Map<String, Object>  matchAndReplaceSql(String sql,Map<String,Object> paraMap){
		Map<String, Object>  map=new HashMap<String, Object>();
		String result = sql        					//将所有字符都转成小写 为分割处理做准备
			   	   .replaceAll("\n|\t|\r|\\s{1,}", " ");	//去掉所有换行符 制表符 多个空格转换为单个空格  
		Matcher matcher = Pattern.compile("\\[.*?\\]").matcher(result);//#\\{.*?\\}
		//(@)(.*?),
		String value=null;
		while (matcher.find()) {
			
			String key = matcher.group() ;//获取匹配到的参数
			
			String column = key.replaceAll("\\[", "").replaceAll("\\]","");
			
			if(paraMap.get(column.toLowerCase()) == null){
				value="";
			}else{
			
			 value = String.valueOf(paraMap.get(column.toLowerCase()));
			}
			result = result.replace(key, "'" + value + "'");
			
		}
		map.put("result", result);
		
		return map;
	}

	//导出
	@Override
	public String queryHrFiledTabStrucExport(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map> list = new ArrayList<Map>();
		List<HrFiledTabStruc> hrFiledTablist = (List<HrFiledTabStruc>) hrFiledTabStrucMapper.queryHrFiledTabStrucExport(entityMap);
		
		//如果表中字段没有值就不导出改字段
		for(HrFiledTabStruc hrFiledTab:hrFiledTablist){
			Map<String,String> tabMap = new HashMap<String, String>();
			tabMap.put("FILED_TABLE_STRUC", JSONArray.toJSONString(hrFiledTab, true));
			list.add(tabMap);
			
//			if(hrFiledTab.getIs_cite() != 1){
//				entityMap.put("field_tab_code", hrFiledTab.getField_tab_code());
//				List<HrFiledData> hrFiledDataList = (List<HrFiledData>) hrFiledDataMapper.queryHrFiledDataExport(entityMap);
//				for(HrFiledData hrFiledData:hrFiledDataList){
//					Map<String,String> filedMap = new HashMap<String, String>();
//					filedMap.put("FILED_DATA", JSONArray.toJSONString(hrFiledData, true));
//					list.add(filedMap);
//				}
//			}
		}
	
		
	
		return JSONArray.toJSONString(list, true);
	}
	
  //导入
	@Override
	public void readJsonFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode,
			Map<String, Object> entityMap) throws DataAccessException, IOException {
		
		List<Object> list_err = new ArrayList<Object>();
		
		try {
			plupload.setRequest(request);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) plupload.getRequest();   
	        MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();  
	        if(map != null) {
	        	Iterator<String> iter = map.keySet().iterator();  
	            while(iter.hasNext()) {  
	                String str = (String) iter.next();  
	                List<MultipartFile> fileList =  map.get(str); 
	                for(MultipartFile multipartFile : fileList) {  
	                	StringBuffer strBuf = new StringBuffer();
	                	String charset = FileUtil.codeString(multipartFile.getInputStream());
	                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), charset));
	                    int tempchar;
	                    while ((tempchar = bufferedReader.read()) != -1) {
	                        strBuf.append((char) tempchar);
	                    }
	                    bufferedReader.close();
	                    String json = strBuf.toString();
                		
                		Object object = JSON.parse(json);
                		if (!(object instanceof JSONArray)) {
                			response.getWriter().print("{\"error\":\"数据格试错误,要求JSONArray格式\"}");
                	
                		}
                			
                		List<Map> list = JSONArray.parseArray(json,Map.class);
                		List<Map<String,Object>>  filedTabStrucList = new ArrayList<Map<String,Object>>();
                		List<Map<String,Object>>  filedDataList = new ArrayList<Map<String,Object>>();
                		HrFiledTabStruc filedTabStruc = new HrFiledTabStruc();
                		HrFiledData filedData = new HrFiledData();
                		
                		if(list.size()==0){
                			response.getWriter().print("{\"error\":\"未插入数据！\"}");
                
                		}
                		
                		for (Map<String,String> item: list) {
                			StringBuffer err_sb = new StringBuffer();
                			
                			if(item.get("FILED_TABLE_STRUC") != null){
                				Map<String,Object> filedTabStrucMap = JSONObject.parseObject(item.get("FILED_TABLE_STRUC").toString(), new TypeReference<Map<String, Object>>(){});

                    			HrFiledTabStruc struc = hrFiledTabStrucMapper.queryByCodePrint(filedTabStrucMap);
                    			if(struc != null){
                    				err_sb.append("代码表编码" + struc.getField_tab_code() + "已存在！");
                    			}else{
                    				filedTabStrucMap.put("group_id", SessionManager.getGroupId());
                    				filedTabStrucMap.put("hos_id", SessionManager.getHosId());
                    				hrFiledTabStrucMapper.add(filedTabStrucMap);
                    			}
                    			
                    			if(err_sb.length() > 0){
                    				struc.setError_type(err_sb.toString());
                    				list_err.add(struc);
                    			}
                			}
                			if(item.get("FILED_DATA") !=null){
                				
                				Map<String,Object> filedDataMap = JSONObject.parseObject(item.get("FILED_DATA").toString(), new TypeReference<Map<String, Object>>(){});

								HrFiledData data = hrFiledDataMapper .queryByCodePrint(filedDataMap);
                    			if(data != null){
//                    				err_sb.append("代码数据表代码项编码" + data.getField_col_code() + "已存在！");
                    			}else{
                    				filedDataMap.put("group_id", SessionManager.getGroupId());
                    				filedDataMap.put("hos_id", SessionManager.getHosId());
                    				hrFiledDataMapper.add(filedDataMap);
                    			}
                    			

                    			if(err_sb.length() > 0){
                    				data.setError_type(err_sb.toString());
                    				list_err.add(data);
                    			}
                    			
                			}
	                    }
                		
//                		if(list_err.size() == 0){
//                			hrFiledTabStrucMapper.addBatch(filedTabStrucList);
//                			hrFiledDataMapper.addBatch(filedDataList);
//                		}
	                }
	            }
	        }
			
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
	}
	

}
