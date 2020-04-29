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
    
    var bus_add = ${bus_add};
    var bus_dec = ${bus_dec}; 
    
    var bus_add_col = new Array();
    var bus_dec_col = new Array();
    
    console.log(${bus_add})
    
  //打印 单元格格式化 用
    var renderFunc = {
			 
    		init_price : function(value){ //期初余额
				return formatNumber(value, '${ass_05005 }', 1);
			},
			add_price : function(value){ //采购增加 
				return formatNumber(value, '${ass_05005 }', 1);
			},
			add_price_sum : function(value){ //采购增加 合计
				return formatNumber(value, '${ass_05005 }', 1);
			},
			reduce_price: function(value){ //采购退货
				return formatNumber(value, '${ass_05005 }', 1);
			},
			reduce_price_sum : function(value){ //采购退货 合计
				return formatNumber(value, '${ass_05005 }', 1);
			},
			sum_price : function(value){ // 期末余额
				return formatNumber(value, '${ass_05005 }', 1); 
			}
	};
    $(function ()
    {
    	//原值增加
    	$.each(bus_add.Rows,function(i,n){
    		bus_add_col.push({display:n.bus_name,name:'bus_'+n.bus_type,align:'right',width:'10%',
           	 render : function(rowdata, rowindex,
						value) {
					 return formatNumber(value,'${ass_05006 }',1);
				}
           }) ;
    	});
    										   
    	bus_add_col.push({display:'合计',name:'r_add_sum',align:'right',width:'10%',
          	 render : function(rowdata, rowindex,value) {
				 return formatNumber(value,'${ass_05005 }',1);
			}
         });
    	
    	//原值减少
    	$.each(bus_dec.Rows,function(i,n){
    		bus_dec_col.push({display:n.bus_name,name:'dec_'+n.bus_type,align:'right',width:'10%', 
           	 render : function(rowdata, rowindex,value) {
				 return formatNumber(value,'${ass_05006 }',1);
			}
                }) ;
    	});
    	
    	bus_dec_col.push({display:'合计',name:'r_dec_sum',align:'right',width:'10%',
       	 	render : function(rowdata, rowindex,value) {
				 return formatNumber(value,'${ass_05005 }',1);
			}
         });
    	
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();

		
    });
    //查询
    function  query(){
    	if(isnull($("#acc_year_month_begin").val()) || isnull($("#acc_year_month_end").val())){
   			$.ligerDialog.error("请选择起始会计期间！");
   			return;
   		}
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		if(!isnull($("#acc_year_month_begin").val())){
        		grid.options.parms.push({name:'year_month_begin',value:$("#acc_year_month_begin").val()}); 
            }
            if(!isnull($("#acc_year_month_end").val())){
        		grid.options.parms.push({name:'year_month_end',value:$("#acc_year_month_end").val()}); 
            }
     	  grid.options.parms.push({name:'type_level',value:$("#type_level").val()});
     	 grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split("@")[0]});

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                    
                     
                     { display: '类别编码', name: 'ass_type_code', align: 'left',width:'10%'
					 		},
					 { display: '类别名称', name: 'ass_type_name', align: 'left',width:'20%'
					 		},
					 { display: '采购仓库', name: 'store_name', align: 'left',width:'20%'
					 		},		
                     { display: '期初金额', name: 'r_begin', align: 'right',width:'15%',
					 			 render : function(rowdata, rowindex,value) {
										 return formatNumber(value,'${ass_05005 }',1);
									},formatter:'###,##0.00'
					 		},
					 { display: '原值增加',  align: 'center',formatter:'###,##0.00',
					 			columns:bus_add_col
					 		
							 },
			 		 { display: '原值减少',  align: 'center',formatter:'###,##0.00',
			 			columns:bus_dec_col
					 		},
					 { display: '期末金额', name: 'r_end', align: 'right',width:'15%',
					 			 render : function(rowdata, rowindex,value) {
									 return formatNumber(value,'${ass_05005 }',1);
								},formatter:'###,##0.00'
					 		}
                     ],
                     dataAction: 'server',
                     dataType: 'server',
                     usePager:true,
                     url:'queryAssPriceChangeStore.do',
                     width: '100%', 
                     height: '100%', 
                     checkbox: false,
                     rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     delayLoad:true,
                     toolbar: { items : [ 
                    	 { text: '查   询', id:'search', click: query,icon:'search' },
              			 { line:true },
              			 { text: '打   印', id:'print', click: printDate,icon:'print' },
					     { line:true },
				     ]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="固定资产原值表动表"; 
    }
    
    function loadDict(){
    	
		var param = {query_key:''};
		
	 	$("#acc_year_month_begin").ligerTextBox({width:70}); 
       	$("#acc_year_month_end").ligerTextBox({width:70}); 
		$("#type_level").ligerComboBox({
	           width : 60,
	           data: [
	               { text: '1', id: '1' },
	               { text: '2', id: '2' },
	               { text: '3', id: '3' },
	               { text: '4', id: '4' },
	               { text: '5', id: '5' },
	               { text: '6', id: '6' }
	           ]
	       });
		
		//默认年
        if(${ass_year_month}){
			$("#acc_year_month_begin").val('${ass_year_month }')
        }else{
        	autodate("#acc_year_month_begin","YYYYMM"); 
        }
        if(${ass_year_month}){
			$("#acc_year_month_end").val('${ass_year_month }')
        }else{
        	autodate("#acc_year_month_end","YYYYMM"); 
        }
        
        $("#store_id").ligerComboBox({
          	url: '../queryHosStoreDict.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160,
          	onSelected :function(value,text){
          		//loadHead(null);
          		//query();
          	}
 		  });
	}
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
	}
	
	//打印数据
 	function printDate(){
 		if(grid.getData().length==0){
   			$.ligerDialog.error("请先查询数据！");
   			return;
   		}
 		
 		var time=new Date();
 		var date=$("#acc_year_month_begin").val()+"至"+$("#acc_year_month_end").val();
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"会计期间："},
    	          {"cell":1,"value":$("#acc_year_month").val()},
    	          {"cell":4,"value":"报表日期:"},
  				  {"cell":5,"value":date} ,
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":4,"value":"制表人:"},
    				{"cell":5,"value":"${sessionScope.user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "资产原值变动表",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.guanLiReports.AssPriceChangeStoreService",
 				method_name: "queryAssPriceChangStoreMainPrint",
 				bean_name: "assPriceChangeStoreService" ,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
 				};
 		
 		$.each(grid.options.parms,function(i,obj){
 				printPara[obj.name]=obj.value;
 		});
 		
 		officeGridPrint(printPara);
 	   		
 	}
 	
    function changeYearMonth() {
    	var v_year_month_b=$("#acc_year_month_begin").val();
    	var v_year_month_e=$("#acc_year_month_end").val();
    	var parm = "&year_month="+v_year_month_b+"&year_month_end="+v_year_month_e;
    	ajaxJsonObjectByUrl("queryBusTypesStore.do?isCheck=false"+parm, {},
				function(responseData) {
    		   bus_add = ${bus_add};
    		     bus_dec = ${bus_dec};
				}, false);
		if (grid) {
			//由于一个对象多次绑定相同的事件，需要进行解绑在绑定
			grid.unbind(); 
		}
		loadHead();
		//query();
	}

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">会计期间：</td>
			<td align="left" class="l-table-edit-td"><input name="acc_year_month_begin" type="text" id="acc_year_month_begin" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" 
            onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'acc_year_month_end\');}',isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"
				onchange="changeYearMonth()"/></td>
				<td align="left" width="2%">&nbsp;至：</td>
			<td align="left"><input name="acc_year_month_end" type="text" id="acc_year_month_end" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'acc_year_month_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" onchange="changeYearMonth()" /></td>
           
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资产分类汇总到级次：</td>
			<td align="left" class="l-table-edit-td"><input
				name="type_level" type="text" id="type_level" /></td>
				<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">采购库房：</td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" /></td>
            <td align="left"></td>
		</tr>

	</table>

	<div id="maingrid"></div>
</body>
</html>
