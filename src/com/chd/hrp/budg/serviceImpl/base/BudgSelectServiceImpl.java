package com.chd.hrp.budg.serviceImpl.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.hrp.budg.dao.base.BudgSelectMapper;
import com.chd.hrp.budg.service.base.BudgSelectService;


@Service("budgSelectService")
public class BudgSelectServiceImpl implements BudgSelectService {
	
	private static Logger logger = Logger.getLogger(BudgSelectServiceImpl.class);
	
	@Resource(name = "budgSelectMapper")
	private final BudgSelectMapper budgSelectMapper = null;
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	/**
	 * 医保类型性质下拉框
	 */
	@Override
	public String queryBudgYBTypeNature(Map<String, Object> entityMap) throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgYBTypeNature(entityMap, rowBounds));
	}
	
	
	/**
	 * 病种名称下拉框
	 */
	@Override
	public String queryBudgSingleDisease(Map<String, Object> entityMap) throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgSingleDisease(entityMap, rowBounds));
	}
	/**
	 * 病种名称下拉框 (根据医保类型编码 查询对应的 单病种)
	 */
	@Override
	public String queryBudgSingleDiseaseByInsCode(Map<String, Object> entityMap) throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgSingleDiseaseByInsCode(entityMap, rowBounds));
	}
	/**
	 * 指标性质下拉框
	 */
	@Override
	public String queryBudgIndexNature(Map<String, Object> entityMap) throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgIndexNature(entityMap, rowBounds));
	}
	
	
	/**
	 * 医保类型下拉框
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryBudgYBType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgYBType(entityMap, rowBounds));
	}
	
	/**
	 * 根据付费机制 查询 医保类型下拉框（查 医保付费机制维护表 BUDG_YB_PAY_MODE_SET）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryBudgYBTypeByMode(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgYBTypeByMode(entityMap, rowBounds));
	}

	@Override
	public String queryBudgDeptType(Map<String, Object> entityMap)	throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgDeptType(entityMap, rowBounds));
	}

	@Override
	public String queryBudgDeptKind(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgDeptKind(entityMap, rowBounds));
	}

	@Override
	public String queryBudgDeptNature(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgDeptNature(entityMap, rowBounds));
	}

	@Override
	public String queryBudgDeptOut(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgDeptOut(entityMap, rowBounds));
	}
	
	@Override
	public String queryBudgCharStanNature(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgCharStanNature(entityMap, rowBounds));
	}
	
	/**
	 * 费用标准下拉框
	 */
	@Override
	public String queryBudgChargeStan(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgChargeStan(entityMap, rowBounds));
	}
	
	/**
	 * 费用标准下拉框  费用标准取值方法维护 添加、修改页面专用
	 */
	@Override
	public String queryBudgChargeStanGetWay(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgChargeStanGetWay(entityMap, rowBounds));
	}
	
	@Override
	public String queryBudgChargeStanDept(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgChargeStanDept(entityMap, rowBounds));
	}

	@Override
	public String queryBudgGetValueWay(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgGetValueWay(entityMap, rowBounds));
	}

	@Override
	public String queryBudgFormula(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgFormula(entityMap, rowBounds));
	}

	@Override
	public String queryBudgFun(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgFun(entityMap, rowBounds));
	}
	
	@Override
	public String queryBudgFunParaMethod(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgFunParaMethod(entityMap, rowBounds));
	}
	
	@Override
	public String queryBudgComType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgComType(entityMap, rowBounds));
	}
	
	@Override
	public String queryBudgOraclePkg (Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgOraclePkg(entityMap, rowBounds));
	}
	
	@Override
	public String queryBudgSubjLevel(Map<String, Object> entityMap)	throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgSubjLevel(entityMap, rowBounds));
	}
	
	@Override
	public String queryBudgCostSubjLevel(Map<String, Object> entityMap)	throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgCostSubjLevel(entityMap, rowBounds));
	}
	
	@Override
	public String queryBudgAccSubjNature(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgAccSubjNature(entityMap, rowBounds));
	}

	@Override
	public String queryBudgSubj(Map<String, Object> entityMap)throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 50);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}
		
		return JSON.toJSONString(budgSelectMapper.queryBudgSubj(entityMap, rowBounds));
	}

	@Override
	public String queryAccSubj(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryAccSubj(entityMap, rowBounds));
	}
	
	/**
	 * 会计科目下拉框 支出项目字典页面 专用 勿动
	 */
	@Override
	public String queryAccSubjDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgSelectMapper.queryAccSubjDict(entityMap, rowBounds));
	}
	
	//收入预算科目下拉框
		@Override
		public String queryBudgIncomeSubj(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgIncomeSubj(entityMap, rowBounds));
		}

		@Override
		public String queryBudgYear(Map<String, Object> entityMap)	throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgYear(entityMap, rowBounds));
		}
		/**
		 * 预算年份 下拉框（上下十年）
		 * @param entityMap
		 * @return
		 * @throws Exception
		 */
		@Override
		public String queryBudgYearTen(Map<String, Object> entityMap)	throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgYearTen(entityMap, rowBounds));
		}
		
		/**
		 * 预算年份 下拉框（启始年上五年）
		 * @param entityMap
		 * @return
		 * @throws Exception
		 */
		@Override
		public String queryBudgYearFive(Map<String, Object> entityMap)	throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			}else{
				
				rowBounds = rowBoundsALL;
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgYearFive(entityMap, rowBounds));
		}
		/**
		 * 预算月份  下拉框
		 * @param entityMap
		 * @return
		 * @throws Exception
		 */
		@Override
		public String queryBudgMonth(Map<String, Object> entityMap)	throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
				
			}else{
				
				rowBounds = rowBoundsALL;
				
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgMonth(entityMap, rowBounds));
		}

		@Override
		public String queryBudgIncomeSubjType(Map<String, Object> entityMap)	throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgIncomeSubjType(entityMap, rowBounds));
		}
		
		@Override
		public String queryBudgCostSubjType(Map<String, Object> entityMap)	throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgCostSubjType(entityMap, rowBounds));
		}

		@Override
		public String queryBudgIncomeTypeSet(Map<String, Object> entityMap)	throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgIncomeTypeSet(entityMap, rowBounds));
		}

		@Override
		public String queryBudgCostTypeSet(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgCostTypeSet(entityMap, rowBounds));
		}

		@Override
		public String queryBudgDeptDict(Map<String, Object> entityMap)	throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgDeptDict(entityMap, rowBounds));
		}

		/**
		 * 基本指标名称下拉框
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryBudgDeptindex_code_name(Map<String, Object> entityMap) throws DataAccessException {
          RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgDeptindex_code_name(entityMap, rowBounds));
		}
		
		/**
		 * 基本指标名称下拉框   基本指标取值方法维护 添加、修改页面专用
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryBudgIndexCodeGetWay(Map<String, Object> entityMap) throws DataAccessException {
          RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgIndexCodeGetWay(entityMap, rowBounds));
		}
		
		/**
		 * 基本指标对应 预算科室下拉框
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryBudgBasicIndexDept(Map<String, Object> entityMap) throws DataAccessException {
          RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgBasicIndexDept(entityMap, rowBounds));
		}
		
		/**
		 * 函数分类下拉框
		 */
		@Override
		public String qureyBudgFunType(Map<String, Object> entityMap) throws DataAccessException {
			 RowBounds rowBounds = new RowBounds(0, 20);
				
				if (entityMap.get("pageSize") != null) {
					
					rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
					
				}else{
					
					rowBounds = rowBoundsALL;
					 
				}
				
				return JSON.toJSONString(budgSelectMapper.qureyBudgFunType(entityMap, rowBounds));
		}
		
		/**
		 * 元素类型下拉框（计算公式页面用）
		 */
		@Override
		public String queryElementType(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryElementType(entityMap, rowBounds));
		}

		@Override
		public String queryBudgDataNature(Map<String, Object> entityMap) throws DataAccessException {
			 RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgDataNature(entityMap, rowBounds));
		}
		
		/**
		 * 工资项目下拉框
		 */
		@Override
		public String queryBudgWageItem(Map<String, Object> entityMap) throws DataAccessException {
			 RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgWageItem(entityMap, rowBounds));
		}
		
		/**
		 * 期间属性 下拉框
		 */
		@Override
		public String queryBudgPeriodNature(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgPeriodNature(entityMap, rowBounds));
		}
		@Override
		public String queryBudgAwardsItem(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgAwardsItem(entityMap, rowBounds));
		}
		
		@Override
		public String queryBudgEditMethod(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgEditMethod(entityMap, rowBounds));
		}
		@Override
		public String queryBudgGetWay(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgGetWay(entityMap, rowBounds));
		}

		/**
		 * 物资分类名称下拉框
		 */
		@Override
		public String queryMatTypes(Map<String, Object> entityMap) throws DataAccessException {
			 RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
			 }
			 return JSON.toJSONString(budgSelectMapper.queryMatTypes(entityMap, rowBounds));
		}

		
		@Override
		public String queryBudgResolveWay(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgResolveWay(entityMap, rowBounds));
		}
		
		/**
		 * 药品类别 下拉框 带变更号
		 */
		@Override
		public String queryBudgDrugType(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgDrugType(entityMap, rowBounds));
		}
		
		/**
		 * 药品类别 下拉框 不带变更号
		 */
		@Override
		public String queryBudgMedType(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgMedType(entityMap, rowBounds));
		}
		
		@Override
		public String queryBudgCostFassetType(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgCostFassetType(entityMap, rowBounds));
		}
		
		/**
		 * 支出项目下拉框 
		 */
		@Override
		public String queryBudgPaymentItem(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgPaymentItem(entityMap, rowBounds));
		}
		
		/**
		 * 支出项目下拉框 带变更号
		 */
		@Override
		public String queryBudgPaymentItemDict(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgPaymentItemDict(entityMap, rowBounds));
		}
		
		/**
		 * 根据 预算层次 budg_level （01医院年度 02医院月份 03科室年度 04科室月份 ） 
		 * 编制方法EDIT_METHOD（01零基预算 02增量预算 03确定预算 04概率预算 ） 从  业务预算编制方案表 查询 指标下拉框
		 */
		@Override
		public String qureyBudgIndexFromPlan(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.qureyBudgIndexFromPlan(entityMap, rowBounds));
		}
		
		/**
		 * 预算指标下拉框（查询表 BUDG_INDEX_DICT）
		 */
		@Override
		public String queryBudgIndexDict(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgIndexDict(entityMap, rowBounds));
		}
		/**
		 * 预算指标(可累加)下拉框（查询表 BUDG_INDEX_DICT）
		 */
		@Override
		public String queryBudgIndexAccumulationDict(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgIndexAccumulationDict(entityMap, rowBounds));
		}
		/**
		 * 分解或汇总（resolve_or_sum） 下拉框
		 */
		@Override
		public String queryBudgResolveOrSum(Map<String, Object> entityMap) 	throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgResolveOrSum(entityMap, rowBounds));
		}
		/**
		 * 预算层次（budg_level） 下拉框
		 */
		@Override
		public String queryBudgLevel(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgLevel(entityMap, rowBounds));
		}
		/**
		 * 审批类型下拉框
		 * @param entityMap
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		public String queryBudgCheckType(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgCheckType(entityMap, rowBounds));
		}
		/**
		 * 审批状态下拉框
		 * @param entityMap
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		public String queryBudgBcState(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgBcState(entityMap, rowBounds));
		}
		/**
		 * 业务预算状态（state） 下拉框
		 * @param entityMap
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		public String queryBudgState(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgState(entityMap, rowBounds));
		}
		
		/**	
		 * 根据   编制方法EDIT_METHOD（01零基预算 02增量预算 03确定预算 04概率预算 ） 从  医院年度收入预算编制方案表 查询 科目下拉框
		 */
		@Override
		public String qureySubjIndexFromPlan(Map<String, Object> entityMap)	throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.qureySubjIndexFromPlan(entityMap, rowBounds));
		}
		/**
		 * 根据指标查询科室下拉框
		 */
		@Override
		public String queryBudgIndexDeptSet(Map<String, Object> entityMap) throws DataAccessException {
			
			RowBounds rowBounds = new RowBounds(0, 20);
			
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgIndexDeptSet(entityMap, rowBounds));
		}

		/**	
		 * 根据
		 *   分解或汇总方法(RESOLVE_OR_SUM)从  医院年度收入预算编制方案表 查询 科目下拉框
		 */
		@Override
		public String qureyResolveSubjIndexFromPlan(Map<String, Object> entityMap)
				throws DataAccessException {
			 RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.qureyResolveSubjIndexFromPlan(entityMap, rowBounds));
		}
		
		/**	
		 * 根据  编制方法为分解方法 
		 *		从  医院年度收入预算编制方案表 查询 分解 科目下拉框
		 */
		@Override
		public String qureySubjCodeFromPlan(Map<String, Object> entityMap)
				throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.qureySubjCodeFromPlan(entityMap, rowBounds));

		}
		/**
		 * 项目状态下拉菜单
		 */
		@Override
		public String qureyProjStateSelect(Map<String, Object> entityMap) {
			return JSON.toJSONString(budgSelectMapper.qureyProjStateSelect(entityMap));

		}
		
		/**
		 * 资金计划 状态下拉菜单
		 */
		@Override
		public String qureyCashPlanStateSelect(Map<String, Object> entityMap) {
			return JSON.toJSONString(budgSelectMapper.qureyCashPlanStateSelect(entityMap));
			
		}

		/**
		 * 项目名称（proj_name） 下拉框
		 * @param entityMap
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		public String queryProjName(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryProjName(entityMap, rowBounds));
		}

		/**
		 * 项目负责人（state） 下拉框
		 * @param entityMap
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		public String queryConEmpId(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryConEmpId(entityMap, rowBounds));
		}

		/**
		 * 状态 下拉框（01 新建 02已提交 03 已审核）
		 */
		@Override
		public String queryBudgApplyState(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgApplyState(entityMap, rowBounds));
		}
		
		/**
		 * 预算申报单号 下拉框
		 */
		@Override
		public String queryBudgApplyCode(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgApplyCode(entityMap, rowBounds));
		}
		
		/**
		 * 申报类型 下拉框
		 */
		@Override
		public String queryBudgApplyType(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgApplyType(entityMap, rowBounds));
		}
		
		/**
		 * 资金来源 下拉框
		 */
		@Override
		public String queryBudgSource(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgSource(entityMap, rowBounds));
		}
		/**
		 * 项目立项所用下拉框
		 */
		@Override
		public String qureyProjSetUpStateSelect(Map<String, Object> entityMap) {
			// TODO Auto-generated method stub
			return JSON.toJSONString(budgSelectMapper.qureyProjSetUpStateSelect(entityMap));

		}
		
		/**
		 * 根据项目状态       查询项目下拉框 
		 * @param entityMap
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@Override
		public String queryProjByState(Map<String, Object> entityMap) {
			RowBounds rowBounds = new RowBounds(0, 20);
			if(entityMap.get("pageSize")!=null){
				
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryProjByState(entityMap, rowBounds));
		}
		
		
		/**
		 * 项目类型下拉框
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		@Override
		public String queryBudgProjType(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgProjType(entityMap, rowBounds));
		}
		
		
		/**
		 * 项目级别 下拉框
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		@Override
		public String queryBudgProjLevel(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgProjLevel(entityMap, rowBounds));
		}
		
		/**
		 * 医院 下拉框
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		@Override
		public String queryHosInfoDict(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryHosInfoDict(entityMap, rowBounds));
		}
		
		/**
		 * 资金来源 下拉框 不包括 自筹资金
		 */
		@Override
		public String queryBudgSourceNotExistsZCZJ(Map<String, Object> entityMap) {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(budgSelectMapper.queryBudgSourceNotExistsZCZJ(entityMap, rowBounds));
		}

		/**
		 * 支出项目 下拉框
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		@Override
		public String queryPaymentItem(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryPaymentItem(entityMap, rowBounds));
		}

		/**
		 * 资产性质 下拉框
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		@Override
		public String queryAssNatures(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryAssNatures(entityMap, rowBounds));
		}
		/**
		 * 无形资产性质
		 */
		@Override
		public String queryAssNaturesInassets(Map<String, Object> entityMap)throws DataAccessException  {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryAssNaturesInassets(entityMap, rowBounds));
		}
		
		/**
		 * 资产字典下拉框
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		@Override
		public String queryAssDict(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryAssDict(entityMap, rowBounds));
		}
		
		
		
		/**
		 * 资产字典下拉框
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		@Override
		public String queryAssDictInassets(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryAssDictInassets(entityMap, rowBounds));
		}
		/**
		 * 业务  调整单号 下拉框
		 */
		@Override
		public String queryBudgAdjustCode(Map<String, Object> entityMap) throws DataAccessException {
		
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgAdjustCode(entityMap, rowBounds));
		}
		
		/**
		 * 现金流量项目  下拉框
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		@Override
		public String queryCashItem(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryCashItem(entityMap, rowBounds));
		}
		
		/**
		 * 现金流量类型下拉框
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		@Override
		public String queryCashType(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryCashType(entityMap, rowBounds));
		}
		
		/**
		 * 现金流向  下拉框
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		@Override
		public String queryCashDire(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				
			}
			
			return JSON.toJSONString(budgSelectMapper.queryCashDire(entityMap, rowBounds));
		}
		/**
		 * 科室下拉框
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		@Override
		public String queryBudgHosDept(Map<String, Object> entityMap) {
			RowBounds rowBounds = new RowBounds(0, 20);
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgHosDept(entityMap, rowBounds));
		}
		/**
		 * 部门下拉框
		 */
		@Override
		public String queryDept(Map<String, Object> entityMap) {
			return JSON.toJSONString(budgSelectMapper.queryDept(entityMap));
		}

		/**
		 * 物资分类与预算科目对应关系表中查询物资分类
		 */
		@Override
		public String queryBudgMatTypeSubj(Map<String, Object> entityMap) throws DataAccessException {
			return JSON.toJSONString(budgSelectMapper.queryBudgMatTypeSubj(entityMap));
		}

		/**
		 * 系统字典表
		 */
		@Override
		public String queryBudgSysDict(Map<String, Object> entityMap) throws DataAccessException {
			return JSON.toJSONString(budgSelectMapper.queryBudgSysDict(entityMap));
		}

		/**
		 * 职工
		 * @param entityMap (f_code required=true)
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@Override
		public String queryEmpDict(Map<String, Object> entityMap) {
			// TODO Auto-generated method stub
			return  JSON.toJSONString(budgSelectMapper.queryEmpDict(entityMap));
		}

		/**
		 * 职工类别
		 * @param entityMap (f_code required=true)
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@Override
		public String queryEmpType(Map<String, Object> entityMap) {
			// TODO Auto-generated method stub
			return JSON.toJSONString(budgSelectMapper.queryEmpType(entityMap));
		}

		/**
		 * 全部科室下拉框包括已停用  
		 * @param entityMap
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@Override
		public String queryBudgDeptDictAll(Map<String, Object> entityMap) {
			// TODO Auto-generated method stub
			return JSON.toJSONString(budgSelectMapper.queryBudgDeptDictAll(entityMap));
		}

		/**
		 * 所有奖金项目包括已停用 下拉框
		 * @param entityMap
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@Override
		public String queryBudgAwardsItemAll(Map<String, Object> entityMap) {
			// TODO Auto-generated method stub
			return JSON.toJSONString(budgSelectMapper.queryBudgAwardsItemAll(entityMap));
		}

		/**
		 * 所有职工类别 包括已停用
		 * @param entityMap (f_code required=true)
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@Override
		public String queryEmpTypeAll(Map<String, Object> entityMap) {
			// TODO Auto-generated method stub
			return JSON.toJSONString(budgSelectMapper.queryEmpTypeAll(entityMap));
		}
		
		/**
		 * 职工类别下拉框
		 */
		@Override
		public String queryBudgEmpType(Map<String, Object> entityMap) throws DataAccessException {
			return JSON.toJSONString(budgSelectMapper.queryBudgEmpType(entityMap));
		}
		
		/**
		 * 职工名称下拉框
		 */
		@Override
		public String queryBudgEmpName(Map<String, Object> entityMap) throws DataAccessException {
			
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
				
			}else{
				
				rowBounds = rowBoundsALL;
				
			}
			return JSON.toJSONString(budgSelectMapper.queryBudgEmpName(entityMap, rowBounds));
		}
		
		/**
		 *计划类型下拉框
		 */
		@Override
		public String queryBudgPlanType(Map<String, Object> entityMap) throws DataAccessException {
			return JSON.toJSONString(budgSelectMapper.queryBudgPlanType(entityMap));
		}
		
		/**
		 * 医疗收入  调整单号 下拉框
		 */
		@Override
		public String queryIncomeAdjustCode(Map<String, Object> entityMap) throws DataAccessException {
		
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryIncomeAdjustCode(entityMap, rowBounds));
		}
		
		/**
		 * 科室 下拉框 (其他费用预算使用   dept_no ) 预算科室（IS_BUDG=1）
		 */
		@Override
		public String queryBudgDept(Map<String, Object> entityMap) throws DataAccessException {
			
			return JSON.toJSONString(budgSelectMapper.queryBudgDept(entityMap));
		}
		
		/**
		 * 其他收入  调整单号 下拉框
		 */
		@Override
		public String queryElseIncomeAdjustCode(Map<String, Object> entityMap) throws DataAccessException {
		
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryElseIncomeAdjustCode(entityMap, rowBounds));
		}

		/**
		 * 资产性质 下拉框
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		@Override
		public String queryAssNaturesBuget(Map<String, Object> entityMap)throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryAssNaturesBuget(entityMap, rowBounds));
		}

		
		/**
		 * 仓库  下拉框 不带NO
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		@Override
		public String queryHosStoreDict(Map<String, Object> entityMap)throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryHosStoreDict(entityMap, rowBounds));
		}
		
		/**
		 * 仓库  下拉框 带NO
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		@Override
		public String queryHosStoreDictNo(Map<String, Object> entityMap)throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryHosStoreDictNo(entityMap, rowBounds));
		}
		
		/**
		 * 自自定义分解系数下拉框
		 */
		@Override
		public String queryBudgResolveDataDict(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgResolveDataDict(entityMap, rowBounds));
		}
		/**
		 * 收入预算科目类别
		 */
		@Override
		public String queryBudgTypeCode(Map<String, Object> entityMap)
				throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgTypeCode(entityMap, rowBounds));
		}
		/**
		 * 支出预算科目类别
		 */
		@Override
		public String queryBudgCostTypeCode(Map<String, Object> entityMap)
				throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(budgSelectMapper.queryBudgCostTypeCode(entityMap, rowBounds));
		}


		@Override
		public String queryBudgCPlan(Map<String, Object> entityMap)
				throws DataAccessException {
			List<Map<String, Object>> treeList = budgSelectMapper.queryBudgCPlan(entityMap);
			
			// return ChdJson.toJson(treeList);
			return JSONArray.toJSONString(treeList);
		}


		@Override
		public String queryContLSelect(Map<String, Object> entityMap)
				throws DataAccessException {
			return JSON.toJSONString(budgSelectMapper.queryContLSelect(entityMap));
		}


		@Override
		public String queryContPSelect(Map<String, Object> entityMap)
				throws DataAccessException {
			return JSON.toJSONString(budgSelectMapper.queryContPSelect(entityMap));
		}


		@Override
		public String queryContWSelect(Map<String, Object> entityMap)
				throws DataAccessException {
			return JSON.toJSONString(budgSelectMapper.queryContWSelect(entityMap));
		}


		@Override
		public String queryTabCode(Map<String, Object> entityMap)
				throws DataAccessException {
			return JSON.toJSONString(budgSelectMapper.queryTabCode(entityMap));
			}


		@Override
		public String queryContMSelect(Map<String, Object> entityMap)
				throws DataAccessException {
			return JSON.toJSONString(budgSelectMapper.queryContMSelect(entityMap));

		}


		@Override
		public String queryReLinkSelect(Map<String, Object> entityMap)
				throws DataAccessException {
			return JSON.toJSONString(budgSelectMapper.queryReLinkSelect(entityMap));

		}


		@Override
		public String queryContNoteSelect(Map<String, Object> entityMap)
				throws DataAccessException {
			return JSON.toJSONString(budgSelectMapper.queryContNoteSelect(entityMap));

		}


		@Override
		public String queryUseStateSelect(Map<String, Object> entityMap)
				throws DataAccessException {
			return JSON.toJSONString(budgSelectMapper.queryUseStateSelect(entityMap));

		}
}
