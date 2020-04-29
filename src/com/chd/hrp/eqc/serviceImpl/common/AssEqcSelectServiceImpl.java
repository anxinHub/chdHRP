package com.chd.hrp.eqc.serviceImpl.common;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.hrp.eqc.dao.common.AssEqcSelectMapper;
import com.chd.hrp.eqc.service.common.AssEqcSelectService;


@Service("assEqcSelectService")
public class AssEqcSelectServiceImpl implements AssEqcSelectService {
	
		private static Logger logger = Logger.getLogger(AssEqcSelectServiceImpl.class);
		
		@Resource(name = "assEqcSelectMapper")
		private final AssEqcSelectMapper assEqcSelectMapper = null;
		
		RowBounds rowBoundsALL = new RowBounds(0, 20);
		
	
		@Override
		public String queryDeptType(Map<String, Object> entityMap)	throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(assEqcSelectMapper.queryDeptType(entityMap, rowBounds));
		}
	
		@Override
		public String queryDeptKind(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(assEqcSelectMapper.queryDeptKind(entityMap, rowBounds));
		}
	
		@Override
		public String queryDeptNature(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(assEqcSelectMapper.queryDeptNature(entityMap, rowBounds));
		}
	
		@Override
		public String queryDeptOut(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(assEqcSelectMapper.queryDeptOut(entityMap, rowBounds));
		}
		


		@Override
		public String queryDeptDict(Map<String, Object> entityMap)	throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(assEqcSelectMapper.queryDeptDict(entityMap, rowBounds));
		}


		@Override
		public String queryAssType(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(assEqcSelectMapper.queryAssType(entityMap, rowBounds));
		}
		
		
		/**
		 * 支出项目下拉框 带变更号
		 */
		@Override
		public String queryPaymentItemDict(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			 if(entityMap.get("pageSize")!=null){
				 rowBounds=new RowBounds(0,(Integer) entityMap.get("pageSize"));
				 
			 }else{
				 rowBounds = rowBoundsALL;
			 }
			 return JSON.toJSONString(assEqcSelectMapper.queryPaymentItemDict(entityMap, rowBounds));
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
			
			return JSON.toJSONString(assEqcSelectMapper.queryPaymentItem(entityMap, rowBounds));
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
			
			return JSON.toJSONString(assEqcSelectMapper.queryAssNatures(entityMap, rowBounds));
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
			
			return JSON.toJSONString(assEqcSelectMapper.queryAssDict(entityMap, rowBounds));
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
			
			return JSON.toJSONString(assEqcSelectMapper.queryAssDictInassets(entityMap, rowBounds));
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
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			return  JSON.toJSONString(assEqcSelectMapper.queryEmpDict(entityMap,rowBounds));
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
			
			return JSON.toJSONString(assEqcSelectMapper.queryHosStoreDict(entityMap, rowBounds));
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
			
			return JSON.toJSONString(assEqcSelectMapper.queryHosStoreDictNo(entityMap, rowBounds));
		}

		@Override
		public String queryBusiDataSource(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(assEqcSelectMapper.queryBusiDataSource(entityMap, rowBounds));
		}

		@Override
		public String queryAssCardDict(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(assEqcSelectMapper.queryAssCardDict(entityMap, rowBounds));
		}

		@Override
		public String querySysUser(Map<String, Object> entityMap)throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(assEqcSelectMapper.querySysUser(entityMap, rowBounds));
		}

		@Override
		public String queryCostChargeKind(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			
			return JSON.toJSONString(assEqcSelectMapper.queryCostChargeKind(entityMap, rowBounds));		
		}

		@Override
		public String queryHosUnit(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			return JSON.toJSONString(assEqcSelectMapper.queryHosUnit(entityMap, rowBounds));	
		}

		@Override
		public String queryCostChargeItem(Map<String, Object> entityMap) throws DataAccessException {
			
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			return JSON.toJSONString(assEqcSelectMapper.queryCostChargeItem(entityMap, rowBounds));	
		}

		@Override
		public String queryAssEqcConsumableItem(Map<String, Object> entityMap)	throws DataAccessException {
			
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			return JSON.toJSONString(assEqcSelectMapper.queryAssEqcConsumableItem(entityMap, rowBounds));	
		}

		@Override
		public String queryAssEqcResourceType(Map<String, Object> entityMap)	throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			return JSON.toJSONString(assEqcSelectMapper.queryAssEqcResourceType(entityMap, rowBounds));	
		}

		@Override
		public String queryAssYear(Map<String, Object> entityMap)	throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			return JSON.toJSONString(assEqcSelectMapper.queryAssYear(entityMap, rowBounds));	
		}

		@Override
		public String queryAssGroupOrCardDict(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			if("01".equals(entityMap.get("analysis_attribute"))){
				
				return JSON.toJSONString(assEqcSelectMapper.queryAssEqUnit(entityMap, rowBounds));
			}else{
				return JSON.toJSONString(assEqcSelectMapper.queryAssCardDict(entityMap, rowBounds));
			}
			
		}

		@Override
		public String queryAssAnalysisObject(Map<String, Object> entityMap) 	throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			
			if (entityMap.get("pageSize") != null) {
				
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				
			}else{
				
				rowBounds = rowBoundsALL;
				 
			}
			return JSON.toJSONString(assEqcSelectMapper.queryAssAnalysisObject(entityMap, rowBounds));
		}
		
}
