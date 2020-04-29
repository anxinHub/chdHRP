<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
    var dataFormat;
    var editor;
    $(function (){
        loadDict();
        loadForm();
        loadHead();
        if ('${state}' != 0) {//置黑不能点击
			toobarmanage = gridManager.toolbarManager;
			toobarmanage.setDisabled('save');
			
		}
		$("#ven_id").change(function(){
 			
 			var ven_id = liger.get("ven_id").getValue().split("@")[0];
 			var ven_no = liger.get("ven_id").getValue().split("@")[1];

 			if (ven_no == null || ven_id == null || ven_id == ""
 					|| ven_no == "") {
 				ven_no = "";
 				ven_id = "";
 			}
 			
 			editor.grid.url = '../../queryAssNoDictTable.do?isCheck=false&ven_id='+ven_id+'&ven_no='+ven_no;
 		});
    });  
     
    function  save(){
   	 gridManager.endEdit();
  	 	var data = gridManager.getData();
  	 	console.log(data)
		var num = 0;
		for (var i = 0; i < data.length; i++) {

			if (data[i].ass_id) {
				num++;
			}
		}
		if (!num) {
			$.ligerDialog.error('明细数据不能为空');
			return false;
		}
       var formPara={
           
          bill_no:$("#bill_no").val() == "" ? '0' : $("#bill_no").val(),
           
          invoice_no:$("#invoice_no").val(),
           
          bill_date:$("#bill_date").val(),
           
          ven_id:liger.get("ven_id").getValue().split("@")[0],
          
          ven_no:liger.get("ven_id").getValue().split("@")[1],
           
          pact_code:liger.get("pact_code").getValue(),
           
          bill_money:$("#bill_money").val(),
          
          ParamVo : JSON.stringify(data)
        };
       
       if(validateGrid()){
    	   ajaxJsonObjectByUrl("saveAssBackPreBillMain.do",formPara,function(responseData){
               
               if(responseData.state=="true"){
               	 parentFrameUse().query();
    				 $("#bill_no").val(responseData.bill_no);
               }
           });
       }
      
   }
    function loadHead(){
    	var ven_id = liger.get("ven_id").getValue().split("@")[0];
		var ven_no = liger.get("ven_id").getValue().split("@")[1];

		if (ven_no == null || ven_id == null || ven_id == ""
				|| ven_no == "") {
			ven_no = "";
			ven_id = "";
		}
		
		editor = {
				type : 'select',
				valueField : 'ass_id_no',
				textField : 'ass_name',
				selectBoxWidth : 800,
				selectBoxHeight : 240,
				grid : {
					columns : [ {
						display : '资产编码',
						name : 'ass_code',
						align : 'left'
					}, {
						display : '资产名称',
						name : 'ass_name',
						align : 'left'
					}, {
						display : '资产分类',
						name : 'ass_type_name',
						align : 'left'
					}, {
						display : '规格',
						name : 'ass_spec',
						align : 'left'
					}, {
						display : '型号',
						name : 'ass_model',
						align : 'left'
					}, {
						display : '品牌',
						name : 'ass_brand',
						align : 'left'
					},{
						display : '计量单位',
						name : 'ass_unit_name',
						align : 'left'
					},{
						display : '资产ID',
						name : 'ass_id',
						hide : true
					}, {
						display : '资产性质',
						name : 'naturs_name',
						hide : true
					}  ],
					switchPageSizeApplyComboBox : false,
					onSelectRow : f_onSelectRow_detail,
					url : '../../queryAssNoDictTable.do?isCheck=false&ven_id='+ven_id+'&ven_no='+ven_no,
					pageSize : 30
				},
				keySupport : true,
				alwayShowInDown : true,
				keySupport : true,
				autocomplete : true,
				onSuccess : function() {
					this.parent("tr").next(
							".l-grid-row").find(
							"td:first").focus();
				}
			};
    	
    	
    	grid = $("#maingrid").ligerGrid({
           columns: [  
						{display: '资产性质', 
							name: 'naturs_code', 
							textField : 'naturs_name',
							align: 'left',
							totalSummary:
		                    {
		                        render: function (suminf, column, cell)
		                        {
		                            return '<div>合计</div>';
		                        },
		                        align: 'center'
		                    }
						},{ display: '资产变更号', 
							name: 'ass_no', 
							align: 'left',
							hide :true
							},{
							display : '资产名称',
							name : 'ass_id',
							align : 'left',
							textField : 'ass_name',
							editor : editor,
							render : function(rowdata, rowindex,
									value) {
								return rowdata.ass_name;
							}
		                    
						},
                     { display: '开票金额', name: 'bill_money', align: 'right',
							render : function(rowdata, rowindex,
									value) {
								 return formatNumber(
											rowdata.bill_money == null ? 0
													: rowdata.bill_money,
											'${ass_05005}',
											1);
							},editor:{
							type : 'float',
							onChanged: function (){
								$("#bill_money").val(grid.getTotalInfoSum('bill_money')); 
							}
						},
						totalSummary:
			                     {
							render: function (suminf, column, cell)
			                   {
			                   return '<div>' + formatNumber(suminf.sum,'${ass_05005}',1) + '</div>';
			               },
			               align: 'right'
			                     }
				 		},
                     { display: '说明', name: 'note', align: 'left',editor : {
							type : 'text'
						}
					 	}
                     ],
                     dataAction: 'server',
                     dataType: 'server',
                     usePager:true,
                     width: '100%', 
                     height: '100%', 
                     onBeforeEdit : f_onBeforeEdit,
                     url : 'queryAssBackPreBilldetail.do?isCheck=false&bill_no=${bill_no}',
                     checkbox: true,
                     enabledEdit : true,
                     rownumbers:true,
                     selectRowButtonOnly:true ,
                     toolbar: { items: [
		    				  { text: '保存', id:'save', click: saveAssBackPreBillMain, icon:'save' },
		    				  { line:true },
				              { text: '关闭', id:'exit', click: exit,icon:'close' },
		    				  { line:true},
				              { text : '批量打印',
				            	 id : 'print',
								click : printDate,
								icon : 'print'
				              },
				              { line:true},
				              {
									text : '打印模板设置',
									id : 'printSet',
									click : printSet,
									icon : 'print'
								}
		    				  ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
        is_addRow() ;
    }
    
    function exit(){
    	frameElement.dialog.close();
    }
    function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("form").validate({
         errorPlacement: function (lable, element)
         {
             if (element.hasClass("l-textarea"))
             {
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }
             else if (element.hasClass("l-text-field"))
             {
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }
             else
             {
                 lable.appendTo(element.parents("td:first").next("td"));
             }
         },
         success: function (lable)
         {
             lable.ligerHideTip();
             lable.remove();
         },
         submitHandler: function ()
         {
             $("form .l-text,.l-textarea").ligerHideTip();
         }
     });
     $("form").ligerForm();
    }       
    function validateGrid() {
		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行

		$.each(data, function(i, v) {
			if (!isnull(v.naturs_code)) {
				var key = v.naturs_code + "|" +v.ass_id;
				var value = "第" + (i + 1) + "行";

				if (v.ass_id == '@' || isnull(v.ass_id)) {
					msg += "[卡片号]、";
				}
				if (msg != "") {
					msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
				}
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msg = targetMap.get(key) + "与" + value + "重复！\n\r",
					value;
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",
							value);
				}
			}
		});
		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		return true;
	}
    function saveAssBackPreBillMain(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
		autocomplete("#pact_code", "../../queryContractMain.do?isCheck=false","id", "text",true,true,'',false,'${pact_code}');
		
		autocomplete("#ven_id", "../../queryHosSupDict.do?isCheck=false","id", "text",true,true,null,false,'${ven_id}','300');

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
		 			useId='${user_id}';
		 		}
		 		/*var data = gridManager.getCheckedRows();
				if (data.length == 0){
					$.ligerDialog.error('请选择行');
				}else{*/
					
					var bill_no ="" ;
					/*  var ASS_NO = "";  */ 
					/* $(data).each(function (){		
						 if(this.state != 2){
							 ASS_NO = ASS_NO + this.ASS_NO + "<br>";
						}  */
						 
						 bill_no += "'"+this.bill_no+"',"
						 
							
					/*});
				}*/
		    	var para={
		    		
		       
		    			template_code:'05084',
		    			class_name:"com.chd.hrp.ass.serviceImpl.bill.AssBackPreBillMainServiceImpl",
		    			method_name:"queryAssBackPreBillDY",
		    			 bill_no:$("#bill_no").val(), 
		    			  paraId :bill_no.substring(0,bill_no.length-1), 
		    			isPrintCount:false,//更新打印次数
		    			isPreview:false,//预览窗口，传绝对路径
		    			use_id:useId,
		    			p_num:0
		    	};
		    	officeFormPrint(para);
	}
	
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
	  
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票流水号：</td>
            <td align="left" class="l-table-edit-td"><input name="bill_no" type="text" id="bill_no" ltype="text" value="${bill_no}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font
					color="red">*</font></b>发票号：</td>
            <td align="left" class="l-table-edit-td"><input name="invoice_no" type="text" id="invoice_no" ltype="text" value="${invoice_no}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font
					color="red">*</font></b>开票日期：</td>
            <td align="left" class="l-table-edit-td">
            <input name="bill_date" type="text" id="bill_date" ltype="text" value="${bill_date}" validate="{required:true,maxlength:20}" 
            class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font
					color="red">*</font></b>供应商编码：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id" ltype="text"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同：</td>
            <td align="left" class="l-table-edit-td"><input name="pact_code" type="text" id="pact_code" ltype="text" value="${pact_code}"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票金额：</td>
            <td align="left" class="l-table-edit-td"><input name="bill_money" type="text" id="bill_money" ltype="text" value="${bill_money}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        </table>
        <div id="maingrid"></div>
    </form>
    </body>
</html>
