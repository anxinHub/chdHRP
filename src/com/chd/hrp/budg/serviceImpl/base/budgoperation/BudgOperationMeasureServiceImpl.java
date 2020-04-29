/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.base.budgoperation;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.base.budgoperation.BudgOperationMeasureMapper;
import com.chd.hrp.budg.entity.BudgOperationMeasure;
import com.chd.hrp.budg.service.base.budgoperation.BudgOperationMeasureService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 运营尺度
 * @Table:
 * BUDG_OPERATION_MEASURE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgOperationMeasureService")
public class BudgOperationMeasureServiceImpl implements BudgOperationMeasureService {

	private static Logger logger = Logger.getLogger(BudgOperationMeasureServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgOperationMeasureMapper")
	private final BudgOperationMeasureMapper budgOperationMeasureMapper = null;
    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	
	
	/**
	 * 保存数据 
	 */
	@Override
	public String saveBudgOperationMeasure(List<Map<String, Object>> listVo) throws DataAccessException{
		
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		for(Map<String,Object> item : listVo){
			
			if("1".equals(item.get("flag"))){ //添加
				
				addList.add(item) ;
				
			}else{ //修改
				
				updateList.add(item) ;
			}
		}
		
		try {
			
			if(addList.size()>0){
				
				String err_code = "" ;//记录编码重复错误信息
				
				String err_name = "" ;//记录名称重复错误信息
				
				for (int i = 0; i < addList.size(); i++) {
					for (int j = i + 1; j < addList.size(); j++) {
						
						Map<String,Object>  item = addList.get(i);
						Map<String,Object>  map = addList.get(j);
						if (String.valueOf(item.get("measure_code")).equals(map.get("measure_code"))) {
							err_code += (Integer.parseInt(String.valueOf(item.get("rowNo"))) + 1) + "行与 " + (Integer.parseInt(String.valueOf(map.get("rowNo"))) + 1) + "行;" ;
						}
						if (String.valueOf(item.get("measure_code")).equals(map.get("measure_code"))) {
							err_name += (Integer.parseInt(String.valueOf(item.get("rowNo"))) + 1) + "行与 " + (Integer.parseInt(String.valueOf(map.get("rowNo"))) + 1) + "行;" ;
						}
					}
				}
				
				if(err_code != "" && err_name != ""){
					
					return "{\"error\":\"第"+err_code+"数据编码,第"+err_name+"名称重复\",\"state\":\"false\"}";
					
				}else if( err_code != "" ){
					
					return "{\"error\":\"第"+err_code+"数据编码重复\",\"state\":\"false\"}";
					
				}else if(err_name != ""){
					
					return "{\"error\":\"第"+err_name+"数据名称重复\",\"state\":\"false\"}";
				}
				
				//批量 查询添加数据 编码是否已存在
				String str = budgOperationMeasureMapper.queryCodeExistList(addList) ;
				
				//批量 查询添加数据 名称是否已存在
				String  nameStr = budgOperationMeasureMapper.queryNameExistList(addList) ;
				
				if(str == null &&  nameStr == null){
					
					int count = budgOperationMeasureMapper.addBatch(addList);
					
				}else if(str != null){
					if(nameStr != null){
						return "{\"error\":\"第"+str+"行数据编码,第"+nameStr+"名称已占用\",\"state\":\"false\"}";
					}else{
						return "{\"error\":\"第"+str+"行数据编码已占用\",\"state\":\"false\"}";
					}
					
				}else{
					return "{\"error\":\"第"+nameStr+"行数据名称已占用\",\"state\":\"false\"}";
				}
			}
			
			if(updateList.size()>0){
				String err_name = "" ;//记录名称重复错误信息
				
				for (int i = 0; i < updateList.size(); i++) {
					for (int j = i + 1; j < addList.size(); j++) {
						
						Map<String,Object>  item = addList.get(i);
						Map<String,Object>  map = addList.get(j);
						if (String.valueOf(item.get("measure_code")).equals(map.get("measure_code"))) {
							err_name += (Integer.parseInt(String.valueOf(item.get("rowNo"))) + 1) + "行与 " + (Integer.parseInt(String.valueOf(map.get("rowNo"))) + 1) + "行;" ;
						}
					}
				}
				
				if(err_name != ""){
					
					return "{\"error\":\"第"+err_name+"数据名称重复\",\"state\":\"false\"}";
				}
				
				//批量 查询添加数据 名称是否已存在
				String  nameStr = budgOperationMeasureMapper.queryNameExistList(updateList) ;
				
				if(nameStr != null){
					return "{\"error\":\"第"+nameStr+"行数据编码,第"+nameStr+"名称已占用\",\"state\":\"false\"}";
				}
				
				int state = budgOperationMeasureMapper.updateBatch(updateList);
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
			
			//return "{\"error\":\"添加失败! 方法 add\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 添加运营尺度<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象运营尺度
		//获取对象医院医保额度控制
		int count = budgOperationMeasureMapper.queryDataExist(entityMap);

		if (count > 0) {

			return "{\"message\":\"数据重复,请重新添加.\",\"state\":\"false\"}";

		}
		
		try {
			
			int state = budgOperationMeasureMapper.add(entityMap);
			
			return "{\"message\":\"添加成功.\",\"state\":\"true\",\"group_id\":\""+entityMap.get("group_id").toString()
					+"\",\"hos_id\":\""+entityMap.get("hos_id").toString()
					+"\",\"copy_code\":\""+entityMap.get("copy_code").toString()+"\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 add\",\"state\":\"false\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加运营尺度<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			String str = "" ;
			
			for(Map<String,Object> item : entityList){
				
				BudgOperationMeasure budgOperationMeasure = queryByCode(item);

				if (budgOperationMeasure != null) {

					str += "运营尺度编码:" + item.get("measure_code")+ ";" ;

				}
				
				int count  = budgOperationMeasureMapper.queryNameExist(item);
				if(count >  0 ){
					str += "运营尺度名称:"+item.get("measure_name")+";" ;
				}
			}
			
			if(str == ""){
				
				budgOperationMeasureMapper.addBatch(entityList);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}else{
				
				return "{\"error\":\"添加失败！"+str+"已被占用\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新运营尺度<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgOperationMeasureMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新运营尺度<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgOperationMeasureMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除运营尺度<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgOperationMeasureMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除运营尺度<BR> 
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
			map.put("dict_code", "BUDG_OPERATION_MEASURE");
			map.put("group_id", entityList.get(0).get("group_id"));
			map.put("hos_id", entityList.get(0).get("hos_id"));
			map.put("copy_code", entityList.get(0).get("copy_code"));
			map.put("acc_year", "");
			map.put("p_flag", "");//包括子科目
			if(entityList!=null && entityList.size()>0){
				for(Map<String, Object> mapVo : entityList){
					storeIdStr+=mapVo.get("measure_code")+",";
					
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
			
			budgOperationMeasureMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加运营尺度<BR> 
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
		//判断是否存在对象运营尺度
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgOperationMeasure> list = (List<BudgOperationMeasure>)budgOperationMeasureMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgOperationMeasureMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgOperationMeasureMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集运营尺度<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgOperationMeasure> list = (List<BudgOperationMeasure>)budgOperationMeasureMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgOperationMeasure> list = (List<BudgOperationMeasure>)budgOperationMeasureMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象运营尺度<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgOperationMeasureMapper.queryByCode(entityMap);
	}
	/**
	 * @Description 
	 * 获取对象运营尺度<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public int queryNameExist(Map<String, Object> mapVo) throws DataAccessException {
		return budgOperationMeasureMapper.queryNameExist(mapVo);
	}
	
	/**
	 * @Description 
	 * 获取运营尺度<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgOperationMeasure
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgOperationMeasureMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取运营尺度<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgOperationMeasure>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgOperationMeasureMapper.queryExists(entityMap);
	}
	
}
