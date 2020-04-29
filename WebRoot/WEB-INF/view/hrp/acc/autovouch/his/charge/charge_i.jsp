<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>

<style type="text/css">
	.dialog {
		z-index: 999;
		position: absolute;
		width: 400px;
		height: 300px;
		background: #fff;
		border: 1px solid #ddd;
	}
			
	.dialog .title {
		padding: 0 80px 0 20px;
		height: 42px;
		line-height: 42px;
		border-bottom: 1px solid #eee;
		font-size: 14px;
		font-weight: 700;
		color: #333;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		background-color: #f8f8f8;
		border-radius: 2px 2px 0 0;
		position: relative;
	}
			
	.close {
		position: absolute;
		right: 20px;
		width: 16px;
		height: 16px;
		font-size: 18px;
		cursor: pointer
	}
			
	.details_content {
		padding: 10px;
		height: 237px;
		overflow: auto;
	}
			
	#details_t td {
		padding: 5px
	}
			
	#details_t td.timeline {
		padding: 0;
		width: 1px;
		border: 1px dashed #f8f8f8
	}
			
	#details_ul {
		margin-left: 20px;
	}
			
	#details_ul>li {
		padding: 5px 10px;
		position: relative;
		border-left: 1px solid #dddddd;
		margin-left: 10px;
	}
			
	#details_ul li.frist {
		padding-top: 0
	}
	
	#details_ul li.end {
		padding-bottom: 0
	}
			
	#details_ul li.end i {
		bottom: 0
	}
			
	#details_ul li i {
		position: absolute;
		width: 10px;
		height: 10px;
		left: -5px
	}
			
	#details_ul li svg .g_red {
		fill: #e01607;
		cy: 5
	}
			
	#details_ul li svg .g_t {
		fill: #f1ed03;
		cy: 5
	}
	
	#details_ul li span {
		display: inline-block;
		vertical-align: top;
	}
	
	#details_ul svg {
		width: 20px;
		height: 20px;
	}
			
	.afterdate .timer {
		margin-left: 6px;
	}
	
	.timer {
		margin-right: 18px;
	}
	
	.text {
		margin-top: 5px;
		margin-left: 11px;
		padding: 2px;
		cursor: pointer;
	}
			
	#details_ul .d_status {
		background: rgba(3, 34, 255, 0.58);
		margin-top: 7px;
		padding: 0px 0;
		width: 56px;
		text-align: center;
		color: #fff;
	}
			
	#details_assess {
		margin-top: 26px;
		margin-left: 26px;
	}
	
	#details_message {
		display: inline-block;
		width: 260px;
		height: 100px;
		border: 1px solid #ddd;
	}
	
	.cuilabel {
		display: inline-block;
		margin-right: 10px;
		width: 70px;
		text-align: right
	}
	
	.cui-block {
		margin-top: 10px
</style>	

<script type="text/javascript">

    var grid;
    var balGrid;
    var gridManager = null;
    var radionManager=null; 
    
    $(function ()
    {
        loadDict();//加载下拉框
    	loadChargeHead();	
    	f_setColumns();
    	$(':button').ligerButton({width:80});
    	$("button[name='createVouchBtn']").ligerButton({width:100});
    });
    
    //查询住院收入数据
    function  queryCharge(){

    	var column_name=f_setColumns();
    	if(column_name==""){
    		grid.set({ data: {}});
    		//grid._clearGrid();
    		//grid._setData({});
    		//grid.reRender();
    		return;
    	}
    	
    	var busi_type_code='0103'
    	if(liger.get("io_type").getValue()==2){
    		busi_type_code="010302";
    	}
    	
		grid.options.parms = [];
		grid.options.parms.push({name : 'column_name',value : column_name});
		grid.options.parms.push({name : 'charge_date_b',value : $("#charge_date_b").val()});
		grid.options.parms.push({name : 'charge_date_e',value : $("#charge_date_e").val()});
		grid.options.parms.push({name : 'dept_code',value : $("#dept_code").val()});
		grid.options.parms.push({name : 'io_type',value : liger.get("io_type").getValue()});
		grid.options.parms.push({name : 'busi_type_code',value : busi_type_code});
		grid._setUrl("queryHisChargeDataI.do");
		
		
		//grid.loadData(grid.where);//加载查询条件
     }
    
    //加载收入表头
	function loadChargeHead() {
		grid = $("#maingrid").ligerGrid({
					columns : [],
					dataAction: 'server',dataType: 'server',usePager:false,url:'queryHisChargeDataI.do',width: '100%', height: $(window).height()-60, checkbox: false,
					rownumbers:true,delayLoad:true,selectRowButtonOnly:true,title:'收入数据'/*,heightDiff: -5*/
					
				});
	
		gridManager = $("#maingrid").ligerGetGridManager();
	}
    
    function getTotalSum(){
    	var totalSum=0;
    	var totalSumDiv=document.getElementsByName("totalSumDiv");
    	$.each(totalSumDiv,function(i,obj){
    		totalSum=totalSum+parseFloat($(obj).text());
    		
    	});
    	return totalSum;
    }
 

    //动态设置收入数据表头
	function f_setColumns(){ 
		 var column_name="";
		 var para={
				   charge_date_b:$("#charge_date_b").val(),
				   charge_date_e:$("#charge_date_e").val(),
				   io_type:liger.get("io_type").getValue(),
				   dept_code:$("#dept_code").val()
			};
		 ajaxJsonObjectByUrl("queryHisChargeHeadI.do?isCheck=false",para,function(responseData){
				//console.log(JSON.stringify(responseData))
			 	var columns = [
			 	              {display:'科室编码',name:"dept_code",width: 100,align : 'left', frozen: true},
			 	              {display:'科室名称',name:"dept_name",width: 100,align : 'left', frozen: true},
			 	              {display:'凭证编号',name:"vouch_no",width: 100,align : 'left', frozen: true,render:function(rowdata){
									if(rowdata.vouch_no=='-'){
										return rowdata.vouch_no;
									}else{
										return "<a href=javascript:void(0); onclick=openSuperVouch('"+rowdata.__id+"','"+rowdata.vouch_no+"',$(event))>"+rowdata.vouch_no+"</a>";
									}
								}}
			 	               ];
				
				$.each(responseData.Rows,function(i,obj){
					//,render : function(rowdata, rowindex, value) { return formatNumber(value, 2, 1); }
					columns.push({display:obj.charge_kind_name,name:"c_"+obj.charge_kind_code.toLowerCase(),width: 100,align : 'right', totalSummary: { 
	                    render: function (suminf, column, cell) {
	                        return '<div name=totalSumDiv>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '2', 1)+ '</div>';
	                    }
					 }});
					column_name=column_name+obj.charge_kind_code+",";
				});	
				
			    grid.set('columns', columns); 
			    //grid.reRender();
			},false);
		 
       return column_name;
     } 

    function loadDict(){

      	autocomplete("#template_code","queryAutoBusiTemplate.do?isCheck=false&busi_type_code=0103","id","text",true,true,"",true);
    	$("#charge_date_b").ligerTextBox({width:90});autodate("#charge_date_b", "yyyy-mm-dd", "month_first");
    	$("#charge_date_e").ligerTextBox({width:90});autodate("#charge_date_e", "yyyy-mm-dd", "month_last");
    	$("#dept_code").ligerTextBox({width:150});
    	$("#vouch_date").val('${vouch_date}');
    	
    	var io_type_data = [{ id: 1, text: '1 住院' }];
    	if(${type}==1){
    		//温医个性化需求
    		io_type_data.push({ id: 2, text: '2 其它' });
    	}
    	$("#io_type").ligerComboBox({data: io_type_data,width:120,valueField:'id',textField:'text',autocomplete: false,cancelable: false}); 
		liger.get("io_type").setValue(1);
		liger.get("io_type").setText('1 住院');

	}
    
    
	//生成凭证
	function createVouch(){
		/* if($("#charge_date_b").val().substring(0,7)!=$("#charge_date_e").val().substring(0,7)){
			$.ligerDialog.error("不能跨月生成凭证！");
			return;
		} */
		if(liger.get("template_code").getValue()==""){
			$.ligerDialog.error("请选择凭证模板！");
			return;
		}
		if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
		
		var para={
				charge_date_b:$("#charge_date_b").val(),
				charge_date_e:$("#charge_date_e").val(),
				template_code:liger.get("template_code").getValue(),
				io_type:liger.get("io_type").getValue(),
				vouch_date:$("#vouch_date").val()
		};
		
		
		$.ligerDialog.confirm('确定生成凭证?', function (yes){
        	if(yes){
        		var loadIndex = layer.load(1);
        		ajaxJsonObjectBylayer("queryHisChargeVouchI.do",para,function(responseData){	
        			//console.log(responseData);
        			layer.close(loadIndex);
        			var para={
        				url:'hrp/acc/accvouch/superVouch/superVouchMainPage.do?isCheck=false&openType=autoVouch',
        				title:'会计凭证',
        				width:0,
        				height:0,
        				isShowMin:true,
        				isModal:true,
        				/* data:{vouch:responseData.vouch,busi_log_table:'ACC_BUSI_LOG_0103',busi_type_code:'0103',
        					busi_no:responseData.busi_no,template_code:liger.get("template_code").getValue(),vouch_id:responseData.auto_id} */
       					data:{auto_id:auto_id.auto_id,busi_log_table:'ACC_BUSI_LOG_0103',busi_type_code:'0103',}
        			};
        			//期末处理生成凭证格式：data:{vouch:responseData,busi_log_table:'ACC_VOUCH_SOURCE',busi_type_code:'0103'}
        			parent.openDialog(para);
          		},layer,loadIndex);
        	}
        }); 

		
	} 
	
	
	function openVouch(para){
		parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?vouch_id='+para,'会计凭证',0,0,true,true);
	}
	
		//parent.openFullDialog('hrp/acc/accvouch/superVouch/superVouchMainPage.do?','会计凭证',0,0,true,true);
			// 弹出维修进度页面
			function openSuperVouch(id, value, evt) {
				
				evt = window.event || event;
				evt.target = evt.srcElement ? evt.srcElement : evt.target;
				// 获取弹窗定位
				var tdOffset = $(evt.target).parent('div').offset();
				var distance_x = tdOffset.left + $(evt.target).parent('div').outerWidth();
				var distance_y = tdOffset.top;
				// 设定弹窗top定位的最大值
				if(tdOffset.top + $('#details').outerHeight() + 45 > $('body').height()) {
					distance_y = $('body').height() - $('#details').outerHeight() - 45;
				}
				$("#details").show();
				$('#details').css({
					left: distance_x,
					top: distance_y
				});
				var para={
						charge_date_b:$("#charge_date_b").val(),
						charge_date_e:$("#charge_date_e").val(),
				};

				$("#details").show(0, function() {
					ajaxJsonObjectByUrl("queryVouchNo.do?isCheck=false",para,function(responseData){
						var syt = [];
						console.log(responseData.Rows);
						$.each(responseData.Rows,function(i,k){
							 syt.push(
									'<div><a href=javascript:void(0); onclick=openVouch('+k.vouch_id+')>'+k.vouch_no+'</a></div>' 
									); 
						})
						$('#details_assess').html(syt)		
					},false);
				})
				// 点击外面关闭弹窗
				$(document).on('click.detail', function(evt) {
					var evt = window.event || event;
					evt.target = evt.srcElement ? evt.srcElement : evt.target;
					if($(evt.target).text() === '凭证详情') {
						return false;
					}
					if($(evt.target).closest('#details').length === 0 && $(evt.target).closest('#details').length === 0) {
						$('#details').hide(0, function() {
							$(document).off('click.detail');
						});
					}
				});
				// 关闭按钮
				$('.close').click(function() {
					$('#details').hide();
					$('.close').unbind('click');
				});
				// 拖动弹窗
				$(document).on('mousedown.detail', '#details', function(evt) {
					var evt = window.event || event;
					evt.target = evt.srcElement ? evt.srcElement : evt.target;
					var startx = evt.clientX,
						starty = evt.clientY,
						startLeft = $('#details').position().left,
						startTop = $('#details').position().top;
					$(document).mousemove(function(evt) {
						var evt = window.event || event;
						evt.target = evt.srcElement ? evt.srcElement : evt.target;
						var leftOffset = evt.clientX - (startx - startLeft),
							topOffset = evt.clientY - (starty - startTop);
						$('#details').css({
							left: leftOffset,
							top: topOffset
						})
					});
					$(document).mouseup(function() {
						$(document).off('mousemove');
					})
				})
			}
	
	function printDate(){
		 if(grid.getData().length==0){
   		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
   	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
							{"cell":0,"value":"收费日期："+$("#charge_date_b").val()+"至"+$("#charge_date_e").val(),"colSpan":"5"},
	      		  ]
	      	};
	   		
  		var printPara={
  			rowCount:1,
  			title:'门诊收费',
  			columns: JSON.stringify(grid.getPrintColumns()),//表头
  			class_name: "com.chd.hrp.acc.service.autovouch.his.HisAccChargeService",
			method_name: "queryHisChargeDataIPrint",
			bean_name: "hisAccChargeService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
  			};
   	
  		//执行方法的查询条件
  		$.each(grid.options.parms,function(i,obj){
  			printPara[obj.name]=obj.value;
   	});
  		
   	officeGridPrint(printPara); 
	
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;" >

	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<table width="100%" cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px">
   		 <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;width:120px;"><b><font color="red">*</font></b>收费日期：</td>
            <td align="left" class="l-table-edit-td" width="100px">
				<table>
					<tr>
						<td>
							<input type="text" id="charge_date_b" style="font-size:12px" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input type="text" id="charge_date_e" style="font-size:12px" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td"  style=padding-left:20px;">医嘱类别：</td>
            <td align="left" class="l-table-edit-td"><input name="io_type" type="text" id="io_type" ltype="text" /></td>
			
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td" ><input name="dept_code" type="text" id="dept_code" ltype="text" /></td>
            <td align="right">
            	<button accessKey="Q" onclick="queryCharge();"><b>查询（<u>Q</u>）</b></button>
            	&nbsp;&nbsp;&nbsp;&nbsp;
            	<button  onclick="printDate();"><b>打 印</b></button>
            	&nbsp;&nbsp;
            </td>
        </tr> 
    </table>
    <hr/>
    <table width="100%" cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px">
    <tr>
    	<td align="right" class="l-table-edit-td"  style="padding-left:20px;width:120px;"><b><font color="red">*</font></b>凭证模板：</td>
		<td align="left" class="l-table-edit-td" style="width:120px;">
		    <input type="text" id="template_code" ltype="text" />
		</td>
		<td align="right" class="l-table-edit-td" style="width:120px;"><b><font color="red">*</font></b>凭证日期：</td>
		<td align="left" class="l-table-edit-td" style="width:120px;">
			<input class="Wdate" name="vouch_date"  style="width:105px" type="text"  id="vouch_date"  ltype="text"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
		</td>
    	<td algin="left">
    		<button accessKey="S" onclick="createVouch();" name="createVouchBtn" title="选择按收款员生成，不选择按日期全部生成"><b>生成凭证（<u>S</u>）</b></button>
    	</td>
    </tr>
    </table>
    
    <div id="maingrid"></div>
    <!-- 凭证详情弹窗	 -->
	<div id="details" class="dialog" style="display:none">
		<div class="title">凭证详情
			<span class="close ">X</span>
		</div>
		<div class="details_content">
			<!-- 维修进度评价 -->
			<div id="details_assess"></div>
		</div>
	</div>
	
</body>
</html>
