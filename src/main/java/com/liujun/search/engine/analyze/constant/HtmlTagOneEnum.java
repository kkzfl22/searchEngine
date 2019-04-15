package com.liujun.search.engine.analyze.constant;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/04/13
 */
public enum HtmlTagOneEnum {
  /** 定义文档类型。 */
  HTML_BASE_TYPE("<!doctype", ">"),

  /** 一个简单的换行符。 */
  HTML_BASE_BR("<br", ">", "/>"),

  /** 定义水平线 */
  HTML_BASE_HR("<hr", ">", "/>"),

  /** 规定了用户可以在其输入数据的输入字段 */
  HTML_FORM_INPUT("<input", ">", "/>"),

  /**
   * <keygen> 标签规定用于表单的密钥对生成器字段。
   *
   * <p>当提交表单时，私钥存储在本地，公钥发送到服务器。
   *
   * <p>HTML5新标签
   */
  HTML_FORM_KEYGEN("<keygen", ">", "/>"),

  /**
   * 定义图像。
   *
   * <p><img> 标签定义 HTML 页面中的图像。
   *
   * <p><img> 标签有两个必需的属性：src 和 alt。
   *
   * <p>注释：从技术上讲，图像并不会插入 HTML 页面中，而是链接到 HTML 页面上。<img> 标签的作用是为被引用的图像创建占位符。
   *
   * <p>提示：通过在 <a> 标签中嵌套 <img> 标签，给图像添加到另一个文档的链接。
   *
   * <p>HTML5 中不支持以下属性：align、border、hspace、longdesc、vspace。
   *
   * <p>在 HTML 4.01 中，以下属性：align、border、hspace、vspace 已废弃。
   *
   * <p>在 HTML 中，<img> 标签没有结束标签。
   *
   * <p>在 XHTML 中，<img> 标签必须被正确地关闭。
   */
  HTML_IMG_IMG("<img", ">", "/>"),

  /**
   * <area> 标签定义图像映射内部的区域（图像映射指的是带有可点击区域的图像）。
   *
   * <p><area> 元素始终嵌套在 <map> 标签内部。
   *
   * <p>注释： <img> 标签中的 usemap 属性与 <map> 元素中的 name 相关联，以创建图像与映射之间的关系。
   */
  HTML_IMG_AREA("<area", ">", "/>"),

  /**
   * IE 10、Opera 和 Chrome 浏览器支持 <track> 标签。
   *
   * <p><track> 标签为媒体元素（比如 <audio> and <video>）规定外部文本轨道。
   *
   * <p>这个元素用于规定字幕文件或其他包含文本的文件，当媒体播放时，这些文件是可见的。
   *
   * <p><track> 标签是 HTML5 中的新标签。
   */
  HTML_VIDEO_TRACK("<track", ">", "/>"),

  /**
   * 所有主流浏览器都支持 <link> 标签。
   *
   * <p><link> 标签定义文档与外部资源的关系。
   *
   * <p><link> 标签最常见的用途是链接样式表。
   *
   * <p>注意： link 元素是空元素，它仅包含属性。
   *
   * <p>注意： 此元素只能存在于 head 部分，不过它可出现任何次数。
   *
   * <p>一些 HTML 4.01 属性在 HTML5 中不支持。
   *
   * <p>HTML5 新增了 "sizes" 属性。
   */
  HTML_LINK_LINK("<link", ">", "/>"),

  /**
   * <col> 标签规定了 <colgroup> 元素内部的每一列的列属性。
   *
   * <p>通过使用 <col> 标签，可以向整个列应用样式，而不需要重复为每个单元格或每一行设置样式。
   *
   * <p>HTML5 中不再支持 HTML 4.01 中的大部分属性。
   */
  HTML_TABLE_COL("<col", ">", "/>"),

  /**
   * 定义关于 HTML 文档的元信息。
   *
   * <p>元数据（Metadata）是数据的数据信息。
   *
   * <p><meta> 标签提供了 HTML 文档的元数据。元数据不会显示在客户端，但是会被浏览器解析。
   *
   * <p>META元素通常用于指定网页的描述，关键词，文件的最后修改时间，作者及其他元数据。
   *
   * <p>元数据可以被使用浏览器（如何显示内容或重新加载页面），搜索引擎（关键词），或其他 Web 服务调用。
   *
   * <p>注意：<meta> 标签通常位于 <head> 区域内。
   *
   * <p>注意： 元数据通常以 名称/值 对出现。
   *
   * <p>注意： 如果没有提供 name 属性，那么名称/值对中的名称会采用 http-equiv 属性的值。
   */
  HTML_META_META("<meta", ">", "/>"),

  /**
   * <base> 标签为页面上的所有的相对链接规定默认 URL 或默认目标。
   *
   * <p>在一个文档中，最多能使用一个 <base> 元素。<base> 标签必须位于 <head> 元素内部。
   *
   * <p>提示：请把 <base> 标签排在 <head> 元素中第一个元素的位置，这样 head 区域中其他元素就可以使用 <base> 元素中的信息了。
   *
   * <p>注释：如果使用了 <base> 标签，则必须具备 href 属性或者 target 属性或者两个属性都具备。
   */
  HTML_META_BASE("<base", ">", "/>"),

  /**
   * HTML5不再支持。 HTML 4.01 已废弃。 定义页面中文本的默认字体、颜色或尺寸。
   *
   * <p>只有 IE 9 和更早版本的 IE 浏览器支持 <basefont> 标签。应该避免使用该标签。
   *
   * <p>HTML5 不支持 <basefont> 标签。请用 CSS 代替。
   *
   * <p>在 HTML 4.01 中，<basefont> 元素 已废弃。
   *
   * <p><basefont> 标签定义文档中所有文本的默认颜色、大小和字体。
   *
   * <p>HTML5 不支持 <basefont> 标签，HTML 4.01 已废弃 <basefont> 标签
   */
  HTML_META_BASEFONT("<basefont", ">", "/>"),

  /**
   * 所有主流浏览器都支持 <embed> 标签。
   *
   * <p><embed> 标签定义了一个容器，用来嵌入外部应用或者互动程序（插件）。
   *
   * <p><embed> 标签是 HTML 5 中的新标签
   */
  HTML_SCRIPT_EMBED("<embed", ">", "/>"),

  /**
   * <param> 标签支持大部分主流浏览器。但是 <object> 定义的文件格式不是所有的浏览器都支持。
   *
   * <p><param>元素允许您为插入 XHTML 文档的对象规定 run-time 设置，也就是说，此标签可为包含它的 <object> 或者 <applet> 标签提供参数。
   *
   * <p>HTML 4.01 属性： "type" 和 "valuetype"，在 HTML5 中不支持。
   */
  HTML_SCRIPT_PARAM("<param", ">", "/>"),
  ;

  /** 标签 */
  private String tag;

  /** 结束标签形式1 */
  private String tagEnd1;

  /** 结束形式2 */
  private String tagEnd2;

  HtmlTagOneEnum(String tag, String tagEnd1) {
    this.tag = tag;
    this.tagEnd1 = tagEnd1;
  }

  HtmlTagOneEnum(String tag, String tagEnd1, String tagEnd2) {
    this.tag = tag;
    this.tagEnd1 = tagEnd1;
    this.tagEnd2 = tagEnd2;
  }

  public String getTag() {
    return tag;
  }

  public String getTagEnd1() {
    return tagEnd1;
  }

  public String getTagEnd2() {
    return tagEnd2;
  }
}
