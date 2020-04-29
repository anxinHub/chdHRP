var GC;
(function (GC) {
    (function (Spread) {
    (function (Sheets) {
        (function (designer) {
            (function (index) {
                'use strict';

                var needSuspend;

                function updateLayout() {
                    $(".content").css('height', $(".content").children('.fill-spread-content').height());
                    $(".header").css("width", $(window).width() + "px");
                    if ($(".ribbon-bar").data("gcui-gcuiribbon")) {
                        $(".ribbon-bar").data("gcui-gcuiribbon").updateRibbonSize();
                    }
                    var spread = designer.wrapper.spread;
                    if (spread && spread.isPaintSuspended()) {
                        spread.resumePaint();
                        spread.refresh();
                        needSuspend = true;
                    }
                   
                }
                var _windowResizeTimer = null;

                function _doWindowResize() {
                    if (_windowResizeTimer) {
                        window.clearTimeout(_windowResizeTimer);
                    }
                    _windowResizeTimer = window.setTimeout(function () {
                        updateLayout();
                        _windowResizeTimer = null;
                        if (needSuspend) {
                            needSuspend = false;
                            window.setTimeout(function() {
                                designer.wrapper.spread.suspendPaint();
                            }, 300);
                        }
                    }, 100); //now delay 100ms to resize designer
                };

                $(document).ready(function () {
                    designer.loader.loadContent();
                    $(window).resize(_doWindowResize);
                    $(window).resize();

                    $("#verticalSplitter").draggable({
                        axis: "y",
                        containment: "parent",
                        scroll: false,
                        zIndex: 100,
                        stop: function (event, ui) {
                            var $this = $(this), top = $this.offset().top, offset = top - $(".header").height(), $content = $(".content .fill-spread-content");

                            // adjust size of related items
                            $("#formulaBarText").css({ height: (20 + offset) }); // 20: original height of formulaBarText
                            $content.css({ top: top + 10 }); // 10: height of the space for verticalSplitter
                            $content.parent().css({ height: $content.height() });
                            designer.wrapper.spread.refresh();
                        }
                    });

                    function disableDragDrop(dataTransfer) {
                        if (dataTransfer) {
                            dataTransfer.effectAllowed = "none";
                            dataTransfer.dropEffect = "none";
                        }
                    }
                    window.addEventListener("dragenter", function (e) {
                        if (e.target.id != dropzoneId) {
                            e.preventDefault();
                            disableDragDrop(e.dataTransfer);
                        }
                    }, false);
                    window.addEventListener("dragover", function (e) {
                        e = e || event;
                        e.preventDefault();
                        disableDragDrop(e.dataTransfer);
                    }, false);
                    window.addEventListener("drop", function (e) {
                        e = e || event;
                        e.preventDefault();
                        disableDragDrop(e.dataTransfer);
                    }, false);
                });

                designer.loader.ready(function () {
                	
                    //To Fix the designer resize performance issues.
                    $(window).unbind("resize.gcuiribbon");
                    if(typeof(parent.spreadNS))parent.spreadNS=GC.Spread.Sheets;//perry 给父页面对象赋值
                    if(typeof(parent.excelIo))parent.excelIo=new GC.Spread.Excel.IO();
                    if(typeof parent.initSpreadTable!= "undefined"){
                    	parent.initSpreadTable();//perry 加载父页面的方法
                    }
                   // designer.wrapper.spread.addCustomFunction(new REP());
                   // designer.wrapper.spread.addCustomFunction(new RES());
                    $("#verticalSplitter").show();
                });
            })(designer.index || (designer.index = {}));
            var index = designer.index;
        })(Sheets.designer || (Sheets.designer = {}));
        var designer = Sheets.designer;
    })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));



/*function REP(){}
REP.prototype = new GC.Spread.CalcEngine.Functions.Function("REP", 0, 0, {name: "REP",description: "系统取值函数"});
REP.prototype.evaluate = function (args) {
    return "";
};

function RES(){}
RES.prototype = new GC.Spread.CalcEngine.Functions.Function("RES", 0, 0, {name: "RES",description: "系统取值元素"});
RES.prototype.evaluate = function (args) {
    return "";
};
*/
//打开选择文件窗口
function openFileDialog(spread, dialogArgs){
	GC.Spread.Sheets.designer.actions.open(spread,dialogArgs);
}

//导出excel文件
function exportExcel(spread, dialogArgs){
	GC.Spread.Sheets.designer.actions.exportExcel(spread,dialogArgs);
} 


var REPFunction = new GC.Spread.CalcEngine.Functions.Function("REP", 1, 255, {name: "REP",description: "系统取值函数"});
REPFunction.evaluate = function()
{
    var result = "";
  /*  for (var i = 0; i < arguments.length; i++) {
        result += arguments[i];
    }*/
    return result;
}
GC.Spread.CalcEngine.Functions.defineGlobalCustomFunction("REP", REPFunction);

var REPFunction = new GC.Spread.CalcEngine.Functions.Function("RES", 1, 255, {name: "RES",description: "系统取值元素"});
REPFunction.evaluate = function()
{
    var result = "";
  /*  for (var i = 0; i < arguments.length; i++) {
        result += arguments[i];
    }*/
    return result;
}
GC.Spread.CalcEngine.Functions.defineGlobalCustomFunction("RES", REPFunction);
