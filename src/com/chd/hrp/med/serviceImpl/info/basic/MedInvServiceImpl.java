/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.info.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.dao.base.MedNoOtherMapper;
import com.chd.hrp.med.dao.info.basic.MedInvCertRelaMapper;
import com.chd.hrp.med.dao.info.basic.MedInvDictMapper;
import com.chd.hrp.med.dao.info.basic.MedInvMapper;
import com.chd.hrp.med.dao.info.basic.MedStoreInvMapper;
import com.chd.hrp.med.dao.info.basic.MedStoreTypeMapper;
import com.chd.hrp.med.dao.info.basic.MedTypeDictMapper;
import com.chd.hrp.med.dao.info.relaset.MedDeptMatchMapper;
import com.chd.hrp.med.dao.info.relaset.MedStoreMatchMapper;
import com.chd.hrp.med.entity.MedInv;
import com.chd.hrp.med.entity.MedStoreType;
import com.chd.hrp.med.entity.MedTypeDict;
import com.chd.hrp.med.service.info.basic.MedInvService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 08105 药品材料表 
 * @Table:
 * MED_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medInvService")
public class MedInvServiceImpl implements MedInvService {

	private static Logger logger = Logger.getLogger(MedInvServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medInvMapper")
	private final MedInvMapper medInvMapper = null;
	@Resource(name = "medInvDictMapper")
	private final MedInvDictMapper medInvDictMapper = null;
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;
	@Resource(name = "medNoOtherMapper")
	private final MedNoOtherMapper medNoOtherMapper = null;
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	@Resource(name = "medTypeDictMapper")
	private final MedTypeDictMapper medTypeDictMapper = null;
    @Resource(name = "medStoreTypeMapper")
    private final MedStoreTypeMapper medStoreTypeMapper = null;
    @Resource(name = "medStoreInvMapper")
    private final MedStoreInvMapper medStoreInvMapper = null;
    @Resource(name = "medDeptMatchMapper")
    private final MedDeptMatchMapper medDeptMatchMapper = null;
    @Resource(name = "medStoreMatchMapper")
    private final MedStoreMatchMapper medStoreMatchMapper = null;
    @Resource(name = "medInvCertRelaMapper")
    private final MedInvCertRelaMapper medInvCertRelaMapper = null;
    
	/**
	 * @Description 
	 * 添加08105 药品材料表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		try {
			//判断编码是否自动生成
			if("自动生成".equals(entityMap.get("inv_code"))){
				boolean is_loop = true;
				int index = 0;
				String inv_code = "";
				int max_length = 0;
				String type_max_inv_code = null;
				while(is_loop){
					/*用于查询并更新最大流水码----begin-----*/
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					map.put("type_code", "2");
					if(!"1".equals(MyConfig.getSysPara("08014"))){
						String max_no = medNoOtherMapper.queryMedNoOther(map);//获取当前编码最大值
						//如果不存在则插入
						if(max_no == null || "".equals(max_no)){
							max_no = "1";
							map.put("max_no", Integer.valueOf(max_no));
							medNoOtherMapper.insertMedNoOther(map);
						}else{
							map.put("max_no", Integer.valueOf(max_no)+1);
							medNoOtherMapper.updateMedNoOther(map);
						}
						/*用于查询并更新最大流水码----end-------*/
						
						/*查询系统参数08002获得编码位数---begin----*/
						map.put("group_id", entityMap.get("group_id"));
						map.put("hos_id", entityMap.get("hos_id"));
						map.put("copy_code", entityMap.get("copy_code"));
						map.put("para_code", "08002");
						String reg = "\\d+";
						String maxLength =medCommonMapper.getMedAccPara(map);
						if(maxLength.matches(reg)){
							
							max_length = Integer.valueOf(medCommonMapper.getMedAccPara(map));
							/*查询系统参数08002获得编码位数---end------*/
							inv_code = "" + max_no;
							
							for (int i = inv_code.length() ; i < max_length; i++) {
								inv_code = "0" + inv_code;
							}
						}else{
							return "{\"error\":\"系统参数08002格式错误.\"}";
						}
					}else{
						if(index == 0){
							type_max_inv_code = medInvMapper.queryMedInvMaxByType(entityMap);
						}else{
							type_max_inv_code = String.valueOf(Long.parseLong(type_max_inv_code) + 1);
						}
						//获取材料类别编码
						Map<String,Object> typeMap = new HashMap<String,Object>();
						typeMap.put("group_id", entityMap.get("group_id"));
						typeMap.put("hos_id", entityMap.get("hos_id"));
						typeMap.put("copy_code", entityMap.get("copy_code"));
						typeMap.put("med_type_id", entityMap.get("med_type_id"));
						if(entityMap.get("med_type_no") != null && !"".equals(String.valueOf(entityMap.get("med_type_no")))){
							typeMap.put("med_type_no", entityMap.get("med_type_no"));
						}else{
							typeMap.put("is_stop", "0");
						}
						MedTypeDict mtd = medTypeDictMapper.queryMedTypeDictByUniqueness(typeMap);
			
						/*查询系统参数08002获得编码位数---end------*/
						/**算法：
						 * 该类别下有材料：1||'inv_code(0101000001)' + 1 = 10101000002 
						 * 该类别下没有材料：1||'inv_code('')' + 1 = 2
						 * 如果值为2证明该类别没有生成过材料编码应从1开始
						 * 如果不是需要把前缀1去掉*/
						/*查询系统参数08002获得编码位数---begin----*/
						map.put("group_id", entityMap.get("group_id"));
						map.put("hos_id", entityMap.get("hos_id"));
						map.put("copy_code", entityMap.get("copy_code"));
						map.put("para_code", "08002");
						String reg = "\\d+";
						String maxLength =medCommonMapper.getMedAccPara(map);
						if(maxLength.matches(reg)){
							max_length = Integer.valueOf(medCommonMapper.getMedAccPara(map));
						if(type_max_inv_code.equals("2")){
							inv_code = "1";
							for (int i = 1 ; i < max_length; i++) {
								inv_code = "0" + inv_code;
							}
							inv_code = mtd.getMed_type_code() + inv_code;
						}else{
							inv_code = type_max_inv_code.substring(1, type_max_inv_code.length());
						}
						
					  }else{
							return "{\"error\":\"系统参数08002格式错误.\"}";
						}
					}
					
					/*检验自动生成的编码是否重复*/
					map.put("field_table", "med_inv");
					map.put("field_key1", "inv_code");
					map.put("field_value1", inv_code);
					map.put("field_key2", "use_state");
					map.put("field_value2", 1);
					int flag = sysFunUtilMapper.existsSysCodeNameByAdd(map);
					if (flag == 0) {
						is_loop = false;
					}
					index ++;
				}
				entityMap.put("inv_code", inv_code);
			}
			
			/*判断编码是否重复----------------begin------------*/
			Map<String, Object> existsCodeMap = new HashMap<String, Object>();
			existsCodeMap.put("group_id", entityMap.get("group_id"));
			existsCodeMap.put("hos_id", entityMap.get("hos_id"));
			existsCodeMap.put("copy_code", entityMap.get("copy_code"));
			existsCodeMap.put("field_table", "med_inv");
			existsCodeMap.put("field_key1", "inv_code");
			existsCodeMap.put("field_value1", entityMap.get("inv_code"));
			existsCodeMap.put("field_key2", "use_state");
			existsCodeMap.put("field_value2", 1);
			int flag = sysFunUtilMapper.existsSysCodeNameByAdd(existsCodeMap);
			if (flag != 0) {
				return "{\"error\":\"编码：" + entityMap.get("inv_code") + "已存在.\"}";
			}
			/*判断编码是否重复----------------end--------------*/
			
			//先取出材料ID
			//entityMap.put("inv_id", medInvMapper.queryMaxInv_id()+1);
			entityMap.put("inv_id", medInvMapper.queryMedInvSeq());
			
			
			/*处理供应商信息-------------------begin-----------*/
			List<Map<String,Object>> supList = new ArrayList<Map<String,Object>>();
			if(entityMap.get("supData") != null && !"".equals(entityMap.get("supData"))){
	
				String[] supData = entityMap.get("supData").toString().split("@");
				
				for(String s : supData){
					Map<String,Object> supMap = new HashMap<String,Object>();
					supMap.put("group_id", entityMap.get("group_id"));
					supMap.put("hos_id", entityMap.get("hos_id"));
					supMap.put("copy_code", entityMap.get("copy_code"));
					supMap.put("inv_id", entityMap.get("inv_id"));
					supMap.put("sup_id", s.split(",")[0]);
					supMap.put("is_default", "1".equals(s.split(",")[1]) ? 1 : 0);
					supList.add(supMap);
				}
				
			}
			/*处理供应商信息-------------------end-------------*/
			
			/*处理证件信息-------------------begin-----------*/
			List<Map<String,Object>> certList = new ArrayList<Map<String,Object>>();
			if(entityMap.get("certIds") != null && !"".equals(entityMap.get("certIds"))){

				String[] certData = entityMap.get("certIds").toString().split(",");
				
				for(String id : certData){
					Map<String,Object> certMap = new HashMap<String,Object>();
					certMap.put("group_id", entityMap.get("group_id"));
					certMap.put("hos_id", entityMap.get("hos_id"));
					certMap.put("copy_code", entityMap.get("copy_code"));
					certMap.put("inv_id", entityMap.get("inv_id"));
					certMap.put("cert_id", id.split("@")[0]);
					certMap.put("cert_code", id.split("@")[1]);
					certList.add(certMap);
				}
			}
			/*处理证件信息-------------------end-------------*/
			
			//处理默认计划来源
			if(entityMap.get("source_plan") == null || "".equals(entityMap.get("source_plan"))){
				entityMap.put("source_plan", 1);
			}
			
			//新增药品
			medInvMapper.add(entityMap);
			
			//新增变更
			//entityMap.put("inv_no", medInvDictMapper.queryMaxInv_no()+1);
			entityMap.put("change_user", SessionManager.getUserId());
			entityMap.put("change_date", new Date());
			entityMap.put("change_note", "新增");
			medInvDictMapper.add(entityMap);

			if(supList.size() > 0){
				medInvMapper.addMedInvSup(supList);
			}
			
			if(certList.size() > 0){
				//插入材料证件对应关系
				medInvCertRelaMapper.addBatchMedInvCertRela(certList);
			}
			
			//2017/03/23 将controller中的代码移到了此处并优化,解决报错问题
			/*处理仓库材料对应关系信息-------------------begin-------------*/
			MedStoreType medStoreType = null;
			//存在同一分类对应多个仓库的 一对多关系
			List<MedStoreType> listMst=new ArrayList<MedStoreType>();
			
			
			if(entityMap.get("med_type_id") != null && ! "".equals(entityMap.get("med_type_id")) ){
				//存在同一分类对应多个仓库的 一对多关系
				//medStoreType =   medStoreTypeMapper.queryMedStoreTypeByCode(entityMap);
				listMst=medStoreTypeMapper.queryMedStoreType(entityMap);
			}
			for (MedStoreType mst : listMst) {
				Map<String, Object> medStoreInvMap =new HashMap<String, Object>();
				
				medStoreInvMap.put("group_id",entityMap.get("group_id"));
				
				medStoreInvMap.put("hos_id",entityMap.get("hos_id"));
				
				medStoreInvMap.put("copy_code",entityMap.get("copy_code"));
				
				medStoreInvMap.put("store_id",mst.getStore_id());
				
				medStoreInvMap.put("inv_id",entityMap.get("inv_id"));
				//材料为新增材料 不需要判断是否重复
				medStoreInvMapper.addMedStoreInv(medStoreInvMap); 
            }
			
			/*处理仓库材料对应关系信息-------------------end-------------*/
			
			
			return "{\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("inv_id").toString()+","
			+"\"}";
		}
		catch (DataAccessException e) {
			
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedInv\"}";
		}
	}
	 /*避免添加出问题 导入中使用*/
	@Override
     public String addimp(Map<String,Object> entityMap)throws DataAccessException{

		try {
			//判断编码是否自动生成
			if("自动生成".equals(entityMap.get("inv_code"))){
				boolean is_loop = true;
				String inv_code = "";
				while(is_loop){
					/*查询系统参数08002获得编码位数---begin----*/
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					map.put("para_code", "08002");
					String reg = "\\d+";
					String maxLength =medCommonMapper.getMedAccPara(map);
					/*查询系统参数08002获得编码位数---end------*/
					//判断参数值是否合法
					if(maxLength.matches(reg)){
						int max_length = Integer.valueOf(maxLength);
						//判断是否按类别编码为前缀并逐个递增自动生成
						if("1".equals(MyConfig.getSysPara("08014"))){
							String type_max_inv_code= medInvMapper.queryMedInvMaxByType(entityMap);
							//获取材料类别编码
							Map<String,Object> typeMap = new HashMap<String,Object>();
							typeMap.put("group_id", entityMap.get("group_id"));
							typeMap.put("hos_id", entityMap.get("hos_id"));
							typeMap.put("copy_code", entityMap.get("copy_code"));
							typeMap.put("med_type_id", entityMap.get("med_type_id"));
							if(entityMap.get("med_type_no") != null && !"".equals(String.valueOf(entityMap.get("med_type_no")))){
								typeMap.put("med_type_no", entityMap.get("med_type_no"));
							}else{
								typeMap.put("is_stop", "0");
							}
							if(type_max_inv_code.equals("2")){
								//该类别第一个编码从1开始
								inv_code = "1";
								//获取类别编码
								MedTypeDict mtd = medTypeDictMapper.queryMedTypeDictByUniqueness(typeMap);
								//补位
								for (int i = inv_code.length() ; i < max_length; i++) {
									inv_code = "0" + inv_code;
								}
								inv_code = mtd.getMed_type_code() + inv_code;
							}else{
								inv_code = type_max_inv_code.substring(1, type_max_inv_code.length());
							}
						}else{
							//按流水码补位自动生成材料编码
							/*用于查询并更新最大流水码----begin-----*/
							Map<String,Object> noMap = new HashMap<String,Object>();
							noMap.put("group_id", entityMap.get("group_id"));
							noMap.put("hos_id", entityMap.get("hos_id"));
							noMap.put("type_code", 2);
							String max_no = medNoOtherMapper.queryMedNoOther(noMap);//获取当前编码最大值
							//如果不存在则插入
							if(max_no == null || "".equals(max_no)){
								max_no = "1";
								noMap.put("max_no", Integer.valueOf(max_no));
								medNoOtherMapper.insertMedNoOther(noMap);
							}else{
								noMap.put("max_no", Integer.valueOf(max_no)+1);
								medNoOtherMapper.updateMedNoOther(noMap);
							}
							/*用于查询并更新最大流水码----end-------*/
							inv_code = "" + max_no;
							//补位
							for (int i = inv_code.length() ; i < max_length; i++) {
								inv_code = "0" + inv_code;
							}
						}
					}else{
						return "{\"error\":\"系统参数格式错误.\"}";
					}
					
					/*检验自动生成的编码是否重复*/
					map.put("field_table", "med_inv");
					map.put("field_key1", "inv_code");
					map.put("field_value1", inv_code);
					map.put("field_key2", "use_state");
					map.put("field_value2", 1);
					int flag = sysFunUtilMapper.existsSysCodeNameByAdd(map);
					if (flag == 0) {
						is_loop = false;
					}
				}
				entityMap.put("inv_code", inv_code);
			}
			
			/*判断编码是否重复----------------begin------------*/
			Map<String, Object> existsCodeMap = new HashMap<String, Object>();
			existsCodeMap.put("group_id", entityMap.get("group_id"));
			existsCodeMap.put("hos_id", entityMap.get("hos_id"));
			existsCodeMap.put("copy_code", entityMap.get("copy_code"));
			existsCodeMap.put("field_table", "med_inv");
			existsCodeMap.put("field_key1", "inv_code");
			existsCodeMap.put("field_value1", entityMap.get("inv_code"));
			existsCodeMap.put("field_key2", "use_state");
			existsCodeMap.put("field_value2", 1);
			int flag = sysFunUtilMapper.existsSysCodeNameByAdd(existsCodeMap);
			if (flag != 0) {
				return "{\"error\":\"编码：" + entityMap.get("inv_code") + "已存在.\"}";
			}
			/*判断编码是否重复----------------end--------------*/
			
			/*判断名称规格型号生产厂商是否重复----------------begin----导入中使用--------*/
			Map<String, Object> existsCodeMap1 = new HashMap<String, Object>();
			existsCodeMap1.put("group_id", entityMap.get("group_id"));
			existsCodeMap1.put("hos_id", entityMap.get("hos_id"));
			existsCodeMap1.put("copy_code", entityMap.get("copy_code"));
			existsCodeMap1.put("field_table", "med_inv");
			existsCodeMap1.put("field_key1", "inv_name");
			existsCodeMap1.put("field_key2", "inv_model");
			existsCodeMap1.put("field_value1", entityMap.get("inv_name"));
			existsCodeMap1.put("field_value2", entityMap.get("inv_model"));
			if(entityMap.get("fac_id").toString() != ""){
			  existsCodeMap1.put("field_key3", "fac_id");
			  existsCodeMap1.put("field_value3", entityMap.get("fac_id"));
			}
			
			int flag1 = sysFunUtilMapper.existsSysCodeNameByAdd(existsCodeMap1);
			if (flag1 != 0) {
				return "1";
			}
			/*判断名称规格型号生产厂商----------------end--------------*/
			//先取出材料ID
			entityMap.put("inv_id", medInvMapper.queryMedInvSeq());
			/*处理供应商信息-------------------begin-----------*/
			List<Map<String,Object>> supList = new ArrayList<Map<String,Object>>();
			if(entityMap.get("supData") != null && !"[]".equals(String.valueOf(entityMap.get("supData")))){
				JSONArray json = JSONArray.parseArray((String)entityMap.get("supData"));
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( ChdJson.validateJSON(String.valueOf(jsonObj.get("sup_id")))){
						Map<String,Object> supMap = new HashMap<String,Object>();
						supMap.put("group_id", entityMap.get("group_id"));
						supMap.put("hos_id", entityMap.get("hos_id"));
						supMap.put("copy_code", entityMap.get("copy_code"));
						supMap.put("inv_id", entityMap.get("inv_id"));
						supMap.put("sup_id", Integer.valueOf(String.valueOf(jsonObj.get("sup_id"))));
						//20170310增加导入默认供应商字段,解决导入少是否默认字段导致的报错问题;
						supMap.put("is_default", 1);
						supList.add(supMap);
					}
				}
			}
			/*处理供应商信息-------------------end-------------*/

			//处理默认计划来源
			if(entityMap.get("source_plan") == null || "".equals(entityMap.get("source_plan"))){
				entityMap.put("source_plan", 1);
			}
			//处理品种码字段
			if(entityMap.get("is_inv_bar") == null || "".equals(entityMap.get("is_inv_bar"))){
				entityMap.put("is_inv_bar", 0);
			}
			
			//新增药品
			medInvMapper.add(entityMap);
			
			//新增变更
			entityMap.put("change_user", SessionManager.getUserId());
			entityMap.put("change_date", new Date());
			entityMap.put("change_note", "新增");
			medInvDictMapper.add(entityMap);
			
			if(supList.size() > 0){
				medInvMapper.addMedInvSup(supList);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedInv\"}";
		}
	}
	 /*避免添加出问题 导入中使用*/
	@Override
    public String addImpBatch(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			int is_default;
			for(Map<String, Object> entityMap : entityList){
				//判断编码是否自动生成
				if("自动生成".equals(entityMap.get("inv_code"))){
					boolean is_loop = true;
					String inv_code = "";
					while(is_loop){
						/*查询系统参数08002获得编码位数---begin----*/
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("group_id", entityMap.get("group_id"));
						map.put("hos_id", entityMap.get("hos_id"));
						map.put("copy_code", entityMap.get("copy_code"));
						map.put("para_code", "08002");
						String reg = "\\d+";
						String maxLength =medCommonMapper.getMedAccPara(map);
						/*查询系统参数08002获得编码位数---end------*/
						//判断参数值是否合法
						if(maxLength.matches(reg)){
							int max_length = Integer.valueOf(maxLength);
							//判断是否按类别编码为前缀并逐个递增自动生成
							if("1".equals(MyConfig.getSysPara("08014"))){
								String type_max_inv_code= medInvMapper.queryMedInvMaxByType(entityMap);
								//获取材料类别编码
								Map<String,Object> typeMap = new HashMap<String,Object>();
								typeMap.put("group_id", entityMap.get("group_id"));
								typeMap.put("hos_id", entityMap.get("hos_id"));
								typeMap.put("copy_code", entityMap.get("copy_code"));
								typeMap.put("med_type_id", entityMap.get("med_type_id"));
								if(entityMap.get("med_type_no") != null && !"".equals(String.valueOf(entityMap.get("med_type_no")))){
									typeMap.put("med_type_no", entityMap.get("med_type_no"));
								}else{
									typeMap.put("is_stop", "0");
								}
								if(type_max_inv_code.equals("2")){
									//该类别第一个编码从1开始
									inv_code = "1";
									//获取类别编码
									MedTypeDict mtd = medTypeDictMapper.queryMedTypeDictByUniqueness(typeMap);
									//补位
									for (int i = inv_code.length() ; i < max_length; i++) {
										inv_code = "0" + inv_code;
									}
									inv_code = mtd.getMed_type_code() + inv_code;
								}else{
									inv_code = type_max_inv_code.substring(1, type_max_inv_code.length());
								}
							}else{
								//按流水码补位自动生成材料编码
								/*用于查询并更新最大流水码----begin-----*/
								Map<String,Object> noMap = new HashMap<String,Object>();
								noMap.put("group_id", entityMap.get("group_id"));
								noMap.put("hos_id", entityMap.get("hos_id"));
								noMap.put("type_code", 2);
								String max_no = medNoOtherMapper.queryMedNoOther(noMap);//获取当前编码最大值
								//如果不存在则插入
								if(max_no == null || "".equals(max_no)){
									max_no = "1";
									noMap.put("max_no", Integer.valueOf(max_no));
									medNoOtherMapper.insertMedNoOther(noMap);
								}else{
									noMap.put("max_no", Integer.valueOf(max_no)+1);
									medNoOtherMapper.updateMedNoOther(noMap);
								}
								/*用于查询并更新最大流水码----end-------*/
								inv_code = "" + max_no;
								//补位
								for (int i = inv_code.length() ; i < max_length; i++) {
									inv_code = "0" + inv_code;
								}
							}
						}else{
							return "{\"error\":\"系统参数格式错误.\"}";
						}
						
						/*检验自动生成的编码是否重复*/
						map.put("field_table", "med_inv");
						map.put("field_key1", "inv_code");
						map.put("field_value1", inv_code);
						map.put("field_key2", "use_state");
						map.put("field_value2", 1);
						int flag = sysFunUtilMapper.existsSysCodeNameByAdd(map);
						if (flag == 0) {
							is_loop = false;
						}
					}
					entityMap.put("inv_code", inv_code);
				}
				
				/*判断编码是否重复----------------begin------------*/
				Map<String, Object> existsCodeMap = new HashMap<String, Object>();
				existsCodeMap.put("group_id", entityMap.get("group_id"));
				existsCodeMap.put("hos_id", entityMap.get("hos_id"));
				existsCodeMap.put("copy_code", entityMap.get("copy_code"));
				existsCodeMap.put("field_table", "med_inv");
				existsCodeMap.put("field_key1", "inv_code");
				existsCodeMap.put("field_value1", entityMap.get("inv_code"));
				existsCodeMap.put("field_key2", "use_state");
				existsCodeMap.put("field_value2", 1);
				int flag = sysFunUtilMapper.existsSysCodeNameByAdd(existsCodeMap);
				if (flag != 0) {

					throw new SysException("{\"error\":\"编码：" + entityMap.get("inv_code") + "已存在.\"}");
				}
				/*判断编码是否重复----------------end--------------*/
				
				/*判断名称规格型号生产厂商是否重复----------------begin----导入中使用--------*/
				Map<String, Object> existsCodeMap1 = new HashMap<String, Object>();
				existsCodeMap1.put("group_id", entityMap.get("group_id"));
				existsCodeMap1.put("hos_id", entityMap.get("hos_id"));
				existsCodeMap1.put("copy_code", entityMap.get("copy_code"));
				existsCodeMap1.put("field_table", "med_inv");
				existsCodeMap1.put("field_key1", "inv_name");
				existsCodeMap1.put("field_key2", "inv_model");
				existsCodeMap1.put("field_value1", entityMap.get("inv_name"));
				existsCodeMap1.put("field_value2", entityMap.get("inv_model"));
				if(entityMap.get("fac_id").toString() != ""){
				  existsCodeMap1.put("field_key3", "fac_id");
				  existsCodeMap1.put("field_value3", entityMap.get("fac_id"));
				}
				existsCodeMap1.put("field_key4", "use_state");
				existsCodeMap1.put("field_value4", 1);
				
				int flag1 = sysFunUtilMapper.existsSysCodeNameByAdd(existsCodeMap1);
				if (flag1 == 0) {
					
					/*判断名称规格型号生产厂商----------------end--------------*/
					//先取出材料ID
					entityMap.put("inv_id", medInvMapper.queryMedInvSeq());
		
					//处理默认计划来源
					if(entityMap.get("source_plan") == null || "".equals(entityMap.get("source_plan"))){
						entityMap.put("source_plan", 1);
					}
					//处理品种码字段
					if(entityMap.get("is_inv_bar") == null || "".equals(entityMap.get("is_inv_bar"))){
						entityMap.put("is_inv_bar", 0);
					}
					
					//新增药品
					medInvMapper.add(entityMap);
					
					//新增变更
					entityMap.put("change_user", SessionManager.getUserId());
					entityMap.put("change_date", new Date());
					entityMap.put("change_note", "新增");
					medInvDictMapper.add(entityMap);
					is_default = 1;
				}else{
					/*根据材料名称规格型号与生产厂商获取已插入的材料ID*/
					Map<String, Object> seachMap = new HashMap<String, Object>();
					seachMap.put("group_id", entityMap.get("group_id"));
					seachMap.put("hos_id", entityMap.get("hos_id"));
					seachMap.put("copy_code", entityMap.get("copy_code"));
					seachMap.put("inv_name", entityMap.get("inv_name"));
					seachMap.put("inv_model", entityMap.get("inv_model"));
					if(entityMap.get("fac_id").toString() != ""){
						seachMap.put("fac_id", entityMap.get("fac_id"));
					}
					entityMap.put("inv_id", medInvMapper.getInvIdByImp(entityMap));
					is_default = 0;
				}
				
				/*处理供应商信息-------------------begin-----------*/
				if( ChdJson.validateJSON(String.valueOf(entityMap.get("sup_id")))){
					Map<String,Object> supMap = new HashMap<String,Object>();
					supMap.put("group_id", entityMap.get("group_id"));
					supMap.put("hos_id", entityMap.get("hos_id"));
					supMap.put("copy_code", entityMap.get("copy_code"));
					supMap.put("inv_id", entityMap.get("inv_id"));
					supMap.put("sup_id", Integer.valueOf(String.valueOf(entityMap.get("sup_id"))));
					//20170310增加导入默认供应商字段,解决导入少是否默认字段导致的报错问题;
					supMap.put("is_default", is_default);
					
					//如果重复则不添加
					Map<String, Object> MedInvSupbyCode = medInvMapper.queryMedInvSupbyCode(supMap);
					if(MedInvSupbyCode == null){
						List<Map<String, Object>> supList = new ArrayList<Map<String,Object>>();
						supList.add(supMap);
						medInvMapper.addMedInvSup(supList);
					}
				}
				/*处理供应商信息-------------------end-------------*/
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedInv\"}";
		}
	}
	
	/**
	 * @Description 
	 * 添加材料供应商<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedInvSup(Map<String,Object> entityMap)throws DataAccessException{
		try{
			/*处理供应商信息-------------------begin-----------*/
			List<Map<String,Object>> supList = new ArrayList<Map<String,Object>>();
				
			if(entityMap.get("supData") != null && !"".equals(entityMap.get("supData"))){

				String[] supData = entityMap.get("supData").toString().split("@");
				
				for(String s : supData){
					Map<String,Object> supMap = new HashMap<String,Object>();
					supMap.put("group_id", entityMap.get("group_id"));
					supMap.put("hos_id", entityMap.get("hos_id"));
					supMap.put("copy_code", entityMap.get("copy_code"));
					supMap.put("inv_id", entityMap.get("inv_id"));
					supMap.put("sup_id", s.split(",")[0]);
					supMap.put("is_default", "1".equals(s.split(",")[1]) ? 1 : 0);
					supList.add(supMap);
				}
				
				medInvMapper.deleteMedInvSup(entityMap);
				medInvMapper.addMedInvSup(supList);
			}else{
				return "{\"error\":\"请录入供应商！\",\"state\":\"false\"}";
			}
			/*处理供应商信息-------------------end-------------*/
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");

			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedInv\"}";
		}
	}
	
	/**
	 * @Description 
	 * 批量添加08105 药品材料表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			medInvMapper.addBatch(entityList);
			medInvDictMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedInv\"}";
		}
	}
	
	/**
	 * @Description 
	 * 批量添加08105 药品材料表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedInvSupBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			medInvMapper.deleteMedInvSupBatch(entityList);
			medInvMapper.addMedInvSup(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedInv\"}";
		}
	}
	
	/**
	 * @Description 
	 * 更新08105 药品材料表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			/*判断编码是否重复----------------begin------------*/
			Map<String, Object> existsCodeMap = new HashMap<String, Object>();
			existsCodeMap.put("group_id", entityMap.get("group_id"));
			existsCodeMap.put("hos_id", entityMap.get("hos_id"));
			existsCodeMap.put("copy_code", entityMap.get("copy_code"));
			existsCodeMap.put("field_table", "med_inv");
			existsCodeMap.put("field_id", "inv_id");
			existsCodeMap.put("field_id_value", entityMap.get("inv_id"));
			existsCodeMap.put("field_key1", "inv_code");
			existsCodeMap.put("field_value1", entityMap.get("inv_code"));
			int flag = sysFunUtilMapper.existsSysCodeNameByUpdate(existsCodeMap);
			if (flag != 0) {
				return "{\"error\":\"编码：" + entityMap.get("inv_code") + "已存在.\"}";
			}
			/*判断编码是否重复----------------end--------------*/
			//组装数据
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("inv_ids", entityMap.get("inv_id"));
			if(!entityMap.get("old_is_bar").equals(entityMap.get("is_bar"))){
				//有库存不能更改是否条码管理
				int num = medCommonMapper.getHoldBalanceByInv(map);
				if(num > 0){
					return "{\"error\":\"该材料库存不为0，不能更改是否条码管理！\"}";
				}
			}
			if(!entityMap.get("old_is_com").equals(entityMap.get("is_com"))){
				//有库存不能更改是否代销
				int num = medCommonMapper.getHoldBalanceByInv(map);
				if(num > 0){
					return "{\"error\":\"该材料库存不为0，不能更改是否代销管理！\"}";
				}
			}
			
			/*处理供应商信息-------------------begin-----------*/
			List<Map<String,Object>> supList = new ArrayList<Map<String,Object>>();
			if(entityMap.get("supData") != null && !"".equals(entityMap.get("supData"))){

				String[] supData = entityMap.get("supData").toString().split("@");
				
				for(String s : supData){
					Map<String,Object> supMap = new HashMap<String,Object>();
					supMap.put("group_id", entityMap.get("group_id"));
					supMap.put("hos_id", entityMap.get("hos_id"));
					supMap.put("copy_code", entityMap.get("copy_code"));
					supMap.put("inv_id", entityMap.get("inv_id"));
					supMap.put("sup_id", s.split(",")[0]);
					supMap.put("is_default", "1".equals(s.split(",")[1]) ? 1 : 0);
					supList.add(supMap);
				}
			}
			/*处理供应商信息-------------------end-------------*/
			
			
			/*处理证件信息-------------------begin-----------*/
			List<Map<String,Object>> certList = new ArrayList<Map<String,Object>>();
			if(entityMap.get("certIds") != null && !"".equals(entityMap.get("certIds"))){

				String[] certData = entityMap.get("certIds").toString().split(",");
				
				for(String id : certData){
					Map<String,Object> certMap = new HashMap<String,Object>();
					certMap.put("group_id", entityMap.get("group_id"));
					certMap.put("hos_id", entityMap.get("hos_id"));
					certMap.put("copy_code", entityMap.get("copy_code"));
					certMap.put("inv_id", entityMap.get("inv_id"));
					certMap.put("cert_id", id.split("@")[0]);
					certMap.put("cert_code", id.split("@")[1]);
					certList.add(certMap);
				}
			}
			/*处理证件信息-------------------end-------------*/
			
			
			medInvMapper.update(entityMap);
			
			//变更信息
			entityMap.put("change_user", SessionManager.getUserId());
			entityMap.put("change_date", new Date());
			if(entityMap.get("change_note") == null || "".equals(entityMap.get("change_note"))){
				entityMap.put("change_note", "变更");
			}
				
			if("1".equals(entityMap.get("is_change"))){
				//变更表所有记录is_stop = 1
				map.put("is_stop", "1");
				medInvDictMapper.updateIsStop(map);
				
				//新增变更表变更记录
				medInvDictMapper.add(entityMap);
			}else{
				//修改变更记录
				medInvDictMapper.update(entityMap);
			}

			//对应关系先删除再插入
			medInvMapper.deleteMedInvSup(entityMap);
			if(supList.size() > 0){
				medInvMapper.addMedInvSup(supList);
			}
			
			if(certList.size() > 0){
				//对应关系先删除再插入
				medInvCertRelaMapper.deleteMedInvCertRela(entityMap);
				medInvCertRelaMapper.addBatchMedInvCertRela(certList);
			}
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (DataAccessException e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");

			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMedInv\"}";
		}		
	}
	/**
	 * @Description 
	 * 批量更新08105 药品材料表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
		  medInvMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMedInv\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 批量更新材料信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedInvBatch(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			System.out.println("list>>>>>>>>>>>>"+entityMap);
			if(entityMap.get("inv_ids") != null && !"".equals(entityMap.get("inv_ids").toString())){
				List<Map<String, Object>> invList = medInvDictMapper.queryByCodes(entityMap);
				if(invList.size() == 0){
					return "{\"error\":\"请选择材料！\"}";
				}
				String user_id = SessionManager.getUserId();
				for(Map<String, Object> invMap : invList){
					
					if(entityMap.get("price_type") != null && !"".equals(entityMap.get("price_type").toString())){
						invMap.put("price_type", entityMap.get("price_type").toString());
					}else{
						invMap.put("price_type", invMap.get("price_type"));
					}
					if(entityMap.get("amortize_type") != null && !"".equals(entityMap.get("amortize_type").toString())){
						invMap.put("amortize_type", entityMap.get("amortize_type").toString());
					}else{
						invMap.put("amortize_type", invMap.get("amortize_type"));
					}
					if(entityMap.get("inv_model") != null && !"".equals(entityMap.get("inv_model").toString())){
						invMap.put("inv_model", entityMap.get("inv_model").toString());
					}else{
						invMap.put("inv_model", invMap.get("inv_model"));
					}
					if(entityMap.get("unit_code") != null && !"".equals(entityMap.get("unit_code").toString())){
						invMap.put("unit_code", entityMap.get("unit_code").toString());
					}else{
						invMap.put("unit_code", invMap.get("unit_code"));
					}
					if(entityMap.get("fac_id") != null && !"".equals(entityMap.get("fac_id").toString())){
						invMap.put("fac_id", entityMap.get("fac_id").toString());
					}else{
						invMap.put("fac_id", invMap.get("fac_id"));
					}
					if(entityMap.get("fac_no") != null && !"".equals(entityMap.get("fac_no").toString())){
						invMap.put("fac_no", entityMap.get("fac_no").toString());
					}else{
						invMap.put("fac_no", invMap.get("fac_no"));
					}
					if(entityMap.get("plan_price") != null && !"".equals(entityMap.get("plan_price").toString())){
						invMap.put("plan_price", entityMap.get("plan_price").toString());
					}else{
						invMap.put("plan_price", invMap.get("plan_price"));
					}
					if(entityMap.get("price_rate") != null && !"".equals(entityMap.get("price_rate").toString())){
						invMap.put("price_rate", entityMap.get("price_rate").toString());
					}else{
						invMap.put("price_rate", invMap.get("price_rate"));
					}
					if(entityMap.get("sell_price") != null && !"".equals(entityMap.get("sell_price").toString())){
						invMap.put("sell_price", entityMap.get("sell_price").toString());
					}else{
						invMap.put("sell_price", invMap.get("sell_price"));
					}
					if(entityMap.get("sdate") != null && !"".equals(entityMap.get("sdate").toString())){
						invMap.put("sdate", entityMap.get("sdate"));
					}else{
						invMap.put("sdate", invMap.get("sdate"));
					}
					if(entityMap.get("edate") != null && !"".equals(entityMap.get("edate").toString())){
						invMap.put("edate", entityMap.get("edate"));
					}else{
						invMap.put("edate", invMap.get("edate"));
					}
					if(entityMap.get("agent_name") != null && !"".equals(entityMap.get("agent_name").toString())){
						invMap.put("agent_name", entityMap.get("agent_name").toString());
					}else{
						invMap.put("agent_name", invMap.get("agent_name"));
					}
					if(entityMap.get("brand_name") != null && !"".equals(entityMap.get("brand_name").toString())){
						invMap.put("brand_name", entityMap.get("brand_name").toString());
					}else{
						invMap.put("brand_name", invMap.get("brand_name"));
					}
					if(entityMap.get("inv_usage") != null && !"".equals(entityMap.get("inv_usage").toString())){
						invMap.put("inv_usage", entityMap.get("inv_usage").toString());
					}else{
						invMap.put("inv_usage", invMap.get("inv_usage"));
					}
					if(entityMap.get("inv_structure") != null && !"".equals(entityMap.get("inv_structure").toString())){
						invMap.put("inv_structure", entityMap.get("inv_structure").toString());
					}else{
						invMap.put("inv_structure", invMap.get("inv_structure"));
					}
					if(entityMap.get("per_weight") != null && !"".equals(entityMap.get("per_weight").toString())){
						invMap.put("per_weight", entityMap.get("per_weight").toString());
					}else{
						invMap.put("per_weight", invMap.get("per_weight"));
					}
					if(entityMap.get("per_volum") != null && !"".equals(entityMap.get("per_volum").toString())){
						invMap.put("per_volum", entityMap.get("per_volum").toString());
					}else{
						invMap.put("per_volum", invMap.get("per_volum"));
					}
					if(entityMap.get("abc_type") != null && !"".equals(entityMap.get("abc_type").toString())){
						invMap.put("abc_type", entityMap.get("abc_type").toString());
					}else{
						invMap.put("abc_type", invMap.get("abc_type"));
					}
					if(entityMap.get("manage_type") != null && !"".equals(entityMap.get("manage_type").toString())){
						invMap.put("manage_type", entityMap.get("manage_type").toString());
					}else{
						invMap.put("manage_type", invMap.get("manage_type"));
					}
					if(entityMap.get("is_single_ven") != null && !"".equals(entityMap.get("is_single_ven").toString())){
						invMap.put("is_single_ven", entityMap.get("is_single_ven").toString());
					}else{
						invMap.put("is_single_ven", invMap.get("is_single_ven"));
					}
					if(entityMap.get("stora_tran_cond") != null && !"".equals(entityMap.get("stora_tran_cond").toString())){
						invMap.put("stora_tran_cond", entityMap.get("stora_tran_cond").toString());
					}else{
						invMap.put("stora_tran_cond", invMap.get("stora_tran_cond"));
					}
					if(entityMap.get("use_state") != null && !"".equals(entityMap.get("use_state").toString())){
						invMap.put("use_state", entityMap.get("use_state").toString());
					}else{
						invMap.put("use_state", invMap.get("use_state"));
					}
					if(entityMap.get("is_bid") != null && !"".equals(entityMap.get("is_bid").toString())){
						invMap.put("is_bid", entityMap.get("is_bid").toString());
					}else{
						invMap.put("is_bid", invMap.get("is_bid"));
					}
					if(entityMap.get("bid_date") != null && !"".equals(entityMap.get("bid_date").toString())){
						invMap.put("bid_date", entityMap.get("bid_date"));
					}else{
						invMap.put("bid_date", invMap.get("bid_date"));
					}
					if(entityMap.get("bid_code") != null && !"".equals(entityMap.get("bid_code").toString())){
						invMap.put("bid_code", entityMap.get("bid_code").toString());
					}else{
						invMap.put("bid_code", invMap.get("bid_code"));
					}
					if(entityMap.get("is_zero_store") != null && !"".equals(entityMap.get("is_zero_store").toString())){
						invMap.put("is_zero_store", entityMap.get("is_zero_store").toString());
					}else{
						invMap.put("is_zero_store", invMap.get("is_zero_store"));
					}
					if(entityMap.get("is_involved") != null && !"".equals(entityMap.get("is_involved").toString())){
						invMap.put("is_involved", entityMap.get("is_involved").toString());
					}else{
						invMap.put("is_involved", invMap.get("is_involved"));
					}
					if(entityMap.get("is_implant") != null && !"".equals(entityMap.get("is_implant").toString())){
						invMap.put("is_implant", entityMap.get("is_implant").toString());
					}else{
						invMap.put("is_implant", invMap.get("is_implant"));
					}
					if(entityMap.get("is_charge") != null && !"".equals(entityMap.get("is_charge").toString())){
						invMap.put("is_charge", entityMap.get("is_charge").toString());
					}else{
						invMap.put("is_charge", invMap.get("is_charge"));
					}
					if(entityMap.get("is_highvalue") != null && !"".equals(entityMap.get("is_highvalue").toString())){
						invMap.put("is_highvalue", entityMap.get("is_highvalue").toString());
					}else{
						invMap.put("is_highvalue", invMap.get("is_highvalue"));
					}
					if(entityMap.get("is_dura") != null && !"".equals(entityMap.get("is_dura").toString())){
						invMap.put("is_dura", entityMap.get("is_dura").toString());
					}else{
						invMap.put("is_dura", invMap.get("is_dura"));
					}
					if(entityMap.get("is_com") != null && !"".equals(entityMap.get("is_com").toString())){
						invMap.put("is_com", entityMap.get("is_com").toString());
					}else{
						invMap.put("is_com", invMap.get("is_com"));
					}
					if(entityMap.get("is_bar") != null && !"".equals(entityMap.get("is_bar").toString())){
						invMap.put("is_bar", entityMap.get("is_bar").toString());
					}else{
						invMap.put("is_bar", invMap.get("is_bar"));
					}
					if(entityMap.get("is_per_bar") != null && !"".equals(entityMap.get("is_per_bar").toString())){
						invMap.put("is_per_bar", entityMap.get("is_per_bar").toString());
					}else{
						invMap.put("is_per_bar", invMap.get("is_per_bar"));
					}
					if(entityMap.get("is_inv_bar") != null && !"".equals(entityMap.get("is_inv_bar").toString())){
						invMap.put("is_inv_bar", entityMap.get("is_inv_bar").toString());
					}else{
						invMap.put("is_inv_bar", invMap.get("is_inv_bar"));
					}
					if(entityMap.get("is_quality") != null && !"".equals(entityMap.get("is_quality").toString())){
						invMap.put("is_quality", entityMap.get("is_quality").toString());
					}else{
						invMap.put("is_quality", invMap.get("is_quality"));
					}
					if(entityMap.get("is_disinfect") != null && !"".equals(entityMap.get("is_disinfect").toString())){
						invMap.put("is_disinfect", entityMap.get("is_disinfect").toString());
					}else{
						invMap.put("is_disinfect", invMap.get("is_disinfect"));
					}
					if(entityMap.get("is_cert") != null && !"".equals(entityMap.get("is_cert").toString())){
						invMap.put("is_cert", entityMap.get("is_cert").toString());
					}else{
						invMap.put("is_cert", invMap.get("is_cert"));
					}
					if(entityMap.get("is_sec_whg") != null && !"".equals(entityMap.get("is_sec_whg").toString())){
						invMap.put("is_sec_whg", entityMap.get("is_sec_whg").toString());
					}else{
						invMap.put("is_sec_whg", invMap.get("is_sec_whg"));
					}
					if(entityMap.get("is_shel_make") != null && !"".equals(entityMap.get("is_shel_make").toString())){
						invMap.put("is_shel_make", entityMap.get("is_shel_make").toString());
					}else{
						invMap.put("is_shel_make", invMap.get("is_shel_make"));
					}
					if(entityMap.get("note") != null && !"".equals(entityMap.get("note").toString())){
						invMap.put("note", entityMap.get("note").toString());
					}else{
						invMap.put("note", invMap.get("note"));
					}
					if(entityMap.get("memory_encoding") != null && !"".equals(entityMap.get("memory_encoding").toString())){
						invMap.put("memory_encoding", entityMap.get("memory_encoding").toString());
					}else{
						invMap.put("memory_encoding", invMap.get("memory_encoding"));
					}
					if(entityMap.get("source_plan") != null && !"".equals(entityMap.get("source_plan").toString())){
						invMap.put("source_plan", entityMap.get("source_plan").toString());
					}else{
						invMap.put("source_plan", invMap.get("source_plan"));
					}
					
					//防止材料信息为空
					if(invMap.get("alias") == null || "".equals(invMap.get("alias").toString())){
						invMap.put("alias", "");
					}
					if(invMap.get("alias_spell") == null || "".equals(invMap.get("alias_spell").toString())){
						invMap.put("alias_spell", "");
					}
					invMap.put("is_add_sale", 0);
							
					//变更信息
					invMap.put("change_user", user_id);
					invMap.put("change_date", new Date());
					if(entityMap.get("change_note") != null && !"".equals(entityMap.get("change_note").toString())){
						invMap.put("change_note", entityMap.get("change_note").toString());
					}else{
						invMap.put("change_note", "变更");
					}
					//默认供应商
					if(entityMap.get("sup_id") != null && !"".equals(entityMap.get("sup_id").toString())){
						invMap.put("sup_id", entityMap.get("sup_id").toString());
					}
					invMap.put("is_default", "1");
				}
				//组装数据
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("inv_ids", entityMap.get("inv_ids"));
				if(entityMap.get("is_bar") != null && !"".equals(entityMap.get("is_bar").toString())){
					//有库存不能更改是否条码管理
					int num = medCommonMapper.getHoldBalanceByInv(map);
					if(num > 0){
						return "{\"error\":\"含库存不为0的材料，不能更改是否条码管理！\"}";
					}
				}
				if(entityMap.get("is_com") != null && !"".equals(entityMap.get("is_com").toString())){
					//有库存不能更改是否代销管理
					int num = medCommonMapper.getHoldBalanceByInv(map);
					if(num > 0){
						return "{\"error\":\"含库存不为0的材料，不能更改是否代销管理！\"}";
					}
				}
				System.out.println(invList);
				medInvMapper.updateBatch(invList);
					System.out.println("============="+entityMap.get("is_change"));
				if("1".equals(entityMap.get("is_change"))){
					//变更表所有记录is_stop = 1
					map.put("is_stop", "1");
					medInvDictMapper.updateIsStop(map);
					
					//新增变更表变更记录
					medInvDictMapper.addBatch(invList);
				}else{
					//修改变更记录
					medInvDictMapper.updateBatch(invList);
				}
				
				if(entityMap.get("sup_id") != null && !"".equals(entityMap.get("sup_id").toString())){
					//首先查看返回哪些材料inv_id中有sup_id,有  看is_default=0,则先update,然后插入
					//sup_id=0   则直接update,然后插入
					//entityMap.put("isDefault", "0");
					List<Map<String,Object>> inList = JsonListMapUtil.toListMapLower(medInvMapper.queryMedInvInSup(invList));
					if(inList.size()>0){
						medInvMapper.updateMedInvSupIsNotDefault(inList);
						medInvMapper.updateMedInvSupIsDefault(inList);
					}
					
					
					List<Map<String,Object>> notInList = JsonListMapUtil.toListMapLower(medInvMapper.queryMedInvNotInSup(invList));
					if(notInList.size() > 0){
						List<Map<String,Object>> newList = new ArrayList<Map<String,Object>>();
						for(Map<String,Object> mp: notInList){
							Map<String,Object> m  = new HashMap<String,Object>();
							m.put("group_id", mp.get("group_id"));
							m.put("hos_id", mp.get("hos_id"));
							m.put("copy_code", mp.get("copy_code"));
							m.put("inv_id", mp.get("inv_id"));
							m.put("sup_id", mp.get("sup_id"));
							m.put("is_default", "1");
							newList.add(m);
						}
						medInvMapper.updateMedInvSupIsNotDefault(newList);
						medInvMapper.addMedInvSup(newList);
					}
					
					
					
					/*int sup=medInvMapper.queryMedInvSupCode(entityMap);
					System.out.println("sup=============="+sup);
					if(sup>0){
						return "{\"error\":\"信息已存在,请重新添加.\",\"state\":\"true\"}";
					}else{
						//修改供应商为非默认（由于批量修改的材料有可能含该供应商会造成主键冲突所以改为删除）
						//medInvMapper.updateMedInvSupIsNotDefault(invList);
						//删除原默认供应商
						medInvMapper.deleteMedInvSupByDefault(invList);
						//添加新默认供应商
						medInvMapper.addMedInvSup(invList);
					}*/
					
				}

				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}else{
				return "{\"error\":\"请选择材料！\"}";
			}
		}catch (DataAccessException e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");

			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMedInv\"}";
		}		
	}
	/**
	 * @Description 
	 * 删除08105 药品材料表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
	    	//有库存不能删除
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("inv_ids", entityMap.get("inv_id"));
			int num = medCommonMapper.getHoldBalanceByInv(map);
			if(num > 0){
				return "{\"error\":\"该材料库存不为0，不能删除！\"}";
			}
    		
			//删除药品
			medInvMapper.delete(entityMap);
			
			//新增药品变更变表删除记录
			entityMap.put("change_user", SessionManager.getUserId());
			entityMap.put("change_date", new Date());
			entityMap.put("change_note", "删除");
			if(entityMap.get("source_plan") == null || "".equals(entityMap.get("source_plan"))){
				entityMap.put("source_plan", 1);
			}
			medInvDictMapper.addForDelete(entityMap);
			//更新变更表中inv_id为当前值的所有记录is_stop = 1
			map.put("is_stop", "1");
			medInvDictMapper.updateIsStop(map);
			
			//删除材料供应商对应关系
			medInvMapper.deleteMedInvSup(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
    	catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedInv\"}";
		}	
  }
    
	/**
	 * @Description 
	 * 批量删除08105 药品材料表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			//组装数据批量判断材料是否含有库存
			int group_id = 0;
			int hos_id = 0;
			String copy_code = "";
			StringBuffer inv_ids = new StringBuffer();
			for(Map<String, Object> map : entityList){
				if(group_id == 0){
					group_id = Integer.parseInt(map.get("group_id").toString());
				}
				if(hos_id == 0){
					hos_id =  Integer.parseInt(map.get("hos_id").toString());
				}
				if("".equals(copy_code)){
					copy_code = map.get("copy_code").toString();
				}
				inv_ids.append(map.get("inv_id").toString()).append(",");
			}
			System.out.println("group_id : "+group_id+", hos_id : "+hos_id+", copy_code : "+copy_code+", inv_ids : "+inv_ids);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", group_id);
			map.put("hos_id", hos_id);
			map.put("copy_code", copy_code);
			map.put("inv_ids", inv_ids.substring(0, inv_ids.length()-1));

			int num = medCommonMapper.getHoldBalanceByInv(map);
			if(num > 0){
				return "{\"error\":\"存在库存不为0的材料，不能删除！\"}";
			}
			
			//2017-03-24 判断材料是否被引用
			String reStr="";
			map.put("dict_code", "med_inv".toUpperCase());
			map.put("dict_id_str",inv_ids.substring(0, inv_ids.length()-1));
			map.put("acc_year", "");
			map.put("p_flag", "1");
			sysFunUtilMapper.querySysDictDelCheck(map);
			
			if(map.get("reNote")!=null) {
				reStr+=map.get("reNote");
			}
			
			
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败,选择的药品材料被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			
			medInvMapper.deleteBatch(entityList);
			
			//新增药品变更变表删除记录
			map.put("change_user", SessionManager.getUserId());
			map.put("change_date", new Date());
			map.put("change_note", "删除");
			if(map.get("source_plan") == null || "".equals(map.get("source_plan"))){
				map.put("source_plan", 1);
			}
			medInvDictMapper.addForDelete(map);
			//更新变更表中inv_id为当前值的所有记录is_stop = 1
			map.put("is_stop", "1");
			medInvDictMapper.updateIsStop(map);
			
			//删除材料供应商对应关系
			medInvMapper.deleteMedInvSupBatch(entityList);
			
			//2017-03-24 删除对应关系
			//删除科室配套表明细
			medDeptMatchMapper.deleteBatchMdmDetail(entityList);
			//删除仓库配套表明细
			medStoreMatchMapper.deleteBatchMsmDetail(entityList);
			//删除仓库材料信息
			medStoreInvMapper.deleteBatchMedStoreInvRela(entityList);
			//删除材料证件对应关系
			medInvCertRelaMapper.deleteBatchMedInvCertRelaByInv(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");

			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedInv\"}";
		}	
	}
	
	
	
	
	/**
	 * @Description 
	 * 批量审核08105 药品材料表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String auditMedInv(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {	
			

			
			
			     medInvMapper.auditMedInv(entityList);
			
			
				
				medInvDictMapper.auditMedInvDict(entityList);
			
		
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"审核失败 数据库异常 请联系管理员! 方法 auditMedInv\"}";
		}	
	}
	
	
	
	
	
	/**
	 * @Description 
	 * 添加08105 药品材料表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08105 药品材料表
		MedInv medInv = queryByCode(entityMap);

		if (medInv != null) {

			medInvMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			medInvMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (DataAccessException e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedInv\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集08105 药品材料表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = medInvMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medInvMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 材料改变药品类别查询改类别材料数
	 */
	@Override
	public String queryChangeMedTypeCode(Map<String,Object> entityMap) throws DataAccessException{
			List<Map<String,Object>> list = medInvMapper.queryChangeMedTypeCode(entityMap);
			return ChdJson.toJson(list);
	}
	
	/**
	 * 验证停用材料库存
	 */
	@Override
	public String queryStopTimeByCode(Map<String,Object> entityMap) throws DataAccessException{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("group_id",entityMap.get("group_id"));
		map.put("hos_id",entityMap.get("hos_id"));
		map.put("copy_code",entityMap.get("copy_code"));
		map.put("inv_id",entityMap.get("inv_id"));
		if(entityMap.get("is_com").equals("0")){
			map.put("table","MED_INV_HOLD");
		}else if(entityMap.get("is_com").equals("1")){
			map.put("table","MED_AFFI_INV_HOLD");
		}
		List<Map<String,Object>> list = medInvMapper.queryStopTimeByCode(map);
		return ChdJson.toJson(list);
	}
	
	/**
	 * @Description 
	 * 获取对象08105 药品材料表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medInvMapper.queryByCode(entityMap);
	}
	
	
	/**
	 * @Description 
	 * 获取08105 药品材料表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedInv
	 * @throws DataAccessException
	*/
	@Override
	public  <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medInvMapper.queryByUniqueness(entityMap);
	}
	
	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return medInvMapper.queryExists(entityMap);
	}
	@Override
	public String queryDict(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = medInvDictMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medInvDictMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 供应商列表
	 */
	@Override
	public String queryMedInvSup(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = medInvMapper.queryMedInvSup(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
	/**
	 * 供应商列表
	 */
	@Override
	public String queryMedInvSupList(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = medInvMapper.queryMedInvSupList(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = medInvMapper.queryMedInvSupList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	
	/**
	 * 不查停用材料
	 */
	@Override
	public String queryMedInvSupListDisable(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = medInvMapper.queryMedInvSupListDisable(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = medInvMapper.queryMedInvSupListDisable(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	@Override
	public <T> T queryDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return medInvDictMapper.queryByCode(entityMap);
	}
	/**
	 * 复制 时 根据材料名称、规格 查询 材料是否已存在
	 */
	@Override
	public String queryMedInvExist(Map<String, Object> mapVo) throws DataAccessException {
		int count = medInvMapper.queryMedInvExist(mapVo);
		if(count > 0){
			return "{\"info\":\"材料:"+mapVo.get("inv_name")+"规格为:"+mapVo.get("inv_model")+"的材料已存在.\",\"state\":\"false\"}";
		}else{
			return "{\"info\":\"校验通过.\",\"state\":\"true\"}";
		}
	}
	
	@Override
	public String queryMedInvCertInfo(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list= medInvMapper.queryMedInvCertInfo(entityMap);
		
		for(Map<String,Object> item :list){
			
			List<Map<String,Object>> supList = medInvMapper.queryCertSup(item);
			
			item.put("supData", JSONObject.parseObject(ChdJson.toJson(supList)));
		}
		
		return ChdJson.toJson(list);
	}
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	@Override
	public String queryHosFacDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(medInvMapper.queryHosFacDict(entityMap, rowBounds));
	}
	
	//入库模板打印（包含主从表）
		@Resource(name = "superPrintService")
		private final SuperPrintService superPrintService = null;
		@Override
		public String queryMedInvByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException {
			
				List<Map<String,Object>> map=medInvMapper.queryMedInvPrintTemlateByMainBatch(entityMap);
				//查询入库明细表
				//List<Map<String,Object>> list=medInvMapper.queryMedInvPrintTemlateByDetail(entityMap);
				
				return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,null);
				
			
		}
		
	
	//查询用于导出
	@Override
	public List<Map<String,Object>> queryMedInvList(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		return ChdJson.toListLower(medInvMapper.queryMedInvList(entityMap));
	}
		
	/**
	 * @Description 
	 * 导入<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String importData(Map<String, Object> entityMap)throws DataAccessException{
		
		try {	
			List<Map<String, Object>> invList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> supRelaList = new ArrayList<Map<String,Object>>();
			String group_id = SessionManager.getGroupId();
			String hos_id = SessionManager.getHosId();
			String copy_code = SessionManager.getCopyCode();
			String user_id = SessionManager.getUserId();
			
			Map<String, Object> seachMap = new HashMap<String, Object>();
			seachMap.put("group_id", group_id);
			seachMap.put("hos_id", hos_id);
			seachMap.put("copy_code", copy_code);
			seachMap.put("user_id", user_id);
			
			//药品目录--判断是否重复（键：药品名称-药品规格）
			List<Map<String, Object>> medInvList = medInvMapper.queryMedInvListForImport(seachMap);
			Map<String, String> medInvMap = new HashMap<String, String>();
			Map<String, String> invCodeMap = new HashMap<String, String>();
			for(Map<String, Object> medInv : medInvList){
				medInvMap.put(medInv.get("INV_NAME").toString()+","+medInv.get("INV_MODEL").toString(), medInv.get("INV_ID").toString());
				invCodeMap.put(medInv.get("INV_CODE").toString(), "1");
			}
			
			//药品分类，表med_type
			List<Map<String, Object>> medTypeList = medInvMapper.queryMedTypeListForImport(seachMap);
			Map<String, String> medTypeMap = new HashMap<String, String>();
			for(Map<String, Object> medType : medTypeList){
				medTypeMap.put(medType.get("MED_TYPE_CODE").toString(), medType.get("MED_TYPE_ID").toString()+","+medType.get("MED_TYPE_NO").toString());
				medTypeMap.put(medType.get("MED_TYPE_NAME").toString(), medType.get("MED_TYPE_ID").toString()+","+medType.get("MED_TYPE_NO").toString());
			}
			
			//计价方式
			Map<String, String> priceTypeMap = new HashMap<String, String>();
			priceTypeMap.put("0", "0");
			priceTypeMap.put("1", "1");
			priceTypeMap.put("2", "2");
			priceTypeMap.put("先进先出", "0");
			priceTypeMap.put("移动加权平均", "1");
			priceTypeMap.put("一次性加权平均", "2");
			
			//药品属性，表med_sx
			List<Map<String, Object>> medSxList = medInvMapper.queryMedSxListForImport(seachMap);
			Map<String, String> medSxMap = new HashMap<String, String>();
			for(Map<String, Object> medSx : medSxList){
				medSxMap.put(medSx.get("MED_SX_NAME").toString(), medSx.get("MED_SX_ID").toString());
			}
			
			//药品剂型，表med_jx
			List<Map<String, Object>> medJxList = medInvMapper.queryMedJxListForImport(seachMap);
			Map<String, String> medJxMap = new HashMap<String, String>();
			for(Map<String, Object> medJx : medJxList){
				medJxMap.put(medJx.get("MED_JX_NAME").toString(), medJx.get("MED_JX_ID").toString());
			}
			
			//计量单位，表hos_unit
			List<Map<String, Object>> hosUnitList = medInvMapper.queryHosUnitListForImport(seachMap);
			Map<String, String> hosUnitMap = new HashMap<String, String>();
			for(Map<String, Object> hosUnit : hosUnitList){
				hosUnitMap.put(hosUnit.get("UNIT_CODE").toString(), hosUnit.get("UNIT_CODE").toString());
				hosUnitMap.put(hosUnit.get("UNIT_NAME").toString(), hosUnit.get("UNIT_CODE").toString());
			}
			
			//生产厂商，表hos_fac_dict
			List<Map<String, Object>> hosFacList = medInvMapper.queryHosFacListForImport(seachMap);
			Map<String, String> hosFacMap = new HashMap<String, String>();
			for(Map<String, Object> hosFac : hosFacList){
				hosFacMap.put(hosFac.get("FAC_CODE").toString(), hosFac.get("FAC_ID").toString()+","+hosFac.get("FAC_NO").toString());
				hosFacMap.put(hosFac.get("FAC_NAME").toString(), hosFac.get("FAC_ID").toString()+","+hosFac.get("FAC_NO").toString());
			}
			
			//供应商，表hos_sup_dict
			List<Map<String, Object>> hosSupList = medInvMapper.queryHosSupListForImport(seachMap);
			Map<String, String> hosSupMap = new HashMap<String, String>();
			for(Map<String, Object> hosSup : hosSupList){
				hosSupMap.put(hosSup.get("SUP_CODE").toString(), hosSup.get("SUP_ID").toString());
				hosSupMap.put(hosSup.get("SUP_NAME").toString(), hosSup.get("SUP_ID").toString());
			}
			
			//ABC分类不需要字典
			
			//是否
			Map<String, String> yesOrNoMap = new HashMap<String, String>();
			yesOrNoMap.put("1", "1");
			yesOrNoMap.put("0", "0");
			yesOrNoMap.put("是", "1");
			yesOrNoMap.put("否", "0");
			
			//管理类别，表med_inv_mana_type
			List<Map<String, Object>> manaTypeList = medInvMapper.queryManaTypeListForImport(seachMap);
			Map<String, String> manaTypeMap = new HashMap<String, String>();
			for(Map<String, Object> manaType : manaTypeList){
				manaTypeMap.put(manaType.get("MANAGE_TYPE_NAME").toString(), manaType.get("MANAGE_TYPE").toString());
			}
			
			//计划来源
			Map<String, String> sourcePlanMap = new HashMap<String, String>();
			sourcePlanMap.put("1", "1");
			sourcePlanMap.put("2", "2");
			sourcePlanMap.put("科室申领", "1");
			sourcePlanMap.put("库房报备", "2");
			
			//存放错误信息
			StringBuffer errorJson = new StringBuffer();
			
			//解析前台数据
			String data = entityMap.get("data").toString();
			List<Map<String, List<String>>> dataList = SpreadTableJSUtil.toListMap(data, 1);
			if(dataList==null || dataList.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			String inv_code;
			String inv_id;
			boolean is_loop = true;
			int index = 0;
			int max_length = 0;
			String type_max_inv_code = null;
			List<String> rowList=null;
			
			String invKey;
			for(Map<String, List<String>> dataMap : dataList){
				Map<String, Object> map = new HashMap<String, Object>();
				
				/**校验药品编码************begin*****************/
				rowList = dataMap.get("药品编码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，药品编码为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("inv_code", rowList.get(1));
				/**校验药品编码************end*******************/
				
				/**校验药品名称************begin*****************/
				rowList = dataMap.get("药品名称");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，药品名称为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("inv_name", rowList.get(1));
				map.put("spell_code", StringTool.toPinyinShouZiMu(rowList.get(1)));
				map.put("wbx_code", StringTool.toWuBi(rowList.get(1)));
				/**校验药品名称************end*******************/
				invKey = rowList.get(1);
	
				/**校验药品规格************begin*****************/
				rowList = dataMap.get("药品规格");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，药品规格为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("inv_model", rowList.get(1));
				/**校验药品规格************end*******************/
				invKey += "," + rowList.get(1);
				
				if(medInvMap.containsKey(invKey)){
					return "{\"warn\":\"" + rowList.get(0) + "，药品规格已存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}else{
					medInvMap.put(invKey, "0");
				}
				
				/**校验别名******begin*****************/
				rowList = dataMap.get("别名");
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
					map.put("alias", rowList.get(1));
					map.put("alias_spell", StringTool.toPinyinShouZiMu(rowList.get(1)));
				}else{
					map.put("alias", "");
					map.put("alias_spell", "");
				}
				/**校验别名******end*******************/
				
				/**校验药品类别**************begin*****************/
				rowList = dataMap.get("药品类别");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，药品类别为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!medTypeMap.containsKey(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，药品类别不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("med_type_id", medTypeMap.get(rowList.get(1)).split(",")[0]);
				map.put("med_type_no", medTypeMap.get(rowList.get(1)).split(",")[1]);
				/**校验药品类别**********end*******************/
				
				/**校验计价方法******begin*****************/
				rowList = dataMap.get("计价方法");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，计价方法为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!priceTypeMap.containsKey(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，计价方法不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("price_type", priceTypeMap.get(rowList.get(1)));
				/**校验计价方法******end*******************/
				
				/**校验药品属性******begin*****************/
				rowList = dataMap.get("药品属性");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，药品属性为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!medSxMap.containsKey(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，药品属性不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("med_sx_id", medSxMap.get(rowList.get(1)));
				/**校验药品属性******end*******************/
				
				/**校验药品剂型******begin*****************/
				rowList = dataMap.get("药品剂型");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，药品剂型为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!medJxMap.containsKey(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，药品剂型不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("med_jx_id", medJxMap.get(rowList.get(1)));
				/**校验药品剂型******end*******************/
				
				/**校验入库单位******begin*****************/
				rowList = dataMap.get("入库单位");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，入库单位为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!hosUnitMap.containsKey(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，入库单位不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("unit_code", hosUnitMap.get(rowList.get(1)));
				/**校验入库单位******end*******************/
	
				/**校验病区拆零系数**********begin*****************/
				rowList = dataMap.get("病区拆零系数");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，病区拆零系数为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!NumberUtil.isNumeric(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，病区拆零系数为非法数字！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("bqplxs", rowList.get(1));
				/**校验病区拆零系数**********end*******************/
				
				/**校验病区单位******begin*****************/
				rowList = dataMap.get("病区单位");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，病区单位为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!hosUnitMap.containsKey(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，病区单位不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("bq_unit_code", hosUnitMap.get(rowList.get(1)));
				/**校验病区单位******end*******************/
	
				/**校验门诊拆零系数**********begin*****************/
				rowList = dataMap.get("门诊拆零系数");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，门诊拆零系数为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!NumberUtil.isNumeric(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，门诊拆零系数为非法数字！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("mzplxs", rowList.get(1));
				/**校验门诊拆零系数**********end*******************/
				
				/**校验门诊单位******begin*****************/
				rowList = dataMap.get("门诊单位");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，门诊单位为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!hosUnitMap.containsKey(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，门诊单位不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("mz_unit_code", hosUnitMap.get(rowList.get(1)));
				/**校验门诊单位******end*******************/
				
				/**校验生产厂商******begin*****************/
				rowList = dataMap.get("生产厂商");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，生产厂商为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!hosFacMap.containsKey(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，生产厂商不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("fac_id", hosFacMap.get(rowList.get(1)).split(",")[0]);
				map.put("fac_no", hosFacMap.get(rowList.get(1)).split(",")[1]);
				/**校验生产厂商******end*******************/
	
				/**校验计划价**********begin*****************/
				rowList = dataMap.get("计划价");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("plan_price", "0");
				}else{
					if(!NumberUtil.isNumeric(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，计划价为非法数字！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("plan_price", rowList.get(1));
				}
				/**校验计划价**********end*******************/
	
				/**校验加价率**********begin*****************/
				rowList = dataMap.get("加价率");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("price_rate", "0");
				}else{
					if(!NumberUtil.isNumeric(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，加价率为非法数字！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("price_rate", rowList.get(1));
				}
				/**校验加价率**********end*******************/
	
				/**校验零售价**********begin*****************/
				rowList = dataMap.get("零售价");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("sell_price", "0");
				}else{
					if(!NumberUtil.isNumeric(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，零售价为非法数字！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("sell_price", rowList.get(1));
				}
				/**校验零售价**********end*******************/
	
				/**校验启用日期**********begin*****************/
				rowList = dataMap.get("启用日期");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("sdate", null);
				}else{
					map.put("sdate", DateUtil.stringToDate(rowList.get(1), "yyyy-MM-dd"));
				}
				/**校验启用日期**********end*******************/
	
				/**校验停用日期**********begin*****************/
				rowList = dataMap.get("停用日期");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("edate", null);
				}else{
					map.put("edate", DateUtil.stringToDate(rowList.get(1), "yyyy-MM-dd"));
				}
				/**校验停用日期**********end*******************/
	
				/**校验代理商**********begin*****************/
				rowList = dataMap.get("代理商");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("agent_name", null);
				}else{
					map.put("agent_name", rowList.get(1));
				}
				/**校验代理商**********end*******************/
	
				/**校验品牌名称**********begin*****************/
				rowList = dataMap.get("品牌名称");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("brand_name", null);
				}else{
					map.put("brand_name", rowList.get(1));
				}
				/**校验品牌名称**********end*******************/
	
				/**校验药品用途**********begin*****************/
				rowList = dataMap.get("药品用途");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("inv_usage", null);
				}else{
					map.put("inv_usage", rowList.get(1));
				}
				/**校验药品用途**********end*******************/
	
				/**校验包装规格**********begin*****************/
				rowList = dataMap.get("包装规格");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("inv_structure", null);
				}else{
					map.put("inv_structure", rowList.get(1));
				}
				/**校验包装规格**********end*******************/
	
				/**校验单位重量**********begin*****************/
				rowList = dataMap.get("单位重量");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("per_weight", null);
				}else{
					map.put("per_weight", rowList.get(1));
				}
				/**校验单位重量**********end*******************/
	
				/**校验单位体积**********begin*****************/
				rowList = dataMap.get("单位体积");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("per_volum", null);
				}else{
					map.put("per_volum", rowList.get(1));
				}
				/**校验单位体积**********end*******************/
	
				/**校验ABC分类**********begin*****************/
				rowList = dataMap.get("ABC分类");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("abc_type", null);
				}else{
					map.put("abc_type", rowList.get(1));
				}
				/**校验ABC分类**********end*******************/
	
				/**校验管理类别**********begin*****************/
				rowList = dataMap.get("管理类别");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("manage_type", null);
				}else{
					if(!manaTypeMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，管理类别不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("manage_type", manaTypeMap.get(rowList.get(1)));
				}
				/**校验管理类别**********end*******************/
	
				/**校验是否在用**********begin*****************/
				rowList = dataMap.get("是否在用");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("use_state", 1);
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否在用不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("use_state", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否在用**********end*******************/
	
				/**校验储运条件**********begin*****************/
				rowList = dataMap.get("储运条件");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("stora_tran_cond", null);
				}else{
					map.put("stora_tran_cond", rowList.get(1));
				}
				/**校验储运条件**********end*******************/
	
				/**校验是否中标**********begin*****************/
				rowList = dataMap.get("是否中标");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_bid", 0);
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否中标不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_bid", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否中标**********end*******************/
	
				/**校验中标日期**********begin*****************/
				rowList = dataMap.get("中标日期");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("bid_date", null);
				}else{
					map.put("bid_date", DateUtil.stringToDate(rowList.get(1), "yyyy-MM-dd"));
				}
				/**校验中标日期**********end*******************/
	
				/**校验中标编码**********begin*****************/
				rowList = dataMap.get("中标编码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("bid_code", null);
				}else{
					map.put("bid_code", rowList.get(1));
				}
				/**校验中标编码**********end*******************/
	
				/**校验是否唯一供应商**********begin*****************/
				rowList = dataMap.get("是否唯一供应商");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_single_ven", 0);
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否唯一供应商不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_single_ven", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否唯一供应商**********end*******************/
	
				/**校验存储编码**********begin*****************/
				rowList = dataMap.get("存储编码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("memory_encoding", null);
				}else{
					map.put("memory_encoding", rowList.get(1));
				}
				/**校验存储编码**********end*******************/
	
				/**校验计划来源**********begin*****************/
				rowList = dataMap.get("计划来源");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("source_plan", 1);
				}else{
					if(!sourcePlanMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，计划来源不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("source_plan", sourcePlanMap.get(rowList.get(1)));
				}
				/**校验计划来源**********end*******************/
	
				/**校验是否零库存管理**********begin*****************/
				rowList = dataMap.get("是否零库存管理");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_zero_store", 0);
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否零库存管理不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_zero_store", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否零库存管理**********end*******************/
	
				/**校验是否条码管理**********begin*****************/
				rowList = dataMap.get("是否条码管理");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_bar", 0);
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否条码管理不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_bar", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否条码管理**********end*******************/
	
				/**校验是否个体码管理**********begin*****************/
				rowList = dataMap.get("是否个体码管理");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_per_bar", 0);
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否个体码管理不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_per_bar", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否个体码管理**********end*******************/
	
				/**校验是否品种码管理**********begin*****************/
				rowList = dataMap.get("是否品种码管理");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_inv_bar", 0);
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否品种码管理不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_inv_bar", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否品种码管理**********end*******************/
	
				/**校验是否保质期管理**********begin*****************/
				rowList = dataMap.get("是否保质期管理");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_quality", 0);
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否保质期管理不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_quality", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否保质期管理**********end*******************/
	
				/**校验是否证件管理**********begin*****************/
				rowList = dataMap.get("是否证件管理");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_cert", 0);
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否证件管理不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_cert", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否证件管理**********end*******************/
	
				/**校验是否科室库管理**********begin*****************/
				rowList = dataMap.get("是否科室库管理");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_sec_whg", 0);
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否科室库管理不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_sec_whg", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否科室库管理**********end*******************/
	
				/**校验是否自制品**********begin*****************/
				rowList = dataMap.get("是否自制品");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_shel_make", 0);
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否自制品不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_shel_make", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否自制品**********end*******************/
				
				//必填项
				map.put("group_id", group_id);
				map.put("hos_id", hos_id);
				map.put("copy_code", copy_code);
				map.put("amortize_type", null);
				map.put("is_batch", 1);
				map.put("is_implant", 0);
				map.put("is_involved", 0);
				map.put("is_charge", 0);
				map.put("is_highvalue", 0);
				map.put("is_dura", 0);
				map.put("is_com", 0);
				map.put("is_disinfect", 0);
				map.put("is_add_sale", 0);
				map.put("state", 0);
				map.put("note", null);
				map.put("bar_code_new", map.get("inv_code"));
				map.put("inv_id", medInvMapper.queryMedInvSeq());
				//变更表
				map.put("change_user", SessionManager.getUserId());
				map.put("change_date", new Date());
				map.put("change_note", "新增");
				map.put("is_stop", 0);
				/**处理药品编码*****************begin*****************/
				if("自动生成".equals(map.get("inv_code").toString())){
					inv_code = "";
					is_loop = true;
					index = 0;
					max_length = 0;
					type_max_inv_code = null;
	
					/**查询系统参数08002获得编码位数---begin----*/
					String maxLength = MyConfig.getSysPara("08002").toString();
					if(NumberUtil.isNumeric(maxLength)){
						max_length = Integer.valueOf(maxLength);
					}else{
						return "{\"error\":\"系统参数08002格式错误.\"}";
					}
					while(is_loop){
						/**用于查询并更新最大流水码----begin-----*/
						Map<String, Object> loopMap = new HashMap<String, Object>();
						loopMap.putAll(seachMap);
						loopMap.put("type_code", "2");
						if(!"1".equals(MyConfig.getSysPara("08014"))){
							String max_no = medNoOtherMapper.queryMedNoOther(loopMap);//获取当前编码最大值
							//如果不存在则插入
							if(max_no == null || "".equals(max_no)){
								max_no = "1";
								loopMap.put("max_no", Integer.valueOf(max_no));
								medNoOtherMapper.insertMedNoOther(loopMap);
							}else{
								loopMap.put("max_no", Integer.valueOf(max_no)+1);
								medNoOtherMapper.updateMedNoOther(loopMap);
							}
							/**用于查询并更新最大流水码----end-------*/
							
							inv_code = "" + max_no;
							for (int i = inv_code.length() ; i < max_length; i++) {
								inv_code = "0" + inv_code;
							}
						}else{
							loopMap.put("med_type_id", map.get("med_type_id"));
							if(index == 0){
								type_max_inv_code = medInvMapper.queryMedInvMaxByType(loopMap);
							}else{
								type_max_inv_code = String.valueOf(Long.parseLong(type_max_inv_code) + 1);
							}
							//获取材料类别编码
							Map<String,Object> typeMap = new HashMap<String,Object>();
							typeMap.put("group_id", group_id);
							typeMap.put("hos_id", hos_id);
							typeMap.put("copy_code", copy_code);
							typeMap.put("med_type_id", map.get("med_type_id"));
							if(map.get("med_type_no") != null && !"".equals(String.valueOf(map.get("med_type_no")))){
								typeMap.put("med_type_no", map.get("med_type_no"));
							}else{
								typeMap.put("is_stop", "0");
							}
							MedTypeDict mtd = medTypeDictMapper.queryMedTypeDictByUniqueness(typeMap);
				
							/**查询系统参数08002获得编码位数---end------*/
							/**算法：
							 * 该类别下有材料：1||'inv_code(0101000001)' + 1 = 10101000002 
							 * 该类别下没有材料：1||'inv_code('')' + 1 = 2
							 * 如果值为2证明该类别没有生成过材料编码应从1开始
							 * 如果不是需要把前缀1去掉*/
							if(type_max_inv_code.equals("2")){
								inv_code = "1";
								for (int i = 1 ; i < max_length; i++) {
									inv_code = "0" + inv_code;
								}
								inv_code = mtd.getMed_type_code() + inv_code;
							}else{
								inv_code = type_max_inv_code.substring(1, type_max_inv_code.length());
							}
						}
						
						//检验自动生成的编码是否重复
						if(!invCodeMap.containsKey(inv_code)){
							invCodeMap.put(inv_code, "1");
							is_loop = false;
						}
						index ++;
					}
					map.put("inv_code", inv_code);
				}
				/**处理药品编码*****************end*******************/
				
				//增加新记录
				invList.add(map);
				
				/**校验供应商******begin*****************/
				rowList = dataMap.get("供应商");
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
					
					if(!hosSupMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，供应商不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
	
					Map<String, Object> supRMap = new HashMap<String, Object>();
					supRMap.put("group_id", group_id);
					supRMap.put("hos_id", hos_id);
					supRMap.put("copy_code", copy_code);
					supRMap.put("inv_id", map.get("inv_id"));
					supRMap.put("sup_id", hosSupMap.get(rowList.get(1)));
					supRMap.put("is_default", 1);
					
					//增加新记录
					supRelaList.add(supRMap);
				}
				/**校验供应商******end*******************/
			}
			
			//批量添加
			medInvMapper.addBatch(invList);
			medInvDictMapper.addBatch(invList);
			//批量添加对应关系
			if(supRelaList.size()>0){
				medInvMapper.addMedInvSup(supRelaList);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}	
	
}
