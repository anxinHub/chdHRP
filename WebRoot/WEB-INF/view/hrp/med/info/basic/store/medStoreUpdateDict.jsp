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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
	
    var dataFormat;
	var grid;
	var gridManager;
	var store_id;
    $(function (){
    	store_id = '${store_id}';
        loadDict();  
        loadForm();
        loadHead(null);
        
    });  
    
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
    	//根据表字段进行添加查询条件
    	grid.options.parms.push({name:'store_id',value : store_id}); 
		//加载查询条件
		grid.loadData(grid.where);
 	}

function loadHead(){
	grid = $("#maingrid").ligerGrid({
       columns: [ 
                 { display: '仓库编码', name: 'store_code', align: 'left'},
				 { display: '仓库名称', name: 'store_name', align: 'left' },
				 { display: '库房分类', name: 'type_code', align: 'left'},
				 { display: '排序号', name: 'sort_code', align: 'left'},
				 { display: '备注', name: 'note', align: 'left' },
				 { display: '是否停用', name: 'is_stop', align: 'left',
					 render : function(rowdata, rowindex,
								value) {
							if(rowdata.is_stop == 0){
								return "否";
							}else{
								return "是"
							}
						}
				 },
				 { display: '操作人', name: 'user_name', align: 'left' },
				 { display: '变更原因', name: 'dlog', align: 'left' },
				 
				 
                ],
                 dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedStoreDict.do?store_id='+store_id,
                 width: '100%', height: '100%', checkbox: false,rownumbers:false,
                 selectRowButtonOnly:true
               });

    	gridManager = $("#maingrid").ligerGetGridManager();
	}
     
    
    function loadDict(){

    	
     }  
    function loadForm(){
    	
    }
    
    
    
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            
        </table>
    </form>
    <div id="maingrid"></div>
    </body>
</html>
