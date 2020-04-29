/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.compilationbasic.dbzprice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.budg.dao.business.compilationbasic.dbzprice.BudgDbzPriceMapper;
import com.chd.hrp.budg.dao.business.compilationbasic.workhosdbz.BudgWorkHosDbzMapper;
import com.chd.hrp.budg.dao.business.compilationbasic.zeyfincome.BudgZeyfIncomeHisMapper;
import com.chd.hrp.budg.entity.BudgDbzPrice;
import com.chd.hrp.budg.service.business.compilationbasic.dbzprice.BudgDbzPriceService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 单病种收费标准
 * @Table:
 * BUDG_DBZ_PRICE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgDbzPriceService")
public class BudgDbzPriceServiceImpl implements BudgDbzPriceService {

	private static Logger logger = Logger.getLogger(BudgDbzPriceServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgDbzPriceMapper")
	private final BudgDbzPriceMapper budgDbzPriceMapper = null;
	
	//引入DAO操作
	@Resource(name = "budgWorkHosDbzMapper")
	private final BudgWorkHosDbzMapper budgWorkHosDbzMapper = null;
	
	//引入DAO操作
	@Resource(name = "budgZeyfIncomeHisMapper")
	
	private final BudgZeyfIncomeHisMapper budgZeyfIncomeHisMapper = null;
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	/**
	 * 保存数据 
	 */
	@Override
	public String save(List<Map<String, Object>> listVo) throws DataAccessException{
		
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> rate = new ArrayList<Map<String,Object>>();//科室年度业务概率预算  用List
		
		for(Map<String,Object> item : listVo){
			
			if("1".equals(item.get("flag"))){ //添加
				
				addList.add(item) ;
				
			}else{ //修改
				
				updateList.add(item) ;
			}
			
		}
		
		try {
			
			if(addList.size()>0){
				//批量 查询 添加数据是否已存在
				String  str = budgDbzPriceMapper.queryDataExistList(addList) ;
				
				if(str == null){
					
					int count = budgDbzPriceMapper.addBatch(addList);
					
				}else{
					
					return "{\"error\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
			}
			
			if(updateList.size()>0){
				
				int state = budgDbzPriceMapper.updateBatch(updateList);
				
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}
		
	}
	/**
	 * @Description 
	 * 添加单病种收费标准<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象单病种收费标准
		int count = queryDataExist(entityMap);

		if (count > 0) {

			return "{\"message\":\"数据重复,请重新添加.\",\"state\":\"false\"}";

		}
		
		try {
			
			int state = budgDbzPriceMapper.add(entityMap);
			
			return "{\"message\":\"添加成功.\",\"state\":\"true\",\"group_id\":\""+entityMap.get("group_id").toString()
					+"\",\"hos_id\":\""+entityMap.get("hos_id").toString()
					+"\",\"copy_code\":\""+entityMap.get("copy_code").toString()+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 add\",\"state\":\"false\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加单病种收费标准<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		String str = "";
		
		try {
			
			for(Map<String,Object> item :entityList){
				
				int count  = budgDbzPriceMapper.queryDataExist(item);
				
				if(count >  0){
					
					str += "医保类型:" + item.get("insurance_name") + " 医保单病种:" + item.get("disease_name") ;
					
				}
						
			}
			
			if(str != ""){
				
				return "{\"error\":\"添加失败！以下数据:【"+str+"】已存在.\",\"state\":\"false\"}";
				
			}else{
				
				budgDbzPriceMapper.addBatch(entityList);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
				
			}
			

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 addBatch\",\"state\":\"false\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新单病种收费标准<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgDbzPriceMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新单病种收费标准<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgDbzPriceMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除单病种收费标准<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgDbzPriceMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除单病种收费标准<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgDbzPriceMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加单病种收费标准<BR> 
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
		//判断是否存在对象单病种收费标准
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgDbzPrice> list = (List<BudgDbzPrice>)budgDbzPriceMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgDbzPriceMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgDbzPriceMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集单病种收费标准<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgDbzPriceMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgDbzPriceMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象单病种收费标准<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgDbzPriceMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取单病种收费标准<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgDbzPrice
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgDbzPriceMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取单病种收费标准<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgDbzPrice>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		
		return budgDbzPriceMapper.queryExists(entityMap);
	}
	/**
	 * 根据医保单病种编码  查询医保类型编码是否存在（查询 医保单病种维护表 BUDG_YB_SINGLE_DISEASE ）
	 */
	@Override
	public int queryBudgInsuranceCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return  budgDbzPriceMapper.queryBudgInsuranceCode(entityMap); 
	}
	
	/**
	 * 查询医保单病种编码是否存在
	 */
	@Override
	public int queryBudgDiseaseCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return  budgDbzPriceMapper.queryBudgDiseaseCode(entityMap); 
	}
	
	/**
	 * 医保单病种下拉框 添加页面用
	 */
	@Override
	public String queryBudgSingleDC(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}		
		return  JSON.toJSONString(budgDbzPriceMapper.queryBudgSingleDC(entityMap,rowBounds)); 
	}
	
	/**
	 * 根据主键 查询数据是否存在
	 */
	@Override
	public int queryDataExist(Map<String, Object> mapVo) {
		
		return  budgDbzPriceMapper.queryDataExist(mapVo); 
	}
	
