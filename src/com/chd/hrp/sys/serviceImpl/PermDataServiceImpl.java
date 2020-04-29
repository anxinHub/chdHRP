package com.chd.hrp.sys.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.sys.dao.PermDataMapper;
import com.chd.hrp.sys.entity.PermData;
import com.chd.hrp.sys.entity.UserPermData;
import com.chd.hrp.sys.service.PermDataService;
import com.github.pagehelper.PageInfo;
@Service("permDataService")
public class PermDataServiceImpl implements PermDataService{
	
	private static Logger logger = Logger.getLogger(PermDataServiceImpl.class);
	
	@Resource(name = "permDataMapper")
	private final PermDataMapper permDataMapper = null;

	@Override
	public String queryPermData(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<PermData> list = permDataMapper.queryPermData(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public List<UserPermData> queryUserPermData(
			Map<String, Object> entityMap)
			throws DataAccessException {
		return permDataMapper.queryUserPermData(entityMap);
	}

	@Override
	public List<UserPermData> queryRolePermData(
			Map<String, Object> entityMap) throws DataAccessException {
		return permDataMapper.queryRolePermData(entityMap);
	}

	@Override
	public String addUserPermData(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			
			permDataMapper.addUserPermData(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addUserPermData\"}";
		}
	}

	@Override
	public String addRolePermData(List<Map<String, Object>> entityMap)throws DataAccessException {
		
		try {
			
			permDataMapper.addRolePermData(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addRolePermData\"}";
		}
	}

	@Override
	public String deleteRolePermData(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			permDataMapper.deleteRolePermData(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteRolePermData\"}";
		}
	}

	@Override
	public String deleteUserPermData(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			permDataMapper.deleteUserPermData(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteUserPermData\"}";
		}
	}

	@Override
	public String queryColumnIdByTableCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return permDataMapper.queryColumnIdByTableCode(entityMap);
	}

	@Override
	public String addBatchPerm(Map<String, Object> mapVo) throws DataAccessException {
		
		try{
		
			String sql = "select column_id  from SYS_PERM_DATA where table_code='"+mapVo.get("table_code")+"'";
			
			mapVo.put("sql", sql);
			
			String columnId = permDataMapper.queryColumnIdByTableCode(mapVo);//查询字段
			
			mapVo.put("columnId", columnId);
			
			StringBuffer sqlWhere = new StringBuffer();
			
			String[] obj = String.valueOf(mapVo.get("obj")).split("\\.");
			
			String[] wsql = String.valueOf(mapVo.get("obj")).split("\\*\\*\\*\\*");
			
			sqlWhere.append(" where 1 = 1");
			
			if(mapVo.get("table_code").equals("HOS_EMP_YH")){
				mapVo.put("dic_table_code", "hr_fiied_data");
				mapVo.put("table_code", "HOS_EMP_YH");
				mapVo.put("columnId", 	"FIELD_COL_CODE");
			}
	       if(("HOS_EMP_YH").equals(obj[0])){
	    	   sqlWhere.append(" and FIELD_TAB_CODE='HOS_EMP_YH'");
	       }
			if(("1").equals(obj[5])){
				sqlWhere.append(" and group_id = " + mapVo.get("group_id").toString());
			}
			if(("1").equals(obj[6])){
				sqlWhere.append(" and hos_id = " + mapVo.get("hos_id").toString());
			}
			if(("1").equals(obj[7])){
				sqlWhere.append(" and copy_code = '" + mapVo.get("copy_code").toString()+"'");
			}
			if(("1").equals(obj[8])){ 
				sqlWhere.append(" and acc_year = '" + mapVo.get("acc_year").toString()+"'");
			}
			if(mapVo.get("mod_code").toString().equals("00")){ 
				sqlWhere.append(" and is_stop = 0 "); 
			}//2017/07/18
			//由于不知道为什么只给系统平台(mod_code等于00)加is_stop=0,但是绩效和奖金系统需查询未停用的,所以加上以下两个分支
	
			else if("09".equals(mapVo.get("mod_code").toString()) && "APHI_DEPT_DICT".equals(mapVo.get("table_code").toString())){
				sqlWhere.append(" and is_stop = 0 ");
			}
			//由于物资类别需要查询的是未停用的 ，并且是物流模块，所以增加了一个判断
			else if("04".equals(mapVo.get("mod_code").toString()) && "MAT_TYPE_DICT".equals(mapVo.get("table_code").toString())){
				sqlWhere.append(" and is_stop = 0 ");
			}else if("01".equals(mapVo.get("mod_code").toString()) && "BUDG_PAYMENT_ITEM_DICT".equals(mapVo.get("table_code").toString())){
				sqlWhere.append(" and is_stop = 0 ");
			}
			if("REP_DEFINE".equals(mapVo.get("table_code").toString())){
				sqlWhere.append(" and is_perm = 1 ");
			}
			if(wsql.length > 1 && wsql[1] != null && !"".equals(wsql[1]) && (wsql[1].startsWith(" and ") || wsql[1].startsWith(" AND "))){
				sqlWhere.append(wsql[1].replace("a.", "").replace("A.", ""));
			}
			
			mapVo.put("sqlWhere", sqlWhere);
			
			if("1".equals(mapVo.get("tip"))){//如果tip=1 则添加用户权限  
				
				mapVo.put("user_id", mapVo.get("perm_id"));
				
				permDataMapper.deleteUserPermData(mapVo);
				
				permDataMapper.addBatchUserPermData(mapVo);
				
			}else{// 否则添加角色权限
				
				mapVo.put("role_id", mapVo.get("perm_id"));
				
				permDataMapper.deleteRolePermData(mapVo);
				
				permDataMapper.addBatchRolePermData(mapVo);
			}
		
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	
	@Override
	public String addBatchUserPermData(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			permDataMapper.addBatchUserPermData(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchUserPermData\"}";
		}
	}

	@Override
	public String addBatchRolePermData(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			permDataMapper.addBatchRolePermData(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchRolePermData\"}";
		}
	}

}
