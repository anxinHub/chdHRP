package com.chd.hrp.budg.serviceImpl.project.begin;

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
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.budg.dao.base.BudgSelectMapper;
import com.chd.hrp.budg.dao.project.begin.BudgProjBeginDetailMapper;
import com.chd.hrp.budg.dao.project.begin.BudgProjBeginMapper;
import com.chd.hrp.budg.dao.project.begin.BudgProjDetailYearMapper;
import com.chd.hrp.budg.dao.project.begin.BudgProjYearMapper;
import com.chd.hrp.budg.entity.BudgProjBegain;
import com.chd.hrp.budg.service.project.begin.BudgProjBeginService;
import com.chd.hrp.budg.serviceImpl.common.BudgFunTypeServiceImpl;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @Description:
 * 期初项目预算表
 * @Table:
 * COM_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("budgProjBeginService")
public class BudgProjBeginServiceImpl implements BudgProjBeginService {
	private static Logger logger = Logger.getLogger(BudgFunTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgProjBeginMapper")
	private final BudgProjBeginMapper budgProjBeginMapper = null;
    //引入DAO操作
	@Resource(name = "budgProjBeginDetailMapper")
	private final BudgProjBeginDetailMapper budgProjBeginDetailMapper = null;
	//引入DAO操作
	@Resource(name = "budgProjYearMapper")
	private final BudgProjYearMapper budgProjYearMapper = null;
    //引入DAO操作
	@Resource(name = "budgProjDetailYearMapper")
	private final BudgProjDetailYearMapper budgProjDetailYearMapper = null;
	//引入DAO操作
	@Resource(name = "budgSelectMapper")
	private final BudgSelectMapper budgSelectMapper = null;
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		//获取对象期初项目预算
		BudgProjBegain budgProjBegain = queryByCode(entityMap);

		if (budgProjBegain != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgProjBeginMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
	}

	/**
	 * @Description 
	 * 添加数据   同时操作四张表,做到添加数据的时候，数据能够同步
	 * 表一：期初项目预算明细表
	 * 表二：期初项目预算表
	 * 添加数据的时候必须判断BUDG_PROJ_BEGAIN_MARK:state:0,未记账状态才可以进行添加
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {		
			BudgProjBegain  budgProjBegain=budgProjBeginMapper.querybudgProjBegin(entityMap);
			if(budgProjBegain==null){
			budgProjBeginMapper.addBatch(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
						}else{
			return "{\"msg\":\"数据已经存在,不能再次添加.\",\"state\":\"false\"}";
						}
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
	}
	/**
	 * @Description 
	 * 跟新数据：期初预算项目sate:01新建  02：审核
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/	
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		try {			
			budgProjBeginDetailMapper.update(entityMap);
     		budgProjBeginMapper.update(entityMap);
			//budgProjDetailYearMapper.update(entityMap);	
			//budgProjYearMapper.update(entityMap);	
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"跟新失败  数据库异常 请联系管理员! 方法 update\"}";

			}	
			
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {
     try {
    	 int count=0;
    	 List<Map<String, Object>> addList=new ArrayList<Map<String, Object>>();
    	 List<Map<String, Object>> updateList=new ArrayList<Map<String, Object>>();
    	 for(Map<String,Object> mapVo:entityList){
    		 count=this.querybudgProjBegain(mapVo);
			 if(count==0){	
				 addList.add(mapVo);
			 }else{
				 updateList.add(mapVo);
			 }
    	 }
    	 if(addList.size()>0)
    		 budgProjBeginMapper.addBatch(addList);
    	 if(updateList.size()>0)
    		 budgProjBeginMapper.updateBatch(entityList);			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败! 方法 updateBatch\"}";

		}	
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			//删除---期初预算明细表中的数据
			budgProjBeginDetailMapper.deleteBatch(entityMap);
			//删除---期初预算表表中的数据
			budgProjBeginMapper.deleteBatch(entityMap);	
			//删除--年度项目预算明细表中的数据
			//budgProjDetailYearMapper.deleteBatch(entityMap);
			//删除--年度项目预算表中的数据
			//budgProjYearMapper.deleteBatch(entityMap);					
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}	

	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @Description 
	 * 查询数据   期初项目预算表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
        SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) budgProjBeginMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) budgProjBeginMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {		
		return budgProjBeginMapper.queryByCode(entityMap);
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
	 * @Description 
	 * 资金来源下拉框  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/	
	public String queryBudgSourceId(Map<String, Object> entityMap) throws DataAccessException {
     RowBounds rowBounds = new RowBounds(0, 20);		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}		
		return JSON.toJSONString(budgProjBeginMapper.queryBudgSourceId(entityMap, rowBounds));
	}
	/**
	 * @Description 
	 * 资金来源名称下拉框  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/	
	@Override
	public String queryBudgSourceName(Map<String, Object> entityMap) throws DataAccessException {
		  RowBounds rowBounds = new RowBounds(0, 20);		
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}		
			return JSON.toJSONString(budgProjBeginMapper.queryBudgSourceName(entityMap, rowBounds));
	}
	/**
	 * @Description 
	 * 支出项目下拉框  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	/*public String queryBudgPaymentItemId(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgProjBeginMapper.queryBudgPaymentItemId(entityMap, rowBounds));
	}*/

	/**
	 * @Description 
	 * 支出项目变更号下拉框  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	public String queryBudgPaymentItemNo(Map<String, Object> entityMap) throws DataAccessException {
       RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgProjBeginMapper.queryBudgPaymentItemNo(entityMap, rowBounds));
	}

	/**
	 * @Description 
	 * 期初项目预算记账BUDG_PROJ_BEGAIN_MARK中状态初始为0未记账
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	public Map<String,Object> queryBegainMark(Map<String, Object> mapVo) throws DataAccessException {
	
		return budgProjBeginMapper.queryBegainMark(mapVo);
	}
	/**
	 * @Description 
	 * 期初项目预算记账BUDG_PROJ_BEGAIN中proj_id
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@Override
	public String queryProjId(Map<String, Object> entityMap) throws DataAccessException {
       RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgProjBeginMapper.queryProjId(entityMap, rowBounds));
	}

	/**
	 * @Description 
	 * 期初预算项目审核,点击审核按钮的时候,state:01变成02
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/	
	public String BudgProjBegainAudit(List<Map<String, Object>> listVo) throws DataAccessException {
		budgProjBeginMapper.BudgProjBegainAudit(listVo);		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	/**
	 * @Description 
	 * 期初预算项目消核,点击销审按钮的时候,state:02变成01
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/	
	@Override
	public String UnBudgProjBegainAudit(List<Map<String, Object>> listVo) throws DataAccessException {
		budgProjBeginMapper.UnBudgProjBegainAudit(listVo);	
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}

   //查询表中的数据是否已经存在
	public int queryDataExists(Map<String, Object> mapVo) throws DataAccessException {
		int count=budgProjBeginMapper.queryDataExists(mapVo);
		int countDetail=budgProjBeginDetailMapper.queryDataExists(mapVo);
		int cou=count+countDetail;
		return cou;
	}
	/**
	 * @Description 
	 * 项目名称下拉框  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	/*public String queryBudgProjName(Map<String, Object> entityMap) throws DataAccessException {
      RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgProjBeginMapper.queryBudgProjName(entityMap, rowBounds));
	}*/

	/**
	 * @Description 
	 * 预算下拉框  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	public String queryBudgYear(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgProjBeginMapper.queryBudgYear(mapVo, rowBounds));
	}
	public int querybudgProjBegain(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgProjBeginMapper.querybudgProjBegain(mapVo);
	}

	@Override
	public int querybudgProjBegainDetail(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgProjBeginMapper.querybudgProjBegainDetail(mapVo);
	}

	@Override
	public Map<String,Object> queryB_Usable_Amount(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgProjBeginMapper.queryB_Usable_Amount(mapVo);
	}

	@Override
	public Map<String, Object> queryMainLast(Map<String, Object> mapVo) throws DataAccessException {
		//查询最小年度;
		Map<String,Object> map=budgProjBeginMapper.queryMinYear(mapVo);
		int budg_year=Integer.parseInt(String.valueOf((Integer.parseInt(String.valueOf((String)mapVo.get("budg_year")))-1)));
		int budg_year_min= Integer.parseInt(map.get("BUDG_YEAR_MIN").toString());
		if((budg_year<=budg_year_min)){
		mapVo.put("budg_year",budg_year_min);
		return budgProjBeginMapper.queryMainLastU(mapVo);
		}else{
			mapVo.put("budg_year", String.valueOf((Integer.parseInt(String.valueOf((String)mapVo.get("budg_year")))-1)));
			return budgProjBeginMapper.queryMainLast(mapVo);
		}
		
	}

	@Override
	public Map<String, Object> queryMinYear(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgProjBeginMapper.queryMinYear(mapVo);
	}

	@Override
	public List<Map<String, Object>> queryDetail(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgProjBeginMapper.queryDetail(mapVo);
	}

	@Override
	public Map<String, Object> queryLastMainData(Map<String, Object> hashMap) throws DataAccessException {
		
		return budgProjBeginMapper.queryLastMainData(hashMap) ;
	}

	@Override
	public List<Map<String, Object>> queryLastMainDetailData(Map<String, Object> hashMap) throws DataAccessException {
		return budgProjBeginMapper.queryLastMainDetailData(hashMap) ;
	}
	
	/**
	 * 根据 年度、 项目、资金来源 查询  该年度之后相同项目、资金来源是否存在数据（若存在则不能录入）
	 */
	@Override
	public int queryNextDataExist(Map<String, Object> mapVo) {
		
		return budgProjBeginMapper.queryNextDataExist(mapVo) ;
	}
	
	/**
	 * 根据选定 项目、资金来源  查询 项目负责人、期初预算余额、期初可用余额 
	 */
	@Override
	public String qureyInfoData(Map<String, Object> mapVo) throws DataAccessException {
		//根据选定 项目、资金来源  查询 期初预算余额、期初可用余额
		Map<String,Object> map = budgProjBeginMapper.qureyInfoData(mapVo);
		
		// 根据选定 项目、资金来源  查询 项目负责人
		Map<String,Object> empMap = budgProjBeginMapper.qureyProjEmp(mapVo);
		
		if(map != null ){
			if(empMap != null){
				return "{\"con_emp_name\":\""+ empMap.get("con_emp_name")+"\",\"b_remain_amount\":\""+map.get("remain_amount")+
						"\",\"b_usable_amount\":\""+map.get("usable_amount")+"\",\"state\":\"true\"}";
			}else{
				return "{\"b_remain_amount\":\""+map.get("remain_amount")+"\",\"b_usable_amount\":\""
						+map.get("usable_amount")+"\",\"state\":\"true\"}";
			}
			
		}else{
			
			if(empMap != null){
				
				return "{\"con_emp_name\":\""+ empMap.get("con_emp_name")+"\",\"state\":\"true\"}";
			}else{
				
				return "{\"state\":\"true\"}";
			}
			
		}
		
	}
	
	/**
	 * 
	 *  明细表 根据 选定 项目、资金来源、支出项目后  查询 期初预算余额 
	 */
	@Override
	public String qureyInfoDataDetail(Map<String, Object> mapVo) throws DataAccessException {
		
		//明细表 根据 选定 项目、资金来源、支出项目后  查询 期初预算余额 
		Map<String,Object> map = budgProjBeginMapper.qureyInfoDataDetail(mapVo);
		
		
		if(map != null ){
			
				return "{\"b_remain_amount\":\""+map.get("remain_amount")+"\",\"state\":\"true\"}";
			
		}else{
			
			return "{\"state\":\"true\"}";
			
		}
	}

	/**
	 * 根据 年度、 项目、资金来源、支出项目  查询  该年度之后相同项目、资金来源、支出项目是否存在数据（若存在则不能录入）
	 */
	@Override
	public int queryNextDataDetailExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgProjBeginMapper.queryNextDataDetailExist(mapVo) ;
	}

