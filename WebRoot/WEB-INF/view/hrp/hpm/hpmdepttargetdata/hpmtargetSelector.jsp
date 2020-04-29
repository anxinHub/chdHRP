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
    	//加载数据
    	loadHead(null);	
    	loadHotkeys();
        $("#target_code").ligerTextBox({width:160});
        $("#is_stop").ligerComboBox({width:160});
		
    });
    
    
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'target_code',value:liger.get("target_code").getValue()}); 
    	grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
	}
   	
    //加载grid
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ 
            	{ display: '指标编码', name: 'target_code', align: 'left',width: '10%'/* ,
            		render : function(rowdata, rowindex,value) {
						return "<a href=javascript:openUpdate('"+
									rowdata.target_code   + "|" + 
									rowdata.group_id   + "|" + 
									rowdata.hos_id   + "|" + 
									rowdata.copy_code   + "')>"+
									rowdata.target_code+
								"</a>";
					} */
				},

				{ display: '指标名称', name: 'target_name', align: 'left',width: '35%'},
                
				{ display : '指标性质',name : 'nature_code',align : 'left',
					render : function(rowdata, rowindex, value) {
						return rowdata.nature_name;
					}
				}
			],
            dataAction: 'server',dataType: 'server',usePager:true,
            url:'../../../hrp/hpm/hpmtargetmethod/queryHpmTargetMethodNature.do?isCheck=false',
            width: '100%',height: '100%',checkbox: true,rownumbers:true,pageSize:500,
			selectRowButtonOnly:true,//heightDiff: -10,
            toolbar: { 
            	items: [
                	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' }
				]
           	} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
/* 
    function openUpdate(obj){
    
		var vo = obj.split("|");
		var parm = "&target_code="+
		vo[0]   +"&group_id"+ 
		vo[1]   +"&hos_id"+ 
		vo[2]   +"&copy_code"+ 
		vo[3] 
		
    	$.ligerDialog.open({ url : 'hpmTargetUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 750, title:'修改',
    			modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveHpmTarget(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    } */
    
  	//键盘事件
	function loadHotkeys(){
		hotkeys('Q',query);
	}
  
    //字典下拉框
    function loadDict(){
    	autocomplete("#target_code","../queryTarget.do?isCheck=false","id","text",true,true);
    }
    
    //页面回显
   	function saveSelectTarge(){
    	
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return ;
        }
        var paramVo_id =[];
        var paramVo_text =[]; 
        
		$(data).each(function (){	
			paramVo_id.push(this.target_code);
			paramVo_text.push(this.target_code+" "+this.target_name); 
		});
            
		parent.liger.get("target_code").setValue(paramVo_id.toString());
		parent.liger.get("target_code").setText(paramVo_text.toString());
	}
   
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="target_code" type="text" id="target_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td">
             	<select id="is_stop" name="is_stop" style="width: 135px;">
               		<option value="">请选择</option>
			        <option value="0">否</option>
			        <option value="1">是</option>
                </select>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
