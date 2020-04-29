package com.chd.hrp.prm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.hrp.prm.dao.PrmDeptFormuLaMethodMapper;
import com.chd.hrp.prm.dao.PrmDeptSchemeMapper;
import com.chd.hrp.prm.entity.PrmDeptFormulaMethod;
import com.chd.hrp.prm.service.PrmDeptFormuLaMethodService;



@Service("prmDeptFormuLaMethodService")
public class PrmDeptFormuLaMethodServiceImpl  implements PrmDeptFormuLaMethodService{
	
	private static Logger logger = Logger.getLogger(PrmDeptSchemeServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmDeptFormuLaMethodMapper")
	private final PrmDeptFormuLaMethodMapper prmDeptFormuLaMethodMapper = null;
	@Override
	public PrmDeptFormulaMethod queryPrmDeptFormuLaMethod(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		return prmDeptFormuLaMethodMapper.queryPrmDeptFormuLaMethod(entityMap);
	}
	@Override
	public String savePrmDeptFormula(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			//查询指标
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("{目标值}", "goal_value");
			map.put("{实际完成值}", "kpi_value");
			map.put("{满分标准}", "full_score");
			map.put("{权重值}", "ratio");
			List<String> targetCodeList = new ArrayList<String>();//存储指标编码
			//获取计算公式中文字符串,用于匹配、获取、替换
			String formula_method_eng = String.valueOf(entityMap.get("formula_method_chs"));
			
			//正则表达式:用于匹配指标
			String regex = "\\{.*?\\}";
			
			Pattern p = Pattern.compile(regex);//获取正则对象
			Matcher matcher = p.matcher(formula_method_eng);//将正则表达式作用在字符串(指标)上
			//String formula_method_eng= new StringBuffer();
			StringBuffer sb= new StringBuffer();
			//匹配字符串中的指标
			while(matcher.find()){
				
				String targetKey = matcher.group();//获取匹配到的指标
				//String prmTarget = map.get(targetKey).toString();
				if(map.get(targetKey)==null){//如果不存在
					sb.append(targetKey);
				}else{//存在
					formula_method_eng = formula_method_eng.replace(targetKey, "{" + map.get(targetKey).toString() + "}");
					targetCodeList.add(map.get(targetKey).toString());
				
				}
			}
				if(sb.toString().length() > 0 ){
								
					return "{\"error\":\""+sb.toString()+"\\n等指标不存在\"}";
							}
		
			
			
			entityMap.put("formula_method_eng", formula_method_eng);
			//获取对象9906 绩效计算公式表
			List<Map<String,Object>> prmFormula = prmDeptFormuLaMethodMapper.queryPrmDeptFormulaByCode(entityMap);
			
			//判断计算公式编码是否存在
			if (prmFormula.size()>0) {
	
				//return "{\"error\":\"数据重复,请重新添加.\"}";
				prmDeptFormuLaMethodMapper.updatePrmDeptFormula(entityMap);
			}else{
			
			prmDeptFormuLaMethodMapper.savePrmDeptFormula(entityMap);
			
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 savePrmDeptFormula\"}";

		}	
	}
	/**
	 * @Description 
	 * 批量删除 指示灯<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchDeptFormuLaMethod(List<Map<String, Object>> entityList)throws DataAccessException {

		try {

			prmDeptFormuLaMethodMapper.deleteBatchDeptFormuLaMethod(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptFunStack\"}";

		}
		// TODO Auto-generated method stub
		
	}
	
/*	public Map<String,Object> targetlistToMap(List<Map<String,Object>> list){
		
		if(list.size() == 0){
			
			return null;
		}
		
		Map<String,Object> map = new HashMap<String,Object>();//用于存储数据
		
		for(int x = 0 ;x < list.size() ; x++){
			
			Map<String, Object> prmTarget = list.get(x);
			
			map.put("{"+prmTarget.get("")+"}", prmTarget.get(""));
		}
		
		
		return map;
	}*/
}
