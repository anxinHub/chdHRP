/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）调整科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.wage;

import java.util.ArrayList;
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
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccWageItemsMapper;
import com.chd.hrp.acc.dao.wage.AccWageCalMapper;
import com.chd.hrp.acc.dao.wage.AccWageMapper;
import com.chd.hrp.acc.dao.wagedata.AccWagePayMapper;
import com.chd.hrp.acc.entity.AccWage;
import com.chd.hrp.acc.entity.AccWageCal;
import com.chd.hrp.acc.entity.AccWageItems;
import com.chd.hrp.acc.service.wage.AccWageCalService;
import com.chd.hrp.acc.serviceImpl.wagedata.AccWageLogServiceImpl;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 工资套合并计算公式<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWageCalService")
public class AccWageCalServiceImpl implements AccWageCalService {

	private static Logger logger = Logger.getLogger(AccWageLogServiceImpl.class);
	
	@Resource(name = "accWageCalMapper")
	private final AccWageCalMapper accWageCalMapper = null;
	
	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;
    
	@Resource(name = "accWagePayMapper")
	private final AccWagePayMapper accWagePayMapper = null;
	
	@Resource(name = "accWageMapper")
	private final AccWageMapper accWageMapper = null;
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 添加AccWageCal
	 * @param AccWageCal entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccWageCal(Map<String,Object> entityMap)throws DataAccessException{
		
		List<String> list = new ArrayList<String>();
		
		try {
			
			String wage_code = entityMap.get("wage_code").toString();
			
			String cal_name = entityMap.get("cal_name").toString();
			
			Pattern  p=  Pattern.compile("\\{.*?\\}");        
	        
			Matcher m = p.matcher(cal_name);
			
			while (m.find()) {
				
				list.add(m.group());
	           
	        } 
			
			for (int j = 0; j < list.size(); j++) {
				
				entityMap.remove("wage_code");
				
				entityMap.put("wage_name", list.get(j).split("\\.")[0].replaceAll("\\{","").replaceAll("\\}", ""));
				
				AccWage wageList = accWageMapper.queryAccWageByCal(entityMap);
				
				if(wageList!=null){
					
					cal_name=cal_name.replaceFirst(wageList.getWage_name(), wageList.getWage_code().toString());
				
				}
				
				entityMap.put("wage_code", wageList.getWage_code());
				
				entityMap.put("item_name", list.get(j).split("\\.")[1].replaceAll("\\{","").replaceAll("\\}", ""));
				
				AccWageItems itemList = accWageItemsMapper.queryAccWageColumnByCal(entityMap);
				
				if(itemList!=null){
					
					cal_name=cal_name.replaceFirst(itemList.getItem_name(), itemList.getColumn_item().toString());
				
				}
				
			}
					
            String regexStr = "[\u4E00-\u9FA5]";
			
		    while (Pattern.compile(regexStr).matcher(cal_name).find()) {     
		    	
		    	return "{\"msg\":\"公式不合法.\",\"state\":\"false\"}";
		    	
		       }  
		    
		   entityMap.put("wage_code", wage_code);
           
           entityMap.put("cal_eng", cal_name);
           
           AccWageCal accWageCal = accWageCalMapper.queryAccWageCalByCode(entityMap);
           
           if(accWageCal!=null){
        	   
        	   accWageCalMapper.updateAccWageCal(entityMap);
        	   
        	   return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
           }
           
           accWageCalMapper.addAccWageCal(entityMap);
			
           return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccWageCal\"}";

		}

	}
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 批量添加AccWageCal
	 * @param  AccWageCal entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccWageCal(Map<String,Object> entityList)throws DataAccessException{

		try {
				
			
			//accWageCalMapper.addBatchAccWageCal(calItem);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
				
			
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccWageCal\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 查询AccWageCal分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccWageCal(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String,Object>> list = accWageCalMapper.queryAccWageCal(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = accWageCalMapper.queryAccWageCal(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 查询AccWageCalByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWageCal queryAccWageCalByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageCalMapper.queryAccWageCalByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 批量删除AccWageCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccWageCal(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				accWageCalMapper.deleteBatchAccWageCal(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccWageCal\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 删除AccWageCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccWageCal(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accWageCalMapper.deleteAccWageCal(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccWageCal\"}";

		}
    }
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 更新AccWageCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWageCal(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accWageCalMapper.updateAccWageCal(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageCal\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 批量更新AccWageCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccWageCal(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accWageCalMapper.updateBatchAccWageCal(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageCal\"}";

		}
		
	}

}
