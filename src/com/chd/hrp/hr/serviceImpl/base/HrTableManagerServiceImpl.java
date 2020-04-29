package com.chd.hrp.hr.serviceImpl.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.exception.SysException;
import com.chd.hrp.hr.dao.base.BaseCRUDMapper;
import com.chd.hrp.hr.dao.base.CreateTablesMapper;
import com.chd.hrp.hr.dao.sc.HrTableStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrColStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrTabStrucMapper;
import com.chd.hrp.hr.entity.base.TableColumns;
import com.chd.hrp.hr.entity.sc.HrTableColumn;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
import com.chd.hrp.hr.entity.sysstruc.HrColStruc;
import com.chd.hrp.hr.entity.sysstruc.HrTabStruc;
import com.chd.hrp.hr.service.base.HrTableManagerService;

/**
 * 
 * @ClassName: HrTableManagerServiceImpl
 * @Description: 数据表操作
 * @author zn
 * @date 2017年11月13日 上午10:32:52
 * 
 *
 */
@Service("hrTableManagerService")
public class HrTableManagerServiceImpl implements HrTableManagerService {
	private static Logger logger = Logger.getLogger(HrTableManagerServiceImpl.class);

	@Resource(name = "createTablesMapper")
	private final CreateTablesMapper createTablesMapper = null;

	@Resource(name = "hrTabStrucMapper")
	private final HrTabStrucMapper hrTabStrucMapper = null;
	
	@Resource(name = "hrColStrucMapper")
	private final HrColStrucMapper hrColStrucMapper = null;
	
	@Resource(name = "baseCRUDMapper")
	private final BaseCRUDMapper baseCRUDMapper = null;
	
	@Resource(name = "hrTableStrucMapper")
	private final HrTableStrucMapper hrTableStrucMapper = null;

	/**
	 * 根据map结构创建表
	 * 
	 * @param newTableMap
	 */
	@Override
	public void createTableByMap(Map<String, List<Object>> newTableMap) {
		// 做创建表操作
		if (newTableMap.size() > 0) {
			for (Entry<String, List<Object>> entry : newTableMap.entrySet()) {
				Map<String, List<Object>> map = new HashMap<String, List<Object>>();
				map.put(entry.getKey(), entry.getValue());
				logger.info("开始创建表：" + entry.getKey());
				createTablesMapper.createTable(map);
				logger.info("完成创建表：" + entry.getKey());
			}
		}
	}
	
	public void createTableByEntity(HrTabStruc tab) {
		if(tab != null && tab.getHrColStrucList() != null && tab.getHrColStrucList().size() > 0){
			Map<String, List<Object>> map = new HashMap<String, List<Object>>();
			List<Object> cols = new ArrayList<Object>();
			for (HrColStruc obj : tab.getHrColStrucList()) {
				obj.setFileTypeLength(handleFileTypeLength(obj.getData_type()));
				cols.add(obj);
			}
			map.put(tab.getTab_code(), cols);
			
			logger.info("开始创建表：" + tab.getTab_code());
			createTablesMapper.createTable(map);
			logger.info("完成创建表：" + tab.getTab_code());
		}
	}
	
	/**
	 * 根据表名删除表
	 * 
	 * @param tableName
	 *            表名
	 */
	@Override
	public void dorpTableByName(String tableName) {
		if (tableName != null && StringUtils.isNotBlank(tableName)) {
			logger.info("开始删除表" + tableName);
			createTablesMapper.dorpTableByName(tableName);
			logger.info("完成删除表" + tableName);
		}
	}

