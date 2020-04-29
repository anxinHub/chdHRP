
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.serviceImpl.dict;

import java.util.ArrayList;
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
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.dict.AssFacDictMapper;
import com.chd.hrp.ass.dao.dict.AssFacMapper;
import com.chd.hrp.ass.dao.dict.AssFacTypeMapper;
import com.chd.hrp.ass.dao.dict.AssHosFacBankMapper;
import com.chd.hrp.ass.dao.dict.AssMatFacAttrMapper;
import com.chd.hrp.ass.entity.dict.AssFacAttr;
import com.chd.hrp.ass.entity.dict.AssFacType;
import com.chd.hrp.ass.service.dict.AssFacService;
import com.chd.hrp.ass.service.dict.AssMatFacAttrService;
import com.chd.hrp.sys.entity.FacType;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050115 生产厂商附属表
 * @Table:
 * ASS_FAC_ATTR
 * @Author: linuxxu
 * @email:  linuxxu@s-chd.com
 * @Version: 1.0
 */
 


@Service("assMatFacAttrService")
public class AssMatFacAttrServiceImpl implements AssMatFacAttrService {

	private static Logger logger = Logger.getLogger(AssMatFacAttrServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assMatFacAttrMapper")
	private final AssMatFacAttrMapper assMatFacAttrMapper = null;
	
	@Resource(name = "assHosFacBankMapper")
	private final AssHosFacBankMapper hosFacBankMapper = null;
    
	
	@Resource(name = "AssFacService")
	private final AssFacService facService = null;
	
	@Resource(name = "assFacMapper")
	private final AssFacMapper facMapper = null;
	
	@Resource(name = "assFacDictMapper")
	private final AssFacDictMapper facDictMapper = null;
	
	
	@Resource(name = "assFacTypeMapper")
	private final AssFacTypeMapper facTypeMapper = null;

	/**
	 * @Description 
	 * 添加050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatFacAttr(Map<String,Object> entityMap)throws DataAccessException{
		
		String mod_code = String.valueOf(entityMap.get("mod_code"));//记录前台传的 mod_code 的值（中间有变动）
		String supJson = "";

		Map<String, Map<String, Object>> mv = (Map<String, Map<String, Object>>) SessionManager.queryHosRulesMap();
		if (null != mv) {
			if (mv.get("HOS_FAC").get("is_auto").toString().equals("1")) {
				// 台州单独自动生成编码规则
				supJson = facService.addFacTz(entityMap);
			} else {
				supJson = facService.addFac(entityMap);
			}
		} else {
			supJson = facService.addFac(entityMap);
		}
		if(JSONObject.parseObject(supJson).get("error") != null ){
			return supJson;
		}else{
			//查询 新增供应商 的sup_id
			Long fac_id = facMapper.queryCurrentSequence();
			
			entityMap.put("fac_id",fac_id);
		}
		
		entityMap.put("mod_code",mod_code);
		/*处理银行信息-------------------begin-----------*/
		List<Map<String,Object>> bankList = new ArrayList<Map<String,Object>>();
		if(entityMap.get("bankData") != null && !"[]".equals(String.valueOf(entityMap.get("bankData")))){
			JSONArray json = JSONArray.parseArray((String)entityMap.get("bankData"));
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				if( ChdJson.validateJSON(String.valueOf(jsonObj.get("bank_no")))){
					Map<String,Object> bankMap = new HashMap<String,Object>();
					bankMap.put("group_id", entityMap.get("group_id"));
					bankMap.put("hos_id", entityMap.get("hos_id"));
					bankMap.put("fac_id", entityMap.get("fac_id"));
					bankMap.put("bank_no", jsonObj.get("bank_no"));
					bankMap.put("bank_name", jsonObj.get("bank_name"));
					bankList.add(bankMap);
				}
			}
		}
		/*处理银行信息-------------------end-------------*/
		//获取对象050115 生产厂商附属表
		List<AssFacAttr> list = assMatFacAttrMapper.queryMatFacAttrByCodeExists(entityMap);

