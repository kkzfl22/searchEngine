package com.liujun.search.engine.analyze.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 网页的标签信息
 *
 * <p>标签在java代码中需采用小写，否则会出现匹配不了的情况
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/13
 */
public enum HtmlTagEnum {

  // ******************************base基础开始*************************************************************

  /** 定义一个 HTML 文档 */
  HTML_BASE_HTML("<html", "</html"),



  /** 为文档定义一个标题 */
  HTML_BASE_TITLE("<title", "</title"),

  /** 定义文档的主体 */
  HTML_BASE_BODAY("<body", "</body"),

  /** 定义 HTML 标题 */
  HTML_BASE_H1("<h1", "</h1"),

  /** 定义 HTML 标题 */
  HTML_BASE_H2("<h2", "</h2"),

  /** 定义 HTML 标题 */
  HTML_BASE_H3("<h3", "</h3"),

  /** 定义 HTML 标题 */
  HTML_BASE_H4("<h4", "</h4"),

  /** 定义 HTML 标题 */
  HTML_BASE_H5("<h5", "</h5"),

  /** 定义 HTML 标题 */
  HTML_BASE_H6("<h6", "</h6"),

  /** 定义一个段落 */
  HTML_BASE_P("<p", "</p"),


  // ******************************base基础结束*************************************************************

  // ******************************format络式开始*************************************************************

  /**
   * 标签定义首字母缩略词。
   *
   * <p>HTML5 不支持 <acronym> 标签。请使用 <abbr> 标签代替它。
   */
  HTML_FORMAT_ACRONYM("<acronym", "</acronym"),

  /**
   * 标签用来表示一个缩写词或者首字母缩略词，如"WWW"或者"NATO"。
   *
   * <p>通过对缩写词语进行标记，您就能够为浏览器、拼写检查程序、翻译系统以及搜索引擎分度器提供有用的信息。
   */
  HTML_FORMAT_ABBR("<abbr", "</abbr"),

  /** 定义文档作者或拥有者的联系信息。 */
  HTML_FORMAT_ADDRESS("<address", "</address"),

  /** 标签定义粗体的文本。 */
  HTML_FORMAT_B("<b", "</b"),

  /**
   * 允许您设置一段文本，使其脱离其父元素的文本方向设置。
   *
   * <p>标签是 HTML5 的新标签
   */
  HTML_FORMAT_BDI("<bdi", "</bdi"),

  /** 标签用来覆盖默认的文本方向。 */
  HTML_FORMAT_BDO("<bdo", "</bdo"),

  /** HTML5不再支持。 定义大号文本。 */
  HTML_FORMAT_BIG("<big", "</big"),

  /** 定义块引用。 */
  HTML_FORMAT_BLOCKQUOTE("<blockquote", "</blockquote"),

  /** HTML5不再支持。 HTML 4.01 已废弃。定义居中文本。 */
  HTML_FORMAT_CENTER("<center", "</center"),

  /**
   * <cite> 标签定义作品（比如书籍、歌曲、电影、电视节目、绘画、雕塑等等）的标题。
   *
   * <p>注释：人名不属于作品的标题。
   *
   * <p>在 HTML5 中，<cite> 标签定义作品的标题。
   *
   * <p>在 HTML 4.01 中，<cite> 标签定义一个引用。
   */
  HTML_FORMAT_CITE("<cite", "</cite"),

  /**
   * 定义计算机代码文本。
   *
   * <code> 标签是一个短语标签，用来定义计算机代码文本。
   *
   * 提示：我们并不反对使用这个标签，但是如果您只是为了达到某种视觉效果而使用这个标签的话，我们建议您使用 CSS ，这样可能会取得更丰富的效果。
   */
  HTML_FORMAT_CODE("<code", "</code"),

  /** 定义文档中已经删除的文本 */
  HTML_FORMAT_DEL("<del", "</del"),

  /** 定义项目 */
  HTML_FORMAT_DFN("<dfn", "</dfn"),

  /** 定义强调文本 */
  HTML_FORMAT_EM("<em", "</em"),

  /** HTML5不再支持。 HTML 4.01 已废弃。 定义文本的字体、尺寸和颜色 */
  HTML_FORMAT_FONT("<font", "</font"),

  /** 定义斜体文本。 */
  HTML_FORMAT_I("<i", "</i"),

  /** 定义被插入文本。 */
  HTML_FORMAT_INS("<ins", "</ins"),

  /** 定义键盘文本。 */
  HTML_FORMAT_KBD("<kbd", "</kbd"),

  /** 定义带有记号的文本。，html5新标签 */
  HTML_FORMAT_MARK("<mark", "</mark"),

  /** 定义度量衡。仅用于已知最大和最小值的度量。 HTML5新标签 */
  HTML_FORMAT_METER("<meter", "</meter"),

  /** 定义预格式文本 */
  HTML_FORMAT_PRE("<pre", "</pre"),

  /** 定义运行中的任务进度（进程）。 HTML5新标签 */
  HTML_FORMAT_PROGRESS("<progress", "</progress"),

  /** 定义短的引用。 */
  HTML_FORMAT_Q("<q", "</q"),

  /**
   * 定义不支持 ruby 元素的浏览器所显示的内容。
   *
   * <p>html5新标签
   */
  HTML_FORMAT_RP("<rp", "</rp"),

