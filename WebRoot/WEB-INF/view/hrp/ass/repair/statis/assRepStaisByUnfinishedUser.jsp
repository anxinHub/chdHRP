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
				#details {
					z-index: 999;
					position: absolute;
					width: 400px;
					height: 300px;
					background: #fff;
					border: 1px solid #ddd;
				}

				#details .title {
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
				.details-btns {
					text-align: right;
					padding-right: 60px;
				}
				.details-btns a {
					display:inline-block;
				    font-size: 12px;
				    height: 25px;
				    line-height: 25px;
				    padding: 0 18px;
				    margin-top: 0;
					border-color: #1e9fff;
				    background-color: #1e9fff;
				    color: #fff;
				}
				.details-btns a:hover {
					cursor:pointer;
					/* background: #f0f0f0; */
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
				<jsp:param value="dialog,grid,select,datepicker" name="plugins" />
			</jsp:include>
			<script src="<%=path %>/lib/starRaty/lib/jquery.raty.min.js" type="text/javascript"></script>
			<script>
				var app_time,
					state,
					eme_status,
					rep_dept,
					fau_note,
					fau_code,
					rep_code,
					grid,
					gridRowData;

				function initMainGrid() {
					var gridObj = {
						editable: false,
						checkbox: true,
						height: '100%',
						addRowByKey: true, //  快捷键控制添加行
						selectionModel: {
							type: 'row',
							mode: 'block'
						}
					};
					gridObj.columns = [{
							display: "操作",
							width: 120,
							align: 'lift',
							name: "m",
							editable: false,
							render: function (ui) { // 修改页打开
								return '<a data-item=' + ui.rowIndx + ' data-col=' + ui.dataIndx + ' class="td-a">维修进度</a>'
							}
						},
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
							display: '紧急程度',
							align: "left",
							name: 'eme_status',
							//cls: 'g_red',
							width: 120,
							render: function (ui) { // 修改页打开
								if(ui.rowData.eme_status==='一般'){
									return '<span>'+ui.cellData+'</span>'
								}else{
									return '<span style="color: red">'+ui.cellData+'</span>' 
								}
								
							}
							
						},
						{
							display: "报修进度",
							align: "left",
							width: 120,
							name: "state"
						},
						{
						display: "报修时间",
						align: "left",
						width: 120,
						name: "app_time"
						},
						{
							display: "接单时间",
							width: 120,
							align: "left",
							name: "order_time"
						},
						{
							display: "完成时间",
							width: 120,
							align: "left",
							name: "comp_time"
						},
						{
							display: "维修(小时)",
							align: "right",
							width: 120,
							name: "rep_hours",
							render: function(ui) {
								return '';
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
							display: "维修标识",
							width: 120,
							align: "left",
							name: "rep_bz"
						},
						{
							display: "卡片编号",
							align: "lift",
							width: 120,
							name: "ass_card_no",
							editable: false,
							render: function (ui) { // 修改页打开
								if(ui.cellData){
									return '<a data-item=' + ui.rowIndx + ' data-col=' + ui.dataIndx + ' class="td-a">' + ui.cellData +
									'</a>'
								}else{
									return '无'
								}
							}
						},
						{
							display: "资产名称",
							width: 120,
							align: "left",
							name: "ass_name"
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
							assMyRepairUpdate(data, index, value);
						} else if (dataIndx === 'ass_card_no') {
							// 卡片编号页面打开
							queryCard(data, index, value)
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
					console.log(parent.parmVo)
 					grid.loadData(parent.parmVo,'queryAssRepUnfinishedReport.do?isCheck=false');
				}
				 
				function cuiDailog(data) {
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
				function assMyRepairUpdate(data, index, value) {
					$.etDialog.open({
						url: 'assRepReportCentreUpdatePage.do?isCheck=false&rep_code='+data.rep_code,
						isMax:true,
						title: '报修单据修改页',
						btn: ['关闭'],
						btn2: function (index, el) {
							var iframeWindow = window[el.find('iframe').get(0).name];
							iframeWindow.saveData();
						}
					});

				}
				// 弹出资产卡片页面
				function queryCard(data, index, value) {
					var parm = '';
					$.etDialog.open({
						url: 'assCardMainPage.do?isCheck=false&ass_card_no='+value,
						height: 500,
						width: 700,
						title: '资产卡片页',
						btn: ["关闭"],
						btn1: function (index) {
							$.etDialog.close(index); // 关闭弹窗
							return false;
						}
					});
				}
				// 时间轴渲染
				function timeLineRender(data) {
					ajaxPostData({
						url: "queryTimeLineRender.do?isCheck=false",
						data:{rep_code: data.rep_code},
						success: function (res) {
							var data = res;
							var $details_ul = $('<div id="details_ul"></div>');
							var svgns = "http://www.w3.org/2000/svg";
							$.svg = function $svg(tagName) {
								return $(document.createElementNS(svgns, tagName));
							};
							if ($('#details_ul').length) {
								$('#details_ul').empty();
								$details_ul = $('#details_ul');
							}

							$(data).each(function (index, item) {
								var $li = $("<li></li>");
								var $img = $('<i></i>');
								var $status = $('<span class="d_status"></span>');
								var $time = $('<span class="timer"></span>');
								var $text = $('<span class="text"></span>');
								var $svg = $.svg('svg');
								var $circle = $.svg('circle');

								$svg.attr({
									height: 20,
									width: 20
								});
								$circle.attr({
									cx: 5,
									cy: 10,
									r: 4,
									fill: '#ddd'
								});
								if (index === 0) {
									$li.addClass('frist');
									$circle.attr({
										fill: 'rgb(139, 195, 74)',
										cy: 5,
										r: 5
									});
								} else if (index === data.length - 1) {
									$li.addClass('end');
									$circle.attr({
										cy: 5
									});
								}
								$svg.append($circle);
								$img.append($svg);

								$status.text(item.state_name);
								$time.text(item.task_time);
								$text.text('维修人员：' + item.user_name);
								$text.attr({
									phone1: item.phone1,
									phone2: item.phone2,
									team: item.team_name
								})

								$li.append($img);
								$li.append($status);
								$li.append($text);
								$li.append('<br>');
								$li.append($time);
								$details_ul.append($li);
							})

							$details_ul.prependTo($('.details_content'));
							qipaoShow();
						}
					});
				}
				// 星星与评价渲染
				function starRatyRender(data) {
					gridRowData = data;
					var details_message = $('<div id="details_message"></div>');
					$('#details_assess').empty();
					$('#details_assess').prepend('评分等级：<div id="star" style="display:inline-block;vertical-align:middle"></div>');
					$('#details_assess').append(
						'<div id="details_m_c" style="margin-top:10px"><span style="display: inline-block;vertical-align: top;">评价：</span></div>');
					$('#details_m_c').append(details_message);

					$.fn.raty.defaults.path = '<%=path %>/lib/starRaty/lib/img'; // 配置星星图片路径
					$('#star').raty({
						//取SCORE
						score: data.score,
						readOnly: true
					});
					if(!data.score_note){
						data.score_note='';
					}
					details_message.text(data.score_note);
					var is_any = false;
					if(data.is_any==1){
						is_any=true;
					}
					$('#scoreCheck').attr("checked",is_any)

				}
				// 气泡移入移出方法
				function qipaoShow() {
					var qipao2 = $('.qipao').clone();
					$('#details').on('mouseover', '.text', function (evt) {
						var topOffset = $(this).position().top - 10;
						var leftOffset = $(this).position().left + 29 + $(this).outerWidth();
						evt = window.event || event;
						var target = evt.srcElement ? evt.srcElement : evt.target;
						if (target.className === 'text') {
							var phone1 = $(target).attr('phone1');
							var phone2 = $(target).attr('phone2');
							var teamN = $(target).attr('team');
						}
						// 气泡赋值
						$('.phone1').text(phone1);
						$('.phone2').text(phone2);
						$('.team').text(teamN);

						qipao2.appendTo($(this)).css({
							left: leftOffset,
							top: topOffset
						});
						qipao2.show()
					});

					$('#details').on('mouseout', '.text', function () {
						qipao2.stop().hide();
					});
				}
			 
				$(function () {
					//initStatus();
					initMainGrid();
					search();
				})
			</script>
		</head>

		<body style="position: relative">
			<hr />
			<div id="mainGrid"></div>
			<!-- 维修进度弹窗	 -->
			<div id="details" style="display:none">
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
		</body>

		</html>