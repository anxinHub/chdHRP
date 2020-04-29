<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    	loadHead(null);	//加载数据
    	$("#para_code").ligerTextBox({width:160});
    	$("#para_name").ligerTextBox({width:160});
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'para_code',value:$("#para_code").val()}); 
    	grid.options.parms.push({name:'para_name',value:$("#para_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '参数编码', name: 'para_code', align: 'left'
					 },
                     { display: '参数名称', name: 'para_name', align: 'left'
					 },
                     { display: '参数值', name: 'para_value', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								var str = "";
								if(rowdata.para_type == '0'){
									str = "<input name='para_value"+rowindex+"' type='text' id='para_value"+rowindex+"'"
										+" value='"+rowdata.para_value+"' style='margin-top:5px;'/>";
								}
								if(rowdata.para_type == '1'){
									var obj = eval('[' + rowdata.para_json + ']'); 
									var option = "";
									str = "<select id='para_value"+rowindex+"'  name = 'para_value"+rowindex+"' style='margin-top:5px;width:128;'>";
									for(var json in obj){
										if(rowdata.para_value == obj[json].code){
											str = str + "<option value='"+obj[json].code+"' selected='selected'>"+obj[json].value+"</option>";
										}else{
											str = str + "<option value='"+obj[json].code+"'>"+obj[json].value+"</option>";
										}
									}
									str = str + "</option>";
									
									
								}
								return str;
							}
					 },
                     { display: '参数说明', name: 'note', align: 'left'
					 },
                     { display: '操作', name: 'edit', align: 'center',
							render : function(rowdata, rowindex,
									value) {
									return "<div class='l-button' style='width: 60px; margin-top:1px; margin-left: 60px;' ligeruiid='Button1004'" 
									+"onclick=save('"+rowdata.group_id+"','"+rowdata.hos_id+"','"+rowdata.copy_code+"','"+rowdata.para_code+"','"+rowindex+"')>"
				       					+"<span>保存</span></div>";
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgPara.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	function save(group_id,hos_id,copy_code,para_code,rowindex){
		var para_value = $("#para_value"+rowindex+"").val();
		var reg = new RegExp("^[0-9][0-9]*$");
		if(rowindex == 0 && !reg.test(para_value)){
			$.ligerDialog.error("输入数据类型错误，请输入整数类型数据");
			return ;
		}
		var formPara = {
				group_id:group_id,
				hos_id:hos_id,
				copy_code:copy_code,
				para_code:para_code,
				para_value:para_value
		};
		 ajaxJsonObjectByUrl("updateBudgPara.do?isCheck=false",formPara,function(responseData){
	            if(responseData.state=="true"){
	                query();
	            }
	      });
	}
     
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">参数编码：</td>
            <td align="left" class="l-table-edit-td"><input name="para_code" type="text" id="para_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">参数名称：</td>
            <td align="left" class="l-table-edit-td"><input name="para_name" type="text" id="para_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
