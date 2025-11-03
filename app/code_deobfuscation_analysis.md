# Android音乐下载应用代码还原分析

## 项目概述

这是一个Android音乐下载应用的工具类包（package q0），支持多个音乐平台：

- QQ音乐
- 网易云音乐
- 酷狗音乐
- 酷我音乐
- 咪咕音乐

## 文件分类与还原

### 1. 异常处理类

**a.java** → **CrashHandler.java**

- 全局异常处理器（UncaughtExceptionHandler）
- 捕获崩溃信息并跳转到错误页面
- 收集设备信息、应用版本等

主要方法还原：

- `b()` → `getInstance()`
- `e()` → `init()`
- `c()` → `getStackTraceString()`
- `a()` → `getDeviceInfo()`
- `d()` → `getCurrentTime()`

---

### 2. 数据类

**a0.java** → **GlobalData.java**

- 全局静态数据存储
- 包含列表数据、状态码、字符串等

字段还原：

- `f3786a` → `DEFAULT_CODE = 290`
- `f3787b` → `dataList`
- `f3788c` → `statusCode`
- `f3789d`, `f3790e`, `f3791f` → `urlString1/2/3`
- `f3792g` → `intValue`

---

### 3. UI相关类

#### **b.java** → **DialogHelper.java**

- 对话框显示工具类
- 主要方法：`a()` → `showDialog()`

#### **b0.java** → **ViewHolder.java**

- TextView和RelativeLayout的持有者类
- 用于列表视图优化

#### **t.java** → **CheckBoxHolder.java**

- CheckBox持有者类

#### **m.java** → **DialogFactory.java**

- 对话框创建工厂类
- `a()` → `createDialog()`

#### **i.java** → **StatusBarManager.java**

- 沉浸式状态栏管理
- `a()` → `setStatusBarColor()`
- 支持黑色和白色主题

#### **j.java** → **StatusBarColor.java**（枚举）

- BLACK - 黑色状态栏
- WHITE - 白色状态栏

---

### 4. 文件操作类

#### **d.java** → **FileUtils.java**

- 综合文件操作工具类

主要方法还原：

- `a()` → `getMD5(String filePath)` - 计算文件MD5
- `b()` → `copyFile(String oldPath, String newPath)` - 复制文件
- `c()` → `deleteFile(String path)` - 删除文件
- `d()` → `getExternalStoragePath(Context)` - 获取外部存储路径
- `e()` → `createDirectory(String path)` - 创建目录
- `f()` → `readFileUTF8(String path)` - 读取UTF-8文件
- `g()` → `readFileWithEncoding(String path)` - 根据配置读取文件
- `h()` → `writeFile(String content, String path, String encoding)` - 写入文件
- `i()` → `bytesToHex(byte[] bytes)` - 字节转十六进制
- `j()` → `writeId3Tags()` - 写入音频文件标签（MP3/FLAC）

#### **d0.java** → **ZipUtils.java**

- 压缩/解压缩工具类
- `a()` → `decompress(byte[])` - 解压数据
- `b()` → `compress(byte[])` - 压缩数据

---

### 5. 网络请求类

#### **f.java** → **HttpRequestUtils.java**

- HTTP请求工具类

方法还原：

- `a()` → `get(String url)` - GET请求
- `b()` → `getWithHeaders(String url, Map<String, String> headers)` - 带请求头的GET
- `c()` → `postBytes(String url, byte[] data)` - POST字节数据（带压缩）
- `d()` → `postString(String url, String data)` - POST字符串数据

#### **g.java** → **DownloadUtils.java**

- 文件下载和特殊请求工具

方法还原：

- `a()` → `downloadFile(String url, String savePath)` - 下载文件
- `b()` → `getYoudaoNote(String noteId)` - 获取有道云笔记内容
- `c()` → `inputStreamToBytes(InputStream)` - 流转字节数组
- `d()` → `getWithCookie(String url, String cookie)` - 带Cookie的GET请求
- `e()` → `postForm(String url, String formData)` - POST表单数据

#### **n.java** → **OkHttpClient.java**

- OkHttp客户端单例
- `a()` → `getInstance()` - 获取单例
- 设置20秒超时

---

### 6. 图片下载类

#### **h.java** → **ImageDownloadUtils.java**

- 多平台音乐封面下载工具

方法还原：

