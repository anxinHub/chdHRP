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
        $("#mat_type_code").ligerTextBox({width:160});
        $("#subj_code").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'budg_year',value:$("#budg_year").val()}); 
    	  grid.options.parms.push({name:'subj_code',value:$("#subj_code").val()}); 
    	  grid.options.parms.push({name:'mat_type_id',value:$("#mat_type_id").val()}); 
    	  grid.options.parms.push({name:'mat_type_no',value:$("#mat_type_no").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
        	if($("#budg_year").val()!=""){
        		return rowdata.budg_year.indexOf($("#budg_year").val()) > -1;	
        	}
        	if($("#mat_type_id").val()!=""){
        		return rowdata.mat_type_id.indexOf($("#mat_type_id").val()) > -1;	
        	}
        	if($("#mat_type_no").val()!=""){
        		return rowdata.mat_type_no.indexOf($("#mat_type_no").val()) > -1;	
        	}
        	if($("#subj_code").val()!=""){
        		return rowdata.subj_code.indexOf($("#subj_code").val()) > -1;	
        	}
        	if($("#mat_type_code").val()!=""){
        		return rowdata.subj_code.indexOf($("#mat_type_code").val()) > -1;	
        	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '物资分类编码', name: 'mat_type_code', align: 'left'
				 		},
             		 { display: '物资类别名称', name: 'mat_type_name', align: 'left'
				 		},
              		{ display: '预算支出科目编码', name: 'subj_code', align: 'left'
				 		},
             		 { display: '预算支出科目名称', name: 'subj_name', align: 'left'
				 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgMatTypeIncomeShip.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
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
								rowdata.budg_year   + "|" + 
								rowdata.mat_type_id  
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
		$.ligerDialog.open({ url : 'budgMatTypeIncomeShipAddPage.do?isCheck=false&',data:{}, height: 1000,width: 1400, title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgMatTypeIncomeShip(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
		
    	/*var index = layer.open({
					type : 2,
					title : '末级物资分类对应预算收入科目。对应关系中要求物资分类对收入科目，为一对一或一对多。',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'budgMatTypeIncomeShipAddPage.do?isCheck=false'
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
							this.budg_year+"@"+ 
							this.mat_type_id 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteBudgMatTypeIncomeShip.do",{ParamVo : ParamVo.toString()},function (responseData){
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
					content : 'budgMatTypeIncomeShipImportPage.do?isCheck=false'
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
			"budg_year="+vo[3]   +"&"+ 
			"mat_type_id="+vo[4] 
		 
		 
		 $.ligerDialog.open({ url : 'budgMatTypeIncomeShipUpdatePage.do?isCheck=false&'+parm,data:{}, height: 300,width: 450, title:'更新',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.updateBudgMatTypeIncomeShip(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
		 
   
    }
    
    //打印
    function printDate(){
    	if(grid.getData().length==0){
			$.ligerDialog.error("无打印数据！");
			return;
		}
	    
    	grid.options.parms=[];
    	grid.options.newPage=1;
		
      grid.options.parms.push({name:'budg_year',value:$("#budg_year").val()}); 
   	  grid.options.parms.push({name:'subj_code',value:$("#subj_code").val()}); 
   	  grid.options.parms.push({name:'mat_type_id',value:$("#mat_type_id").val()}); 
   	  grid.options.parms.push({name:'mat_type_no',value:$("#mat_type_no").val()}); 
        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"物资分类与预算支出科目对应关系",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryBudgMatTypeIncomeShip.do?isCheck=false", selPara, function(responseData) {
   			printGridView(responseData,printPara);
    	});
    }
    function loadDict(){
            //字典下拉框
    	 autocompleteAsync("#budg_year","../../../queryBudgYear.do?isCheck=false","id","text",true,true,'',true);
    	 budg_year = liger.get("budg_year").getValue();
    	 autocomplete("#subj_code","../../../queryBudgSubj.do?isCheck=false&subj_type="+'04'+"&budg_year="+budg_year,"id","text",true,true);
    	 autocomplete("#mat_type_code","../../../queryMatTypes.do?isCheck=false","id","text",true,true);
    	 $("#subj_code").ligerTextBox({width:160}); 
    	 $("#mat_type_code").ligerTextBox({width:160});  
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
       <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算年度：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资分类：</td>
            <td align="left" class="l-table-edit-td"><input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算支出科目：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
