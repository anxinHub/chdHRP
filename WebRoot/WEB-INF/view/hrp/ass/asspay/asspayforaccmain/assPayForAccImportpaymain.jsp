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
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        $("#invoice_no").ligerTextBox({width:160});
        $("#bill_date_beg").ligerTextBox({width:90});
        $("#bill_date_end").ligerTextBox({width:90});
        $("#ven_id").ligerTextBox({
			width : 160,
			 disabled : true,
			cancelable : false 
		});
        $("#pact_code").ligerComboBox({width:160});
        $("#state").ligerComboBox({width:160});
        $("#create_emp").ligerTextBox({width:160});
        $("#audit_emp").ligerTextBox({width:160});
        $("#audit_date_beg").ligerTextBox({width:90});
        $("#audit_date_end").ligerTextBox({width:90});
        query();
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'invoice_no',value:$("#invoice_no").val()}); 
    	  grid.options.parms.push({name:'bill_date_beg',value:$("#bill_date_beg").val()}); 
    	  grid.options.parms.push({name:'bill_date_end',value:$("#bill_date_end").val()}); 
    	  grid.options.parms.push({name:'ven_id',value:liger.get("ven_id").getValue().split("@")[0]}); 
    	  grid.options.parms.push({name:'ven_no',value:liger.get("ven_id").getValue().split("@")[1]}); 
    	  grid.options.parms.push({name:'audit_date_beg',value:$("#audit_date_beg").val()}); 
    	  grid.options.parms.push({name:'audit_date_end',value:$("#audit_date_end").val()}); 
    	  grid.options.parms.push({name:'pact_code',value:liger.get("pact_code").getValue()}); 
    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue() }); 
    	  grid.options.parms.push({name:'create_emp',value:liger.get("create_emp").getValue()}); 
    	  grid.options.parms.push({name:'audit_emp',value:liger.get("audit_emp").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '发票流水号', name: 'bill_no', align: 'left', frozen: true,width : 120,
						render : function(rowdata, rowindex,
								value) {
							if(rowdata.note == "合计"){
								return '';
							}
							return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
							+ "|" + rowdata.copy_code + "|"
							+ rowdata.bill_no  +"')>"+rowdata.bill_no+"</a>";
						}
					 		},
					 { display: '摘要', name: 'note', align: 'left', frozen: true,width : 120
					 		},
					 { display: '供应商', name: 'ven_name', align: 'left', frozen: true,width : 220
					 		},		
                     { display: '发票号', name: 'invoice_no', align: 'left', frozen: true,width : 120
					 		},
                     { display: '开票日期', name: 'bill_date', align: 'left',width : 120
					 		},
                     { display: '合同', name: 'pact_code', align: 'left',width : 120
					 		},
                     { display: '发票金额', name: 'bill_money', align: 'right',width : 120,
								render: function(item)
					            {
					                    return formatNumber(item.bill_money,'${ass_05005}',1);
					            }
					 		},
                     { display: '制单人', name: 'create_emp_name', align: 'left',width : 120
					 		},
                     { display: '制单时间', name: 'create_date', align: 'left',width : 120
					 		},
                     { display: '确认人', name: 'audit_emp_name', align: 'left',width : 120
					 		},
                     { display: '确认时间', name: 'audit_date', align: 'left',width : 120
					 		},
					 { display: '状态', name: 'state_name', align: 'left',width : 120
						 	}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssBillMain.do',delayLoad :true,
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,checkBoxDisplay : isCheckDisplay,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    	{ text: '生成付款单（<u>A</u>）', id:'save', click: save, icon:'add' },
				    	, {
							line : true
						}, {
							text : '关闭',
							id : 'close',
							click : this_close,
							icon : 'candle'
						} 
				    ]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.bill_no 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
   
    
    function isCheckDisplay(rowdata) {
		if (rowdata.note == "合计")
			return false;
		return true;
	}
    
    function save(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var bill_nos = [];
			$.each(data,function(){
				bill_nos.push("'"+this.bill_no+"'");
			});
			$.post("initAssBill.do?isCheck=false",
					{
					 'bill_nos':bill_nos.toString(),
					 'ven_id' : '${ven_id}',
					 'ven_no' : '${ven_no}',
					 'note':'${note}',
					 'pay_date' : '${pay_date}'
					 },
					 function(data){
						parent.parentFrameUse().query();
						parent.parentFrameUse().openUpdate(data.update_para);
						parent.this_close();
						this_close();
			},"json");
			
		}
    
    	}
    	
    
    function this_close() {
		frameElement.dialog.close();
	}
    
	
   
  
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			" copy_code="+vo[2]   +"&"+ 
			"bill_no="+vo[3] 

		parent.$.ligerDialog.open({
			title: '付款发票登记修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'assBillMainUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    }
    function loadDict(){
		var param = {query_key:''};
    	
		autocomplete("#create_emp", "../../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true, param, true);
    	
		autocomplete("#audit_emp", "../../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true, param, true);
		
		autocomplete("#ven_id", "../../queryHosSupDict.do?isCheck=false", "id","text", true, true, null, false, null, "160"); 
		
		liger.get("ven_id").setValue("${ven_id}@${ven_no}");
		liger.get("ven_id").setText("${ven_code} ${ven_name}"); 
		
		autocomplete("#pact_code", "../../queryContractMain.do?isCheck=false","id", "text",true,true,param,true,null,"200");
		$('#state').ligerComboBox({
			data:[{id:2,text:'确认'},{id:1,text:'审核'},{id:0,text:'新建'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true
		})  ;
		autodate("#bill_date_beg","YYYY-mm-dd","month_first");

		autodate("#bill_date_end","YYYY-mm-dd","month_last");
		
         }  
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">开票日期：</td>
            <td align="left" class="l-table-edit-td" width="5%"><input name="bill_date_beg" type="text" id="bill_date_beg"  class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left" width="2%">至：</td>
            <td align="left" class="l-table-edit-td"><input name="bill_date_end" type="text" id="bill_date_end"  class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'bill_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票号：</td>
            <td align="left" class="l-table-edit-td"><input name="invoice_no" type="text" id="invoice_no"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人：</td>
            <td align="left" class="l-table-edit-td"><input name="create_emp" type="text" id="create_emp"   /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核时间：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_date_beg" type="text" id="audit_date_beg"  class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
            <td align="left">至：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_date_end" type="text" id="audit_date_end"  class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'audit_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td">
           <!--  <select id="state" name="state">
            		<option value="">全部</option>
            		<option value="0">新建</option>
            		<option value="1">审核</option>
            		<option value="2">确认</option>
            	</select> -->
            	<input  name="state" type="text" id="state"/>
            	</td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同：</td>
            <td align="left" class="l-table-edit-td"><input name="pact_code" type="text" id="pact_code"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核人：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_emp" type="text" id="audit_emp"   /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
