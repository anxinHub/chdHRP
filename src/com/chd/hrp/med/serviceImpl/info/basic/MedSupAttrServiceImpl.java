package com.chd.hrp.med.serviceImpl.info.basic;

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
import com.chd.hrp.ass.entity.dict.AssSupAttr;
import com.chd.hrp.mat.dao.info.basic.HosSupBankMapper;
import com.chd.hrp.med.dao.info.basic.MedSupAttrMapper;
import com.chd.hrp.med.service.info.basic.MedSupAttrService;
import com.chd.hrp.sys.dao.SupDictMapper;
import com.chd.hrp.sys.dao.SupMapper;
import com.chd.hrp.sys.dao.SupTypeMapper;
import com.chd.hrp.sys.entity.SupType;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.chd.hrp.sys.serviceImpl.SupServiceImpl;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050113 供应商附属表
 * @Table:
 * ASS_Sup_ATTR
 * @Author: linuxxu
 * @email:  linuxxu@s-chd.com
 * @Version: 1.0
 */

@Service("medSupAttrService")
public class MedSupAttrServiceImpl implements MedSupAttrService{

	private static Logger logger = Logger.getLogger(MedSupAttrServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medSupAttrMapper")
	private final MedSupAttrMapper medSupAttrMapper = null;
	@Resource(name = "hosSupBankMapper")
	private final HosSupBankMapper hosSupBankMapper = null;
	
	@Resource(name = "supMapper")
	private final SupMapper supMapper = null;
	
	@Resource(name = "supDictMapper")
	private final SupDictMapper supDictMapper = null;
	
	@Resource(name = "supTypeMapper")
	private final SupTypeMapper supTypeMapper = null;
	
	@Resource(name = "supService")
	private final SupServiceImpl supService = null;
	
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
	/**
	 * @Description 
	 * 添加050113 供应商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String addMedSupAttr(Map<String, Object> entityMap)throws DataAccessException {
		
		String mod_code = String.valueOf(entityMap.get("mod_code"));//记录前台传的 mod_code 的值（中间有变动）

		if (entityMap.get("group_id") == null) {
			
			entityMap.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (entityMap.get("hos_id") == null) {
			
			entityMap.put("hos_id", SessionManager.getHosId());
			
		}

		Map<String, Map<String, Object>> mv = sysBaseService.getHosRulesList(entityMap);

		String medSupAttrJson= null;
		if (null != mv) {
			if (mv.get("HOS_SUP").get("is_auto").toString().equals("1")) {
				// 台州单独自动生成编码规则
				medSupAttrJson = supService.addSupTz(entityMap);
			} else {
				medSupAttrJson = supService.addSup(entityMap);
			}
		} else {
			medSupAttrJson = supService.addSup(entityMap);
		}
		if(JSONObject.parseObject(medSupAttrJson).get("error") != null ){
			return medSupAttrJson;
		}else{
			//查询 新增供应商 的sup_id
			Long sup_id = supMapper.querySupCurrentSequence();
			
			entityMap.put("sup_id",sup_id);
		}
		
		entityMap.put("mod_code",mod_code);

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
					bankMap.put("sup_id", entityMap.get("sup_id"));
					bankMap.put("bank_no", jsonObj.get("bank_no"));
					bankMap.put("bank_name", jsonObj.get("bank_name"));
					bankMap.put("bank_area_number", jsonObj.get("bank_area_number"));
					bankMap.put("bank_code", jsonObj.get("bank_code"));
					bankMap.put("bank_area_name", jsonObj.get("bank_area_name"));
					bankMap.put("bank_full_name", jsonObj.get("bank_full_name"));
					bankMap.put("is_default", jsonObj.get("is_default"));
					bankList.add(bankMap);
				}
			}
		}
		/*处理供应商信息-------------------end-------------*/
		//获取对象050113 供应商附属表
		//AssSupAttr assSupAttr = queryMedSupAttrByCode(entityMap);
		int isExists = medSupAttrMapper.querySupAttrExist(entityMap);
		
		try {
			if (isExists > 0) {
				return "{\"error\":\"供应商附属表信息已存在,请重新添加.\",\"state\":\"true\"}";
			}else{
				//新增
				medSupAttrMapper.addMedSupAttr(entityMap);
				//添加 供应商归属系统表 
				medSupAttrMapper.addHosSupMod(entityMap);
				//插入供应商银行信息
				if(bankList.size() > 0){
					hosSupBankMapper.addHosSupBank(bankList);
				}
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		
		}
		catch (Exception e) {
		
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
		
		}	
	}

	/**
	 * @Description 
	 * 批量添加050113 供应商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMedSupAttr(List<Map<String, Object>> entityList)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
				
			medSupAttrMapper.addBatchMedSupAttr(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	
		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchAssSupAttr\"}";
	
		}
	}

	/**
	 * @Description 
	 * 更新050113 供应商附属表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedSupAttr(Map<String, Object> entityMap) throws DataAccessException {
		
		String mod_code = String.valueOf(entityMap.get("mod_code"));//记录前台传的 mod_code 的值（中间有变动）
		
		String medSupAttrJson = "";

		if (entityMap.get("group_id") == null) {
			
			entityMap.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (entityMap.get("hos_id") == null) {
			
			entityMap.put("hos_id", SessionManager.getHosId());
			
		}

		Map<String, Map<String, Object>> mv = sysBaseService.getHosRulesList(entityMap);
		if (null != mv) {
			if (mv.get("HOS_SUP").get("is_auto").toString().equals("1")) {
				// 台州单独自动生成编码规则
				medSupAttrJson = supService.updateSupTz(entityMap);
			} else {
				medSupAttrJson = supService.updateSup(entityMap);
			}
		} else {
			medSupAttrJson = supService.updateSup(entityMap);
		}
		if(JSONObject.parseObject(medSupAttrJson).get("error") != null ){
			return medSupAttrJson;
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
					bankMap.put("sup_id", entityMap.get("sup_id"));
					bankMap.put("bank_no", jsonObj.get("bank_no"));
					bankMap.put("bank_name", jsonObj.get("bank_name"));
					bankMap.put("bank_area_number", jsonObj.get("bank_area_number"));
					bankMap.put("bank_code", jsonObj.get("bank_code"));
					bankMap.put("bank_area_name", jsonObj.get("bank_area_name"));
					bankMap.put("bank_full_name", jsonObj.get("bank_full_name"));
					bankMap.put("is_default", jsonObj.get("is_default"));
					bankList.add(bankMap);
				}
			}
		}
		/*处理供应商信息-------------------end-------------*/
		//获取对象050113 供应商附属表
		AssSupAttr assSupAttr = queryMedSupAttrByCode(entityMap);
		// 查询 供应商 系统模块对应关系是否存在
		int count = medSupAttrMapper.queryHosSupModExist(entityMap);
		try {
			if (assSupAttr != null && assSupAttr.getIs_flag()!=null) {
				//修改 
				medSupAttrMapper.updateMedSupAttr(entityMap);
				if(count > 0){
					medSupAttrMapper.updateHosSupMod(entityMap);
				}else{
					medSupAttrMapper.addHosSupMod(entityMap);
				}
				//先清空后插入供应商银行信息
				hosSupBankMapper.deleteHosSupBank(entityMap);
				if(bankList.size() > 0){
					hosSupBankMapper.addHosSupBank(bankList);
				}
			}else{
				//新增
				medSupAttrMapper.addMedSupAttr(entityMap);
				//插入供应商银行信息
				if(count > 0){
					medSupAttrMapper.updateHosSupMod(entityMap);
				}else{
					medSupAttrMapper.addHosSupMod(entityMap);
				}
				//先清空后插入供应商银行信息
				hosSupBankMapper.deleteHosSupBank(entityMap);
				if(bankList.size() > 0){
					hosSupBankMapper.addHosSupBank(bankList);
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
	 * 批量更新050113 供应商附属表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMedSupAttr(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			
			medSupAttrMapper.updateBatchMedSupAttr(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"更新失败 请联系管理员! 方法 updateBatchAssSupAttr\"}";

			}	
	}

	/**
	 * @Description 
	 * 删除050113 供应商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String deleteMedSupAttr(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			int state = medSupAttrMapper.deleteMedSupAttr(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteAssSupAttr\"}";

		}
	}

	/**
	 * @Description 
	 * 批量删除050113 供应商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMedSupAttr(List<Map<String, Object>> listVo)
			throws DataAccessException {
		try {
			
			//删除 供应商与系统模块对应关系
			medSupAttrMapper.deleteBatchHosSupMod(listVo);
			//删除 供应商银行信息
			hosSupBankMapper.deleteHosSupBankBatch(listVo);
			
			medSupAttrMapper.deleteBatchMedSupAttr(listVo);
			//bell  不能删除主表数据
			//删除 供应商变更表数据
			//supDictMapper.deleteBatchSupDict(listVo);
			
			//删除 供应商主表数据
			//supMapper.deleteBatchSup(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");

		}
	}

	/**
	 * @Description 
	 * 查询结果集050113 供应商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedSupAttr(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssSupAttr> list = medSupAttrMapper.queryMedSupAttr(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssSupAttr> list = medSupAttrMapper.queryMedSupAttr(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	/**
	 * @Description 
	 * 查询对象050113 供应商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public AssSupAttr queryMedSupAttrByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		return medSupAttrMapper.queryMedSupAttrByCode(entityMap);
	}

	/**
	 * @Description 
	 * 查询供应商银行信息表<BR> 
	 * @param  entityMap
	 * @return List<HosSupBank>
	 * @throws DataAccessException
	*/
	@Override
	public String queryHosSupBank(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return ChdJson.toJson(hosSupBankMapper.queryHosSupBank(entityMap));
	}
    
	/**
	 * @Description 
	 * 添加银行信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addHosSupBank(Map<String,Object> entityMap)throws DataAccessException{
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
						bankMap.put("sup_id", entityMap.get("sup_id"));
						bankMap.put("bank_no", jsonObj.get("bank_no"));
						bankMap.put("bank_name", jsonObj.get("bank_name"));
						bankMap.put("bank_area_number", jsonObj.get("bank_area_number"));
						bankMap.put("bank_code", jsonObj.get("bank_code"));
						bankMap.put("bank_area_name", jsonObj.get("bank_area_name"));
						bankMap.put("bank_full_name", jsonObj.get("bank_full_name"));
						bankMap.put("is_default", jsonObj.get("is_default"));
						bankList.add(bankMap);
					}
				}
			}else{
				return "{\"error\":\"请录入银行账户信息！\",\"state\":\"false\"}";
			}
			/*处理银行信息-------------------end-------------*/
			
			hosSupBankMapper.deleteHosSupBank(entityMap);
			hosSupBankMapper.addHosSupBank(bankList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedInv\"}";
		}
	}
	/**
	 * 供应商类型树  数据查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryHosSupTypeByMenu(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<SupType> groupDictList = supTypeMapper.querySupType(entityMap);
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
	 * 根据 供应商编码 查询 供应商ID(导入使用)
	 */
	@Override
	public Long querySupIdByCode(Map<String, Object> mapVo) throws DataAccessException {
		return medSupAttrMapper.querySupIdByCode(mapVo);
	}

	@Override
	public Long queryDeptIdByCode(Map<String, Object> mapVo) throws DataAccessException {
		return medSupAttrMapper.queryDeptIdByCode(mapVo);
	}

	/**
	 * 根据供应商类别编码 查询该供应商类别是否存在
	 */
	@Override
	public int querySupTypeExist(Map<String, Object> mapVo)	throws DataAccessException {
		return medSupAttrMapper.querySupTypeExist(mapVo);
	}
	/**
	 * 查询 所属系统模块编码是否存在 
	 */
	@Override
	public int queryModExist(Map<String, Object> mapVo)	throws DataAccessException {
		return medSupAttrMapper.queryModExist(mapVo);
	}

}
