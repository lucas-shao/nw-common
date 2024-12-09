package com.normalworks.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EmailUtil
 * 邮箱工具类
 *
 * @author : shaoshuai.shao
 * @version : V1.0
 * @date : 2022/11/22 8:54 PM
 */
public class EmailUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);

    private final static String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@" +
            "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";

    /**
     * 邮件模版引擎，初始化一次
     */
    private static VelocityEngine velocityEngine;

    /**
     * 初始化邮件模版引擎
     */
    static {

        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute");
        velocityEngine.setProperty("runtime.log.logsystem.log4j.logger", LOGGER.getName());
        velocityEngine.setProperty(Velocity.RESOURCE_LOADER, "string");
        velocityEngine.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
        velocityEngine.addProperty("string.resource.loader.repository.static", "false");
        velocityEngine.addProperty("input.encoding", "UTF-8");
        velocityEngine.addProperty("output.encoding", "UTF-8");

        velocityEngine.init();
    }

    /**
     * 获取邮箱的用户名，即@之前的字符串
     * <p>
     * lucas_shao@126.com 即为 lucas_shao
     *
     * @param email 邮箱地址
     * @return 邮箱用户名
     */
    public static String fetchUserName(String email) {
        return StringUtils.substringBefore(email, "@");
    }

    /**
     * 获取邮箱的域名，即@之后的字符串
     * lucas_shao@126.com 即为 126.com
     *
     * @param email 邮箱地址
     * @return 邮箱域名
     */
    public static String fetchDomain(String email) {
        return StringUtils.substringAfter(email, "@");
    }

    /**
     * 校验输入的email格式是否合法
     *
     * @param email
     * @return
     */
    public static boolean verify(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 渲染邮件内容
     *
     * @param templateId 邮件模版ID
     * @param template   邮件模版
     * @param params     邮件参数
     * @return 渲染后的内容
     */
    public static String renderContent(String templateId, String template, Map<String, String> params) {
        StringResourceRepository repo = (StringResourceRepository) velocityEngine.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);
        repo.putStringResource(templateId, template);
        VelocityContext vc = new VelocityContext();
        if (!CollectionUtils.isEmpty(params)) {
            for (String key : params.keySet()) {
                // 为了避免渲染空的情况，默认值为空字符串
                vc.put(key, (params.get(key) == null ? "" : params.get(key)));
            }
        }
        StringWriter writer = new StringWriter();
        velocityEngine.getTemplate(templateId).merge(vc, writer);
        return writer.toString();
    }


    public static void main(String[] args) {

        System.out.println(verify("lucas_shao@126.com"));
        // Initialize my template repository. You can replace the "Hello $w" with your String.
        StringResourceRepository repo = (StringResourceRepository) velocityEngine.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);
        repo.putStringResource("emailTemplate", "Hello $OTP");

        VelocityContext vc = new VelocityContext();
        vc.put("OTP", "world");
        StringWriter writer = new StringWriter();
        velocityEngine.getTemplate("emailTemplate").merge(vc, writer);

        System.out.println(writer.toString());

        String t1 = "Upload Your Documents \uD83D\uDCE4 $OTP";
        String t2 = "me";
        String t3 = "brovo $BA";
        Map<String, String> params = new HashMap<>();
        params.put("OTP", "0000");
        params.put("BA", "ALI");
        System.out.println(renderContent("1", t1, params));
        System.out.println(renderContent("2", t2, params));
        System.out.println(renderContent("3", t3, params));

    }
}
