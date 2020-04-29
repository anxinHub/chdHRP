package com.chd.hrp.acc.serviceImpl.single;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.acc.dao.single.AccGztMapper;
import com.chd.hrp.acc.dao.wagedata.AccWagePayMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageSchemeItemMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageSchemeMapper;
import com.chd.hrp.acc.entity.AccWageScheme;
import com.chd.hrp.acc.entity.AccWageSchemeItem;
import com.chd.hrp.acc.service.single.AccGztService;
import com.chd.hrp.sys.dao.EmpDictMapper;
import com.chd.hrp.sys.entity.EmpDict;

/**
 * 
 * @author yang
 *
 */
@Service("accGztService")
public class AccGztServiceImpl implements AccGztService {

	private static Logger logger = Logger.getLogger(AccGztServiceImpl.class);
	
	@Resource(name = "accWageSchemeMapper")
	private final AccWageSchemeMapper accWageSchemeMapper = null;
	
	@Resource(name = "empDictMapper")
	private final EmpDictMapper empDictMapper = null;
	
	@Resource(name = "accGztMapper")
	private final AccGztMapper accGztMapper = null;
	
	@Resource(name = "accWageSchemeItemMapper")
	private final AccWageSchemeItemMapper accWageSchemeItemMapper = null;
	
	@Resource(name = "accWagePayMapper")
	private final AccWagePayMapper accWagePayMapper = null;
	
	/**
	 * 获取职工个人工资条的基本项信息
	 */
	@Override
	public String getGztEmpBaseInfo(Map<String, Object> paraMap) throws DataAccessException {
		String userId=SessionManager.getUserId();
		String userCode=SessionManager.getUserCode();
		String userName=SessionManager.getUserName();
		String empId=SessionManager.getEmpCode();
		paraMap.put("group_id", SessionManager.getGroupId());
		paraMap.put("hos_id", SessionManager.getHosId());
		paraMap.put("emp_id",empId);
		StringBuilder res=new StringBuilder();
		res.append("{\"user_id\":\""+userId+"\",\"user_code\":\""+userCode+"\",\"user_name\":\""+userName+"\",\"emp_id\":\""+empId+"\"");

		try{
			//查询职工信息
			Map<String, Object> empMap = accGztMapper.queryGztEmpById(paraMap);
			if(empMap == null || empMap.size()==0){
				res.append(",\"error\":\"查询职工失败.\",\"state\":\"false\"}");
				return res.toString();
			}
			
			String empCode=empMap.get("emp_code").toString();
			String empName=empMap.get("emp_name").toString();
			String depCode=empMap.get("dept_code").toString();
			String depName=empMap.get("dept_name").toString();
			res.append(",\"emp_code\":\""+empCode+"\",\"emp_name\":\""+empName+"\",\"dept_code\":\""+depCode+"\",\"dept_name\":\""+depName+"\"");
						
			//查询工资套
			List<Map<String, Object>> wageList = accGztMapper.queryGztWage(paraMap);
			if(wageList==null || wageList.size()==0){
				res.append(",\"error\":\"没有维护工资条.\",\"state\":\"false\"}");
				return res.toString();
			}
			
			String copyCode=null;
			res.append(",\"wage\":[");
			for(Map<String, Object> wage :wageList){
				copyCode=wage.get("copy_code").toString();
				res.append("{\"id\":\""+wage.get("wage_code")+"\",\"text\":\""+wage.get("wage_name")+"\"},");
			}
			res.setCharAt(res.length()-1, ']');
			res.append(",\"copy_code\":\""+copyCode+"\"}");
			return res.toString();
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 查询工资条方案的工资项
	 */
	@Override
	public String queryGztWageItem(Map<String, Object> paraMap) throws DataAccessException {
		try{
			// 1.检查用户有没有绑定职工
			String empId = SessionManager.getEmpCode();
			if(StringUtils.isEmpty(empId)){
				return "{\"error\":\"没有绑定职工.\",\"state\":\"false\"}";
			}
			
			// 2.查询工资方案
			paraMap.put("group_id", SessionManager.getGroupId());
			paraMap.put("hos_id", SessionManager.getHosId());
			paraMap.put("emp_id", empId);
			AccWageScheme scheme = accGztMapper.queryGztSchemeByEmpId(paraMap);
			if(scheme == null){
				return "{\"error\":\"没有维护工资条.\",\"state\":\"false\"}";
			}
			
			// 3.检查工资条下有没有工资项，有就返回工资项，否则提示
			paraMap.remove("emp_id");
			paraMap.put("scheme_id", scheme.getScheme_id());
			List<AccWageSchemeItem> wageItemList = accWageSchemeItemMapper.queryAccWageSchemeItem(paraMap);
			if(wageItemList.isEmpty()){
				return "{\"error\":\"没有维护工资项.\",\"state\":\"false\"}";
			}else{
				return ChdJson.toJson(wageItemList);
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 指定的月是否有工资数据
	 */
	@Override
	public boolean queryGzIsGrantByMonth(Map<String, Object> paraMap) throws DataAccessException {
		try{
			int count = accGztMapper.queryAccWagePayByMonth(paraMap);
			if(count == 0){
				return false;
			}
			return true;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryGztDetail(Map<String, Object> paraMap) throws DataAccessException {
		
		StringBuilder sql = new StringBuilder();
		String[] item_column = paraMap.get("column_item").toString().split(",");
		for (int i = 1; i < item_column.length; i++) {
			sql.append(",round(nvl(" + item_column[i] + ", 0), 2) as " + item_column[i]);
		}
		paraMap.put("sql", sql.substring(1));
		List<Map<String, Object>> list = accGztMapper.queryGztDetail(paraMap);
		return ChdJson.toJson(list);
	}

}
