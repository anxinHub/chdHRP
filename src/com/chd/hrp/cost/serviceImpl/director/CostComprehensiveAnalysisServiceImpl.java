package com.chd.hrp.cost.serviceImpl.director;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.director.CostComprehensiveAnalysisMapper;
import com.chd.hrp.cost.service.director.CostComprehensiveAnalysisService;
import com.github.pagehelper.PageInfo;


@Service("costComprehensiveAnalysisService")
public class CostComprehensiveAnalysisServiceImpl implements CostComprehensiveAnalysisService{

	@Resource(name = "costComprehensiveAnalysisMapper")
	private final CostComprehensiveAnalysisMapper costComprehensiveAnalysisMapper = null;

	@Override
	public String queryCostDepartmentOperation(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
            SysPage sysPage = new SysPage();
		
			sysPage = (SysPage) entityMap.get("sysPage");
	
			if (sysPage.getTotal() == -1) {
	
				List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryCostDepartmentOperation(entityMap);
	
				return ChdJson.toJsonLower(list);
	
			} else {
	
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
	
				List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryCostDepartmentOperation(entityMap, rowBounds);
	
				PageInfo page = new PageInfo(list);
	
				return ChdJson.toJsonLower(list,page.getTotal());
	
			}
	}
    
	
	/**
	 * 
	 * @param entityMap
	 * @return
	 * 打印
	 * @throws DataAccessException
	 */
	@Override
	public List<Map<String, Object>> queryCostDepartmentOperationPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list=costComprehensiveAnalysisMapper.queryCostDepartmentOperationPrint(entityMap);
		
		return list;
		
	}
	

	@Override
	public List<Map<String, Object>> queryCostDirectPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list=costComprehensiveAnalysisMapper.queryCostDirectPrint(entityMap);
		return list;
	}


	@Override
	public String queryCostGeneralMessage(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryCostGeneralMessage(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryCostGeneralMessage(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());

		}
	}

	@Override
	public String queryCostGeneralMessageDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryCostGeneralMessageDetail(entityMap);

				return ChdJson.toJsonLower(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryCostGeneralMessageDetail(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJsonLower(list,page.getTotal());

			}
	}

	@Override
	public String queryCostGeneralMessageMed(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryCostGeneralMessageMed(entityMap);

				return ChdJson.toJsonLower(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryCostGeneralMessageMed(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJsonLower(list,page.getTotal());

			}
	}

	@Override
	public String queryCostGeneralMessageMedDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryCostGeneralMessageMedDetail(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryCostGeneralMessageMedDetail(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());

		}
	}

	@Override
	public String queryCostGeneralMessage_t1(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryCostGeneralMessage_t1(entityMap);

		return ChdJson.toJsonLower(list);
	}

	@Override
	public String queryCostGeneralMessage_t2(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryCostGeneralMessage_t2(entityMap);

		return ChdJson.toJsonLower(list);
	}

	@Override
	public String queryCostGeneralMessage_t3(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryCostGeneralMessage_t3(entityMap);

		return ChdJson.toJsonLower(list);
	}

	@Override
	public String queryCostGeneralMessageProfit(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryCostGeneralMessageProfit(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryCostGeneralMessageProfit(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());
		}
	}

	@Override
	public String queryCostGeneralMessageProfitClinic(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryCostGeneralMessageProfitClinic(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryCostGeneralMessageProfitClinic(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());
		}
	}
	
	@Override
	public String queryCostGeneralMessageProfitClinicCost(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryCostGeneralMessageProfitClinicCost(entityMap);

				return ChdJson.toJsonLower(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryCostGeneralMessageProfitClinicCost(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJsonLower(list,page.getTotal());
			}
	}
	
	

	@Override
	public String queryCostGeneralMessageProfitInhos(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryCostGeneralMessageProfitInhos(entityMap);

				return ChdJson.toJsonLower(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryCostGeneralMessageProfitInhos(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJsonLower(list,page.getTotal());
			}
	}

	@Override
	public String queryCostGeneralMessageProfitInhosCost(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryCostGeneralMessageProfitInhosCost(entityMap);

				return ChdJson.toJsonLower(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryCostGeneralMessageProfitInhosCost(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJsonLower(list,page.getTotal());
			}
	}

	@Override
	public String queryCostGeneralMessageProfitMedical(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryCostGeneralMessageProfitMedical(entityMap);

				return ChdJson.toJsonLower(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryCostGeneralMessageProfitMedical(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJsonLower(list,page.getTotal());
			}
	}

	@Override
	public String queryCostDirect(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryCostDirect(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryCostDirect(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());
		}
	}

	@Override
	public String queryCostDirectIncomeKind(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryCostDirectIncomeKind(entityMap);

				return ChdJson.toJsonLower(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryCostDirectIncomeKind(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJsonLower(list,page.getTotal());
			}
	}

	@Override
	public String queryMultiIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryMultiIncome(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryMultiIncome(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());
	}
}

	@Override
	public String queryMultiIncomeByDeptDir(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryMultiIncomeByDeptDir(entityMap);

				return ChdJson.toJsonLower(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryMultiIncomeByDeptDir(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJsonLower(list,page.getTotal());

			}	
	}

	@Override
	public String queryMultiIncomeTotalCost(Map<String, Object> entityMap)
			throws DataAccessException {
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryMultiIncomeTotalCost(entityMap);

				return ChdJson.toJsonLower(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryMultiIncomeTotalCost(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJsonLower(list,page.getTotal());

			}
	}

	@Override
	public String queryMultiIncomeChangeCostDir(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryMultiIncomeChangeCostDir(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryMultiIncomeChangeCostDir(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());

		}
	}

	@Override
	public String queryMultiIncomeDirectCostDir(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryMultiIncomeDirectCostDir(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryMultiIncomeDirectCostDir(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());

		}
	}

	@Override
	public String queryMultiIncomeByChargeType(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costComprehensiveAnalysisMapper.queryMultiIncomeByChargeType(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costComprehensiveAnalysisMapper.queryMultiIncomeByChargeType(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());

		}
	}
}