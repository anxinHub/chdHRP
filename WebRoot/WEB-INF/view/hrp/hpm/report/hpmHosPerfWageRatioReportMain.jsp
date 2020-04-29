<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>

<style type="text/css">
.ccc .l-grid-row-cell{background: #F1D3F7;}
</style>


<script type="text/javascript">


	var grid;
	var gridManager = null;
	var userUpdateStr;
	
	
	//页面初始化
	$(function (){
		loadDict();//加载字典
		loadHead(null);	//加载grid
		toolbar();//加载工具栏
		loadHotkeys();//加载快捷键
		
   });
	
	 //查询
	function  query(){
		 
		var acct_yearm = $("#acct_yearm").val();
		if(acct_yearm == ""){
    		$.ligerDialog.warn('请选择核算年月');
    		return false;
    	}
		
		grid.options.parms=[];
		grid.options.newPage=1;
		grid.options.parms.push({name : 'acct_yearm',value:acct_yearm});
		//grid.options.parms.push({name : 'begin_date',value:$('#begin_date').val()});
		//grid.options.parms.push({name : 'column_name',value:liger.get("target_code").getValue()});
    	
		grid.loadData(grid.where);
	}
	
	 //加载表格
	function loadHead(){
	    grid = $("#maingrid").ligerGrid({
			columns: [
				{ display: '科别', name: 'kind_code', align: 'left',frozen:true},
				{ display: '绩效工资', name: 'perf_wages', align: 'right', editor: { type: 'float' },
					render:function(rowdata, rowindex, value){
						var col = arguments[arguments.length - 1];if (rowdata.is_audit == 1) {rowdata.notEidtColNames.push(col.columnname);}
						return formatNumber(rowdata.perf_wages ==null ? 0 : rowdata.perf_wages,2,1);
					}
						
				
				},
				{ display: '定员', name: 'perf_pers', align: 'right', editor: { type: 'float' },
					render:function(rowdata, rowindex, value){
						var col = arguments[arguments.length - 1];if (rowdata.is_audit == 1) {rowdata.notEidtColNames.push(col.columnname);}

						return formatNumber(rowdata.perf_pers ==null ? 0 : rowdata.perf_pers,2,1);
					}
	
				},
				{ display: '人均奖', name: 'perf_bonus', align: 'right', 
					render:function(rowdata, rowindex, value){
						return formatNumber(rowdata.perf_bonus ==null ? 0 : rowdata.perf_bonus,2,1);
					}
				},
				{ display: '医疗收入(手工录入)', name: 'perf_income', align: 'right', editor: { type: 'float' },
					render:function(rowdata, rowindex, value){
						var col = arguments[arguments.length - 1];if (rowdata.is_audit == 1) {rowdata.notEidtColNames.push(col.columnname);}
						return formatNumber(rowdata.perf_income ==null ? 0 : rowdata.perf_income,2,1);
					}
				},
				{ display: '占收入比率', name: 'perf_income_ratio', align: 'right'/* ,editor: { type: 'float', onChange: function (editValue){editValue.value = editValue.value/100;} } */,
				 	render: function (rowdata, rowindex, value){
				 		//if(rowdata.activity_percent!=0){
				 			return formatNumber(rowdata.perf_income_ratio ==null ? 0 : rowdata.perf_income_ratio * 100,2,1)+"%";
				 		}
				}
			],
			dataAction: 'server',dataType: 'server',usePager:false,
			url:'queryHpmHosPerfWageRatioReport.do',width: '100%', height: '100%',rownumbers:true, 
			selectRowButtonOnly:true,enabledEdit: true,delayLoad:true,
			onBeforeSubmitEdit:f_onBeforeEdit,onAfterEdit:f_onAfterEdit,isAddRow:false,
            rowClsRender:  function (rowdata,rowid){
	        	if (rowdata.is_audit == 1){
	            	return "";
	            }else{
	                return "ccc";
	            }
	        }

		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	    
	    
	//工具栏
	function toolbar(){
      	var obj = [];
      	
      	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
      	obj.push({ line:true });
      	
      	obj.push({ text: '生成（<u>G</u>）', id:'init', click: initData, icon:'bookpen' });
      	obj.push({ line:true });
      	
      	obj.push({ text: '审核（<u>C</u>）', id:'audit', click: audit,icon:'right' });
      	obj.push({ line:true });
      	
      	obj.push({ text: '反审（<u>U</u>）', id:'reaudit', click: reaudit,icon:'back' });
      	obj.push({ line:true });
      	
      	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
      	obj.push({ line:true });
      	
      	$("#toptoolbar").ligerToolBar({ items: obj});
	}
 	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		hotkeys('G',initData);
		hotkeys('C',audit);
		hotkeys('U',reaudit);
		hotkeys('P',print);
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
				title: "全院绩效工资比例计算表",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.report.AphiHosPerfWageRatioReportService",
				method_name: "queryHpmHosPerfWageRatioReportPrint",
				bean_name: "aphiHosPerfWageRatioReportService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}


	var perf_wages1;
    var perf_pers1;
    var perf_income1;
    
    //编辑前事件
    function f_onBeforeEdit(){
    	 var data = gridManager.getCheckedRows();
    	 $(data).each(function (){

				perf_wages1 = this.perf_wages;

    		    perf_pers1 = this.perf_pers;
    		    
    		    perf_income1 = this.perf_income;
    		    
		  });
    }
    
    //编辑后事件
	function f_onAfterEdit(e){
		 
    	
    	var data = gridManager.getCheckedRows();
		var ParamVo =[];
		var res= 0;
		$(data).each(function (){
			  
			if(perf_wages1 != this.perf_wages && res == 0){
			  
				if(this.perf_pers.toString() != "" && this.perf_pers != 0){
					this.perf_bonus =this.perf_wages / this.perf_pers;
					gridManager.updateRow(e.record,{perf_bonus:this.perf_bonus});
				}
		
			  	if(this.perf_income.toString() != "" && this.perf_income != 0){
				  	this.perf_income_ratio =this.perf_wages / this.perf_income;
				 	gridManager.updateRow(e.record,{perf_income_ratio:this.perf_income_ratio});
			  	}
				  	
			  	res =1;
			}
				  
			if(perf_pers1 != this.perf_pers && res == 0){
				 
				if(this.perf_wages.toString() != "" && this.perf_wages != 0){
					this.perf_bonus =this.perf_wages / this.perf_pers;
					gridManager.updateRow(e.record,{perf_bonus:this.perf_bonus});
				}
	
				res =1;
			}
				
			if(perf_income1 != this.perf_income && res == 0){
				 
				if(this.perf_wages.toString() != "" && this.perf_wages != 0){
					this.perf_income_ratio =this.perf_wages / this.perf_income;
					gridManager.updateRow(e.record,{perf_income_ratio:this.perf_income_ratio});
				}
	
				res =1;
			}
				
			ParamVo.push(this.acct_year+"#"+this.acct_month+"#"+this.report_code+"#"+this.perf_wages+"#"+this.perf_pers+"#"+this.perf_bonus+"#"+this.perf_income+"#"+this.perf_income_ratio);
		
		});
		  
		if(e.value == e.oldvalue){
			return false;
		}

		ajaxJsonObjectByUrl("updateHpmHosPerfWageRatioReport.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
       		if(responseData.state=="true"){
       			query();
     		}
        });
	}
	
	//审核
	function audit(){
		
		var acct_yearm = $("#acct_yearm").val();
		
		if(!acct_yearm){
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}
		
		var param = {
			acct_year : acct_yearm.substring(0,4),
			acct_month : acct_yearm.substring(4,6),
			is_audit : 1
		};
		
		ajaxJsonObjectByUrl("updateshenhe.do?isCheck=false",param,function (responseData){
             if(responseData.state=="true"){
             	 query();
             }
		});
	}
	
	//反审
	function reaudit(){
		
		var acct_yearm = $("#acct_yearm").val();
		
		if(!acct_yearm){
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}
		
		var param = {
			acct_year : acct_yearm.substring(0,4),
			acct_month : acct_yearm.substring(4,6),
			is_audit : 0
		};
		
		ajaxJsonObjectByUrl("updateshenhe.do?isCheck=false",param,function (responseData){
             if(responseData.state=="true"){
             	 query();
             }
		});
	}
	
	
	//生成 
	function initData(){
		
		var acct_yearm = $("#acct_yearm").val();
		if(!acct_yearm){
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}
		var param = {
			acct_yearm : acct_yearm
		};
		
		$.ligerDialog.confirm('确定要生成吗?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("initHpmHosPerfWageRatioReport.do", param,function(responseData){
		    		if(responseData.state=="true"){
		    			query();
		    		}
		    	});
			}
		});
		
	}
	
	
	//加载字典
	function loadDict(){
		$("#acct_yearm").ligerTextBox({width:160 });autodate("#acct_yearm","yyyymm");
	}  

</script>
</head>

<body style="padding: 0px; overflow: hidden;" >
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算年月：</td>
            <td align="left" class="l-table-edit-td"><input name="acct_yearm"  class="Wdate"  type="text" id="acct_yearm" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"/></td>
            <td align="left"></td>
		</tr>
	</table>

	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>