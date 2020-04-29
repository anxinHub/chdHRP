<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<title></title>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
	    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    
    	 $(':button').ligerButton({width:80});
    });
  
    
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
    	//根据表字段进行添加查询条件
		grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(",")[0]}); 

		//加载查询条件
		grid.loadData(grid.where);
 	}

    function loadHead(){

        grid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '科室编码', name: 'dept_code', align: 'left'
 					 },
                      { display: '科室名称', name: 'dept_name', align: 'left'
 					 }
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true,url:'queryDeptDictSet.do?isCheck=false&store_ref_dept=0&store_id=${store_id}',
                      width: '100%', height: '90%', checkbox: true,rownumbers:true,
                      selectRowButtonOnly:true,
                      toolbar: { items: [
       					{ text: '保存', id:'save', click: save, icon:'add' },
       				]}
                    });

         gridManager = $("#maingrid").ligerGetGridManager();
    }

    function save(){
    	var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
							ParamVo.push(this.dept_id+"@"+"${store_id}");
						
					});
				ajaxJsonObjectByUrl("addMatRefStoreDept.do?isCheck=false", {
					ParamVo : ParamVo.toString()
				}, function(responseData) {
					if (responseData.state == "true") {
						parent.query2();
						query();
					}
				});
		}
    }
    
    function loadDict(){
            //字典下拉框
	autocomplete("#dept_id", "../../queryMatDept.do?isCheck=false", "id", "text", true, true);
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
         	<table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-top: 20px;">
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室信息：</td>
		            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"  /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
		            <td align="left" class="l-table-edit-td"><button  onclick="query();"><b>查询</b></button></td>
		            <td align="left"></td>
		        </tr> 
    		</table>
    		
    		<div id="maingrid"></div>

</body>
</html>
