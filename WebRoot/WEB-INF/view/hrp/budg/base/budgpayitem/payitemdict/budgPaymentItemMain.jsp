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
	var newpage, // 存入当前page页的变量 
	selectArr = []; // 存入所选择数据的数组
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
        $("#payment_item_name").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'payment_item_name',value:$("#payment_item_name").val()}); 
    	  grid.options.parms.push({name:'payment_item_nature',value:liger.get("payment_item_nature").getValue()}); 
    	  grid.options.parms.push({name:'control_way',value:liger.get("control_way").getValue()}); 
    	  grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 

    	//加载查询条件
		// console.log(grid.getSelecteds());
    	grid.loadData(grid.where);
     }

	
    function loadHead(){
		var oldPageSize = 50;
		var scroll_onoff = true;
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '项目编码', name: 'payment_item_code', align: 'left',
							render : function(rowdata, rowindex, value) {
								return "<a href=javascript:openUpdate('"
										+ rowdata.group_id + "|"
										+ rowdata.hos_id + "|"
										+ rowdata.copy_code + "|"
										+ rowdata.payment_item_id +"|"
										 + rowdata.__id + "')>"
										+ rowdata.payment_item_code + "</a>";
							}
					 		},
                     { display: '项目名称', name: 'payment_item_name', align: 'left'
					 		},
                     { display: '项目全称', name: 'item_name_all', align: 'left'
					 		},
                     { display: '项目级次', name: 'item_level', align: 'left'
					 		},
                     { display: '是否末级', name: 'is_last', align: 'left',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_last == 0){
										return "否";
									}
									return "是";
					 			}
					 		},
                     { display: '支出项目性质', name: 'payment_item_nature_name', align: 'left'
					 		},
                     { display: '是否管理费', name: 'is_manage', align: 'left',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_manage == 0){
										return "否";
									}
									return "是";
					 			}
					 		},
                     { display: '控制方式', name: 'control_way_name', align: 'left'
					 		},
                     { display: '是否停用', name: 'is_stop', align: 'left',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_stop == 0){
										return "否";
									}
									return "是";
					 			}
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryBudgPaymentItem.do?isCheck=false',
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
					onCheckRow: function (checked, data, rowid, rowdata) {         // 复选框选择时存入数据，同时重复数据不存入
							var code = rowid + "@" + grid.options.page;
							if (checked) {
								selectArr.push(code);
							} else {
								var index = selectArr.indexOf(code);
								if (index > -1)
									selectArr.splice(index, 1);
							}
						},
					onChangePage: function(page){           // 当点击换页按钮时，触发此事件，更新newpage,以免跳转不了
							 newpage = page ;
							 scroll_onoff = false;				//  当点解input翻页时，限制滚动条滚动，让滚动条重置到所翻页的启始处
						},
					onAfterShowData:function(){
						var pageNum = grid.options.page;
							grid.changePages(newpage);         
							grid.selectedDataRender(selectArr,oldPageSize, grid.options.pageSize, pageNum);
							var selects = selectArr;
							if(selects.length > 0)
							{
								var selectNow = selects[selects.length - 1].split('@')[0]; 
								if(oldPageSize != grid.options.pageSize){            // pagesize改变时触发changePages
									newpage = selects[selects.length - 1].split('@')[1];
									grid.changePages(newpage);
									oldPageSize=grid.options.pageSize;
									scroll_onoff = true;
									return false;
								}
								if(scroll_onoff)                                       // 防止每页的滚动条都在动  
								grid.gridbody.scrollTop(28 * (selectNow.replace("r1","")*1 -1));
							}
							oldPageSize=grid.options.pageSize;
							scroll_onoff = true;
						},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.payment_item_id  + "|" +
								rowdata.__id
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
		$.ligerDialog.open({ url : 'budgPaymentItemAddPage.do?isCheck=false&',data:{}, height: 500,width: 700, title:'支出项目',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgPaymentItem(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
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
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.payment_item_id 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteBudgPaymentItem.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
	
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"payment_item_id="+vo[3] 

		var code = vo[vo.length - 1] + "@" + grid.options.page;          // code为存入的选择数据
		var index = selectArr.indexOf(code);
		if (index > -1)              // 判断是否有重复数据，有的话先删除一个
			selectArr.splice(index, 1);
		selectArr.push(code); 
		 
		 $.ligerDialog.open({ url : 'budgPaymentItemUpdatePage.do?isCheck=false&'+parm,data:{}, height: 500,width: 700, title:'支出项目',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgPaymentItem(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
    }
	function imp(){
	   	
	   	var index = layer.open({
					type : 2,
					title : '导入',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'budgPaymentItemImportPage.do?isCheck=false'
				});
				layer.full(index);
	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
	}	
    
  //打印数据
	function printDate(){
		if(grid.getData().length==0){
			$.ligerDialog.error("无打印数据！");
			return;
		}
	    
    	grid.options.parms=[];
		
    	grid.options.parms.push({name:'payment_item_name',value:$("#payment_item_name").val()}); 
		grid.options.parms.push({name:'payment_item_nature',value:liger.get("payment_item_nature").getValue()}); 
		grid.options.parms.push({name:'control_way',value:liger.get("control_way").getValue()}); 
		grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 
        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"支出项目字典",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryBudgPaymentItem.do?isCheck=false", selPara, function(responseData) {
	   		 $.each(responseData.Rows, function (index,value) {
	             if(value.is_stop==0){
	            	 value.is_stop='否';
	             }else{
	            	 value.is_stop='是';
	             }
	             if(value.is_manage==0){
	            	 value.is_manage='否';
	             }else{
	            	 value.is_manage='是';
	             }
	             if(value.is_last==0){
	            	 value.is_last='否';
	             }else{
	            	 value.is_last='是';
	             }
	             
	         });
   			printGridView(responseData,printPara);
    	});
	}
    function loadDict(){
            //字典下拉框
    	$("#payment_item_nature").ligerComboBox({
	      	url: '../../../../acc/queryBudgSysDict.do?isCheck=false&f_code=PAYMENT_ITEM_NATURE',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 160,
	      	autocomplete: true,
	      	width: 160
		 });
    	
    	$("#control_way").ligerComboBox({
	      	url: '../../../../acc/queryBudgSysDict.do?isCheck=false&f_code=CONTROL_WAY',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 160,
	      	autocomplete: true,
	      	width: 160
		 });
    	$("#is_stop").ligerComboBox({
			width : 160
		});
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="payment_item_name" type="text" id="payment_item_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">控制方式：</td>
            <td align="left" class="l-table-edit-td"><input name="control_way" type="text" id="control_way" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目性质：</td>
            <td align="left" class="l-table-edit-td"><input name="payment_item_nature" type="text" id="payment_item_nature" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td">
            	<select name="is_stop" id="is_stop" >
            	     <option value=""></option>
	            	<option value="0">否</option>
	            	<option value="1">是</option>
	            </select>
            </td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
