/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.allot.out;

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
import com.chd.hrp.ass.dao.allot.out.AssAllotOutDetailInassetsMapper;
import com.chd.hrp.ass.dao.allot.out.AssAllotOutInassetsMapper;
import com.chd.hrp.ass.dao.allot.out.source.AssAllotOutSourceInassetsMapper;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutDetailInassets;
import com.chd.hrp.ass.service.allot.out.AssAllotOutDetailInassetsService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050901 资产无偿调拨出库单明细(无形资产)
 * @Table:
 * ASS_ALLOT_OUT_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assAllotOutDetailInassetsService")
public class AssAllotOutDetailInassetsServiceImpl implements AssAllotOutDetailInassetsService {

	private static Logger logger = Logger.getLogger(AssAllotOutDetailInassetsServiceImpl.class);
	//引入DAO操作
		@Resource(name = "assAllotOutDetailInassetsMapper")
		private final AssAllotOutDetailInassetsMapper assAllotOutDetailInassetsMapper = null;
		
		@Resource(name = "assAllotOutSourceInassetsMapper")
		private final AssAllotOutSourceInassetsMapper assAllotOutSourceInassetsMapper = null;
		
		@Resource(name = "assAllotOutInassetsMapper")
		private final AssAllotOutInassetsMapper assAllotOutInassetsMapper = null;
	    
