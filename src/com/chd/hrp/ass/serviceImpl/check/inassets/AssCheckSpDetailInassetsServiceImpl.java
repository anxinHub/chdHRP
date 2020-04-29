/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.check.inassets;

import java.util.*;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.check.inassets.AssCheckSpDetailInassetsMapper;
import com.chd.hrp.ass.entity.check.inassets.AssCheckSpDetailInassets;
import com.chd.hrp.ass.service.check.inassets.AssCheckSpDetailInassetsService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 051101 仓库盘盈登记明细_无形资产
 * @Table:
 * ASS_CHECK_SP_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assCheckSpDetailInassetsService")
public class AssCheckSpDetailInassetsServiceImpl implements AssCheckSpDetailInassetsService {

	private static Logger logger = Logger.getLogger(AssCheckSpDetailInassetsServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assCheckSpDetailInassetsMapper")
	private final AssCheckSpDetailInassetsMapper assCheckSpDetailInassetsMapper = null;
    
	/**
	 * @Description 
	 * 添加051101 仓库盘盈登记明细_医用设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象051101 仓库盘盈登记明细_医用设备
		AssCheckSpDetailInassets assCheckSpDetailInassets = queryByCode(entityMap);

		if (assCheckSpDetailInassets != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assCheckSpDetailInassetsMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加051101 仓库盘盈登记明细_医用设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assCheckSpDetailInassetsMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新051101 仓库盘盈登记明细_医用设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assCheckSpDetailInassetsMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新051101 仓库盘盈登记明细_医用设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assCheckSpDetailInassetsMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除051101 仓库盘盈登记明细_医用设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assCheckSpDetailInassetsMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除051101 仓库盘盈登记明细_医用设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assCheckSpDetailInassetsMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加051101 仓库盘盈登记明细_医用设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		List<Map<String,Object>> listVo = new ArrayList<Map<String,Object>>();
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
		try{	
			
			for(Map map : detail){    
				Map<String,Object> mapVo = new HashMap<String, Object>();
				if (map.get("ass_id") == null || "".equals(map.get("ass_id"))) {
					continue;
				}
				String ass_id_no = map.get("ass_id").toString();
				
				mapVo.put("group_id",entityMap.get("group_id").toString());
				mapVo.put("hos_id",entityMap.get("hos_id").toString());
				mapVo.put("copy_code",entityMap.get("copy_code").toString());
				mapVo.put("check_plan_no",entityMap.get("check_plan_no").toString());
				mapVo.put("check_no",entityMap.get("check_no").toString());
				mapVo.put("store_id",entityMap.get("store_id").toString());
				mapVo.put("store_no",entityMap.get("store_no").toString());
				
				mapVo.put("ass_id", ass_id_no.split("@")[0].toString());
				mapVo.put("ass_no", ass_id_no.split("@")[1].toString());
				
				
				if(map.get("ass_card_no") != null && !map.get("ass_card_no").equals("")){
					mapVo.put("ass_card_no",map.get("ass_card_no").toString());
				}else{
					mapVo.put("ass_card_no","");
				}
				
				if(map.get("acc_amount") != null && !map.get("acc_amount").equals("")){
					mapVo.put("acc_amount",map.get("acc_amount").toString());
				}else{
					mapVo.put("acc_amount","0");
				}
				
				if(map.get("price") != null && !map.get("price").equals("")){
					mapVo.put("price",map.get("price").toString());
				}else{
					mapVo.put("price","0");
				}
				
				if(map.get("add_depre") != null && !map.get("add_depre").equals("")){
					mapVo.put("add_depre",map.get("add_depre").toString());
				}else{
					mapVo.put("add_depre","0");
				}
				
				if(map.get("add_depre_month") != null && !map.get("add_depre_month").equals("")){
					mapVo.put("add_depre_month",map.get("add_depre_month").toString());
				}else{
					mapVo.put("add_depre_month","0");
				}
				
				if(map.get("cur_money") != null && !map.get("cur_money").equals("")){
					mapVo.put("cur_money",map.get("cur_money").toString());
				}else{
					mapVo.put("cur_money","0");
				}
				
				if(map.get("fore_money") != null && !map.get("fore_money").equals("")){
					mapVo.put("fore_money",map.get("fore_money").toString());
				}else{
					mapVo.put("fore_money","0");
				}
				
				if(map.get("p_reason") != null && !map.get("p_reason").equals("")){
					mapVo.put("p_reason",map.get("p_reason").toString());
				}else{
					mapVo.put("p_reason","");
				}
				
				listVo.add(mapVo);
			}
			assCheckSpDetailInassetsMapper.deleteBatch(listVo);
			assCheckSpDetailInassetsMapper.addBatch(listVo);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	/**
	 * @Description 
	 * 查询结果集051101 仓库盘盈登记明细_医用设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssCheckSpDetailInassets> list = (List<AssCheckSpDetailInassets>)assCheckSpDetailInassetsMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssCheckSpDetailInassets> list = (List<AssCheckSpDetailInassets>)assCheckSpDetailInassetsMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象051101 仓库盘盈登记明细_医用设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assCheckSpDetailInassetsMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051101 仓库盘盈登记明细_医用设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssCheckSpDetailInassets
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assCheckSpDetailInassetsMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051101 仓库盘盈登记明细_医用设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssCheckSpDetailInassets>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assCheckSpDetailInassetsMapper.queryExists(entityMap);
	}
	//新版打印  调用的方法
		@Override
		public Map<String, Object> queryAssInSpecialByPrintTemlatePrints(Map<String, Object> entityMap)throws DataAccessException {
			
			try{
				
				Map<String,Object> reMap=new HashMap<String,Object>();
				WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
				AssCheckSpDetailInassetsMapper assCheckSpDetailInassetsMapper = (AssCheckSpDetailInassetsMapper)context.getBean("assCheckSpDetailInassetsMapper");
				 
			/*	//主页面 批量打印查询
				if("1".equals(String.valueOf(entityMap.get("p_num")))){
					
					//查询 专用设备 入库主表
					List<Map<String,Object>> map= assCheckSdetailSpecialMapper.queryAssInSpecialPrintTemlateByMainBatch(entityMap);
					//查询 专用设备  入库明细表
					List<Map<String,Object>> list= assCheckSdetailSpecialMapper.queryAssInSpecialPrintTemlateByDetail(entityMap);
					
					reMap.put("main", map);
					
					reMap.put("detail", list); 
					
					return reMap;
					
				}else{ *///修改页面 打印查询
					//
					Map<String,Object> map= assCheckSpDetailInassetsMapper.queryAssInSpecialPrintTemlateByMains(entityMap);
					//查询 专用设备  入库明细表
					List<Map<String,Object>> list= assCheckSpDetailInassetsMapper.queryAssInSpecialPrintTemlateByDetails(entityMap);
					
				
					reMap.put("main", map);
					
					reMap.put("detail", list);
					
					return reMap;
					
				/*}*/
				
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				throw new SysException(e.getMessage());
			}
			
			
		}
}
