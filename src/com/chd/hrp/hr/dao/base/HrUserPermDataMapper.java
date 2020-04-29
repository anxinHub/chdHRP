package com.chd.hrp.hr.dao.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.base.HrUser;
import com.chd.hrp.hr.entity.base.HrUserPermData;
import com.chd.hrp.hr.entity.base.PermData;




public interface HrUserPermDataMapper extends SqlMapper{

	List<HrUserPermData> queryUserPermData(Map<String, Object> entityMap);

	List<PermData> queryPermData(Map<String, Object> entityMap, RowBounds rowBounds);

	void addUserPermData(List<HrUserPermData> entityMap);

	void addRolePermData(List<HrUserPermData> entityMap);

	void deleteRolePermData(HashMap<String,Object> entityMap);

	void deleteUserPermData(HashMap<String,Object> map);

	String queryColumnIdByTableCode(Map<String, Object> entityMap);

	void addBatchUserPermData(Map<String, Object> entityMap);

	void addBatchRolePermData(Map<String, Object> entityMap);

	List<HrUser> queryUser(Map<String, Object> entityMap, RowBounds rowBounds);

	List<PermData> queryPermData(Map<String, Object> entityMap);

}
