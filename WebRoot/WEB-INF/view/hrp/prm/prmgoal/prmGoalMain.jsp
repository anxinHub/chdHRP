<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    $(function (){
        loadDict()//加载下拉框
    	loadHead(null);	//加载数据
        $("#acc_year").ligerTextBox({width:160});
       	
    	
        toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    });
    
    //查询
    function  query(){
		grid.options.parms=[];
    	grid.options.newPage=1;
    	
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'acc_year',value:$("#acc_year").val()}); 
    	grid.options.parms.push({name:'goal_code',value:liger.get("goal_code").getValue()}); 
    	grid.options.parms.push({name:'hos_id',value:liger.get("hos_id").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    
	//获取查询条件的数值
	function f_getWhere() {
		if (!grid)
			return null;
		var clause = function(rowdata, rowindex) {
			if ($("#acc_year").val() != "") {
				return rowdata.acc_year.indexOf($("#acc_year").val()) > -1;
			}
			if ($("#goal_code").val() != "") {
				return rowdata.goal_code.indexOf($("#goal_code").val()) > -1;
			}
			if ($("#hos_id").val() != "") {
				return rowdata.hos_id.indexOf($("#hos_id").val()) > -1;
			}
		};
		return clause;
	}
	
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
        	columns: [ 
				{ display: '单位信息', name: 'hos_name', align: 'left'},
                { display: '绩效年度', name: 'acc_year', align: 'left'},
                { display: '目标名称', name: 'goal_name', align: 'left',render: 
                	function (rowdata) {
                    	return "<a href=javascript:openUpdate('"+rowdata.group_id+ "|" +rowdata.hos_id+ "|" + rowdata.copy_code +"|"+ rowdata.acc_year +"|"+rowdata.goal_code+ "')>"+rowdata.goal_name+"</a>";
                    }
                },
                { display: '目标描述', name: 'goal_note', align: 'left'}
            ],
            dataAction: 'server',dataType: 'server',usePager:true,url:'queryPrmGoal.do',width: '100%', height: '100%', 
            checkbox: true,rownumbers:true,delayLoad:true,selectRowButtonOnly:true,//heightDiff: -10,
    		onDblClickRow : 
    			function (rowdata, rowindex, value){
					openUpdate(
						rowdata.group_id   + "|" + 
						rowdata.hos_id   + "|" + 
						rowdata.copy_code   + "|" + 
						rowdata.acc_year   + "|" + 
						rowdata.goal_code 
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
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addGoal, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteGoal,icon:'delete' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
	
    

  	//键盘事件
	function loadHotkeys(){
		hotkeys('Q',query);
		hotkeys('A',addGoal);
		hotkeys('D',deleteGoal);
		hotkeys('P',print);
	}
  	
  	//添加页面跳转
	function addGoal(){ 
		parent.$.ligerDialog.open({url: 'hrp/prm/prmgoal/prmGoalAddPage.do?isCheck=false', 
			height: $(window).height(),width:$(window).width(),title:'添加',modal:true,showToggle:false,showMax:true,showMin: true,isResize:true,
			parentframename: window.name,
			buttons: [ 
				{ text: '确定', onclick: 
					function (item, dialog) { 
						dialog.frame.savePrmGoal(); 
					},
					cls:'l-dialog-btn-highlight' 
				}, 
			    { text: '取消', onclick: 
			    	function (item, dialog) { 
			    		dialog.close(); 
			    	} 
				} 
			] 
		});
    }
  	
  	//删除目标
	function deleteGoal(){ 
		
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
					this.acc_year   +"@"+ 
					this.goal_code 
				) 
			});
            
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deletePrmGoal.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
    }
	
  	//修改页面跳转
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"acc_year="+vo[3]   +"&"+ 
			"goal_code="+vo[4] 
    	parent.$.ligerDialog.open({ url : 'hrp/prm/prmgoal/prmGoalUpdatePage.do?isCheck=false&' + parm,
    		data:{}, height: $(window).height(),width: $(window).width(), title:'修改',modal:true,showToggle:false,showMax:true,showMin: false,
    		parentframename: window.name,isResize:true,
    		buttons:[ 
    			{ text: '确定', onclick:
    				function (item, dialog) { 
    					dialog.frame.savePrmGoal(); 
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
  	
  	
	//字典下拉框
    function loadDict(){
    	autocomplete("#goal_code","../quertPrmGoalDict.do?isCheck=false","id","text",true,true,"",false,"","400");
    	autocomplete("#hos_id","../quertSysHosInfoDict.do?isCheck=false","id","text",true,true,"",true);
    	autodate("#acc_year","yyyy");
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
       			title:'目标管理',
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
    	
   		ajaxJsonObjectByUrl("queryPrmGoal.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});
   		
    }
	 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	

        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">单位信息：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">绩效年度：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year" type="text"  id="acc_year" ltype="text"   class="Wdate" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy',minDate:'2016-01-01'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">目标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="goal_code" type="text" id="goal_code" ltype="text" /></td>
            <td align="left"></td>
        </tr> 

    </table>
	
	<div id="toptoolbar" ></div>
	<div id="maingrid"></div>
</body>
</html>

