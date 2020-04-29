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
import com.chd.hrp.cost.dao.CostApplDeptMapper;
import com.chd.hrp.cost.entity.CostIncomeDetail;
import com.chd.hrp.cost.service.CostApplDeptService;
import com.github.pagehelper.PageInfo;

@Service("costApplDeptService")
public class CostApplDeptServiceImpl implements CostApplDeptService {

	private static Logger logger = Logger.getLogger(CostApplDeptServiceImpl.class);
	
	/**
	 * @Description 
	 * 开单统计明细<BR> 查询CostApplSum分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Resource(name = "costApplDeptMapper")
	private final CostApplDeptMapper costApplDeptMapper = null;
	
	@Override
	public String queryCostApplDeptMain(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal()==-1){
			List<CostIncomeDetail> list = costApplDeptMapper.queryCostApplDeptMain(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostIncomeDetail> list = costApplDeptMapper.queryCostApplDeptMain(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	@Override
	public String queryCostApplDeptDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal()==-1){
			List<CostIncomeDetail> list = costApplDeptMapper.queryCostApplDeptDetail(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostIncomeDetail> list = costApplDeptMapper.queryCostApplDeptDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	@Override
	public List<Map<String, Object>> queryCostApplDeptPrint(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> list=null;
		
		String para_value = MyConfig.getSysPara("03002");

	   	 if("1".equals(para_value)){
	   		 list = costApplDeptMapper.queryCostApplDeptMainPrint(entityMap);
	       }else if("2".equals(para_value)){
	       	 list = costApplDeptMapper.queryCostApplDeptDetailPrint(entityMap);
	       }
		
		return list;

	}
	
	

}
