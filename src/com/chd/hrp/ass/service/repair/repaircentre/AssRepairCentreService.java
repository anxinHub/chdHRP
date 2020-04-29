package com.chd.hrp.ass.service.repair.repaircentre;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;

public interface AssRepairCentreService extends SqlService{
	/**
	 * 主页面数据
	 * @param mapVo
	 * @return
	 */
	String queryAssRepairCenter(Map<String, Object> mapVo);
	
	/**
	 * 时间轴数据
	 * @param mapVo
	 * @return
	 */
	String queryTimeLineRender(Map<String, Object> mapVo);

	/**
	 * 根据卡片号取卡片数据
	 * @param mapVo
	 * @return
	 */
	Map<String, Object> queryCardDataByCode(Map<String, Object> mapVo);
	/**
	 * 单据查看页图片数据
	 * @param mapVo
	 * @return
	 */
	String queryImgUrlByRepCode(Map<String, Object> mapVo);
	/**
	 * 单据查看页数据
	 * @param mapVo
	 * @return
	 */
	Map<String, Object> queryAssRepairByCode(Map<String, Object> mapVo);
	/**
	 * 转单页工程师数据
	 * @param mapVo
	 * @return
	 */
	String queryRepTeamUser(Map<String, Object> mapVo);
	/**
	 * 接单
	 * @param mapVo
	 * @return
	 */
	String repairReceiving(Map<String, Object> mapVo);
	/**
	 * 转单
	 * @param mapVo
	 * @return
	 */
	String updateRepUser(Map<String, Object> mapVo);
	/**
	 * 误报
	 * @param mapVo
	 * @return
	 */
	String updateEndRepUser(Map<String, Object> mapVo);
	/**
	 * 完成维修页面材料数据
	 * @param mapVo
	 * @return
	 */
	String queryMatInvDict(Map<String, Object> mapVo);
	/**
	 * 完成维修
	 * @param mapVo
	 * @return
	 */
	String updateEndRepairState(Map<String, Object> mapVo);
	/**
	 * 查询彩色块数据
	 * @param mapVo
	 * @return
	 */
	Map<String, Object> queryCountState(Map<String, Object> mapVo);

	String queryAssRepInv(Map<String, Object> mapVo);

	//List<Map<String, Object>> queryAssRepInvCode(Map<String, Object> mapVo);
}
