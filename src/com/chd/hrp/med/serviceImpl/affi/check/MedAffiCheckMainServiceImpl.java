/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.affi.check;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.med.dao.affi.check.MedAffiCheckDetailMapper;
import com.chd.hrp.med.dao.affi.check.MedAffiCheckMainMapper;
import com.chd.hrp.med.dao.affi.check.MedAffiCheckRelaMapper;
import com.chd.hrp.med.dao.base.MedAffiInMapper;
import com.chd.hrp.med.dao.base.MedAffiOutMapper;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.dao.base.MedNoManageMapper;
import com.chd.hrp.med.dao.base.MedNoOtherMapper;
import com.chd.hrp.med.entity.MedAffiCheckMain;
import com.chd.hrp.med.service.affi.check.MedAffiCheckMainService;
import com.chd.hrp.med.service.affi.in.MedAffiInCommonService;
import com.chd.hrp.med.service.affi.out.MedAffiOutCommonService;
import com.chd.hrp.med.service.base.MedCommonService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table:
 * MED_CHECK_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medAffiCheckMainService")
public class MedAffiCheckMainServiceImpl implements  MedAffiCheckMainService{

	private static Logger logger = Logger.getLogger(MedAffiCheckMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medAffiCheckMainMapper")
	private final MedAffiCheckMainMapper medAffiCheckMainMapper = null;
	
	@Resource(name = "medAffiCheckDetailMapper")
	private final MedAffiCheckDetailMapper medAffiCheckDetailMapper = null;
	
	
	@Resource(name = "medAffiOutCommonService")
	private final MedAffiOutCommonService medAffiOutCommonService = null;
	
	@Resource(name = "medAffiInCommonService")
	private final MedAffiInCommonService medAffiInCommonService = null;
    
	@Resource(name = "medAffiCheckRelaMapper")
	private final MedAffiCheckRelaMapper medAffiCheckRelaMapper = null;
	
	@Resource(name = "medNoManageMapper")
	private final MedNoManageMapper medNoManageMapper = null;
	
	@Resource(name = "medNoOtherMapper")
	private final MedNoOtherMapper medNoOtherMapper = null;
	
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;
	
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	@Resource(name = "medAffiOutMapper")
	private final MedAffiOutMapper medAffiOutMapper = null;
	@Resource(name = "medAffiInMapper")
	private final MedAffiInMapper medAffiInMapper = null;
	
	/**
	 * @Description 
	 * 添加<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{

		try {			
			entityMap = defaultValue(entityMap);
			String create_date = (String) entityMap.get("create_date");
			entityMap.put("year", create_date.substring(0, 4));
			entityMap.put("month", create_date.substring(5, 7));			
			Long check_id = medAffiCheckMainMapper.queryMedAffiCheckMainSeq();// 查询序列
			entityMap.put("check_id", check_id);
			entityMap.put("check_code", getNextIn_no(entityMap));// 获取出库单号			
			// ----------------------------------保存明细数据--------------------------------			
			List<Map<String, Object>> check_detail_batch = new ArrayList<Map<String, Object>>();// 存放明细
			JSONArray check_detail_json = JSONArray.parseArray((String) entityMap.get("check_detail_data"));// 解析明细数据
			Iterator check_detail_it = check_detail_json.iterator();
			while (check_detail_it.hasNext()) {
				Map<String, Object> mapDetailVo = defaultDetailValue();
				mapDetailVo.put("group_id", entityMap.get("group_id"));
				mapDetailVo.put("hos_id", entityMap.get("hos_id"));
				mapDetailVo.put("copy_code", entityMap.get("copy_code"));
				mapDetailVo.put("check_id", entityMap.get("check_id"));
				mapDetailVo.put("check_code", entityMap.get("check_code"));
				JSONObject jsonObj = JSONObject.parseObject(check_detail_it.next().toString());
				//没有药品ID视为空行
				if(jsonObj.get("inv_id") != null && !"".equals(String.valueOf(jsonObj.get("inv_id")))){
					mapDetailVo.put("detail_id", medAffiCheckDetailMapper.queryMedAffiCheckDetailSeq());
					mapDetailVo.put("inv_id", jsonObj.get("inv_id"));
					mapDetailVo.put("inv_no", jsonObj.get("inv_no"));
					if (validateJSON(String.valueOf(jsonObj.get("batch_no")))) {
						mapDetailVo.put("batch_no", jsonObj.get("batch_no").toString());
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("bar_code")))) {
						mapDetailVo.put("bar_code", jsonObj.get("bar_code").toString());
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("location_id")))) {
						mapDetailVo.put("location_id", jsonObj.get("location_id"));
					}else{
						mapDetailVo.put("location_id", "0");
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("cur_amount")))) {
						mapDetailVo.put("cur_amount", jsonObj.get("cur_amount").toString());
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("chk_amount")))) {
						mapDetailVo.put("chk_amount", jsonObj.get("chk_amount").toString());
					}
					if (validateJSON(String.valueOf(jsonObj.get("price")))) {
						mapDetailVo.put("price", jsonObj.get("price").toString());
					}
					if (validateJSON(String.valueOf(jsonObj.get("inva_date")))) {
						mapDetailVo.put("inva_date", jsonObj.get("inva_date").toString());
					}
					if (validateJSON(String.valueOf(jsonObj.get("disinfect_date")))) {
						mapDetailVo.put("disinfect_date", jsonObj.get("disinfect_date").toString());
					}
					if (validateJSON(String.valueOf(jsonObj.get("note")))) {
						mapDetailVo.put("note", jsonObj.get("note"));
					}
					check_detail_batch.add(mapDetailVo);
				}
			}
			if(check_detail_batch.size() > 0){
				
				medAffiCheckMainMapper.add(entityMap);		
				medAffiCheckDetailMapper.addBatch(check_detail_batch);
			}else{
				
				return "{\"error\":\"明细数据为空！\"}";
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
		
		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("check_id").toString()+","
			+"\"}";
	}
	
	/**
	 * 返回用用于保存的默认值 
	 */
	public Map<String, Object> defaultValue(Map<String, Object> mapVo) {
		if (mapVo.get("group_id") == null) {mapVo.put("group_id", mapVo.get("group_id"));}
		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", mapVo.get("hos_id"));}
		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", mapVo.get("copy_code"));}
		if (mapVo.get("check_code") == null) {mapVo.put("check_code", "");}
		if (mapVo.get("store_id") == null) {mapVo.put("store_id", "");}
		if (mapVo.get("store_no") == null) {mapVo.put("store_no", "");}
		if (mapVo.get("dept_id") == null) {mapVo.put("dept_id", "");}
		if (mapVo.get("dept_no") == null) {mapVo.put("dept_no", "");}
		if (mapVo.get("check_date") == null) {mapVo.put("check_date", "");}
		if (mapVo.get("emp_id") == null) {mapVo.put("emp_id", "");}
		if (mapVo.get("maker") == null) {mapVo.put("maker", "");}
		if (mapVo.get("checker") == null) {mapVo.put("checker", "");}
		if (mapVo.get("state") == null) {mapVo.put("state", "1");}
		if (mapVo.get("brif") == null) {mapVo.put("brif", "");}		
		if (mapVo.get("make_date") == null) {mapVo.put("make_date", "");}		
		if (mapVo.get("create_date") == null) {mapVo.put("create_date", "");}
		return mapVo;
	}

	// 返回用用于保存的默认值
		public Map<String, Object> defaultDetailValue() {
			Map<String, Object> mapDetailVo = new HashMap<String, Object>();
			mapDetailVo.put("hos_id", 0);
			mapDetailVo.put("group_id", 0);
			mapDetailVo.put("copy_code", "");
			mapDetailVo.put("check_id", 0);
			mapDetailVo.put("check_code", "");
			mapDetailVo.put("inv_id", 0);
			mapDetailVo.put("inv_no", 0);
			mapDetailVo.put("batch_no", "");
			mapDetailVo.put("bar_code", "");
			mapDetailVo.put("location_id", 0);
			mapDetailVo.put("cur_amount", "");
			mapDetailVo.put("chk_amount", "");
			mapDetailVo.put("price", 0);
			mapDetailVo.put("inva_date", "");
			mapDetailVo.put("disinfect_date", "");
			mapDetailVo.put("note", "");
			return mapDetailVo;
		}


		public boolean validateJSON(String str) {
			if (str != null && !"null".equals(str) && !"".equals(str)) {
				return true;
			}
			return false;
		}
	
	/**
	 * 获取需要生成的出库单号
	 * 
	 * @param entityMap
	 *            map参数必须包含(group_id, hos_id, copy_code, store_id, year, month,
	 *            bus_type_code)这六个键值
	 * @return
	 */
		public String getNextIn_no(Map<String, Object> entityMap) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("store_id", entityMap.get("store_id"));
			// 获取仓库别名store_alias
			//String store_alias = medCommonMapper.queryStoreAliasById(map);
			map.put("table_code", "MED_AFFI_CHECK_MAIN");
			map.put("year", entityMap.get("year"));
			map.put("month", entityMap.get("month"));
			map.put("prefixe", "PD");
			map.put("store_alias", "");
			map.put("bus_type", "");
			// 判断是否存在该业务流水码
			int flag = medNoManageMapper.queryIsExists(map);
			String max_no = "";
			if (flag == 0) {// 如不存在则流水码为1，并插入流水码表中
				max_no = "1";
				map.put("max_no", 1);
				medNoManageMapper.add(map);
			} else {
				// 更新该业务流水码+1
				medNoManageMapper.updateMaxNo(map);
				// 取出该业务更新后的流水码
				max_no = medNoManageMapper.queryMaxCode(map);
			}
			// 补流水码前缀0
			for (int i = max_no.length(); i < 5; i++) {
				max_no = "0" + max_no;
			}
			// 组装流水码
			String in_no = map.get("prefixe") + "-" + entityMap.get("year").toString() + entityMap.get("month").toString() + max_no;
			return in_no;
		}
	
