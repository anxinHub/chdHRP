/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.info.basic;

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
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.med.dao.info.basic.MedLocationDictMapper;
import com.chd.hrp.med.dao.info.basic.MedLocationTypeMapper;
import com.chd.hrp.med.entity.MedLocationDict;
import com.chd.hrp.med.entity.MedLocationType;
import com.chd.hrp.med.service.info.basic.MedLocationDictService;
import com.chd.hrp.sys.dao.EmpDictMapper;
import com.chd.hrp.sys.dao.StoreMapper;
import com.chd.hrp.sys.entity.EmpDict;
import com.chd.hrp.sys.entity.Store;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）
 * @Table:
 * MED_LOCATION_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medLocationDictService")
public class MedLocationDictServiceImpl implements MedLocationDictService {

	private static Logger logger = Logger.getLogger(MedLocationDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medLocationDictMapper")
	private final MedLocationDictMapper medLocationDictMapper = null;
	
	//引入DAO操作
	@Resource(name = "medLocationTypeMapper")
	private final MedLocationTypeMapper medLocationTypeMapper = null;
	
	//引入DAO操作
	@Resource(name = "storeMapper")
	private final StoreMapper storeMapper = null;
	
	//引入DAO操作
	@Resource(name = "empDictMapper")
	private final EmpDictMapper empDictMapper = null;
    
	/**
	 * @Description 
	 * 添加货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
	/*	获取对象货位性质 LOCATION_NATURE
		0、固定货位
		1、自由货位
		
		货位控制方式：
		0、不控制
		1、提示控制
		2、强制控制（入库不可以修改货位）
	*/
		MedLocationDict medLocationDict = queryByCode(entityMap);

		if (medLocationDict != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}else{
			
		}
		
		try {
			
			int state = medLocationDictMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedLocationDict\"}";

		}
		
	}
	/**
	 * @Description 
	 * /*	获取对象货位性质 LOCATION_NATURE
		0、固定货位
		1、自由货位
		
		货位控制方式：
		0、不控制
		1、提示控制
		2、强制控制（入库不可以修改货位）
	*/
	
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medLocationDictMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedLocationDict\"}";

		}
		
	}
	
		/**
	 * @Description 
	 *	获取对象货位性质 LOCATION_NATURE
		0、固定货位
		1、自由货位
		
		货位控制方式：
		0、不控制
		1、提示控制
		2、强制控制（入库不可以修改货位）

	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = medLocationDictMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMedLocationDict\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 	获取对象货位性质 LOCATION_NATURE
		0、固定货位
		1、自由货位
		
		货位控制方式：
		0、不控制
		1、提示控制
		2、强制控制（入库不可以修改货位）
	<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  medLocationDictMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMedLocationDict\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除货位性质 LOCATION_NATURE
0、固定货位
1、自由货位

货位控制方式：
0、不控制
1、提示控制
2、强制控制（入库不可以修改货位）<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medLocationDictMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedLocationDict\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medLocationDictMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedLocationDict\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象货位性质 LOCATION_NATURE
/*1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）*/
		MedLocationDict medLocationDict = queryByCode(entityMap);

		if (medLocationDict != null) {

			int state = medLocationDictMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medLocationDictMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedLocationDict\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedLocationDict> list = (List<MedLocationDict>) medLocationDictMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedLocationDict> list = (List<MedLocationDict>) medLocationDictMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T>T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return medLocationDictMapper.queryByCode(entityMap);
	}
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return medLocationDictMapper.queryByUniqueness(entityMap);
	}
	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public String importMedLocationDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		
		try {
			
			//1.判断表头是否为空
			String columns=entityMap.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);	
			if(jsonColumns==null || jsonColumns.size()==0){
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}
			
			//2.判断数据是否为空
			String content=entityMap.get("content").toString();
			List<Map<String,List<String>>> allDataList=SpreadTableJSUtil.toListMap(content,1);
			if(allDataList==null || allDataList.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			
			
			Map<String,Object> locationDictMap = new HashMap<String, Object>();//存储货位Map
			Map<String,Object> locationTypeMap = new HashMap<String, Object>();//存储货位分类Map
			Map<String,Object> storeMap = new HashMap<String, Object>();//存储所属仓库Map
			Map<String,Object> empMap = new HashMap<String, Object>();//存储拣货员Map
			
			Map<String, Object> locationDictQueryMap = new HashMap<String, Object>();//查询货位字典Map
			locationDictQueryMap.put("group_id", SessionManager.getGroupId());
			locationDictQueryMap.put("hos_id", SessionManager.getHosId());
			locationDictQueryMap.put("copy_code", SessionManager.getCopyCode());
			locationDictQueryMap.put("is_stop", "0");
			
			List<MedLocationDict> medLocationDictList = (List<MedLocationDict>)medLocationDictMapper.query(locationDictQueryMap);
			for(MedLocationDict medLocationDict : medLocationDictList){
				locationDictMap.put(medLocationDict.getLocation_code(), medLocationDict.getLocation_code());
			}
			
			Map<String, Object> locationTypeQueryMap = new HashMap<String, Object>();//查询货位分类Map
			locationTypeQueryMap.put("group_id", SessionManager.getGroupId());
			locationTypeQueryMap.put("hos_id", SessionManager.getHosId());
			locationTypeQueryMap.put("copy_code", SessionManager.getCopyCode());
			locationTypeQueryMap.put("is_stop", "0");
			
			List<MedLocationType> medLocationTypeList = (List<MedLocationType>)medLocationTypeMapper.query(locationTypeQueryMap);
			for(MedLocationType medLocationType :medLocationTypeList){
				locationTypeMap.put(medLocationType.getLocation_type_code(), medLocationType.getLocation_type_id());
				locationTypeMap.put(medLocationType.getLocation_type_name(), medLocationType.getLocation_type_id());
			}
			
			Map<String, Object> storeQueryMap = new HashMap<String, Object>();//查询所属库房Map 
			storeQueryMap.put("group_id", SessionManager.getGroupId());
			storeQueryMap.put("hos_id", SessionManager.getHosId());
			storeQueryMap.put("copy_code", SessionManager.getCopyCode());
			storeQueryMap.put("is_stop", "0");
			storeQueryMap.put("is_med", "1");
			
			List<Store> storeList = storeMapper.queryStore(storeQueryMap);
			for(Store store : storeList){
				storeMap.put(store.getStore_code(), store.getStore_id());
				storeMap.put(store.getStore_name(), store.getStore_id());
			}
			
			Map<String, Object> empQueryMap = new HashMap<String, Object>();//查询拣货员Map 
			empQueryMap.put("group_id", SessionManager.getGroupId());
			empQueryMap.put("hos_id", SessionManager.getHosId());
			empQueryMap.put("is_stop", "0");
			
			List<EmpDict> empList = empDictMapper.queryEmpDict(empQueryMap);
			for(EmpDict empDict : empList){
				empMap.put(empDict.getEmp_code(), empDict.getEmp_id().toString());
				empMap.put(empDict.getEmp_name(), empDict.getEmp_id().toString());
			}
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			
			// 用于存储传的数据值,判断数据是否重复
			Map<String, String> exitMap = new HashMap<String, String>();
			// 用于记录重复数据
			StringBuffer err_sb = new StringBuffer();
			
			
			// 遍历导入数据
			for (Map<String, List<String>> item : allDataList) {
				
				List<String> location_code = item.get("货位编码");
				List<String> location_name = item.get("货位名称");
				List<String> location_type_code = item.get("货位分类");
				List<String> grid_no = item.get("排序号");
				List<String> store = item.get("所属库房");
				List<String> location_nature = item.get("货位性质");
				List<String> ctrl_type = item.get("控制方式");
				List<String> picker = item.get("拣货员");
				List<String> is_stop = item.get("是否停用");
				List<String> note = item.get("备注");
				
				
				if (location_code.get(1) == null) {
					return "{\"warn\":\"货位编码为空！\",\"state\":\"false\",\"row_cell\":\"" + location_code.get(0) + "\"}";
				}else{
					if(locationDictMap.get(location_code.get(1)) != null){
						return "{\"warn\":\"货位编码已经存在！\",\"state\":\"false\",\"row_cell\":\"" + location_code.get(0) + "\"}";
					}
				}
				
				if (location_name.get(1) == null) {
					return "{\"warn\":\"货位名称为空！\",\"state\":\"false\",\"row_cell\":\"" + location_name.get(0) + "\"}";
				}

				if (location_type_code.get(1) == null) {
					return "{\"warn\":\"货位分类为空！\",\"state\":\"false\",\"row_cell\":\"" + location_type_code.get(0) + "\"}";
				} else {
					if (locationTypeMap.get(location_type_code.get(1)) == null) {
						return "{\"warn\":\"" + location_type_code.get(1) + ",货位分类不存在！\",\"state\":\"false\",\"row_cell\":\"" + location_type_code.get(0) + "\"}";
					}
				}
				
				if (grid_no.get(1) == null) {
					return "{\"warn\":\"排序号为空！\",\"state\":\"false\",\"row_cell\":\"" + grid_no.get(0) + "\"}";
				}
				
				if (store.get(1) == null) {
					return "{\"warn\":\"所属库房为空！\",\"state\":\"false\",\"row_cell\":\"" + store.get(0) + "\"}";
				} else {
					if (storeMap.get(store.get(1)) == null) {
						return "{\"warn\":\"" + store.get(1) + ",所属库房不存在！\",\"state\":\"false\",\"row_cell\":\"" + store.get(0) + "\"}";
					}
				}
				
				if (location_nature.get(1) == null) {
					return "{\"warn\":\"货位性质为空！\",\"state\":\"false\",\"row_cell\":\"" + location_nature.get(0) + "\"}";
				}
				
				if (ctrl_type.get(1) == null) {
					return "{\"warn\":\"控制方式为空！\",\"state\":\"false\",\"row_cell\":\"" + ctrl_type.get(0) + "\"}";
				}
				
				if (picker.get(1) == null) {
					return "{\"warn\":\"拣货员为空！\",\"state\":\"false\",\"row_cell\":\"" + picker.get(0) + "\"}";
				} else {
					if (empMap.get(picker.get(1)) == null) {
						return "{\"warn\":\"" + picker.get(1) + ",拣货员不存在！\",\"state\":\"false\",\"row_cell\":\"" + picker.get(0) + "\"}";
					}
				}
				
				if (is_stop.get(1) == null) {
					return "{\"warn\":\"是否停用为空！\",\"state\":\"false\",\"row_cell\":\"" + is_stop.get(0) + "\"}";
				}
				
				
				// 以职工编码+职工名称+科室名称+职务名称为键值,判断导入数据是否重复
				String key = location_code.get(1);
				if (exitMap.get(key) != null) {
					err_sb.append(location_code.get(1) + "货位编码");
				} else {
					exitMap.put(key, key);
				}
				
				Map<String,Object> addMap = new HashMap<String,Object>();
				addMap.put("group_id", entityMap.get("group_id"));
				addMap.put("hos_id", entityMap.get("hos_id"));
				addMap.put("copy_code", entityMap.get("copy_code"));
				addMap.put("location_code", location_code.get(1));
				addMap.put("location_name", location_name.get(1));
				addMap.put("spell_code", StringTool.toPinyinShouZiMu(location_name.get(1)));
				addMap.put("wbx_code", StringTool.toWuBi(location_name.get(1)));
				addMap.put("grid_no", grid_no.get(1));
				addMap.put("location_type_id", locationTypeMap.get(location_type_code.get(1)));
				addMap.put("store_id", storeMap.get(store.get(1)));
				addMap.put("location_nature", location_nature.get(1));
				addMap.put("ctrl_type", ctrl_type.get(1));
				addMap.put("picker", empMap.get(picker.get(1)));
				addMap.put("is_stop",is_stop.get(1));
				if (note.get(1) == null) {
					addMap.put("note", "");
				}else{
					addMap.put("note", note);
				}
				
				addList.add(addMap);
			}
			
			if (err_sb.length() > 0) {// 重复数据是否存在
				return "{\"warn\":\"以下数据【" + err_sb.toString() + "】编码重复！\",\"state\":\"false\"}";
			}
			
			medLocationDictMapper.addBatch(addList);
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}
	
	/**
	 * @Description 
	 * 获取货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedLocationDict
	 * @throws DataAccessException
	*/
	
	
}
