package com.chd.hrp.mat.dao.dura.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description: 耐用品二维码查询
 * @Table:
 * @Version: 1.0
 */
public interface MatDuraQueryQrCodeMapper extends SqlMapper {

	public List<Map<String, Object>> queryMatDuraQueryQrCode(
			Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryMatDuraQueryQrCode(
			Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
}
