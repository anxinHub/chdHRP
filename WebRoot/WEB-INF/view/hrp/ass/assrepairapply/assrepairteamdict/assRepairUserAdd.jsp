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
<script src="<%=path %>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<script language="javascript" src="<%=path%>/lib/Lodop/LodopFuncs.js"></script>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    var para;
    
    $(function ()
    {
    	loadHead(null);	//加载数据
    	
    	$("#para_code").ligerTextBox({width:160});
    	
    	$("#emp_code").ligerTextBox({width:160});
    	
    	para = '${rep_team_code}';
    	
    	loadDict();
    	
    	/* $('input:radio[name="choose_box"]').bind("change",function(){
    		query();
		}) */
    	
    });
    //查询
    function  query(){
 		
    		grid.options.parms=[];
    		
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'rep_team_code',value:para}); 
        	
       	grid.options.parms.push({name:'user_name',value:$("#user_name").val()});  
       	
       	 
       	//grid.options.parms.push({name:'kind_code',value:liger.get("emp_kind").getValue()});  
       	
        	
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){

        grid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '人员编码', name: 'user_code', align: 'left'
 					 },
                      { display: '人员名称', name: 'user_name', align: 'left'
 					 },
                      { display: '院内电话', name: 'phone1', align: 'left' ,editor : {
 							type : 'text',
 						},
			 		 },
              		  { display: '联系电话', name: 'phone2', align: 'left',editor : {
 							type : 'text',
 						},
				     }
                      ],
                      dataAction: 'server',
                      dataType: 'server',
                      usePager:true,
                      url:'querySysUserNotExists.do?isCheck=false',
                      width: '100%', 
                      height: '94%', 
                      enabledEdit : true ,
                      checkbox: true,
                      rownumbers:true,
                      delayLoad:true,
                      selectRowButtonOnly:true,//heightDiff: -10,
                      toolbar: { items: [
                      	{ text: '查询', id:'search', click: query,icon:'search' },
                      	{ line:true }
                      	
     				]}
                    });

        gridManager = $("#grid").ligerGetGridManager();
    }
    
    function save(){
    	
    	var data = grid.getCheckedRows();
    	
        if (data.length == 0){
        	
        	$.ligerDialog.error('请选择行');
        	
        }else{
        	
            var ParamVo =[];
            
            $(data).each(function (){
            	if(this.phone1===undefined){
            		this.phone1='';
            	}
            	if(this.phone2===undefined){
            		this.phone2='';
            	}
				ParamVo.push(
						para  +"@"+  
						this.user_id +"@"+  
						this.phone1  +"@"+  
						this.phone2    
				);
            });
            console.log(ParamVo.toString())
             ajaxJsonObjectByUrl("addRepairUser.do?isCheck=false",{ParamVo:ParamVo.toString()},function (responseData){
                if(responseData.state=="true"){
                	
                	query();
                	
                	parent.queryRepUser();
                	
                }
               
             });
            	
        }
    	
    }
    
	
	
	
	function loadDict(){
		
		//autocomplete("#dept_code","../queryDeptDictNo.do?isCheck=false","id","text",true,true);
        
		//autocomplete("#emp_kind","../../sys/queryEmpKindDict.do?isCheck=false","id","text",true,true);
        
	}
     
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_kind" type="text" id="emp_kind" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td> 
        </tr> 
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用户：</td>
            <td align="left" class="l-table-edit-td"><input name="user_name" type="text" id="user_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
    </table>
	<div id="maingrid"></div>


</body>
</html>
