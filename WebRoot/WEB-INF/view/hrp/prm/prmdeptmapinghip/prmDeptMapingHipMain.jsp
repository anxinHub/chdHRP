<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">


    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    
    //页面初始化
    $(function (){
    	
        loadDict();//加载下拉框
    	loadHead(null);	//加载数据
		
        $("#dept_id").ligerTextBox({width:160});
        $("#sys_dept_id").ligerTextBox({width:160});
        $("#ref_code").ligerTextBox({width:160});
        $("#spilt_perc").ligerTextBox({width:160});
        
        toolbar();//加载工具栏
        loadHotkeys();//加载快捷键
    });
    
    
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	 
		grid.options.parms.push({name:'dept_id',value:liger.get("dept_code1").getValue()}); 
		grid.options.parms.push({name:'sys_dept_id',value:liger.get("dept_code").getValue()}); 
		grid.options.parms.push({name:'ref_code',value:liger.get("ref_code").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
	}
   
	
    //加载grid
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '绩效科室编码', name: 'dept_code', align: 'left',render : 
					function(rowdata, rowindex, value) {
						return "<a href=javascript:openUpdate('"
								+ rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.dept_id   + "|" + 
								rowdata.sys_dept_id  + "')>"
								+ rowdata.dept_code + "</a>";
					}
	   	 		},
	   	 		
               	{ display: '绩效科室编码', name: 'dept_name', align: 'left'},
               	
               	{ display: '平台科室编码', name: 'sys_dept_code', align: 'left'},
               	
               	{ display: '平台科室名称', name: 'sys_dept_name', align: 'left'},
               	
               	{ display: '关系', name: 'ref_name', align: 'left'},
               	
               	{ display: '拆分比例', name: 'spilt_perc', align: 'left'}
			],
            dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmDeptMapingHip.do',
            width: '100%', height: '100%', checkbox: true,rownumbers:true,
            selectRowButtonOnly:true,//heightDiff: -10,
			onDblClickRow : function (rowdata, rowindex, value){
				openUpdate(
					rowdata.group_id   + "|" + 
					rowdata.hos_id   + "|" + 
					rowdata.copy_code   + "|" + 
					rowdata.dept_id   + "|" + 
					rowdata.sys_dept_id 
				);
			} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
    
    
  	//工具栏
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addDeptMaping, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteDeptMaping,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: templateDeptMaping,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importDeptMaping,icon:'up' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
  	
  	
    //键盘事件
	function loadHotkeys(){
		hotkeys('Q',query);
		hotkeys('A',addDeptMaping);
		hotkeys('D',deleteDeptMaping);
		hotkeys('T',templateDeptMaping);
		hotkeys('I',importDeptMaping); 
	}
    
    
    //添加页跳转
    function addDeptMaping(){
    	parent.$.ligerDialog.open({
    		url: 'hrp/prm/prmdeptmapinghip/prmDeptMapingHipAddPage.do?isCheck=false', height: $(window).height(),
			width: $(window).width(), title:'添加',modal: true, showToggle: false, showMax: true, 
			showMin: false, isResize: true,parentframename: window.name 
		});
    }
    
    
    //删除
    function deleteDeptMaping (){
    	
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ; 
        }
        
        
        var ParamVo =[];
        $(data).each(function (){					
			ParamVo.push(
				this.group_id   +"@"+ 
				this.hos_id   +"@"+ 
				this.copy_code   +"@"+ 
				this.dept_id   +"@"+ 
				this.sys_dept_id 
			) 
		});
        
        $.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("deletePrmDeptMappingHip.do",{ParamVo : ParamVo},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
    }
    
  
    //下载导入模板
    function templateDeptMaping(){
    	location.href = "downTemplate.do?isCheck=false";
    	
    }
    
    
    //导入
    function importDeptMaping (){
    	$.ligerDialog.open({
    		url: 'prmDeptMapingHipImportPage.do?isCheck=false', height: 500,width: 800, 
    		title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true 
    	});
    }
    
   	
    //修改页
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "group_id="+
			vo[0]   +"&hos_id="+ 
			vo[1]   +"&copy_code="+ 
			vo[2]   +"&dept_id="+ 
			vo[3]   +"&sys_dept_id="+ 
			vo[4];
		
		
		parent.$.ligerDialog.open({
			url: 'hrp/prm/prmdeptmapinghip/prmDeptMapingHipUpdatePage.do?isCheck=false&'+parm, 
			height: $(window).height(),width: $(window).width(), title:'修改',modal: true, 
			showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name 
		});
    }
    
    
  	//字典下拉框
    function loadDict(){
    	autocomplete("#dept_code1","../../hpm/queryAphiDeptDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#dept_code","../../prm/queryPrmDeptHipName.do?isCheck=false","id","text",true,true);
    	autocomplete("#ref_code","../queryPrmDeptRefDict.do?isCheck=false","id","text",true,true);
	}  
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">绩效科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_code1" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">平台科室：</td>
            <td align="left" class="l-table-edit-td"><input name="sys_dept_id" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">关系</td>
            <td align="left" class="l-table-edit-td"><input name="ref_code" type="text" id="ref_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
           
        </tr> 
    </table>
	
	<div id="toptoolbar" ></div>
	<div id="maingrid"></div>
</body>
</html>
