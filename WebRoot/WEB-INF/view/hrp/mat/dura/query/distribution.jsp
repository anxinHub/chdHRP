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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
	var time = new Date(); //获得当前时间
	var year = time.getFullYear();//获得年、月、日
	var month = time.getMonth()+1;
	var day = time.getDate(); 
	var date = year+"年"+month+"月"+day;
    var grid;
    var gridManager = null;
    var userUpdateStr;
	var paraMoney = '${p04005}';
	var paraPrice = '${p04006}';
	
    var renderFunc = {
		end_amount: function(value){//期末数量
  				return formatNumber(value, 2, 1); 
		}
	};
    
    $(function (){
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		// query();
    });
    //查询
    function  query(){
    	
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'year',
			value : liger.get("year").getValue() == null ? "" : liger.get("year").getValue()
		});
		grid.options.parms.push({
			name : 'month',
			value : liger.get("month").getValue() == null ? "" : liger.get("month").getValue()
		}); 
		grid.options.parms.push({
			name : 'inv_message',
			value : liger.get("inv_message").getValue() == null ? "" : liger.get("inv_message").getValue()
		}); 
		grid.options.parms.push({
			name : 'inv_id',
			value : liger.get("inv_id").getValue() == null ? "" : liger.get("inv_id").getValue()
		}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
				{
					display: '科室(仓库)编码', name: 'dept_code', align: 'left', width: '120',
				}, { 
					display: '科室(仓库)名称', name: 'dept_name', align: 'left', width: '150',
				}, { 
					display: '材料编码', name: 'inv_code', align: 'left', width: '120',
				}, { 
					display: '材料名称', name: 'inv_name', align: 'left', width: '200',
				}, { 
					display: '结存数量', name: 'end_amount', align: 'right', width: '120',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, 2, 1);
					}
		 		}
		 	],
			dataAction: 'server', dataType: 'server', usePager: true, url: 'queryMatDuraQueryDistribution.do',
			width: '100%', height: '97%', checkbox: false, rownumbers: true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly: true,//heightDiff: -10,
			toolbar: { 
				items: [ 
					 { text: '查询（<u>Q</u>）', id: 'search', click: query, icon: 'search'},
					 { line:true },
					 { text: '打印', id:'print', click: print, icon:'print' },
		   			 { line:true }
				]
			}
		});
        gridManager = $("#maingrid").ligerGetGridManager();
    }
   
    function loadDict(){
		//字典下拉框
    	autodate("#year","YYYY"); 
        autodate("#month","MM"); 
        $("#year").ligerTextBox({width:100}); 
       	$("#month").ligerTextBox({width:60}); 
 	    var count = 0;
	
		$("#inv_id").ligerComboBox({
			url: "",
			valueField: "id",
			textField: "text",
			selectBoxWidth: '400',
			selectBoxHeight:'260',
			setTextBySource: true,
			width: '180',
			autocomplete: true,
			highLight: true,
			keySupport: true,
			async: true,
			alwayShowInDown: true,
		    onSuccess: function (data) {
				if (count == 0) {this.setValue(data[0].id);}
				count++;
			}  
		});
	
		$("#inv_id").ligerTextBox({width:235});
		$("#inv_message").ligerTextBox({width:240});
        //格式化按钮
		$("#query").ligerButton({click: query, width:70});
// 		$("#print").ligerButton({click: print, width:70});
	}
    
    function subjSelector(){ 
    	$.ligerDialog.open({ url : 'matInvSelectorPage.do?isCheck=false',data:{}, height: 470,width: 480, title:'耐用品材料选择器',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
   			buttons: [ 
	           { text: '确定', onclick: 
	        	   function (item, dialog) { 
		        	   var boxData = dialog.frame.getListBox();
		        	   var param = "";
			           var subj_param="";
		        	   subj_box_data = "";
		        		 
		        	   $.each(boxData,function(i,v){
		        		   
							if(boxData.length == (i+1)){
								
							 	  subj_box_data = subj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'}";
								  
								  param = param + v.text;
								   
								  subj_param=subj_param + v.id;
				        		  
							  }else{ 
								  subj_box_data = subj_box_data +"{'id':'"+v.id+"','text':'"+v.text+"'},";
				        			
								  param = param + v.text+ ",";
				        			 
								  subj_param=subj_param + v.id + ",";
				        		  
							  }
		        	   });
		        	   query_subj_code = param;
		        	
		        	   liger.get("inv_id").setValue(subj_param);
		              
		        	   liger.get("inv_id").setText(param);
		        	    
			           dialog.close(); 
	        	   },cls:'l-dialog-btn-highlight' 
	           }, 
	           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
	        ] 
    	});
    }
  //打印
	function print(){
		if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月："},
    	          {"cell":1,"value":liger.get("year").getValue()+"年"+liger.get("month").getValue()+"月"} , /*          {"cell":1,"value":""+liger.get("begin_confirm_date").getValue()+"至"+liger.get("end_confirm_date").getValue()}	, */
    	        /*   {"cell":3,"value":"仓库："},
    	          {"cell":4,"value":""+liger.get("store_code").getText()==''?'空':liger.get("store_code").getText().split(" ")[1]+""} */
        	]}; 
    	//表尾
		var foots = {
			rows: [
				{"cell":0,"value":"制单日期:"} ,
				{"cell":1,"value":date} ,
			]
		}; 
    	var printPara={
          		title: "全院耐用品数量分布",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.dura.query.MatDuraQueryService",
    			method_name: "queryMatDuraQueryDistributionPrint",
       			bean_name: "matDuraQueryService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);
    	/* if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
   		
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];

		//表头
	  	var heads = {
	  		rows: [
					{"cell":0,"value":"单位: ${sessionScope.hos_name}","colSpan":3},
					{"cell":0,"value":"统计日期: " + $("#startDate").val() +" 年  "+ $("#endDate").val(),"colSpan":3,"br":true}
	   		]
	  	};
		//表尾
		var foots = {
			rows: [
					{"cell":0,"value":"主管:","colSpan":3} ,
					{"cell":3,"value":"复核人:","colSpan":3},
					{"cell":1, "from":"right","align":"right","value":"制单人： ${sessionScope.user_name}","colSpan":2},
					{"cell":0,"value":"制单日期:","br":true} ,
	   		]
		}; 
  	  
        var printPara={
			title: "业务明细查询",//标题
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.mat.service.dura.query.MatDuraQueryService",
			method_name: "queryMatDuraQueryDistributionPrint",
			bean_name: "matDuraQueryService", 
			heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
		};
		
		//执行方法的查询条件
		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
		});
			
		officeGridPrint(printPara); */
    }
   
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" >	</div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="left" class="l-table-edit-td">
            	<table>
        			<tr>
        			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">年度：</td>
		            <td align="left" class="l-table-edit-td"><input name="year" type="text" id="year" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})"/></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">月份：</td>
		            <td align="left" class="l-table-edit-td"><input name="month" type="text" id="month" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})"/></td>
		            <td align="left"></td>
        			</tr>
        		</table>
            </td>
            <td align="left" class="l-table-edit-td"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"> 材料信息：</td>
            <td align="left" class="l-table-edit-td" ><input name="inv_message" type="text" id="inv_message" ltype="text"  /></td>
            
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;"> 材料名称：</td>
            <td align="left" class="l-table-edit-td"><input name="inv_id" type="text" id="inv_id" ltype="text" /></td>
            <td align="left"><input class="l-button l-button-test"  type="button" value="选择" onclick="subjSelector();"/></td>
        </tr>
    </table>
    <div align="center">
    	<h2>
	    	<span id="year_month_span"></span>全院耐用品数量分布
    	</h2>
    </div>
	<div id="maingrid"></div>
</body>
</html>
