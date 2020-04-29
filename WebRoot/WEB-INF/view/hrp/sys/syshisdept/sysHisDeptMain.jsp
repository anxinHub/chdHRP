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
    	grid.options.parms.push({name:'his_dept_code',value:liger.get("his_dept_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: 'HIS科室编码', name: 'his_dept_code', align: 'left',},
                     { display: 'HIS科室名称', name: 'his_dept_name', align: 'left'},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'querySysHisDept.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    	                { text: '删除', id:'delete', click: remove,icon:'delete' },
    	                { line:true }, 
		                { text: '导入', id:'import', click: imp,icon:'up' },
    	                { line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function remove(){
     	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
				ParamVo.push(
				//表的主键
				this.his_dept_code
				)
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteSysHisDept.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }

    }

    function imp(){
    	var para={
			    "column": [
			        {
			            "name": "his_dept_code",
			            "display": "HIS科室编码",
			            "width": "200",
			            "require":true
			        },
			        {
			            "name": "his_dept_name",
			            "display": "HIS科室名称",
			            "width": "200",
			            "require":true
			        }
			    ]/* ,
			    isUpdate:true */
			};
			importSpreadView("hrp/sys/syshisdept/importSysHisDept.do?isCheck=false",para); 

       }

    function loadDict(){
            //字典下拉框
    	  $("#his_dept_code").ligerTextBox({ width:160 });
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">HIS科室编码：</td>
            <td align="left" class="l-table-edit-td"><input name="his_dept_code" type="text" id="his_dept_code" ltype="text" /></td>
            <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
