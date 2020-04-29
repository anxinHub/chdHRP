package com.chd.hrp.cost.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostExecDeptMapper;
import com.chd.hrp.cost.entity.CostIncomeDetail;
import com.chd.hrp.cost.service.CostExecDeptService;
import com.github.pagehelper.PageInfo;

@Service("costExecDeptService")
public class CostExecDeptServiceImpl implements CostExecDeptService {

	private static Logger logger = Logger.getLogger(CostExecDeptServiceImpl.class);
	
	@Resource(name = "costExecDeptMapper")
	private final CostExecDeptMapper costExecDeptMapper = null;
	
   /**
	* 
	* @Title: queryCostExecSum
	* @Description: 执行收入统计查询-收费类别
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object> 
	* @date 2020年2月14日   
	* @author sjy
	 */
    @Override
	public String queryCostExecDeptMain(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<CostIncomeDetail> list = costExecDeptMapper.queryCostExecDeptMain(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostIncomeDetail> list = costExecDeptMapper.queryCostExecDeptMain(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
    
    /**
   	* 
   	* @Title: queryCostExecSum
   	* @Description: 执行收入统计查询-收费项目
   	* @param @param mapVo
   	* @param @param mode
   	* @param @return
   	* @param @throws Exception
   	* @return Map<String,Object> 
   	* @date 2020年2月14日   
   	* @author sjy
   	 */
    @Override
	public String queryCostExecDeptDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<CostIncomeDetail> list = costExecDeptMapper.queryCostExecDeptDetail(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostIncomeDetail> list = costExecDeptMapper.queryCostExecDeptDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
    
    
    @Override
	public List<Map<String, Object>> queryCostExecDeptPrint(Map<String, Object> entityMap) throws DataAccessException {

    	List<Map<String, Object>> list = null;
    	
    	String para_value = MyConfig.getSysPara("03002");

    	 if("1".equals(para_value)){
    		 list = costExecDeptMapper.queryCostExecDeptMainPrint(entityMap);
        }else if("2".equals(para_value)){
        	 list = costExecDeptMapper.queryCostExecDeptDetailPrint(entityMap);
        }
		
		return list;

	}
}
