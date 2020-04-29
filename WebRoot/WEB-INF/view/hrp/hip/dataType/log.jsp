<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="dialog" name="plugins" />
</jsp:include>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var type_info = {
 			Rows : [ {
 				"id" : "0",
 				"text" : "手动" 
 			},{
 				"id" : "1",
 				"text" : "自动" 
 			}],
 			Total : 2
 	};
    var state_info = {
 			Rows : [ {
 				"id" : "0",
 				"text" : "成功" 
 			}, {
 				"id" : "1",
 				"text" : "失败" 
 			}],
 			Total : 2
 	};
    $(function ()
    {
         loadDict()//加载下拉框
    	//加载数据
    	 loadHead(null);	
         loadHotkeys();
    	 
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		
		/**
    	if(isnull($("#start_time").val())){
 			$.ligerDialog.error('开始日期不能为空!');
 			return;
 		} 
    	 
    	if(isnull($("#end_time").val())){
 			$.ligerDialog.error('结束日期不能为空!');
 			return;
 		} */
		
		grid.options.parms.push({name:'start_time',value:$("#start_time").val()}); 
		grid.options.parms.push({name:'end_time',value:$("#end_time").val()}); 
		grid.options.parms.push({name:'type',value:liger.get("type").getValue()}); 
		grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
		grid.options.parms.push({name:'type_code',value:$("#type_code").val()});
    	//加载查询条件
    	grid.loadData(grid.where);
     }
	
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					    display: '业务名称', name: 'type_name', align: 'left',isSort:false
				      },{
					    display: '开始时间', name: 'start_time', align: 'left',isSort:false
				      },{
					    display: '结束时间', name: 'end_time', align: 'left',isSort:false 
				      },{
					    display: '作业调度者', name: 'user_name', align: 'left',isSort:false 
				      },{
					    display: '任务类型', name: 'type', align: 'left',isSort:false 
				      },{
					    display: '状态', name: 'state', align: 'left',isSort:false 
				      },{
						display: '执行结果', name: 'note', align: 'left',isSort:false,
						render: function(rowData, rowIndex, value){
							return '<a href=javascript:open_tip("'+rowData.type_id+"|"+rowData.log_id+'")>'+rowData.note+'</a>';
						}
						
				      }],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryHipSyncLog.do?isCheck=false&type_id=${type_id}',
			width: '100%', height: '100%', checkbox: false,rownumbers:true,
			delayLoad: false,//初始化加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
			   				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
			   				{ line:true },	 
			   				{ text: '删除日志（<u>S</u>）', id:'clearData', click: clearData, icon:'delete' },
			   				{ line:true },	 
			   				{ text: '关闭（<u>C</u>）', id:'this_close', click: this_close, icon:'candle' }
			   			]}

		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function open_tip(obj){
    	ajaxJsonObjectByUrl("queryHipSyncLogByTypeId.do?isCheck=false",{"type_id" : obj.split("|")[0],"log_id" : obj.split("|")[1]},function (responseData){
    		$("#note").html(responseData.note);
        	$.ligerDialog.open({width:'800',height:'500', target: $("#note") });
    	});
    }
    
    
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('S', clearData);
		hotkeys('C', this_close);
	}
    function loadDict(){
    	
    	$("#start_time").ligerTextBox({
			width : 90
		});
    	$("#end_time").ligerTextBox({
			width : 90
		});
    	
    	$("#type_code").ligerTextBox({
			width : 100
		});
    	
		autoCompleteByData("#type", type_info.Rows, "id", "text", false, true,null,false,null,80);
        
    	autoCompleteByData("#state", state_info.Rows, "id", "text", false, true, true,false,null,80);
    	
		autodate("#start_time","YYYY-mm-dd","month_first");

		autodate("#end_time","YYYY-mm-dd","month_last");
    }  
    
   
    function clearData(){
    	/**
    	if(isnull($("#start_time").val())){
 			$.ligerDialog.error('开始日期不能为空!');
 			return;
 		} 
    	 
    	if(isnull($("#end_time").val())){
 			$.ligerDialog.error('结束日期不能为空!');
 			return;
 		} */
		$.ligerDialog.confirm('是否清空三个月以前的日志数据?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("clearHipSyncLog.do?isCheck=false",{"type_id" : "${type_id}"},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
			}
		});
    }
    
    function this_close(){
		frameElement.dialog.close();
	}

	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-left: 50px">
        <tr>
			<td align="left"  class="l-table-edit-td" style="padding-left: 20px;">日期：</td>
			<td align="left" class="l-table-edit-td" ><input name="start_time" type="text" id="start_time" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td >&nbsp;至：</td>
            <td><input name="end_time" type="text" id="end_time" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				 
			<td align="left"  class="l-table-edit-td" style="padding-left: 20px;">任务类型：</td>
			<td align="left"  class="l-table-edit-td"><input name="type"
				type="text" id="type" 
				 /></td>
			<td align="left"  class="l-table-edit-td" style="padding-left: 20px;">状态：</td>
			<td align="left"  class="l-table-edit-td"><input name="state"
				type="text" id="state" 
				 /></td>
			<td align="left"  class="l-table-edit-td" style="padding-left: 20px;">业务名称：</td>
			<td align="left"  class="l-table-edit-td"><input name="type_code"
				type="text" id="type_code" 
				 /></td>	 	 	  	
        </tr> 
    </table>
	<div id="maingrid"></div>
	
	<div id="note"></div>
</body>
</html>
