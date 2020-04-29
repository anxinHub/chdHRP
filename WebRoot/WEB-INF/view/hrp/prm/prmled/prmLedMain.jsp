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
    	
        loadDict()//加载下拉框
    	loadHead(null);	//加载数据
    	loadHotkeys();//加载快捷键
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    		
    });
    
    
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		
        //根据表字段进行添加查询条件
		grid.options.parms.push({name:'sec_code',value:liger.get("sec_code").getValue()}); 
		grid.options.parms.push({name:'kpi_beg_score',value:$("#kpi_beg_score").val()}); 
		grid.options.parms.push({name:'kpi_end_score',value:$("#kpi_end_score").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
	}
	
    
    //加载grid
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           	columns: [ 
				{ display: '等级代码', name: 'sec_code', align: 'center',width:'100',render : 
					function(rowdata, rowindex,value) {
						return "<a href=javascript:openUpdate('" +
							rowdata.sec_code   + "|" + 
							rowdata.group_id   + "|" + 
							rowdata.hos_id   + "|" + 
							rowdata.copy_code   + "|" + 
							rowdata.acc_year  +"')>" +
							rowdata.sec_code+"</a>";
						}
				},
                { display: '等级名称', name: 'sec_name', align: 'center',width:'100'},
                
                { display: 'KPI起始分', name: 'kpi_beg_score', align: 'center',width:'100'},
                
                { display: 'KPI结束分', name: 'kpi_end_score', align: 'center',width:'100'},
                
 				{ display: '指示灯', name: 'led', align: 'center',width:'150',render : 
 					function(rowdata, rowindex, value) {
						return "<img style='margin-top: 7px;width:20px;' src='<%=path%>/"+rowdata.led_path+"' border='0' width ='50px' />";
					}
	   			},
                
	   			{ display: '指示灯路径', name: 'led_path', align: 'left'}
			],
            dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmLed.do',
            width: '100%', height: '100%', checkbox: true,rownumbers:true,
            selectRowButtonOnly:true,//heightDiff: -10,
  			onDblClickRow : function (rowdata, rowindex, value){
				openUpdate(
					rowdata.sec_code   + "|" +
					rowdata.group_id   + "|" + 
					rowdata.hos_id   + "|" + 
					rowdata.copy_code   + "|" + 
					rowdata.acc_year
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
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addLed, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteLed,icon:'delete' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplateLed,icon:'down' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importLed,icon:'up' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}

  	
   	//修改页跳转
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
				"&sec_code=" + vo[0] +
				"&group_id"+ vo[1] +
				"&hos_id" + vo[2] +
				"& copy_code" + vo[3] 
		
    	$.ligerDialog.open({ 
    		url : 'prmLedUpdatePage.do?isCheck=false&' + parm,data:{}, 
    		height: 350,width: 600, title:'修改',modal:true,showToggle:false,
    		showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.savePrmLed(); 
    				},cls:'l-dialog-btn-highlight' 
    			}, 
    			{ text: '取消', onclick: 
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		] 
    	});
    }
   	
   	
   	//快捷键
	function loadHotkeys(){
		
		hotkeys('Q',query);
		 
		hotkeys('A',addLed);
		
		hotkeys('D',deleteLed);
		
		hotkeys('P',print);
		
		hotkeys('T',downTemplateLed);
		
		hotkeys('I',importLed);
	}

	
   	//添加页面跳转
    function addLed(){
    	$.ligerDialog.open({
    		url: 'prmLedAddPage.do?isCheck=false', height: 350,width: 600, 
    		title:'添加',modal:true,showToggle:false,showMax:false,
    		showMin: true,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.savePrmLed(); 
    				},cls:'l-dialog-btn-highlight' 
    			}, 
    			{ text: '取消', onclick: 
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		] 
    	});
    }
      
	
   	//删除
   	function deleteLed(){
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
				this.sec_code +"@"+
				this.led_path
			) 
		});
		
        $.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("deletePrmLed.do",{ParamVo : ParamVo},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
		}); 
	}
    
   	
   	//下载导入模板
    function downTemplateLed(){
		location.href = "downTemplate.do?isCheck=false";
	}
	
   	
   	//导入
   	function importLed(){
		$.ligerDialog.open({
			url: 'prmLedImportPage.do?isCheck=false', height: 500,width: 800, 
			title:'导入',modal:true,showToggle:false,showMax:false,
			showMin: true,isResize:true 
		});
	}
      
      
    //字典下拉框
    function loadDict(){
    	autocomplete("#sec_code","../quertPrmLedDict.do?isCheck=false","id","text",true,true);
    	
    	$("#sec_code").ligerTextBox({width:160});
        $("#sec_name").ligerTextBox({width:160});
        $("#kpi_beg_score").ligerTextBox({width:160});
        $("#kpi_end_score").ligerTextBox({width:160});
        $("#led_path").ligerTextBox({width:160});
    }  

  	//打印
	function print(){
  		
  		
    	if(grid.getData().length==0){
			$.ligerDialog.warn("请先查询数据 ");
			return;
		}
    	
    	var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
    	
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
   		
    	var printPara={
       			title:'指示灯维护',
       			head:[
    				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num-1,"br":true},
       			],
       			foot:[
    				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num-1,"br":true}
       			],
       			columns:grid.getColumns(1),
       			headCount:1,//列头行数
       			autoFile:true,
       			type:3
       	};
    	
   		ajaxJsonObjectByUrl("queryPrmLed.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});
   		
    }  
	 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">等级：</td>
            <td align="left" class="l-table-edit-td"><input name="sec_code" type="text" id="sec_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">KPI起始分：</td>
            <td align="left" class="l-table-edit-td"><input name="kpi_beg_score" type="text" id="kpi_beg_score" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">KPI结束分：</td>
            <td align="left" class="l-table-edit-td"><input name="kpi_end_score" type="text" id="kpi_end_score" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
	
	<div id="toptoolbar" ></div>
	<div id="maingrid"></div>
</body>
</html>
