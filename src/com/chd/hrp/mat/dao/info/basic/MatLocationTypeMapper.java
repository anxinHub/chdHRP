package com.chd.hrp.mat.dao.info.basic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatLocationType;

import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatLocationType;
/**
 * 
 * @Description:
 * 04401 货位分类字典
 * @Table:
 * MAT_LOCATION_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatLocationTypeMapper extends SqlMapper{

	public int queryMatLocationDictIsExists(Map<String, Object> map) throws DataAccessException;
	
	
	
	
}
