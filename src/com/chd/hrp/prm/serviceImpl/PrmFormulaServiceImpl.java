
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.prm.dao.PrmFormulaMapper;
import com.chd.hrp.prm.dao.PrmFormulaStackMapper;
import com.chd.hrp.prm.dao.PrmTargetMapper;
import com.chd.hrp.prm.entity.PrmFormula;
import com.chd.hrp.prm.entity.PrmTarget;
import com.chd.hrp.prm.service.PrmFormulaService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 9906 绩效计算公式表
 * @Table:
 * PRM_FORMULA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmFormulaService")
public class PrmFormulaServiceImpl implements PrmFormulaService {

	private static Logger logger = Logger.getLogger(PrmFormulaServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmFormulaMapper")
	private final PrmFormulaMapper prmFormulaMapper = null;
	
	@Resource(name = "prmFormulaStackMapper")
	private final PrmFormulaStackMapper prmFormulaStackMapper = null;
	
	@Resource(name = "prmTargetMapper")
	private final PrmTargetMapper prmTargetMapper = null;
    
	/**
	 * @Description 
	 * 添加9906 绩效计算公式表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmFormula(Map<String,Object> entityMap)throws DataAccessException{
		try {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			//获取对象9906 绩效计算公式表
			PrmFormula prmFormula = queryPrmFormulaByCode(entityMap);
			
			//判断计算公式编码是否存在
			if (prmFormula != null) {
	
				return "{\"error\":\"数据重复,请重新添加.\"}";
	
			}
			
			//查询指标
			List<PrmTarget> targetlist = prmTargetMapper.queryPrmTarget(entityMap);
			Map<String,PrmTarget> targetMap = targetlistToMap(targetlist);
			List<String> targetCodeList = new ArrayList<String>();//存储指标编码
			
			//获取计算公式中文字符串,用于匹配、获取、替换
			String formula_method_eng = String.valueOf(entityMap.get("formula_method_chs"));
			
			//正则表达式:用于匹配指标
			String regex = "\\{.*?\\}";
			
			Pattern p = Pattern.compile(regex);//获取正则对象
			Matcher matcher = p.matcher(formula_method_eng);//将正则表达式作用在字符串(指标)上
			StringBuffer sb = new StringBuffer();
			
			//匹配字符串中的指标
			while(matcher.find()){
				
				String targetKey = matcher.group();//获取匹配到的指标
				PrmTarget prmTarget = targetMap.get(targetKey);
				if(prmTarget == null){//如果不存在
					sb.append(targetKey);
				}else{//存在
					formula_method_eng = formula_method_eng.replace(targetKey, "{" + prmTarget.getTarget_code() + "}");
					targetCodeList.add(prmTarget.getTarget_code());
				}
			}
			
			if(sb.toString().length() > 0 ){
				
				return "{\"error\":\""+sb.toString()+"\\n等指标不存在\"}";
			}
		
		
			entityMap.put("formula_method_eng", formula_method_eng);
			int state = prmFormulaMapper.addPrmFormula(entityMap);
			
			
			if(entityMap.containsKey("formula_stack_value")){
				if (!entityMap.get("formula_stack_value").toString().equals("undefined")) {
					prmFormulaStackMapper.deletePrmFormulaStack(entityMap);
					//List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("formula_stack_value").toString());
	
					for (int x = 0; x< targetCodeList.size() ; x++) {
						
						Map<String,Object> map = new HashMap<String,Object>();
	
						map.put("group_id", entityMap.get("group_id"));
	
						map.put("hos_id", entityMap.get("hos_id"));
	
						map.put("copy_code", entityMap.get("copy_code"));
						
						map.put("formula_code", entityMap.get("formula_code"));
						
						map.put("target_code", targetCodeList.get(x));
						
						map.put("stack_seq_no", x);
	
						listVo.add(map);
					}
					prmFormulaStackMapper.addBatchPrmFormulaStack(listVo);
				}
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败 ");

			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmFormula\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加9906 绩效计算公式表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmFormula(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmFormulaMapper.addBatchPrmFormula(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmFormula\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新9906 绩效计算公式表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmFormula(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
		
			//查询指标
			List<PrmTarget> targetlist = prmTargetMapper.queryPrmTarget(entityMap);
			Map<String,PrmTarget> targetMap = targetlistToMap(targetlist);
			List<String> targetCodeList = new ArrayList<String>();
					
			//获取计算公式中文字符串,用于匹配、获取、替换
			String formula_method_eng = String.valueOf(entityMap.get("formula_method_chs"));
					
			//正则表达式:用于匹配指标
			String regex = "\\{.*?\\}";
					
			Pattern p = Pattern.compile(regex);//获取正则对象
			Matcher matcher = p.matcher(formula_method_eng);//将正则表达式作用在字符串(指标)上
			StringBuffer sb = new StringBuffer();
					
			//匹配字符串中的指标
			while(matcher.find()){
						
				String targetKey = matcher.group();//获取匹配到的指标
				PrmTarget prmTarget = targetMap.get(targetKey);
				if(prmTarget == null){//如果不存在
					sb.append(targetKey);
				}else{//存在
					formula_method_eng = formula_method_eng.replace(targetKey, "{" + prmTarget.getTarget_code() + "}");
					targetCodeList.add(prmTarget.getTarget_code());
				}
			}
					
			if(sb.toString().length() > 0 ){
				return "{\"error\":\""+sb.toString()+"\\n指标不存在\"}";
			}
			
			
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
			entityMap.put("formula_method_eng", formula_method_eng);
			
			int state = prmFormulaMapper.updatePrmFormula(entityMap);
			
			if(entityMap.containsKey("formula_stack_value")){
				if (!entityMap.get("formula_stack_value").toString().equals("undefined")) {
					prmFormulaStackMapper.deletePrmFormulaStack(entityMap);
					//List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("formula_stack_value").toString());
	
					for (int x = 0 ; x < targetCodeList.size() ; x++) {
						
						Map<String,Object> map = new HashMap<String,Object>();
	
						map.put("group_id", entityMap.get("group_id"));
						map.put("hos_id", entityMap.get("hos_id"));
						map.put("copy_code", entityMap.get("copy_code"));
						map.put("formula_code", entityMap.get("formula_code"));
						map.put("target_code", targetCodeList.get(x));
						map.put("stack_seq_no", x);
	
						listVo.add(map);
					}
					prmFormulaStackMapper.addBatchPrmFormulaStack(listVo);
				}
			}
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			throw new SysException("{\"error\":\"操作失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新9906 绩效计算公式表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmFormula(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmFormulaMapper.updateBatchPrmFormula(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmFormula\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除9906 绩效计算公式表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmFormula(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmFormulaMapper.deletePrmFormula(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmFormula\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除9906 绩效计算公式表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmFormula(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			prmFormulaStackMapper.deleteBatchPrmFormulaStack(entityList);
			prmFormulaMapper.deleteBatchPrmFormula(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmFormula\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集9906 绩效计算公式表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmFormula(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmFormula> list = prmFormulaMapper.queryPrmFormula(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmFormula> list = prmFormulaMapper.queryPrmFormula(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象9906 绩效计算公式表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmFormula queryPrmFormulaByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmFormulaMapper.queryPrmFormulaByCode(entityMap);
	}
	
	
	public Map<String,PrmTarget> targetlistToMap(List<PrmTarget> list){
		
		if(list.size() == 0){
			
			return null;
		}
		
		Map<String,PrmTarget> map = new HashMap<String,PrmTarget>();//用于存储数据
		
		for(int x = 0 ;x < list.size() ; x++){
			
			PrmTarget prmTarget = list.get(x);
			
			map.put("{"+prmTarget.getTarget_name()+"}", prmTarget);
		}
		
		
		return map;
	}
	@Override
	public List<Map<String, Object>> queryPrmFormulaPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<PrmFormula> list = prmFormulaMapper.queryPrmFormula(entityMap);
		
		return JsonListMapUtil.beanToListMap(list);
	}
}
