package com.normalworks.common.utils;

import com.normalworks.common.utils.assertion.AssertionException;
import com.normalworks.common.utils.assertion.CommonResultCode;
import com.normalworks.common.utils.enums.HttpContentTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * FileUtil
 * 文件工具类
 *
 * @author : shaoshuai
 * @version V1.0
 * @date Date : 2021年11月21日 3:28 下午
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static final String TEMP_FILE_DIR = "/tmp/files";

    /**
     * 从文件名中获取文件后缀名
     *
     * @param fileName
     * @return
     */
    public static String fetchFileExtension(String fileName) {

        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        String extension = StringUtils.substringAfterLast(fileName, ".");

        return StringUtils.trim(extension);
    }

    /**
     * 将网络上的媒体资源通过URL链接下载到指定的本地文件路径中
     */
    public static File download2Path(String mediumUrl, String newPath, String newFileName) throws Exception {

        File downloadTmpFile = download(mediumUrl);
        Path sourcePath = Paths.get(downloadTmpFile.getAbsolutePath());
        Path targetPath = Paths.get(newPath, newFileName);
        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        return downloadTmpFile;
    }

    /**
     * 将网络上的媒体资源通过URL链接下载到本地文件
     *
     * @param mediumUrl 网上的媒体URL地址
     * @return 存放在本地的临时文件
     * @throws Exception
     */
    public static File download(String mediumUrl) throws Exception {

        // 获取链接
        HttpURLConnection httpURLConnection = UrlUtil.obtainHttpURLConnection(mediumUrl);

        // 获取输入流
        InputStream inputStream = httpURLConnection.getInputStream();

        // 优先从文件内容中获取文件类型
        String contentType = httpURLConnection.getContentType();
        HttpContentTypeEnum httpContentType = HttpContentTypeEnum.getByRawContentType(contentType);

        // 如果获取不到，再从URL的文件后缀名取
        String fileSuffix;
        if (httpContentType != null) {
            fileSuffix = httpContentType.getExtensionNameWithoutDot();
        } else {
            String fileName = StringUtils.substringAfterLast(mediumUrl, "/");
            fileSuffix = StringUtils.substringAfterLast(fileName, ".");
        }

        // 创建临时文件
        File tmpFile = FileUtil.generateTmpFile(fileSuffix);

        // 创建输入流
        FileOutputStream outStream = new FileOutputStream(tmpFile);

        try {
            // 读取所有二进制数据
            readInputStreamToOutputStream(inputStream, outStream);

        } finally {
            // 关闭输入流
            if (inputStream != null) {
                inputStream.close();
            }
            // 关闭输出流
            if (outStream != null) {
                outStream.close();
            }
            // 关闭url http管道连接
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return tmpFile;
    }

    /**
     * 判断文件是否存在
     */
    public static boolean fileExists(String filePath, String fileName) {
        Path path = Paths.get(filePath, fileName);
        return Files.exists(path);
    }

    /**
     * 删除本地文件
     *
     * @param file 文件
     * @return 删除结果
     */
    public static boolean delete(File file) {
        if (null == file) {
            LOGGER.info("file为空，直接返回删除成功");
            return true;
        }
        return delete(file.getAbsolutePath());
    }

    /**
     * 批量删除本地文件集合
     *
     * @param fileList 本地文件集合
     * @return 批量删除结果
     */
    public static void deleteFiles(List<File> fileList) {
        if (CollectionUtils.isEmpty(fileList)) {
            LOGGER.info("fileList为空，直接返回删除成功");
            return;
        }
        for (File file : fileList) {
            delete(file);
        }
    }

    /**
     * 删除本地文件
     *
     * @param localFilePath
     * @return 删除结果
     */
    public static boolean delete(String localFilePath) {

        if (StringUtils.isBlank(localFilePath)) {
            LOGGER.info("localFilePath为空，直接返回删除成功");
            return true;
        }

        File file = new File(localFilePath);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                LOGGER.info("File was deleted successfully. localFilePath = " + localFilePath);
            } else {
                LOGGER.info("Failed to delete the file. localFilePath = " + localFilePath);
            }
            return deleted;
        } else {
            LOGGER.info("文件不存在，直接返回删除成功 localFilePath = " + localFilePath);
            return true;
        }
    }

    /**
     * 生成固定文件后缀的临时文件
     *
     * @param fileTypeSuffix 文件后缀 pdf/jpg/jpeg
     * @return 临时文件
     * @throws IOException
     */
    public static File generateTmpFile(String fileTypeSuffix) {

        try {
            if (StringUtils.isBlank(fileTypeSuffix)) {
                return generateTmpFile();
            }

            String fileName = UUID.randomUUID().toString() + "." + fileTypeSuffix;
            String fullName = TEMP_FILE_DIR + "/" + fileName;
            Path path = Paths.get(TEMP_FILE_DIR);
            if (!Files.exists(path)) {
                Path pathCreate = Files.createDirectories(path);
            }
            File tmpFile = new File(fullName);
            return tmpFile;
        } catch (Throwable e) {
            LOGGER.error("生成临时文件失败", e);
            throw new AssertionException(CommonResultCode.SYSTEM_EXCEPTION, "生成临时文件失败");
        }
    }

    /**
     * 生成临时文件
     *
     * @return 临时文件
     */
    public static File generateTmpFile() {

        try {
            String fileName = UUID.randomUUID().toString();
            String fullName = TEMP_FILE_DIR + "/" + fileName;
            Path path = Paths.get(TEMP_FILE_DIR);
            if (!Files.exists(path)) {
                Path pathCreate = Files.createDirectories(path);
            }
            File tmpFile = new File(fullName);
            return tmpFile;
        } catch (Throwable e) {
            LOGGER.error("生成临时文件失败", e);
            throw new AssertionException(CommonResultCode.SYSTEM_EXCEPTION, "生成临时文件失败");
        }
    }

    /**
     * 创建文件目录
     *
     * @param fileDirPath
     * @return
     * @throws IOException
     */
    public static Path createFileDir(String fileDirPath) {

        Path path = Paths.get(fileDirPath);
        if (!Files.exists(path)) {

            try {
                Path pathCreate = Files.createDirectories(path);
                return pathCreate;
            } catch (Exception e) {
                LOGGER.error("创建文件目录失败 fileDirPath = " + fileDirPath, e);
                throw new AssertionException(CommonResultCode.SYSTEM_EXCEPTION, "创建文件目录失败");
            }
        }
        return path;
    }

    /**
     * 通过文件路径获取文件名
     *
     * @param filePath
     * @return
     */
    public static String fetchFileName(String filePath) {
        if (StringUtils.contains(filePath, "/")) {
            return StringUtils.substringAfterLast(filePath, "/");
        } else {
            return filePath;
        }
    }

    /**
     * 压缩目录
     *
     * @param sourceDirPath 源目录路径  /tmp/dir
     * @param zipFilePath   压缩文件路径  /tmp/dir.zip
     */
    public static void zipDir(String sourceDirPath, String zipFilePath) {
        Path sourceDir = Paths.get(sourceDirPath);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            Files.walk(sourceDir).filter(path -> !Files.isDirectory(path)).forEach(path -> {
                ZipEntry zipEntry = new ZipEntry(sourceDir.relativize(path).toString());
                try {
                    zipOutputStream.putNextEntry(zipEntry);
                    Files.copy(path, zipOutputStream);
                    zipOutputStream.closeEntry();
                } catch (IOException e) {
                    System.err.println(e);
                    throw new AssertionException(CommonResultCode.ZIP_DIR_FAILED, "压缩文件失败");
                }
            });
        } catch (IOException e) {
            System.err.println("Failed to create zip file: " + e);
            throw new AssertionException(CommonResultCode.ZIP_DIR_FAILED, "压缩文件失败");
        }
    }

    /**
     * 过滤文件名后缀中的特殊字符
     */
    public static String filterFileExtensionSpecialCharOrFetchFromUrl(String fileName, String fileUrl) {
        // 获取fileName的后缀名,以及文件名
        String fileExtension = StringUtils.substringAfterLast(fileName, ".");
        String fileNameWithoutExtension = StringUtils.substringBeforeLast(fileName, ".");
        // 过滤fileName的后缀名中的特殊字符
        String filterFileExtension = fileExtension.replaceAll("[^a-zA-Z0-9]", "");

        if (StringUtils.isNotBlank(fileNameWithoutExtension)
                && StringUtils.isNotBlank(filterFileExtension)) {
            return fileNameWithoutExtension + "." + filterFileExtension;
        } else {
            // 截取fileUrl中的文件名
            return fetchFileName(fileUrl);
        }
    }

    /**
     * 给文件追加内容
     */
    public static void append(File file, String content) {
        FileWriter fileWriter = null;
        try {
            // 创建FileWriter对象，以追加模式打开文件
            fileWriter = new FileWriter(file, true);
            // 将String的内容写入文件
            fileWriter.append(content);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AssertionException(CommonResultCode.APPEND_FILE_FAILED, "追加文件失败");
        } finally {
            if (fileWriter != null) {
                try {
                    // 关闭FileWriter对象
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new AssertionException(CommonResultCode.APPEND_FILE_FAILED, "追加文件失败");
                }
            }
        }
    }

    /**
     * 将输入流数据写到输出流中
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     */
    private static void readInputStreamToOutputStream(InputStream inputStream, FileOutputStream outputStream) throws
            IOException {

        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inputStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outputStream.write(buffer, 0, len);
        }
    }

    private static byte[] readInputStream(InputStream inputStream) throws Exception {

        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = inputStream.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            //把outStream里的数据写入内存
            return outStream.toByteArray();
        } finally {
            //关闭输入流
            inputStream.close();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(filterFileExtensionSpecialCharOrFetchFromUrl(".\"", "https://www.baidu.com/3899b1e4-5c9a-41a1-8117-b9e91a836905.05.45 (34).jpeg"));
        System.out.println(fetchFileExtension("https://www.baidu.com/234.png"));
    }
}
