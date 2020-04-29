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
    var gridManager = null;
    var userUpdateStr;

    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        
         $("#set_code").bind("change", function() {
            if (liger.get("set_code").getValue()) {
                liger.get("store_code").setValue("");
                liger.get("store_code").setText("");
                $("#store_code").ligerComboBox({ disabled: true });
            } else {
                $("#store_code").ligerComboBox({ disabled: false });
            }

        });

        $("#store_code").bind("change", function() {
            if (liger.get("store_code").getValue()) {
                liger.get("set_code").setValue("");
                liger.get("set_code").setText("");
                $("#set_code").ligerComboBox({ disabled: true });
            } else {
                $("#set_code").ligerComboBox({ disabled: false });
            }
        }); 

        $("#state").ligerComboBox({  
            data: [
                { text: '未审核', id: '1' },
                { text: '审核', id: '2' },
               /*  { text: '记账', id: '3' } */
                { text: '已付款', id: '4' }
            ], width:200,
        }); 
    
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
       	grid.options.parms.push({
			name : 'begin_date',
			value : $('#begin_date').val()
		}); 
        
       	grid.options.parms.push({
			name : 'end_date',
			value : $('#end_date').val()
		}); 
        
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]
		});
		
		grid.options.parms.push({ 
			name : 'bill_no',
			value : $('#bill_no').val()
		}); 
		grid.options.parms.push({ 
			name : 'state',
			value : liger.get("state").getValue() 
		}); 
	 	grid.options.parms.push({ name: 'set_id', value: liger.get("set_code").getValue() == null ? "" : liger.get("set_code").getValue() });
		grid.options.parms.push({ name: 'store_id', value: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0] });
    	//加载查询条件
    			grid.options.parms.push({name : 'sup_msg',value : $("#sup_msg").val()});
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
		 			display: '供货单位', name: 'sup_name', align: 'left', minWidth: '30%'
		 		},  { 
		 			display: '金额', name: 'bill_money', align: 'right', width: '15%',
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.bill_money ==null ? 0 : rowdata.bill_money, '${p04005}', 1);
					},//formatter:'###,##0.00'
		 		},  { 
		 			display: '发票号', name: 'bill_no', align: 'left', width: '40%'
		 		},  { 
		 			display: '发票张数', name: 'countnum', align: 'right', width: '10%'
		 		} 
		 		],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatPayBillDetail.do',
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
 		grid.options.lodop.title="入库汇总表";
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
    	          {"cell":0,"value":"入库日期："+liger.get("begin_date").getValue()+"至"+liger.get("end_date").getValue()},
    	          {"cell":1,"value":"库房:"},
    	          {"cell":3,"value":liger.get("store_code").getText()==''?"空":liger.get("store_code").getText().split(" ")[1]}
        	]}; 
    	//表尾  常州需求  表尾不显示制表日期
    	/*var time=new Date();
    	var date=time.getFullYear()+"年"+(time.getMonth()+1)+"月"+time.getDate()+"日";
    	 var foots = {
    			rows: [
    				{"cell":0,"value":"制表日期:"} ,
    				{"cell":1,"value":date} ,
    			]
    		};  */
    	var printPara={
          		title: "入库汇总表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.matpayquery.MatAccountReportBillArrNonPayService",
       			method_name: "queryMatPayBillDetailPrint",
       			bean_name: "matAccountReportBillArrNonPayService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			//foots:JSON.stringify(foots),//表尾打印数据,可以为空
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		 
        	officeGridPrint(printPara);
   		
    		
     }
	 
	//字典下拉框
    function loadDict(){
    	autodate("#begin_date", "yyyy-mm-dd", "month_first");
        autodate("#end_date", "yyyy-mm-dd", "month_last");
  	  	$("#begin_date").ligerTextBox({width:100});
      	$("#end_date").ligerTextBox({width:100});
		//供应商
			$("#sup_msg").ligerTextBox({});
		autocomplete("#sup_code", "../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',280);
		$("#bill_no").ligerTextBox({width:180});
	
	  autocomplete("#set_code", "../queryMatVirStore.do?isCheck=false", "id", "text", true, true, '', false, '', 200);
		 autocomplete("#store_code", "../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {is_write:1}, false, '', 200);
	}
	
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
            	开票日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:false,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:false,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        
			<td align="right" class="l-table-edit-td" width="10%">
				供应商：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td" width="10%" >
				发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;票：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="bill_no" type="text" id="bill_no" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr> 
        <tr>
          	 <td align="right" class="l-table-edit-td" style="padding-left:20px;">虚&nbsp;&nbsp;仓:</b></td>
              <td align="left" class="l-table-edit-td">
               <input name="set_code" type="text" id="set_code" ltype="text" />
             </td> 
             <td align="right" class="l-table-edit-td" style="padding-left:20px;">仓&nbsp;&nbsp;库:</b>
                        </td>
                        <td align="left" class="l-table-edit-td">
                            <input name="store_code" type="text" id="store_code" ltype="text" />
             </td>
                  <td align="right" class="l-table-edit-td"  width="10%">
				供应商信息：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_msg" type="text" id="sup_msg" ltype="text" validate="{required:false}" />
            </td>
        </tr>
        <tr>
          <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
           <td align="left" class="l-table-edit-td" >
            <input name="state" type="text" id="state" ltype="text"/>
           </td> 
        </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
