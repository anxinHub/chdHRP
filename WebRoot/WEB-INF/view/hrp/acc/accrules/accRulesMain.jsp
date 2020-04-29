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
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
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
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '系统', name: 'mod_name', align: 'center'
					 },
                     { display: '项目', name: 'proj_name', align: 'center'
					 },
                     { display: '最大级次', name: 'max_level', align: 'center'
					 },
                     { display: '最大长度', name: 'max_length', align: 'center'
					 },
                     { display: '1级', name: 'level1', align: 'center',
							render : function(rowdata, rowindex,
									value) {
							return "<input type='text' style='margin-top:5px;' size='5' id='level1"+rowindex+"' name='level1' value='"+rowdata.level1+"'/>";
							}
					 },
                     { display: '2级', name: 'level2', align: 'center',
							render : function(rowdata, rowindex,
									value) {
							return "<input type='text' style='margin-top:5px;' size='5' id='level2"+rowindex+"' name='level2' value='"+rowdata.level2+"'/>";
							}
					 },
                     { display: '3级', name: 'level3', align: 'center',
							render : function(rowdata, rowindex,
									value) {
							return "<input type='text' style='margin-top:5px;' size='5' id='level3"+rowindex+"' name='level3' value='"+rowdata.level3+"'/>";
							}
					 },
                     { display: '4级', name: 'level4', align: 'center',
							render : function(rowdata, rowindex,
									value) {
							return "<input type='text' style='margin-top:5px;' size='5' id='level4"+rowindex+"' name='level4' value='"+rowdata.level4+"'/>";
							}
					 },
                     { display: '5级', name: 'level5', align: 'center',
							render : function(rowdata, rowindex,
									value) {
							return "<input type='text' style='margin-top:5px;' size='5' id='level5"+rowindex+"' name='level5' value='"+rowdata.level5+"'/>";
							}
					 },
                     { display: '6级', name: 'level6', align: 'center',
							render : function(rowdata, rowindex,
									value) {
							return "<input type='text' style='margin-top:5px;' size='5' id='level6"+rowindex+"' name='level6' value='"+rowdata.level6+"'/>";
							}
					 },
                     { display: '7级', name: 'level7', align: 'center',
							render : function(rowdata, rowindex,
									value) {
							return "<input type='text' style='margin-top:5px;' size='5' id='level7"+rowindex+"' name='level7' value='"+rowdata.level7+"'/>";
							}
					 },
                     { display: '8级', name: 'level8', align: 'center',
							render : function(rowdata, rowindex,
									value) {
							return "<input type='text' style='margin-top:5px;' size='5' id='level8"+rowindex+"' name='level8' value='"+rowdata.level8+"'/>";
							}
					 },
                     { display: '9级', name: 'level9', align: 'center',
							render : function(rowdata, rowindex,
									value) {
							return "<input type='text' size='5' style='margin-top:5px;' id='level9"+rowindex+"' name='level9' value='"+rowdata.level9+"'/>";
							}
					 },
                     { display: '10级', name: 'level10', align: 'center',
							render : function(rowdata, rowindex,
									value) {
							return "<input type='text' size='5' style='margin-top:5px;' id='level10"+rowindex+"' name='level10' value='"+rowdata.level10+"'/>";
							}
					 },
                     { display: '是否生成',width:80, name: 'is_auto', align: 'center',
							render : function(rowdata, rowindex,
									value) {
							return "<input type='checkbox' style='margin-top:7px;' id='is_auto' name='is_auto"+rowindex+"'/>";
							}
					 },
					 { display: '操作',name: 'edit',width:90, align: 'center',
							render : function(rowdata, rowindex,
									value) {
							return "<div class='l-button' style='width: 60px;margin-top:1px;margin-left: 18px;' ligeruiid='Button1004' onclick=save('"+rowindex+"','"+rowdata.proj_code+"');>"
		       					+"<span>保存</span></div>";
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryRules.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function loadDict(){
            //字典下拉框
    	
         }   
    
    function save(rowindex,proj_code){
    	
    	var level1 = $("#level1"+rowindex).val();
    	var level2 = $("#level2"+rowindex).val();
    	var level3 = $("#level3"+rowindex).val();
    	var level4 = $("#level4"+rowindex).val();
    	var level5 = $("#level5"+rowindex).val();
    	var level6 = $("#level6"+rowindex).val();
    	var level7 = $("#level7"+rowindex).val();
    	var level8 = $("#level8"+rowindex).val();
    	var level9 = $("#level9"+rowindex).val();
    	var level10 = $("#level10"+rowindex).val();
    	var is_auto = 1;
    	if($("input[name='is_auto"+rowindex+"']").attr("checked") == true){
    		is_auto = 0;
    	}
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
    			proj_code:proj_code,
    			is_auto:is_auto
    	};
		ajaxJsonObjectByUrl("updateRules.do",formPara,function(responseData){
                query();
        });
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none;"></div>

	<div id="toptoolbar" ></div>

	<div id="maingrid"></div>

</body>
</html>
