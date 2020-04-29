package com.chd.hrp.med.dao.info.basic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedLocationType;

import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedLocationType;
/**
 * 
 * @Description:
 * 08401 货位分类字典
 * @Table:
 * MED_LOCATION_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedLocationTypeMapper extends SqlMapper{

	public int queryMedLocationDictIsExists(Map<String, Object> map) throws DataAccessException;
	
	
	
	
}
