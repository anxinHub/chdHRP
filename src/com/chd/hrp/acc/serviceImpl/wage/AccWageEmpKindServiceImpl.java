/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.wage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.acc.dao.wage.AccWageEmpKindMapper;
import com.chd.hrp.acc.entity.AccWageEmp;
import com.chd.hrp.acc.entity.AccWageEmpKind;
import com.chd.hrp.acc.service.wage.AccWageEmpKindService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 工资套职工关系<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com 
* @Version: 1.0
*/


@Service("accWageEmpKindService")
public class AccWageEmpKindServiceImpl implements AccWageEmpKindService {

	private static Logger logger = Logger.getLogger(AccWageEmpKindServiceImpl.class);
	
	@Resource(name = "accWageEmpKindMapper")
	private final AccWageEmpKindMapper accWageEmpKindMapper = null;
    
	/**
	 * @Description 
	 * 工资套职工关系<BR> 添加AccWageEmp
	 * @param AccWageEmp entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccWageEmpKind(Map<String,Object> entityMap)throws DataAccessException{
		String wage_code = ((String) entityMap.get("wage_code")).trim();
		String kind_code = ((String) entityMap.get("kind_code")).trim();
		
		if ("".equals(wage_code)||"".equals(kind_code)) {
			return "{\"error\":\"工资套与职工分类不能为空.\"}";
		}
		
		AccWageEmp accWageEmp = queryAccWageEmpKindByCode(entityMap);

		if (accWageEmp != null) {

			return "{\"error\":\"对应关系已存在.\"}";

		}
		
		try {
			
			accWageEmpKindMapper.addAccWageEmpKind(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccWageEmp\"}";

		}

	}
	/**
	 * @Description 
	 * 工资套职工关系<BR> 查询AccWageEmp分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccWageEmpKind(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccWageEmp> list = accWageEmpKindMapper.queryAccWageEmpKind(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccWageEmp> list = accWageEmpKindMapper.queryAccWageEmpKind(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	 
	/**
	 * @Description 
	 * 工资套职工关系<BR> 查询AccWageEmpByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWageEmp queryAccWageEmpKindByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return  accWageEmpKindMapper.queryAccWageEmpKindByCode(entityMap);
		
	}
	
	/**
	 * @Description 工资套职工关系<BR>
	 *              批量删除AccWageEmp
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAccWageEmpKind(Map<String, Object> mapVo) throws DataAccessException {
		try {
			
			List<Map> list = JSONArray
					.parseArray(String.valueOf(mapVo.get("paramVo")), Map.class);
			for(Map<String, Object> kind : list){
				kind.put("group_id",Integer.valueOf(SessionManager.getGroupId()));
				kind.put("hos_id",Integer.valueOf(SessionManager.getHosId()));
				kind.put("copy_code",SessionManager.getCopyCode());
			    kind.put("acc_year", MyConfig.getCurAccYear());
			}
			//
			accWageEmpKindMapper.deleteBatchAccWageEmpKind(JsonListMapUtil.beanToListMap(list));
			//删除工资套下工资方案对应的职工分类
			accWageEmpKindMapper.deleteBatchAccWageSKind(JsonListMapUtil.beanToListMap(list));
			//删除该工资套该职工分类的计算方法
			accWageEmpKindMapper.deleteBatchAccWageCal(JsonListMapUtil.beanToListMap(list));
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccWageEmp\"}";
		}
	}
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 更新AccWageEmp
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWageEmpKind(Map<String,Object> entityMap)throws DataAccessException{

		try {

			entityMap.put("wage_code", entityMap.get("new_wage_code"));
			entityMap.put("kind_code", entityMap.get("new_kind_code"));
			AccWageEmp accWageEmp = queryAccWageEmpKindByCode(entityMap);

			if (accWageEmp != null) {

				return "{\"error\":\"对应关系已存在.\"}";

			}
			
			accWageEmpKindMapper.updateAccWageEmpKind(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageEmp\"}";

		}
	}
	@Override
	public List<Map<String,Object>> queryAccWageEmpKindListPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String,Object>> list = accWageEmpKindMapper.queryAccWageEmpKindListPrint(entityMap);
		
		return  list;
	}
	
	// 保存工资套与职工分类的关系
	@Override
	public String saveAccWageEmpKind(Map<String, Object> mapVo) throws DataAccessException {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		try{
			List<AccWageEmpKind> list = JSONArray
				.parseArray(String.valueOf(mapVo.get("paramVo")), AccWageEmpKind.class);
			if(list.size() > 0){
				List<AccWageEmpKind> saveList = new ArrayList<AccWageEmpKind>();
				for(AccWageEmpKind kind : list){
					if(kind.getKind_code() != null){
						kind.setGroup_id(Integer.valueOf(SessionManager.getGroupId()));
						kind.setHos_id(Integer.valueOf(SessionManager.getHosId()));
						kind.setCopy_code(SessionManager.getCopyCode());
						kind.setWage_code(mapVo.get("wage_code").toString());
						saveList.add(kind);
					}
				}
				
				int count = accWageEmpKindMapper.deleteAccWageEmpKindByWageCode(mapVo);
				if(count == 0 && saveList.size() == 0){
					return "{\"warn\":\"没有可添加数据\",\"state\":\"true\"}";
				}
				if(saveList.size() > 0){
					accWageEmpKindMapper.addBatchAccWageEmpKind(JsonListMapUtil.beanToListMap(saveList));
				}
			}
			return "{\"msg\":\"添加成功\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败\",\"state\":\"false\"}";
		}
	}

}
