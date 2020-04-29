package com.chd.hrp.ass.dao.repair.repaircentre;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssRepairCentreMapper extends SqlMapper{
	/**
	 * 主页面数据
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryAssRepairCenter(Map<String, Object> mapVo);
	/**
	 * 时间轴数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryTimeLineRender(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 卡片明细数据
	 * @param mapVo
	 * @return
	 */
	Map<String, Object> queryCardDataByCode(Map<String, Object> mapVo);
	/**
	 * 单据页图片数据
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryImgUrlByRepCode(Map<String, Object> mapVo);
	/**
	 * 单据明细数据
	 * @param mapVo
	 * @return
	 */
	Map<String,Object> queryAssRepairByCode(Map<String, Object> mapVo);
	/**
	 * 转单页维修工程师数据
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryRepTeamUser(Map<String, Object> mapVo);
	/**
	 * 判断维修任务状态
	 * 取该单据最新的状态
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> existsAssRepairTaskState(Map<String, Object> mapVo);
	/**
	 * 更新单据表中单据的状态
	 * @param mapVo
	 * @return
	 */
	int updateAssRepairState(Map<String, Object> mapVo);
	/**
	 * 接单、转单、误报、完成维修、都将单据数据添加至维修任务表
	 * @param tsdkMap
	 * @return
	 */
	int addAssRepTask(Map<String, Object> tsdkMap);
	/**
	 * 转单时 对操作用户添加一条状态为已转单的数据
	 * 
	 * @param mapVo
	 * @return
	 */
	int addAssRepTaskByMyUserId(Map<String, Object> mapVo);
	//更新单据发送状态
	public void updateAssRepairSend(Map<String, Object> map);
	/**
	 * 误报时单据结束状态
	 * @param mapVo
	 * @return
	 */
	int updateAssRepairEndState(Map<String, Object> mapVo);
	/**
	 * 完成维修页面维修材料数据
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryMatInvDict(Map<String, Object> mapVo);
	
	List<Map<String, Object>> queryMatInvDict(Map<String, Object> mapVo, RowBounds rowBounds);
	/**
	 * 维修完成更新单据数据
	 * @param mapVo
	 * @return
	 */
	int updateEndRepairState(Map<String, Object> mapVo);
	/**
	 * 维修完成后添加维修材料表数额
	 * @param invMap
	 * @return
	 */
	int addRepairInv(Map<String, Object> invMap);
	
	int queryCountState(Map<String, Object> mapVo);
	/**
	 * 转单时添加任务数据
	 * @param mapVo
	 * @return
	 */
	int addAssRepTaskToTuskUser(Map<String, Object> mapVo);
	List<Map<String, Object>> queryAssRepInv(Map<String, Object> mapVo);
	
	
	
	
	List<Map<String, Object>> queryAssRepairCenterPrint(Map<String, Object> mapVo);
	//List<Map<String, Object>> queryAssRepInvCode(Map<String, Object> mapVo);
}
