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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		 query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'begin_in_date',
			value : $("#begin_in_date").val()
		});
		grid.options.parms.push({
			name : 'end_in_date',
			value : $("#end_in_date").val()
		}); 
		grid.options.parms.push({
			name : 'bus_type_code',
			value : liger.get("bus_type_code").getValue() == null ? "" : liger.get("bus_type_code").getValue()
		}); 
		grid.options.parms.push({
			name : 'state',
			value : liger.get("state").getValue() == null ? "" : liger.get("state").getValue()
		}); 
		grid.options.parms.push({
			name : 'begin_confirm_date',
			value : $("#begin_confirm_date").val()
		});
		grid.options.parms.push({
			name : 'end_confirm_date',
			value : $("#end_confirm_date").val()
		}); 
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'in_no',
			value : $("#in_no").val()
		}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
	
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '退库单号', name: 'in_no', align: 'left', width: 130,
					render : function(rowdata, rowindex, value) {
						if(value == '合计'){
							return value ;
						}
						return '<a href=javascript:update_open("' 
							+ rowdata.group_id 
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code 
							+ ',' + rowdata.in_id
							+ '")>'+rowdata.in_no+'</a>';
					}
				}, { 
		 			display: '摘要', name: 'brief', align: 'left', width: '200'
		 		}, { 
		 			display: '仓库', name: 'store_name', align: 'left', width: '150'
		 		}, { 
		 			display: '业务类型', name: 'bus_type_name', align: 'left', width: '80'
		 		}, { 
		 			display: '供应商', name: 'sup_name', align: 'left', width: '150'
		 		}, { 
		 			display: '采购员', name: 'stocker_name', align: 'left', width: '80'
		 		}, { 
		 			display: '金额', name: 'amount_money', align: 'right', width: '100',
		 			render : function(rowdata, rowindex, value) {
		 				if(rowdata.amount_money == null ){
		 					return "";
		 				}
						return formatNumber(value ==null ? 0 : value, '${p08005 }', 1);
					}
		 		}, { 
		 			display: '编制日期', name: 'in_date', align: 'left', width: '80'
		 		}, { 
		 			display: '制单人', name: 'maker_name', align: 'left', width: '80'
		 		}, { 
		 			display: '确认日期', name: 'confirm_date', align: 'left', width: '80'
		 		}, { 
		 			display: '确认人', name: 'confirmer_name', align: 'left', width: '80'
		 		}, { 
		 			display: '状态', name: 'state', align: 'right', width: '80',render: fieldTypeRender,hide:true
		 		},{ 
		 			display: '状态', name: 'state_name', align: 'right', width: '80',render: fieldTypeRender
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedAffiBack.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			checkBoxDisplay:isCheckDisplay,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				{ line:true },
				{ text: '复制（<u>C</u>）', id:'copy', click: copy_no, icon:'copy' },
				{ line:true },
				{ text: '删除（<u>D</u>）', id:'delete', click: remove, icon:'delete' },
				{ line:true }, 
				{ text: '审核（<u>S</u>）', id:'audit', click: audit, icon:'audit' },
				{ line:true }, 
				{ text: '消审（<u>U</u>）', id:'unAudit', click: unAudit, icon:'unaudit' },
				{ line:true }, 
				{ text: '退库确认（<u>C</u>）', id:'confirm', click: confirm, icon:'account' },
				{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
				{ line:true } ,
				{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' }
				
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  //是否显示复选框
    function isCheckDisplay(rowdata){
       	if(rowdata.in_id == null) return false;
         return true;
    }
    
  //字段类型渲染器
    function fieldTypeRender(r){
	  	 if(r.state == null){
	  		 return "";
	  	 }
    	 for (var i = 0, l = medInMain_state.Rows.length; i < l; i++){
                var o = medInMain_state.Rows[i];
                if (o.id == r.state) {
                	return o.text;
                }
            }
            return "未确认";
        }
  
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add_open);
		hotkeys('C', copy_no);
		hotkeys('D', remove);
		hotkeys('S', audit);
		hotkeys('U', unAudit);
		hotkeys('F', confirm);
		//hotkeys('P', print);
	}
    
    //添加页面
    function add_open(){
    	parent.$.ligerDialog.open({
			title: '代销退货单添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/affi/back/medAffiBackAddPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: true,initShowMax:true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
    	
	}
    //修改页面
    function update_open(obj){		
		var vo = obj.split(",");
		var paras ="group_id="+vo[0] +"&hos_id="+vo[1] +"&copy_code="+vo[2] +"&in_id="+vo[3] ;
		
		parent.$.ligerDialog.open({
			title: '代销退货单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/affi/back/medAffiBackUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, initShowMax:true,isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
		 
    }
    //删除
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state > 1){
					in_nos = in_nos + this.in_no + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("删除失败！"+in_nos+"单据不是未审核状态");
				return;
			}
			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteMedAffiBack.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    //复制
	function copy_no(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			$.ligerDialog.confirm('确定复制?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("copyMedAffiBack.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	//审核
	function audit(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 1){
					in_nos = in_nos + this.in_no + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("审核失败！"+in_nos+"单据不是未审核状态");
				return;
			}
			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("auditMedAffiBackBatch.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	//销审
	function unAudit(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					in_nos = in_nos + this.in_no + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("消审失败！"+in_nos+"单据不是已审核状态");
				return false;
			}
			$.ligerDialog.confirm('确定消审?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("unAuditMedAffiBackBatch.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    //确认
    function confirm(){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return false;
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					in_nos = in_nos + this.in_no + ",";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("退库确认失败！"+in_nos+"单据不是已审核状态");
				return false;
			}
			$.ligerDialog.confirm('确定退库确认?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("confirmMedAffiBackBatch.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    
  //打印设置
	function printSet(){
		
		
		var useId=0;//统一打印
		if('${p08023 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08023 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
		
		officeFormTemplate({template_code:"08018",use_id:useId});
		
		/* parent.parent.$.ligerDialog.open({url : 'hrp/med/affi/back/affiBackPrintSetPage.do?template_code=08019&use_id='+useId,
			data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
		}); */
	}
    //打印
    function print(){
    	
    	 var useId=0;//统一打印
 		if('${p08023 }'==1){
 			//按用户打印
 			useId='${sessionScope.user_id }';
 		}else if('${p08023 }'==2){
 			//按仓库打印
 			if(liger.get("store_code").getValue()==""){
 				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
 				return;
 			}
 			useId=liger.get("store_code").getValue().split(",")[0];
 		}

    	//if($("#create_date_b").val())
 		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			
			var in_id ="" ;
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					in_nos = in_nos + this.in_no + "<br>";
				}
				
				in_id  += this.in_id+","
					
			});
			 /* var para={
	    			paraId :in_id.substring(0,in_id.length-1) ,
	    			
	    			template_code:'08019',
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	};  */
	    	
	    	 var para={
	    			paraId :in_id.substring(0,in_id.length-1) ,
	    			template_code:'08018',
	    			class_name:"com.chd.hrp.med.serviceImpl.affi.in.MedAffiInCommonServiceImpl",
	    			method_name:"queryMedAffiInByPrintTemlate1",
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    	}; 
		 	
				officeFormPrint(para);
		 	
			//alert(JSON.stringify(para));
			
	    	//printTemplate("hrp/med/affi/back/queryMedAffiBackByPrintTemlate.do?isCheck=false",para);
	    	
		}
    	
    }
   
    function loadDict(){
		//字典下拉框
		autoCompleteByData("#state", medInMain_state.Rows, "id", "text", true, true,null,null,null,"180");
		//alert(liger.get("bus_type_code").getValue());
		autocomplete("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true, {codes : '29'}, true,null,"180");
		autocomplete("#store_code", "../../queryMedStore.do?isCheck=false", "id", "text", true, true,{is_com : '1'},null,null,"180");
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,null,null,null,"180");
        $("#begin_in_date").ligerTextBox({width:90});
        autodate("#begin_in_date", "yyyy-mm-dd", "month_first");
        $("#end_in_date").ligerTextBox({width:90});
        autodate("#end_in_date", "yyyy-mm-dd", "month_last");
        $("#begin_confirm_date").ligerTextBox({width:90});
        $("#end_confirm_date").ligerTextBox({width:90});
        $("#in_no").ligerTextBox({width : 219});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  width="10%">
            	制单日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_in_date" id="begin_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_in_date" id="end_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				业务类型：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" />
            </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				状态：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="state" type="text" id="state" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
            	退货日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
			<td align="right" class="l-table-edit-td" >
				供应商：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr> 
        <tr>
			<td align="right" class="l-table-edit-td" >
				单据号：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="in_no" type="text" id="in_no" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
        </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
