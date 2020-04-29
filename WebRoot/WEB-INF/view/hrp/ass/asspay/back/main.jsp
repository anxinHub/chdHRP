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
        $("#pay_no").ligerTextBox({width:160});
        $("#pay_date_beg").ligerTextBox({width:90});
        $("#pay_date_end").ligerTextBox({width:90});
        $("#ven_id").ligerTextBox({width:160});
        $("#state").ligerComboBox({width:160});
        $("#create_emp").ligerTextBox({width:160});
        $("#audit_emp").ligerTextBox({width:160});
        $("#audit_date_beg").ligerTextBox({width:90});
        $("#audit_date_end").ligerTextBox({width:90});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'pay_no',value:$("#pay_no").val()}); 
    	  grid.options.parms.push({name:'ass_nature',value:liger.get("ass_nature").getValue().split("@")[0]});
    	  grid.options.parms.push({name:'pay_date_beg',value:$("#pay_date_beg").val()}); 
    	  grid.options.parms.push({name:'pay_date_end',value:$("#pay_date_end").val()}); 
    	  grid.options.parms.push({name:'ven_id',value:liger.get("ven_id").getValue().split("@")[0]}); 
    	  grid.options.parms.push({name:'ven_no',value:liger.get("ven_id").getValue().split("@")[1]}); 
    	  grid.options.parms.push({name:'audit_date_beg',value:$("#audit_date_beg").val()}); 
    	  grid.options.parms.push({name:'audit_date_end',value:$("#audit_date_end").val()}); 
    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue() }); 
    	  grid.options.parms.push({name:'create_emp',value:liger.get("create_emp").getValue()}); 
    	  grid.options.parms.push({name:'audit_emp',value:liger.get("audit_emp").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '退款单号', name: 'pay_no', align: 'left', frozen: true,width : 120,
						render : function(rowdata, rowindex,
								value) {
							if(rowdata.note == "合计"){
								return '';
							}
							return "<a href=javascript:openUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
							+ "|" + rowdata.copy_code + "|"
							+ rowdata.pay_no  +"')>"+rowdata.pay_no+"</a>";
						}
					 		},
					 { display: '摘要', name: 'note', align: 'left', frozen: true,width : 120,
					 			render : function(rowdata, rowindex,
										value) {
									var note = "";
									if(value != null){
										note = value;
									}
									//if (rowdata.is_import  == 1) {
									if (value  == "引入退款发票登记") {
										return "<a href=javascript:openViewPay('"
										+ rowdata.group_id
										+ "|"
										+ rowdata.hos_id
										+ "|"
										+ rowdata.copy_code
										+ "|"
										+ rowdata.pay_no
										+ "|"
										+ rowdata.bill_no
										+ "')>"
										+ note
										+ "</a>";
									 } else{
										return rowdata.note;
									}
									}	
					 			
					 		},
					 { display: '供应商', name: 'ven_name', align: 'left', frozen: true,width : 220
					 		},		
					 { display: '仓库', name: 'store_name', align: 'left', frozen: true,width : 220
					 		},			
                     { display: '退款日期', name: 'pay_date', align: 'left',width : 120
					 		},
                     { display: '发票金额', name: 'pay_money', align: 'right',width : 120,
								render: function(item)
					            {
					                    return formatNumber(item.pay_money,'${ass_05005}',1);
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
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssBackPayMain.do',delayLoad :true,
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,checkBoxDisplay : isCheckDisplay,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    	{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	{ line:true },
				    	{ text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
				    	{ line:true },
				    	{ text: '退款确认（<u>G</u>）', id:'confirm', click: confirm,icon:'ok' }
				    	,{ line:true },{
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
								rowdata.pay_no 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function confirm(){
    	var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.pay_no);

					});
			$.ligerDialog.confirm('确定退款?', function(yes) {

				if (yes) {
					ajaxJsonObjectByUrl("updateConfirmAssBackPay.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
    }
    
    
  //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${hos_name}</td></tr>";
 		head=head+"<tr><td>制单日期："+$("#create_date_start").val() +" 至  "+ $("#create_date_end").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="资产退款单";
    }
	
	 //打印模板设置
	  
	  function printSet(){
		  
		  var useId=0;//统一打印
			if('${ass_05088}'==1){
				//按用户打印
				useId='${user_id }';
			}
			
	//POBrowser.openWindow('../../../../PageOffice/printFormSet.do?isCheck=false&template_code=01001&userid=${user_id}', 'fullscreen=yes');
	officeFormTemplate({template_code:"05088",use_id:useId});
	}
	
	//打印
	    function printDate(){
		    	
		    	 var useId=0;//统一打印
		 		if('${ass_05088}'==1){
		 			//按用户打印
		 			useId='${user_id }';
		 		}
		 		var data = gridManager.getCheckedRows();
				if (data.length == 0){
					$.ligerDialog.error('请选择行');
				}else{
					
					var pay_no ="" ;
					/* var ASS_NO = "";  */
					$(data).each(function (){		
						/*  if(this.state != 2){
							 ASS_NO = ASS_NO + this.ASS_NO + "<br>";
						} */
						 
						pay_no += "'"+this.pay_no+"',"
						 
							
					});
				
		    	var para={
		    		
		       
		    			template_code:'05088',
		    			class_name:"com.chd.hrp.ass.serviceImpl.pay.back.AssBackPayMainServiceImpl",
		    			method_name:"queryAssBackPayDY",
						
		    			paraId :pay_no.substring(0,pay_no.length-1),
		    			isPrintCount:false,//更新打印次数
		    			isPreview:true,//预览窗口，传绝对路径
		    			use_id:useId,
		    			p_num:1
		    	};
		    	//officeFormPrint(para);
		    	ajaxJsonObjectByUrl("queryAssBackPayState.do?isCheck=false",{paraId:pay_no.substring(0,pay_no.length-1),state:2},function(responseData){
					if (responseData.state == "true") {
						officeFormPrint(para);
					}
				});
	}
	}
    function isCheckDisplay(rowdata) {
		if (rowdata.note == "合计")
			return false;
		return true;
	}
    
    function add_open(){
    	
    	parent.$.ligerDialog.open({
			title: '资产退款添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asspay/back/assBackPayMainAddPage.do?isCheck=false&',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
    	}
    	
    
 	function openViewPay(obj){
   		var vo = obj.split("|");
   		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
   				+ "copy_code=" + vo[2] + "&" + "pay_no=" + vo[3] ;

   		parent.$.ligerDialog.open({
   			title: '资产付款发票查看',
   			height: $(window).height(),
   			width: $(window).width(),
   			url: 'hrp/ass/asspay/back/assViewPayPage.do?isCheck=false&'+parm,
   			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
   			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
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
							this.pay_no 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssBackPayMain.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
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
			"pay_no="+vo[3] 

		parent.$.ligerDialog.open({
			title: '资产退款修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asspay/back/assBackPayMainUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    }
    function loadDict(){
		var param = {query_key:''};
    	
		autocomplete("#create_emp", "../../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true, param, true);
    	
		autocomplete("#audit_emp", "../../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true, param, true);
		
		autocomplete("#ven_id", "../../queryHosSupDictNo.do?isCheck=false","id", "text",true,true,param,true,null,"350");
		
		$('#state').ligerComboBox({
			data:[{id:2,text:'确认'},{id:1,text:'审核'},{id:0,text:'新建'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true
		});
		autodate("#pay_date_beg","YYYY-mm-dd","month_first");

		autodate("#pay_date_end","YYYY-mm-dd","month_last");
		
		autocomplete("#ass_nature", "../../queryAssNaturs.do?isCheck=false","id", "text",true,true,null,false,null,"160");
         }  
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">退款日期：</td>
            <td align="left" class="l-table-edit-td" width="5%"><input name="pay_date_beg" type="text" id="pay_date_beg"  class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left" width="2%">至：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_date_end" type="text" id="pay_date_end"  class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'pay_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">退款单号：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_no" type="text" id="pay_no"   /></td>
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核人：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_emp" type="text" id="audit_emp"   /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
  			 <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature"   /></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
