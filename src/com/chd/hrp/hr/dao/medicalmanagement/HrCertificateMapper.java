package com.chd.hrp.hr.dao.medicalmanagement;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrCertificate;

/**
 * 证件管理
 * @author Administrator
 *
 */
public interface HrCertificateMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrCertificate> queryCertificateById(Map<String, Object> entityMap);
    /**
     * 删除证件管理
     * @param entityList
     * @param mapVo 
     */
	void deleteCertificate(@Param(value="list")List<?> entityList,@Param(value="map") Map<String, Object> mapVo);
	HrCertificate queryByCodeExec(Map<String, Object> entityMap);
	
	/**
	 * 查询是否存在
	 * @param hrNursingPromotion
	 * @return
	 */
	HrCertificate queryByCode(HrCertificate hrNursingPromotion);
	/**
	 *添加页 查询是否存在
	 * @param hrNursingPromotion
	 * @return
	 */
	public String queryByCodeNO(Map<String, Object> entityMap);
	
	/**
	 * 修改开展例数
	 * @param Certificate
	 */
	void updateCaseNuM(HrCertificate Certificate);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryCertificateByPrint(Map<String, Object> entityMap);

	void addBatchImport(List<Map<String, Object>> saveList);
	
	public Long queryHrCertSeq() throws DataAccessException;
	
	public int queryByCodeCount(Map<String, Object> entityMap);
}
