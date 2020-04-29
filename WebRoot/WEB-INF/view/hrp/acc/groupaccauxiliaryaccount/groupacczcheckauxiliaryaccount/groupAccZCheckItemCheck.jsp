<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
		
    	
    	loadHead(null);	//加载数据
    	
    	$("#check_item_code").ligerTextBox({width:160});
    	
    	$("#check_item_name").ligerTextBox({width:160});
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'check_item_code',value:$("#check_item_code").val()}); 
    	grid.options.parms.push({name:'check_item_name',value:$("#check_item_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    //var item=true;
    function loadHead(){
    	var url = "";
    	//alert('${check_type_name}');
    	 if('${check_type_name}'=='部门'){
			 url = "../acccheckitem/queryAccCheckItemDept.do?isCheck=false";
		 }
    	 else if ('${check_type_name}'=='职工'){
    		 url="../acccheckitem/queryAccCheckItemEmp.do?isCheck=false";
    	 }
    	 else if('${check_type_name}'=='项目'){
    		 url="../acccheckitem/queryAccCheckItemProj.do?isCheck=false";
    	 }
    	 else if('${check_type_name}'=='库房'){
    		 url="../acccheckitem/queryAccCheckItemStore.do?isCheck=false";
    	 }
    	 else if('${check_type_name}'=='客户'){
    		 url="../acccheckitem/queryAccCheckItemCus.do?isCheck=false";
    	 }
    	 else if('${check_type_name}'=='供应商'){
    		 url="../acccheckitem/queryAccCheckItemSup.do?isCheck=false";
    	 }
    	 else if('${check_type_name}'=='资金来源'){
    		 url="../acccheckitem/queryAccCheckItemSource.do?isCheck=false";
    	 }
    	 else if('${check_type_name}'=='单位'){
    		 url="../acccheckitem/queryAccCheckItemHos.do?isCheck=false";
    	 }
		 else{
			 url = "../acccheckitem/queryAccCheckItem.do?isCheck=false&check_type_id=${check_type_id}";
		 }
    	grid = $("#maingrid").ligerGrid({
           columns: [
                     { display: '核算项编码', name: 'check_item_code', align: 'left'
					 },
                     { display: '核算项名称', name: 'check_item_name', align: 'left'
					 }
                     ],

                     dataAction: 'server',dataType: 'server',usePager:true,url:url,

                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function loadDict(){
            //字典下拉框
            
         } 
    
    function saveSelectData(){
    	
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return false ;
        }else if (data.length > 300){
        	$.ligerDialog.error('选择的数据不能超过300行');
        	return false ;
        }else{
            var paramVo_id =[];
            var paramVo_text =[]; 
            $(data).each(function (){					
            	paramVo_id.push(this.check_item_id);
            	paramVo_text.push(this.check_item_code+" "+this.check_item_name); 
			});
            
            parent.liger.get("check_item_code").setValue(paramVo_id.toString());
            parent.liger.get("check_item_code").setText(paramVo_text.toString());
            
        }
    	
        parent.liger.get("accZCheckItemCheck").close();
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<!--div id="toptoolbar" ></div-->
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算项编码：</td>
            <td align="left" class="l-table-edit-td"><input name="check_item_code" type="text" id="check_item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算项名称：</td>
            <td align="left" class="l-table-edit-td"><input name="check_item_name" type="text" id="check_item_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>
</body>
</html>
