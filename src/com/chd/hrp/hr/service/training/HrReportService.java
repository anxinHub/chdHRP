package com.chd.hrp.hr.service.training;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.base.HrUser;

public interface HrReportService {
    
	
   /**
    * 查询签到汇总表
    * @param page
    * @return
    */
	String queryReportSign(Map<String, Object> page) throws DataAccessException;
     /**
      * 查询考核评价表
      * @param page
      * @return
      */
	String queryReportExam(Map<String, Object> page) throws DataAccessException;
      /**
       * 查询补考评价
       * @param page
       * @return
       */
	String queryReportBuKao(Map<String, Object> page) throws DataAccessException;
    /**
     * 生成综合评价
     * @param page
     * @return
     */
	String queryReportEvaluate(Map<String, Object> page) throws DataAccessException;
	/**
	 * 添加综合评价
	 * @param mapVo
	 * @return
	 */
	String addReportEvaluate(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 个人年度汇总
	 * @param page
	 * @return
	 */
	String queryReportEmpYear(Map<String, Object> page) throws DataAccessException;
	/**
	 * 查询职工
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> queryUser(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询已经生成过的综合评价
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryReportEvaluateTable(Map<String, Object> page)throws DataAccessException;

}