  /** 定义字符（中文注音或字符）的解释或发音。HTML5新标签 */
  HTML_FORMAT_RT("<rt", "</rt"),

  /** 定义 ruby 注释（中文注音或字符）。 */
  HTML_FORMAT_RUBY("<ruby", "</ruby"),

  /** 加删除线和文本 */
  HTML_FORMAT_S("<s", "</s"),

  /** 定义计算机代码样本。 */
  HTML_FORMAT_SAMP("<samp", "</samp"),

  /** 定义小号文本。 */
  HTML_FORMAT_SMALL("<small", "</small"),

  /** HTML5不再支持。 HTML 4.01 已废弃。 定义加删除线的文本。 */
  HTML_FORMAT_STRIKE("<strike", "</strike"),

  /** 定义语气更为强烈的强调文本。 */
  HTML_FORMAT_STRONG("<strong", "</strong"),

  /** 定义下标文本。 */
  HTML_FORMAT_SUB("<sub", "</sub"),

  /** 定义上标文本。 */
  HTML_FORMAT_SUP("<sup", "</sup"),

  /** 定义一个日期/时间 HTML5新标签 */
  HTML_FORMAT_TIME("<time", "</time"),

  /** HTML5不再支持。 定义打字机文本。 */
  HTML_FORMAT_TT("<tt", "</tt"),

  /** 定义下划线文本。 */
  HTML_FORMAT_U("<u", "</u"),

  /** 定义文本的变量部分。 */
  HTML_FORMAT_VAR("<var", "</var"),

  /** 规定在文本中的何处适合添加换行符。 */
  HTML_FORMAT_WBR("<wbr", "</wbr"),

  // ******************************format络式结束*************************************************************

  // ******************************form表单开始*************************************************************

  /** 定义一个 HTML 表单，用于用户输入。 */
  HTML_FORM_FORM("<form", "</form"),

  /** 定义多行的文本输入控件。 */
  HTML_FORM_TEXTAREA("<textarea", "</textarea"),

  /** 定义按钮。 在html表单中使用input表单中创建按钮 */
  HTML_FORM_BUTTON("<button", "</button"),

  /** 定义选择列表（下拉列表）。 */
  HTML_FORM_SELECT("<select", "</select"),

  /** 定义选择列表中相关选项的组合。 */
  HTML_FORM_OPTGROUP("<optgroup", "</optgroup"),

  /**
   * 定义选择列表中的选项。
   *
   * <p>The <option> 标签定义下拉列表中的一个选项（一个条目）。
   *
   * <p><option> 标签中的内容作为 <select> 或者<datalist> 一个元素使用。
   */
  HTML_FORM_OPTION("<option", "</option"),

  /**
   * <label> 标签为 input 元素定义标注（标记）。
   *
   * <p>label 元素不会向用户呈现任何特殊效果。不过，它为鼠标用户改进了可用性。 如果您在 label元素内点击文本，就会触发此控件。
   *
   * <p>就是说，当用户选择该标签时，浏览器就会自动将焦点转到和标签相关的表单控件上。
   *
   * <p><label> 标签的 for 属性应当与相关元素的 id 属性相同。
   */
  HTML_FORM_LABLE("<label", "</label"),

  /**
   * <fieldset> 标签可以将表单内的相关元素分组。
   *
   * <p><fieldset> 标签会在相关表单元素周围绘制边框。
   */
  HTML_FORM_FIELDSET("<fieldset", "</fieldset"),

  /** The <legend> 元素为 <fieldset>元素定义标题。 */
  HTML_FORM_LEGEND("<legend", "</legend"),

  /**
   * <datalist> 标签规定了 <input> 元素可能的选项列表。
   *
   * <p><datalist> 标签被用来在为 <input> 元素提供"自动完成"的特性。用户能看到一个下拉列表，里边的选项是预先定义好的，将作为用户的输入数据。
   *
   * <p>请使用 <input> 元素的 list 属性来绑定 <datalist> 元素。
   *
   * <p>HTML5新标签
   */
  HTML_FORM_DATALIST("<datalist", "</datalist"),

  /**
   * <output> 标签作为计算结果输出显示(比如执行脚本的输出)。
   *
   * <p>注意:Internet Explorer 浏览器不支持 <output> 标签。
   */
  HTML_FORM_OUTPUT("<output", "</output"),

  // ******************************form表单结束*************************************************************

  // ******************************frame框架开始*************************************************************

  /**
   * HTML5不再支持。 定义框架集的窗口或框架。
   *
   * <p>HTML5 不支持 <frame> 标签。
   *
   * <p><frame> 标签定义 <frameset> 中的子窗口（框架）。
   *
   * <p><frameset> 中的每个 <frame> 都可以设置不同的属性，比如 border、scrolling, noresize 等等。
   *
   * <p>注释：如果您希望验证包含框架的页面，请确保 <!DOCTYPE> 被设置为 "HTML Frameset DTD" 或者 "XHTML Frameset DTD" 。
   */
  HTML_FRAME_FRAME("<frame", "</frame"),

