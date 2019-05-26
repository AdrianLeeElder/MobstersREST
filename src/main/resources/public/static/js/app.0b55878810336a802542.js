webpackJsonp([1],{"96sJ":function(t,e){},NHnr:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n("7+uW"),i=n("mtWM"),r=n.n(i).a.create({baseURL:"/api/v1"}),a={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",[this.item.running?[e("v-progress-circular",{attrs:{indeterminate:"",color:"primary"}})]:this.item.queued?[e("v-icon",[this._v("queue")])]:this.item.complete?[e("v-icon",[this._v("check_circle")])]:this._e()],2)},staticRenderFns:[]};var c={name:"AccountsDashboard",data:function(){return{dailyActionHeaders:[{text:"Action Name",value:"name",sortable:!1},{text:"Status",value:"running",sortable:!1}],accountHeaders:[{text:"Status",value:"status"},{text:"Username",value:"username"},{text:"Bot Mode",value:"botMode"},{text:"Priority",value:"priority",sortable:!0},{text:"Account",value:"account"}],mobsters:[],editingAccount:{priority:"Low",botMode:"Daily"},priorities:["Low","Medium","High"],botModes:["Buy Property","Daily"],showAccountEditor:!1,selectedAccounts:[]}},methods:{editAccount:function(t){this.editingAccount=t,this.showAccountEditor=!0},saveAccount:function(){this.showAccountEditor=!1},addToQueue:function(){this.selectedAccounts.forEach(function(t){t.running||t.queued||(t.queued=!0)}),this.selectedAccounts=[]}},created:function(){var t=this;r.get("mobsters").then(function(e){t.mobsters=e.data},this).catch(function(e){t.mobsters=[{username:"zombie",dailyActions:[{name:"Login",running:!1,complete:!0},{name:"Daily Energy Link",running:!0,complete:!1,queued:!1},{name:"Logout",running:!1,complete:!1,queued:!0}],botMode:"Daily",priority:"Low",running:!0,queued:!1,complete:!1},{username:"bigtrac",dailyActions:[{name:"Login",running:!1,complete:!0,queued:!1},{name:"Daily Energy Link",running:!0,complete:!0,queued:!1},{name:"Logout",running:!1,complete:!1,queued:!0}],botMode:"Buy Property",buyPropertyMessages:["Bought a Skyscraper for $1000 x 10","Buying more newsstands to see what to buy next"],priority:"High",running:!1,queued:!1,complete:!1}]},this)},computed:{getStatusIcon:function(t){if(t.running)return"running"}},components:{StatusIndicator:n("VU/8")({props:["item"],name:"StatusIndicator"},a,!1,function(t){n("mEzr")},null,null).exports}},s={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",[n("v-layout",{attrs:{row:"",wrap:""}},[n("v-flex",{attrs:{xs12:""}},[n("v-spacer"),t._v(" "),n("v-btn",{staticClass:"white--text",attrs:{disabled:0===t.selectedAccounts.length,color:"primary"},on:{click:t.addToQueue}},[n("v-icon",{attrs:{left:""}},[t._v("queue_play_next")]),t._v(" Add to Queue\n      ")],1)],1),t._v(" "),n("v-flex",{attrs:{xs12:""}},[n("v-divider")],1),t._v(" "),n("v-flex",{attrs:{xs12:""}},[n("v-data-table",{attrs:{headers:t.accountHeaders,items:t.mobsters,"hide-actions":"","item-key":"username","select-all":""},scopedSlots:t._u([{key:"items",fn:function(e){return[n("tr",{staticStyle:{cursor:"pointer"},on:{click:function(t){e.expanded=!e.expanded}}},[n("td",[n("v-checkbox",{attrs:{primary:"","hide-details":""},on:{click:function(t){t.stopPropagation()}},model:{value:e.selected,callback:function(n){t.$set(e,"selected",n)},expression:"props.selected"}})],1),t._v(" "),n("td",[n("status-indicator",{attrs:{item:e.item}})],1),t._v(" "),n("td",[t._v(t._s(e.item.username))]),t._v(" "),n("td",[t._v(t._s(e.item.botMode))]),t._v(" "),"Low"===e.item.priority?n("td",[n("v-icon",[t._v("star_border")])],1):"Medium"===e.item.priority?n("td",[n("v-icon",[t._v("star_half")])],1):"High"===e.item.priority?n("td",[n("v-icon",[t._v("star")])],1):t._e(),t._v(" "),n("td",[n("v-btn",{attrs:{icon:""},on:{click:function(n){n.stopPropagation(),t.editAccount(e.item)}}},[n("v-icon",[t._v("create")])],1),t._v(" "),n("v-btn",{attrs:{icon:""}},[n("v-icon",[t._v("more_vert")])],1)],1)])]}},{key:"expand",fn:function(e){return["Daily"===e.item.botMode?n("v-card",{staticClass:"ma-3"},[n("v-data-table",{attrs:{items:e.item.dailyActions,headers:t.dailyActionHeaders},scopedSlots:t._u([{key:"items",fn:function(e){return[n("td",[t._v(t._s(e.item.name))]),t._v(" "),n("td",[n("status-indicator",{attrs:{item:e.item}})],1)]}}])})],1):"Buy Property"===e.item.botMode?n("v-card",{staticClass:"ma-3"},[n("v-list",t._l(e.item.buyPropertyMessages,function(e,o){return n("v-list-tile",{key:o},[n("v-list-tile-content",[n("v-list-tile-title",[t._v(t._s(e))])],1)],1)}))],1):t._e()]}}]),model:{value:t.selectedAccounts,callback:function(e){t.selectedAccounts=e},expression:"selectedAccounts"}}),t._v(" "),n("v-dialog",{attrs:{"max-width":"300px"},model:{value:t.showAccountEditor,callback:function(e){t.showAccountEditor=e},expression:"showAccountEditor"}},[n("v-card",[n("v-card-title",[t._v("Edit Account Settings")]),t._v(" "),n("v-card-text",[n("v-select",{attrs:{label:"Priority",items:t.priorities},model:{value:t.editingAccount.priority,callback:function(e){t.$set(t.editingAccount,"priority",e)},expression:"editingAccount.priority"}}),t._v(" "),n("v-select",{attrs:{label:"Bot Mode",items:t.botModes},model:{value:t.editingAccount.botMode,callback:function(e){t.$set(t.editingAccount,"botMode",e)},expression:"editingAccount.botMode"}})],1),t._v(" "),n("v-card-actions",[n("v-btn",{attrs:{color:"primary","justify-right":""},on:{click:function(e){t.saveAccount()}}},[t._v("Save")])],1)],1)],1)],1)],1)],1)},staticRenderFns:[]},u={name:"App",data:function(){return{drawer:!1}},components:{AccountsDashboard:n("VU/8")(c,s,!1,null,null,null).exports}},l={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-app",{staticStyle:{"background-color":"#e3e3e3"},attrs:{id:"inspire"}},[n("v-toolbar",{attrs:{dark:"",color:"primary",app:""}},[n("v-toolbar-side-icon",{on:{click:function(e){e.stopPropagation(),t.drawer=!t.drawer}}}),t._v(" "),n("v-toolbar-title",{staticClass:"white--text"},[t._v("Mobsters Bot - By Adrian Elder")]),t._v(" "),n("v-spacer"),t._v(" "),n("v-btn",{attrs:{icon:""}},[n("v-icon",[t._v("more_vert")])],1)],1),t._v(" "),n("v-navigation-drawer",{attrs:{app:""},model:{value:t.drawer,callback:function(e){t.drawer=e},expression:"drawer"}},[n("v-list",[n("v-list-group",[n("v-list-tile",{attrs:{slot:"activator"},slot:"activator"},[n("v-list-tile-action",[n("v-icon",[t._v("account_circle")])],1),t._v(" "),n("v-list-tile-title",[t._v("\n            Mobsters\n          ")])],1)],1)],1)],1),t._v(" "),n("v-content",[n("v-container",{attrs:{fluid:""}},[n("v-card",[n("accounts-dashboard")],1)],1)],1)],1)},staticRenderFns:[]};var d=n("VU/8")(u,l,!1,function(t){n("YlMf")},null,null).exports,v=n("/ocq");o.a.use(v.a);var m=new v.a({routes:[{path:"/",name:"HelloWorld"}]}),p=n("3EgV"),_=n.n(p);n("gJtD"),n("96sJ"),n("Y/7d");o.a.config.productionTip=!1,o.a.use(_.a),new o.a({el:"#app",router:m,components:{App:d},template:"<App/>"})},"Y/7d":function(t,e){},YlMf:function(t,e){},gJtD:function(t,e){},mEzr:function(t,e){}},["NHnr"]);
//# sourceMappingURL=app.0b55878810336a802542.js.map