<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
	<script>
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
    	grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()}); 
    	
    	//加载查询条件
    	grid.loadData(grid.where);
     }
  

    function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科目编码', name: 'subj_code', align: 'left',
							render : function(rowdata, rowindex,value) {
								if(rowdata.is_last == "0"){
									return rowdata.subj_code;
								}
								var pars = {
									subj_code:rowdata.subj_code,
									subj_name:rowdata.subj_name,
									subj_dire:rowdata.subj_dire
								}
								return "<a href=javascript:openUpdate('"+rowdata.subj_code+"|"+rowdata.subj_name+"|"+rowdata.subj_dire+"')>"+rowdata.subj_code+"</a>";
							}
					 },
                     { display: '科目名称', name: 'subj_name', align: 'left' },
                     { display: '方向', name: 'subj_dire', align: 'left',
							render : function(rowdata, rowindex,value) {
								if(rowdata.subj_dire == 0){
									return "借";
								}else{
									return "贷"
								}
							}
					 },
                     { display: '初始未核销金额', name: 'debit', align: 'right',formatter:'###,##0.00',
						 render:function(rowdata){
							 return formatNumber(Math.abs(rowdata.debit),2,1);
						 }
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccCurrentAccount.do?isCheck=true',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '打印', id:'print', click: print,icon:'print' },
                     	{ line:true }
    				]}
        	});
    	
        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function print(){
    	
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		 }
           	
    	/* var heads={
        		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
        		  "rows": [
    	          {"cell":0,"value":"科室名称："+liger.get("dept_id").getText(),"colSpan":"5"},
    	          {"cell":3,"value":"项目名称："+liger.get("proj_id").getText(),"from":"right","align":"right","colSpan":"4"}
        		  ]
        	}; */
             	
      	var printPara={
       			title: "往来初始账",//标题
       			columns: JSON.stringify(grid.getPrintColumns()),//表头
       			class_name: "com.chd.hrp.acc.service.verification.AccNostroService",
      			method_name: "queryCurrentAccountPrint",
      			bean_name: "accNostroService"/* ,
      			heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
      			/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
         		};
      	
      	$.each(grid.options.parms,function(i,obj){
     			printPara[obj.name]=obj.value;
      	});
      	
      	officeGridPrint(printPara);
      	
    }
    
    function openUpdate(obj){	
		var vo = obj.split("|");
		var parm = "subj_code="+vo[0]+
			"&subj_name="+vo[1]+
			"&subj_dire="+vo[2]; 
    	$.ligerDialog.open({ 
    		url : 'accCurrentAccountDetailPage.do?isCheck=false&' + parm,data:{}, 
    		height: 500,width: 900, title:'往来初始明细账',
    		modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '保存', onclick: function (item, dialog) { dialog.frame.saveAccVouchCheck(); },cls:'l-dialog-btn-highlight' }, 
    			{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
    		] });
    }
    
    function loadDict(){
            //字典下拉框
    	var param = {
        		SUBJ_NATURE_CODE1:'04',
        		SUBJ_NATURE_CODE2:'05',
        		is_last : 1
        	};
        $("#subj_code").ligerComboBox({
		 	parms : param,
	      	url: '../querySubj.do?isCheck=false',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: subjWidth,
	      	autocomplete: true,
	      	width: 180
		 });
     }   
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">往来科目：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	 
    </table>
	<center><h1>往来初始账</h1></center>
	<div id="maingrid"></div>

</body>
</html>
