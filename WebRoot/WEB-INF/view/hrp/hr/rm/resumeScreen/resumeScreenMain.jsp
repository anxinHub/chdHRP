<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />   
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<script src="<%=path%>/lib/et_assets/common.js"></script>
<script src="<%=path%>/lib/et_assets/hr/common.js"></script>
<script src="<%=path%>/lib/hrp/hr/hr.js"></script>
<script src="<%=path%>/lib/et_components/et_plugins/etUpload.min.js"></script>
<script type="text/javascript">
	var dataFormat;
	var grid; 
	var gridManager = null; 
	var cardgrid;
	var cardgridManager = null;
	var userUpdateStr;
	var fileUpload;
	$(function() {
		$("#layout1").ligerLayout({
			bottomHeight  : '270',
			heightDiff: 5
		});
		
		loadHead(null);
		loadHotkeys();
	});

	function this_close() {
		frameElement.dialog.close();
	}

	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.loadData(grid.where);
	}
	function queryCard(demand_id) {
		cardgrid.options.parms = [];
		cardgrid.options.newPage = 1;
		cardgrid.options.parms.push({
			name : 'demand_id',
			value : demand_id
		});
		
		cardgrid.loadData(cardgrid.where);
	}
	
	

	function loadHead() {
		
		grid = $("#maingrid").ligerGrid({
					columns : [{
									display : '招聘科室',
									name : 'dept_id_name',
									align : 'left'
								},
								{
									display : '招聘岗位',
									name : 'station_name',
									align : 'left'
								},
								{
									display : '招聘人数',
									name : 'demand_num',
									align : 'left'
								},
								{
									display : '任职要求',
									name : 'demand_require',
									align : 'left'
								},
								{
									display : '任职资格',
									name : 'demand_qualify',
									align : 'left'
								},{
									display : '性别',
									name : 'demand_sex_name',
									align : 'left',
								},
								{
									display : '学历',
									name : 'demand_edu_name',
									align : 'left'
								},
								{
									display : '招聘主题',
									name : 'theme_name',
									align : 'left'
								}],
							dataAction : 'server',
							dataType : 'server',
							url:'queryDemandOfResume.do?tab_code=HR_RECRUIT_DEMAND&demand_state=02',
							usePager : true,
							width : '100%',
							height : '50%',
							alternatingRow : true,
							isScroll : true,
							rownumbers : true,
							heightDiff: -10,
							onDblClickRow :function(data,rowid,rowdata) {
								var demand_id = data.demand_id;
								queryCard(demand_id);
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		cardgrid = $("#cardgrid").ligerGrid({
				columns : [{
								display : '姓名',
								name : 'app_name',
								align : 'left'
							},
							{
								display : '性别',
								name : 'app_sex_name',
								align : 'left'
							},
							{
								display : '出生年月',
								name : 'app_birth',
								align : 'left'
							},
							{
								display : '民族',
								name : 'app_ethnic_name',
								align : 'left'
							},
							{
								display : '身份证',
								name : 'app_cardid',
								align : 'left'
							},
							{
								display : '学历',
								name : 'app_edu_name',
								align : 'left'
							},
							{
								display : '学位',
								name : 'app_dege_name',
								align : 'left'
							},
							{
								display : '专业方向',
								name : 'app_major_name',
								align : 'left'
							},
							{
								display : '英语水平',
								name : 'app_cet_name',
								align : 'left'
							},
							{
								display : '电子邮件',
								name : 'app_email',
								align : 'left'
							},
							{
								display : '联系电话',
								name : 'app_phone',
								align : 'left'
							},
							{
								display : '状态',
								name : 'app_state',
								align : 'left',
								render : function(rowdata, rowindex,
										value) {
									if(rowdata.app_state == '01'){
										return "在职";
									}
									if(rowdata.app_state == '02'){
										return '离职'
									}
									if(rowdata.app_state == '03'){
										return '其他情况'
									}
								}
							}
							 ],
					dataAction : 'server',
					dataType : 'server',
					url:'queryResumeRecord.do?isCheck=false&tab_code=HR_RECRUIT_RECORD&record_state=01',
					usePager : true,
					width : '100%',
					height : '97%',
					checkbox : true,
					isScroll : true,
					rownumbers : true,
					delayLoad : true,//初始化明细数据
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '通过',
							id : 'resumePass',
							click : resumePass,
							icon : 'right'
						}
						, {
							line : true
						},
// 						{
// 							text : '岗位调整',
// 							id : 'openAdjust',
// 							click : openAdjust,
// 							icon : 'settings'
// 						}, 
						{
							text : '简历导入',
							id : 'import',
							click : resumeIsExist,
							icon : 'up'
						},  {
							line : true
						},	{
 							text : '查看简历',
 							id : 'openResume',
 							click : openResume,
 							icon : 'search'
 						},
						, {
							line : true
						}, {
							text : '添加应聘人员',
							id : 'addResume',
							click : addResume,
							icon : 'add'
						},  {
							line : true
						},{
							text : '修改应聘人员信息',
							id : 'updateResume',
							click : updateResume,
							icon : 'modify'
						}, {
							line : true
						},{
							text : '简历标识',
							id : 'label',
							click : openLabel,
							icon : 'plus'
						}]}
				});
		cardgridManager = $("#cardgrid").ligerGetGridManager();
		
	}
	
  function addResume(){
	  var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择招聘岗位');
		} else {
	  		$.ligerDialog.open({url: 'recruitAppAddPage.do?isCheck=false&',data:{demand_id:data[0].demand_id}, height: 380,width: 790, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});
		}
  }	

  function openResume(){
	  var data = cardgridManager.getCheckedRows();
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.error('请选择应聘人员');
		} else {
			var fileUpload;
			var dialogIndex = $.etDialog.open({
                content: '<div id="fileUpload"></div>',
                title: '文件查看',
                width: 400,
                height: 350,
                btn: ['关闭'],
                btn1: function () {
                    $.etDialog.close(dialogIndex);
                },
                success: function () {
                	fileUpload = $('#fileUpload').etUpload({
                        type: 'file'
                    });

                    var valueStr = data[0].app_resumes;
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
  }
  function updateResume(){
	  var data = cardgridManager.getCheckedRows();
		if (data.length == 0|| data.length > 1) {
			$.ligerDialog.error('请选择一个应聘人员');
		} else {
			var param = [];
// 			$(data).each(function() {
// 					param.push({
// 						group_id: data[0].group_id,
// 						hos_id: data[0].hos_id,
// 						app_id: data[0].app_id,
// 						demand_id:data[0].demand_id
// 					});

// 			});
			var parm = "group_id="+data[0].group_id+"&hos_id="+data[0].hos_id+"&demand_id="+data[0].demand_id+"&app_id="+data[0].app_id;
			$.ligerDialog.open({ url : 'recruitAppUpdatePage.do?isCheck=false&tab_code=HR_RECRUIT_APP&'+parm, height: 380,width: 790, title:'简历修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true});
		}
    }
  function openLabel(){
	  var data = cardgridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var param = [];
			 var err ="";
			$(data).each(function() {
				if(this.app_label == null || this.app_label == ''){
					param.push({
						group_id: this.group_id,
						hos_id: this.hos_id,
						app_id: this.app_id
					});
				}else{
					if(err == ""){
							err = this.row_id;
					}else{
						err += "、"+this.row_id;
					}
				}	
			});
			 if (err != "") {
    				$.ligerDialog.warn("第["+err+"]行已经添加过简历标签！");
    				return;
    		}
// 			var param = "group_id="+group_id+"&hos_id="+hos_id+"&app_id="+app_id;
			$.ligerDialog.open({ url : 'recruitLabelMainPage.do?isCheck=false&tab_code=HR_RECRUIT_APP' ,data:param, height: 300,width: 500, title:'简历标签',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true});
		}
    }
	
	function addDict(){
		parent.$.ligerDialog.open({
			title: '新增',
			height: 350,
			width: 700,
			url: 'hrp/hr/recruitment/theme/themeAddPage.do?isCheck=false',
			modal: false, showToggle: false, showMax: false, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
	}
	
	function openAdjust(){
		  var data = cardgridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择招聘人员');
			} 
	 		else {
	 			var parm = [];
	 			$(data).each(
	 					function() {
	 						parm.push(this.group_id + "@" + this.hos_id
	 											+ "@" + this.copy_code + "@"
	 											+ this.employ_id);
	 					});
	 			$.ligerDialog.open({ url : 'recruitStationAdjustMainPage.do?isCheck=false&' + parm,data:{}, height: 200,width: 300, title:'岗位调整',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true});
	 		}
	}
	
	function resumePass(){
			
			var data = cardgridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择应聘人员');
			} else {
				var ParamVo = [];
				var theme_id;
				$(data).each(function(i) {
					theme_id  = this.theme_id
					ParamVo.push({
						record_id: this.record_id,
						demand_id: this.demand_id,
						app_id: this.app_id,
						group_id: this.group_id,
						hos_id: this.hos_id,
						record_state: '02',
						record_note: this.record_note
					});
				});
				$.ligerDialog.confirm(
								'确定通过?',
								function(yes) {
									if (yes) {
										$.post("updateRecordStage01Batch.do?isCheck=false&tab_code=HR_RECRUIT_RECORD",  {ParamVo:JSON.stringify(ParamVo)},
												   function(data,status){
													   if(data.state == "true"){
														   queryCard(this.demand_id); 
														   $.ligerDialog.success("通过成功！");
													   }else{
															$.ligerDialog.warn(data.error);
													   }
														
												 },"json");
									}
								});
			}
	
	}
	
	function resumeIsExist(){
			var dataSelect = cardgridManager.getCheckedRows();
			if (dataSelect.length == 0|| dataSelect.length > 1) {
				$.ligerDialog.error('请选择一个应聘人员');
				return ;
			}  
			if(dataSelect[0].app_resumes != null && dataSelect[0].app_resumes != ''){
				$.ligerDialog.confirm(
						'该应聘人员已经上传过简历，是否覆盖？',
						function(yes) {
							if (yes) {
								importResume(dataSelect);
							}
						});
			}else{
				importResume(dataSelect);
			}
	}
	
	function importResume(dataSelect){
		
	    var fileUpload;
         var dialogIndex = $.etDialog.open({
             content: '<div id="fileUpload"></div>',
             title: '文件上传',
             width: 400,
             height: 350,
             btn: ['上传', '关闭'],
             btn1: function () {
                 var fileValue = fileUpload.getValues();
                 var formData = new FormData();
				 var ParamVo = [];
                 fileValue.forEach((file) => {formData.append('files', file, file.name);});

                 ajaxPostFormData({
                     url: '../../fileUpload.do?isCheck=false',
                     data: formData,
                     success: function (res) {
                         if(res.data && Array.isArray(res.data)){
                        	 //业务处理，返回值是json数据，直接把这个数组保存到数据库字段里
                        	 ParamVo.push({
								app_id: dataSelect[0].app_id,
								group_id: dataSelect[0].group_id,
								hos_id: dataSelect[0].hos_id,
								app_resumes : res.data,
							});
                        	 $.post("updateRecruitApp.do?isCheck=false&tab_code=HR_RECRUIT_APP",  {ParamVo:JSON.stringify(ParamVo)},
							   function(data,status){
								   if(data.state == "true"){
									   queryCard(dataSelect[0].demand_id); 
									   $.etDialog.success('上传成功！');
									   $.etDialog.close(dialogIndex);
								   }else{
									   $.etDialog.warn("上传失败！");
								   }
									
							 },"json");
                         }
                     }
                 });
             },
             success: function () {
                 fileUpload = $('#fileUpload').etUpload({
                     type: 'file',
                     multiple: true
                 });
             }
         });
	}
	
	var rowindex_id = "";
	var column_name = "";


	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
// 		hotkeys('A', save);
// 		hotkeys('D', unselect);

	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="layout1">
		<div position="center" title="招聘岗位">
				<div id="maingrid"></div>
		</div>
		<div position="bottom" title="应聘人员">
				<div id="cardgrid"></div>
		</div>
	</div>
</body>
</html>
