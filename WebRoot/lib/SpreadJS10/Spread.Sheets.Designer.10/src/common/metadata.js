var __extends = this.__extends || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    __.prototype = b.prototype;
    d.prototype = new __();
};
var GC;
(function (GC) {
    (function (Spread) {
    (function (Sheets) {
        (function (designer) {
            'use strict';

            designer.PrimitiveTypes = {
                STRING: 'string',
                NUMBER: 'number',
                BOOLEAN: 'boolean',
                OBJECT: 'object',
                FUNCTION: 'function',
                UNDEFINED: 'undefined'
            };

            var BaseMetaObject = (function () {
                function BaseMetaObject(source, meta, metadata) {
                    this._source = source;
                    this._meta = meta;
                    this._metadata = metadata;
                }
                BaseMetaObject.prototype._getSourceValue = function (propName) {
                    var prop = this._meta.properties[propName];
                    var getter = prop.getter;
                    if (getter !== undefined) {
                        if (typeof getter === designer.PrimitiveTypes.STRING) {
                            var getter2 = this._source[getter];
                            if (typeof getter2 !== designer.PrimitiveTypes.FUNCTION) {
                                throw 'metadata invalide. source object "' + this._source + '" have no a function named "' + getter + '".';
                            }
                            getter = getter2;
                        }
                        if (typeof getter !== designer.PrimitiveTypes.FUNCTION) {
                            throw 'metadata invalide. expect a "function" for getter of property "' + propName + '" of type "' + this._meta + '", but actual is a "' + (typeof getter) + '"';
                        }
                    } else {
                        getter = this._source[propName];
                        if (typeof getter !== designer.PrimitiveTypes.FUNCTION) {
                            getter = function () {
                                return this[propName];
                            };
                        }
                    }
                    return getter.apply(this._source);
                };
                BaseMetaObject.prototype._setSourceValue = function (propName, value) {
                    var prop = this._meta.properties[propName];
                    var setter = prop.setter;
                    if (setter !== undefined) {
                        if (typeof setter === designer.PrimitiveTypes.STRING) {
                            var setter2 = this._source[setter];
                            if (typeof setter2 !== designer.PrimitiveTypes.FUNCTION) {
                                throw 'metadata invalide. source object "' + this._source + '" have no a function named "' + setter + '".';
                            }
                            setter = setter2;
                        }
                        if (typeof setter !== designer.PrimitiveTypes.FUNCTION) {
                            throw 'metadata invalide. expect a "function" for setter of property "' + propName + '" of type "' + this._meta + '", but actual is a "' + (typeof setter) + '"';
                        }
                    } else {
                        setter = this._source[propName];
                        if (typeof setter !== designer.PrimitiveTypes.FUNCTION) {
                            setter = function (value) {
                                this[propName] = value;
                            };
                        }
                    }

                    if (typeof value !== prop.type) {
                        value = BaseMetaObject._convert(value, prop.type);
                    }

                    setter.call(this._source, value);
                };

                BaseMetaObject._convert = function (value, type) {
                    if (value === undefined || value === null) {
                        return value;
                    }

                    switch (type) {
                        case designer.PrimitiveTypes.NUMBER:
                            return parseFloat(value);
                        case designer.PrimitiveTypes.BOOLEAN:
                            return !!value;
                        case designer.PrimitiveTypes.STRING:
                            return value.toString();
                        default:
                            return value;
                    }
                };

                BaseMetaObject.prototype._genProperties = function () {
                    var self = this;
                    Object.getOwnPropertyNames(this._meta.properties).forEach(function (propName) {
                        self[propName] = self._genProperty(propName);
                    });
                };
                BaseMetaObject.prototype._genProperty = function (propName) {
                    throw 'not implemented.';
                };
                BaseMetaObject.undefinedValue = {};
                BaseMetaObject.indeterminateValue = {};
                return BaseMetaObject;
            })();
            designer.BaseMetaObject = BaseMetaObject;
            var ShadowObject = (function (_super) {
                __extends(ShadowObject, _super);
                function ShadowObject(source, meta, metadata) {
                    _super.call(this, source, meta, metadata);
                    this._values = {};
                    this._genProperties();
                }
                ShadowObject.prototype._value = function (propName, value) {
                    if (value === undefined) {
                        value = this._values[propName];
                        if (value === undefined) {
                            value = this._getSourceValue(propName);
                            var propMeta = this._metadata[this._meta.properties[propName].type];
                            if (propMeta !== undefined) {
                                value = this._values[propName] = new ShadowObject(value, propMeta, this._metadata);
                            }
                            return value;
                        } else if (value === BaseMetaObject.undefinedValue) {
                            return undefined;
                        }
                        return value;
                    }

                    var expectType = this._meta.properties[propName].type;
                    if (typeof value !== expectType) {
                        value = BaseMetaObject._convert(value, expectType);
                    }

                    this._values[propName] = value;
                    $(this).trigger($.Event('change', { property: propName }));
                    return this;
                };
                ShadowObject.prototype.reset = function (propName) {
                    this._values[propName] = undefined;
                };
                ShadowObject.prototype.commit = function () {
                    var self = this;
                    Object.getOwnPropertyNames(this._values).forEach(function (propName) {
                        var value = self._values[propName];
                        if (value !== undefined) {
                            if (value instanceof ShadowObject) {
                                value.commit();
                                return;
                            }

                            if (value === BaseMetaObject.undefinedValue) {
                                value = undefined;
                            }

                            self._setSourceValue(propName, value);
                        }
                    });
                };

                ShadowObject.prototype._genProperty = function (propName) {
                    return this._value.bind(this, propName);
                };
                return ShadowObject;
            })(BaseMetaObject);
            var WrappedObject = (function (_super) {
                __extends(WrappedObject, _super);
                function WrappedObject(source, meta, metadata) {
                    _super.call(this, source, meta, metadata);

                    this._genProperties();
                }
                WrappedObject.prototype._genProperty = function (propName) {
                    var self = this;
                    var meta = this._metadata[this._meta.properties[propName].type];

                    return function (value) {
                        if (value === undefined) {
                            var v = self._getSourceValue(propName);
                            if (meta !== undefined) {
                                return new WrappedObject(v, meta, self._metadata);
                            }
                            return v;
                        }

                        if (meta !== undefined) {
                            throw 'Complex property don\'t support set.';
                        }

                        var expectType = self._meta.properties[propName].type;
                        if (typeof value !== expectType) {
                            value = BaseMetaObject._convert(value, expectType);
                        }

                        self._setSourceValue(propName, value);
                        $(self).trigger($.Event('change', { property: propName }));

                        return this;
                    };
                };
                return WrappedObject;
            })(BaseMetaObject);
            var KnockoutShadowObject = (function (_super) {
                __extends(KnockoutShadowObject, _super);
                function KnockoutShadowObject(source, meta, metadata) {
                    _super.call(this, source, meta, metadata);
                    this._values = {};
                    this._genProperties();
                }
                KnockoutShadowObject.prototype.reset = function (propName) {
                    this._values[propName] = undefined;
                };
                KnockoutShadowObject.prototype.commit = function () {
                    var self = this;
                    Object.getOwnPropertyNames(this._values).forEach(function (propName) {
                        var value = self._values[propName];
                        if (value !== undefined) {
                            if (value instanceof KnockoutShadowObject) {
                                value.commit();
                                return;
                            }

                            if (value === BaseMetaObject.undefinedValue) {
                                value = undefined;
                            }

                            self._setSourceValue(propName, value);
                        }
                    });
                };

                KnockoutShadowObject.prototype._genProperty = function (propName) {
                    var self = this;
                    var propMeta = this._meta.properties[propName];

                    //            var propTypeMeta = this._metadata[propMeta.type];
                    //            if (propTypeMeta != null) {
                    //                return new KnockoutShadowObject()
                    //            }
                    return ko.computed({
                        read: function () {
                            var value = self._values[propName];
                            if (value === undefined) {
                                return self._getSourceValue(propName);
                            } else if (value === BaseMetaObject.undefinedValue) {
                                return undefined;
                            } else {
                                return value;
                            }
                        },
                        write: function (newValue) {
                            var value = newValue;
                            if (value === undefined) {
                                value = BaseMetaObject.undefinedValue;
                            } else if (typeof value !== propMeta.type) {
                                value = BaseMetaObject._convert(value, propMeta.type);
                            }
                            self._values[propName] = value;
                        }
                    });
                };
                return KnockoutShadowObject;
            })(BaseMetaObject);

            var MetaFactor = (function () {
                function MetaFactor(metadata) {
                    this._metadata = metadata;
                }
                MetaFactor.prototype.genWrapper = function (source, type) {
                    var target = source[MetaFactor.internalWrapper];
                    if (target !== undefined) {
                        return target;
                    }

                    var meta = this._metadata[type];
                    if (meta === undefined) {
                        return source;
                    }

                    target = new WrappedObject(source, meta, this._metadata);
                    source[MetaFactor.internalWrapper] = target;
                    return target;
                };

                MetaFactor.prototype.genShadow = function (source, type) {
                    var meta = this._metadata[type];
                    if (meta === undefined) {
                        return source;
                    }

                    return new ShadowObject(source, meta, this._metadata);
                };
                MetaFactor.prototype.genKoShadow = function (source, type) {
                    var meta = this._metadata[type];
                    if (meta === undefined) {
                        return source;
                    }

                    return new KnockoutShadowObject(source, meta, this._metadata);
                };
                MetaFactor.internalWrapper = '__gc_metadata_wrapper';
                return MetaFactor;
            })();
            designer.MetaFactor = MetaFactor;
        })(Sheets.designer || (Sheets.designer = {}));
        var designer = Sheets.designer;
    })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));
