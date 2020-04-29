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
	
	$(function() {
		
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
  	
    	grid.options.parms.push({name : 'state',value : liger.get("state").getValue()});
    	grid.options.parms.push({name : 'name',value : $("#name").val()});	
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	

	function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
			    {display: '编号', name: 'job_code', align: 'left', width: '120',
			    	 render : function(rowdata, rowindex,value) {
							return "<a href=javascript:open_update('" + rowdata.id +"')>"+rowdata.job_code+"</a>";
               	 }	
			    }, 
				{display: '任务名称', name: 'name', align: 'left',width:200}, 
				{display: '执行规则', name: 'cron', align: 'left',width:200}, 
				{display: '业务', name: 'type_name', align: 'left',width:100}, 
				{display: '业务时间', name: 'period', align: 'left',width:300, 
					render: function (col) { 
					 	switch(col.ptype){
						 	case 5:
						 		return col.period+"周";
						 		break;
						 	case 4:
						 		return col.period+"个月";
						 		break;
						 	case 3:
						 		return col.period+"天";
						 		break;
						 	case 2:
						 		return col.period+"小时";
						 		break;
						 	case 1:
						 		return col.period+"分钟";
						 		break;
						 	default :
						 		return col.period;
					 	}
	                }	
				}, 
				{display: '状态', name: 'state', align: 'left',width:220,
					 render: function (col) { 
						 	if(col.state==0)
						 		return "<span style='color: red;'>停止</span>";
						 	else if(col.state==1)
						 		return "运行中";
						 	else
						 		return col.state;
		                }
				},
				{display: '备注', name: 'note', align: 'left',width:250}
			 
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'query.do',
			width: '100%', height: '100%', checkbox: true, rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true } , 	
				{ text: '添加（<u>P</u>）', id:'add', click: addJob, icon:'add' },
				{ line:true },
				{ text: '启动（<u>E</u>）', id:'start', click: startJob, icon:'flowstart' },
				{ line:true },
				{ text: '停止（<u>S</u>）', id:'stop', click: stopJob, icon:'flowstop' },
				{ line:true },
				{ text: '删除（<u>S</u>）', id:'delete', click: remove, icon:'delete' },
				{ line:true }
				]},
				onDblClickRow : function (rowdata, rowindex, value){
					open_update(rowdata.id);
				} 
		});
		
        gridManager = $("#maingrid").ligerGetGridManager();
    }
	
	function addJob(){
		parent.$.ligerDialog.open({
			url: 'hrp/hip/dataJob/addPage.do', 
			width: 620,
			height: 400,
			title:'任务添加',
			modal:true,
			showToggle:false,
			showMax:false,
			showMin: false,
			isResize:true,
			buttons: [ 
			  	{ 
					text: '保存', 
					onclick: function (item, dialog) { 
						dialog.frame.save(); 
						query();
			  		},
			  		cls:'l-dialog-btn-highlight' 
			  	}, 
			  	{ 
				  	text: '关闭', 
				  	onclick: function (item, dialog) {
				  		dialog.close();
				  	} 
			  	} 
			 ],
			 parentframename:window.name 
		});
		
	}
	function open_update(id){
		parent.$.ligerDialog.open({
			url: 'hrp/hip/dataJob/updatePage.do?id='+id, 
			width: 620,
			height: 400,
			title:'任务编辑',
			modal:true,
			showToggle:false,
			showMax:false,
			showMin: false,
			isResize:true,
			buttons: [ 
			  	{ 
					text: '保存', 
					onclick: function (item, dialog) { 
						dialog.frame.save();
						query();
			  		},
			  		cls:'l-dialog-btn-highlight' 
			  	}, 
			  	{ 
				  	text: '关闭', 
				  	onclick: function (item, dialog) {
				  		dialog.close();
				  	} 
			  	} 
			 ],
			 parentframename:window.name
		});
		
	}
	
    function remove(){
    	var result=true;
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){
            	if(this.state==1){
            		result=false;
            	}
				ParamVo.push(
				this.id 
				)
			});
            if(!result){
    			$.ligerDialog.error("所选数据有任务执行中，请先停止任务再删除");
				return;
        	}
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("delete.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
    }
	
	function loadDict() {
		$("#state").ligerComboBox({  
	         data: [
				{"id" : "1","text" : "启动"}, 
				{"id" : "0","text" : "停止"}
	         ],
	         initWidth: 400
	     }); 
		
	}
	
	 //启动
   function startJob(){
		var data = gridManager.getCheckedRows();
		var ok=true;
		if (data.length == 0){
			$.ligerDialog.error('请选择要执行的任务！');
			return;
		}else{
			var ParamVo =[];
			var id = "";
			$(data).each(function (){		
				if(this.state != 0){
					ok=false;
					$.ligerDialog.error('您选择的数据状态有非停止状态，请检查后重新执行！');
					return false;
				}
				id+=  this.id + ",";
				ParamVo.push(
					this.id 
				) 
			});
			if(ok){
				$.ligerDialog.confirm('确定要执行任务?', function (yes){
					if(yes){
						ajaxJsonObjectByUrl("startDataJob.do",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								query();
							}
						});
					}
				}); 
			}
		}
	}
 
	function stopJob(){
		var data = gridManager.getCheckedRows();
		var ok=true;
		if (data.length == 0){
			$.ligerDialog.error('请选择要停止的任务！');
			return;
		}else{
			
			var ParamVo =[];
			var id = "";
			$(data).each(function (){		
				if(this.state != 1){
					ok=false;
					return false;
				}
				id+=  this.id + ",";
				ParamVo.push(
					this.id 
				) 
			});
			if(ok){
				$.ligerDialog.confirm('确定要停止任务?', function (yes){
					if(yes){
						ajaxJsonObjectByUrl("stopDataJob.do",{ParamVo : ParamVo.toString()},function (responseData){
							if(responseData.state=="true"){
								query();
							}
						});
					}
				}); 
			}else{
				$.ligerDialog.error('您选择的数据状态有非执行状态，请检查后重新执行！');
			}
		}
	}
	
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">

		<tr>
		

			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">任务名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="name" type="text" requried="false" id="name" />
			</td>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">状态：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="state" type="text" requried="false" id="state" />
			</td>
			

		</tr>
	
		
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
