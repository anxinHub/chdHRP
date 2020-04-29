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
			<style>
				.imgwrap {
					display: inline-block;
					width: 100px;
					height: 100px;
					border: 1px solid #aecaf0
				}

				.imgs {
					display: flex;
					justify-content: space-around;
				}

				.imgwrap img {
					cursor: pointer;
					background-color: #fff;
					border: 1px solid #aecaf0;
					transition: all 0.4s;
				}

				.imgwrap img:hover {
					transform: scale(1.4);
				}
			</style>
			<jsp:include page="${path}/resource.jsp">
				<jsp:param value="dialog,grid,select,validate,tab,upload checkbox" name="plugins" />
			</jsp:include>
			<script>
				var grid, validate, checkBox_2, checkBox_1, rep_code, eme_status, rep_user, rep_dept, phone, loc_code, fau_code, fau_note, rep_bz, ass_card_no,
					ass_name, reason;
				var pathName= [];
				function initDict1() {
					// 报修单号
					rep_code = $("#rep_code");
					// 紧急程度
					eme_status = $("#eme_status").etSelect({
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
	                     defaultValue: "${eme_status}"
					});
					// 报修人
					 rep_dept = $("#rep_dept").etSelect({
                     url: '../../queryDeptDict.do?isCheck=false',
                     defaultValue: '${rep_dept}',
                     onChange:function(value){
                    	 reloadSelect(value)
                    	 
                     },
                     onInit: function (value) {
                    	 reloadSelect(value)
                     }
                    
                 });
                 rep_user = $("#rep_user").etSelect({
                	 url: '../../queryUserDict.do?isCheck=false&key='+'${user_id}',
                     defaultValue: "${rep_user}"/* ,
                     onInit:function(value){
                    	 ajaxPostData({
                             url: '../../queryDeptDictInitValue.do?isCheck=false&user_id='+'${user_id}',
                             delayCallback: true,
                             success: function (data) {
                            	 
                            	 
                             }
                         })
                     } */
                }); 
	                 ass_card_no = $("#ass_card_no").etSelect({
	     				defaultValue: '${ass_card_no}',
	     				onChange : function(value){
	     					backwashData(value)
	     				}
	     			});
					// 报修人电话
					phone = $("#phone");
					// 位置
					 loc_code = $("#loc_code").etSelect({
	                     url: '../../querySuperLocationSelect.do?isCheck=false&is_last=1',
	                     defaultValue: "${loc_code}"
	                 });
					// 故障分类
					fau_code = $("#fau_code").etSelect({
						url: '../../querySuperFaultTypeSelect.do?isCheck=false',
					  defaultValue: "${fau_code}"
					});
					// 故障内容
					fau_note = $("#fau_note");
					
					 file = $('#file').etUpload({
		                   multiple: true
		               });
				}
				   function backwashData(data){
	 					$('#ass_name').val($('#ass_card_no').text().split(' ')[1])
	 			 }
				   function reloadSelect (value){
						ass_card_no.reload({
							url: "../../queryAssCardNoDictSelect.do?isCheck=false",
							type: "POST",
							para: {
								 dept_id: value,
								 use_state: '1,2,3,4,5'
							}, 
						});
					}
				function initDict2() {
					// 进入故障知识库
					checkBox_1 = $('#checkBox_1').etCheck({
						onChange: function (status, checked, disabled) {
							if (checked) {
								// 勾选 则改变
								/* rep_bz.reload({
									url: '',
									data: {}
								}) */
							}
							// console.log('status', status, "checked", checked, "disabled", disabled)
						},
					});
					// 没有卡片
					checkBox_2 = $('#checkBox_2').etCheck({
						onChange: function (status, checked, disabled) {
							if (checked) {
								// 勾选没有卡片，关闭资产卡片验证
								validate.closeValidate(ass_card_no);
							} else {
								validate.openValidate(ass_card_no);
							}
							// console.log('status', status, "checked", checked, "disabled", disabled)
						},
					});
					// 维修标识
					rep_bz = $("#rep_bz").etSelect({
						 options: [{
	                          id: 1,
	                          text: '内部维修'
	                      }, {
	                          id: 2,
	                          text: '外部维修'
	                      }],
	                     defaultValue: "${rep_bz}"
					});
					// 资产名称
					ass_name = $('#ass_name');
					// 维修结果
					reason = $('#reason');
				}

				function initGrid() {
					var gridObj = {
						editable: false,
						height: '100%',
						inWindowHeight: true,
						selectionModel: {
							type: 'row',
							mode: 'block'
						}
					};
					gridObj.columns = [{
							display: "维修材料",
							width: 120,
							name: "inv_name"
						},
						{
							display: "材料分类",
							width: 120,
							name: "mat_type_name"
						},
						{
							display: '品牌',
							name: 'brand_name',
							width: 120
						},
						{
							display: "规格型号",
							align: "left",
							width: 120,
							name: "inv_model"
						},
						{
							display: "数量",
							width: 120,
							align: 'center',
							name: "amount"
						}
					];
					gridObj.dataModel = { // 数据加载的有关属性
						url: 'queryAssRepInv.do?isCheck=false&rep_code='+${rep_code},
						recIndx: 'a'
					};
					grid = $("#mainGrid").etGrid(gridObj);
				}
				// 验证
				function initValidate() {
					validate = $.etValidate({
						items: [{
							el: $("#reason"),
							required: true,
							text: /[0-9a-zA-Z\u0391-\uFFE5]{10,}/ig,
							emptyTip: '不能少于十个字'
						}, {
							el: $("#rep_bz"),
							required: true,
						}, {
							el: $("#ass_card_no"),
							required: true,
						}, {
							el: $("#ass_name"),
							required: true,
						}]
					});
				};

				 
				function renderImgs() {
					<%-- var imgs = $('<div class ="imgs"></div>');
					var imgT = $('.imgwrap').clone();
					// var arr = ['<%=path %>/lib/ligerUI/skins/icons/toggle2.png', '<%=path %>/lib/ligerUI/skins/icons/toggle2.png',
					// 	'<%=path %>/lib/ligerUI/skins/icons/toggle2.png', '<%=path %>/lib/ligerUI/skins/icons/toggle2.png', '<%=path %>/lib/ligerUI/skins/icons/toggle2.png'
					// ]
					$('#images').empty();
					$(arr).each(function (index, item) {
						var imgW = imgT.clone();
						imgW.find('img').attr('src', item);
						imgs.append(imgW);
					})
					$('#images').append(imgs); --%>
					ajaxPostData({
	                    url: "queryImgUrlByRepCode.do?isCheck=false",
	                    data: {'rep_code':${rep_code}},
	                    success: function (res) {
	                        	$(res).each(function(index,item){
	                        		pathName.push('<%=path%>/'+item.ATT_PATH+'/'+item.ATT_NAME_N)
	                        	})
	                        		file.setValues(pathName); 
	                        
	                    }
	                })
				}
				$(function () {
					// 生成tab页标签
					$("#etTab").etTab({
						onChange: function (obj) {
							if (obj.tabid === '1' && !grid) {
								initGrid();
							}
						}
					});
					// 报修信息tab页 下拉框
					initDict1();
					// loadImg();
					renderImgs();
					// 维修信息tab页 下拉框
					initDict2();
					initValidate();
					$('#save').click(function () {
						saveData();
					})
				})
			</script>
		</head>

		<body>
			<div id="etTab" style="height:100%">
				<div title="报修信息">
					<div class="main">
						<table class="table-layout">
							<tr>
								<td class="label">报修单号：</td>
								<td class="ipt">
									<input id="rep_code" type="text" disabled value="${rep_code}">
								</td>
								<td class="label">紧急程度：</td>
								<td class="ipt">
									<select id="eme_status" type="text" disabled style="width: 180px"></select>
								</td>
							</tr>
							<tr>
								<td class="label">报修人：</td>
								<td class="ipt">
									<select name="rep_user" id="rep_user" disabled style="width: 180px"></select>
								</td>
								<td class="label">报修科室：</td>
								<td class="ipt">
									<select name="rep_dept" id="rep_dept" disabled style="width: 180px"></select>
								</td>
							</tr>
							<tr>
								<td class="label">报修人电话：</td>
								<td class="ipt">
									<input id="phone" type="text" disabled value="${phone}"/>
								</td>
								<td class="label">位置：</td>
								<td class="ipt">
									<select name="loc_code" id="loc_code" disabled style="width: 180px"></select>
								</td>
							</tr>
							<tr>
								<td class="label">故障分类：</td>
								<td class="ipt">
									<select id="fau_code" type="text" disabled style="width: 180px"></select>
								</td>
							</tr>
							<tr>
								<td class="label" style="vertical-align: top">故障内容：</td>
								<td class="ipt">
									<textarea name="fau_note" id="fau_note" readonly cols="30" rows="10">${fau_note}</textarea>
								</td>
							</tr>
							 <tr>
			                    <td class="label">文件：</td>
			                    <td class="ipt" colspan="4">
			                        <div id="file"></div>
			                    </td>
			                </tr>
						</table>
					</div>
				</div>
				<div id="inv_data" title="维修信息">
					<div class="main" style="position:relative">
						<table class="table-layout" style="width: 100%">
							<tr>
								<td class="label no-empty">维修标识：</td>
								<td class="ipt" style="width:310px">
									<select id="rep_bz" type="text" style="width:180px"></select>&nbsp;&nbsp;
									<input type="checkbox" id="checkBox_1">
									<label for="checkBox_1">进入故障知识库</label>
								</td>
							</tr>
							<tr>
								<td class="label no-empty">资产卡片：</td>
								<td class="ipt">
									<select id="ass_card_no" type="text" style="width:180px"></select>&nbsp;&nbsp;
									<input type="checkbox" id="checkBox_2" />
									<label for="checkBox_2">没有卡片</label>
								</td>
							</tr>
							<tr>
								<td class="label no-empty">资产名称：</td>
								<td class="ipt">
									<input type="text" id="ass_name" />
								</td>
							</tr>
							<tr>
								<td class="label">维修结果：</td>
								<td class="ipt">
									<textarea style="width: 350px;height:50px;" name="reason" id="reason"  cols="30" rows="10">${reason}</textarea>
								</td>
							</tr>
						</table>
					</div>
					<div id="mainGrid"></div>
				</div>
			</div>
		</body>

		</html>