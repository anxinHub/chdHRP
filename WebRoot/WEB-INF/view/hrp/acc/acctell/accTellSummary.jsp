 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null; 
    
    $(function (){
    	
    	loadHead(null);	//加载数据
    	
    	$("#summary").ligerTextBox({width:160});  
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'summary',value:$("#summary").val()});  

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '摘要', name: 'summary', align: 'left',
                    	 editor: {
     						type : 'text',
     					 }
							 
					 }   ,
					 //占位列
					 { display: ' ', name: 'nulls', align: 'left',width:'1' 
					 }    
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccTellSummary.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,delayLoad:true,onLoaded :f_onLoaded,
                     onChangeRow:onChangeRowSeve, enabledEdit : true,
                     isAddRow:false,   
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true },
    	                { text : '下载导入模板', id : 'downTemplate', click : itemclick, icon : 'down' },
						{ line : true },
						{ text : '导入', id : 'import', click : itemclick, icon : 'up' } 
    	                
    				]}
    				 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
	function f_onLoaded() {
		      
				grid.addRow(); 
				  
	} 

    function onChangeRowSeve(e){
    	    
    	 if(e.record.sid == undefined){
    		var formPara = { 
    				summary : e.record.summary 
     
    			}; 
    		ajaxJsonObjectByUrl("addAccTellSummary.do", formPara, function( responseData) {
    			
    			 if (responseData.state == "true") {
    				  query();
    								
    							}
    			 });
    	}else { 
			var formPara = {
			    sid:e.record.sid,
			    user_id :e.record.user_id,
				summary : e.record.summary

			};
			ajaxJsonObjectByUrl("updateAccTellSummary.do", formPara, function(
					responseData) {

				if (responseData.state == "true") { 
					 query();

				}
			});
		}  

	}
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "delete":
				var data = gridManager.getCheckedRows(); 
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(function() {
						if(this.sid && this.user_id){
							ParamVo.push(
							//表的主键
							this.sid + "@" + this.user_id)
						}
					}); 
					if(ParamVo.length == 0){
						$.ligerDialog.error('当前选择无法删除');
						return;
					}
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) { 
							ajaxJsonObjectByUrl("deleteAccTellSummary.do", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
				return;
			case "import":
				var para = {
					"column" : [ { 
						"name" : "summary",
						"display" : "摘要",
						"width" : "200",
						"require" : true
					} 
					 ]
				};
				
				importSpreadView("hrp/acc/acctell/importAccTellSummary.do?isCheck=false", para);
				return; 
			case "downTemplate":
				location.href = "downTemplateSummary.do?isCheck=false";
				return;

			}
		}

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;"  >
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
            <td align="left" class="l-table-edit-td"><input name="summary" type="text" id="summary" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
          
        </tr> 
	
    </table>

	<div id="maingrid"></div>

</body>
</html>
