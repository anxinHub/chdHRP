<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript" charset="utf-8"
	src="<%=path%>/lib/build/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=path%>/lib/build/ueditor.all.min.js">
	
</script>
<script type="text/javascript" charset="utf-8"
	src="<%=path%>/lib/build/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
var ue = UE.getEditor('summary', {
	autoHeightEnabled : false,
	autoFloatEnabled : false,
	toolbars : [

	]
});
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		
		
		toolbar();
		loadHotkeys();
		

		$("#acc_year_month").ligerTextBox({
			width : 160
		});
		
		$("#goal_code").ligerTextBox({
			width : 160
		});
		$("#dept_code").ligerTextBox({
			width : 160
		});
		$("#emp_code").ligerTextBox({
			width : 160
		});
		$("#hos_id").ligerTextBox({
			width : 160
		});
		$("#summary").ligerTextBox({
			width : 1350,
			height : 220
		});
		$("#layout1").ligerLayout({
			centerBottomHeight : 180
		});
		
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name:'acc_year',value:$("#acc_year_month").val().substring(0,4)}); 
    	  grid.options.parms.push({name:'acc_month',value:$("#acc_year_month").val().substring(4,6)}); 
    	  
    	  grid.options.parms.push({name:'hos_id',value:liger.get("hos_id").getValue()}); 
    	  grid.options.parms.push({name:'emp_id',value:liger.get("emp_id").getValue().split(".")[0]}); 
    	  grid.options.parms.push({name:'emp_no',value:liger.get("emp_id").getValue().split(".")[1] == null?'':liger.get("emp_id").getValue().split(".")[1]}); 
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(".")[0]}); 
    	  grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split(".")[1] == null?'':liger.get("dept_id").getValue().split(".")[1]}); 
    	  grid.options.parms.push({name:'goal_code',value:liger.get("goal_code").getValue()}); 

		//加载查询条件
		grid.loadData(grid.where);
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns: [ 
	                     { display: '考核年度', name: 'acc_year', align: 'left'
						 		},
	                     { display: '考核期间', name: 'acc_month', align: 'left'
						 		},
	                     { display: '科室分类', name: 'dept_kind_name', align: 'left'
						 		},
	                     { display: '科室编码', name: 'dept_code', align: 'left'
						 		},
	                     { display: '科室名称', name: 'dept_name', align: 'left'
						 		},
	                     { display: '绩效得分', name: 'kpi_score', align: 'right',
			                 		render : function(rowdata, rowindex,
											value) {
											return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
											rowdata.hos_id   + "|" + 
											rowdata.copy_code   + "|" + 
											rowdata.acc_year  + "|" + 
											rowdata.acc_month  + "|" + 
											rowdata.goal_code   + "|" + 
											rowdata.dept_id   + "|" + 
											rowdata.dept_no+"')>"+rowdata.kpi_score+"</a>";
									}
						 		},
								 { display: '指示灯', name: 'led', align: 'center',width:'150',
			                    	 render : function(rowdata, rowindex, value) {
			                    		 
			                    		 if(rowdata.led_path == null || rowdata.led_path == ''){
			                    			 return '';
			                    		 }
			                    		 
			 							return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata.led_path+"' border='0' width ='50px' />";
			 						}
							   }
	                     ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryPrmEmpKpiSummary.do',
					width : '100%',
					height : '65%',
					checkbox : true,
					rownumbers : true,
					selectRowButtonOnly : true//heightDiff: -10,
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({text : '打印（<u>P</u>）',id : 'print',click : print,icon : 'print'});
       	obj.push({ line:true });
       	
       	obj.push({text : '总结（<u>A</u>）',id : 'add',click : addEmpKpiSummary,icon : 'edit'});
       	obj.push({ line:true });
       	
       	obj.push({text : '反馈（<u>B</u>）',id : 'add',click : addEmpKpiSummary,icon : 'settle'});
       	obj.push({ line:true });
       	
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
	
	
	function openUpdate(obj){
		var vo = obj.split("|");
		var parm = "&group_id=" + vo[0] + "&hos_id=" + vo[1] + "&copy_code="
				+ vo[2] + "&acc_year=" + vo[3]+"&acc_month="+vo[4]+"&goal_code="+vo[5]+"&emp_id="+vo[6]+"&emp_no="+vo[7];
		parent.$.ligerDialog.open({
			url : 'hrp/prm/prmempkpiscore/prmEmpKpiScoreSchemeMainPage.do?isCheck=false&' + parm,
			data : {},
			height: $(window).height(),
			width: $(window).width(),
			title : '',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,
			buttons : [ {
				text : '关闭',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
		
	}
	

	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('P', print);

		hotkeys('A', addEmpKpiSummary);

		hotkeys('B', addEmpKpiSummary);

	}

	function addEmpKpiSummary() {

		var data = gridManager.getCheckedRows();
		var summary = UE.getEditor('summary').getContentTxt();
		if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(
									this.group_id + "@" + 
									this.hos_id + "@" + 
									this.copy_code + "@" + 
									this.goal_code + "@" + 
									this.acc_year + "@" + 
									this.acc_month + "@" + 
									this.emp_no + "@" + 
									this.emp_id + "@" + 
									this.kpi_score + "@" + 
									summary
								)
					});

			ajaxJsonObjectByUrl("addPrmEmpKpiSummary.do", {ParamVo : ParamVo}, function(responseData) {
				
				if (responseData.state == "true") {
					query();
				}
			});

		}
	}

	function loadDict() {
		//字典下拉框
	   autocompleteAsync("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",false,"",false);
        
       autocompleteAsync("#emp_id","../queryHosEmpDict.do?isCheck=false","id","text",true,true,"",false,null);
	   	       
 	   autocompleteAsync("#dept_id","../queryPrmDeptDict.do?isCheck=false","id","text",true,true,"",false);
	        
 	   autocompleteAsync("#goal_code","../quertPrmGoalDict.do?isCheck=false","id","text",true,true,"",false,"","400");
		
	}
	
	//打印
    function print() {

		if (grid.getData().length == 0) {
			$.ligerDialog.warn("请先查询数据！");
			return;
		}

		/* var heads={
		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  "rows": [
	      {"cell":0,"value":"科室名称："+liger.get("dept_id").getText(),"colSpan":"5"},
	      {"cell":3,"value":"项目名称："+liger.get("proj_id").getText(),"from":"right","align":"right","colSpan":"4"}
			  ]
		}; */
	
		var printPara={
				title: "职工绩效考评总结",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.prm.service.PrmEmpKpiScoreService",
				method_name: "queryPrmEmpKpiScoreEmpPrint",
				bean_name: "prmEmpKpiScoreService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">单位名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="hos_id" type="text" id="hos_id" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="emp_id" type="text" id="emp_id" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">考核期间：</td>
			<td align="left" class="l-table-edit-td">
				<input name="acc_year_month" type="text" id="acc_year_month" ltype="text" class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" />
			</td>
			<!-- <td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">考核期间：</td>
			<td align="left" class="l-table-edit-td">
				<input name="acc_month" type="text" id="acc_month" ltype="text" class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM',minDate:'2016-01-01'})" />
			</td> -->
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">目标名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="goal_code" type="text" id="goal_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
		</tr>
	</table>
	
	<div id="layout1">
		<div position="center" title="">
			<div id="toptoolbar"></div>
			<div id="maingrid"></div>
		</div>
		<div position="centerbottom" title="总结">
			<textarea rows="14" cols="220" class="liger-textarea" id="summary"
				name="summary" validate="{required:true}"></textarea>
		</div>
	</div>
</body>
</html>
