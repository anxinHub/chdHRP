package com.chd.hrp.budg.serviceImpl.base.budgdrug;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.budg.dao.base.budgdrug.BudgDrugTypeDictMapper;
import com.chd.hrp.budg.dao.base.budgdrug.BudgDrugTypeMapper;
import com.chd.hrp.budg.entity.BudgDrugType;
import com.chd.hrp.budg.service.base.budgdrug.BudgDrugTypeService;
import com.chd.hrp.mat.entity.MatType;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.entity.MedType;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

@Service("budgDrugTypeService")
public class BudgDrugTypeServiceImpl implements BudgDrugTypeService {
	
	private static Logger logger = Logger.getLogger(BudgDrugTypeServiceImpl.class);
	
	@Resource(name="budgDrugTypeMapper")
	private final BudgDrugTypeMapper budgDrugTypeMapper=null;
	
	@Resource(name="medCommonMapper")
	private MedCommonMapper medCommonMapper=null;
	
	@Resource(name="budgDrugTypeDictMapper")
	private BudgDrugTypeDictMapper budgDrugTypeDictMapper=null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;    

	@Override
	public String queryBudgDrugTypeByTree(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<BudgDrugType> list = (List<BudgDrugType>) budgDrugTypeMapper.query(entityMap);
		if (list.size()>0) {
			int row = 0;
			for (BudgDrugType mt : list) {
				if (row == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				row++;
				jsonResult.append("\"pcode\":\""+mt.getSuper_code()+"\"," + 
						"\"code\":\"" + mt.getMed_type_code()+ "\"," + 
						"\"id\":\"" + mt.getMed_type_id()+ "\"," + 
						"\"text\":" + "\"" + mt.getMed_type_code()+ " " + mt.getMed_type_name() + "\"" 
					);
				jsonResult.append("}");
			}
		}
		jsonResult.append("]}");
		return jsonResult.toString();
	}
	
	/**
	 * @Description 
	 * 获取对药品分类字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public BudgDrugType queryBudgDrugTypeById(Map<String,Object> entityMap)throws DataAccessException{
		
		return budgDrugTypeMapper.queryBudgDrugTypeById(entityMap);
	}

	
	/**
	 * @Description 
	 * 添加 08102 药品分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.remove("super_code");
		entityMap.remove("spell_code");
		entityMap.remove("wbx_code");
		//判断名称编码是否重复
		List<Map<String, Object>> mtlist = queryBudgDrugTypeByCodeName(entityMap);
		if (mtlist.size()>0) {
			for(Map<String, Object> medType : mtlist ){
				if(entityMap.get("med_type_code").equals(medType.get("code"))){
					return "{\"error\":\"编码：" + medType.get("code").toString() + "已存在.\"}";
				}
				//if(entityMap.get("mat_type_name").equals(matType.get("name"))){
					//return "{\"error\":\"名称：" + matType.get("name").toString() + "已存在.\"}";
				//}
			}
		}
		
		//判断是否符合编码规则
		Map<String, Object> comMap = new HashMap<String, Object>();
		comMap.put("group_id", entityMap.get("group_id"));
		comMap.put("hos_id", entityMap.get("hos_id"));
		comMap.put("copy_code", entityMap.get("copy_code"));
		comMap.put("proj_code", "med_type".toUpperCase());
		comMap.put("mod_code", "08");
        String rules = medCommonMapper.getMedHosRules(comMap);//获取编码规则2-2-2....
        if(rules.length() <= 0){
        	return "{\"error\":\"请维护药品类别编码规则！\"}";
        }
        String med_type_code = (String)entityMap.get("med_type_code");//类别编码
        StringBuffer s_code = new StringBuffer();//上级编码
        String super_code_temp = "0";
        int type_level =0;//级次 
        int begin_index = 0;
        int end_index = 0;
        int length = 0;
        String[] ruless  = rules.split("-");
        int strLength = med_type_code.length();
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
        	super_code_temp = med_type_code.substring(begin_index, end_index);
        	
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
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("med_type_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("med_type_name").toString()));

		Map<String, Object> utilMap = new HashMap<String, Object>();
		if(!"0".equals(super_code)){
	        //判断上级编码是否存在
			utilMap.put("group_id", entityMap.get("group_id"));
			utilMap.put("hos_id", entityMap.get("hos_id"));
			utilMap.put("copy_code", entityMap.get("copy_code"));
			utilMap.put("med_type_code", super_code.toString());
			utilMap.put("type_level", type_level-1);
	        Map<String,Object> type = budgDrugTypeMapper.queryByCode(utilMap);
	        if(type == null ){
	        	return "{\"error\":\"输入类别编码的上级类别编码（"+super_code+"）不存在，不允许添加，请添加上级类别后再操作！\"}";
	   	 	}
	        utilMap.put("med_type_id", type.get("med_type_id"));
		}
        
        try {
            //新增物资类别
        	budgDrugTypeMapper.add(entityMap);
			
	        //新增物资类别变更
			entityMap.put("change_user", SessionManager.getUserId());
			entityMap.put("change_date", new Date());
			entityMap.put("change_note", "新增");
			budgDrugTypeDictMapper.add(entityMap);
			if(!"0".equals(super_code)){
		        //更新上级类别is_last为否(0)
	            utilMap.put("is_last", "0");
	            budgDrugTypeMapper.updateBudgDrugTypeIsLast(utilMap);
				budgDrugTypeDictMapper.updateBudgDrugTypeDictIsLast(utilMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"med_type_id\":\""+entityMap.get("med_type_id")+"\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedType\"}";
		}
	}

	/**
	 * @Description 
	 * 删除 药品分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    
		try {
			//判断是否被使用
			String reStr="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dict_code", "med_type".toUpperCase());
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("dict_id_str", entityMap.get("med_type_id"));
			map.put("acc_year", "");
			map.put("p_flag", "1");
			sysFunUtilMapper.querySysDictDelCheck(map);
			
			
			if(map.get("reNote")!=null) {
				reStr+=map.get("reNote");
			}
			
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的药品类别被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			    
			}
			
			//判断是否为非末级
		
			//MatType mt = matTypeMapper.queryMatTypeById(entityMap);
			
			
			//if(mt.getIs_last() == 0){
				
				
				//return "{\"error\":\"删除失败，选择的物资类别为非末级！\",\"state\":\"false\"}";
				
			//}
			
			//删除物资类别
			
			
			int state = budgDrugTypeMapper.delete(entityMap);
			
			
			if(state > 0){
				
				Map<String, Object> dictMap = new HashMap<String, Object>();
				dictMap.put("group_id", entityMap.get("group_id"));
				dictMap.put("hos_id", entityMap.get("hos_id"));
				dictMap.put("copy_code", entityMap.get("copy_code"));
				//修改上级编码非末级标记
				dictMap.put("med_type_id", entityMap.get("med_type_id"));
				budgDrugTypeMapper.updateBudgDrugTypeSuperIsLastByIds(dictMap);
				budgDrugTypeDictMapper.updateBudgDrugTypeDictSuperIsLastByIds(dictMap);
				
				//新增变更表删除记录
				entityMap.put("change_user", SessionManager.getUserId());
				entityMap.put("change_date", new Date());
				entityMap.put("change_note", "删除");
				budgDrugTypeDictMapper.addBudgDrugTypeDictForDelete(entityMap);
				
				//停用所有变更记录
				budgDrugTypeDictMapper.updateBudgDrugTypeDictIsStop(entityMap);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedType\"}";
		}	
  }

	/**
	 * @Description 
	 * 更新04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			budgDrugTypeMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"med_type_id\":\""+entityMap.get("med_type_id")+"\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMedType\"}";
		}	
	}
	
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		if ("".equals(entityMap.get("med_type_id"))) {
			return add(entityMap);
			
		}else{
			return update(entityMap);
		}
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgDrugType> list = (List<BudgDrugType>) budgDrugTypeMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgDrugType> list = (List<BudgDrugType>) budgDrugTypeMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	/**
	 * 根据code和name查询出所有符合要求的数据
	 * @param entityMap
	 * @return Map
	 */
	@Override
	public List<Map<String, Object>> queryBudgDrugTypeByCodeName(Map<String,Object> entityMap){
		Map<String,Object> utilMap=new HashMap<String,Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", entityMap.get("copy_code"));
		utilMap.put("table_name", "med_type");
		if(entityMap.get("med_type_id") != null && !"".equals(entityMap.get("med_type_id").toString())){
			utilMap.put("c_id", "med_type_id");
			utilMap.put("c_id_value", entityMap.get("med_type_id"));
		}
		utilMap.put("c_code", "med_type_code");
		utilMap.put("c_code_value", entityMap.get("med_type_code"));
		utilMap.put("c_name", "med_type_name");
		utilMap.put("c_name_value", entityMap.get("med_type_name"));
		return medCommonMapper.existsMedCodeName(utilMap);
	}
	
	
	public String impMedType(Map<String,Object> entityMap,Map<String,Object> utilMap)throws DataAccessException{
		
		 try {
	            //新增药品类别
				budgDrugTypeMapper.add(entityMap);
				
		        //新增药品类别变更
				entityMap.put("change_user", SessionManager.getUserId());
				entityMap.put("change_date", new Date());
				entityMap.put("change_note", "新增");
				budgDrugTypeDictMapper.add(entityMap);
				if(!"0".equals(entityMap.get("entityMap"))){
			        //更新上级类别is_last为否(0)
		            utilMap.put("is_last", "0");
		            budgDrugTypeMapper.updateBudgDrugTypeIsLast(utilMap);
		            budgDrugTypeDictMapper.updateBudgDrugTypeDictIsLast(utilMap);
				}
				return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"mat_type_id\":\""+entityMap.get("mat_type_id")+"\"}";
			}
			catch (DataAccessException e) {
				logger.error(e.getMessage(), e);
				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatType\"}";
			}
		
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap)	throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return  budgDrugTypeMapper.queryByCode(entityMap);
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

}
