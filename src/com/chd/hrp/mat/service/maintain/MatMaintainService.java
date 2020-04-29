package com.chd.hrp.mat.service.maintain;

import java.util.List;
import java.util.Map;

public interface MatMaintainService {
	/**
	 * 查询仓库现有材料用于养护
	 * @param mapVo
	 * @return
	 */
	public String queryStoreExistInvForMaintain(Map<String, Object> mapVo);
	/**
	 * 添加养护记录
	 * @param mapVo
	 * @return
	 */
	public String addMatMaintainMainAndDetail(Map<String, Object> mapVo);
	/**
	 * 通过养护记录id查询养护记录主表信息
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryMatMaintainMainByID(Map<String, Object> mapVo);
	/**
	 * 通过养护记录主表主键查询对应明细信息用于修改页面的显示
	 * @param mapVo
	 * @return
	 */
	public String queryMatMaintainDetailByMtID(Map<String, Object> mapVo);
	/**
	 * 修改明细表的内容
	 * @param mapVo
	 * @return
	 */
	public String updateMatMaintainMainAndDetail(Map<String, Object> mapVo);
	/**
	 * 查询养护记录主表记录
	 * @param mapVo
	 * @return
	 */
	public String queryMatMaintainMain(Map<String, Object> mapVo);
	/**
	 * 删除养护记录主表和对应的明细表
	 * @param listVo
	 * @return
	 */
	public String deleteMatMaintainMainAndDetail(List<Map<String, Object>> listVo);
	/**
	 * 通过store_id,mat_type_id搜索仓库库存生成养护记录明细
	 * @param mapVo
	 * @return
	 */
	String addMatMaintainMainAndDetailByStoreAndMatType(Map<String, Object> mapVo);
	/**
	 * 打印
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryMatMaintainDetailByMtIDForPrint(Map<String, Object> mapVo);

}
