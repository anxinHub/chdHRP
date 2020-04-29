<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE>
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
	<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
     var grid;
     var gridManager = null;
     $(function (){
        loadForm();
        loadDict();
        loadHead(null);	
        query();
     });  

	function loadForm(){
	    
	    $.metadata.setType("attr", "validate");
	     var v = $("form").validate({
	         errorPlacement: function (lable, element)
	         {
	             if (element.hasClass("l-textarea"))
	             {
	                 element.ligerTip({ content: lable.html(), target: element[0] }); 
	             }
	             else if (element.hasClass("l-text-field"))
	             {
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }
	             else
	             {
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable)
	         {
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function ()
	         {
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
	    //$("form").ligerForm();
	 }       

	//查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'show_id',
			value : $("#show_id").val()
		});

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    
    function loadDict(){
    	
    	$("#show_name").ligerTextBox({width: 160, disabled: true});
     }  
  
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [{ 
					display: '业务类型编码', name: 'bus_type_code', width: '40%'
				}, { 
					display: '业务类型名称', name: 'bus_type_name', width: '56%'
				} ],
			dataAction: 'server',dataType: 'server',usePager: false,url:'queryMedShowDetailByCode.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			isChecked: function(rowdata){
				if(rowdata.is_choose == 1){
					return true;
				}else{
					return false;
				}
			},
			toolbar: { items: [
			   	{ text: '保存（<u>A</u>）', id:'add', click: save, icon:'save' },
			   	{ line:true },
			   	{ text: '关闭（<u>C</u>）', id:'close', click: this_close, icon:'close' },
			   	{ line:true }
			]}
		});
		
        gridManager = $("#maingrid").ligerGetGridManager();
    }
	
    function save(){
        var formPara={
			group_id : $("#group_id").val(),
			hos_id : $("#hos_id").val(),
			copy_code : $("#copy_code").val(),
			show_id : $("#show_id").val(),
			detailData : JSON.stringify(gridManager.getSelectedRows())
		};
        ajaxJsonObjectByUrl("addMedShowDetail.do?isCheck=false",formPara,function(responseData){
            if(responseData.state=="true"){
                parent.query();
            }
        });
    }
    
	function this_close(){
		frameElement.dialog.close();
	}

    </script>

	</head>
  
	<body>
		<div id="pageloading" class="l-loading" style="display: none"></div>
		<div >
			<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
			            <td style="display: none;">
			            	<input name="group_id" type="text" id="group_id" value="${group_id}" ltype="text" />
			            	<input name="hos_id" type="text" id="hos_id" value="${hos_id}" ltype="text" />
			            	<input name="copy_code" type="text" id="copy_code" value="${copy_code}" ltype="text" />
			            	<input name="show_id" type="text" id="show_id" value="${show_id}" ltype="text" />
			            </td>
					</tr>
			    	<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:50px;">
			            	显示名称：
			            </td>
			            <td align="left" class="l-table-edit-td">
			            	<input name="show_name" type="text" id="show_name" value="${show_name}" ltype="text"/>
			            </td>
			        </tr> 
					</table>
			    </form>
		</div>
		<div style="width: 100%; height: 100%;">
			<div id="maingrid"></div>
		</div>
	</body>
</html>
