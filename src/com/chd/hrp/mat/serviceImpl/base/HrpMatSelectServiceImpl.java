package com.chd.hrp.mat.serviceImpl.base;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.hrp.mat.dao.base.HrpMatSelectMapper;
import com.chd.hrp.mat.service.base.HrpMatSelectService;

@Service("hrpMatSelectService")
public class HrpMatSelectServiceImpl implements HrpMatSelectService {
	
	private static Logger logger = Logger.getLogger(HrpMatSelectServiceImpl.class);
	
	@Resource(name = "hrpMatSelectMapper")
	private final HrpMatSelectMapper hrpMatSelectMapper = null;
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	@Override
	public String queryMatYearOrNo(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatYearOrNo(entityMap, rowBounds));
	}
	
	@Override
	public String queryMatType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatType(entityMap, rowBounds));
	}
	
	@Override
	public String queryMatTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatTypeDict(entityMap, rowBounds));
	}
	
	@Override
	public String queryMatTypeDictByWrite(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatTypeDictByWrite(entityMap, rowBounds));
	}
	
	/**
	 * 材料改变物资类别查询改类别材料数
	 */
	@Override
	public String queryChangeMatTypeCode(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryChangeMatTypeCode(entityMap, rowBounds));
	}

	@Override
	public String queryMatFimTypeDict(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatFimTypeDict(mapVo, rowBounds));
	}
	@Override
	public String queryMatTypeDictCode(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatTypeDictCode(entityMap, rowBounds));
	}
	
	@Override
	public String queryMatFinaType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatFinaType(entityMap, rowBounds));
	}
	
	@Override
	public String queryMatInv(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatInv(entityMap, rowBounds));
	}
	
	@Override
	public String queryMatInvDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatInvDict(entityMap, rowBounds));
	}
	
	@Override
	public String queryHosSup(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryHosSup(entityMap, rowBounds));
	}
	
	@Override
	public String queryHosSupDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryHosSupDict(entityMap, rowBounds));
	}
	@Override
	public String queryHosSupDictUniversalMethod(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryHosSupDictUniversalMethod(entityMap, rowBounds));
	}
	
	
	@Override
	public String queryHosSupDictDisable(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryHosSupDictDisable(entityMap, rowBounds));
	}
	
	@Override
	public String queryHosFac(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryHosFac(entityMap, rowBounds));
	}
	
	@Override
	public String queryHosFacInv(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryHosFacInv(entityMap, rowBounds));
	}
	
	@Override
	public String queryHosFacDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryHosFacDict(entityMap, rowBounds));
	}
	
	@Override
	public String queryHosUnit(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryHosUnit(entityMap, rowBounds));
	}
	
	@Override
	public String queryMatSysList(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatSysList(entityMap, rowBounds));
	}
	/**
	 * 货位分类
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMatLocationType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatLocationType(entityMap, rowBounds));
	}
	/**
	 * 货位字典
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMatLocationDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatLocationDict(entityMap, rowBounds));
	}
	/**
	 * //当前用户查看有权限的仓库
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMatStore(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatStore(entityMap, rowBounds));
	}
	
	/**
	 * //当前用户查看有权限的仓库
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMatStoredisable(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatStoredisable(entityMap, rowBounds));
	}
	
	/**
	 * //当前用户查看只有读的权限的仓库
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMatStoreByRead(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatStoreByRead(entityMap, rowBounds));
	}
	
	/**
	 * //当前用户查看只有读的权限的仓库
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMatStoreByReaddisable(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatStoreByReaddisable(entityMap, rowBounds));
	}
	
	/**
	 * //当前用户查看只有写的权限的仓库
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMatStoreByWrite(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatStoreByWrite(entityMap, rowBounds));
	}
	
	/**
	 * 查询所有仓库
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMatStoreAll(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatStoreAll(entityMap, rowBounds));
	}
	/**
	 * 查看有权限的仓库列表 and 仓库别名
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMatStoreAlias(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatStoreAlias(entityMap, rowBounds));
	}
	/**
	 * 普通职工/领料人 --职工表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatEmp(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatEmp(entityMap, rowBounds));
	}
	/**
	 * 普通职工/领料人 --职工变更表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatEmpDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatEmpDict(entityMap, rowBounds));
	}
	
	//材料证件分类
	@Override
	public String queryMatInvCertType(Map<String, Object> entityMap) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.qryMatInvCertType(entityMap, rowBounds));
	}
	/**
	 * 职能科室
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMatDeptIsManager(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatDeptIsManager(entityMap, rowBounds));
	}
	//库房管理员
	@Override
	public String queryMatManagerEmp(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatManagerEmp(entityMap, rowBounds));
	}
	//采购人
	@Override
	public String queryMatStockEmp(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatStockEmp(entityMap, rowBounds));
	}
	//采购人 -- 变更表
	@Override
	public String queryMatStockEmpDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatStockEmpDict(entityMap, rowBounds));
	}

	//采购人 一个仓库对应多个采购员 查询   即墨需求   2017/04/06  gaopei
	 @Override
     public String queryMatStockEmpByStore(Map<String, Object> entityMap) throws DataAccessException {
		 RowBounds rowBounds = new RowBounds(0, 20);
		 if (entityMap.get("pageSize") != null) {
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			}else{
				 rowBounds = rowBoundsALL;
			}
			return JSON.toJSONString(hrpMatSelectMapper.queryMatStockEmpByStore(entityMap, rowBounds));
		}
	
	//库房分类
	@Override
	public String queryMatStoreType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatStoreType(entityMap, rowBounds));
	}
	//计划状态
		@Override
		public String queryMatPlanState(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			if (entityMap.get("pageSize") != null) {
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			}else{
				 rowBounds = rowBoundsALL;
			}
			return JSON.toJSONString(hrpMatSelectMapper.queryMatPlanState(entityMap, rowBounds));
		}
		//编制科室
		@Override
		public String queryMatDeptDict(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			if (entityMap.get("pageSize") != null) {
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			}else{
				 rowBounds = rowBoundsALL;
			}
			return JSON.toJSONString(hrpMatSelectMapper.queryMatDeptDict(entityMap, rowBounds));
		}
		
		//通过参数控制 编制科室权限 
		@Override
		public String queryMatDeptDictDate(Map<String, Object> entityMap)
				throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			if (entityMap.get("pageSize") != null) {
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			}else{
				 rowBounds = rowBoundsALL;
			}
			return JSON.toJSONString(hrpMatSelectMapper.queryMatDeptDictDate(entityMap, rowBounds));
		}
		
		//通过参数控制 物资类别权限 
			@Override
			public String queryMatTypeDictDate(Map<String, Object> entityMap)
						throws DataAccessException {
					// TODO Auto-generated method stub
					RowBounds rowBounds = new RowBounds(0, 200);
					if (entityMap.get("pageSize") != null) {
						rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
					}else{
						 rowBounds = rowBounds;
					}
					return JSON.toJSONString(hrpMatSelectMapper.queryMatTypeDictDate(entityMap, rowBounds));
				}
			//通过参数控制仓库权限 
			@Override
			public String queryMatStoreDictDate(Map<String, Object> entityMap)
						throws DataAccessException {
					// TODO Auto-generated method stub
					RowBounds rowBounds = new RowBounds(0, 20);
					if (entityMap.get("pageSize") != null) {
						rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
					}else{
						 rowBounds = rowBoundsALL;
					}
					return JSON.toJSONString(hrpMatSelectMapper.queryMatStoreDictDate(entityMap, rowBounds));
				}
			
			
			//库房处只能选到‘是否采购库房’为是的仓库
			@Override 
			public String queryMatStoreDictPro(Map<String, Object> entityMap)
						throws DataAccessException {
					// TODO Auto-generated method stub
					RowBounds rowBounds = new RowBounds(0, 20);
					if (entityMap.get("pageSize") != null) {
						rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
					}else{
						 rowBounds = rowBoundsALL;
					}
					return JSON.toJSONString(hrpMatSelectMapper.queryMatStoreDictPro(entityMap, rowBounds));
				}
			
			
		
		//盘点科室
		@Override
		public String queryMatPDDeptDict(Map<String, Object> entityMap)
				throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			if (entityMap.get("pageSize") != null) {
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			}else{
				 rowBounds = rowBoundsALL;
			}
			return JSON.toJSONString(hrpMatSelectMapper.queryMatPDDeptDict(entityMap, rowBounds));
		}

		
		//编制科室
		@Override
		public String queryMatAppDept(Map<String, Object> entityMap) throws DataAccessException {
			RowBounds rowBounds = new RowBounds(0, 20);
			if (entityMap.get("pageSize") != null) {
				rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
			}else{
				 rowBounds = rowBoundsALL;
			}
			return JSON.toJSONString(hrpMatSelectMapper.queryMatAppDept(entityMap, rowBounds));
		}
	/**
	 * 物资仓库配套表信息
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatStoreMatch(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatStoreMatch(entityMap, rowBounds));
	}
	/**
	 * 证件编码下拉框
	 * @param mapVo
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatInvCert(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatInvCert(mapVo, rowBounds));
	}
	/**
	 * 获取年份
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatYear(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatYear(entityMap, rowBounds));
	}
	/**
	 * 获取月份
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatMonth(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatMonth(entityMap, rowBounds));
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
		return JSON.toJSONString(hrpMatSelectMapper.queryHosSupType(mapVo, rowBounds));
	}
	/**
	 * 供应商 证件类别
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMatVenCertType(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatVenCertType(mapVo, rowBounds));
	}
	
	/**
	 * 物资科室配套表信息
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatDeptMatch(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatDeptMatch(entityMap, rowBounds));
	}
	
	/**
	 * 当前用户查看有权限的科室
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatDept(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatDept(entityMap, rowBounds));
	}
	/**
	 * 业务类型
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatBusType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatBusType(entityMap, rowBounds));
	}
	
	@Override
	public String queryMatBusTypes(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		} 
		return JSON.toJSONString(hrpMatSelectMapper.queryMatBusTypes(entityMap, rowBounds));
	}
	/**
	 * 物资分类级次
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatTypeLevel(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatTypeLevel(mapVo, rowBounds));
	}
	
	/**
	 * 物资分类级次-带id
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatTypeLevel_2(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatTypeLevel_2(mapVo, rowBounds));
	}
	
	
	/**
	 * 采购协议类别
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatProtocolType(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatProtocolType(entityMap, rowBounds));
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
		return JSON.toJSONString(hrpMatSelectMapper.queryHosPackage(entityMap, rowBounds));
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
		return JSON.toJSONString(hrpMatSelectMapper.queryPurDept(entityMap, rowBounds));
	}
	
	/**
	 * 计划类型
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatPlanType(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatPlanType(entityMap, rowBounds));
	}
	
	/**
	 * 采购计划-采购员
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatPurStockEmp(Map<String, Object> entityMap) throws DataAccessException {


		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatPurStockEmp(entityMap, rowBounds));
	}
	
	/**
	 * 包装单位
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatHosPackage(Map<String, Object> entityMap) throws DataAccessException {


		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatHosPackage(entityMap, rowBounds));
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
		return JSON.toJSONString(hrpMatSelectMapper.querySignedDept(entityMap, rowBounds));
	}
	/**
	 * 采购类型
	 * @param entityMap
	 * @return
	 */
	@Override
	public String queryMatStockType(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatStockType(entityMap, rowBounds));
	}
	/**
	 * 采购协议
	 * @param entityMap
	 * @return
	 */
	@Override
	public String queryMatProtocolMain(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatProtocolMain(entityMap, rowBounds));
	}
	/**
	 * 付款协议
	 * @param entityMap
	 * @return
	 */
	@Override
	public String queryMatPactFkxyMain(Map<String, Object> entityMap) throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatPactFkxyMain(entityMap, rowBounds));
	}
	/**
	 * 付款方式(结算方式)
	 * @param entityMap
	 * @return
	 */
	@Override
	public String queryMatPayType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatPayType(entityMap,rowBounds));
	}
	/**
	 * 采购发票  付款条件下拉框
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatPayTerm(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatPayTerm(entityMap, rowBounds));
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
		return JSON.toJSONString(hrpMatSelectMapper.queryHosDept(entityMap, rowBounds));
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
		return JSON.toJSONString(hrpMatSelectMapper.queryHosDeptDict(entityMap, rowBounds));
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
		return JSON.toJSONString(hrpMatSelectMapper.queryHosDeptByPerm(entityMap, rowBounds));
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
		return JSON.toJSONString(hrpMatSelectMapper.queryHosDeptDictByPerm(entityMap, rowBounds));
	}

	@Override
	public String queryMatHosInfoDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatHosInfoDict(entityMap, rowBounds));
	}

	@Override
	public String queryMatHosInfo(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatHosInfo(entityMap, rowBounds));
	}

	//根据仓库对应关系查询材料
	@Override
	public String queryMatInvDictByStoreInv(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatInvDictByStoreInv(entityMap, rowBounds));
	}

	//项目
	@Override
	public String queryMatProj(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatProj(entityMap, rowBounds));
	}

	//项目(含变更号)
	@Override
	public String queryMatProjDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatProjDict(entityMap, rowBounds));
	}

	//出库物资用途
	@Override
	public String queryMatOutUse(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatOutUse(entityMap, rowBounds));
	}
	//科室需求编制--维护供应商
	@Override
	public String queryMatSupByInvId(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatSupByInvId(entityMap, rowBounds));
	}

	@Override
	public String queryHosDeptLevel(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryHosDeptLevel(entityMap, rowBounds));
	}

	@Override
	public String queryMatVirStore(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatVirStore(entityMap, rowBounds));
	}
	@Override
	public String queryMatVirStoreWithEntireStoreWriteOrRead(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatVirStoreWithEntireStoreWriteOrRead(entityMap, rowBounds));
	}

	@Override
	public String queryMatVirStoreByWrite(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatVirStoreByWrite(entityMap, rowBounds));
	}

	@Override
    public String queryMatStoreStocker(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatStoreStocker(entityMap, rowBounds));
    }
	
	/**
	 * 注册证号
	 */
	@Override
	public String queryMatInvCertId(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatInvCertId(entityMap, rowBounds));
	}
	/**
	 * 注册证号 带起止日期
	 */
	@Override
	public String queryMatInvCertIdWithDate(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatInvCertIdWithDate(entityMap, rowBounds));
	}
	
	/**
	 * 管理类别
	 */
	@Override
	public String queryMatManageType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatManageType(entityMap, rowBounds));
	}
	
	@Override
	public String queryMatTypeDicts(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatTypeDicts(entityMap, rowBounds));
	}

	@Override
	public String queryMatLocation(Map<String, Object> entityMap)throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatLocation(entityMap, rowBounds));
	}

	@Override
	public String queryPermDeptAndStoreDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryPermDeptAndStoreDict(entityMap, rowBounds));
	}

	@Override
	public String queryPermMatTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryPermMatTypeDict(entityMap, rowBounds));
	}

	@Override
	public String querySysUser(Map<String, Object> entityMap){
	RowBounds rowBounds = new RowBounds(0, 20);
	if (entityMap.get("pageSize") != null) {
		rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
	}else{
		 rowBounds = rowBoundsALL;
	}
	return JSON.toJSONString(hrpMatSelectMapper.querySysUser(entityMap, rowBounds));

    }

	@Override
    public String queryMatStoreMatchRead(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatStoreMatchRead(entityMap, rowBounds));
    }
  
	@Override 
    public String queryMatDeptMatchRead(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatDeptMatchRead(entityMap, rowBounds));
    }
	//领用人
	@Override
	public String queryMatEmpDictData(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(mapVo.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatEmpDictData(mapVo, rowBounds));
	}
	//查询年月  
	@Override
	public String queryYearMonth(Map<String, Object> mapVo) {
		return JSON.toJSONString(hrpMatSelectMapper.queryYearMonth(mapVo));
	}

	@Override
	public String queryHosSupDictForPay(Map<String, Object> entityMap) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryHosSupDictForPay(entityMap, rowBounds));
	}
	/**
	 * 查询对应仓库的物资分类信息
	 */
	@Override
	public String queryMatTypeByStoreID(Map<String, Object> mapVo) {
		
		return JSON.toJSONString(hrpMatSelectMapper.queryMatTypeByStoreID(mapVo));
	}

	@Override
	public String queryMatExaminerEmp(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatExaminerEmp(entityMap, rowBounds));
	}
	
	@Override
	public String queryHosSupDictMethod(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}else{
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryHosSupDictMethod(entityMap, rowBounds));
	}

	@Override
	public String queryMatInsuraType(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(mapVo.get("pageSize").toString()));
		}else{
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatInsuraType(mapVo, rowBounds));
	}

	@Override
	public String queryMatInstruType(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(mapVo.get("pageSize").toString()));
		}else{
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpMatSelectMapper.queryMatInstruType(mapVo, rowBounds));
	}

	//证件类型
	@Override
	public String queryMatCertType(Map<String, Object> mapVo)
			throws DataAccessException {
		
		return JSON.toJSONString(hrpMatSelectMapper.queryMatCertType(mapVo));
	}
	//预警类型
	@Override
	public String queryMatWarnType(Map<String, Object> mapVo)
			throws DataAccessException {
		
		return JSON.toJSONString(hrpMatSelectMapper.queryMatWarnType(mapVo));
	}
	
}
