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
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'nature_code',value:$("#nature_code").val()}); 
    	grid.options.parms.push({name:'nature_name',value:$("#nature_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#nature_code").val()!=""){
                		return rowdata.nature_code.indexOf($("#nature_code").val()) > -1;	
                	}
                	if($("#nature_name").val()!=""){
                		return rowdata.nature_name.indexOf($("#nature_name").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '成本习性编码', name: 'nature_code', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
 							return "<a href=javascript:openUpdate('"+rowdata.nature_id+ "|" +rowdata.nature_code +"')>"+rowdata.nature_code+"</a>";
                    	 }
					 },
                     { display: '成本习性名称', name: 'nature_name', align: 'left'
					 },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostDeptNature.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    				]} ,
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.nature_id 
							);
    				}  
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
			"nature_id="+vo[0] 
    	$.ligerDialog.open({ url : 'costDeptNatureUpdatePage.do?isCheck=false&' + parm,data:{}, height: 200,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostDeptNature(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
	    	$("#nature_code").ligerTextBox({width:160});
	    	$("#nature_name").ligerTextBox({width:160});
         }  

	
	 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本习性编码：</td>
            <td align="left" class="l-table-edit-td"><input name="nature_code" type="text" id="nature_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本习性名称：</td>
            <td align="left" class="l-table-edit-td"><input name="nature_name" type="text" id="nature_name" /></td>
        </tr> 
	
        <tr>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
					<th width="200">成本习性编码</th>
					<th width="200">成本习性名称</th>
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
