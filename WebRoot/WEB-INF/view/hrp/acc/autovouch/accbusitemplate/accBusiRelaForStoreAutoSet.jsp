<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
    <jsp:param value="dialog,select,validate,grid,form,checkbox" name="plugins" />
</jsp:include>
</head>
<script type="text/javascript">
	var parentFrameName = parent.$.etDialog.parentFrameName; // 拿取window名
	var parentWindow = parent.window[parentFrameName]; // 当前页拿取window对象
	var accBusiRela = parentWindow.accBusiRela;
	var p_level_field = "${level_field}";
	
	var $level_field, $is_last, $is_set;
	var subj_codes = [];
	var subjCodeObj = {};
	var dataAll;
	var saveObj= {};
	var editData;
	var jsonHead;
	var headData = {};
	$(function () {
		initDict();
		relaGrid();
		query();
	});

	function initDict() {
		//类别级次
		if(p_level_field !=''){
			var level_field_data =  "[";
			for(var i=1;i<parseInt(p_level_field)+1;i++){
				level_field_data =level_field_data +"{text: '"+i+"', id: "+i+"},";
			}
			level_field_data = level_field_data.substring(0,level_field_data.length-1);
			if(level_field_data.length>0){
				level_field_data = level_field_data+"]";
			}
			$level_field = $("#level_field").etSelect({
			    options: eval(level_field_data),
			    defaultValue: "none"
			});
		}else{
			$level_field = $("#level_field").etSelect({
			    options: [],
			    defaultValue: "none",
			});
			$level_field.disabled();
		}
		$is_last = $("#is_last").etSelect({
			options: [{ id: "1", text: "是" }], 
			defaultValue: "none"
		})
		$is_set = $("#is_set").etSelect({
			options: [{ id: "1", text: "是" }], 
			defaultValue: "none"
		})
		
		ajaxPostData({
			url: 'queryAccSubjForAutoSet.do?isCheck=false',
			async: false, 
			data: {
				acc_year: accBusiRela.acc_year, 
				kind_code: accBusiRela.kind_code, 
				where_sql: accBusiRela.where_sql
			}, 
			success: function (data) {
				$.each(data.Rows, function(){
					subj_codes.push(
						{"label": this.subj_name, "id": this.subj_code, "text": this.subj_name}
					);
					subjCodeObj[this.subj_code] = this.subj_name;
				})
			}
		})
	};
	
	var query = function () {
		var params = [ {
			name: 'table_id', value: accBusiRela.table_id
		}, {
			name: 'meta_code', value: accBusiRela.meta_code
		}, {
			name: 'mod_code', value: accBusiRela.mod_code
		}, {
			name: 'acc_year', value: accBusiRela.acc_year
		}, {
			name: 'is_store', value: accBusiRela.is_store
		}, {
			name: 'is_resource', value: accBusiRela.is_resource
		}, {
			name: 'code_field', value: $("#code_field").val() 
		}, {
			name: 'is_last', value: $is_last.getValue() 
		}, {
			name: 'is_set', value: $is_set.getValue() 
		} ];
		
		if(p_level_field != ''){
			params.push({name: 'level_field', value : $level_field.getValue()});
		}

    	//重新查询后对象应为空
    	saveObj= {};
    	//查询
        relaGrid.loadData(params, "queryAccBusiRelaForStoreAutoSet.do?isCheck=false");
    };
    
	/* 生成仓库列 */
	var getStoreColumns = function() {
		var columns = [];
		var whereSql = "";
		if(accBusiRela.meta_code.indexOf("04") == 0){
			whereSql = " and is_mat=1";
		}else if(accBusiRela.meta_code.indexOf("08") == 0){
			whereSql = " and is_med=1";
		}else if(accBusiRela.meta_code.indexOf("05") == 0){
			whereSql = " and is_ass=1";
		}
		ajaxPostData({
			url: 'queryAccBusiHosStore.do?isCheck=false&whereSql='+whereSql,
			async: false,	  
			success: function (data) {
				if(data.Rows){
					jsonHead = eval(data.Rows);
					$.each(jsonHead,function(index, v){
						if(v.store_id){
							headData[v.store_id + "_name"] = v.store_id;
							columns.push({
								display : v.store_code+v.store_name, 
								name : v.store_id + "_name", 
								width : 160,
								sortable: false, 
								editor: {
									type: 'select',
									keyField : v.store_id,
									source: subj_codes,
									change: function(event, ui) {
										editData = {
											subj_code: (ui.selected ? ui.selected.id : ""), //明细表考勤项目值
										};
									},
								}
							})
						}
					});
				}
				/* var newColumns = relaColumns.concat(columns);
				relaGrid.option('columns', newColumns);
				relaGrid.refreshView(); */
			},
		});
		
		return columns;
	}; 

	//grid表格
	var relaGrid = function() {
		var columns = [];
		var leftColumns = [ {
			display : '类别编码', name : 'code_field', width : 120, editable: false, sortable: false, 
		}, {
			display : '类别名称', name : 'name_field', width : 160, editable: false, sortable: false, 
		} ];
		
		columns = leftColumns.concat(getStoreColumns());
		
		// 基础表格参数
		var toolbar = {
			items: [ { 
				type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] 
			}, { 
				type: "button", label: '保存', icon: 'save', listeners: [{ click: saveData }] 
			}, { 
				type: "button", label: '批量设置', icon: 'edit', listeners: [{ click: openBatchEdit }] 
			}, { 
				type: "button", label: '关闭', icon: 'close', listeners: [{ click: this_close }] 
			} ]
	    };
		
		var paramObj = {	
			width : 'auto',
			height: '100%',
			usePager: false, 
			editable: true,
			inWindowHeight: '100%',
			freezeCols : 2, //冻结两列
			toolbar: toolbar,
			checkbox: true,
			columns : columns, 
			editorEnd: editorEnd, 
		};
		relaGrid = $("#mainGrid").etGrid(paramObj);
	};
	
	//单元格结束编辑事件
	function editorEnd(event, ui){
		//确定唯一值“类别编码+仓库”
		var rowIndex = ui.rowData.id_field + ui.dataIndx;
		
		var rowDetail = {
			code_field: ui.rowData.code_field, 
			type_id: ui.rowData.id_field, 
			store_id: headData[ui.dataIndx], 
			subj_code: editData ? editData.subj_code : "" 
		}
		saveObj[rowIndex] = rowDetail;
		
		editData = {};
		return true;
	} 
	
	//保存
	function saveData() {
		if (JSON.stringify(saveObj)=="{}") {
            $.etDialog.warn('未获得修改数据');
            return;
        }
		var saveObjs= [];
		for (var rowIndex in saveObj){
			var detail = saveObj[rowIndex];
			saveObjs.push({
				type_id: detail.type_id, 
				code_field: detail.code_field, 
				store_id: detail.store_id, 
				subj_code : detail.subj_code, 
			});
		}
		
		ajaxPostData({ 
			url: 'saveAccBusiMapByStore.do?isCheck=false',
			data: {
				meta_code: accBusiRela.meta_code, 
				mod_code: accBusiRela.mod_code, 
				table_id: accBusiRela.table_id, 
				acc_year: accBusiRela.acc_year, 
				'allData': JSON.stringify(saveObjs)
			},
			success: function () {
				//保存后对象应为空
				saveObj= {};
				query();
			}
		});
	}
	
	//批量设置
	var form;
	var is_first = true;
	function openBatchEdit(){
		//判断grid是否勾选
    	var checkData = relaGrid.selectGet();
		if(checkData.length == 0){
			$.etDialog.warn("请先勾选表格数据！");
            return false;
		}
		
		//第一次需构建Form表单
		if(is_first){
			//根据jsonHead动态生成Form表单元素
			var fieldItems = [];
			$.each(jsonHead, function(i, v){
				fieldItems.push({
					id: v.store_id, 
					name: v.store_code+v.store_name, 
					type: "select", 
					width: "280px", 
					labelStyle: "width: 80px;",
					iptStyle: "width: 120px;",
					place: 1, 
			        required: false, 
			        OPTIONS: {
			    	    options: subj_codes,
						defaultValue : "none",
						backEndSearch: false, 
					}, 
				});
				fieldItems.push({
					id: v.store_id+"_check", 
					name: "", 
					type: "checkbox", 
					width: "20px", 
					iptStyle: "width: 20px;",
					place: 1, 
			        required: false
				});
			});
			//构建Form表单
			form = $("#batchEditForm").etForm({
			    colNum: 2, 
			    fieldItems: fieldItems
			});
			//构建表单中的插件
			form.initWidget();
			
			is_first = false;
		}
		
		//弹出Form表单
		$.etDialog.open({
			content: $('#batchEditDiv'), 
			width: "600",
			height: $(window).height() - 50,
			title: '批量维护',
			btn: ['确定', '关闭'],
			showMax: false,
			maxmin: false,
			//zIndex: 8,
			shade: 0, //无遮罩
			btn1: function (index, el) {
				//获取Form表单数据
				var formData = form.getFormData(); //需要先构建成插件
				//解析得json对象storeJson和grid行对象rowdata
				var rowdata = {};
				var formObj = {};
				var storeSubjData = [];
				for (var [a, b] of formData.entries()) {
					if(a.indexOf("_check") != -1){
						a = a.replace(/_check/, "");
						if(b == "true"){
							rowdata[a] = b;
							rowdata[a+"_name"] = subjCodeObj[b];  //根据科目编码获取科目名称
						}
					}
					
					var obj = {};
					if(formObj[a]){
						obj = formObj[a]; 
						obj.is_check = b;
					}else{
						obj.store_id = a;
						obj.subj_code = b;
					}
					
					formObj[a] = obj;
				}
				$.each(formObj, function(i, v){
					storeSubjData.push(v);
				});
				//保存对应关系
				ajaxPostData({
					url: 'saveAccBusiMapByStoreBatch.do?isCheck=false',
					data: {
						meta_code: accBusiRela.meta_code, 
						mod_code: accBusiRela.mod_code, 
						table_id: accBusiRela.table_id, 
						acc_year: accBusiRela.acc_year, 
						storeSubjData: JSON.stringify(storeSubjData), 
						checkData: JSON.stringify(checkData)
					},
					success: function () {
						//保存后清空选项
						form.clearFormData();
						//填充grid数据（含子集）
						/* $.each(relaGrid.getAllData(), function(rindex, rdata){
							console.log(rdata);
							console.log(rdata._rowIndx);
							$.each(checkData, function(i, v){
								if(rdata.code_field.indexOf(v.rowData.code_field) == 0 ){
									relaGrid.updateRow(rdata._rowIndx, rowdata);
								}
							});
						}) */
						//填充有点问题 直接使用页面的查询功能
						query();
						//关闭弹出层
						$.etDialog.close(index);
					}
				});
			},
			btn2: function (index, el) {
				//关闭弹出层
				$.etDialog.close(index);
			},
		});
	}
	
	//关闭页面
	function this_close(){
		var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		parent.$.etDialog.close(curIndex);
	}
</script>
<body>
	<table class="table-layout">
		<tr>
			<td class="label">类别编码：</td>
			<td class="ipt">
				<input name="code_field" type="text" id="code_field" style="width:160px"/>
			</td>
			<td class="label">类别级次：</td>
			<td class="ipt">
				<select id="level_field"  style="width:160px"></select>
			</td>
			<td class="label">末级：</td>
			<td class="ipt">
				<select id="is_last"  style="width:90px"></select>
			</td>
			<td class="label">未配置：</td>
			<td class="ipt">
				<select id="is_set"  style="width:90px"></select>
			</td>
		</tr>
	</table>
	
	<div id="mainGrid"></div>
	
	<div id="batchEditDiv" style="display: none;">
		<form id="batchEditForm"></form>
		<!-- <div id="batchEditForm"></div> -->
	</div>
</body>
</html>