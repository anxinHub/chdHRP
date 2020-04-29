<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
    var grid, rightgrid, para;
    var gridManager = null;
    var rightgridManager = null;
    
    $(function () {
    	loadDict();
    	loadHead();	//加载数据
    	$("#emp_code").ligerTextBox({ width: 160 });
    	$("#wage_name").ligerTextBox({width:160,disabled:true});
    });
    
    function loadDict(){
    	autocomplete("#emp_code","../queryEmpDict.do?isCheck=false&is_stop=0","id","text",true,true);
    }
    
	//查询
    function  gridQuery(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	//加载查询条件
    	grid.loadData(grid.where);
	}
  
    //查询工资套下的职工分类
    function query(obj){
    	$("#emp_code").val("");
    	
		if(obj=="update" || obj=="add"){
    		return;
    	}
   		if(obj.toString().indexOf("object") > -1 && para==null){
   			$.ligerDialog.error('请选择工资套！');
           	return;
   		}else if(obj.toString().indexOf("object")<0){
   			obj = $.parseJSON(obj);
   			para = obj.wage_code;
   			$("#wage_name").val(obj.wage_name);
   			$("#wage_code").val(para)
   		}
    	rightgrid.options.parms=[];
    	rightgrid.options.newPage=1;
        //根据表字段进行添加查询条件
        rightgrid.options.parms.push({name:'wage_code',value:para}); 
        
    	//加载查询条件
    	rightgrid.loadData(rightgrid.where);
	}

    // 加载grid
    function loadHead(){
    	// 工资套grid
		grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '工资套编码', name: 'wage_code', align: 'left',width:'25%',
					render:function(rowdata){
						return "<a href=javascript:openUpdate('"+rowdata.wage_code+"|"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"')>"+rowdata.wage_code+"</a>";
					}
				},
				{ display: '工资套名称', name: 'wage_name', align: 'left', width:'50%' },
				{ display: '操作', name: 'edit', align: 'center', width:'25%',
					render : function(rowdata) {
						return "<a href=javascript:query('"+JSON.stringify(rowdata)+"')>查看<a/>";
					}
				}
			],
			dataAction: 'server',
			dataType: 'server', 
			usePager : false, 
			url:'../accwage/queryAccWage.do',
			width: '100%', 
			height: '100%', 
			checkbox: true,
			rownumbers:true,
			enabledEdit: true,
			selectRowButtonOnly:true,//heightDiff: -10,
			onDblClickRow:function (data, rowindex, rowobj) {
				query(data);
			},
			toolbar: {
				items: [
					{ text: '添加工资套', id:'add', click: itemclick,icon:'add' },
					{ line:true },
					{ text: '删除工资套', id:'delete', click: itemclick,icon:'delete' },
					{ line:true }
				]
			}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
        
        // 工资套下职工grid
        rightgrid = $("#rightgrid").ligerGrid({
            columns: [ 
                { display: '职工编码', name: 'emp_code', align: 'left', width:'30%' },
				{ display: '职工姓名', name: 'emp_name', align: 'left', width:'30%' }
                <%--
                { display: '职工分类编码', name: 'kind_code', align: 'left', width:'30%' },
				{ display: '职工分类名称', name: 'kind_name', align: 'left', width:'30%' }
                --%>
			],
			dataAction: 'server',
			dataType: 'server',
			usePager : true, 
			<%--url : '../accwageempkind/queryAccWageEmpKind.do',--%>
			url : 'queryAccWageEmp.do?isCheck=false',
			width: '100%', 
			height: '95%', 
			checkbox: true,
			rownumbers:true,
			enabledEdit: true,
			delayLoad:true,
			selectRowButtonOnly:true,//heightDiff: -10,  
			toolbar: { 
				items: [
					{ text: '查询', id:'query', click: queryWageEmp,icon:'search' },
					{ line:true },
					{ text: '添加', id:'add', click: addRow,icon:'add' },
					{ line:true },
					{ text: '删除', id:'del', click: delRow,icon:'delete' },
					{ line:true }/* ,
					{ text: '提交', id:'save', click: saveEmpKind,icon:'ok' } */
				]
			}
		});
		rightgridManager = $("#rightgrid").ligerGetGridManager();
    }
    
    // 查询工资套关联的职工
    function queryWageEmp(){
    	var wc = $("#wage_code").val();
    	if(!wc){
    		$.ligerDialog.warn('请选择工资套');
    		return;
    	}
    	rightgrid.options.parms = [];
    	rightgrid.options.parms.push({ name: 'emp_id', value: liger.get("emp_code").getValue().split(".")[0] });
    	rightgrid.options.parms.push({ name: 'wage_code',value: $("#wage_code").val() }); 
    	rightgrid.loadData(grid.where);
    }
    
    // 添加、删除工资套
    function itemclick(item){ 
        if(item.id){
            switch (item.id){
            	// 进入添加页
                case "add":
              		$.ligerDialog.open({url: 'accWageEmpAddPage.do?isCheck=false', height: 275,width: 400, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
              	
              	// 执行删除
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var paramVo =[];
                        $(data).each(function (){					
                        	paramVo.push(
							//表的主键
							this.wage_code
							)
                        });
                        $.ligerDialog.confirm('删除工资套，将删除与该工资套相关的所有数据', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("../accwage/deleteAccWage.do?isCheck=false",{paramVo : JSON.stringify(paramVo)},function (responseData){
                            		if(responseData.state=="true"){
                            			gridQuery();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
            }   
        }
    }
    
    // 打开工资套更新页面
    function openUpdate(obj){
    	var vo = obj.split("|");
		var parm = "wage_code="+
			vo[0]   +"&group_id="+ 
			vo[1]   +"&hos_id="+ 
			vo[2]   +"&copy_code="+ 
			vo[3];

    	$.ligerDialog.open({ url : '../accwage/accWageUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccWage(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
    
    // 添加工资套对应职工or职工类别
    function addRow(){
    	var wc = $("#wage_code").val();
    	if(!wc){
    		$.ligerDialog.warn('请选择工资套');
    	}else{
    		// 打开职工列表or职工类别列表
    		$.ligerDialog.open({
				title: '选择人员', 
				width: 800, 
				height: 600, 
				url: 'openEmpDataPage.do?isCheck=false&wageCode='+$("#wage_code").val(), 
				<%--url: 'openEmpKindDataPage.do?isCheck=false&wage_code='+$("#wage_code").val(),--%> 
				buttons: [
					{ text: '确定', onclick: f_importOK },
					{ text: '取消', onclick: f_importCancel }
				]
			}); 
    	}
    }
    
    // 确认选中职工数据
    function f_importOK(item, dialog){
    	var rows = dialog.frame.f_select();
    	var formPara = {
   			paramVo : JSON.stringify(rows),
   			wage_code : $("#wage_code").val()
   		};
   		<%--ajaxJsonObjectByUrl("../accwageempkind/saveAccWageEmpKind.do", formPara, function(responseData){--%>
   		ajaxJsonObjectByUrl("addAccWageEmp.do?isCheck=false", formPara, function(responseData){
   			query({wage_code:$("#wage_code").val()});
   		});
   		<%--
   		dialog.close();
   		--%>
    }
    
    // 关闭职工数据页面
    function f_importCancel(item, dialog){
    	dialog.close();
    }
    
    // 删除工资套对应的人职工or职工类别
    function delRow(){
    	var data = rightgridManager.getSelectedRows();
    	var paramVo = [];
        if (data.length == 0){
			$.ligerDialog.warn('请选择行');
        }else{
        	$.ligerDialog.confirm('确定删除此职工与该工资套的关联？', function (yes) {
        		if(yes){
        			$(data).each(function (){
        				paramVo.push(this);
        			});
        			var formPara = {
       					paramVo : JSON.stringify(data),
       					wage_code : $("#wage_code").val(),
       					
       				};
        			ajaxJsonObjectByUrl("deleteAccWageEmp.do?isCheck=false", formPara, function(){
        				query({wage_code:$("#wage_code").val()});
        			});
        		}
        	});
        	
        	<%--
        	$.ligerDialog.confirm('确定删除该职工分类及对应的计算公式，同时删除工资方案中对应的职工分类？', function (yes) {
        		if(yes){
        			$(data).each(function (){
        				paramVo.push(this);
        			});
        			var formPara = {
       					paramVo : JSON.stringify(data),
       					wage_code : $("#wage_code").val(),
       					
       				};
        			ajaxJsonObjectByUrl("../accwageempkind/deleteAccWageEmpKind.do", formPara, function(){
        				query({wage_code:$("#wage_code").val()});
        			});
        		}
        	});
        	--%>
		}
    }
    
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div style="width: 100%;">
		<div  style="float: left; width: 35%;">
			<div id="toptoolbar" ></div>
			<div id="maingrid"></div>
		</div>
		<div  style="float: left; width: 65%">
			<div id="righttoolbar" ></div>
			<table cellpadding="0" cellspacing="0" class="l-table-edit" >
		        <tr>
		        	<td align="right" class="l-table-edit-td" style="padding-left:20px;">职工：</td>
					<td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" /></td>
					<td align="left"></td>
		       		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套:</td>
		       		<td align="left">
		       			<input type="text" id="wage_name" name="wage_name" disabled="disabled">
		       			<input type="hidden" id="wage_code" name="wage_code">
		       		</td>
		        </tr> 
		    </table>
			 <div id="rightgrid"></div>
		</div>
	</div>
</body>
</html>
