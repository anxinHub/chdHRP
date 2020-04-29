<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var charge_type;
    
    $(function (){
    	$("#layout1").ligerLayout({ 
			leftWidth : 255,
			onLeftToggle: function(){			//每展开左边树即刷新一次表格，以免结构混乱
				grid._onResize()
			},
			onEndResize: function(a, b){    //每调整左边树宽度大小即刷新一次表格，以免结构混乱
				// if(b.target == $(".l-layout-dragging-yline")[0])   //触发此事件时先判断一下触发目标，否则触发滚动条时也会执行
					grid._onResize()
            }
		});
        $("#left").height($("#layout1").height() - 25);

        loadTree();
        loadDict()//加载下拉框
    	//加载数据
		loadHotkeys();
    });

    //加载树形菜单
    function loadTree(){
        ajaxJsonObjectByUrl("queryMatDuraInitChargeTree.do?isCheck=false", {},
            function(responseData) {
                if (responseData != null) {
                    $("#tree").ligerTree({
                        data : responseData.Rows,
                        parentIcon: null,
                        childIcon: null ,
                        checkbox : false,
                        idFieldName : 'code',
                        parentIDFieldName : 'super_code',
                        textFieldName : 'name',
                        onSelect: onSelect,
                        isExpand: false,
                        nodeWidth: 180,
                    });
            $("#tree").css({"width":"100%"});
                    treeManager = $("#tree").ligerGetTreeManager();
                    treeManager.collapseAll(); //全部收起
                }
            }
        );
    }
    
    /* 设置树形菜单 */
    function onSelect(note){
    	//alert(JSON.stringify(note.data));
    	var charge_value = note.data.id;
    	var charge_text = note.data.name;
    	charge_type = note.data.type;
    	
    	if(charge_type == 0){
    		//库房
    		$("#type1").html("库房");
    		$("#type2").html("库房");
    		$("#type3").html("库房");
    		autocompleteAsync("#charge_code", "../../../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true, "", false, false, 240);
    	}else if(charge_type == 1){
    		//科室
    		$("#type1").html("科室");
    		$("#type2").html("科室");
    		$("#type3").html("科室");
    		autocompleteAsync("#charge_code", "../../../queryHosDeptDictByPerm.do?isCheck=false", "id", "text", true, true, "", false, false, 240);
    	}
		
    	if(charge_value == 0){
        	liger.get("charge_code")._changeValue(charge_value, "全部未记账"+charge_text, false);
    	}else{
        	liger.get("charge_code")._changeValue(charge_value, charge_text, false);
    	}
    }

    function charge(){
		if(!liger.get("charge_code").getValue()){
			$.ligerDialog.warn("请点击左侧菜单选择要记账的单位！");
			return false;
		}
		$.ligerDialog.confirm('请务必确认期初数据准确，记账后“' + liger.get("charge_code").getText() + '”耐用品期初数据将不允许修改，是否继续?', function (yes){
	    	if(yes){
				var formPara ={
					type : charge_type,
					charge_date : $("#charge_date").val(),
					charge_code : liger.get("charge_code").getValue() ==null ? "" : liger.get("charge_code").getValue().split(",")[0]
				};
				ajaxJsonObjectByUrl("saveMatDuraInitCharge.do?isCheck=true", formPara, function (responseData){
		        	if(responseData.state=="true"){
		        		
		        	}
		        });
	    	}
		});
    }
   
    function loadDict(){
    	autocompleteAsync("#charge_code", "../../../queryMatStore.do?isCheck=false", "id", "text", true, true, "", false, false, 240);
    	
    	$("#charge_date").ligerTextBox({width:240});
    	autodate("#charge_date");
		$("#charge").ligerButton({click: charge, width:90});
	}  
    //键盘事件
	function loadHotkeys() {
		hotkeys('C', charge);
	 } 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout1" style="height: 100%;width: 100%" >
        <div position="left" id="left" >
            <div class="l-scroll" style="width: 100%;height:100%;">
                <ul id="tree" style="overflow: auto;height:100%;"/>
            </div>
        </div>
		<div position="center" title="记账">
			<table cellpadding="0" cellspacing="0" style="width: 600px; margin-top: 30px;" class="l-table-edit">
				<tr>
					<td align="center" class="l-table-edit-td" colspan="2">
						<span style="color: red">记账后，物资初始账数据将不允许修改，请确认期初入库数据无误后再记账。</span>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr><td>&nbsp;</td></tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td align="right" class="l-table-edit-td" width="40%">
						<span style="color: red">*</span>记账日期： 
					</td>
					<td align="left" class="l-table-edit-td" width="60%">
						<input class="Wdate" name="charge_date" id="charge_date" type="text" required="true" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr><td>&nbsp;</td></tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td align="right" class="l-table-edit-td" >
						<span id="type1">库房</span>：
					</td>
					<td align="left" class="l-table-edit-td" >
						<input name="charge_code" type="text" id="charge_code" ltype="text" validate="{required:false,maxlength:100}" />
					</td>
				</tr>
				<tr>
					<td align="center" class="l-table-edit-td" >
					</td>
					<td align="left" class="l-table-edit-td">
						<span style="color: red"><span id="type2">库房</span>为空则对所有<span id="type3">库房</span>进行记账</span>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr><td>&nbsp;</td></tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td align="center" class="l-table-edit-td" colspan="2">
						<button id ="charge" accessKey="C" type="button" ><b>记账（<u>C</u>）</b></button>
					</td>
				</tr>
			</table>
		</div>  
    </div>  
</body>
</html>