		/**
		 * @Description 
		 * 添加050901 资产无偿调拨出库单明细(无形资产)<BR> 
		 * @param  entityMap
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String add(Map<String,Object> entityMap)throws DataAccessException{
			
			//获取对象050901 资产无偿调拨出库单明细(无形资产)
			AssAllotOutDetailInassets assAllotOutDetailInassets = queryByCode(entityMap);

			if (assAllotOutDetailInassets != null) {

				return "{\"error\":\"数据重复,请重新添加.\"}";

			}
			
			try {
				
				int state = assAllotOutDetailInassetsMapper.add(entityMap);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

			}
			
		}
		/**
		 * @Description 
		 * 批量添加050901 资产无偿调拨出库单明细(无形资产)<BR> 
		 * @param  entityList
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
			
			try {
				
				assAllotOutDetailInassetsMapper.addBatch(entityList);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

			}
			
		}
		
			/**
		 * @Description 
		 * 更新050901 资产无偿调拨出库单明细(无形资产)<BR> 
		 * @param  entityMap
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String update(Map<String,Object> entityMap)throws DataAccessException{
			
			try {
				
			  int state = assAllotOutDetailInassetsMapper.update(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

			}	
			
		}
		/**
		 * @Description 
		 * 批量更新050901 资产无偿调拨出库单明细(无形资产)<BR> 
		 * @param  entityList
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
			
			try {
				
			  assAllotOutDetailInassetsMapper.updateBatch(entityList);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

			}	
			
		}
		/**
		 * @Description 
		 * 删除050901 资产无偿调拨出库单明细(无形资产)<BR> 
		 * @param  entityMap
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
	    public String delete(Map<String, Object> entityMap) throws DataAccessException {
	    	
	    try {
				
				int state = assAllotOutDetailInassetsMapper.delete(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

			}	
			
	  }
	    
		/**
		 * @Description 
		 * 批量删除050901 资产无偿调拨出库单明细(无形资产)<BR> 
		 * @param  entityList
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
			
			try {
				Map<String, Object> inMapVo =new HashMap<String, Object>();
				inMapVo.put("group_id",entityList.get(0).get("group_id"));
				inMapVo.put("hos_id",entityList.get(0).get("hos_id"));
				inMapVo.put("copy_code", entityList.get(0).get("copy_code"));
				inMapVo.put("allot_out_no", entityList.get(0).get("allot_out_no"));
				
				assAllotOutSourceInassetsMapper.deleteBatch(entityList);
				assAllotOutDetailInassetsMapper.deleteBatch(entityList);
				
				List<AssAllotOutDetailInassets> list = (List<AssAllotOutDetailInassets>)assAllotOutDetailInassetsMapper.queryExists(inMapVo);
				
				double price = 0;   
				double add_depre = 0; 
				double cur_money = 0; 
				double fore_money = 0;
				
				if(list != null){
					for(AssAllotOutDetailInassets temp :  list ){
						price += temp.getPrice();
						add_depre += temp.getAdd_depre();
						cur_money += temp.getCur_money();
						fore_money += temp.getFore_money();
					}
				}
				inMapVo.put("price", price+"");
				inMapVo.put("add_depre", add_depre+"");
				inMapVo.put("cur_money", cur_money+"");
				inMapVo.put("fore_money", fore_money+"");
				assAllotOutInassetsMapper.updateAllotOutMoney(inMapVo);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"price\":\""+price+"\",\"price\":\""+add_depre+"\",\"add_depre\":\""+cur_money+"\",\"fore_money\":\""+fore_money+"\"}";
			

			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException(e.getMessage());

			}	
		}
		
		/**
		 * @Description 
		 * 添加050901 资产无偿调拨出库单明细(无形资产)<BR> 
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
			//判断是否存在对象050901 资产无偿调拨出库单明细(无形资产)
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",entityMap.get("group_id"));
			mapVo.put("hos_id",entityMap.get("hos_id"));
	    	mapVo.put("copy_code", entityMap.get("copy_code"));
	    	mapVo.put("acct_year", entityMap.get("acct_year"));
			
			List<AssAllotOutDetailInassets> list = (List<AssAllotOutDetailInassets>)assAllotOutDetailInassetsMapper.queryExists(mapVo);
			
			if (list.size()>0) {

				int state = assAllotOutDetailInassetsMapper.update(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			
			try {
				
				int state = assAllotOutDetailInassetsMapper.add(entityMap);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

			}
			
		}
		/**
		 * @Description 
		 * 查询结果集050901 资产无偿调拨出库单明细(无形资产)<BR> 
		 * @param  entityMap
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String query(Map<String,Object> entityMap) throws DataAccessException{
			
			SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<AssAllotOutDetailInassets> list = (List<AssAllotOutDetailInassets>)assAllotOutDetailInassetsMapper.query(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<AssAllotOutDetailInassets> list = (List<AssAllotOutDetailInassets>)assAllotOutDetailInassetsMapper.query(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
			
		}
		
		/**
		 * @Description 
		 * 获取对象050901 资产无偿调拨出库单明细(无形资产)<BR> 
		 * @param  entityMap<BR>
		 *  参数为主键
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
			return assAllotOutDetailInassetsMapper.queryByCode(entityMap);
		}
		
		/**
		 * @Description 
		 * 获取050901 资产无偿调拨出库单明细(无形资产)<BR> 
		 * @param  entityMap<BR>
		 *  参数为要检索的字段
		 * @return AssAllotOutDetailInassets
		 * @throws DataAccessException
		*/
		@Override
		public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
			return assAllotOutDetailInassetsMapper.queryByUniqueness(entityMap);
		}
		
		/**
		 * @Description 
		 * 获取050901 资产无偿调拨出库单明细(无形资产)<BR> 
		 * @param  entityMap<BR>
		 *  参数为要检索的字段
		 * @return List<AssAllotOutDetailInassets>
		 * @throws DataAccessException
		*/
		@Override
		public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
			return assAllotOutDetailInassetsMapper.queryExists(entityMap);
		}
		@Override
		public List<AssAllotOutDetailInassets> queryByAssAllotOutNo(Map<String, Object> entityMap)
				throws DataAccessException {
			return assAllotOutDetailInassetsMapper.queryByAllotOutNo(entityMap);
		}
		@Override
		public List<AssAllotOutDetailInassets> queryByImportAllotIn(Map<String, Object> entityMap)
				throws DataAccessException {
			return assAllotOutDetailInassetsMapper.queryByImportAllotIn(entityMap);
		}
}
