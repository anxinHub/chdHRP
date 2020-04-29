var GC;
(function (GC) {
    (function (Spread) {
    (function (Sheets) {
        (function (designer) {
            var ColorHelper = (function () {
                function ColorHelper() {
                }
                ColorHelper.parse = function (color, theme) {
                    if (!color) {
                        return null;
                    }

                    if (ColorHelper.rrggbbPattern.test(color) || 
                        ColorHelper.rgbPattern.test(color) || 
                        ColorHelper.rgbFunctionPattern.test(color) || 
                        ColorHelper.rgbaFunctionPattern.test(color) || 
                        ColorHelper.standardColorNames.indexOf(color.toLowerCase()) > -1) {
                        return {
                            baseColor: color,
                            color: color
                        };
                    }

                    var baseColorName;

                    var index = -1;
                    var t = color.split(' ');
                    if (t) {
                        if (t.length > 1) {
                            if (t[0] === undefined || t[0] === null) {
                                throw 'invalid color name.';
                            }
                            var cn1 = t[0].toLowerCase();
                            if (cn1 === "background") {
                                index = parseInt(t[1], 10) - 1;
                            } else if (cn1 === "text") {
                                index = parseInt(t[1], 10) + 1;
                            } else if (cn1 === "accent") {
                                index = parseInt(t[1], 10) + 3;
                            } else {
                                throw 'invalid color name.';
                            }
                            baseColorName = t[0] + ' ' + t[1];
                        } else if (t.length === 1) {
                            if (t[0] === undefined || t[0] === null) {
                                throw 'invalid color name.';
                            }
                            var cn2 = t[0].toLowerCase();
                            if (cn2 !== "hyperlink" && cn2 !== "followedHyperlink") {
                                throw 'invalid color name.';
                            }
                            baseColorName = t[0];
                        }
                        var tint = 0;
                        if (t.length > 2) {
                            tint = parseInt(t[2], 10);
                        }

                        if (index >= 0 && index <= 11) {
                            return {
                                name: color,
                                baseColor: theme.getColor(baseColorName),
                                tint: tint,
                                color: theme.getColor(color)
                            };
                        }
                    }
                    throw 'invalid color name.';
                };
                ColorHelper.rrggbbPattern = /^#([0-9a-f]{2})([0-9a-f]{2})([0-9a-f]{2})$/i; //#rrggbb
                ColorHelper.rgbPattern = /^#([0-9a-f])([0-9a-f])([0-9a-f])$/i; //#rgb
                ColorHelper.rgbFunctionPattern = /^rgb\(([\s\d]*),([\s\d]*),([\s\d]*)\)$/i; //rgb(n,n,n) or rgb(n%,n%,n%)
                ColorHelper.rgbaFunctionPattern = /^rgba\(([\s\d]*),([\s\d]*),([\s\d]*),([\s\d]*)\)$/i; //rgba(r,g,b,a) or rgba(r%,g%,b%,a)
                ColorHelper.standardColorNames = ["aliceblue", "antiquewhite", "aqua", "aquamarine", "azure", "beige", "bisque", "black", "blanchedalmond", "blue", "blueviolet", "brown", "burlywood", "cadetblue", "chartreuse", "chocolate", "coral", "cornflowerblue", "cornsilk", "crimson", "cyan", "darkblue", "darkcyan", "darkgoldenrod", "darkgray", "darkgreen", "darkkhaki", "darkmagenta", "darkolivegreen", "darkorange", "darkorchid", "darkred", "darksalmon", "darkseagreen", "darkslateblue", "darkslategray", "darkturquoise", "darkviolet", "deeppink", "deepskyblue", "dimgray", "dodgerblue", "firebrick", "floralwhite", "forestgreen", "fuchsia", "gainsboro", "ghostwhite", "gold", "goldenrod", "gray", "green", "greenyellow", "honeydew", "hotpink", "indianred?", "indigo?", "ivory", "khaki", "lavender", "lavenderblush", "lawngreen", "lemonchiffon", "lightblue", "lightcoral", "lightcyan", "lightgoldenrodyellow", "lightgray", "lightgreen", "lightpink", "lightsalmon", "lightseagreen", "lightskyblue", "lightslategray", "lightsteelblue", "lightyellow", "lime", "limegreen", "linen", "magenta", "maroon", "mediumaquamarine", "mediumblue", "mediumorchid", "mediumpurple", "mediumseagreen", "mediumslateblue", "mediumspringgreen", "mediumturquoise", "mediumvioletred", "midnightblue", "mintcream", "mistyrose", "moccasin", "navajowhite", "navy", "oldlace", "olive", "olivedrab", "orange", "orangered", "orchid", "palegoldenrod", "palegreen", "paleturquoise", "palevioletred", "papayawhip", "peachpuff", "peru", "pink", "plum", "powderblue", "purple", "red", "rosybrown", "royalblue", "saddlebrown", "salmon", "sandybrown", "seagreen", "seashell", "sienna", "silver", "skyblue", "slateblue", "slategray", "snow", "springgreen", "steelblue", "tan", "teal", "thistle", "tomato", "turquoise", "violet", "wheat", "white", "whitesmoke", "yellow", "yellowgreen"];
                return ColorHelper;
            })();
            designer.ColorHelper = ColorHelper;
        })(Sheets.designer || (Sheets.designer = {}));
        var designer = Sheets.designer;
    })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));