	@Override
	public void createOrModifyTableConstruct(Map<String, Object> entityMap) {

		// 用于存需要增加字段的表名+结构
		Map<String, List<Object>> addTableMap = new HashMap<String, List<Object>>();

		// 用于存需要更新字段类型等的表名+结构
		Map<String, List<Object>> modifyTableMap = new HashMap<String, List<Object>>();

		// 用于存需要删除字段的表名+结构
		Map<String, List<Object>> removeTableMap = new HashMap<String, List<Object>>();

		//获取表信息
		HrTabStruc tab = hrTabStrucMapper.queryTabColsByCode(entityMap);
		
		String tableName = tab.getTab_code();
		
		List<HrColStruc> newFieldList = tab.getHrColStrucList();

		// 先查该表是否以存在
		int exist = createTablesMapper.findTableCountByTableName(tableName);
		
		if (exist == 0) {
			
			if(newFieldList != null && newFieldList.size() >  0){
				//创建表
				createTableByEntity(tab);
			}else{
				throw new SysException("创建表结构失败");
			}
			
		} else {
			// 先查出表结构
			List<TableColumns> tableColumnList = createTablesMapper.findTableEnsembleByTableName(tableName);
			// 从TableColumns中取出我们需要比较的列的List
			// 先取出name用来筛选出增加和删除的字段
			List<String> columnNames = new ArrayList<String>();
			for (TableColumns tableColumn : tableColumnList) {
				columnNames.add(tableColumn.getColumn_name());
			}

			//
			//List<HrColStruc> newFieldList = hrColStrucMapper.queryHrColStrucEntityByTabCode(entityMap);

			// 用于存删除的字段
			List<Object> removeFieldList = new ArrayList<Object>();
			// 用于存新增的字段
			List<Object> addFieldList = new ArrayList<Object>();
			// 用于存修改的字段
			List<Object> modifyFieldList = new ArrayList<Object>();
			// 用于存修改主键的字段
			List<Object> modKeyFieldList = new ArrayList<Object>();

			// 验证对比从model中解析的fieldList与从数据库查出来的columnList
			// 1. 找出增加的字段
			// 2. 找出删除的字段
			// 3. 找出更新的字段
			buildAddAndRemoveAndModifyFields(tableName, addTableMap, modifyTableMap, removeTableMap, removeFieldList,
					addFieldList, modifyFieldList, modKeyFieldList, newFieldList, tableColumnList, columnNames);
			
			
			// 如果存在主键改动 先删除主键
			if (modKeyFieldList.size() > 0) {
				int count = baseCRUDMapper.queryCountByCustomSql(String.valueOf(entityMap.get("tab_code")));
				
				if(count > 0){
					throw new SysException("表存在数据，不允许修改主键字段");
				}
				dropFieldsKeyByName(tableName);
			}

			// 4. 添加新的字段
			addFieldsByMap(addTableMap);
			// 5. 删除字段
			removeFieldsByMap(removeTableMap);
			// 6. 修改字段类型等
			modifyFieldsByMap(modifyTableMap);

			if (modKeyFieldList.size() > 0) {
				// 更新联合主键
				Map<String, List<String>> tableMap = new HashMap<String, List<String>>();

				// 查询表的主键列
				List<String> cols = new ArrayList<String>();
				for (HrColStruc col : newFieldList) {
					if(col.getIs_pk() != null && col.getIs_pk() == 1){
						cols.add(col.getCol_code());
					}
				}

				tableMap.put(tableName.toUpperCase(), cols);

				StringBuffer  sql=new StringBuffer();
				
				sql.append("  declare   num number(4);  pk_name varchar2(100); begin  select count(1) into  num from user_constraints t  where t.table_name = upper('" +tableName+"' )    and t.constraint_type = 'P';");
				
				sql.append("    if num = 1 then select constraint_name into pk_name   from user_constraints  where table_name = upper('" +tableName+"') and constraint_type = 'P';");
				
				sql.append("   execute immediate 'alter table "+tableName +"drop constraint '|| pk_name ||' cascade drop index'; end if;");
				
				sql.append("execute immediate 'alter table "+tableName +" add constraint PK_"+tableName +" primary key (");
				
				
				for (HrColStruc col : newFieldList) {
					if(col.getIs_pk() != null && col.getIs_pk() == 1){
						//cols.add(col.getCol_code());
						sql.append(col.getCol_code()+",");
					}
				}
				//sql.delete(sql.length(), sql.length()-1);
				sql.deleteCharAt(sql.length() - 1);
				sql.append(")' ;  end;");
				entityMap.put("sql", sql.toString());
				createTablesMapper.createPk(entityMap);
			}
		}
	}

