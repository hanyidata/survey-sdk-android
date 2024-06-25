# Version 0.3.1015
 - 统一版本
 - 新增发送ID场景支持

# Version 0.1.107
 - HYPopupDialog 弹出增加检测context，如果context不再显示状态不再弹出。 合并解决 Bug#2， 需要精确设置context，如果PageA切换到PageB。context不能是全局的，必须是PageA。只要PageA不再显示了问卷就不再弹出
 - HYPopupDialog 增加 close 方法，允许取消关闭。[HYPopupDialog close] 合并解决 Bug#2， 如果无法做到控制context，那么也可以主动调用close方法关闭PopupDialog。

# Version 0.1.6
- 新增弹窗支持 具体参考：HYPopupDialog.makeDialog

# Version 0.1.4
- 低版本版本兼容
  - targetSdkVersion 28
  - minSdkVersion 22
  - compileSdkVersion 30
  - kotlin 1.5.32
- 新增 onSize, onClose 事件
- 优化200初始高度
- 新增 服务器配置参数 options->server


