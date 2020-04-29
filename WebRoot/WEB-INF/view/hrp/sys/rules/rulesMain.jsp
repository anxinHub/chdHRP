<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css"
	rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'mod_code',value:liger.get("mod_code").getValue()}); 
    	grid.options.parms.push({name:'copy_code',value:liger.get("copy_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#mod_code").val()!=""){
                		return rowdata.mod_code.indexOf(liger.get("mod_code").getValue()) > -1;	
                	}
                	if($("#copy_code").val()!=""){
                		return rowdata.copy_code.indexOf(liger.get("copy_code").getValue()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '系统', name: 'mod_name', align: 'center'
					 },
                     { display: '账套', name: 'copy_name', align: 'center'
					 },
                     { display: '项目', name: 'proj_name', align: 'center'
					 },
                     { display: '最大级次', name: 'max_level', align: 'center',
                    	 render : function(rowdata, rowindex,
									value) {
									return "<input type='text' size='5' id='max_level"+rowindex+"' name='max_level"+rowindex+"' value='"+rowdata.max_level+"'/>";
							}
					 },
                     { display: '最大长度', name: 'max_length', align: 'center'
					 },
                     { display: '1级', name: 'level1', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.max_level >= 1){
									return "<input type='text' size='5' id='level1"+rowindex+"' name='level1"+rowindex+"' value='"+rowdata.level1+"'/>";
								}
								return "0";
							}
					 },
                     { display: '2级', name: 'level2', align: 'center',
							render : function(rowdata, rowindex,
									value) {
							if(rowdata.max_level >= 2){
								return "<input type='text' size='5' id='level2"+rowindex+"' name='level2"+rowindex+"' value='"+rowdata.level2+"'/>";
							}
							return "0";
							}
					 },
                     { display: '3级', name: 'level3', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.max_level >= 3){
									return "<input type='text' size='5' id='level3"+rowindex+"' name='level3"+rowindex+"' value='"+rowdata.level3+"'/>";
								}
								return "0";
							}
					 },
                     { display: '4级', name: 'level4', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.max_level >= 4){
									return "<input type='text' size='5' id='level4"+rowindex+"' name='level4"+rowindex+"' value='"+rowdata.level4+"'/>";
								}
								return "0";
							}
					 },
                     { display: '5级', name: 'level5', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.max_level >= 5){
									return "<input type='text' size='5' id='level5"+rowindex+"' name='level5"+rowindex+"' value='"+rowdata.level5+"'/>";
								}
								return "0";
							}
					 },
                     { display: '6级', name: 'level6', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.max_level >= 6){
									return "<input type='text' size='5' id='level6"+rowindex+"' name='level6"+rowindex+"' value='"+rowdata.level6+"'/>";
								}
								return "0";
							}
					 },
                     { display: '7级', name: 'level7', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.max_level >= 7){
									return "<input type='text' size='5' id='level7"+rowindex+"' name='level7"+rowindex+"' value='"+rowdata.level7+"'/>";
								}
								return "0";
							}
					 },
                     { display: '8级', name: 'level8', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.max_level >= 8){
									return "<input type='text' size='5' id='level8"+rowindex+"' name='level8"+rowindex+"' value='"+rowdata.level8+"'/>";
								}
								return "0";
							}
					 },
                     { display: '9级', name: 'level9', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.max_level >= 9){
									return "<input type='text' size='5' id='level9"+rowindex+"' name='level9"+rowindex+"' value='"+rowdata.level9+"'/>";
								}
								return "0";
							}
					 },
                     { display: '10级', name: 'level10', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.max_level >= 10){
									return "<input type='text' size='5' id='level10"+rowindex+"' name='level10"+rowindex+"' value='"+rowdata.level10+"'/>";
								}
								return "0";
							}
					 },
                     { display: '按流水号生成',width:80, name: 'is_auto', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								if(value=='1'){
									return "<input type='checkbox' id='is_auto"+rowindex+"' name='is_auto"+rowindex+"' checked/>";
								}else{
									return "<input type='checkbox' id='is_auto"+rowindex+"' name='is_auto"+rowindex+"' />";
								}
							
							}
					 },
					 { display: '操作',name: 'edit',width:90, align: 'center',
							render : function(rowdata, rowindex,
									value) {
							return "<div class='l-button' style='width: 60px; float: left; margin-right: 40px;margin-left: 18px;' ligeruiid='Button1004' onclick=save('"+rowindex+"','"+rowdata.proj_code+"','"+rowdata.group_id+"','"+rowdata.hos_id+"','"+rowdata.copy_code+"','"+rowdata.mod_code+"');>"
		       					+"<span>保存</span></div>";
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryRules.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function loadDict(){
            //字典下拉框
    	
    	autocomplete("#mod_code",
				"../queryModDict.do?isCheck=false", "id", "text", true,
				true);
    	autocomplete("#copy_code","../queryCopyCodeDict.do?isCheck=false","id","text",true,true);
        
         }   
 function save(rowindex,proj_code,group_id,hos_id,copy_code,mod_code){
    	var level1 = $("#level1"+rowindex+"").val();
    	var level1 = $("#level1"+rowindex+"").val();
    	var level2 = $("#level2"+rowindex+"").val();
    	var level3 = $("#level3"+rowindex+"").val();
    	var level4 = $("#level4"+rowindex+"").val();
    	var level5 = $("#level5"+rowindex+"").val();
    	var level6 = $("#level6"+rowindex+"").val();
    	var level7 = $("#level7"+rowindex+"").val();
    	var level8 = $("#level8"+rowindex+"").val();
    	var level9 = $("#level9"+rowindex+"").val();
    	var level10 = $("#level10"+rowindex+"").val();
    	var max_level = $("#max_level"+rowindex+"").val();
    	var is_auto = 0;
    	if($("input[name='is_auto"+rowindex+"']").attr("checked") == true){
    		is_auto = 1;
    	}
    	if (typeof(level1) == "undefined"){level1=0};
    	if (level1 == null){level1=0	}
    	if (typeof(level2) == "undefined"){level2=0};
    	if (level2 == null){level2=0	}
    	if (typeof(level3) == "undefined"){level3=0};
    	if (level3 == null){level3=0	}
    	if (typeof(level4) == "undefined"){level4=0};
    	if (level4 == null){level4=0	}
    	if (typeof(level5) == "undefined"){level5=0};
    	if (level5 == null){level5=0	}
    	if (typeof(level6) == "undefined"){level6=0};
    	if (level6 == null){level6=0	}
    	if (typeof(level7) == "undefined"){level7=0};
    	if (level7 == null){level7=0	}
    	if (typeof(level8) == "undefined"){level8=0};
    	if (level8 == null){level8=0	}
    	if (typeof(level9) == "undefined"){level9=0};
    	if (level9 == null){level9=0	}
    	if (typeof(level10) == "undefined"){level10=0};
    	if (level10 == null){level10=0	}
    	
    	if (typeof(max_level) == "undefined"){max_level=0};
    	if (max_level == null){max_level=0	}

    	var formPara = {
    			level1:level1,
    			level2:level2,
    			level3:level3,
    			level4:level4,
    			level5:level5,
    			level6:level6,
    			level7:level7,
    			level8:level8,
    			level9:level9,
    			level10:level10,
    			max_level:max_level,
    			proj_code:proj_code,
    			group_id:group_id,
    			hos_id:hos_id,
    			copy_code:copy_code,
    			is_auto:is_auto,
    			mod_code:mod_code
    	};
		ajaxJsonObjectByUrl("updateRules.do",formPara,function(responseData){
                query();
        });
 }	
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">系统：</td>
			<td align="left" class="l-table-edit-td"><input name="mod_code"
				type="text" id="mod_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">账套编码：</td>
			<td align="left" class="l-table-edit-td"><input name="copy_code"
				type="text" id="copy_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>

	</table>

	<div id="maingrid"></div>

</body>
</html>
