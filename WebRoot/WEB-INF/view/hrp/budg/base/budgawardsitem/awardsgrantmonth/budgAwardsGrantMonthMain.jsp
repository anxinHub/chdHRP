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
<script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
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
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'awards_item_code',value:$("#awards_item_code").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '奖金项目编码', name: 'awards_item_code', align: 'left',
                    	 render: function(rowdata,rowindex,value){
                    		 return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.awards_item_code+"|"+rowdata.id+"')>"+rowdata.awards_item_code+"</a>";
                    	 }
					 		},
					 { display: '奖金项目名称', name: 'awards_item_name', align: 'left'
					 		},
                     { display: '序号', name: 'id', align: 'left'
					 		},
                     { display: '发放月', name: 'grant_month', align: 'left'
					 		},
					 { display: '参考期间', columns:[
	                     { display: '开始年度', name: 'start_year', align: 'left'},
	                     { display: '开始月份', name: 'start_month', align: 'left'},
	                     { display: '结束年度', name: 'end_year', align: 'left'},
	                     { display: '结束月份', name: 'end_month', align: 'left'}]
					 	}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgAwardsGrantMonth.do?isCheck=false',
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
								rowdata.awards_item_code   + "|" + 
								rowdata.id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
		$.ligerDialog.open({ url : 'budgAwardsGrantMonthAddPage.do?isCheck=false',data:{}, height: 350,width: 650,
			title:'奖金发放月添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgAwardsGrantMonth();},
				cls:'l-dialog-btn-highlight' },{ text: '取消', onclick: function (item, dialog) { dialog.close();}}]
    	}); 
		
    	/*var index = layer.open({
					type : 2,
					title : '开始年度和结束年度，只有两个取值为本年、上年。',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'budgAwardsGrantMonthAddPage.do?isCheck=false'
				});
				layer.full(index);
				*/
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
				this.awards_item_code   +"@"+ 
				this.id 
				) });
			    $.ligerDialog.confirm('确定删除?', function (yes){
			    	if(yes){
			        	ajaxJsonObjectByUrl("deleteBudgAwardsGrantMonth.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
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
					content : 'budgAwardsGrantMonthImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = "group_id="+vo[0] +"&hos_id="+vo[1] +"&copy_code="+vo[2] +"&awards_item_code="+vo[3] +"&id="+vo[4] 
		 
		 
		 $.ligerDialog.open({url:'budgAwardsGrantMonthUpdatePage.do?isCheck=false&'+parm,data:{},height:300,width:600, 
			title:'奖金发放月修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgAwardsGrantMonth();},
				cls:'l-dialog-btn-highlight' },{ text: '取消', onclick: function (item, dialog) { dialog.close();}}]
    	}); 
		 
    /*var index = layer.open({
					type : 2,
					title : '更新',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'budgAwardsGrantMonthUpdatePage.do?isCheck=false&' + parm
				});
				layer.full(index);	
    */
    }
    
    //打印数据
	function printDate(){
		if(grid.getData().length==0){
			$.ligerDialog.error("无打印数据！");
			return;
		}
	    
    	grid.options.parms=[];
		
    	grid.options.parms.push({name:'awards_item_code',value:$("#awards_item_code").val()}); 

        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"奖金发放月信息",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryBudgAwardsGrantMonth.do?isCheck=false", selPara, function(responseData) {
   			printGridView(responseData,printPara);
    	});
	}
    function loadDict(){
         //字典下拉框
    	 $("#awards_item_code").ligerTextBox({width:160});
    }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add_open);
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">奖金项目：</td>
            <td align="left" class="l-table-edit-td"><input name="awards_item_code" type="text" id="awards_item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
