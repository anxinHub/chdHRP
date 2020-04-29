package com.chd.hrp.mat.dao.invlocation;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.DzbqShanDeng;
import com.chd.hrp.mat.entity.DzbqView;
import com.chd.task.mat.MatBean;

public interface MatInvLocationMapper extends SqlMapper {
	/**
	 * 查询材料对应的货位信息
	 * 
	 * @param mapVo
	 * @return
	 */
	public List<DzbqView> queryInvLocation(Map<String, Object> mapVo);
	

	/**
	 * 查询HRP库存与电子标签库存是否有变动
	 * 
	 * @param mapVo
	 * @return
	 */
	public List<MatBean> queryInvHrp(Map<String, Object> mapVo);

	/**
	 * 入库闪灯
	 * 
	 * @param mapVo
	 * @return
	 */
	public List<DzbqShanDeng> queryInInv(Map<String, Object> mapVo);

	/**
	 * 出库闪灯
	 * 
	 * @param mapVo
	 * @return
	 */
	public List<DzbqShanDeng> queryOutInv(Map<String, Object> mapVo);

	/**
	 * 出入库 灯塔
	 * 
	 * @param mapVo
	 * @return
	 */
	public List<DzbqShanDeng> queryInvlocationShanDeng(Map<String, Object> mapVo);
	
	/**
	 *  出入库发送材料信息到电子标签设备上
	 * 
	 * @param mapVo
	 * @return
	 */
	public List<DzbqView> querySendData(Map<String, Object> mapVo);
}
