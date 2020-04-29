package com.chd.hrp.acc.serviceImpl.accAly;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.accAly.AccAlyDupMapper;
import com.chd.hrp.acc.service.accAly.AccAlyDupService;
import com.github.pagehelper.PageInfo;

 


@Service("accAlyDupService")
public class AccAlyDupServiceImpl implements AccAlyDupService {

	private static Logger logger = Logger.getLogger(AccAlyDupServiceImpl.class);
	//引入DAO操作
	@Resource(name = "accAlyDupMapper")
	private final AccAlyDupMapper accAlyDupMapper = null;

	
	/**
	 * @Description 
	 * 添加医疗收入执行数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象医疗收入执行数据
		 Map<String,Object> map= queryByCode(entityMap);
		 int count=Integer.parseInt(map.get("C").toString());
		 if (count !=0) {
			return "{\"error\":\"数据重复,请重新添加.\"}";
		 }		
		try {
			//验证父级编码
			if(!entityMap.get("super_code").toString().equals("0")){
				HashMap<String,Object> hm=new HashMap<String,Object>();
				hm.putAll(entityMap); 
				hm.put("dup_code", hm.get("super_code"));
				map= queryByCode(hm);
				count=Integer.parseInt(map.get("C").toString());
				if (count ==0) {
					return "{\"error\":\"指标编码错误，父级编码不存在.\"}";
				}	
				hm.clear();
				hm.put("dup_code", entityMap.get("super_code"));
				hm.put("is_last", "0");
				hm.put("group_id", entityMap.get("group_id"));
				hm.put("hos_id", entityMap.get("hos_id"));
				hm.put("copy_code", entityMap.get("copy_code"));
				accAlyDupMapper.update(hm);
			}
			int state = accAlyDupMapper.add(entityMap);	
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	 
	
		/**
	 * @Description 
	 * 更新杜邦分析指标<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = accAlyDupMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}

	/**
	 * @Description 
	 * 删除数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = accAlyDupMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
	
	@Override
	public List<Map<String, Object>> queryDupPrintDate(Map<String, Object> entityMap) throws DataAccessException {
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list = (List<Map<String,Object>>)accAlyDupMapper.query(entityMap);
		for(Map<String,Object> map:list){
			if(map.get("IS_STOP").toString().equals("1"))
				map.put("IS_STOP", "停用");
			else
				map.put("IS_STOP", "启用");
		}
		return list;
	}
    
	/**
	 * @Description 
	 * 批量删除<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			String dup_code=null;
			String[] code=null;
			for(Map<String,Object> map:entityList){
				dup_code=map.get("dup_code").toString();
				code=dup_code.split("@");
				map.put("dup_code", code[0]);
				map.put("super_code", code[1]);
			}
			accAlyDupMapper.deleteBatch(entityList);
			//判断修改其父级级别
			List<Map<String,Object>> tmpList=null;
			Map<String,Object> parentMap=null;
			for(Map<String,Object> map:entityList){
				if(!map.get("super_code").toString().equals("0")){
					tmpList=(List<Map<String, Object>>) accAlyDupMapper.query(map);
					if(tmpList==null||tmpList.size()==0){
						//修改父级为末级
						parentMap=new HashMap<String,Object>();
						parentMap.put("dup_code", map.get("super_code"));
						parentMap.put("is_last", "1");
						parentMap.put("group_id", map.get("group_id"));
						parentMap.put("hos_id", map.get("hos_id"));
						parentMap.put("copy_code", map.get("copy_code"));
						accAlyDupMapper.update(parentMap);
						
					}
				}
			}
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	

	/**
	 * @Description 
	 * 查询数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)accAlyDupMapper.query(entityMap);
			
			return ChdJson.toJsonLower(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)accAlyDupMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
			
		}
		
	}
	

	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return accAlyDupMapper.queryByCode(entityMap);
	}
	

	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return accAlyDupMapper.queryByUniqueness(entityMap);
	}
	

	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return accAlyDupMapper.queryExists(entityMap);
	}


	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}
