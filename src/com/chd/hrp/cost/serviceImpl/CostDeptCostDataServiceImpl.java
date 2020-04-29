/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostAllocationProcessMapper;
import com.chd.hrp.cost.dao.CostDeptCostDataMapper;
import com.chd.hrp.cost.dao.CostDeptDriDataMapper;
import com.chd.hrp.cost.dao.CostParaAssSetDataMapper;
import com.chd.hrp.cost.dao.CostParaManSetDataMapper;
import com.chd.hrp.cost.dao.CostParaMedSetDataMapper;
import com.chd.hrp.cost.entity.CostDeptCostData;
import com.chd.hrp.cost.entity.CostDeptDriData;
import com.chd.hrp.cost.entity.CostParaAssSetData;
import com.chd.hrp.cost.entity.CostParaManSetData;
import com.chd.hrp.cost.entity.CostParaMedSetData;
import com.chd.hrp.cost.entity.CostStructureAnalysis;
import com.chd.hrp.cost.service.CostDeptCostDataService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 科室成本核算表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costDeptCostDataService")
public class CostDeptCostDataServiceImpl implements CostDeptCostDataService {

	private static Logger logger = Logger.getLogger(CostDeptCostDataServiceImpl.class);
	
	@Resource(name = "costDeptCostDataMapper")
	private final CostDeptCostDataMapper costDeptCostDataMapper = null;

	//=====================================
	
	@Resource(name = "costParaManSetDataMapper")
	private final CostParaManSetDataMapper costParaManSetDataMapper = null;//管理科室分摊数据采集表
	
	@Resource(name = "costParaAssSetDataMapper")
	private final CostParaAssSetDataMapper costParaAssSetDataMapper = null;//医辅科室分摊数据采集表
	
	@Resource(name = "costParaMedSetDataMapper")
	private final CostParaMedSetDataMapper costParaMedSetDataMapper = null;//医技科室分摊数据采集表
	
	@Resource(name = "costDeptDriDataMapper")
	private final CostDeptDriDataMapper costDeptDriDataMapper = null;//科室直接成本表
	
	@Resource(name = "costAllocationProcessMapper")
	private final CostAllocationProcessMapper costAllocationProcessMapper = null;//科室直接成本表
	
