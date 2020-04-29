/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.startup.LoadMenuFile;
import com.chd.base.util.ChdJson;
import com.chd.hrp.sys.dao.ModMapper;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.entity.Mod;
import com.chd.hrp.sys.entity.ModStart;
import com.chd.hrp.sys.service.ModService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("modService")
public class ModServiceImpl implements ModService {

	private static Logger logger = Logger.getLogger(ModServiceImpl.class);
	
	@Resource(name = "modMapper")
	private final ModMapper modMapper = null;
	
	@Resource(name = "modStartMapper")
	private final ModStartMapper modStartMapper = null;
	@Resource(name = "modStartService")
	private final ModStartServiceImpl modStartService = null;
    
	/**
	 * @Description 添加Mod
	 * @param Mod entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMod(Map<String,Object> entityMap)throws DataAccessException{
		
		Mod mod = queryModByCode(entityMap);

		if (mod != null) {

			return "{\"error\":\"编码：" + mod.getMod_code() + "已存在.\"}";

		}
		
		try {
			
			modMapper.addMod(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addMod\"}";

		}

	}
	
	/**
	 * @Description 批量添加Mod
	 * @param  Mod entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMod(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			modMapper.addBatchMod(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchMod\"}";

		}
	}
	
	/**
	 * @Description 查询Mod分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMod(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Mod> list = modMapper.queryMod(entityMap, rowBounds);
		
		List<Mod> resultList = new ArrayList<Mod>();
		
		Integer value=0;
		
		for(Mod temp : list){
			// 通过License 判断用户授权
			if(LoadMenuFile.getModMap().get(temp.getMod_code())!=null){
				
				entityMap.put("mod_code", temp.getMod_code());
				
				ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
				
				if(modStart != null){
					
					temp.setModStart(modStart);
					
					modStart.setIs_show(value.toString());
					
					if(modStart.getMod_code().equals("01") || modStart.getMod_code().equals("0101") || modStart.getMod_code().equals("0102")){
						
						if(!"".equals(modStart.getStart_year())&&null!=modStart.getStart_year()){
							
							entityMap.put("acc_year", modStart.getStart_year());
							
							entityMap.put("start_month", modStart.getStart_month());
							
							value = modStartService.queryModStartData(entityMap);
						
						}
					}
					
				}
				
				resultList.add(temp);
				
			}
		}
		return ChdJson.toJson(resultList);
		
	}
	
	//集团、医院系统权限查询页面
	@Override
	public String querySysModList(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Mod> list =new ArrayList<Mod>();
		if(entityMap.get("flag")!=null && entityMap.get("flag").equals("admin")){
			//系统管理员
			list = modMapper.queryMod(entityMap);
		}else{
			//集团管理员
			list = modMapper.queryModByGroupPerm(entityMap);
		}
		
		List<Mod> resultList = new ArrayList<Mod>();
		if(list!=null && list.size()>0){
			for(Mod temp : list){
				// 通过License 判断用户授权
				if(LoadMenuFile.getModMap().get(temp.getMod_code())!=null){
					resultList.add(temp);
				}
			}
		}
		
		return ChdJson.toJson(resultList);
		
	}
	
	/**
	 * @Description 查询ModByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Mod queryModByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return modMapper.queryModByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除Mod
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMod(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = modMapper.deleteBatchMod(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchMod\"}";

		}
		
	}
	
		/**
	 * @Description 删除Mod
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteMod(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				modMapper.deleteMod(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteMod\"}";

		}
    }
	
	/**
	 * @Description 更新Mod
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMod(Map<String,Object> entityMap)throws DataAccessException{

		try {

			modMapper.updateMod(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateMod\"}";

		}
	}
	
	/**
	 * @Description 批量更新Mod
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMod(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			modMapper.updateBatchMod(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateMod\"}";

		}
		
	}
	
	/**
	 * @Description 导入Mod
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importMod(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryModList(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Mod> list = modMapper.queryModList(entityMap, rowBounds);
		List<Mod> resultList = new ArrayList<Mod>();
		for(Mod temp : list){
			entityMap.put("mod_code", temp.getMod_code());
			ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
			if(modStart != null){
				temp.setModStart(modStart);
			}
			resultList.add(temp);
		}
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(resultList, page.getTotal());
	}
}