	public String importBudgProjBegain(Map<String, Object> map) throws DataAccessException {
		/*//查询最小年度
		 Map<String, Object>   min_year=budgProjBeginMapper.queryMinYear(map);*/
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
			entityMap.put("state", "01");//状态（state）01新建02审核
			
			//查询资金来源名称
			List<Map<String,Object>> listSourceIdDict = budgProjBeginMapper.queryBudgSourceNam(entityMap);		
			// 判断资金来源是否存在用 map key为项目的id ， value : 、code等信息
			Map<String, Map<String, Object>> dictMap = new HashMap<String, Map<String, Object>>();
			for(Map<String, Object> dict : listSourceIdDict){
				if(dict.get("source_name") != null){
					dictMap.put(dict.get("source_name").toString(), dict);//改成name
					
				}
			}
			
			
			//查询项目编码
			List<Map<String,Object>> listProjDict = budgProjBeginMapper.queryProjNam(entityMap);
			// 判断项目ID是否存在用 map key为项目的id ， value : 、code等信息
			Map<String, Map<String, Object>> dictProjMap = new HashMap<String, Map<String, Object>>();
			for(Map<String, Object> dict : listProjDict){
				if(dict.get("proj_code") != null){//改成code
					dictProjMap.put(dict.get("proj_code").toString(), dict);
					
				}
			}
			
			
			
			
			List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
			
			
			for(Map<String,List<String>> item : liData ){
				
				
				List<String> budg_year = item.get("预算年度") ;
				
				List<String> proj_id = item.get("项目名称") ;
				
				List<String> source_id = item.get("资金来源名称") ;
				
				List<String> budg_amount = item.get("预算金额") ;
				
				List<String> in_amount = item.get("到账金额") ;
				
				List<String> cost_amount = item.get("支出金额") ;

	

                List<String> b_remain_amount = item.get("期初预算余额") ;
				
				List<String> b_usable_amount = item.get("期初可用余额") ;
				
				if(budg_year == null){
					
					return "{\"warn\":\"" + budg_year.get(0) + ",预算年度为空！\",\"state\":\"false\",\"row_cell\":\"" + budg_year.get(0) + "\"}";
					
				}
				
				if(proj_id == null){
					//get(0)获取坐标
					return "{\"warn\":\"" + proj_id.get(0) + ",项目编码！\",\"state\":\"false\",\"row_cell\":\"" + proj_id.get(0) + "\"}";
				
				}else{
					
					if(dictProjMap.get(proj_id.get(1)) == null){
						//get(1)获取单元格的值
						return "{\"warn\":\"" + proj_id.get(0) + ",项目编码！\",\"state\":\"false\",\"row_cell\":\"" + proj_id.get(0) + "\"}";
					}
				}
				
				if(source_id == null){
					
					return "{\"warn\":\"" + source_id.get(0) + ",资金来源名称！\",\"state\":\"false\",\"row_cell\":\"" + source_id.get(0) + "\"}";
				
				}else{
					
					if(dictMap.get(source_id.get(1)) == null){
						
						return "{\"warn\":\"" + source_id.get(0) + ",资金来源名称！\",\"state\":\"false\",\"row_cell\":\"" + source_id.get(0) + "\"}";
					}
				}
				
				
				if(budg_amount == null){
					
					return "{\"warn\":\"" + budg_amount.get(0) + ",预算金额为空！\",\"state\":\"false\",\"row_cell\":\"" + budg_amount.get(0) + "\"}";
					
				}else{
					
					 try{
						    Double.parseDouble((budg_amount.get(1)));
						   
					 }catch(NumberFormatException e){
						 
						 return "{\"warn\":\"" + budg_amount.get(0) + ",预算金额输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + budg_amount.get(0) + "\"}";
					  }
					
				}
                if(in_amount == null){
					
					return "{\"warn\":\"" + in_amount.get(0) + ",到账金额为空！\",\"state\":\"false\",\"row_cell\":\"" + in_amount.get(0) + "\"}";
					
				}else{
					
					 try{
						    Double.parseDouble((in_amount.get(1)));
						   
					 }catch(NumberFormatException e){
						 
						 return "{\"warn\":\"" + in_amount.get(0) + ",到账金额输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + in_amount.get(0) + "\"}";
					  }
					
				}
                if(cost_amount == null){
					
					return "{\"warn\":\"" + cost_amount.get(0) + ",支出金额为空！\",\"state\":\"false\",\"row_cell\":\"" + cost_amount.get(0) + "\"}";
					
				}else{
					
					 try{
						    Double.parseDouble((cost_amount.get(1)));
						   
					 }catch(NumberFormatException e){
						 
						 return "{\"warn\":\"" + cost_amount.get(0) + ",支出金额输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + cost_amount.get(0) + "\"}";
					  }
					
				}
				//存放 正确信息用Map
				Map<String,Object> returnMap = new HashMap<String,Object>();
				
				returnMap.put("group_id", SessionManager.getGroupId());
				
				returnMap.put("hos_id", SessionManager.getHosId());
				
				returnMap.put("copy_code", SessionManager.getCopyCode());
							
				returnMap.put("budg_year", budg_year.get(1));
				
				returnMap.putAll(dictMap.get(source_id.get(1)));
				
	            returnMap.putAll(dictProjMap.get(proj_id.get(1)));
				
				returnMap.put("budg_amount", budg_amount.get(1));
				
				returnMap.put("in_amount", in_amount.get(1));
				
				returnMap.put("cost_amount", cost_amount.get(1));
				returnMap.put("maker",0);
				returnMap.put("make_date",null);
				returnMap.put("checker", 0);
				returnMap.put("check_date",null);
				returnMap.put("b_remain_amount",b_remain_amount.get(1));
				returnMap.put("b_usable_amount",b_usable_amount.get(1));    
				returnMap.put("state","01"); 
				returnMap.put("remain_amount",Double.parseDouble(budg_amount.get(1).toString())-Double.parseDouble(cost_amount.get(1).toString())+Double.parseDouble(b_remain_amount.get(1).toString()));
				
				returnMap.put("usable_amount",Double.parseDouble(in_amount.get(1).toString())-Double.parseDouble(cost_amount.get(1).toString())+Double.parseDouble(b_usable_amount.get(1).toString()));
				
				returnList.add(returnMap);
				
			}
			StringBuffer err_sb = new StringBuffer();
			
			//校验 数据重复
			for( int i = 1; i < returnList.size(); i++ ){
				
				for(int j = i + 1 ; j < returnList.size(); j++){
					
					
					if(returnList.get(i).get("budg_year").equals(returnList.get(j).get("budg_year")) && returnList.get(i).get("proj_id").equals(returnList.get(j).get("proj_id"))&& returnList.get(i).get("source_id").equals(returnList.get(j).get("source_id")) ){
						
						err_sb.append(returnList.get(i).get("budg_year")+"预算年度,"+returnList.get(i).get("proj_id")+"项目Id"+returnList.get(i).get("source_id")+"资金来源Id");  
					}
				}
				
			}
			
			if( err_sb.length() > 0){
				
				 return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
				
			}else{
			
				addBatch(returnList);
			
				
				
				
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}
           }catch(Exception e){
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String importBudgProjBegainDetail(Map<String, Object> map) throws DataAccessException {
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
				
				//查询资金来源名称
				List<Map<String,Object>> listSourceIdDict = budgProjBeginMapper.queryBudgSourceNam(entityMap);		
				// 判断资金来源是否存在用 map key为项目的id ， value : 、code等信息
				Map<String, Map<String, Object>> dictMap = new HashMap<String, Map<String, Object>>();
				//存放 正确信息用Map
				Map<String,Object> returnMap = new HashMap<String,Object>();
				for(Map<String, Object> dict : listSourceIdDict){
					if(dict.get("source_name") != null){
						dictMap.put(dict.get("source_name").toString(), dict);//改成name
						
					}
				}
				
				
				//查询项目编码
				List<Map<String,Object>> listProjDict = budgProjBeginMapper.queryProjNam(entityMap);
				// 判断项目ID是否存在用 map key为项目的id ， value : 、code等信息
				Map<String, Map<String, Object>> dictProjMap = new HashMap<String, Map<String, Object>>();
				for(Map<String, Object> dict : listProjDict){
					if(dict.get("proj_code") != null){//改成code
						dictProjMap.put(dict.get("proj_code").toString(), dict);
						
					}
				}
				//查询支出项目编码
				List<Map<String,Object>> listPaymentItem = budgProjBeginMapper.queryPaymentItem(entityMap);
				// 判断项目ID是否存在用 map key为项目的id ， value : 、code等信息
				Map<String, Map<String, Object>> PaymentItemMap = new HashMap<String, Map<String, Object>>();
				
				for(Map<String, Object> dict : listPaymentItem){
					if(dict.get("payment_item_name") != null){//改成code
						PaymentItemMap.put(dict.get("payment_item_name").toString(), dict);
						
					}
				}
				
				
				
				List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
				
				
				for(Map<String,List<String>> item : liData ){
					
		
					List<String> budg_year = item.get("预算年度") ;
					
					List<String> proj_id = item.get("项目名称") ;
					
					List<String> source_id = item.get("资金来源") ;
					
					List<String> payment_item_name = item.get("支出项目") ;
					
					List<String> budg_amount = item.get("预算金额") ;
					
					List<String> cost_amount = item.get("支出金额") ;

	                List<String> b_remain_amount = item.get("期初预算余额") ;
					
					
					if(budg_year == null){
						
						return "{\"warn\":\"" + budg_year.get(0) + ",预算年度为空！\",\"state\":\"false\",\"row_cell\":\"" + budg_year.get(0) + "\"}";
						
					}
					
					if(proj_id == null){
						//get(0)获取坐标
						return "{\"warn\":\"" + proj_id.get(0) + ",项目编码！\",\"state\":\"false\",\"row_cell\":\"" + proj_id.get(0) + "\"}";
					
					}else{
						
						if(dictProjMap.get(proj_id.get(1)) == null){
							//get(1)获取单元格的值
							return "{\"warn\":\"" + proj_id.get(0) + ",项目编码！\",\"state\":\"false\",\"row_cell\":\"" + proj_id.get(0) + "\"}";
						}
					}
					
					if(source_id == null){
						
						return "{\"warn\":\"" + source_id.get(0) + ",资金来源名称！\",\"state\":\"false\",\"row_cell\":\"" + source_id.get(0) + "\"}";
					
					}else{
						
						if(dictMap.get(source_id.get(1)) == null){
							
							return "{\"warn\":\"" + source_id.get(0) + ",资金来源名称！\",\"state\":\"false\",\"row_cell\":\"" + source_id.get(0) + "\"}";
						}
					}
					
					if(payment_item_name == null){
						
						return "{\"warn\":\"" + payment_item_name.get(0) + ",支出项目！\",\"state\":\"false\",\"row_cell\":\"" + payment_item_name.get(0) + "\"}";
					
					}else{
						
						if(PaymentItemMap.get(payment_item_name.get(1)) == null){
							
							return "{\"warn\":\"" + payment_item_name.get(0) + ",支出项目！\",\"state\":\"false\",\"row_cell\":\"" + payment_item_name.get(0) + "\"}";
						}
					}
					
					if(budg_amount == null){
						
						return "{\"warn\":\"" + budg_amount.get(0) + ",预算金额为空！\",\"state\":\"false\",\"row_cell\":\"" + budg_amount.get(0) + "\"}";
						
					}else{
						
						 try{
							    Double.parseDouble((budg_amount.get(1)));
							   
						 }catch(NumberFormatException e){
							 
							 return "{\"warn\":\"" + budg_amount.get(0) + ",预算金额输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + budg_amount.get(0) + "\"}";
						  }
						
					}
	                
	                if(cost_amount == null){
						
						return "{\"warn\":\"" + cost_amount.get(0) + ",支出金额为空！\",\"state\":\"false\",\"row_cell\":\"" + cost_amount.get(0) + "\"}";
						
					}else{
						
						 try{
							    Double.parseDouble((cost_amount.get(1)));
							   
						 }catch(NumberFormatException e){
							 
							 return "{\"warn\":\"" + cost_amount.get(0) + ",支出金额输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + cost_amount.get(0) + "\"}";
						  }
						
					}
					
					
					returnMap.put("group_id", SessionManager.getGroupId());
					
					returnMap.put("hos_id", SessionManager.getHosId());
					
					returnMap.put("copy_code", SessionManager.getCopyCode());
								
					returnMap.put("budg_year", budg_year.get(1));
					
					returnMap.putAll(dictMap.get(source_id.get(1)));
					
		            returnMap.putAll(dictProjMap.get(proj_id.get(1)));
		            
		            returnMap.putAll(PaymentItemMap.get(payment_item_name.get(1)));
		            
		            
					returnMap.put("budg_amount", budg_amount.get(1));
					

					
					returnMap.put("cost_amount", cost_amount.get(1));

					returnMap.put("b_remain_amount",b_remain_amount.get(1));
				  
					returnMap.put("remain_amount",Double.parseDouble(budg_amount.get(1).toString())-Double.parseDouble(cost_amount.get(1).toString())+Double.parseDouble(b_remain_amount.get(1).toString()));

					
					returnList.add(returnMap);
					
				}
				StringBuffer err_sb = new StringBuffer();
				
				//校验 数据重复
				for( int i = 1; i < returnList.size(); i++ ){
					
					for(int j = i + 1 ; j < returnList.size(); j++){
						
						
						if(returnList.get(i).get("budg_year").equals(returnList.get(j).get("budg_year")) && returnList.get(i).get("proj_id").equals(returnList.get(j).get("proj_id"))&& returnList.get(i).get("source_id").equals(returnList.get(j).get("source_id")) &&returnList.get(i).get("payment_item_id").equals(returnList.get(j).get("payment_item_id"))){
							
							err_sb.append(returnList.get(i).get("budg_year")+"预算年度,"+returnList.get(i).get("proj_id")+"项目Id"+returnList.get(i).get("source_id")+"资金来源Id"+returnList.get(i).get("payment_item_id")+"支出项目Id");  
						}
					}
					
				}
				
				if( err_sb.length() > 0){
					
					 return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
					
				}else{
				
					Map<String,Object> ma=budgProjBeginMapper.queryKey(returnMap);
					if(ma!=null){
					budgProjBeginDetailMapper.addBatch(returnList);
					return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
					}else{
					
					
					
					return "{\"msg\":\"你的主表里面没数，自己检查检查.\",\"state\":\"false\"}";
					}
				}
	           }catch(Exception e){
				e.printStackTrace();
				logger.debug(e.getMessage());
				throw new SysException(e.getMessage());
	           }
	}
}