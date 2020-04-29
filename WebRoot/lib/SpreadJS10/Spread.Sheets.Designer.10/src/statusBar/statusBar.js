var GC;
(function (GC) {
    (function (Spread) {
    (function (Sheets) {
        (function (designer) {
            (function (statusBar) {
                'use strict';

                function initStatusBar() {
                    $(".statusBar").find(".statusbar-zoom-slider").slider({ max: 400, min: 50 });
                }

                function addEvents() {
                    $(".statusBar").find(".statusbar-zoom-value").click(function () {
                        var zoomDialog = new designer.ZoomDialog();
                        zoomDialog.open();
                    });
                    designer.wrapper.spreadElement.bind("FileOpened", function (evt, data) {
                        designer.ribbon.updateZoomToStatusBar();
                    });
                }

                designer.loader.ready(function () {
                    initStatusBar();
                    if (!designer.wrapper.spread.notWorking) {
                        addEvents();
                    }
                });
            })(designer.statusBar || (designer.statusBar = {}));
            var statusBar = designer.statusBar;
        })(Sheets.designer || (Sheets.designer = {}));
        var designer = Sheets.designer;
    })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));