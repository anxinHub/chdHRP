var GC;
(function (GC) {
    (function (Spread) {
        (function (Sheets) {
            (function (designer) {
                var CEUtility = (function () {
                    function CEUtility() {

                    }

                    function prepareCalcService() {
                        if (!CEUtility.calcService) {
                            CEUtility.calcService = designer.wrapper.spread.getCalcService();
                        }
                    }

                    CEUtility.unparse = function (source, expr, row, col) {
                        prepareCalcService();
                        return CEUtility.calcService.unparse(source, expr, row, col);
                    };

                    CEUtility.parse = function (source, formula, row, col, ignoreError, forceA1) {
                        prepareCalcService();
                        return CEUtility.calcService.parse(source, formula, row, col, ignoreError, forceA1);
                    };

                    CEUtility.parseRangeToExpString = function (range) {
                        var Calc = GC.Spread.Sheets.CalcEngine;
                        return Calc.rangeToFormula(range, 0, 0, Calc.RangeReferenceRelative.allRelative);
                    };

                    CEUtility.parseExpStringToRanges = function (expString, sheet) {
                        var Calc = GC.Spread.Sheets.CalcEngine;
                        var ranges = [];
                        var exps = expString.split(",");
                        try {
                            for (var i = 0; i < exps.length; i++) {
                                var range = Calc.formulaToRange(sheet, exps[i]);
                                ranges.push(range);
                            }
                        } catch (e) {
                            return null;
                        }
                        return ranges;
                    };

                    CEUtility.parseExternalRangeToString = function (externalRange) {
                        var str = "";
                        if (externalRange.resource) {
                            str += "'" + externalRange.resource.name() + "'";
                        }
                        if (externalRange.range) {
                            str += CEUtility.parseRangeToExpString(externalRange.range);
                        }
                        return str;
                    };

                    CEUtility.parseStringToExternalRanges = function (expString, sheet) {
                        var Calc = GC.Spread.Sheets.CalcEngine;
                        var results = [];
                        var exps = expString.split(",");
                        try {
                            for (var i = 0; i < exps.length; i++) {
                                var range = Calc.formulaToRange(sheet, exps[i]);
                                results.push({range: range});
                            }
                        } catch (e) {
                            return null;
                        }
                        return results;
                    };
                    return CEUtility;
                })();
                designer.CEUtility = CEUtility;
            })(Sheets.designer || (Sheets.designer = {}));
            var designer = Sheets.designer;
        })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));