  /**
   * HTML5 不支持 <frameset> 标签。 <frameset> 标签定义一个框架集。
   *
   * <p><frameset> 元素被用来组织一个或者多个 <frame> 元素。每个 <frame> 有各自独立的文档。
   *
   * <p><frameset> 元素规定在框架集中存在多少列或多少行，以及每行每列占用的百分比/像素。
   *
   * <p>注释：如果您希望验证包含框架的页面，请确保 <!DOCTYPE> 被设置为 "HTML Frameset DTD" 或者 "XHTML Frameset DTD" 。
   */
  HTML_FRAME_FRAMESET("<frameset", "</frameset"),

  /**
   * HTML5 不支持 <noframes> 标签。
   *
   * <p><noframes> 元素可为那些不支持框架的浏览器显示文本。noframes 元素位于 frameset 元素内部。
   *
   * <p><noframes> 元素插入在 <frameset> 元素中使用。
   *
   * <p>注意： 如果您希望验证包含框架的页面，请确保 DTD 被设置为 "Frameset DTD"。
   */
  HTML_FRAME_NOFRAMES("<noframes", "</noframes"),

  /**
   * <iframe> 标签规定一个内联框架。
   *
   * <p>一个内联框架被用来在当前 HTML 文档中嵌入另一个文档。
   *
   * <p>在 XHTML 中，name 属性已被废弃，并将被去掉。请使用 id 属性代替。
   */
  HTML_FRAME_IFRAME("<iframe", "</iframe"),

  // ******************************frame框架结束*************************************************************

  // ******************************image图像开始*************************************************************

  /**
   * <map> 标签用于客户端图像映射。图像映射指带有可点击区域的一幅图像。
   *
   * <p><img>中的 usemap 属性可引用 <map> 中的 id 或 name 属性（取决于浏览器），所以我们应同时向 <map> 添加 id 和 name 属性。
   *
   * <p>area 元素永远嵌套在 map 元素内部。area 元素可定义图像映射中的区域。
   *
   * <p>注意: 在 HTML5 中, 如果 id 属性在<map> 标签中指定, 则你必须同样指定 name 属性。
   *
   * <p>在 XHTML 中，name 属性已经废弃，使用 id 属性替换它。
   */
  HTML_IMG_MAP("<map", "</map"),

  /**
   * IE 9、Firefox、Opera、Chrome 和 Safari 支持 <canvas> 标签。
   *
   * <p>注释：IE 8 或更早版本的 IE 浏览器不支持 <canvas> 标签。
   *
   * <p><canvas> 标签通过脚本（通常是 JavaScript）来绘制图形（比如图表和其他图像）。
   *
   * <p><canvas> 标签只是图形容器，您必须使用脚本来绘制图形。
   *
   * <p>注释：<canvas> 元素中的任何文本将会被显示在不支持 <canvas> 的浏览器中。
   *
   * <p><canvas> 标签是 HTML5 中的新标签。
   */
  HTML_IMG_CANVAS("<canvas", "</canvas"),

  /**
   * figure 标签用于对元素进行组合。
   *
   * <p>IE 9、Firefox、Opera、Chrome 和 Safari 支持 <figure> 标签。
   *
   * <p>注释：IE 8 或更早版本的 IE 浏览器不支持 <figure> 标签。
   *
   * <p><figure> 标签规定独立的流内容（图像、图表、照片、代码等等）。
   *
   * <p><figure> 元素的内容应该与主内容相关，同时元素的位置相对于主内容是独立的。如果被删除，则不应对文档流产生影响。
   *
   * <p><figure> 标签是 HTML 5 中的新标签。
   */
  HTML_IMG_FIGURE("<figure", "</figure"),

  /**
   * IE 9、Firefox、Opera、Chrome 和 Safari 支持 <figcaption> 标签。
   *
   * <p>注释：IE 8 或更早版本的 IE 浏览器不支持 <figcaption> 标签。
   *
   * <p><figcaption> 标签为 <figure> 元素定义标题。
   *
   * <p><figcaption> 元素应该被置于 <figure> 元素的第一个或最后一个子元素的位置。
   *
   * <p><figcaption> 标签是 HTML5 中的新标签。
   */
  HTML_IMG_FIGCAPTION("<figcaption", "</figcaption"),

  // ******************************image图像结束*************************************************************

  // ******************************audio音频开始*************************************************************

  /**
   * IE 9+、Firefox、Opera、Chrome 和 Safari 都支持 <audio> 标签。
   *
   * <p>注释： IE 8 或更早版本的 IE 浏览器不支持 <audio> 标签。
   *
   * <p><audio> 标签定义声音，比如音乐或其他音频流。
   *
   * <p>目前，<audio> 元素支持的3种文件格式：MP3、Wav、Ogg。
   *
   * <p>提示：可以在 <audio> 和 </audio> 之间放置文本内容，这些文本信息将会被显示在那些不支持 <audio> 标签的浏览器中。
   *
   * <p><audio> 标签是 HTML5 的新标签。
   */
  HTML_VIDEO_AUDIO("<audio", "</audio"),

  /**
   * IE 9+、Firefox、Opera、Chrome 和 Safari 都支持 <source> 标签。
   *
   * <p>注释：IE 8 或更早版本的 IE 浏览器都不支持 <source> 标签。
   *
   * <p><source> 标签为媒体元素（比如 <video> 和 <audio>）定义媒体资源。
   *
   * <p><source> 标签允许您规定两个视频/音频文件共浏览器根据它对媒体类型或者编解码器的支持进行选择。
   *
   * <p><source> 标签是 HTML5 中的新标签。
   */
  HTML_VIDEO_SOURCE("<audio", "</audio"),

