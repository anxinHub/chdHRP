package com.chd.hrp.med.serviceImpl.base;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.hrp.med.dao.base.HrpMedSelectMapper;
import com.chd.hrp.med.service.base.HrpMedSelectService;

@Service("hrpMedSelectService")
public class HrpMedSelectServiceImpl implements HrpMedSelectService {
	
	private static Logger logger = Logger.getLogger(HrpMedSelectServiceImpl.class);
	
	@Resource(name = "hrpMedSelectMapper")
	private final HrpMedSelectMapper hrpMedSelectMapper = null;
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	@Override
	public String queryMedYearOrNo(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedYearOrNo(entityMap, rowBounds));
	}
	
	@Override
	public String queryMedType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedType(entityMap, rowBounds));
	}
	
	@Override
	public String queryMedTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedTypeDict(entityMap, rowBounds));
	}
	
	@Override
	public String queryMedTypeDictByWrite(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedTypeDictByWrite(entityMap, rowBounds));
	}
	
	/**
	 * 材料改变药品类别查询改类别材料数
	 */
	@Override
	public String queryChangeMedTypeCode(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryChangeMedTypeCode(entityMap, rowBounds));
	}

	@Override
	public String queryMedFimTypeDict(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedFimTypeDict(mapVo, rowBounds));
	}
	@Override
	public String queryMedTypeDictCode(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedTypeDictCode(entityMap, rowBounds));
	}
	
	@Override
	public String queryMedFinaType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedFinaType(entityMap, rowBounds));
	}
	
	@Override
	public String queryMedInv(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedInv(entityMap, rowBounds));
	}
	
	@Override
	public String queryMedInvDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedInvDict(entityMap, rowBounds));
	}
	
	@Override
	public String queryHosSup(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryHosSup(entityMap, rowBounds));
	}
	
	@Override
	public String queryHosSupDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryHosSupDict(entityMap, rowBounds));
	}
	@Override
	public String queryHosSupDictDisable(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryHosSupDictDisable(entityMap, rowBounds));
	}
	
	@Override
	public String queryHosFac(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryHosFac(entityMap, rowBounds));
	}
	
	@Override
	public String queryHosFacInv(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryHosFacInv(entityMap, rowBounds));
	}
	
	@Override
	public String queryHosFacDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryHosFacDict(entityMap, rowBounds));
	}
	
	@Override
	public String queryHosUnit(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryHosUnit(entityMap, rowBounds));
	}
	
	@Override
	public String queryMedSysList(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedSysList(entityMap, rowBounds));
	}
	/**
	 * 货位分类
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMedLocationType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedLocationType(entityMap, rowBounds));
	}
	/**
	 * 货位字典
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMedLocationDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedLocationDict(entityMap, rowBounds));
	}
	/**
	 * //当前用户查看有权限的仓库
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMedStore(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedStore(entityMap, rowBounds));
	}
	
	/**
	 * //当前用户查看有权限的仓库
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMedStoredisable(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedStoredisable(entityMap, rowBounds));
	}
	
	/**
	 * //当前用户查看只有读的权限的仓库
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMedStoreByRead(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedStoreByRead(entityMap, rowBounds));
	}
	
	/**
	 * //当前用户查看只有读的权限的仓库
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMedStoreByReaddisable(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedStoreByReaddisable(entityMap, rowBounds));
	}
	
	/**
	 * //当前用户查看只有写的权限的仓库
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMedStoreByWrite(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedStoreByWrite(entityMap, rowBounds));
	}
	
	/**
	 * 查询所有仓库
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMedStoreAll(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedStoreAll(entityMap, rowBounds));
	}
	/**
	 * 查看有权限的仓库列表 and 仓库别名
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMedStoreAlias(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedStoreAlias(entityMap, rowBounds));
	}
	/**
	 * 普通职工/领料人 --职工表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedEmp(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedEmp(entityMap, rowBounds));
	}
	/**
	 * 普通职工/领料人 --职工变更表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedEmpDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedEmpDict(entityMap, rowBounds));
	}
	
	//材料证件分类
	@Override
	public String queryMedInvCertType(Map<String, Object> entityMap) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.qryMedInvCertType(entityMap, rowBounds));
	}
	/**
	 * 职能科室
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMedDeptIsManager(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedDeptIsManager(entityMap, rowBounds));
	}
	//库房管理员
	@Override
	public String queryMedManagerEmp(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedManagerEmp(entityMap, rowBounds));
	}
	//采购人
	@Override
	public String queryMedStockEmp(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedStockEmp(entityMap, rowBounds));
	}
	//采购人 -- 变更表
	@Override
	public String queryMedStockEmpDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedStockEmpDict(entityMap, rowBounds));
	}

	//采购人 一个仓库对应多个采购员 查询   即墨需求   2017/04/06  gaopei
	 @Override
     public String queryMedStockEmpByStore(Map<String, Object> entityMap) throws DataAccessException {
		 RowBounds rowBounds = new RowBounds(0, 20);
		 if (entityMap.get("pageSize") != null) {
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			}else{
				 rowBounds = rowBoundsALL;
			}
			return JSON.toJSONString(hrpMedSelectMapper.queryMedStockEmpByStore(entityMap, rowBounds));
		}
	
	//库房分类
	@Override
	public String queryMedStoreType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedStoreType(entityMap, rowBounds));
	}
	//计划状态
		@Override
		public String queryMedPlanState(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			if (entityMap.get("pageSize") != null) {
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			}else{
				 rowBounds = rowBoundsALL;
			}
			return JSON.toJSONString(hrpMedSelectMapper.queryMedPlanState(entityMap, rowBounds));
		}
		//编制科室
		@Override
		public String queryMedDeptDict(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			if (entityMap.get("pageSize") != null) {
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			}else{
				 rowBounds = rowBoundsALL;
			}
			return JSON.toJSONString(hrpMedSelectMapper.queryMedDeptDict(entityMap, rowBounds));
		}
		//通过参数控制 编制科室权限 
		@Override
		public String queryMedDeptDictDate(Map<String, Object> entityMap)
				throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			if (entityMap.get("pageSize") != null) {
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			}else{
				 rowBounds = rowBoundsALL;
			}
			return JSON.toJSONString(hrpMedSelectMapper.queryMedDeptDictDate(entityMap, rowBounds));
		}
		
		//通过参数控制 物资类别权限 
			@Override
			public String queryMedTypeDictDate(Map<String, Object> entityMap)
						throws DataAccessException {
					// TODO Auto-generated method stub
					RowBounds rowBounds = new RowBounds(0, 200);
					if (entityMap.get("pageSize") != null) {
						rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
					}else{
						 rowBounds = rowBounds;
					}
					return JSON.toJSONString(hrpMedSelectMapper.queryMedTypeDictDate(entityMap, rowBounds));
				}
		//通过参数控制仓库权限 
		@Override
		public String queryMedStoreDictDate(Map<String, Object> entityMap)
					throws DataAccessException {
				// TODO Auto-generated method stub
				RowBounds rowBounds = new RowBounds(0, 20);
				if (entityMap.get("pageSize") != null) {
					rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
				}else{
					 rowBounds = rowBoundsALL;
				}
				return JSON.toJSONString(hrpMedSelectMapper.queryMedStoreDictDate(entityMap, rowBounds));
			}
		//盘点科室
		@Override
		public String queryMedPDDeptDict(Map<String, Object> entityMap)
				throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			if (entityMap.get("pageSize") != null) {
				rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			}else{
				 rowBounds = rowBoundsALL;
			}
			return JSON.toJSONString(hrpMedSelectMapper.queryMedPDDeptDict(entityMap, rowBounds));
		}

		
		//编制科室
		@Override
		public String queryMedAppDept(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			if (entityMap.get("pageSize") != null) {
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			}else{
				 rowBounds = rowBoundsALL;
			}
			return JSON.toJSONString(hrpMedSelectMapper.queryMedAppDept(entityMap, rowBounds));
		}
	/**
	 * 药品仓库配套表信息
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedStoreMatch(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedStoreMatch(entityMap, rowBounds));
	}
	/**
	 * 证件编码下拉框
	 * @param mapVo
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedInvCert(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedInvCert(mapVo, rowBounds));
	}
	/**
	 * 获取年份
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedYear(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedYear(entityMap, rowBounds));
	}
	/**
	 * 获取月份
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedMonth(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedMonth(entityMap, rowBounds));
	}
	/**
	 * 供应商 类别
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryHosSupType(Map<String, Object> mapVo) throws DataAccessException{
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryHosSupType(mapVo, rowBounds));
	}
	/**
	 * 供应商 证件类别
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMedVenCertType(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedVenCertType(mapVo, rowBounds));
	}
	
	/**
	 * 药品科室配套表信息
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedDeptMatch(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedDeptMatch(entityMap, rowBounds));
	}
	
	/**
	 * 当前用户查看有权限的科室
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedDept(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedDept(entityMap, rowBounds));
	}
	/**
	 * 业务类型
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedBusType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedBusType(entityMap, rowBounds));
	}
	@Override
	public String queryMedBusTypes(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		} 
		return JSON.toJSONString(hrpMedSelectMapper.queryMedBusTypes(entityMap, rowBounds));
	}
	/**
	 * 药品分类级次
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedTypeLevel(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedTypeLevel(mapVo, rowBounds));
	}
	
	/**
	 * 药品分类级次-带id
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedTypeLevel_2(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedTypeLevel_2(mapVo, rowBounds));
	}
	
	
	/**
	 * 采购协议类别
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedProtocolType(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedProtocolType(entityMap, rowBounds));
	}
	/**
	 *包装单位下拉框
	 * @param entityMap
	 * @return
	 */
	@Override
	public String queryHosPackage(Map<String, Object> entityMap) {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryHosPackage(entityMap, rowBounds));
	}
	
	/**
	 * 查看当前用户有权限查看的采购科室
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryPurDept(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryPurDept(entityMap, rowBounds));
	}
	
	/**
	 * 计划类型
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedPlanType(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedPlanType(entityMap, rowBounds));
	}
	
	/**
	 * 采购计划-采购员
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedPurStockEmp(Map<String, Object> entityMap) throws DataAccessException {


		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedPurStockEmp(entityMap, rowBounds));
	}
	
	/**
	 * 包装单位
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedHosPackage(Map<String, Object> entityMap) throws DataAccessException {


		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedHosPackage(entityMap, rowBounds));
	}
	/**
	 * 签订部门：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
	 * @param entityMap
	 * @return
	 */
	@Override
	public String querySignedDept(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.querySignedDept(entityMap, rowBounds));
	}
	/**
	 * 采购类型
	 * @param entityMap
	 * @return
	 */
	@Override
	public String queryMedStockType(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedStockType(entityMap, rowBounds));
	}
	/**
	 * 采购协议
	 * @param entityMap
	 * @return
	 */
	@Override
	public String queryMedProtocolMain(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedProtocolMain(entityMap, rowBounds));
	}
	/**
	 * 付款方式(结算方式)
	 * @param entityMap
	 * @return
	 */
	@Override
	public String queryMedPayType(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedPayType(entityMap, rowBounds));
	}
	/**
	 * 采购发票  付款条件下拉框
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedPayTerm(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedPayTerm(entityMap, rowBounds));
	}
	/**
	 * 科室
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryHosDept(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryHosDept(entityMap, rowBounds));
	}
	/**
	 * 科室--变更
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryHosDeptDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryHosDeptDict(entityMap, rowBounds));
	}
	/**
	 * 有权限科室
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryHosDeptByPerm(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryHosDeptByPerm(entityMap, rowBounds));
	}
	/**
	 * 有权限科室--变更
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryHosDeptDictByPerm(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryHosDeptDictByPerm(entityMap, rowBounds));
	}

	@Override
	public String queryMedHosInfoDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedHosInfoDict(entityMap, rowBounds));
	}

	@Override
	public String queryMedHosInfo(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedHosInfo(entityMap, rowBounds));
	}

	//根据仓库对应关系查询材料
	@Override
	public String queryMedInvDictByStoreInv(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedInvDictByStoreInv(entityMap, rowBounds));
	}

	//项目
	@Override
	public String queryMedProj(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedProj(entityMap, rowBounds));
	}

	//项目(含变更号)
	@Override
	public String queryMedProjDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedProjDict(entityMap, rowBounds));
	}

	//出库药品用途
	@Override
	public String queryMedOutUse(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedOutUse(entityMap, rowBounds));
	}
	//科室需求编制--维护供应商
	@Override
	public String queryMedSupByInvId(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedSupByInvId(entityMap, rowBounds));
	}

	@Override
	public String queryHosDeptLevel(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryHosDeptLevel(entityMap, rowBounds));
	}

	@Override
	public String queryMedVirStore(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedVirStore(entityMap, rowBounds));
	}

	@Override
	public String queryMedVirStoreByWrite(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedVirStoreByWrite(entityMap, rowBounds));
	}

	@Override
    public String queryMedStoreStocker(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedStoreStocker(entityMap, rowBounds));
    }
	
	/**
	 * 注册证号
	 */
	@Override
	public String queryMedInvCertId(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedInvCertId(entityMap, rowBounds));
	}
	/**
	 * 注册证号 带起止日期
	 */
	@Override
	public String queryMedInvCertIdWithDate(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedInvCertIdWithDate(entityMap, rowBounds));
	}
	
	/**
	 * 管理类别
	 */
	@Override
	public String queryMedManageType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedManageType(entityMap, rowBounds));
	}
	
	@Override
	public String queryMedTypeDicts(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedTypeDicts(entityMap, rowBounds));
	}

	@Override
	public String queryMedLocation(Map<String, Object> entityMap)throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedLocation(entityMap, rowBounds));
	}

	@Override
	public String queryPermDeptAndStoreDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryPermDeptAndStoreDict(entityMap, rowBounds));
	}

	@Override
	public String queryPermMedTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryPermMedTypeDict(entityMap, rowBounds));
	}

	@Override
	public String querySysUser(Map<String, Object> entityMap){
	RowBounds rowBounds = new RowBounds(0, 20);
	if (entityMap.get("pageSize") != null) {
		rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
	}else{
		 rowBounds = rowBoundsALL;
	}
	return JSON.toJSONString(hrpMedSelectMapper.querySysUser(entityMap, rowBounds));

    }
	
	@Override
	public String queryMedSx(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedSx(entityMap, rowBounds));
	}
	
	@Override
	public String queryMedJx(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedJx(entityMap, rowBounds));
	}
	
	@Override
    public String queryMedStoreMatchRead(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedStoreMatchRead(entityMap, rowBounds));
    }
  
	@Override 
    public String queryMedDeptMatchRead(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedDeptMatchRead(entityMap, rowBounds));
    }
	//领用人
	@Override
	public String queryMedEmpDictData(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(mapVo.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMedSelectMapper.queryMedEmpDictData(mapVo, rowBounds));
	}

	
	
	

}
