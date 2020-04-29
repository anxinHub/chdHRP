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
    
    $(function ()
    {
		 
    	loadHead(null);	//加载数据
    	
    });
    
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'rela_code',value:$("#rela_code").val()});  
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
     
  //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#rela_code").val()!=""){
                		return rowdata.rela_code.indexOf($("#rela_code").val()) > -1;	
                	} 
        };
        return clause; 
    }
  
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '单位ID', name: 'hos_id', align: 'left',hide:true
					 },
                     { display: '单位编码', name: 'hos_code', align: 'left',
					 },
                     { display: '单位名称', name: 'hos_name', align: 'left' 
					 }, 
					 { display: '账套编码', name: 'copy_code', align: 'left',
					 },
					 { display: '账套名称', name: 'copy_name', align: 'left',
				     }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'querySysHosCopyRela.do?rela_code=${rela_code}' ,
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,isChecked: f_isChecked,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
              					{ text: '保存（<u>A</u>）', id:'add', click: addSet, icon:'add' },
              	                { line:true },
              			]}
                  }); 

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function f_isChecked(rowdata){ 
    	//console.log(rowdata.rela_code)
    	if(rowdata.rela_code != "" && rowdata.rela_code != null){
    		return true;
    	}
    	 
        return false ;
    }
    
	function addSet(){
		var data = gridManager.getCheckedRows();
		
    	var ParamVo =[];
        if (data.length == 0){
        	ParamVo.push('null'+"@"+'${group_id}'+ "@"+ '${rela_code}' ) 
        }else{
            $(data).each(function (){					
				ParamVo.push(
						'${group_id}'+ "@"+ '${rela_code}' + "@"+ this.hos_id +"@"+ this.copy_code
				) });
    	}
        
        ajaxJsonObjectByUrl("addSysHosCopyRela.do?isCheck=true", {ParamVo : ParamVo.toString()},function(responseData){
            if(responseData.state=="true"){
            	loadHead(null);
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">对应关系编码：</td>
            <td align="left" class="l-table-edit-td"><input name="rela_code" type="text" id="rela_code" ltype="text"  value="${rela_code},${rela_name}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td> 
        </tr> 
          
    </table>

	<div id="maingrid"></div>

</body>
</html>