  /**
   * <video> 标签定义视频，比如电影片段或其他视频流。
   *
   * <p>目前，<video> 元素支持三种视频格式：MP4、WebM、Ogg。
   *
   * <p>MP4 = MPEG 4文件使用 H264 视频编解码器和AAC音频编解码器
   *
   * <p>WebM = WebM 文件使用 VP8 视频编解码器和 Vorbis 音频编解码器
   *
   * <p>Ogg = Ogg 文件使用 Theora 视频编解码器和 Vorbis音频编解码器
   *
   * <p>音频格式的 MIME 类型 格式 MIME-type MP4 video/mp4 WebM video/webm Ogg video/ogg
   *
   * <p><video> 标签是 HTML5 的新标签。
   *
   * <p>提示：可以在 <video> 和 </video> 标签之间放置文本内容，这样不支持 <video> 元素的浏览器就可以显示出该标签的信息。
   */
  HTML_VIDEO_VIDEO("<video", "</video"),

  // ******************************audio音频结束*************************************************************

  // ******************************href链接开始*************************************************************

  /**
   * 所有主流浏览器都支持 <a> 标签。
   *
   * <p>标签定义及使用说明 <a> 标签定义超链接，用于从一个页面链接到另一个页面。
   *
   * <p><a> 元素最重要的属性是 href 属性，它指定链接的目标。
   *
   * <p>在所有浏览器中，链接的默认外观如下：
   *
   * <p>未被访问的链接带有下划线而且是蓝色的 已被访问的链接带有下划线而且是紫色的 活动链接带有下划线而且是红色的
   *
   * <p>提示：如果没有使用 href 属性，则不能使用 hreflang、media、rel、target 以及 type 属性。
   *
   * <p>提示：通常在当前浏览器窗口中显示被链接页面，除非规定了其他 target。
   *
   * <p>提示：请使用 CSS 来改变链接的样式。
   *
   * <p>在 HTML 4.01 中，<a> 标签既可以是超链接，也可以是锚。在 HTML5 中，<a> 标签是超链接，但是假如没有 href 属性，它仅仅是超链接的一个占位符。
   *
   * <p>HTML5 有一些新的属性，同时不再支持一些 HTML 4.01 的属性。
   */
  HTML_LINK_A("<a", "</a"),

  /**
   * 目前大多数浏览器支持 <nav> 标签。
   *
   * <p><nav> 标签定义导航链接的部分。
   *
   * <p>并不是所有的 HTML 文档都要使用到 <nav> 元素。<nav> 元素只是作为标注一个导航链接的区域。
   *
   * <p>在不同设备上（手机或者PC）可以制定导航链接是否显示，以适应不同屏幕的需求。
   *
   * <p><nav> 是 HTML5 的新标签。
   */
  HTML_LINK_NAV("<nav", "</nav"),

  // ******************************href链接结束*************************************************************

  // ******************************list列表开始*************************************************************
  /**
   * 所有主流浏览器都支持
   *
   * <p>ul标签定义无序列表。 将ul标签与li标签一起使用，创建无序列表。
   */
  HTML_LIST_UL("<ul", "</ul"),

  /**
   * 目前大多数浏览器支持
   *
   * <p>ol 标签。ol标签定义了一个有序列表. 列表排序以数字来显示。
   *
   * <p>使用li标签来定义列表选项。
   *
   * <p>提示： 如果需要无序列表，请使用
   *
   * <p>ul 标签。提示：使用 CSS 来定义列表样式。
   *
   * <p>在 HTML 4.01 中"start" 和 "type" 属性已经废弃，HTML5不支持该属性。
   *
   * <p"reversed" 属性是 HTML5 中的新属性。
   *
   * <p>在HTML 4.01中"compact" 属性已经废弃,在 HTML5中不支持该属性。
   */
  HTML_LIST_OL("<ol", "</ol"),

  /**
   * li 标签定义列表项目。
   *
   * <p>li 标签可用在有序列表（ol）、无序列表（ul）和菜单列表（menu）中。
   *
   * <p"type" 属性 在 HTML 4.01 已被废弃。HTML5 不支持该属性。
   *
   * <p"value" 属性 在 HTML 4.01 已被废弃。HTML5 不支持该属性。
   */
  HTML_LIST_LI("<li", "</li"),

  /**
   * 所有主流浏览器都支持 <dir> 标签。
   *
   * <p>HTML5 不支持 <dir> 标签。请用 CSS 代替。
   *
   * <p>在 HTML 4.01 中，<dir> 元素 已废弃。
   *
   * <p><dir> 标签被用来定义目录列表。\
   *
   * <p>提示：请使用 CSS 来为列表添加样式！在我们的 CSS 教程中，您可以找到更多有关为列表添加样式的细节。
   *
   * <p>HTML5 不支持 <dir> 标签，HTML 4.01 已废弃 <dir> 标签。
   */
  HTML_LIST_DIR("<dir", "</dir"),

  /**
   * dl 标签定义一个描述列表。
   *
   * <p>dl 标签与 dt （定义项目/名字）和 dd （描述每一个项目/名字）一起使用。
   *
   * <p>在 HTML 4.01 中，dl 标签定义一个定义列表。
   *
   * <p>在 HTML5 中，dl 标签定义一个描述列表。
   */
  HTML_LIST_DL("<dl", "</dl"),

