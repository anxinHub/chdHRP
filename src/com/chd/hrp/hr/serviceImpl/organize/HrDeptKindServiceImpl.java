package com.chd.hrp.hr.serviceImpl.organize;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.entity.dict.AssFac;
import com.chd.hrp.ass.entity.in.AssInMainSpecial;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.dao.organize.HrDeptKindMapper;
import com.chd.hrp.hr.dao.organize.HrDeptMapper;
import com.chd.hrp.hr.entity.orangnize.HrDept;
import com.chd.hrp.hr.entity.orangnize.HrDeptKind;
import com.chd.hrp.hr.service.organize.HrDeptKindService;
import com.chd.hrp.hr.serviceImpl.base.HrSelectServiceImpl;
import com.github.pagehelper.PageInfo;

/**
 * 部门分类
 * 
 * @author Administrator
 *
 */
@Service("hrDeptKindService")
public class HrDeptKindServiceImpl implements HrDeptKindService {

	private static Logger logger = Logger.getLogger(HrDeptKindServiceImpl.class);

	@Resource(name = "hrDeptKindMapper")
	private final HrDeptKindMapper hrDeptKindMapper = null;
	
	
	@Resource(name = "hrDeptMapper")
	private final HrDeptMapper hrDeptMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 新增部门
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)

	public String addDeptKind(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		//获取对象职务等级
	List<HrDeptKind> list = hrDeptKindMapper.queryDeptKindById(entityMap);

	if (list.size() > 0) {

		for (HrDeptKind hrDutyLevel : list) {
			if (hrDutyLevel.getKind_code().equals(entityMap.get("kind_code"))) {
				return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() + "已存在.\"}";
			}
			if (hrDutyLevel.getKind_name().equals(entityMap.get("kind_name"))) {
				return "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}";
			}
		}
	}
		
		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("kind_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("kind_name").toString()));

			hrDeptKindMapper.addDeptKind(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrDeptKind queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrDeptKindMapper.queryByCode(entityMap);
	}
	/**
	 * 修改保存
	 */
	@Override
	public String deptKindUpate(Map<String, Object> entityMap) throws DataAccessException {

		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());

			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("kind_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("kind_name").toString()));
			
			
			List<HrDeptKind> deptKind = hrDeptKindMapper.queryDeptKindByCodeName(entityMap);
			if (deptKind != null || deptKind.size() > 0) {

				for (HrDeptKind deptKind2 : deptKind) {
					if(deptKind2.getKind_name().equals(entityMap.get("kind_name").toString())){
						return "{\"error\":\"名称：" + entityMap.get("kind_name").toString() + "重复.\"}";
					}
				}
				

			}
			
			hrDeptKindMapper.deptKindUpate(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	/**
	 * 查询所有部门
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrDeptKind> list = (List<HrDeptKind>) hrDeptKindMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrDeptKind> list = (List<HrDeptKind>) hrDeptKindMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 删除部门
	 */
	@Override
	public String deleteDeptKind(List<Map<String,Object>> entityList) throws DataAccessException {

		try {
			for(Map<String,Object> item : entityList){
				List<HrDept> list = hrDeptMapper.queryDept(item);
				
				if(list.size() >0 ){
					return "{\"error\":\"所选部门分类正被部门架构使用，无法删除.\",\"state\":\"true\"}";
				}
			}
			int state = hrDeptKindMapper.deleteDeptKind(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

	}
	catch (Exception e) {

		logger.error(e.getMessage(), e);

		return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchDeptKind\"}";

	}
}
	@Override
	public String queryDeptKindDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrDeptKindMapper.queryDeptKindDict(entityMap, rowBounds));
	}

	public HrDeptKind queryDeptKindByCode(Map<String, Object> entityMap) throws DataAccessException{
		
		return hrDeptKindMapper.queryDeptKindByCode(entityMap);
		
	}




}