	/**
	 * @Description 
	 * 批量添加<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{	
		try {		
			medAffiCheckMainMapper.addBatch(entityList);			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
		}		
	}
	
		/**
	 * @Description 
	 * 更新<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{		
		try {		
			entityMap = defaultValue(entityMap);
			String create_date = (String) entityMap.get("create_date");
			entityMap.put("year", create_date.substring(0, 4));
			entityMap.put("month", create_date.substring(5, 7));			
			// ----------------------------------保存明细数据--------------------------------		
			List<Map<String, Object>> check_detail_batch = new ArrayList<Map<String, Object>>();// 存放明细
			JSONArray check_detail_json = JSONArray.parseArray((String) entityMap.get("check_detail_data"));// 解析明细数据
			Iterator check_detail_it = check_detail_json.iterator();
			while (check_detail_it.hasNext()) {
				Map<String, Object> mapDetailVo = defaultDetailValue();
				mapDetailVo.put("group_id", entityMap.get("group_id").toString());
				mapDetailVo.put("hos_id", entityMap.get("hos_id").toString());
				mapDetailVo.put("copy_code", entityMap.get("copy_code").toString());
				mapDetailVo.put("check_id", entityMap.get("check_id").toString());
				mapDetailVo.put("check_code", entityMap.get("check_code").toString());
				JSONObject jsonObj = JSONObject.parseObject(check_detail_it.next().toString());
				//没有药品ID视为空行
				if(jsonObj.get("inv_id") != null && !"".equals(String.valueOf(jsonObj.get("inv_id")))){
					
					if (validateJSON(String.valueOf(jsonObj.get("detail_id")))) {
						mapDetailVo.put("detail_id", jsonObj.get("detail_id").toString());
					}else{
						mapDetailVo.put("detail_id", medAffiCheckDetailMapper.queryMedAffiCheckDetailSeq());
					}
					
					mapDetailVo.put("inv_id", jsonObj.get("inv_id").toString());
					mapDetailVo.put("inv_no", jsonObj.get("inv_no").toString());
					if (validateJSON(String.valueOf(jsonObj.get("batch_no")))) {
						mapDetailVo.put("batch_no", jsonObj.get("batch_no").toString());
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("bar_code")))) {
						mapDetailVo.put("bar_code", jsonObj.get("bar_code").toString());
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("location_id")))) {
						mapDetailVo.put("location_id", jsonObj.get("location_id").toString());
					}else{
						mapDetailVo.put("location_id", "0");//货位
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("cur_amount")))) {
						mapDetailVo.put("cur_amount", jsonObj.get("cur_amount").toString());
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("chk_amount")))) {
						mapDetailVo.put("chk_amount", jsonObj.get("chk_amount").toString());
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("price")))) {
						mapDetailVo.put("price", jsonObj.get("price").toString());
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("inva_date")))) {
						mapDetailVo.put("inva_date", jsonObj.get("inva_date"));
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("disinfect_date")))) {
						mapDetailVo.put("disinfect_date", jsonObj.get("disinfect_date"));
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("note")))) {
						mapDetailVo.put("note", jsonObj.get("note"));
					}
					check_detail_batch.add(mapDetailVo);
				}
			}
			if(check_detail_batch.size() > 0){
				
				medAffiCheckMainMapper.update(entityMap);			
				medAffiCheckDetailMapper.delete(entityMap);			
				medAffiCheckDetailMapper.addBatch(check_detail_batch);			
			}else{
			
				return "{\"error\":\"明细数据为空！\"}";
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"更新失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 update\"}";
		}	
		return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	}
	/**
	 * @Description 
	 * 批量更新<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{	
		try {			
			medAffiCheckMainMapper.updateBatch(entityList);			
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"更新失败\"}");
		}	
		return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	}
	/**
	 * @Description 
	 * 删除<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {   	
    try {		
			int state = medAffiCheckMainMapper.delete(entityMap);				
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"更新失败\"}");
		}
    
    	return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
  }
    
	/**
	 * @Description 
	 * 批量删除<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{		
		try {			
			medAffiCheckDetailMapper.deleteBatch(entityList);			
			medAffiCheckMainMapper.deleteBatch(entityList);			
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"更新失败\"}");
		}	
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 添加<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));		
		List<MedAffiCheckMain> list = (List<MedAffiCheckMain>)medAffiCheckMainMapper.queryExists(mapVo);		
		if (list.size()>0) {
			int state = medAffiCheckMainMapper.update(entityMap);			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		
		try {			
			int state = medAffiCheckMainMapper.add(entityMap);			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"更新失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";
		}		
	}
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{		
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");		
		if (sysPage.getTotal()==-1){			
			List<MedAffiCheckMain> list = (List<MedAffiCheckMain>)medAffiCheckMainMapper.query(entityMap);			
			return ChdJson.toJson(list);			
		}else{			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());			
			List<MedAffiCheckMain> list = (List<MedAffiCheckMain>)medAffiCheckMainMapper.query(entityMap, rowBounds);			
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJson(list, page.getTotal());			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medAffiCheckMainMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedAffiCheckMain
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medAffiCheckMainMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<MedAffiCheckMain>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return medAffiCheckMainMapper.queryExists(entityMap);
	}
	/**
	 * 获取主表信息
	 */
	@Override
	public String queryMedAffiCheckMain(Map<String, Object> entityMap) throws DataAccessException {		
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");		
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = (List<Map<String,Object>>)medAffiCheckMainMapper.queryMedAffiCheckMain(entityMap);			
			return ChdJson.toJsonLower(list);			
		}else{			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());			
			List<Map<String,Object>> list = (List<Map<String,Object>>)medAffiCheckMainMapper.queryMedAffiCheckMain(entityMap, rowBounds);			
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());			
		}
	}

	/**
	 * 审核
	 */
	@Override
	public String auditMedAffiCheckMain(List<Map<String, Object>> entityMap) throws DataAccessException {
		List<Map<String, Object>> mainList =new ArrayList<Map<String, Object>>();//存放med_out_main字段		
		try {
			List<Map<String, Object>> listVo = entityMap;			
			for(Map<String, Object> tmp:entityMap){
				tmp.put("state", "2");
				mainList.add(tmp);				
			}			
			medAffiCheckMainMapper.updateAuditBatch(mainList);		
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"审核失败 数据库异常 请联系管理员! 方法 auditMedAffiCheckMain\"}";
		}
		return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
	}
	/**
	 * 销审
	 */
	@Override
	public String unAuditMedAffiCheckMain(List<Map<String, Object>> entityMap) throws DataAccessException {
		List<Map<String, Object>> mainList =new ArrayList<Map<String, Object>>();//存放med_check_main字段		
		try {
			List<Map<String, Object>> listVo = entityMap;			
			for(Map<String, Object> tmp:entityMap){
				tmp.put("state", "1");
				mainList.add(tmp);				
			}			
			medAffiCheckMainMapper.updateAuditBatch(mainList);			
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"消审失败 数据库异常 请联系管理员! 方法 unAuditMedAffiCheckMain\"}";
		}
		return "{\"msg\":\"消审成功.\",\"state\":\"true\"}";
	}
	
	
	/**
	 * 生成出入库单
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String createInOut(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("08005"));
			//同一时间
			String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			String day = date.substring(8, 10);  //用于生成单号
			
			//存放出库数据
			List<Map<String, Object>> outMainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> outDetailList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> outSourceList = new ArrayList<Map<String,Object>>();
			//存放入库数据
			List<Map<String, Object>> inMainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> inDetailList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> inSourceList = new ArrayList<Map<String,Object>>();
			
			//存放盘点与出入库对应关系
			List<Map<String, Object>> relaList = new ArrayList<Map<String,Object>>();
			
			double in_money_sum = 0;  //记录每个盘点单入库总金额
			double out_money_sum = 0;  //记录每个盘点单出库总金额
			StringBuffer invEnoughMsg = new StringBuffer();  //记录库存不足的药品/*用于查询个体码----begin-----*/
			
			Map<String,Object> barCodeMap = new HashMap<String,Object>();
			barCodeMap.put("group_id", entityList.get(0).get("group_id"));
			barCodeMap.put("hos_id", entityList.get(0).get("hos_id"));
			barCodeMap.put("type_code", 1);
			String bar_code = medNoOtherMapper.queryMedNoOther(barCodeMap);//获取当前个体码
			//如果不存在则插入
			if(bar_code == null || "".equals(bar_code)){
				bar_code = "000000000000";
				barCodeMap.put("max_no", bar_code);
				medNoOtherMapper.insertMedNoOther(barCodeMap);
			}
			String init_bar_code = bar_code;
			/*用于查询个体码----end-------*/

			/*用于查询批次----begin-----*/
			Map<String,Object> batchSnMap = new HashMap<String,Object>();
			batchSnMap.put("group_id", entityList.get(0).get("group_id"));
			batchSnMap.put("hos_id", entityList.get(0).get("hos_id"));
			batchSnMap.put("copy_code", entityList.get(0).get("copy_code"));
			batchSnMap.put("c_max", "batch_sn");
			batchSnMap.put("table_name", "med_affi_in_detail");
			batchSnMap.put("c_name", "inv_id");//查询批次所用
			batchSnMap.put("c_name1", "batch_no");//查询批次所用
			/*用于查询批次----end-----*/
			
			//存放相同药品批号的最大批次号
			Map<String, Integer> invBatchSnMap = new HashMap<String, Integer>();
			
			//一个盘点单需要对应一张出入库单(根据盘盈盘亏有可能只有一张出库或一张入库单)，不能多张盘点单一起生成
			for(Map<String, Object> entityMap : entityList){
				//获取选中的主表数据
				Map<String, Object> mainMap = toMapLower(medAffiCheckMainMapper.queryMedAffiCheckMainForInOut(entityMap));
				//获取明细盘盈数据
				List<Map<String, Object>> inCheckList = toListMapLower(medAffiCheckDetailMapper.queryMedAffiCheckDetailProfitForInOut(entityMap));
				//获取明细盘亏数据
				List<Map<String, Object>> outCheckList = toListMapLower(medAffiCheckDetailMapper.queryMedAffiCheckDetailLossForInOut(entityMap));
				
				mainMap.put("brief", "由盘点单"+mainMap.get("check_code").toString()+"生成");
		
				//期间-年
				mainMap.put("year", year);
				//期间-月
				mainMap.put("month", month);
				entityMap.put("day", day);  //用于生成单号
				//制单人
				mainMap.put("maker", entityMap.get("user_id"));
				//状态
				mainMap.put("state", 1);
				
				//注：出库的时间都是String型，入库所用时间都是Date型
				//如果存在盘亏数据则根据先进先出生成出库单，
				if(outCheckList.size() > 0){
					Map<String, Object> outMap = new HashMap<String, Object>();
					outMap.putAll(mainMap);
					//业务类型
					outMap.put("bus_type_code", 9);
					outMap.put("table_code", "med_affi_out");
					//单号
					String out_no = medCommonService.getMedNextNo(outMap);
					if(out_no.indexOf("error") > 0){
						return out_no;
					}
					outMap.put("out_no", out_no);
					//单据ID
					outMap.put("out_id", medAffiOutMapper.queryMedAffiOutMainSeq());
					outMap.put("out_date", DateUtil.stringToDate(date, "yyyy-MM-dd"));
					//盘点单与出库单对应关系
					mainMap.put("out_id", outMap.get("out_id").toString());
					mainMap.put("out_no", outMap.get("out_no").toString());
					
					
					//明细--按先进先出取相关批次
					for(Map<String, Object> map : outCheckList){
						map.put("group_id", outMap.get("group_id").toString());
						map.put("hos_id", outMap.get("hos_id").toString());
						map.put("copy_code", outMap.get("copy_code").toString());
						map.put("out_id", outMap.get("out_id").toString());
						map.put("out_no", outMap.get("out_no").toString());
						map.put("store_id", outMap.get("store_id"));
						map.put("sale_price", "0");
						map.put("sell_price", "0");
						map.put("allot_price", "0");
						//按先进先出匹配批次信息
						List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>) medCommonMapper.queryMedAffiOutDetailInvList(map));
						Double amount = Double.valueOf(String.valueOf((map.get("amount") == null ? 0 : map.get("amount"))));
						Double imme_amount = null;
						//按先进先出生成出库单
						for(Map<String, Object> fifomap : fifoList){
							Map<String, Object> invMap = defaultOutDetailValue();
							invMap.putAll(map);
							invMap.put("detail_id", medAffiOutMapper.queryMedAffiOutDetailSeq());
							if(fifomap.get("inva_date") != null && !"".equals(fifomap.get("inva_date").toString())){
								invMap.put("inva_date", DateUtil.stringToDate(fifomap.get("inva_date").toString(), "yyyy-MM-dd"));
								//invMap.put("inva_date", DateUtil.dateToString((Date)invMap.get("inva_date"), "yyyy-MM-dd"));
							}
							if(fifomap.get("disinfect_date") != null && !"".equals(fifomap.get("disinfect_date").toString())){
								invMap.put("disinfect_date", DateUtil.stringToDate(fifomap.get("disinfect_date").toString(), "yyyy-MM-dd"));
								//invMap.put("disinfect_date", DateUtil.dateToString((Date)invMap.get("disinfect_date"), "yyyy-MM-dd"));
							}
							invMap.put("batch_sn", fifomap.get("batch_sn").toString());
							//当前批次即时库存
							imme_amount = Double.valueOf(String.valueOf((fifomap.get("imme_amount") == null ? 0 : fifomap.get("imme_amount"))));
							//判断当前批号批次是否充足
							if(amount <= imme_amount){
								invMap.put("amount", amount.toString());
								//计算金额
								invMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((fifomap.get("price") == null ? 0 : fifomap.get("price")))), money_para)));
								invMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((fifomap.get("sale_price") == null ? 0 : fifomap.get("sale_price")))), money_para)));
								invMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((fifomap.get("sell_price") == null ? 0 : fifomap.get("sell_price")))), money_para)));
								invMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((fifomap.get("allot_price") == null ? 0 : fifomap.get("allot_price")))), money_para)));
								out_money_sum = out_money_sum + Float.parseFloat(invMap.get("amount_money").toString());//记录总金额
								//记录出库明细表信息
								outDetailList.add(invMap);
								amount = 0.0;
								break;
							}else{
								//取当前批号批次数量并且申请单数量响应减少
								invMap.put("amount", imme_amount.toString());		
								//计算金额
								invMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((fifomap.get("price") == null ? 0 : fifomap.get("price")))), money_para)));
								invMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((fifomap.get("sale_price") == null ? 0 : fifomap.get("sale_price")))), money_para)));
								invMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((fifomap.get("sell_price") == null ? 0 : fifomap.get("sell_price")))), money_para)));
								invMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((fifomap.get("allot_price") == null ? 0 : fifomap.get("allot_price")))), money_para)));
								out_money_sum = out_money_sum + Float.parseFloat(invMap.get("amount_money").toString());//记录总金额
								//记录出库明细表信息
								outDetailList.add(invMap);			
								amount = NumberUtil.sub(amount, imme_amount);
							}
							//当数量为0，证明已经完成先进先出操作
							if(amount == 0){
								break;
							}
						}
						if(amount > 0){
							invEnoughMsg.append(String.valueOf(map.get("inv_code"))).append(" ").append(String.valueOf(map.get("inv_name"))).append(",");
						}
					}
					
					//记录出库主表信息
					outMainList.add(defaultOutValue(outMap));
					
				}
				//如果存在盘盈数据则生成入库单
				if(inCheckList.size() > 0){
					
					Map<String, Object> inMap = new HashMap<String, Object>();
					inMap.putAll(mainMap);
					//业务类型
					inMap.put("bus_type_code", 8);
					inMap.put("table_code", "med_affi_in");
					//单号
					String in_no = medCommonService.getMedNextNo(inMap);
					if(in_no.indexOf("error") > 0){
						return in_no;
					}
					inMap.put("in_no", in_no);
					
					//单据ID
					inMap.put("in_id", medAffiInMapper.queryAffiInMainSeq());
					inMap.put("in_date", DateUtil.stringToDate(date, "yyyy-MM-dd"));
					//盘点单与入库单对应关系
					mainMap.put("in_id", inMap.get("in_id"));
					mainMap.put("in_no", inMap.get("in_no"));
					
					//明细
					for(Map<String, Object> map : inCheckList){
						map.put("group_id", inMap.get("group_id").toString());
						map.put("hos_id", inMap.get("hos_id").toString());
						map.put("copy_code", inMap.get("copy_code").toString());
						map.put("in_id", inMap.get("in_id").toString());
						map.put("in_no", inMap.get("in_no").toString());
						map.put("store_id", inMap.get("store_id").toString());
						map.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
						map.put("sale_price", "0");
						map.put("sell_price", "0");
						map.put("allot_price", "0");
						Map<String, Object> invMap = new HashMap<String, Object>();
						invMap.putAll(map);
						if(map.get("inva_date") != null && !"".equals(map.get("inva_date").toString())){
							invMap.put("inva_date", DateUtil.stringToDate(map.get("inva_date").toString(), "yyyy-MM-dd"));
							//invMap.put("inva_date", DateUtil.dateToString((Date)invMap.get("inva_date"), "yyyy-MM-dd"));
						}
						if(map.get("disinfect_date") != null && !"".equals(map.get("disinfect_date").toString())){
							invMap.put("disinfect_date", DateUtil.stringToDate(map.get("disinfect_date").toString(), "yyyy-MM-dd"));
							//invMap.put("disinfect_date", DateUtil.dateToString((Date)invMap.get("disinfect_date"), "yyyy-MM-dd"));
						}
						
						
						//如果药品批号的最大批次已经记录过就不重新从表里查询最大批次会出现重复现象
						String batchKey = invMap.get("inv_id").toString()+invMap.get("batch_no").toString();
						if(invBatchSnMap.get(batchKey) == null){
							/*自动生成批次-------begin--------*/
							//查询该批号最大批次
							batchSnMap.put("c_value", invMap.get("inv_id").toString());//药品ID
							batchSnMap.put("c_value1", invMap.get("batch_no").toString());//药品批号
							String batchSn = medCommonMapper.getMedMaxNo(batchSnMap);//最大批次
							if(batchSn == null || "".equals(batchSn) || "0".equals(batchSn)){
								invMap.put("batch_sn",  1);//批次
							}else{
								invMap.put("batch_sn",  Integer.parseInt(batchSn) + 1);//批次
							}
							/*自动生成批次-------end---------*/
							invBatchSnMap.put(batchKey, Integer.valueOf(String.valueOf(invMap.get("batch_sn"))));
						}else{
							invMap.put("batch_sn", invBatchSnMap.get(batchKey) + 1);
							invBatchSnMap.put(batchKey, invBatchSnMap.get(batchKey) + 1);
						}
						//生成个体码--如果系统参数08010高值药品自动生成条形码为是，则不管是否为个体码管理都要拆分生成个体码
						if("0".equals(invMap.get("is_per_bar").toString()) && ("0".equals(String.valueOf(invMap.get("is_highvalue"))) || "0".equals(String.valueOf(MyConfig.getSysPara("03010"))))){
							if(ChdJson.validateJSON(String.valueOf(invMap.get("bar_code")))){
								invMap.put("bar_code",  invMap.get("bar_code").toString());//个体码
							}else{
								invMap.put("bar_code", invMap.get("sn").toString());//个体码--个体码默认条形码
							}
							//记录入库明细
							inDetailList.add(invMap);
						}else{
							//根据一码一物规则自动拆分数量并生成个体码
							for(int i = 1; i <= Integer.parseInt(invMap.get("amount").toString()); i++){
								Map<String, Object> barMap = new HashMap<String, Object>();
								barMap.putAll(invMap);
								bar_code = medCommonService.getNextBar_code(bar_code);
								if(i > 1){
									barMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
								}
								//拆分数量和金额
								barMap.put("amount",  1);//数量
								if(invMap.get("num_exchange") != null){
									barMap.put("num",  Float.parseFloat(barMap.get("amount").toString())/Float.parseFloat(invMap.get("num_exchange").toString()));//包装件数
								}
								if(invMap.get("num") != null){
									barMap.put("pack_price",  Float.parseFloat(invMap.get("num").toString())*Float.parseFloat(invMap.get("price").toString()));//包装件数
								}
								barMap.put("amount_money",  Float.parseFloat(invMap.get("amount_money").toString())/Float.parseFloat(invMap.get("amount").toString()));//金额
								barMap.put("bar_code",  bar_code);//个体码
								//记录入库明细
								inDetailList.add(barMap);
							}
						}
					}//for--end
					
				
					
					//记录入库主表
					inMainList.add(inMap);
				}
				
				relaList.add(mainMap);
			}

			if(invEnoughMsg.length() > 0){
				return "{\"error\":\""+invEnoughMsg.toString()+"库存物资不足\"}";
			}
			
			//更新个体码
			if(!init_bar_code.equals(bar_code)){
				barCodeMap.put("max_no", bar_code);
				medNoOtherMapper.updateMedNoOther(barCodeMap);
			}
			
			//保存出库信息
			if(outMainList.size() > 0){
				medAffiOutMapper.addMedAffiOutMainBatch(outMainList);
				medAffiOutMapper.addMedAffiOutDetail(outDetailList);
			}
			
			//保存入库信息
			if(inMainList.size() > 0){
				medAffiInMapper.addMedAffiInMainBatch(inMainList);
				medAffiInMapper.addMedAffiInDetail(inDetailList);
			}
			//System.out.println("***********relaList:"+ChdJson.toJson(relaList));
			//保存盘点单与出入库单对应关系
			medAffiCheckRelaMapper.addBatch(relaList);
			//修改盘点单状态为3:已生成
			medAffiCheckMainMapper.updateStateBatch(entityList);
		} catch (DataAccessException e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 createInOut\"}";

		}
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	
	@Override
	public String queryMedAffiCheckMainByMedInv(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> list = (List<Map<String,Object>>)medAffiCheckMainMapper.queryMedAffiCheckMainByMedInv(entityMap);			
		return ChdJson.toJsonLower(list);	
		/*
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");		
		if (sysPage.getTotal()==-1){			
			List<Map<String,Object>> list = (List<Map<String,Object>>)medAffiCheckMainMapper.queryMedAffiCheckMainByMedInv(entityMap);			
			return ChdJson.toJsonLower(list);			
		}else{			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());			
			List<Map<String,Object>> list = (List<Map<String,Object>>)medAffiCheckMainMapper.queryMedAffiCheckMainByMedInv(entityMap, rowBounds);			
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());			
		}*/
	}

	@Override
	public MedAffiCheckMain queryMedAffiCheckMainByCode(Map<String, Object> entityMap) throws DataAccessException {
		return medAffiCheckMainMapper.queryMedAffiCheckMainByCode(entityMap);
	}

	@Override
	public String queryMedAffiCheckDetailByCheckID(Map<String, Object> entityMap) throws DataAccessException {
			
		List<Map<String,Object>> list = (List<Map<String,Object>>)medAffiCheckDetailMapper.queryMedAffiCheckDetailByCheckID(entityMap);
			
		return ChdJson.toJsonLower(list);
	}
	
	
	// 返回用用于保存的默认值
		public Map<String, Object> defaultOutDetailValue() {

			Map<String, Object> mapDetailVo = new HashMap<String, Object>();

			mapDetailVo.put("group_id", 0);
			mapDetailVo.put("hos_id", 0);
			mapDetailVo.put("copy_code", "");
			mapDetailVo.put("out_id", 0);
			mapDetailVo.put("out_no", "");
			// mapDetailVo.put("out_detail_id", "");
			mapDetailVo.put("inv_id", 0);
			mapDetailVo.put("inv_no", 0);
			mapDetailVo.put("batch_sn", 0);
			mapDetailVo.put("batch_no", "");
			mapDetailVo.put("price", 0);
			mapDetailVo.put("amount", 0);
			mapDetailVo.put("sale_price", 0);
			mapDetailVo.put("sale_money", 0);
			mapDetailVo.put("sell_price", 0);
			mapDetailVo.put("sell_money", 0);
			mapDetailVo.put("allot_price", 0);
			mapDetailVo.put("allot_money", 0);
			mapDetailVo.put("amount_money", 0);
			mapDetailVo.put("num_exchange", 0);
			mapDetailVo.put("pack_price", 0);
			mapDetailVo.put("num", 0);
			mapDetailVo.put("bar_code", "");
			mapDetailVo.put("is_per_bar", 0);
			mapDetailVo.put("sn", "");
			mapDetailVo.put("inva_date", "");
			mapDetailVo.put("disinfect_date", "");
			mapDetailVo.put("location_id", 0);
			mapDetailVo.put("note", "");
			mapDetailVo.put("pack_code", "");

			return mapDetailVo;
		}

		/**
		 * 返回用用于保存的默认值 out_id 序列 out_no 自动生成 year 年 截取入库日期 month 月 截取入库日期
		 * bus_type_code 前台选择 store_id 前台选择 分割提取 store_no 前台选择 分割提取 brief 摘要 前台提取
		 * out_date 出库日期 前台提取 dept_id 前台选择 分割提取 dept_no 前台选择 分割提取 dept_emp 前台选择
		 * company 调拨单位 前台录入
		 */
		public Map<String, Object> defaultOutValue(Map<String, Object> mapVo) {

			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", mapVo.get("group_id"));
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", mapVo.get("hos_id"));
			}

			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", mapVo.get("copy_code"));
			}

			if (mapVo.get("year") == null) {
				mapVo.put("year", "");
			}

			if (mapVo.get("month") == null) {
				mapVo.put("month", "");
			}

			if (mapVo.get("bus_type_code") == null) {
				mapVo.put("bus_type_code", "");
			}

			if (mapVo.get("store_id") == null) {
				mapVo.put("store_id", "");
			}

			if (mapVo.get("store_no") == null) {
				mapVo.put("store_no", "");
			}

			if (mapVo.get("brief") == null) {
				mapVo.put("brief", "");
			}

			if (mapVo.get("out_date") == null) {
				mapVo.put("out_date", "");
			}

			if (mapVo.get("dept_id") == null) {
				mapVo.put("dept_id", "");
			}

			if (mapVo.get("dept_no") == null) {
				mapVo.put("dept_no", "");
			}

			if (mapVo.get("dept_emp") == null) {
				mapVo.put("dept_emp", "");
			}

			if (mapVo.get("company") == null) {
				mapVo.put("company", "");
			}

			if (mapVo.get("use_code") == null) {
				mapVo.put("use_code", "");
			}

			if (mapVo.get("proj_id") == null) {
				mapVo.put("proj_id", "");
			}

			if (mapVo.get("maker") == null) {
				mapVo.put("maker", "");
			}

			if (mapVo.get("make_date") == null) {
				mapVo.put("make_date", "");
			}

			if (mapVo.get("checker") == null) {
				mapVo.put("checker", "");
			}

			if (mapVo.get("check_date") == null) {
				mapVo.put("check_date", "");
			}

			if (mapVo.get("confirmer") == null) {
				mapVo.put("confirmer", "");
			}

			if (mapVo.get("confirm_date") == null) {
				mapVo.put("confirm_date", "");
			}

			if (mapVo.get("state") == null) {
				mapVo.put("state", "1");
			}

			if (mapVo.get("is_dir") == null) {
				mapVo.put("is_dir", "0");
			}

			if (mapVo.get("his_flag") == null) {
				mapVo.put("his_flag", "");
			}

			return mapVo;
		}
		
	//map键转小写
	public Map<String, Object> toMapLower(Map<String, Object> map) {
			
		Map<String, Object> viewMap = new HashMap<String, Object>();
		for (String key : map.keySet()) {

			viewMap.put(key.toLowerCase(), map.get(key));
		}
			
		return viewMap;
	}
		
	//list<map>键转小写
	public List<Map<String, Object>> toListMapLower(List<Map<String, Object>> list) {
			
		List<Map<String, Object>> viewList = new ArrayList<Map<String, Object>>();
			
		if (list.size() > 0) {

			for (Map<String, Object> map : list) {

				Map<String, Object> viewMap = new HashMap<String, Object>();

				for (String key : map.keySet()) {

					viewMap.put(key.toLowerCase(), map.get(key));
				}

				viewList.add(viewMap);
			}
		}
		return viewList;
	}
	
	//盘点模板打印（包含主从表）
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public String queryMedAffiCheckByPrintTemlate(Map<String, Object> entityMap)throws DataAccessException {
		try{
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
					List<Map<String,Object>> map=medAffiCheckMainMapper.queryAffiCheckPrintTemlateByMainBatch(entityMap);
					List<Map<String,Object>> list = medAffiCheckMainMapper.queryAffiCheckPrintTemlateByDetail(entityMap);
					return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
			}else{
					//查询盘点单主表
					Map<String,Object> map = medAffiCheckMainMapper.queryAffiCheckPrintTemlateByMain(entityMap);
						
					//查询盘点单明细表
					List<Map<String,Object>> list = medAffiCheckMainMapper.queryAffiCheckPrintTemlateByDetail(entityMap);
					return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
						
				}
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				throw new SysException(e.getMessage());
			}
		}
	
	/**
	 * 引入仓库药品
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedAffiStoreInvDetail(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> detailList= new ArrayList<Map<String,Object>>();
		
		JSONArray json = JSONArray.parseArray((String)entityMap.get("allData"));
		Iterator it = json.iterator();
		while (it.hasNext()) {
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
				Map<String, Object> detailMap = new HashMap<String, Object>();
			
				detailMap.put("group_id", entityMap.get("group_id"));
				detailMap.put("hos_id", entityMap.get("hos_id"));
				detailMap.put("copy_code", entityMap.get("copy_code"));
				detailMap.put("store_id", entityMap.get("store_id"));
				detailMap.put("inv_id", jsonObj.get("inv_id"));
				detailMap.put("inv_no", jsonObj.get("inv_no"));
				detailMap.put("price", jsonObj.get("price"));
				detailMap.put("bar_code", jsonObj.get("bar_code"));
				detailMap.put("batch_no", jsonObj.get("batch_no"));
				detailList.add(detailMap);
			}
		}
		
		List<Map<String, Object>> list= medAffiCheckMainMapper.queryMedAffiStoreInvDetail(detailList);
		return ChdJson.toJsonLower(list);
		
	}
}