- `a()` → `getBitmapFromUrl(String url)` - 从URL获取Bitmap
- `b()` → `downloadImageToFile(String url, String path)` - 下载图片到文件
- `c()` → `getKugouAlbumCover(String hash)` - 酷狗专辑封面
- `d()` → `httpGet(String url)` - HTTP GET请求
- `e()` → `downloadKugouCover(String hash, String path)` - 下载酷狗封面
- `f()` → `downloadIkunshareCover(String id, String path)` - 下载ikunshare封面
- `g()` → `getIkunshareBitmap(String id)` - 获取ikunshare封面
- `j()` → `getQQMusicBitmap(String albumMid)` - QQ音乐封面
- `k()` → `downloadQQMusicCover(String albumMid, String path)` - 下载QQ音乐封面
- `l()` → `downloadQQMusicCoverBySongMid()` - 通过歌曲mid下载封面
- `m()` → `getNeteaseCloudBitmap(String id)` - 网易云封面
- `n()` → `httpGetNetease(String url)` - 网易云HTTP请求
- `o()` → `downloadNeteaseCloudCover(String id, String path)` - 下载网易云封面

#### **c0.java** → **ImageLoaderConfig.java**

- 图片加载器配置类（Universal Image Loader）
- `a()` → `getDefaultDisplayOptions()` - 获取默认显示选项
- `b()` → `initImageLoader(Context)` - 初始化图片加载器

---

### 7. 歌词下载类

#### **l.java** → **LyricDownloadUtils.java**

- 多平台歌词下载工具

方法还原：

- `a()` → `downloadKugouLyric(String hash, String savePath, String encoding)` - 酷狗歌词
- `b()` → `downloadKugouLyricAlt()` - 酷狗歌词备用方法
- `c()` → `downloadIkunshareLyric(String id, ...)` - ikunshare歌词
- `d()` → `downloadLyricFromUrl(String url, ...)` - 从URL下载歌词
- `e()` → `downloadQQMusicLyric(String songMid, ...)` - QQ音乐歌词
- `f()` → `downloadQQMusicLyricWithTranslation()` - QQ音乐歌词（带翻译）
- `g()` → `downloadNeteaseCloudLyric(String id, ...)` - 网易云歌词
- `h()` → `downloadNeteaseCloudLyricWithTranslation()` - 网易云歌词（带翻译）

---

### 8. 音乐搜索类

#### **q.java** → **MusicSearchUtils.java**

- 多平台音乐搜索核心类

方法还原：

- `a()` → `searchKugou(String keyword)` - 搜索酷狗音乐
- `b()` → `searchKuwo(String keyword)` - 搜索酷我音乐
- `c()` → `searchMigu(String keyword)` - 搜索咪咕音乐
- `d()` → `searchQQMusic(String keyword, int page)` - 搜索QQ音乐
- `e()` → `search(Platform platform, String keyword)` - 统一搜索入口
- `f()` → `searchNeteaseCloud(String keyword)` - 搜索网易云音乐

返回格式：`List<Map<String, Object>>`，包含：

- id - 歌曲ID
- name - 歌曲名
- singer - 歌手
- album - 专辑
- time - 时长
- br - 音质图标
- maxbr - 最高音质（mp3/hq/sq/hr/dsd）
- mvid - MV ID
- filename - 文件名
- filesize - 各音质文件大小
- filehash - 各音质文件哈希值

#### **p.java** → **MusicPlatform.java**（枚举）

- QQ - QQ音乐
- WYY - 网易云音乐
- MIGU - 咪咕音乐
- KUGOU - 酷狗音乐
- KUWO - 酷我音乐

---

### 9. MV相关类

#### **w.java** → **MvUrlParser.java**

- MV链接解析工具
- `a()` → `parseMvUrl(String type, String id)` - 解析MV链接
- `b()` → `parseKugouMv(String hash)` - 解析酷狗MV
- `c()` → `parseQQMusicMv(String vid)` - 解析QQ音乐MV
- `d()` → `parseNeteaseCloudMv(String id)` - 解析网易云MV

返回：不同清晰度的下载链接列表

#### **v.java** → **MvDownloader.java**

- MV下载管理器
- `a()` → `downloadMv(Context, String url, String fileName)` - 使用系统DownloadManager下载MV

---

### 10. 加密工具类

#### **u.java** → **AESUtils.java**

