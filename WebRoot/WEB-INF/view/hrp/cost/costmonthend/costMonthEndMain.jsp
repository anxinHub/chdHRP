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
    var year;
    var month;
    var last_year;
    var last_month;
    $(function ()
    {
		loadDict();
    	loadHead(null);	//加载数据
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	//加载查询条件
    	grid.loadData(grid.where);
     }
   
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '年度',
				name : 'acc_year',
				align : 'left',
				width : '100'
			}, {
				display : '月份',
				name : 'acc_month',
				align : 'left',
				width : '100'
			}, {
				display : '开始时间',
				name : 'begin_date',
				align : 'left',
				width : '100'
			}, {
				display : '结束时间',
				name : 'end_date',
				align : 'left',
				width : '100'
			}, {
				display : '是否结转',
				name : 'cost_flag',
				align : 'left',
				width : '100',
				render : function(rowdata, rowindex, value) {

					if (rowdata.cost_flag == 1) {
						return "是";
					} else {
						return "否";
					}
				}

			}, {
				display : '结转人',
				name : 'cost_user',
				align : 'left',
				width : '100'

			}, {
				display : '结转日期',
				name : 'cost_date',
				align : 'left',
				width : '100'
			} ],
			dataAction : 'server',
			dataType : 'server',
			url : 'queryCostYearMonth.do?isCheck=false',
			width : '100%',
			height : '100%',
			usePager : false,
			rownumbers : true,
			selectRowButtonOnly : true,
			heightDiff : -20
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function loadDict(){
  
        year = '${year}';
        month = '${month}';
        last_year = '${last_year}';
        last_month = '${last_month}';
         
    	if(year==""){
			$("#year_month").val("");
		}
		else{
			$("#year_month").val(year+'.'+month);
		}
    	if(year==""){
			$("#generate_year_month").val("");
		}
		else{
			$("#generate_year_month").val( last_year+'.'+ last_month);
		}
         
    	if(last_year==""){
    		
			$("#last_year_month").val("");
		}
    	
		else{
			
			$("#last_year_month").val(last_year+'.'+last_month);
		}
    	
     	 $("#year_month").ligerTextBox({width:140});
     	 
    	 $("#last_year_month").ligerTextBox({width:140});
    	 
         $("#year_month").ligerTextBox({disabled:true});
         
         $("#last_year_month").ligerTextBox({disabled:true});
         
         
         }  
    
 

	function check() {
		   var ParamVo = [];
			ParamVo={
					year:year,
					month:month
			}
	    ajaxJsonObjectByUrl("updateCostFinalCharge.do?isCheck=false",ParamVo,function (responseData){
	    	if(responseData.state=="true"){
	    	       year = responseData.acc_year;
	               month = responseData.acc_month;
	               last_year = responseData.last_year;
	               last_month = responseData.last_month;

	              	if(year==""){
	        			$("#year_month").val("");
	        		}
	        		else{
	        			$("#year_month").val(year+'.'+month);
	        		}
	              	
	               	if(last_year==""){
	               		
	        			$("#last_year_month").val("");
	        		}
	        		else{
	        			$("#last_year_month").val(last_year+'.'+last_month);
	        		}
	               	if(year==""){
	        			$("#generate_year_month").val("");
	        		}
	        		else{
	        			$("#generate_year_month").val( last_year+'.'+ last_month);
	        		}
	               	
	        		query();
	    	}     
	     	});
			
			$.ligerDialog.confirm('是否生成分析报表?', function (yes){
            	if(yes){
            		var formPara = {
            				acc_year : last_year,
            				acc_month : last_month
            			};

      					ajaxJsonObjectByUrl("saveCostAnalysisProc.do?isCheck=false", formPara,
      							function(responseData) {
      								if (responseData.state == "true") {
      								}
      							});
            	}
            }); 
	}
    
  
	function uncheck() {
		
		if($("#last_year_month").val()==''){
			$.ligerDialog.warn('不存在上个期间，不能反结账！');
			return;
		}
		   var ParamVo = [];
			ParamVo={
					year:year,
					month:month,
					last_year:last_year,
					last_month:last_month
			}
	    ajaxJsonObjectByUrl("updateCostFinalUnCharge.do?isCheck=false",ParamVo,function (responseData){
		    	if(responseData.state=="true"){
		    	       year = responseData.acc_year;
		               month = responseData.acc_month;
		               last_year = responseData.last_year;
		               last_month = responseData.last_month;
		              	if(year==""){
		        			$("#year_month").val("");
		        		}
		        		else{
		        			$("#year_month").val(year+'.'+month);
		        		}
		              	
		               	if(last_year==""){
		               		
		        			$("#last_year_month").val("");
		        		}
		        		else{
		        			$("#last_year_month").val(last_year+'.'+last_month);
		        		}
		               	if(year==""){
		        			$("#generate_year_month").val("");
		        		}
		        		else{
		        			$("#generate_year_month").val( last_year+'.'+ last_month);
		        		}
		        		query();
		    	} 
	     	});
	}
	
	function generate() {
		/* if ($("#acc_year").val() == "") {

			$.ligerDialog.error('统计年份不能为空');

			return;
		}
		if ($("#acc_month").val() == "") {

			$.ligerDialog.error('统计月份不能为空');

			return;
		} */
		var formPara = {
			acc_year :  last_year,
			acc_month : last_month
		};

		$.ligerDialog.confirm('确定生成当前月数据?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("saveCostAnalysisProc.do?isCheck=false", formPara,
						function(responseData) {
							if (responseData.state == "true") {
							}
						});
			}
		});
	}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
   <table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td">上月：</td>
				<td align="left" class="l-table-edit-td"><input name="last_year_month" type="text" id="last_year_month" ltype="text"  disabled="disabled"/></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left"><input class="l-button l-button-test"
					type="button" id = "uncheck"  value="反&nbsp;&nbsp;&nbsp;&nbsp;结"
					onclick="uncheck()" /></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td">本月：</td>
				<td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month"  disabled="disabled" ltype="text"/></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left"><input class="l-button l-button-test"  id = "check"
					type="button" value="结&nbsp;&nbsp;&nbsp;&nbsp;转" onclick="check()" /></td>

			</tr>
			<tr>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td">生成年月：</td>
				<td align="left" class="l-table-edit-td"><input name="generate_year_month" type="text" id="generate_year_month"  disabled="disabled" ltype="text"/></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left" class="l-table-edit-td"></td>
				<td align="left"><input class="l-button l-button-test"  id = "generate"
					type="button" value="成本分析" onclick="generate()" /></td>

			</tr>
		</table>

	<div id="maingrid"></div>
</body>
</html>
