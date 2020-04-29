package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrAcademicPaper;


/**
 * 学术论文发表
 * @author Administrator
 *
 */
public interface HrAcademicPaperService {
    /**
     * 增加学术论文发表
     * @param mapVo
     * @return
     */
	String addAcademicPaper(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrAcademicPaper queryByCodeAcademicPaper(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改学术论文发表
	 * @param mapVo
	 * @return
	 */
	String updateAcademicPaper(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除学术论文发表
	 * @param listVo
	 */
	String  deleteAcademicPaper(List<HrAcademicPaper> listVo)throws DataAccessException;
	/**
	 * 查询学术论文发表
	 * @param page
	 * @return
	 */
	String queryAcademicPaper(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryAcademicPaperTree(Map<String, Object> mapVo)throws DataAccessException;

}
