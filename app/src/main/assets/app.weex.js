// { "framework": "Vue" }

/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}


/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _foold = __webpack_require__(1);

	var _foold2 = _interopRequireDefault(_foold);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	_foold2.default.el = '#root';
	exports.default = new Vue(_foold2.default);

/***/ }),
/* 1 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(2)
	)

	/* script */
	__vue_exports__ = __webpack_require__(3)

	/* template */
	var __vue_template__ = __webpack_require__(4)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/litingzhe/weexNews/src/foold.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-43f98d53"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 2 */
/***/ (function(module, exports) {

	module.exports = {
	  "titleContent": {
	    "flexDirection": "column",
	    "marginTop": 15,
	    "marginLeft": 40,
	    "height": 150,
	    "width": 400,
	    "justifyContent": "center",
	    "textAlign": "center"
	  },
	  "titleText": {
	    "fontSize": 25,
	    "color": "#000000",
	    "textAlign": "left",
	    "textOverflow": "ellipsis",
	    "whiteSpace": "nowrap",
	    "overflow": "hidden"
	  },
	  "line": {
	    "width": 750,
	    "height": 3,
	    "background": "lavender"
	  },
	  "newsImage": {
	    "width": 150,
	    "height": 150,
	    "marginTop": 15,
	    "marginLeft": 30
	  }
	}

/***/ }),
/* 3 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	var modal = weex.requireModule('modal');
	var stream = weex.requireModule('stream');
	var LOADMORE_COUNT = 4;

	exports.default = {
	    data: function data() {
	        return {
	            lists: []

	        };
	    },

	    methods: {
	        //            fetch (event) {
	        //                modal.toast({message: 'loadmore', duration: 1})
	        //
	        //                setTimeout(() => {
	        //                    const length = this.lists.length
	        //                    for (let i = length; i < length + LOADMORE_COUNT; ++i) {
	        //                        this.lists.push(i + 1)
	        //                    }
	        //                }, 800)
	        //            },
	        getNews: function getNews(callback) {
	            return stream.fetch({
	                timeout: 30000,
	                method: 'GET',
	                type: 'json',
	                url: 'http://news-at.zhihu.com/api/4/theme/11'
	            }, callback);
	        },
	        toWebView: function toWebView(url) {

	            weex.requireModule("myModule").toShowWebView(url);
	        }
	    },

	    created: function created() {
	        var _this = this;

	        this.getNews(function (res) {

	            if (!res.ok) {
	                modal.toast({ message: '请求失败', duration: 1 });
	            } else {
	                console.log('get:' + res);
	                _this.lists = res.data.stories;

	                modal.toast({ message: '请求成功' + res, duration: 1 });
	            }
	        });
	    }
	};

/***/ }),
/* 4 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('list', {
	    staticClass: ["list"]
	  }, _vm._l((_vm.lists), function(data) {
	    return _c('cell', {
	      staticClass: ["cell"],
	      appendAsTree: true,
	      attrs: {
	        "append": "tree"
	      },
	      on: {
	        "click": function($event) {
	          _vm.toWebView('http://www.baidu.com')
	        }
	      }
	    }, [_c('div', {
	      staticStyle: {
	        flexDirection: "row",
	        width: "750px",
	        height: "180px"
	      }
	    }, [_c('image', {
	      staticClass: ["newsImage"],
	      attrs: {
	        "src": data.images[0]
	      }
	    }), _c('div', {
	      staticClass: ["titleContent"]
	    }, [_c('text', {
	      staticClass: ["titleText"]
	    }, [_vm._v(_vm._s(data.title) + "\n                ")]), _c('text', {
	      staticClass: ["titleText"]
	    }, [_vm._v(_vm._s(data.id) + "\n                ")])])]), _c('div', {
	      staticClass: ["line"]
	    })])
	  }))
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ })
/******/ ]);