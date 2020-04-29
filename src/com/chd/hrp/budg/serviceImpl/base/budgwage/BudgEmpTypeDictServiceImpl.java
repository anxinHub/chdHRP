/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.base.budgwage;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.budg.dao.base.budgwage.BudgEmpTypeDictMapper;
import com.chd.hrp.budg.entity.BudgEmpTypeDict;
import com.chd.hrp.budg.service.base.budgwage.BudgEmpTypeDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 职工类别字典
 * @Table:
 * BUDG_EMP_TYPE_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgEmpTypeDictService")
public class BudgEmpTypeDictServiceImpl implements BudgEmpTypeDictService {

	private static Logger logger = Logger.getLogger(BudgEmpTypeDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgEmpTypeDictMapper")
	private final BudgEmpTypeDictMapper budgEmpTypeDictMapper = null;
    
	/**
	 * @Description 
	 * 添加职称字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		String str = "";
		//获取对象职称字典
		BudgEmpTypeDict budgEmpTypeDict = queryByCode(entityMap);

		if (budgEmpTypeDict != null) {
			
			str += "类别编码:"+entityMap.get("type_code")+";";

		}
		
		int count = budgEmpTypeDictMapper.queryNameExist(entityMap);
		
		if( count > 0){
			str += "类别名称:"+entityMap.get("type_name");
		}
		
		if (str != "") {
			
			return "{\"error\":\""+str+" 已被占用,请重新添加.\",\"state\":\"false\"}";

		}
		
		try {
			
			int state = budgEmpTypeDictMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败!\"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量添加职称字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/  
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		String str = "";
		
		for (Map<String, Object> entityMap : entityList) {
			//获取对象职称字典
			BudgEmpTypeDict budgEmpTypeDict = queryByCode(entityMap);

			if (budgEmpTypeDict != null) {
				str += "类别编码:"+entityMap.get("type_code")+";";
			}
			
			int count = budgEmpTypeDictMapper.queryNameExist(entityMap);
			
			if( count > 0){
				str += "类别名称:"+entityMap.get("type_name");
			}
		}
		
		if (str != "") {
			return "{\"error\":\""+str+" 已被占用,请重新添加.\",\"state\":\"false\"}";
		}
		
		try {
			
			budgEmpTypeDictMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败!\"}");

		}
		
	}
	
		/**
	 * @Description 
	 * 更新职称字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			int count = budgEmpTypeDictMapper.queryNameExist(entityMap);
			
			if( count > 0){
				
				return "{\"error\":\"职称名称:"+entityMap.get("type_name")+" 重复,请重新添加.\"}";
			}
			
			int state = budgEmpTypeDictMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败!\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新职称字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgEmpTypeDictMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败!\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 删除职称字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgEmpTypeDictMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败!\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除职称字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgEmpTypeDictMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败!\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加职称字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象职称字典
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgEmpTypeDict> list = (List<BudgEmpTypeDict>)budgEmpTypeDictMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgEmpTypeDictMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgEmpTypeDictMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败! 方法 addOrUpdate\"}");

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集职称字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgEmpTypeDict> list = (List<BudgEmpTypeDict>)budgEmpTypeDictMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgEmpTypeDict> list = (List<BudgEmpTypeDict>)budgEmpTypeDictMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象职称字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgEmpTypeDictMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取职称字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgEmpTypeDict
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgEmpTypeDictMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取职称字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgEmpTypeDict>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgEmpTypeDictMapper.queryExists(entityMap);
	}
	@Override
	public int queryNameExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgEmpTypeDictMapper.queryNameExist(mapVo);
	}
	
	/**
	 *最新版导入
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String importBudgEmpTypeDict(Map<String, Object> map) throws DataAccessException {
		try{
			
			String content=map.get("content").toString();
			
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			/*Map<String, Object> entityMap=new HashMap<String,Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
			// 查询现金流量项目表中所有未停用的现金流量项目id code等基本信息   匹配数据用
			List<Map<String,Object>> listDict = budgEmpTypeDictMapper.queryBudgEmpTypeDict(entityMap);
			
			// 判断现金流量项目编码是否存在    用 map key为现金流量项目code ， value : 现金流量项目id、code等信息
			Map<String, Map<String, Object>> dictMap = new HashMap<String, Map<String, Object>>();
			
			for(Map<String, Object> dict : listDict){
				if(dict.get("type_code") != null){
					dictMap.put(dict.get("type_code").toString(), dict);
				}
			}*/
			
			List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
			
			
			for(Map<String,List<String>> item : liData ){
				
				
				List<String> type_code = item.get("类别编码") ;
				
				List<String> type_name = item.get("类别名称") ;
				
				List<String> is_stop = item.get("是否停用") ;
				
				if(type_code == null){
					
					return "{\"warn\":\"" + type_code.get(0) + ",类别编码为空！\",\"state\":\"false\",\"row_cell\":\"" + type_code.get(0) + "\"}";
				}
				
				if(type_name == null){
					
					return "{\"warn\":\"" + type_name.get(0) + ",类别名称为空！\",\"state\":\"false\",\"row_cell\":\"" + type_name.get(0) + "\"}";
				}
				
				if(is_stop == null){
					
					return "{\"warn\":\"" + is_stop.get(0) + ",是否停用为空！\",\"state\":\"false\",\"row_cell\":\"" + is_stop.get(0) + "\"}";
				}else{
					
					 try{
						 
						Integer.parseInt(is_stop.get(1));
						   
					 }catch(NumberFormatException e){
						 
						 return "{\"warn\":\"" + is_stop.get(0) + ",是否停用输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + is_stop.get(0) + "\"}";
					 }
				}
				
				//存放 正确信息用Map
				Map<String,Object> returnMap = new HashMap<String,Object>();
				
				returnMap.put("group_id", SessionManager.getGroupId());
				
				returnMap.put("hos_id", SessionManager.getHosId());
				
				returnMap.put("copy_code", SessionManager.getCopyCode());
				
				returnMap.put("type_code",type_code.get(1));
				
				returnMap.put("type_name", type_name.get(1));
				
				returnMap.put("is_stop", is_stop.get(1));
					
				returnList.add(returnMap);
					
			}
			
			StringBuffer err_sb = new StringBuffer();
			
			//校验 数据重复
			for( int i = 1; i < returnList.size(); i++ ){
				
				for(int j = i + 1 ; j < returnList.size(); j++){
					
					if(returnList.get(i).get("type_code").equals(returnList.get(j).get("type_code"))){
						
						err_sb.append(returnList.get(i).get("type_code")+"类别编码");
					}
				}
				
			}
			
			if( err_sb.length() > 0){
				
				 return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
				
			}else{
				
				String str = addBatch(returnList);
				
				if("false".equals(JSONObject.parseObject(str).get("state"))){
					
					return str ;
				}else{
					
					return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
				}
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}
	
}
