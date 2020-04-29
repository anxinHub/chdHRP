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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
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
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'show_name',
			value : $("#show_name").val()
		});

    	//加载查询条件
    	grid.loadData(grid.where);
     }
	
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '编码', name: 'show_id', align: 'left', 
				}, { 
					display: '显示名称', name: 'show_name', align: 'left', 
					render : function(rowdata, rowindex, value) {
						return '<a href=javascript:update_open("' 
							+ rowdata.group_id 
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code 
							+ ',' + rowdata.show_id
							+ '")>'+rowdata.show_name+'</a>';
					}
				}, { 
		 			display: '是否显示', name: 'show_flag', align: 'left', 
		 			render : function(rowdata, rowindex, value){
		 				return value == 1 ? "是" : "否";
		 			}
		 		}, { 
		 			display: '方向', name: 'direction_flag', align: 'left', 
		 			render : function(rowdata, rowindex, value){
		 				return value == 1 ? "增加" : "减少";
		 			}
		 		}, { 
		 			display: '设置', name: '', align: 'left',
		 			render : function(rowdata, rowindex, value) {
						return '<a href=javascript:real_open("' 
							+ rowdata.group_id 
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code 
							+ ',' + rowdata.show_id
							+ ',' + rowdata.show_name
							+ '")>设置对应业务</a>';
					}
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedShowSet.do',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: false,//初始化加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				{ line:true },
				{ text: '删除（<u>D</u>）', id:'delete', click: remove, icon:'delete' },
				{ line:true }
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add_open);
		hotkeys('D', remove);
	}
    
    function add_open(){
    	
    	$.ligerDialog.open({
			title: '添加',
			height: 400,
			width: 500,
			url: 'addPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true, top: '20%'
		});   
	}
    
    function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"show_id="+vo[3] ;
		$.ligerDialog.open({
			title: '修改',
			height: 400,
			width: 500,
			url: 'updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true, top :  '20%'
		});   
    }
    
    function real_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"show_id="+vo[3] +"&"+ 
			"show_name="+vo[4] ;
		$.ligerDialog.open({
			title: '业务对应关系设置',
			height: 500,
			width: 700,
			url: 'busRealPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true, top :  '10%'
		});   
    }
    	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			$(data).each(function (){		
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.show_id
				) 
			});
			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteMedShowSet.do",{ParamVo : ParamVo.toString()},function (responseData){
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
        $("#show_name").ligerTextBox({width:160});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-left: 50px">
        <tr>
            <td align="right" class="l-table-edit-td" >
            	显示名称：
            </td>
            <td align="left" class="l-table-edit-td" >
				<input name="show_name" type="text" id="show_name" ltype="text" validate="{required:false,maxlength:100}" />
	        </td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
