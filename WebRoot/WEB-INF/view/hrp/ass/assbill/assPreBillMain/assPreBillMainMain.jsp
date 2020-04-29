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
		 //loadHotkeys();
        $("#bill_no").ligerTextBox({width:160});
        $("#invoice_no").ligerTextBox({width:160});
        $("#bill_date").ligerTextBox({width:160});
        $("#ven_id").ligerTextBox({width:160});
        $("#pact_code").ligerTextBox({width:160});
        $("#bill_money").ligerTextBox({width:160});
        $("#state").ligerTextBox({width:160});
        $("#create_emp").ligerTextBox({width:160});
        $("#create_date").ligerTextBox({width:160});
        $("#audit_emp").ligerTextBox({width:160});
        $("#audit_date").ligerTextBox({width:160});
        $("#note").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'bill_no',value:$("#bill_no").val()}); 
    	  grid.options.parms.push({name:'invoice_no',value:liger.get("invoice_no").getValue()}); 
    	  grid.options.parms.push({name:'begin_bill_date',value:$("#begin_bill_date").val()}); 
    	  grid.options.parms.push({name:'end_bill_date',value:$("#end_bill_date").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '发票流水号', name: 'bill_no', align: 'left',
                    	 render : function(rowdata, rowindex, value) {
                    		 return "<a href=javascript:openUpdate('"
								+ rowdata.group_id + "|"
								+ rowdata.hos_id + "|"
								+ rowdata.copy_code + "|"
								+ rowdata.bill_no + "')>"
								+ value + "</a>"
                     	 }
					 },
                     { display: '发票号', name: 'invoice_no', align: 'left'
					 		},
                     { display: '开票日期', name: 'bill_date', align: 'left'
					 		},
                     { display: '供应商编码', name: 'ven_id', align: 'left',textField : 'sup_name'
					 		},
                     { display: '合同ID', name: 'pact_code', align: 'left',textField : 'contract_no'
					 		},
                     { display: '发票金额', name: 'bill_money', align: 'right',
								render : function(rowdata, rowindex,
										value) {
									 return formatNumber(
												rowdata.bill_money == null ? 0
														: rowdata.bill_money,
												'${ass_05005}',
												1);
								},
					 		},
                     { display: '状态', name: 'state', align: 'left',
					 			render : function(rowdata, rowindex, value) {
					 				return value == 0 || value== null ? '新建' : '已审核';
					 			}
                     
					 },
                     { display: '制单人', name: 'create_emp', align: 'left',textField : 'create_name'
					 		},
                     { display: '制单时间', name: 'create_date', align: 'left'
					 		},
                     { display: '审核人', name: 'audit_emp', align: 'left',textField : 'audit_name'
					 		},
                     { display: '审核时间', name: 'audit_date', align: 'left'
					 		},
                     { display: '备注', name: 'note', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssPreBillMain.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
				    	                { line:true },
						                { text: '审核', id:'check', click:check,icon:'ok' }
				    	                ,{ line:true },
				    	                {text: '销审（<u>X</u>）',id: 'notToExamine',click: notToExamine,icon: 'right'},  
										{line: true},
										{
											text : '批量打印（<u>P</u>）',
											id : 'print',
											click : printDate,
											icon : 'print'
										},{ 
											line:true 
											},
										{
												text: '模板设置',
												id:'printSet', 
												click: printSet, 
												icon:'print' }	
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
    function check(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
				ParamVo.push(
				this.group_id   +"@"+ 
				this.hos_id   +"@"+ 
				this.copy_code   +"@"+ 
				this.bill_no 
				) });
    	$.ligerDialog.confirm('确定审核?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("checkAssPreBillMain.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
    }
}
    function add_open(){
    	
					parent.$.ligerDialog.open({
						title: '付款发票登记添加',
						height: $(window).height(),
						width: $(window).width(),
						url: 'hrp/ass/bill/assprebillmain/assPreBillMainAddPage.do?isCheck=false&',
						modal: true, 
						showToggle: false, 
						showMax: true, 
						showMin: false, 
						isResize: true,
						parentframename: window.name
					}); 
    	}
    	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.bill_no 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssPreBillMain.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    function imp(){
    	
    	var index = layer.open({
					type : 2,
					title : '导入',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'assPreBillMainImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"bill_no="+vo[3] 
		 
		 
		parent.$.ligerDialog.open({ 
			url : 'hrp/ass/bill/assprebillmain/assPreBillMainUpdatePage.do?isCheck=false&parm='+parm, 
					height: $(window).height(),
					width: $(window).width(), 
					title:'付款发票登记修改',
					modal:true,
					showToggle:false,
					showMax:true,
					showMin: false,
					isResize:true,
					parentframename: window.name
    	}); 
		 

		}
    

    /*var index = layer.open({
					type : 2,
					title : '更新',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'assPreBillMainUpdatePage.do?isCheck=false&' + parm
				});
				layer.full(index);	
    */
	function notToExamine(){
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.bill_no  );
					});
			$.ligerDialog.confirm('确定销审?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("updateNotToExamineAssPreBillMain.do?isCheck=false", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		} }
    
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${hos_name}</td></tr>";
 		head=head+"<tr><td>制单日期："+$("#create_date_start").val() +" 至  "+ $("#create_date_end").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="预付款发票";
    }
	
	 //打印模板设置
	  
	  function printSet(){
		  
		  var useId=0;//统一打印
			if('${ass_05084}'==1){
				//按用户打印
				useId='${user_id }';
			}
			
	//POBrowser.openWindow('../../../../PageOffice/printFormSet.do?isCheck=false&template_code=01001&userid=${user_id}', 'fullscreen=yes');
	officeFormTemplate({template_code:"05084",use_id:useId});
	}
	
	//打印
	    function printDate(){
		    	
		    	 var useId=0;//统一打印
		 		if('${ass_05084}'==1){
		 			//按用户打印
		 			useId='${user_id }';
		 		}
		 		var data = gridManager.getCheckedRows();
				if (data.length == 0){
					$.ligerDialog.error('请选择行');
				}else{
					
					var bill_no ="" ;
					/* var ASS_NO = "";  */
					$(data).each(function (){		
						/*  if(this.state != 2){
							 ASS_NO = ASS_NO + this.ASS_NO + "<br>";
						} */
						 
						 bill_no += "'"+this.bill_no+"',"
						 
							
					});
				
		    	var para={
		    		
		       
		    			template_code:'05084',
		    			class_name:"com.chd.hrp.ass.serviceImpl.bill.AssPreBillMainServiceImpl",
		    			method_name:"queryAssPreBillDY",
						
		    			paraId :bill_no.substring(0,bill_no.length-1),
		    			isPrintCount:false,//更新打印次数
		    			isPreview:true,//预览窗口，传绝对路径
		    			use_id:useId,
		    			p_num:1
		    	};
		    	//officeFormPrint(para);
		    	ajaxJsonObjectByUrl("queryAssPreBillState.do?isCheck=false",{paraId:bill_no.substring(0,bill_no.length-1),state:2},function(responseData){
					if (responseData.state == "true") {
						officeFormPrint(para);
					}
				});
				}
	}
    
    
	    function loadDict(){
	        //字典下拉框
		autocomplete("#pact_code", "../../queryContractMain.do?isCheck=false","id", "text",true,true,'',false,null);
		
		autocomplete("#ven_id", "../../queryHosSupDict.do?isCheck=false","id", "text",true,true,null,false,null);
			$("#bill_no").ligerTextBox({width:160});
	        $("#invoice_no").ligerTextBox({width:160});
	        $("#begin_bill_date").ligerTextBox({width:90});
	        $("#end_bill_date").ligerTextBox({width:90});
	        autodate("#begin_bill_date","YYYY-mm-dd","month_first");

			autodate("#end_bill_date","YYYY-mm-dd","month_last");
			
		
	 } 
	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
	}
	function f_onSelectRow_detail(data, rowindex, rowobj) {
	
		selectData = "";
		selectData = data;
		console.log(data)
		if (column_name == "ass_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					naturs_code : data.naturs_code,
					naturs_name: data.naturs_name,
					ass_no : data.ass_id_no.split("@")[1],
					ass_name : data.ass_name
					
				});
	
			}
		}
		return true;
	}
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">开票日期：</td>
            <td align="left" class="l-table-edit-td" width="5%">
            <input name="begin_bill_date" type="text" id="begin_bill_date" ltype="text" 
            validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left" width="2%">至：</td>
            <td align="left" class="l-table-edit-td">
            <input name="end_bill_date" type="text" id="end_bill_date" ltype="text" 
            validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票流水号：</td>
            <td align="left" class="l-table-edit-td"><input name="bill_no" type="text" id="bill_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票号：</td>
            <td align="left" class="l-table-edit-td"><input name="invoice_no" type="text" id="invoice_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
