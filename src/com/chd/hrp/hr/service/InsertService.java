package com.chd.hrp.hr.service;

import java.util.List;
import java.util.Map;
import java.util.stream.BaseStream;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.orangnize.HosStation;

public interface InsertService {
    /**
     * 添加
     * @param mapVo
     * @return
     */
	String addBaseInfo(Map<String, Object> mapVo)throws DataAccessException;


}
