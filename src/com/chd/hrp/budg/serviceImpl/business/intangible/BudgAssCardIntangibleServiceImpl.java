package com.chd.hrp.budg.serviceImpl.business.intangible;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.business.fixedassets.BudgAssCardMapper;
import com.chd.hrp.budg.dao.business.intangible.BudgAssCardIntangibleMapper;
import com.chd.hrp.budg.entity.BudgAssCard;
import com.chd.hrp.budg.service.business.intangible.BudgAssCardIntangibleService;
import com.chd.hrp.budg.serviceImpl.business.fixedassets.BudgAssCardServiceImpl;
import com.github.pagehelper.PageInfo;

@Service("budgAssCardIntangibleService")
public class BudgAssCardIntangibleServiceImpl implements BudgAssCardIntangibleService{

	
	private static Logger logger = Logger.getLogger(BudgAssCardIntangibleServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgAssCardIntangibleMapper")
	private final BudgAssCardIntangibleMapper budgAssCardIntangibleMapper = null;
	
	/**
	 * @Description 
	 * 添加
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		//获取对象

				BudgAssCard budgAssCard = queryByCode(entityMap);

				if (budgAssCard != null) {

					return "{\"error\":\"数据重复,请重新添加.\"}";

				}
				
				try {
					String naturs_code =budgAssCardIntangibleMapper.querynatursCode(entityMap);
					entityMap.put("naturs_code", naturs_code);
					Date in_date = new SimpleDateFormat("yyyy-MM-dd").parse( entityMap.get("in_date").toString());
					entityMap.put("in_date", in_date);
					int state = budgAssCardIntangibleMapper.add(entityMap);
					List<Map<String,Object>> detailAddList = new ArrayList<Map<String,Object>>();

					JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
					Iterator it = json.iterator();
					while (it.hasNext()) {
						JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
						Map<String,Object> detailMap=new HashMap<String,Object>();
						if( jsonObj.get("source_id") != null && !"".equals(jsonObj.get("source_id"))){
							detailMap.put("naturs_code", entityMap.get("naturs_code"));
							detailMap.put("group_id", entityMap.get("group_id"));
							detailMap.put("group_id", entityMap.get("group_id"));
							detailMap.put("hos_id", entityMap.get("hos_id"));
							detailMap.put("copy_code", entityMap.get("copy_code"));
							detailMap.put("ass_card_no", entityMap.get("ass_card_no"));
							detailMap.put("source_id", jsonObj.get("source_id"));
							detailMap.put("price", jsonObj.get("price"));
							detailMap.put("depre_money", jsonObj.get("depre_money"));
							detailMap.put("cur_money", jsonObj.get("cur_money"));
							detailMap.put("fore_money", jsonObj.get("fore_money"));
							detailAddList.add(detailMap);
						}
						
						
					}
					int i =budgAssCardIntangibleMapper.addBudgAssCardSource(detailAddList);
					return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

				}
				catch (Exception e) {

					logger.error(e.getMessage(), e);

					return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

				}
	}
	
	/**
	 * @Description 
	 * 批量添加
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String, Object>> entityList)
			throws DataAccessException {
	try {
			
			budgAssCardIntangibleMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加 资产现状——资金来源数据
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBudgAssCardSource(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgAssCardIntangibleMapper.addBudgAssCardSource(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 addBatch\"}";

		}
		
	}
	/**
	 * @Description 
	 * 更新
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			Date in_date = new SimpleDateFormat("yyyy-MM-dd").parse( entityMap.get("in_date").toString());
			entityMap.put("in_date", in_date);
		  int state = budgAssCardIntangibleMapper.update(entityMap);
		  List<Map<String,Object>> detailupdateList = new ArrayList<Map<String,Object>>();
		  List<Map<String,Object>> detailAddList = new ArrayList<Map<String,Object>>();

			JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				Map<String,Object> detailMap=new HashMap<String,Object>();
				if( jsonObj.get("source_id") != null && !"".equals(jsonObj.get("source_id"))){
					
					detailMap.put("naturs_code", entityMap.get("naturs_code"));
					detailMap.put("group_id", entityMap.get("group_id"));
					detailMap.put("group_id", entityMap.get("group_id"));
					detailMap.put("hos_id", entityMap.get("hos_id"));
					detailMap.put("copy_code", entityMap.get("copy_code"));
					detailMap.put("ass_card_no", entityMap.get("ass_card_no"));
					detailMap.put("source_id", jsonObj.get("source_id"));
					detailMap.put("price", jsonObj.get("price"));
					detailMap.put("depre_money", jsonObj.get("depre_money"));
					detailMap.put("cur_money", jsonObj.get("cur_money"));
					detailMap.put("fore_money", jsonObj.get("fore_money"));
					Map<String,Object> assCardSourceMap=budgAssCardIntangibleMapper.queruBudgAssCardSource(detailMap);
					if (assCardSourceMap!=null) {
						detailAddList.add(detailMap);
						budgAssCardIntangibleMapper.deleteBudgAssCardSource(detailMap);
					}
					
				}
			}
			int i =budgAssCardIntangibleMapper.addBudgAssCardSource(detailAddList);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
	}
	/**
	 * @Description 
	 * 批量更新
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String, Object>> entityList)
			throws DataAccessException {

		try {
			
			budgAssCardIntangibleMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
	}
	/**
	 * @Description 
	 * 删除
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String delete(Map<String, Object> entityMap)
			throws DataAccessException {
		 try {
				
				int state = budgAssCardIntangibleMapper.delete(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

			}	
			
	}
	/**
	 * @Description 
	 * 批量删除
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList)
			throws DataAccessException {
		try {
			budgAssCardIntangibleMapper.deletesoure(entityList);
			budgAssCardIntangibleMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	/**
	 * @Description 
	 * 添加
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象

		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgAssCard> list = (List<BudgAssCard>)budgAssCardIntangibleMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgAssCardIntangibleMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgAssCardIntangibleMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
	}
	/**
	 * @Description 
	 * 查询结果集
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) budgAssCardIntangibleMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)budgAssCardIntangibleMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	/**
	 * @Description 
	 * 获取对象
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		return budgAssCardIntangibleMapper.queryByCode(entityMap);
	}
	/**
	 * @Description 
	 * 获取
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgAssCard
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		return budgAssCardIntangibleMapper.queryByUniqueness(entityMap);
	}
	/**
	 * @Description 
	 * 获取
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgAssCard>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		return budgAssCardIntangibleMapper.queryExists(entityMap);
	}

	@Override
	public String queryBudgAssCardSourceBySourceId(Map<String, Object> mapVo) {
	List<Map<String,Object>> list = (List<Map<String, Object>>) budgAssCardIntangibleMapper.queryBudgAssCardSourceBySourceId(mapVo);
		
		return ChdJson.toJson(list);
	}
	
	/**
	 * 校验数据是否存在
	 */
	@Override
	public int queryDataExist(Map<String,Object> entityMap)throws DataAccessException{
		return budgAssCardIntangibleMapper.queryDataExist(entityMap);
	}
	
	/**
	 * 校验资产现状_资金来源 数据是否存在
	 */
	@Override
	public int queryDataExistSource(Map<String,Object> entityMap)throws DataAccessException{
		return budgAssCardIntangibleMapper.queryDataExistSource(entityMap);
	}

}