  /**
   * dt 标签定义一个描述列表的项目/名字。
   *
   * <p>dt 标签与 dl （定义一个描述列表）和 dd （描述每一个项目/名字）一起使用。
   *
   * <p>在 HTML 4.01 中，dt 标签定义一个定义列表的条目。
   *
   * <p>在 HTML5 中，dt 标签定义一个描述列表的项目/名字。
   */
  HTML_LIST_DT("<dt", "</dt"),

  /**
   * dd 标签被用来对一个描述列表中的项目/名字进行描述。
   *
   * <p>dd 标签与 dl （定义一个描述列表）和 dt （定义项目/名字）一起使用。
   *
   * <p>在 dd 标签内，您能放置段落、换行、图片、链接、列表等等。
   */
  HTML_LIST_DD("<dd", "</dd"),

  /**
   * 目前主流浏览器并不支持 <menu> 标签。
   *
   * <p><menu> 标签定义了一个命令列表或菜单。
   *
   * <p><menu> 标签通常用于文本菜单，工具条和命令列表选项。
   *
   * <p>HTML 4.01的 <menu> 元素已废弃。
   *
   * <p>HTML5 中 <menu> 元素已被重新定义 。
   */
  HTML_LIST_MENU("<menu", "</menu"),

  /**
   * 目前，主流浏览器都不支持 <command> 标签。
   *
   * <p>注释：只有 IE 9 支持 <command> 标签，其他之前版本或者之后版本的 IE 浏览器不支持 <command> 标签。
   *
   * <p><command> 标签可以定义用户可能调用的命令（比如单选按钮、复选框或按钮）。
   *
   * <p>当使用 <menu> 元素时，command 元素将作为菜单或者工具栏的一部分显示出来。但是，用 command
   * 规定键盘快捷键时，command元素能被放置在页面的任何位置，但元素不可见。
   *
   * <p><command> 标签是 HTML 5 中的新标签。
   */
  HTML_LIST_COMMAND("<command", "</command"),

  // ******************************list列表结束*************************************************************

  // ******************************table表格开始*************************************************************

  /**
   * 所有主流浏览器都支持 <table> 标签。
   *
   *
   * <table> 标签定义 HTML 表格
   *
   * 一个 HTML 表格包括 <table> 元素，一个或多个 <tr>、<th> 以及 <td> 元素。
   *
   * <tr> 元素定义表格行，<th> 元素定义表头，<td> 元素定义表格单元。
   *
   * 更复杂的 HTML 表格也可能包括 <caption>、<col>、<colgroup>、<thead>、<tfoot> 以及 <tbody> 元素。
   *
   *在 HTML5 中，仅支持 "border" 属性，并且只允许使用值 "1" 或 ""。
   *
   *
   */
  HTML_TABLE_TABLE("<table", "</table"),

  /**
   * 所有主流浏览器都支持 <caption> 标签。
   *
   * <caption> 标签定义表格的标题。
   *
   * <caption> 标签必须直接放置到 <table> 标签之后。
   *
   * 您只能对每个表格定义一个标题。
   *
   * 提示：通常这个标题会被居中于表格之上。然而，CSS 属性 "text-align" 和 "caption-side" 能用来设置标题的对齐方式和显示位置。
   *
   *
   * HTML5 不支持 align 属性。
   *
   * HTML 4.01 已废弃 align 属性。
   */
  HTML_TABLE_CAPTION("<caption", "</caption"),

  /**
   * 所有主流浏览器都支持 <th> 标签。
   *
   * <p>标签定义 HTML 表格中的表头单元格。
   *
   * <p><th> 标签定义 HTML 表格中的表头单元格。
   *
   * <p>HTML 表格有两种单元格类型：
   *
   * <p>表头单元格 - 包含头部信息（由 <th> 元素创建） 标准单元格 - 包含数据（由 <td> 元素创建） <th> 元素中的文本通常呈现为粗体并且居中。
   *
   * <p><td> 元素中的文本通常是普通的左对齐文本。
   *
   * <p>HTML 5 中不再支持 HTML 4.01 中的某些属性。
   */
  HTML_TABLE_TH("<th", "</th"),

  /**
   * 所有主流浏览器都支持 <tr> 标签。
   *
   * <p><tr> 标签定义 HTML 表格中的行。
   *
   * <p>一个 <tr> 元素包含一个或多个 <th> 或 <td> 元素。
   *
   * <p>在 HTML 5 中，不支持 <tr> 标签在 HTML 4.01 中的任何属性。
   */
  HTML_TABLE_TR("<tr", "</tr"),

  /**
   * 所有主流浏览器都支持 <td> 标签。
   *
   * <p><td> 标签定义 HTML 表格中的标准单元格。
   *
   * <p>HTML 表格有两种单元格类型：
   *
   * <p>表头单元格 - 包含头部信息（由 <th> 元素创建） 标准单元格 - 包含数据（由 <td> 元素创建） <th> 元素中的文本通常呈现为粗体并且居中。
   *
   * <p><td> 元素中的文本通常是普通的左对齐文本。
   *
   * <p>提示：如果需要将内容横跨多个行或列，请使用 colspan 和 rowspan 属性！
   *
   * <p>HTML 5 中不再支持 HTML 4.01 中的某些属性。
   */
  HTML_TABLE_TD("<td", "</td"),

