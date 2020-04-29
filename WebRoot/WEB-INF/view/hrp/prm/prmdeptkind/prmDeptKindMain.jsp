<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%-- <jsp:include page="${path}/inc.jsp"/> --%>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    
    
    //页面初始化
    $(function (){
        loadDict()//加载下拉框
    	loadHead(null);//加载数据
        toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
		
        $("#dept_kind_code").ligerTextBox({width:160});
        $("#dept_kind_name").ligerTextBox({width:160});
        $("#dept_kind_note").ligerTextBox({width:160});
        $("#is_stop").ligerComboBox({width:160 });
		
    });
    
    
    //查询
    function  query(){
   		grid.options.parms=[];
   		grid.options.newPage=1;
        //根据表字段进行添加查询条件
          
   	  	grid.options.parms.push({name:'dept_kind_code',value:liger.get("dept_kind_code").getValue()}); 
   	 	grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
	}
  
  	
    
    //加载grid
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '科室分类编码', name: 'dept_kind_code', align: 'left',render : 
					function(rowdata, rowindex,value) {
						return "<a href=javascript:openUpdate('"+
								rowdata.dept_kind_code   + "|" + 
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.acc_year  +"')>"
									+rowdata.dept_kind_code
								+"</a>";
 							}
				},
                { display: '科室分类名称', name: 'dept_kind_name', align: 'left'},
                { display: '科室分类说明', name: 'dept_kind_note', align: 'left'},
                { display: '是否停用', name: 'is_stop', align: 'left',render :
                	function(rowdata, rowindex,value) {
						if(rowdata.is_stop == 0){
							return "否";
						}else{
							return "是"
						}
 					}
				}
			],
            dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmDeptKind.do',
            width: '100%', height: '100%', checkbox: true,rownumbers:true,
            selectRowButtonOnly:true,//heightDiff: -10,
			onDblClickRow : 
				function (rowdata, rowindex, value){
					openUpdate(
						rowdata.dept_kind_code + "|" + 
						rowdata.group_id   + "|" + 
						rowdata.hos_id   + "|" + 
						rowdata.copy_code   + "|" 
						
					);
				} 
		});

         gridManager = $("#maingrid").ligerGetGridManager();
    }
	
    //打开修改页
    function openUpdate(obj){

		var vo = obj.split("|");

		var parm = "&dept_kind_code="+
			vo[0]   +"&group_id"+ 
			vo[1]   +"&hos_id"+ 
			vo[2]   +"&copy_code"+ 
			vo[3] 
		
    	$.ligerDialog.open({ url : 'prmDeptKindUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.savePrmDeptKind(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
  	//工具栏
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addDeptKind, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteDeptKind,icon:'delete' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
    
    //键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		 
		hotkeys('A',addDeptKind);
		
		hotkeys('D',deleteDeptKind);
		
	}
    
    function addDeptKind(){
    	
    	$.ligerDialog.open({url: 'prmDeptKindAddPage.do?isCheck=false', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.savePrmDeptKind(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    	
    }
    
    function deleteDeptKind(){
    	
  	  var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.dept_kind_code 
				) 
			});
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deletePrmDeptKind.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
  	
  }
    
    function downTemplateDeptKind(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    }
    
    
    function importDeptKind(){
    	
    	$.ligerDialog.open({url: 'prmDeptKindImportPage.do?isCheck=false', height: 450,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
    }
    
    //字典下拉框
    function loadDict(){

		autocomplete("#dept_kind_code","../queryPrmDeptKind.do?isCheck=false","id","text",true,true);
	}  
    
    
	 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室分类编码：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td">           
               	<select id="is_stop" name="is_stop" style="width: 135px;">
               	                    <option value=""></option>
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                				</select></td>
            <td align="left"></td>
        </tr> 
    </table>
	
	<div id="toptoolbar" ></div>
	<div id="maingrid"></div>
</body>
</html>
