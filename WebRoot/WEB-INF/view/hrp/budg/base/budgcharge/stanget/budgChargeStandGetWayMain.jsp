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
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'charge_stand_code',value:$("#charge_stand_code").val()}); 
    	  grid.options.parms.push({name:'get_value_way',value:liger.get("get_value_way").getValue()}); 
    	  grid.options.parms.push({name:'charge_stan_nature',value:liger.get("charge_stan_nature").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '费用标准编码', name: 'charge_stand_code', align: 'left',
                    	 render:function(rowdata,rowindex,value){
                    		return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.charge_stand_code+"')>"+rowdata.charge_stand_code+"</a>" 
                    	 }
					 },
					 { display: '费用标准名称', name: 'charge_stand_name', align: 'left'
					 		},
					 { display: '费用标准性质', name: 'charge_stan_nature_name', align: 'left'
					 		},
                     { display: '取值方法', name: 'get_value_way_name', align: 'left'
					 		},
                     { display: '计算公式', name: 'formula_name', align: 'left',
			 			render:function(rowdata,rowindex,value){
			 				if(rowdata.formula_name){
			 					return "<a href=javascript:openFormula('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.formula_id+"')>"+rowdata.formula_name+"</a>" 
			 				}
                    		
                    	 }
			 		 },
                     { display: '取值函数', name: 'fun_name', align: 'left',
			 			render:function(rowdata,rowindex,value){
			 				if(rowdata.fun_name){
			 					return "<a href=javascript:openFun('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.fun_id+"')>"+rowdata.fun_name+"</a>" 
			 				}
                    		
                    	 }
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgChargeStandGetWay.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' }/* ,
		                { line:true },
		                { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate,icon:'down' },
		                { line:true },
		                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' } */
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.charge_stand_code 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
    	parent.$.ligerDialog.open({ url : 'hrp/budg/base/budgcharge/stanget/budgChargeStandGetWayAddPage.do?isCheck=false&',data:{}, height: 500,width: 800, title:'添加',
				modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
				buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.savebudgChargeStandGetWay(); },
					cls:'l-dialog-btn-highlight' },{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
		
    	/*var index = layer.open({
					type : 2,
					title : '添加',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'budgChargeStandGetWayAddPage.do?isCheck=false'
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
							this.charge_stand_code 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteBudgChargeStandGetWay.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
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
					content : 'budgChargeStandGetWayImportPage.do?isCheck=false'
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
			"charge_stand_code="+vo[3] 
		 
		parent.$.ligerDialog.open({url:'hrp/budg/base/budgcharge/stanget/budgChargeStandGetWayUpdatePage.do?isCheck=false&'+parm,data:{}, height: 500,width: 800,
				title:'费用标准取值方法维护',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
				buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.savebudgChargeStandGetWay(); },
				cls:'l-dialog-btn-highlight' },{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
			    ]
    	}); 
		 
    /*var index = layer.open({
					type : 2,
					title : '更新',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'budgChargeStandGetWayUpdatePage.do?isCheck=false&' + parm
				});
				layer.full(index);	
    */
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
			"index_type_code="+'02' //指标类型 01基本指标 02费用标准 03预算指标  04收入科目
			
			parent.$.ligerDialog.open({ url : 'hrp/budg/common/budgfun/budgFunUpdatePage.do?isCheck=false&'+parm,
				data:{}, height: 500,width: 600,
				title:'取值函数',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
    	}); 
    }
    function loadDict(){
        //字典下拉框
        //费用标准性质下拉框
    	autocomplete("#charge_stan_nature","../../../queryBudgCharStanNature.do?isCheck=false","id","text",true,true);
         
    	//取值方法下拉框
    	autocomplete("#get_value_way","../../../queryBudgGetValueWay.do?isCheck=false","id","text",true,true,"",false,"",200);
     	$("#charge_stand_code").ligerTextBox({width:160});
     	$("#charge_stan_nature").ligerTextBox({width:160});
        $("#get_value_way").ligerTextBox({width:160});
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">费用标准：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_stand_code" type="text" id="charge_stand_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">费用标准性质：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_stan_nature" type="text" id="charge_stan_nature" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取值方法：</td>
            <td align="left" class="l-table-edit-td"><input name="get_value_way" type="text" id="get_value_way" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
