package com.normalworks.common.utils.currency;

import org.apache.commons.lang3.StringUtils;

/**
 * CurrencyEnum
 *
 * @author : shaoshuai
 * @version V1.0
 * @link:https://en.wikipedia.org/wiki/ISO_4217
 * @date Date : 2021年11月14日 11:08 上午
 */
public enum CurrencyEnum {

    CNY("CNY", "156", "人民币"),

    USD("USD", "840", "美元"),

    GBP("GBP", "826", "英镑"),

    AUD("AUD", "036", "澳元"),

    CAD("CAD", "124", "加元"),

    EUR("EUR", "978", "欧元"),

    HKD("HKD", "344", "港元"),

    NZD("NZD", "554", "新西兰元"),

    SGD("SGD", "702", "新加坡元"),

    JPY("JPY", "392", "日元"),

    BGN("BGN", "975", "保加利亚列弗"),

    CZK("CZK", "203", "捷克克朗"),

    DKK("DKK", "208", "丹麦克朗"),

    HUF("HUF", "348", "匈牙利福林"),

    PLN("PLN", "985", "波兰兹罗提"),

    RON("RON", "946", "罗马尼亚列伊"),

    SEK("SEK", "752", "瑞典克朗"),

    CHF("CHF", "756", "瑞士法郎"),

    ISK("ISK", "352", "冰岛克朗"),

    NOK("NOK", "578", "挪威克朗"),

    HRK("HRK", "191", "克罗地亚库纳"),

    RUB("RUB", "643", "俄罗斯卢布"),

    TRY("TRY", "949", "土耳其里拉"),

    BRL("BRL", "986", "巴西雷亚尔"),

    IDR("IDR", "360", "印度尼西亚盾"),

    ILS("ILS", "376", "以色列新谢克尔"),

    INR("INR", "356", "印度卢比"),

    KRW("KRW", "410", "韩元"),

    MXN("MXN", "484", "墨西哥比索"),

    MYR("MYR", "458", "马来西亚林吉特"),

    PHP("PHP", "608", "菲律宾比索"),

    THB("THB", "764", "泰铢"),

    ZAR("ZAR", "710", "南非兰特"),

    MAD("MAD", "504", "摩洛哥迪拉姆"),

    TWD("TWD", "901", "新台币"),

    AED("AED", "784", "阿联酋迪拉姆"),

    AFN("AFN", "971", "阿富汗尼"),

    ALL("ALL", "008", "阿尔巴尼亚列克"),

    AMD("AMD", "051", "亚美尼亚德拉姆"),

    ANG("ANG", "532", "荷属安的列斯盾"),

    AOA("AOA", "973", "安哥拉宽扎"),

    ARS("ARS", "032", "阿根廷比索"),

    AWG("AWG", "533", "阿鲁巴弗罗林"),

    AZN("AZN", "944", "阿塞拜疆马纳特"),

    BAM("BAM", "977", "波斯尼亚和黑塞哥维那可兑换马克"),

    BBD("BBD", "052", "巴巴多斯元"),

    BDT("BDT", "050", "孟加拉塔卡"),

    BHD("BHD", "048", "巴林第纳尔"),

    BIF("BIF", "108", "布隆迪法郎"),

    BMD("BMD", "060", "百慕大元"),

    BND("BND", "096", "文莱元"),

    BOB("BOB", "068", "玻利维亚诺"),

    BOV("BOV", "984", "玻利维亚诺"),

    BSD("BSD", "044", "巴哈马元"),

    BTN("BTN", "064", "不丹努尔特鲁姆"),

    BWP("BWP", "072", "博茨瓦纳普拉"),

    BYN("BYN", "933", "白俄罗斯卢布"),

    BZD("BZD", "084", "伯利兹元"),

    CDF("CDF", "976", "刚果法郎"),

    CHE("CHE", "947", "WIR euro (complementary currency)"),

    CHW("CHW", "948", "WIR franc (complementary currency)"),

    CLF("CLF", "990", "智利（资金）"),

    CLP("CLP", "152", "智利比索"),

    COP("COP", "170", "哥伦比亚比索"),

    COU("COU", "970", "哥伦比亚比索"),

    CRC("CRC", "188", "哥斯达黎加科朗"),

    CUP("CUP", "192", "古巴比索"),

    CVE("CVE", "132", "佛得角埃斯库多"),

    DJF("DJF", "262", "吉布提法郎"),

    DOP("DOP", "214", "多米尼加比索"),

    DZD("DZD", "012", "阿尔及利亚第纳尔"),

    EGP("EGP", "818", "埃及镑"),

    ERN("ERN", "232", "厄立特里亚纳克法"),

    ETB("ETB", "230", "埃塞俄比亚比尔"),

    FJD("FJD", "242", "斐济元"),

    FKP("FKP", "238", "福克兰群岛镑"),

    GEL("GEL", "981", "格鲁吉亚拉里"),

    GHS("GHS", "936", "加纳塞地"),

    GIP("GIP", "292", "直布罗陀镑"),

    GMD("GMD", "270", "冈比亚达拉西"),

    GNF("GNF", "324", "几内亚法郎"),

    GTQ("GTQ", "320", "危地马拉格查尔"),

    GYD("GYD", "328", "圭亚那元"),

    HNL("HNL", "340", "洪都拉斯伦皮拉"),

    HTG("HTG", "332", "海地古德"),

    IQD("IQD", "368", "伊拉克第纳尔"),

    IRR("IRR", "364", "伊朗里亚尔"),

    JMD("JMD", "388", "牙买加元"),

