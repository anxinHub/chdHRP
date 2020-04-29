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
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
		
        $("#naturs_code").ligerTextBox({width:160});
        $("#naturs_name").ligerTextBox({width:160});
      
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'naturs_code',value:$("#naturs_code").val()}); 
    	  grid.options.parms.push({name:'naturs_name',value:$("#naturs_name").val()}); 
    	 /*  grid.options.parms.push({name:'naturs_store',value:$("#naturs_store").val()}); */ 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '性质编码', name: 'naturs_code', align: 'left',
                    	 render : function(rowdata, rowindex, value) {

								return "<a href=javascript:openUpdate('"
										+ rowdata.group_id + "|"
										+ rowdata.hos_id + "|"
										+ rowdata.copy_code + "|"
										+ rowdata.naturs_code + "')>"
										+ rowdata.naturs_code + "</a>";

							}
                    
					 		},
                     { display: '性质名称', name: 'naturs_name', align: 'left'
					 		}/* ,
					 		  { display: '维护仓库', name: 'naturs_store', align: 'left'
						 		} */
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssNaturs.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad :false,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true }
				    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	function openUpdate(obj) {

		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "&group_id=" + vo[0] + "&hos_id=" + vo[1] + "&copy_code="
				+ vo[2] + "&naturs_code=" + vo[3];

		$.ligerDialog.open({
			url : 'assNatursUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height: 500,
			width: 600, 
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveAssTypeDict();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

	}
    function loadDict(){
            //字典下拉框
            
         }  
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">性质编码：</td>
            <td align="left" class="l-table-edit-td"><input name="naturs_code" type="text" id="naturs_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">性质名称：</td>
            <td align="left" class="l-table-edit-td"><input name="naturs_name" type="text" id="naturs_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
           <!--   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">维护仓库：</td>
            <td align="left" class="l-table-edit-td"><input name="naturs_store" type="text" id="naturs_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td> -->
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
