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
    	$('body').height("100%");//以免拉动左边树时结构混乱
        loadDict()//加载下拉框
    	loadHead(null);	//加载数据
    	
    	toolbar();
        loadHotkeys();
        $("#acc_year_month").ligerTextBox({width:160});
        $("#goal_code").ligerTextBox({width:400});
        $("#kpi_code").ligerTextBox({width:160});
        $("#is_audit").ligerComboBox({width:160 });
		
        
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'acc_year',value:$("#acc_year_month").val().substring(0,4)}); 
    	  grid.options.parms.push({name:'acc_month',value:$("#acc_year_month").val().substring(4,6)}); 
    	  grid.options.parms.push({name:'goal_code',value:liger.get("goal_code").getValue()}); 
    	  grid.options.parms.push({name:'kpi_code',value:liger.get("kpi_code").getValue()}); 
    	  grid.options.parms.push({name:'is_audit',value:$("#is_audit").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '考核单位', name: 'check_hos_name', align: 'left'
				 		},
                     { display: 'KPI指标编码', name: 'kpi_code', align: 'left'
					 		},
                     { display: 'KPI指标名称', name: 'kpi_name', align: 'left'
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
                     { display: '审核状态', name: 'is_audit', align: 'left'
					 		},
                     { display: '审核人', name: 'user_code', align: 'left'
					 		},
                     { display: '审核日期', name: 'audit_date', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmHosKpiValueSchemeCalculate.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,enabledEdit: true,delayLoad:true,
                     selectRowButtonOnly:true//heightDiff: -10,
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
    }
    
    
    function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '生成（<u>C</u>）', id:'create', click: createHosKpiValue, icon:'bookpen' });
       	obj.push({ line:true });
       	
       	/*.push({ text: '计算（<u>D</u>）', id:'collect', click: collectHosKpiValue, icon:'account' });
       	obj.push({ line:true });*/
       	
       	obj.push({ text: '审核（<u>A</u>）', id:'audit', click:auditHosKpiValue,icon:'audit' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '反审核（<u>B</u>）', id:'reAudit', click:reAuditHosKpiValue,icon:'back' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '保存（<u>S</u>）', id:'save', click:saveHosKpiValue,icon:'save' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
    
    
	function loadHotkeys(){
		
		hotkeys('Q',query);
		hotkeys('P',print);
        hotkeys('C',createHosKpiValue);
        hotkeys('D',collectHosKpiValue);
        hotkeys('A',auditHosKpiValue);
        hotkeys('B',reAuditHosKpiValue);
        hotkeys('S',saveHosKpiValue)

	}
	
	function saveHosKpiValue(){
		
		var data = gridManager.getUpdated();
		   
    	if(data.length==0){
    		
    		$.ligerDialog.warn('沒有数据更新');
    		
    	}else{
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.acc_year + "@"
								+ this.acc_month + "@" + this.kpi_code + "@"
								+ this.check_hos_id + "@" + this.goal_code + "@" +this.kpi_value + "@")
					});

					ajaxJsonObjectByUrl("saveBatchHosKpiValueCalculate.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
    	}
		
	}
	
	function createHosKpiValue(){
		if($("#acc_year_month").val()==""){
     		$.ligerDialog.warn("考核期间不能为空");
     		return false;
    	}
		
		
		$.ligerDialog.confirm('确定生成吗?',function(yes){
			if(yes){
				var ParamVo = {
					acc_year :$("#acc_year_month").val().substring(0,4),
					acc_month:$("#acc_year_month").val().substring(4,6)
				}
				
			    ajaxJsonObjectByUrl("createHosKpiValueCalculate.do",ParamVo,function (responseData){
			     	if(responseData.state=="true"){
			     		query();
			     	}
			     });
			}
		})
	}
	function collectHosKpiValue(){}
	function auditHosKpiValue(){
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
														this.check_hos_id 
														) });
             $.ligerDialog.confirm('确定审核?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("auditPrmHosKpiValueCalculate.do",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             }); 
         }
	}
	function reAuditHosKpiValue(){
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
														this.check_hos_id 
														) });
             $.ligerDialog.confirm('确定取消审核?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("reAuditHosKpiValueCalculate.do",{ParamVo : ParamVo.toString()},function (responseData){
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
    	autocomplete("#goal_code","../quertPrmGoalDict.do?isCheck=false","id","text",true,true,"",false,"","400");
            
    	autocompleteAsync("#kpi_code", "../queryPrmHosKpi.do?isCheck=false", "id",
				"text", true, true, "", false, "", "400");
    	
    	autodate("#acc_year_month","yyyyMM");
    	
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
				title: "院级KPI指标数据计算",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.prm.service.PrmHosKpiValueCalculateService",
				method_name: "queryPrmHosKpiValueSchemeCalculatePrint",
				bean_name: "prmHosKpiValueCalculateService"/* ,
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
        </tr> 
	
            <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">考核期间：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="acc_year_month" type="text" id="acc_year_month" ltype="text" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
            <td align="left"></td>
          
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">目标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="goal_code" type="text" id="goal_code" ltype="text" validate="{required:true,maxlength:20}" style="width: 200px;"/></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">KPI指标：</td>
            <td align="left" class="l-table-edit-td"><input name="kpi_code" type="text" id="kpi_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核标志：</td>
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
