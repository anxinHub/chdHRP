package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.nutz.dao.DaoException;

import com.chd.base.SqlMapper;

public interface EcsMapper extends SqlMapper{

	//查询供应商产品
	public List<Map<String, Object>> queryMatSupProdSpec(Map<String, Object> mapVo) throws DaoException;
	public List<Map<String, Object>> queryMatSupProdSpec(Map<String, Object> mapVo, RowBounds rowBounds) throws DaoException;
	
	//更新状态
	public int updateSupProdSpecStateById(Map<String, Object> mapVo) throws DaoException;

	//添加对应关系
	public int addMatSupProdSpecInv(Map<String, Object> mapVo) throws DaoException;

	//精准查询供应商产品
	public Map<String, Object> querySupProdSpecStateById(Map<String, Object> mapVo);

	//查询是否存在对应关系
	public String queryInvIdBySpecId(Map<String, Object> mapVo);

	//查询供应商材料
	public List<Map<String, Object>> queryMatInvList(Map<String, Object> mapVo) throws DaoException;
	public List<Map<String, Object>> queryMatInvList(Map<String, Object> mapVo, RowBounds rowBounds) throws DaoException;
	
	//查询送货单
	public List<Map<String, Object>> queryMatDeliveryList(Map<String, Object> mapVo) throws DaoException;
	public List<Map<String, Object>> queryMatDeliveryList(Map<String, Object> mapVo, RowBounds rowBounds) throws DaoException;
	
	//根据证件名称和供应商ID查询证件
	public String queryMatCertIdByEcs(Map<String, Object> mapVo) throws DaoException;
	//新增证件
	public Long queryMatInvCertSeq() throws DaoException;
	public Long queryMatInvCertTypeID(Map<String, Object> mapVo) throws DaoException;
	public int addMatInvCertByEcs(Map<String, Object> mapVo) throws DaoException;
	//新增证件材料对应关系
	public int addMatInvCertRelaByEcs(Map<String, Object> mapVo) throws DaoException;
	
	//精准查询送货单主表
	public Map<String, Object> queryMatDeliveryMainByCode(Map<String, Object> mapVo) throws DaoException;
	
	//精准查询送货单明细
	public List<Map<String, Object>> queryMatDeliveryDetailByCode(Map<String, Object> mapVo) throws DaoException;

	//添加送货单入库单对应关系
	public int addDlvInRela(List<Map<String, Object>> listVo) throws DaoException;
	
	//验收/作废送货单状态
	public int updateMatDeliveryState(Map<String, Object> mapVo) throws DaoException;

	//删除送货单入库单对应关系
	public int deleteDlvInRela(List<Map<String, Object>> listVo) throws DaoException;
}