	private void buildAddAndRemoveAndModifyFields(String tableName, Map<String, List<Object>> addTableMap,
			Map<String, List<Object>> modifyTableMap, Map<String, List<Object>> removeTableMap,
			List<Object> removeFieldList, List<Object> addFieldList, List<Object> modifyFieldList,
			List<Object> modKeyFieldList, List<HrColStruc> newFieldList, List<TableColumns> tableColumnList,
			List<String> columnNames) {
		// 1. 找出增加的字段
		// 根据数据库中表的结构和model中表的结构对比找出新增的字段
		buildNewFields(tableName, addTableMap, newFieldList, addFieldList, modKeyFieldList, columnNames);

		// 将fieldList转成Map类型，字段名作为主键
		Map<String, HrColStruc> fieldMap = new HashMap<String, HrColStruc>();
		for (HrColStruc obj : newFieldList) {
			fieldMap.put(obj.getCol_code().toUpperCase(), obj);
		}

		// 2. 找出删除的字段
		buildRemoveFields(tableName, removeTableMap, removeFieldList, modKeyFieldList, tableColumnList, fieldMap);

		// 3. 找出更新的字段
		buildModifyFields(tableName, modifyTableMap, modifyFieldList, modKeyFieldList, tableColumnList, fieldMap);

	}

	private void buildModifyFields(String tableName, Map<String, List<Object>> modifyTableMap,
			List<Object> modifyFieldList, List<Object> modKeyFieldList, List<TableColumns> tableColumnList,
			Map<String, HrColStruc> fieldMap) {
		for (TableColumns sysColumn : tableColumnList) {
			// 数据库中有该字段时
			HrColStruc createTableParam = fieldMap.get(sysColumn.getColumn_name());
			if (createTableParam != null) {
				// 检查是否要删除已有主键
				// 原本是主键，现在不是了，那么要去做删除主键的操作
				if ("P".equals(sysColumn.getColumn_key()) && (createTableParam.getIs_pk() == null || createTableParam.getIs_pk() != 1)) {
					modKeyFieldList.add(createTableParam);
				}

				// 验证是否有更新
				// 1.验证类型
				if (!sysColumn.getData_type().toLowerCase().equals(createTableParam.getData_type().toLowerCase())) {
					modifyFieldList.add(createTableParam);
					continue;
				}

				// 2.验证长度
				// 3.验证小数点位数
				int length = handleFileTypeLength(createTableParam.getData_type());
				if (length == 1) {
					if (sysColumn.getData_length() != createTableParam.getFiled_length()) {
						modifyFieldList.add(createTableParam);
						continue;
					}
				} else if (length == 2) {
					if (sysColumn.getData_precision() != createTableParam.getFiled_length()
							|| sysColumn.getData_scale() != createTableParam.getPrec()) {
						modifyFieldList.add(createTableParam);
						continue;
					}
				}

				// 4.验证主键
				if (!"P".equals(sysColumn.getColumn_key()) && createTableParam.getIs_pk() != null
						&& createTableParam.getIs_pk() == 1) {
					modKeyFieldList.add(createTableParam);
					continue;
				}

				// 5.验证是否可以为null(主键不参与是否为null的更新)
				/*if (sysColumn.getNullable().equals("N")
						&& !(createTableParam.getIs_pk() != null && createTableParam.getIs_pk() == 1)) {
					if (createTableParam.getIs_m() == null || createTableParam.getIs_m() != 1) {
						// 一个是可以一个是不可用，所以需要更新该字段
						modifyFieldList.add(createTableParam);
						continue;
					}
				} else if (sysColumn.getNullable().equals("Y")
						&& !(createTableParam.getIs_pk() != null && createTableParam.getIs_pk() == 1)) {
					if (createTableParam.getIs_m() != null && createTableParam.getIs_m() == 1) {
						// 一个是可以一个是不可用，所以需要更新该字段
						modifyFieldList.add(createTableParam);
						continue;
					}
				}*/

			}
		}

		if (modifyFieldList.size() > 0) {
			modifyTableMap.put(tableName, modifyFieldList);
		}

	}

