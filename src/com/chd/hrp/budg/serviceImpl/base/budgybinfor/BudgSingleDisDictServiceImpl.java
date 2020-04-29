
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.serviceImpl.base.budgybinfor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.base.budgybinfor.BudgSingleDisDictMapper;
import com.chd.hrp.budg.service.base.budgybinfor.BudgSingleDisDictService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 医保单病种字典
 * @Table:
 * SINGLE_DISEASE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("budgSingleDisDictService")
public class BudgSingleDisDictServiceImpl implements BudgSingleDisDictService {

	private static Logger logger = Logger.getLogger(BudgSingleDisDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgSingleDisDictMapper")
	private final BudgSingleDisDictMapper budgSingleDisDictMapper = null;
    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	/**
	 * @Description 
	 * 添加医保单病种字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			String str="";
			//获取对象医保单病种字典
			Map<String,Object> mapVo = budgSingleDisDictMapper.queryByCode(entityMap);
			if (mapVo != null) {
				str += "病种编码已被占用;";
			}
			
			int count = budgSingleDisDictMapper.queryNameExist(entityMap);
			if(count > 0 ){
				str +="病种名称已被占用;";
			}
			if(str != ""){
				return "{\"error\":\""+str+""+"请重新添加.\"}";
			}
			budgSingleDisDictMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
		
	}
	
	/**
	 * @Description 
	 * 批量添加医保单病种字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			
			budgSingleDisDictMapper.addBatch(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
			
		}
		
	}
	
	/**
	 * @Description 
	 * 更新医保单病种字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap) throws DataAccessException{	
		try {	
			int count = budgSingleDisDictMapper.queryNameExist(entityMap);
			if(count > 0 ){
				return "{\"error\":\"病种名称已被占用,请重新添加.\"}";
			}
			budgSingleDisDictMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";
		}		
	}
	
	/**
	 * @Description 
	 * 批量更新医保单病种字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{	
		try {	
			budgSingleDisDictMapper.updateBatch(entityList);	
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";
		}		
	}
	
	/**
	 * @Description 
	 * 删除医保单病种字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
		try {
			budgSingleDisDictMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";
		}		
	}
    
	/**
	 * @Description 
	 * 批量删除医保单病种字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			String storeIdStr="";//删除科目，判断业务使用
			String reStr="";
			String superCode="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dict_code", "BUDG_SINGLE_DISEASE_DICT");
			map.put("group_id", entityList.get(0).get("group_id"));
			map.put("hos_id", entityList.get(0).get("hos_id"));
			map.put("copy_code", entityList.get(0).get("copy_code"));
			map.put("acc_year", "");
			map.put("p_flag", "");//包括子科目
			if(entityList!=null && entityList.size()>0){
				for(Map<String, Object> mapVo : entityList){
					storeIdStr+="'"+mapVo.get("disease_code")+"',";
					
						map.put("dict_id_str", storeIdStr.substring(0, storeIdStr.length()-1));
						//删除科目时，判断业务表是否使用
						//if(LoadSystemInfo.getHrpMap().get("hrpDatatype").toString().equalsIgnoreCase("oracle")){
						
						sysFunUtilMapper.querySysDictDelCheck(map);
						
						storeIdStr="";
						//}
						if(map.get("reNote")!=null)reStr+=map.get("reNote");
					
				}
			}
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的单病种字典被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			
			budgSingleDisDictMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());

		}
		
	}
	
	/**
	 * @Description 
	 * 查询结果集医保单病种字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");	
		if (sysPage.getTotal()==-1){	
			List<?> list = budgSingleDisDictMapper.query(entityMap);		
			return ChdJson.toJson(list);	
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());	
			List<?> list = budgSingleDisDictMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());	
		}
	}
	
	/**
	 * @Description 
	 * 获取对象医保单病种字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgSingleDisDictMapper.queryByCode(entityMap);
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
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
	/**
	 * 查询病种名称是否已被占用
	 */
	@Override
	public int queryNameExist(Map<String, Object> mapVo) throws DataAccessException {
		return budgSingleDisDictMapper.queryNameExist(mapVo);
	}
	
}
