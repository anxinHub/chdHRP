package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrAcademicPaperIntegration;


/**
 * 学术论文积分
 * @author Administrator
 *
 */
public interface HrAcademicPaperIntegrationService {
    /**
     * 增加学术论文积分
     * @param mapVo
     * @return
     */
	String addAcademicPaperIntegration(Map<String, Object> mapVo)throws DataAccessException;
 
	/**
	 * 删除学术论文积分
	 * @param listVo
	 */
	String  deleteAcademicPaperIntegration(List<HrAcademicPaperIntegration> listVo)throws DataAccessException;
	/**
	 * 查询学术论文积分
	 * @param page
	 * @return
	 */
	String queryAcademicPaperIntegration(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询论文类别下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryPaperType(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询影响因子下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryAffectPara(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 继承
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String copyAcademicPaper(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 根据年份查询分数
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryTotMainByYear(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 保存最高分
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String savePersonalAcadePaper(Map<String, Object> entityMap) throws DataAccessException;

}
