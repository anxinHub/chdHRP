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
    	loadHead(null);	//加载数据
    	 
    });
   
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '库房编码', name: 'store_code', align: 'left'
					 },
                     { display: '库房名称', name: 'store_name', align: 'left'
					 },
                     { display: '启用年度', name: 'start_year', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								if(value != null){
									return "<input class='Wdate'"
									+"name='start_year"+rowindex+"' type='text' id='start_year"+rowindex+"' ltype='text'"
										+"validate='{required:true,maxlength:20}'"
										+"onFocus='year();' style='margin-top:5px;' value='"+value+"' disabled='disabled'/>";
								}
								return "<input class='Wdate'"
								+"name='start_year"+rowindex+"' type='text' id='start_year"+rowindex+"' ltype='text'"
									+"validate='{required:true,maxlength:20}'"
									+"onFocus='year();' style='margin-top:5px;'/>";
							}
					 },
                     { display: '启用月度', name: 'start_month', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								if(value != null){
									return "<input class='Wdate'"
									+"name='start_month"+rowindex+"' type='text' id='start_month"+rowindex+"' ltype='text'"
										+"validate='{required:true,maxlength:20}'"
										+"onFocus='month();' style='margin-top:5px;' value='"+value+"' disabled='disabled'/>";
								}
								
								return "<input class='Wdate'"
								+"name='start_month"+rowindex+"' type='text' id='start_month"+rowindex+"' ltype='text'"
									+"validate='{required:true,maxlength:20}'"
									+"onFocus='month();' style='margin-top:5px;' />";
							}
					 },
                     { display: '启用人', name: 'user_name', align: 'left'
					 },
                     { display: '操作时间', name: 'opt_date', align: 'left'
					 },
                     { display: '操作', name: 'user_id', align: 'center',
							render : function(rowdata, rowindex,
									value) { 
								if(value == null){
									return "<div class='l-button' id ='button"+rowindex+"' style='width: 60px; margin-top:1px; margin-left: 30px;' ligeruiid='Button1004'" 
									+"onclick=save('"+rowdata.store_id+"','"+rowindex+"')>"
				       					+"<span>启用</span></div>";
								}
								return "<div class='l-button' style='width: 60px; margin-top:1px; margin-left: 30px;background:#f5f5f5;' ligeruiid='Button1004'"  
								+">"
			       					+"<span>启用</span></div>";
			       					 
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryStoreMod.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function year(){
    	WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'});
    }
    function month(){
    	WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'});
    }
	function save(store_id,index){
		  
		var start_year = $("#start_year"+index).val();
		if(start_year == ""){
			$.ligerDialog.warn("请选择启用年份！");
			return;
		}
		var start_month = $("#start_month"+index).val();
		if(start_month == ""){
			$.ligerDialog.warn("请选择启用月份！");
			return;
		}
		 
		var formPara = {
				store_id:store_id,
				start_year:start_year,
				start_month:start_month
		};
		$.ligerDialog.confirm('该仓库启用年月为'+$("#start_year"+index).val()+$("#start_month"+index).val()+'确定保存吗?<br><span style="color:red">该保存执行后不能进行逆转!请确认后再进行操作！</span>', function (yes){
        	if(yes){
        		ajaxJsonObjectByUrl("../medsysset/addStoreModStart.do?isCheck=false",formPara,function(responseData){
       			 $("#start_year"+index).attr('disabled','disabled');
       			 $("#start_month"+index).attr('disabled','disabled');
        
       			 if(responseData.state=="true"){
       				 loadHead(null);	
       	         }
       		 });
        	}
        }); 
		 
	}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none" ></div>

	<div id="maingrid"></div>

</body>
</html>
