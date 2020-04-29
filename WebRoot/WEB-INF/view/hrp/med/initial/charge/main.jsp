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
    var renderFunc = {
			 
			amount_money:function(value){//金额
				return formatNumber(value, '${p08005 }', 1);
			} ,
			status:function(value){//是否记账
				if(value==1){
					return "是";
				}else if (value==0){
					return "否";
				} 
			} 
    
	}; 
    $(function (){
    	$("#layout1").ligerLayout({ centerBottomHeight : 350});
  
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		//loadHotkeys();
		loadForm();
    });

    function loadForm(){
	    $.metadata.setType("attr", "validate");
	     var v = $("form").validate({
	         errorPlacement: function (lable, element){
	             if (element.hasClass("l-textarea")){
	                 element.ligerTip({ content: lable.html(), target: element[0] }); 
	             }else if (element.hasClass("l-text-field")){
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }else{
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable){
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function (){
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
		$("form").ligerForm();
    }       

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [{ 
        	   display: '仓库编码', name: 'store_code', align: 'left'
			},{ 
				display: '仓库名称', name: 'store_name', align: 'left'
			},{ 
				display: '是否记账', name: 'status', align: 'left', 
				render : function(rowdata, rowindex, value) {
					return rowdata.status == 1 ? "是" : "否";
				}
			},{ 
				display: '操作时间', name: 'acc_date', align: 'left'
			}],
			dataAction: 'server',dataType: 'server',usePager:false,url:'queryMedInitCharge.do',
			delayLoad: false,width: '98%', height: '50%', checkbox: false,rownumbers:false,
			selectRowButtonOnly:false,//heightDiff: -10,
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function save(){
		if($("form").valid()){
    		var formPara ={
				acc_date : $("#acc_date").val(),
				store_id : liger.get("store_code").getValue() ==null?"":liger.get("store_code").getValue().split(",")[0]
			};
			ajaxJsonObjectByUrl("addMedInitCharge.do",formPara,function (responseData){
        		if(responseData.state=="true"){
        			grid.loadData();
        		}
        	});
		}
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>记账日期："+$("#acc_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="期初记账";
    }
    
   
    function loadDict(){
    	autocomplete("#store_code", "../../queryMedStore.do?isCheck=false", "id", "text", true, true);
    	
    	$("#acc_date").ligerTextBox({width:180});
    	autodate("#acc_date");
		$("#save").ligerButton({click: save, width:90});
	}  
    //键盘事件
	function loadHotkeys() {
		hotkeys('J', save);
	 } 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout1" style="height: 100%;width: 100%" >
		<div position="center" title="记账">
			<div id="toptoolbar" ></div>
			<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" style="width:80%" class="l-table-edit">
					<tr>
						<td align="right" class="l-table-edit-td" width="40%">
							<span style="color: red">*</span>记账日期：
						</td>
						<td align="left" class="l-table-edit-td" width="40%">
							<input class="Wdate" name="acc_date" id="acc_date" type="text" required="true" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td align="right" class="l-table-edit-td" >
							仓库：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false,maxlength:100}" />
						</td>
					</tr>
					<tr>
						<td align="center" class="l-table-edit-td" >
						</td>
						<td align="left" class="l-table-edit-td">
							<span style="color: red">仓库为空则对所有仓库进行记账</span>
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td align="center" class="l-table-edit-td" >
						</td>
						<td align="left" class="l-table-edit-td" >
							<button id ="save" type="button" accessKey="J"><b>记账（<u>J</u>）</b></button>
						</td>
					</tr>
				</table>
			</form>
		</div>  
		<div position="centerbottom" title="仓库记账详情">
			<div id="maingrid" style="margin-top:10px;margin-left: 10px;"></div>
		</div>
    </div>  
</body>
</html>
