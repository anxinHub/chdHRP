<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="dialog, select, grid, checkbox, validate" name="plugins" />
        </jsp:include>
        <script>
            var checkBox_1, checkBox_2, grid, validate, rep_bz, rep_comp, rep_comp_user, ass_card_no, ass_name, rep_note;

            function initValidate() {
                validate = $.etValidate({
                    items: [
                        { el: $("#rep_bz"), required: true },
                        { el: $("#rep_comp"), required: false },
                        { el: $("#rep_comp_user"), required: false },
                        { el: $("#ass_card_no"), required: true },
                        { el: $("#ass_name"), required: true },
                        { el: $("#rep_note"), required: true },
                    ]
                });
            };
            function initDict() {
                checkBox_1 = $('#checkBox_1').etCheck({
                    onChange: function (status, checked, disabled) {
                    },
                });
                checkBox_2 = $('#checkBox_2').etCheck({
                    onChange: function (status, checked, disabled) {
                        if (checked) {
                            // 勾选没有卡片，关闭资产卡片验证
                        	$('#card_no').removeClass("no-empty");
                        	$('#ass_name').removeAttr('disabled');
                            ass_card_no.setValue('none');
                            ass_card_no.disabled();
                            validate.closeValidate(ass_card_no);
                        } else {
                        	$('#card_no').addClass("no-empty")
                        	$('#ass_name').attr('disabled','disabled');
                            ass_card_no.enabled();
                            validate.openValidate(ass_card_no);
                        }
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
                    defaultValue: "none",
                    onChange: function (value) {
                        // 模拟当维修标识值为外部维修时，关闭维修公司、维修工程师验证
                        if (value == 1) {
                        	$('#comp_user').removeClass("no-empty");
                        	$('#comp').removeClass("no-empty");
                        	$('#rep_comp_user').attr('disabled','disabled');
                        	$('#rep_comp').attr('disabled','disabled');
                        	$('#rep_comp').val('');
                        	$('#rep_comp_user').val('');
                            validate.closeValidate(rep_comp);
                            validate.closeValidate(rep_comp_user);
                        } else {
                        	$('#rep_comp_user').removeAttr('disabled');
                        	$('#rep_comp').removeAttr('disabled');
                        	$('#comp_user').addClass("no-empty")
                        	$('#comp').addClass("no-empty")
                            validate.openValidate(rep_comp);
                            validate.openValidate(rep_comp_user);
                        }
                    }
                });
                // 维修公司
                rep_comp = $("#rep_comp");
                // 维修工程师
                rep_comp_user = $('#rep_comp_user');
                // 资产卡片
                ass_card_no = $("#ass_card_no").etSelect({
                	url: "../../queryAssCardNoDictSelect.do?isCheck=false&use_state=1,2,3,4,5",
                	defaultValue: "${ass_card_no}",
	          		onChange: function (value) {
	          			   $('#ass_name').val($('#ass_card_no').text().split(' ')[1]);
		          		}
                });
                // 资产名称
                ass_name = $('#ass_name').val('${ass_name}');
                // 维修结果
                rep_note = $('#rep_note');
            }

            function initGrid() {
                var gridObj = {
                    height: '100%',
                   	editable: true,
                   	checkbox: true,
                   	usePager:true,
                   	pageModel:{type:'remote'},
                    selectionModel: {
                        type: 'row',
                        mode: 'block'
                    },
                    dataModel: {     // 加载数据模块
	                    location: "remote",  // 若是后台数据 传入URL值 同时location 为 remote
						url:'',
	                },
                };
                gridObj.columns = [{
                        display: "维修材料",
                        width: 240,
                        name: "INV_NAME"
                    },
                    {
                        display: "材料分类",
                        width: 120,
                        name: "MAT_TYPE_NAME"
                    },
                    {
                        display: '品牌',
                        name: 'BRAND_NAME',
                        width: 120
                    },
                    {
                        display: "规格型号",
                        align: "left",
                        width: 120,
                        name: "INV_MODEL"
                    },
                    {
                        display: "数量",
                        width: 120,
                        align: 'center',
                        name: "AMOUNT",
                       	dataType :'float'
                    }
                ];
                gridObj.toolbar = {
                		items: [{	
                			type: "button",
							label: '查询',
							icon: 'search',
							id: 'search',
							listeners: [{
								click: search
							}]
                		}]
                };

                grid = $("#mainGrid").etGrid(gridObj);

                $('#mainGrid').on('click', '.td-a', function (evt) {
                    var index = $(this).attr('data-item') * 1;
                    var dataIndx = $(this).attr('data-col');
                    var data = grid.getRowData(index);
                    var value = $(this).text();
                    // 点击a标签时不让它选中
                    grid.selectRemove(index);
                    if (dataIndx === 'a') {
                        // 报修单号页面打开
                        queryRepairOrder(data, index, value);
                    } else if (dataIndx === 'j') {
                        // 卡片编号页面打开
                        queryCard(data, index, value)
                    } else {
                        // 维修进度页面打开
                        queryDetails(data, index, value, evt)
                    }
                    return false;
                })
            }
            
            function search(){
            	var param = [{
					name: 'inv_name',
					value: $('#inv_name').val()
				},
			];
			grid.loadData(param,'queryMatInvDict.do?isCheck=false');
            }
            
            function saveData(value) {
            	 var formData = [];
                var gridData = grid.selectGet();
                var ParamVo = [];
                var isTrue = false ; 
	            $(gridData).each(function () {
	                var rowdata = this.rowData;
	                if(!rowdata.amount){
	                	   $.etDialog.error('请维护【'+rowdata.inv_name+'】材料数量');
	                	   isTrue=true
	                	   return false;
	                }
	                ParamVo.push(rowdata.inv_id+' '+rowdata.amount);
	            });
	            if(isTrue){
	            	return;
	            }
                if (!validate.test()) {
                    return;
                }
                formData.push({name:'task_user',value:${user_id}})
                formData.push({name:'rep_code',value: value[0].rowData.rep_code})
                formData.push({name:'ass_card_no',value: $('#ass_card_no').val()})
                formData.push({name:'ass_name',value:  $('#ass_name').val()})
                formData.push({name:'rep_bz',value:  $('#rep_bz').val()})
                formData.push({name:'rep_comp_user',value: $('#rep_comp_user').val()})
                formData.push({name:'rep_comp',value:  $('#rep_comp').val()})
                formData.push({name:'rep_note',value: $('#rep_note').val()})
                formData.push({name:'invdata',value: ParamVo})
                formData.push({name:'is_base',value: $('#checkBox_1').get(0).checked})
                formData.push({name:'is_card',value: $('#checkBox_2').get(0).checked})
                ajaxPostData({
                    url: 'updateEndRepairState.do?isCheck=false',
                    data:formData,
                    success: function () {
                        parent.query();
                    }
                })
            }
            $(function () {
                initDict();
                initValidate();    
                initGrid();
                search();
            })
        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label no-empty">维修标识：</td>
                    <td class="ipt" style="width:310px">
                        <select id="rep_bz" style="width:180px"></select>&nbsp;&nbsp;
                        <input type="checkbox" id="checkBox_1">
                        <label for="checkBox_1">进入故障知识库</label>
                    </td>
                    <td class="label no-empty" id="card_no" >资产卡片：</td>
                    <td class="ipt">
                        <select id="ass_card_no" style="width:180px" ></select>&nbsp;&nbsp;
                    	<input type="checkbox" id="checkBox_2" />
                        <label for="checkBox_2">没有卡片</label>
                    </td>
                </tr>
                <tr>
                    <td class="label" id="comp_user" >维修工程师：</td>
                    <td class="ipt">
                        <input type="text" id="rep_comp_user" />
                    </td>
                    <td class="label" id="comp">维修公司：</td>
                    <td class="ipt">
                        <input id="rep_comp" type="text" />
                    </td>
                </tr>
                <tr>
                    <td class="label no-empty" id="card_name">资产名称：</td>
                    <td class="ipt">
                        <input type="text" id="ass_name"  disabled="disabled"  />
                    </td>
                    <td class="label">维修材料：</td>
                    <td class="ipt">
                        <input id="inv_name" type="text" />
                    </td>
                </tr>
                <tr>
                	<td class="label">维修内容：</td>
                    <td class="ipt">
                        <textarea style="width: 350px;height:50px" name="rep_note" id="rep_note" cols="30" rows="10">完成</textarea>
                    </td>
                </tr>
                <tr>
                	<td class="label">问题描述：</td>
                    <td class="ipt">
                        <textarea style="width: 350px;height:50px" name="fau_note" id="fau_note" cols="30" rows="10">${fau_note }</textarea>
                    </td>
                </tr>
            </table>
        </div>
        <div id="mainGrid"></div>
    </body>

    </html>