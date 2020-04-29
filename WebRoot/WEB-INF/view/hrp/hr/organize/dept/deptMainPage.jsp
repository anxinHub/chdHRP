<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	loadHead("");	//加载数据
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	/* if(liger.get("dept_code").getValue() != null && liger.get("dept_code").getValue() != ''){
    		grid.options.parms.push({name:'dept_codes',value:liger.get("dept_code").getText().split(" ")[0]}); 
        } */
   	    if(liger.get("kind_code").getValue() != null && liger.get("kind_code").getValue() != ''){
   	    	grid.options.parms.push({name:'kind_code',value:liger.get("kind_code").getValue()}); 
        }
   	    if(liger.get("is_last").getValue() != null && liger.get("is_last").getValue() != ''){
   	    	grid.options.parms.push({name:'is_last',value:liger.get("is_last").getValue()}); 
        }
   	    if(liger.get("is_disable").getValue() != null && liger.get("is_disable").getValue() != ''){
   	    	grid.options.parms.push({name:'is_disable',value:liger.get("is_disable").getValue()}); 
        }
	    	grid.options.parms.push({name:'dept_id',value:$("#dept_id").val()}); 
        //根据表字段进行添加查询条件
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    
    function loadHead(dept_id){
   		grid = $("#maingrid").ligerGrid({
	           columns: [ 
	                     { display: '部门编码', name: 'dept_code', align: 'left',width:100,render : function(rowdata, rowindex,
									value) {
								return "<a href=javascript:updateDept('"+rowdata.group_id   + "|" + rowdata.hos_id   + "|" + rowdata.dept_id+"')>"+rowdata.dept_code+"</a>";
						}},
	                     { display: '部门名称', name: 'dept_name', align: 'left',width:180,
	                    	 render : function(rowdata, rowindex, value) {
									return formatSpace(rowdata.dept_name,rowdata.dept_level - 1);
								}
						 },
						 { display: '类别', name: 'kind_name', align: 'left',width:100},
					 	 { display: '上级编码', name: 'super_code', align: 'left',
							 render : function(rowdata, rowindex,value) {
								if(value == '0'){
									return '';
								}else{
									return value
								}
							},width:100}, 
						 /* { display: '部门级次', name: 'dept_level', align: 'left',width:60}, */
						 { display: '自定义编码', name: 'udefine_code', align: 'left',width:100},
					 	 { display: '排序号', name: 'sort_code', align: 'left',width:100},
						 { display: '是否末级', name: 'is_last', align: 'left',
								render : function(rowdata, rowindex,value) {
									if(rowdata.is_last == 0){
										return "否";
									}else{
										return '<span style="color:red;"> 是</span>'
									}
								},width:60
					 	 },
	                     { display: '操作人', name: 'user_name', align: 'left',width:100},
	                     { display: '操作时间', name: 'create_date', align: 'left',width:100},
						 { display: '是否停用', name: 'is_disable', align: 'left',
								render : function(rowdata, rowindex,value) {
									if(rowdata.is_disable == 0){
										return "否";
									}else{
										return '<span style="color:red;"> 是</span>'
									}
								},width:60
					 	 },
					 	 { display: '操作原因', name: 'dlog', align: 'left',width:100},
					 	 { display: '拼音码', name: 'spell_code', align: 'left',width:100},
						 { display: '五笔码', name: 'wbx_code', align: 'left',width:100},
						 { display: '备注', name: 'note', align: 'left',width:180}
						 ],
					 dataAction: 'server',dataType: 'server',usePager:true,url:'../deptDict/queryDeptDict.do?isCheck=false',
                     height: '98%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,
                     toolbar:{ items: [
									   { text: '查询', id:'search', click: query,icon:'search' },
									   { line:true },
                                       {text: '添加', icon:'add', click:addDept },
									   { line:true },
                                       {text: '删除',icon:'delete', click:deleteDept  },
									   { line:true },
                                       {text: '导入',icon:'up', click:importDept  },
									   { line:true },
                                       {text: '模板', icon:'down', click:downTemp },
									   { line:true },
                                       {text: '打印', icon:'print', click:print },
									   { line:true },
									   { text: '部门架构图', click: openOrg , icon: 'merge' }
                    ]},
                    checkbox:true,
                    onDblClickRow : function (rowdata, rowindex, value)
    				{
                    	updateDept(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.dept_id
							);
    				} 
   		});
    	gridManager = $("#maingrid").ligerGetGridManager();
    }

    function loadDict(){
            //字典下拉框
            autocomplete("#kind_code","../deptKind/queryDeptKindDict.do?isCheck=false","id","text",true,true);
           // autocomplete("#dept_code","../queryDeptDict.do?isCheck=false","id","text",true,true);
	       	$("#is_disable").ligerComboBox({
	       		 data:[{id:1,text:'是'},{id:0,text:'否'}],
	       		 valueField: 'id',
	                textField: 'text',
	    			 cancelable:true
	       	 ,width:160});
	       	$("#is_last").ligerComboBox({
	       		 data:[{id:1,text:'是'},{id:0,text:'否'}],
	       		 valueField: 'id',
	                textField: 'text',
	    			 cancelable:true
	       	 ,width:160});
	       	$("#dept_id").ligerTextBox({width:160});
         }

    </script>

	<script type="text/javascript">
       
       $(function ()
       {
		   $("#btn_saveColumn").click(function(){
			   var path = window.location.pathname+"/maingrid";
			   var url = '../addBatchSysTableStyle.do?isCheck=false';
			   saveColHeader({
				   grid:grid,
				   path:path,
				   url:url
			   });
		   })
       });
       function downTemp(){
    	   location.href = "downTemplate.do?isCheck=false";
       }
       
       function addDept(){
    	   parent.$.ligerDialog.open({
    		   url: 'hrp/hr/dept/deptAddPage.do?isCheck=false',
    				   height: 300,width: 700, title:'新增部门',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true ,
    			   parentframename: window.name,
    		   buttons: [ 
				           { text: '保存', onclick: function (item, dialog) { dialog.frame.saveDept(); },cls:'l-dialog-btn-highlight' }, 
				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
				         ]});
    	   query();
       }
       function updateDept(rowStr){
    	   var rowArr=rowStr.split("|");
    		parent.$.ligerDialog.open(
    			   {url : 'hrp/hr/dept/deptUpdatePage.do?isCheck=false&dept_id=' + rowArr[2]+'&hos_id='+rowArr[1]+'&group_id='+rowArr[0], 
    			    height: 400,width: 700,  title:'部门修改', modal:true,showToggle:false, showMax:false,showMin: true,isResize:true ,   parentframename: window.name,
    		   buttons: [ 
				           { text: '保存', onclick: function (item, dialog) { dialog.frame.saveGroup();},cls:'l-dialog-btn-highlight' }, 
				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
				         ]});
    	   query();
       }
       function importDept(){
    	   $.ligerDialog.open({url: 'deptImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
       }
       function openOrg(){
       	parent.$.etDialog.open({
               url: 'hrp/hr/dept/deptOrgPage.do?isCheck=false',
               title: '组织架构图',
               width: $(window).width(),
               height: $(window).height()-100,
               btn: ['取消' ]
           })
       }
       function deleteDept(){
   		var node = grid.getSelectedRows();
   		if(node == ""){
   			$.ligerDialog.error('请先选择部门');
   			return;
   		}else{
   			
   		    var ParamVo =[];
            $(node).each(function (){					
				ParamVo.push(
				//表的主键
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.dept_code +"@"+ 
						this.dept_id +"@"+ 
						this.super_code 
				)
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteDept.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            });
   			
   			
   		 
   		}
   	}
     //打印
   	function print(){
       	
       	if(grid.getData().length==0){
       		
   			$.ligerDialog.error("请先查询数据！");
   			
   			return;
   		}
       	
       	var selPara={};
       	console.log(grid.options.parms)
       	$.each(grid.options.parms,function(i,obj){
       	
       		selPara[obj.name]=obj.value;
       	  
       	});
      		
     
   		var dates = getCurrentDate();
   		
       	var cur_date = dates.split(";")[2];
       	//跨所有列:计算列数
       	var colspan_num = grid.getColumns(1).length-1;
       	
      		var printPara={
      			title:'部门维护',
      			head:[
   				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
   			/* 	{"cell":0,"value":"统计日期: " + $("#begin_in_date").val() +" 至  "+ $("#end_in_date").val(),"colspan":colspan_num,"br":true} */
      			],
      			foot:[
   				{"cell":0,"value":"主管:","colspan":3,"br":false} ,
   				{"cell":3,"value":"复核人:","colspan":colspan_num-5,"br":false},
   				{"cell":colspan_num-2,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":true},
   				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
      			],
      			columns:grid.getColumns(1),
      			headCount:1,//列头行数
      			autoFile:true,
      			type:3
      		};
      		ajaxJsonObjectByUrl("../deptDict/queryDeptDict.do?isCheck=false", selPara, function (responseData) {
      			printGridView(responseData,printPara);
   		});

      		
       }

   </script>
</head>

 <body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading"></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <!-- <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td> -->
            
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:50}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门分类：</td>
            <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否是末级：</td>
            <td align="left" class="l-table-edit-td"><input name="is_last" type="text" id="is_last" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_disable" type="text" id="is_disable" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
           
       </tr>
      </table>
      <div id="maingrid"></div>
	
</body>
</html>
