/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.cost.dao.CostInnerServerMapper;
import com.chd.hrp.cost.entity.CostChargeItemArrt;
import com.chd.hrp.cost.entity.CostChargeKindArrt;
import com.chd.hrp.cost.entity.CostInnerServer;
import com.chd.hrp.cost.service.CostInnerServerService;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 内部服务量表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costInnerServerService")
public class CostInnerServerServiceImpl implements CostInnerServerService {

	private static Logger logger = Logger.getLogger(CostInnerServerServiceImpl.class);
	
	@Resource(name = "costInnerServerMapper")
	private final CostInnerServerMapper costInnerServerMapper = null;
    

	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;
	/**
	 * @Description 
	 * 内部服务量表<BR> 添加CostInnerServer
	 * @param CostInnerServer entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostInnerServer(Map<String,Object> entityMap)throws DataAccessException{
		
		CostInnerServer costInnerServer = queryCostInnerServerByCode(entityMap);

		if (costInnerServer != null) {

			return "{\"error\":\"此年月对应关系已存在.\"}";

		}
		
		try {
			
			costInnerServerMapper.addCostInnerServer(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostInnerServer\"}";

		}

	}
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 批量添加CostInnerServer
	 * @param  CostInnerServer entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostInnerServer(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costInnerServerMapper.addBatchCostInnerServer(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostInnerServer\"}";

		}
	}
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 查询CostInnerServer分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostInnerServer(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostInnerServer> list = costInnerServerMapper.queryCostInnerServer(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostInnerServer> list = costInnerServerMapper.queryCostInnerServer(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostInnerServerPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costInnerServerMapper.queryCostInnerServerPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 内部服务量表<BR> 查询CostInnerServerByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostInnerServer queryCostInnerServerByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costInnerServerMapper.queryCostInnerServerByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 批量删除CostInnerServer
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostInnerServer(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costInnerServerMapper.deleteBatchCostInnerServer(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostInnerServer\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 删除CostInnerServer
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostInnerServer(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costInnerServerMapper.deleteCostInnerServer(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostInnerServer\"}";

		}
    }
			
		
		@Override
		public String deleteMonthlyCostInnerServer(Map<String, Object> entityMap)
				throws DataAccessException {
			// TODO Auto-generated method stub
			try {
				
				costInnerServerMapper.deleteMonthlyCostInnerServer(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				// TODO: handle exception
	            logger.error(e.getMessage(), e);
				
				throw new SysException("{\"error\":\"删除失败\"}");
			}
		}
		
	/**
	 * @Description 
	 * 内部服务量表<BR> 更新CostInnerServer
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostInnerServer(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costInnerServerMapper.updateCostInnerServer(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostInnerServer\"}";

		}
	}
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 批量更新CostInnerServer
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostInnerServer(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costInnerServerMapper.updateBatchCostInnerServer(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostInnerServer\"}";

		}
		
	}

	@Override
	public CostInnerServer queryCostServerItemDict(Map<String, Object> entityMap) throws DataAccessException {
	
		return costInnerServerMapper.queryCostServerItemDict(entityMap);
	}
   /**
    * 导入
    * @param mapVo
    * @return
    */
	public String readAssFinaDictFiles(Map<String, Object> map) {

		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(map);
			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}
			for (Map<String, List<String>> map2 : list) {
				if (map2.get("acc_year").get(1) == null|| map2.get("acc_year").get(1).equals("")) {
					
					return "{\"msg\":\"" + map2.get("acc_year").get(0)+ "，统计年度为空！\",\"state\":\"false\",\"row_cell\":\""+ map2.get("acc_year").get(0) + "\"}";
				
				} else if (map2.get("acc_month").get(1) == null || map2.get("acc_month").get(1).equals("")) {
					
					return "{\"msg\":\"" + map2.get("acc_month").get(0)+ "，统计月份为空！\",\"state\":\"false\",\"row_cell\":\""+ map2.get("acc_month").get(0) + "\"}";
				
				} else if (map2.get("server_dept_id").get(1) == null|| map2.get("server_dept_id").get(1).equals("")) {
					
					return "{\"msg\":\""+ map2.get("server_dept_id").get(0)+ "，服务科室编码为空！\",\"state\":\"false\",\"row_cell\":\""+ map2.get("server_dept_id").get(0) + "\"}";
				
				} else if (map2.get("server_by_dept_id").get(1) == null || map2.get("server_by_dept_id").get(1).equals("")) {
					
					return "{\"msg\":\""+ map2.get("server_by_dept_id").get(0)+ "，被服务科室编码为空！\",\"state\":\"false\",\"row_cell\":\""+ map2.get("server_by_dept_id").get(0) + "\"}";
				
				} else if (map2.get("server_item_code").get(1) == null || map2.get("server_item_code").get(1).equals("")) {
				
					return "{\"msg\":\"" + map2.get("server_item_code").get(0)+ "，服务项目编码为空！\",\"state\":\"false\",\"row_cell\":\""+ map2.get("server_item_code").get(0) + "\"}";
				
				}else if (map2.get("server_num").get(1) == null || map2.get("server_num").get(1).equals("")) {
					 
					return "{\"msg\":\"" + map2.get("server_num").get(0)+ "，服务量为空！\",\"state\":\"false\",\"row_cell\":\""+ map2.get("server_num").get(0) + "\"}";
				
				}
				
				
				Map<String, Object> mapVo1 = new HashMap<String, Object>();

				mapVo1.put("group_id", SessionManager.getGroupId());

				mapVo1.put("hos_id", SessionManager.getHosId());

				mapVo1.put("copy_code", SessionManager.getCopyCode());

				mapVo1.put("acc_year", map2.get("acc_year").get(1));

				mapVo1.put("acc_month", ( map2.get("acc_month").get(1).length() == 1) ? ('0' + map2.get("acc_month").get(1).toString()) : map2.get("acc_month").get(1).toString());

				mapVo1.put("server_dept_id", map2.get("server_dept_id").get(1));

				mapVo1.put("server_by_dept_id", map2.get("server_by_dept_id").get(1));

				mapVo1.put("server_item_code", map2.get("server_item_code").get(1));
				
				mapVo1.put("server_num", map2.get("server_num").get(1));
				
				//mapVo1.put("price", map2.get("price").get(1));
				
			

				// 判断开单科室是否存在
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", mapVo1.get("group_id"));
				mapVo.put("hos_id", mapVo1.get("hos_id"));
				mapVo.put("copy_code", mapVo1.get("copy_code"));
				mapVo.put("dept_code", mapVo1.get("server_dept_id"));

				DeptDict deptDict = deptDictMapper.queryDeptDictByCode(mapVo);

				if (deptDict != null) {

					mapVo1.put("server_dept_no", deptDict.getDept_no());

					mapVo1.put("server_dept_id", deptDict.getDept_id());

				} else {
					throw new SysException(map2.get("server_dept_id").get(0)
							+ ",服务科室不存在！");

				}

				// 判断执行科室是否存在
				Map<String, Object> mapVo_dept = new HashMap<String, Object>();
				mapVo_dept.put("group_id", mapVo1.get("group_id"));
				mapVo_dept.put("hos_id", mapVo1.get("hos_id"));
				mapVo_dept.put("copy_code", mapVo1.get("copy_code"));
				mapVo_dept.put("dept_code", mapVo1.get("server_by_dept_id"));

				DeptDict deptDict1 = deptDictMapper
						.queryDeptDictByCodeDept(mapVo_dept);

				if (deptDict1 != null) {

					mapVo1.put("server_by_dept_no", deptDict1.getDept_no());

					mapVo1.put("server_by_dept_id", deptDict1.getDept_id());

				} else {
					throw new SysException(map2.get("server_by_dept_id").get(0)
							+ ",被服务科室不存在！");

				}

				
				// 判断服务项目是否存在
				Map<String, Object> mapVoServer = new HashMap<String, Object>();
				mapVoServer.put("group_id", mapVo1.get("group_id"));
				mapVoServer.put("hos_id", mapVo1.get("hos_id"));
				mapVoServer.put("copy_code", mapVo1.get("copy_code"));
				mapVoServer.put("server_item_code", mapVo1.get("server_item_code"));

				CostInnerServer costInnerServer = costInnerServerMapper.queryCostServerItemDict(mapVoServer);

				if (costInnerServer != null) {

					mapVo1.put("server_item_code", costInnerServer.getServer_item_code());

				} else { 
					throw new SysException(map2.get("server_item_code").get(0)
							+ ",服务项目不存在！");

				} 
				
				CostInnerServer costInnerServerByCode = costInnerServerMapper.queryCostInnerServerByCode(mapVo1);
				
				if(null != costInnerServerByCode){
					throw new SysException("当前月"
				              +" 服务科室:" + map2.get("server_dept_id").get(1)
				              +" 被服务科室:" + map2.get("server_by_dept_id").get(1)
				              +" 服务项目编码:" + map2.get("server_item_code").get(1)
							  +" 已经存在");
				}
				
				costInnerServerMapper.addCostInnerServer(mapVo1);
			}

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}

	}

}
