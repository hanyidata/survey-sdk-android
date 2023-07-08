"use strict";(self.webpackChunksurvey=self.webpackChunksurvey||[]).push([[249],{32249:function(e,t,i){i.d(t,{Z:function(){return c}});var s=i(96257),n=(t=(i(9653),i(57327),i(41539),i(21249),i(54747),i(92222),i(82772),{props:{survey:Object,question:Object,page:Object},computed:{style:function(){return this.survey.style},isVisible:function(){return this.question._visible&&this.question._skips<=0},gridRows:function(){var e;return this.question&&this.question.itemLayout?(e="TWO"==this.question.itemLayout?2:"THREE"==this.question.itemLayout?3:1,Math.ceil(this.question.items.length/e)):1},gridColumns:function(){return this.question&&this.question.itemLayout?"TWO"==this.question.itemLayout?2:"THREE"==this.question.itemLayout?3:1:1}},methods:{arrayfind:function(e,t){return null!=e?e.indexOf(t):-1}}}),{mixins:[t],data:function(){return{textVal:""}},beforeMount:function(){this.init()},methods:{init:function(){this.question._value&&(this.textVal=this.question._value)},handleChange:function(e){this.$emit("change",{key:this.question.name,value:e.detail.value})}}}),a=i(69453),o=(n=(0,a.Z)(n,(function(){var e=this,t=e.$createElement;t=e._self._c||t;return t("v-uni-view",{staticClass:"hy-question hy-text"},["TEXTAREA"===e.question.inputType?t("v-uni-textarea",{staticClass:"text textarea",attrs:{"placeholder-class":"placehoder-s",placeholder:e.question.placeHolder,maxlength:e.question.maxLength||-1},on:{input:function(t){arguments[0]=t=e.$handleEvent(t),e.handleChange.apply(void 0,arguments)}},model:{value:e.textVal,callback:function(t){e.textVal=t},expression:"textVal"}}):t("v-uni-input",{staticClass:"text input",attrs:{maxlength:e.question.maxLength||-1,"placeholder-class":"placehoder-s",placeholder:e.question.placeHolder},on:{input:function(t){arguments[0]=t=e.$handleEvent(t),e.handleChange.apply(void 0,arguments)}},model:{value:e.textVal,callback:function(t){e.textVal=t},expression:"textVal"}})],1)}),[],!1,null,null,null,!1,void 0,void 0).exports,i(91038),i(78783),{props:{checked:Boolean,color:String,byscore:Boolean}}),u=(o=(0,a.Z)(o,(function(){var e=this,t=e.$createElement;t=e._self._c||t;return t("v-uni-view",{staticClass:"ck-i-s-wrap",class:{"by-q-score":e.byscore}},[t("v-uni-view",{staticClass:"ck-w-input",class:{checked:e.checked},style:{backgroundColor:e.checked?e.color:"",color:e.checked?e.color:""}},[e.checked?t("v-uni-view",{staticClass:"ck-view"}):e._e()],1)],1)}),[],!1,null,null,null,!1,void 0,void 0).exports,(0,a.Z)({mixins:[t],components:{"checkbox-item":o},computed:{value:function(){var e=this.question&&this.question._value;return this.applicableLabel=-1==e,e},minValue:function(){return this.question.min},maxValue:function(){var e=10;return this.question?this.question.maxLength:e},length:function(){var e=10;return this.question&&(e=this.question.maxLength||10),Array.from({length:e},(function(e,t){return t+1}))},range:function(){var e,t,i=[];return this.question&&(e=null!=this.question.min?this.question.min:1,t=null!=this.question.max?this.question.max:10,0===e&&(t+=1),i=Array.from({length:t},(function(t,i){return i+e}))),i}},data:function(){return{applicableLabel:!1}},methods:{handleClick:function(e,t){console.debug("score clcik",e),this.applicableLabel=!1,this.$emit("change",{key:this.question.name,value:e})},handleChange:function(){this.applicableLabel=!this.applicableLabel,this.$emit("change",{key:this.question.name,value:this.applicableLabel?-1:null})}}},(function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("v-uni-view",{staticClass:"hy-question hy-score"},[e.question.showLabel?i("v-uni-view",{staticClass:"score-label"},[i("v-uni-view",{staticClass:"score-label-l left",style:{color:e.style.tagColor}},[i("v-uni-rich-text",{staticClass:"label-text",attrs:{nodes:e.question.labels[0]}})],1),i("v-uni-view",{staticClass:"score-label-l right",style:{color:e.style.tagColor}},[i("v-uni-rich-text",{staticClass:"label-text",attrs:{nodes:e.question.labels[1]}})],1)],1):e._e(),"NUMBER"==e.question.inputType?i("v-uni-view",{staticClass:"q-score Number"},e._l(e.range,(function(t,s){return i("v-uni-label",{key:s,staticClass:"q-score-i",class:{active:null!==e.value&&t<=e.value,item_left:t<=e.minValue,item_right:t==e.value||t==e.maxValue,item_mid:t>e.minValue&&t<e.maxValue},style:{backgroundColor:null!==e.value&&t<=e.value&&e.style?e.style.mainColor:"#e8eef4"},on:{click:function(i){arguments[0]=i=e.$handleEvent(i),e.handleClick(t,s)}}},[e._v(e._s(t))])})),1):i("v-uni-view",{staticClass:"q-score not-Number"},e._l(e.length,(function(t,s){return i("v-uni-label",{key:s,staticClass:"q-score-i"},[i("v-uni-view",{staticClass:"eval-emo",class:{star:"STAR"===e.question.inputType&&(!e.question._value||-1==e.question._value||e.question._value<s+1),star_active:"STAR"===e.question.inputType&&e.question._value>=s+1,icon:"ICON"===e.question.inputType&&(!e.question._value||-1==e.question._value||e.question._value<s+1),icon_active:"ICON"===e.question.inputType&&e.question._value>=s+1,like:"LIKE"===e.question.inputType&&(!e.question._value||-1==e.question._value||e.question._value<s+1),like_active:"LIKE"===e.question.inputType&&e.question._value>=s+1},on:{click:function(i){arguments[0]=i=e.$handleEvent(i),e.handleClick(t,s)}}})],1)})),1),e.question.inapplicable?i("v-uni-view",{staticClass:"q-inapplicable",on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.handleChange.apply(void 0,arguments)}}},[i("checkbox-item",{staticClass:"q-inapp-checbox",attrs:{color:e.survey.style.mainColor,checked:e.applicableLabel}}),i("v-uni-view",{staticClass:"q-in-label",style:{color:e.style.itemColor||"#333333"}},[i("v-uni-rich-text",{attrs:{nodes:e.question.inapplicableLabel}})],1)],1):e._e()],1)}),[],!1,null,null,null,!1,void 0,void 0).exports),l=(i(57658),i(38862),{props:{checked:Boolean,color:String}}),r=(l=(0,a.Z)(l,(function(){var e=this,t=e.$createElement;t=e._self._c||t;return t("v-uni-view",{staticClass:"radio-item-w"},[t("v-uni-view",{staticClass:"radio-input",class:{checked:e.checked},style:{backgroundColor:e.checked?e.color:"",borderColor:e.checked?e.color:""}},[e.checked?t("v-uni-view",{staticClass:"checked-view"}):e._e()],1)],1)}),[],!1,null,null,null,!1,void 0,void 0).exports,i(74916),i(15306),{props:{question:Object,item:Object},data:function(){return{inputModel:""}},created:function(){var e;this.question._comment&&(e=JSON.parse(this.question._comment),this.inputModel=e[this.item.value])},methods:{handleChange:function(e){var t=this.question._comment?JSON.parse(this.question._comment):{},i=this.inputModel.replace(/^\s\s*/,"").replace(/\s\s*$/,"");0<i.length?t[this.item.value]=i:delete t[this.item.value],i="{}"===JSON.stringify(t)?null:JSON.stringify(t);this.$emit("change",{key:this.question.name,value:this.question._value,comment:i})}}}),c=(r=(0,a.Z)(r,(function(){var e=this,t=e.$createElement;t=e._self._c||t;return t("v-uni-view",{staticClass:"hy-t-input",class:{"ck-input":-1<e.question._value.indexOf(e.item.value),"label-type":"LABEL"===e.question.inputType}},[t("v-uni-label",{staticClass:"required"},[e._v(e._s(e.item.isRequired?"*":""))]),t("v-uni-view",{staticClass:"item-text"},[t("v-uni-input",{staticClass:"text-input",attrs:{type:"text"},on:{input:function(t){arguments[0]=t=e.$handleEvent(t),e.handleChange.apply(void 0,arguments)}},model:{value:e.inputModel,callback:function(t){e.inputModel=t},expression:"inputModel"}})],1)],1)}),[],!1,null,null,null,!1,void 0,void 0).exports,l=(0,a.Z)({mixins:[t],components:{"hy-item-text-input":r,"radio-item":l},data:function(){return{items:[]}},created:function(){var e=this;this.question.items.forEach((function(t){e.items.push({text:t.text,value:t.value,checked:!1,enableTextInput:t.enableTextInput,isRequired:t.isRequired})}))},beforeMount:function(){this.init()},methods:{init:function(){var e=this;this.question._value&&this.question.items.forEach((function(t,i){e.items[i].checked=t.value===e.question._value}))},handleChange:function(e){var t=e.value,i=this.question._comment?JSON.parse(this.question._comment):{};this.items.forEach((function(e,s){var n=e.value;e.checked=t==e.value,i[n]&&!e.checked&&delete i[n]})),e={key:this.question.name};0<t.length&&(e.value=t),"{}"!==JSON.stringify(i)&&(e.comment=JSON.stringify(i)),this.$emit("change",e)},handleItemInputChange:function(e){this.$emit("change",e)}}},(function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("v-uni-view",{staticClass:"hy-question hy-single-choice",class:{"column-and-tag":"LABEL"===e.question.inputType&&"ONE"!==e.question.itemLayout,"tag-item":"LABEL"===e.question.inputType&&"ONE"===e.question.itemLayout,"n-column":"LABEL"!==e.question.inputType&&"ONE"!==e.question.itemLayout}},[i("v-uni-view",{directives:[{name:"show",rawName:"v-show",value:e.question&&e.question.items,expression:"question && question.items"}],staticClass:"radio-group",style:{"grid-template-rows":"repeat("+e.gridRows+", 1fr)","grid-template-columns":"repeat("+e.gridColumns+", 1fr)"}},e._l(e.items,(function(t,s){return i("v-uni-view",{key:s,staticClass:"radio-i",class:{checked:t.checked},style:{backgroundColor:"LABEL"!==e.question.inputType||t.checked?"":e.style.defaultTagPadding}},[i("v-uni-label",{staticClass:"radio-i-row",style:{backgroundColor:"LABEL"===e.question.inputType&&t.checked?e.style.selectedTagPadding:""},on:{click:function(i){arguments[0]=i=e.$handleEvent(i),e.handleChange(t)}}},["LABEL"!==e.question.inputType?i("radio-item",{attrs:{byscore:!1,color:e.style.mainColor,checked:t.checked}}):e._e(),i("v-uni-label",{staticClass:"radio-i-label item-content",style:{color:e.style.itemColor||"#333333"}},[i("v-uni-rich-text",{staticClass:"radio-i-text",attrs:{nodes:t.text}})],1)],1),t&&t.enableTextInput&&e.question._value&&t.checked?i("v-uni-view",{staticClass:"item-text-input"},[i("hy-item-text-input",{key:t.id,attrs:{question:e.question,item:t},on:{change:function(t){arguments[0]=t=e.$handleEvent(t),e.handleItemInputChange.apply(void 0,arguments)}}})],1):e._e()],1)})),1)],1)}),[],!1,null,null,null,!1,void 0,void 0).exports,r=(i(26699),i(32023),(0,a.Z)({mixins:[t],components:{"hy-item-text-input":r,"checkbox-item":o},data:function(){return{items:[]}},created:function(){var e=this;this.question.items.forEach((function(t){e.items.push({text:t.text,value:t.value,checked:!1,enableTextInput:t.enableTextInput,isRequired:t.isRequired})}))},beforeMount:function(){this.init()},methods:{itemCheckedClass:function(){return function(e){return this.question._value&&-1<this.question._value.indexOf(e.value)?"checked":""}},itemLabelBgColor:function(){return function(e){return"LABEL"!==this.question.inputType||this.question._value&&-1<this.question._value.indexOf(e.value)?{}:{backgroundColor:this.style.defaultTagPadding}}},init:function(){var e=this;this.question._value&&this.items.forEach((function(t,i){e.items[i].checked=-1<e.question._value.indexOf(t.value)}))},handleChange:function(e){var t=this.question._value||[],i=this.question._comment?JSON.parse(this.question._comment):{},s=(this.items.forEach((function(s,n){var a=s.value;a===e.value?s.checked=!s.checked:s.checked=t.includes(a),i[a]&&!s.checked&&delete i[a]})),this.items.filter((function(e){return e.checked})).map((function(e){return e.value}))),n={key:this.question.name};0<s.length&&(n.value=s),"{}"!==JSON.stringify(i)&&(n.comment=JSON.stringify(i)),this.$emit("change",n)},handleItemInputChange:function(e){this.$emit("change",e)}}},(function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("v-uni-view",{staticClass:"hy-question hy-multiple-choice",class:{"column-and-tag":"LABEL"===e.question.inputType&&"ONE"!==e.question.itemLayout,"tag-item":"LABEL"===e.question.inputType&&"ONE"===e.question.itemLayout,"n-column":"LABEL"!==e.question.inputType&&"ONE"!==e.question.itemLayout}},[i("v-uni-view",{directives:[{name:"show",rawName:"v-show",value:e.question&&e.question.items,expression:"question && question.items"}],staticClass:"ck-group",style:{"grid-template-rows":"repeat("+e.gridRows+", 1fr)","grid-template-columns":"repeat("+e.gridColumns+", 1fr)"}},e._l(e.items,(function(t,s){return i("v-uni-view",{key:s,staticClass:"ck-i",class:{checked:t.checked},style:{backgroundColor:"LABEL"!==e.question.inputType||t.checked?"":e.style.defaultTagPadding}},[i("v-uni-label",{staticClass:"ck-i-row",style:{backgroundColor:"LABEL"===e.question.inputType&&t.checked?e.style.selectedTagPadding:""},on:{click:function(i){arguments[0]=i=e.$handleEvent(i),e.handleChange(t)}}},["LABEL"!==e.question.inputType?i("checkbox-item",{attrs:{byscore:!1,color:e.style.mainColor,checked:t.checked}}):e._e(),i("v-uni-label",{staticClass:"ck-label item-content",style:{color:e.style.itemColor||"#333333"}},[i("v-uni-rich-text",{staticClass:"ck-l-text",attrs:{nodes:t.text}})],1)],1),t&&t.enableTextInput&&e.question._value&&t.checked?i("v-uni-view",{staticClass:"item-text-input"},[i("hy-item-text-input",{key:t.id,attrs:{question:e.question,item:t},on:{change:function(t){arguments[0]=t=e.$handleEvent(t),e.handleItemInputChange.apply(void 0,arguments)}}})],1):e._e()],1)})),1)],1)}),[],!1,null,null,null,!1,void 0,void 0).exports),o=(i(40561),{data:function(){return{currentIndex:-1,checkedTags:[],checkedIndex:[],textarea:"",tagDefaultStyle:null,tagActiveStyle:null,imageTypeClass:""}},computed:{currentTags:function(){return-1<this.currentIndex?this.question.items[this.currentIndex].configure.split(","):[]},isTagsActive:function(){var e=this,t=[];return this.currentTags.forEach((function(i){-1<e.arrayfind(e.checkedTags,i)?t.push(!0):t.push(!1)})),t}},beforeMount:function(){this.init()},methods:{init:function(){var e=this;!this.question._value&&this.question.isDefaultValue&&(this.question._value=this.question.evaluationValue),this.question._value&&(this.question.items.forEach((function(t,i){var s;t.value===e.question._value&&(e.currentIndex=i,(s=e.question._tags)&&s.length&&t.configure&&t.configure.split(",").forEach((function(t,i){-1<s.indexOf(t)&&(e.checkedIndex.push(i),e.checkedTags.push(t))})),e.textarea=e.question._comment)})),this.question._comment)&&(this.textarea=this.question._comment),this.tagDefaultStyle={background:this.style.defaultTagPadding,color:this.style.tagColor,borderColor:this.style.defaultTagStroke},this.tagActiveStyle={background:this.style.selectedTagPadding,color:this.style.mainColor,borderColor:this.style.selectedTagStroke}},checkEmo:function(e,t){this.checkedTags=[],this.checkedIndex=[],this.currentIndex=t,this.changeValue({value:e.value})},changeValue:function(e){var t={key:this.question.name,value:-1<this.currentIndex?this.question.items[this.currentIndex].value:null,comment:this.textarea||null,tags:0<this.checkedTags.length?this.checkedTags:null};this.$emit("change",t)},checkTag:function(e,t){var i=this.checkedIndex.indexOf(t);-1!=i?(this.checkedIndex.splice(i,1),this.checkedTags.splice(i,1)):(this.checkedIndex.push(t),this.checkedTags.push(e)),this.question._tags=this.checkedTags,this.changeValue({tags:this.checkedTags})},checkText:function(){this.changeValue({comment:this.textarea})}}}),n={components:{"hy-question-text":n,"hy-question-score":u,"hy-question-single-choice":l,"hy-question-multiple-choice":r,"hy-question-evaluation":(0,a.Z)({mixins:[t,o]},(function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("v-uni-view",{staticClass:"hy-question hy-evalution"},[i("v-uni-view",{staticClass:"show-images"},e._l(e.question.items,(function(t,s){return i("v-uni-view",{key:s,staticClass:"eval-img-i",on:{click:function(i){arguments[0]=i=e.$handleEvent(i),e.checkEmo(t,s)}}},[i("v-uni-image",{staticClass:"eval-emo",class:{"index-0":0===s,"index-1":1===s&&5==e.question.items.length,"index-2":2===s&&5==e.question.items.length||1===s&&3==e.question.items.length,"index-3":3===s&&5==e.question.items.length,"index-4":4===s&&5==e.question.items.length||2===s&&3==e.question.items.length||1===s&&2==e.question.items.length,"eval-shadow":e.question._value===t.value||!e.question._value&&e.question.isDefaultValue&&e.question.evaluationValue===t.value}}),"ALWAYS"===e.question.itemLayout?[i("v-uni-view",{staticClass:"eval-text-i"},[i("v-uni-rich-text",{staticClass:"eval-desc 2",style:{color:e.question.value===t.value?e.style.mainColor:e.style.tagColor},attrs:{nodes:t.text}})],1)]:e._e()],2)})),1),"SELECT"===e.question.itemLayout&&-1<e.currentIndex?i("v-uni-view",{staticClass:"show-texts"},[-1<e.currentIndex?i("v-uni-view",{staticClass:"eval-text-i"},[i("v-uni-rich-text",{staticClass:"eval-desc",style:{color:e.style.tagColor},attrs:{nodes:e.question.items[e.currentIndex].text}})],1):e._e()],1):e._e(),-1<e.currentIndex&&e.question.items&&e.question.items[e.currentIndex]&&e.question.items[e.currentIndex].configure?i("v-uni-view",{class:{"show-tags":!0,two:2===e.currentTags.length,"adaptive-tag":"ADAPTIVE"===e.question.labelLayout}},e._l(e.currentTags,(function(t,s){return i("v-uni-view",{key:s,staticClass:"eval-tag-i flex-center",class:{active:e.isTagsActive[s]},style:{background:e.isTagsActive[s]?e.style.selectedTagPadding:e.style.defaultTagPadding,color:e.isTagsActive[s]?e.style.mainColor:e.style.tagColor,borderColor:e.isTagsActive[s]?e.style.selectedTagStroke:e.style.defaultTagStroke},on:{click:function(i){arguments[0]=i=e.$handleEvent(i),e.checkTag(t,s)}}},[i("v-uni-rich-text",{staticClass:"rich-text",attrs:{nodes:t}})],1)})),1):e._e(),e.question.hasOther&&-1<e.currentIndex?i("v-uni-textarea",{staticClass:"eval-area",style:{backgroundColor:e.style.defaultTagPadding,color:e.style.itemColor,border:"1px solid "+e.style.defaultTagStroke},attrs:{type:"textarea",placeholder:e.question.placeHolder,maxlength:"300"},on:{input:function(t){arguments[0]=t=e.$handleEvent(t),e.checkText.apply(void 0,arguments)}},model:{value:e.textarea,callback:function(t){e.textarea=t},expression:"textarea"}}):e._e()],1)}),[],!1,null,null,null,!1,void 0,void 0).exports,"hy-question-score-evaluation":(0,a.Z)({mixins:[t,o]},(function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("v-uni-view",{staticClass:"hy-question hy-score-eval"},[i("v-uni-view",{staticClass:"show-images",class:[e.imageTypeClass]},e._l(e.question.items,(function(t,s){return i("v-uni-view",{key:s,staticClass:"q-image-i",class:{active:e.currentIndex>=s},on:{click:function(i){arguments[0]=i=e.$handleEvent(i),e.checkEmo(t,s)}}},[i("v-uni-image",{staticClass:"eval-emo",class:{star:("STAR"===e.question.inputType||"STAR"!==e.question.inputType&&(!e.configureJSON||!e.customImgs.defaultIcon))&&(-1==e.currentIndex||e.currentIndex<s),star_active:("STAR"===e.question.inputType||"STAR"!==e.question.inputType&&(!e.configureJSON||!e.customImgs.activeIcon))&&e.currentIndex>=s,icon:"ICON"===e.question.inputType&&(-1==e.currentIndex||e.currentIndex<s),icon_active:"ICON"===e.question.inputType&&e.currentIndex>=s,like:"LIKE"===e.question.inputType&&(-1==e.currentIndex||e.currentIndex<s),like_active:"LIKE"===e.question.inputType&&e.currentIndex>=s,custom:"CUSTOM"===e.question.inputType&&e.configureJSON&&e.customImgs.defaultIcon&&(-1==e.currentIndex||e.currentIndex<s),custom_active:"CUSTOM"===e.question.inputType&&e.configureJSON&&e.customImgs.activeIcon&&e.currentIndex>=s},attrs:{src:"CUSTOM"==e.question.inputType?e.currentIndex>=s?e.customImgs.activeIcon||"":e.customImgs.defaultIcon||"":""}})],1)})),1),-1<e.currentIndex?i("v-uni-view",{staticClass:"show-texts"},[i("v-uni-view",{staticClass:"q-text-i"},[i("v-uni-rich-text",{staticClass:"eval-desc",style:{color:e.style.tagColor},attrs:{nodes:e.question.items[e.currentIndex].text}})],1)],1):e._e(),-1<e.currentIndex&&e.question.items&&e.question.items[e.currentIndex]&&e.question.items[e.currentIndex].configure?i("v-uni-view",{class:{"show-tags":!0,two:2===e.currentTags.length,"adaptive-tag":"ADAPTIVE"===e.question.labelLayout}},e._l(e.currentTags,(function(t,s){return i("v-uni-view",{key:s,staticClass:"q-tag-i flex-center",class:{active:e.isTagsActive[s]},style:{background:e.isTagsActive[s]?e.style.selectedTagPadding:e.style.defaultTagPadding,color:e.isTagsActive[s]?e.style.mainColor:e.style.tagColor,borderColor:e.isTagsActive[s]?e.style.selectedTagStroke:e.style.defaultTagStroke},on:{click:function(i){arguments[0]=i=e.$handleEvent(i),e.checkTag(t,s)}}},[i("v-uni-rich-text",{staticClass:"rich-text",attrs:{nodes:t}})],1)})),1):e._e(),e.question&&e.question.hasOther&&-1<e.currentIndex?i("v-uni-textarea",{staticClass:"eval-area",style:{backgroundColor:e.style.defaultTagPadding,color:e.style.itemColor,border:"1px solid "+e.style.defaultTagStroke},attrs:{type:"textarea",placeholder:e.question.placeHolder,maxlength:"300"},on:{input:function(t){arguments[0]=t=e.$handleEvent(t),e.checkText.apply(void 0,arguments)}},model:{value:e.textarea,callback:function(t){e.textarea=t},expression:"textarea"}}):e._e()],1)}),[],!1,null,null,null,!1,void 0,void 0).exports},props:{survey:Object,page:Object},data:function(){return{componentsMap:{SCORE:"hy-question-score",SINGLE_CHOICE:"hy-question-single-choice",MULTIPLE_CHOICES:"hy-question-multiple-choice",TEXT:"hy-question-text",EVALUATION:"hy-question-evaluation",SCORE_EVALUATION:"hy-question-score-evaluation"}}},methods:{handleQuestionChange:function(e){var t=e.key,i=e.value,s=e.comment;e=e.tags;console.debug("question ".concat(t," data changed=> value: ").concat(i," comment: ").concat(s," tags: ").concat(e)),getApp().globalData._hy_state.survey.updateValue(t,i,s,e)}}},u={components:{"hy-survey-page":(0,a.Z)(n,(function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("v-uni-view",{staticClass:"hy-sv-page"},e._l(e.page.questions,(function(t,n){return s("v-uni-view",{key:n,staticClass:"question"},[t._visible&&t._skips<=0?s("v-uni-view",[s("v-uni-view",{staticClass:"hy-q-title",style:{color:e.survey.style&&e.survey.style.questionColor||"#333333"}},[e.survey.showQuestionNumbers?s("v-uni-label",{staticClass:"index"},[e._v(e._s("MANUALLY"==e.survey.questionNumberMode?t.code:t._no)+".")]):e._e(),t.isRequired?s("v-uni-label",{staticClass:"required"},[e._v("*")]):e._e(),s("v-uni-rich-text",{staticClass:"text label",attrs:{nodes:t.title}})],1),s("v-uni-view",{directives:[{name:"show",rawName:"v-show",value:!t._valid,expression:"!item._valid"}],staticClass:"hy-q-error"},[s("v-uni-image",{staticClass:"icon",attrs:{mode:"widthFix",src:i(58594),alt:""}}),s("v-uni-text",{staticClass:"message"},[e._v(e._s(t._error))])],1),s("v-uni-view",{staticClass:"hy-q-body"},["hy-question-score"==e.componentsMap[t.type]?[s("hy-question-score",{key:t.id,attrs:{question:t,page:e.page,survey:e.survey},on:{change:function(t){arguments[0]=t=e.$handleEvent(t),e.handleQuestionChange.apply(void 0,arguments)}}})]:"hy-question-single-choice"==e.componentsMap[t.type]?[s("hy-question-single-choice",{key:t.id,attrs:{question:t,page:e.page,survey:e.survey},on:{change:function(t){arguments[0]=t=e.$handleEvent(t),e.handleQuestionChange.apply(void 0,arguments)}}})]:e._e(),"hy-question-multiple-choice"==e.componentsMap[t.type]?[s("hy-question-multiple-choice",{key:t.id,attrs:{question:t,page:e.page,survey:e.survey},on:{change:function(t){arguments[0]=t=e.$handleEvent(t),e.handleQuestionChange.apply(void 0,arguments)}}})]:"hy-question-text"==e.componentsMap[t.type]?[s("hy-question-text",{key:t.id,attrs:{question:t,page:e.page,survey:e.survey},on:{change:function(t){arguments[0]=t=e.$handleEvent(t),e.handleQuestionChange.apply(void 0,arguments)}}})]:e._e(),"hy-question-evaluation"==e.componentsMap[t.type]?[s("hy-question-evaluation",{key:t.id,attrs:{question:t,page:e.page,survey:e.survey},on:{change:function(t){arguments[0]=t=e.$handleEvent(t),e.handleQuestionChange.apply(void 0,arguments)}}})]:"hy-question-score-evaluation"==e.componentsMap[t.type]?[s("hy-question-score-evaluation",{key:t.id,attrs:{question:t,page:e.page,survey:e.survey},on:{change:function(t){arguments[0]=t=e.$handleEvent(t),e.handleQuestionChange.apply(void 0,arguments)}}})]:e._e()],2)],1):e._e()],1)})),1)}),[],!1,null,null,null,!1,void 0,void 0).exports},props:{delay:Number,borderRadiusMode:{type:String,default:""},resize:Boolean,show:Boolean,surveyId:String,channelId:String,options:Object,parameters:Object},computed:{pages:function(){return this.survey&&this.survey.pages?this.survey.pages.filter((function(e){return!e.isEmpty})):[]},topRadius:function(){return this.survey&&this.survey.channel&&this.borderRadiusMode&&"TOP"!=this.borderRadiusMode?this.survey.channel.configure.appBorderRadius:"0px"},bottomRadius:function(){return this.survey&&this.survey.channel&&this.borderRadiusMode&&"BOTTOM"!=this.borderRadiusMode?this.survey.channel.configure.appBorderRadius:"0px"},pageIndex:function(){return this.survey&&this.survey._currentPage&&this.survey._currentPage._index||0},hasNextPage:function(){var e=this;return!!(this.survey&&this.survey.pages&&this.survey.pages.length>this.pageIndex+1&&0<this.pages.filter((function(t){return t._index>e.pageIndex&&!t.isEmpty})).map((function(e){return e._index})).length)},hasPreviousPage:function(){return!!(this.survey&&this.pages&&0<this.pages.length&&0<this.pageIndex)},sessionInfo:function(){return this.survey?getApp().globalData._hy_state.service.sessions[this.survey.id]:{}},abnormalConcludingRemark:function(){if(this.survey)try{var e=JSON.parse(this.survey.abnormalConcludingRemark);return e&&"object"===(0,s.Z)(e)?e.text:this.survey.abnormalConcludingRemark}catch(e){return this.survey.abnormalConcludingRemark}return""},concludingRemark:function(){if(this.survey)try{var e=JSON.parse(this.survey.concludingRemark);return e&&"object"===(0,s.Z)(e)?e.text:this.survey.concludingRemark}catch(e){return this.survey.concludingRemark}return""},backgroundColor:function(){return this.survey&&this.survey.style&&this.survey.showBackground?this.survey.style.backgroundColor:""}},data:function(){return{observer:null,survey:null,status:"init",surveyError:"",scrollTop:0,surveyIsRequesting:!1,isRequesting:!1,hideInterval:null,scrollInter:null,isH5:null!=window}},created:function(){getApp().globalData._hy_subscribe(this.goNextPageEve)},mounted:function(){var e,t=this;this.resize&&(this.observer=new ResizeObserver((function(e){e.forEach((function(e){console.debug("onSize: "+e.contentRect.height),t.$emit("size",{width:e.contentRect.width,height:e.contentRect.height})}))})),e=document.querySelector(".sv-scroll-view"),this.observer.observe(e,{attributes:!0,childList:!0,subtree:!0})),console.debug("survey created",this.surveyId,this.channelId,this.options,this.parameters),this.loadSurvey()},unmounted:function(){this.observer&&(this.observer.disconnect(),this.observer=null)},methods:{distubUploadEve:function(e){var t=this.survey&&this.survey.responseId||"";getApp().globalData._hy_state.service.distubUploadSend(this.surveyId,e,t)},goNextPageEve:function(e,t){"goNextPage"===e&&this.handleNext()},loadSurvey:function(){var e=this;this.surveyId&&this.channelId&&getApp().globalData._hy_state.service.loadSurveyFromServer(this.surveyId,this.channelId,this.options,this.parameters).then((function(t){e.survey=t,getApp().globalData._hy_survey=e.survey,e.$emit("load",e.survey),e.distubUploadEve("OPEN")})).catch((function(t){e.surveyError=t||"系统错误",e.surveyIsRequesting=!1,e.$emit("loadfailed",t),e.$emit("hide",t)}))},handleSubmit:function(){var e=this;this.isRequesting||(this.isRequesting=!0,this.survey.performSubmit(this.sessionInfo,this.parameters).then((function(t){t&&(e.survey._isEarlyCompleted&&(e.surveyError="非常抱歉，你不适合本次调查的人群条件，感谢您的参与!"),e.survey._isFinished)&&(e.status="finish"),getApp().globalData._hy_unsubscribe(e.goNextPageEve),e.isRequesting=!1,e.$emit("submit"),e.handleHide()})).catch((function(t){e.isRequesting=!1,t&&(e.surveyError=t||"系统错误",e.$emit("submitfailed",t),e.handleHide())})))},handleHide:function(){var e=this;this.hideInterval=setTimeout((function(){e.$emit("hide","autoclose")}),this.delay)},handleNext:function(){var e=this;this.surveyError="",this.isRequesting||(this.isRequesting=!0,this.survey.performNext(this.sessionInfo,this.parameters).then((function(t){t&&(e.survey._isEarlyCompleted&&(e.surveyError="非常抱歉，你不适合本次调查的人群条件，感谢您的参与!",e.handleHide()),e.survey._isFinished?e.status="finish":(e.survey.performNextPage(),e.scrollToHead())),e.isRequesting=!1})).catch((function(t){e.isRequesting=!1,t&&(e.surveyError=t||"error",e.handleHide())})))},handleScroll:function(e){this.scrollTop=e.detail.scrollTop,console.debug("scrollTop",this.scrollTop)},scrollToHead:function(){var e=this;this.scrollInter=setTimeout((function(){e.scrollTop=0,e.scrollInter=null,clearTimeout(this.scrollInter)}),1)},handlePrevious:function(){this.survey.performPrevPage(),this.scrollToHead()}},beforeDestroy:function(){this.hideInterva&&clearTimeout(this.hideInterval),this.hideInterval=null,this.scrollInter&&clearTimeout(this.scrollInter),this.scrollInter=null}},(0,a.Z)(u,(function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("v-uni-scroll-view",{staticClass:"hy-survey-wrap",class:{mp:!e.isH5},style:{borderTopLeftRadius:e.topRadius,borderTopRightRadius:e.topRadius,borderBottomLeftRadius:e.bottomRadius,borderBottomRightRadius:e.bottomRadius,backgroundColor:e.survey&&e.survey.showBackground&&e.backgroundColor?e.backgroundColor:"white"},attrs:{"scroll-y":"true","scroll-top":e.scrollTop}},[e.surveyIsRequesting?s("v-uni-view",{staticClass:"sv-loading"},[s("v-uni-view",{staticClass:"out-div"})],1):e._e(),e.surveyError||"finish"==e.status?s("v-uni-view",{staticClass:"hy-survey flex-center"},[s("v-uni-view",{staticClass:"hy-finish",class:{error:e.surveyError,"flex-center":!e.surveyError}},[e.surveyError?s("v-uni-view",{staticClass:"text-error"},[s("img",{attrs:{src:i(22105),alt:""}}),s("v-uni-view",{staticClass:"remark"},[e._v(e._s(e.surveyError))])],1):e.survey?s("v-uni-view",{staticClass:"default-finish"},[s("v-uni-rich-text",{staticClass:"text",class:e.survey.isEarlyCompleted?"early":"",attrs:{nodes:e.survey.isEarlyCompleted?e.abnormalConcludingRemark:e.concludingRemark}})],1):e._e()],1)],1):e.show?s("v-uni-view",{staticClass:"hy-survey sv-scroll-view",attrs:{"scroll-top":e.scrollTop},on:{scroll:function(t){arguments[0]=t=e.$handleEvent(t),e.handleScroll.apply(void 0,arguments)}}},[e.survey&&e.pages&&e.show?s("v-uni-view",{staticClass:"survey-pages"},[e._l(e.pages,(function(t,i){return[t.isEmpty?e._e():s("v-uni-view",{key:i,staticClass:"pages-row"},[t&&e.pageIndex===t._index?s("hy-survey-page",{attrs:{index:t._index,page:t,survey:e.survey}}):e._e()],1)]})),s("v-uni-view",{directives:[{name:"show",rawName:"v-show",value:e.survey._hasValue,expression:"survey._hasValue"}],staticClass:"survey-actions",class:{"only-one-btn":!e.hasPreviousPage,"two-btn":e.hasPreviousPage}},[e.hasPreviousPage?s("v-uni-button",{staticClass:"action previous",style:{backgroundColor:"#f6f6f6"},attrs:{size:"mini",type:"primary"},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.handlePrevious()}}},[e._v("上一页")]):e._e(),e.hasNextPage?s("v-uni-button",{staticClass:"action next",class:{exclusive:!e.hasPreviousPage,btn_loading:e.isRequesting},style:{backgroundColor:e.survey.style.enableCustomButton?e.survey.style.buttonColor:e.survey.style.mainColor},attrs:{size:"mini",type:"primary"},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.handleNext()}}},[e._v("下一页")]):e._e(),e.hasNextPage?e._e():s("v-uni-button",{staticClass:"action submit",class:{exclusive:!e.hasPreviousPage,btn_loading:e.isRequesting},style:{backgroundColor:e.survey.style.enableCustomButton?e.survey.style.buttonColor:e.survey.style.mainColor},attrs:{size:"mini",type:"primary"},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.handleSubmit()}}},[e._v("提交")])],1)],2):e._e(),e.survey&&e.survey.showBrand?s("v-uni-view",{staticClass:"survey-footer"},[e.survey.brandLogo?s("v-uni-view",{staticClass:"footer-logo logo-view",style:{backgroundImage:"url("+e.survey.brandLogo+")",backgroundSize:"60px 14px"}}):s("v-uni-image",{staticClass:"footer-logo no-logo"})],1):e._e()],1):s("v-uni-view",{staticClass:"survey-errored"})],1)}),[],!1,null,"1a700f22",null,!1,void 0,void 0).exports)},58594:function(e,t,i){e.exports=i.p+"static/img/hy-question-error.svg"},22105:function(e,t,i){e.exports=i.p+"static/img/stopSurvey_page.svg"}}]);