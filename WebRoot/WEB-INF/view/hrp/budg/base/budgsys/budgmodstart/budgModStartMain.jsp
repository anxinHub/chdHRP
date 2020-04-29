<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
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
                     { display: '系统编码', name: 'mod_code', align: 'center'
					 },
                     { display: '系统名称', name: 'mod_name', align: 'center'
					 },
                     { display: '启用年度', name: 'modStart.start_year', align: 'center',
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
                     { display: '启用月度', name: 'modStart.start_month', align: 'center',
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
                     { display: '启用人', name: 'modStart.create_user', align: 'center'
					 },
                     { display: '操作时间', name: 'modStart.create_date', align: 'center'
					 },
                     { display: '操作', name: 'modStart.create_user', align: 'center',
							render : function(rowdata, rowindex,
									value) {
								if(value == null){
									return "<div class='l-button' style='width: 60px; margin-top:1px; margin-left: 30px;' ligeruiid='Button1004'" 
									+"onclick=save('"+rowdata.mod_code+"','"+rowindex+"')>"
				       					+"<span>启用</span></div>";
								}
								return "<div class='l-button' style='width: 60px; margin-top:1px; margin-left: 30px;background:#f5f5f5;' ligeruiid='Button1004'"  
								+">"
			       					+"<span>启用</span></div>";
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgModStart.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function year(){
    	WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'});
    }
    function month(){
    	WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'});
    }
	function save(mod_code,index){
		var start_year = $("#start_year"+index).val();
		if(start_year == ""){
			return;
		}
		var start_month = $("#start_month"+index).val();
		if(start_month == ""){
			return;
		}
		var formPara = {
				mod_code:mod_code,
				start_year:start_year,
				start_month:start_month
		};
		 ajaxJsonObjectByUrl("addBudgModStart.do",formPara,function(responseData){
			 $("#start_year"+index).attr('disabled','disabled');
			 $("#start_month"+index).attr('disabled','disabled');
			 if(responseData.state=="true"){
				 loadHead(null);	
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
