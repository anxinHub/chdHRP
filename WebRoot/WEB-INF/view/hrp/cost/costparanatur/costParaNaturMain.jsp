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
        $("#para_code").ligerTextBox({width:160});
        $("#para_name").ligerTextBox({width:160});
        $("#note").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'para_code',value:$("#para_code").val()}); 
    	  grid.options.parms.push({name:'para_name',value:$("#para_name").val()}); 
    	  grid.options.parms.push({name:'note',value:$("#note").val()}); 
    	  grid.options.parms.push({name:'note',value:liger.get("bill_type").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '分摊性质编码', name: 'para_code', align: 'left'
					 		},
                     { display: '分摊性质名称', name: 'para_name', align: 'left'
					 		},
                     { display: '默认分摊参数编码', name: 'para_value', align: 'left'
					 		},
                     { display: '默认分摊参数名称', name: 'para_value_name', align: 'left'
					 		},
                     { display: '分摊级次编码', name: 'bill_type', align: 'left'
					 		},
                     { display: '分摊级次名称', name: 'bill_name', align: 'left'
					 		},
                     { display: '备注', name: 'note', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostParaNatur.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
						{ line:true }, 
		                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
		                { line:true },
		                { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate,icon:'down' },
		                { line:true },
		                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' }
				     ]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.para_code 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
  		$.ligerDialog.open({url: 'costParaNaturAddPage.do?isCheck=false', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostParaNatur(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
  		
    	}
    	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.para_code 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	$.post("deleteCostParaNatur.do",{ParamVo : ParamVo.toString()},function (responseData){
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
    function imp(){
    	
    	var index = layer.open({
					type : 2,
					title : '导入',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'costParaNaturImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"para_code="+vo[2] 
		
    	$.ligerDialog.open({ url : 'costParaNaturUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 500,  title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostParaNatur(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    
    }
    function loadDict(){
    	//字典下拉框
    	  $("#para_code").ligerTextBox({width:160});
		  autoCompleteByData("#bill_type", cost_bill_type_state.Rows, "id","text", true, true, "", false, '${bill_type}');
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('I', imp);
		

	 }
  //打印数据
	 function printDate(){
		//有数据直接打印
		 if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	var heads={
	 	    		//"isAuto":true,//系统默认，页眉显示页码
	 	    		"rows": [
	 		          {"cell":0,"value":"单位："+$("sessionScope.hos_name").val(),"colSpan":"5"}
	 	    	]};
	 	       var printPara={
	 	      		title: "分摊性质",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.CostParaNaturService",
	 	   			method_name: "queryCostParaNaturPrint",
	 	   			bean_name: "costParaNaturService",
	 	   		    heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
	 	   			
	 	       	};
	 	      //执行方法的查询条件
	 		  $.each(grid.options.parms,function(i,obj){
	 			printPara[obj.name]=obj.value;
	  	      });
	 		
	  	     officeGridPrint(printPara);
	 }
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分摊性质：</td>
            <td align="left" class="l-table-edit-td"><input name="para_code" type="text" id="para_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分摊级次：</td>
            <td align="left" class="l-table-edit-td"><input name="bill_type" type="text" id="bill_type" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
