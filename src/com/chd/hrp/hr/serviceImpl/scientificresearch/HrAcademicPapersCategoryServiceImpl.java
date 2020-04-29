package com.chd.hrp.hr.serviceImpl.scientificresearch;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.scientificresearch.HrAcademicPapersCategoryMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrAcademicPapersCategory;
import com.chd.hrp.hr.service.scientificresearch.HrAcademicPapersCategoryService;
import com.github.pagehelper.PageInfo;

/**
 * 学术论文类别
 * 
 * @author Administrator
 *
 */
@Service("hrAcademicPapersCategoryService")
public class HrAcademicPapersCategoryServiceImpl implements HrAcademicPapersCategoryService {
	private static Logger logger = Logger.getLogger(HrAcademicPapersCategoryServiceImpl.class);

	@Resource(name = "hrAcademicPapersCategoryMapper")
	private final HrAcademicPapersCategoryMapper hrAcademicPapersCategoryMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);




}
