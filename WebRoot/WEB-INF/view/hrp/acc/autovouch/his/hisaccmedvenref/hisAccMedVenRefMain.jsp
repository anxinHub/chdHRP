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
    	
		$("#ven_code").ligerTextBox({width:180});
    	
    	$("#ven_name").ligerTextBox({width:180});
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        
    	grid.options.parms.push({name:'ven_code',value:$("#ven_code").val()}); 
    	grid.options.parms.push({name:'ven_name',value:$("#ven_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					 { display: 'HIS供应商编码', name: 'ven_code', align: 'left'
					  },
                     { display: 'HIS供应商名称', name: 'ven_name', align: 'left'
					 },
					 { display: 'HRP供应商编码', name: 'sup_code', align: 'left'
					  },
                     { display: 'HRP供应商名称', name: 'sup_name', align: 'left'
					 },
					 { display: '操作',  align: 'left',render:function(rowdata,index,value){
						 return "<a href='#' onClick='javascript:addAccMedVenRef("+index+")'>【设置】</a>&nbsp;&nbsp;&nbsp;&nbsp;"
						 +"<a href='#' onClick='javascript:delAccMedVenRef("+index+")'>【删除】</a>";
					 }}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHisAccMedVenRef.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

	function addAccMedVenRef(obj){
       	
       	var data = gridManager.getRow(obj);
       	
       	$.ligerDialog.open({url: 'hisAccMedVenRefAddPage.do?isCheck=false&ven_code='+data.ven_code,  height: $(window).height(),width: $(window).width()-100, title:'选择对应关系',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
   				buttons: [ 
   				           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccMedVenRef(); },cls:'l-dialog-btn-highlight' }, 
   				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
   				         ]
   		});
       }
        
      function delAccMedVenRef(obj){
      	
      	var data = gridManager.getRow(obj);
      	
      	var ParamVo =[];

  			ParamVo.push(
  			//表的主键
  			data.group_id   +"@"+ 
  			data.hos_id   +"@"+ 
  			data.ven_code +"@"+ 
  			data.sup_id +"@"+ 
  			data.copy_code 
  			);
              $.ligerDialog.confirm('确定删除?', function (yes){
              	if(yes){
                  	if(data.sup_id == null){
                  		$.ligerDialog.error('HRP供应商为空!');
                  		return false;
                  	}
                  	ajaxJsonObjectByUrl("deleteHisAccMedVenRef.do",{ParamVo : ParamVo.toString()},function (responseData){
                  		if(responseData.state=="true"){
                  			query();
                  		}
                  	});
              	}
              }); 
        }
      
    function loadDict(){
            //字典下拉框
    }   
    
	 function downTemplate(){
	    	
	    	location.href = "downTemplate.do";
	    	
	    }
	    
	    function importData(){
	    	
	    	$.ligerDialog.open({url: 'accHisAccMedVenRefImportPage.do?isCheck=false', height: 500,width: 1135, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
	    	
	    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">HIS供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_code" type="text" id="ven_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">HRP供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_name" type="text" id="ven_name" /></td>
        </tr>
        <tr>
        	<td align="right" style="padding-left:20px;"><input class="l-button" type="button" value=" 查询" onclick="query();" /></td>
        	<td><input class="l-button" style="width: 90px;margin-left: 10px" type="button" value="下载导入模板" onclick="downTemplate();" />
            <input class="l-button" style="margin-left: 10px" type="button" value=" 导入" onclick="importData();" /></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
