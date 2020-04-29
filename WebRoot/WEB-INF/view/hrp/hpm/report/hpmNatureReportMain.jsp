<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	var grid;
	
	var gridManager = null;
	var userUpdateStr;
	var column_name="";
	
	$(function (){
    	
		loadDict();
		loadHead(null);	
		
		setTimeout(function(){
			var targetObj = eval("["+ "{'id':'" + liger.get("target_code").getValue() + "','text':'"+liger.get("target_code").getText()+"'}"+"]");
			f_setColumns(targetObj);
		},300); 
		
		//单选事件
		$(':radio').bind('click',function(){
			
			var showKind = this.value;
			
			var sys_dates = getCurrentDate();
			var currentYear = sys_dates.split(";")[0];
			var currentMonth = sys_dates.split(";")[1];
			
			if(showKind == 1 ){//显示类型为月报
				$('#begin_date').val(currentYear + '-' + currentMonth);
				$('#end_date').val(currentYear + '-' + currentMonth);
			}
			
			if(showKind == 2 ){//显示类型为季报
				var begin_end_yearMonth = getYearMonth(currentYear,currentMonth);//获取当月所在季度期间值
				$('#begin_date').val(begin_end_yearMonth.split(';')[0]);
				$('#end_date').val(begin_end_yearMonth.split(';')[1]);
			}
			
			if(showKind == 3 ){//显示类型为年报
				$('#begin_date').val(currentYear+'-01');
				$('#end_date').val(currentYear+'-12');
			}
			
		});
		
		$('#target_code').bind('change',function(){
			if(flag){//单选时执行此处
				var targetObj = eval("["+ "{'id':'" + liger.get("target_code").getValue() + "','text':'"+liger.get("target_code").getText()+"'}"+"]");
				f_setColumns(targetObj);
				
			}
		});
   });
	
	//根据传入的年月获取季度期间
	function getYearMonth(cur_year,cur_month){
		
		var begin_end_yearMonth = "";
		
		if(cur_month == '01' || cur_month == '02' || cur_month == '03'){
			begin_end_yearMonth = cur_year + '-01' + ';' + cur_year + '-03';
		}
		
		if(cur_month == '04' || cur_month == '05' || cur_month == '06'){
			begin_end_yearMonth = cur_year + '-04' + ';' + cur_year + '-06';
		}
		
		if(cur_month == '07' || cur_month == '08' || cur_month == '09'){
			begin_end_yearMonth = cur_year + '-07' + ';' + cur_year + '-09';
		}
		
		if(cur_month == '10' || cur_month == '11' || cur_month == '12'){
			begin_end_yearMonth = cur_year + '-10' + ';' + cur_year + '-12';
		}
		
		return begin_end_yearMonth;
	}
	
	
	 //查询
    function  query(){
		 
		grid.options.parms=[];
		grid.options.newPage=1;
		grid.options.parms.push({name : 'begin_date',value:$('#begin_date').val()});
		grid.options.parms.push({name : 'end_date',value:$('#end_date').val()});
		grid.options.parms.push({name : 'nature_code',value:liger.get("dept_nature").getValue()});
		grid.options.parms.push({name : 'column_name',value:column_name});
    	
		grid.loadData(grid.where);
     }
	
	function loadHead(){
	    grid = $("#maingrid").ligerGrid({
				columns: [],
				dataAction: 'server',dataType: 'server',usePager:false,url:'queryHpmNatureReport.do',width: '100%', height: '100%',rownumbers:true,
				delayLoad: true,//初始化不加载，默认false  
				selectRowButtonOnly:true,
				toolbar: { items: [
		  			 { text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
					 { line:true }/* ,
				     { text: '打印', id:'print', click: print, icon:'print' },
					 { line:true } */
		  		]}	
			});

	        gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function f_setColumns(checkData){ 
		
		column_name="";
		var columns = [];	
	   	var param = "";
	  	var target_valueId="";
	   
		$.each(checkData,function(i,v){
			if(checkData.length == (i+1)){
				param = param + v.text;
				target_valueId=target_valueId+v.id.toString();
				
				columns.push(
					{display:'人员类别/指标',name:"nature_name",width: 100,align : 'left', frozen: true}
				);
				
				columns.push(
					{display:v.text,columns :[
						{display: '本期', name:"bq_"+v.id,width: 160, align: 'right',render : 
							function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value, 2, 1);
							}
						},
						{display: '上期', name:"sq_"+v.id,width: 160, align: 'right',render : 
							function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value, 2, 1);
							}
						},
						{display: '同比', name:"tb_"+v.id,width: 160, align: 'right',render : 
							function(rowdata, rowindex, value) {
								
								return formatNumber(value*100, 2, 1)+'%';
							}
						}]
					}
				);
			}else{
				param = param + v.text + ",";
				target_valueId=target_valueId+v.id.toString()+ ",";
				  
				columns.push(
					{display:v.text,columns :[
						{display: '本期', name:"bq_"+v.id,width: 160, align: 'right',render : 
							function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value, 2, 1);
							}
						},
						{display: '上期', name:"sq_"+v.id,width: 160, align: 'right',render : 
							function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value, 2, 1);
							}
						},
						{display: '同比', name:"tb_"+v.id,width: 160, align: 'right',render : 
							function(rowdata, rowindex, value) {
								
								return formatNumber(value*100, 2, 1)+'%';
							}
						}
					]}
				);
			}
			  
			column_name=column_name+v.id+",";
		});
		  
		if(column_name != ''){
			grid.set('columns', columns);
		} 
		
		//指标选择分为下拉框选择(单选)或者选择页面选择(多选)
		flag = false;//若为多选,根据flag=false值,在触发值改变事件时,则不调用此方法(f_setColumns()),避免执行以下两行语句时,出现死循环,
		
		//多选时设置下拉框id,设置下拉框text 
	   	liger.get("target_code").setValue(target_valueId);
	   	liger.get("target_code").setText(param);
	   	flag = true;//flag=true时,单选时触发事件,进入此方法,渲染动态列
		query();
	}
	
	function choiceTarget(){
		
		$.ligerDialog.open({ 
			url : 'hpmChoiceTargetMainPage.do?isCheck=false' ,data:{}, 
			height: 470,width: 480, title:'指标选择器',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [
				{ text: '确定', onclick: 
					function (item, dialog) { 
						var checkData = dialog.frame.getListBox();
						f_setColumns(checkData);
						query();
		        	   	dialog.close(); 
					},
					cls:'l-dialog-btn-highlight'}, 
					{ text: '关闭', onclick: 
						function (item, dialog) { 
							dialog.close(); 
						} 
					} 
			] 
		});
	}
	
	function print(){
		
	}
	
	function loadDict(){
        //字典下拉框
		autocomplete("#dept_nature","../queryHpmDeptNature.do?isCheck=false","id","text",true,true);
        
		 //字典下拉框
		autocomplete("#target_code","../queryTarget.do?isCheck=false","id","text",true,true,'',true);
		$("#begin_date").ligerTextBox({width:120});
        autodate("#begin_date", "yyyy-MM");
        $("#end_date").ligerTextBox({width:120});
        autodate("#end_date", "yyyy-MM");
	}  

</script>
</head>

<body style="padding: 0px; overflow: hidden;" onload="f_setColumns();">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="2" color="red">*</font>期间：</td>
            <td align="left" class="l-table-edit-td">
            	<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td>
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM'})"/>
						</td>
            		</tr>
				</table>
            </td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">显示栏目：</td>
			<td align="left" class="l-table-edit-td">
				<input name="showKind" type="radio" id="showKind" ltype="text" value="1" checked="checked"/>月报
				<input name="showKind" type="radio" id="showKind" ltype="text" value="2"/>季报
				<input name="showKind" type="radio" id="showKind" ltype="text" value="3"/>年报
			</td>
			<td align="left"></td>
		</tr>
		
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室性质：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_nature" type="text" id="dept_nature" ltype="text"/></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">指标名称：</td>
			<td align="left" class="l-table-edit-td"><input name="target_code" type="text" id="target_code" ltype="text"/></td>
			<td align="left"><input class="l-button l-button-test"  type="button" value="选择" onclick="choiceTarget()"/></td>
			<td align="left"></td>
			
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>