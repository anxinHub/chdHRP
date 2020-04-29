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
                    { el: $("#acade_status"), required: true },
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
                        url: 'queryAcadeStatus.do',
                        data: { year: value },
                        success: function (res) {
                            $("#acade_status").val(res.acade_status)
                        }
                    });
                    setTimeout(function () {
                        query()
                    }, 10);
                },
                defaultDate: true
            });

            $("#status_name").on('change', function () {
                query();
            })
        };
        
        var query = function () {
            var params = [
                { name: 'year', value: year.getValue() },
                { name: 'status_name', value: $("#status_name").val() }
            ];
            grid.loadData(params);
        };
        var save = function () {
            var ParamVo = [];
            var acade_status = $("#acade_status").val();
            var isPass = grid.validateTest({
                required: {
                	status_code: true,
             	  status_name:true
                }
            });
            if (!isPass) {
                return;
            }
            var nos = "";
            
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return;
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      if(acade_status){
                    	  if(parseInt(rowdata.score)>parseInt(acade_status)){
                    		  nos = nos + rowdata.status_code + " "+rowdata.status_name+ ",";
                    	  }
                      }
                      ParamVo.push(rowdata);
                  });
               
            }
            //验证重复数据
        	if (!grid.checkRepeat(grid.selectGet(),['status_code','status_name'])) {
               	return;
           	}
            
        	if(nos != ''){
            	$.etDialog.error("保存失败！"+nos+" 的学术地位积分不能高于最高分");
				return;
            }else{
            	ajaxPostData({
                    url: 'addAcademicStatusIntegral.do',
                   data: {
                       paramVo: JSON.stringify(ParamVo),
                       status_name: $("#status_name").val(),
                       acade_status :$("#acade_status").val(),
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
                    status_code: item.rowData.status_code,
                    score: item.rowData.score,
                    year:  year.getValue(),
                });
            });
            $.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                url: 'deleteAcademicStatusIntegral.do',
                data: { paramVo: JSON.stringify(param) },
                success: function () {
                    grid.deleteRows(selectData);
                }
            })
            });
        };
        var extend = function () {
        	  ajaxPostData({
                  url: 'inheritStatus.do?isCheck=false',
                 success: function () {
                     query();
                 }
             })
        };
        
        var initGrid = function () {
            var columns = [
                { display: '学术地位编码', name: 'status_code', width: 140, editable: false },
                { display: '学术地位名称', name: 'status_name', width: 140,
                    editor: {
                        type: 'select',
                        keyField: 'status_code',
                        url: 'queryStatus.do?isCheck=false',
                        change: function (rowData, cellData) {
                            grid.updateRow(cellData.rowIndx, { a: cellData.selected.id });
                        }
                    }
                },
                { display: '学术地位积分', name: 'score', width: 140 }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                dataModel: {
                    url: 'queryAcademicStatusIntegral.do'
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
			var acade_status = $("#acade_status").val(); 
			if(acade_status){
				ajaxPostData({
			    	url: 'savePersonalAcadeStatus.do?isCheck=false',
			        data: {
			       		year: year, 
			       		acade_status: acade_status
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
        <table class="table-layout">
            <tr>
                <td class="label">年度：</td>
                <td class="ipt">
                    <input id="year" type="text" />
                </td>
                <td class="label" style="width: 120px;">学术地位名称：</td>
                <td class="ipt">
                    <input id="status_name" type="text" />
                </td>
                
                <td align="right" class="l-table-edit-td" colspan="4" >
					<table>
						<tr>
							<td>设置最高分:</td>
							<td align="left" class="l-table-edit-td">
								<input  name="acade_status" type="text" id="acade_status" onkeyup="value=value.replace(/[^\d]/g,'')" ltype="text" validate="{required:false}"/>
								&nbsp;&nbsp;&nbsp;
								<button onClick="saveHonor();">保存</button>
							</td>
						</tr>
					</table>
					
				</td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>