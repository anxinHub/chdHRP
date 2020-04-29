<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style = "overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<script src="<%=path%>/lib/hrp/hr/hr.js" type="text/javascript"></script> 
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<script src="<%=path%>/lib/et_assets/common.js"></script>
<script src="<%=path%>/lib/et_assets/hr/common.js"></script>
<script src="<%=path%>/lib/et_components/et_plugins/etUpload.min.js"></script>

<script type="text/javascript">
	var grid;
	var gridManager=[];
	
	$(function(){
		// 初始化 员工 下拉框
		loadDict();
		
		//初始化界面
		loadHead(null);
 		$("#emp_id").ligerTextBox({ width:160 });
/*		$("#em_name").ligerTextBox({ width:160}); */
	})
	
	//初始化 下拉搜索  员工下拉框
	function loadDict(){
		autocomplete("#emp_id","../../baseSelect.do?isCheck=false&field_tab_code=HOS_EMP", "id", "text",true,true,"",false,"",180);
	}
	
	//初始化界面
	function loadHead(){
		//自动获取数据库表列
		var columns = getGridColumns({ ui:'liger', group_id:${group_id}, hos_id:${hos_id},gridTables:['HR_NEWPERSONENTRY_AUDIT'], design:'queryNewPersonEntryAudit.do' });
		grid = $("#maingrid").ligerGrid({
			columns: columns,
			dataAction: 'server', dataType: 'server', delayLoad: false,isScroll: true,
			url: 'queryNewPersonEntryAudit.do',
			width: '100%', height: '100%', checkbox: true, rownumbers: true, usePage: true,
			selectRowButtonOnly: true,
			toolbar: {
				items:[
					{ text: '查询', id: 'search', click: query, icon: 'search' },
					{ line: true },
					{ text: '新增', id: 'add', click: add, icon: 'add' },
					{ line: true },
					/* { text: '修改', id: 'edit', click: edit, icon: 'edit' },
					{ line: true }, */
					{ text: '删除', id: 'delete', click: remove, icon: 'delete' },
					{ line: true },
					{ text: '提交', id: 'submit', click: submit, icon: 'audit' },
					{ line: true },
					{ text: '上传合同附件', id: 'uploadContractFile', click: uploadContractFile, icon: 'up' },
					{ line: true },
					{ text: '查看合同附件', id: 'openContractFile', click: openContractFile, icon: 'communication' },
					{ line: true },
					{ text: '批量导入', id: 'impDate', click: impDate, icon: 'up' },
					{ line: true },
					{ text: '用人部门审核', id: 'employDeptAudit',click: employDeptAudit,icon: 'audit',hide:'${updateNewPersonEntryAuditEmpDepBatch}'==""?true:false },
					//{ line: true },
					{ text: '分管副院长审核', id: 'viceDeanAudit',click: viceDeanAudit,icon: 'audit',hide:'${updateNewPersonEntryAuditViceDeanBatch}'==""?true:false },
					//{ line: true },
					{ text: '人力资源审核', id: 'hrAudit',click: hrAudit,icon: 'audit',hide:'${updateNewPersonEntryAuditHRBatch}'==""?true:false},
					//{ line: true },
					{ text: '院长审核', id: 'deanAudit',click: deanAudit,icon: 'audit',hide:'${updateNewPersonEntryAuditDeanBatch}'==""?true:false},
					//{ line: true },
					{ text: '审核模板设置', id: 'printModual', click: printSet, icon: 'settings'},
					{ line: true },
					{ text: '打印审核模板', id: 'printModual', click: printModual, icon: 'print'}
				]
			},
			//选中行，用于修改
			onDblClickRow: function(data, rowid, rowdata){
				openUpdate(data);
			}
		});
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
/* 	//绑定按钮 点击
	function itemClick(item){
		if(item.id){
			switch(item.id){
				case 'add':
					alert("新增");
					return;
				case 'edit':
					return;
				case 'delete':
					return;
				case 'submit':
					return;
				
			}
		}
	} */
	
	//查询
	function query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		//添加查询条件
		grid.options.parms.push({ name: 'emp_id', value: liger.get("emp_id").getValue() }); //getValue()取 id。getText()取文本，无法获取自己输入的值
		grid.options.parms.push({ name: 'emp_name', value: liger.get("emp_id").getText() });  //这种value方式能使用自己输入的
		
		//加载已有查询条件
		grid.loadData(grid.where);
	}

	//新增
	function add(){  
		$.ligerDialog.open({url: 'newPersonEntryAuditAddPage.do?isCheck=false',height:300, width:700,title:'新增',modal:true,
							showToggle:false,showMax:false,showMin:false,isResize:true});
	}
	
