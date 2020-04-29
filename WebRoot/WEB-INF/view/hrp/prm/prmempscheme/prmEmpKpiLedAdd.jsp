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
    
    var clicked = 0;
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        
        toobar();
        
        loadHotkeys();

    });
    
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		grid.options.parms.push({name:'acc_year',value:'${acc_year}'}); 
		grid.loadData(grid.where);
   }

    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                { display: '等级', name: 'sec_code', align: 'center',textField: 'sec_name',
					editor : {
						type : 'select',
						valueField : 'sec_code',
						textField : 'sec_name',
						selectBoxWidth : 500,
						selectBoxHeight : 240,
						grid : {
							columns : [ {
								display : '等级代码',
								name : 'sec_code',
								align : 'center'
							}, {
								display : '等级名称',
								name : 'sec_name',
								align : 'center'
							},  {
								display : '指示灯',
								name : 'led_path',
								align : 'center',
		                    	 render : function(rowdata, rowindex, value) {
			 							return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata.led_path+"' border='0' width ='50px' />";
			 						}
							} ],
						
							switchPageSizeApplyComboBox : false,
							onSelectRow : f_onSelectRow_detail,
							url : 'queryPrmEmpLedByEditerGrid.do?isCheck=false',
							pageSize : 30
						},
						keySupport: true,
						alwayShowInDown : true,
						keySupport : true,
						autocomplete : true,
						onSuccess : function() {
							this.parent("tr").next(".l-grid-row").find(
									"td:first").focus();
						}
					}
				 		},
                { display: 'KPI起始分', name: 'kpi_beg_score', align: 'center',editor: { type: 'float' ,
					precision : 0},
             		render : function(rowdata, rowindex,
							value) {
             			if(value == 0 || value == '' || value == null){
             				return '0';
             			}else{
             				return value;
             			}
             		}
				 		},
                { display: 'KPI结束分', name: 'kpi_end_score', align: 'center',editor: { type: 'float' ,
					precision : 0},
             		render : function(rowdata, rowindex,
							value) {
             			if(value == 0 || value == '' || value == null){
             				return '0';
             			}else{
             				return value;
             			}
             		}
				 		},
				 { display: '指示灯', name: 'led', align: 'center',
	                    	 render : function(rowdata, rowindex, value) {
	                    		 
	                    		 if(rowdata.led_path == null || rowdata.led_path == ''){
	                    			 return '';
	                    		 }
	                    		 
	 							return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata.led_path+"' border='0' width ='50px' />";
	 						}
					   }
                ],
                dataAction: 'server',dataType: 'server',usePager:true,onBeforeEdit : f_onBeforeEdit,
                url:'queryPrmEmpKpiLed.do?group_id=${group_id}&hos_id=${hos_id}&copy_code=${copy_code}&acc_year=${acc_year}&acc_month=${acc_month}&goal_code=${goal_code}&kpi_code=${kpi_code}&emp_id=${emp_id}&emp_no=${emp_no}',
                width: '100%', height: '100%', checkbox: true,rownumbers:true,alternatingRow : true,
                selectRowButtonOnly:true,enabledEdit : true//heightDiff: -10,
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
	}
    
  //选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		if (column_name == "sec_code") {
			if (selectData != "" || selectData != null) {
				grid.updateRow(rowindex_id, {
					sec_code : data.sec_code,
					sec_name : data.sec_name,
					led_path : data.led_path
				});
			}
		}
		return true;
	}

  
  //键盘事件
	function loadHotkeys(){
		
		hotkeys('C',addGridRow);
 
	}
    
	function addGridRow(){ 
		
		grid.addRow();
		
    }
    
	function is_addRow(){
		setTimeout(function () { 
			//当数据为空时 默认新增一行
			var data = grid.getData();
			if (data.length==0)
				grid.addRow();
			}, 500);
		
	}
	
	
	function validateGrid() {
 		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行
		$.each(data, function(i, v) {
			var key = v.sec_name+"|"+v.led_path;
			var value = "第" + (i + 1) + "行";
			if (isnull(v.sec_name)) {
				gridManager.deleteRow(i);
				return;
			}
			if (isnull(v.sec_name)) {
				msg += "[等级]、";
			}
			
			if (msg != "") {
				msgMap.put(value+msg+"不能为空或不能为零！\n\r", "");
			}
			if (isnull(targetMap.get(key))) {
				targetMap.put(key, value);
			} else {
				msg += msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r", value);
			}

		});
		
		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (data.length == 0) {
			$.ligerDialog.warn("无数据保存");
			return false;
		}
		return true;
	} 
	
	
	
	function saveSchemeSection(){
	
		var dataSection = gridManager.rows;
		if(dataSection.length > 0){

			$.each(dataSection, function(d_index, d_content){ 
	      		  if(typeof(d_content.sec_code) == "undefined" || d_content.sec_code == ""){
	      			gridManager.deleteRow(d_content);//删除选择的行
	         		return true; 
	      		  }
	      		
			})
		}
		var formPara = {
				
				dataSection : JSON.stringify(dataSection),
				acc_year : $("#acc_year").val(),
				acc_month : $("#acc_month").val(),
				goal_code :"${goal_code}",
				emp_no : liger.get("emp_no").getValue().split(".")[1],
				emp_id : liger.get("emp_no").getValue().split(".")[0],
				kpi_code : "${kpi_code}",
		};
		if(validateGrid()){
			ajaxJsonObjectByUrl("saveEmpKpiLed.do", formPara, function(responseData) {

				if (responseData.state == "true") {
					query();
					parent.query();
				}
			});
		}
	}
	
	
	function DeleteGridRow(){
		
	     var data = gridManager.getCheckedRows();

	     if (data.length == 0){
         	$.ligerDialog.error('请选择行');
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
														this.goal_code   +"@"+ 
														this.kpi_code   +"@"+ 
														this.emp_no +"@"+ 
														this.emp_id +"@"+ 
														this.sec_code
														);
					}									
					});
             if(ParamVo == ""){
					return;
				}
             $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deletePrmEmpKpiLed.do",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             }); 
         }
	}
	
    function loadDict(){
        //字典下拉框

    	autocomplete("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",false);
        
		liger.get("hos_id").setValue("${hos_id}");
        
        liger.get("hos_id").setText("${hos_code} ${hos_name}");
    	
    	autocomplete("#emp_no","../queryHosEmpDict.do?isCheck=false","id","text",true,true,"",false);
    	
		liger.get("emp_no").setValue("${emp_id}.${emp_no}");
        
        liger.get("emp_no").setText("${emp_code} ${emp_name}");
        
		$("#hos_id").ligerTextBox({width : 160,disabled: true});
    	
    	$("#emp_no").ligerTextBox({width : 160,disabled: true});
    	
    	$("#acc_year").ligerTextBox({width : 70,disabled: true});
    	
    	$("#acc_month").ligerTextBox({width : 70,disabled: true});
    	
    	$("#kpi_code").ligerTextBox({width : 160,disabled: true});
    	
    	$("#nature_code").ligerTextBox({width : 160,disabled: true});
    	
    	$("#goal_value").ligerTextBox({width : 70,disabled: true});
    	
    	$("#ratio").ligerTextBox({width : 70,disabled: true});
	}  

    function toobar(){
    	$("#toptoolmod").ligerToolBar({ items: [
    		{ text: '添加（<u>C</u>）', id:'add', click: addGridRow, icon:'add' },
            { line:true },
    		{ text: '删除', id:'delete', click: DeleteGridRow,icon:'delete' },
			{ line:true },
    	]});
    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;"  onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	  <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">单位信息：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核单元：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_no" type="text" id="emp_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
             <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核年度：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year"  ltype="text"  value="${acc_year}"  validate="{required:true,maxlength:20}"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">考核期间：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_month" type="text" id="acc_month"  value="${acc_month}"   ltype="text" validate="{required:true,maxlength:20}"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})"/></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="kpi_code" type="text" id="kpi_code" ltype="text"  value="${kpi_name}"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">指标性质：</td>
            <td align="left" class="l-table-edit-td"><input name="nature_code" type="text" id="nature_code" ltype="text"  value="${nature_name}"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
             <td align="right" class="l-table-edit-td"  style="padding-left:5px;">目标值：</td>
            <td align="left" class="l-table-edit-td"><input name="goal_value" type="text" id="goal_value" value="${goal_value}"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:5px;">满分：</td>
            <td align="left" class="l-table-edit-td"><input name="ratio" type="text" id="ratio"  value="${full_score}" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
    </table>
	<div id="toptoolmod"></div>
	<div id="maingrid"></div>

</body>
</html>
