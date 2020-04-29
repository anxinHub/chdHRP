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
<script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
        
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 
    	grid.options.parms.push({name:'kind_code',value:liger.get("kind_code").getValue()});
    	grid.options.parms.push({name:'natur_code',value:liger.get("natur_code").getValue()});
    	grid.options.parms.push({name:'out_code',value:liger.get("out_code").getValue()});
    	grid.options.parms.push({name:'is_budg',value:$("#is_budg").prop("checked")?1:0});
    	grid.options.parms.push({name:'is_manager',value:$("#is_manager").prop("checked")?1:0});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					{ display: '是否预算科室', name: 'budg', align: 'center',
							render: function(rowdata, rowindex,value) {
							if(rowdata.budg == '√'){
								return "<input id=is_budg"+rowdata.dept_code+"  type ='checkbox' checked='checked' onchange=\"checkEnable("+rowdata.dept_code+")\" style='margin-top:8px;'>";
							}else{
								return "<input id=is_budg"+rowdata.dept_code+"  type ='checkbox' onchange=\"checkEnable("+rowdata.dept_code+")\" style='margin-top:8px;'>";
							}
							}
					},
                    { display: '部门类型', name: 'type_name', align: 'left'},
			 		{ display: '部门编码', name: 'dept_code', align: 'left'},
			 		{ display: '部门名称', name: 'dept_name', align: 'left'},
			 		{ display: '部门分类', name: 'kind_name', align: 'left'},
			 		{ display: '部门性质', name: 'natur_name', align: 'left'},
			 		{ display: '支出性质', name: 'out_name', align: 'left'},
			 		{ display: '部门主管', name: 'emp_name', align: 'left'},
                    { display: '是否职能科室', name: 'manager', align: 'center'}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryBudgDeptSet.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,heightDiff: 30,
                     onAfterShowData : initCheckEnable, 
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '保存', id:'add', click: updateBudgDeptSet, icon:'add' },
    	                { line:true },
		                { text: '打印', id:'print', click: printDate,icon:'print' },
		                { line:true }/* ,
		                { text: '下载导入模板', id:'downTemplate', click: downTemplate,icon:'down' },
		                { line:true },
		                { text: '导入', id:'import', click: imp,icon:'up' } */
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	//添加
	function updateBudgDeptSet(){
		var data = gridManager.getData();
		 var ParamVo =[];
         $(data).each(function (){
        	 	var is_budg = 0;
      	 		if($("#is_budg"+this.dept_code+"").prop("checked")){
      	 			is_budg = 1;
      	 		};
  	 			ParamVo.push(
 					this.group_id   +"@"+ 
 					this.hos_id   +"@"+ 
 					this.dept_id  +"@"+ 
 					(this.type_code?this.type_code:"")  +"@"+
 					is_budg  +"@"+
 					(this.natur_code? this.natur_code:"") +"@"+
 					(this.out_code? this.out_code:"")  +"@"+
 					(this.emp_id? this.emp_id:"")  +"@"+
 					(this.is_manager? this.is_manager:"")  +"@"+
 					(this.is_stock? this.is_stock:"") +"@"+
 					(this.para_code? this.para_code:"-1")
 					) 
         });
         
      	ajaxJsonObjectByUrl("updateBudgDeptSet.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
       		if(responseData.state=="true"){
       			query();
       		}
       	});
         
	}

	//导入
	function imp(){
		$.ligerDialog.open({
    		url: 'budgDeptSetImportPage.do?isCheck=false', 
    		height: 500,width: 800, title:'导入',
    		modal:true, showToggle:false, showMax:false, showMin: false, isResize:true 
    	});
	}
	
	//打印
	function printDate(){
		if(grid.getData().length==0){
			$.ligerDialog.error("无打印数据！");
			return;
		}
	    
    	grid.options.parms=[];
    	grid.options.newPage=1;
		
    	grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 
    	grid.options.parms.push({name:'kind_code',value:liger.get("kind_code").getValue()});
    	grid.options.parms.push({name:'natur_code',value:liger.get("natur_code").getValue()});
    	grid.options.parms.push({name:'out_code',value:liger.get("out_code").getValue()});
    	grid.options.parms.push({name:'is_budg',value:$("#is_budg").prop("checked")?1:0});
    	grid.options.parms.push({name:'is_manager',value:$("#is_manager").prop("checked")?1:0});
        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"预算科室 定义",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryBudgDeptSet.do?isCheck=false", selPara, function(responseData) {
   			printGridView(responseData,printPara);
    	});
	}
	
	//选中某科室，则其全部下级科室选择框被禁用；取消选中时，其全部下级选择框取消禁用
	function checkEnable(code){
		var data = grid.getData();
		if($("#is_budg"+code+"").prop("checked")){
	 			$(data).each(function(){
	 				var dept_code = this.dept_code ;
	 				if(dept_code.indexOf(code) == 0 && dept_code != code){
	 					$("#is_budg"+this.dept_code+"").prop("checked",false);
	 					$("#is_budg"+this.dept_code+"").prop("disabled",true);
	 				}
	 			})
	 	}else{
	 		$(data).each(function(){
	 			var dept_code = this.dept_code ;
 				if(dept_code.indexOf(code) == 0 && dept_code != code && this.is_budg == 0){
 					$("#is_budg"+this.dept_code+"").prop("checked",false);
 					$("#is_budg"+this.dept_code+"").prop("disabled",false);
 				}
 			})
	 	} 
	}
	
	//grid加载完数据后  禁用是 否预算科室列 相应的checkbox
	
	function initCheckEnable(){
		var data = grid.getData();
		$.each(data,function(i, v){
			var code = v.dept_code;
			if($("#is_budg"+code+"").prop("checked")){
	 			$(data).each(function(){
	 				var dept_code = this.dept_code ;
	 				if(dept_code.indexOf(code) == 0 && dept_code != code){
	 					$("#is_budg"+this.dept_code+"").prop("checked",false);
	 					$("#is_budg"+this.dept_code+"").prop("disabled",true);
	 				}
	 			})
		 	}else{
		 		if($("#is_budg"+code+"").prop("disabled")){
		 			
		 			$(data).each(function(){
			 			var dept_code = this.dept_code ;
			 			
		 				if(dept_code.indexOf(code) == 0 && dept_code != code){
		 					$("#is_budg"+this.dept_code+"").prop("checked",false);
		 					$("#is_budg"+this.dept_code+"").prop("disabled",true);
		 				}
		 			})
		 		}else{
		 			$(data).each(function(){
			 			var dept_code = this.dept_code ;
		 				if(dept_code.indexOf(code) == 0 && dept_code != code && this.is_budg == 0){
		 					$("#is_budg"+this.dept_code+"").prop("checked",false);
		 					$("#is_budg"+this.dept_code+"").prop("disabled",false);
		 				}
		 			})
		 		}
		 	} 
		})
	}
	
	
	//下载模板
	function downTemplate(){
		location.href = "downTemplate.do?isCheck=false";
	}
	
    
    //字典加载
    function loadDict(){
        //字典下拉框
        
        //部门类型下拉框
    	autocomplete("#type_code","../../../queryBudgDeptType.do?isCheck=false","id","text",true,true);
        //部门分类
    	autocomplete("#kind_code","../../../queryBudgDeptKind.do?isCheck=false","id","text",true,true);
    	//部门性质下拉框
    	autocomplete("#natur_code","../../../queryBudgDeptNature.do?isCheck=false","id","text",true,true);
    	//支出性质下拉框
    	autocomplete("#out_code","../../../queryBudgDeptOut.do?isCheck=false","id","text",true,true);
    }  
    
  	
		  
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门类型：</td>
            <td align="left" class="l-table-edit-td"><input  name="type_code" type="text" id="type_code"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门分类：</td>
            <td align="left" class="l-table-edit-td"><input  name="kind_code" type="text" id="kind_code"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门性质：</td>
            <td align="left" class="l-table-edit-td"><input  name="natur_code" type="text" id="natur_code"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出性质：</td>
            <td align="left" class="l-table-edit-td"><input  name="out_code" type="text" id="out_code"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="left"></td>
            <td align="left" class="l-table-edit-td"><input  name="is_budg" type="checkbox" id="is_budg"  ltype="text" />只显示预算科室</td>
            <td align="left" class="l-table-edit-td"><input  name="is_manager" type="checkbox" id="is_manager"  ltype="text" />只显示职能科室</td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
