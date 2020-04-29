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
import com.chd.hrp.budg.entity.BudgDrugTypeDict;
import com.chd.hrp.budg.service.base.budgdrug.BudgDrugTypeDictService;
import com.chd.hrp.budg.service.base.budgdrug.BudgDrugTypeService;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.entity.MedType;
import com.chd.hrp.med.entity.MedTypeDict;
import com.github.pagehelper.PageInfo;

@Service("budgDrugTypeDictService")
public class BudgDrugTypeDictServiceImpl implements BudgDrugTypeDictService {
	
	private static Logger logger = Logger.getLogger(BudgDrugTypeDictServiceImpl.class);
	
	@Resource(name = "budgDrugTypeDictMapper")
	private final BudgDrugTypeDictMapper budgDrugTypeDictMapper = null;
	
	@Resource(name = "budgDrugTypeService")
	private final BudgDrugTypeService budgDrugTypeService = null;
	
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;
	
	@Resource(name = "budgDrugTypeMapper")
	private final BudgDrugTypeMapper budgDrugTypeMapper = null;
	
	
	
	/**
	 * @Description 
	 * 添加04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.remove("super_code");
		entityMap.remove("spell_code");
		entityMap.remove("wbz_code");
		//判断名称编码是否重复
		List<Map<String, Object>> mtlist = budgDrugTypeService.queryBudgDrugTypeByCodeName(entityMap);
		if (mtlist.size()>0) {
			for(Map<String, Object> medType : mtlist ){
				if(entityMap.get("med_type_code").equals(medType.get("code"))){
					return "{\"error\":\"编码：" + medType.get("code").toString() + "已存在.\"}";
				}
				//if(entityMap.get("mat_type_name").equals(matType.get("name"))){
				//	return "{\"error\":\"名称：" + matType.get("name").toString() + "已存在.\"}";
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
        		return "{\"error\":\"变更失败 编码位数不符合编码规则 请重新输入！\"}";
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
			Map<String, Object> map = budgDrugTypeMapper.queryByCode(utilMap);
	        if(map == null ){
	        	return "{\"error\":\"输入类别编码的上级类别编码（"+super_code+"）不存在，不允许变更，请添加上级类别后再操作！\"}";
	   	 	}
	       utilMap.put("med_type_id", map.get("med_type_id"));
		}
		
		Map<String, Object> dictMap = new HashMap<String, Object>();
		dictMap.put("group_id", entityMap.get("group_id"));
		dictMap.put("hos_id", entityMap.get("hos_id"));
		dictMap.put("copy_code", entityMap.get("copy_code"));
		dictMap.put("c_max", "med_type_no");
		dictMap.put("table_name", "med_type_dict");
		dictMap.put("c_name", "med_type_id");
		dictMap.put("c_value", entityMap.get("med_type_id"));
		String max_no = medCommonMapper.getMedMaxNo(dictMap);
		
		try {
	        //更新药品类别
			budgDrugTypeMapper.update(entityMap);
			if(!"0".equals(super_code)){
		        //更新上级类别is_last为否(0)
	            utilMap.put("is_last", "0");
	            budgDrugTypeMapper.updateBudgDrugTypeIsLast(utilMap);
	            budgDrugTypeDictMapper.updateBudgDrugTypeDictIsLast(utilMap);
			}
			
			//更新未变更前的上级科目末级标志
			Map<String, Object> isLastMap = new HashMap<String, Object>();
			isLastMap.put("group_id", entityMap.get("group_id"));
			isLastMap.put("hos_id", entityMap.get("hos_id"));
			isLastMap.put("copy_code", entityMap.get("copy_code"));
			isLastMap.put("med_type_id", entityMap.get("med_type_id"));
			budgDrugTypeMapper.updateBudgDrugTypeSuperIsLastByIds(isLastMap);
			budgDrugTypeDictMapper.updateBudgDrugTypeDictSuperIsLastByIds(isLastMap);
			
			//变更信息
			entityMap.put("max_no", max_no);
			entityMap.put("change_user", SessionManager.getUserId());
			entityMap.put("change_date", new Date());
			entityMap.put("change_note", "变更");
			
			if("1".equals(entityMap.get("is_save_change"))){
				//停用上一次的变更记录
				budgDrugTypeDictMapper.updateBudgDrugTypeDictIsStop(entityMap);
				
	            //新增物资类别变更
				budgDrugTypeDictMapper.add(entityMap);
			}else{
				//更新变更记录数据为现有数据
				budgDrugTypeDictMapper.update(entityMap);
			}
			return "{\"msg\":\"变更成功.\",\"state\":\"true\",\"med_type_id\":\""+entityMap.get("med_type_id")+"\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"变更失败 数据库异常 请联系管理员! 方法 saveMedTypeDict\"}";
		}
	}

	/**
	 * @Description 
	 * 查询结果集  药品分类变更表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<BudgDrugTypeDict> list = (List<BudgDrugTypeDict>) budgDrugTypeDictMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<BudgDrugTypeDict> list = (List<BudgDrugTypeDict>) budgDrugTypeDictMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) 	throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap)	throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap)	throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap)	throws DataAccessException {

		return budgDrugTypeDictMapper.queryByCode(entityMap);
		
	}
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap)	throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
