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
    var renderFunc = {
			 
			amount_money:function(value){//金额
				return formatNumber(value, '${p04005 }', 1);
			} 
	}; 
    
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
			name : 'begin_date',
			value : $("#begin_date").val()
		});
		grid.options.parms.push({
			name : 'end_date',
			value : $("#end_date").val()
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
			name : 'state',
			value : liger.get("state").getValue() == null ? "" : liger.get("state").getValue().split(",")[0]
		}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '入库单号', name: 'in_no', align: 'left', width: 130,
					render : function(rowdata, rowindex, value) {
						return '<a href=javascript:update_open("' 
							+ rowdata.group_id 
							+ ',' + rowdata.hos_id 
							+ ',' + rowdata.copy_code 
							+ ',' + rowdata.in_id
							+ '")>'+rowdata.in_no+'</a>';
					}
				}, { 
		 			display: '摘要', name: 'brief', align: 'left', width: 200
		 		}, { 
					display: '入库日期', name: 'in_date', align: 'left', width: 100
		 		}, { 
		 			display: '供应商', name: 'sup_name', align: 'left', width: 200
		 		}, { 
		 			display: '仓库', name: 'store_name', align: 'left', width: 150
		 		}, { 
		 			display: '业务类型', name: 'bus_type_name', align: 'left', width: 100
		 		}, { 
		 			display: '制单人', name: 'maker_name', align: 'left', width: 80
		 		}, { 
		 			display: '确认人', name: 'confirmer_name', align: 'left', width: 80
		 		}, { 
		 			display: '确认日期', name: 'confirm_date', align: 'left', width: 100
		 		}, { 
		 			display: '金额', name: 'amount_money', align: 'right', width: 120,
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
					}
		 		}, { 
		 			display: '状态', name: 'state_name', align: 'left', width: 80
		 		} ],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatInitIn.do',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
				{ line:true },
				{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				{ line:true },
				{ text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
				{ line:true }, 
				{ text: '入库确认（<u>C</u>）', id:'confirm', click: confirm,icon:'account' },
				{ line:true } ,
				{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' },
				{ line:true } ,
				{ text: '模板设置', id:'printSet', click: printSet, icon:'settings' },
			]}, 
			onDblClickRow : function (rowdata, rowindex, value){
				update_open(
					rowdata.group_id + "," + 
					rowdata.hos_id + "," + 
					rowdata.copy_code + "," + 
					rowdata.in_id 
				);
			} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>日期范围："+$("#begin_date").val() +" 至  "+ $("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="常备材料期初入库";
    }
    
    function add_open(){

    	parent.$.ligerDialog.open({
			title: '期初入库单添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/initial/in/addPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
    
    function update_open(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"in_id="+vo[3] ;
		parent.$.ligerDialog.open({
			title: '期初入库单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/initial/in/updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
    }
    	
    function remove(){
    	var is_delete = "${p04011 }";//是否可删除他人单据
    	var user_id = "${sessionScope.user_id}";//当前操作用户
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				//is_delete：1不能删除他人单据
				if(is_delete == 1){
					//判断是否本人制单
					if(user_id != this.maker){
						in_nos = in_nos + this.in_no + "<br>";
					}else{
						//判断单据状态
						if(this.state > 1){
							in_nos = in_nos + this.in_no + "<br>";
						}
					}
				}else {
					//判断单据状态
					if(this.state > 1){
						in_nos = in_nos + this.in_no + "<br>";
					}
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			if(in_nos != ""){
				if(is_delete == 1){
					$.ligerDialog.error("删除失败！<br>以下单据不是未审核状态或为他人制单：<br>"+in_nos);
					return;
				}else{
					$.ligerDialog.error("删除失败！<br>以下单据不是未审核状态：<br>"+in_nos);
					return;
				}
			}
			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteMatInitIn.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    	
  //批量入库确认
	function confirm() {
		var is_store='${p04045 }';
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var todayDate = new Date();
			var todayYear = todayDate.getFullYear();
			var todayMonth = todayDate.getMonth() + 1;
			var todayDate = todayDate.getDate();
			todayMonth = todayMonth < 10 ? '0' + todayMonth : todayMonth;
			todayDate = todayDate < 10 ? '0' + todayDate : todayDate;
			var today = todayYear + '-' + todayMonth + '-' + todayDate;
			var confirmDate;
			if('${p04047 }'==0){
				confirmData(today);
			}else{
				$.ligerDialog.open({
					content: "确认日期:<input id='confirmDate' value=" + today + " style='text-align:center; border: 1px solid blue; height: 18px;' onFocus='WdatePicker({isShowClear:true,readOnly:true,dateFmt:\"yyyy-MM-dd\"})' />",
					width: 300,
					height: 150,
					buttons: [
						{ 
							text: '确定',
							onclick: function (item, dialog) {
								confirmDate = $("#confirmDate").val();
							
								if (confirmDate) {
									dialog.close();
									confirmData(confirmDate)
								}
							}
						},
		                { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
					]
				})
			}
				
		}
	}
    
    function confirmData(confirmDate){
    	
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state == 3){
					in_nos = in_nos + this.in_no + "<br>";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id   +"@"+
					confirmDate
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("入库确认失败！<br>以下单据已入库确认：<br>"+in_nos);
				return;
			}
			$.ligerDialog.confirm('确定入库确认?', function (yes){
				if(yes){
					
					ajaxJsonObjectByUrl("confirmMatInitInBatch.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    //打印模板设置
    function printSet(){
	  
    	var useId=0;//统一打印
		if('${p04017 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p04017 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
    /* 	parent.$.ligerDialog.open({url : 'hrp/mat/initial/in/storageInPrintSetPageQc.do?template_code=04008&use_id='+useId,
    		data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
    		}); */
    		
		officeFormTemplate({template_code:"04008",use_id:useId});
    }

  //打印
    function print(){
    	 var useId=0;//统一打印
 		if('${p04017 }'==1){
 			//按用户打印
 			useId='${sessionScope.user_id }';
 		}else if('${p04017 }'==2){
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
			
// 			if(in_nos != ""){
// 				$.ligerDialog.error("打印失败！<br>以下单据不是已审核状态：<br>"+in_nos);
// 				return;
// 			} 
			
			
			/*  var para={
	    			paraId :in_id.substring(0,in_id.length-1) ,
	    			
	    			template_code:'04008',
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	}; 
			
	    	printTemplate("hrp/mat/initial/in/queryMatInByPrintTemlateQc.do?isCheck=false",para); */
	    	
			var para={
					template_code:'04008',
					class_name:"com.chd.hrp.mat.serviceImpl.storage.in.MatStorageInServiceImpl",
					method_name:"queryMatInByPrintTemlateNewPrint",
					isSetPrint:false,//是否套打，默认非套打
					isPreview:true,//是否预览，默认直接打印
					paraId :in_id.substring(0,in_id.length-1) ,
					use_id:useId,
	    			p_num:1
			}; 
			officeFormPrint(para);
	    	
		}
    	
    }
   
    function loadDict(){
		//字典下拉框
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:'1'});
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true);
		var paras = {
			table_code : "mat_in_main",
			field_code : "state",
			notValues : "2"
		}
		autocomplete("#state", "../../queryMatSysList.do?isCheck=false", "id", "text", true, true, paras);
        $("#begin_date").ligerTextBox({width:100});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        $("#end_date").ligerTextBox({width:100});
        autodate("#end_date", "yyyy-mm-dd", "month_last");
	}  
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('P', printDate);
	}
	//打印数据
	function printDate(){
		
		return;
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
            	日期范围：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
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
			<td align="right" class="l-table-edit-td"  width="10%">
				状态：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="state" type="text" id="state" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr> 
        <tr>
			<td align="right" class="l-table-edit-td" >
				供应商：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr>
                <th width="200">单据ID</th>	
                <th width="200">入库单号</th>	
                <th width="200">年份</th>	
                <th width="200">月份</th>	
                <th width="200">业务类型</th>	
                <th width="200">供应商变更ID</th>	
                <th width="200">供应商ID</th>	
                <th width="200">仓库ID</th>	
                <th width="200">仓库变更ID</th>	
                <th width="200">货位</th>	
                <th width="200">摘要</th>	
                <th width="200">采购员</th>	
                <th width="200">采购类型编码</th>	
                <th width="200">是否为初始化单据</th>	
                <th width="200">制单人</th>	
                <th width="200">入库日期</th>	
                <th width="200">审核人</th>	
                <th width="200">审核日期</th>	
                <th width="200">入库确认人</th>	
                <th width="200">入库确认日期</th>	
                <th width="200">状态</th>	
                <th width="200">是否已经付款</th>	
                <th width="200">付款日期</th>	
                <th width="200">发票号码</th>	
                <th width="200">开发票金额</th>	
                <th width="200">是否全部开发票</th>	
                <th width="200">调拨单位</th>	
                <th width="200">结算方式</th>	
                <th width="200">盘点单号</th>	
                <th width="200">协议编号</th>	
                <th width="200">申请科室</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
