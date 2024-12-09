package com.normalworks.common.utils;

import java.util.List;

public class MarkdownUtil {

    private final static String CRLF = "\r\n";

    public static void appendFirstTitle(StringBuilder sb, String text) {
        sb.append("# ").append(text).append(CRLF);
    }

    public static void appendSecondTitle(StringBuilder sb, String text) {
        sb.append("## ").append(text).append(CRLF);
    }

    public static void appendThirdTitle(StringBuilder sb, String text) {
        sb.append("### ").append(text).append(CRLF);
    }

    public static void appendList(StringBuilder sb, String text) {
        sb.append("* ").append(text).append(CRLF);
    }

    public static void appendTextWithEnter(StringBuilder sb, String text) {
        sb.append(text).append(CRLF);
    }

    public static void appendQuote(StringBuilder sb, String text) {
        sb.append("> ").append(text).append(CRLF);
    }

    public static void appendTableHeaders(StringBuilder sb, String... headers) {
        sb.append("|");
        for (String header : headers) {
            sb.append(header).append("|");
        }
        sb.append(CRLF);
        sb.append("|");
        for (String header : headers) {
            sb.append("---").append("|");
        }
        sb.append(CRLF);
    }

    public static void appendTableHeaders(StringBuilder sb, List<String> headers) {
        sb.append("|");
        for (String header : headers) {
            sb.append(header).append("|");
        }
        sb.append(CRLF);
        sb.append("|");
        for (String header : headers) {
            sb.append("---").append("|");
        }
        sb.append(CRLF);
    }

    public static void appendTableRow(StringBuilder sb, String... cells) {
        sb.append("|");
        for (String cell : cells) {
            sb.append(cell).append("|");
        }
        sb.append(CRLF);
    }

    public static void appendTableRow(StringBuilder sb, List<String> cells) {
        sb.append("|");
        for (String cell : cells) {
            sb.append(cell).append("|");
        }
        sb.append(CRLF);
    }
}
