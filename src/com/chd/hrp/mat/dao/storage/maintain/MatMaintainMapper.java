package com.chd.hrp.mat.dao.storage.maintain;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface MatMaintainMapper extends SqlMapper {
	/**
	 * 查询仓库现有材料及库存用于养护
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryStoreExistInvForMaintain(Map<String, Object> mapVo);
	/**
	 * 获取养护记录主表 序列
	 * @return
	 */
	public Long queryMatMaintainMainSeq();
	/**
	 * 获取养护记录明细表 序列
	 * @return
	 */
	public Long queryMatMaintainDetailSeq();
	/**
	 * 批量添加养护记录明细
	 * @param maintain_detail_list
	 */
	public void addMatMaintainDetailBatch(List<?> maintain_detail_list);
	/**
	 * 根据仓库id,和类别查询对应库存,从而批量添加养护记录明细
	 * @param entityMap
	 */
	public void addMatMaintainMainAndDetailByStoreAndMatType(Map<String, Object> entityMap);
	
	/**
	 * 添加养护记录主表数据
	 * @param mapMain
	 */
	public void addMatMaintainMain(Map<String, Object> mapMain);
	/**
	 * 通过主键查询养护记录主表的一条记录
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryMatMaintainMainByID(Map<String, Object> mapVo);
	/**
	 * 通过养护主表id获取对应明细表信息
	 * @param mapVo
	 * @return
	 */
	public List<?> queryMatMaintainDetailByMtID(Map<String, Object> mapVo);
	/**
	 * 批量删除养护记录表明细
	 * @param detailList
	 */
	public void deleteMatMaintainDetailBatch(List<Map<String, Object>> detailList);
	/**
	 * 修改养护记录主表信息
	 * @param mainMap
	 */
	public void updateMatMaintainMain(Map<String, Object> mainMap);
	/**
	 * 查询养护记录主表信息
	 * @param mapVo
	 * @return
	 */
	public List<?> queryMatMaintainMain(Map<String, Object> mapVo);
	/**
	 * 删除养护记录主表
	 * @param entityList
	 */
	public void deleteMatMaintainMain(List<Map<String, Object>> entityList);
	/**
	 * 删除养护记录明细表
	 * @param entityList
	 */
	public void deleteMatMaintainDetail(List<Map<String, Object>> entityList);
	

}
