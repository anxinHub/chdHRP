
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.med.serviceImpl.info.basic;

import java.util.*;

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
import com.chd.hrp.ass.entity.dict.AssFacAttr;
import com.chd.hrp.mat.dao.info.basic.HosFacBankMapper;
import com.chd.hrp.med.dao.info.basic.MedFacAttrMapper;
import com.chd.hrp.med.service.info.basic.MedFacAttrService;
import com.chd.hrp.sys.dao.FacDictMapper;
import com.chd.hrp.sys.dao.FacMapper;
import com.chd.hrp.sys.dao.FacTypeMapper;
import com.chd.hrp.sys.entity.FacType;
import com.chd.hrp.sys.service.FacService;
import com.chd.hrp.sys.service.base.SysBaseService;
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
 


@Service("medFacAttrService")
public class MedFacAttrServiceImpl implements MedFacAttrService {

	private static Logger logger = Logger.getLogger(MedFacAttrServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medFacAttrMapper")
	private final MedFacAttrMapper medFacAttrMapper = null;
	@Resource(name = "hosFacBankMapper")
	private final HosFacBankMapper hosFacBankMapper = null;
	
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
    
	
	@Resource(name = "facService")
	private final FacService facService = null;
	
	@Resource(name = "facMapper")
	private final FacMapper facMapper = null;
	
	@Resource(name = "facDictMapper")
	private final FacDictMapper facDictMapper = null;
	
	
	@Resource(name = "facTypeMapper")
	private final FacTypeMapper facTypeMapper = null;

	/**
	 * @Description 
	 * 添加050115 生产厂商附属表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedFacAttr(Map<String,Object> entityMap)throws DataAccessException{
		
		String mod_code = String.valueOf(entityMap.get("mod_code"));//记录前台传的 mod_code 的值（中间有变动）
		String supJson = "";

		Map<String, Map<String, Object>> mv = sysBaseService.getHosRulesList(entityMap);
		if (null != mv && mv.get("HOS_FAC").get("is_auto").toString().equals("1")) {
				// 台州单独自动生成编码规则
			supJson = facService.addFacTz(entityMap);
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
		//AssFacAttr assFacAttr = queryMedFacAttrByCode(entityMap);

		int isExists = medFacAttrMapper.queryFacAttrExist(entityMap);
		try {
			if (isExists>0) {
				return "{\"error\":\"生产厂商附属表信息已存在,请重新添加.\",\"state\":\"true\"}";
			}else{
				//新增
				medFacAttrMapper.addMedFacAttr(entityMap);
				
				/*//添加 生产厂商归属系统表 
				matFacAttrMapper.addHosFacMod(entityMap);*/
				//添加 生产厂商银行信息
				if(bankList.size() > 0){
					hosFacBankMapper.addHosFacBank(bankList);
				}
				
			}
		    return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");

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
	public String addBatchMedFacAttr(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medFacAttrMapper.addBatchMedFacAttr(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchAssFacAttr\"}";

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
	public String updateMedFacAttr(Map<String,Object> entityMap)throws DataAccessException{
		
		String mod_code = String.valueOf(entityMap.get("mod_code"));//记录前台传的 mod_code 的值（中间有变动）
		String supJson = "";

		Map<String, Map<String, Object>> mv = sysBaseService.getHosRulesList(entityMap);
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
					bankMap.put("is_stop", "0");
					bankList.add(bankMap);
				}
			}
		}
		/*处理供应商信息-------------------end-------------*/
		
		AssFacAttr assFacAttr = queryMedFacAttrByCode(entityMap);
		//int count = medFacAttrMapper.queryHosFacModExist(entityMap);
		int count = medFacAttrMapper.queryFacAttrExist(entityMap);
		try {
			if (count < 1) {
				medFacAttrMapper.addMedFacAttr(entityMap);
				/*if(count > 0){
					medFacAttrMapper.updateHosFacMod(entityMap);
				}else{
					medFacAttrMapper.addHosFacMod(entityMap);
				}*/
				hosFacBankMapper.deleteHosFacBank(entityMap);
				if(bankList.size() > 0){
					hosFacBankMapper.addHosFacBank(bankList);
				}
			}else{
				medFacAttrMapper.updateMedFacAttr(entityMap);
				/*if(count > 0){
					medFacAttrMapper.updateHosFacMod(entityMap);
				}else{
					medFacAttrMapper.addHosFacMod(entityMap);
				}*/
				hosFacBankMapper.deleteHosFacBank(entityMap);
				if(bankList.size() > 0){
					hosFacBankMapper.addHosFacBank(bankList);
				}
			}
		    return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");

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
	public String updateBatchMedFacAttr(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medFacAttrMapper.updateBatchMedFacAttr(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchAssFacAttr\"}";

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
    public String deleteMedFacAttr(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medFacAttrMapper.deleteMedFacAttr(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteAssFacAttr\"}";

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
	public String deleteBatchMedFacAttr(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			//删除 生产厂商 与系统模块对应关系
			medFacAttrMapper.deleteBatchHosFacMod(entityList);
			
			hosFacBankMapper.deleteHosFacBankBatch(entityList);
			
			medFacAttrMapper.deleteBatchMedFacAttr(entityList);
			//删除 供应商变更表数据
			facDictMapper.deleteBatchFacDict(entityList);
			
			//删除 供应商主表数据
			facMapper.deleteBatchFac(entityList);
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");

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
	public String queryMedFacAttr(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssFacAttr> list = medFacAttrMapper.queryMedFacAttr(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssFacAttr> list = medFacAttrMapper.queryMedFacAttr(entityMap, rowBounds);
			
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
		List<FacType> groupDictList = facTypeMapper.queryFacType(entityMap);
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
	public AssFacAttr queryMedFacAttrByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medFacAttrMapper.queryMedFacAttrByCode(entityMap);
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
			
			int state = medFacAttrMapper.init(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 init\"}";

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

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addHosFacBank\"}";
		}
	}
	/**
	 * 根据 生产厂商编码 查询 生产厂ID
	 */
	@Override
	public Long queryFacIdByCode(Map<String, Object> mapVo)	throws DataAccessException {
		return medFacAttrMapper.queryFacIdByCode(mapVo);
	}
	/**
	 * 查询 生产厂商名称是否存在 
	 */
	@Override
	public int queryFacExists(Map<String, Object> mapVo) throws DataAccessException {
		return medFacAttrMapper.queryFacExists(mapVo);
	}
	/**
	 * 查询 生产厂商分类 编码是否存在 
	 */
	@Override
	public int queryFacTypeExist(Map<String, Object> mapVo)	throws DataAccessException {
		return medFacAttrMapper.queryFacTypeExist(mapVo);
	}
	/**
	 * 查询 所属系统模块编码是否存在
	 */
	@Override
	public int queryModExist(Map<String, Object> mapVo) throws DataAccessException {
		return medFacAttrMapper.queryModExist(mapVo);
	}
}
