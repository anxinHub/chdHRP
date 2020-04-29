package com.chd.hrp.hr.serviceImpl.sysstruc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.sysstruc.HrFiiedTabTypeMapper;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedTabType;
import com.chd.hrp.hr.entity.sysstruc.HrTabType;
import com.chd.hrp.hr.service.sysstruc.HrFiiedTabTypeService;
@Service("hrFiiedTabTypeService")
public class HrFiiedTabTypeServiceImpl implements HrFiiedTabTypeService{
	

	private static Logger logger = Logger.getLogger(HrFiiedTabTypeServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "hrFiiedTabTypeMapper")
	private final HrFiiedTabTypeMapper hrFiiedTabTypeMapper = null;
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {	
		//获取对象如：国家标准、地方标准、医院标准
		HrFiiedTabType hrFiiedTabType = queryByCode(entityMap);

		if (hrFiiedTabType != null) {

			return "{\"error\":\"类别代码或者名称重复,请重新添加.\"}";

		}
		
		try {
			
			int state = hrFiiedTabTypeMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}}

	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {
			
			hrFiiedTabTypeMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("hos_id", SessionManager.getHosId());

			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("type_filed_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("type_filed_name").toString()));
			
			if("1".equals(entityMap.get("is_change").toString())){
				HrFiiedTabType hrFiiedTabType =hrFiiedTabTypeMapper.queryFTabTypeByName(entityMap);
				if(hrFiiedTabType != null){
					if(hrFiiedTabType.getType_filed_name().toString().equals(entityMap.get("type_filed_name").toString())){
						return "{\"error\":\"名称：" + entityMap.get("type_filed_name").toString() + "已存在.\"}";
					}
				}
			}
			
			int state = hrFiiedTabTypeMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\""+e.getMessage()+"! 方法 update\"}";
		}	
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {
			
		  hrFiiedTabTypeMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		  try {
				 
				int state = hrFiiedTabTypeMapper.delete(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

			}	
			
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
	try {
			
			hrFiiedTabTypeMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象如：国家标准、地方标准、医院标准
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<HrFiiedTabType> list = (List<HrFiiedTabType>)hrFiiedTabTypeMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = hrFiiedTabTypeMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = hrFiiedTabTypeMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

			List<HrFiiedTabType> list = (List<HrFiiedTabType>)hrFiiedTabTypeMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {	
		return hrFiiedTabTypeMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return hrFiiedTabTypeMapper.queryByUniqueness(entityMap);
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return hrFiiedTabTypeMapper.queryExists(entityMap);
	}

	@Override
	public String deletehrFiiedTabType(List<HrFiiedTabType> listVo) {
		try {
			
			//先判断该编码是否被使用了
			int count = hrFiiedTabTypeMapper.queryCount(listVo);
			if(count>0) {
				return "{\"error\":\"该类别代码已经被使用，请删除其他数据再进行删除!\",\"state\":\"false\"}";
			}
			hrFiiedTabTypeMapper.deleteHrFiiedTabType(listVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	
		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			throw new SysException(e.getMessage());
	
		}	
		
	}

	@Override
	public String importDate(Map<String, Object> entityMap)
			throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					saveMap.put("type_filed_code", map.get("type_filed_code").get(1));
					saveMap.put("type_filed_name", map.get("type_filed_name").get(1));
					saveMap.put("note", map.get("note").get(1));
					HrFiiedTabType type = hrFiiedTabTypeMapper.queryByCode(saveMap);
					
					if(type != null){
						failureMsg.append("<br/>类别代码 "+map.get("type_filed_name")+" 已存在; ");
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrFiiedTabTypeMapper.addBatch(saveList);
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 "+successNum+"条"+failureMsg+"\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}															

}
