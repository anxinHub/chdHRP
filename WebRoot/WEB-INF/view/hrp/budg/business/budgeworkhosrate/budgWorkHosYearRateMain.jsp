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
        $("#year").ligerTextBox({width:160});
        $("#index_code").ligerTextBox({width:160});
        $("#measure_name").ligerTextBox({width:160});
        $("#measure_value").ligerTextBox({width:160});
        $("#rate").ligerTextBox({width:160});
        $("#count_value").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'year',value:$("#year").val()}); 
    	  grid.options.parms.push({name:'index_code',value:$("#index_code").val()}); 
    	  grid.options.parms.push({name:'measure_name',value:$("#measure_name").val()}); 
    	  grid.options.parms.push({name:'measure_value',value:$("#measure_value").val()}); 
    	  grid.options.parms.push({name:'rate',value:$("#rate").val()}); 
    	  grid.options.parms.push({name:'count_value',value:$("#count_value").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#year").val()!=""){
                		return rowdata.year.indexOf($("#year").val()) > -1;	
                	}
                	if($("#index_code").val()!=""){
                		return rowdata.index_code.indexOf($("#index_code").val()) > -1;	
                	}
                	if($("#measure_name").val()!=""){
                		return rowdata.measure_name.indexOf($("#measure_name").val()) > -1;	
                	}
                	if($("#measure_value").val()!=""){
                		return rowdata.measure_value.indexOf($("#measure_value").val()) > -1;	
                	}
                	if($("#rate").val()!=""){
                		return rowdata.rate.indexOf($("#rate").val()) > -1;	
                	}
                	if($("#count_value").val()!=""){
                		return rowdata.count_value.indexOf($("#count_value").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '年度', name: 'year', align: 'left'
					 		},
                     { display: '指标编码', name: 'index_code', align: 'left'
					 		},
                     { display: '运营尺度名称', name: 'measure_name', align: 'left'
					 		},
                     { display: '运营预期', name: 'measure_value', align: 'left'
					 		},
                     { display: '概率', name: 'rate', align: 'left'
					 		},
                     { display: '计算值', name: 'count_value', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgWorkHosYearRate.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
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
								rowdata.year   + "|" + 
								rowdata.index_code   + "|" + 
								rowdata.measure_name 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
		$.ligerDialog.open({ url : 'budgWorkHosYearRateAddPage.do?isCheck=false&',data:{}, height: 300,width: 450, title:'医院年度业务概率预算',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.savebudgWorkHosYearRate(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
		
    	/*var index = layer.open({
					type : 2,
					title : '医院年度业务概率预算',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'budgWorkHosYearRateAddPage.do?isCheck=false'
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
							this.year   +"@"+ 
							this.index_code   +"@"+ 
							this.measure_name 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteBudgWorkHosYearRate.do",{ParamVo : ParamVo.toString()},function (responseData){
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
					content : 'budgWorkHosYearRateImportPage.do?isCheck=false'
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
			"year="+vo[3]   +"&"+ 
			"index_code="+vo[4]   +"&"+ 
			"measure_name="+vo[5] 
		 
		 
		 $.ligerDialog.open({ url : 'budgWorkHosYearRateUpdatePage.do?isCheck=false&',data:{}, height: 300,width: 450, title:'医院年度业务概率预算',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.savebudgWorkHosYearRate(); },cls:'l-dialog-btn-highlight' },
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
					content : 'budgWorkHosYearRateUpdatePage.do?isCheck=false&' + parm
				});
				layer.full(index);	
    */
    }
    function loadDict(){
            //字典下拉框
            
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
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年度：</td>
            <td align="left" class="l-table-edit-td"><input name="year" type="text" id="year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标编码：</td>
            <td align="left" class="l-table-edit-td"><input name="index_code" type="text" id="index_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">运营尺度名称：</td>
            <td align="left" class="l-table-edit-td"><input name="measure_name" type="text" id="measure_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">运营预期：</td>
            <td align="left" class="l-table-edit-td"><input name="measure_value" type="text" id="measure_value" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">概率：</td>
            <td align="left" class="l-table-edit-td"><input name="rate" type="text" id="rate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计算值：</td>
            <td align="left" class="l-table-edit-td"><input name="count_value" type="text" id="count_value" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