    JOD("JOD", "400", "约旦第纳尔"),

    KES("KES", "404", "肯尼亚先令"),

    KGS("KGS", "417", "吉尔吉斯斯坦索姆"),

    KHR("KHR", "116", "柬埔寨瑞尔"),

    KMF("KMF", "174", "科摩罗法郎"),

    KPW("KPW", "408", "朝鲜圆"),

    KWD("KWD", "414", "科威特第纳尔"),

    KYD("KYD", "136", "开曼群岛元"),

    KZT("KZT", "398", "哈萨克斯坦坚戈"),

    LAK("LAK", "418", "老挝基普"),

    LBP("LBP", "422", "黎巴嫩镑"),

    LKR("LKR", "144", "斯里兰卡卢比"),

    LRD("LRD", "430", "利比里亚元"),

    LSL("LSL", "426", "莱索托洛提"),

    LYD("LYD", "434", "利比亚第纳尔"),

    MDL("MDL", "498", "摩尔多瓦列伊"),

    MGA("MGA", "969", "马达加斯加阿里亚里"),

    MKD("MKD", "807", "马其顿第纳尔"),

    MMK("MMK", "104", "缅甸元"),

    MNT("MNT", "496", "蒙古图格里克"),

    MOP("MOP", "446", "澳门元"),

    MRU("MRU", "929", "毛里塔尼亚乌吉亚"),

    MUR("MUR", "480", "毛里求斯卢比"),

    MVR("MVR", "462", "马尔代夫卢菲亚"),

    MWK("MWK", "454", "马拉维克瓦查"),

    MXV("MXV", "979", "墨西哥（资金）"),

    MZN("MZN", "943", "莫桑比克梅蒂卡尔"),

    NAD("NAD", "516", "纳米比亚元"),

    NGN("NGN", "566", "尼日利亚奈拉"),

    NIO("NIO", "558", "尼加拉瓜科多巴"),

    NPR("NPR", "524", "尼泊尔卢比"),

    OMR("OMR", "512", "阿曼里亚尔"),

    PAB("PAB", "590", "巴拿马巴波亚"),

    PEN("PEN", "604", "秘鲁新索尔"),

    PGK("PGK", "598", "巴布亚新几内亚基那"),

    PKR("PKR", "586", "巴基斯坦卢比"),

    PYG("PYG", "600", "巴拉圭瓜拉尼"),

    QAR("QAR", "634", "卡塔尔里亚尔"),

    RSD("RSD", "941", "塞尔维亚第纳尔"),

    RWF("RWF", "646", "卢旺达法郎"),

    SAR("SAR", "682", "沙特里亚尔"),

    SBD("SBD", "090", "所罗门群岛元"),

    SCR("SCR", "690", "塞舌尔卢比"),

    SDG("SDG", "938", "苏丹镑"),

    SHP("SHP", "654", "圣赫勒拿镑"),

    SLE("SLE", "925", "Sierra Leonean leone"),

    SOS("SOS", "706", "索马里先令"),

    SRD("SRD", "968", "苏里南元"),

    SSP("SSP", "728", "南苏丹镑"),

    STN("STN", "930", "圣多美和普林西比多布拉"),

    SVC("SVC", "222", "萨尔瓦多科朗"),

    SYP("SYP", "760", "叙利亚镑"),

    SZL("SZL", "748", "斯威士兰里兰吉尼"),

    TJS("TJS", "972", "塔吉克斯坦索莫尼"),

    TMT("TMT", "934", "土库曼斯坦马纳特"),

    TND("TND", "788", "突尼斯第纳尔"),

    TOP("TOP", "776", "汤加潘加"),

    TTD("TTD", "780", "特立尼达和多巴哥元"),

    TZS("TZS", "834", "坦桑尼亚先令"),

    UAH("UAH", "980", "乌克兰格里夫纳"),

    UGX("UGX", "800", "乌干达先令"),

    UYU("UYU", "858", "乌拉圭比索"),

    UZS("UZS", "860", "乌兹别克斯坦苏姆"),

    VES("VES", "928", "委内瑞拉玻利瓦尔"),

    VND("VND", "704", "越南盾"),

    VUV("VUV", "548", "瓦努阿图瓦图"),

    WST("WST", "882", "萨摩亚塔拉"),

    XAF("XAF", "950", "中非金融合作法郎"),

    XCD("XCD", "951", "东加勒比元"),

    XOF("XOF", "952", "西非金融合作法郎"),

    XPF("XPF", "953", "太平洋法郎"),

    YER("YER", "886", "也门里亚尔"),

    ZMW("ZMW", "967", "赞比亚克瓦查"),

    ZWL("ZWL", "932", "津巴布韦元");

    public static CurrencyEnum getByCurrencyCode(String currencyCode) {

        for (CurrencyEnum enumValue : CurrencyEnum.values()) {
            if (StringUtils.equals(enumValue.getCurrencyCode(), currencyCode)) {
                return enumValue;
            }
        }
        return null;
    }

    public static CurrencyEnum getByCurrencyValue(String currencyValue) {

        for (CurrencyEnum currencyEnum : CurrencyEnum.values()) {
            if (StringUtils.equals(currencyEnum.getCurrencyValue(), currencyValue)) {
                return currencyEnum;
            }
        }
        return null;
    }

    CurrencyEnum(String currencyCode, String currencyValue, String memo) {
        this.currencyCode = currencyCode;
        this.currencyValue = currencyValue;
        this.memo = memo;
    }

    private String currencyCode;

    private String currencyValue;

    private String memo;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(String currencyValue) {
        this.currencyValue = currencyValue;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
