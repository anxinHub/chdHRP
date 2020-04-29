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
    
    $(function (){
        loadDict();//加载下拉框
        loadHead(null);	//加载数据 
        loadHotkeys();
    });
    
    //查询
    function query(){
    	if(isnull($("#acc_year_month").val())){
   			$.ligerDialog.error("请选择会计期间！");
   			return;
   		}
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'ass_naturs',value:liger.get("ass_nature").getValue()}); 
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split("@")[0]}); 
    	grid.options.parms.push({name:'year_month',value:$("#acc_year_month").val()}); 
     	 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科室编码', name: 'dept_code', align: 'left'},
                     { display: '科室名称', name: 'dept_name', align: 'left'},
                     { display: '账面数量', name: 'acc_amount', align: 'left'
             		 },
             		 { display: '盘点数量', name: 'check_amount', align: 'left'
            		 },
                     { display: '差额', name: 'amount', align: 'left',
 						render : function(rowdata, rowindex,
 								value) {
 							if(rowdata.dept_name != '合计' && rowdata.dept_name != '专用设备小计'
 							   && rowdata.dept_name != '一般设备小计' && rowdata.dept_name != '其他固定资产小计'	
 							   && rowdata.dept_name != '其他无形资产小计'
 							){
 								return "<a href=javascript:openDetail('"+rowdata.dept_id + "|" + liger.get("ass_nature").getValue()
 								+ "|" + $("#acc_year_month").val() +"')>"+rowdata.amount+"</a>";
 							}
 						}
                     },
                     { display: '盘盈亏金额', name: 'price', align: 'left'
                     }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssDeptAccountReport.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,delayLoad:true,
                     toolbar: { items: [
                             { text: '查   询', id:'search', click: query,icon:'search' },
                             { line:true },
 						     { text: '打 印', id:'print', click: printDate,icon:'print' },
 						     { line:true }
						     
                     ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function openDetail(obj){
    	var vo = obj.split("|");
		var parm = 
			"dept_id="+vo[0]   +"&"+ 
			"ass_nature="+vo[1]   +"&"+ 
			"year_month="+vo[2];

		parent.$.ligerDialog.open({
			title: '明细查看',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assdeptreport/assDeptAccountReportDetPage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    }
    
    function loadDict(){
    	
		var param = {query_key:''};
        
         //字典下拉框
		$("#ass_nature").ligerComboBox({
          	url: '../queryAssNaturs.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160,
          	onSelected :function(value,text){
          		//loadHead(null);
          		query();
          	}
 		  });
    	 //科室编码
    	autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id","text", true, true,param,true);
    	 
        $("#acc_year_month").ligerTextBox({width:70});
        
      //默认年
        if(${ass_year_month}){
			$("#acc_year_month").val('${ass_year_month }')
        }else{
        	autodate("#acc_year_month","YYYYMM"); 
        }

    } 
    //键盘事件
	function loadHotkeys() {

	}
    
    
	//打印数据
 	function printDate(){
 		if(grid.getData().length==0){
   			$.ligerDialog.error("请先查询数据！");
   			return;
   		}
 		
 		var time=new Date();
 		var date=$("#acc_year_month").val();
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"报表日期:"},
  				  {"cell":1,"value":date} ,
  				  {"cell":5,"value":"资产性质:"},
				  {"cell":6,"value":$("#ass_nature").val()} ,
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":2,"value":"制表人:"},
    				{"cell":3,"value":"${user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "资金折旧月报表",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.serviceImpl.deptreport.AssDeptAccountReportServiceImpl",
 				method_name: "queryPrint",
 				bean_name: "assDeptAccountReportService" ,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
 				};
 		
 		$.each(grid.options.parms,function(i,obj){
 				printPara[obj.name]=obj.value;
 		});
 		
 		officeGridPrint(printPara);
 	   		
 	}
    
	    	
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr >
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year_month" type="text" id="acc_year_month" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" 
            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           
        	<td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
       
    </table>

	<div id="maingrid"></div>
</body>
</html>
