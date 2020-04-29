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
    	grid.options.parms.push({name:'awards_item_code',value:liger.get("awards_item_code").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '奖金项目编码', name: 'awards_item_code', align: 'left',
                    	 	render:function(rowdata,rowindex,value){
                    	 		return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + 
									rowdata.hos_id  + "|" + rowdata.copy_code + "|" + 
									rowdata.awards_item_code +"')>"+rowdata.awards_item_code+"</a>";
                    	 	}
					 	},
					 { display: '奖金项目名称', name: 'awards_item_name', align: 'left'
					 		},
                     { display: '编制方法', name: 'method_name', align: 'left'
					 		},
                     { display: '取值方法', name: 'get_way_name', align: 'left'
					 		},
                     { display: '计算公式', name: 'formula_name', align: 'left'
					 		},
                     { display: '取值函数', name: 'fun_name', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgAwardsEditMethod.do?isCheck=false',
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
								rowdata.awards_item_code 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
		$.ligerDialog.open({ url :'budgAwardsEditMethodAddPage.do?isCheck=false',data:{}, height: 300,width: 650,
			title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgAwardsEditMethod(); },
			cls:'l-dialog-btn-highlight' },{ text: '取消', onclick: function (item, dialog) { dialog.close();}}]
    	}); 
		
    	/*var index = layer.open({
					type : 2,
					title : 'BUDG_AWARDS_EDIT_METHOD',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'budgAwardsEditMethodAddPage.do?isCheck=false'
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
				this.awards_item_code 
				) 
			});
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteBudgAwardsEditMethod.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
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
					content : 'budgAwardsEditMethodImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    }	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm ="group_id="+vo[0] +"&hos_id="+vo[1] +"&copy_code="+vo[2] +"&awards_item_code="+vo[3] 
		 
		 
		 $.ligerDialog.open({url:'budgAwardsEditMethodUpdatePage.do?isCheck=false&'+parm,data:{},height:300,width:650, 
				 title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgAwardsEditMethod();},
				cls:'l-dialog-btn-highlight' },{ text: '取消', onclick: function (item, dialog) { dialog.close();}}]
    	}); 
		 
    /*var index = layer.open({
					type : 2,
					title : '更新',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'budgAwardsEditMethodUpdatePage.do?isCheck=false&' + parm
				});
				layer.full(index);	
    */
    }
	function loadDict(){
	     //字典下拉框
	    //奖金项目下拉框
	    autocomplete("#awards_item_code","../../../queryBudgAwardsItem.do?isCheck=false","id","text",true,true);
	     
		$("#awards_item_code").ligerTextBox({width:160});
	}  
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		//hotkeys('P', printDate);
		hotkeys('I', imp);
		

	 }
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">奖金项目编码：</td>
            <td align="left" class="l-table-edit-td"><input name="awards_item_code" type="text" id="awards_item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