	/**
	 * 查询要生成的数据
	 */
	@Override
	public List<Map<String, Object>> queryDate(Map<String, Object> mapVo) {
		
		return budgDbzPriceMapper.queryDate(mapVo); 
	}
	
	/**
	 * 医保类型下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryBudgYBTY(Map<String, Object> entityMap) throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgDbzPriceMapper.queryBudgYBTY(entityMap, rowBounds));
	}
	@Override
	public String budgDbzPriceImportNewPage(Map<String, Object> map) throws DataAccessException {
		try{
			
			String content=map.get("content").toString();
			
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			Map<String, Object> entityMap=new HashMap<String,Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
			//查询医保类型编码
			List<Map<String,Object>> listInsurancecodeDict = budgZeyfIncomeHisMapper.queryBudgHosInsuranceCode(entityMap);		
			// 判断资金来源是否存在用 map key为项目的id ， value : 、code等信息
			Map<String, Map<String, Object>> dictMap = new HashMap<String, Map<String, Object>>();
		
			for(Map<String, Object> dict : listInsurancecodeDict){
				if(dict.get("insurance_name") != null){
					dictMap.put(dict.get("insurance_name").toString(), dict);//改成name
					
				}
			}
			
			//查询病种编码
			List<Map<String,Object>> listDiseaseCodeDict = budgWorkHosDbzMapper.queryBudgDiseaseCodeId(entityMap);		
			// 判断资金来源是否存在用 map key为项目的id ， value : 、code等信息
			Map<String, Map<String, Object>> dictDeptMap = new HashMap<String, Map<String, Object>>();
		
			for(Map<String, Object> dict : listDiseaseCodeDict){
				if(dict.get("disease_name") != null){
					dictDeptMap.put(dict.get("disease_name").toString(), dict);//改成name
					
				}
			}
			
			List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
			
			
			for(Map<String,List<String>> item : liData ){
				
				List<String> budg_year = item.get("年度【必填】") ;
				
				List<String> insurance_name = item.get("医保类型名称【必填】") ;
				
				List<String> disease_name = item.get("病种名称【必填】") ;
			
				
				List<String> charge_standard = item.get("收费标准(元)【必填】") ;
				
				if(budg_year == null){
					
					return "{\"warn\":\"" + budg_year.get(0) + ",年度为空！\",\"state\":\"false\",\"row_cell\":\"" + budg_year.get(0) + "\"}";
					
				}
				
				if(insurance_name == null){
					//get(0)获取坐标
					return "{\"warn\":\"" + insurance_name.get(0) + ",医保类型名称！\",\"state\":\"false\",\"row_cell\":\"" + insurance_name.get(0) + "\"}";
				
				}else{
					
					if(dictMap.get(insurance_name.get(1)) == null){
						//get(1)获取单元格的值
						return "{\"warn\":\"" + insurance_name.get(0) + ",医保类型名称！\",\"state\":\"false\",\"row_cell\":\"" + insurance_name.get(0) + "\"}";
					}
				}
				if(disease_name == null){
					//get(0)获取坐标
					return "{\"warn\":\"" + disease_name.get(0) + ",病种名称！\",\"state\":\"false\",\"row_cell\":\"" + disease_name.get(0) + "\"}";
				
				}else{
					
					if(dictDeptMap.get(disease_name.get(1)) == null){
						//get(1)获取单元格的值
						return "{\"warn\":\"" + disease_name.get(0) + ",病种名称！\",\"state\":\"false\",\"row_cell\":\"" + disease_name.get(0) + "\"}";
					}
				}
				
               if(charge_standard == null){
					
					return "{\"warn\":\"" + charge_standard.get(0) + ",收费标准为空！\",\"state\":\"false\",\"row_cell\":\"" + charge_standard.get(0) + "\"}";
					
				}else{
					
					 try{
						    Double.parseDouble((charge_standard.get(1)));
						   
					 }catch(NumberFormatException e){
						 
						 return "{\"warn\":\"" + charge_standard.get(0) + ",收费标准输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + charge_standard.get(0) + "\"}";
					  }
					
				}
				
               
				
				//存放 正确信息用Map
				Map<String,Object> returnMap = new HashMap<String,Object>();
				
				returnMap.put("group_id", SessionManager.getGroupId());
				
				returnMap.put("hos_id", SessionManager.getHosId());
				
				returnMap.put("copy_code", SessionManager.getCopyCode());
				
				returnMap.put("charge_standard", charge_standard.get(1));
							
				returnMap.put("budg_year", budg_year.get(1));
				

				
				returnMap.putAll(dictDeptMap.get(disease_name.get(1)));
				
				returnMap.putAll(dictMap.get(insurance_name.get(1)));
				
				returnList.add(returnMap);
				
			}
			StringBuffer err_sb = new StringBuffer();
			
			//校验 数据重复
			for( int i = 0; i < returnList.size(); i++ ){
				
				for(int j = i + 1 ; j < returnList.size(); j++){
					
					
					if(returnList.get(i).get("disease_name").equals(returnList.get(j).get("disease_name")) &&
					   returnList.get(i).get("budg_year").equals(returnList.get(j).get("budg_year")) && 
					   returnList.get(i).get("insurance_name").equals(returnList.get(j).get("insurance_name"))){
						
						err_sb.append(returnList.get(i).get("disease_name")+"病种名称,"+returnList.get(i).get("year")+"年度,"+returnList.get(i).get("insurance_name")+"医保类型名称");  
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
