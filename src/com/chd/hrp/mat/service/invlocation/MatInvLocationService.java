package com.chd.hrp.mat.service.invlocation;

import java.util.List;
import java.util.Map;

import com.chd.hrp.mat.entity.DzbqShanDeng;
import com.chd.hrp.mat.entity.DzbqView;
import com.chd.task.mat.MatBean;

public interface MatInvLocationService {
	/**
	 * 查询材料对应的货位信息
	 * 
	 * @param mapVo
	 * @return
	 */
	public String queryInvLocation(Map<String, Object> mapVo);

	/**
	 * 查询材料对应的货位信息
	 * 
	 * @param mapVo
	 * @return
	 */
	public List<DzbqView> queryInvLocationList(Map<String, Object> mapVo);


	/**
	 * 查询出库单对应的材料信息
	 * 
	 * @param mapVo
	 * @return
	 */
	public List<DzbqShanDeng> queryInInv(Map<String, Object> mapVo);

	/**
	 * 查询出库单对应的材料信息
	 * 
	 * @param mapVo
	 * @return
	 */
	public List<DzbqShanDeng> queryOutInv(Map<String, Object> mapVo);

	/**
	 * 
	 * @param mapVo
	 * @return
	 */
	public List<DzbqShanDeng> queryInvlocationShanDeng(Map<String, Object> mapVo);

	/**
	 * 传送的json数据
	 * 
	 * @param list
	 *            业务类型 '01'：闪灯，'02':灯塔,'03':数据和闪灯一起传递
	 * @param type
	 */
	public void matInvlocation(List<Map<String, Object>> list, String type);
	
	/**
	 *  出入库发送材料信息到电子标签设备上
	 * 
	 * @param mapVo
	 * @return
	 */
	public List<DzbqView> querySendData(Map<String, Object> mapVo);

}
