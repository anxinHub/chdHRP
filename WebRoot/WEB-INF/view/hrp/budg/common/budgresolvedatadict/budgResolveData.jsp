<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
<jsp:param value="grid,select,datepicker,ligerUI,dialog" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    $(function (){
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		
		 $("#budg_level").change(function(){
			 query();
		 });
		 $("#resolve_data_code").change(function(){
			 query();
		 });
    });
    //查询
    function  query(){
    	var parms=[];
        //根据表字段进行添加查询条件
    	parms.push({name:'budg_level',value:liger.get("budg_level").getValue()}); 
    	parms.push({name:'resolve_data_code',value:$("#resolve_data_code").val()}); 

    	//加载查询条件
    	grid.loadData(parms,"queryBudgResolveDataDict.do?isCheck=false");
     }
    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
                     { display: '预算层次', name: 'budg_level_name', align: 'center',width:"20%"},
					 { display: '分解系数编码', name: 'resolve_data_code', align: 'center',width:"30%"/* ,
                    	 render:function(ui) {
	 						return '<a href=javascript:openUpdate("'+ ui.rowData.group_id + "|" + ui.rowData.hos_id 
							+ "|" + ui.rowData.copy_code + "|" + ui.rowData.budg_level + "|" + ui.rowData.resolve_data_code + '")>'+ui.rowData.resolve_data_code+'</a>';
								} */
					 	},
                     { display: '分解系数名称', name: 'resolve_data_name', align: 'center',width:"47%"}
                     ],
                     dataModel:{
                    	 method:'POST',
                    	 location:'remote',
                    	 url:'',
                    	 recIndx: 'resolve_data_code' //必填 且该列不能为空  
                    },
                    usePager:true,width: '100%', height: '100%',checkbox: true,
    				toolbar: {
    	                items: [
    	                { type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
    	                //{ type: "button", label: '添加',icon:'plus',listeners: [{ click: add_open}] },
    	            ]},
    	            rowSelect: function(event,ui) {
    	                parentFrameUse().liger.get("resolve_data").setValue(ui.rowData.resolve_data_code);
    	                parentFrameUse().liger.get("resolve_data").setText(ui.rowData.resolve_data_code +' '+ ui.rowData.resolve_data_name);
	   	        		frameElement.dialog.close();
					}
                  });
    }
    
    
    function add_open(){  
    	var budg_level = liger.get("budg_level").getValue() ;
    	if(!budg_level){
    		
    		$.etDialog.error('预算层次不能为空');
    		
    		return false;
    		
    	}
		parent.$.etDialog.open({ 
			url : '../../../../../../hrp/budg/common/budgresolvedatadict/budgResolveDataDictAddPage.do?isCheck=false&budg_level='+budg_level,
			title:'自定义分解系数添加',
			isMax: true,
			frameNameObj: {index:window.name} //用于parent弹出层调用本页面的方法或变量
   		}); 
    }
    //删除;
    function remove(){
    	
    	var data = grid.selectGetChecked();
        if (data.length == 0){
        	$.etDialog.error('请选择行');
        }else{
        	var ParamVo = []
            $(data).each(function (){	
            	
            	ParamVo.push(
            			this.rowData.group_id + "@" +
           	        	this.rowData.hos_id + "@" +
           	        	this.rowData.copy_code + "@" +
	       	        	this.rowData.budg_level + "@" +
	       	        	this.rowData.resolve_data_code 
       	         );
											
            });
            
			$.etDialog.confirm('确定删除?', function (yes){
            	if(yes){
            		ajaxPostData({
	                    url: "deleteBudgResolveDataDict.do?isCheck=false",
	                    data: { ParamVo: ParamVo.toString() },
	                    success: function(responseData) {
	                    	query();
	                    }
	                });
            	}
            }); 
        }
    }
    
   //修改页面跳转
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = "budg_level="+vo[3] +"&resolve_data_code="+vo[4] 
		
		parent.$.etDialog.open({ 
			url : '../../../../../../hrp/budg/common/budgresolvedatadict/budgResolveDataDictUpdatePage.do?isCheck=false&'+parm,
			title:'自定义分解系数添加',
			isMax: true,
			frameNameObj: {index:window.name} //用于parent弹出层调用本页面的方法或变量
   		}); 
    }

	 //加载下拉框
    function loadDict(){
            //字典下拉框
            //预算层次(默认 医院月份)
           autocomplete("#budg_level","../../queryBudgLevel.do?isCheck=false","id","text",true,true,'',false,'${budg_level}');
            
           $("#budg_level").ligerTextBox({width:160,disabled:true});
           $("#resolve_data_code").ligerTextBox({width:160});
    }  
	
	
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

	 }
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算层次：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_level" type="text" id="budg_level" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分解系数：</td>
            <td align="left" class="l-table-edit-td"><input name="resolve_data_code" type="text" id="resolve_data_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>        
    </table>
	<div id="maingrid"></div>
	
</body>
</html>
