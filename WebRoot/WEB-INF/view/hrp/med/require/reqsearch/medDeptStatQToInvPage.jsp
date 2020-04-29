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
	var store_id = null;
	var store_no;
	var store_name;
	$(function() {
		
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		
		$("#begin_date").ligerTextBox({width:160});
		$("#end_date").ligerTextBox({width:160});
		
		$("#store_code").ligerTextBox({width:160});
      
        $("#inv_code").ligerTextBox({width:160});
        
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
		
    	grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()}); 
    	grid.options.parms.push({name : 'end_date',value : $("#end_date").val() }); 
    	grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(',')[0]}); 
    	grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(',')[1]});
    	grid.options.parms.push({name : 'inv_code',value : $("#inv_code").val()});
    	
    	
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		       columns: [ 
		                 
						 { display: '药品编码', name: 'inv_code', align: 'left'},
						 { display: '药品名称', name: 'inv_name', align: 'left'},
						 
						 { display: '规格型号', name: 'inv_model', align: 'left' },
						 { display: '计量单位', name: 'inv_unit', align: 'left' },
						 { display: '供应商', name: '', align: 'left' },
						 { display: '生产厂商', name: '', align: 'left' },
						 { display: '科室编码', name: 'dept_code', align: 'left'},
						 { display: '科室名称', name: 'dept_name', align: 'left' },
						 { display: '上期数量', name: '', align: 'right' },
						 { display: '需求数量', name: '', align: 'right' },
						 { display: '单价', name: '', align: 'right' },
						 { display: '金额', name: '', align: 'right' },
						 { display: '汇总金额', name: '', align: 'right' }
					
		                ],
		                
		                 dataAction: 'server',dataType: 'server',usePager:true,url:'.do',
		                 width: '100%', height: '100%', checkbox: false,rownumbers:false,delayLoad:true,
		                 selectRowButtonOnly:true,
		                 toolbar: { items: 
		                	 	[
		                          { text: '查询', id:'query', click: query ,icon:'search' },
		                            { line:true }/* ,
		                            
		                            {text : '打印',id : 'print',click : print ,icon : 'print'}, 
		      						{line : true} */
		                   		]
		                 }
		               });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function print(){
		
		
	}
	function loadDict() {
		
		$("#store_code").ligerComboBox({
           	url: '../../queryMedStore.do?isCheck=false',
           	valueField: 'id',
            textField: 'text', 
            selectBoxWidth: 160,
           	autocomplete: true,
           	initValue : 0,
           	width: 160
  		  });
		
		
		
		$("#begin_date").val(getFirstDay());
   		$("#end_date").val(getEndDay());
		
		
	}
	function getFirstDay(){
		var seperator1 = "-";
		var date = new Date();
		var month = date.getMonth() + 1;
		var year = date.getFullYear();
		if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
		var firstDate = date.getFullYear() + seperator1 + month + seperator1 + '01';
        return firstDate;
        
	}
	
	function getEndDay(){
		var seperator1 = "-";
		var date = new Date();
		var month = date.getMonth() + 1;
		var year = date.getFullYear();
		
		var last = getLastDay(year,month);
		
		if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
		var lastDay = date.getFullYear() + seperator1 + month + seperator1 + last;
        return lastDay;
	}
	
	function getLastDay(year,month) {          
		 var new_year = year;    //取当前的年份           
		 var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）           
		 if(month>12){            //如果当前大于12月，则年份转到下一年                  
		  	new_month -=12;        //月份减           
		 	new_year++;            //年份增           
		 }          
		 var new_date = new Date(new_year,new_month,1);                //取当年当月中的第一天           
		 var date_count =   (new Date(new_date.getTime()-1000*60*60*24)).getDate();//获取当月的天数         
		 var last_date =   new Date(new_date.getTime()-1000*60*60*24);//获得当月最后一天的日期  
		 return date_count;  
		
	}
	
	

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
		<tr></tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><font color="red">*</font>日期范围：</td>
			<td align="left" class="l-table-edit-td">
				<input class="Wdate" name="begin_date" type="text" requried="true"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"
				id="begin_date" />
			</td>
			<td align="center" class="l-table-edit-td" style="width: 15px;">至：</td>
			<td align="right" class="l-table-edit-td">
				<input class="Wdate" name="end_date" type="text" requried="true"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"
				id="end_date" />
			</td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><font color="red">*</font>汇总库房：</td>
			<td align="left" class="l-table-edit-td">
				<input name="store_code" type="text" requried="true" id="store_code" />
			</td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">药品信息：</td>
			<td align="left" class="l-table-edit-td">
				<input name="inv_code" type="text" requried="false" id="inv_code" />
			</td>
			<td align="left"></td>
		</tr>
		
		
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
