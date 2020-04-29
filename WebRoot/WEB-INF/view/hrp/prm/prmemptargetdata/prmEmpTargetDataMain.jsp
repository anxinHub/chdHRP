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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
    	$('body').height("100%");  //   以免拉动左边树时结构混乱
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
    	
    	toolbar();
        loadHotkeys();
        $("#acc_year").ligerTextBox({width:160});
        $("#dept_code").ligerTextBox({width:160});
        $("#is_audit").ligerComboBox({width:160 });
        $("#target_code").ligerTextBox({width:160});
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'acc_year',value:$("#acc_year").val()}); 
    	  grid.options.parms.push({name:'dept_code',value:liger.get("dept_code").getValue()}); 
    	  grid.options.parms.push({name:'emp_no',value:liger.get("emp_no").getValue().split(".")[1]}); 
    	  grid.options.parms.push({name:'target_code',value:liger.get("target_code").getValue()}); 
    	  grid.options.parms.push({name:'is_audit',value:$("#is_audit").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科室名称', name: 'dept_name', align: 'left'
	                        },
	                 { display: '职工名称', name: 'emp_name', align: 'left'
	                        },
                     { display: '指标编码', name: 'target_code', align: 'left'
					 		},
		             { display: '指标名称', name: 'target_name', align: 'left'
						   },
                     { display: '指标值', name: 'target_value', align: 'left',
	                    		editor:{type: 'float' ,precision : 4},
	                     		render : function(rowdata, rowindex,
	    								value) {
	                     			var col = arguments[arguments.length -1];
	                     			if(rowdata.is_audit == 1){
	                     				rowdata.notEidtColNames.push(col.columnname);
	                     			}
	                     			return rowdata.target_value == null || rowdata.target_value == "" ? "" : formatNumber(rowdata.target_value,2,1) ;
	                     		}
					 		},
                     { display: '审核标志', name: 'is_audit', align: 'left',
					 			 render : function(rowdata, rowindex,
											value) {
										if(rowdata.is_audit == 0){
											return "未审核";
										}else{
											return "审核"
										}
									}
					 		},
                     { display: '审核人', name: 'user_code', align: 'left'
					 		},
                     { display: '审核时间', name: 'audit_date', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmEmpTargetPrmTargetData.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,enabledEdit: true,delayLoad:true,
                     selectRowButtonOnly:true//heightDiff: -10,
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
        $(document).bind('keydown.maingrid', function(event) {
			if (event.keyCode == 13) {// enter,也可以改成9:tab

				grid.endEditToPrmNext();
			}
		});
		
		$("#maingrid").on('focus', 'input', function() {
			if (clicked != 0)
				return;
			var curdom = $(this).parent();
			if (curdom.hasClass('l-text-combobox') && !$(this).attr('readonly')) {
				var clkbutton = curdom.find('.l-trigger-icon');
				clicked = 2;
				clkbutton[0].click();
			}
		});
    }
    
    
    
  	//工具栏
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '生成（<u>C</u>）', id:'create', click: createEmpTarget, icon:'bookpen' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteEmpTarget,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click:downTemplateEmpTarget,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>O</u>）', id:'import', click: importEmpTarget,icon:'up' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '审核（<u>A</u>）', id:'audit', click:auditEmpTarget,icon:'audit' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '反审核（<u>B</u>）', id:'reAudit', click:reAuditEmpTarget,icon:'back' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '保存（<u>S</u>）', id:'save', click:saveEmpTarget,icon:'save' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	

	function loadHotkeys(){
		
		hotkeys('Q',query);
		hotkeys('P',print);
        hotkeys('C',createEmpTarget);
        hotkeys('D',deleteEmpTarget);
		hotkeys('O',importEmpTarget);
		hotkeys('T',downTemplateEmpTarget);
        hotkeys('A',auditEmpTarget);
        hotkeys('B',reAuditEmpTarget);
        hotkeys('S',saveEmpTarget);

	}
	
	function saveEmpTarget(){
		
		var data = gridManager.getUpdated();
		   
    	if(data.length==0){
    		
    		$.ligerDialog.warn('沒有数据更新');
    		
    	}else{
			var ParamVo = [];
			$(data).each(
					function() {
						var target_value = this.target_value == ""?"0":this.target_value;
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.acc_year + "@"
								+ this.acc_month + "@" + this.target_code + "@"
								+ + this.emp_no + "@" + this.emp_id + "@" + target_value)
					});

					ajaxJsonObjectByUrl("saveBatchPrmEmpTargetData.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
    	}
	}
	
	function createEmpTarget(){
		if($("#acc_year").val()==""){
     		$.ligerDialog.warn("核算年月不能为空");
     		return false;
    	}
    	
		$.ligerDialog.confirm('确定生成吗?',function(yes){
			if(yes){
				var ParamVo =[];
				ParamVo.push(
					$("#acc_year").val() 
				)
           
    			ajaxJsonObjectByUrl("createPrmEmpTargetData.do",{ParamVo : ParamVo.toString()},function (responseData){
     				if(responseData.state=="true"){
     					query();
     				}
     			});
			}
		});
    	
	}
	function deleteEmpTarget(){
		 var data = gridManager.getCheckedRows();
         if (data.length == 0){
         	$.ligerDialog.warn('请选择行');
         }else{
             var ParamVo =[];
             $(data).each(function (){					
            	 if(isnull(this.group_id)){
						gridManager.deleteSelectedRow();
					}else{
														ParamVo.push(
														this.group_id   +"@"+ 
														this.hos_id   +"@"+ 
														this.copy_code   +"@"+ 
														this.acc_year   +"@"+ 
														this.acc_month   +"@"+ 
														this.target_code   +"@"+ 
														this.emp_no   +"@"+ 
														this.emp_id 
														);
					}});
             if(ParamVo == ""){
 				return;
 			}
             $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deletePrmEmpTargetData.do",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             }); 
         }
	}
	
	
	//导入
	function importEmpTarget() {
		
		parent.$.ligerDialog.open({ url : 'hrp/prm/prmemptargetdata/prmEmpTargetDataImportPage.do?isCheck=false',
			data:{columns : grid.columns, grid : grid}, height: 300,width: 450,title:'职工指标数据采集导入',modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
		
	}
	
	function downTemplateEmpTarget(){location.href = "downTemplate.do?isCheck=false";}
	
	//审核
	function auditEmpTarget(){
		 
		var data = gridManager.getData();
		if(data.length == 0){
			$.ligerDialog.warn('未找到审核的数据 ');
			return ; 
		}
		
		
		var year_month = $('#acc_year').val();
		var param = {
			acc_year : year_month.substring(0,4),
			acc_month : year_month.substring(4,6)
		};
		
		var checkedRows = gridManager.getCheckedRows();
		if(checkedRows.length != 0){
			param['checkedRows'] = JSON.stringify(checkedRows);
		}
		
		$.ligerDialog.confirm('确定审核?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("auditPrmEmpTargetData.do", param, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
	//反审核
	function reAuditEmpTarget(){
		
		var data = gridManager.getData();
		if(data.length == 0){
			$.ligerDialog.warn('未找到反审核的数据 ');
			return ; 
		}
		
		
		var year_month = $('#acc_year').val();
		var param = {
			acc_year : year_month.substring(0,4),
			acc_month : year_month.substring(4,6)
		};
		
		var checkedRows = gridManager.getCheckedRows();
		if(checkedRows.length != 0){
			param['checkedRows'] = JSON.stringify(checkedRows);
		}
		
		$.ligerDialog.confirm('确定反审核?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("reAuditPrmEmpTargetData.do", param, function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
			}
		});
	}
	
    function loadDict(){
            //字典下拉框
    	  autocomplete("#dept_code","../queryPrmDept.do?isCheck=false","id","text",true,true,"", false);
            
    	  autocomplete("#target_code", "../quertPrmTargetDict.do?isCheck=false",
  				"id", "text", true, true, "", false, "", "300");
    	  
    	  autocomplete("#emp_no","../quertPrmEmpDict.do?isCheck=false","id","text",true,true,"", false);
    	  
    	   autodate("#acc_year","yyyymm");
         }  
    
  //打印
	function print(){
  		
  		
    	if(grid.getData().length==0){
			$.ligerDialog.warn("请先查询数据 ");
			return;
		}
    	
    	var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
    	
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
   		
    	var printPara={
       			title:'职工绩效指标数据采集',
       			head:[
    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
       			],
       			foot:[
    				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
       			],
       			columns:grid.getColumns(1),
       			headCount:1,//列头行数
       			autoFile:true,
       			type:3
       	};
    	
   		ajaxJsonObjectByUrl("queryPrmEmpTargetPrmTargetData.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});
   		
    }
  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
		      name="acc_year" type="text" id="acc_year" ltype="text"
			  onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"
			  style="width: 160px;" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工名称：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_no" type="text" id="emp_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="target_code" type="text" id="target_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核状态：</td>
               <td align="left" class="l-table-edit-td">
                                <select id="is_audit" name="is_audit" style="width: 135px;">
                                	<option value="">全部</option>
			                		<option value="0">未审核</option>
			                		<option value="1">已审核</option>
                				</select></td>
            <td align="left"></td>
        </tr> 
    </table>
	
	<div id="toptoolbar" ></div>
	<div id="maingrid"></div>
</body>
</html>
