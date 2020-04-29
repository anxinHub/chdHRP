package com.chd.hrp.ass.serviceImpl.repair.asslocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.repair.asslocation.AssLocationMapper;
import com.chd.hrp.ass.service.repair.asslocation.AssLocationService;
import com.chd.hrp.ass.serviceImpl.repair.AssInvArrtServiceImpl;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
@Service("assLocationService")
public class AssLocationServiceImpl implements  AssLocationService{
	
	private static Logger logger = Logger.getLogger(AssInvArrtServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assLocationMapper")
	private final AssLocationMapper assLocationMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;    
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;    
	
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		//获取对象tabledesc
			Map<String,Object> MapVo = queryAssLocationByCode(entityMap);

			if (MapVo != null) {

				return "{\"error\":\"数据重复,请重新添加.\"}";

			}
				
		//判断是否符合编码规则
		Map<String, Object> comMap = new HashMap<String, Object>();
		comMap.put("group_id", entityMap.get("group_id"));
		comMap.put("hos_id", entityMap.get("hos_id")); 
		comMap.put("copy_code", entityMap.get("copy_code"));
		comMap.put("proj_code", "ASS_LOCATION".toUpperCase());
		comMap.put("mod_code", "05");
        String rules = matCommonMapper.getMatHosRules(comMap);//获取编码规则2-2-2....
        if(rules.length() <= 0){
        	return "{\"error\":\"请维护资产位置编码规则！\"}";
        }
        String mat_type_code = (String)entityMap.get("loc_code");//类别编码
        StringBuffer s_code = new StringBuffer();//上级编码
        String super_code_temp = "0";
        int type_level =0;//级次 
        int begin_index = 0;
        int end_index = 0;
        int length = 0;
        String[] ruless  = rules.split("-");
        int strLength = mat_type_code.length();
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
        	super_code_temp = mat_type_code.substring(begin_index, end_index);
        	
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
        List<Map<String,Object>>listVo=assLocationMapper.queryAssLocationSuperAll(entityMap);
        if(!super_code.equals("0")){
	       Map<String, Object> assLocMap = assLocationMapper.querySuperLocationByLocCode(entityMap);
	        if(assLocMap==null){
	        	return "{\"error\":\"添加失败！请按照规则维护上级位置.\",\"state\":\"false\"}";
	        }
	        if(assLocMap.get("IS_STOP").toString().equals("1")){
	        	return "{\"error\":\"添加失败！上级位置已停用，无法维护子集数据.\",\"state\":\"false\"}";
	        }
        }
        StringBuffer loc_name_all=new StringBuffer();
        for (Map<String, Object> map : listVo) {
        	loc_name_all.append(map.get("NAME").toString()+"-");
        }
        
        if(loc_name_all!= null && "".equals(loc_name_all.toString())){
        	loc_name_all.append(loc_name_all.toString()+entityMap.get("loc_name").toString());
        }else{
        	loc_name_all.append(entityMap.get("loc_name").toString());
        }
        entityMap.put("loc_name_all", loc_name_all.toString());
		try {
			
			assLocationMapper.addLocation(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (DataAccessException e) {

			throw new SysException(e) ;

		}
	}

	public Map<String, Object> queryAssLocationByCode(Map<String, Object> entityMap) {
		
		return assLocationMapper.queryAssLocationByCode(entityMap);
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			  List<Map<String,Object>>listVo=assLocationMapper.queryAssLocationSuperAll(entityMap);
		        StringBuffer loc_name_all=new StringBuffer();
		        for (Map<String, Object> map : listVo) {
		        	loc_name_all.append(map.get("NAME").toString()+"-");
		        }
		        
		        if(loc_name_all!= null && "".equals(loc_name_all.toString())){
		        	loc_name_all.append(loc_name_all.toString()+entityMap.get("loc_name").toString());
		        }else{
		        	loc_name_all.append(entityMap.get("loc_name").toString());
		        }
		        entityMap.put("loc_name_all", loc_name_all.toString());
		        
			
			Map<String,Object> OldMap =assLocationMapper.queryAssLocationByCode(entityMap);
			entityMap.put("old_loc_name", OldMap.get("LOC_NAME"));
			entityMap.put("new_loc_name", entityMap.get("loc_name"));
			assLocationMapper.update(entityMap);
			
			assLocationMapper.updateLocNameAll(entityMap);
			

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
	public String queryAssLocationTree(Map<String, Object> mapVo) {
		mapVo.put("super_code", 0);
		List<Map<String,Object>>listVo=assLocationMapper.queryAssLocationTree(mapVo);
		
		return JSONArray.toJSONString(listVo);
	}

 


	@Override
	public String queryAssLocation(Map<String, Object> mapVo) {
		List<Map<String,Object>>listVo=assLocationMapper.queryAssLocation(mapVo);
		
		return ChdJson.toJsonLower(listVo);
	}

	@Override
	public String deleteAssLocationBatch(List<Map> listVo) {
		for (Map map : listVo) {
			String reStr="";
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("dict_code", "ASS_LOCATION");
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", "");
			mapVo.put("p_flag", "");//包括子科目
			mapVo.put("dict_id_str",map.get("loc_code"));
				sysFunUtilMapper.querySysDictDelCheck(mapVo);
				//}
				if(map.get("reNote")!=null)reStr+=map.get("reNote");
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的故障分类被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			Integer a =assLocationMapper.queryAssExists(map);
			if(a>0){
				  return "{\"error\":\"位置已被使用.无法删除\",\"state\":\"false\"}";
			}
		}
		assLocationMapper.deleteAssLocationDeptBatch(listVo);
		assLocationMapper.deleteAssLocationBatch(listVo);
		return "{\"msg\":\"删除成功\",\"state\":\"true\"}";
	}

}