  /**
   * 所有主流浏览器都支持 <thead> 标签。
   *
   * <thead> 标签用于组合 HTML 表格的表头内容。
   *
   * <thead> 元素应该与 <tbody> 和 <tfoot> 元素结合起来使用，用来规定表格的各个部分（表头、主体、页脚）。
   *
   * 通过使用这些元素，使浏览器有能力支持独立于表格表头和表格页脚的表格主体滚动。当包含多个页面的长的表格被打印时，表格的表头和页脚可被打印在包含表格数据的每张页面上。
   *
   * <thead> 标签必须被用在以下情境中：作为 <table> 元素的子元素，出现在 <caption>、<colgroup> 元素之后，<tbody>、 <tfoot> 和 <tr> 元素之前。
   *
   * 注释：<thead> 元素内部必须包含一个或者多个 <tr> 标签。
   *
   * 提示：<thead>、<tbody> 和 <tfoot> 元素默认不会影响表格的布局。不过，您可以使用 CSS 来为这些元素定义样式，从而改变表格的外观。
   *
   */
  HTML_TABLE_THEAD("<thead", "</thead"),

  /**
   * 所有主流浏览器都支持 <tbody> 标签
   *
   * <tbody> 标签用于组合 HTML 表格的主体内容。
   *
   * <tbody> 元素应该与 <thead> and <tfoot> 元素结合起来使用，用来规定表格的各个部分（主体、表头、页脚）。
   *
   * 通过使用这些元素，使浏览器有能力支持独立于表格表头和表格页脚的表格主体滚动。当包含多个页面的长的表格被打印时，表格的表头和页脚可被打印在包含表格数据的每张页面上。
   *
   * <tbody> 标签必须被用在以下情境中：作为 <table> 元素的子元素，出现在 <caption>、<colgroup> 和 <thead> 元素之后。
   */
  HTML_TABLE_TBODY("<tbody", "</tbody"),

  /**
   * 所有主流浏览器都支持 <tfoot> 标签。
   *
   * <tfoot> 标签用于组合 HTML 表格的页脚内容。
   *
   * <tfoot> 元素应该与 <thead> 和 <tbody> 元素结合起来使用，用来规定表格的各个部分（页脚、表头、主体）。
   *
   * 通过使用这些元素，使浏览器有能力支持独立于表格表头和表格页脚的表格主体滚动。当包含多个页面的长的表格被打印时，表格的表头和页脚可被打印在包含表格数据的每张页面上。
   *
   * <tfoot> 标签必须被用在以下情境中：作为 <table> 元素的子元素，出现在 <caption>、<colgroup> 和 <thead> 元素之后，<tbody> 和 <tr> 元素之前。
   *
   * 注释：<tfoot> 元素内部必须包含一个或者多个 <tr> 标签。
   *
   * 提示：<thead>、<tbody> 和 <tfoot> 元素默认不会影响表格的布局。不过，您可以使用 CSS 来为这些元素定义样式，从而改变表格的外观。
   *
   */
  HTML_TABLE_TFOOT("<tfoot", "</tfoot"),

  /**
   *所有主流浏览器都支持 <colgroup> 标签。
   *
   * <colgroup> 标签用于对表格中的列进行组合，以便对其进行格式化。
   *
   * 通过使用 <colgroup> 标签，可以向整个列应用样式，而不需要重复为每个单元格或每一行设置样式。
   *
   * 注释：只能在 <table> 元素之内，在任何一个 <caption> 元素之后，在任何一个 <thead>、<tbody>、<tfoot>、<tr> 元素之前使用 <colgroup> 标签。
   *
   * 提示：如果想对 <colgroup> 中的某列定义不同的属性，请在 <colgroup> 标签内使用 <col> 标签。
   *
   * HTML5 中不再支持 HTML 4.01 中的大部分属性。
   */
  HTML_TABLE_COLGROUP("<colgroup", "</colgroup"),

  // ******************************table表格结束*************************************************************
  // ******************************style样式开始*************************************************************

  /**
   * <style> 标签定义 HTML 文档的样式信息。
   *
   * <p>在 <style> 元素中，您可以规定在浏览器中如何呈现 HTML 文档。
   *
   * <p>每个 HTML 文档能包含多个 <style> 标签。
   *
   * <p>提示：如需链接外部样式表，请使用 <link> 标签。
   *
   * <p>提示：如需学习更多有关样式表的知识，请阅读我们的 CSS 教程。
   *
   * <p>注释：如果没有使用 "scoped" 属性，则每一个 <style> 标签必须位于 head 头部区域。
   */
  HTML_STYLE_STYLE("<style", "</style"),

  /**
   * 所有主流浏览器都支持 <div> 标签。
   *
   * <p><div> 标签定义 HTML 文档中的一个分隔区块或者一个区域部分。
   *
   * <p><div>标签常用于组合块级元素，以便通过 CSS 来对这些元素进行格式化。
   *
   * <p>提示：<div> 元素经常与 CSS 一起使用，用来布局网页。
   *
   * <p>注释：默认情况下，浏览器通常会在 <div> 元素前后放置一个换行符。然而，您可以通过使用 CSS 改变这种情况
   *
   * <p>HTML5 中不支持 align 属性。
   *
   * <p>在 HTML 4.01 中，align 属性 已废弃。
   */
  HTML_STYLE_DIV("<div", "</div"),

