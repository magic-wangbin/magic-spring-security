package com.magic.security.demo.web;

import com.magic.security.demo.dto.request.FileInfo;
import com.magic.security.demo.exception.FileUploadException;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * 上传.
 */
@RestController
@RequestMapping(value = "file")
public class FileUploadController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @PostMapping
    public FileInfo uploadFile(MultipartFile multipartFile) {
        logger.info(ReflectionToStringBuilder.toString(multipartFile, ToStringStyle.MULTI_LINE_STYLE));

        //
        String fileDestination = "C:\\Users\\Administrator\\Desktop\\upload\\";
        String fileName = UUID.randomUUID().toString();
        File file = new File(fileDestination.concat(fileName).concat(".txt"));

        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            logger.info("上传文件失败：" + e.getMessage());
            throw new FileUploadException("上传文件失败");
        }

        return new FileInfo(file.getAbsolutePath());
    }

    /**
     * 文件的下载.
     *
     * @param fileId
     */
    @GetMapping("/{fileId}")
    public void downFile(@PathVariable String fileId, HttpServletRequest request, HttpServletResponse response) {

        //
        String folder = "C:\\Users\\Administrator\\Desktop\\upload\\";

        try (
                InputStream inputStream = new FileInputStream(new File(folder, fileId + ".txt"));
                OutputStream outputStream = response.getOutputStream();
        ) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=james.txt");

            IOUtils.copy(inputStream, outputStream);

            outputStream.flush();

        } catch (FileNotFoundException e) {
            throw new FileUploadException("文件没有查询到");
        } catch (IOException e) {
            logger.info("文件下载失败：", e);
            throw new FileUploadException("文件下载失败");
        }

    }

}
