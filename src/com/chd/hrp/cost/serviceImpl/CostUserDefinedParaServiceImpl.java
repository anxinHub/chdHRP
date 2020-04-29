/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.cost.dao.CostDeptParaDictMapper;
import com.chd.hrp.cost.dao.CostInComeCollectionMapper;
import com.chd.hrp.cost.dao.CostUserDefinedParaMapper;
import com.chd.hrp.cost.entity.CostClinicWork;
import com.chd.hrp.cost.entity.CostDeptParaDict;
import com.chd.hrp.cost.entity.CostUserDefinedPara;
import com.chd.hrp.cost.service.CostUserDefinedParaService;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 自定义参数数据采集表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costUserDefinedParaService")
public class CostUserDefinedParaServiceImpl implements
		CostUserDefinedParaService {

	private static Logger logger = Logger
			.getLogger(CostUserDefinedParaServiceImpl.class);

	@Resource(name = "costUserDefinedParaMapper")
	private final CostUserDefinedParaMapper costUserDefinedParaMapper = null;

	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;
 
	@Resource(name = "costDeptParaDictMapper")
	private final CostDeptParaDictMapper costDeptParaDictMapper = null;


	/**
	 * @Description 自定义参数数据采集表<BR>
	 *              添加CostUserDefinedPara
	 * @param CostUserDefinedPara
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addCostUserDefinedPara(Map<String, Object> entityMap)
			throws DataAccessException {

		CostUserDefinedPara costUserDefinedPara = queryCostUserDefinedParaByCode(entityMap);

		if (costUserDefinedPara != null) {

			/*
			 * 2016/11/3 lxj 修改提示信息,解决报空指针异常的问题
			 */

			return "{\"error\":\"对应关系已存在.\"}";

		}

		try {

			costUserDefinedParaMapper.addCostUserDefinedPara(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostUserDefinedPara\"}";

		}

	}

	/**
	 * @Description 自定义参数数据采集表<BR>
	 *              批量添加CostUserDefinedPara
	 * @param CostUserDefinedPara
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchCostUserDefinedPara(
			List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costUserDefinedParaMapper.addBatchCostUserDefinedPara(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostUserDefinedPara\"}";

		}
	}

	/**
	 * @Description 自定义参数数据采集表<BR>
	 *              查询CostUserDefinedPara分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryCostUserDefinedPara(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<CostUserDefinedPara> list = costUserDefinedParaMapper
					.queryCostUserDefinedPara(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<CostUserDefinedPara> list = costUserDefinedParaMapper
					.queryCostUserDefinedPara(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public List<Map<String, Object>> queryCostUserDefinedParaPrint(
			Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> list = costUserDefinedParaMapper
				.queryCostUserDefinedParaPrint(entityMap);

		return list;

	}

	/**
	 * @Description 自定义参数数据采集表<BR>
	 *              查询CostUserDefinedParaByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public CostUserDefinedPara queryCostUserDefinedParaByCode(
			Map<String, Object> entityMap) throws DataAccessException {

		return costUserDefinedParaMapper
				.queryCostUserDefinedParaByCode(entityMap);

	}

	/**
	 * @Description 自定义参数数据采集表<BR>
	 *              批量删除CostUserDefinedPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchCostUserDefinedPara(
			List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			int state = costUserDefinedParaMapper
					.deleteBatchCostUserDefinedPara(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostUserDefinedPara\"}";

		}

	}

	/**
	 * @Description 自定义参数数据采集表<BR>
	 *              删除CostUserDefinedPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteCostUserDefinedPara(Map<String, Object> entityMap)
			throws DataAccessException {

		try {
			costUserDefinedParaMapper.deleteCostUserDefinedPara(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostUserDefinedPara\"}";

		}
	}

	@Override
	public String deleteMonthlyCostUserDefinedPara(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			costUserDefinedParaMapper.deleteMonthlyCostUserDefinedPara(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}
	
	/**
	 * @Description 自定义参数数据采集表<BR>
	 *              更新CostUserDefinedPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateCostUserDefinedPara(Map<String, Object> entityMap)
			throws DataAccessException {

		try {

			costUserDefinedParaMapper.updateCostUserDefinedPara(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostUserDefinedPara\"}";

		}
	}

	/**
	 * @Description 自定义参数数据采集表<BR>
	 *              批量更新CostUserDefinedPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchCostUserDefinedPara(
			List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costUserDefinedParaMapper
					.updateBatchCostUserDefinedPara(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostUserDefinedPara\"}";

		}

	}

	@Override
	public String costUserDefinedParaExtendInheritance(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			
		List<CostUserDefinedPara> costUserDefinedParaList = costUserDefinedParaMapper.queryCostUserDefinedPara(entityMap);
		/*
		 * 源年月自定义参数数据同时存在时
		 * */
		if(costUserDefinedParaList.size()>0){
			
			/*
			 * 删除目标年月自定义参数数据
			 * */
			costUserDefinedParaMapper.deleteCostUserDefinedExtendPara(entityMap);
			

			/*
			 * 添加目标年月自定义参数数据
			 * */
			costUserDefinedParaMapper.addCostUserDefinedExtendPara(entityMap);
			
		}else {
			
			return "{\"error\":\"源年月没有数据可继承.\"}";
		}
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"继承失败\"}");
		}
		return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
	}

	@Override
	public String synchroCostUserDefinedPara(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			List<Map<String, Object>>  resultList =new ArrayList<Map<String,Object>>();
			
			resultList = getSynchroCostUserDefinedPara(entityMap);
			
			if(resultList.size() == 0){
				
				return "{\"error\":\"当月没有数据同步!.\",\"state\":\"false\"}";
			}
			
			costUserDefinedParaMapper.deleteCostUserDefinedExtendPara(entityMap);
			
			costUserDefinedParaMapper.addBatchCostUserDefinedPara(resultList);
			
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"同步失败\"}");
		}
	}
	
	public List<Map<String, Object>> getSynchroCostUserDefinedPara(Map<String, Object> mapVo){
	    
		List<Map<String, Object>>  resultList =new ArrayList<Map<String,Object>>();
		
		String target_para_code = mapVo.get("target_para_code").toString();
		
		if("01".equals(target_para_code)){
			
			resultList = getCostIncome(mapVo);
			
		 }else if("02".equals(target_para_code)){
			 
			 resultList = getCostClinicWork(mapVo);
			 
		 }else if("03".equals(target_para_code)){
			 
			 resultList = getCostInhosWork(mapVo);
		 }
		
		
		
		return   resultList;
	}
	
	//收入
   public List<Map<String, Object>> getCostIncome(Map<String, Object> entityMap){
	
	   List<Map<String, Object>> main = costUserDefinedParaMapper.queryIncomeMain(entityMap);
	   
	   List<Map<String, Object>> detail = costUserDefinedParaMapper.queryIncomeDetail(entityMap);
	   
		return  main.size()>0?main:detail;
	}
	
	   //门急诊人次
	public List<Map<String, Object>> getCostClinicWork(Map<String, Object> entityMap){
			   
			return costUserDefinedParaMapper.getCostClinicWork(entityMap); 
	} 
		//住院床日数
	public List<Map<String, Object>> getCostInhosWork(Map<String, Object> entityMap){
		   
		   return costUserDefinedParaMapper.getCostInhosWork(entityMap); 
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
				if (map2.get("acc_year").get(1) == null
						|| map2.get("acc_year").get(1).equals("")) {

					return "{\"msg\":\"" + map2.get("acc_year").get(0)
							+ "，统计年度为空！\",\"state\":\"false\",\"row_cell\":\""
							+ map2.get("acc_year").get(0) + "\"}";

				} else if (map2.get("acc_month").get(1) == null
						|| map2.get("acc_month").get(1).equals("")) {

					return "{\"msg\":\"" + map2.get("acc_month").get(0)
							+ "，统计月份为空！\",\"state\":\"false\",\"row_cell\":\""
							+ map2.get("acc_month").get(0) + "\"}";

				} else if (map2.get("dept_id").get(1) == null
						|| map2.get("dept_id").get(1).equals("")) {

					return "{\"msg\":\"" + map2.get("dept_id").get(0)
							+ "，科室编码为空！\",\"state\":\"false\",\"row_cell\":\""
							+ map2.get("dept_id").get(0) + "\"}";

				} else if (map2.get("para_code").get(1) == null
						|| map2.get("para_code").get(1).equals("")) {

					return "{\"msg\":\""
							+ map2.get("para_code").get(0)
							+ "，成本分摊编码为空！\",\"state\":\"false\",\"row_cell\":\""
							+ map2.get("para_code").get(0) + "\"}";

				} else if (map2.get("para_value").get(1) == null
						|| map2.get("para_value").get(1).equals("")) {

					return "{\"msg\":\"" + map2.get("para_value").get(0)
							+ "，统计值为空！\",\"state\":\"false\",\"row_cell\":\""
							+ map2.get("para_value").get(0) + "\"}";

				}
				Map<String, Object> mapVo1 = new HashMap<String, Object>();

				mapVo1.put("group_id", SessionManager.getGroupId());

				mapVo1.put("hos_id", SessionManager.getHosId());

				mapVo1.put("copy_code", SessionManager.getCopyCode());

				mapVo1.put("acc_year", map2.get("acc_year").get(1));

				mapVo1.put(
						"acc_month",
						(map2.get("acc_month").get(1).length() == 1) ? ('0' + map2
								.get("acc_month").get(1).toString())
								: map2.get("acc_month").get(1).toString());

				mapVo1.put("dept_id", map2.get("dept_id").get(1));

				mapVo1.put("para_code", map2.get("para_code").get(1));

				mapVo1.put("para_value", map2.get("para_value").get(1));

				// 判断执行科室是否存在
				Map<String, Object> mapVo_dept = new HashMap<String, Object>();
				mapVo_dept.put("group_id", mapVo1.get("group_id"));
				mapVo_dept.put("hos_id", mapVo1.get("hos_id"));
				mapVo_dept.put("copy_code", mapVo1.get("copy_code"));
				mapVo_dept.put("dept_code", mapVo1.get("dept_id"));

				DeptDict deptDict1 = deptDictMapper
						.queryDeptDictByCodeDept(mapVo_dept);

				if (deptDict1 != null) {

					mapVo1.put("dept_no", deptDict1.getDept_no());

					mapVo1.put("dept_id", deptDict1.getDept_id());

				} else {
					throw new SysException(map2.get("dept_id").get(0)
							+ ",科室不存在！");

				}

				// 判断分摊编码是否存在
				Map<String, Object> mapVodept = new HashMap<String, Object>();
				mapVodept.put("group_id", mapVo1.get("group_id"));
				mapVodept.put("hos_id", mapVo1.get("hos_id"));
				mapVodept.put("copy_code", mapVo1.get("copy_code"));
				mapVodept.put("para_code", mapVo1.get("para_code"));

				CostDeptParaDict cdpd = costDeptParaDictMapper
						.queryCostDeptParaDictByCode(mapVodept);

				if (cdpd != null) {

					mapVo1.put("para_code", cdpd.getPara_code());

				}

				costUserDefinedParaMapper.addCostUserDefinedPara(mapVo1);

			}

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());

		}
	}

	

	

}
