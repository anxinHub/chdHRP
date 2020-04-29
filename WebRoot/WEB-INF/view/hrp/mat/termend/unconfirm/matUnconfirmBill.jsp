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
	var store_id = null;
	var store_no;
	var store_name;
	var currentFirstDate;
	var currentEndDate;
	var year;
	var month; 
	var query_flag=0;
    var renderFunc = {
    		/* state:function(value){//状态
    			
				return formatNumber(value, '${sessionScope.mat_para_map["04005"] }', 1);
			}, */
			/* is_com:function(value){//是否代销
				if(value==1){
					return "是";
				}else if(value==0){
					return "否";
				}
 
			}  */
	}; 

	$(function() {
		loadBody();//加载下拉框
		loadHead(null);//加载数据	
		query();//查询
	});

	function loadSelDate(){//年月的onchange方法
		if(year==null||year==''||month==null||month==''){
			return;
		}else{
			query_flag=0;
			var str = getMonthDate(liger.get("year").getValue() , liger.get("month").getValue());
	        var p = str.split(';');
	        currentFirstDate = p[0];
	        currentEndDate = p[1];
		}
	}
	
	function loadBody(){//加载body
	   //返回当前年,当前月,
		var date = getCurrentDate();
        var aa = date.split(';');
        year = aa[0];
        month = aa[1];
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		autocomplete("#year","../../queryMatYear.do?isCheck=false","id","text",true,true);
        liger.get("year").setValue(year);
        liger.get("year").setText(year);
        
        autocomplete("#month","../../queryMatMonth.do?isCheck=false","id","text",true,true);
        liger.get("month").setValue(month);
        liger.get("month").setText(month);
		
/* 		$("#store_code").ligerComboBox({
           	url: '../../queryMatStore.do?isCheck=false',
           	valueField: 'id',
            textField: 'text', 
            selectBoxWidth: 160,
           	autocomplete: true,
           	initValue : 0,
           	width: 160
  		  }); */
		$("#store_code").ligerComboBox({
           	url: '../../queryMatStoreDictDate.do?isCheck=false',
           	valueField: 'id',
            textField: 'text', 
            selectBoxWidth: 160,
           	autocomplete: true,
           	initValue : 0,
           	width: 160,
           	parms:{read_or_write:1}
  		  });
		
		$("#year").ligerTextBox({width:80});
		$("#month").ligerTextBox({width:80});
		$("#store_code").ligerTextBox({width:160});
	}
	
	function loadHead() {//加载grid；定义按钮及其方法名
		grid = $("#maingrid").ligerGrid({
		       columns: [ { display: '单据标记', name: 'bill_flag',  hide:true},
		                  { display: '仓库名称', name: 'store_name', align: 'left',width :'15%'},
		                  { display: '业务类型', name: 'bus_type_name', align: 'left',width :'10%' },
		                  { display: 'out_id', name: 'out_id',hide:true},
		                  { display: 'in_id', name: 'in_id',hide:true},
		                  { display: 'special_id', name: 'special_id',hide:true},
		                  { display: '单据号', name: 'bill_code', align: 'left',width :'15%',
		                		render : function(rowdata, rowindex, value) {
									return '<a href=javascript:openUpdate("' 
											+ rowdata.in_id 
											+ ','  + rowdata.special_id 
											+ ','  + rowdata.out_id 
											
											
											+ ',' + rowdata.bus_type_code
											+ '")>'+rowdata.bill_code+'</a>';
								}
		                	  
		                  },
						  { display: '制单人', name: 'maker', align: 'left' ,width :'10%'},
						  { display: '编制日期', name: 'create_date', align: 'left',width :'10%'},
						  { display: '单据状态', name: 'state', align: 'left',width :'10%'},
						  { display: '是否代销', name: 'is_com', align: 'left',width :'10%'}
					
		                ],
		                
		                 dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatUnconfirmBill.do',
		                 width: '100%', height: '100%', checkbox : false , rownumbers : true,delayLoad:true,
		                 selectRowButtonOnly:true,
		                 toolbar: { items: 
		                	 	[
		                            {text: '查询', id:'query', click: query ,icon:'search' },
		                            { line:true },/*
		                            {text: '结转下月', id:'checkout', click: checkout ,icon:'' },
		                            { line:true },*/
		                            {text : '打印',id : 'print',click : print ,icon : 'print'}, 
		      						{line : true}
		                   		]}
		               });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
	
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>编制年月："+$("#year").val() +"年"+ $("#month").val()+"日"+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="未确认单据列表";
    }
    
	
	function checkout(btn){//结转下月
		
		if(query_flag==0){//结转之前先查询
			//query();
			$.ligerDialog.warn('请先查询！');
    		return;
		}
	
		var data =gridManager.getData();
		if(data.length==0){
			$.ligerDialog.warn('该月没有未确认单据！');
    		return;
		}else{
			$.ligerDialog.confirm('将要把全部未确认单据结转到下个月，是否继续？', function (yes){
				if(yes){//确定要结转下月。
					//结转月份必须是物流当前所在期间
			    	if('${mat_year}'==liger.get("year").getValue()  &&  '${mat_month}'== liger.get("month").getValue()){
			    		var paramStr ="";
                        $(data).each(function(){//返回单据标记数组，根据数组确定更新哪些表的数据
                        	if(paramStr.length==0) paramStr=this.bill_flag;
                        	 if(paramStr.indexOf(this.bill_flag)<0){
                        		 paramStr+=','+this.bill_flag;
                        		}
                        });
	                    var formPara={
	                    		year : liger.get("year").getValue(),
	                    		month : liger.get("month").getValue(),
	                    		store_id : liger.get("store_code").getValue().split(',')[0],
	                    		billFlag: paramStr
	                    }
	                    
		    	    	ajaxJsonObjectByUrl("updateUnconfirmBill.do",formPara,function(responseData){
			              if(responseData.state=="true"){
			                  query();
			              }
		    	    	});
		    	    	
			    	}else{
			    		$.ligerDialog.warn('选择的月份不是当前物流系统未结账的最小期间，请重新选择！');
			    		return;
			    	}
				}
			});
		}
	}
	function openUpdate(obj) {
		var voStr = obj.split(",");
		var paras =  "in_id=" + voStr[0];
		var paraspet ="special_id=" + voStr[1]; 
		var parasout = "out_id=" + voStr[2];
		var bus_type_code = voStr[3];
		
		if(bus_type_code =='2'){
			parent.$.ligerDialog.open({
				title: '(采购入库)',
				height: $(window).height(),
				width: $(window).width(),
				url: 'hrp/mat/storage/in/updatePage.do?isCheck=false&' + paras,
				modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});  
		}else if (bus_type_code == '28'){
			parent.$.ligerDialog.open({
				title: '(代销出库)',
				height: $(window).height(),
				width: $(window).width(),
				url: 'hrp/mat/affi/out/matAffiOutCommonUpdatePage.do?isCheck=false&' + parasout,
				modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});  
		}else if (bus_type_code == '3'){

			parent.$.ligerDialog.open({
				title: '(科室领用)',
				height: $(window).height(),
				width: $(window).width(),
				url: 'hrp/mat/storage/out/outlibrary/matOutMainUpdatePage.do?isCheck=false&' + parasout,
				modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});  
		} else if (bus_type_code == '47'){

			parent.$.ligerDialog.open({
				title: '(专购品入库)',
				height: $(window).height(),
				width: $(window).width(),
				url: 'hrp/mat/storage/special/updatePage.do?isCheck=false&' + paraspet,
				modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});  
		}
		else if (bus_type_code == '49'){

			parent.$.ligerDialog.open({
				title: '(专购品出库)',
				height: $(window).height(),
				width: $(window).width(),
				url: 'hrp/mat/storage/special/updatePage.do?isCheck=false&' + paraspet,
				modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});  
		}
		else if (bus_type_code == '14'){

			parent.$.ligerDialog.open({
				title: '(入库单查看)',
				height: $(window).height(),
				width: $(window).width(),
				url: 'hrp/mat/storage/tran/matTranMainUpdateInPage.do?isCheck=false&' + paras,
				modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});  
		}
		else if (bus_type_code == '15'){

			parent.$.ligerDialog.open({
				title: '(出库单查看)',
				height: $(window).height(),
				width: $(window).width(),
				url: 'hrp/mat/storage/tran/matTranMainUpdateOutPage.do?isCheck=false&' + parasout,
				modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});  
		}
		else if (bus_type_code == '12'){

			parent.$.ligerDialog.open({
				title: '(出库单查看)',
				height: $(window).height(),
				width: $(window).width(),
				url: 'hrp/mat/storage/back/updatePage.do?isCheck=false&' + paras,
				modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});  
		}
		else if (bus_type_code == '8'){

			parent.$.ligerDialog.open({
				title: '(出库单查看)',
				height: $(window).height(),
				width: $(window).width(),
				url: 'hrp/mat/storage/tran/matTranMainUpdateInPage.do?isCheck=false&' + paras,
				modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});  
		}
		else if (bus_type_code == '27'){

			parent.$.ligerDialog.open({
				title: '(代销入库)',
				height: $(window).height(),
				width: $(window).width(),
				url: 'hrp/mat/affi/in/matAffiInCommonUpdatePage.do?isCheck=false&' + paras,
				modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});  27
		}
		 
	}
	//打印
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
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
   			title:'未确认单据列表',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"统计日期: " + $("#year").val() +" 至  "+ $("#month").val(),"colspan":colspan_num,"br":true}
   			],
   			foot:[
				{"cell":0,"value":"主管:","colspan":3,"br":false} ,
				{"cell":3,"value":"复核人:","colspan":colspan_num-5,"br":false},
				{"cell":colspan_num-2,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":true},
				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
   			],
   			columns:grid.getColumns(1),
   			headCount:1,//列头行数
   			autoFile:true,
   			type:3
   		};
   		ajaxJsonObjectByUrl("queryMatUnconfirmBill.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
	
	function query() {//查询方法
		query_flag=1;
		
		if(liger.get("year").getValue() =='' || liger.get("year").getValue() == null||liger.get("month").getValue() =='' || liger.get("month").getValue() == null){
			$.ligerDialog.warn("请选择年月！");	
			return;
		}
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
    	grid.options.parms.push({name : 'createDateB',value : currentFirstDate}); 
    	grid.options.parms.push({name : 'createDateE',value : currentEndDate}); 
    	
    	grid.options.parms.push({name : 'year',value : liger.get("year").getValue()});
    	grid.options.parms.push({name : 'month',value : liger.get("month").getValue()});
    	
    	grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(',')[0]}); 
    	grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(',')[1]});
 
		//加载查询条件
		grid.loadData(grid.where);
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="100%">
		<tr></tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">编制年月：</td>
			<td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td><input name="year" type="text" id="year" requried="true" /></td>
						<td align="center" class="l-table-edit-td" style="width: 15px;">年</td>
						<td align="right" class="l-table-edit-td">
							<input name="month" type="text" id="month" requried="true" onchange="loadSelDate();"/>
						</td>
						<td align="center" class="l-table-edit-td" style="width: 15px;">月</td>
					</tr>
				</table>
			</td>
			<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;</td>
			
			<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;</td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">仓库：</td>
			<td align="left" class="l-table-edit-td">
				<input name="store_code" type="text" requried="true" id="store_code" />
			</td>
			<td align="left"></td>
			
		</tr>
		
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
