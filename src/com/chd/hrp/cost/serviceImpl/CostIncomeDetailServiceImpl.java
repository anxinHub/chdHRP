/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.h2.util.New;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.cost.dao.CostBusiSourecDictMapper;
import com.chd.hrp.cost.dao.CostIncomeDetailMapper;
import com.chd.hrp.cost.entity.CostBusiSourecDict;
import com.chd.hrp.cost.entity.CostIncomeDetail;
import com.chd.hrp.cost.service.CostIncomeDetailService;
import com.chd.hrp.hip.dao.HrpHipSelectMapper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * @Title. @Description. 科室收入数据明细表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costIncomeDetailService")
public class CostIncomeDetailServiceImpl implements CostIncomeDetailService {

	private static Logger logger = Logger.getLogger(CostIncomeDetailServiceImpl.class);

	@Resource(name = "costIncomeDetailMapper")
	private final CostIncomeDetailMapper costIncomeDetailMapper = null;
	
	@Resource(name = "costBusiSourecDictMapper")
	private final CostBusiSourecDictMapper costBusiSourecDictMapper = null;



	/**
	 * @Description 科室收入数据明细表<BR>
	 *              查询CostIncomeDetail分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryCostIncomeDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<CostIncomeDetail> list = costIncomeDetailMapper.queryCostIncomeDetail(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostIncomeDetail> list = costIncomeDetailMapper.queryCostIncomeDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}

	}
	
	

	/**
	 * @Description 科室收入数据明细表<BR>
	 *              批量删除CostIncomeDetail
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchCostIncomeDetail(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

		    costIncomeDetailMapper.deleteBatchCostIncomeDetail(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostIncomeDetail\"}";

		}

	}
	
	/**
	 * @Description 科室支出总账<BR>
	 *              采集成本数据
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Resource(name = "hrpHipSelectMapper")
	private final HrpHipSelectMapper hrpHipSelectMapper = null;

	@Override
	public String addCostIncomeDetailByHis(Map<String, Object> entityMap) throws DataAccessException {

		try {

			// 查询是否存在同名的dblink，如果存在下拉列表优先从dblink中读取数据
			int is_exists = hrpHipSelectMapper.existsDblink(entityMap);

			if (is_exists >= 0) {
				entityMap.put("dblink", entityMap.get("ds_code"));
			}
			entityMap.put("user_id", SessionManager.getUserId());
			costIncomeDetailMapper.addCostIncomeDetailByHis(entityMap);

			return "{\"msg\":\"采集成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"采集失败\"}");
		}
	}

	// 导入
	public String impCostIncomeDetail(Map<String, Object> entityMap) {

		try {
			
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
			
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			
			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}
			for (Map<String, List<String>> map : list) {
			
                Map<String, Object> mapVo = new HashMap<String, Object>();
				
                mapVo.put("acc_year", map.get("acc_year").get(1));
				
				mapVo.put("acc_month", map.get("acc_month").get(1));
				
				mapVo.put("appl_dept_code", map.get("appl_dept_code").get(1));
				
				mapVo.put("appl_dept_name", map.get("appl_dept_name").get(1));
				
				mapVo.put("exec_dept_code", map.get("exec_dept_code").get(1));
				
				mapVo.put("exec_dept_name", map.get("exec_dept_name").get(1));
				
				mapVo.put("charge_kind_code", map.get("charge_kind_code").get(1));
				
				mapVo.put("charge_kind_name", map.get("charge_kind_name").get(1));
				
                mapVo.put("charge_item_code", map.get("charge_item_code").get(1));
				
				mapVo.put("charge_item_name", map.get("charge_item_name").get(1));
				
                mapVo.put("busi_data_source_code", map.get("busi_data_source_code").get(1));
				
				mapVo.put("busi_data_source_name", map.get("busi_data_source_name").get(1));
				
				//判断数据来源是否存在
				Map<String, Object> busiSourecMapVo = new HashMap<String, Object>();
				
				busiSourecMapVo.put("busi_data_source_type", 1);//数据来源类型 1.收入数据来源
				busiSourecMapVo.put("busi_data_source_code", mapVo.get("busi_data_source_code"));
				
				CostBusiSourecDict costBusiSourecDict = costBusiSourecDictMapper.queryCostBusiSourecDictByCode(busiSourecMapVo);
				
	            if(null == costBusiSourecDict){
					
					return "{\"error\":\""+ map.get("busi_data_source_code").get(0)+"收入数据来源-"+mapVo.get("busi_data_source_name")+"-"+map.get("busi_data_source_code").get(1)+"编码不存在.\",\"state\":\"false\"}";
				}
	            
				
                mapVo.put("price", map.get("price").get(1));
				
				mapVo.put("num", map.get("num").get(1));
				
                mapVo.put("money", map.get("money").get(1));
				
				mapVo.put("create_user",SessionManager.getUserId());
				
				mapVo.put("create_date", new Date()); 
				
				resultList.add(mapVo);
			}

			   //默认1500条批量提交一次
               int  totalCount =list.size();  
			   
		       int  page_size = 1500;  
		       
		       int  requestCount = (totalCount-1)/page_size+1;
		       
	           if(resultList.size()>0){
				   
			       for (int i = 0; i < requestCount; i++) {
			    	   
			    	   Integer fromIndex = i * page_size;
			    	   
			    	   int toIndex = Math.min(totalCount, (i + 1) * page_size);
			   
			    	   costIncomeDetailMapper.addBatchCostIncomeDetail(resultList.subList(fromIndex, toIndex));
			    	   
				     }
			   }
	           
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}

	}

}
