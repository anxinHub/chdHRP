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
    	grid.options.parms.push({name:'cost_type_code',value:$("#cost_type_code").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '成本类型编码', name: 'cost_type_code', align: 'left',
						render : function(rowdata, rowindex,value) {
						  return "<a href=javascript:openUpdate('"+
						   rowdata.cost_type_id+"')>"+rowdata.cost_type_code+"</a>";
						} 
					 },
                     { display: '成本类型名称', name: 'cost_type_name', align: 'left'},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHtcCostTypeDict.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     checkBoxDisplay : f_checkBoxDisplay,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: remove,icon:'delete' },
						{ line:true }
    				]}, 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }


    function f_checkBoxDisplay(rowdata){
    	var num =new Array("01","02","03","04","05","06","07","08")
	      if(num.indexOf(rowdata.cost_type_code) != -1){
                   return false;
		   }
        }
    function add_open(){
	    	$.ligerDialog.open({
	    	    url: 'htcCostTypeDictAddPage.do?isCheck=false',
	    	    height: 200,
	    	    width: 500,
	    	    title: '添加',
	    	    modal: true,
	    	    showToggle: false,
	    	    showMax: false,
	    	    showMin: true,
	    	    isResize: true,
	    	    buttons: [{
	    	        text: '确定',
	    	        onclick: function(item, dialog) {
	    	            dialog.frame.saveCostDeptTypeDict();
	    	        },
	    	        cls: 'l-dialog-btn-highlight'
	    	    },
	    	    {
	    	        text: '取消',
	    	        onclick: function(item, dialog) {
	    	            dialog.close();
	    	        }
	    	    }]
	    	});
        }
    function remove(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
				ParamVo.push(
					  this.cost_type_id 
				)
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	$.post("deleteHtcCostTypeDict.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}else{
                			$.ligerDialog.warn(responseData.error);
                		}
                	},"json");
            	}
            }); 
          }
        }
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "cost_type_id="+vo[0] 
		$.ligerDialog.open({
		    url: 'htcCostTypeDictUpdatePage.do?isCheck=false&' + parm,
		    data: {},
		    height: 200,
		    width: 500,
		    title: '修改',
		    modal: true,
		    showToggle: false,
		    showMax: false,
		    showMin: false,
		    isResize: true,
		    buttons: [{
		        text: '确定',
		        onclick: function(item, dialog) {
		            dialog.frame.saveCostTypeDict();
		        },
		        cls: 'l-dialog-btn-highlight'
		    },
		    {
		        text: '取消',
		        onclick: function(item, dialog) {
		            dialog.close();
		        }
		    }]
		});
    }
    function loadDict(){
            //字典下拉框
	    	$("#cost_type_code").ligerTextBox({ width:160 });
	    	$("#cost_type_name").ligerTextBox({ width:160 });
         }  
	
    </script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本类型：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_type_code" type="text" id="cost_type_code" /></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
