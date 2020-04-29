package com.chd.hrp.hr.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.hrp.hr.entity.sc.HrTableColumnConfig;
import com.chd.hrp.hr.entity.sc.HrTableColumnFormConfig;
import com.chd.hrp.hr.entity.sc.HrTableColumnGridConfig;
import com.chd.hrp.hr.util.enums.ComponentType;

/**
 * UI组件构建
 * @author zhaonan
 *
 */
public class UIComponentBuilder {
	private HrTableColumnConfig config;
	private List<HrTableColumnGridConfig> gridConfigList;
	private List<HrTableColumnFormConfig> searchConfigList;
	private List<HrTableColumnFormConfig> formConfigList;
	private Boolean isLiger;
	
	public UIComponentBuilder(HrTableColumnConfig config) {
		this(config, false);
	}
	
	public UIComponentBuilder(HrTableColumnConfig config, boolean isLiger) {
		this.config = config;
		this.gridConfigList = this.config.getGridSetData();
		this.searchConfigList = this.config.getSearchSetData();
		this.formConfigList = this.config.getFormSetData();
		this.isLiger = isLiger;
	}

	public JSONArray columns(){
		String hiddenField = isLiger ? "hide" : "hidden";
		JSONArray columnArr = new JSONArray();
		Collections.sort(gridConfigList);
		for (HrTableColumnGridConfig grid : gridConfigList) {
			JSONObject column = new JSONObject();
			column.put("name", grid.getCol_code().toLowerCase());
			column.put("display", grid.getView_name());
			column.put("width", grid.getWidth());
			column.put("align", grid.getText_align());
			column.put(hiddenField, grid.getIs_view() == 1 ? false : true);
			column.put("type", ComponentType.getDesc(grid.getCom_type_code()));

			// 处理下拉框类型字段
			Map<String, Object> options = new HashMap<String, Object>();
			if ("01".equals(grid.getCom_type_code()) && grid.getField_tab_code() != null) {
				String path = ServletUtils.getRequest().getContextPath();
				options.put("type", "select");
				options.put("valueField", "id");
				options.put("textField", "text");
				options.put("url",
						path + "/hrp/hr/baseSelect.do?isCheck=false&field_tab_code=" + grid.getField_tab_code());
				options.put("keySupport", true);
				options.put("autocomplete", true);

				column.put(hiddenField, true);
				JSONObject name_column = new JSONObject();
				name_column.put("name", grid.getCol_code().toLowerCase() + "_name");
				name_column.put("display", grid.getView_name());
				name_column.put("width", grid.getWidth());
				name_column.put("align", grid.getText_align());
				name_column.put(hiddenField, false);
				name_column.put("type", ComponentType.getDesc(grid.getCom_type_code()));
				columnArr.add(name_column);
			}

			// 日期类型
			if ("03".equals(grid.getCom_type_code())) {
				options.put("type", "date");
			}
			
			// 处理文件上传类型字段
			if ("04".equals(grid.getCom_type_code()) || "05".equals(grid.getCom_type_code())) {
				Map<String, String> fileOption = new HashMap<String, String>();
				fileOption.put("keyField", "file");
				fileOption.put("type", "file");
				column.put("fileModel", fileOption);
			}

			column.put("editor", options);
			columnArr.add(column);
		}
		return columnArr;
	}
	
	public JSONArray searchFields(){
		return fields(this.searchConfigList);
	}
	
	public JSONArray formFields(){
		return fields(this.formConfigList);
	}
	
	public JSONArray fields(List<HrTableColumnFormConfig> configList){
		Collections.sort(configList);
		String name = isLiger ? "name" : "id";
		String textField = isLiger ? "display" : "name";
		JSONArray fieldArr = new JSONArray();
		for (HrTableColumnFormConfig form : configList) {
			if (form.getIs_view() == 1) {
				JSONObject field = new JSONObject();
				field.put(name, form.getCol_code().toLowerCase());
				field.put(textField, form.getView_name());
				field.put("width", form.getWidth());
				field.put("align", form.getText_align());
				field.put("type", ComponentType.getDesc(form.getCom_type_code()));
				if(isLiger){
					//是否必填
					if(form.getIs_required() == 1){
						Map<String, Object> validate = new HashMap<String, Object>();
						validate.put("required", true);
						field.put("validate", validate);
					}
					// 代码表数据
					Map<String, Object> options = new HashMap<String, Object>();
					if ("01".equals(form.getCom_type_code()) && form.getField_tab_code() != null) {
						String path = ServletUtils.getRequest().getContextPath();
						options.put("url",
								path + "/hrp/hr/baseSelect.do?isCheck=false&field_tab_code=" + form.getField_tab_code());
						options.put("valueField", "id");
						options.put("textField", "text");
						options.put("selectBoxWidth", form.getWidth());
						options.put("autocomplete", "true");
						options.put("width",  form.getWidth());
						if (form.getIs_default_value() == 1) {
							options.put("initValue", form.getDefault_value());
							options.put("initText ", form.getDefault_value_name());
						}
					}else{
						if (form.getIs_default_value() == 1) {
							options.put("value", form.getDefault_value());
						}
					}
	
					field.put("editor", options);
				}else{
					field.put("width", Integer.parseInt(form.getWidth()) * form.getField_width());
					field.put("place", form.getField_width());//占列数
					if (form.getIs_default_value() == 1) {
						field.put("value", form.getDefault_value());
					}
					field.put("required", form.getIs_required() == 1 ? true : false);
	
					// 区间
					if (form.getIs_section() != null && form.getIs_section() == 1) {
						if ("03".equals(form.getCom_type_code())) {
							Map<String, Object> rangeMap = new LinkedHashMap<String, Object>();
							rangeMap.put("range", true);
							field.put("OPTIONS", rangeMap);
							field.put("required", false);
						} else {
							field.put("type", "range");
							Map<String, Object> rangeIdMap = new LinkedHashMap<String, Object>();
							String[] rangeIdArr = { form.getCol_code().toLowerCase() + "_beg",
									form.getCol_code().toLowerCase() + "_end" };
							rangeIdMap.put("rangeId", rangeIdArr);
							field.put("OPTIONS", rangeIdMap);
							field.put("required", false);
						}
					}
	
					// 代码表数据
					Map<String, Object> options = new HashMap<String, Object>();
					if ("01".equals(form.getCom_type_code()) && form.getField_tab_code() != null) {
						String path = ServletUtils.getRequest().getContextPath();
						options.put("url",
								path + "/hrp/hr/baseSelect.do?isCheck=false&field_tab_code=" + form.getField_tab_code());
						options.put("defaultValue", "none");
						options.put("maxOptions", 10000);
						field.put("OPTIONS", options);
					}
					
					if ("04".equals(form.getCom_type_code()) || "05".equals(form.getCom_type_code())) {
						String path = ServletUtils.getRequest().getContextPath();
						options.put("url",
								path + "/hrp/hr/fileUpload.do?isCheck=false");
						options.put("fileType", "file");
						if("05".equals(form.getCom_type_code())){
							options.put("multiple", true);
						}
						field.put("OPTIONS", options);
					}
					
				}
				fieldArr.add(field);
			}
		}
		return fieldArr;
	}

}
