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
    
    var rightgrid;
    
    var gridManager = null;
    
    var rightgridManager = null;
    
    var userUpdateStr;
    
    var paraDate = '';

    var vouch_no_begin = '';

    var vouch_no_end = '';
    
    var vouch_type_code = '';
    //由于存储 凭证起止日期和附件  以便于打印时使用
    var span_begin_date = '';
    var span_end_date = '';
    var span_att_num ='';
    var vouch_sum ='';
    
    $(function ()
    {
    	loadHead();	//加载数据
    	loadDict();
    	$("#acc_month").ligerTextBox({width:100});
    	$("#vouch_type_code").ligerTextBox({width:100}); 
    	
    	$("#subj_level").bind("change", function (){
    		rightQuery(paraDate);
    	});
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		
    		if(liger.get("acc_month").getValue()==""){
            	
            	$.ligerDialog.error('会计期间为必填项，不能为空！');
            	
            	return;
            }

    		
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'acc_year',value:liger.get("acc_month").getValue().split(".")[0]});
        grid.options.parms.push({name:'acc_month',value:liger.get("acc_month").getValue().split(".")[1]}); 
    	grid.options.parms.push({name:'vouch_type_code',value:liger.get("vouch_type_code").getValue()}); 
    	grid.options.parms.push({name:'subj_level',value:liger.get("subj_level").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    
    //查询
    function  rightQuery(data){
    	
    	paraDate = data;
    	rightgrid.options.parms=[];
    	rightgrid.options.newPage=1;
    	
    	
        //根据表字段进行添加查询条件
        var vo = data.split("|");
        
        var selPara={
        		acc_year: liger.get("acc_month").getValue().split(".")[0],
        		acc_month: liger.get("acc_month").getValue().split(".")[1],
        		vouch_type_code: vo[5],
        		vouch_no_begin: vo[6],
        		vouch_no_end: vo[7]
        };
    	ajaxJsonObjectByUrl("queryAccSubjByVouchBT.do?isCheck=false", selPara,function (responseData){
    		$("#h_title").text('分册名称'+vo[8]);
     		$("#span_begin_date").text(responseData.Rows[0].vouch_date_begin);
     		$("#span_end_date").text(responseData.Rows[0].vouch_date_end);
     		$("#span_begin_no").text(vo[6]);
     		$("#span_end_no").text(vo[7]);
     		$("#span_att_num").text(responseData.Rows[0].vouch_att_num==null ? 0 : responseData.Rows[0].vouch_att_num);
     		$("#vouch_sum").text(vo[7]-vo[6]+1);
     		span_begin_date =  responseData.Rows[0].vouch_date_begin ;
     		span_end_date = responseData.Rows[0].vouch_date_end ;
     		span_att_num = responseData.Rows[0].vouch_att_num;
    	},false);
        
        rightgrid.options.parms.push({name:'wei_id',value:vo[0]});
        rightgrid.options.parms.push({name:'group_id',value:vo[1]}); 
        rightgrid.options.parms.push({name:'hos_id',value:vo[2]}); 
        rightgrid.options.parms.push({name:'copy_code',value:vo[3]}); 
        rightgrid.options.parms.push({name:'acc_year',value:vo[4]});
        rightgrid.options.parms.push({name:'acc_month',value:liger.get("acc_month").getValue().split(".")[1]}); 
        rightgrid.options.parms.push({name:'vouch_type_code',value:vo[5]}); 
        rightgrid.options.parms.push({name:'vouch_no_begin',value:vo[6]}); 
        rightgrid.options.parms.push({name:'vouch_no_end',value:vo[7]});
        
        rightgrid.options.parms.push({name:'subj_level',value:liger.get("subj_level").getValue() == null ? "" : liger.get("subj_level").getValue().split(",")[0]
        		});
        vouch_type_code=vo[5];
        vouch_no_begin=vo[6];
        vouch_no_end = vo[7];
        
    	//加载查询条件
    	rightgrid.loadData(rightgrid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '分册名称', name: 'wei_name', align: 'left', 
                    	 render:function(rowdata){
                    		 return "<a href=javascript:rightQuery('"+rowdata.wei_id+"|"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.acc_year+"|"+rowdata.vouch_type_code+"|"+rowdata.vouch_no_begin+"|"+rowdata.vouch_no_end+"|"+rowdata.wei_name+"')>"+rowdata.wei_name+"</a>";
                    	 }
					 },
					 
                     { display: '起始凭证号', name: 'vouch_no_begin', align: 'left'
					 },
					 { display: '结束凭证号', name: 'vouch_no_end', align: 'left'
					 },
                     { display: '操作', name: 'edit', align: 'center',
							render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:openUpdate('"+rowdata.wei_id+"|"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.acc_year+"')>修改<a/>";
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccVouchWei.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
						{ text: '查询', id:'search', click: query,icon:'search' },
						{ line:true },
                     	{ text: '添加', id:'add', click: itemclick,icon:'add' },
                     	{ line:true },
                     	{ text: '删除', id:'delete', click: itemclick,icon:'delete' },
                     	{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
        rightgrid = $("#rightgrid").ligerGrid({
			columns: [ {
						display: '科目编码', name: 'subj_code', align: 'left',width:'23%' 
					}, { 
						display: '科目名称', name: 'subj_name', align: 'left',width:'25%'
					}, { 
						display: '借方金额', name: 'debit', align: 'right',width:'25%',formatter: "###,##0.00",
						render : function(rowdata, rowindex, value) {
							return formatNumber(rowdata.debit, 2, 1);
						}
					}, { 
						display: '贷方金额', name: 'credit', align: 'right',width:'25%',formatter: "###,##0.00",
						render : function(rowdata, rowindex, value) {
 		  					return formatNumber(rowdata.credit, 2, 1);
						}
            } ],
                      dataAction: 'server',dataType: 'server',usePager:false,
                      url:'queryAccSubjByVouchWei.do?isCheck=false',
                      width: '100%', height: '100%', checkbox: false,rownumbers:true,
                      selectRowButtonOnly:true,delayLoad:true,
                      heightDiff: -70,  
                      toolbar: { items: [
										{ line:true },
                 						{ text: '打印', id:'print', click: printDate,icon:'print' }
                 						/* { line:true },
                                      	{ text: '导出', id:'down', click: exportDate,icon:'down' }, */
                     				]},
                     				lodop:{
                    		    		title:"凭证分册",
                    					fn:{
                        	    			debit:function(value){//借方
                        	    				if(value == 0){return "";}
                        	           			else{return formatNumber(value, 2, 1);}
                        	    			},
                        	    			credit:function(value){//贷方
                        	    				if(value == 0){return "";}
                        	          			else{return formatNumber(value, 2, 1);}
                        	   				}
                        	    		}
                    				}
                    });

        rightgridManager = $("#rightgrid").ligerGetGridManager();
    }
    
  //打印回调方法
    function lodopPrint(){
    	rightgrid.options.parms=[];
    	rightgrid.options.newPage=1;
        //根据表字段进行添加查询条件
		 
        var selPara={
        		acc_year :liger.get("acc_month").getValue().split(".")[0],
        		acc_month :liger.get("acc_month").getValue().split(".")[1],
        		vouch_type_code:vouch_type_code,
        		vouch_no_begin : vouch_no_begin,
        		vouch_no_end : vouch_no_end
        };
    	ajaxJsonObjectByUrl("queryAccSubjByVouchBT.do?isCheck=false", selPara,function (responseData){
    		
    		var head="<table class='head' width='100%'><tr><td>起止日期：自<span style='font-size:14px'>"+responseData.Rows[0].vouch_date_begin+"</span>起至<span style='font-size:14px'>"+responseData.Rows[0].vouch_date_end+"</span>止</td></tr>";
        	head=head+"<tr><td>凭证号数：自<span style='font-size:14px'>"+vouch_no_begin+"</span>起至<span style='font-size:14px'>"+vouch_no_end+"</span>止</td><td align='right'>附件总数："+responseData.Rows[0].vouch_att_num+"</td></tr></table>";
     		rightgrid.options.lodop.head=head;
    	},false);
    	
 		
 		
 		
    }
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'accVouchWeiAddPage.do', height: 370,width: 400, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							//表的主键
							this.wei_id+"@"+this.group_id +"@"+this.hos_id+"@"+this.copy_code+"@"+this.acc_year   
							);
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAccVouchWei.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
            }   
        }
        
    }
	function printDate(){
		
		 if(paraDate ==''){
				
				$.ligerDialog.warn("请先点击左侧表格中的一个【分册名称】链接后,再点击打印");
				
		 }else{
				
				/* /**
				 * 普通表格打印单表头<BR>
				 * 参数1:请求地址URL<BR>
				 * 参数2:请求参数条件<BR>
				 * 参数3:单表头<BR>
				 * 表头格式<BR>
				 *<p>
				 * var columnInfos = [ {
				 *		name : "dept_code",
				 *		displayName : "科室编码",
				 *      size: 80  列宽度
				 *	}]<BR>
				 *<p>
				 * 参数4:标题<BR>
				 * 参数5:延迟打印时间 <BR>
				        数据要加载 需要一点时间来缓冲  默认500 如果打印没有数据的话，延迟时间稍长一些<BR>
				 *
				 var vo = paraDate.split("|");
					var printPara={
						usePager:false,
						wei_id : vo[0],
		        		group_id : vo[1],
		        		hos_id : vo[2],
		        		copy_code : vo[3],
		        		acc_year : vo[4],
		        		vouch_no_begin : vo[5],
		        		vouch_no_end : vo[6] 
			         };
				var columnInfos =[{name : "subj_code",
					 	displayName : "科目编码",
					    size: 150 },
					    {	name : "subj_name",
						 	displayName : "科目明称",
						    size: 200},
					    {	name : "debit",
						 	displayName : "借方金额",
						    size: 150},
					    {	name : "credit",
						 	displayName : "贷方金额",
						    size: 150}
					    
				] 	
				viewPrintOneHead("queryAccSubjByVouchWei.do?isCheck=false", paraDate, columnInfos, vo[7]+"凭证分册信息", 1000) */
				
			 if(rightgrid.getData().length==0){
					$.ligerDialog.error("无打印数据！");
					return;
				}
			 var vo = paraDate.split("|");
			var heads={
		      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		      		  "rows": [
		      	  	          {"cell":0,"value":"起止日期：自"+span_begin_date+"起至"+span_end_date+"止","colSpan":"2"},
		      	  	     	  {"cell":0,"value":"凭证号数：自"+vo[6]+"起至"+vo[7]+"止","colSpan":"2","br":"true"},
		      	  	          {"cell":2,"value":"凭证号汇总："+(vo[7]-vo[6]+1)},
		      	  	          {"cell":3,"value":"附件总数："+span_att_num}
  
		      		  ]
		      	}; 
		   		
		   		var printPara={
		   			rowCount:1,
		   			title:'凭证分册'+vo[8], 
		   			columns: JSON.stringify(rightgrid.getPrintColumns()),//表头
		   			class_name: "com.chd.hrp.acc.service.vouch.AccVouchWeiService",
					method_name: "queryAccSubjByVouchWeiPrint",
					bean_name: "accVouchWeiService",
					heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
					vouch_no_begin:vo[6],
				    vouch_no_end:vo[7]
		   			};
		    	
		   		//执行方法的查询条件
		   		$.each(grid.options.parms,function(i,obj){
		   			printPara[obj.name]=obj.value;
		    	});
		   			
		    	officeGridPrint(printPara);
		    
		    	/* rightgrid.options.parms=[];
		    	rightgrid.options.newPage=1;
		        //根据表字段进行添加查询条件
		        var vo = paraDate.split("|");
				
		        rightgrid.options.parms.push({name:'wei_id',value:vo[0]});
		        rightgrid.options.parms.push({name:'group_id',value:vo[1]}); 
		        rightgrid.options.parms.push({name:'hos_id',value:vo[2]}); 
		        rightgrid.options.parms.push({name:'copy_code',value:vo[3]}); 
		        rightgrid.options.parms.push({name:'acc_year',value:vo[4]}); 
		        rightgrid.options.parms.push({name:'vouch_no_begin',value:vo[5]}); 
		        rightgrid.options.parms.push({name:'vouch_no_end',value:vo[6]}); 
		        var selPara={};
		    	$.each(rightgrid.options.parms,function(i,obj){
		    		selPara[obj.name]=obj.value;
		    	});
		   		//console.log(grid)
		   		var printPara={
		   			headCount:2,
		   			title:vo[6]+"-"+vo[7]+"凭证分册信息",
		   			type:3,
		   			columns:rightgrid.getColumns(1)
		   		};
		   		ajaxJsonObjectByUrl("queryAccSubjByVouchWei.do?isCheck=false", selPara, function(responseData) {
		   			printGridView(responseData,printPara);
		    	}); */
		 }
	}
	function exportDate(){
		
		if(paraDate ==''){
			
			$.ligerDialog.warn("请先点击左侧表格中的一个【分册名称】链接后,再点击导出");
			
	 	}else{
	 		//有数据直接导出
			 if($("#resultPrint > table > tbody").html()!=""){
				lodopExportExcel("resultPrint","导出Excel","凭证分册信息.xls",true);
				return;
			} 
			
			//重新查询数据，避免分页导致打印数据不全
			var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

			var vo = paraDate.split("|");
			var exportPara={
				usePager:false,
				wei_id : vo[0],
        		group_id : vo[1],
        		hos_id : vo[2],
        		copy_code : vo[3],
        		acc_year : vo[4],
        		vouch_no_begin : vo[5],
        		vouch_no_end : vo[6] 
	         };
			ajaxJsonObjectByUrl("queryAccSubjByVouchWei.do",exportPara,function (responseData){
				var trHtml='';
				$.each(responseData.Rows,function(idx,item){ 
					 trHtml+="<tr>";
					 trHtml+="<td>"+item.subj_code+"</td>"; 
					 trHtml+="<td>"+item.subj_name+"</td>"; 
					 trHtml+="<td>"+item.debit+"</td>";
					 trHtml+="<td>"+item.credit+"</td>";
					 trHtml+="</tr>";
					$("#resultPrint > table > tbody").empty();
					$("#resultPrint > table > tbody").append(trHtml);
				});
				manager.close();
				//alert($("#resultPrint").html())
				lodopExportExcel("resultPrint","准备导出","凭证分册信息",true);
		    },true,manager);
			return; 
	 	}
	}
    function loadDict() {
		//字典下拉框
		autocomplete("#vouch_type_code", "../../queryVouchType.do?isCheck=false","id", "text", true, true,'',false,'',100);
		
		autocomplete("#subj_level","../../querySubjLevel.do?isCheck=false","id","text",true,true,'',false,'',100);
		
		$("#acc_month").ligerComboBox({url: '../../queryYearMonth.do?isCheck=false',valueField: 'id',textField: 'text', selectBoxWidth: 90,autocomplete: true,width: 90});
		
		var year_Month = '${yearMonth}';

   	    liger.get("acc_month").setValue(year_Month);
		 
   	    liger.get("acc_month").setText(year_Month);
	}

    
    function openUpdate(obj){
    	
    	var vo = obj.split("|");
		var parm = "wei_id="+
			vo[0]   +"&group_id="+ 
			vo[1]   +"&hos_id="+ 
			vo[2]   +"&copy_code="+ 
			vo[3]   +"&acc_year="+ 
			vo[4];

    	$.ligerDialog.open({ url : 'accVouchWeiUpdatePage.do?' + parm,data:{}, height: 370,width: 400, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccVouchWei(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>会计期间：</td>
			<td align="left" class="l-table-edit-td"><input 
				name="acc_month" type="text" id="acc_month" 
				style="width: 160px;" /> 
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">凭证类型：</td>
			<td align="left" class="l-table-edit-td"><input
				name="vouch_type_code" type="text" id="vouch_type_code" 
				/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科目级次：</td>
			<td align="left" class="l-table-edit-td"><input
				name="subj_level" type="text" id="subj_level" 
				/></td>
			<td align="left"></td>
		</tr>
	</table>

	<div style="width: 100%">
		<div  style="float: left; width: 40%;">
			<div id="toptoolbar" ></div>
		
			<div id="maingrid"></div>
		</div> 
		<div  style="margin-left: 10px; float: left; width: 58%;">
			<div id="righttoolbar" >
				<h2 id="h_title" align="center">凭证分册</h2>
				<div align="left" style="font-size: 14px">起止日期：自<span id="span_begin_date"></span>起至<span id="span_end_date"></span>止</div>
				<div align="left" style="font-size: 14px"><div align="left" style="float: left">凭证号数：自<span id="span_begin_no"></span>起至<span id="span_end_no"></span>止</div><div align="right" style="float: right">附件总数：<span id="span_att_num"></span></div></div>
				<div align="left" style="font-size: 14px"><div align="left" style="float: left;padding-left:20px">凭证号汇总：<span id="vouch_sum"></span></div></div>
			
			</div> 
		
			 <div id="rightgrid"></div>
		</div>
		<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
			<tr>
                <th width="200">科目编号</th>	
                <th width="200">科目名称</th>	
                <th width="200">借方金额</th>
                <th width="200">贷方金额</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
	</div>

</body>
</html>
