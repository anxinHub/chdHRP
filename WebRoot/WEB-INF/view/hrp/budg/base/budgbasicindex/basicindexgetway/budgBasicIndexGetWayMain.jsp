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

        $("#get_value_way").ligerTextBox({width:160});
        $("#formula_id").ligerTextBox({width:160});
        $("#fun_id").ligerTextBox({width:160});
        index_nature:liger.get("index_nature").getValue();
		index_code:liger.get("index_code").getValue();
        get_value_way:liger.get("get_value_way").getValue();
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'index_code',value:liger.get("index_code").getValue()}); 
        grid.options.parms.push({name:'get_value_way',value:liger.get("get_value_way").getValue()}); 
    	grid.options.parms.push({name:'index_nature',value:liger.get("index_nature").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '指标编码', name: 'index_code', align: 'left',
                    	 render : function(rowdata, rowindex, value) {
     						
     							return '<a href=javascript:openUpdate("' 
     								+ rowdata.group_id + '|' + rowdata.hos_id + '|' + rowdata.copy_code 
     								+ '|' + rowdata.index_code +'")>'+rowdata.index_code+'</a>';
     						
     					}
					 },
					 { display: '指标名称', name: 'index_name', align: 'left'},
					 { display: '指标性质', name: 'index_nature_name', align: 'left'},
                     { display: '取值方法', name: 'get_value_way_name', align: 'left'
					 		},
                     { display: '计算公式', name: 'formula_name', align: 'left',
					 			render : function(rowdata, rowindex, value) {
		  							if(value){
		  								return '<a href=javascript:openFormula("' 
										+ rowdata.group_id + '|' + rowdata.hos_id + '|' + rowdata.copy_code 
										+'|' + rowdata.formula_id+'")>'+rowdata.formula_name+'</a>';
		  							}
								}
					 		},
                     { display: '取值函数', name: 'fun_name', align: 'left',
	                       render : function(rowdata, rowindex, value) {
	  							if(value){
	  								return '<a href=javascript:openFun("' 
									+ rowdata.group_id + '|' + rowdata.hos_id + '|' + rowdata.copy_code 
									 +'|' + rowdata.fun_id+'")>'+rowdata.fun_name+'</a>';
	  							}
							}
					 	}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgBasicIndexGetWay.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
		                { line:true },
    					]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.index_code 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
 
    function add_open(){
    	
    	parent.$.ligerDialog.open({ url : 'hrp/budg/base/budgbasicindex/basicindexgetway/budgBasicIndexGetWayAddPage.do?isCheck=false',data:{}, height:400,width:800, 
            title:'基本指标取值方法维护添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
            parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
            buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgBasicIndexGetWay(); },cls:'l-dialog-btn-highlight' },
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
							this.index_code 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteBudgBasicIndexGetWay.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
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
					content : 'budgBasicIndexGetWayImportPage.do?isCheck=false'
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
			"copy_code="+vo[2]   +"&"+ 
			"index_code="+vo[3] 
		 
		 
		parent.$.ligerDialog.open({ url : 'hrp/budg/base/budgbasicindex/basicindexgetway/budgBasicIndexGetWayUpdatePage.do?isCheck=false&'+parm,data:{}, height: 400,width: 800, 
			title:'基本指标取值方法维护',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgBasicIndexGetWay(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
		
		 
    }
    
	function openFormula(obj){
		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"formula_id="+vo[3] 
		 
			parent.$.ligerDialog.open({ url : 'hrp/budg/common/budgformula/budgFormulaUpdatePage.do?isCheck=false&'+parm,data:{}, height: 500,width: 600,
				title:'计算公式',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
    	}); 
    }
    
	function openFun(obj){
		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"fun_code="+vo[3] +"&"+ 
			"index_type_code="+'01' //指标类型 01基本指标 02费用标准 03预算指标  04收入科目
		 
			parent.$.ligerDialog.open({ url : 'hrp/budg/common/budgfun/budgFunUpdatePage.do?isCheck=false&'+parm,
				data:{}, height: 500,width: 600,
				title:'取值函数',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
    	}); 
    }
    
    function loadDict(){
    	  //指标性质
        autocomplete("#index_nature","../../../queryBudgIndexNature.do?isCheck=false","id","text",true,true);
    	  //指标名称
        autocomplete("#index_code","../../../queryBudgDeptindex_code_name.do?isCheck=false","id","text",true,true);
     
        //取值方法
        autocomplete("#get_value_way","../../../   queryBudgGetValueWay.do?isCheck=false","id","text",true,true);
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		hotkeys('I', imp);
		

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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标性质：</td>
            <td align="left" class="l-table-edit-td"><input name="index_nature" type="text" id="index_nature" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="index_code" type="text" id="index_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取值方法：</td>
            <td align="left" class="l-table-edit-td"><input name="get_value_way" type="text" id="get_value_way" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
          
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
