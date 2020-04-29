package com.chd.hrp.hr.dao.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrMeetDiagApplication;

public interface HrMeetDiagApplicationMapper extends SqlMapper{
    /**
     * 删除全院大会诊
     * @param entityList
     * @param mapVo 
     */
	void deleteMeetDiagApplication(@Param(value="list") List<HrMeetDiagApplication> entityList,@Param(value="map") Map<String, Object> mapVo);
    /**
     * 提交全院大会诊
     * @param hrNursingPromotion
     */
	void confirmHrMeetDiag(HrMeetDiagApplication hrNursingPromotion);
    /**
     * 撤回全院大会诊
     * @param hrNursingPromotion
     */
	void reConfirmHrHrMeetDiag(HrMeetDiagApplication hrNursingPromotion);
    /**
     * 查询
     * @param hrNursingPromotion
     * @return
     */
	HrMeetDiagApplication queryByCode(HrMeetDiagApplication hrNursingPromotion);
   /**
    * 添加页查询
    * @param entityMap
    * @return
    */
	HrMeetDiagApplication queryByCodeAdd(Map<String, Object> entityMap);
   /**
    * 审核
    * @param hrNursingPromotion
    */
	void auditHrMeetDiag(HrMeetDiagApplication hrNursingPromotion);
   /**
    * 销审
    * @param hrNursingPromotion
    */
	void unauditHrHrMeetDiag(HrMeetDiagApplication hrNursingPromotion);
    /**
     * 未通过
     * @param hrNursingPromotion
     */
	void dispassHrHrMeetDiag(HrMeetDiagApplication hrNursingPromotion);
	/**
	 * 查询历史记录
	 * @param entityMap
	 * @return
	 */
	List<HrMeetDiagApplication> queryHistroy(Map<String, Object> entityMap);
	/**
	 * 查询历史记录
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	List<HrMeetDiagApplication> queryHistroy(Map<String, Object> entityMap,
			RowBounds rowBounds);
	  /**
     * 打印
     * @param entityMap
     * @return
     */
	List<Map<String, Object>> queryMeetDiagAppByPrint(Map<String, Object> entityMap);

}
