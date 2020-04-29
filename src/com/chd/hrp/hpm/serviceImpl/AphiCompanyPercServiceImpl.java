package com.chd.hrp.hpm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiCompanyPercMapper;
import com.chd.hrp.hpm.dao.AphiCostitemMapper;
import com.chd.hrp.hpm.entity.AphiCompanyPerc;
import com.chd.hrp.hpm.entity.AphiCostitem;
import com.chd.hrp.hpm.service.AphiCompanyPercService;

/**
 * alfred
 */

@Service("aphiCompanyPercService")
public class AphiCompanyPercServiceImpl implements AphiCompanyPercService {

	private static Logger logger = Logger.getLogger(AphiCompanyPercServiceImpl.class);
	
	@Resource(name = "aphiCompanyPercMapper")
	private final AphiCompanyPercMapper aphiCompanyPercMapper = null;
	
    
	/**
	 * 
	 */
	@Override
	public String addCompanyPerc(Map<String,Object> entityMap)throws DataAccessException{
		AphiCompanyPerc cp = queryCompanyPercByCode(entityMap);
		
		if(cp != null){
			
			if(cp.getGroup_id() !=null){
				
				return "{\"error\":\"集团名称：" + cp.getGroup_name() + "重复.\"}";
				
			}
			
			if(cp.getHos_id() !=null){
				
				return "{\"error\":\"医院名称：" + cp.getHos_name() + "重复.\"}";
				
			}
		}
		try {

			aphiCompanyPercMapper.addCompanyPerc(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码  addCompanyPerc\"}";
		}
	
	}
	
	/**
	 * 
	 */
	@Override
	public String queryCompanyPerc(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		return JsonListBeanUtil.listToJson(aphiCompanyPercMapper.queryCompanyPerc(entityMap, rowBounds), sysPage.getTotal());
		
	}
	
	/**
	 * 
	 */
	@Override
	public AphiCompanyPerc queryCompanyPercByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return aphiCompanyPercMapper.queryCompanyPercByCode(entityMap);
		
	}
	
	/**
	 * 
	 */
	@Override
	public String deleteCompanyPerc(Map<String, Object> entityMap,String codes)throws DataAccessException{
		
		try{

			if (codes != null && !codes.equals("")) {
				
				String[] code_array = codes.split(",");

				for(int i=0 ; i < code_array.length; i++){
					
					String[] code_arrays=code_array[i].split(";");
					
					entityMap.put("group_id", code_arrays[0]);
					
					entityMap.put("hos_id", code_arrays[1]);
					
					entityMap.put("copy_code", code_arrays[2]);
					
					aphiCompanyPercMapper.deleteCompanyPerc(entityMap);
					
				}
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} else {
				
				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
				
			}
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCompanyPerc\"}";
			
		}
	}
	
	/**
	 * 
	 */
	@Override
	public String updateCompanyPerc(Map<String,Object> entityMap)throws DataAccessException{
		
		try {

			aphiCompanyPercMapper.updateCompanyPerc(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码  updateCompanyPerc\"}";
		}
	}
	
	@Override
	public String createCompanyPerc(Map<String, Object> entityMap) throws DataAccessException {

	// 先清空数据库
		//aphiCompanyPercMapper.clearCompanyPerc(entityMap);

		// 生成数据
		//List<AphiCostitem> allCostitem = aphiCostitemMapper.queryCostitem(entityMap);
		try {

			/*for (int i = 0; i < allCostitem.size(); i++) {

				AphiCostitem costitem = allCostitem.get(i);

				entityMap.put("cost_item_code", costitem.getCost_item_code());

				entityMap.put("is_acc", "1");

				//entityMap.put("cost_percent", "");

				aphiCompanyPercMapper.addCompanyPerc(entityMap);

			}
*/
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			return "{\"msg\":\"生成失败.\",\"state\":\"false\"}";

		}

	}

	@Override
	public String addBatchCompanyPerc(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		 int  state = aphiCompanyPercMapper.addBatchCompanyPerc(entityMap);
		 if(state>0){
			 return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		 }
		 	return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
	}

	
}