	private void buildRemoveFields(String tableName, Map<String, List<Object>> removeTableMap,
			List<Object> removeFieldList, List<Object> modKeyFieldList, List<TableColumns> tableColumnList, Map<String, HrColStruc> fieldMap) {
		for (TableColumns field : tableColumnList) {
			// 判断该字段在新的model结构中是否存在
			if (fieldMap.get(field.getColumn_name()) == null) {
				// 不存在，做删除处理
				removeFieldList.add(field.getColumn_name());
				if("P".equals(field.getColumn_key())){
					modKeyFieldList.add(new HrColStruc());
				}
			}
		}
		if (removeFieldList.size() > 0) {
			removeTableMap.put(tableName, removeFieldList);
		}

	}

	private void buildNewFields(String tableName, Map<String, List<Object>> addTableMap, List<HrColStruc> newFieldList,
			List<Object> addFieldList, List<Object> modKeyFieldList, List<String> columnNames) {
		for (HrColStruc colStruc : newFieldList) {
			colStruc.setFileTypeLength(handleFileTypeLength(colStruc.getData_type()));
			// 循环新的model中的字段，判断是否在数据库中已经存在
			if (!columnNames.contains(colStruc.getCol_code().toUpperCase())) {
				// 不存在，表示要在数据库中增加该字段
				addFieldList.add(colStruc);
				if(colStruc.getIs_pk() != null && colStruc.getIs_pk() == 1){
					modKeyFieldList.add(colStruc);
				}
			}
		}
		if (addFieldList.size() > 0) {
			addTableMap.put(tableName, addFieldList);
		}

	}

	/**
	 * 根据表名删除联合主键
	 * 
	 * @param dropKeyTableMap
	 */
	private void dropFieldsKeyByName(String tableName) {
		// 先去做删除主键的操作，这步操作必须在增加和修改字段之前！
		if (tableName != null && StringUtils.isNotBlank(tableName)) {
			logger.info("开始为表" + tableName + "删除主键");
			createTablesMapper.dropKeyTableField(tableName);
			logger.info("完成为表" + tableName + "删除主键");
		}
	}

	/**
	 * 根据map结构对表中添加新的字段
	 * 
	 * @param addTableMap
	 */
	private void addFieldsByMap(Map<String, List<Object>> addTableMap) {
		// 做增加字段操作
		if (addTableMap.size() > 0) {
			for (Entry<String, List<Object>> entry : addTableMap.entrySet()) {
				for (Object obj : entry.getValue()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(entry.getKey(), obj);
					HrColStruc fieldProperties = (HrColStruc) obj;
					logger.info("开始为表" + entry.getKey() + "增加字段" + fieldProperties.getCol_name());
					createTablesMapper.addTableField(map);
					logger.info("完成为表" + entry.getKey() + "增加字段" + fieldProperties.getCol_name());
				}
			}
		}
	}

	/**
	 * 根据map结构修改表中的字段类型等
	 * 
	 * @param modifyTableMap
	 */
	private void modifyFieldsByMap(Map<String, List<Object>> modifyTableMap) {
		// 做修改字段操作
		if (modifyTableMap.size() > 0) {
			for (Entry<String, List<Object>> entry : modifyTableMap.entrySet()) {
				for (Object obj : entry.getValue()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(entry.getKey(), obj);
					HrColStruc fieldProperties = (HrColStruc) obj;
					logger.info("开始修改表" + entry.getKey() + "中的字段" + fieldProperties.getCol_name());
					createTablesMapper.modifyTableField(map);
					logger.info("完成修改表" + entry.getKey() + "中的字段" + fieldProperties.getCol_name());
				}
			}
		}
	}

