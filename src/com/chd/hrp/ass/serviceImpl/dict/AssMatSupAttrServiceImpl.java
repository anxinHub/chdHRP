package com.chd.hrp.ass.serviceImpl.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.json.Json;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.dict.AssHosSupBankMapper;
import com.chd.hrp.ass.dao.dict.AssMatSupAttrMapper;
import com.chd.hrp.ass.dao.dict.AssSupDictMapper;
import com.chd.hrp.ass.dao.dict.AssSupMapper;
import com.chd.hrp.ass.dao.dict.AssSupTypeMapper;
import com.chd.hrp.ass.entity.dict.AssSupAttr;
import com.chd.hrp.ass.entity.dict.AssSupType;
import com.chd.hrp.ass.service.dict.AssMatSupAttrService;
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

@Service("assMatSupAttrService")
public class AssMatSupAttrServiceImpl implements AssMatSupAttrService{

	private static Logger logger = Logger.getLogger(AssMatSupAttrServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assMatSupAttrMapper")
	private final AssMatSupAttrMapper matSupAttrMapper = null;
	@Resource(name = "assHosSupBankMapper")
	private final AssHosSupBankMapper hosSupBankMapper = null;
	
	@Resource(name = "assSupMapper")
	private final AssSupMapper supMapper = null;
	
	@Resource(name = "assSupDictMapper")
	private final AssSupDictMapper supDictMapper = null;
	
	@Resource(name = "assSupTypeMapper")
	private final AssSupTypeMapper supTypeMapper = null;
	
	@Resource(name = "supService")
	private final SupServiceImpl supService = null;
	
	/**
	 * @Description 
	 * 添加050113 供应商附属表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String addMatSupAttr(Map<String, Object> entityMap)throws DataAccessException {
		
		String mod_code = String.valueOf(entityMap.get("mod_code"));//记录前台传的 mod_code 的值（中间有变动）
		Map<String, Map<String, Object>> mv = (Map<String, Map<String, Object>>) SessionManager.queryHosRulesMap();
		String matSupAttrJson= null;
		if (null != mv) {
			if (mv.get("HOS_SUP").get("is_auto").toString().equals("1")) {
				// 台州单独自动生成编码规则
				matSupAttrJson = supService.addSupTz(entityMap);
			} else {
				matSupAttrJson = supService.addSup(entityMap);
			}
		} else {
			matSupAttrJson = supService.addSup(entityMap);
		}
		if(JSONObject.parseObject(matSupAttrJson).get("error") != null ){
			return matSupAttrJson;
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
		//AssSupAttr assSupAttr = queryMatSupAttrByCode(entityMap);
		int isExists = matSupAttrMapper.querySupAttrExist(entityMap);
		
		try {
			if (isExists > 0) {
				return "{\"error\":\"供应商附属表信息已存在,请重新添加.\",\"state\":\"true\"}";
			}else{
				//新增
				matSupAttrMapper.addMatSupAttr(entityMap);
				//添加 供应商归属系统表 
				matSupAttrMapper.addHosSupMod(entityMap);
				//插入供应商银行信息
				if(bankList.size() > 0){
					hosSupBankMapper.addHosSupBank(bankList);
				}
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		
		}
		catch (Exception e) {
		
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		
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
	public String addBatchMatSupAttr(List<Map<String, Object>> entityList)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
				
			matSupAttrMapper.addBatchMatSupAttr(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	
		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
	
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
	public String updateMatSupAttr(Map<String, Object> entityMap) throws DataAccessException {
		
		String mod_code = String.valueOf(entityMap.get("mod_code"));//记录前台传的 mod_code 的值（中间有变动）
		
		String matSupAttrJson = "";

		Map<String, Map<String, Object>> mv = (Map<String, Map<String, Object>>) SessionManager.queryHosRulesMap();

		if (null != mv) {
			if (mv.get("HOS_SUP").get("is_auto").toString().equals("1")) {
				// 台州单独自动生成编码规则
				matSupAttrJson = supService.updateSupTz(entityMap);
			} else {
				matSupAttrJson = supService.updateSup(entityMap);
			}
		} else {
			matSupAttrJson = supService.updateSup(entityMap);
		}
		if(JSONObject.parseObject(matSupAttrJson).get("error") != null ){
			return matSupAttrJson;
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
		AssSupAttr assSupAttr = queryMatSupAttrByCode(entityMap);
		// 查询 供应商 系统模块对应关系是否存在
		int count = matSupAttrMapper.queryHosSupModExist(entityMap);
		try {
			if (assSupAttr != null && assSupAttr.getIs_flag()!=null) {
				//修改 
				matSupAttrMapper.updateMatSupAttr(entityMap);
				if(count > 0){
					matSupAttrMapper.updateHosSupMod(entityMap);
				}else{
					matSupAttrMapper.addHosSupMod(entityMap);
				}
				//先清空后插入供应商银行信息
				hosSupBankMapper.deleteHosSupBank(entityMap);
				if(bankList.size() > 0){
					hosSupBankMapper.addHosSupBank(bankList);
				}
			}else{
				//新增
				matSupAttrMapper.addMatSupAttr(entityMap);
				//插入供应商银行信息
				if(count > 0){
					matSupAttrMapper.updateHosSupMod(entityMap);
				}else{
					matSupAttrMapper.addHosSupMod(entityMap);
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

			throw new SysException(e.getMessage());
		
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
	public String updateBatchMatSupAttr(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			
			matSupAttrMapper.updateBatchMatSupAttr(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

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
	public String deleteMatSupAttr(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			int state = matSupAttrMapper.deleteMatSupAttr(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

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
	public String deleteBatchMatSupAttr(List<Map<String, Object>> listVo)
			throws DataAccessException {
		try {
			
			//删除 供应商与系统模块对应关系
			matSupAttrMapper.deleteBatchHosSupMod(listVo);
			//删除 供应商银行信息
			hosSupBankMapper.deleteHosSupBankBatch(listVo);
			
			matSupAttrMapper.deleteBatchMatSupAttr(listVo);
			//bell  不能删除主表数据
			//删除 供应商变更表数据
			supDictMapper.deleteBatchSupDict(listVo);
			
			//删除 供应商主表数据
			supMapper.deleteBatchSup(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

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
	public String queryMatSupAttr(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<AssSupAttr> list = matSupAttrMapper.queryMatSupAttr(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssSupAttr> list = matSupAttrMapper.queryMatSupAttr(entityMap, rowBounds);
			
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
	public AssSupAttr queryMatSupAttrByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		return matSupAttrMapper.queryMatSupAttrByCode(entityMap);
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

			throw new SysException(e.getMessage());
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
		List<AssSupType> groupDictList = supTypeMapper.querySupType(entityMap);
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
		return matSupAttrMapper.querySupIdByCode(mapVo);
	}

	@Override
	public Long queryDeptIdByCode(Map<String, Object> mapVo) throws DataAccessException {
		return matSupAttrMapper.queryDeptIdByCode(mapVo);
	}

	/**
	 * 根据供应商类别编码 查询该供应商类别是否存在
	 */
	@Override
	public int querySupTypeExist(Map<String, Object> mapVo)	throws DataAccessException {
		return matSupAttrMapper.querySupTypeExist(mapVo);
	}
	/**
	 * 查询 所属系统模块编码是否存在 
	 */
	@Override
	public int queryModExist(Map<String, Object> mapVo)	throws DataAccessException {
		return matSupAttrMapper.queryModExist(mapVo);
	}

}
