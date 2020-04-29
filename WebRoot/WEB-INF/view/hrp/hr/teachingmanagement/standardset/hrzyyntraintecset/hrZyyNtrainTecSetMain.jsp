<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="hr,dialog,grid,select,datepicker,validate,pageOffice" name="plugins" />
        </jsp:include>
        <script>
            var grid, yaer;
            var initGrid = function () {
                var columns = [
                    { display: '年度', name: 'year', width: 100,editor:getRecentYearForSelect(),
                    	editable : function (col){
               			    if(col.rowData){
               				    if(col.rowData.hos_id != null && col.rowData.group_id != null){
               					    return false;
               				    }
               				        return true;
               			    }else{
               				    return true;
               			    }
               		    }
                    },
                    { display: '会计签到', name: 'meet_sign', width: 100 },
                    { display: '技能培训', name: 'skill_train', width: 100 },
                    { display: '技能考核', name: 'skill_eval', width: 100 },
                    { display: '病种考核', name: 'drg_eval', width: 100 },
                    { display: '出科考试', name: 'ade', width: 100 },
                    { display: '网络登记', name: 'online_reg', width: 100 },
                    { display: '核心课程前后测', name: 'core_class_test', width: 100 },
                    { display: '科室评价', name: 'dept_eval', width: 100 },
                    { display: '总得分', name: 'tot_score', width: 100 },
                    { display: '备注', name: 'note', width: 100 },
                ];
                var paramObj = {
                    height: '100%',
                    inWindowHeight: true,
                    checkbox: true,
                    editable: function (ui) {
                        // 第一条不可编辑
                        return ui.rowIndx !== -1; 
                    },
                   /*  dataModel: {
                        url: ''
                    }, */
                    columns: columns,
                    toolbar: {
                        items: [
                            { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                            { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                            { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                            { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                            { type: 'button', label: '计算', listeners: [{ click: math }], icon: 'calculate' },
                            /* { type: 'button', label: '导入', listeners: [{ click: putin }], icon: 'import' },
                            { type: 'button', label: '导出', listeners: [{ click: putout }], icon: 'export' }, */
                            { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
                        ]
                    }
                };
                grid = $("#mainGrid").etGrid(paramObj,'queryHrZyyNtrainTecSet.do');
            };

             var initFrom = function () {
            	 year = $("#year").etDatepicker({
                     view: "years",
                     minView: "years",
                     dateFormat: "yyyy",
                     onChange: query
                 });
                /* record_select = $("#record_select").etSelect({
                    url: 'queryEducationComboBox.do?isCheck=false',
                    defaultValue: 'none',
                    onChange: query
                });

                profession_select = $("#profession_select").etSelect({
                    url: 'queryProfessionalComboBox.do?isCheck=false',
                    defaultValue: 'none',
                    onChange: query
                });

                dept_select = $("#dept_select").etSelect({
                    url: 'queryDeptComboBox.do?isCheck=false',
                    defaultValue: 'none',
                    onChange: query
                }); */
            } 

            var query = function () {
                var params = [
                    { name: 'year', value: year.getValue() }
                ];
                grid.loadData(params, "queryHrZyyNtrainTecSet.do");
            };
            var print = function () { 
            	if(grid.getAllData()==null){
	        		$.etDialog.error("请先查询数据！");
	    			return;
    			}
        		var heads={
            		 /* "isAuto":true,//系统默认，页眉显示页码
            		"rows": [
        	          {"cell":0,"value":"表名："+tree.getSelectedNodes()[0].name},
            		]  */}; 
	        	var printPara={
	                title: "住院医师规培轮转成绩标准设置（医技）打印",//标题
	                columns: JSON.stringify(grid.getPrintColumns()),//表头
	                class_name: "com.chd.hrp.hr.service.teachingmanagement.standardset.HrZyyNtrainTecSetService",
	                method_name: "queryZyyNtrainTecSetByPrint",
	                bean_name: "hrZyyNtrainTecSetService",
	                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
	                foots: '',//表尾需要打印的查询条件,可以为空 
	            };
	            $.each(grid.getUrlParms(),function(i,obj){
	                printPara[obj.name]=obj.value;
	            }); 
	            //console.log(printPara);
	            officeGridPrint(printPara);   		
	            	
	    }
        var putout = function () { 
        	exportGrid(grid);
        }
	  <%--var putin = function () { 
		  var para = {
	    			"column" : [ {
	    				"name" : "year",
	    				"display" : "年度",
	    				"width" : "200",
	    				"require" : true
	    			},{
	    				"name" : "dept_id",
	    				"display" : "轮转科室",
	    				"width" : "200"
	    			} ,{
	    				"name" : "teacher",
	    				"display" : "带教老师",
	    				"width" : "200"
	    			},{
	    				"name" : "meet_sign",
	    				"display" : "会议签到",
	    				"width" : "200"
	    			} ,{
	    				"name" : "skill_train",
	    				"display" : "技能培训",
	    				"width" : "200"
	    			} ,{
	    				"name" : "skill_eval",
	    				"display" : "技能考核",
	    				"width" : "200"
	    			} ,{
	    				"name" : "drg_eval",
	    				"display" : "病种考核",
	    				"width" : "200"
	    			} ,{
	    				"name" : "ade",
	    				"display" : "出科考试",
	    				"width" : "200"
	    			},{
	    				"name" : "online_reg",
	    				"display" : "网络登记",
	    				"width" : "200"
	    			},{
	    				"name" : "core_class_eval",
	    				"display" : "核心课程考核",
	    				"width" : "200"
	    			},{
	    				"name" : "core_class_test",
	    				"display" : "核心课程前后测评",
	    				"width" : "200"
	    			},{
	    				"name" : "dept_eval",
	    				"display" : "科室评价",
	    				"width" : "200"
	    			},{
	    				"name" : "tot_score",
	    				"display" : "总得分",
	    				"width" : "200"
	    			},{
	    				"name" : "note",
	    				"display" : "备注",
	    				"width" : "200"
	    			}]

	    		};
	    		importSpreadView("/hrp/hr/teachingmanagement/standardset/hrzyyntraintecset/importZyyNtrainTecSet.do?isCheck=false", para);

	            	
	   }--%>
            var save = function () {
            	  var ParamVo = [];
            	   
                  var data = grid.selectGet();
                  if (data.length == 0) {
                      $.etDialog.error('请选择行');
                      return;
                  } else {
                	  var isPass = grid.validateTest({
                          required: {
                       	  	  year: true
                          }
                      });
                	  if(!isPass){
                		  return;
                	  }
                        $(data).each(function () {
                            var rowdata = this.rowData;
                            ParamVo.push(rowdata);
                        });
                       }
                  //验证重复数据
              	if (!grid.checkRepeat(grid.selectGet(), ['year'])){
                    return;
                }
                ajaxPostData({
                    url: 'addOrUpdateHrZyyNtrainTecSet.do',
                    data: {
                        paramVo: JSON.stringify(ParamVo)
                    },
                    success: function () {
                        query();
                    }
                })
            };

            var add = function () {
                grid.addRow();
            };

            var remove = function () {
                var data = grid.selectGet();
                if (data.length == 0) {
                    $.etDialog.error('请选择行');
                } else {
                    var param = [];
                    $(data).each(function () {
                        var rowdata = this.rowData;
                        param.push(rowdata);
                    });

                    $.etDialog.confirm('确定删除?', function () {
                        ajaxPostData({
                            url: 'deleteHrZyyNtrainTecSet.do',
                            data: {
                                paramVo: JSON.stringify(param)
                            },
                            success: function () {
                                grid.deleteRows(data);
                                tree.reAsyncChildNodes(null, 'refresh');
                            }
                        })
                    });
                }
            };
            
            var math = function () {
                var gridAllData = grid.getAllData();
                if (!gridAllData || gridAllData.length === 0) {
                    return;
                }

                ajaxPostData({
                    url: 'mathHrZyyNtrainTecSet.do',
                    data: {
                        paramVo: JSON.stringify(gridAllData)
                    },
                    success: function () {
                        query();
                    }
                })
            };
           

            $(function () {
            	initFrom();
                initGrid();
                query();
            });



        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                <tr>
                  <td class="label">年度：</td>
                 <td class="ipt">
                    <input id="year" type="text" />
                 </td>
                </tr>
            </table>
        </div>
        <div id="mainGrid"></div>
    </body>

    </html>