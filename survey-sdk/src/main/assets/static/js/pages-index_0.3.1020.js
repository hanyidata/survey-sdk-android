"use strict";(self.webpackChunksurvey=self.webpackChunksurvey||[]).push([[769],{9716:function(e,n,s){s.r(n),s.d(n,{default:function(){return o}});n={components:{"hy-survey":s(5506).Z},data:function(){return{show:!0,surveyId:"6564986681139200",channelId:"6564987304321024",sendId:{NODE_ENV:"production",VUE_APP_DARK_MODE:"false",VUE_APP_NAME:"survey-ui-next-uni2",VUE_APP_PLATFORM:"h5",VUE_APP_INDEX_CSS_HASH:"63b34199",VUE_APP_INDEX_DARK_CSS_HASH:"e6047db7",VUE_APP_SURVEY_ENV:"https://dev.xmplus.cn/api/survey",VUE_APP_SURVEY_ID:"6564986681139200",VUE_APP_CHANNEL_ID:"6564987304321024",VUE_APP_RESET:"false",BASE_URL:"./"}.VUE_APP_TOKEN,surveyError:"",survey:{},parameters:{},options:{reset:"false",language:{NODE_ENV:"production",VUE_APP_DARK_MODE:"false",VUE_APP_NAME:"survey-ui-next-uni2",VUE_APP_PLATFORM:"h5",VUE_APP_INDEX_CSS_HASH:"63b34199",VUE_APP_INDEX_DARK_CSS_HASH:"e6047db7",VUE_APP_SURVEY_ENV:"https://dev.xmplus.cn/api/survey",VUE_APP_SURVEY_ID:"6564986681139200",VUE_APP_CHANNEL_ID:"6564987304321024",VUE_APP_RESET:"false",BASE_URL:"./"}.VUE_APP_DEFAULT_LANGUAGE,halfscreen:!1,showType:"embedded"}}},methods:{hideMonitor:function(){this.show=!1},hideSurvey:function(){this.show=!1},handleLoad:function(e){console.debug("问卷加载成功"),this.survey=e},handleFailed:function(e){console.error("问卷打开失败: ",e)},submitFailed:function(e){console.error("问卷提交失败: ",e)},handleSubmit:function(){console.debug("问卷已提交")},handleCancel:function(){console.debug("问卷已关闭"),this.isShowSurvey=!1,this.show=!1},handleSize:function(e){console.debug("size",e)}}};var o=(0,s(9453).Z)(n,(function(){var e=this,n=e.$createElement;n=e._self._c||n;return e.show?n("v-uni-view",{staticClass:"hy-app"},[e.surveyId&&e.channelId?n("hy-survey",{attrs:{delay:3e3,resize:!0,show:!0,surveyId:e.surveyId,channelId:e.channelId,sendId:e.sendId,options:e.options,parameters:e.parameters},on:{hide:function(n){arguments[0]=n=e.$handleEvent(n),e.hideMonitor.apply(void 0,arguments)},load:function(n){arguments[0]=n=e.$handleEvent(n),e.handleLoad.apply(void 0,arguments)},loadfailed:function(n){arguments[0]=n=e.$handleEvent(n),e.handleFailed.apply(void 0,arguments)},submitfailed:function(n){arguments[0]=n=e.$handleEvent(n),e.submitFailed.apply(void 0,arguments)},submit:function(n){arguments[0]=n=e.$handleEvent(n),e.handleSubmit.apply(void 0,arguments)},size:function(n){arguments[0]=n=e.$handleEvent(n),e.handleSize.apply(void 0,arguments)}}}):e._e()],1):e._e()}),[],!1,null,null,null,!1,void 0,void 0).exports}}]);