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
    var grid;
    var show_detail=0;
    var gridManager = null;
    var userUpdateStr;
    var year;
	var month;
	var query_flag=0;
    var renderFunc = {
    		bill_money:function(value){//发票金额
				return formatNumber(value==null?0:value, '${p04005 }', 1);
			},
    		coupon_money:function(value){//优惠金额
				return formatNumber(value==null?0:value, '${p04005 }', 1);
			}, 
			payable_money:function(value){//应付金额
				return formatNumber(value==null?0:value, '${p04005 }', 1);
			},
			payed_money:function(value){//已付金额
				return formatNumber(value==null?0:value, '${p04005 }', 1);
			},
			nopay_money:function(value){//未付金额
				return formatNumber(value==null?0:value, '${p04005 }', 1);
			}
	}; 
    
    $(function (){
        loadDict();//加载下拉框
    	loadHead();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		if(liger.get("year").getValue() =='' || liger.get("year").getValue() == null||liger.get("month").getValue() =='' || liger.get("month").getValue() == null){
			$.ligerDialog.warn("请选择年月！");	
			return;
		}
        
		grid.options.parms.push({name : 'year',value : liger.get("year").getValue()});
    	grid.options.parms.push({name : 'month',value : liger.get("month").getValue()});
    	grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()});
    	grid.options.parms.push({name : 'end_date',value : $("#end_date").val()});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]});
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    		grid = $("#maingrid").ligerGrid({
    			columns: [{display: '供应商编码', name: 'sup_code', align: 'left', width: '120'},
    			          {display: '供应商名称', name: 'sup_name', align: 'left', width: '300'},
    			          { display: '期初', columns:
    			                [
    			                    { display: '物流', name: 'mat_begin', align: 'right', width: 130,
    			                    	render : function(rowdata, rowindex, value) {
    			    						return formatNumber(rowdata.mat_begin ==null ? 0 : rowdata.mat_begin, '${p04005 }', 1);
    			    					},formatter:'###,##0.00'
    			                    }, 
    			                    { display: '财务', name: 'acc_begin', width: 130, align: 'right',
    			                    	render : function(rowdata, rowindex, value) {
    			    						return formatNumber(rowdata.acc_begin ==null ? 0 : rowdata.acc_begin, '${p04005 }', 1);
    			    					}	,formatter:'###,##0.00'
    			                    }
    			                ]
    			          },{ display: '本期增加', columns:
  			                [
			                    { display: '物流', name: 'mat_add_this', align: 'right', width: 130, 
			                    	render : function(rowdata, rowindex, value) {
			    						return formatNumber(rowdata.mat_add_this ==null ? 0 : rowdata.mat_add_this, '${p04005 }', 1);
			    					},formatter:'###,##0.00'
			                    }
			                ]
			          	  },{ display: '本期减少', columns:
	  			                [
				                    { display: '物流', name: 'mat_mis_this', align: 'right', width: 130,
				                    	render : function(rowdata, rowindex, value) {
				    						return formatNumber(rowdata.mat_mis_this ==null ? 0 : rowdata.mat_mis_this, '${p04005 }', 1);
				    					},formatter:'###,##0.00'
				                    },
				                    { display: '财务', name: 'acc_mis_this', align: 'right', width: 130,
				                    	render : function(rowdata, rowindex, value) {
				    						return formatNumber(rowdata.acc_mis_this ==null ? 0 : rowdata.acc_mis_this, '${p04005 }', 1);
				    					},formatter:'###,##0.00'	
				                    }
				                ]
				          },{ display: '期末余额', columns:
	  			                [
				                    { display: '物流', name: 'mat_end', align: 'right', width: 130,
				                    	render : function(rowdata, rowindex, value) {
				    						return formatNumber(rowdata.mat_end ==null ? 0 : rowdata.mat_end, '${p04005 }', 1);
				    					},formatter:'###,##0.00'
				                    },
				                    { display: '财务', name: 'acc_end', align: 'right', width: 130,
				                    	render : function(rowdata, rowindex, value) {
				    						return formatNumber(rowdata.acc_end ==null ? 0 : rowdata.acc_end, '${p04005 }', 1);
				    					},formatter:'###,##0.00'
				                    }
				                ]
				          }

    		 		],
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAccountReportBillArrNonPaySum.do?isCheck=true',
    			width: '100%', height: '100%', rownumbers:true,
    			delayLoad: true,//初始化不加载，默认false
    			selectRowButtonOnly:true,//heightDiff: -10,
    			toolbar: { items: [
    				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
					{ line:true },
					{ text: '打印', id:'print', click: print, icon:'print' },
	   				{ line:true }
    			]}, 
    		});
    		
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>开票日期："+$("#begin_date").val() +" 至  "+ $("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="票到款未付总表";
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
    	          {"cell":1,"value":""+liger.get("year").getValue()+"年"+liger.get("month").getValue()+"月"}
        	]};
    	//表尾
    	var time=new Date();
    	var date=time.getFullYear()+"年"+(time.getMonth()+1)+"月"+time.getDate()+"日";
    	var foots = {
    			rows: [
    				{"cell":0,"value":"制表日期:"} ,
    				{"cell":1,"value":date} ,
    			]
    		}; 
    	var printPara={
          		title: "票到款未付总表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.matpayquery.MatAccountReportBillArrNonPayService",
       			method_name: "queryMatAccountReportBillArrNonPaySumPrint",
       			bean_name: "matAccountReportBillArrNonPayService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots:JSON.stringify(foots),//表尾打印数据,可以为空

           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);
   		
    }
	//字典下拉框
    function loadDict(){
    	//返回当前年,当前月,
		var date = getCurrentDate();
        var aa = date.split(';');
        year = aa[0];
        month = aa[1];
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		autocomplete("#year","../queryMatYear.do?isCheck=false","id","text",true,true);
        liger.get("year").setValue(year);
        liger.get("year").setText(year);
        
        autocomplete("#month","../queryMatMonth.do?isCheck=false","id","text",true,true);
        liger.get("month").setValue(month);
        liger.get("month").setText(month);
        
		//供应商
		autocomplete("#sup_code", "../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',240);
		$("#year").ligerTextBox({width:80});
		$("#month").ligerTextBox({width:80});
      	$("#sup_code").ligerTextBox({width:240});
      	autodate("#begin_date", "yyyy-mm-dd", "month_first");
		autodate("#end_date", "yyyy-mm-dd", "month_last");
	}
	
    function loadSelDate(){//年月的onchange方法
		if(year==null||year==''||month==null||month==''){
			return;
		}else{
			query_flag=0;
			var str = getMonthDate(liger.get("year").getValue() , liger.get("month").getValue());
	        var p = str.split(';');
	        $("#begin_date").val(p[0]);
	        $("#end_date").val(p[1]);
		}
	}
    
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
            <td align="right" class="l-table-edit-td" style="padding-left: 10px;">编制年月：</td>
			<td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td>
							<input name="year" type="text" id="year" requried="true" />
						</td>
						<td align="center" class="l-table-edit-td" style="width: 15px;">年</td>
						<td align="right" class="l-table-edit-td">
							<input name="month" type="text" id="month" requried="true" onchange="loadSelDate();"/>
						</td>
						<td align="center" class="l-table-edit-td" style="width: 15px;">月</td>
					</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td" >
				供应商：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td style="display: none;">
	            <input name="begin_date" type="text" id="begin_date"  ltype="text" />
	            <input name="end_date" type="text" id="end_date"  ltype="text" />
	        </td>
        </tr> 
        
    </table>
	<div id="maingrid"></div>
</body>
</html>
