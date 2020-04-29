package com.chd.hrp.hip.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.hip.dao.DataTypeMapper;
import com.chd.hrp.hip.entity.DataDecode;
import com.chd.hrp.hip.entity.HipDataType;
import com.chd.hrp.hip.entity.HipSyncLog;
import com.chd.hrp.hip.service.DataTypeService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

@Service("dataTypeService")
public class DataTypeServiceImpl implements DataTypeService {

	private static Logger logger = Logger.getLogger(DataTypeServiceImpl.class);

	@Resource(name = "dataTypeMapper")
	private final DataTypeMapper dataTypeMapper = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	/**
	 * 主页面查询列表
	 */
	@Override
	public String queryHipDataType(Map<String, Object> map) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Map<String,Object>> list = dataTypeMapper.queryHipDataType(map, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * 查询表
	 */
	@Override
	public String queryHrpTable(Map<String, Object> map) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		if(map.get("table_name")!=null && !"".equals(map.get("table_name").toString())){
			map.put("table_name", map.get("table_name").toString().toUpperCase());
		}
		
		List<Map<String,Object>> list = dataTypeMapper.queryHrpTable(map, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * 根据表查询列
	 */
	@Override
	public String queryHrpTableColumn(Map<String, Object> map) throws DataAccessException {
	
		List<Map<String,Object>> list = dataTypeMapper.queryHrpTableColumn(map);
		return ChdJson.toJson(list);
		
	}
	
	/**
	 * 查询HipDataType返回sql
	 */
	@Override
	public List<HipDataType> queryHipDataTypeBySql(Map<String, Object> map) throws DataAccessException {
	
		return dataTypeMapper.queryHipDataTypeBySql(map);
		
	}
	
	/**
	 * 保存同步日志
	 */
	@Override
	public int saveHipSyncLog(List<Map<String, Object>> list) throws DataAccessException {
	
		return dataTypeMapper.saveHipSyncLog(list);
		
	}

	@Override
	public String saveHipDataType(Map<String, Object> map) throws DataAccessException {
		try {
			
			map.put("field_table", "HIP_DATA_TYPE");
			map.put("field_key1", "type_code");
			map.put("field_value1", map.get("type_code").toString());
			String type_id = "";
			if(map.get("type_id") != null && !"".equals(map.get("type_id"))){
				map.put("field_id", "type_id");
				map.put("field_id_value", map.get("type_id").toString());
				
				int count = sysFunUtilMapper.existsSysCodeNameByUpdate(map);
				
				if(count > 0){
					return "{\"error\":\"类型编码已经存在，不能保存！\",\"state\":\"false\"}";
				}
				
				dataTypeMapper.updateHipDataType(map);
				type_id = map.get("type_id").toString();
			}else{
				
				int count = sysFunUtilMapper.existsSysCodeNameByAdd(map);
				
				if(count > 0){
					return "{\"error\":\"类型编码已经存在，不能保存！\",\"state\":\"false\"}";
				}
				
				type_id = UUIDLong.absStringUUID();
				map.put("type_id", type_id);
				dataTypeMapper.addHipDataType(map);
			}
			
			

			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"type_id\":\""+type_id+"\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}


	@Override
	public String deleteHipDataType(List<Map<String, Object>> listMap) throws DataAccessException {
		try {
			dataTypeMapper.deleteBatchHipSyncLog(listMap);
			dataTypeMapper.deleteBatchHipDataDecode(listMap);
			dataTypeMapper.deleteHipDataType(listMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public HipDataType queryHipDataTypeByCode(Map<String, Object> map) throws DataAccessException {
		
		return dataTypeMapper.queryHipDataTypeByCode(map);
	}
	
	@Override
	public String queryHipDataDecode(Map<String, Object> entityMap) throws DataAccessException{

		List<Map<String, Object>> list = dataTypeMapper.queryHipDataDecodeList(entityMap);
		return ChdJson.toJson(list);
	}

	@Override
	public Map<String, Object> addHipDataDecode(Map<String, Object> entityMap) throws DataAccessException{
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {

			//解析明细数据
			List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("allData")));
			Iterator iterator = json.iterator();
			JSONObject jsonObj = null;
			Map<String, Object> detailMap = null;
			while(iterator.hasNext()){
				jsonObj = JSONObject.parseObject(iterator.next().toString());
				
				detailMap = new HashMap<String, Object>();
				detailMap.put("decode_id", UUIDLong.absStringUUID());
				detailMap.put("type_id", entityMap.get("type_id"));
				detailMap.put("table_col", jsonObj.getString("table_col"));
				detailMap.put("source_col", jsonObj.getString("source_col"));
				detailMap.put("decode_table", jsonObj.getString("decode_table"));
				detailMap.put("decode_col", jsonObj.getString("decode_col"));
				detailMap.put("decode_type", jsonObj.getInteger("decode_type"));
				detailMap.put("rela_col", jsonObj.getString("rela_col"));
				detailMap.put("create_type", jsonObj.getString("create_type"));
				detailMap.put("join_table", jsonObj.getString("join_table"));
				detailMap.put("el", jsonObj.getString("el"));
				detailMap.put("note", jsonObj.getString("note"));
				
				detailList.add(detailMap);
			}
			
			/*if(detailList.size() == 0){
				retMap.put("state", "false");
				retMap.put("error", "请添加转换信息");
				return retMap;
			}*/
			//先删除再添加
			dataTypeMapper.deleteHipDataDecode(entityMap);
			if(detailList.size() > 0){
				dataTypeMapper.addHipDataDecode(detailList);
			}
			//返回信息
			retMap.put("state", true);
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}

	@Override
	public HipDataType queryHipDataTypeById(Map<String, Object> map) throws DataAccessException {
		return dataTypeMapper.queryHipDataTypeById(map);
	}
	
	/**
	 * 同步数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public synchronized String saveHrpData(Map<String, Object> mapVo,HipDataType dataType,List<Map<String, Object>> list) throws DataAccessException{
		
		StringBuffer note=new StringBuffer();
		
		
		Map<String,Object> hrpMap = new HashMap<String, Object>();
		Map<String,Object> colMap = new HashMap<String, Object>();//要添加的字段
		String column="";
		String inscolumn="";
		int reCount=0;
		//删除、增量同步SQL条件处理
		StringBuffer whereSql=new StringBuffer();
		whereSql.append(" select 1 from "+dataType.getTo_table()+" where" );
		String whereCol="";
		String insertWhereSql=null;
		
		try{
			
			
			/*清洗数据*/
			note.append(DateUtil.dateToString(DateUtil.getCurrenDate())+", 查询字段转换 ");
			List<DataDecode> decodeList=new ArrayList<DataDecode>();
			decodeList=dataTypeMapper.queryHipDataDecodeById(dataType.getType_id());
			note.append("["+decodeList.size()+"条]");
			String seq="";//序列
			if(decodeList!=null && decodeList.size()>0){
				note.append("<br>"+DateUtil.dateToString(DateUtil.getCurrenDate())+", 开始清洗数据 ");
				
				Map<String,List<String>> deErrorMap=new HashMap<String, List<String>>();
				List<String> deErrorList=new ArrayList<String>();
				Map<String,List<Map<String,Object>>> dicMap=new HashMap<String, List<Map<String,Object>>>();
				List<Map<String,Object>> listNew=new ArrayList<Map<String,Object>>();
				Map<String,Object> mapNew = null;
				
 				//需要清洗的数据
				int r=0;
				for(Map<String, Object> mapData:list){
					r++;
					mapNew = new HashMap<String, Object>();
					mapNew.putAll(mapData);
					
					for(DataDecode decode: decodeList){
						if(decode.getDecode_type()==2 && decode.getCreate_type()!=null && !"".equals(decode.getCreate_type()) ){
							//系统生成，转换方式不为空
							
							String key=null;
							for (Map.Entry<String, Object> entry : mapData.entrySet()) { 
								if(entry.getKey().equalsIgnoreCase(decode.getTable_col())){
									key=entry.getKey();
									break;
								}
							}
							if(key!=null){
								//系统生成，删除源数据
								mapNew.remove(key);
							}
							
							if(decode.getCreate_type().equalsIgnoreCase("uuid")){
								mapNew.put(decode.getTable_col().toLowerCase(), UUIDLong.absStringUUID());
							}else if(r==1){
								//拼sql，只处理第一条数据
								column+=decode.getTable_col().toLowerCase()+",";
								seq+=decode.getCreate_type()+".nextval as "+decode.getTable_col().toLowerCase()+",";
							}
							
						}else if(decode.getDecode_type()==1){
							//对应转换
							
							String sCol=decode.getSource_col();//来源字段
							String table=decode.getDecode_table();//转换表
							String reCol=decode.getRela_col();//对应字段
							String deCol=decode.getDecode_col();//取值字段
							String tableCol=decode.getTable_col();//目标表字段
							String joinTable = decode.getJoin_table();//中间表
							String el = decode.getEl();//表达式
							
							if(tableCol==null || "".equals(tableCol) || sCol==null || "".equals(sCol) || table==null || "".equals(table) || reCol==null || "".equals(reCol) || deCol==null || "".equals(deCol)){
								continue;
							}
							
							table=table.toLowerCase();
							if(dicMap.get(table)==null){
								//查询字典数据
								String qSql= "select * from "; 
								if(decode.getQuery_sql() != null && "".equals(decode.getQuery_sql())){
									qSql += "(" + decode.getQuery_sql() + ")";
									if(qSql.indexOf("@begin_date") > -1){
										qSql.replace("@begin_date", "to_date('"+mapVo.get("begin_date").toString()+"', 'yyyy-MM-dd')");
									}
									if(qSql.indexOf("@end_date") > -1){
										qSql.replace("@end_date", "to_date('"+mapVo.get("end_date").toString()+"', 'yyyy-MM-dd')");
									}
								}else{
									qSql += decode.getDecode_table();
								}
								qSql += " a";
								if(joinTable != null && !"".equals(joinTable) && el != null && !"".equals(el)){
									qSql+=" left join " + joinTable + " b on " + el;
								}
								if(decode.getIs_group()==1){
									qSql+=" where a.group_id="+dataType.getDgroup_id()+" ";
								}
								if(decode.getIs_hos()==1){
									qSql+=" and a.hos_id="+dataType.getDhos_id()+" ";
								}
								if(decode.getIs_copy()==1){
									qSql+=" and a.copy_code='"+dataType.getDcopy_code()+"' ";
								}
								dicMap.put(table, dataTypeMapper.queryDataList(qSql));
							}
							
							List<Map<String,Object>> dictList=dicMap.get(table);
							if(dictList!=null && dictList.size()>0){
								
								//查找源数据
								String sKey=null;
								String tKey=null;
								String sValue=null;
								for (Map.Entry<String, Object> entry : mapData.entrySet()) { 
									if(entry.getKey().equalsIgnoreCase(sCol)){
										//来源字段
										sKey=entry.getKey();
										if(entry.getValue()!=null && !"".equals(entry.getValue().toString())){
											sValue=entry.getValue().toString();
										}
										
									}
									if(entry.getKey().equalsIgnoreCase(tableCol)){
										//目标表字段
										tKey=entry.getKey();
									}
								}
								if(sValue==null){
									continue;
								}
								
								//源数据与对应数据一致，转成新数据
								boolean isDe=false;
								for(Map<String,Object> dict:dictList){
									if(sValue.equals(dict.get(reCol.toUpperCase()))){
										if(tKey!=null){
											//如果数据里面存在，目标表字段与源数据key一致，删除
											mapNew.remove(tKey);
										}
										
										isDe=true;
										mapNew.put(tableCol,dict.get(deCol.toUpperCase()));
										break;
									}
								}
								
								if(!isDe){
									//没有找到转换的数据
									deErrorList.clear();
									if(deErrorMap.get(sCol+"->"+tableCol)!=null && deErrorMap.get(sCol+"->"+tableCol).size()>0){
										deErrorList=deErrorMap.get(sCol+"->"+tableCol);
										if(!deErrorList.contains(sValue)){
											deErrorList.add(sValue);
										}
									}else{
										deErrorList.add(sValue);
									}
									deErrorMap.put(table+" ( "+sCol+" -> "+tableCol+" ) ", deErrorList);
								}
							}
							
						}
					}
					listNew.add(mapNew);
				}
				
				if(deErrorMap!=null && deErrorMap.size()>0){
					StringBuffer derror=new StringBuffer();
					derror.append("没有找到需要转换的数据: ");
					for(Map.Entry<String, List<String>> entry :deErrorMap.entrySet()){
						derror.append("<br>"+entry.getKey()+": "+entry.getValue().toString());
					}
					throw new SysException(derror.toString());
				}
				list=new ArrayList<Map<String,Object>>();
				list.addAll(listNew);
			}
			
			
			
			//处理集团、医院、账套
			if(dataType.getDgroup_id()!=0){
				hrpMap.put("group_id", dataType.getDgroup_id());
				whereSql.append(" "+dataType.getTo_table()+".group_id=temp.group_id");
				
			}
			if(dataType.getDhos_id()!=0){
				hrpMap.put("hos_id", dataType.getDhos_id());
				whereSql.append(" and "+dataType.getTo_table()+".hos_id=temp.hos_id");
			}
			if(!"0".equals(dataType.getDcopy_code())){
				hrpMap.put("copy_code", dataType.getDcopy_code());
				whereSql.append(" and "+dataType.getTo_table()+".copy_code=temp.copy_code");
			}
			
			
			
			if(dataType.getPk_col()!=null && !"".equals(dataType.getPk_col())){
				
				/*
				 * 处理同步{}表达式
				 *  {insert_where_sql:' and in_no not in(select BUSINESS_NO from ACC_BUSI_LOG_080101 )'}
				 * */
				String pkCol=dataType.getPk_col();
				
				if(pkCol.indexOf("{")!=-1 && pkCol.indexOf("}")!=-1){
					String el=pkCol.substring(pkCol.indexOf("{"),pkCol.indexOf("}")+1);
					
					//处理同步sql，去掉{}
					pkCol=pkCol.substring(0, pkCol.indexOf("{"));
					
					//同步条件的el表达式
					try{
						if(el!=null && !"".equals(el)){
							JSONObject elJson=JSONObject.parseObject(el);
							//{insert_where_sql:'and in_no not in(select BUSINESS_NO from acc_busi_log_080101 )'
							if(elJson!=null && elJson.getString("insert_where_sql")!=null && !"".equals(elJson.getString("insert_where_sql"))){
								insertWhereSql=elJson.getString("insert_where_sql");
							}
						}	
					}catch(Exception e){
						note.append("<br>["+el+"]格式化错误");
					}
					
				}
				
				//处理同步SQL表达式
				if(pkCol.toLowerCase().indexOf("@begin_date")!=-1 || pkCol.toLowerCase().indexOf("@end_date")!=-1){
					
					if(dataType.getSync_type()==1){
						//增量同步不能有date>=@begin_date
						throw new SysException("增量同步不能有"+dataType.getPk_col());
					}
					
					String dateSql="";
					if(dataType.getDgroup_id()!=0){
						dateSql=" and ";
					}
					
					dateSql+=pkCol;
					
					if(mapVo.get("begin_date")!=null && !"".equals(mapVo.get("begin_date").toString())){
						dateSql=dateSql.replace("@begin_date", "'"+mapVo.get("begin_date").toString()+"'");
					}else{
						throw new SysException("begin_date不允许为空");
					}
					
					if(mapVo.get("end_date")!=null && !"".equals(mapVo.get("end_date").toString())){
						dateSql=dateSql.replace("@end_date", "'"+mapVo.get("end_date").toString()+"'");
					}
					
					hrpMap.put("del_where_sql",dateSql);
					
				}else{
					
					String[] pkArray=pkCol.split(",");
					for(String s:pkArray){
						if(dataType.getDgroup_id()!=0 || whereCol.length()>0){
							whereCol+=" and";
						}
						
						if(dataType.getSync_type()==0){
							//删除同步
							whereCol+=" "+s+"="+"#{item."+s+"}";
						}else{
							//增量同步
							whereCol+=" "+dataType.getTo_table()+"."+s+"="+"temp."+s;
						}
						
					}
					
				}
				
			}else if(dataType.getSync_type()==1){
				throw new SysException("增量同步必须要维护条件表达式");
			}else if(dataType.getData_type()==1){
				throw new SysException("按日期取数必须要维护条件表达式");
			}
			
			if(!"".equals(whereCol)){
				if(dataType.getSync_type()==0 && !"".equals(whereCol)){
					//删除同步
					hrpMap.put("del_where_sql", whereCol);
					dataType.setData_type(0);//没有日期，按主键字段同步
				}else{
					//增量同步
					hrpMap.put("where_sql", whereSql.toString()+whereCol);
				}
			}
			
			//添加判断sql
			if(insertWhereSql!=null && !"".equals(insertWhereSql)){
				hrpMap.put("insert_where_sql", insertWhereSql);
			}
			
			
			hrpMap.put("hip_table", dataType.getTo_table());
			hrpMap.put("sync_type", dataType.getSync_type());
			hrpMap.put("data_type", dataType.getData_type());
			/*删除同步*/
			/*if(dataType.getSync_type()==0){
				//删除同步
				if(!"".equals(whereStr)){
					hrpMap.put("where_sql", whereStr);
				}
				note.append("<br>"+DateUtil.dateToString(DateUtil.getCurrenDate())+", 删除数据 ");
				reCount=dataTypeMapper.deleteHrpData(hrpMap, list);
				note.append("["+reCount+"条]");
				hrpMap.remove("where_sql");
			}else{
				//增量同步
				if(!"".equals(whereStr)){
					hrpMap.put("where_sql", whereSql.toString()+" and "+whereStr);
				}
			}*/
			
			
			/*处理要添加的列及数据类型*/
			colMap=list.get(0);//查询数据
			//List<Map<String,Object>> colTypeList=dataTypeMapper.queryHrpTableColumnType(dataType.getTo_table());
			//String hql="";
			for (String key : colMap.keySet()) { 
				inscolumn += key+",";
				
//				if(key.indexOf("date") != -1 || key.indexOf("DATE") != -1){
//					key = "to_date("+key+",'yyyy-MM-dd')";
//				}
				column +=key+",";
				
				/*boolean isType=false;
				if(colTypeList!=null && colTypeList.size()>0){
					for(Map<String,Object> m: colTypeList){
						if(key.equalsIgnoreCase(m.get("column_name").toString())){
							isType=true;
							
							if(m.get("data_type").toString().equalsIgnoreCase("DATE")) {
								hql +="to_date(#{item."+key+",jdbcType=DATE},'yyyy-MM-dd') "+key+",";
							}else if(m.get("data_type").toString().indexOf("TIMESTAMP")!=-1) {
								hql +="to_date(#{item."+key+",jdbcType=DATE},'hh24:mi:ss') "+key+",";
							}else{
								hql +="#{item."+key+",jdbcType=VARCHAR} "+key+",";
							}
							
						}
					}
				}
				
				if(!isType){
					hql +="#{item."+key+"} "+key+",";
				}*/
				
			} 
			inscolumn=inscolumn.substring(0, inscolumn.length()-1);
			column=column.substring(0, column.length()-1);
			//hql=hql.substring(0, hql.length()-1);
			hrpMap.put("hip_column", column);
			hrpMap.put("hip_inscolumn", inscolumn);
			hrpMap.put("hip_seq", seq);
			hrpMap.put("type_id", dataType.getType_id());
			//hrpMap.put("hip_hql", hql);
			
			//保存数据
			note.append("<br>"+DateUtil.dateToString(DateUtil.getCurrenDate())+", 同步数据 ");
			
			
			List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> mapData : list) {

				Set<String> set = mapData.keySet();
				Iterator<String> iterator = set.iterator();
				while (iterator.hasNext()) {
					String k_data = iterator.next();

					if (k_data.toString().toLowerCase().startsWith("date")) {
						if (mapData.get(k_data) != null && !"".equals(mapData.get(k_data))) {
							mapData.put(k_data, DateUtil.stringToDate1(mapData.get(k_data).toString()));
						} else {
							mapData.put(k_data, null);
						}
					} else if (k_data.toString().toLowerCase().endsWith("date")) {
						if (mapData.get(k_data) != null && !"".equals(mapData.get(k_data))) {
							mapData.put(k_data, DateUtil.stringToDate1(mapData.get(k_data).toString()));
						} else {
							mapData.put(k_data, null);
						}
					} else {
						mapData.put(k_data, mapData.get(k_data));

					}
				}

				paramList.add(mapData);
			}
			
			//1000条数据提交一次
			int count = 500;

			if (paramList.size() <= count) {

				hrpMap.put("commit_index", 1);
				dataTypeMapper.saveHrpData(hrpMap, paramList);

			} else {

				List<Map<String, Object>> listNew = new ArrayList<Map<String, Object>>(count);
				int i = 0;
				for (Map<String, Object> mapData : paramList) {
					if (listNew.size() == count) {
						i++;
						hrpMap.put("commit_index", i);
						dataTypeMapper.saveHrpData(hrpMap, listNew);
						listNew = new ArrayList<Map<String, Object>>(count);
						listNew.add(mapData);
					} else {
						listNew.add(mapData);
					}

				}
				if (listNew.size() > 0) {
					i++;
					hrpMap.put("commit_index", i);
					dataTypeMapper.saveHrpData(hrpMap, listNew);
				}
			}
			
			reCount=dataTypeMapper.queryDataCount(dataType.getType_id());
			
			note.append("["+reCount+"条]");
		
		}catch(Exception e){
			note.append("<br>"+e.getMessage());
			throw new SysException(note.toString(),e);
		}
		
		return note.toString();
	}

	@Override
	public String querySourceColListByType(Map<String, Object> entityMap) throws DataAccessException{
		
		//获取Sql语句
		String qSql = dataTypeMapper.queryQSqlByType(entityMap);
		List<String> cols = getSelectListBySql(qSql);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		for(String col : cols){
			map = new HashMap<String, Object>();
			map.put("id", col);
			map.put("text", col);
			list.add(map);
		}
		
		return JSON.toJSONString(list);
	}
	
	@Override
	public String queryHrpDictTable(Map<String, Object> entityMap) throws DataAccessException{
		
		/*RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} */
		if(entityMap.get("key") != null){
			entityMap.put("table_name", entityMap.get("key"));
		}
		
		//List<Map<String, Object>> list = dataTypeMapper.queryHrpTable(entityMap, rowBounds);
		List<Map<String, Object>> list = dataTypeMapper.queryHrpTable(entityMap);
		
		for(Map<String, Object> map : list){
			map.put("id", map.get("table_code"));
			map.put("text", map.get("table_name"));
		}

		return JSON.toJSONString(list);
	}
	
	@Override
	public String queryHrpDictTableCol(Map<String, Object> entityMap) throws DataAccessException{

		List<Map<String, Object>> list = dataTypeMapper.queryHrpTableColumn(entityMap);
		for(Map<String, Object> map : list){
			map.put("id", map.get("column_name"));
			map.put("text", map.get("column_name"));
		}

		return JSON.toJSONString(list);
	}
	
	//解析出查询字段(截取【SELECT 】即从第7位开始到第一个【 FROM】之间的字符)
	public List<String> getSelectListBySql(String sql) throws DataAccessException{
		List<String> list = new ArrayList<String>();
		//解析出查询字段(截取第一个【SELECT】到第一个【FROM】之间的字符)
		String selectStr = sql.substring(sql.toUpperCase().indexOf("SELECT") + 6, sql.toUpperCase().indexOf("FROM")).trim();
		//每一个字段之间用【,】逗号隔开
		String[] strs = selectStr.split(",");
		String[] cols = null;
		String col = null;
		int left_count = 0, right_count = 0;
		for(int i = 0; i < strs.length; i++){
			col = strs[i].trim();
			//判断是否使用函数
			if(col.indexOf("(") > 0){
				left_count += getCount(col, "(");
			}
			if(col.indexOf(")") > 0){
				right_count += getCount(col, ")");
			}
			
			if(left_count - right_count > 0){
				//函数未结束
				continue;
			}
			
			//如果字段中存在空格则取最后一个数组为该字段的最终别名(别名有可能存在引号)
			if(col.indexOf(" ") > 0){
				cols = col.split(" ");
				col = cols[cols.length - 1].replace("\"", "");
			}
			
			list.add(col);
			//初始化变量
			left_count = 0; 
			right_count = 0;
		}
		
		return list;
	}
	
	public int getCount(String str, String subStr){
	    int result=0;
	    while(str.contains(subStr)){
	         str = str.substring(str.indexOf(subStr)+subStr.length(),str.length());
	         result += 1;
	    }

	    return result;
	}

	@Override
	public String queryHipSyncLog(Map<String, Object> map) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		
		List<HipSyncLog> list = dataTypeMapper.queryHipSyncLog(map, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public String clearHipSyncLog(Map<String, Object> map) throws DataAccessException {
		try {
			
			dataTypeMapper.deleteHipSyncLog(map);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public HipSyncLog queryHipSyncLogByTypeId(Map<String, Object> entityMap) throws DataAccessException {
		return dataTypeMapper.queryHipSyncLogByTypeId(entityMap);
	}
	
	
}
