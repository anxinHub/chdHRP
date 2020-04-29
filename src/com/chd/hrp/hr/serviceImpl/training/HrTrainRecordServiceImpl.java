package com.chd.hrp.hr.serviceImpl.training;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.training.HrTrainRecordMapper;
import com.chd.hrp.hr.dao.training.setting.HrNoticeModeMapper;
import com.chd.hrp.hr.entity.training.setting.HrNoticeMode;
import com.chd.hrp.hr.service.training.HrTrainRecordService;

@Service("hrTrainRecordService")
public class HrTrainRecordServiceImpl implements HrTrainRecordService {

	@Resource(name = "hrTrainRecordMapper")
	private HrTrainRecordMapper hrTrainRecordMapper;
	@Resource(name = "hrNoticeModeMapper")
	private HrNoticeModeMapper hrNoticeModeMapper;

	@Override
	public String saveTrainRecordCourse(Map<String, Object> mapVo) {
		try {
			Map<String, Object> map = hrTrainRecordMapper.queryTrainClass(mapVo);
			if (map == null || map.isEmpty()) {
				hrTrainRecordMapper.addTrainClasss(mapVo);
			} else {
				hrTrainRecordMapper.updateTrainClasss(mapVo);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String saveTrainRecordTarget(List<Map<String, Object>> listVo) {
		try {
			
			Map<String, Object> mapVo = new HashMap<>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("plan_id", listVo.get(0).get("plan_id"));
			
			if (!listVo.isEmpty()) {
				List<Map<String, Object>> delList = new ArrayList<>();
				for (Map<String, Object> map : listVo) {
					if (map.get("row_id") != null) {
						delList.add(map);
					}else {
						mapVo.put("emp_id", map.get("emp_id"));
						List<Map<String,Object>> list = hrTrainRecordMapper.queryTrainRecordTarget(mapVo);
						if (!list.isEmpty() && list.size() == 1) {
							return "{\"error\":\"人员重复\"}";
						}
					}
				}
				if (!delList.isEmpty()) {
					hrTrainRecordMapper.deleteTrainRecordTarget(delList);
				}
				hrTrainRecordMapper.addTrainRecordTarget(listVo);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String deleteTrainRecordTarget(List<Map<String, Object>> listVo) {
		try {
			if (!listVo.isEmpty()) {
				hrTrainRecordMapper.deleteTrainRecordTarget(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryTrainRecordTarget(Map<String, Object> mapVo) {
		try {
			if (mapVo.get("dept_code") != null && mapVo.get("dept_code") != "") {
				String dept_id_no = mapVo.get("dept_code").toString();
				mapVo.put("dept_id", dept_id_no.split("@")[1]);
				mapVo.put("dept_no", dept_id_no.split("@")[0]);
			}
			List<Map<String, Object>> list = hrTrainRecordMapper.queryTrainRecordTarget(mapVo);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 打印
	 */
	@Override
	public List<Map<String, Object>> queryTrainRecordTargetPrint(Map<String, Object> mapVo) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			List<Map<String, Object>> list = hrTrainRecordMapper.queryTrainRecordTarget(mapVo);
			return list;
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryTrainRecordNoticePrint(Map<String, Object> mapVo) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			List<Map<String, Object>> list = hrTrainRecordMapper.queryTrainRecordNotice(mapVo);
			return list;
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryTrainRecordSignInPrint(Map<String, Object> mapVo) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			List<Map<String, Object>> list = hrTrainRecordMapper.queryTrainRecordSignIn(mapVo);
			return list;
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryTrainRecordBKEmpPrint(Map<String, Object> mapVo) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			List<Map<String, Object>> list = hrTrainRecordMapper.queryTrainRecordBKEmp(mapVo);
			return list;
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryTrainRecordNotice(Map<String, Object> mapVo) {
		try {
			if (mapVo.get("dept_code") != null && mapVo.get("dept_code") != "") {
				String dept_id_no = mapVo.get("dept_code").toString();
				mapVo.put("dept_id", dept_id_no.split("@")[1]);
				mapVo.put("dept_no", dept_id_no.split("@")[0]);
			}

			List<Map<String, Object>> list = hrTrainRecordMapper.queryTrainRecordNotice(mapVo);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String createTrainRecordNotice(Map<String, Object> mapVo) {
		try {
			Map<String, Object> trainClass = hrTrainRecordMapper.queryTrainClass(mapVo);
			if (trainClass == null || trainClass.isEmpty()) {
				return "{\"error\":请先保存课程\"\"}";
			}

			trainClass.put("note", "");
			List<Map<String, Object>> list = hrTrainRecordMapper.queryTrainObject(mapVo);
			if (list == null || list.isEmpty()) {
				return "{\"error\":请先保存培训对象\"\"}";
			}

			Integer seatNumber = 0;
			for (Map<String, Object> map : list) {
				map.put("seat_number", ++seatNumber);
			}
			trainClass.put("list", list);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			trainClass.put("train_date", dateFormat.format(trainClass.get("train_date")));

			hrTrainRecordMapper.createTrainRecordNotice(trainClass);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String deleteTrainRecordNotice(List<Map<String, Object>> listVo) {
		try {
			if (!listVo.isEmpty()) {
				hrTrainRecordMapper.deleteTrainRecordNotice(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String saveTrainRecordNotice(List<Map<String, Object>> listVo) {
		try {
			if (!listVo.isEmpty()) {
				List<Map<String, Object>> delList = new ArrayList<>();
				for (Map<String, Object> map : listVo) {
					if (map.get("row_id") != null) {
						delList.add(map);
					}
				}
				if (!delList.isEmpty()) {
					hrTrainRecordMapper.deleteTrainRecordNotice(delList);
				}
				hrTrainRecordMapper.addTrainRecordNotice(listVo);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryTrainRecordSignIn(Map<String, Object> mapVo) {
		try {
			if (mapVo.get("dept_code") != null && mapVo.get("dept_code") != "") {
				String dept_id_no = mapVo.get("dept_code").toString();
				mapVo.put("dept_id", dept_id_no.split("@")[1]);
				mapVo.put("dept_no", dept_id_no.split("@")[0]);
			}

			List<Map<String, Object>> list = hrTrainRecordMapper.queryTrainRecordSignIn(mapVo);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String deleteTrainRecordSignIn(List<Map<String, Object>> listVo) {
		try {
			if (!listVo.isEmpty()) {
				hrTrainRecordMapper.deleteTrainRecordSignIn(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String saveTrainRecordSignIn(List<Map<String, Object>> listVo) {
		try {
			if (!listVo.isEmpty()) {
				List<Map<String, Object>> delList = new ArrayList<>();
				for (Map<String, Object> map : listVo) {
					if (map.get("row_id") != null) {
						delList.add(map);
					}
				}
				if (!delList.isEmpty()) {
					hrTrainRecordMapper.deleteTrainRecordSignIn(delList);
				}
				hrTrainRecordMapper.saveTrainRecordSignIn(listVo);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryTrainRecordCourseware(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = hrTrainRecordMapper.queryTrainRecordCourseware(mapVo);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String deleteTrainRecordCourseware(List<Map<String, Object>> listVo) {
		try {
			if (!listVo.isEmpty()) {
				hrTrainRecordMapper.deleteTrainRecordCourseware(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String saveTrainRecordCourseware(List<Map<String, Object>> listVo) {
		try {
			if (!listVo.isEmpty()) {
				Integer kj_id = 1;
				for (Map<String, Object> map : listVo) {
					map.put("kj_id", kj_id++);
				}
				Map<String, Object> mapVo = new HashMap<>();
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("plan_id", listVo.get(0).get("plan_id"));
				hrTrainRecordMapper.deleteTrainRecordCoursewareByPlanId(mapVo);
				hrTrainRecordMapper.saveTrainRecordCourseware(listVo);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryTrainRecordBKEmp(Map<String, Object> mapVo) {
		try {
			if (mapVo.get("dept_code") != null && mapVo.get("dept_code") != "") {
				String dept_id_no = mapVo.get("dept_code").toString();
				mapVo.put("dept_id", dept_id_no.split("@")[1]);
				mapVo.put("dept_no", dept_id_no.split("@")[0]);
			}

			List<Map<String, Object>> list = hrTrainRecordMapper.queryTrainRecordBKEmp(mapVo);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String deleteTrainRecordBKEmp(List<Map<String, Object>> listVo) {
		try {
			if (!listVo.isEmpty()) {
				hrTrainRecordMapper.deleteTrainRecordBKEmp(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String saveTrainRecordBKEmp(List<Map<String, Object>> listVo) {
		try {
			if (!listVo.isEmpty()) {
				Map<String, Object> mapVo = new HashMap<>();
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("plan_id", listVo.get(0).get("plan_id"));
				hrTrainRecordMapper.deleteTrainRecordBKEmpByPlanId(mapVo);
				hrTrainRecordMapper.saveTrainRecordBKEmp(listVo);

				mapVo.put("emp_num", listVo.size());
				hrTrainRecordMapper.updateTrainRecordBK(mapVo);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String saveTrainRecordBK(Map<String, Object> mapVo) {
		try {
			Map<String, Object> map = hrTrainRecordMapper.queryTrainRecordBK(mapVo);
			if (map == null || map.isEmpty()) {
				hrTrainRecordMapper.saveTrainRecordBK(mapVo);
			} else {
				hrTrainRecordMapper.updateTrainRecordBK(mapVo);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryEmpDetailInfo(Map<String, Object> mapVo) {
		try {
			Map<String, Object> map = hrTrainRecordMapper.queryEmpDetailInfo(mapVo);
			return ChdJson.toJson(map);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String saveTrainRecordPhoto(List<Map<String, Object>> saveList) {
		try {
			if (!saveList.isEmpty()) {
				Map<String, Object> mapVo = new HashMap<>();
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("plan_id", saveList.get(0).get("plan_id"));
				hrTrainRecordMapper.deleteTrainRecordPhoto(mapVo);

				hrTrainRecordMapper.saveTrainRecordPhoto(saveList);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String querySignInEmpSelect(Map<String, Object> mapVo) {
		try {
			if (mapVo.get("dept_code") != null && mapVo.get("dept_code") != "") {
				String dept_id_no = mapVo.get("dept_code").toString();

				mapVo.put("dept_id", dept_id_no.split("@")[1]);
				mapVo.put("dept_no", dept_id_no.split("@")[0]);
			}
			if (mapVo.get("ids") != null && mapVo.get("ids") != "") {
				String object = (String) mapVo.get("ids");
				String ids = object.substring(0, object.length() - 1);
				mapVo.put("ids", ids);
			}
			if (mapVo.get("type") != null && mapVo.get("type") != "") {
				String type = mapVo.get("type").toString();
				if ("signin".equals(type)) {
					mapVo.put("not_table", "HR_TRAIN_SIGN_IN");
				} else if ("inform".equals(type)) {
					mapVo.put("not_table", "HR_TRAIN_INFORM");
				}
			}

			List<Map<String, Object>> map = hrTrainRecordMapper.querySignInEmpSelect(mapVo);
			return JSONArray.toJSONString(map);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryTrainRecordClass(Map<String, Object> mapVo) {
		try {
			Map<String, Object> map = hrTrainRecordMapper.queryTrainClass(mapVo);
			return ChdJson.toJson(map);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryTrainRecordBK(Map<String, Object> mapVo) {
		try {
			Map<String, Object> map = hrTrainRecordMapper.queryTrainRecordBK(mapVo);
			return ChdJson.toJson(map);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryTrainRecordPhoto(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = hrTrainRecordMapper.queryTrainRecordPhoto(mapVo);
			return list;
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String deleteTrainRecordPhoto(Map<String, Object> mapVo) {
		try {
			hrTrainRecordMapper.deleteTrainRecordPhoto(mapVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String importDate(Map<String, Object> mapVo) {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();

		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(mapVo);
			if (list != null && list.size() > 0) {
				String type = mapVo.get("type").toString();
				for (Map<String, List<String>> map : list) {
					// 过滤空行
					if ((null == map.get("emp_name")
							|| ("".equals(map.get("emp_name").get(1)) || "null".equals(map.get("emp_name").get(1))))
							& (null == map.get("emp_code") || ("".equals(map.get("emp_code").get(1))
									|| "null".equals(map.get("emp_code").get(1))))) {
						continue;
					}
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("plan_id", mapVo.get("plan_id"));

					if (map.get("emp_code") != null) {
						saveMap.put("emp_code", map.get("emp_code").get(1));
					}
					saveMap.put("emp_name", map.get("emp_name").get(1));

					String emp_id = hrTrainRecordMapper.queryEmpIdByCodeName(saveMap);
					if (emp_id == null) {
						failureMsg.append("查询不到" + map.get("emp_name").get(1) + "的数据。");
						failureNum++;
						continue;
					}
					saveMap.put("emp_id", emp_id);

					switch (type) {
					case "target":
						saveMap.put("table_code", "HR_TRAIN_OBJECT");
						break;
					case "notice":
						// Map<String, Object> trainClass = hrTrainRecordMapper.queryTrainClass(mapVo);
						// if (trainClass == null || trainClass.isEmpty()) {
						// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						// trainClass.put("train_date",
						// dateFormat.format(trainClass.get("train_date")));
						// saveMap.putAll(trainClass);
						// }
						saveMap.put("table_code", "HR_TRAIN_INFORM");
						saveMap.put("train_date", map.get("train_date").get(1));
						saveMap.put("time_begin", map.get("time_begin").get(1));
						saveMap.put("time_end", map.get("time_end").get(1));
						saveMap.put("train_site", map.get("train_site").get(1));
						saveMap.put("teacher", map.get("teacher").get(1));
						saveMap.put("train_title", map.get("train_title").get(1));
						saveMap.put("way_name", map.get("inform_way_code").get(1));
						HrNoticeMode notice = hrNoticeModeMapper.queryByUniqueness(saveMap);
						if (notice == null) {
							failureMsg.append("无法查询到名称为[" + map.get("inform_way_code").get(1) + "]通知方式");
							failureNum++;
							continue;
						}

						saveMap.put("inform_way_code", notice.getWay_code());
						saveMap.put("note", map.get("note").get(1));
						saveMap.put("seat_number", map.get("seat_number").get(1));
						break;
					case "signin":
						saveMap.put("table_code", "HR_TRAIN_SIGN_IN");
						saveMap.put("in_time", map.get("in_time").get(1));
						saveMap.put("out_time", map.get("out_time").get(1));
						String sign_case = map.get("sign_case").get(1);
						if ("迟到+早退".equals(sign_case)) {
							saveMap.put("sign_case", "4");
						} else if ("迟到".equals(sign_case)) {
							saveMap.put("sign_case", "2");
						} else if ("早退".equals(sign_case)) {
							saveMap.put("sign_case", "3");
						} else {
							saveMap.put("sign_case", "1");
						}
						break;
					default:
						break;
					}

					Integer count = hrTrainRecordMapper.queryEmpExists(saveMap);
					if (count > 0) {
						failureMsg.append(map.get("emp_name").get(1) + "已存在;");
						failureNum++;
					} else {
						successNum++;
						saveList.add(saveMap);
					}
				}
				if (saveList.size() > 0) {
					switch (type) {
					case "target":
						hrTrainRecordMapper.addTrainRecordTarget(saveList);
						break;
					case "notice":
						hrTrainRecordMapper.addTrainRecordNotice(saveList);
						break;
					case "signin":
						hrTrainRecordMapper.saveTrainRecordSignIn(saveList);
						break;
					default:
						break;
					}
				}
				if (failureNum > 0) {
					failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 " + successNum + "条" + failureMsg + "\",\"state\":\"true\"}";
		} catch (Exception e) {
			return "{\"error\":\"导入失败！\"}";
		}
	}

	@Override
	public String queryNoticeModeSelect(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> map = hrTrainRecordMapper.queryNoticeModeSelect(mapVo);
			return JSONArray.toJSONString(map);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryEmpSelectForRecord(Map<String, Object> mapVo) {
		try {
			if (mapVo.get("dept_code") != null && mapVo.get("dept_code") != "") {
				String dept_id_no = mapVo.get("dept_code").toString();
				mapVo.put("dept_id", dept_id_no.split("@")[1]);
				mapVo.put("dept_no", dept_id_no.split("@")[0]);
			}
			List<Map<String, Object>> map = hrTrainRecordMapper.queryEmpSelectForRecord(mapVo);
			return JSONArray.toJSONString(map);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryTrainRecordTargetForAdd(Map<String, Object> mapVo) {
		try {
			if (mapVo.get("dept_code") != null && mapVo.get("dept_code") != "") {
				String dept_id_no = mapVo.get("dept_code").toString();
				mapVo.put("dept_id", dept_id_no.split("@")[1]);
				mapVo.put("dept_no", dept_id_no.split("@")[0]);
			}
			List<Map<String, Object>> map = hrTrainRecordMapper.queryTrainRecordTargetForAdd(mapVo);
			return ChdJson.toJson(map);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String createTrainRecordSignIn(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = hrTrainRecordMapper.queryTrainObjectForSignIn(mapVo);
			if (list == null || list.isEmpty()) {
				return "{\"error\":请先保存培训对象\"\"}";
			}

			hrTrainRecordMapper.saveTrainRecordSignIn(list);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String createTrainRecordBKEmp(Map<String, Object> mapVo) {
		try {
			mapVo.put("bkemp", "bkemp");
			List<Map<String, Object>> list = hrTrainRecordMapper.queryTrainObjectForSignIn(mapVo);
			if (list == null || list.isEmpty()) {
				return "{\"error\":请先保存培训对象\"\"}";
			}

			hrTrainRecordMapper.saveTrainRecordBKEmp(list);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

}
