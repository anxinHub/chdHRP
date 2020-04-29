<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,datepicker,grid,dialog" name="plugins" />
    </jsp:include>
    <script>
        var year;
        var grid;
        var initFrom = function () {
            year = $("#year").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                defaultDate: ['yyyy'],
                onChange: query
            });
        };
        var query = function (yearProp) {
            var params = [
            	 { name: 'year', value: year.getValue() },
            ];
            grid.loadData(params, 'queryLevelSet.do');
        };
        var generate = function () {
       /*      var yearValue = year.getValue();

            if (!yearValue) {
                $.etDialog.error('请选择年度');
                return;
            }

            ajaxPostData({
                url: 'generateLevelSet.do?isCheck=false',
                data: {
                    paramVo: JSON.stringify(grid.getAllData)
                },
                success: function () {

                }
            }) */
       	 grid.loadData([], 'generateLevelSet.do?isCheck=false');

        };
        var inherit = function () {
        	   ajaxPostData({
                   url: 'inheritLevelSet.do?isCheck=false',
                   success: function () {
                	   query();
                   }
                   })
        };
        var save = function () {
        	  var ParamVo = [];
              var data = grid.selectGet();
              if (data.length == 0) {
                  $.etDialog.error('请选择行');
                  return;
              } else {
                    $(data).each(function () {
                        var rowdata = this.rowData;
                        ParamVo.push(rowdata);
                    });}
             //验证重复数据
        	if (!grid.checkRepeat(
        			data,
        			['level_name','year','education']
        	)		
        	) {
               return;
           } 
           var isPass = grid.validateTest({
				required: {
					year :true,
					level_name:true
				}
			})
			if (!isPass) {
				return;
			}
            ajaxPostData({
                url: 'addLevelSet.do',
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
            var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            var param = [];
            selectData.forEach(function (item) {
                param.push({
                	year: item.rowData.year,
                	level_code:item.rowData.level_code
                });
            })
$.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                 url: 'deleteLevelSet.do',
                data: { paramVo: JSON.stringify(param) },
                success: function () {
                    grid.deleteRows(selectData);
                }
            })
});
        };
        var initGrid = function () {
            var yearEditor = getRecentYearForSelect();
            var columns = [
                {
                    display: '年份', name: 'year', width: 120,
                    editor: yearEditor,
                    editable : function(col){
                    	if(col.rowData){
                    		if(col.rowData.group_id != null && col.rowData.hos_id != null){
                                return false;
                            }
                            return true;
                        }else{
                            return true;
                        }
                    }
                },
                {
                    display: '阶别', name: 'level_name', width: 80,
                    editor: {
                        type: 'select',
                        keyField: 'level_code',
                        url: '../queryDicLevel.do?isCheck=false',
                    }
                },
                { display: '年度在职教育', name: 'education', width: 120 ,
	        		editor: {
	    				type: 'number'
	                }
},
                {
                    display: '学术能力', columns: [
                        { display: '读书报告', name: 'book_report', width: 120,
			        		editor: {
			    				type: 'number'
			                }
},
                        { display: '案例分析', name: 'case_analy', width: 120 ,
	editor: {
		type: 'number'
    }
},
                        { display: '个案报告分析', name: 'special_case_analy', width: 120 ,
	editor: {
		type: 'number'
    }
},
                    ]
                },
                { display: 'CPR考核成绩', name: 'cpr_score', width: 100 ,
	        		editor: {
	    				type: 'number'
	                }
},
                { display: '同事评价成绩', name: 'peer_score', width: 100,
	editor: {
		type: 'number'
    }
 },
                { display: '笔试成绩', name: 'write_score', width: 100,
		editor: {
			type: 'number'
        }
 },
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '生成', listeners: [{ click: generate }], icon: 'add' },
                        { type: 'button', label: '继承', listeners: [{ click: inherit }], icon: 'add' },
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        $(function () {
            initGrid();
            initFrom();
            query();
        })
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