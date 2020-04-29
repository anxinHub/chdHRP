<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
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
                     { display: '参数编码', name: 'para_code', align: 'left',width:60
					 },
                     { display: '参数名称', name: 'para_name', align: 'left',width:400
					 },
                     { display: '参数值', name: 'para_value', align: 'center',width:170,
							render : function(rowdata, rowindex,
									value) {
								var str = "";
								if(rowdata.para_type == '0'){
									str = "<input name='para_value"+rowindex+"' type='text' id='para_value"+rowindex+"'"
										+" value='"+rowdata.para_value+"' style='margin-top:5px;width:150;'/>";
								}
								if(rowdata.para_type == '1'){
									var obj = eval('[' + rowdata.para_json + ']'); 
									var option = "";
									str = "<select id='para_value"+rowindex+"'  name = 'para_value"+rowindex+"' style='margin-top:5px;width:150;'>";
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
                     { display: '参数说明', name: 'note', align: 'left',width:380
					 },
                     { display: '操作', name: 'edit', align: 'left',width:100,
							render : function(rowdata, rowindex,
									value) {
									return "<div class='l-button' style='width: 60px; margin-top:1px; margin-left: 20px;' ligeruiid='Button1004'" 
									+"onclick=save('"+rowdata.group_id+"','"+rowdata.hos_id+"','"+rowdata.copy_code+"','"+rowdata.para_code+"','"+rowindex+"')>"
				       					+"<span>保存</span></div>";
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmPara.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '保存', id:'add', click: add_open, icon:'add' },
    	                { line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function add_open(){
    	grid.endEdit();
    	
    	
    	var updateData = grid.getData();
    	var ParamVo =[];
		if(updateData.length > 0){
            $(updateData).each(function (i,v){	
            	
            	this.para_value = $("#para_value"+i+"").val();
					ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.para_code   +"@"+ 
					this.para_value  
					) });
            
            
    	}
    	
    	
    	/*alert(ParamVo.toString().length); */
    	if(ParamVo.toString().length >0 ){
    		ajaxJsonObjectByUrl("saveBatchHpmPara.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
        		if(responseData.state=="true"){
        			query();
        		}
        	});
    	}else{
    		$.ligerDialog.error('无数据需要保存');
    	}
    	
    }
    
	function save(group_id,hos_id,copy_code,para_code,rowindex){
		var para_value = $("#para_value"+rowindex+"").val();
		var reg = new RegExp("^[0-9][0-9]*$");
		if(rowindex == 0 && !reg.test(para_value)){
			alert("输入数据类型错误，请输入整数类型数据");
			return ;
		}
		var formPara = {
				group_id:group_id,
				hos_id:hos_id,
				copy_code:copy_code,
				para_code:para_code,
				para_value:para_value
		};
		 ajaxJsonObjectByUrl("updateHpmPara.do",formPara,function(responseData){
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
