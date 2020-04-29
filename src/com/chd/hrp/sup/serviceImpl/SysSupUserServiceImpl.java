/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.sup.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.base.util.WisdomCloud;
import com.chd.hrp.sup.dao.SysSupUserMapper;
import com.chd.hrp.sup.entity.SysSupUser;
import com.chd.hrp.sup.service.SysSupUserService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 供应商管理用户 
 * @Table:
 * SYS_SUP_USER
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("sysSupUserService")
public class SysSupUserServiceImpl implements SysSupUserService {

	private static Logger logger = Logger.getLogger(SysSupUserServiceImpl.class);
	//引入DAO操作
	@Resource(name = "sysSupUserMapper")
	private final SysSupUserMapper sysSupUserMapper = null;
    
	/**
	 * @Description 
	 * 添加供应商管理用户<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		// 检查是否已经存在管理员
		if (!"".equals(entityMap.get("sup_id").toString()) && "1".equals(entityMap.get("is_manager").toString())) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get("group_id"));
			mapVo.put("hos_id", entityMap.get("hos_id"));
			mapVo.put("copy_code", entityMap.get("copy_code"));
			mapVo.put("is_manager", "1");
			mapVo.put("sup_id", entityMap.get("sup_id") == null ? "" : entityMap.get("sup_id"));
			List<SysSupUser> list = (List<SysSupUser>) sysSupUserMapper.queryExists(mapVo);
			if (list.size() > 0) {
				return "{\"error\":\"同一个供应商只能有一个管理员.\"}";
			}
		}
		
		//获取对象供应商管理用户
		SysSupUser sysSupUser = queryByCode(entityMap);
		if (sysSupUser != null) {
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}
		
		try {
			//为添加供应商管理员做的限制
			if("".equals(entityMap.get("sup_id").toString())){
				entityMap.put("sup_id", "0");
				entityMap.put("sup_no", "0");
			}
			int state = sysSupUserMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
		
	}
	/**
	 * @Description 
	 * 批量添加供应商管理用户<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			sysSupUserMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新供应商管理用户<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = sysSupUserMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新供应商管理用户<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  sysSupUserMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除供应商管理用户<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = sysSupUserMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除供应商管理用户<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			sysSupUserMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加供应商管理用户<BR> 
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
		//判断是否存在对象供应商管理用户
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
    	mapVo.put("sup_id", entityMap.get("sup_id"));
    	if(!"0".equals(entityMap.get("sup_id").toString())){
			List<SysSupUser> list = (List<SysSupUser>)sysSupUserMapper.queryExists(mapVo);
			int state = sysSupUserMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		
		try {
			int state = sysSupUserMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";
		}
		
	}
	/**
	 * @Description 
	 * 添加供应商管理用户<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String generate(Map<String,Object> entityMap)throws DataAccessException{
		List<Map<String, Object>> sups=(List<Map<String, Object>>) querySupDict(entityMap);
		
		
		List<Map<String, Object>> addList=new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : sups) {
			Map<String, Object> queryMap=new HashMap<String, Object>();
			queryMap=map;
			queryMap.put("copy_code", entityMap.get("copy_code"));
			List<SysSupUser> list = (List<SysSupUser>)sysSupUserMapper.queryExists(queryMap);
			
			if (list.size()>0) {
				continue;
			}else{
				Map<String, Object> addMap=new HashMap<String, Object>();
				addMap=map;
				
				
				addMap.put("user_code", map.get("sup_code"));
				addMap.put("user_name", map.get("sup_name"));
				addMap.put("mod_code", "10");
				addMap.put("is_disable", "1");
				addMap.put("is_manager", "0");
				WisdomCloud wc;
	            try {
		            wc = new WisdomCloud();
		            addMap.put("user_pwd", wc.encrypt("123456"));
	            }
	            catch (Exception e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
	            }

	            addMap.put("spell_code", StringTool.toPinyinShouZiMu(map.get("sup_name").toString()));
	            addMap.put("wbx_code", StringTool.toWuBi(map.get("sup_name").toString()));
	            addList.add(addMap);
			}
		}
		
		try {
			List<Map<String, Object>> batchList=new ArrayList<Map<String,Object>>();
			for (int x = 0 ; x < addList.size() ; x++) {
				
				batchList.add(addList.get(x));
				
				if (x % 500 == 0) {
					sysSupUserMapper.deleteBatch(batchList);
					sysSupUserMapper.addBatch(batchList);
					batchList.removeAll(batchList);
				}
			}
			if(batchList.size()>0){
				sysSupUserMapper.deleteBatch(batchList);
				sysSupUserMapper.addBatch(batchList);
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";
			
		}
		
	}
	/**
	 * @Description 
	 * 查询结果集供应商管理用户<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<SysSupUser> list = (List<SysSupUser>)sysSupUserMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<SysSupUser> list = (List<SysSupUser>)sysSupUserMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象供应商管理用户<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return sysSupUserMapper.queryByCode(entityMap);
	}
	
	
	/**
	 * @Description 
	 * 获取供应商管理用户<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return SysSupUser
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return sysSupUserMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取供应商管理用户<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<SysSupUser>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return sysSupUserMapper.queryExists(entityMap);
	}
	@Override
	public List<?> querySupDict(Map<String, Object> entityMap) throws DataAccessException {
		return sysSupUserMapper.querySupDict(entityMap);
	}
	@Override
    public String updateSysSupUserPwd(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			  sysSupUserMapper.updateSysSupUserPwd(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

			}	
    }
	@Override
	public SysSupUser queryByCodeSupId(Map<String, Object> entityMap) throws DataAccessException {
		return sysSupUserMapper.queryByCodeSupId(entityMap);
	}
	
}
