<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">	
    var grid;
    var gridManager = null;
    $(function ()
    {
		loadDict();
    	loadHead(null);	//加载数据
    	$("#year_month1").ligerTextBox({ width:60 });
    	$("#year_month2").ligerTextBox({ width:60 });
    	$("#date_type").ligerTextBox({ width:160 });
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		
		grid.options.parms.push({name:'year_month1',value:$("#year_month1").val()}); 
		grid.options.parms.push({name:'year_month2',value:$("#year_month2").val()}); 
		grid.options.parms.push({name:'date_type',value:$("#date_type").val()}); 
		grid.options.parms.push({name:'kind_code',value:liger.get("kind_code").getValue()}); 
		grid.options.parms.push({name:'natur_code',value:liger.get("natur_code").getValue()}); 
		grid.options.parms.push({name:'dept_level',value:liger.get("dept_level").getValue()});
            
    		
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	/**
    	var columnses = "{display : '全院', align: 'center',"
		    +"columns:["
                     +"{ display: '科室编码', name: 'dept_code', align: 'left'},"
					 +"{ display: '科室名称', name: 'dept_name', align: 'left'}"
		    +"]},";
    	
    	ajaxJsonObjectByUrl("loadCostStructureTitle.do",null,function (responseData){
    		$.each(responseData.Rows,function(idx,item){ 
    			columnses = columnses + "{display : '"+item.cost_type_name+"', align: 'center',"
				+"columns:["
	                     +"{ display: '金额', name: '', align: 'left'},"
						 +"{ display: '比例', name: '', align: 'left'}"
			 +"]},";
			});
    	});
    	*/
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
						  {display : '全院', align: 'center',
						    columns:[
				                     { display: '科室编码', name: 'dept_code', align: 'left'
									 },
									 { display: '科室名称', name: 'dept_name', align: 'left'
									 }
						    ]},
						    {display : '人员经费', align: 'center',
								columns:[
					                     { display: '金额', name: 'cost_emp_amount', align: 'left'
										 },
										 { display: '%', name: 'cost_emp_amount_ratio', align: 'left',
												render : function(rowdata, rowindex,
														value) {
													var taotal = rowdata.cost_emp_amount+
													rowdata.cost_mate_amount+
													rowdata.cost_drug_amount+
													rowdata.cost_fasset_amount+
													rowdata.cost_iasset_amount+
													rowdata.cost_risk_amount+
													rowdata.cost_other_amount;
													
													return (rowdata.cost_emp_amount / taotal) * 100 + "%";
												}
										 }
							 ]},
					 		 {display : '卫生材料费', align: 'center',
							     columns:[
				                     { display: '金额', name: 'cost_mate_amount', align: 'left'
									 },
									 { display: '%', name: 'cost_mate_amount_ratio', align: 'left',
											render : function(rowdata, rowindex,
													value) {
												var taotal = rowdata.cost_emp_amount+
												rowdata.cost_mate_amount+
												rowdata.cost_drug_amount+
												rowdata.cost_fasset_amount+
												rowdata.cost_iasset_amount+
												rowdata.cost_risk_amount+
												rowdata.cost_other_amount;
												
												return (rowdata.cost_mate_amount / taotal) * 100 + "%";
											}
									 }
						     ]},
						     {display : '药品费', align: 'center',
								columns:[
					                     { display: '金额', name: 'cost_drug_amount', align: 'left'
										 },
										 { display: '%', name: 'cost_drug_amount_ratio', align: 'left',
												render : function(rowdata, rowindex,
														value) {
													var taotal = rowdata.cost_emp_amount+
													rowdata.cost_mate_amount+
													rowdata.cost_drug_amount+
													rowdata.cost_fasset_amount+
													rowdata.cost_iasset_amount+
													rowdata.cost_risk_amount+
													rowdata.cost_other_amount;
													
													return (rowdata.cost_drug_amount / taotal) * 100 + "%";
												}
										 }
							 ]},
							 {display : '固定资产折旧', align: 'center',
									columns:[
						                     { display: '金额', name: 'cost_fasset_amount', align: 'left'
											 },
											 { display: '%', name: 'cost_fasset_amount_ratio', align: 'left',
													render : function(rowdata, rowindex,
															value) {
														var taotal = rowdata.cost_emp_amount+
														rowdata.cost_mate_amount+
														rowdata.cost_drug_amount+
														rowdata.cost_fasset_amount+
														rowdata.cost_iasset_amount+
														rowdata.cost_risk_amount+
														rowdata.cost_other_amount;
														
														return (rowdata.cost_fasset_amount / taotal) * 100 + "%";
													}
											 }
							]},
							{display : '无形资产摊销', align: 'center',
									columns:[
							                 { display: '金额', name: 'cost_iasset_amount', align: 'left'
											 },
										     { display: '%', name: 'cost_fasset_amount_ratio', align: 'left',
													render : function(rowdata, rowindex,
															value) {
														var taotal = rowdata.cost_emp_amount+
														rowdata.cost_mate_amount+
														rowdata.cost_drug_amount+
														rowdata.cost_fasset_amount+
														rowdata.cost_iasset_amount+
														rowdata.cost_risk_amount+
														rowdata.cost_other_amount;
														
														return (rowdata.cost_iasset_amount / taotal) * 100 + "%";
													}
											 }
							]},
							{display : '提取医疗风险基金', align: 'center',
								columns:[
								         { display: '金额', name: 'cost_risk_amount', align: 'left'
										 },
										 { display: '%', name: 'cost_fasset_amount_ratio', align: 'left',
												render : function(rowdata, rowindex,
														value) {
													var taotal = rowdata.cost_emp_amount+
													rowdata.cost_mate_amount+
													rowdata.cost_drug_amount+
													rowdata.cost_fasset_amount+
													rowdata.cost_iasset_amount+
													rowdata.cost_risk_amount+
													rowdata.cost_other_amount;
													
													return (rowdata.cost_risk_amount / taotal) * 100 + "%";
												}
										 }
							]},
							{display : '其他费用', align: 'center',
								columns:[
									    { display: '金额', name: 'cost_other_amount', align: 'left'
										},
										{ display: '%', name: 'cost_other_amount_ratio', align: 'left',
											render : function(rowdata, rowindex,
													value) {
												var taotal = rowdata.cost_emp_amount+
												rowdata.cost_mate_amount+
												rowdata.cost_drug_amount+
												rowdata.cost_fasset_amount+
												rowdata.cost_iasset_amount+
												rowdata.cost_risk_amount+
												rowdata.cost_other_amount;
												
												return (rowdata.cost_other_amount / taotal) * 100 + "%";
											}
										}
						   ]}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostStructureAnalysis.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    	                //{ text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
		                //{ line:true },
		                { text: '打印', id:'print', click: printDate,icon:'print' },
		                { line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }


    function loadDict(){
            //字典下拉框
    	$("form").ligerForm();
    	var param = {
            	kind_code1:'01',
            	kind_code2:'02'
        };
    	autocomplete("#kind_code","../../../sys/queryDeptKindDict.do?isCheck=false","id","text",true,true,param);
    	var param1 = {
    			natur_code1:'03',
    			natur_code2:'05'
    	};
    	autocomplete("#natur_code","../../../acc/queryDeptNatur.do?isCheck=false","id","text",true,true,param1);
    	
    	
    	autocomplete("#dept_level","../../queryDeptLevel.do?isCheck=false","id","text",true,true); 
         }  
    
  //打印数据
	 function printDate(){
		//有数据直接打印
		
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","列表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
				
			usePager:false
         };
      
	    ajaxJsonObjectByUrl("queryCostStructureAnalysis.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
				
					 trHtml+="<td>"+item.dept_code+"</td>"; 
					 trHtml+="<td>"+item.dept_name+"</td>"; 
					 trHtml+="<td>"+item.cost_emp_amount+"</td>"; 
				     trHtml+="<td>"+(item.cost_emp_amount/(item.cost_emp_amount+item.cost_mate_amount+item.cost_drug_amount+item.cost_fasset_amount+item.cost_iasset_amount+item.cost_risk_amount+item.cost_other_amount)* 100 )+"%"+"</td>";
					 trHtml+="<td>"+item.cost_mate_amount+"</td>"; 
					 trHtml+="<td>"+(item.cost_mate_amount/(item.cost_emp_amount+item.cost_mate_amount+item.cost_drug_amount+item.cost_fasset_amount+item.cost_iasset_amount+item.cost_risk_amount+item.cost_other_amount)* 100 )+"%"+"</td>";
					 trHtml+="<td>"+item.cost_drug_amount+"</td>"; 
					 trHtml+="<td>"+(item.cost_drug_amount/(item.cost_emp_amount+item.cost_mate_amount+item.cost_drug_amount+item.cost_fasset_amount+item.cost_iasset_amount+item.cost_risk_amount+item.cost_other_amount)* 100 )+"%"+"</td>";
					 trHtml+="<td>"+item.cost_fasset_amount+"</td>"; 
					 trHtml+="<td>"+(item.cost_fasset_amount/(item.cost_emp_amount+item.cost_mate_amount+item.cost_drug_amount+item.cost_fasset_amount+item.cost_iasset_amount+item.cost_risk_amount+item.cost_other_amount)* 100 )+"%"+"</td>";
					 trHtml+="<td>"+item.cost_iasset_amount+"</td>"; 
					 trHtml+="<td>"+(item.cost_iasset_amount/(item.cost_emp_amount+item.cost_mate_amount+item.cost_drug_amount+item.cost_fasset_amount+item.cost_iasset_amount+item.cost_risk_amount+item.cost_other_amount)* 100 )+"%"+"</td>";
					 trHtml+="<td>"+item.cost_risk_amount+"</td>"; 
					 trHtml+="<td>"+(item.cost_risk_amount/(item.cost_emp_amount+item.cost_mate_amount+item.cost_drug_amount+item.cost_fasset_amount+item.cost_iasset_amount+item.cost_risk_amount+item.cost_other_amount)* 100 )+"%"+"</td>";
					 trHtml+="<td>"+item.cost_other_amount+"</td>"; 
					 trHtml+="<td>"+(item.cost_other_amount/(item.cost_emp_amount+item.cost_mate_amount+item.cost_drug_amount+item.cost_fasset_amount+item.cost_iasset_amount+item.cost_risk_amount+item.cost_other_amount)* 100 )+"%"+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","列表",true);
	    },true,manager);
		return;
	 }
 
	 function dateType(){
		 var date_type = $("#date_type").val();
		 if(date_type == 0){
			     $('#year_month1').unbind('focus');
				 $('#year_month1').bind('focus',function(){
				 	WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'});
				 });
				 $('#year_month2').unbind('focus');
				 $('#year_month2').bind('focus',function(){
				 	WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'});
				 });
		 }else{
				 $('#year_month1').unbind('focus');
				 $('#year_month1').bind('focus',function(){
					 WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'});
				 });
				 $('#year_month2').unbind('focus');
				 $('#year_month2').bind('focus',function(){
					 WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'});
				 });
		 }
	 }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
