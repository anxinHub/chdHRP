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
		loadDict();
    	loadHead(null);	//加载数据	
    	
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'inv_code',value:$("#inv_code").val()}); 
    	grid.options.parms.push({name:'goodsid',value:$("#goodsid").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [{ display: 'HRP材料编码', name: 'inv_code', align: 'left',
							render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:openUpdate('"+rowdata.inv_id   + "|" + 
									rowdata.goodsid   + "|"  +"')>"+rowdata.inv_code+"</a>";
							}
					 },
                     { display: 'HRP材料名称', name: 'inv_name', align: 'left'
					 },
                     { display: '省平台材料编码', name: 'goodsid', align: 'left'
					 },
                     { display: '省平台材料名称', name: 'goodsname', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatInvRela.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: addOpen, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: deleteData,icon:'delete' },
    	                { line:true },
    	                { text: '同步', id:'delete', click: syncData,icon:'collect' },
    	                { line:true },
    	                { text: '下载导入模板', id:'downTemplate', click:downTemplate, icon:'down' },
    	                { line:true },
    	                { text: '导入', id:'import', click: imp,icon:'up' },
    	                { line:true },
    	                { text: '批量修改', id:'batchUpdate', click: batchUpdate,icon:'offset' }
    	               
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.inv_id   + "|" +  
								rowdata.goodsid 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //批量修改(cjc)
	function batchUpdate(){
    	
    	parent.$.ligerDialog.open({url: 'hrp/mat/platform/matInvRelaUpdatePage.do?isCheck=false', height: $(window).height(),
			width: $(window).width(), title:'批量修改',modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name });
  		
    }

    //下载模板(cjc)
    function downTemplate(){

    	location.href = "downTemplate.do?isCheck=false";
    }
    
    //导入(cjc)
    function imp(){
    	var index = layer.open({
					type : 2,
					title : '省平台材料对应关系',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '750px', '370px' ],
					content : 'matPlatFormImportPage.do?isCheck=false'
				});
				/* layer.full(index); */
    	}
    
    function addOpen (){
    	
    	$.ligerDialog.open({url: 'matInvRelaAddPage.do?isCheck=false', 
				height: 430,width: 520, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
				buttons: [ 
				           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMatinvRela(); },cls:'l-dialog-btn-highlight' }, 
				           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
				         ] 
		});
		return;
		
    }
    
    function deleteData(){
    	
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
				ParamVo.push(
				//表的主键
				 
				this.inv_id   +"@"+   
				this.goodsid
				)
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteMatInvRela.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
        return;
    }
    
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "inv_id="+ vo[0]   +"&goodsid="+ vo[1]   ; 
		
    	$.ligerDialog.open({ url : 'updateMatInvRelaPage.do?isCheck=false&' + parm,data:{}, 
    			height: 430,width: 520, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    			buttons: [ 
    			           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMatinvRela(); },cls:'l-dialog-btn-highlight' }, 
    			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
    			         ] 
    	});

    }
    
    function syncData(){
 
		var date = new Date();
           // 当前日期的年
        var todayYear = date.getFullYear();
           // 当前时间的月
        var todayMonth = date.getMonth()+1;
           // 当前时间的日
        var todayDate = date.getDate(); 
		
		todayMonth = todayMonth < 10 ? '0' + todayMonth :  todayMonth;
		todayDate = todayDate < 10 ? '0' + todayDate : todayDate; 
		var today = todayYear + '-' + todayMonth + '-' + todayDate ;
		
		var endDate;
    	$.ligerDialog.open({
			content: "终止日期:<input id='endDate' value=" + today + " style='text-align:center; border: 1px solid blue; height: 18px;' onFocus='WdatePicker({isShowClear:true,readOnly:true,dateFmt:\"yyyy-MM-dd \"})' />"
			+"<br/><br/><br/>"+"<span style=\"color:red\">起始时间为终止时间前10天</span>"
			,
			width: 300,
			height: 150,
			buttons: [
				{ 
					text: '确定',
					onclick: function (item, dialog) {
						endDate = $("#endDate").val() +' 23:59:59' ;
						if (endDate) {
							dialog.close();
							saveSyncData(endDate)
						}
					}
				},
                { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
			]
		})
    	
    	
    }
    
    function saveSyncData(endDate) {
    	$.ligerDialog.confirm('确定同步数据?', function(yes) {
			if (yes) {
				//判断所选确认日期是否已经结账
				ajaxJsonObjectByUrl("saveSyncMatInvRela.do?isCheck=false", {endDate : endDate}, function(responseData) {
					if (responseData.state == "true") {
								query();
					}
				},false);
				
			}
	});
    	
    }
    
    
    function loadDict(){
        //字典下拉框
    	$("#inv_code").ligerTextBox({width:160});
    	$("#goodsid").ligerTextBox({width:160}); 
     }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">HRP材料：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">省平台材料：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="goodsid" type="text" id="goodsid" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>

</body>
</html>