- AES加密工具（用于网易云音乐API）
- `a()` → `aesEncrypt(String text, String key)` - AES加密
- `b()` → `getEncSecKey()` - 获取encSecKey
- `c()` → `encryptParams(String params)` - 加密参数

---

### 11. 通用工具类

#### **s.java** → **CommonUtils.java**

- 通用工具类

方法还原：

- `a()` → `md5(String text)` - MD5加密
- `b()` → `getDeviceInfo()` - 获取设备信息JSON
- `c()` → `formatFileSize(String size)` - 格式化文件大小（转MB）
- `d()` → `installApk(Context, String path)` - 安装APK
- `e()` → `checkNotificationPermission(Context)` - 检查通知权限
- `f()` → `isNumeric(String str)` - 判断是否为数字
- `g()` → `isServiceRunning(Context, String className)` - 判断服务是否运行
- `h()` → `showNotificationPermissionDialog(Context)` - 显示通知权限对话框
- `i()` → `formatTime(int seconds)` - 格式化时间（秒转mm:ss）

#### **r.java** → **ToastUtils.java**

- Toast提示工具
- `c()` → `showToast(Context, String message)` - 显示Toast
- `d()` → `showToastCenter(Context, String message)` - 显示居中Toast

#### **z.java** → **TimeUtils.java**

- 网络时间获取
- `a()` → `getNetworkTime()` - 从苏宁服务器获取时间

---

### 12. 数据存储类

#### **e.java** → **HistoryManager.java**

- 搜索历史管理（SharedPreferences）

方法还原：

- `a()` → `clearHistory()` - 清除历史
- `b()` → `getHistory()` - 获取历史列表
- `c()` → `saveHistory(String keyword)` - 保存搜索记录（最多20条）

分隔符：`∮©∮`

#### **o.java** → **SongDatabaseHelper.java**

- SQLite数据库辅助类
- 表名：`song_list`
- 字段：id, name, path

---

### 13. 应用管理类

#### **k.java** → **AppUpdateManager.java**

- 应用更新检查和管理

方法还原：

- `i()` → `checkUpdate()` - 检查更新
- `g()` → `getSplashConfig()` - 获取启动页配置
- `h()` → `checkStoragePermission()` - 检查存储权限
- `k()` → `downloadAndInstallApk(String url)` - 下载并安装APK
- `l()` → `init()` - 初始化
- `m()` → `createDirectories()` - 创建必要的目录
- `n()` → `showProgressDialog()` - 显示进度对话框
- `o()` → `showUpdateDialog()` - 显示更新对话框
- `p()` → `showGreetingByTime(String time)` - 根据时间显示问候语

字段还原：

- `f3814q` → `STORAGE_ROOT_PATH`
- `f3815a` → `isForceUpdate` - 是否强制更新
- `f3817c` → `latestVersion` - 最新版本
- `f3818d` → `updateLog` - 更新日志
- `f3819e` → `updateTitle` - 更新标题
- `f3820f` → `downloadUrl` - 下载地址
- `f3821g` → `shareUrl` - 分享地址
- `f3822h` → `fileMd5` - 文件MD5
- `f3828n` → `tempFilePath` - 临时文件路径

#### **y.java** → **AppValidator.java**

- 应用验证类（反盗版/黑名单检查）
- `a()` → `validate()` - 验证应用
- 从有道云笔记获取验证信息

---

### 14. 其他类

#### **c.java** → **NumberEnum.java**（枚举）

- ONE, TWO, THREE

#### **x.java** → **ContextHolder.java**

- Context持有类

---

## API接口整理

### 1. ikunshare API

- 搜索：`http://api.ikunshare.com/songinfoandlrc?musicId={id}`
- 版本检查：`http://api.ikunshare.com/client/cgi-bin/check_version`
- 启动页：`http://api.ikunshare.com/client/cgi-bin/Splash`

### 2. 酷狗音乐 API

- 搜索：`http://songsearch.kugou.com/song_search_v2`
- 歌词：`https://m3ws.kugou.com/api/v1/krc/get_krc`
- 歌曲信息：`https://m.kugou.com/api/v1/song/get_song_info`
- MV：`http://trackermv.kugou.com/interface/index`

### 3. 网易云音乐 API