<form name="form1" method="post"  id="form1" >
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间类型：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="date_type" name="data_type" onchange="dateType();">
            		<option ></option>
            		<option value="0">年</option>
            		<option value="1">月</option>
            	</select>
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="year_month1" type="text" id="year_month1" class="Wdate" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="year_month2" type="text" id="year_month2" class="Wdate" />
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室类型：</td>
            <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" /></td>
         </tr> 
         <tr>
         	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室性质：</td>
            <td align="left" class="l-table-edit-td"><input name="natur_code" type="text" id="natur_code" /></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本级次：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
            	<select>
            		<option>医疗成本</option>
            		<option>医疗全成本</option>
            		<option>医院全成本</option>
            	</select>
            </td>
            <!--<td align="right" class="l-table-edit-td"  style="padding-left:20px;">分摊级别：</td>
            <td align="left" class="l-table-edit-td"></td>-->
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" >科室级次：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_level" type="text" id="dept_level" /></td>
         </tr>
    </table>
</form>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
			   	
			   	<tr>
					<th width="200" colspan="2">全院</th>
					<th width="200" colspan="2">人员经费</th>
					<th width="200" colspan="2">卫生材料费</th>
					<th width="200" colspan="2">药品费</th>
					<th width="200" colspan="2">固定资产折旧</th>
					<th width="200" colspan="2">无形资产摊销</th>
					<th width="200" colspan="2">提取医疗风险基金</th>
					<th width="200" colspan="2">其他费用</th>
			    </tr>
	
				   	<tr>
				   	<th width="200">科室编码</th>
					<th width="200">科室名称</th>
					<th width="200">金额</th>
					<th width="200">占比</th>
					<th width="200">金额</th>
					<th width="200">占比</th>
					<th width="200">金额</th>
					<th width="200">占比</th>
					<th width="200">金额</th>
					<th width="200">占比</th>
					<th width="200">金额</th>
					<th width="200">占比</th>
					<th width="200">金额</th>
					<th width="200">占比</th>
					<th width="200">金额</th>
					<th width="200">占比</th>
				   	</tr>
				   	
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
