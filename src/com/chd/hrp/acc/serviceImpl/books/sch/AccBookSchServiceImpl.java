/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.books.sch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.books.sch.AccBookSchMapper;
import com.chd.hrp.acc.service.books.sch.AccBookSchService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
 

/**
 * 全年账簿 
 * @author gaopei
 *
 */
@Service("accBookSchService")
public class AccBookSchServiceImpl implements AccBookSchService {

	private static Logger logger = Logger.getLogger(AccBookSchServiceImpl.class);

	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null; 
	@Resource(name = "accBookSchMapper")
	private final AccBookSchMapper accBookSchMapper = null; 
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null; 
	
	//查询方案树形列表
	@Override
	public String queryAccBookSchListForTree(Map<String,Object> entityMap)throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		if(entityMap.get("is_check") == null && "".equals(entityMap.get("is_check").toString())){
			entityMap.put("is_check", 0);
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{Rows:[");
		List<Map<String, Object>> list = accBookSchMapper.queryAccBookSchList(entityMap);

		//拼装顶级（个人与主体）
		sb.append("{id:\"1\",pId:\"top\",name:\"个人方案\",title:\"个人方案\",open:false}");
		sb.append("{id:\"2\",pId:\"top\",name:\"主体方案\",title:\"主体方案\",open:false},");
		
		//拼装级次
		if (list!=null && list.size()>0) {
			Map<String,Object> pidMap=new HashMap<String,Object>();
			int row = 0;
			for (Map<String, Object> map : list) {
				
				if(pidMap.size()==0 || pidMap.get(map.get("che_group"))==null){
					if (row == 0) {
						sb.append("{");
					} else {
						sb.append(",{");
					}
					row++;
					pidMap.put(map.get("che_group").toString(), "t_"+row);
					sb.append("id:\"t_"+row+"\",");
					sb.append("pId:\""+map.get("che_type").toString()+"\",");
					sb.append("name:\"" + map.get("che_group").toString()+ "\",");
					sb.append("title:\"" + map.get("che_group").toString() + "\",");
					sb.append("open:false");
					sb.append("}");
				}
			}
			
			for (Map<String, Object> map : list) {
				sb.append(",{");
				sb.append("id:\"" + map.get("sch_id") + "\",");
				sb.append("pId:\"" + pidMap.get(map.get("che_group").toString()) + "\",");
				sb.append("name:\"" + map.get("che_name") + "\",");
				sb.append("che_group:\"" + map.get("che_group") + "\",");
				sb.append("note:\"" + map.get("note") + "\",");
				sb.append("che_type:\"" + map.get("che_type") + "\",");
				
				sb.append("}");
			}
		}
		
		sb.append("]}");
		return sb.toString();
	}
	
	//查询方案列表
	@Override
	public String queryAccBookSchList(Map<String,Object> entityMap)throws DataAccessException{

		if(entityMap.get("is_check") == null && "".equals(entityMap.get("is_check").toString())){
			entityMap.put("is_check", 0);
		}
		
		List<Map<String, Object>> list = accBookSchMapper.queryAccBookSchList(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	//方案保存
	@Override
	public Map<String, Object> saveAccBookSch(Map<String, Object> entityMap) throws DataAccessException{
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("create_user", SessionManager.getUserId());
			entityMap.put("create_date",DateUtil.getCurrenDate());
			entityMap.put("spell_code", StringTool.toQuanPin(entityMap.get("che_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("che_name").toString()));

			if(entityMap.get("is_check") == null && "".equals(entityMap.get("is_check").toString())){
				entityMap.put("is_check", 0);
			}
			if(entityMap.get("che_group") == null || "".equals(entityMap.get("che_group"))){
				entityMap.put("che_group", "系统默认");
			}

			//添加
			if(entityMap.get("sch_id") == null || "".equals(entityMap.get("sch_id").toString())){
				//检验方案名称
				entityMap.put("field_table","ACC_BOOK_SCH");
				entityMap.put("field_key1", "che_name");
				entityMap.put("field_value1", entityMap.get("che_name"));
				entityMap.put("field_key2", "page");
				entityMap.put("field_value2", entityMap.get("page"));
				int count = sysFunUtilMapper.existsSysCodeNameByAdd(entityMap);
				if (count >0) {
					retMap.put("state", "false");
					retMap.put("error", "方案：[" + entityMap.get("che_name").toString() + "]重复.");
					return retMap;
				}
				
				entityMap.put("sch_id", UUIDLong.absStringUUID());
				//添加
				accBookSchMapper.addAccBookSch(entityMap);
			}else{
				//检验方案名称
				entityMap.put("field_table","ACC_BOOK_SCH");
				entityMap.put("field_id", "sch_id");
				entityMap.put("field_id_value", entityMap.get("sch_id"));
				entityMap.put("field_key1", "che_name");
				entityMap.put("field_value1", entityMap.get("che_name"));
				entityMap.put("field_key2", "page");
				entityMap.put("field_value2", entityMap.get("page"));
				int count = sysFunUtilMapper.existsSysCodeNameByUpdate(entityMap);
				if (count >0) {
					retMap.put("state", "false");
					retMap.put("error", "方案：[" + entityMap.get("che_name").toString() + "]重复.");
					return retMap;
				}
				
				//修改
				accBookSchMapper.updateAccBookSch(entityMap);
			}
			
			retMap.put("msg", "操作成功");
		} catch (Exception e) { 
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}
		
		return retMap;
	}
	
	//方案条件保存
	@Override
	public Map<String, Object> saveAccBookSchDetail(Map<String, Object> entityMap) throws DataAccessException{
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		try {
			//判断在没有选择方案的时候添加一个默认方案，查询后在删除
			if("1".equals(entityMap.get("sch_id"))){   
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("sch_id", entityMap.get("sch_id"));
				tempMap.put("group_id", SessionManager.getGroupId());
				tempMap.put("hos_id", SessionManager.getHosId());
				tempMap.put("copy_code", SessionManager.getCopyCode());
				tempMap.put("create_user", SessionManager.getUserId());
				tempMap.put("create_date",DateUtil.getCurrenDate());
			
				tempMap.put("che_name", "1");
				tempMap.put("che_group", "临时");
				tempMap.put("che_type", "1");
				tempMap.put("spell_code", "1");
				tempMap.put("wbx_code", "1");
				tempMap.put("is_check", entityMap.get("is_check")==null?0:entityMap.get("is_check"));
				Map<String, Object> list = accBookSchMapper.queryAccBookSchById(tempMap);
				if (list == null || list.isEmpty()) {
					accBookSchMapper.addAccBookSch(tempMap);
				}
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
			if(entityMap.get("con_id") == null || "".equals(entityMap.get("con_id").toString())){
				entityMap.put("con_id", UUIDLong.absStringUUID());
			}
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			if(entityMap.get("subj_code") != null && !"[]".equals(entityMap.get("subj_code").toString())){
				JSONArray data=JSONArray.parseArray(entityMap.get("subj_code").toString());
				if(data!=null && data.size()>0){
					Map<String, Object> dataMap = new HashMap<String, Object>();
					
					for(int i=0;i<data.size();i++){
						dataMap = new HashMap<String,Object>();
						JSONObject dataJson=JSONObject.parseObject(data.getString(i));
						dataMap.put("group_id", entityMap.get("group_id"));
						dataMap.put("hos_id", entityMap.get("hos_id"));
						dataMap.put("copy_code", entityMap.get("copy_code"));
						dataMap.put("sch_id", entityMap.get("sch_id"));
						dataMap.put("subj_code", dataJson.get("subj_code"));
						dataMap.put("is_fw", entityMap.get("is_fw")==null?0:entityMap.get("is_fw"));
						list.add(dataMap);
					}
				}
			}
			
			List<Map<String, Object>> listcheck = new ArrayList<Map<String,Object>>();
			Map<String, Object> datacheckMap = new HashMap<String, Object>();
			if(entityMap.get("check_type1") != null && !"".equals(entityMap.get("check_type1").toString())){
				datacheckMap = new HashMap<String,Object>();
				datacheckMap.put("group_id", entityMap.get("group_id"));
				datacheckMap.put("hos_id", entityMap.get("hos_id"));
				datacheckMap.put("copy_code", entityMap.get("copy_code"));
				datacheckMap.put("sch_id", entityMap.get("sch_id"));
				datacheckMap.put("sort_code", "1");
				datacheckMap.put("check_type_id", entityMap.get("check_type1"));
				datacheckMap.put("check_item_code_b", entityMap.get("check_item1_b"));
				datacheckMap.put("check_item_code_e", entityMap.get("check_item1_e"));
				listcheck.add(datacheckMap);
			}
			if(entityMap.get("check_type2") != null && !"".equals(entityMap.get("check_type2").toString())){
				datacheckMap = new HashMap<String,Object>();
				datacheckMap.put("group_id", entityMap.get("group_id"));
				datacheckMap.put("hos_id", entityMap.get("hos_id"));
				datacheckMap.put("copy_code", entityMap.get("copy_code"));
				datacheckMap.put("sch_id", entityMap.get("sch_id"));
				datacheckMap.put("sort_code", "2");
				datacheckMap.put("check_type_id", entityMap.get("check_type2"));
				datacheckMap.put("check_item_code_b", entityMap.get("check_item2_b"));
				datacheckMap.put("check_item_code_e", entityMap.get("check_item2_e"));
				listcheck.add(datacheckMap);
			}
			if(entityMap.get("check_type3") != null && !"".equals(entityMap.get("check_type3").toString())){
				datacheckMap = new HashMap<String,Object>();
				datacheckMap.put("group_id", entityMap.get("group_id"));
				datacheckMap.put("hos_id", entityMap.get("hos_id"));
				datacheckMap.put("copy_code", entityMap.get("copy_code"));
				datacheckMap.put("sch_id", entityMap.get("sch_id"));
				datacheckMap.put("sort_code", "3");
				datacheckMap.put("check_type_id", entityMap.get("check_type3"));
				datacheckMap.put("check_item_code_b", entityMap.get("check_item3_b"));
				datacheckMap.put("check_item_code_e", entityMap.get("check_item3_e"));
				listcheck.add(datacheckMap);
			}
			if(entityMap.get("check_type4") != null && !"".equals(entityMap.get("check_type4").toString())){
				datacheckMap = new HashMap<String,Object>();
				datacheckMap.put("group_id", entityMap.get("group_id"));
				datacheckMap.put("hos_id", entityMap.get("hos_id"));
				datacheckMap.put("copy_code", entityMap.get("copy_code"));
				datacheckMap.put("sch_id", entityMap.get("sch_id"));
				datacheckMap.put("sort_code", "4");
				datacheckMap.put("check_type_id", entityMap.get("check_type4"));
				datacheckMap.put("check_item_code_b", entityMap.get("check_item4_b"));
				datacheckMap.put("check_item_code_e", entityMap.get("check_item4_e"));
				listcheck.add(datacheckMap);
			}
			
			accBookSchMapper.deleteAccBookSchDetail(entityMap);
			accBookSchMapper.addAccBookSchCon(entityMap);
			if(list.size() > 0){
				accBookSchMapper.addAccBookSchSubj(list);
			}
			if(listcheck.size() > 0){
				accBookSchMapper.addAccBookSchCheck(listcheck);
			}
			 
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}
		
		return retMap;
	}
	
	//方案删除
	@Override
	public Map<String, Object> deleteAccBookSch(Map<String, Object> entityMap) throws DataAccessException{
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
			accBookSchMapper.deleteAccBookSch(entityMap);
			
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}
		
		return retMap;
	}
	
	//方案查询
	@Override
	public Map<String, Object> queryAccBookSchById(Map<String, Object> entityMap) throws DataAccessException{

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		return accBookSchMapper.queryAccBookSchById(entityMap);
	}
	
	//方案条件查询
	@Override
	public Map<String, Object> queryAccBookSchDetailById(Map<String, Object> entityMap) throws DataAccessException{

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("acc_year", SessionManager.getAcctYear());
		
		//获取一般条件
		Map<String, Object> retMap = accBookSchMapper.queryAccBookSchConById(entityMap);
		if(retMap == null || retMap.size() == 0){
			retMap = new HashMap<String, Object>();
		}
		//获取科目条件
		List<Map<String, Object>> list = accBookSchMapper.queryAccBookSchSubjById(entityMap);
		if(list != null && list.size() > 0){
			retMap.put("subj_code", list);
		}else{
			retMap.put("subj_code", "");
		}
		
		//获取科目条件
		List<Map<String, Object>> listcheck = JsonListMapUtil.toListMapLower(accBookSchMapper.queryAccBookSchCheckById(entityMap));
		if(listcheck != null && listcheck.size() > 0){
			boolean have_b = false, have_e = false; 
			Map<String,Object> checkMap = null;
			Map<String,Object> checkMap1 = null;
			Map<String,Object> checkMap2 = null;
			Map<String,Object> checkMap4 = null;
			for (Map<String, Object> map : listcheck){
				checkMap = new HashMap<String,Object>();
				checkMap1 = new HashMap<String,Object>();
				checkMap2 = new HashMap<String,Object>();
				checkMap4 = new HashMap<String,Object>();
				if(map != null && !map.isEmpty()){
					if(map.get("check_item_b") != null && !"".equals(map.get("check_item_b").toString())){
						have_b = true;
					}
					if(map.get("check_item_e") != null && !"".equals(map.get("check_item_e").toString())){
						have_e = true;
					}
					if(have_b || have_e){
						checkMap=accBookSchMapper.queryAccBookSchCheckTypeById(map);
						checkMap1.put("group_id", SessionManager.getGroupId());
						checkMap1.put("hos_id", SessionManager.getHosId());
						checkMap1.put("copy_code", SessionManager.getCopyCode());
						checkMap1.put("check_table", checkMap.get("CHECK_TABLE"));
						checkMap1.put("column_id", checkMap.get("COLUMN_ID"));
						checkMap1.put("column_code", checkMap.get("COLUMN_CODE"));
						checkMap1.put("column_name",checkMap.get("COLUMN_NAME"));
						checkMap1.put("is_sys",checkMap.get("IS_SYS"));
						checkMap1.put("is_change",checkMap.get("IS_CHANGE"));
						checkMap1.put("check_type_id",map.get("check_type_id"));
						if(have_b){
							checkMap1.put("check_item_code",map.get("check_item_code_b"));
							checkMap2=accBookSchMapper.queryAccBookSchCheckItemById(checkMap1);
						}
						if(have_e){
							checkMap1.put("check_item_code",map.get("check_item_code_e"));
							checkMap4=accBookSchMapper.queryAccBookSchCheckItemById(checkMap1);
						}
					}
				}
				
				retMap.put("check_item_type" + map.get("sort_code").toString(), map.get("check_type_id"));
				retMap.put("check_item_code" + map.get("sort_code").toString() + "_b", have_b ? map.get("check_item_code_b") : "");
				retMap.put("check_item_name" + map.get("sort_code").toString() + "_b", have_b ? checkMap2.get("TEXT") : "");
				retMap.put("check_item_code" + map.get("sort_code").toString() + "_e", have_e ? map.get("check_item_code_e") : "");
				retMap.put("check_item_name" + map.get("sort_code").toString() + "_e", have_e ? checkMap4.get("TEXT") : "");
			}
		}else{
			retMap.put("check", "");
		}
		
		retMap.put("state", "true");
		
		return retMap;
	}
	
	//查询科目树形列表
	@Override
	public String queryAccSubjListForTree(Map<String,Object> entityMap)throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("acc_year", SessionManager.getAcctYear());
		entityMap.put("user_id", SessionManager.getUserId());
		
		return superVouchService.queryAccVcouchSubjTree(entityMap);
	}
	
	@Override
	public String queryAccBookSchCheckNameBySchId(Map<String, Object> entityMap) throws DataAccessException{

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> list=accBookSchMapper.queryAccBookSchCheckNameBySchId(entityMap);
		
		return ChdJson.toJson(list);
	}
}