- 搜索：`http://music.163.com/weapi/search/get`
- 歌词：`http://music.163.com/api/song/lyric`
- 歌曲详情：`http://music.163.com/api/song/detail`
- MV：`http://music.163.com/api/mv/detail`
- 加密方式：AES-CBC

### 4. QQ音乐 API

- 搜索：`http://u6.y.qq.com/cgi-bin/musicu.fcg`
- 备用搜索：`http://app.kzti.top/client/cgi-bin/QQSearch`
- 歌词：`https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg`
- 封面：`http://y.qq.com/music/photo_new/T002R{size}M000{mid}.jpg`
- MV：`http://u.y.qq.com/cgi-bin/musicu.fcg`

### 5. 酷我音乐 API

- 搜索：`http://search.kuwo.cn/r.s`

### 6. 咪咕音乐 API

- 搜索：`https://jadeite.migu.cn/music_search/v3/search/searchAll`
- 需要签名验证

---

## 关键常量

### 音质标识

- **mp3** - 标准音质（128kbps）
- **hq** - 高品质（320kbps）
- **sq** - 无损音质（FLAC）
- **hr/hires** - 高解析度音质
- **dsd** - DSD音质

### 文件路径

- 音乐：`{STORAGE}/MusicDownloader/Music/`
- MV：`{STORAGE}/MusicDownloader/Mv/`
- APK：`{STORAGE}/MusicDownloader/Apk/`
- 临时文件：`{APP_DIR}/app_tmpFile/`

### SharedPreferences

- 文件名：`pms`
- 存储项：
    - `history` - 搜索历史
    - `storageType` - 存储类型（1=内部，2=SD卡）
    - `type` - 歌词编码类型（0=UTF-8, 1=GBK）
    - `logo_url` - 启动页图片URL
    - `status_bar_color` - 状态栏颜色

---

## 混淆规则分析

1. **类名混淆**：使用单字母命名（a-z, a0-z0）
2. **方法名混淆**：使用单字母方法名
3. **字段名混淆**：使用 f + 数字 格式
4. **保留注释**：`/* loaded from: classes.dex */`
5. **枚举未完全混淆**：枚举值保持可读
6. **字符串部分混淆**：部分中文显示为乱码

---

## 建议的重构步骤

1. **创建新包结构**：
   ```
   com.example.musicdownloader
   ├── network      (网络请求相关)
   ├── download     (下载相关)
   ├── utils        (工具类)
   ├── data         (数据管理)
   ├── ui           (UI相关)
   └── manager      (管理类)
   ```

2. **重命名类和方法**：按照上述还原的命名进行重构

3. **提取常量**：将所有硬编码的URL、Key等提取到常量类

4. **分离业务逻辑**：将网络请求与业务逻辑分离

5. **添加文档注释**：为每个公开方法添加JavaDoc

6. **代码规范化**：
    - 统一命名规范
    - 添加空行和格式化
    - 移除无用代码

---

## 安全建议

1. **API密钥保护**：代码中直接暴露了多个API的请求方式，建议加密存储
2. **证书校验**：网络请求应添加SSL证书校验
3. **反编译保护**：建议使用更强的混淆规则或加壳保护
4. **敏感信息**：设备ID、用户数据等应加密存储
5. **更新机制**：APK下载应验证签名，防止中间人攻击

---

## 技术栈识别

- **开发语言**：Java
- **最低SDK版本**：23 (Android 6.0)
- **目标SDK版本**：推测为30+ (Android 11+)
- **主要依赖库**：
    - OkHttp - 网络请求
    - Universal Image Loader - 图片加载
    - JAudioTagger - 音频标签编辑
    - Android Support/AndroidX

- **设计模式**：
    - 单例模式 (OkHttpClient)
    - 工厂模式 (DialogFactory)
    - 观察者模式 (BroadcastReceiver)
    - 持有者模式 (ViewHolder)

---

## 总结

这是一个功能完整的多平台音乐下载应用的核心工具包，实现了：

- ✅ 5个音乐平台的搜索功能
- ✅ 多音质下载支持（mp3/hq/sq/hr/dsd）
- ✅ 歌词下载（支持翻译）
- ✅ 专辑封面下载
- ✅ MV下载
- ✅ 音频标签写入
- ✅ 搜索历史管理
- ✅ 应用更新检查
- ✅ 崩溃日志收集

代码质量中等，混淆程度较高，但通过分析可以完整还原其功能和结构。
