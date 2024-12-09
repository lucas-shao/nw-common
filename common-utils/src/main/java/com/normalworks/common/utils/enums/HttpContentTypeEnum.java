package com.normalworks.common.utils.enums;

import com.normalworks.common.utils.AssertUtil;
import com.normalworks.common.utils.assertion.CommonResultCode;
import org.apache.commons.lang3.StringUtils;

/**
 * HttpContentTypeEnum
 * HTTP内容类型枚举
 * <p>
 * ref: https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
 * ref: https://www.iana.org/assignments/media-types/media-types.xhtml
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年11月14日 11:55 下午
 */
public enum HttpContentTypeEnum {

    // ~~ 文字

    HTML("text", "html", ".html"),

    TXT("text", "plain", ".txt"),

    XML("text", "xml", ".xml"),

    CSS("text", "css", ".css"),

    ASP("text", "asp", ".asp"),

    CSV("text", "csv", ".csv"),

    // ~~ 图片

    FAX("image", "fax", ".fax"),

    GIF("image", "gif", ".gif"),

    X_ICON("image", "x-icon", ".ico"),

    JPEG("image", "jpeg", ".jpeg"),

    JPG("image", "jpg", ".jpg"),

    PNETVUE("image", "pnetvue", ".net"),

    PNG("image", "png", ".png"),

    TIFF("image", "tiff", ".tiff"),

    TIF("image", "tiff", ".tif"),

    BMP("image", "bmp", ".bmp"),

    X_MS_BMP("image", "x-ms-bmp", ".bmp"),

    WEBP("image", "webp", ".webp"),

    HEIC("image", "heic", ".heic"),

    HEIF("image", "heif", ".heif"),

    // ~~ 音频

    X_MEI_AAC("audio", "x-mei-aac", ".acp"),

    AIFF("audio", "aiff", ".aiff"),

    BASIC("audio", "basic", ".au"),

    X_LIQUID_FILE("audio", "x-liquid-file", ".la1"),

    X_LIQUID_SECURE("audio", "x-liquid-secure", ".lavs"),

    X_LA_LMS("audio", "x-la-lms", ".lmsff"),

    MPEGURL("audio", "mpegurl", ".m3u"),

    MID("audio", "mid", ".mid"),

    MP1("audio", "mp1", ".mp1"),

    MP2("audio", "mp2", ".mp2"),

    MP3("audio", "mp3", ".mp3"),

    AUDIO_MPEG("audio", "mpeg", ".mp3"),

    RN_MPEG("audio", "rn-mpeg", ".mpga"),

    SCPLS("audio", "scpls", ".pls"),

    AUDIO_BASIC("audio", "basic", ".snd"),

    WAV("audio", "wav", ".wav"),

    X_MS_WAX("audio", "x-ms-wax", ".wax"),

    X_MS_WMA("audio", "x-ms-wma", ".wma"),

    X_M4A("audio", "x-m4a", ".m4a"),

    // ~~ 视频

    X_MS_ASF("video", "x-ms-asf", ".asf"),

    AVI("video", "avi", ".avi"),

    IVF("video", "x-ivf", ".IVF"),

    X_MPEG("video", "x-mpeg", ".m1v"),

    X_SGI_MOVIE("video", "x-sgi-movie", ".movie"),

    VIDEO_MPEG("video", "mpeg", ".mpv2"),

    MPEG4("video", "mpeg4", ".mp4"),

    MP4("video", "mp4", ".mp4"),

    X_MPG("video", "x-mpg", ".mpa"),

    X_MS_WM("video", "x-ms-wm", ".wm"),

    X_MS_WMX("video", "x-ms-wmx", ".wmx"),

    X_MS_WMV("video", "x-ms-wmv", ".wmv"),

    X_MS_WVX("video", "x-ms-wvx", ".wvx"),

    MOV("video", "quicktime", ".mov"),

    // message

    EML("message", "rfc822", ".eml"),

    // ~~ 其他常用

    PDF("application", "pdf", ".pdf"),

    X_BMP("application", "x-bmp", ".bmp"),

    MSWORD("application", "msword", ".doc"),

    VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_DOCUMENT("application", "vnd.openxmlformats-officedocument.wordprocessingml.document", ".docx"),

