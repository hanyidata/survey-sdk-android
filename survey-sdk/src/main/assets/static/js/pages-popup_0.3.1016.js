"use strict";(self.webpackChunksurvey=self.webpackChunksurvey||[]).push([[468],{1520:function(e,i,n){n.r(i),n.d(i,{default:function(){return o}}),n(9653);i={components:{"hy-survey":n(7375).Z},data:function(){return{surveyId:null,channelId:null,sendId:null,parameters:{},options:{},isLoadSurvey:!1,show:!1,dMarginTop:"0px",survey:{},ready:!1}},computed:{halfscreen:function(){return null!==this.options&&void 0!==this.options&&this.options.halfscreen||!1},style:function(){return this.survey.style},configure:function(){return this.survey&&this.survey.channel&&this.survey.channel.configure||{}},channelType:function(){return this.survey&&this.survey.channel&&this.survey.channel.type||"APP"},injectType:function(){return Number(this.configure&&this.configure.injectType)||{}},embed:function(){return this.survey&&this.survey.embed||{}},embedHeightMode:function(){return this.configure&&this.configure.embedHeightMode||this.embed.embedHeightMode||"AUTO"},embedHeight:function(){return this.configure&&this.configure.embedHeight||this.embed.embedHeight||"50%"},embedPaddingWidth:function(){return this.configure&&this.configure.appPaddingWidth||this.embed.appPaddingWidth||0},embedVerticalAlign:function(){return this.configure&&this.configure.embedVerticalAlign||this.embed.embedVerticalAlign||"BOTTOM"},embedBackGround:function(){return this.survey?this.configure&&this.configure.embedBackGround&&this.isLoadSurvey||"":0},heightValue:function(){return this.isLoadSurvey?"AUTO"===this.embedHeightMode?"auto":"100%"===this.embedHeight?"calc(100% - "+this.dMarginTop+")":this.embedHeight:"0px"},maxHeightValue:function(){return"AUTO"===this.embedHeightMode?this.embedHeight:""},appBorderRadius:function(){return this.configure&&this.configure.appBorderRadius||this.embed.appBorderRadius||"10px"}},created:function(){var e,i;document||(e=(i=uni.getMenuButtonBoundingClientRect()).top,i=i.height,this.dMarginTop=e+i+"px"),getApp().globalData._hy_subscribe(this.handleEvent)},methods:{hideSurvey:function(){this.isLoadSurvey=!1,this.show=!1,this.survey={}},handleEvent:function(e,i){"show"===e&&(this.surveyId=i.surveyId,this.channelId=i.channelId,this.sendId=i.sendId,this.options=i.options,this.parameters=i.params,this.show=!0,this.ready=!0)},handleLoad:function(e){console.debug("load survey",e),this.survey=e,this.isLoadSurvey=!0,this.options.onLoad&&this.options.onLoad()},handleFailed:function(e){this.options.onFailed&&this.options.onFailed(e)},handleCancel:function(){this.isLoadSurvey=!1,this.show=!1},handleSubmit:function(){this.options.onSubmit&&this.options.onSubmit()},submitFailed:function(e){this.options.onSubmitFailed&&this.options.onSubmitFailed(e)},handleSize:function(e){console.debug("size",e)}}};var t=n(9453),s=(i=(0,t.Z)(i,(function(){var e=this,i=e.$createElement;i=e._self._c||i;return e.show?i("v-uni-view",{staticClass:"hy-wx-dialog_c"},[e.embedBackGround?i("v-uni-view",{staticClass:"hy-wx-mask",attrs:{catchtouchmove:"true"}}):e._e(),i("v-uni-view",{staticClass:"hy-wx-dialog",class:{"show-border":!e.embedBackGround,dialog_top:"1"==e.injectType&&"TOP"===e.embedVerticalAlign,dialog_center:"1"==e.injectType&&"CENTER"===e.embedVerticalAlign,dialog_bottom:"1"==e.injectType&&"BOTTOM"===e.embedVerticalAlign},style:{width:"calc(100% - "+e.embedPaddingWidth+" - "+e.embedPaddingWidth+")",height:e.heightValue,maxHeight:e.maxHeightValue,marginTop:"AUTO"!=e.embedHeightMode&&"100%"==e.embedHeight||"TOP"===e.embedVerticalAlign?e.dMarginTop:"0px",borderRadius:""+e.appBorderRadius}},[e.show&&(e.sendId||e.surveyId&&e.channelId)?i("hy-survey",{ref:"hySurvey",staticClass:"hy-wx-dialog-ct",attrs:{show:e.ready,surveyId:e.surveyId,delay:3e3,channelId:e.channelId,sendId:e.sendId,options:e.options,heightValue:e.heightValue,maxHeightValue:e.maxHeightValue,resize:!0,parameters:e.parameters},on:{load:function(i){arguments[0]=i=e.$handleEvent(i),e.handleLoad.apply(void 0,arguments)},loadfailed:function(i){arguments[0]=i=e.$handleEvent(i),e.handleFailed.apply(void 0,arguments)},hide:function(i){arguments[0]=i=e.$handleEvent(i),e.hideSurvey.apply(void 0,arguments)},cancel:function(i){arguments[0]=i=e.$handleEvent(i),e.handleCancel.apply(void 0,arguments)},submitfailed:function(i){arguments[0]=i=e.$handleEvent(i),e.submitFailed.apply(void 0,arguments)},size:function(i){arguments[0]=i=e.$handleEvent(i),e.handleSize.apply(void 0,arguments)},submit:function(i){arguments[0]=i=e.$handleEvent(i),e.handleSubmit.apply(void 0,arguments)}}}):e._e()],1)],1):e._e()}),[],!1,null,null,null,!1,void 0,void 0).exports,n(2502)),o=(0,t.Z)({components:{"hy-survey-monitor":i},data:function(){return{survey:null,surveyId:"6451594545514496",channelId:"6489516166858752",sendId:{NODE_ENV:"production",VUE_APP_DARK_MODE:"false",VUE_APP_NAME:"survey-ui-next-uni2",VUE_APP_PLATFORM:"h5",VUE_APP_INDEX_CSS_HASH:"63b34199",VUE_APP_INDEX_DARK_CSS_HASH:"e6047db7",VUE_APP_SURVEY_ENV:"https://www.xmplus.cn/api/survey",VUE_APP_SURVEY_ID:"6451594545514496",VUE_APP_CHANNEL_ID:"6489516166858752",VUE_APP_RESET:"false",BASE_URL:"./"}.VUE_APP_TOKEN,surveyParams:{}}},methods:{handleShow:function(){var e={onSubmit:this.handleSubmit,onSubmitFailed:this.submitFailed,onCancel:this.handleCancel,onLoad:this.handleLoad,onFailed:this.handleFailed,showType:"dialog"};(0,s.u)(this.surveyId,this.channelId,e,this.surveyParams,this.sendId)},handleCancel:function(){console.debug("已关闭问卷")},handleLoad:function(){console.debug("问卷加载成功")},handleFailed:function(e){console.error("问卷打开失败: ",e)},submitFailed:function(e){console.error("问卷提交失败: ",e)},handleSubmit:function(){console.debug("问卷已提交")}}},(function(){var e=this,i=e.$createElement;i=e._self._c||i;return i("v-uni-view",{staticClass:"popup"},[i("v-uni-view",{staticStyle:{"padding-top":"20px"}},[i("v-uni-button",{on:{click:function(i){arguments[0]=i=e.$handleEvent(i),e.handleShow.apply(void 0,arguments)}}},[e._v("弹出问卷")])],1),i("hy-survey-monitor")],1)}),[],!1,null,null,null,!1,void 0,void 0).exports}}]);