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
	var codeData =  '${codeData}';
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
    	  grid.options.parms.push({name:'payment_item_name',value:$("#payment_item_name").val()}); 
    	  grid.options.parms.push({name:'payment_item_nature',value:liger.get("payment_item_nature").getValue()}); 
    	  grid.options.parms.push({name:'control_way',value:liger.get("control_way").getValue()}); 
    	  grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 

    	//加载查询条件
		// console.log(grid.getSelecteds());
    	grid.loadData(grid.where);
     }

	
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '支出项目编码', name: 'payment_item_code', align: 'left',
					 		},
                     { display: '支出项目名称', name: 'payment_item_name', align: 'left'
					 		},
                     { display: '支出项目全称', name: 'item_name_all', align: 'left'
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
					 { display: '是否末级', name: 'is_last', align: 'left',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_last == 0){
										return "否";
									}
									return "是";
					 			}
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryBudgPaymentItem.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true, isChecked : initCheck,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '重置（<u>C</u>）', id:'reset', click: reset,icon:'setting' },
                     	{ line:true },
				    	{ text: '保存（<u>S</u>）', id:'save', click: save, icon:'save' },
				    	]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function initCheck(rowdata){
    	
    	if(codeData.indexOf(rowdata.payment_item_code) > -1){
    		return true ;
    	}else{
    		return false ;
    	}
    	 
    }
    
    //重置
    function reset(){
    	
    	var data = gridManager.getCheckedRows();
    	//取消选中
    	 $(data).each(function (){
    		 
    		 gridManager.unselect(this) 
    	 })
    	
    }
    
    //保存
    function save(){
    	
    	
    	var data = gridManager.getCheckedRows();
    	parent.detailGrid.deleteAllRows(data);
    	parent.detailGrid.addRows(data);
    	parent.$.ligerDialog.close(); //关闭弹出窗; 
    	parent.$(".l-dialog,.l-window-mask").css("display","none"); //隐藏遮罩层
    }
    
    function loadDict(){
            //字典下拉框
    }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('C', reset);

		hotkeys('S', save);
	 }
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>

	<div id="maingrid"></div>
	
</body>
</html>
