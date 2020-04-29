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
		
		$("#budg_year").change(function(){
			query() ;
		})
		$("#payment_item_id").change(function(){
			query() ;
		})
		$("#fund_nature").change(function(){
			query() ;
		})
		$("#subj_code").change(function(){
			query() ;
		})
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
	        //根据表字段进行添加查询条件
	        grid.options.parms.push({name:'budg_year',value:liger.get("budg_year").getValue()}); 
			grid.options.parms.push({name:'payment_item_id',value:liger.get("payment_item_id").getValue()}); 
			grid.options.parms.push({name:'fund_nature',value:liger.get("fund_nature").getValue()}); 
			grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '预算年度', name: 'budg_year', align: 'left',
                    	 render:function(rowdata,rowindex,value){
	                    	 return "<a href=javascript:openUpdate('"
								+ rowdata.group_id + "|"
								+ rowdata.hos_id + "|"
								+ rowdata.copy_code + "|"
								+ rowdata.budg_year +  "|"
								+ rowdata.payment_item_id + "|"
								+ rowdata.fund_nature +"')>"+ rowdata.budg_year + "</a>";
							}
					 	},
                     { display: '支出项目编码', name: 'payment_item_code', align: 'left'
					 		},
					 { display: '支出项目名称', name: 'payment_item_name', align: 'left'
					 		},
                     { display: '资金性质', name: 'fund_nature_name', align: 'left'
					 		},
					 { display: '支出预算科目编码', name: 'subj_code', align: 'left'
					 		},
                     { display: '支出预算科目名称', name: 'subj_name', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgPaymentItemCostShip.do?isCheck=false',
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
								rowdata.copy_code   + "|" + 
								rowdata.budg_year   + "|" + 
								rowdata.payment_item_id   + "|" + 
								rowdata.fund_nature 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
		$.ligerDialog.open({ url : 'budgPaymentItemCostShipAddPage.do?isCheck=false',data:{}, 
				height: 300,width:450,title:'添加支出项目与支出预算科目对应关系',modal:true,
				showToggle:false,showMax:false,showMin: false,isResize:true,
				buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgPaymentItemCostShip(); },
					cls:'l-dialog-btn-highlight' },{text:'取消',onclick: function (item,dialog){dialog.close();}}]
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
					this.budg_year   +"@"+ 
					this.payment_item_id   +"@"+ 
					this.fund_nature 
					) 
				});
	            $.ligerDialog.confirm('确定删除?', function (yes){
	            	if(yes){
	                	ajaxJsonObjectByUrl("deleteBudgPaymentItemCostShip.do",{ParamVo : ParamVo.toString()},function (responseData){
	                		if(responseData.state=="true"){
	                			query();
	                		}
	                	});
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
					content : 'budgPaymentItemCostShipImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm ="group_id="+vo[0] +"&hos_id="+vo[1] +"&copy_code="+vo[2] +"&budg_year="+vo[3]   
					+"&payment_item_id="+vo[4] +"&fund_nature="+vo[5] 
		 
		 $.ligerDialog.open({ url : 'budgPaymentItemCostShipUpdatePage.do?isCheck=false&'+parm,data:{}, 
			height: 300,width: 450, title:'支出项目与支出预算科目对应关系修改',modal:true,showToggle:false,showMax:false,
			showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgPaymentItemCostShip(); },
				cls:'l-dialog-btn-highlight' },{text:'取消',onclick: function (item,dialog){dialog.close();}}]
    	}); 
		 
    }
    
    //打印数据
	function printDate(){
		if(grid.getData().length==0){
			$.ligerDialog.error("无打印数据！");
			return;
		}
	    
    	grid.options.parms=[];
		
    	grid.options.parms.push({name:'budg_year',value:liger.get("budg_year").getValue()}); 
		grid.options.parms.push({name:'payment_item_id',value:liger.get("payment_item_id").getValue()}); 
		grid.options.parms.push({name:'fund_nature',value:liger.get("fund_nature").getValue()}); 
		grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()});  
        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"支出项目与预算支出科目对应关系",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryBudgPaymentItemCostShip.do?isCheck=false", selPara, function(responseData) {
   			printGridView(responseData,printPara);
    	});
	}
    function loadDict(){
        //字典下拉框
		autocompleteAsync("#budg_year","../../../queryBudgYear.do?isCheck=false","id","text",true,true,'',true);
        
        budg_year = liger.get("budg_year").getValue();
       	
        //预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
        autocomplete("#subj_code","../../../queryBudgSubj.do?isCheck=false&subj_type=05&is_last=1&budg_year="+budg_year,"id","text",true,true);
       	
        autocomplete("#fund_nature","../../../../sys/querySysSourceNature.do?isCheck=false","id","text",true,true); 
        
        autocomplete("#payment_item_id","../../../queryBudgPaymentItem.do?isCheck=false&is_last=1","id","text",true,true); 
        //字典下拉框
        $("#budg_year").ligerTextBox({width:160});
        $("#payment_item_id").ligerTextBox({width:160});
        $("#fund_nature").ligerTextBox({width:160});
        $("#subj_code").ligerTextBox({width:160});
    }  
    //键盘事件
	  function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('P', printDate);
		hotkeys('I', imp);
	 }
 	  
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算年度：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出项目：</td>
            <td align="left" class="l-table-edit-td"><input name="payment_item_id" type="text" id="payment_item_id" ltype="text" validate="{required:true,maxlength:200}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金性质：</td>
            <td align="left" class="l-table-edit-td"><input name="fund_nature" type="text" id="fund_nature" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出预算科目：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:200}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
