package com.chd.hrp.ass.serviceImpl.repair.assfaulttype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.repair.assfaulttype.AssFaultTypeMapper;
import com.chd.hrp.ass.service.repair.assfaulttype.AssFaultTypeService;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
@Service("assFaultTypeService")
public class AssFaultTypeServiceImpl implements  AssFaultTypeService{

	@Resource(name = "assFaultTypeMapper")
	private final AssFaultTypeMapper assFaultTypeMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;    
	
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		//获取对象tabledesc
			Map<String,Object> MapVo = queryAssFaultTypeByCode(entityMap);
	
			if (MapVo != null) {
	
				return "{\"error\":\"数据重复,请重新添加.\"}";
	
			}
				
		//判断是否符合编码规则
		Map<String, Object> comMap = new HashMap<String, Object>();
		comMap.put("group_id", entityMap.get("group_id"));
		comMap.put("hos_id", entityMap.get("hos_id")); 
		comMap.put("copy_code", entityMap.get("copy_code"));
		comMap.put("proj_code", "ASS_FAULT_TYPE".toUpperCase());
		comMap.put("mod_code", "05");
	    String rules = matCommonMapper.getMatHosRules(comMap);//获取编码规则2-2-2....
	    if(rules.length() <= 0){
	    	return "{\"error\":\"请维护资产位置编码规则！\"}";
	    }
	    String fau_code = (String)entityMap.get("fau_code");//类别编码
	    StringBuffer s_code = new StringBuffer();//上级编码
	    String super_code_temp = "0";
	    int type_level =0;//级次 
	    int begin_index = 0;
	    int end_index = 0;
	    int length = 0;
	    String[] ruless  = rules.split("-");
	    int strLength = fau_code.length();
	    for(int i = 0; i < ruless.length ; i++){
	    	if("0".equals(ruless[i])){
	    		break;
	    	}
			s_code.append(super_code_temp);
	    	length = Integer.valueOf(ruless[i]);
	    	end_index = begin_index + length;
	    	//防止越界
	    	if(end_index >= strLength){
	    		end_index = strLength;
	    	}
	    	super_code_temp = fau_code.substring(begin_index, end_index);
	    	
	    	if(super_code_temp.length() == length){
	    		type_level += 1;
	    	}else{
	    		return "{\"error\":\"添加失败 编码位数不符合编码规则 请重新输入！\"}";
	    	}
	    	begin_index += length;
	    	//截取完毕以后跳出
	    	if(end_index == strLength){
	    		break;
	    	}
	    }
	    String super_code = "";
	    if(s_code.length()>1){
	    	super_code = s_code.toString().substring(1, s_code.length());
	    }else{
	    	super_code = s_code.toString();
	    }
	    entityMap.put("super_code", super_code.toString());
	    entityMap.put("type_level", type_level);
	    
		try {
			if(!"0".equals(super_code.toString())){
				Map<String,Object> AssFaultTypeMap =assFaultTypeMapper.queryAssFaultTypeSuperIsExists(entityMap);
				if(AssFaultTypeMap==null){
					
					return "{\"error\":\"添加失败，请先维护上级后，再维护子集数据.\",\"state\":\"false\"}";
				} 
				if(AssFaultTypeMap.get("IS_STOP").toString().equals("1")){
					return "{\"error\":\"添加失败，上级故障分类已停用无法维护下级数据.\",\"state\":\"false\"}";
				}
				
			}
			assFaultTypeMapper.addFaultType(entityMap);
			List<Map<String,Object>>list = assFaultTypeMapper.queryAssRepUserFaultBySuperCod(entityMap);
			if(list.size()>0){
				assFaultTypeMapper.extendsBySuperCode(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	
		}
		catch (DataAccessException e) {
	
			throw new SysException(e) ;
	
		}
}

	public Map<String, Object> queryAssFaultTypeByCode(Map<String, Object> entityMap) {
		
		return assFaultTypeMapper.queryAssFaultTypeByCode(entityMap);
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {
			assFaultTypeMapper.update(entityMap);

			 return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			throw  new SysException(e) ;
		}
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryAssFaultTypeTree(Map<String, Object> mapVo) throws DataAccessException{
		mapVo.put("super_code", 0);
		List<Map<String,Object>>listVo=assFaultTypeMapper.queryAssFaultTypeTree(mapVo);
		
		return JSONArray.toJSONString(listVo);
	}

	@Override
	public String queryAssFaultType(Map<String, Object> mapVo) throws DataAccessException{
		List<Map<String,Object>>list = assFaultTypeMapper.queryAssFaultType(mapVo);
		return ChdJson.toJsonLower(list);
	}

	@Override
	public String deleteAssFaultTypeBatch(List<Map> listVo) throws DataAccessException {
		try {
			StringBuilder fau_codes = new StringBuilder();
			for (Map map : listVo) {
				fau_codes.append(map.get("fau_code").toString()+",");
			}
			String fau_code = fau_codes.substring(0, fau_codes.length()-1);
			String reStr="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dict_code", "ASS_FAULT_TYPE");
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("acc_year", "");
			map.put("p_flag", "");//包括子科目
			map.put("dict_id_str",fau_code);
				sysFunUtilMapper.querySysDictDelCheck(map);
				//}
				if(map.get("reNote")!=null)reStr+=map.get("reNote");
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的故障分类被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			assFaultTypeMapper.deleteAssRepUserFaultTypeBatch(listVo);
			assFaultTypeMapper.deleteAssFaultTypeBatch(listVo);
			return "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
		  throw new SysException(e);
		}
	}

}
