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

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.cost.dao.CostItemDictNoMapper;
import com.chd.hrp.cost.dao.CostOutAcctVouchMapper;
import com.chd.hrp.cost.entity.CostChargeItemArrt;
import com.chd.hrp.cost.entity.CostChargeKindArrt;
import com.chd.hrp.cost.entity.CostItemDictNo;
import com.chd.hrp.cost.entity.CostOutAcctVouch;
import com.chd.hrp.cost.service.CostOutAcctVouchService;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.dao.SourceMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.entity.Source;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 科室支出总账<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costOutAcctVouchService")
public class CostOutAcctVouchServiceImpl implements CostOutAcctVouchService {

	private static Logger logger = Logger.getLogger(CostOutAcctVouchServiceImpl.class);
	
	@Resource(name = "costOutAcctVouchMapper")
	private final CostOutAcctVouchMapper costOutAcctVouchMapper = null;
	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;
	
	@Resource(name = "costItemDictNoMapper")
	private final CostItemDictNoMapper costItemDictNoMapper = null;
	
    
	@Resource(name = "sourceMapper")
	private final SourceMapper sourceMapper = null;
	/**
	 * @Description 
	 * 科室支出总账<BR> 添加CostOutAcctVouch
	 * @param CostOutAcctVouch entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostOutAcctVouch(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			costOutAcctVouchMapper.addCostOutAcctVouch(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostOutAcctVouch\"}";

		}

	}
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 批量添加CostOutAcctVouch
	 * @param  CostOutAcctVouch entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostOutAcctVouch(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costOutAcctVouchMapper.addBatchCostOutAcctVouch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostOutAcctVouch\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 查询CostOutAcctVouch分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostOutAcctVouch(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostOutAcctVouch> list = costOutAcctVouchMapper.queryCostOutAcctVouch(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostOutAcctVouch> list = costOutAcctVouchMapper.queryCostOutAcctVouch(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostOutAcctVouchPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costOutAcctVouchMapper.queryCostOutAcctVouchPrint(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryCostOutAcctVouchPrmPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costOutAcctVouchMapper.queryCostOutAcctVouchPrmPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 科室支出总账<BR> 查询CostOutAcctVouchByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostOutAcctVouch queryCostOutAcctVouchByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costOutAcctVouchMapper.queryCostOutAcctVouchByCode(entityMap);
		 
	}
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 批量删除CostOutAcctVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostOutAcctVouch(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				 costOutAcctVouchMapper.deleteBatchCostOutAcctVouch(entityList);
				 
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostOutAcctVouch\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 删除CostOutAcctVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostOutAcctVouch(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costOutAcctVouchMapper.deleteCostOutAcctVouch(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostOutAcctVouch\"}";

		}
    }
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 更新CostOutAcctVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostOutAcctVouch(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costOutAcctVouchMapper.updateCostOutAcctVouch(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostOutAcctVouch\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 批量更新CostOutAcctVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostOutAcctVouch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costOutAcctVouchMapper.updateBatchCostOutAcctVouch(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostOutAcctVouch\"}";

		}
		
	}

	@Override
	public CostOutAcctVouch queryCostDeptByCode(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("is_flag", SessionManager.getCostParaMap().get("03001"));
		return 	costOutAcctVouchMapper.queryCostDeptByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 采集成本数据
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostOutAcctVouchByAcc(Map<String, Object> entityMap)throws DataAccessException{
		
		try {

			costOutAcctVouchMapper.addCostOutAcctVouchByAcc(entityMap);

			return "{\"msg\":\"采集成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"采集失败\"}");
			//return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostOutAcctVouch\"}";
		}
	}

	@Override
	public String readAssFinaDictFiles(Map<String, Object> map)
			throws DataAccessException {
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(map);
			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}
			for (Map<String, List<String>> map2 : list) {
				if (map2.get("acc_year").get(1) == null || map2.get("acc_year").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("acc_year").get(0)+ "，统计年度为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("acc_year").get(0)+ "\"}";
				} else if (map2.get("acc_month").get(1) == null || map2.get("acc_month").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("acc_month").get(0)+ "，统计月份为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("acc_month").get(0)+ "\"}";
				} else if (map2.get("dept_id").get(1) == null || map2.get("dept_id").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("dept_id").get(0)+ "，科室编码为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("dept_id").get(0) + "\"}";
				} else if (map2.get("cost_item_id").get(1) == null || map2.get("cost_item_id").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("cost_item_id").get(0) + "，成本项目编码为空！\",\"state\":\"false\",\"row_cell\":\""+ map2.get("cost_item_id").get(0) + "\"}";
				} else if (map2.get("money").get(1) == null || map2.get("money").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("money").get(0) + "，金额为空！\",\"state\":\"false\",\"row_cell\":\""+ map2.get("money").get(0) + "\"}";
				}
				Map<String, Object> mapVo1 = new HashMap<String, Object>();

				mapVo1.put("group_id", SessionManager.getGroupId());

				mapVo1.put("hos_id", SessionManager.getHosId());

				mapVo1.put("copy_code", SessionManager.getCopyCode());
				
				mapVo1.put("acc_year", map2.get("acc_year").get(1));
				
				mapVo1.put("acc_month", ( map2.get("acc_month").get(1).length() == 1) ? ('0' + map2.get("acc_month").get(1).toString()) : map2.get("acc_month").get(1).toString());
				
				mapVo1.put("dept_id", map2.get("dept_id").get(1));
				
				mapVo1.put("cost_item_id", map2.get("cost_item_id").get(1));
				
				mapVo1.put("source_id", map2.get("source_id").get(1));
				
				mapVo1.put("amount", map2.get("money").get(1));
				
//				mapVo1.put("create_user", SessionManager.getUserId());
//				
//				mapVo1.put("create_date", new Date());
				
				//判断科室是否存在
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", mapVo1.get("group_id"));
				mapVo.put("hos_id", mapVo1.get("hos_id"));
				mapVo.put("copy_code", mapVo1.get("copy_code"));
				mapVo.put("dept_code", mapVo1.get("dept_id"));
				
				DeptDict  deptDict = deptDictMapper.queryDeptDictByCode(mapVo);
				
				if(deptDict != null){
					
					mapVo1.put("dept_id", deptDict.getDept_id());
					
					mapVo1.put("dept_no", deptDict.getDept_no());
					
				}else{
					throw new SysException(map2.get("appl_dept_id").get(0) + ",开单科室不存在！");							
					
				}
				//成本项目是否存在
				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				byCodeMap.put("group_id", mapVo1.get("group_id"));
				byCodeMap.put("hos_id", mapVo1.get("hos_id"));
				byCodeMap.put("copy_code", mapVo1.get("copy_code"));
				byCodeMap.put("cost_item_code", mapVo1.get("cost_item_id"));
			
				CostItemDictNo cidn = costItemDictNoMapper.queryCostItemDictNoByCode(byCodeMap);
			  
				if (cidn !=null) {
					
	               mapVo1.put("cost_item_id", cidn.getCost_item_id());
					
					mapVo1.put("cost_item_no", cidn.getCost_item_no());
				}else{
					
					throw new SysException(map2.get("cost_item_id").get(0) + ",成本项目编码不存在！");
				}
				
                //判断资金来源是否存在
				Map<String, Object> mapVo_Arrt = new HashMap<String, Object>();
				mapVo_Arrt.put("group_id", mapVo1.get("group_id"));
				mapVo_Arrt.put("hos_id", mapVo1.get("hos_id"));
				mapVo_Arrt.put("copy_code", mapVo1.get("copy_code"));
				mapVo_Arrt.put("source_code", mapVo1.get("source_id"));
				
						Source source= sourceMapper.querySourceByCode(mapVo_Arrt);
			
					if(source != null){
						
						mapVo1.put("source_id", source.getSource_id());
						
					}else{
						
						throw new SysException(map2.get("charge_kind_id").get(0) + ",收费类别不存在！");
					}
					costOutAcctVouchMapper.addCostOutAcctVouch(mapVo1);
				
			}
			
			
			
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

	@Override
    public String readCostItemDictFilesX(Map<String, Object> map) throws DataAccessException {
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(map);
			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}

			Map<String, Object> m = new HashMap<String, Object>();
			m.put("group_id", SessionManager.getGroupId());
			m.put("hos_id", SessionManager.getHosId());
			m.put("copy_code", SessionManager.getCopyCode());
			m.put("is_last", "1"); 
			m.put("is_stop", "0"); 
			// 查询成本项目字典
			List<CostItemDictNo> cia = (List<CostItemDictNo>) costItemDictNoMapper.queryCostItemDictNo(m);
			// 构建成本项目集合 用于判断 名称以及编码
			Map<String, Object> r_m = new HashMap<String, Object>();
			for (CostItemDictNo c : cia) {
				r_m.put(c.getCost_item_name(), c.getCost_item_id() + "-" + c.getCost_item_no());
			}
			long dept_id = 0;
			long dept_no = 0;
			for (Map<String, List<String>> map2 : list) {
				// 根据 名称导入数据

				if (map2.get("acc_year").get(1) == null || map2.get("acc_year").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("acc_year").get(0) + "，统计年度为空！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("acc_year").get(0) + "\"}";

				} else if (map2.get("acc_month").get(1) == null || map2.get("acc_month").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("acc_month").get(0) + "，统计月份为空！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("acc_month").get(0) + "\"}";
				} 
				// 判断科室是否存在
				Map<String, Object> mapVo_dept = new HashMap<String, Object>();
				mapVo_dept.put("group_id", map.get("group_id"));
				mapVo_dept.put("hos_id", map.get("hos_id"));
				mapVo_dept.put("copy_code", map.get("copy_code"));
				mapVo_dept.put("dept_code", map2.get("dept_id").get(1));
				mapVo_dept.put("dept_name", map2.get("dept_name").get(1));

				DeptDict deptDict1 = deptDictMapper.queryDeptDictByCodeDept(mapVo_dept);

				if (deptDict1 != null) {
					dept_id = deptDict1.getDept_id();
					
					dept_no = deptDict1.getDept_no();

				} else {
					throw new SysException(map2.get("dept_id").get(0) + ",科室不存在！");

				}
				for (String key : map2.keySet()) {
					
					if("group_id".equals(key)){
						continue;
					}
					if("hos_id".equals(key)){
						continue;
					}
					if("copy_code".equals(key)){
						continue;
					}
					if("acc_year".equals(key)){
						continue;
					}
					if("acc_month".equals(key)){
						continue;
					}
					
					if("dept_id".equals(key)){
						continue;
					}
					if("dept_no".equals(key)){
						continue;
					}
					if("dept_name".equals(key)){
						continue;
					}
					
					if(map2.get(key)==null){
						continue;
					}
					
					if(map2.get(key).get(1)== null || map2.get(key).get(1).equals("")){
						continue;
					}
					Map<String, Object> mapVo1 = new HashMap<String, Object>();

					mapVo1.put("group_id", SessionManager.getGroupId());

					mapVo1.put("hos_id", SessionManager.getHosId());

					mapVo1.put("copy_code", SessionManager.getCopyCode());

					mapVo1.put("acc_year", map2.get("acc_year").get(1));

					mapVo1.put("acc_month", (map2.get("acc_month").get(1).length() == 1) ? ('0' + map2.get("acc_month").get(1).toString()) : map2
					        .get("acc_month").get(1).toString());

					mapVo1.put("dept_code", map2.get("dept_id").get(1));

					mapVo1.put("dept_id", dept_id);
					mapVo1.put("dept_no", dept_no);

					if (null != r_m.get(key)) {
						mapVo1.put("cost_item_id", r_m.get(key).toString().split("-")[0]);
						mapVo1.put("cost_item_no", r_m.get(key).toString().split("-")[1]);
						mapVo1.put("amount", map2.get(key).get(1));
						mapVo1.put("source_id", '1');
					} else {
						return "{\"msg\":\"" + map2.get(key).get(0) + "，" + key + " 不存在！\",\"state\":\"false\",\"row_cell\":\""
						        + map2.get(key).get(0) + "\"}";
					}
					costOutAcctVouchMapper.deleteCostOutAcctVouch(mapVo1);
				 	costOutAcctVouchMapper.addCostOutAcctVouch(mapVo1);
				}
			}

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}

	}

	@Override
    public String queryCostOutAcctVouchPrm(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("group_id", SessionManager.getGroupId());
		m.put("hos_id", SessionManager.getHosId());
		m.put("copy_code", SessionManager.getCopyCode());
		m.put("is_last", "1"); 
		m.put("is_stop", "0"); 
		//收费项目集合
		List<CostItemDictNo> prm = (List<CostItemDictNo>) costItemDictNoMapper.queryCostItemDictNo(m);
		//由于绩效成本中 收费项目 特殊  和普通账套下的收费项目数量相差太大,数量超过一定量后，页面会加载崩溃， 为避免页面加载崩溃 暂时控制收费项目个数为50个
		List<CostItemDictNo> prm_new=new ArrayList<CostItemDictNo>();
		if(prm.size()>50){
			for (int i = 0; i < prm.size(); i++) {
				if(i>50){
					break;
				}
				prm_new.add(prm.get(i));
            } 
			
		}else{
			prm_new=prm;
		}
		entityMap.put("prm", prm_new);
		
		entityMap.put("is_flag", SessionManager.getCostParaMap().get("03001"));
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = costOutAcctVouchMapper.queryCostOutAcctVouchPrm(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = costOutAcctVouchMapper.queryCostOutAcctVouchPrm(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
    }

	@Override
	public String deleteMonthlyCostOutAcctVouch(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			costOutAcctVouchMapper.deleteMonthlyCostOutAcctVouch(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
            logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除成功\"}");
		}
	}

	@Override
	public String checkCostOutAcctVouch(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal()==-1){
				List<Map<String, Object>> list = costOutAcctVouchMapper.checkCostOutAcctVouch(entityMap);
				return ChdJson.toJsonLower(list);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = costOutAcctVouchMapper.checkCostOutAcctVouch(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				return ChdJson.toJsonLower(list, page.getTotal());
			}
	}
}
