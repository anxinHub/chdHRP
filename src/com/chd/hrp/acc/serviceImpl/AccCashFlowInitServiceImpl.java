/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccCashFlowInitMapper;
import com.chd.hrp.acc.entity.AccCashFlow;
import com.chd.hrp.acc.entity.AccCashFlowInit;
import com.chd.hrp.acc.entity.AccMatchInit;
import com.chd.hrp.acc.service.AccCashFlowInitService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 现金流量初始帐<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accCashFlowInitService")
public class AccCashFlowInitServiceImpl implements AccCashFlowInitService {

	private static Logger logger = Logger.getLogger(AccCashFlowInitServiceImpl.class);
	
	@Resource(name = "accCashFlowInitMapper")
	private final AccCashFlowInitMapper accCashFlowInitMapper = null;
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
    
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 添加AccCashFlow
	 * @param AccCashFlow entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccCashFlowInit(Map<String,Object> entityMap)throws DataAccessException{
		
		AccCashFlowInit accCashFlowInit = queryAccCashFlowInitByCode(entityMap);

		if (accCashFlowInit != null) {
			return "{\"error\":\"数据已存在,不能添加.\"}";
		}
		
		try {
			accCashFlowInitMapper.addAccCashFlowInit(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败,数据库异常\"}");
		}

	}
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 批量添加AccCashFlow
	 * @param  AccCashFlow entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccCashFlowInit(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accCashFlowInitMapper.addBatchAccCashFlowInit(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccCashFlowInit\"}";

		}
	}
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 查询AccCashFlow分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccCashFlowInit(Map<String,Object> entityMap) throws DataAccessException{
		
		 SysPage sysPage = new SysPage();
		 sysPage = (SysPage) entityMap.get("sysPage");
			
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = accCashFlowInitMapper.queryAccCashFlowInit(entityMap);
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = accCashFlowInitMapper.queryAccCashFlowInit(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 查询AccCashFlowByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccCashFlowInit queryAccCashFlowInitByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accCashFlowInitMapper.queryAccCashFlowInitByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 批量删除AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccCashFlowInit(List<Map<String,Object>> entityList)throws DataAccessException{

		try {	
			String str ="";
			for(Map<String,Object>  item :entityList){
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("group_id", item.get("group_id"));
				map.put("hos_id", item.get("hos_id"));
				map.put("copy_code", item.get("copy_code"));
				map.put("acc_year", item.get("acc_year"));
				map.put("subj_code", item.get("subj_code"));
				map.put("acc_month", "00");
				
				//查询 账表中 科目的期初帐是否存在
				int count = accCashFlowInitMapper.queryLederExist(map);
				if(count>0){
					str += item.get("subj_name") +",";
				}
			}
			if(!"".equals(str)){
				return "{\"error\":\"删除失败！！账表中【科目:"+str.substring(0,str.length()-1)+"】初始帐数据已存在,不能删除.\"}";
			}
			
			accCashFlowInitMapper.deleteBatchAccCashFlowInit(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败 数据库异常\"}");

		}
		
	}
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 删除AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccCashFlowInit(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accCashFlowInitMapper.deleteAccCashFlowInit(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccCashFlowInit\"}";

		}
    }
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 更新AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccCashFlowInit(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accCashFlowInitMapper.updateAccCashFlowInit(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCashFlowInit\"}";

		}
	}
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 批量更新AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccCashFlowInit(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			Map<String,Object> distinctMap = new HashMap<String,Object>(); //去重Map
			StringBuffer sb = new StringBuffer();//提示信息
			
			//1.根据会计年度、科目编码、现金流量ID判断数据是否重复
			for(int i=0; i<entityList.size(); i++){
				String key = 
					String.valueOf(entityList.get(i).get("acc_year")) + 
					String.valueOf(entityList.get(i).get("subj_code")) +
					String.valueOf(entityList.get(i).get("cash_item_id"));
				
				if(distinctMap.get(key) != null){
					String msg = 
						  entityList.get(i).get("acc_year") +"年度,会计科目:" 
						+ entityList.get(i).get("subj_name") + "现金流量项目:"
						+ entityList.get(i).get("cash_item_name");
					sb.append(msg + "\r\n");
				}
				
				distinctMap.put(key, key);
			}
			
			
			if(sb.length() > 0){
				return "{\"warn\":\"修改数据中"+ sb.toString()+"存在重复数据,不能保存.\"}";
			}
				
			//2.删除修改的数据（先删除原数据）
			accCashFlowInitMapper.deleteUpdateDate(entityList);
			
			//3.添加新增的数据( 后添加 )
			accCashFlowInitMapper.addBatchAccCashFlowInit(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

		}
		
	}
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 导入AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccCashFlowInit(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
	
	@Override
	public String queryAccSubj(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(accCashFlowInitMapper.queryAccSubj(entityMap, rowBounds));
	}
	/**
	 * 查询  会计科目  是否存在 
	 * @param mapVo
	 * @return
	 */
	public int querySubjExist(Map<String, Object> mapVo) throws DataAccessException{
		return accCashFlowInitMapper.querySubjExist(mapVo);
	}
	/**
	 * 查询  现金流量项目  是否存在 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryAccCashFlowExist(Map<String, Object> mapVo) throws DataAccessException{
		return accCashFlowInitMapper.queryAccCashFlowExist(mapVo);
	}

	@Override
	public List<Map<String, Object>> queryAccCashFlowInitPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accCashFlowInitMapper.queryAccCashFlowInit(entityMap);
		
		return list;
	}
}
