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
    	
		$("#pay_type_code").ligerTextBox({width:180});
    	
    	$("#pay_type_name").ligerTextBox({width:180});
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        
    	grid.options.parms.push({name:'pay_type_code',value:$("#pay_type_code").val()}); 
    	grid.options.parms.push({name:'pay_type_name',value:$("#pay_type_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: 'HIS付款方式编码', name: 'pay_type_code', align: 'left'
					 },
                     { display: 'HIS付款方式名称', name: 'pay_type_name', align: 'left'
					 },
					 { display: 'HRP付款方式编码', name: 'pay_type_code', align: 'left'
					 },
                     { display: 'HRP付款方式名称', name: 'pay_type_name', align: 'left'
					 },
					 { display: '操作',  align: 'left',render:function(rowdata,index,value){
						 return "<a href='#' onClick='javascript:addAccMedPayTypeRef("+index+")'>【设置】</a>&nbsp;&nbsp;&nbsp;&nbsp;"
						 +"<a href='#' onClick='javascript:delAccMedPayTypeRef("+index+")'>【删除】</a>";
					 }}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHisAccMedPayTypeRef.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

   	function addAccMedPayTypeRef(obj){
       	
       	var data = gridManager.getRow(obj);
       	
       	var parm="";
       	
       		parm="group_id="+data.group_id+
       		"&hos_id="+data.hos_id+
       		"&subj_id="+data.subj_id+
       		"&acc_year="+data.acc_year+
       		"&copy_code="+data.copy_code;
       	
       	$.ligerDialog.open({url: 'accMedPayTypeRefAddPage.do?'+parm, height: 200,width: 452, title:'选择对应关系',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
   				buttons: [ 
   				           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccSubj(); },cls:'l-dialog-btn-highlight' }, 
   				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
   				         ]
   		});
       }
        
      function delAccMedPayTypeRef(obj){
      	
      	var data = gridManager.getRow(obj);
      	
      	var ParamVo =[];

  			ParamVo.push(
  			//表的主键
  			data.group_id   +"@"+ 
  			data.hos_id   +"@"+ 
  			data.content_code +"@"+ 
  			data.subj_id +"@"+ 
  			data.acc_year +"@"+ 
  			data.copy_code 
  			);
              $.ligerDialog.confirm('确定删除?', function (yes){
              	if(yes){
                  	if(data.content_code == null){
                  		$.ligerDialog.error('财政补助内容为空!');
                  		return false;
                  	}
                  	ajaxJsonObjectByUrl("deleteAccMedPayTypeRef.do",{ParamVo : ParamVo},function (responseData){
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
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款方式编码：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_type_code" type="text" id="pay_type_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">付款方式名称：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_type_name" type="text" id="pay_type_name" /></td>
        </tr> 
	
        <tr>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