	/**
	 * @Description 
	 * 科室成本核算表<BR> 科室成本分摊
	 * @param CostDeptCostData entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deptCostShareData(Map<String, Object> entityMap)throws DataAccessException {
		
		try {
			
			String year_month =(String)entityMap.get("year_month");
			
			entityMap.put("acc_year", year_month.substring(0, 4));
			
			entityMap.put("acc_month", year_month.substring(4, 6));

			Map<String,Double> costDeptDriDataMap = new HashMap<String,Double>();//组装直接成本map
			
			Map<String,Long> costDeptDriSourceMap = new HashMap<String,Long>();//组装资金来源map
			
			List<Map<String,Object>> allocationProcessList = new ArrayList<Map<String,Object>>();//组装分摊过程map
			
			Map<String,Map<String,Object>> costDeptCostDataMap = new HashMap<String,Map<String,Object>>();//组装直接成本map
			
			Map<String,Double> equalMap = new HashMap<String,Double>();//组装平级分摊map
			
			Map<String,Double> dirManAmountMap = new HashMap<String,Double>();//组装管理分摊map
			
			Map<String,Double> dirAssAmountMap = new HashMap<String,Double>();//组装医辅map
			
			Map<String,Double> indirAssManAmountMap = new HashMap<String,Double>();//组装间接分摊医辅管理成本map

			//1查询科室直接成本表
			
			List<CostDeptDriData> deptDriDataList = costDeptDriDataMapper.queryCostDeptDriDataByShare(entityMap);
			
			for(CostDeptDriData cddd:deptDriDataList){
				
				String key = cddd.getDept_id().toString()+cddd.getDept_no()+cddd.getCost_item_id()+cddd.getCost_item_no();
					
				costDeptDriDataMap.put(key, cddd.getDir_amount());
				
				costDeptDriSourceMap.put(key, cddd.getSource_id());
				
				Map<String,Object> map =new HashMap<String,Object>();
				
				map.put("group_id", cddd.getGroup_id());map.put("hos_id", cddd.getHos_id());
				
				map.put("copy_code", cddd.getCopy_code());map.put("acc_year", cddd.getAcc_year());
				
				map.put("acc_month", cddd.getAcc_month());map.put("dept_id", cddd.getDept_id());
				
				map.put("dept_no", cddd.getDept_no());map.put("cost_item_id", cddd.getCost_item_id());
				
				map.put("cost_item_no", cddd.getCost_item_no());map.put("source_id", cddd.getSource_id());
				
				map.put("dir_amount", (cddd.getDir_amount()==null)?0:cddd.getDir_amount());
				
				map.put("man_amount",0.0);
				
				map.put("ass_amount", 0.0);map.put("med_amount", 0.0);
				
				costDeptCostDataMap.put(key, map);
				
			}

			//管理科室平级分摊
			List<CostParaManSetData>  costParaManSetDataList = costParaManSetDataMapper.queryCostParaManSetDataByShare(entityMap);
			
			for(CostParaManSetData cpmsd:costParaManSetDataList){
				
				if(cpmsd.getType_code().equals(cpmsd.getS_type_code())){
					
					String processKey =cpmsd.getDept_id().toString() + cpmsd.getDept_no() + cpmsd.getCost_item_id() + cpmsd.getCost_item_no();
					
					String equalKey =cpmsd.getServer_dept_id().toString() + cpmsd.getServer_dept_no()+ cpmsd.getCost_item_id() + cpmsd.getCost_item_no();
					
					Map<String,Object> map =new HashMap<String,Object>();

					map.put("group_id", cpmsd.getGroup_id());map.put("hos_id", cpmsd.getHos_id());map.put("copy_code", cpmsd.getCopy_code());
					
					map.put("acc_year", cpmsd.getAcc_year());map.put("acc_month", cpmsd.getAcc_month());
					
					map.put("dept_id", cpmsd.getDept_id());map.put("dept_no", cpmsd.getDept_no());map.put("source_id", costDeptDriSourceMap.get(processKey));
					
					map.put("server_dept_id", cpmsd.getServer_dept_id());map.put("server_dept_no", cpmsd.getServer_dept_no());
					
					map.put("cost_item_id", cpmsd.getCost_item_id());map.put("cost_item_no", cpmsd.getCost_item_no());
					
					Double equal_amount = costDeptDriDataMap.get(processKey)*(cpmsd.getPara_value()/cpmsd.getTotal_value());

					map.put("equal_amount", equal_amount);//科室直接成本取 costDeptDriDataMap 里面的数据
					
					if(equalMap.get(equalKey) != null){
						
						equalMap.put(equalKey, equalMap.get(equalKey)+equal_amount);
						
					}else{
						
						equalMap.put(equalKey, equal_amount);
						
					}
					
					map.put("dir_amount", costDeptDriDataMap.get(processKey));map.put("dir_man_amount",0) ;
					
					map.put("dir_ass_amount", 0);map.put("dir_med_amount", 0);//受益科室参数值/总参数值为
					
					map.put("indir_ass_man_amount", 0);map.put("indir_med_man_amount", 0);
					
					map.put("indir_ass_med_man_amount", 0);map.put("indir_med_ass_amount", 0);
					
					allocationProcessList.add(map);
					
				}

			}

			//管理科室分摊
			
			for(CostParaManSetData cpmsd:costParaManSetDataList){
				
				if(!cpmsd.getType_code().equals(cpmsd.getS_type_code())){
					
					String processKey =cpmsd.getDept_id().toString() + cpmsd.getDept_no() + cpmsd.getCost_item_id() + cpmsd.getCost_item_no();
					
					String dirManKey =cpmsd.getServer_dept_id().toString() + cpmsd.getServer_dept_no()+ cpmsd.getCost_item_id() + cpmsd.getCost_item_no();
					
					Map<String,Object> map =new HashMap<String,Object>();

					map.put("group_id", cpmsd.getGroup_id());map.put("hos_id", cpmsd.getHos_id());map.put("copy_code", cpmsd.getCopy_code());
					
					map.put("acc_year", cpmsd.getAcc_year());map.put("acc_month", cpmsd.getAcc_month());
					
					map.put("dept_id", cpmsd.getDept_id());map.put("dept_no", cpmsd.getDept_no());map.put("source_id", costDeptDriSourceMap.get(processKey));
					
					map.put("server_dept_id", cpmsd.getServer_dept_id());map.put("server_dept_no", cpmsd.getServer_dept_no());
					
					map.put("cost_item_id", cpmsd.getCost_item_id());map.put("cost_item_no", cpmsd.getCost_item_no());

					map.put("equal_amount", 0);map.put("dir_amount", (costDeptDriDataMap.get(processKey)==null)?0:costDeptDriDataMap.get(processKey));//科室直接成本取 costDeptDriDataMap 里面的数据
					
					Double dir_amount = (costDeptDriDataMap.get(processKey)==null)?0:costDeptDriDataMap.get(processKey);
					
					if(equalMap.get(processKey) !=null){
						
						dir_amount = equalMap.get(processKey) + dir_amount;
						
					}
					
					map.put("dir_ass_amount", 0);map.put("dir_med_amount", 0);
					
					Double man_amount = dir_amount*(cpmsd.getPara_value()/cpmsd.getTotal_value());
					
					map.put("dir_man_amount",man_amount) ;

					dirManAmountMap.put(dirManKey, man_amount);
					
					if(costDeptCostDataMap.get(dirManKey) != null){
						
						Map mapDeptCost = costDeptCostDataMap.get(dirManKey);
						
						mapDeptCost.put("man_amount", (Double)mapDeptCost.get("man_amount")+man_amount);
						
						costDeptCostDataMap.put(dirManKey, mapDeptCost);

					}
					
					map.put("indir_ass_man_amount", 0);map.put("indir_med_man_amount", 0);
					
					map.put("indir_ass_med_man_amount", 0);map.put("indir_med_ass_amount", 0);
					
					allocationProcessList.add(map);
					
				}
			
			}

			//医辅科室平级分摊
			List<CostParaAssSetData>  costParaAssSetDataList = costParaAssSetDataMapper.queryCostParaAssSetDataByShare(entityMap);
			
			for(CostParaAssSetData cpasd:costParaAssSetDataList){
				
				if(cpasd.getType_code().equals(cpasd.getS_type_code())){
					
					String processKey =cpasd.getDept_id().toString() + cpasd.getDept_no() + cpasd.getCost_item_id() + cpasd.getCost_item_no();
					
					String equalKey =cpasd.getServer_dept_id().toString() + cpasd.getServer_dept_no()+ cpasd.getCost_item_id() + cpasd.getCost_item_no();
					
					Map<String,Object> map =new HashMap<String,Object>();

					map.put("group_id", cpasd.getGroup_id());map.put("hos_id", cpasd.getHos_id());map.put("copy_code", cpasd.getCopy_code());
					
					map.put("acc_year", cpasd.getAcc_year());map.put("acc_month", cpasd.getAcc_month());
					
					map.put("dept_id", cpasd.getDept_id());map.put("dept_no", cpasd.getDept_no());map.put("source_id", costDeptDriSourceMap.get(processKey));
					
					map.put("server_dept_id", cpasd.getServer_dept_id());map.put("server_dept_no", cpasd.getServer_dept_no());
					
					map.put("cost_item_id", cpasd.getCost_item_id());map.put("cost_item_no", cpasd.getCost_item_no());
					
					Double equal_amount = costDeptDriDataMap.get(processKey)==null?0:costDeptDriDataMap.get(processKey)*(cpasd.getPara_value()/cpasd.getTotal_value());

					map.put("equal_amount", equal_amount);//科室直接成本取 costDeptDriDataMap 里面的数据
					
					if(equalMap.get(equalKey) != null){
						
						equalMap.put(equalKey, equalMap.get(equalKey)+equal_amount);
						
					}else{
						
						equalMap.put(equalKey, equal_amount);
						
					}
					
					map.put("dir_amount", costDeptDriDataMap.get(processKey)==null?0:costDeptDriDataMap.get(processKey));map.put("dir_man_amount",0) ;
					
					map.put("dir_ass_amount", 0);map.put("dir_med_amount", 0);//受益科室参数值/总参数值为
					
					map.put("indir_ass_man_amount", 0);map.put("indir_med_man_amount", 0);
					
					map.put("indir_ass_med_man_amount", 0);map.put("indir_med_ass_amount", 0);
					
					allocationProcessList.add(map);
					
				}

			}

			//医辅科室分摊

			for(CostParaAssSetData cpasd:costParaAssSetDataList){
				
				if(!cpasd.getType_code().equals(cpasd.getS_type_code())){
					
					String processKey =cpasd.getDept_id().toString() + cpasd.getDept_no() + cpasd.getCost_item_id() + cpasd.getCost_item_no();
					
					String equalKey =cpasd.getServer_dept_id().toString() + cpasd.getServer_dept_no()+ cpasd.getCost_item_id() + cpasd.getCost_item_no();
					
					Map<String,Object> map =new HashMap<String,Object>();

					map.put("group_id", cpasd.getGroup_id());map.put("hos_id", cpasd.getHos_id());map.put("copy_code", cpasd.getCopy_code());
					
					map.put("acc_year", cpasd.getAcc_year());map.put("acc_month", cpasd.getAcc_month());
					
					map.put("dept_id", cpasd.getDept_id());map.put("dept_no", cpasd.getDept_no());map.put("source_id", costDeptDriSourceMap.get(processKey));
					
					map.put("server_dept_id", cpasd.getServer_dept_id());map.put("server_dept_no", cpasd.getServer_dept_no());
					
					map.put("cost_item_id", cpasd.getCost_item_id());map.put("cost_item_no", cpasd.getCost_item_no());

					Double dir_amount = costDeptDriDataMap.get(processKey)==null?0:costDeptDriDataMap.get(processKey);
					
					map.put("dir_amount", dir_amount);//科室直接成本取 costDeptDriDataMap 里面的数据
					
					if(equalMap.get(processKey) != null){
						
						dir_amount = dir_amount + equalMap.get(processKey);
						
						map.put("equal_amount", 0);
						
					}else{
						
						map.put("equal_amount", 0);
						
					}
					
					Double ass_amount = dir_amount*(cpasd.getPara_value()/cpasd.getTotal_value());
					
					map.put("dir_ass_amount", ass_amount);
					
					dirAssAmountMap.put(equalKey, ass_amount);
					
					map.put("indir_ass_man_amount", 0);
					
					Double ass_man_amount = 0.0;
					
					if(dirManAmountMap.get(processKey)  != null){//分摊医辅、间接分摊医辅管理

						ass_man_amount = dirManAmountMap.get(processKey) *(cpasd.getPara_value()/cpasd.getTotal_value());
								
						map.put("indir_ass_man_amount", ass_man_amount);
						
						indirAssManAmountMap.put(equalKey, ass_man_amount);
						
					}
					
					if(costDeptCostDataMap.get(equalKey) != null){//合计成本核算表
						
						Map mapDeptCost = costDeptCostDataMap.get(equalKey);
						
						mapDeptCost.put("ass_amount", (Double)mapDeptCost.get("ass_amount")+ass_amount+ass_man_amount);
						
						costDeptCostDataMap.put(equalKey, mapDeptCost);

					}

					map.put("dir_man_amount",0) ;
					
					map.put("dir_med_amount", 0);//受益科室参数值/总参数值为
					
					map.put("indir_med_man_amount", 0);
					
					map.put("indir_ass_med_man_amount", 0);map.put("indir_med_ass_amount", 0);
					
					allocationProcessList.add(map);
					
				}

			}

			//医技科室平级分摊
			List<CostParaMedSetData>  costParaMedSetDataList = costParaMedSetDataMapper.queryCostParaMedSetDataByShare(entityMap);
			
			for(CostParaMedSetData cpmsd:costParaMedSetDataList){
				
				if(cpmsd.getType_code().equals(cpmsd.getS_type_code())){
					
					String processKey =cpmsd.getDept_id().toString() + cpmsd.getDept_no() + cpmsd.getCost_item_id() + cpmsd.getCost_item_no();
					
					String equalKey =cpmsd.getServer_dept_id().toString() + cpmsd.getServer_dept_no()+ cpmsd.getCost_item_id() + cpmsd.getCost_item_no();
					
					Map<String,Object> map =new HashMap<String,Object>();

					map.put("group_id", cpmsd.getGroup_id());map.put("hos_id", cpmsd.getHos_id());map.put("copy_code", cpmsd.getCopy_code());
					
					map.put("acc_year", cpmsd.getAcc_year());map.put("acc_month", cpmsd.getAcc_month());
					
					map.put("dept_id", cpmsd.getDept_id());map.put("dept_no", cpmsd.getDept_no());map.put("source_id", costDeptDriSourceMap.get(processKey));
					
					map.put("server_dept_id", cpmsd.getServer_dept_id());map.put("server_dept_no", cpmsd.getServer_dept_no());
					
					map.put("cost_item_id", cpmsd.getCost_item_id());map.put("cost_item_no", cpmsd.getCost_item_no());

					Double equal_amount = costDeptDriDataMap.get(processKey)==null?0:costDeptDriDataMap.get(processKey)*(cpmsd.getPara_value()/cpmsd.getTotal_value());

					map.put("equal_amount", equal_amount);//科室直接成本取 costDeptDriDataMap 里面的数据
					
					if(equalMap.get(equalKey) != null){
						
						equalMap.put(equalKey, equalMap.get(equalKey)+equal_amount);
						
					}else{
						
						equalMap.put(equalKey, equal_amount);
						
					}
					
					map.put("dir_amount", costDeptDriDataMap.get(processKey)==null?0:costDeptDriDataMap.get(processKey));//科室直接成本取 costDeptDriDataMap 里面的数据
					
					map.put("dir_man_amount",0) ;
					
					map.put("dir_ass_amount",0);map.put("dir_med_amount", cpmsd.getPara_value()/cpmsd.getTotal_value());//受益科室参数值/总参数值为
					
					map.put("indir_ass_man_amount", 0);map.put("indir_med_man_amount", 0);
					
					map.put("indir_ass_med_man_amount", 0);map.put("indir_med_ass_amount", 0);
					
					allocationProcessList.add(map);
					
				}

			}
			
			//医技科室分摊

			for(CostParaMedSetData cpmsd:costParaMedSetDataList){
				
				if(!cpmsd.getType_code().equals(cpmsd.getS_type_code())){
					
					String processKey =cpmsd.getDept_id().toString() + cpmsd.getDept_no() + cpmsd.getCost_item_id() + cpmsd.getCost_item_no();
					
					String equalKey =cpmsd.getServer_dept_id().toString() + cpmsd.getServer_dept_no()+ cpmsd.getCost_item_id() + cpmsd.getCost_item_no();
					
					Map<String,Object> map =new HashMap<String,Object>();

					map.put("group_id", cpmsd.getGroup_id());map.put("hos_id", cpmsd.getHos_id());map.put("copy_code", cpmsd.getCopy_code());
					
					map.put("acc_year", cpmsd.getAcc_year());map.put("acc_month", cpmsd.getAcc_month());
					
					map.put("dept_id", cpmsd.getDept_id());map.put("dept_no", cpmsd.getDept_no());map.put("source_id", costDeptDriSourceMap.get(processKey));
					
					map.put("server_dept_id", cpmsd.getServer_dept_id());map.put("server_dept_no", cpmsd.getServer_dept_no());
					
					map.put("cost_item_id", cpmsd.getCost_item_id());map.put("cost_item_no", cpmsd.getCost_item_no());

					Double dir_amount = costDeptDriDataMap.get(processKey)==null?0:costDeptDriDataMap.get(processKey);
					
					map.put("dir_amount", dir_amount);//科室直接成本取 costDeptDriDataMap 里面的数据
					
					if(equalMap.get(processKey) != null){
						
						dir_amount = dir_amount + equalMap.get(processKey);
						
						map.put("equal_amount", 0);
						
					}else{
						
						map.put("equal_amount", 0);
						
					}
					
					Double med_amount = dir_amount*(cpmsd.getPara_value()/cpmsd.getTotal_value());
					
					map.put("dir_med_amount", med_amount);
					
					map.put("indir_ass_man_amount", 0);
					
					Double med_man_amount = 0.0;
					
					if(dirManAmountMap.get(processKey)  != null){
						
						med_man_amount = dirAssAmountMap.get(processKey) *(cpmsd.getPara_value()/cpmsd.getTotal_value());
						
						map.put("indir_med_man_amount", med_man_amount);
						
					}
					
					Double med_ass_amount = 0.0;
					
					if(dirAssAmountMap.get(processKey)  != null){
						
						med_ass_amount = dirAssAmountMap.get(processKey) *(cpmsd.getPara_value()/cpmsd.getTotal_value());
						
						map.put("indir_med_ass_amount", med_ass_amount);
						
					}

					Double ass_med_man_amount = 0.0;
					
					if(indirAssManAmountMap.get(processKey) != null){
						
						ass_med_man_amount = indirAssManAmountMap.get(processKey) *(cpmsd.getPara_value()/cpmsd.getTotal_value());
						
						map.put("indir_ass_med_man_amount", ass_med_man_amount);
						
					}
					
					if(costDeptCostDataMap.get(equalKey) != null){//合计成本核算表
						
						Map mapDeptCost = costDeptCostDataMap.get(equalKey);
						
						mapDeptCost.put("med_amount", (Double)mapDeptCost.get("med_amount")+med_amount+med_man_amount+med_ass_amount+ass_med_man_amount+ass_med_man_amount);
						
						costDeptCostDataMap.put(equalKey, mapDeptCost);

					}

					map.put("dir_man_amount",0) ;map.put("dir_ass_amount",0);
					
					map.put("indir_ass_man_amount",0);
					
					allocationProcessList.add(map);
					
				}

			}

			if(allocationProcessList.size() >0 ){
				
				costAllocationProcessMapper.deleteCostAllocationProcess(entityMap);//删除分摊过程表 当月数据
				
				costAllocationProcessMapper.addBatchCostAllocationProcess(allocationProcessList);
				
				//汇总科室直接成本表
				
				costDeptCostDataMapper.deleteCostDeptCostData(entityMap);
				
				List<Map<String,Object>> costDeptCostDataList = new ArrayList<Map<String,Object>>();
				
				for(String key :costDeptCostDataMap.keySet()){
					
					costDeptCostDataList.add(costDeptCostDataMap.get(key));
					
				}
				
				costDeptCostDataMapper.addBatchCostDeptCostData(costDeptCostDataList);
				
			}else{
				
				return "{\"error\":\"分摊失败 当前配置没有采集到数据! \"}";
				
			}
			
			return "{\"msg\":\"分摊完成.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"分摊失败 数据库异常 请联系管理员! 错误编码 deptCostShareData\"}";

		}
	}

	
	/**
	 * @Description 
	 * 科室成本核算表<BR> 查询CostDeptCostData分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostDeptCostData(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<CostDeptCostData> list = costDeptCostDataMapper.queryCostDeptCostData(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<CostDeptCostData> list = costDeptCostDataMapper.queryCostDeptCostData(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}

	@Override
	public String queryCostStructureAnalysis(Map<String, Object> entityMap)throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<CostStructureAnalysis> list = costDeptCostDataMapper.queryCostStructureAnalysis(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<CostStructureAnalysis> list = costDeptCostDataMapper.queryCostStructureAnalysis(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}


	@Override
	public CostDeptCostData queryCostDeptCostDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
}
