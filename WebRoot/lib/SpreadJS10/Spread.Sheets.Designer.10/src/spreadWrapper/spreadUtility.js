var GC;
(function (GC) {
    (function (Spread) {
    (function (Sheets) {
        (function (designer) {
            'use strict';
            var SpreadUtility = (function () {
                function SpreadUtility() {
                }
                SpreadUtility.getRangeType = function (range) {
                    if (!range) {
                        return -1;
                    }
                    if (range.col == -1 && range.row == -1) {
                        return 3 /* Sheet */;
                    } else if (range.col == -1) {
                        return 1 /* Row */;
                    } else if (range.row == -1) {
                        return 0 /* Column */;
                    } else {
                        return 2 /* Cell */;
                    }
                };
                return SpreadUtility;
            })();
            designer.SpreadUtility = SpreadUtility;

            (function (RangeType) {
                RangeType[RangeType["Column"] = 0] = "Column";
                RangeType[RangeType["Row"] = 1] = "Row";
                RangeType[RangeType["Cell"] = 2] = "Cell";
                RangeType[RangeType["Sheet"] = 3] = "Sheet";
            })(designer.RangeType || (designer.RangeType = {}));
            var RangeType = designer.RangeType;
        })(Sheets.designer || (Sheets.designer = {}));
        var designer = Sheets.designer;
    })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));