    X_IMG("application", "x-img", ".img"),

    X_JPG("application", "x-jpg", ".jpg"),

    X_PNG("application", "x-png", ".png"),

    X_PPT("application", "x-ppt", ".ppt"),

    VND_MS_POWERPOINT("application", "vnd.ms-powerpoint", ".ppt"),

    VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_PRESENTATION("application", "vnd.openxmlformats-officedocument.presentationml.presentation", ".pptx"),

    X_XLS("application", "x-xls", ".xls"),

    VND_MS_EXCEL("application", "vnd.ms-excel", ".xls"),

    VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_SHEET("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet", ".xlsx"),

    XLSX("application", "xlsx", ".xlsx"),

    VND_RN_REALMEDIA_VBR("application", "vnd.rn-realmedia-vbr", ".rmvb"),

    X_PIC("application", "x-pic", ".pic"),

    X_ICO("application", "x-ico", ".ico"),

    ZIP("application", "zip", ".zip"),

    X_ZIP_COMPRESSED("application", "x-zip-compressed", ".zip"),

    X_BZIP("application", "x-bzip", ".bz"),

    X_BZIP2("application", "x-bzip2", ".bz2"),

    GZIP("application", "gzip", ".gz"),

    X_TAR("application", "x-tar", ".tar"),

    X_7Z_COMPRESSED("application", "x-7z-compressed", ".7z"),

    VND_RAR("application", "vnd.rar", ".rar"),

    VND_APPLE_NUMBERS("application", "vnd.apple.numbers", ".numbers"),

    X_IWORK_NUMBERS_SFFNUMBERS("application", "x-iwork-numbers-sffnumbers", ".numbers"),

    // 在HTTP协议中，application/octet-stream 是一个MIME类型，表示数据是原始的字节流，没有特定的内容类型。这通常用于传输二进制文件，如可执行文件、图片、视频等
    OCTET_STREAM("application", "octet-stream", ".bin"),

    /**
     * 存储日历事件、约会、提醒等信息
     */
    ICS("application", "ics", ".ics"),

    PKPASS("application", "vnd.apple.pkpass", ".pkpass"),

    ;

    /**
     * 通过文件后缀名匹配，获取第一个优先匹配到的HttpContentTypeEnum
     * 如：.pdf .png
     */
    public static HttpContentTypeEnum fetchPriorityMatchedHttpContentTypeByExtensionName(String extensionName) {
        for (HttpContentTypeEnum enumValue : HttpContentTypeEnum.values()) {
            if (StringUtils.equalsIgnoreCase(extensionName, enumValue.getExtensionName())) {
                return enumValue;
            }
        }
        return null;
    }

    public static HttpContentTypeEnum getByContentTypeAndMimeType(String contentType, String mimeType) {
        for (HttpContentTypeEnum enumValue : HttpContentTypeEnum.values()) {
            if (StringUtils.equals(contentType, enumValue.getContentType()) && StringUtils.equals(mimeType, enumValue.getMimeType())) {
                return enumValue;
            }
        }
        return null;
    }

    public static HttpContentTypeEnum getByRawContentType(String rawContentType) {

        if (StringUtils.isBlank(rawContentType)) {
            return null;
        }

        String[] contentTypeAndMimeType = StringUtils.split(StringUtils.lowerCase(rawContentType), "/");
        AssertUtil.isTrue(contentTypeAndMimeType.length == 2, CommonResultCode.PARAM_ILLEGAL, "rawContentType的格式不合法，rawContentType = " + rawContentType);

        String contentType = StringUtils.trim(contentTypeAndMimeType[0]);
        String mimeType = StringUtils.trim(contentTypeAndMimeType[1]);

        return getByContentTypeAndMimeType(contentType, mimeType);
    }

    public static String getRawContentType(HttpContentTypeEnum contentType) {
        return contentType.getContentType() + "/" + contentType.getMimeType();
    }

    HttpContentTypeEnum(String contentType, String mimeType, String extensionName) {
        this.contentType = contentType;
        this.mimeType = mimeType;
        this.extensionName = extensionName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public String getExtensionNameWithoutDot() {
        return StringUtils.substringAfter(extensionName, ".");
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    private String contentType;

    private String mimeType;

    private String extensionName;
}