/* 	//修改
	function edit(){
		// 修改   要传递  表名 才可以。
		//$.ligerDialog.open({ url: 'newpersonentryAuditUpdatePage.do?isCheck=false&tab_code=HR_NEWPERSONENTRY_AUDIT', height:300, width: 700, title:'修改', modal: true,
		//					showToggle:false,showMax:false,showMin:false,isResize:true});
 		 $.ligerDialog.open({
			url:'hrNewPersonEntryAuditUpdatePage.do?isCheck=false' + '&tab_code=HR_NEWPERSONENTRY_AUDIT',
			title:'修改',
			height:300,
			width:630,
			button:[
				{ text: '保存', onclick:function(item, dialog){
						dialog.frame.saveData();
					},
					cls: 'l-dialog-btn-highlight'
				},
				{ text: '取消', onclick: function(){
						dialog.close();
					}
				}
			]
		});  
	}
*/
	function openUpdate(data){
		// 已经提交的 不允许修改
		if(data.submit_state == 1){
			$.ligerDialog.warn("该记录已经提交，不允许修改！如需，请联系管理员。");
			return;
		}
 		var parm = "&group_id=" + data.group_id +
			 	   "&hos_id=" + data.hos_id +
			 	   "&emp_id=" + data.emp_id +
			 	   "&emp_name=" + data.emp_name +
			 	   "&tab_code=" + "HR_NEWPERSONENTRY_AUDIT";
		$.ligerDialog.open({   
			url: 'newpersonentryAuditUpdatePage.do?isCheck=false'+ parm,
			title: '修改',
			width: 700,
			height: 300,
            buttons: [ 
            	{ text: '确定', onclick: function (item, dialog) { dialog.frame.saveDate();},cls:'l-dialog-btn-highlight' }, 
            	{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]
		});
	}
	
	//删除
	function remove(){
		var data = gridManager.getCheckedRows();
		if(data.length==0){
			$.ligerDialog.error('请选择您需要删除的行');
			return;
		}
		var paramVo = [];
		var err="";
		// 批量删除遍历
		$(data).each(function (){
			paramVo.push({
				group_id: this.group_id,
				hos_id: this.hos_id,
				emp_id: this.emp_id,
				emp_name: this.emp_name,
				tab_code: 'HR_NEWPERSONENTRY_AUDIT'
			});
		});
		//删除
		$.ligerDialog.confirm('您确定要删除这些数据吗？', function(yes){
			if(yes){
				ajaxJsonObjectByUrl('deleteNewpersonentryAudit.do?isCheck=false&tab_code=HR_NEWPERSONENTRY_AUDIT', {paramVo: JSON.stringify(paramVo)},function(responseDate){
					if(responseDate.state == "true"){
						//$.ligerDialog.success("删除成功！");
						query();
					}
					else{
						$.ligerDialog.error("删除信息出现错误，请联系管理员。错误代码: deleteNewPersonEntryAudit");
					}
				});
			}
		});
	}
	
	//提交
	function submit(){
		var data =gridManager.getCheckedRows();
		if(data.length == 0){
			$.ligerDialog.error("请选择您需要提交申请的行！");
			return;
		}
		var paramVo=[];
		var err = "";
		var todayDate = new Date();
		
		// 多行  遍历 提交
		$(data).each(function (){
			//是否提交了附件
			if(this.upload_contract_file==null || this.upload_contract_file.indexOf("url")<0){
				$.ligerDialog.error("第["+ this.row_id + "]行,合同附件未上传，不允许提交！");
				return;
			}
			//是否已经提交过
			if(this.submit_state != 1){
				paramVo.push({
					emp_id:this.emp_id,
					emp_name: this.emp_name,
					submit_state: 1,
					submit_date: todayDate.toJSON().split(' ')[0]
				});
			}
			else{
				if(err == ""){
					err = this.row_id;
				}
				else{
					err = err + "、" + this.row_id;
				}
			}
		});
		
		if(err!=""){
			$.ligerDialog.warn("第["+ err + "]行入职记录，已经提交过，不可重复提交！");
			return;
		}
		//如果选中的 有未提交的
		if(paramVo.length>0){
			$.ligerDialog.confirm("已确认信息无误，确认提交?", function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateNewpersonentryAuditSubBatch.do?isCheck=false&tab_code=HR_NEWPERSONENTRY_AUDIT",
							{ParamVo: JSON.stringify(paramVo)},
							function(responseData){
								if(responseData.state == "true"){
									query();
								}
							});
				}
			});
		}
	}
	
	// 上传合同附件
	function uploadContractFile(){
		var dataSelect = grid.getCheckedRows();
		if(dataSelect.length != 1){
			$.ligerDialog.error("您需要选中且只允许选中一条记录，方可上传！");
			return;
		}
		if(dataSelect[0].upload_contract_file != null && dataSelect[0].upload_contract_file != ''){
			$.ligerDialog.confirm(
				'该员工附件已经存在，是否要覆盖更新？',
				function(yes){
					if(yes){
						// 更新附件
						importContract(dataSelect);
					}
				}
			);
		}
		else{
			//直接上传
			importContract(dataSelect);
		}
	}
	
	// 实际上传
	function importContract(dataSelect){
		var fileUpload;
		var dialogIndex = $.etDialog.open({
			content: '<div id = "fileUpload"></div>',
			title: '上传合同附件',
			width: 400,
			height:360,
			btn: ['上传','取消'],
			btn1: function(){ //上传
				var fileValue = fileUpload.getValues();
				var formData = new FormData();
				var ParamVo = [];
				//fileValue.forEach((file) => {formData.append('files', file, file.name);});
				// 多文件，循环添加到 formData 中
				fileValue.forEach(
					function(file){
						formData.append('files',file,file.name);
					    //console.log(formData.get('files'));   //输出 参数 查看
					}
				);
				ajaxPostFormData({
					url: '../../fileUpload.do?isCheck=false',
					data: formData,
					success: function(res){
						if(res.data && Array.isArray(res.data)){
							// 业务处理，返回json格式，直接将数组保存到 数据库字段中。
							$(dataSelect).each(function(){
								//var rowdata = this;
								//rowdata.upload_contract_file = res.data;
								//ParamVo.push(rowdata);
								ParamVo.push({
									emp_id: dataSelect[0].emp_id,
									emp_name: dataSelect[0].emp_name,
									upload_contract_file: res.data
								});
							});
							// 如果格式正确 更新库 附件字段
							if(ParamVo.length>0){
								$.post("updateNewpersonentryAuditContract.do?isCheck=false&tab_code=HR_NEWPERSONENTRY_AUDIT",{ParamVo: JSON.stringify(ParamVo)}, 
										function (data, status){
											if(data.state == "true"){
												query();
												$.etDialog.success("上传成功!");
												$.etDialog.close(dialogIndex);
											}
											else{
												$.etDialog.warn("上传失败!");
											}
										},
										"json"
								);
							}	
						}
					}
				});
			},
			//btn2:function(){}, //取消不用编辑，自动的
			success: function (){
				fileUpload = $("#fileUpload").etUpload({
					type: 'file',
					multiple: true
				});
			}
			
		});
	}
	// 查看附件
	function openContractFile(){
		var data = grid.getCheckedRows();
		if(data.length != 1){
			$.ligerDialog.error("您需要选中且只允许选中一行记录，查看附件！");
			return;
		}
		var fileUpload;
		var dialogIndex = $.etDialog.open({
			content: '<div id="fileUpload"></div>',
			title: '查看附件',
			width: 400,
			height: 350,
			btn: ['关闭'],
			btn1: function(){
				$.etDialog.close(dialogIndex);
			},
			success: function(){
				fileUpload = $('#fileUpload').etUpload({
                    type: 'file'
                });

                var valueStr = data[0].upload_contract_file;
                if (valueStr && Array.isArray(JSON.parse(valueStr))) {
                    var fileArr = [];
                    JSON.parse(valueStr).forEach((item) => {
                        fileArr.push(item.url);
                    });
                    fileUpload.setValues(fileArr);
                }
			}
		});
	}
	
	// 批量导入 impDate
	function impDate(){
 		$.ligerDialog.open({
			url: "newpersonentryAuditImprotPage.do?isCheck=false&tab_code=" + "HR_NEWPERSONENTRY_AUDIT" + "&ui=" + "liger",
			parentframename: window.name,
			height: $(window).height(),
			width: $(window).width()
		}); 
	}
	
	//用工部门审核
	function employDeptAudit(){
		var data = gridManager.getCheckedRows();
		if(data.length == 0){
			$.ligerDialog.error("请选中需要审核的数据行！");
			return;
		}
		var paramVo = [];
		var err ="";
		var todayDate = new Date();
		// 多行
		$(data).each(function (){
			if(this.submit_state == 1 && this.employ_dept_audit != 1){
				paramVo.push({
					emp_id: this.emp_id,
					emp_name: this.emp_name,
					employ_dept_audit_date: todayDate.toJSON().split(' ')[0],
					audit_flag: "emp_audit"
				});
			}
			else{
				if(err==""){
					err = this.row_id;
				}
				else{
					err += "、" + this.row_id;
				}
			}
		});
		if(err!=""){
			$.ligerDialog.warn("第[" + err + "]行未提交或已经审核，审核失败！");
			return;
		}
		//如果选中的 有未审核的
		if(paramVo.length>0){
			$.ligerDialog.confirm("已确认信息无误，确认审核?", function (yes){
				if(yes){
					$.ligerDialog.open({
						url: 'newPersonEntryAuditMainPage.do?isCheck=false&',
						data: paramVo,
						title: '审核',
						width: 450,
						height:200,
						modal: true,
						showToggle: false,
						showMax: false,
						showMin: false,
						isResize: false,
						buttons:[
							{ text: '确定', onclick: function(item, dialog){ dialog.frame.saveAudit();}, cls: 'l-dialog-btn-highlight'},
							{ text: '取消', onclick: function(item, dialog){dialog.close();}}
						]
					});
				}
			});
		}
	}
	
	//分管副院长审核
	function viceDeanAudit(){
		var data = gridManager.getCheckedRows();
		if(data.length == 0){
			$.ligerDialog.error("请选中需要审核的数据行！");
			return;
		}
		var paramVo = [];
		var err ="";
		var todayDate = new Date();
		// 多行
		$(data).each(function (){
			if(this.submit_state == 1 && this.employ_dept_audit == 1 && this.vice_dean_audit != 1){
				paramVo.push({
					emp_id: this.emp_id,
					emp_name: this.emp_name,
					vice_dean_audit_date: todayDate.toJSON().split(' ')[0],
					audit_flag: "Vice_Dean_audit"
				});
			}
			else{
				if(err==""){
					err = this.row_id;
				}
				else{
					err += "、" + this.row_id;
				}
			}
		});
		if(err!=""){
			$.ligerDialog.warn("第[" + err + "]行未提交或不允许越级审核或已经审核，审核失败！");
			return;
		}
		//如果选中的 有未审核的
		if(paramVo.length>0){
			$.ligerDialog.open({
				url: 'newPersonEntryAuditMainPage.do?isCheck=false&',
				data: paramVo,
				title: '审核',
				width: 450,
				height:200,
				modal: true,
				showToggle: false,
				showMax: false,
				showMin: false,
				isResize: false,
				buttons:[
					{ text: '确定', onclick: function(item, dialog){ dialog.frame.saveAudit();}, cls: 'l-dialog-btn-highlight'},
					{ text: '取消', onclick: function(item, dialog){dialog.close();}}
				]
			});
		}
	}
	
	//人力资源审核
	function hrAudit(){
		var data = gridManager.getCheckedRows();
		if(data.length == 0){
			$.ligerDialog.error("请选中需要审核的数据行！");
			return;
		}
		var paramVo = [];
		var err ="";
		var todayDate = new Date();
		// 多行
		$(data).each(function (){
			if(this.submit_state == 1 && this.employ_dept_audit == 1 && this.vice_dean_audit == 1 && this.hr_audit != 1){
				paramVo.push({
					emp_id: this.emp_id,
					emp_name: this.emp_name,
					hr_audit_date: todayDate.toJSON().split(' ')[0],
					audit_flag: "HR_audit"
				});
			}
			else{
				if(err==""){
					err = this.row_id;
				}
				else{
					err += "、" + this.row_id;
				}
			}
		});
		if(err!=""){
			$.ligerDialog.warn("第[" + err + "]行未提交或不允许越级审核或已经审核，审核失败！");
			return;
		}
		//如果选中的 有未审核的
		if(paramVo.length>0){
			$.ligerDialog.open({
				url: 'newPersonEntryAuditMainPage.do?isCheck=false&',
				data: paramVo,
				title: '审核',
				width: 450,
				height:200,
				modal: true,
				showToggle: false,
				showMax: false,
				showMin: false,
				isResize: false,
				buttons:[
					{ text: '确定', onclick: function(item, dialog){ dialog.frame.saveAudit();}, cls: 'l-dialog-btn-highlight'},
					{ text: '取消', onclick: function(item, dialog){dialog.close();}}
				]
			});
		}
	}
	
	//院长审核
	function deanAudit(){
		var data = gridManager.getCheckedRows();
		if(data.length == 0){
			$.ligerDialog.error("请选中需要审核的数据行！");
			return;
		}
		var paramVo = [];
		var err ="";
		var todayDate = new Date();
		// 多行
		$(data).each(function (){
			if(this.submit_state == 1 && this.employ_dept_audit == 1 && this.vice_dean_audit == 1 && this.hr_audit == 1 && this.dean_audit != 1){
				paramVo.push({
					emp_id: this.emp_id,
					emp_name: this.emp_name,
					dean_audit_date: todayDate.toJSON().split(' ')[0],
					audit_flag: "Dean_audit"
				});
			}
			else{
				if(err==""){
					err = this.row_id;
				}
				else{
					err += "、" + this.row_id;
				}
			}
		});
		if(err!=""){
			$.ligerDialog.warn("第[" + err + "]行未提交或不允许越级审核或已经审核，审核失败！");
			return;
		}
		//如果选中的 有未审核的
		if(paramVo.length>0){
			$.ligerDialog.open({
				url: 'newPersonEntryAuditMainPage.do?isCheck=false&',
				data: paramVo,
				title: '审核',
				width: 450,
				height:200,
				modal: true,
				showToggle: false,
				showMax: false,
				showMin: false,
				isResize: false,
				buttons:[
					{ text: '确定', onclick: function(item, dialog){ dialog.frame.saveAudit();}, cls: 'l-dialog-btn-highlight'},
					{ text: '取消', onclick: function(item, dialog){dialog.close();}}
				]
			});
		}
	}
	
	// 打印模板设置
	function printSet(){
		// use_id ( 0 统一打印，1 按用户 2 按仓库 3 按供应商)
		officeFormTemplate({template_code: "06101",use_id: 0});
	}
	
	// 模板打印
	function printModual(){
		var params = {
			template_code: '06101', // 必填
			use_id: 0,
			design_code: 'queryNewPersonEntryAudit.do', // 必填，查询设计器 编码
			//id_column: '',  // 默认 'emp_id'
		};
		// 查询 条件
		$.each(grid.options.parms, function(i, obj){
			params[obj.name] = obj.value;
		});
		// 统一调用打印
		hrTemplatePrint(params);
	}
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
<!-- 		<tr>
			<td align="right" class="l-table-edit" style="padding-left: 20px;">职工ID：</td>
			<td align="left" class="l-table-edit" >
				<input name="emp_id" type="text" id="emp_id" class="liger-textbox" validate="{required: true, maxlength: 20}">
			</td>
			<td align="left" style="width:20px;"></td>
			<td align="right" class="l-table-edit" >职工姓名：</td>
			<td align="left" class="l-table-edit" >
				<input name="emp_name" type="text" id="emp_name" class="liger-textbox" validate="{requirde: true, maxlength: 20}">
			</td>
			<td align="left"></td>
		    </tr> 
-->
    <!-- 使用 baseselect  下拉，支持拼音码/五笔码 -->
    	<tr>
    		<td align="right" class="l-table-edit-td" style="padding-left: 20px;">新入职人员：</td>
    		<td align="left" class= "l-table-edit-td">
    			<input name="emp_id" type="text" id="emp_id" ltype="text" />
    		</td>
    		<td align="left"></td>
    	</tr>
	</table>
	
	<div id="maingrid"></div>
</body>
</html>