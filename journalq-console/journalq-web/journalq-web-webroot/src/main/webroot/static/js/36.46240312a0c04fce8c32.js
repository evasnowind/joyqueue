webpackJsonp([36],{G6BX:function(t,e,s){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=s("T0gc"),i=s("95hR"),n=s("1a0f"),r=s("fo4W"),o=s("toiR"),c={name:"archive",components:{MyDialog:r.a,myTable:a.a},mixins:[i.a],props:{search:{type:Object,default:function(){return{topic:"",businessId:"",beginTime:"",endTime:"",sendTime:"",messageId:"",count:10}}},btns:{type:Array,default:function(){return[{txt:"下载消息体",method:"on-download"},{txt:"归档转重试",method:"on-retry"},{txt:"查看消费",method:"on-consume"}]}}},data:function(){return{urls:{search:"/archive/search",consume:"/archive/consume",download:"/archive/download",retry:"/archive/retry"},firstDis:!0,nextDis:!0,showDialog:{visible:!1,title:"消费记录",showFooter:!1,width:"1000px"},consumeData:{rowData:[],colData:[{title:"消息ID",key:"messageId"},{title:"消费时间",key:"consumeTime",formatter:function(t){return Object(o.a)(t.consumeTime)}},{title:"消费者",key:"app"},{title:"消费者主机",key:"clientIpStr"}]},tableData:{rowData:[],colData:[{title:"消息ID",key:"messageId"},{title:"业务ID",key:"businessId"},{title:"发送时间",key:"sendTime",formatter:function(t){return Object(o.a)(t.sendTime)}},{title:"生产者IP",key:"clientIpStr"},{title:"生产者",key:"app"},{title:"队列",key:"topic"}],btns:this.btns},multipleSelection:[],times:[]}},methods:{getListWithDate:function(t){var e=this;if(!this.times||this.times.length<2||!this.search.topic)return this.$Dialog.error({content:"起始时间,结束时间 topic 都不能为空"}),!1;this.firstDis=!0;var s=this.tableData.rowData;this.search.beginTime=this.times[0],this.search.endTime=this.times[1],t?(this.search.sendTime=s[s.length-1].sendTime,this.firstDis=!1):(this.search.sendTime=this.search.beginTime,this.firstDis=!0),n.a.post(this.urlOrigin.search,{},this.search).then(function(t){e.tableData.rowData=t.data,e.tableData.rowData.length<e.search.count?e.nextDis=!0:e.nextDis=!1})},download:function(t){var e=this,s="?topic="+t.topic+"&sendTime="+t.sendTime+"&businessId="+t.businessId+"&messageId="+t.messageId;n.a.get(this.urlOrigin.download+s).then(function(t){e.$Message.success("下载成功")})},retry:function(t){var e=this;n.a.post(this.urlOrigin.retry,{},t).then(function(t){e.$Message.success("操作成功")})},consume:function(t){var e=this;this.showDialog.visible=!0,n.a.get(this.urlOrigin.consume+"/"+t.messageId).then(function(t){e.consumeData.rowData=t.data})}},mounted:function(){}},l={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",[s("div",{staticClass:"ml20 mt30"},[s("d-date-picker",{staticClass:"left mr5",attrs:{type:"daterange","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期","value-format":"timestamp","default-time":["00:00:00","23:59:59"]},model:{value:t.times,callback:function(e){t.times=e},expression:"times"}},[s("span",{attrs:{slot:"prepend"},slot:"prepend"},[t._v("日期范围")])]),t._v(" "),s("d-input",{staticClass:"left mr5",staticStyle:{width:"15%"},attrs:{placeholder:"队列名"},model:{value:t.search.topic,callback:function(e){t.$set(t.search,"topic",e)},expression:"search.topic"}},[s("span",{attrs:{slot:"prepend"},slot:"prepend"},[t._v("队列名")])]),t._v(" "),s("d-input",{staticClass:"left mr5",staticStyle:{width:"15%"},attrs:{placeholder:"业务ID"},model:{value:t.search.businessId,callback:function(e){t.$set(t.search,"businessId",e)},expression:"search.businessId"}},[s("span",{attrs:{slot:"prepend"},slot:"prepend"},[t._v("业务ID")])]),t._v(" "),s("d-button",{attrs:{type:"primary",color:"success"},on:{click:function(e){return t.getListWithDate(!1)}}},[t._v("查询")]),t._v(" "),t._t("extendBtn")],2),t._v(" "),s("my-table",{attrs:{showPagination:!1,showPin:t.showTablePin,data:t.tableData,page:t.page},on:{"on-size-change":t.handleSizeChange,"on-current-change":t.handleCurrentChange,"on-selection-change":t.handleSelectionChange,"on-consume":t.consume,"on-download":t.download,"on-retry":t.retry}}),t._v(" "),s("div",{staticStyle:{float:"right"}},[t.firstDis?s("d-button",{attrs:{color:"info",disabled:""}},[t._v("首页")]):s("d-button",{attrs:{type:"primary"},on:{click:function(e){return t.getListWithDate(!1)}}},[t._v("首页")]),t._v(" "),t.nextDis?s("d-button",{attrs:{color:"info",disabled:""}},[t._v("下一页")]):s("d-button",{attrs:{type:"primary"},on:{click:function(e){return t.getListWithDate(!0)}}},[t._v("下一页")])],1),t._v(" "),s("my-dialog",{attrs:{dialog:t.showDialog}},[s("my-table",{staticStyle:{padding:"0px"},attrs:{showPagination:!1,data:t.consumeData}})],1)],1)},staticRenderFns:[]};var d=s("VU/8")(c,l,!1,function(t){s("GN2e")},"data-v-117cd638",null);e.default=d.exports},GN2e:function(t,e){}});
//# sourceMappingURL=36.46240312a0c04fce8c32.js.map