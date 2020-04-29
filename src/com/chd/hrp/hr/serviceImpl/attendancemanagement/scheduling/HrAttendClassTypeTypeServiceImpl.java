package com.chd.hrp.hr.serviceImpl.attendancemanagement.scheduling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.attendancemanagement.scheduling.HrAttendClassTypeMapper;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrAttendClassType;
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrAttendClassTypeService;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.github.pagehelper.PageInfo;

@Service("hrAttendClassTypeService")
public class HrAttendClassTypeTypeServiceImpl implements HrAttendClassTypeService {
	private static Logger logger = Logger.getLogger(HrAttendClassTypeTypeServiceImpl.class);

	@Resource(name = "hrAttendClassTypeMapper")
	private final HrAttendClassTypeMapper hrAttendClassTypeMapper = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	@Override
	public String addAttendClassType(Map<String, Object> entityMap) throws DataAccessException {
		try {
			String msg = "";
			List<HrAttendClassType> list = hrAttendClassTypeMapper.queryByCodeClassType(entityMap);
			if (list != null) {
				for (HrAttendClassType hrAttendClassType2 : list) {
					if (hrAttendClassType2.getAttend_class_typecode().equals(entityMap.get("attend_class_typecode"))) {
						return "{\"error\":\"班次分类编码：" + hrAttendClassType2.getAttend_class_typecode().toString()
								+ "已存在.\"}";
					}
					if (hrAttendClassType2.getAttend_class_typename().equals(entityMap.get("attend_class_typename"))) {
						return "{\"error\":\"班次分类名称：" + hrAttendClassType2.getAttend_class_typename().toString()
								+ "已存在.\"}";
					}
				}
			}
			hrAttendClassTypeMapper.add(entityMap);
			msg = "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

			return msg;
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public String deleteAttendClassType(List<HrAttendClassType> listVo) throws DataAccessException {
		try {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());

			int count = hrAttendClassTypeMapper.queryArea(listVo, mapVo);
			if (count > 0) {
				return ("{\"error\":\"删除失败，班次分类被以下业务使用：排班处理。\",\"state\":\"false\"}");
			}

			int attendClass = hrAttendClassTypeMapper.queryAttendClass(listVo, mapVo);
			if (attendClass > 0) {
				return ("{\"error\":\"删除失败，班次分类被以下业务使用：班次设置。\",\"state\":\"false\"}");
			}

			hrAttendClassTypeMapper.deleteAttendClassType(listVo, mapVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String queryAttendClassType(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrAttendClassType> list = (List<HrAttendClassType>) hrAttendClassTypeMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrAttendClassType> list = (List<HrAttendClassType>) hrAttendClassTypeMapper.query(entityMap,
					rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public HrAttendClassType queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrAttendClassTypeMapper.queryByCode(entityMap);
	}

	@Override
	public String updateAttendClassType(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<HrAttendClassType> list = hrAttendClassTypeMapper.queryByCodeClassTypeUpdate(entityMap);
			if (list != null) {
				for (HrAttendClassType hrAttendClassType2 : list) {
				
					if (hrAttendClassType2.getAttend_class_typename().equals(entityMap.get("attend_class_typename"))) {
						return "{\"error\":\"班次分类名称：" + hrAttendClassType2.getAttend_class_typename().toString()
								+ "已存在.\"}";
					}
				}
			}
			hrAttendClassTypeMapper.update(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public List<Map<String, Object>> queryAttendClassTypeByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrAttendClassTypeMapper.queryAttendClassTypeByPrint(entityMap));
		 return list;
	}

}
