/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.serviceImpl.sysstruc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.base.BaseCRUDMapper;
import com.chd.hrp.hr.dao.base.CreateTablesMapper;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.dao.sysstruc.HrColStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFiiedTabStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrStoreColSetMapper;
import com.chd.hrp.hr.dao.sysstruc.HrStoreQueSetMapper;
import com.chd.hrp.hr.entity.sysstruc.HrColStruc;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedTabStruc;
import com.chd.hrp.hr.service.base.HrTableManagerService;
import com.chd.hrp.hr.service.sysstruc.HrColStrucService;

/**
 * 
 * @Description: 系统结构列名
 * @Colle: HR_COL_STRUC
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("hrColStrucService")
public class HrColStrucServiceImpl implements HrColStrucService {

	private static Logger logger = Logger.getLogger(HrColStrucServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "hrColStrucMapper")
	private final HrColStrucMapper hrColStrucMapper = null;

	@Resource(name = "createTablesMapper")
	private final CreateTablesMapper createTablesMapper = null;

	@Resource(name = "baseCRUDMapper")
	private final BaseCRUDMapper baseCRUDMapper = null;
	
	@Resource(name = "hrTableManagerService")
	private final HrTableManagerService hrTableManagerService = null;
	
	@Resource(name = "hrStoreColSetMapper")
	private final HrStoreColSetMapper hrStoreColSetMapper = null;
	
	@Resource(name = "hrStoreQueSetMapper")
	private final HrStoreQueSetMapper hrStoreQueSetMapper = null;
	
	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	

	@Override
	public String addOrUpdateHrColStruc(Map<String, Object> entityMap) throws DataAccessException {
		try {
			// 添加数据集
			List<HrColStruc> addlistVo = JSONArray.parseArray(String.valueOf(entityMap.get("addData")),HrColStruc.class);
			// 更新数据集
			List<HrColStruc> modlistVo = JSONArray.parseArray(String.valueOf(entityMap.get("modData")),HrColStruc.class);
			
			if (addlistVo.size() > 0) {
				addlistVo = getSaveData(addlistVo, entityMap,true);
				if (addlistVo.size() > 0) {
					hrColStrucMapper.addBatch(addlistVo);
				}
			}

			if (modlistVo.size() > 0) {
				modlistVo = getSaveData(modlistVo, entityMap,false);
				if (modlistVo.size() > 0) {
					hrColStrucMapper.updateBatch(modlistVo);
				}
			}

			// 更新表结构 
			hrTableManagerService.createOrModifyTableConstruct(entityMap);
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 组装保存数据
	 * 
	 * @param list
	 * @param entityMap
	 * @return
	 */
	private List<HrColStruc> getSaveData(List<HrColStruc> list, Map<String, Object> entityMap,Boolean isAdd) {
		String tab_code = entityMap.get("tab_code").toString();// 表名
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("tab_code", tab_code);
		Map<String,Object> codeNameMap = new HashMap<String,Object>();
		List<Map<String,Object>> colList =ChdJson.toListLower(hrColStrucMapper.queryByTabCode(entityMap));
		for(Map<String,Object> cMap : colList){
			codeNameMap.put(cMap.get("col_name").toString(), cMap.get("col_code"));
			codeNameMap.put(cMap.get("col_code").toString(), cMap.get("col_code"));
			codeNameMap.put(cMap.get("sort").toString(), cMap.get("col_code"));
		}
				
		List<HrColStruc> reList = new ArrayList<HrColStruc>();

		for (HrColStruc hrColStruc : list) {

			if (hrColStruc != null && StringUtils.isNotBlank(hrColStruc.getCol_code())) {
				
				if(hrColStruc.getCol_code() == null || StringUtils.isBlank(hrColStruc.getCol_code())){
					throw new SysException("数据列编码不能为空");
				}
				
				if(hrColStruc.getCol_code().length() > 30){
					throw new SysException("数据列编码长度最大值为30个字符");
				}
				
				if(hrColStruc.getCol_name() == null || StringUtils.isBlank(hrColStruc.getCol_name())){
					throw new SysException("数据列名称不能为空");
				}
				
				if(hrColStruc.getData_type() == null || StringUtils.isBlank(hrColStruc.getData_type())){
					throw new SysException("类型不能为空");
				}
				
				if(!"DATE".equals(hrColStruc.getData_type()) && (hrColStruc.getFiled_length() == null || hrColStruc.getFiled_length() <= 0)){
					throw new SysException("长度不能为空且大于0");
				} else {
					if("VARCHAR2".equalsIgnoreCase(hrColStruc.getData_type()) && hrColStruc.getFiled_length() > 4000){
						throw new SysException("字符类型长度超出范围(1 到 4000)");
					}
					
					if("NUMBER".equalsIgnoreCase(hrColStruc.getData_type()) && hrColStruc.getFiled_length() > 38){
						throw new SysException("数值类型长度超出范围 (1 到 38)");
					}
					
				}
				
				if(hrColStruc.getPrec() != null && (hrColStruc.getPrec() < -84 || hrColStruc.getPrec() > 127)){
					throw new SysException("数值类型长度超出范围 (-84 到 127)");
				}
				
				hrColStruc.setGroup_id(Integer.parseInt(entityMap.get("group_id").toString()));
				hrColStruc.setHos_id(Integer.parseInt(entityMap.get("hos_id").toString()));
				//hrColStruc.setCopy_code(entityMap.get("copy_code").toString());
				hrColStruc.setTab_code(tab_code);
				
				if(isAdd){
					if(codeNameMap.get(hrColStruc.getCol_code())!=null &&  !"".equals(codeNameMap.get(hrColStruc.getCol_code()))){
						throw new SysException("存在重复数据列编码！");
					}
					if(codeNameMap.get(hrColStruc.getCol_name())!=null && !"".equals(codeNameMap.get(hrColStruc.getCol_name()))){
						throw new SysException("存在重复数据列名称！");
					}
					if(codeNameMap.get(hrColStruc.getSort().toString())!=null &&  !"".equals(codeNameMap.get(hrColStruc.getSort().toString()))){
						throw new SysException("存在重复排序号！");
					}
				}
				

				Integer fileTypeLength = handleFileTypeLength(hrColStruc.getData_type());

				if (fileTypeLength == 1) {
					hrColStruc.setFiled_length(hrColStruc.getFiled_length() == null || hrColStruc.getFiled_length() <= 0
							? 1 : hrColStruc.getFiled_length());
				}

				if (fileTypeLength == 2) {
					hrColStruc.setFiled_length(hrColStruc.getFiled_length() == null || hrColStruc.getFiled_length() <= 0
							? 1 : hrColStruc.getFiled_length());
					hrColStruc.setPrec(hrColStruc.getPrec() == null ? 0 : hrColStruc.getPrec());
				}

				hrColStruc.setFileTypeLength(fileTypeLength);

				reList.add(hrColStruc);
			}

		}

		return reList;
	}


	/**
	 * 处理表结构字段格式 0:filedname filedtype,1:filedname filedtype(length),2:filedname
	 * filedtype(length,prec)
	 * 
	 * @param dataType
	 * @return
	 */
	private Integer handleFileTypeLength(String dataType) {
		int fileTypeLength = 0;
		List<Map<String, Object>> dataTypeList = hrColStrucMapper.queryDataType(new HashMap<String, Object>());
		Map<String, Object> dataTypeMap = new HashMap<String, Object>();
		for (Map<String, Object> map : dataTypeList) {
			dataTypeMap.put(map.get("data_type_code").toString(), map.get("length_count").toString());
		}

		if (dataTypeMap != null && dataTypeMap.get(dataType) != null) {
			fileTypeLength = Integer.parseInt(dataTypeMap.get(dataType).toString());
		}

		return fileTypeLength;

	}

	@Override
	public String deleteBatchHrColStruc(String tab_code, List<HrColStruc> entityList) throws DataAccessException {
		try {
			//如果数据表存在数据，不允许修改表结构
			//int count = baseCRUDMapper.queryCountByCustomSql(tab_code);
			
			//if(count > 0){
				//throw new SysException("表存在数据，不允许修改表结构");
			//}
			
			for (HrColStruc col : entityList) {
				if(col.getIs_innr() != null && col.getIs_innr() == 1){
					throw new SysException("内置字段不允许删除");
				}
			}
		
			int state = hrColStrucMapper.deleteBatchHrColStruc(entityList);
			
			hrStoreColSetMapper.deleteBatchByColStruc(entityList);
			hrStoreQueSetMapper.deleteBatchByColStruc(entityList);

			if (state > 0) {
				Map<String, Object> entityMap = new HashMap<String, Object>();
				entityMap.put("group_id", SessionManager.getGroupId());
				entityMap.put("hos_id", SessionManager.getHosId());
				entityMap.put("copy_code", SessionManager.getCopyCode());
				entityMap.put("tab_code", tab_code);
				hrTableManagerService.createOrModifyTableConstruct(entityMap);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryHrColStruc(Map<String, Object> entityMap) throws DataAccessException {
		List<HrColStruc> list = hrColStrucMapper.queryHrColStrucEntity(entityMap);
		return ChdJson.toJson(list);
	}

	@Override
	public HrColStruc queryHrColStrucByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrColStrucMapper.queryByCode(entityMap);
	}
	
	/**
	 * 导入
	 */
	@Override
	public String importExcel(Map<String, Object> entityMap) throws DataAccessException {
		//判断是否的map
		Map<String, Object> isNotMap = new HashMap<String, Object>();
		isNotMap.put("0", "0");
		isNotMap.put("1", "1");
		isNotMap.put("否", "0");
		isNotMap.put("是", "1");
		
	    //判断类型的map   
		Map<String, Object> typeMap = new HashMap<String, Object>();
		List<Map<String,Object>> typeList = hrColStrucMapper.queryDataType(entityMap);
		for(Map<String,Object> map: typeList){
			typeMap.put(map.get("data_type_code").toString(),map.get("data_type_code"));
			typeMap.put(map.get("data_type_name").toString(),map.get("data_type_code"));
		}
		
		//判断代码表
		List<Map<String,Object>> tabList=hrSelectMapper.queryHrFiiedTabStrucDic(entityMap);
		Map<String, Object> tabMap = new HashMap<String, Object>();
		for(Map<String,Object> mmap: tabList){
			tabMap.put(mmap.get("field_tab_code").toString(),mmap.get("field_tab_code"));
			tabMap.put(mmap.get("field_tab_name").toString(),mmap.get("field_tab_code"));
		}
		
		int successNum = 0;
		int countNum = 1;
		boolean flag=true;
		StringBuilder failureMsg = new StringBuilder();
		List<HrColStruc> saveList = new ArrayList<HrColStruc>();
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					HrColStruc saveMap = new HrColStruc();
					saveMap.setGroup_id(Integer.parseInt(SessionManager.getGroupId().toString()));
					saveMap.setHos_id(Integer.parseInt(SessionManager.getHosId().toString()));
					//saveMap.setCopy_code(SessionManager.getCopyCode().toString());
					saveMap.setTab_code(entityMap.get("tab_code").toString().toString());
				
					failureMsg.append("<br/> 第"+countNum+"行 ");
					//数据列字段
					if(map.get("col_code").get(1)!=null && map.get("col_code").get(1)!=""){
						if(map.get("col_code").get(1).toUpperCase().length() > 30){
							failureMsg.append(" 数据列编码长度最大值为30个字符!");
							flag=false;
						}else if(!map.get("col_code").get(1).toString().toUpperCase().matches("[a-zA-Z0-9_]*")){
							failureMsg.append(" 数据列编码非法，请重新命名!");
							flag=false;
						}else{
							saveMap.setCol_code(map.get("col_code").get(1).toUpperCase());
							
							HrColStruc colStruc  = hrColStrucMapper.queryByColCode(saveMap);
							if(colStruc != null){
								failureMsg.append("数据列字段已存在！ ");
								flag=false;
								countNum++;
								continue;
							}
						}
					}else{
						failureMsg.append("数据列字段不能为空！");
						flag=false;
					}
					
					//数据列名称
					if(map.get("col_name").get(1)!=null && map.get("col_name").get(1)!=""){
						saveMap.setCol_name(map.get("col_name").get(1));
					}else{
						failureMsg.append("数据列名称字段不能为空！");
						flag=false;
					}
					
					//数据类型
					if(map.get("data_type_name")!=null && map.get("data_type_name").get(1)!=""){
						if(typeMap.get(map.get("data_type_name").get(1).toString())!=null){
							String typeName = typeMap.get(map.get("data_type_name").get(1).toString()).toString();
							if("DATA".equals(typeName) && (map.get("length")== null || Integer.parseInt(map.get("length").get(1).toString()) <= 0)){
								failureMsg.append("日期类型的长度不能为空且大于0！");
								flag=false;
							}else if("VARCHAR2".equalsIgnoreCase(typeName) && Integer.parseInt(map.get("length").get(1).toString()) > 4000){
								failureMsg.append("字符类型长度超出范围(1 到 4000)！");
								flag=false;
							}else if("NUMBER".equalsIgnoreCase(typeName) && Integer.parseInt(map.get("length").get(1).toString()) > 38){
								failureMsg.append("数值类型长度超出范围 (1 到 38)");
								flag=false;
							}else{
								saveMap.setData_type(typeMap.get(map.get("data_type_name").get(1).toString()).toString());
							}
						}else{
							failureMsg.append("数据类型不存在！");
							flag=false;
						}
					}else{
						failureMsg.append("数据列类型字段不能为空！");
						flag=false;
					}
					
					//是否主键
					if(map.get("is_pk").get(1)!=null && map.get("is_pk").get(1).length()>0){
						String isNot = isNotMap.get(map.get("is_pk").get(1).toString()).toString();
						if(isNotMap.get(isNot)!=null){
							saveMap.setIs_pk(Integer.parseInt(isNotMap.get(isNot).toString()));
						}else{
							failureMsg.append("是否主键值不存在！ ");
							flag=false;
						}
					}
					
					//是否必填项
					//System.out.println(map.get("is_m").get(1).length());
					if(map.get("is_m").get(1)!=null && map.get("is_m").get(1)!="" && map.get("is_m").get(1).length()>0){
						String isNot = isNotMap.get(map.get("is_m").get(1).toString()).toString();
						if(isNotMap.get(isNot)!=null){
							saveMap.setIs_m(Integer.parseInt(isNotMap.get(isNot).toString()));
						}else{
							failureMsg.append("是否必填项值不存在！ ");
							flag=false;
						}
					}
					
					//是否内置
					//System.out.println(map.get("is_innr").get(1).length());
					if(map.get("is_innr").get(1)!=null && map.get("is_innr").get(1)!="" && map.get("is_innr").get(1).length()>0){
						String isNot = isNotMap.get(map.get("is_innr").get(1).toString()).toString();
						if(isNotMap.get(isNot)!=null){
							saveMap.setIs_innr(Integer.parseInt(isNotMap.get(isNot).toString()));
						}else{
							failureMsg.append("是否内置值不存在！ ");
							flag=false;
						}
					}
					
					//长度
					//System.out.println(map.get("length").get(1));
					if(map.get("length").get(1)!=null && map.get("length").get(1)!="" && map.get("length").get(1).length()>0){
						saveMap.setFiled_length(Integer.parseInt(map.get("length").get(1).toString()));
					}
					
					//精度
					//System.out.println(map.get("prec").get(1).length());
					if(map.get("prec").get(1)!=null && map.get("prec").get(1)!="" && map.get("prec").get(1).length()>0){
						if(Integer.parseInt(map.get("prec").get(1).toString())< -84 || Integer.parseInt(map.get("prec").get(1).toString())> 127){
							failureMsg.append("数值类型长度超出范围 (-84 到 127)");
							flag=false;
						}else{
							saveMap.setPrec(Integer.parseInt(map.get("prec").get(1).toString()));
						}
					}
					
					//代码表
					if(map.get("field_tab_code").get(1)!=null && map.get("field_tab_code").get(1)!="" && map.get("field_tab_code").get(1).length()>0){
						String isNot = tabMap.get(map.get("field_tab_code").get(1).toString()).toString();
						if(isNot!=null){
							saveMap.setField_tab_code(isNot.toString());
						}else{
							failureMsg.append("代码表不存在！ ");
							flag=false;
						}
						//saveMap.setField_tab_code(map.get("field_tab_code").get(1).toString());
					}
					
					//排序号
					if(map.get("sort").get(1)!=null && map.get("sort").get(1)!=""){
						saveMap.setSort(Integer.parseInt(map.get("sort").get(1).toString()));
					}
					
					//备注
					if(map.get("note").get(1)!=null){
						saveMap.setNote(map.get("note").get(1).toString());
					}
					
					successNum++;
					countNum++;
					saveList.add(saveMap);
				}
			}
			
			if(flag==false){
				return "{\"error\":\"导入失败！  "+failureMsg+"\"}";
			}else{
				if(saveList.size() > 0){
					hrColStrucMapper.addBatch(saveList);
				}
				return "{\"msg\":\"已成功导入 "+successNum+"条\",\"state\":\"true\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}

	@Override
	public List<Map<String,Object>> queryHrColStrucByPrint(Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrColStrucMapper.queryHrColStrucByPrint(entityMap));
		 return list;
	}

}
