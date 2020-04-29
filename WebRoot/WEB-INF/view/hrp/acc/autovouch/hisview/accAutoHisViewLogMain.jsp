<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
    var userUpdateStr;
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
       
		grid.options.parms.push({name:'his_log_code',value:liger.get("his_log_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
	
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					    display: 'HIS视图编码', name: 'his_log_code', align: 'left'
				      },{
					    display: 'HIS视图名称', name: 'his_log_name', align: 'left' 
				      },{
					    display: '未取到时间', name: 'his_log_date', align: 'left' 
				     }],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccHisViewLog.do',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
			   				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
			   				{ line:true },
			   				{ text: '删除（<u>D</u>）', id:'delete', click: remove, icon:'delete' },
			   				{ line:true },
			   				{ text: '设置（<u>S</u>）', id:'setting', click: setting, icon:'settings' },
			   				{ line:true },
			   				{ text: '执行（<u>R</u>）', id:'runJob', click: runJob, icon:'coffee' },
			   				{ line:true },
							{ text: '打印', id:'print', click: printDate,icon:'print'}
			   			]}

		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('D', remove);
		hotkeys('S', setting);
	}
    function loadDict(){
    	autocomplete("#his_log_code", "../../queryAccHisLog.do?isCheck=false","id", "text", true, true);
    	$("#his_log_code").ligerTextBox({width:180 });
    	
    }  
    
    function remove(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var error_str = "";
			$(data).each(function (){
				
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.his_log_code +"@"+
					this.his_log_date
				);
			});
			
				$.ligerDialog.confirm('确定删除?', function (yes){
					if(yes){
						ajaxJsonObjectByUrl("deleteAccHisViewLog.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								query();
							}
						});
					}
				});
			
		}

    }
    function setting(){
    	
    	parent.$.ligerDialog
		.open({
			title : '设置',
			height : 650,
			width : 800,
			url : 'hrp/acc/autovouch/hisview/accAutoHisViewSettingPage.do?isCheck=false',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			parentframename : window.name
		});
    }
    
    function runJob(){
    	
    	parent.$.ligerDialog
		.open({
			title : '执行',
			height : 400,
			width : 500,
			url : 'hrp/acc/autovouch/hisview/runJobPage.do?isCheck=false',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			parentframename : window.name
		});
    	
    }
    
    function printDate(){
		 if(grid.getData().length==0){
 		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
 	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
	      		  ]
	      	};
	   		
		var printPara={
			rowCount:1,
			title:'HIS视图执行情况',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.autovouch.AccAutoHisLogMainService",
			method_name: "queryAccHisViewLogPrint",
			bean_name: "accAutoHisLogMainService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
			};
 	
		//执行方法的查询条件
		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
 	});
		
 	officeGridPrint(printPara); 
	
	}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-left: 50px">
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b><font color="red"></font></b>视图：</td>
            <td align="left" class="l-table-edit-td">
            	<input id="his_log_code" name="his_log_code" />
            </td>
            
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
