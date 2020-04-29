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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
       $("#charge_stan_code").change(function(){
    	   query()
       })
       $("#charge_stan_code").change(function(){
    	   query()
       })
       $("#type_code").change(function(){
    	   query()
       })
       $("#kind_code").change(function(){
    	   query()
       })
       $("#natur_code").change(function(){
    	   query()
       })
       $("#out_code").change(function(){
    	   query()
       })
       $("#is_manager").change(function(){
    	   query()
       })
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'charge_stan_code',value:liger.get("charge_stan_code").getValue()});
    	grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 
    	grid.options.parms.push({name:'kind_code',value:liger.get("kind_code").getValue()});
    	grid.options.parms.push({name:'natur_code',value:liger.get("natur_code").getValue()});
    	grid.options.parms.push({name:'out_code',value:liger.get("out_code").getValue()});
    	grid.options.parms.push({name:'is_manager',value:$("#is_manager").prop("checked")?1:""});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
			 		{ display: '科室编码', name: 'dept_code', align: 'left'},
			 		{ display: '科室名称', name: 'dept_name', align: 'left',},
			 		{ display: '部门分类', name: 'kind_name', align: 'left'},
			 		{ display: '部门类型', name: 'type_name', align: 'left'},
			 		{ display: '部门性质', name: 'natur_name', align: 'left'},
			 		{ display: '支出性质', name: 'out_name', align: 'left'} ,
			 		{ display: '是否职能科室', name: 'manager', align: 'left'} ,
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryChargeStanDeptSet.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,heightDiff: 30,isChecked: initCheck,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '保存', id:'add', click: addStanDeptSet, icon:'add' },
    	                { line:true }/* ,
		                { text: '打印', id:'print', click: printDate,icon:'print' },
		                { line:true },
		                { text: '下载导入模板', id:'downTemplate', click: downTemplate,icon:'down' },
		                { line:true },
		                { text: '导入', id:'import', click: imp,icon:'up' } */
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function initCheck(e){
    	if(e.charge_stan_code == liger.get("charge_stan_code").getValue()){
    		return true;
    	}else{
    		return false ;
    	}
    }
	//添加
	function addStanDeptSet(){
		var charge_stan_code = liger.get("charge_stan_code").getValue() ;
		if(!charge_stan_code){
			$.ligerDialog.error('费用标准必填');
			return false ;
		}
		var data = gridManager.getCheckedRows();
		if(data.length == 0){
			ajaxJsonObjectByUrl("deleteChargeStanDeptSet.do?isCheck=false&charge_stan_code="+charge_stan_code,{},function (responseData){
	       		if(responseData.state=="true"){
	       			query();
	       		}
	       	});
		}else{
			var ParamVo =[];
			$(data).each(function (){
				ParamVo.push(
				this.dept_id  +"@"+ 
				charge_stan_code  
				) 
			});
    
			ajaxJsonObjectByUrl("addChargeStanDeptSet.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
				if(responseData.state=="true"){
					query();
				}
			});
		}
		
	}

	//导入
	function imp(){
		$.ligerDialog.open({
    		url: 'budgDeptSetImportPage.do?isCheck=false', 
    		height: 500,width: 800, title:'导入',
    		modal:true, showToggle:false, showMax:false, showMin: false, isResize:true 
    	});
	}
	
    
    //字典加载
    function loadDict(){
        //字典下拉框
        
        //费用标准下拉框
    	autocomplete("#charge_stan_code","../../../queryBudgChargeStan.do?isCheck=false&charge_stan_nature=02","id","text",true,true,"",true);
        
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
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">费用标准<span style="color:red">*</span>:</td>
            <td align="left" class="l-table-edit-td"><input  name="charge_stan_code" type="text" id="charge_stan_code"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门类型:</td>
            <td align="left" class="l-table-edit-td"><input  name="type_code" type="text" id="type_code"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门分类:</td>
            <td align="left" class="l-table-edit-td"><input  name="kind_code" type="text" id="kind_code"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门性质:</td>
            <td align="left" class="l-table-edit-td"><input  name="natur_code" type="text" id="natur_code"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出性质:</td>
            <td align="left" class="l-table-edit-td"><input  name="out_code" type="text" id="out_code"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
            <td align="left" class="l-table-edit-td"><input id="is_manager"  name="is_manager" type="checkbox"  ltype="text" validate="{required:true,maxlength:20}"  />只显示职能科室</td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
