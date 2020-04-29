<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,dialog,grid,datepicker,select,validate" name="plugins" />
	</jsp:include>
    <script>
        var year;
        var grid;
        var formValidate;

        var initValidate = function () {
            formValidate = $.etValidate({
                items: [
                    { el: $("#year"), required: true },
                    { el: $("#paper"), required: true },
                ]
            });
        };

        var initFrom = function () {
            year = $("#year").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                onChange: function (value) {
                    // 改变年度，请求获取满分
                    ajaxPostData({
                        url: 'queryTotMainByYear.do',
                        data: { year: value },
                        success: function (res) {
                            $("#paper").val(res.paper)
                        }
                    });
                    setTimeout(function () {
                        query()
                    }, 10);
                },
                defaultDate: true
            });

            $("#paper_type_name").on('change', function () {
                query();
            })
            $("#affect_para_name").on('change', function () {
                query();
            })
        };
        
        var query = function (yearProp) {
            var params = [
                { name: 'year', value: year.getValue() },
                { name: 'paper_type_name', value: $("#paper_type_name").val() },
                { name: 'affect_para_name', value: $("#affect_para_name").val() },
            ];
            grid.loadData(params);
        };
        
        var save = function () {
     	   var isPass = grid.validateTest({
               required: {
            	   paper_type_name: true,
            	   affect_name:true
               }
           });
           if (!isPass) {
               return;
           }
           var paper = $("#paper").val();
           var nos = "";
           
        	var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return;
            } else {
                $(data).each(function () {
                	var rowdata = this.rowData;
                	if(paper){
                  	  if(parseInt(rowdata.score)>parseInt(paper)){
                  		  nos = nos + rowdata.paper_type_name + ",";
                  	  }
                    }
                    ParamVo.push(rowdata);
                });
            }
            
            //验证重复数据
            if (!grid.checkRepeat(grid.selectGet(),['paper_type_name','affect_name'])) {
            	return;
            }
            
            if(nos != ''){
            	$.etDialog.error("保存失败！"+nos+" 的积分不能高于最高分");
				return;
            }else{
            	ajaxPostData({
                    url: 'addAcademicPaperIntegration.do',
                     data: {
                         paramVo: JSON.stringify(ParamVo),
                         paper: $("#paper").val(),
                         affect_para_name: $("#affect_para_name").val(),
                         year: year.getValue(),
                     },
                     success: function () {
                         query();
                     }
                 })
            }
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
                    paper_type_code: item.rowData.paper_type_code,
                    year: year.getValue(),
                    affect_para: item.rowData.affect_para, 
                    score: item.rowData.score,
                });
            });
            $.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                url: 'deleteAcademicPaperIntegration.do',
                data: { paramVo: JSON.stringify(param) },
                success: function () {
                    grid.deleteRows(selectData);
                }
            })
            });
        };
        var extend = function () {
            ajaxPostData({
                url: 'inheritAcademicPaper.do',
                success: function () {
                    query()
                }
            })
        };
        
        var initGrid = function () {
            var columns = [
                { display: '论文类别', name: 'paper_type_name', width: 140,
                    editor: {
                        type: 'select',
                        keyField: 'paper_type_code',
                        url: 'queryPaperType.do?isCheck=false',
                        change: function (rowData, cellData) {
                            grid.updateRow(cellData.rowIndx, { a: cellData.selected.id });
                        }
                    }
                },
                { display: '影响因子', name: 'affect_name', width: 140,
                    editor: {
                        type: 'select',
                        keyField: 'affect_para',
                        url: 'queryAffectPara.do?isCheck=false',
                        change: function (rowData, cellData) {
                            grid.updateRow(cellData.rowIndx, { a: cellData.selected.id });
                        }
                    }
                },
                { display: '积分(每篇)', name: 'score', width: 140 }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                dataModel: {
                   url: 'queryAcademicPaperIntegration.do'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                        { type: 'button', label: '继承', listeners: [{ click: extend }], icon: 'extend' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        $(function () {
            initGrid();
            initFrom();
            initValidate();
        })
        
        function saveHonor(){
			var year = $("#year").val();
			var paper = $("#paper").val(); 
			if(paper){
				ajaxPostData({
			    	url: 'savePersonalAcadePaper.do?isCheck=false',
			        data: {
			       		year: year, 
			       		paper: paper
			        },
			        success: function () {
			        	query();
			        }
			    })
			}
		}
    </script>
</head>

<body>
    <div class="main">
        <table class="table-layout" border="0">
            <tr>
                <td class="label">年度：</td>
                <td >
                    <input id="year" type="text" />
                </td>
                
                <td class="label">论文类别：</td>
                <td >
                    <input id="paper_type_name" type="text" />
                </td>
                <td class="label">影响因子：</td>
                <td >
                    <input id="affect_para_name" type="text" />
                </td>
            </tr>
            <tr>
            	<td class="label">设置最高分：</td>
            	<td >
                    <input  name="paper" type="text" id="paper" onkeyup="value=value.replace(/[^\d]/g,'')" ltype="text" validate="{required:false}"/>
                	<button onClick="saveHonor();">保存</button>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>