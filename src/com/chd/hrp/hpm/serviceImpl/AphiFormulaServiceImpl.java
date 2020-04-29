
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.mvel2.MVEL;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiFormulaMapper;
import com.chd.hrp.hpm.dao.AphiFormulaStackMapper;
import com.chd.hrp.hpm.dao.AphiTargetMapper;
import com.chd.hrp.hpm.entity.AphiFormula;
import com.chd.hrp.hpm.entity.AphiTarget;
import com.chd.hrp.hpm.service.AphiFormulaService;
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
 


@Service("aphiFormulaService")
public class AphiFormulaServiceImpl implements AphiFormulaService {

	private static Logger logger = Logger.getLogger(AphiFormulaServiceImpl.class);
	//引入DAO操作
	@Resource(name = "aphiFormulaMapper")
	private final AphiFormulaMapper aphiFormulaMapper = null;
	
	@Resource(name = "aphiFormulaStackMapper")
	private final AphiFormulaStackMapper aphiFormulaStackMapper = null;
	
	@Resource(name = "aphiTargetMapper")
	private final AphiTargetMapper aphiTargetMapper = null;
    
	/**
	 * @Description 
	 * 添加9906 绩效计算公式表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmFormula(Map<String,Object> entityMap){
		
		try {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			//获取对象9906 绩效计算公式表
			AphiFormula prmFormula = queryPrmFormulaByCode(entityMap);
			
			//判断计算公式编码是否存在
			if (prmFormula != null) {
	
				return "{\"error\":\"数据重复,请重新添加.\"}";
	
			}
			
			//查询指标
			List<AphiTarget> targetlist = aphiTargetMapper.queryPrmTarget(entityMap);
			Map<String,AphiTarget> targetMap = targetlistToMap(targetlist);
			Map<String,String> targetCodeMap = new HashMap<String,String>();//存储指标编码
			
			//获取计算公式中文字符串,用于匹配、获取、替换
			String formula_method_eng = String.valueOf(entityMap.get("formula_method_chs"));
			
			//替换大于号
			String gtReg = "&gt;";
			if(formula_method_eng.indexOf(gtReg) != -1){
				formula_method_eng = matchAndReplace(formula_method_eng,">",gtReg);
			}
			
			//替换小于号
			String ltReg = "&lt;";
			if(formula_method_eng.indexOf(ltReg) != -1){
				formula_method_eng = matchAndReplace(formula_method_eng,"<",ltReg);
			}
			
			//替换&
			String ampReg = "&amp;";
			if(formula_method_eng.indexOf(ampReg) != -1){
				formula_method_eng = matchAndReplace(formula_method_eng,"&",ampReg);
			}
			
			formula_method_eng = formula_method_eng.replaceAll("#\\{科室编码\\}","'-1'");
			
			formula_method_eng = formula_method_eng.replaceAll("#\\{科室名称\\}","'-2'");

			formula_method_eng = formula_method_eng.replaceAll("#\\{科室分类编码\\}","'-3'");
			
			formula_method_eng = formula_method_eng.replaceAll("#\\{科室分类名称\\}","'-4'");
			
			//正则表达式:用于匹配、替换指标
			String regex = "\\{\\S*?\\}";
			
			String formula_method_eng_validate = formula_method_eng.replaceAll(regex, "1");
			
			MVEL.eval(formula_method_eng_validate);
			
			Pattern p = Pattern.compile(regex);//获取正则对象
			Matcher matcher = p.matcher(formula_method_eng);//将正则表达式作用在字符串(指标)上
			StringBuffer sb = new StringBuffer();
			
			//匹配字符串中的指标
			while(matcher.find()){
				
				String targetKey = matcher.group();//获取匹配到的指标
				AphiTarget prmTarget = targetMap.get(targetKey);
				if(prmTarget == null){//如果不存在
					sb.append(targetKey);
				}else{//存在
					formula_method_eng = formula_method_eng.replace(targetKey, "{" + prmTarget.getTarget_code() + "}");
					targetCodeMap.put(prmTarget.getTarget_code(),prmTarget.getTarget_code());
				}
			}
			
			if(sb.toString().length() > 0 ){
				
				return "{\"error\":\""+sb.toString()+"\\n等指标不存在\"}";
			}
		
			
			entityMap.put("formula_method_chs", entityMap.get("formula_method_chs"));
			
			
			formula_method_eng = formula_method_eng.replaceAll("-1","#\\{科室编码\\}");
			
			formula_method_eng = formula_method_eng.replaceAll("-2","#\\{科室名称\\}");

			formula_method_eng = formula_method_eng.replaceAll("-3","#\\{科室分类编码\\}");
			
			formula_method_eng = formula_method_eng.replaceAll("-4","#\\{科室分类名称\\}");
			
			entityMap.put("formula_method_eng", formula_method_eng.replaceAll("(\r\n|\r|\n|\n\r)",""));
			
			aphiFormulaMapper.addPrmFormula(entityMap);
			
			
			if(entityMap.containsKey("formula_stack_value")){
				if (!entityMap.get("formula_stack_value").toString().equals("undefined")) {
					aphiFormulaStackMapper.deletePrmFormulaStack(entityMap);
					//List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("formula_stack_value").toString());
					int count = 1;
					for (String key : targetCodeMap.keySet()) {
						
						Map<String,Object> map = new HashMap<String,Object>();
	
						map.put("group_id", entityMap.get("group_id"));
	
						map.put("hos_id", entityMap.get("hos_id"));
	
						map.put("copy_code", entityMap.get("copy_code"));
						
						map.put("formula_code", entityMap.get("formula_code"));
						
						map.put("target_code", key);
						
						map.put("stack_seq_no", count);
						
						count++;
						
						listVo.add(map);
					}
					count = 1;
					aphiFormulaStackMapper.addBatchPrmFormulaStack(listVo);
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
			
			aphiFormulaMapper.addBatchPrmFormula(entityList);
			
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
			List<AphiTarget> targetlist = aphiTargetMapper.queryPrmTarget(entityMap);
			Map<String,AphiTarget> targetMap = targetlistToMap(targetlist);
			Map<String,String> targetCodeMap = new HashMap<String,String>();
					
			//获取计算公式中文字符串,用于匹配、获取、替换
			String formula_method_eng = String.valueOf(entityMap.get("formula_method_chs"));
			//替换大于号
			String gtReg = "&gt;";
			if(formula_method_eng.indexOf(gtReg) != -1){
				formula_method_eng = matchAndReplace(formula_method_eng,">",gtReg);
			}
			
			//替换小于号
			String ltReg = "&lt;";
			if(formula_method_eng.indexOf(ltReg) != -1){
				formula_method_eng = matchAndReplace(formula_method_eng,"<",ltReg);
			}
			
			//替换&
			String ampReg = "&amp;";
			if(formula_method_eng.indexOf(ampReg) != -1){
				formula_method_eng = matchAndReplace(formula_method_eng,"&",ampReg);
			}
			
			formula_method_eng = formula_method_eng.replaceAll("#\\{科室编码\\}","'-1'");
			
			formula_method_eng = formula_method_eng.replaceAll("#\\{科室名称\\}","'-2'");

			formula_method_eng = formula_method_eng.replaceAll("#\\{科室分类编码\\}","'-3'");
			
			formula_method_eng = formula_method_eng.replaceAll("#\\{科室分类名称\\}","'-4'");
			
			//正则表达式:用于匹配、替换指标
			String regex = "\\{\\S*?\\}";
			
			String formula_method_eng_validate = formula_method_eng.replaceAll(regex, "1");
			
			//验证
			MVEL.eval(formula_method_eng_validate);
			
			
			
			Pattern p = Pattern.compile(regex);//获取正则对象
			Matcher matcher = p.matcher(formula_method_eng);//将正则表达式作用在字符串(指标)上
			StringBuffer sb = new StringBuffer();
					
			//匹配字符串中的指标
			while(matcher.find()){
						
				String targetKey = matcher.group();//获取匹配到的指标
				AphiTarget prmTarget = targetMap.get(targetKey);
				if(prmTarget == null){//如果不存在
					sb.append(targetKey);
				}else{//存在
					formula_method_eng = formula_method_eng.replace(targetKey, "{" + prmTarget.getTarget_code() + "}");
					targetCodeMap.put(prmTarget.getTarget_code(), prmTarget.getTarget_code());
				}
			}
					
			if(sb.toString().length() > 0 ){
				return "{\"error\":\""+sb.toString()+"\\n指标不存在\"}";
			}
			
			
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			
			
			//中文公式转换为字节数组保存
			entityMap.put("formula_method_chs", entityMap.get("formula_method_chs"));
			
			formula_method_eng = formula_method_eng.replaceAll("-1","#\\{科室编码\\}");
			
			formula_method_eng = formula_method_eng.replaceAll("-2","#\\{科室名称\\}");

			formula_method_eng = formula_method_eng.replaceAll("-3","#\\{科室分类编码\\}");
			
			formula_method_eng = formula_method_eng.replaceAll("-4","#\\{科室分类名称\\}");
			
			entityMap.put("formula_method_eng", formula_method_eng.replaceAll("(\r\n|\r|\n|\n\r)",""));
			
			int state = aphiFormulaMapper.updatePrmFormula(entityMap);
			
			if(entityMap.containsKey("formula_stack_value")){
				if (!entityMap.get("formula_stack_value").toString().equals("undefined")) {
					aphiFormulaStackMapper.deletePrmFormulaStack(entityMap);
					//List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("formula_stack_value").toString());
					int count=1;
					for (String key :targetCodeMap.keySet()) {
						
						Map<String,Object> map = new HashMap<String,Object>();
	
						map.put("group_id", entityMap.get("group_id"));
						map.put("hos_id", entityMap.get("hos_id"));
						map.put("copy_code", entityMap.get("copy_code"));
						map.put("formula_code", entityMap.get("formula_code"));
						map.put("target_code", key);
						map.put("stack_seq_no", count);
	
						listVo.add(map);
						count++;
					}
					count=1;
					aphiFormulaStackMapper.addBatchPrmFormulaStack(listVo);
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
			
		  aphiFormulaMapper.updateBatchPrmFormula(entityList);
			
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
			
			int state = aphiFormulaMapper.deletePrmFormula(entityMap);
			
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
			aphiFormulaStackMapper.deleteBatchPrmFormulaStack(entityList);
			aphiFormulaMapper.deleteBatchPrmFormula(entityList);
			
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
		
		try {
			
			SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<AphiFormula> list = aphiFormulaMapper.queryPrmFormula(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<AphiFormula> list = aphiFormulaMapper.queryPrmFormula(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
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
	public AphiFormula queryPrmFormulaByCode(Map<String,Object> entityMap)throws DataAccessException{
		return aphiFormulaMapper.queryPrmFormulaByCode(entityMap);
	}
	
	
	public Map<String,AphiTarget> targetlistToMap(List<AphiTarget> list){
		
		if(list.size() == 0){
			
			return null;
		}
		
		Map<String,AphiTarget> map = new HashMap<String,AphiTarget>();//用于存储数据
		
		for(int x = 0 ;x < list.size() ; x++){
			
			AphiTarget prmTarget = list.get(x);
			
			map.put("{"+prmTarget.getTarget_name()+"}", prmTarget);
		}
		
		
		return map;
	}
	
	
	public String matchAndReplace(String Str,String replaceChar,String regex){
		
		if(Str.indexOf(regex) != -1){
			Pattern p = Pattern.compile(regex);//获取正则对象,替换大于符号
			Matcher matcher = p.matcher(Str);//将正则表达式作用在公式上
			while(matcher.find()){
				String str = matcher.group();//获取匹配到的公式
				Str = Str.replace(str,replaceChar);
			}
		}
		
		return Str;
	}
}
