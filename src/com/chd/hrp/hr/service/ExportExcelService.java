package com.chd.hrp.hr.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.sc.HrTableStruc;

public interface ExportExcelService {
    /**
     * 查询导出数据
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	List<List<String>> queryExportData(Map<String, Object> mapVo)throws DataAccessException;
    
    /**
     * 查询导出表头
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	HrTableStruc queryTabColsByCodeByExport(Map<String, Object> entityMap)throws DataAccessException;


}
