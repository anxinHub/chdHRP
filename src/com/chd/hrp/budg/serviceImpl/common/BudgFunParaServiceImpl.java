/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.common;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.common.BudgFunParaMapper;
import com.chd.hrp.budg.entity.BudgFunPara;
import com.chd.hrp.budg.service.common.BudgFunParaService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 函数参数表
 * @Table:
 * FUN_PARA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("budgFunParaService")
public class BudgFunParaServiceImpl implements BudgFunParaService {

	private static Logger logger = Logger.getLogger(BudgFunParaServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgFunParaMapper")
	private final BudgFunParaMapper budgFunParaMapper = null;
    
	/**
	 * @Description 
	 * 添加函数参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象函数参数表
		BudgFunPara funPara = budgFunParaMapper.queryByCode(entityMap);

		if (funPara != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgFunParaMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addFunPara\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加函数参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgFunParaMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchFunPara\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新函数参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgFunParaMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateFunPara\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新函数参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgFunParaMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchFunPara\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除函数参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgFunParaMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteFunPara\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除函数参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgFunParaMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchFunPara\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加函数参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象函数参数表
		BudgFunPara funPara = budgFunParaMapper.queryByCode(entityMap);

		if (funPara != null) {

			int state = budgFunParaMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgFunParaMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateFunPara\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集函数参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgFunPara> list = (List<BudgFunPara>) budgFunParaMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgFunPara> list = (List<BudgFunPara>) budgFunParaMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象函数参数表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgFunParaMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取函数参数表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return FunPara
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgFunParaMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * 
	 * @param mapVo
	 * @return
	 */
	public String queryComTypePara(Map<String, Object> mapVo) throws DataAccessException {
			    
			List<Map<String,Object>> list = budgFunParaMapper.queryComTypePara(mapVo); 
			 
			if (list.size() >0){
				 
				// 拼接SQL
				StringBuilder json = new StringBuilder();
			 
				json.append("{Rows:[");

				for (Map<String,Object> item : list) {


					json.append("{");
		 
					json.append("\"para_code\":\"" + item.get("para_code") + "\"," + "\"para_name\":" + "\"" + item.get("para_name") + "\","+ "\"com_type_nature\":" + "\"" + item.get("com_type_nature")+ "\"");

					
					json.append("},");

				}

				json.setCharAt(json.length() - 1, ']');
				json.append("}"); 
		 
				return json.toString();

			}
			else { 
				return "{\"error\":\"该函数编码没有配置部件类型\"}";
				
			}
				
	}
	
	/**
	 * @Description 
	 * 获取函数参数表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgFunPara>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgFunParaMapper.queryExists(entityMap);
	}
}