	/**
	 * 根据map结构删除表中的字段
	 * 
	 * @param removeTableMap
	 */
	private void removeFieldsByMap(Map<String, List<Object>> removeTableMap) {
		// 做删除字段操作
		if (removeTableMap.size() > 0) {
			for (Entry<String, List<Object>> entry : removeTableMap.entrySet()) {
				for (Object obj : entry.getValue()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(entry.getKey(), obj);
					String fieldName = (String) obj;
					logger.info("开始删除表" + entry.getKey() + "中的字段" + fieldName);
					createTablesMapper.removeTableField(map);
					logger.info("完成删除表" + entry.getKey() + "中的字段" + fieldName);
				}
			}
		}
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
	public void createTableStruc(Map<String, List<HrTableColumn>> newTableMap) {
		// 做创建表操作
		if (newTableMap.size() > 0) {
			for (Entry<String, List<HrTableColumn>> entry : newTableMap.entrySet()) {
				Map<String, List<HrTableColumn>> map = new HashMap<String, List<HrTableColumn>>();
				map.put(entry.getKey(), entry.getValue());
				logger.info("开始创建表：" + entry.getKey());
				createTablesMapper.createTableStruc(map);
				logger.info("完成创建表：" + entry.getKey());
			}
		}
	}

	@Override
	public void alterTableStruc(Map<String, Object> entityMap) throws DataAccessException {
		try {
			HrTableStruc struc = hrTableStrucMapper.queryByCode(entityMap);
			String tableName = struc.getTab_code();
			List<HrTableColumn> columns = struc.getTableColumn();
			if(columns != null && columns.size() >  0){
				Collections.sort(columns);
				
				// 先查该表是否以存在
				int exist = createTablesMapper.findTableCountByTableName(tableName);
				
				// 查询表是否存在数据
				int existData = createTablesMapper.findTableDateCountByTableName(tableName);
				
				// 如果表不存在或数据无数据，直接重新创建表结构
				if (exist == 0 || existData == 0) {
					//创建表
					Map<String, List<HrTableColumn>> tableMap = new HashMap<String, List<HrTableColumn>>();
					tableMap.put(tableName.toUpperCase(), columns);
					createTableStruc(tableMap);
				} else {
					List<HrTableColumn> addFields = new ArrayList<HrTableColumn>();//新增列表
					List<HrTableColumn> updateFields = new ArrayList<HrTableColumn>();//修改列表
					
					//库中表结构
					List<TableColumns> tableColumnList = createTablesMapper.findTableEnsembleByTableName(tableName);
					Map<String, TableColumns> existColumns = new HashMap<String, TableColumns>();
					for (TableColumns column : tableColumnList) {
						existColumns.put(column.getColumn_name(), column);
					}
					
					//处理新表结构
					for (HrTableColumn column : columns) {
						// 验证是否有更新
						if(existColumns.containsKey(column.getCol_code().toUpperCase())){
							TableColumns existColumn = existColumns.get(column.getCol_code().toUpperCase());
							
							// 1.验证主键
							// 2.验证是否可以为null
							// 主键或非空不允许修改
							if("P".equals(existColumn.getColumn_key()) || "N".equals(existColumn.getNullable())){
								continue;
							}
							
							// 3.验证类型
							if(!column.getData_type_code().equalsIgnoreCase(existColumn.getData_type())){
								updateFields.add(column);
								continue;
							}
							
							// 4.验证长度
							String length = column.getFiled_length();
							if(length == null && ("DATE".equalsIgnoreCase(column.getData_type_code()) 
									|| "TIMESTAMP".equalsIgnoreCase(column.getData_type_code()) 
									|| "CLOB".equalsIgnoreCase(column.getData_type_code()) )){
								continue;
							}
							int precision = 0;
							int scale = 0;
							if(length.indexOf(",") != -1){
								precision = Integer.parseInt(length.split(",")[0]);
								scale = Integer.parseInt(length.split(",")[1]);
							} else {
								precision = Integer.parseInt(length);
							}
							if(existColumn.getData_precision() != null && (existColumn.getData_precision() != precision || existColumn.getData_scale() != scale)){
								updateFields.add(column);
								continue;
							} else if(existColumn.getData_precision() == null && existColumn.getData_length() != precision){
								updateFields.add(column);
								continue;
							}
							
						} else {
							addFields.add(column);
						}
					}
					
					if(addFields.size() > 0){
						for (HrTableColumn column : addFields) {
							Map<String, Object> addFieldMap = new HashMap<>();
							addFieldMap.put(tableName, column);
							createTablesMapper.addTableField(addFieldMap);
						}
					}
					
					if(updateFields.size() > 0){
						for (HrTableColumn column : updateFields) {
							Map<String, Object> updateFieldMap = new HashMap<>();
							updateFieldMap.put(tableName, column);
							createTablesMapper.modifyTableField(updateFieldMap);
						}
					}
				}
			}else{
				throw new SysException("未维护表结构字段，表结构修改失败。");
			}
		} catch (Exception e) {
			logger.error(e);
			throw new SysException(e.getMessage());
		}
	}

}
