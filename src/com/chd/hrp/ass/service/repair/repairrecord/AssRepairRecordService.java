package com.chd.hrp.ass.service.repair.repairrecord;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AssRepairRecordService {
   /**
    * 主要查询
    * @param mapVo
    * @return
    */
	String queryAssRepairRecord(Map<String, Object> mapVo);
    /**
     * 修改跳转
     * @param mapVo
     * @return
     */
	Map<String, Object> queryAssRecordByCode(Map<String, Object> mapVo);
    /**
     * 删除
     * @param repCode
     * @return
     */
	String deleteAssRepairRecord(List<String> repCode);
    /**
     * 添加
     * @param mapVo
     * @param request
     * @param response
     * @return
     */
	String addAssRepairRecord(Map<String, Object> mapVo,HttpServletRequest request, HttpServletResponse response);
    /**
     * 修改
     * @param mapVo
     * @param request
     * @param response
     * @return
     */
	String updateAssRepairRecord(Map<String, Object> mapVo,HttpServletRequest request, HttpServletResponse response);
    /**
     * 材料下拉框
     * @param page
     * @return
     */
	String queryMatInvDictSelect(Map<String, Object> page);
   /**
    * 材料明细
    * @param mapVo
    * @return
    */
	String queryMatInvDictSelectInfo(Map<String, Object> mapVo);
    /**
     * 文件
     * @param mapVo
     * @return
     */
	String queryImgUrlByRecordCode(Map<String, Object> mapVo);
    /**
     * 修改页面材料回显
     * @param mapVo
     * @return
     */
	String queryInvUpdate(Map<String, Object> mapVo);
	/**
	 * 时间轴
	 * @param mapVo
	 * @return
	 */
	String queryTimeLineRecordRender(Map<String, Object> mapVo);
	/**
	 * 查询卡片
	 * @param mapVo
	 * @return
	 */
	Map<String, Object> queryRecordCardDataByCode(Map<String, Object> mapVo);

}
