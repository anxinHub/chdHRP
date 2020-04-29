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
    	  grid.options.parms.push({name:'charge_stan_code',value:$("#charge_stan_code").val()}); 
    	  grid.options.parms.push({name:'charge_stan_nature',value:liger.get("charge_stan_nature").getValue()}); 
    	  grid.options.parms.push({name:'is_stop',value:liger.get("is_stop").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '费用标准编码', name: 'charge_stan_code', align: 'left',
                    	 render:function(rowdata,rowindex,value){
                    		 return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.charge_stan_code+"')>"+rowdata.charge_stan_code+"</a>";
                    	 }
					 		},
                     { display: '费用标准名称', name: 'charge_stan_name', align: 'left'
					 		},
                     { display: '费用标准性质', name: 'charge_stan_nature_name', align: 'left'
					 		},
                     { display: '费用标准描述', name: 'charge_stan_describe', align: 'left',width:400
					 		},
                     { display: '计量单位', name: 'unit', align: 'center',width:100
					 		},
                     { display: '数据精度', name: 'data_precision', align: 'left',width:100
					 		},
                     { display: '是否停用', name: 'stop', align: 'center',width:80},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgChargeStanDict.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
		                { line:true },
		                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
		                { line:true },
		                { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate,icon:'down' },
		                { line:true },
		                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' },
		                { line:true },
		                { text: '对应科室维护（<u>S</u>）', id:'book', click:deptSet,icon:'book' },
		                { line:true },
		                //{ text: '取值方法维护（<u>M</u>）', id:'import', click:getWay,icon:'up' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.charge_stan_code 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
		$.ligerDialog.open({ url : 'budgChargeStanDictAddPage.do?isCheck=false',data:{}, height: 300,width: 750,
				title:'费用标准添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgChargeStanDict(); },
						cls:'l-dialog-btn-highlight' },{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
		
    	/*var index = layer.open({
					type : 2,
					title : '费用标准添加',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'budgChargeStanDictAddPage.do?isCheck=false'
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
							this.charge_stan_code 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteBudgChargeStanDict.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
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
					content : 'budgChargeStanDictImportPage.do?isCheck=false'
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
			"charge_stan_code="+vo[3] 
		 
		 
		 $.ligerDialog.open({ url : 'budgChargeStanDictUpdatePage.do?isCheck=false&'+parm,data:{}, height: 300,width: 750, title:'费用标准修改',
				modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgChargeStanDict(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
		 
    /*var index = layer.open({
					type : 2,
					title : '更新',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'budgChargeStanDictUpdatePage.do?isCheck=false&' + parm
				});
				layer.full(index);	
    */
    }
    //打印
    function printDate(){
    	if(grid.getData().length==0){
			$.ligerDialog.error("无打印数据！");
			return;
		}
	    
    	grid.options.parms=[];
    	grid.options.newPage=1;
		
        grid.options.parms.push({name:'charge_stan_code',value:$("#insurance_code").val() });
        grid.options.parms.push({name:'charge_stan_nature',value:liger.get("charge_stan_nature").getValue() });
        grid.options.parms.push({name:'is_stop',value:liger.get("is_stop").getValue() });
        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"费用标准字典信息",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryBudgChargeStanDict.do?isCheck=false", selPara, function(responseData) {
   			printGridView(responseData,printPara);
    	});
    }
    
    //对应科室维护
    function deptSet(){
    	$.ligerDialog.open({ url : 'budgChargeStanDeptSetPage.do?isCheck=false',data:{},height: 300,width: 750,
			title:'费用标准对应科室维护页面',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true
		}); 
    }
    
    //取值方法维护
    function getWay(){
    	debugger;
    	var data = grid.getCheckedRows();
    	if(data.length != 1 ){
    		$.ligerDialog.error("请选择一行数据,进行取值方法维护！");
    	}else{
    		$.ligerDialog.open({ url : '../stanget/budgChargeStandGetWayUpdatePage.do?isCheck=false&charge_stand_code=' + data[0].charge_stan_code,data:{},height: 300,width: 750,
    			title:'费用标准取值方法维护页面',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true
    		}); 
    	}
    	
    }
    function loadDict(){
        //字典下拉框
        //费用标准性质下拉框
        autocomplete("#charge_stan_nature","../../../queryBudgCharStanNature.do?isCheck=false","id","text",true,true);
        
		autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text", true, true);
    	
    	$("#charge_stan_code").ligerTextBox({width:160});
    	$("#charge_stan_nature").ligerTextBox({width:160});
        $("#is_stop").ligerTextBox({width:160});
    }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add_open);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		hotkeys('P', printDate);
		hotkeys('I', imp);
		
		hotkeys('S', deptSet);
		hotkeys('M', getWay);

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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">费用标准：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_stan_code" type="text" id="charge_stan_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">费用标准性质：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_stan_nature" type="text" id="charge_stan_nature" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}" />
            	</td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