  /**
   * 所有主流浏览器都支持 <span> 标签。
   *
   * <p>定义文档中的节。
   *
   * <p><span> 用于对文档中的行内元素进行组合。
   *
   * <p><span> 标签没有固定的格式表现。当对它应用样式时，它才会产生视觉上的变化。如果不对 <span> 应用样式，那么 <span> 元素中的文本与其他文本不会任何视觉上的差异。
   *
   * <p><span> 标签提供了一种将文本的一部分或者文档的一部分独立出来的方式。
   *
   * <p>提示：被 <span> 元素包含的文本，您可以使用 CSS 对它定义样式，或者使用 JavaScript 对它进行操作。
   */
  HTML_STYLE_SPAN("<span", "</span"),

  /**
   * IE 9、Firefox、Opera、Chrome 和 Safari 支持 <header> 标签。
   *
   * <p>注释：IE 8 或更早版本的 IE 浏览器不支持 <header> 标签。
   *
   * <p><header> 标签定义文档或者文档的一部分区域的页眉。
   *
   * <p><header> 元素应该作为介绍内容或者导航链接栏的容器。
   *
   * <p>在一个文档中，您可以定义多个 <header> 元素。
   *
   * <p>注释：<header> 标签不能被放在 <footer>、<address> 或者另一个 <header> 元素内部。
   *
   * <p><header> 标签是 HTML 5 中的新标签。
   */
  HTML_STYLE_HEADER("<header", "</header"),

  /**
   * IE 9、Firefox、Opera、Chrome 和 Safari 支持 <footer> 标签。
   *
   * <p>注释：IE 8 或更早版本的 IE 浏览器不支持 <footer> 标签。
   *
   * <p><footer> 标签定义文档或者文档的一部分区域的页脚。
   *
   * <p><footer> 元素应该包含它所包含的元素的信息。
   *
   * <p>在典型情况下，该元素会包含文档创作者的姓名、文档的版权信息、使用条款的链接、联系信息等等。
   *
   * <p>在一个文档中，您可以定义多个 <footer> 元素。
   *
   * <p><footer> 标签是 HTML 5 中的新标签。
   */
  HTML_STYLE_FOOTER("<footer", "</footer"),

  /**
   * IE 9+、Firefox、Opera、Chrome 和 Safari <section> 标签。
   *
   * <p>注释：IE 8 或更早版本的 IE 浏览器不支持 <section> 标签。
   *
   * <p><section> 标签定义了文档的某个区域。比如章节、头部、底部或者文档的其他区域。
   *
   * <p><section> 标签是 HTML5 中的新标签。
   */
  HTML_STYLE_SECTION("<section", "</section"),

  /**
   * IE 9+、Firefox、Opera、Chrome 和 Safari 都支持 <article> 标签。
   *
   * <p>注释： IE 8 或更早版本的 IE 浏览器不支持 <article> 标签。
   *
   * <p><article> 标签定义独立的内容。
   *
   * <p><article> 标签定义的内容本身必须是有意义的且必须是独立于文档的其余部分。
   *
   * <p><article> 的潜在来源：
   *
   * <p>论坛帖子 博客文章 新闻故事 评论
   *
   * <p><article> 标签是 HTML5 的新标签。
   */
  HTML_STYLE_ARTICLE("<article", "</article"),

  /**
   * IE 9+、Firefox、Opera、Chrome 和 Safari 都支持 <aside> 标签。
   *
   * <p>注释： IE 8 或更早版本的 IE 浏览器不支持 <aside> 标签。
   *
   * <p><aside> 标签定义 <article> 标签外的内容。
   *
   * <p>aside 的内容应该与附近的内容相关。
   *
   * <p><aside> 标签是 HTML5 的新标签。
   *
   * <p>提示：<aside> 的内容可用作文章的侧栏。
   */
  HTML_STYLE_ASIDE("<aside", "</aside"),

  /**
   * 目前，只有 Chrome 和 Safari 6 支持 <details> 标签。
   *
   * <p><details> 标签规定了用户可见的或者隐藏的需求的补充细节。
   *
   * <p><details> 标签用来供用户开启关闭的交互式控件。任何形式的内容都能被放在 <details> 标签里边。
   *
   * <p><details> 元素的内容对用户是不可见的，除非设置了 open 属性。
   *
   * <p><details> 标签是 HTML5 中的新标签。
   */
  HTML_STYLE_DETAILS("<details", "</details"),

  /**
   * <dialog> 标签定义一个对话框、确认框或窗口。
   *
   * <p><dialog> 标签是 HTML5 中的新标签。
   */
  HTML_STYLE_DIALOG("<dialog", "</dialog"),

  /**
   * 目前，只有 Chrome 和 Safari 6 支持 <summary> 标签。
   *
   * <p><summary> 标签为 <details> 元素定义一个可见的标题。 当用户点击标题时会显示出详细信息。
   *
   * <p><summary> 标签是 HTML5 中的新标签。
   *
   * <p>注释：<summary> 元素应该是 <details> 元素的第一个子元素。
   */
  HTML_STYLE_SUMMARY("<summary", "</summary"),

  // ******************************style样式结束*************************************************************

