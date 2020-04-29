<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	loadHotkeys();
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'begin_date',value:$("#begin_date").val()}); 
        grid.options.parms.push({name:'end_date',value:$("#end_date").val()}); 
    	grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 
    	if(liger.get("dept_code").getValue() !=""){
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_code").getValue().split(".")[0]}); 
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_code").getValue().split(".")[1]}); 
    	}
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '票据类型编码', name: 'type_code', align: 'left'},
   	  				 { display: '票据类型名称', name: 'type_name', align: 'left',width:160},
                     { display: '购置科室', name: 'dept_name', align: 'left'},
                     { display: '购置人', name: 'user_name', align: 'left'},
                     { display: '购置日期', name: 'opt_date', align: 'left'},
                     { display: '起始号码', name: 'begin_num', align: 'left',
   					     render : function(rowdata, rowindex,value) {
      						  return "<a href=javascript:openUpdate('"+rowdata.group_id  + "|" + 
      							rowdata.hos_id   + "|" + 
      							rowdata.copy_code   + "|" + 
      							rowdata.pid  +"')>"+rowdata.begin_num+"</a>";
      	  				  }},
                     { display: '终止号码', name: 'end_num', align: 'left'},
                     { display: '数量', name: 'amount', align: 'right'},
                     { display: '费用', name: 'amoney', align: 'right',
 						 render:function(rowdata){
 							 return formatNumber(rowdata.amoney,2,1);
 						 }
                      },
                     { display: '备注', name: 'note', align: 'left'},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccPaperMain.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code  + "|" + 
								rowdata.pid  
							);
    				},
    				lodop:{
    	         		title:"票据购置",
    	      			fn:{
    	          			debit:function(value){//借方
    	          				if(value == 0){return "";}
    	                 			else{return formatNumber(value, 2, 1);}
    	          			},
    	          			credit:function(value){//贷方
    	          				if(value == 0){return "";}
    	                			else{return formatNumber(value, 2, 1);}
    	         				},
    	         				end_os:function(value){//余额
    	      	   				 if(value==0){return "Q";}
    	      					 else{return formatNumber(value, 2, 1);}
    	        				}
    	          		}
    	      		}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
  //打印回调方法
    function lodopPrint(){
   
    	var head="<table class='head' width='100%'><tr><td>日期："+$("#begin_date").val()+"至"+$("#end_date").val()+"</td>";
 		grid.options.lodop.head=head;
    }
    function add_open(){
    	
    	$.ligerDialog.open({url: 'accPaperMainAddPage.do?isCheck=false', height: 450,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccPayType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    	
    }
    
    function del_open(){
    	
    	 var data = gridManager.getCheckedRows();
		 if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         }else{
             var ParamVo =[];
             $(data).each(function (){					
					ParamVo.push(
					//表的主键
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code +"@"+
					this.pid   
					)
             });
             $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	ajaxJsonObjectByUrl("deleteBatchAccPaperMain.do",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}
                 	});
             	}
             }); 
         }
    }
    
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "&group_id="+ 
			vo[0]   +"&hos_id="+ 
			vo[1]   +"&copy_code="+ 
			vo[2]   +"&pid="+ 
			vo[3]
		
    	$.ligerDialog.open({ url : 'accPaperMainUpdatePage.do?isCheck=false' + parm,data:{}, height: 450,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccPayType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
  //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		
		hotkeys('A', add_open);
		
		hotkeys('D', del_open);

	}
    
    function loadDict(){
            //字典下拉框
            
         autocomplete("#type_code","../../queryAccPaperType.do?isCheck=false","id","text",true,true);
    	 
         autocomplete("#dept_code","../../queryDeptDictNo.do?isCheck=false","id","text",true,true);
    	 
        $(':button').ligerButton({width:80});
            
      	$("#begin_date").ligerTextBox({width:160});
      	
    	$("#end_date").ligerTextBox({width:160});
    	
    	autodate("#begin_date","yyyy-MM-dd");
    	
    	var date = $("#begin_date").val();
   	 	$("#begin_date").val(date.substr(0,date.length-2) + "01");

    	autodate("#end_date","yyyy-MM-dd");

    	
         }  
    
    function printDate(){
		 if(grid.getData().length==0){
 		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
 	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
							{"cell":0,"value":"购置日期："+$("#begin_date").val()+"至"+$("#end_date").val(),"colSpan":"5"},
							//{"cell":3,"value":"系统名称："+liger.get("mod_code").getText(),"from":"right","align":"right","colSpan":"4"}  
	      		  ]
	      	};
	   		
		var printPara={
			rowCount:1,
			title:'票据购置',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.paper.AccPaperMainService",
			method_name: "queryAccPaperMainPrint",
			bean_name: "accPaperMainService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
			};
 	
		//执行方法的查询条件
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
         <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">购置日期：</td>
            <td align="left" class="l-table-edit-td"><input name="begin_date" class="Wdate" id="begin_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td">至</td>
            <td align="left" class="l-table-edit-td"><input name="end_date" class="Wdate" id="end_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">票据类型：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">购置科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    <div style="border:1px">
    <input  type="button" value=" 查询(Q)" onclick="query();"/>
	<input  type="button" value=" 添加(A)" onclick="add_open();"/>
	<input  type="button" value=" 删除(D)" onclick="del_open();"/>
	<input  type="button" value=" 打 印" onclick="printDate();"/>
	</div>
	<div id="maingrid"></div>

</body>
</html>
