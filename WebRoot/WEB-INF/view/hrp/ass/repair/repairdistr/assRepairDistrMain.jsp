<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	String path = request.getContextPath();
	%>
		<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<html>

		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title></title>
			<!-- 维修进度滚动条样式 -->
			<style>
				/*定义滚动条宽高及背景，宽高分别对应横竖滚动条的尺寸*/

				.details_content::-webkit-scrollbar {
					width: 10px;
					height: 10px;
					background-color: #f5f5f5;
				}
				/*定义滚动条的轨道，内阴影及圆角*/

				.details_content::-webkit-scrollbar-track {
					-webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
					border-radius: 10px;
					background-color: #f5f5f5;
				}
				/*定义滑块，内阴影及圆角*/

				.details_content::-webkit-scrollbar-thumb {
					/*width: 10px;*/
					height: 20px;
					border-radius: 10px;
					-webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
					background-color: rgb(207, 207, 207);
				}
				/*滑块效果*/

				::-webkit-scrollbar-thumb:hover {
					border-radius: 5px;
					-webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
					background: rgba(78, 78, 78, 0.4);
				}
			</style>


			<!-- 维修进度弹出框样式 -->
			<style>
				.dialog {
					
				}
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
					display:inline-block;
					margin-right: 10px;
					width: 70px;
					text-align: right
				}
				
				.cui-block {
					margin-top: 10px
				}
			</style>

			<!-- 气泡样式 -->
			<style>
				.details_content .qipao {
					display: none;
					min-width: 145px;
					min-height: 64px;
					background: #fff;
					border: 1px solid #ddd;
					border-radius: 5px;
					z-index: 1;
					position: absolute
				}

				.qipao li {
					padding: 2px 20px;
				}

				.qipao:before {
					content: '';
					position: absolute;
					right: 100%;
					top: 15px;
					width: 0;
					height: 0;
					border-width: 11px;
					border-style: solid;
					border-color: transparent;
					border-right-width: 25px;
					border-right-color: currentColor;
					color: #dddddd;
				}

				.qipao:after {
					content: '';
					position: absolute;
					right: 99%;
					top: 15px;
					width: 0;
					height: 0;
					border-width: 11px;
					border-style: solid;
					border-color: transparent;
					border-right-width: 25px;
					border-right-color: currentColor;
					color: #fff;
				}
			</style>
			
			<!-- 其它标签样式 -->
			<style>
				ul,
				li {
					list-style: none;
					margin: 0;
					padding: 0;
				}

				hr {
					height: 1px;
					border: none;
					border-top: 1px solid #a3c0e8;
				}

				tr.pq-grid-row>td.g_red {
					color: #f00;
				}

				.togglebtn {
					position: absolute;
					background: url(/CHD-HRP/lib/ligerUI/skins/icons/toggle2.png) no-repeat 4px 4px;
					height: 24px;
					width: 24px;
					cursor: pointer;
					left: auto;
					top: -7px;
					right: 0px;
					border-radius: 50%;
					background-color: #ffffff;
					border: 1px solid #ddd;
				}

				.bgi {
					background-image: url(/CHD-HRP/lib/ligerUI/skins/icons/toggle1.png);
				}
			</style>
			<jsp:include page="${path}/resource.jsp">
				<jsp:param value="dialog,grid,select,datepicker,pageOffice,jquery_print,ligerUI" name="plugins" />
			</jsp:include>
			<script src="<%=path %>/lib/starRaty/lib/jquery.raty.min.js" type="text/javascript"></script>
			<script>
				var app_time,state,eme_status,rep_dept,fau_note,fau_code,rep_code,grid;

				function initDict() {
					// 报修日期
					app_time = $("#app_time").etDatepicker({
						range: true,
						defaultDate: ['yyyy-mm-fd', 'yyyy-mm-ed']
					});
					// 报修进度
					state = $('#state').etSelect({
						url: "../../../budg/queryBudgSysDict.do?isCheck=false&f_code=ASS_REPAIR_STATE",
						defaultValue: "none"
					});
					// 紧急程度
					eme_status = $('#eme_status').etSelect({
						 options: [{
	                          id: 1,
	                          text: '1 非常紧急'
	                      }, {
	                          id: 2,
	                          text: '2 紧急'
	                      }, {
	                          id: 3,
	                          text: '3 一般'
	                      }],
	                     defaultValue: "none"
					});
					// 报修科室
					rep_dept = $("#rep_dept").etSelect({
						url: "../../queryDeptDict.do?isCheck=false",
						defaultValue: "none"
					});
					// 问题描述
					fau_note = $("#fau_note");
					// 故障分类
					fau_code = $('#fau_code').etSelect({
						url: "../../querySuperFaultTypeSelect.do?isCheck=false&is_last=1",
						defaultValue: "none"
					});
					// 折叠按钮
					$('.togglebtn').click(function () {
						$('.togglebtn').toggleClass('bgi');
						$('.table-layout').toggle();
						grid.refreshView();
					})
				}

				function initMainGrid() {
					var gridObj = {
						editable: false,
						checkbox: true,
						height: '100%',
						selectionModel: {
							type: 'row',
							mode: 'block'
						}
					};
					gridObj.columns = [ 
						{
							display: "报修单号",
							align: "lift",
							width: 120,
							name: "rep_code",
							editable: false,
							render: function (ui) { // 修改页打开
							     if(ui.rowData.is_urg=='是'){
									// 修改页打开
									return '<a data-item=' + ui.rowIndx + ' data-col=' + ui.dataIndx + ' class="td-a">' + ui.cellData +'</a>&nbsp;&nbsp;&nbsp;<a data-item=' + ui.rowIndx + ' data-value='+ui.cellData+' class="cui" style="color: red">催</a>'
								 }else{
									 return '<a data-item=' + ui.rowIndx + ' data-col=' + ui.dataIndx + ' class="td-a">' + ui.cellData +'</a>'
 
								 }
							}
						},
						{
							display: '报修时间',
							align: "left",
							name: 'app_time',
							width: 120
						},
						{
							display: '紧急程度',
							align: "left",
							name: 'eme_status',
							//cls: 'g_red',
							width: 80,
							render: function(ui) { 
								if(ui.rowData.eme_status === '一般') {
									return '<span>' + ui.cellData + '</span>'
								} else {
									return '<span style="color: red">' + ui.cellData + '</span>'
								}

							}

						},
						{
							display: '是否超时',
							align: "center",
							name: 'hours',
							width: 60,
							render: function (ui) { 
								if(ui.rowData.hours>0){
									return '<span style="color: red">超时</span>' 
								}else{
									return "";
								}
							}
						},
						{
							display: "报修人",
							width: 120,
							align: "left",
							name: "user_name"
						},
						{
							display: "报修电话",
							width: 120,
							align: "left",
							name: "phone"
						},
						{
							display: "报修科室",
							width: 120,
							align: "left", 
							name: "dept_name"
						},
						{
							display: "位置",
							width: 120,
							align: "left",
							name: "loc_name"
						},
						{
							display: "资产名称",
							width: 120,
							align: "left",
							name: "ass_name"
						},
						{
							display: "故障分类",
							width: 120,
							align: "left",
							name: "fau_name"
						},
						{
							display: "问题描述",
							width: 120,
							align: "left",
							name: "fau_note"
						}
					];
					gridObj.dataModel = { // 数据加载的有关属性
						location: 'remote',
						//url: 'http://118.178.184.131:9090/static_column/grid',
						recIndx: 'a'
					};
					gridObj.toolbar = {
						items: [{
								type: "button",
								label: '查询',
								icon: 'search',
								id: 'search',
								listeners: [{
									click: search
								}]
							},
							{
								type: "button",
								label: '派单',
								icon: 'check',
								listeners: [{
									click: submit
								}]
							},
							{
								type: "button",
								label: '退回',
								icon: 'arrowreturnthick-1-n',
								listeners: [{
									click: back
								}]
							},
							{
								label: '模板设置',
								type: "button",
								id: 'printSet',
								icon: 'print',
								listeners: [{
									click: printSet
								}],
							},
							{
								type: "button",
								label: '打印',
								icon: 'print',
								listeners: [{
									click: printData
								}]
							}
						]
					};
					grid = $("#mainGrid").etGrid(gridObj);

					$('#mainGrid').on('click', '.td-a', function (evt) {
						var index = $(this).attr('data-item') * 1;
						var dataIndx = $(this).attr('data-col');
						var data = grid.getRowData(index);
						var value = $(this).text();
						// 点击a标签时不让它选中
						grid.selectRemove(index);
						if (dataIndx === 'rep_code') {
							// 报修单号页面打开
							assRepairDistrUpdate(data, index, value);
						} else {
							// 维修进度页面打开
							queryDetails(data, index, value, evt)
						}
						return false;
					})
					$('#mainGrid').on('click', '.cui', function (evt) {
						var index = $(this).attr('data-item') * 1;
						var data = $(this).attr('data-value');
						// 点击a标签时不让它选中
						grid.selectRemove(index);
						cuiDailog(data)
						return false;
					})
				}
				// 查询
				function search() {
					var param = [{
							name: 'app_time_begin',
							value: $('#app_time').val().split('至')[0]
						},
						{
							name: 'app_time_end',
							value: $('#app_time').val().split('至')[1]
						},
						{
							name: 'state',
							value:  $('#state').val()
						},
						{
							name: 'eme_status',
							value: $("#eme_status").val()
						},
						{
							name: 'rep_dept',
							value: $("#rep_dept").val().split('@')[0]
						},
						{
							name: 'fau_note',
							value: $("#fau_note").val()
						},
						{
							name: 'fau_code',
							value: $("#fau_code").val()
						},
						{
							name: 'rep_code',
							value: $("#rep_code").val()
						},{
							name: 'rep_user',
							value: ${user_id}
						}
					];
					grid.loadData(param,'queryAssRepairByState.do?isCheck=false');
				}
				 
				// 派发
				function submit() {
					 var data = grid.selectGet();
				        if (data.length == 0) {
				            $.etDialog.error('请选择行');
				        } else {
				            var ParamVo = [];
				            $(data).each(function () {
				                var rowdata = this.rowData;
				                ParamVo.push(rowdata.rep_code);
				            });
				           $.etDialog.open({
								url: 'assRepairDistrSubmitPage.do?isCheck=false',
								height: 500,
					            width: 400,
								title: '选择维修人员',
								btn: ['确定', '取消'],
								btn1: function (index, el) {
									var iframeWindow = window[el.find('iframe').get(0).name];
									iframeWindow.save();
									 
								}
							});
				            /* $.etDialog.confirm('确定发送?', function () {
				                ajaxPostData({
				                    url: "saveAssMyRepair.do",
				                    data: {
				                        rep_code: ParamVo.toString()
				                    },
				                    success: function (res) {
				                        if (res.state) {
				                            search();
				                            //tree.reAsyncChildNodes(null, 'refresh');
				                        }
				                    }
				                })
				            }); */
				        }

				}
				
				// 退回
				function back() {
					 var data = grid.selectGet();
				        if (data.length == 0) {
				            $.etDialog.error('请选择行');
				        } else {
				            var ParamVo = [];
				            $(data).each(function () {
				                var rowdata = this.rowData;
				                ParamVo.push(rowdata.rep_code);
				            });
				            
				            
					            $.etDialog.open({
									url: 'backAssRepairPage.do?isCheck=false&rep_code='+ParamVo.toString(),
									height: 200,
						            width: 400,
									title: '退回原因',
									btn: ['确定', '取消'],
									btn1: function (index, el) {
										var iframeWindow = window[el.find('iframe').get(0).name];
										iframeWindow.saveData();
										 
									}
								});
				           
				                
				           
				        }

				}
				
				//添加页保存回调方法，保存数据用
			 function updateRepairDistrState(userId,index){
				 //console.log(userId);
				 var data = grid.selectGet();
			            var ParamVo = [];
			            $(data).each(function () {
			                var rowdata = this.rowData;
			                ParamVo.push(rowdata.rep_code);
			            });
				 var formData = [];
	            	formData.push({name:'user_id' , value : userId})
	            	formData.push({name:'rep_code' , value : ParamVo})
	            	//console.log(formData)
				    ajaxPostData({
			                 url: 'updateRepairDistrState.do?isCheck=false',
			                 data: formData,
			                 delayCallback: true,
			                 success: function (data) {
			                     $.etDialog.close(index);
			                     //父级查询
			                     search();
			                    
			                 }
			             }) 
    
			 }
 				
				function cuiDailog() {
					$.etDialog.open({
						url: 'cuiDanMainPage.do?isCheck=false&rep_code='+data,
						width:400,
						hight:600,
						title: '催单页',
						btn: ['关闭'],
						btn2: function (index, el) {
							var iframeWindow = window[el.find('iframe').get(0).name];
							iframeWindow.saveData();
						}
					});
				}
				
				// 弹出维修进度页面
				function queryDetails(data, index, value, evt) {
					evt = window.event || event;
					evt.target = evt.srcElement ? evt.srcElement : evt.target;
					// 获取弹窗定位
					var tdOffset = $(evt.target).parent('td').offset();
					var distance_x = tdOffset.left + $(evt.target).parent('td').outerWidth();
					var distance_y = tdOffset.top;
					// 设定弹窗top定位的最大值
					if (tdOffset.top + $('#details').outerHeight() + 45 > $('body').height()) {
						distance_y = $('body').height() - $('#details').outerHeight() - 45;
					}
					$('#details').css({
						left: distance_x,
						top: distance_y
					});

					$("#details").show(0, function () {
						// 时间轴渲染
						timeLineRender(data);
						// 星星评价构造函数
						starRatyRender(data);
					})
					// 点击外面关闭弹窗
					$(document).on('click.detail', function (evt) {
						var evt = window.event || event;
						evt.target = evt.srcElement ? evt.srcElement : evt.target;
						if ($(evt.target).text() === '维修进度') {
							return false;
						}
						if ($(evt.target).closest('#details').length === 0 && $(evt.target).closest('#details').length === 0) {
							$('#details').hide(0, function () {
								$(document).off('click.detail');
							});
						}
					});
					// 关闭按钮
					$('.close').click(function () {
						$('#details').hide();
						$('.close').unbind('click');
					});
					// 拖动弹窗
					$(document).on('mousedown.detail', '#details', function (evt) {
						var evt = window.event || event;
						evt.target = evt.srcElement ? evt.srcElement : evt.target;
						var startx = evt.clientX,
							starty = evt.clientY,
							startLeft = $('#details').position().left,
							startTop = $('#details').position().top;
						$(document).mousemove(function (evt) {
							var evt = window.event || event;
							evt.target = evt.srcElement ? evt.srcElement : evt.target;
							var leftOffset = evt.clientX - (startx - startLeft),
								topOffset = evt.clientY - (starty - startTop);
							$('#details').css({
								left: leftOffset,
								top: topOffset
							})
						});
						$(document).mouseup(function () {
							$(document).off('mousemove');
						})
					})
				}
				// 报修单查看页
				function assRepairDistrUpdate(data, index, value) {
					$.etDialog.open({
						url: 'assRepairDistrUpdatePage.do?isCheck=false&rep_code='+data.rep_code,
						isMax:true,
						title: '报修单据查看',
						//btn: ['确定', '取消'],
						btn1: function (index, el) {
							var iframeWindow = window[el.find('iframe').get(0).name];
							iframeWindow.saveData();
						}
					});

				}
				function printData() {
					var useId = 0; //统一打印
					var ass_para_map = "";
					if(ass_para_map == 1) {
						//按用户打印
						useId = '${sessionScope.user_id }';
					}
					//var data = gridManager.getCheckedRows();
					var data = grid.selectGetChecked();
					if(data.length == 0) {
						$.etDialog.error('选择结行');
					} else {
						var rep_codes = "";
						$(data).each(function() {
							var rowdata = this.rowData;
							rep_codes += "'" + rowdata.rep_code + "',"
						});
						var para = {
							rep_codes: rep_codes.substring(0, rep_codes.length - 1),
							template_code: '05313',
							class_name: "com.chd.hrp.ass.serviceImpl.repair.repairdistr.AssRepairDistrServiceImpl",
							method_name: "queryAssRepairCenterPrint",
							isSetPrint: false, //是否套打，默认非套打
							isPreview: true, //是否预览，默认直接打印
							use_id: useId,
							p_num: 1
						};
		
						officeFormPrint(para);
					}
				}
		
				function printSet() {
					var template_code = "";
					var ass_para_map = "";
		
					var useId = 0; //统一打印
					if(ass_para_map == 1) {
						//按用户打印
						useId = '${sessionScope.user_id }';
					}
		
					officeFormTemplate({
						template_code: '05313',
						use_id: useId
					});
				}
				$(function () {
					//initStatus();
					initDict();
					initMainGrid();
				})
			</script>
		</head>

		<body style="position: relative">
			<hr />
			<div class="main" style='position:relative'>
				<table class="table-layout">
					<tr>
						<td class="label">报修日期：</td>
						<td class="ipt">
							<input id="app_time" type="text" style="width:180px" />
						</td>
						<td class="label">报修进度：</td>
						<td class="ipt">
							<select id="state" style="width:180px"></select>
						</td>
						<td class="label">紧急程度：</td>
						<td class="ipt">
							<select id="eme_status" style="width:180px"></select>
						</td>
						<td class="label">报修科室：</td>
						<td class="ipt">
							<select id="rep_dept" style="width:180px"></select>
						</td>
					</tr>
					<tr>
						<td class="label">问题描述：</td>
						<td class="ipt">
							<input id="fau_note" type="text" style="width:180px" />
						</td>
						<td class="label">故障分类：</td>
						<td class="ipt">
							<select id="fau_code" style="width:180px"></select>
						</td>
						<td class="label">报修单号：</td>
						<td class="ipt">
							<input id="rep_code" type="text" style="width:180px"/>
							
						</td>
					</tr>
				</table>
				<div class="togglebtn">
				</div>
			</div>
			<div id="mainGrid"></div>
			<!-- 维修进度弹窗	 -->
			<div id="details" class="dialog" style="display:none">
				<div class="title">维修进度
					<span class="close ">X</span>
				</div>
				<div class="details_content">
					<!-- 维修进度评价 -->
					<div id="details_assess">
					</div>
					<!-- 气泡框 -->
					<div class="qipao">
						<ul>
							<li>电话1：
								<span class="phone1"></span>
							</li>
							<li>电话2：
								<span class="phone2"></span>
							</li>
							<li>班组：
								<span class="team"></span>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div id="cuiDailog" class="dialog" style="display:none">
				<div class="title">催单内容
					<span class="close ">X</span>
				</div>
				<div class="details_content">
					<div class="cui-block">
						<label class="cuilabel">单号：</label>
						<input id="rep_code" type="text" style="width:180px"/>				
					</div>
					<div  class="cui-block">
						<label class="cuilabel" style="vertical-align:top;">催单内容：</label>
						<textarea rows="" cols="" style="width: 178px;height: 70px"></textarea>						
					</div>	
				</div>
			</div>
		</body>

		</html>