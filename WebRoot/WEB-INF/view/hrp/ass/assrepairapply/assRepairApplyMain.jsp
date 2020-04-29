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
    	  grid.options.parms.push({name:'apply_no',value:$("#apply_no").val()}); 
    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
    	  grid.options.parms.push({name:'note',value:$("#note").val()}); 
    	  grid.options.parms.push({name:'create_date',value:$("#create_date").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '申请单号', name: 'apply_no', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
                    		 
    							return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
  																	rowdata.hos_id   + "|" + 
  																	rowdata.copy_code   + "|" + 
  																	rowdata.apply_no   +"')>"+
  																	value+"</a>";
    										}
					 		},
                     { display: '维修部门', name: 'repair_dept_id', align: 'left',textField : 'dept_name',
					 		},
                     { display: '资产名称', name: 'ass_name', align: 'left'
					 		},
                     { display: '申请人', name: 'apply_emp', align: 'left',textField : 'apply_name',
					 		},
                     { display: '申请日期', name: 'apply_date', align: 'left'
					 		},
                     { display: '制单人', name: 'create_emp', align: 'left',textField : 'create_name',
					 		},
                     { display: '制单日期', name: 'create_date', align: 'left'
					 		},
			 		 { display: '报修人电话', name: 'rep_phone', align: 'left'
				 			},
	 				 { display: '维修班组', name: 'rep_team_code', align: 'left',textField : 'rep_team_name',
					 		},
                     { display: '状态', name: 'state_name', align: 'left',width:'100'
					 		},
			 		{ display: '故障内容', name: 'note', align: 'left'
					 		},
                     { display: '紧急程度', name: 'sharp_degree', align: 'left',textField : 'sharp_name',
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssRepairApply.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
						                { line:true },
						                { text: '维修确认', id:'audit', click:audit,icon:'audit' },
						            /*     { line:true },
						                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' } */
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code + "|" + 
								rowdata.apply_no 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
    	parent.$.ligerDialog.open({
			title: '维修申请添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assrepairapply/assRepairApplyAddPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: false, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAssRepairApply(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
		});
    	}
    	
    function openUpdate(obj){
		
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]  +"&"+ 
			"apply_no="+vo[3];
		parent.$.ligerDialog.open({
			title: '维修申请修改',
			height: $(window).height() / 2,
			width: $(window).width()  / 1.8,
			url: 'hrp/ass/assrepairapply/assRepairApplyUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: false, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAssRepairApply(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
					 ]
		});
    }
    function audit(){
    	
    	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code +"@"+ 
							this.apply_no 
							) });
                        $.ligerDialog.confirm('维修确认?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("auditAssRepairApply.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
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
							this.copy_code +"@"+ 
							this.apply_no 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssRepairApply.do",{ParamVo : ParamVo.toString()},function (responseData){
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
					content : 'assRepairApplyImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    
    function loadDict(){
            //字典下拉框
        $("#apply_no").ligerTextBox({width:160});
        $("#create_date").ligerTextBox({width:160});
        /* $("#state").ligerTextBox({width:160}); */
        //状态下拉框
        $('#state').ligerComboBox({
		data:[{id:0,text:'新建'},{id:1,text:'审核'}],
		valueField: 'id',
        textField: 'text',
		cancelable:true,
		width:160
	});
        $("#note").ligerTextBox({width:160});
        autodate("#create_date","YYYY-mm-dd");

		
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

	 }
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">申请单号：</td>
            <td align="left" class="l-table-edit-td"><input name="apply_no" type="text" id="apply_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
          <td align="right" class="l-table-edit-td"   >制单日期：</td>
            <td align="left" class="l-table-edit-td"><input  name="create_date" type="text" id="create_date"
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
			</td>
			<!-- <td align="left" >至：</td>
			<td align="left" ><input name="create_date_end" type="text" id="create_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
			</td> -->
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td"><input name="state" type="text" id="state" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">故障内容：</td>
            <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
