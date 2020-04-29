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
    	$('body').height("100%");  //   以免拉动左边树时结构混乱
        loadDict()//加载下拉框
    	
    	loadHead(null);	//加载数据
    	
    	
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
        $("#acc_year").ligerTextBox({width:160});
        $("#goal_code").ligerTextBox({width:400});
    	$("#dept_kind_code").ligerTextBox({width:160});
    	$("#dept_id").ligerTextBox({
			width : 160
		});
    	$("#kpi_code").ligerTextBox({width:160});
        $("#is_audit").ligerComboBox({width:160 });
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		var acc_year = $("#acc_year").val().substring(0,4);
    		var acc_month = $("#acc_year").val().substring(4);
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({
			name : 'acc_year',
			value : acc_year
		});
		grid.options.parms.push({
			name : 'acc_month',
			value : acc_month
		});
		grid.options.parms.push({
			name : 'kpi_code',
			value : liger.get("kpi_code").getValue()
		});
		grid.options.parms.push({
			name : 'goal_code',
			value : liger.get("goal_code").getValue()
		});
		
		grid.options.parms.push({
			name : 'dept_kind_code',
			value : liger.get("dept_kind_code").getValue()
		});
		grid.options.parms.push({
			name : 'is_audit',
			value : $("#is_audit").val()
		});
		grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(".")[0]}); 
  	    grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split(".")[1] == null?'':liger.get("dept_id").getValue().split(".")[1]});

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科室名称', name: 'dept_name', align: 'left'
				 		},
                     { display: '指标编码', name: 'kpi_code', align: 'left'
					 		},
                     { display: '指标名称', name: 'kpi_name', align: 'left'
					 		},
                     { display: '取值方法', name: 'method_name', align: 'left'
					 		},
					 { display: '计算公式', name: 'formula_method_chs', align: 'left'
						  },
					 { display: '取值函数', name: 'fun_name', align: 'left'
						},
                     { display: '指标值', name: 'kpi_value', align: 'left',
							editor:{type:'text'},
							render : function(rowdata, rowindex,value) {
								  return rowdata.kpi_value == null || rowdata.kpi_value == "" ? "" : formatNumber(rowdata.kpi_value,2,1) ;
							}
					 },
                     { display: '审核状态', name: 'is_audit', align: 'left',
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
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmDeptKpiValueSchemeCalculate.do',
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
    
  	
    
    function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '生成（<u>C</u>）', id:'create', click: createDeptKpiValue, icon:'bookpen' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '计算（<u>D</u>）', id:'collect', click: collectDeptKpiValue, icon:'account' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '审核（<u>A</u>）', id:'audit', click:auditDeptKpiValue,icon:'audit' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '反审核（<u>B</u>）', id:'reAudit', click:reAuditDeptKpiValue,icon:'back' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '保存（<u>S</u>）', id:'save', click:saveDeptKpiValue,icon:'save' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
    
    
	function loadHotkeys(){
		
		hotkeys('Q',query);
		hotkeys('P',print);
        hotkeys('C',createDeptKpiValue);
        hotkeys('D',collectDeptKpiValue);
        hotkeys('A',auditDeptKpiValue);
        hotkeys('B',reAuditDeptKpiValue);
        hotkeys('S',saveDeptKpiValue);
	}
	function saveDeptKpiValue(){
		
		var data = gridManager.getUpdated();
		   
    	if(data.length==0){
    		
    		$.ligerDialog.warn('沒有数据更新');
    		
    	}else{
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.acc_year + "@"
								+ this.acc_month + "@" + this.kpi_code + "@"+this.goal_code + "@" 
								+ this.dept_no + "@" + this.dept_id + "@"+this.kpi_value + "@")
					});

					ajaxJsonObjectByUrl("saveBatchDeptKpiValueCalculate.do?isCheck=false", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
    	}
	}
	
	function createDeptKpiValue(){
		if($("#acc_year").val()==""){
     		$.ligerDialog.warn("考核期间不能为空");
     		return false;
    	}
		
		$.ligerDialog.confirm('确定生成吗?',function(yes){
			if(yes){

				var acc_year = $("#acc_year").val().substring(0,4);
				
				var acc_month = $("#acc_year").val().substring(4);
				
				var ParamVo={
					
					acc_year : acc_year,
					acc_month : acc_month
					
				}
		
		    	ajaxJsonObjectByUrl("createPrmDeptKpiValueCalculate.do",ParamVo,function (responseData){
		     		if(responseData.state=="true"){
		     			query();
		     		}
		     	});
			}
		});
	}
	function collectDeptKpiValue(){
		if($("#acc_year").val()==""){
     		$.ligerDialog.warn("考核期间不能为空");
     		return false;
    	}
			
		$.ligerDialog.confirm('确定计算吗?',function(yes){
			if(yes){
				var ParamVo =[];
				ParamVo.push(
					$("#acc_year").val() 
				)
		
	    	ajaxJsonObjectByUrl("collectPrmDeptKpiValueCalculate.do",{ParamVo : ParamVo.toString()},function (responseData){
	     		if(responseData.state=="true"){
	     			query();
	     		}
	     	});
			}
		})
	}
	function auditDeptKpiValue(){
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        }else{
       	 
            var ParamVo =[];
            $(data).each(function (){			
         
           
														ParamVo.push(
														this.group_id   +"@"+ 
														this.hos_id   +"@"+ 
														this.copy_code   +"@"+ 
														this.acc_year   +"@"+ 
														this.acc_month   +"@"+ 
														this.goal_code   +"@"+ 
														this.kpi_code   +"@"+ 
														this.dept_no   +"@"+ 
														this.dept_id
														) });
            $.ligerDialog.confirm('确定审核?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("auditPrmDeptKpiValueCalculate.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
	}
	function reAuditDeptKpiValue(){
		 var data = gridManager.getCheckedRows();
         if (data.length == 0){
         	$.ligerDialog.warn('请选择行');
         }else{
        	 
             var ParamVo =[];
             $(data).each(function (){			
          
														ParamVo.push(
														this.group_id   +"@"+ 
														this.hos_id   +"@"+ 
														this.copy_code   +"@"+ 
														this.acc_year   +"@"+ 
														this.acc_month   +"@"+ 
														this.goal_code   +"@"+ 
														this.kpi_code   +"@"+ 
														this.dept_no   +"@"+ 
														this.dept_id
														) });
             $.ligerDialog.confirm('确定取消审核?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("reAuditPrmDeptKpiValueCalculate.do",{ParamVo : ParamVo.toString()},function (responseData){
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
        
    	    	autocompleteAsync("#goal_code", "../quertPrmGoalDict.do?isCheck=false",
				"id", "text", true, true, "", true, "", "400");

		autocompleteAsync("#dept_kind_code", "../queryPrmDeptKind.do?isCheck=false",
				"id", "text", true, true, "", false);

		autocompleteAsync("#dept_id","../queryPrmDeptDict.do?isCheck=false","id","text",true,true,"",false,null);

		autocompleteAsync("#kpi_code", "../queryPrmDeptKpi.do?isCheck=false", "id",
				"text", true, true, "", false, "", "400");
	        
            autodate("#acc_year","yyyymm");
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
				title: "科室KPI指标数据计算",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.prm.service.PrmDeptKpiValueCalculateService",
				method_name: "queryPrmDeptKpiValueSchemeCalculatePrint",
				bean_name: "prmDeptKpiValueCalculateService"/* ,
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

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年度：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year"  class="Wdate" type="text" id="acc_year" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">KPI指标：</td>
            <td align="left" class="l-table-edit-td"><input name="kpi_code" type="text" id="kpi_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">目标名称：</td>
            <td align="left" class="l-table-edit-td" colspan="4"><input name="goal_code" type="text" id="goal_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室分类：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td" >审核标志：</td>
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
