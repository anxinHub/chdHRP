/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.info.basic;
   
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.mvc.Mvcs;
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
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoOtherMapper;
import com.chd.hrp.mat.dao.info.basic.MatInvCertRelaMapper;
import com.chd.hrp.mat.dao.info.basic.MatInvDictMapper;
import com.chd.hrp.mat.dao.info.basic.MatInvMapper;
import com.chd.hrp.mat.dao.info.basic.MatStoreInvMapper;
import com.chd.hrp.mat.dao.info.basic.MatStoreTypeMapper;
import com.chd.hrp.mat.dao.info.basic.MatTypeDictMapper;
import com.chd.hrp.mat.dao.info.relaset.MatDeptMatchMapper;
import com.chd.hrp.mat.dao.info.relaset.MatStoreMatchMapper;
import com.chd.hrp.mat.entity.MatInv;
import com.chd.hrp.mat.entity.MatStoreType;
import com.chd.hrp.mat.entity.MatTypeDict;
import com.chd.hrp.mat.service.info.basic.MatInvService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/** 
 *    
 * @Description:
 * 04105 物资材料表 
 * @Table:
 * MAT_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matInvService")
public class MatInvServiceImpl implements MatInvService {

	private static Logger logger = Logger.getLogger(MatInvServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matInvMapper")
	private final MatInvMapper matInvMapper = null;
	@Resource(name = "matInvDictMapper")
	private final MatInvDictMapper matInvDictMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "matNoOtherMapper")
	private final MatNoOtherMapper matNoOtherMapper = null;
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	@Resource(name = "matTypeDictMapper")
	private final MatTypeDictMapper matTypeDictMapper = null;
    @Resource(name = "matStoreTypeMapper")
    private final MatStoreTypeMapper matStoreTypeMapper = null;
    @Resource(name = "matStoreInvMapper")
    private final MatStoreInvMapper matStoreInvMapper = null;
    @Resource(name = "matDeptMatchMapper")
    private final MatDeptMatchMapper matDeptMatchMapper = null;
    @Resource(name = "matStoreMatchMapper")
    private final MatStoreMatchMapper matStoreMatchMapper = null;
    @Resource(name = "matInvCertRelaMapper")
    private final MatInvCertRelaMapper matInvCertRelaMapper = null;
    
	/**
	 * @Description 
	 * 添加04105 物资材料表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		try {
			// 默认按批管理
			entityMap.put("is_batch", 1);
			entityMap.put("state", 0);
			if(entityMap.get("plan_price") == null || "".equals(entityMap.get("plan_price"))){
				entityMap.put("plan_price", 0);
			}
			// 处理日期
			if (entityMap.get("sdate") != null && !"".equals(entityMap.get("sdate"))) {
				entityMap.put("sdate", DateUtil.stringToDate(entityMap.get("sdate")
						.toString(), "yyyy-MM-dd"));
			}
			if (entityMap.get("edate") != null && !"".equals(entityMap.get("edate"))) {
				entityMap.put("edate", DateUtil.stringToDate(entityMap.get("edate")
						.toString(), "yyyy-MM-dd"));
			}
			if (entityMap.get("bid_date") != null && !"".equals(entityMap.get("bid_date"))) {
				entityMap.put("bid_date", DateUtil.stringToDate(entityMap.get("bid_date")
						.toString(), "yyyy-MM-dd"));
			}
	 
			entityMap.put("spell_code",
					StringTool.toPinyinShouZiMu(entityMap.get("inv_name").toString()));
			entityMap.put("wbx_code",
					StringTool.toWuBi(entityMap.get("inv_name").toString()));

			entityMap.put("alias_spell",
					StringTool.toPinyinShouZiMu(entityMap.get("alias").toString()));
			
			//判断编码是否自动生成
			if("自动生成".equals(entityMap.get("inv_code"))){
				boolean is_loop = true;
				int index = 0;
				String inv_code = "";
				int max_length = 0;
				String type_max_inv_code = null;
				Map<String, String> maxMap = null;
				while(is_loop){
					/*用于查询并更新最大流水码----begin-----*/
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					map.put("type_code", "2");
					if("0".equals(MyConfig.getSysPara("04014"))){
						String max_no = matNoOtherMapper.queryMatNoOther(map);//获取当前编码最大值
						//如果不存在则插入
						if(max_no == null || "".equals(max_no)){
							max_no = "1";
							map.put("max_no", Integer.valueOf(max_no));
							matNoOtherMapper.insertMatNoOther(map);
						}else{
							map.put("max_no", Integer.valueOf(max_no)+1);
							matNoOtherMapper.updateMatNoOther(map);
						}
						/*用于查询并更新最大流水码----end-------*/
						
						/*查询系统参数04002获得编码位数---begin----*/
						map.put("group_id", entityMap.get("group_id"));
						map.put("hos_id", entityMap.get("hos_id"));
						map.put("copy_code", entityMap.get("copy_code"));
						map.put("para_code", "04002");
						String reg = "\\d+";
						String maxLength =matCommonMapper.getMatAccPara(map);
						if(maxLength.matches(reg)){
							
							max_length = Integer.valueOf(matCommonMapper.getMatAccPara(map));
							/*查询系统参数04002获得编码位数---end------*/
							//补齐流水码位数
							inv_code = completeInvCodeByLen(max_no, max_length);
						}else{
							return "{\"error\":\"系统参数04002格式错误.\"}";
						}
					}else if("1".equals(MyConfig.getSysPara("04014"))){
						if(index == 0){
							type_max_inv_code = matInvMapper.queryMatInvMaxByType(entityMap);
						}else{
							type_max_inv_code = String.valueOf(Long.parseLong(type_max_inv_code) + 1);
						}
						//获取材料类别编码
						Map<String,Object> typeMap = new HashMap<String,Object>();
						typeMap.put("group_id", entityMap.get("group_id"));
						typeMap.put("hos_id", entityMap.get("hos_id"));
						typeMap.put("copy_code", entityMap.get("copy_code"));
						typeMap.put("mat_type_id", entityMap.get("mat_type_id"));
						if(entityMap.get("mat_type_no") != null && !"".equals(String.valueOf(entityMap.get("mat_type_no")))){
							typeMap.put("mat_type_no", entityMap.get("mat_type_no"));
						}else{
							typeMap.put("is_stop", "0");
						}
						MatTypeDict mtd = matTypeDictMapper.queryMatTypeDictByUniqueness(typeMap);
			
						/*查询系统参数04002获得编码位数---end------*/
						/**算法：
						 * 该类别下有材料：1||'inv_code(0101000001)' + 1 = 10101000002 
						 * 该类别下没有材料：1||'inv_code('')' + 1 = 2
						 * 如果值为2证明该类别没有生成过材料编码应从1开始
						 * 如果不是需要把前缀1去掉*/
						/*查询系统参数04002获得编码位数---begin----*/
						map.put("group_id", entityMap.get("group_id"));
						map.put("hos_id", entityMap.get("hos_id"));
						map.put("copy_code", entityMap.get("copy_code"));
						map.put("para_code", "04002");
						String reg = "\\d+";
						String maxLength =matCommonMapper.getMatAccPara(map);
						if(maxLength.matches(reg)){
							max_length = Integer.valueOf(matCommonMapper.getMatAccPara(map));
						if(type_max_inv_code.equals("2")){
							
							inv_code = mtd.getMat_type_code() + completeInvCodeByLen("1", max_length);
						}else{
							
							inv_code = type_max_inv_code.substring(1, type_max_inv_code.length());
						}
						
					  }else{
							return "{\"error\":\"系统参数04002格式错误.\"}";
						}
					}else if("2".equals(MyConfig.getSysPara("04014"))){
						//获取自动生成编码的流水号位数
						int maxLength = Integer.valueOf(MyConfig.getSysPara("04002"));
						if(type_max_inv_code == null || "".equals(type_max_inv_code)){
							//获取材料类别下的最大材料编码
							map.put("mat_type_id", entityMap.get("mat_type_id"));
							maxMap = matInvMapper.getMaxInvCodeByType(map);
							
							type_max_inv_code = maxMap.get("INV_CODE");
						}
						
						if(type_max_inv_code == null || "".equals(type_max_inv_code)){
							inv_code = completeInvCodeByLen("1", maxLength);
						}else{
							//截取材料编码的流水码(流水码设置的是几位就是材料编码的后几位)
							String tmp_code = type_max_inv_code;
							if(type_max_inv_code.length() > maxLength){
								tmp_code = type_max_inv_code.substring(type_max_inv_code.length()-maxLength, type_max_inv_code.length());
							}else{
								if(!NumberUtil.isNumeric(tmp_code)){
									return "{\"error\":\"自动生成编码失败！原因：类别下最大编码小于流水号位数，并且最大编码含非数字的字符\"}";
								}
							}
							//补齐（流水号+1）后的位数
							inv_code = completeInvCodeByLen(String.valueOf(Integer.valueOf(tmp_code) + 1), maxLength);
						}

						if(type_max_inv_code != null && !"".equals(type_max_inv_code) && type_max_inv_code.length() > maxLength){
							//加原有前缀
							inv_code = type_max_inv_code.substring(0, type_max_inv_code.length()-maxLength) + inv_code;
						}else{
							//加类别前缀
							inv_code = maxMap.get("MAT_TYPE_CODE") + inv_code;
						}
						
						type_max_inv_code = inv_code;
					}
					
					/*检验自动生成的编码是否重复*/
					map.put("field_table", "mat_inv");
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
				
				Object abc= MyConfig.getSysPara("04070");
		       
				
				if(abc!=null && abc.equals("1") && entityMap.get("is_inv_bar").equals("1") ){
					entityMap.put("bar_code_new", inv_code);
					
				}else {
					
					entityMap.put("bar_code_new", entityMap.get("bar_code_new"));
				}
				
				
				
			}
			
			/*判断编码是否重复----------------begin------------*/
			Map<String, Object> existsCodeMap = new HashMap<String, Object>();
			existsCodeMap.put("group_id", entityMap.get("group_id"));
			existsCodeMap.put("hos_id", entityMap.get("hos_id"));
			existsCodeMap.put("copy_code", entityMap.get("copy_code"));
			existsCodeMap.put("field_table", "mat_inv");
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
			entityMap.put("inv_id", matInvMapper.queryMatInvSeq());
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
			
			
			//物资启用日期为添加日期  东阳需求
			if (entityMap.get("sdate")==null || StringUtils.isBlank(entityMap.get("sdate").toString())) {
				entityMap.put("sdate", new Date());
			}
			
			/*
			 *材料增加触发器 
			 * */

			if(("1").equals(MyConfig.getSysPara("04074"))){
				
				logger.debug("------------材料字典添加数据传入HIS存储过程开始-------------");
				
				String message = triggerMatInv(entityMap,1);  
				
				logger.debug(message);
				
				logger.debug("------------材料字典添加数据传入HIS存储过程结束-------------");
			}
			
			//新增物资
			matInvMapper.add(entityMap);
			
			//新增变更
			entityMap.put("change_user", SessionManager.getUserId());
			entityMap.put("change_date", new Date());
			entityMap.put("change_note", "新增");
			matInvDictMapper.add(entityMap);

			if(supList.size() > 0){
				matInvMapper.addMatInvSup(supList);
			}
			
			if(certList.size() > 0){
				//插入材料证件对应关系
				matInvCertRelaMapper.addBatchMatInvCertRela(certList);
			}
			
			//2017/03/23 将controller中的代码移到了此处并优化,解决报错问题
			/*处理仓库材料对应关系信息-------------------begin-------------*/
			MatStoreType matStoreType = null;
			//存在同一分类对应多个仓库的 一对多关系
			List<MatStoreType> listMst=new ArrayList<MatStoreType>();
			
			
			if(entityMap.get("mat_type_id") != null && ! "".equals(entityMap.get("mat_type_id")) ){
				//存在同一分类对应多个仓库的 一对多关系
				//matStoreType =   matStoreTypeMapper.queryMatStoreTypeByCode(entityMap);
				listMst=matStoreTypeMapper.queryMatStoreType(entityMap);
			}
			/*
			 * 物资材料是否审核管理 20172209
			 * */
			if("0".equals(MyConfig.getSysPara("04050"))||MyConfig.getSysPara("04050")== null||"".equals(MyConfig.getSysPara("04050"))){
				
				for (MatStoreType mst : listMst) {
					Map<String, Object> matStoreInvMap =new HashMap<String, Object>();
					
					matStoreInvMap.put("group_id",entityMap.get("group_id"));
					
					matStoreInvMap.put("hos_id",entityMap.get("hos_id"));
					
					matStoreInvMap.put("copy_code",entityMap.get("copy_code"));
					
					matStoreInvMap.put("store_id",mst.getStore_id());
					
					matStoreInvMap.put("inv_id",entityMap.get("inv_id"));
					//材料为新增材料 不需要判断是否重复
					matStoreInvMapper.addMatStoreInv(matStoreInvMap); 
	            }	
				
			}
			
			/*处理仓库材料对应关系信息-------------------end-------------*/
			
			//注：\"inv_id\"这个属性不能去掉
			return "{\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("inv_id").toString()+","
			+"\",\"inv_id\":\""+entityMap.get("inv_id").toString()+"\"}";
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatInv\"}";
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
					/*查询系统参数04002获得编码位数---begin----*/
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					map.put("para_code", "04002");
					String reg = "\\d+";
					String maxLength =matCommonMapper.getMatAccPara(map);
					/*查询系统参数04002获得编码位数---end------*/
					//判断参数值是否合法
					if(maxLength.matches(reg)){
						int max_length = Integer.valueOf(maxLength);
						//判断是否按类别编码为前缀并逐个递增自动生成
						if("1".equals(MyConfig.getSysPara("04014"))){
							String type_max_inv_code= matInvMapper.queryMatInvMaxByType(entityMap);
							//获取材料类别编码
							Map<String,Object> typeMap = new HashMap<String,Object>();
							typeMap.put("group_id", entityMap.get("group_id"));
							typeMap.put("hos_id", entityMap.get("hos_id"));
							typeMap.put("copy_code", entityMap.get("copy_code"));
							typeMap.put("mat_type_id", entityMap.get("mat_type_id"));
							if(entityMap.get("mat_type_no") != null && !"".equals(String.valueOf(entityMap.get("mat_type_no")))){
								typeMap.put("mat_type_no", entityMap.get("mat_type_no"));
							}else{
								typeMap.put("is_stop", "0");
							}
							if(type_max_inv_code.equals("2")){
								//该类别第一个编码从1开始
								inv_code = "1";
								//获取类别编码
								MatTypeDict mtd = matTypeDictMapper.queryMatTypeDictByUniqueness(typeMap);
								//补位
								for (int i = inv_code.length() ; i < max_length; i++) {
									inv_code = "0" + inv_code;
								}
								inv_code = mtd.getMat_type_code() + inv_code;
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
							String max_no = matNoOtherMapper.queryMatNoOther(noMap);//获取当前编码最大值
							//如果不存在则插入
							if(max_no == null || "".equals(max_no)){
								max_no = "1";
								noMap.put("max_no", Integer.valueOf(max_no));
								matNoOtherMapper.insertMatNoOther(noMap);
							}else{
								noMap.put("max_no", Integer.valueOf(max_no)+1);
								matNoOtherMapper.updateMatNoOther(noMap);
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
					map.put("field_table", "mat_inv");
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
			existsCodeMap.put("field_table", "mat_inv");
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
			existsCodeMap1.put("field_table", "mat_inv");
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
			entityMap.put("inv_id", matInvMapper.queryMatInvSeq());
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
			
			//新增物资
			matInvMapper.add(entityMap);
			
			//新增变更
			entityMap.put("change_user", SessionManager.getUserId());
			entityMap.put("change_date", new Date());
			entityMap.put("change_note", "新增");
			matInvDictMapper.add(entityMap);
			
			if(supList.size() > 0){
				matInvMapper.addMatInvSup(supList);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatInv\"}";
		}
	}
	 /*避免添加出问题 导入中使用*/
	@Override
    public String addImpBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			int is_default;
			int index = 1;
			for(Map<String, Object> entityMap : entityList){
				//判断编码是否自动生成
				if("自动生成".equals(entityMap.get("inv_code"))){
					boolean is_loop = true;
					String inv_code = "";
					while(is_loop){
						/*查询系统参数04002获得编码位数---begin----*/
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("group_id", entityMap.get("group_id"));
						map.put("hos_id", entityMap.get("hos_id"));
						map.put("copy_code", entityMap.get("copy_code"));
						map.put("para_code", "04002");
						String reg = "\\d+";
						String maxLength =matCommonMapper.getMatAccPara(map);
						/*查询系统参数04002获得编码位数---end------*/
						//判断参数值是否合法
						if(maxLength.matches(reg)){
							int max_length = Integer.valueOf(maxLength);
							//判断是否按类别编码为前缀并逐个递增自动生成
							if("1".equals(MyConfig.getSysPara("04014"))){
								entityMap.put("index", 1);
								String type_max_inv_code= matInvMapper.queryMatInvMaxByTypes(entityMap);
								//获取材料类别编码
								Map<String,Object> typeMap = new HashMap<String,Object>();
								typeMap.put("group_id", entityMap.get("group_id"));
								typeMap.put("hos_id", entityMap.get("hos_id"));
								typeMap.put("copy_code", entityMap.get("copy_code"));
								typeMap.put("mat_type_id", entityMap.get("mat_type_id"));
								if(entityMap.get("mat_type_no") != null && !"".equals(String.valueOf(entityMap.get("mat_type_no")))){
									typeMap.put("mat_type_no", entityMap.get("mat_type_no"));
								}else{
									typeMap.put("is_stop", "0");
								}
								if(type_max_inv_code.equals("2")){
									//该类别第一个编码从1开始
									inv_code = "1";
									//获取类别编码
									MatTypeDict mtd = matTypeDictMapper.queryMatTypeDictByUniqueness(typeMap);
									//补位
									for (int i = inv_code.length() ; i < max_length; i++) {
										inv_code = "0" + inv_code;
									}
									inv_code = mtd.getMat_type_code() + inv_code;
								}else{
									inv_code = type_max_inv_code.substring(1, type_max_inv_code.length());
								}
								index++;
							}else{
								//按流水码补位自动生成材料编码
								/*用于查询并更新最大流水码----begin-----*/
								Map<String,Object> noMap = new HashMap<String,Object>();
								noMap.put("group_id", entityMap.get("group_id"));
								noMap.put("hos_id", entityMap.get("hos_id"));
								noMap.put("type_code", 2);
								String max_no = matNoOtherMapper.queryMatNoOther(noMap);//获取当前编码最大值
								//如果不存在则插入
								if(max_no == null || "".equals(max_no)){
									max_no = "1";
									noMap.put("max_no", Integer.valueOf(max_no));
									matNoOtherMapper.insertMatNoOther(noMap);
								}else{
									noMap.put("max_no", Integer.valueOf(max_no)+1);
									matNoOtherMapper.updateMatNoOther(noMap);
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
						map.put("field_table", "mat_inv");
						map.put("field_key1", "inv_code");
						map.put("field_value1", inv_code);
						map.put("field_key2", "use_state");
						map.put("field_value2", 1);
						int flag = sysFunUtilMapper.existsSysCodeNameByAdd(map);
						if (flag == 0) {
							is_loop = false;
						}
						else if (flag > 0) {
							throw new SysException("{\"error\":\"自动生成材料字典编码重复\"}");
							
						}
					}
					entityMap.put("inv_code", inv_code);
				}
				
				/*判断编码是否重复----------------begin------------*/
				Map<String, Object> existsCodeMap = new HashMap<String, Object>();
				existsCodeMap.put("group_id", entityMap.get("group_id"));
				existsCodeMap.put("hos_id", entityMap.get("hos_id"));
				existsCodeMap.put("copy_code", entityMap.get("copy_code"));
				existsCodeMap.put("field_table", "mat_inv");
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
				existsCodeMap1.put("field_table", "mat_inv");
				existsCodeMap1.put("field_key1", "inv_name");
				existsCodeMap1.put("field_key2", "inv_model");
				existsCodeMap1.put("field_value1", entityMap.get("inv_name"));
				existsCodeMap1.put("field_value2", entityMap.get("inv_model"));
				if(entityMap.get("fac_id")!=null 
						&& !"".equals(entityMap.get("fac_id").toString()) 
						&& !"null".equals(entityMap.get("fac_id").toString())){
				  existsCodeMap1.put("field_key3", "fac_id");
				  existsCodeMap1.put("field_value3", entityMap.get("fac_id"));
				}
				existsCodeMap1.put("field_key4", "use_state");
				existsCodeMap1.put("field_value4", 1);
				
				int flag1 = sysFunUtilMapper.existsSysCodeNameByAdd(existsCodeMap1);
				if (flag1 == 0) {
					
					/*判断名称规格型号生产厂商----------------end--------------*/
					//先取出材料ID
					entityMap.put("inv_id", matInvMapper.queryMatInvSeq());
		
					//处理默认计划来源
					if(entityMap.get("source_plan") == null || "".equals(entityMap.get("source_plan"))){
						entityMap.put("source_plan", 1);
					}
					//处理品种码字段
					if(entityMap.get("is_inv_bar") == null || "".equals(entityMap.get("is_inv_bar"))){
						entityMap.put("is_inv_bar", 0);
					}
					
					//新增物资
					matInvMapper.add(entityMap);
					
					//新增变更
					entityMap.put("change_user", SessionManager.getUserId());
					entityMap.put("change_date", new Date());
					entityMap.put("change_note", "新增");
					matInvDictMapper.add(entityMap);
					is_default = 1;
				}else{
					/*根据材料名称规格型号与生产厂商获取已插入的材料ID*/
					Map<String, Object> seachMap = new HashMap<String, Object>();
					seachMap.put("group_id", entityMap.get("group_id"));
					seachMap.put("hos_id", entityMap.get("hos_id"));
					seachMap.put("copy_code", entityMap.get("copy_code"));
					seachMap.put("inv_name", entityMap.get("inv_name"));
					seachMap.put("inv_model", entityMap.get("inv_model"));
					if(entityMap.get("fac_id")!=null 
							&& !"".equals(entityMap.get("fac_id").toString()) 
							&& !"null".equals(entityMap.get("fac_id").toString())){
						seachMap.put("fac_id", entityMap.get("fac_id"));
					}
					entityMap.put("inv_id", matInvMapper.getInvIdByImp(seachMap));
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
					Map<String, Object> MatInvSupbyCode = matInvMapper.queryMatInvSupbyCode(supMap);
					if(MatInvSupbyCode == null){
						List<Map<String, Object>> supList = new ArrayList<Map<String,Object>>();
						supList.add(supMap);
						matInvMapper.addMatInvSup(supList);
					}
				}
				/*处理供应商信息-------------------end-------------*/
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatInv\"}";
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
	public String addMatInvSup(Map<String,Object> entityMap)throws DataAccessException{
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
				
				matInvMapper.deleteMatInvSup(entityMap);
				matInvMapper.addMatInvSup(supList);
			}else{
				return "{\"error\":\"请录入供应商！\",\"state\":\"false\"}";
			}
			/*处理供应商信息-------------------end-------------*/
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");

			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatInv\"}";
		}
	}
	
	/**
	 * @Description 
	 * 批量添加04105 物资材料表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			matInvMapper.addBatch(entityList);
			matInvDictMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatInv\"}";
		}
	}
	
	/**
	 * @Description 
	 * 批量添加04105 物资材料表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatInvSupBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			matInvMapper.deleteMatInvSupBatch(entityList);
			matInvMapper.addMatInvSup(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatInv\"}";
		}
	}
	
	/**
	 * @Description 
	 * 更新04105 物资材料表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			/*判断编码是否重复----------------begin------------
			Map<String, Object> existsCodeMap = new HashMap<String, Object>();
			existsCodeMap.put("group_id", entityMap.get("group_id"));
			existsCodeMap.put("hos_id", entityMap.get("hos_id"));
			existsCodeMap.put("copy_code", entityMap.get("copy_code"));
			existsCodeMap.put("field_table", "mat_inv");
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
				int num = matCommonMapper.getHoldBalanceByInv(map);
				if(num > 0){
					return "{\"error\":\"该材料库存不为0，不能更改是否条码管理！\"}";
				}
			}
			if(!entityMap.get("old_is_com").equals(entityMap.get("is_com"))){
				//有库存不能更改是否代销
				int num = matCommonMapper.getHoldBalanceByInv(map);
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
			
			/*
			 *材料增加触发器 
			 * */
			
			if(("1").equals(MyConfig.getSysPara("04074"))){
				
				logger.debug("------------材料字典修改数据传入HIS存储过程开始-------------");
				
				String message = triggerMatInv(entityMap,2);
				
				logger.debug(message);
				
				logger.debug("------------材料字典修改数据传入HIS存储过程结束-------------");
			}
			
			
			matInvMapper.update(entityMap);
			
			//变更信息
			entityMap.put("change_user", SessionManager.getUserId());
			entityMap.put("change_date", new Date());
			if(entityMap.get("change_note") == null || "".equals(entityMap.get("change_note"))){
				entityMap.put("change_note", "变更");
			}
				
			if("1".equals(entityMap.get("is_change"))){
				//变更表所有记录is_stop = 1
				map.put("is_stop", "1");
				matInvDictMapper.updateIsStop(map);
				
				//新增变更表变更记录
				matInvDictMapper.add(entityMap);
			}else{
				//修改变更记录
				matInvDictMapper.update(entityMap);
			}

			//对应关系先删除再插入
			matInvMapper.deleteMatInvSup(entityMap);
			if(supList.size() > 0){
				matInvMapper.addMatInvSup(supList);
			}
			
			if(certList.size() > 0){
				//对应关系先删除再插入
				matInvCertRelaMapper.deleteMatInvCertRela(entityMap);
				matInvCertRelaMapper.addBatchMatInvCertRela(certList);
			}
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (DataAccessException e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");

			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatInv\"}";
		}		
	}
	/**
	 * @Description 
	 * 批量更新04105 物资材料表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
		  matInvMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatInv\"}";
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
	public String updateMatInvBatch(Map<String,Object> entityMap)throws DataAccessException{
		
		try {

			if(entityMap.get("inv_ids") != null && !"".equals(entityMap.get("inv_ids").toString())){
				List<Map<String, Object>> invList = matInvDictMapper.queryByCodes(entityMap);
		
				if(invList.size() == 0){
					return "{\"error\":\"请选择材料！\"}";
				}
				String user_id = SessionManager.getUserId();
				for(Map<String, Object> invMap : invList){
					
					if(entityMap.get("instru_type_id") != null && !"".equals(entityMap.get("instru_type_id").toString())){
						invMap.put("instru_type_id", entityMap.get("instru_type_id").toString());
					}
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
				
					if(entityMap.get("bar_code_new") != null && !"".equals(entityMap.get("bar_code_new").toString())){
						invMap.put("bar_code_new", entityMap.get("bar_code_new").toString());
					}else{
						
						invMap.put("bar_code_new", invMap.get("bar_code_new"));
					}
				
					if(entityMap.get("alias") != null && !"".equals(entityMap.get("alias").toString())){
						invMap.put("alias", entityMap.get("alias").toString());
					}else{
						invMap.put("alias", invMap.get("alias"));
					}
					
					if(entityMap.get("inv_name") != null && !"".equals(entityMap.get("inv_name").toString())){
						invMap.put("inv_name", entityMap.get("inv_name").toString());
					}else{
						invMap.put("inv_name", invMap.get("inv_name"));
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
					int num = matCommonMapper.getHoldBalanceByInv(map);
					if(num > 0){
						return "{\"error\":\"含库存不为0的材料，不能更改是否条码管理！\"}";
					}
				}
				if(entityMap.get("is_com") != null && !"".equals(entityMap.get("is_com").toString())){
					//有库存不能更改是否代销管理
					int num = matCommonMapper.getHoldBalanceByInv(map);
					if(num > 0){
						return "{\"error\":\"含库存不为0的材料，不能更改是否代销管理！\"}";
					}
				}
				//System.out.println(invList);
				matInvMapper.updateBatch(invList);
					//System.out.println("============="+entityMap.get("is_change"));
				if("1".equals(entityMap.get("is_change"))){
					//变更表所有记录is_stop = 1
					map.put("is_stop", "1");
					matInvDictMapper.updateIsStop(map);
					
					//新增变更表变更记录
					matInvDictMapper.addBatch(invList);
				}else{
					//修改变更记录
					matInvDictMapper.updateBatch(invList);
				}
				
				if(entityMap.get("sup_id") != null && !"".equals(entityMap.get("sup_id").toString())){
					//首先查看返回哪些材料inv_id中有sup_id,有  看is_default=0,则先update,然后插入
					//sup_id=0   则直接update,然后插入
					//entityMap.put("isDefault", "0");
					List<Map<String,Object>> inList = JsonListMapUtil.toListMapLower(matInvMapper.queryMatInvInSup(invList));
					if(inList.size()>0){
						matInvMapper.updateMatInvSupIsNotDefault(inList);
						matInvMapper.updateMatInvSupIsDefault(inList);
					}
					
					
					List<Map<String,Object>> notInList = JsonListMapUtil.toListMapLower(matInvMapper.queryMatInvNotInSup(invList));
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
						matInvMapper.updateMatInvSupIsNotDefault(newList);
						matInvMapper.addMatInvSup(newList);
					}
					
					
					
					/*int sup=matInvMapper.queryMatInvSupCode(entityMap);
					System.out.println("sup=============="+sup);
					if(sup>0){
						return "{\"error\":\"信息已存在,请重新添加.\",\"state\":\"true\"}";
					}else{
						//修改供应商为非默认（由于批量修改的材料有可能含该供应商会造成主键冲突所以改为删除）
						//matInvMapper.updateMatInvSupIsNotDefault(invList);
						//删除原默认供应商
						matInvMapper.deleteMatInvSupByDefault(invList);
						//添加新默认供应商
						matInvMapper.addMatInvSup(invList);
					}*/
					
				}

				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}else{
				return "{\"error\":\"请选择材料！\"}";
			}
		}catch (DataAccessException e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");

			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatInv\"}";
		}		
	}
	/**
	 * @Description 
	 * 删除04105 物资材料表<BR> 
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
			int num = matCommonMapper.getHoldBalanceByInv(map);
			if(num > 0){
				return "{\"error\":\"该材料库存不为0，不能删除！\"}";
			}
    		
			//删除物资
			matInvMapper.delete(entityMap);
			
			//新增物资变更变表删除记录
			entityMap.put("change_user", SessionManager.getUserId());
			entityMap.put("change_date", new Date());
			entityMap.put("change_note", "删除");
			if(entityMap.get("source_plan") == null || "".equals(entityMap.get("source_plan"))){
				entityMap.put("source_plan", 1);
			}
			matInvDictMapper.addForDelete(entityMap);
			//更新变更表中inv_id为当前值的所有记录is_stop = 1
			map.put("is_stop", "1");
			matInvDictMapper.updateIsStop(map);
			
			//删除材料供应商对应关系
			matInvMapper.deleteMatInvSup(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
    	catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatInv\"}";
		}	
  }
    
	/**
	 * @Description 
	 * 批量删除04105 物资材料表<BR> 
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
			StringBuffer dict_id_str = new StringBuffer();
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
				dict_id_str.append(map.get("inv_id").toString()).append(",");
			}
			//System.out.println("group_id : "+group_id+", hos_id : "+hos_id+", copy_code : "+copy_code+", inv_ids : "+inv_ids);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", group_id);
			map.put("hos_id", hos_id);
			map.put("copy_code", copy_code);
			map.put("inv_ids", inv_ids.substring(0, inv_ids.length()-1));

			int num = matCommonMapper.getHoldBalanceByInv(map);
			if(num > 0){
				return "{\"error\":\"存在库存不为0的材料，不能删除！\"}";
			}
			
			//2017-03-24 判断材料是否被引用
			String reStr="";
			map.put("dict_code", "mat_inv".toUpperCase());
			map.put("dict_id_str",dict_id_str.substring(0, dict_id_str.length()-1));
			map.put("acc_year", "");
			map.put("p_flag", "1");
			sysFunUtilMapper.querySysDictDelCheck(map);
			
			if(map.get("reNote")!=null) {
				reStr+=map.get("reNote");
			}
			
			
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败,选择的物资材料被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			
			matInvMapper.deleteBatch(entityList);
			
			//新增物资变更变表删除记录
			map.put("change_user", SessionManager.getUserId());
			map.put("change_date", new Date());
			map.put("change_note", "删除");
			if(map.get("source_plan") == null || "".equals(map.get("source_plan"))){
				map.put("source_plan", 1);
			}
			matInvDictMapper.addForDelete(map);
			//更新变更表中inv_id为当前值的所有记录is_stop = 1
			map.put("is_stop", "1");
			matInvDictMapper.updateIsStop(map);
			
			//删除材料供应商对应关系
			matInvMapper.deleteMatInvSupBatch(entityList);
			
			//2017-03-24 删除对应关系
			//删除科室配套表明细
			matDeptMatchMapper.deleteBatchMdmDetail(entityList);
			//删除仓库配套表明细
			matStoreMatchMapper.deleteBatchMsmDetail(entityList);
			//删除仓库材料信息
			matStoreInvMapper.deleteBatchMatStoreInvRela(entityList);
			//删除材料证件对应关系
			matInvCertRelaMapper.deleteBatchMatInvCertRelaByInv(entityList);
			
			
			
			
			//台州需求:HRP_MAT_INV表用于台州接口,HRP给HIS提供的材料中间表,更新材料时更改HRP_MAT_INV表材料的现行状态
			//根据判断表是否存在,台州单独作删除流程处理
			Map<String,Object> queryMap = new HashMap<String,Object>();
			queryMap.put("table_name","HRP_MAT_INV");
			int count = matInvMapper.queryTableIsExit(queryMap);
			
			if(count > 0){//如果HRP_MAT_INV表存在
				matInvMapper.updateBatchHrpMatInvState(entityList);
			}
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");

			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatInv\"}";
		}	
	}
	
	
	
	
	/**
	 * @Description 
	 * 批量审核04105 物资材料表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String auditMatInv(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			int state = 0; //定义审核状态
			
			JSONArray dataJson = JSONArray.parseArray(String.valueOf(entityMap.get("data")));
			
			Iterator iterator = dataJson.iterator();
			
			List<Map<String,Object>> entityList = new ArrayList<Map<String,Object>>();
			
			List<Map<String,Object>> entitynInvList = new ArrayList<Map<String,Object>>();
			
			while(iterator.hasNext()){
				
				JSONObject obj = JSONObject.parseObject(iterator.next().toString());
				Map<String,Object> mapVo = new HashMap<String,Object>();
				
				mapVo.put("group_id", obj.get("group_id"));
				mapVo.put("hos_id", obj.get("hos_id"));
				mapVo.put("copy_code", obj.get("copy_code"));
				mapVo.put("inv_id", obj.get("inv_id"));
				
				if("1".equals(entityMap.get("is_audit"))){//审核
					
					state = 1;
					
					mapVo.put("checker", SessionManager.getUserId());
					mapVo.put("check_date", new Date());
					mapVo.put("state", "1");
					mapVo.put("use_state", "1");
					entitynInvList.add(MatStore(mapVo,obj.get("mat_type_id").toString()));
					
					
				}else if("0".equals(entityMap.get("is_audit"))){//消审
					
					state = 0;
					
					mapVo.put("checker","");
					mapVo.put("check_date", "");
					mapVo.put("state", "0");
					mapVo.put("use_state", "0");
					entitynInvList.add(MatStore(mapVo,obj.get("mat_type_id").toString()));
				}
				
				//台州中心医院:HIS材料中间表(HRP_MAT_INV)更新字段参数
				mapVo.put("inv_name", obj.get("inv_name"));
				mapVo.put("inv_model", obj.get("inv_model"));
				mapVo.put("unit_name", obj.get("unit_name"));
				mapVo.put("inv_model", obj.get("inv_model"));
				mapVo.put("plan_price", obj.get("plan_price"));
				mapVo.put("sell_price",obj.get("sell_price"));
				mapVo.put("fac_name", obj.get("fac_name"));
				mapVo.put("cert_code",obj.get("cert_code"));
				
				mapVo.put("mat_type_code", obj.get("mat_type_code"));
				mapVo.put("mat_type_name", obj.get("mat_type_name"));
				mapVo.put("unit_code", obj.get("unit_code"));
				mapVo.put("fac_code", obj.get("fac_code"));
				
				mapVo.put("is_charge", obj.get("is_charge"));
				mapVo.put("is_highvalue",obj.get("is_highvalue"));
				
				entityList.add(mapVo);
			}
			
			matInvMapper.auditMatInv(entityList);
			matInvDictMapper.auditMatInvDict(entityList);
			
			if("1".equals(MyConfig.getSysPara("04050"))){
				if(state == 1){
					matStoreInvMapper.addBatchMatStoreInv(entitynInvList);
				}else if(state == 0){
					matStoreInvMapper.deleteBatchMatStoreInv(entitynInvList);
				}
			}
			
			
			//台州需求:HRP_MAT_INV表用于台州接口,HRP给HIS提供的材料中间表,更新材料时更改HRP_MAT_INV表材料的现行状态
			//根据判断表是否存在,台州单独作审核流程处理
			Map<String,Object> queryMap = new HashMap<String,Object>();
			queryMap.put("table_name","HRP_MAT_INV");
			int count = matInvMapper.queryTableIsExit(queryMap);
			
			if(count == 0){
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			}
				
			//如果销审,不处理
			if("0".equals(entityMap.get("is_audit"))){
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			}
			
				
			for(Map<String,Object> matInvMap : entityList){
				
				//存储材料信息map
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("inv_id", matInvMap.get("inv_id"));
				map.put("inv_name",matInvMap.get("inv_name"));
				map.put("inv_model", matInvMap.get("inv_model") == null ? "" : matInvMap.get("inv_model"));
				map.put("unit_name",matInvMap.get("unit_name") == null ? "" : matInvMap.get("unit_name"));
				map.put("plan_price",matInvMap.get("plan_price"));
				map.put("sale_price", matInvMap.get("sell_price"));
				map.put("factory_name",matInvMap.get("fac_name") == null ? "" : matInvMap.get("fac_name"));
				map.put("cert_code", matInvMap.get("cert_code") == null ? "" : matInvMap.get("cert_code"));
				
				
				//判断材料是否在HRP_MAT_INV表中存在
				Map<String,Object> hrpMatInvMap = matInvMapper.queryHrpMatInvByCode(matInvMap);
				if(hrpMatInvMap != null){//存在
							
					String sell_price = String.valueOf(matInvMap.get("sell_price"));//mat_inv材料零售价
					String sale_price = String.valueOf(hrpMatInvMap.get("sale_price"));//hrp_mat_inv零售价
							
					String plan_price = String.valueOf(matInvMap.get("plan_price"));//mat_inv材料计划价
					String hrp_plan_price = String.valueOf(hrpMatInvMap.get("plan_price"));//hrp_mat_inv计划价
							
					//改变零售价或者计划价,就改变HRP_MAT_INV表现行状态
					if(!sale_price.equals(sell_price) || !plan_price.equals(hrp_plan_price)){
						map.put("inv_state", 2);
					}else{
						map.put("inv_state",hrpMatInvMap.get("inv_state"));
					}
							
					matInvMapper.updateHrpMatInv(map);
				}else{//为空
					
					String is_charge = String.valueOf(matInvMap.get("is_charge"));//是否收费
					String is_highvalue = String.valueOf(matInvMap.get("is_highvalue"));//是否高值
					String use_state = String.valueOf(matInvMap.get("use_state"));//是否在用
					
					//如果是收费、高值、在用的状态,就新增
					if("1".equals(is_charge) && "1".equals(is_highvalue) && "1".equals(String.valueOf(use_state))){
						map.put("inv_state",1);
						matInvMapper.addHrpMatInv(map);
					}
				}
			}
			
			Map<String,Object> queryDictMatInvTabMap = new HashMap<String,Object>();
			queryDictMatInvTabMap.put("table_name","DICT_MAT_INV");
			int isExit = matInvMapper.queryTableIsExit(queryDictMatInvTabMap);
			
			if(isExit == 0){
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			}
				
			//如果销审,不处理
			if("0".equals(entityMap.get("is_audit"))){
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			}
			
			for(Map<String,Object> matInvMap : entityList){
				
				//存储材料信息map
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("inv_id", matInvMap.get("inv_id"));
				map.put("inv_name",matInvMap.get("inv_name"));
				map.put("mat_type_code",matInvMap.get("mat_type_code") == null ? "" : matInvMap.get("mat_type_code"));
				map.put("mat_type_name",matInvMap.get("mat_type_name") == null ? "" : matInvMap.get("mat_type_name"));
				map.put("inv_model", matInvMap.get("inv_model") == null ? "" : matInvMap.get("inv_model"));
				map.put("unit_code",matInvMap.get("unit_code") == null ? "" : matInvMap.get("unit_code"));
				map.put("unit_name",matInvMap.get("unit_name") == null ? "" : matInvMap.get("unit_name"));
				map.put("fac_code",matInvMap.get("fac_code") == null ? "" : matInvMap.get("fac_code"));
				map.put("fac_name",matInvMap.get("fac_name") == null ? "" : matInvMap.get("fac_name"));
				map.put("plan_price",matInvMap.get("plan_price"));
				map.put("sell_price", matInvMap.get("sell_price"));
				map.put("is_charge", matInvMap.get("is_charge"));
				map.put("is_highvalue", matInvMap.get("is_highvalue"));
				map.put("spell_code",
						StringTool.toPinyinShouZiMu(matInvMap.get("inv_name").toString()));
				
				
				//判断材料是否在DICT_MAT_INV表中存在
				Map<String,Object> hrpMatInvMap = matInvMapper.queryDictMatInvByCode(matInvMap);
				if(hrpMatInvMap != null){//存在
							
					String sell_price = String.valueOf(matInvMap.get("sell_price"));//mat_inv材料零售价
					String sale_price = String.valueOf(hrpMatInvMap.get("sell_price"));//hrp_mat_inv零售价
							
					String plan_price = String.valueOf(matInvMap.get("plan_price"));//mat_inv材料计划价
					String hrp_plan_price = String.valueOf(hrpMatInvMap.get("plan_price"));//hrp_mat_inv计划价
							
					//改变零售价或者计划价,就改变HRP_MAT_INV表现行状态
					if(!sale_price.equals(sell_price) || !plan_price.equals(hrp_plan_price)){
						map.put("change_date",new Date());
						map.put("change_state", 2);
					}else{
						map.put("change_date",new Date());
						map.put("change_state",hrpMatInvMap.get("inv_state"));
					}
							
					matInvMapper.updateDictMatInv(map);
				}else{//为空
					
					String is_charge = String.valueOf(matInvMap.get("is_charge"));//是否收费
					//String is_highvalue = String.valueOf(matInvMap.get("is_highvalue"));//是否高值
					String use_state = String.valueOf(matInvMap.get("use_state"));//是否在用
					
					//如果是收费、高值、在用的状态,就新增
					if("1".equals(is_charge) && "1".equals(String.valueOf(use_state))){
						map.put("change_date",new Date());
						map.put("change_state",1);
						matInvMapper.addDictMatInv(map);
					}
				}
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"审核失败 数据库异常 请联系管理员! 方法 auditMatInv\"}";
		}	
	}
	
	
	/**
	 * @Description 
	 * //即墨 如果按照审核管理的材料，审核后自动添加仓库与材料对应关系<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Map<String, Object>  MatStore(Map<String,Object> entityMap,String mat_type_id)throws DataAccessException{
	
	
		List<MatStoreType> listMst=new ArrayList<MatStoreType>();
		
		if(null != mat_type_id && ! "".equals(mat_type_id)){
			//存在同一分类对应多个仓库的 一对多关系

			entityMap.put("mat_type_id", mat_type_id);
			
			listMst=matStoreTypeMapper.queryMatStoreType(entityMap);
		}
		
		Map<String, Object> matStoreInvMap =new HashMap<String, Object>();
		
		for (MatStoreType mst : listMst) {
			
			matStoreInvMap.put("group_id",entityMap.get("group_id"));
			
			matStoreInvMap.put("hos_id",entityMap.get("hos_id"));
			
			matStoreInvMap.put("copy_code",entityMap.get("copy_code"));
			
			matStoreInvMap.put("store_id",mst.getStore_id());
			
			matStoreInvMap.put("inv_id",entityMap.get("inv_id"));
			
			matStoreInvMap.put("is_apply","0");
			
			matStoreInvMap.put("oper",SessionManager.getUserId());
			
			matStoreInvMap.put("oper_date",new Date());

        }
		
		return matStoreInvMap;
	}
	
	
	
	/**
	 * @Description 
	 * 添加04105 物资材料表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04105 物资材料表
		MatInv matInv = queryByCode(entityMap);

		if (matInv != null) {

			matInvMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			matInvMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (DataAccessException e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatInv\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集04105 物资材料表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage == null || sysPage.getTotal()==-1){
			List<?> list = matInvMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matInvMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * @Description 
	 * 查询结果集04105 物资材料表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public List<Map<String, Object>> printQuery(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) matInvMapper.query(entityMap);
			
		return list;
	}
	
	/**
	 * 材料改变物资类别查询改类别材料数
	 */
	@Override
	public String queryChangeMatTypeCode(Map<String,Object> entityMap) throws DataAccessException{
			List<Map<String,Object>> list = matInvMapper.queryChangeMatTypeCode(entityMap);
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
			map.put("table","MAT_INV_HOLD");
		}else if(entityMap.get("is_com").equals("1")){
			map.put("table","MAT_AFFI_INV_HOLD");
		}
		List<Map<String,Object>> list = matInvMapper.queryStopTimeByCode(map);
		return ChdJson.toJson(list);
	}
	
	/**
	 * @Description 
	 * 获取对象04105 物资材料表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException 
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matInvMapper.queryByCode(entityMap);
	}
	
	
	/**
	 * @Description 
	 * 获取04105 物资材料表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInv
	 * @throws DataAccessException
	*/
	@Override
	public  <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matInvMapper.queryByUniqueness(entityMap);
	}
	
	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return matInvMapper.queryExists(entityMap);
	}
	@Override
	public String queryDict(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = matInvDictMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matInvDictMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 供应商列表
	 */
	@Override
	public String queryMatInvSup(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = matInvMapper.queryMatInvSup(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
	/**
	 * 供应商列表
	 */
	@Override
	public String queryMatInvSupList(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matInvMapper.queryMatInvSupList(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matInvMapper.queryMatInvSupList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	
	/**
	 * 不查停用材料
	 */
	@Override
	public String queryMatInvSupListDisable(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matInvMapper.queryMatInvSupListDisable(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matInvMapper.queryMatInvSupListDisable(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	@Override
	public <T> T queryDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return matInvDictMapper.queryByCode(entityMap);
	}
	/**
	 * 复制 时 根据材料名称、规格 查询 材料是否已存在
	 */
	@Override
	public String queryMatInvExist(Map<String, Object> mapVo) throws DataAccessException {
		int count = matInvMapper.queryMatInvExist(mapVo);
		if(count > 0){
			return "{\"info\":\"材料:"+mapVo.get("inv_name")+"规格为:"+mapVo.get("inv_model")+"的材料已存在.\",\"state\":\"false\"}";
		}else{
			return "{\"info\":\"校验通过.\",\"state\":\"true\"}";
		}
	}
	
	@Override
	public String queryMatInvCertInfo(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list= matInvMapper.queryMatInvCertInfo(entityMap);
		/*
		for(Map<String,Object> item :list){
			
			List<Map<String,Object>> supList = matInvMapper.queryCertSup(item);
			
			item.put("supData", JSONObject.parseObject(ChdJson.toJson(supList)));
		}*/
		
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
		return JSON.toJSONString(matInvMapper.queryHosFacDict(entityMap, rowBounds));
	}
	
	//入库模板打印（包含主从表）
		@Resource(name = "superPrintService")
		private final SuperPrintService superPrintService = null;
		@Override
		public String queryMatInvByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException {
			
				List<Map<String,Object>> map=matInvMapper.queryMatInvPrintTemlateByMainBatch(entityMap);
				//查询入库明细表
				//List<Map<String,Object>> list=matInvMapper.queryMatInvPrintTemlateByDetail(entityMap);
				
				return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,null);
				
			
		}
		
		/**
		 * 材料默认供应商
		 */
		@Override
		public Map<String, Object> queryMatInvSupDefault(Map<String, Object> entityMap) throws DataAccessException {
			return JsonListMapUtil.toMapLower((Map<String, Object>)matInvMapper.queryMatInvSupDefault(entityMap));
		}
		
		/**
		 * 审核
		 */
		@Override
		public String auditMatInvById(Map<String, Object> entityMap) throws DataAccessException {
			try {
				
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				if(entityMap.get("flag").equals("1")){
					entityMap.put("checker", SessionManager.getUserId());
					entityMap.put("check_date", new Date());
					entityMap.put("state", "1");
					entityMap.put("use_state", "1");
				}else{
					entityMap.put("checker", "");
					entityMap.put("check_date", "");
					entityMap.put("state", "0");
					entityMap.put("use_state", "0");
				}
				
				list.add(entityMap);
				
				matInvMapper.auditMatInv(list);
				matInvDictMapper.auditMatInvDict(list);
				
				Map<String,Object> matInvMap = matInvMapper.queryByCode(entityMap);
				
				Map<String,Object> queryDictMatInvTabMap = new HashMap<String,Object>();
				queryDictMatInvTabMap.put("table_name","DICT_MAT_INV");
				int isExit = matInvMapper.queryTableIsExit(queryDictMatInvTabMap);
				
				if(isExit == 0){
					return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
				}
					
				//如果销审,不处理
				if("0".equals(entityMap.get("is_audit"))){
					return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
				}
				
					
				//存储材料信息map
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("inv_id", matInvMap.get("inv_id"));
				map.put("inv_name",matInvMap.get("inv_name"));
				map.put("mat_type_code",matInvMap.get("mat_type_code") == null ? "" : matInvMap.get("mat_type_code"));
				map.put("mat_type_name",matInvMap.get("mat_type_name") == null ? "" : matInvMap.get("mat_type_name"));
				map.put("inv_model", matInvMap.get("inv_model") == null ? "" : matInvMap.get("inv_model"));
				map.put("unit_code",matInvMap.get("unit_code") == null ? "" : matInvMap.get("unit_code"));
				map.put("unit_name",matInvMap.get("unit_name") == null ? "" : matInvMap.get("unit_name"));
				map.put("fac_code",matInvMap.get("fac_code") == null ? "" : matInvMap.get("fac_code"));
				map.put("fac_name",matInvMap.get("fac_name") == null ? "" : matInvMap.get("fac_name"));
				map.put("plan_price",matInvMap.get("plan_price"));
				map.put("sell_price", matInvMap.get("sell_price"));
				map.put("is_charge", matInvMap.get("is_charge"));
				map.put("is_highvalue", matInvMap.get("is_highvalue"));
				map.put("spell_code",
						StringTool.toPinyinShouZiMu(matInvMap.get("inv_name").toString()));
				
				
				//判断材料是否在DICT_MAT_INV表中存在
				Map<String,Object> hrpMatInvMap = matInvMapper.queryDictMatInvByCode(matInvMap);
				if(hrpMatInvMap != null){//存在
							
					String sell_price = String.valueOf(matInvMap.get("sell_price"));//mat_inv材料零售价
					String sale_price = String.valueOf(hrpMatInvMap.get("sell_price"));//hrp_mat_inv零售价
							
					String plan_price = String.valueOf(matInvMap.get("plan_price"));//mat_inv材料计划价
					String hrp_plan_price = String.valueOf(hrpMatInvMap.get("plan_price"));//hrp_mat_inv计划价
							
					//改变零售价或者计划价,就改变HRP_MAT_INV表现行状态
					if(!sale_price.equals(sell_price) || !plan_price.equals(hrp_plan_price)){
						map.put("change_date",new Date());
						map.put("change_state", 2);
					}else{
						map.put("change_date",new Date());
						map.put("change_state",hrpMatInvMap.get("inv_state"));
					}
							
					matInvMapper.updateDictMatInv(map);
				}else{//为空
					
					String is_charge = String.valueOf(matInvMap.get("is_charge"));//是否收费
					//String is_highvalue = String.valueOf(matInvMap.get("is_highvalue"));//是否高值
					String use_state = String.valueOf(matInvMap.get("use_state"));//是否在用
					
					//如果是收费、高值、在用的状态,就新增
					if("1".equals(is_charge) && "1".equals(String.valueOf(use_state))){
						map.put("change_date",new Date());
						map.put("change_state",1);
						matInvMapper.addDictMatInv(map);
					}
				}
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			}catch (DataAccessException e) {
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"操作失败\"}");
				//return "{\"error\":\"审核失败 数据库异常 请联系管理员! 方法 auditMatInv\"}";
			}	
		}
		@Override
		public String triggerMatInv(Map<String, Object> entityMap,int i)
				throws DataAccessException {
			/*
			 * 0 添加材料
			 * 1 修改材料单价
			 * 2 修改材料使用状态
			 * 
			 *  HIS存储过程结构
			 *  create or replace procedure pro_exp_pricelist （
			 *	c_exp_code           in varchar2, --编码
			 *	c_exp_name           in varchar2, --名称
			 *	c_exp_spec           in varchar2, --规格
			 *	c_firm_id            in varchar2, --厂家
			 *	c_units              in varchar2, --单位
			 *	d_retail_price       in number,   --零售价
			 *	c_class_on_inp_rcpt  in varchar2, --U（固定值）
			 *	c_class_on_outp_rcpt in varchar2, --O（固定值）
			 *	c_class_on_reckoning in varchar2, --Z05（固定值）
			 *	c_subj_code          in varchar2, --J02（固定值）
			 *	c_class_on_mr        in varchar2, --其它（固定值）
			 *	c_memos              in varchar2, --备注
			 *	t_start_date         in date,     --启用日期
			 *	t_stop_date          in date,     --停用日期（无停用传空）
			 *	c_material_code      in varchar2  --物价编码
			 *  c_input_code         in varchar2  --拼音码
			 *	)
			 * */
			List<Map<String, Object>> resultSetlist = new ArrayList<Map<String,Object>>();
			String message = "";
			try {
				switch (i) {
				case 1:
					Map<String, Object> resultSetAddMap = new HashMap<String, Object>();
					resultSetAddMap.put("c_exp_code", entityMap.get("inv_code"));
					resultSetAddMap.put("c_exp_name", entityMap.get("inv_name"));
					resultSetAddMap.put("c_spell_code", entityMap.get("spell_code"));
					resultSetAddMap.put("c_exp_spec", entityMap.get("inv_model"));   
					resultSetAddMap.put("c_firm_id", entityMap.get("fac_id"));
					resultSetAddMap.put("c_units", entityMap.get("unit_code"));
					resultSetAddMap.put("d_retail_price", entityMap.get("sell_price"));
					resultSetAddMap.put("c_class_on_inp_rcpt", "U");
					resultSetAddMap.put("c_class_on_outp_rcpt", "O");
					resultSetAddMap.put("c_class_on_reckoning", "Z05");
					resultSetAddMap.put("c_subj_code", "J02");
					resultSetAddMap.put("c_class_on_mr", "其它");
					resultSetAddMap.put("c_memos", "添加材料");
					resultSetAddMap.put("t_start_date", entityMap.get("sdate"));
					resultSetAddMap.put("t_stop_date", entityMap.get("edate"));
					resultSetAddMap.put("c_material_code", entityMap.get("memory_encoding"));
					resultSetlist.add(resultSetAddMap);
					break;
				case 2:
					Map<String, Object> matInvMap = new HashMap<String, Object>();
					matInvMap.put("group_id", entityMap.get("group_id"));
					matInvMap.put("hos_id", entityMap.get("hos_id"));
					matInvMap.put("copy_code", entityMap.get("copy_code"));
					matInvMap.put("inv_id", entityMap.get("inv_id"));
					Map<String, Object> matInvNewMap = matInvMapper.queryByCode(matInvMap); 
					double new_Sell_Price = 0.0; 
					double old_Sell_Price = 0.0;
					 if(!"".equals(entityMap.get("sell_price")) && null !=entityMap.get("sell_price")){
						 new_Sell_Price = Double.parseDouble(entityMap.get("sell_price").toString());
					 }
                     if(!"".equals(matInvNewMap.get("sell_price")) && null !=matInvNewMap.get("sell_price")){
                    	 old_Sell_Price = Double.parseDouble(matInvNewMap.get("sell_price").toString());
					 }
					BigDecimal newSellPrice = new BigDecimal(new_Sell_Price);
					BigDecimal oldSellPrice = new BigDecimal(old_Sell_Price);
					String newEDate = "".equals(entityMap.get("edate"))?"": DateUtil.dateToString((Date) entityMap.get("edate"), "yyyy-MM-dd");
					String oldEDate = "".equals(matInvNewMap.get("edate"))?"": DateUtil.dateToString((Date) matInvNewMap.get("edate"), "yyyy-MM-dd");
					if(newSellPrice.compareTo(oldSellPrice) != 0){
						Map<String, Object> resultSetUpdateMap = new HashMap<String, Object>();
						resultSetUpdateMap.put("c_exp_code", entityMap.get("inv_code"));
						resultSetUpdateMap.put("c_exp_name", entityMap.get("inv_name"));
						resultSetUpdateMap.put("c_spell_code", entityMap.get("spell_code"));
						resultSetUpdateMap.put("c_exp_spec", entityMap.get("inv_model"));
						resultSetUpdateMap.put("c_firm_id", entityMap.get("fac_id"));
						resultSetUpdateMap.put("c_units", entityMap.get("unit_code"));
						resultSetUpdateMap.put("d_retail_price", entityMap.get("sell_price"));
						resultSetUpdateMap.put("c_class_on_inp_rcpt", "U");
						resultSetUpdateMap.put("c_class_on_outp_rcpt", "O");
						resultSetUpdateMap.put("c_class_on_reckoning", "Z05");
						resultSetUpdateMap.put("c_subj_code", "J02");
						resultSetUpdateMap.put("c_class_on_mr", "其它");
						resultSetUpdateMap.put("c_memos", "修改材料零售价");
						resultSetUpdateMap.put("t_start_date", entityMap.get("sdate"));
						resultSetUpdateMap.put("t_stop_date", entityMap.get("edate"));
						resultSetUpdateMap.put("c_material_code", entityMap.get("memory_encoding"));
						resultSetlist.add(resultSetUpdateMap);
					}
					
                    if(newEDate.compareTo(oldEDate) != 0){
                    	Map<String, Object> resultSetUpdateMap = new HashMap<String, Object>();
                    	resultSetUpdateMap.put("c_exp_code", entityMap.get("inv_code"));
						resultSetUpdateMap.put("c_exp_name", entityMap.get("inv_name"));
						resultSetUpdateMap.put("c_spell_code", entityMap.get("spell_code"));
						resultSetUpdateMap.put("c_exp_spec", entityMap.get("inv_model"));
						resultSetUpdateMap.put("c_firm_id", entityMap.get("fac_id"));
						resultSetUpdateMap.put("c_units", entityMap.get("unit_code"));
						resultSetUpdateMap.put("d_retail_price", entityMap.get("sell_price"));
						resultSetUpdateMap.put("c_class_on_inp_rcpt", "U");
						resultSetUpdateMap.put("c_class_on_outp_rcpt", "O");
						resultSetUpdateMap.put("c_class_on_reckoning", "Z05");
						resultSetUpdateMap.put("c_subj_code", "J02");
						resultSetUpdateMap.put("c_class_on_mr", "其它");
						resultSetUpdateMap.put("c_memos", "修改材料停用日期");
						resultSetUpdateMap.put("t_start_date", entityMap.get("sdate"));
						resultSetUpdateMap.put("t_stop_date", entityMap.get("edate"));
						resultSetUpdateMap.put("c_material_code", entityMap.get("memory_encoding"));
						resultSetlist.add(resultSetUpdateMap);
                    }
					break;
				}
				
				    for (Map<String, Object> resultMap : resultSetlist) {
				    	Dao daoTrigger = Mvcs.ctx().getDefaultIoc().get(Dao.class, "daoTriggerMatInv");
						Sql sql = Sqls.create("call comm.pro_exp_pricelist"
								+ "(@IN_c_exp_code,"
								+ " @IN_c_exp_name, "
								+ " @IN_c_exp_spec, "
								+ " @IN_c_firm_id,"
								+ " @IN_c_units,"
								+ " @IN_d_retail_price, "
								+ " @IN_c_class_on_inp_rcpt, "
								+ " @IN_c_class_on_outp_rcpt,"
								+ " @IN_c_class_on_reckoning,"
								+ " @IN_c_subj_code,"
								+ " @IN_c_class_on_mr,"
								+ " @IN_c_memos,"
								+ " @IN_t_start_date,"
								+ " @IN_t_stop_date,"
								+ " @IN_c_material_code,"
								+ " @IN_c_spell_code )"
								); 
						sql.params().set("IN_c_exp_code", resultMap.get("c_exp_code"));
						sql.params().set("IN_c_exp_name", resultMap.get("c_exp_name"));
						sql.params().set("IN_c_exp_spec", resultMap.get("c_exp_spec"));
						sql.params().set("IN_c_firm_id", resultMap.get("c_firm_id"));
						sql.params().set("IN_c_units", resultMap.get("c_units"));
						sql.params().set("IN_d_retail_price", resultMap.get("d_retail_price"));
						sql.params().set("IN_c_class_on_inp_rcpt", resultMap.get("c_class_on_inp_rcpt"));
						sql.params().set("IN_c_class_on_outp_rcpt", resultMap.get("c_class_on_outp_rcpt"));
						sql.params().set("IN_c_class_on_reckoning", resultMap.get("c_class_on_reckoning"));
						sql.params().set("IN_c_subj_code", resultMap.get("c_subj_code"));
						sql.params().set("IN_c_class_on_mr", resultMap.get("c_class_on_mr"));
						sql.params().set("IN_c_memos", resultMap.get("c_memos"));
						sql.params().set("IN_t_start_date", resultMap.get("t_start_date"));
						sql.params().set("IN_t_stop_date",resultMap.get("t_stop_date"));
						sql.params().set("IN_c_material_code", resultMap.get("c_material_code"));
						sql.params().set("IN_c_spell_code", resultMap.get("c_spell_code"));
					    //sql.params().set("OUT_value", OracleTypes.VARCHAR);
					    sql = daoTrigger.execute(sql); 
					    //Record re = sql.getOutParams();
					       message = "材料数据成功发送给HIS！";
					    
					
				    }
				
			} catch (Exception e) {
				
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"操作失败\"}");
			}
			
			return message;
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
			
			//物资目录--判断是否重复（键：物资名称-规格型号）
			List<Map<String, Object>> matInvList = matInvMapper.queryMatInvListForImport(seachMap);
			Map<String, String> matInvMap = new HashMap<String, String>();
			Map<String, String> invCodeMap = new HashMap<String, String>();
			for(Map<String, Object> matInv : matInvList){
				if(matInv.get("INV_MODEL") == null ){
					matInv.put("INV_MODEL", "/");
				}
				matInvMap.put(matInv.get("INV_CODE").toString()+","+matInv.get("INV_NAME").toString()+","+matInv.get("INV_MODEL").toString()+","+matInv.get("UNIT_CODE").toString()+","+matInv.get("PLAN_PRICE").toString(), matInv.get("INV_ID").toString());
				invCodeMap.put(matInv.get("INV_CODE").toString(), "1");
			}
			
			//物资分类，表mat_type
			List<Map<String, Object>> matTypeList = matInvMapper.queryMatTypeListForImport(seachMap);
			Map<String, String> matTypeMap = new HashMap<String, String>();
			for(Map<String, Object> matType : matTypeList){
				matTypeMap.put(matType.get("MAT_TYPE_CODE").toString(), matType.get("MAT_TYPE_ID").toString()+","+matType.get("MAT_TYPE_NO").toString()+","+matType.get("MAT_TYPE_CODE").toString());
				matTypeMap.put(matType.get("MAT_TYPE_NAME").toString(), matType.get("MAT_TYPE_ID").toString()+","+matType.get("MAT_TYPE_NO").toString()+","+matType.get("MAT_TYPE_CODE").toString());
			}
			
			//计价方式
			Map<String, String> priceTypeMap = new HashMap<String, String>();
			priceTypeMap.put("0", "0");
			priceTypeMap.put("1", "1");
			priceTypeMap.put("2", "2");
			priceTypeMap.put("先进先出", "0");
			priceTypeMap.put("移动加权平均", "1");
			priceTypeMap.put("一次性加权平均", "2");
			
			//摊销方式
			Map<String, String> amortizeTypeMap = new HashMap<String, String>();
			amortizeTypeMap.put("1", "1");
			amortizeTypeMap.put("2", "2");
			amortizeTypeMap.put("一次性摊销", "1");
			amortizeTypeMap.put("五五摊销", "2");
			
			//计量单位，表hos_unit
			List<Map<String, Object>> hosUnitList = matInvMapper.queryHosUnitListForImport(seachMap);
			Map<String, String> hosUnitMap = new HashMap<String, String>();
			for(Map<String, Object> hosUnit : hosUnitList){
				hosUnitMap.put(hosUnit.get("UNIT_CODE").toString(), hosUnit.get("UNIT_CODE").toString());
				hosUnitMap.put(hosUnit.get("UNIT_NAME").toString(), hosUnit.get("UNIT_CODE").toString());
			}
			
			//生产厂商，表hos_fac_dict
			List<Map<String, Object>> hosFacList = matInvMapper.queryHosFacListForImport(seachMap);
			Map<String, String> hosFacMap = new HashMap<String, String>();
			for(Map<String, Object> hosFac : hosFacList){
				hosFacMap.put(hosFac.get("FAC_CODE").toString(), hosFac.get("FAC_ID").toString()+","+hosFac.get("FAC_NO").toString());
				hosFacMap.put(hosFac.get("FAC_NAME").toString(), hosFac.get("FAC_ID").toString()+","+hosFac.get("FAC_NO").toString());
			}
			
			//供应商，表hos_sup_dict
			List<Map<String, Object>> hosSupList = matInvMapper.queryHosSupListForImport(seachMap);
			Map<String, String> hosSupMap = new HashMap<String, String>();
			for(Map<String, Object> hosSup : hosSupList){
				hosSupMap.put(hosSup.get("SUP_CODE").toString(), hosSup.get("SUP_ID").toString());
				hosSupMap.put(hosSup.get("SUP_NAME").toString(), hosSup.get("SUP_ID").toString());
			}
			
			//ABC分类
			Map<String, String> abcMap = new HashMap<String, String>();
			abcMap.put("a", "A");
			abcMap.put("b", "B");
			abcMap.put("c", "C");
			abcMap.put("A", "A");
			abcMap.put("B", "B");
			abcMap.put("C", "C");
			
			//是否
			Map<String, String> yesOrNoMap = new HashMap<String, String>();
			yesOrNoMap.put("1", "1");
			yesOrNoMap.put("0", "0");
			yesOrNoMap.put("是", "1");
			yesOrNoMap.put("否", "0");
			
			//管理类别，表mat_inv_mana_type
			List<Map<String, Object>> manaTypeList = matInvMapper.queryManaTypeListForImport(seachMap);
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
			
			//解析前台数据
			if(entityMap.get("data") == null || "".equals(entityMap.get("data"))){
				return "{\"error\":\"EXCEL的数据太多，请减少条数再重新导入！\",\"state\":\"false\"}";
			}
			String data = entityMap.get("data").toString();
			List<Map<String, List<String>>> dataList = SpreadTableJSUtil.toListMap(data, 1);
			if(dataList==null || dataList.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			String inv_code;
			String mat_type_code;
			boolean is_loop = true;
			int index = 0;
			int max_length = 0;
			String type_max_inv_code = null;
			List<String> rowList=null;
			Map<String, String> typeMaxCodeMap = new HashMap<String, String>();
			int maxCode = 0;
			int isMaxCodeFlag = 0;
			
			String invKey;
			for(Map<String, List<String>> dataMap : dataList){
				Map<String, Object> map = new HashMap<String, Object>();
				
				/**校验材料编码************begin*****************/
				rowList = dataMap.get("材料编码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，材料编码为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("inv_code", rowList.get(1));
				/**校验材料编码************end*******************/
				invKey = rowList.get(1);
				
				/**校验材料名称************begin*****************/
				rowList = dataMap.get("材料名称");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，材料名称为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("inv_name", rowList.get(1));
				map.put("spell_code", StringTool.toPinyinShouZiMu(rowList.get(1)));
				map.put("wbx_code", StringTool.toWuBi(rowList.get(1)));
				/**校验材料名称************end*******************/
				invKey += "," + rowList.get(1);
	
				/**校验规格型号************begin*****************/
				rowList = dataMap.get("规格型号");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，规格型号为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("inv_model", rowList.get(1));
				/**校验规格型号************end*******************/
				invKey += "," + rowList.get(1);
				
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
				
				/**校验物资类别**************begin*****************/
				rowList = dataMap.get("物资类别");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，物资类别为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!matTypeMap.containsKey(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，物资类别不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("mat_type_id", matTypeMap.get(rowList.get(1)).split(",")[0]);
				map.put("mat_type_no", matTypeMap.get(rowList.get(1)).split(",")[1]);
				mat_type_code = matTypeMap.get(rowList.get(1)).split(",")[2];
				/**校验物资类别**********end*******************/
				
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
				
				/**校验摊销方式******begin*****************/
				rowList = dataMap.get("摊销方式");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，摊销方式为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!amortizeTypeMap.containsKey(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，摊销方式不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("amortize_type", amortizeTypeMap.get(rowList.get(1)));
				/**校验摊销方式******end*******************/
				
				/**校验计量单位******begin*****************/
				rowList = dataMap.get("计量单位");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，计量单位为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!hosUnitMap.containsKey(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，计量单位不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				map.put("unit_code", hosUnitMap.get(rowList.get(1)));
				/**校验计量单位******end*******************/
				invKey += "," + rowList.get(1);
				
				/**校验生产厂商******begin*****************/
				rowList = dataMap.get("生产厂商");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					//return "{\"warn\":\"" + rowList.get(0) + "，生产厂商为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					map.put("fac_id", null);
					map.put("fac_no", null);
				}else{
					if(!hosFacMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，生产厂商不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("fac_id", hosFacMap.get(rowList.get(1)).split(",")[0]);
					map.put("fac_no", hosFacMap.get(rowList.get(1)).split(",")[1]);
				}
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
				invKey += "," + rowList.get(1);
				
				if(matInvMap.containsKey(invKey)){
					return "{\"warn\":\"" + rowList.get(0) + "，该（规格型号、计量单位、计划价）的材料已存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}else{
					matInvMap.put(invKey, "0");
				}
	
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
	
				/**校验材料用途**********begin*****************/
				rowList = dataMap.get("材料用途");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("inv_usage", null);
				}else{
					map.put("inv_usage", rowList.get(1));
				}
				/**校验材料用途**********end*******************/
	
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
					if(!abcMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，ABC分类不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("abc_type", abcMap.get(rowList.get(1)));
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
					map.put("use_state", "1");
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
					map.put("is_bid", "0");
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
					map.put("is_single_ven", "0");
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
	
				/**校验是否品种码**********begin*****************/
				rowList = dataMap.get("是否品种码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_inv_bar", "0");
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否品种码不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_inv_bar", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否品种码**********end*******************/
	
				/**校验品种码**********begin*****************/
				rowList = dataMap.get("品种码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("bar_code_new", null);
				}else{
					map.put("bar_code_new", rowList.get(1));
				}
				/**校验品种码**********end*******************/
	
				/**校验是否植入材料**********begin*****************/
				rowList = dataMap.get("是否植入材料");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_implant", "0");
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否植入材料不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_implant", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否植入材料**********end*******************/
	
				/**校验是否零库存管理**********begin*****************/
				rowList = dataMap.get("是否零库存管理");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_zero_store", "0");
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否零库存管理不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_zero_store", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否零库存管理**********end*******************/
	
				/**校验是否介入材料**********begin*****************/
				rowList = dataMap.get("是否介入材料");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_involved", "0");
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否介入材料不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_involved", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否介入材料**********end*******************/
	
				/**校验是否灭菌材料**********begin*****************/
				rowList = dataMap.get("是否灭菌材料");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_disinfect", "0");
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否灭菌材料不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_disinfect", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否灭菌材料**********end*******************/
	
				/**校验是否收费**********begin*****************/
				rowList = dataMap.get("是否收费");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_charge", "0");
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否收费不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_charge", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否收费**********end*******************/
	
				/**校验是否高值**********begin*****************/
				rowList = dataMap.get("是否高值");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_highvalue", "0");
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否高值不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_highvalue", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否高值**********end*******************/
	
				/**校验是否耐用品**********begin*****************/
				rowList = dataMap.get("是否耐用品");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_dura", "0");
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否耐用品不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_dura", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否耐用品**********end*******************/
	
				/**校验是否代销**********begin*****************/
				rowList = dataMap.get("是否代销");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_com", "0");
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否代销不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_com", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否代销**********end*******************/
	
				/**校验是否条码管理**********begin*****************/
				rowList = dataMap.get("是否条码管理");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_bar", "0");
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
					map.put("is_per_bar", "0");
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否个体码管理不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_per_bar", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否个体码管理**********end*******************/
	
				/**校验是否保质期管理**********begin*****************/
				rowList = dataMap.get("是否保质期管理");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_quality", "0");
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
					map.put("is_cert", "0");
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否证件管理不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_cert", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否证件管理**********end*******************/
	
				/**校验是否冷链管理**********begin*****************/
				rowList = dataMap.get("是否冷链管理");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_sec_whg", "0");
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						return "{\"warn\":\"" + rowList.get(0) + "，是否冷链管理不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					map.put("is_sec_whg", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否冷链管理**********end*******************/
	
				/**校验是否自制品**********begin*****************/
				rowList = dataMap.get("是否自制品");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_shel_make", "0");
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
				map.put("is_batch", 1);
				map.put("is_add_sale", 0);
				map.put("state", 0);
				map.put("note", null);
				map.put("inv_id", matInvMapper.queryMatInvSeq());
				//变更表
				map.put("change_user", SessionManager.getUserId());
				map.put("change_date", new Date());
				map.put("change_note", "新增");
				map.put("is_stop", 0);
				/**处理物资编码*****************begin*****************/
				if("自动生成".equals(map.get("inv_code").toString())){
					inv_code = "";
					is_loop = true;
					index = 0;
					max_length = 0;
					type_max_inv_code = "";
	
					/**查询系统参数04002获得编码位数---begin----*/
					String maxLength = MyConfig.getSysPara("04002").toString();
					if(NumberUtil.isNumeric(maxLength)){
						max_length = Integer.valueOf(maxLength);
					}else{
						return "{\"error\":\"系统参数04002格式错误.\"}";
					}
					seachMap.put("type_code", "2");
					seachMap.put("mat_type_id", map.get("mat_type_id"));
					while(is_loop){
						/**用于查询并更新最大流水码----begin-----*/
						if(!"1".equals(MyConfig.getSysPara("04014"))){
							if(maxCode == 0){
								maxCode = Integer.valueOf(matNoOtherMapper.queryMatNoOther(seachMap));//获取当前编码最大值
							}
							//如果不存在则插入
							if(maxCode == 0){
								maxCode = 1;
								isMaxCodeFlag = 1; //插入
							}else{
								maxCode = maxCode+1;
								isMaxCodeFlag = 2; //更新
							}
							/**用于查询并更新最大流水码----end-------*/
							
							inv_code = "" + maxCode;
							for (int i = inv_code.length() ; i < max_length; i++) {
								inv_code = "0" + inv_code;
							}
						}else{
							if(typeMaxCodeMap.containsKey(mat_type_code)){
								type_max_inv_code = String.valueOf(Long.valueOf("1"+typeMaxCodeMap.get(mat_type_code).toString()) + 1);
							}else{
								type_max_inv_code = matInvMapper.queryMatInvMaxByType(seachMap);
							}
							
							/**查询系统参数04002获得编码位数---end------*/
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
								inv_code = mat_type_code + inv_code;
							}else{
								inv_code = type_max_inv_code.substring(1, type_max_inv_code.length());
							}
						}
						
						//检验自动生成的编码是否重复
						if(!invCodeMap.containsKey(inv_code)){
							invCodeMap.put(inv_code, "1");
							typeMaxCodeMap.put(mat_type_code, inv_code);
							is_loop = false;
						}
						index ++;
					}
					map.put("inv_code", inv_code);
					
				}
				/**处理物资编码*****************end*******************/
				
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
			matInvMapper.addBatch(invList);
			matInvDictMapper.addBatch(invList);
			//批量添加对应关系
			if(supRelaList.size()>0){
				matInvMapper.addMatInvSup(supRelaList);
			}
			//是否更新最大编码
			seachMap.put("max_no", maxCode);
			if(isMaxCodeFlag == 1){
				matNoOtherMapper.insertMatNoOther(seachMap);
			}else if(isMaxCodeFlag == 2){
				matNoOtherMapper.updateMatNoOther(seachMap);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}	
	
	//补齐材料流水码
	public String completeInvCodeByLen(String inv_code, int len){
		
		for (int i = inv_code.length() ; i < len; i++) {
			inv_code = "0" + inv_code;
		}
		
		return inv_code;
	}
}
