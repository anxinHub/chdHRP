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
	var time = new Date(); //获得当前时间
	var year = time.getFullYear();//获得年、月、日
	var month = time.getMonth()+1;
	var day = time.getDate(); 
	var date = year+"年"+month+"月"+day;
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function (){
        loadDict();//加载下拉框
        loadHotkeys();
    	loadHead(null);	//加载数据
        
    });
    
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
    	if(!$("#year").val()){
    		$.ligerDialog.error("年度不能为空！");
    	}
    	if(!$("#month").val()){
    		$.ligerDialog.error("月份不能为空！");
    	}
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'year',value:$("#year").val()});
        grid.options.parms.push({name:'month',value:$("#month").val()});
    	grid.options.parms.push({name:'inv_code',value:$("#inv_code").val()});
    	grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split(",")[0]});
     	 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					{ display: '仓库编码', name: 'store_code', align: 'left',width: '110',frozen: true
					},
					{ display: '仓库名称', name: 'store_name', align: 'left',width: '110',frozen: true
					},
                     { display: '物资编码', name: 'inv_code', align: 'left',width: '110',frozen: true
                     },
                     { display: '物资名称', name: 'inv_name', align: 'left',width: '110',frozen: true
					 		},
					 { display: '规格型号', name: 'inv_model', align: 'left',width: '110',frozen: true
					 		},
					 { display: '计量单位', name: 'unit_name', align: 'left',width: '110',frozen: true
					 		},
					 { display: '条形码', name: 'bar_code', align: 'left',width: '110',frozen: true
					 		},
                     { display: '期初金额',  align: 'center',
					 			columns:[
                                    {display:'合计',name:'begin_money',align:'right',width:'120',
			                    	 	render : function(rowdata, rowindex,
												value) {
											 return formatNumber(value,'${p04005}',1);
										}
                                    },
								 	{display:'正常',name:'add_begin_money',align:'right',width:'100',
		                           		render : function(rowdata, rowindex,
												value) {
											 return formatNumber(value,'${p04005}',1);
										}
                                    },
		                         	{display:'流转',name:'dec_begin_money',align:'right',width:'100',
		                           		render : function(rowdata, rowindex,
												value) {
											 return formatNumber(value,'${p04005}',1);
										}
                                    }
						  		]

					 		},
					 { display: '入库金额', align: 'center',  
					 		columns:[
                                   {display:'合计',name:'in_money',align:'right',width:'120',
				                    	render : function(rowdata, rowindex,value) {
											return formatNumber(value,'${p04005}',1);
										}
                                    },
									{display:'正常',name:'add_in_money',align:'right',width:'100',
				                        render : function(rowdata, rowindex,value) {
												return formatNumber(value,'${p04005}',1);
										}
                                    },
				                    {display:'流转',name:'dec_in_money',align:'right',width:'100',
			                         	render : function(rowdata, rowindex,value) {
											return formatNumber(value,'${p04005}',1);
										}
                                    }
								]
					 		},
			 		{ display: '出库金额', align: 'center',  
				 		columns:[
                                  {display:'合计',name:'out_money',align:'right',width:'120',
			                    	render : function(rowdata, rowindex,value) {
										return formatNumber(value,'${p04005}',1);
									}
                                   },
								{display:'正常',name:'add_out_money',align:'right',width:'100',
			                        render : function(rowdata, rowindex,value) {
											return formatNumber(value,'${p04005}',1);
									}
                                   },
			                    {display:'流转',name:'dec_out_money',align:'right',width:'100',
		                         	render : function(rowdata, rowindex,value) {
										return formatNumber(value,'${p04005}',1);
									}
                                   }
							]
				 		},
			 		{ display: '结存金额', align: 'center',  
				 		columns:[
                                  {display:'合计',name:'end_money',align:'right',width:'120',
			                    	render : function(rowdata, rowindex,value) {
										return formatNumber(value,'${p04005}',1);
									}
                                   },
								{display:'正常',name:'add_end_money',align:'right',width:'100',
			                        render : function(rowdata, rowindex,value) {
											return formatNumber(value,'${p04005}',1);
									}
                                   },
			                    {display:'流转',name:'dec_end_money',align:'right',width:'100',
		                         	render : function(rowdata, rowindex,value) {
										return formatNumber(value,'${p04005}',1);
									}
                                   }
							]
				 		},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatDuraQueryStock.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                                    { text: '查   询 （<u>Q</u>）', id:'search', click: query,icon:'search' },
                                    { line:true }, 
           						    { text: '打   印 （<u>P</u>）', id:'print', click: printDate,icon:'print' }
                                ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
   
    function loadDict(){
       	//字典下拉框
       	autocomplete("#store_id", "../../queryMatStore.do?isCheck=false", "id", "text", true, true, "", false,'',200);
       	
       	$("#year").ligerTextBox({width:100}); 
       	$("#month").ligerTextBox({width:60}); 
       	$("#inv_code").ligerTextBox({width:240});
       	$("#store_id").ligerTextBox({width:200});
        autodate("#year","YYYY"); 
        autodate("#month","MM"); 
		 
    } 
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('P', printDate);
	}

	//打印数据
	/* function printDate(){
		if(grid.getData().length==0){
  			$.ligerDialog.error("请先查询数据！");
  			return;
  		}
		
		var printPara={
				title: "",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.ass.service.",
				method_name: "",
				bean_name: ""// ,
				//heads: JSON.stringify(heads) //表头需要打印的查询条件,可以为空
				// foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	   		
	} */
	
	function printDate(){
		
if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月："},
    	          {"cell":1,"value":$("#year").val()+"年"+$("#month").val()+"月"} , /*          {"cell":1,"value":""+liger.get("begin_confirm_date").getValue()+"至"+liger.get("end_confirm_date").getValue()}	, */
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
          		title: "耐用品库存查询",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.dura.query.MatDuraQueryService",
    			method_name: "queryMatDuraQueryStockPrint",
       			bean_name: "matDuraQueryService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);
    /* 	
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
       			title:'耐用品库存查询',
       			columns:grid.getColumns(1),
       			headCount:2,//列头行数
       			autoFile:true,
       			type:3
       	};
   		ajaxJsonObjectByUrl("queryMatDuraQueryStock.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});
 */
   		
    }
	  
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr >
        	<td>
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资：</td> 
            <td align="left" class="l-table-edit-td">
            	<input name="inv_code" type="text" id="inv_code"/>
            </td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td> 
            <td align="left" class="l-table-edit-td">
            	<input name="store_id" type="text" id="store_id"/>
            </td>
        </tr> 
       
    </table>
	<div align="center">
    	<h2>
	    	<span id="year_month_span"></span>耐用品库存查询
    	</h2>
    </div>
	<div id="maingrid"></div>
	
</body>
</html>