		try {
			if (list.size()>0) {
				//新增
				assMatFacAttrMapper.addMatFacAttr(entityMap);
				
				//添加 生产厂商归属系统表 
				assMatFacAttrMapper.addHosFacMod(entityMap);
				//添加 生产厂商银行信息
				if(bankList.size() > 0){
					hosFacBankMapper.addHosFacBank(bankList);
				}
			}else{
				return "{\"error\":\"生产厂商附属表信息已存在,请重新添加.\",\"state\":\"true\"}";
			}
		    return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());


		}
		
	}
	/**
	 * @Description 
	 * 批量添加050115 生产厂商附属表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMatFacAttr(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assMatFacAttrMapper.addBatchMatFacAttr(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMatFacAttr(Map<String,Object> entityMap)throws DataAccessException{
		
		String mod_code = String.valueOf(entityMap.get("mod_code"));//记录前台传的 mod_code 的值（中间有变动）
		String supJson = "";

		Map<String, Map<String, Object>> mv = (Map<String, Map<String, Object>>) SessionManager.queryHosRulesMap();
		if (null != mv) {
			if (mv.get("HOS_FAC").get("is_auto").toString().equals("1")) {
				// 台州单独自动生成编码规则
				supJson = facService.updateFacTz(entityMap);
			} else {
				supJson = facService.updateFac(entityMap);
			}
		} else {
			supJson = facService.updateFac(entityMap);
		}
		if(JSONObject.parseObject(supJson).get("error") != null ){
			return supJson;
		}
		entityMap.put("mod_code",mod_code);
		
		/*处理银行信息-------------------begin-----------*/
		List<Map<String,Object>> bankList = new ArrayList<Map<String,Object>>();
		if(entityMap.get("bankData") != null && !"[]".equals(String.valueOf(entityMap.get("bankData")))){
			JSONArray json = JSONArray.parseArray((String)entityMap.get("bankData"));
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				if( ChdJson.validateJSON(String.valueOf(jsonObj.get("bank_no")))){
					Map<String,Object> bankMap = new HashMap<String,Object>();
					bankMap.put("group_id", entityMap.get("group_id"));
					bankMap.put("hos_id", entityMap.get("hos_id"));
					bankMap.put("fac_id", entityMap.get("fac_id"));
					bankMap.put("bank_no", jsonObj.get("bank_no"));
					bankMap.put("bank_name", jsonObj.get("bank_name"));
					bankList.add(bankMap);
				}
			}
		}
		/*处理供应商信息-------------------end-------------*/
		
		List<AssFacAttr> list = assMatFacAttrMapper.queryMatFacAttrByCodeExists(entityMap);
		int count = assMatFacAttrMapper.queryHosFacModExist(entityMap);
		try {
			if (list.size() == 0) {
				assMatFacAttrMapper.addMatFacAttr(entityMap);
				if(count > 0){
					assMatFacAttrMapper.updateHosFacMod(entityMap);
				}else{
					assMatFacAttrMapper.addHosFacMod(entityMap);
				}
				hosFacBankMapper.deleteHosFacBank(entityMap);
				if(bankList.size() > 0){
					hosFacBankMapper.addHosFacBank(bankList);
				}
			}
			else{
				assMatFacAttrMapper.updateMatFacAttr(entityMap);
				if(count > 0){
					assMatFacAttrMapper.updateHosFacMod(entityMap);
				}else{
					assMatFacAttrMapper.addHosFacMod(entityMap);
				}
				hosFacBankMapper.deleteHosFacBank(entityMap);
				if(bankList.size() > 0){
					hosFacBankMapper.addHosFacBank(bankList);
				}
			}
		    return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());


		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050115 生产厂商附属表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMatFacAttr(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assMatFacAttrMapper.updateBatchMatFacAttr(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());


		}	
		
	}
	/**
	 * @Description 
	 * 删除050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatFacAttr(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assMatFacAttrMapper.deleteMatFacAttr(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());


		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050115 生产厂商附属表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMatFacAttr(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			//删除 生产厂商 与系统模块对应关系
			assMatFacAttrMapper.deleteBatchHosFacMod(entityList);
			
			hosFacBankMapper.deleteHosFacBankBatch(entityList);
			
			assMatFacAttrMapper.deleteBatchMatFacAttr(entityList);
			//删除 供应商变更表数据
			facDictMapper.deleteBatchFacDict(entityList);
			
			//删除 供应商主表数据
			facMapper.deleteBatchFac(entityList);
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());


		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatFacAttr(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssFacAttr> list = assMatFacAttrMapper.queryMatFacAttr(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssFacAttr> list = assMatFacAttrMapper.queryMatFacAttr(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * 生产厂商分类 树 数据查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryHosFacTypeByMenu(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<AssFacType> groupDictList = facTypeMapper.queryFacType(entityMap);
		if (groupDictList.size()>0) {
			int row = 0;
			for (int i = 0 ; i<groupDictList.size();i++) {
				if (row == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				row++;
				
				for(int j = 0; j<groupDictList.size(); j++ ){
					if(groupDictList.get(i).getType_code().startsWith(groupDictList.get(j).getType_code()) 
							&& i!=j){
						jsonResult.append("pId:'"+groupDictList.get(j).getType_code() +"',");
					}
				}
				jsonResult.append("id:'" + groupDictList.get(i).getType_code() + "',");
				jsonResult.append("group_id:'" + groupDictList.get(i).getGroup_id() + "',");
				jsonResult.append("hos_id:'" + groupDictList.get(i).getHos_id() + "',");
				jsonResult.append("name:'"+groupDictList.get(i).getType_name()+ "',");
				jsonResult.append("}");
			}
		}
		jsonResult.append("]}");
		return jsonResult.toString();
	}
	
	/**
	 * @Description 
	 * 获取对象050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssFacAttr queryMatFacAttrByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assMatFacAttrMapper.queryMatFacAttrByCode(entityMap);
	}
	
	
	
	/**
	 * @Description 
	 * 生成生产厂商050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String init(Map<String, Object> entityMap) throws DataAccessException {
		
		
		
		try {
			
			int state = assMatFacAttrMapper.init(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());


		}
	}


	/**
	 * @Description 
	 * 查询供应商银行信息表<BR> 
	 * @param  entityMap
	 * @return List<HosSupBank>
	 * @throws DataAccessException
	*/
	@Override
	public String queryHosFacBank(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return ChdJson.toJson(hosFacBankMapper.queryHosFacBank(entityMap));
	}
    
	/**
	 * @Description 
	 * 添加银行信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addHosFacBank(Map<String,Object> entityMap)throws DataAccessException{
		try{
			/*处理银行信息-------------------begin-----------*/
			List<Map<String,Object>> bankList = new ArrayList<Map<String,Object>>();
			if(entityMap.get("bankData") != null && !"[]".equals(String.valueOf(entityMap.get("bankData")))){
				JSONArray json = JSONArray.parseArray((String)entityMap.get("bankData"));
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( ChdJson.validateJSON(String.valueOf(jsonObj.get("bank_no")))){
						Map<String,Object> bankMap = new HashMap<String,Object>();
						bankMap.put("group_id", entityMap.get("group_id"));
						bankMap.put("hos_id", entityMap.get("hos_id"));
						bankMap.put("fac_id", entityMap.get("fac_id"));
						bankMap.put("bank_no", jsonObj.get("bank_no"));
						bankMap.put("bank_name", jsonObj.get("bank_name"));
						bankList.add(bankMap);
					}
				}
			}else{
				return "{\"error\":\"请录入银行账户信息！\",\"state\":\"false\"}";
			}
			/*处理银行信息-------------------end-------------*/
			
			hosFacBankMapper.deleteHosFacBank(entityMap);
			hosFacBankMapper.addHosFacBank(bankList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	/**
	 * 根据 生产厂商编码 查询 生产厂ID
	 */
	@Override
	public Long queryFacIdByCode(Map<String, Object> mapVo)	throws DataAccessException {
		return assMatFacAttrMapper.queryFacIdByCode(mapVo);
	}
	/**
	 * 查询 生产厂商名称是否存在 
	 */
	@Override
	public int queryFacExists(Map<String, Object> mapVo) throws DataAccessException {
		return assMatFacAttrMapper.queryFacExists(mapVo);
	}
	/**
	 * 查询 生产厂商分类 编码是否存在 
	 */
	@Override
	public int queryFacTypeExist(Map<String, Object> mapVo)	throws DataAccessException {
		return assMatFacAttrMapper.queryFacTypeExist(mapVo);
	}
	/**
	 * 查询 所属系统模块编码是否存在
	 */
	@Override
	public int queryModExist(Map<String, Object> mapVo) throws DataAccessException {
		return assMatFacAttrMapper.queryModExist(mapVo);
	}
}
