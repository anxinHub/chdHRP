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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'status_name',value:$("#status_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#status_name").val()!=""){
                		return rowdata.inv_name.indexOf($("#inv_status_namename").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '紧急程度', name: 'status_name', align: 'left',frozen:true,
					 		},
                     { display: '报修到分配(小时)', name: 'bdf_hour', align: 'left',editor:{
                    	 type:'int'
                     }},
                     { display: '分配到接单(小时)', name: 'fdx_hour', align: 'left',editor:{
                    	 type:'int'
                     }},
                     { display: '维修到完成(小时)', name: 'xdw_hour', align: 'left',editor:{
                    	 type:'int'
                     }},
					 { display: '紧急程度id', name: 'status_id', align: 'left',hide:true
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssStatus.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,enabledEdit : true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    	{ text: '保存', id:'save', click: save, icon:'save' }
				    	]}
    				
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
   
    	
    function save(){
    	
    	var data = gridManager.getData();

         $.ligerDialog.confirm('确定保存?', function (yes){
          	if(yes){
              	ajaxJsonObjectByUrl("saveAssStatus.do",{ParamVo : JSON.stringify(data)},function (responseData){
              		if(responseData.state=="true"){
              			query();
              		}
              	});
          	}
          }); 
               
    }
   
   
    function loadDict(){
            //字典下拉框
    	$("#status_name").ligerTextBox({width:160});
     }  
    //键盘事件
	  function loadHotkeys() {

		
	 }
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
       
        <!-- <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">紧急程度：</td>
            <td align="left" class="l-table-edit-td"><input name="status_name" type="text" id="status_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>  -->
        
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
