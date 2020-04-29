/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.jdbc.ConfigInit;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostCollectionMapper;
import com.chd.hrp.cost.dao.CostDeptDriDataMapper;
import com.chd.hrp.cost.dao.CostItemDictNoMapper;
import com.chd.hrp.cost.entity.CostDeptDriData;
import com.chd.hrp.cost.entity.CostItemDictNo;
import com.chd.hrp.cost.service.CostCollectionService;
import com.github.pagehelper.PageInfo;
 
/**
 * @Title. @Description. 科室成本归集
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costCollectionService")
public class CostCollectionServiceImpl implements CostCollectionService {

	private static Logger logger = Logger.getLogger(CostCollectionServiceImpl.class);
 
	@Resource(name = "costCollectionMapper")
	private final CostCollectionMapper costCollectionMapper = null;

	@Resource(name = "costDeptDriDataMapper")
	private final CostDeptDriDataMapper costDeptDriDataMapper = null;
	
	@Resource(name = "costItemDictNoMapper")
	private final CostItemDictNoMapper costItemDictNoMapper = null;

	@Override
	public String queryCostCollection(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<CostDeptDriData> list = costCollectionMapper.queryCostCollection(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),

			        sysPage.getPagesize());

			List<CostDeptDriData> list = costCollectionMapper.queryCostCollection(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
	}
	@Override
	public List<Map<String, Object>> queryCostCollectionPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costCollectionMapper.queryCostCollectionPrint(entityMap);
		
		return list;

	}
	@Override
	public String addCostCollection(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		String costCollectionJson = "";

		entityMap.put("para_code", "03002");

		/*
		 * 0 成本来源于财务 1 成本来源于业务
		 */

		costCollectionJson = addCostCollectionBusiAcc(entityMap);

		/*
		 * if("0".equals(accPara.getPara_value())){ costCollectionJson =
		 * addCostCollectionBusiAcc(entityMap); }else { costCollectionJson =
		 * addCostCollectionBusiTran(entityMap); }
		 */

		return costCollectionJson;
	}
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	/*
	 * 科室成本归集来源与财务
	 */
	public String addCostCollectionBusiAcc(Map<String, Object> entityMap) {

		List<Map<String, Object>> addMapList = new ArrayList<Map<String, Object>>();

		Map<String, Object> delmapVo = new HashMap<String, Object>();
		try {

			List<Map<String, Object>> listMaps = costCollectionMapper.queryCostCollectionBusiAcc(entityMap);

			if (listMaps.size() == 0) {

				return "{\"error\":\"支出采集没有数据！无法归集\"}";

			}

			for (Map<String, Object> mapVosMap : listMaps) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", mapVosMap.get("group_id"));

				delmapVo.put("group_id", mapVosMap.get("group_id"));

				mapVo.put("hos_id", mapVosMap.get("hos_id"));

				delmapVo.put("hos_id", mapVosMap.get("hos_id"));

				mapVo.put("copy_code", mapVosMap.get("copy_code"));

				delmapVo.put("copy_code", mapVosMap.get("copy_code"));

				mapVo.put("acc_year", mapVosMap.get("acc_year"));

				delmapVo.put("acc_year", mapVosMap.get("acc_year"));

				mapVo.put("acc_month", mapVosMap.get("acc_month"));

				delmapVo.put("acc_month", mapVosMap.get("acc_month"));

				mapVo.put("dept_id", mapVosMap.get("dept_id"));

				mapVo.put("dept_no", mapVosMap.get("dept_no"));

				mapVo.put("cost_item_id", mapVosMap.get("cost_item_id"));

				mapVo.put("cost_item_no", mapVosMap.get("cost_item_no"));

				mapVo.put("source_id", mapVosMap.get("source_id"));

				mapVo.put("dir_amount", mapVosMap.get("dir_amount"));

				addMapList.add(mapVo);

			}
//删除已有数据
			costDeptDriDataMapper.deleteCostDeptDriData(delmapVo);
			//分批 批量添加
			Properties config = ConfigInit.getConfiguration();
			String v_count = config.getProperty("maxBatchAmount");
			if (null == v_count) {
				v_count = "1000";
			}
			int count = 1000;
			boolean flag = isNumeric(v_count);

			if (flag) {
				count = Integer.valueOf(v_count);
				// 批量处理太多 影响效率
				if (count > 1000) {
					count = 1000;
				}
			}

			List<Map<String, Object>> costList = new ArrayList<Map<String, Object>>(count);

			for (Map<String, Object> key : addMapList) {

				costList.add(key);

				if (costList.size() == count) {
					
					costDeptDriDataMapper.addBatchCostDeptDriData(costList);

					costList = new ArrayList<Map<String, Object>>(count);

				}
			}
			if (costList.size() > 0) {
				costDeptDriDataMapper.addBatchCostDeptDriData(costList);
			}

			return "{\"msg\":\"科室成本归集成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"归集失败\"}");
		}

	}

	/*
	 * 科室成本归集来源与业务
	 */
	public String addCostCollectionBusiTran(Map<String, Object> entityMap) {

		List<Map<String, Object>> addMapList = new ArrayList<Map<String, Object>>();

		Map<String, Object> delmapVo = new HashMap<String, Object>();
		try {

			String wageString = costCollectionMapper.queryCostCollectionWage();

			String bonusString = costCollectionMapper.queryCostCollectionBonus();

			entityMap.put("wageString", wageString);

			entityMap.put("bonusString", bonusString);

			List<Map<String, Object>> listMaps = costCollectionMapper.queryCostCollectionBusiTran(entityMap);

			if (listMaps.size() == 0) {
 
				return "{\"error\":\"支出采集没有数据！无法归集\"}";

			}

			for (Map<String, Object> mapVosMap : listMaps) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", mapVosMap.get("group_id"));

				delmapVo.put("group_id", mapVosMap.get("group_id"));

				mapVo.put("hos_id", mapVosMap.get("hos_id"));

				delmapVo.put("hos_id", mapVosMap.get("hos_id"));

				mapVo.put("copy_code", mapVosMap.get("copy_code"));

				delmapVo.put("copy_code", mapVosMap.get("copy_code"));

				mapVo.put("acc_year", mapVosMap.get("acc_year"));

				delmapVo.put("acc_year", mapVosMap.get("acc_year"));

				mapVo.put("acc_month", mapVosMap.get("acc_month"));

				delmapVo.put("acc_month", mapVosMap.get("acc_month"));

				mapVo.put("dept_id", mapVosMap.get("dept_id"));

				mapVo.put("dept_no", mapVosMap.get("dept_no"));

				mapVo.put("cost_item_id", mapVosMap.get("cost_item_id"));

				mapVo.put("cost_item_no", mapVosMap.get("cost_item_no"));

				mapVo.put("source_id", mapVosMap.get("source_id"));

				mapVo.put("dir_amount", mapVosMap.get("dir_amount"));

				addMapList.add(mapVo);

			}

			costDeptDriDataMapper.deleteCostDeptDriData(delmapVo);

			costDeptDriDataMapper.addBatchCostDeptDriData(addMapList);

			return "{\"msg\":\"科室成本归集成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostCollectionBusiTran\"}";
		}

	}
	
	@Override
	public String queryCostCollectionPrmHead(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = costCollectionMapper.queryCostCollectionPrmHead(entityMap);
		return  ChdJson.toJson(list);
	}
	
	
	@Override
    public String queryCostCollectionPrm(Map<String, Object> entityMap) throws DataAccessException {
		/*Map<String, Object> m = new HashMap<String, Object>();
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
		}*/
		List<Map<String, Object>> prm_new = costCollectionMapper.queryCostCollectionPrmHead(entityMap);
		entityMap.put("prm", prm_new);
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costCollectionMapper.queryCostCollectionPrm(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),

			        sysPage.getPagesize());

			List<Map<String, Object>> list = costCollectionMapper.queryCostCollectionPrm(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
    }
	@Override
	public List<Map<String, Object>> queryCostCollectionPrmPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		List<Map<String, Object>> prm_new = costCollectionMapper.queryCostCollectionPrmHead(entityMap);
		entityMap.put("prm", prm_new);
		List<Map<String, Object>> list = costCollectionMapper.queryCostCollectionPrm(entityMap);
		return list;
	}

}