  // ******************************meta元信息开始*************************************************************

  /**
   * 所有主流浏览器都支持 <head> 标签。
   *
   * <p><head> 元素是所有头部元素的容器。
   *
   * <p><head> 元素必须包含文档的标题（title），可以包含脚本、样式、meta 信息 以及其他更多的信息。
   *
   * <p>以下列出的元素能被用在 <head> 元素内部：
   *
   * <p>HTML5 不再支持 profile 属性。
   */
  HTML_META_HEAD("<head", "</head"),

  // ******************************meta元信息开始*************************************************************
  // ******************************program程序开始*************************************************************

  /**
   * 所有主流浏览器都支持 <script> 标签。
   *
   * <p><script> 标签用于定义客户端脚本，比如 JavaScript。
   *
   * <p><script> 元素既可包含脚本语句，也可以通过 "src" 属性指向外部脚本文件。
   *
   * <p>JavaScript 通常用于图像操作、表单验证以及动态内容更改。
   *
   * <p>注释：如果使用 "src" 属性，则 <script> 元素必须是空的。
   *
   * <p>提示：请参阅 <noscript> 元素，对于那些在浏览器中禁用脚本或者其浏览器不支持客户端脚本的用户来说，该元素非常有用。
   *
   * <p>注释： 有多种执行外部脚本的方法：
   *
   * <p>如果 async="async"：脚本相对于页面的其余部分异步地执行（当页面继续进行解析时，脚本将被执行） 如果不使用 async 且
   * defer="defer"：脚本将在页面完成解析时执行 如果既不使用 async 也不使用 defer：在浏览器继续解析页面之前，立即读取并执行脚本
   */
  HTML_SCRIPT_SCRIPT("<script", "</script"),

  /**
   * 目前大多数浏览器支持 <noscript> 标签。
   *
   * <p>noscript 元素用来定义在脚本未被执行时的替代内容（文本）。
   *
   * <p>此标签可被用于可识别 <noscript> 标签但无法支持其中的脚本的浏览器。
   *
   * <p>在 HTML 4.01 中，<noscript> 标签只允许插入到 <body> 元素中。
   *
   * <p>在 HTML5 中，<noscript> 标签可以插入到 <head> 和 <body> 区域中。
   *
   * <p>XHTML 不支持 <noscript> 标签。
   */
  HTML_SCRIPT_NOSCRIPT("<noscript", "</noscript"),

  /**
   * 注释：某些浏览器中依然存在对 <applet> 但是需要额外的插件和安装过程才能起作用。
   *
   * <p>HTML5 不支持 <applet> 标签。请使用 <object> 标签代替它。
   *
   * <p>在 HTML 4.01 中，<applet> 元素 已废弃。
   *
   * <p><applet> 标签定义嵌入的 applet。
   *
   * <p>HTML5 不支持 <applet> 标签，HTML 4.01 已废弃 <applet> 标签。
   */
  HTML_SCRIPT_APPLET("<applet", "</applet"),

  /**
   * 目前大多数浏览器支持 <object> 标签。
   *
   * <p>定义一个嵌入的对象。请使用此元素向您的 XHTML 页面添加多媒体。此元素允许您规定插入 HTML 文档中的对象的数据和参数，以及可用来显示和操作数据的代码。
   *
   * <p><object> 标签用于包含对象，比如图像、音频、视频、Java applets、ActiveX、PDF 以及 Flash。
   *
   * <p>object 的初衷是取代 img 和 applet 元素。不过由于漏洞以及缺乏浏览器支持，这一点并未实现。
   *
   * <p>浏览器的对象支持有赖于对象类型。不幸的是，主流浏览器都使用不同的代码来加载相同的对象类型。
   *
   * <p>而幸运的是，object 对象提供了解决方案。如果未显示 object 元素，就会执行位于 <object> 和 </object> 之间的代码。通过这种方式，我们能够嵌套多个
   * object 元素（每个对应一个浏览器）。
   *
   * <p>一些 HTML 4.01 属性在 HTML5 中不被支持。
   *
   * <p"form" 是 HTML5 定义的新属性。
   *
   * <p>在 HTML5 中，objects 可以在form表单中提交。
   *
   * <p>在 HTML5 中，objects 不再出现在 <head> 元素区域内。
   */
  HTML_SCRIPT_OBJECT("<object", "</object"),

// ******************************program程序结束*************************************************************
;

  /** 标签开始 */
  private String start;

  /** 标签结束形式1 */
  private String end;

  HtmlTagEnum(String start, String end) {
    this.start = start;
    this.end = end;
  }

  public String getStart() {
    return start;
  }

  public String getEnd() {
    return end;
  }

  /**
   * 获取网页开始标签集合
   *
   * @return 开始标签信合
   */
  public static List<String> GetHtmlStartTagList() {

    List<String> getList = new ArrayList<>(values().length);

    for (HtmlTagEnum tagSection : values()) {
      getList.add(tagSection.getStart());
    }

    return getList;
  }

  /**
   * 获取网页结束标签集合
   *
   * @return 开始标签信合
   */
  public static List<String> GetHtmlEndTagList() {

    List<String> getList = new ArrayList<>(values().length);

    for (HtmlTagEnum tagSection : values()) {
      getList.add(tagSection.getEnd());
    }

    return getList;
  }
}
