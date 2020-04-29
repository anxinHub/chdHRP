<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function () 
    {
    	loadHead(null);	
    });
    
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
	  	grid.options.parms.push({name:'ass_id',value:'${ass_id}'}); 
		grid.loadData(grid.where);
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '验收项目编码', name: 'accept_item_code', align: 'left',width:'400'
					 		},
                     { display: '验收项目名称', name: 'accept_item_name', align: 'left',width:'400'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryAssAcceptItemDictByAffi.do?ass_id=${ass_id}',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :false,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [ {
 											text : '引入',
 											id : 'initInstall',
 											click :initInstall ,
 											icon : 'refresh'
 										},
 				                        { line:true },
 				    	                { text: '删除', id:'delete', click: remove,icon:'delete' }
 				    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
    function remove(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var i = 0;
			$(data).each(
					function() {
						if (isnull(this.group_id)) {
							gridManager.deleteSelectedRow();
						} else {
							ParamVo.push(this.group_id + "@"
									+ this.hos_id + "@"
									+ this.copy_code + "@"
									+ this.accept_item_code + "@"
									+ "${ass_id}");
						}
						i++;
					});
			if (ParamVo == "") {
				return;
			}
			$.ligerDialog
					.confirm(
							'确定删除?',
							function(yes) {
								if (yes) {
									ajaxJsonObjectByUrl(
											"deleteAssAcceptItemAffi.do?isCheck=false",
											{
												ParamVo : ParamVo
														.toString()
											},
											function(responseData) {
												if (responseData.state == "true") {
													query();
												}
											});
								}
							});
		}
    }
    
    
    function initInstall(){
    	parent.$.ligerDialog.open({
			title: '引入验收项目',
			height : 400,
			width : 1000,
			url: 'assDictImportAcceptItemPage.do?isCheck=false&ass_id=${ass_id}',
			modal: false, showToggle: false, showMax: false, showMin: true, isResize: true,
			parentframename: window.name,top:90,
			buttons: [ { text: '确定', onclick: function (item, dialog) { 
				var obj = {};
				var data = grid.getData();
				dialog.frame.importAcceptItemAffi(obj);
				
				if(data.length != 0){
					
					for(var j = 0; j < obj.gridData.length; j++){
						var flag = true;
						for(var i = 0 ;i < data.length; i++){
							if(data[i].accept_item_code == obj.gridData[j].accept_item_code){
								flag = false;
							}
						}
						if(flag){
							grid.addRows({accept_item_code:obj.gridData[j].accept_item_code,
								accept_item_name:obj.gridData[j].accept_item_name});
						}
					}
					
				}else{
					$.each(obj.gridData,function(i,v){
						grid.addRows({accept_item_code:v.accept_item_code,
							accept_item_name:v.accept_item_name});
					});
				}
			},cls:'l-dialog-btn-highlight' }, 	
			{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ] 
		}); 
    }
    function getData(){
    	return grid.getData();
    }
    function clearData(){
    	return grid.loadData();
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="maingrid"></div>
</body>
</html>
