package doufen.work.oasys.file.controller;

import doufen.work.oasys.common.entity.Page;
import doufen.work.oasys.common.entity.Result;
import doufen.work.oasys.common.entity.ResultStatus;
import doufen.work.oasys.file.entity.File;
import doufen.work.oasys.file.service.FileService;
import com.github.pagehelper.PageInfo;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 文件控制器
 *
 * @author doufen
 * @since 2023/12/23
 */
@RestController
@RequestMapping("files")
public class FileController {
    @Autowired
    private FileService fileService;

//    public FileController(FileService fileService) {
//        this.fileService = fileService;
//    }

    @GetMapping
    public Result<Page<File>> listFile(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, File file) {
        PageInfo<File> pageInfo = fileService.listByEntity(pageNumber, pageSize, file);
        return Result.of(ResultStatus.SUCCESS, Page.of(pageInfo.getList(), pageInfo.getTotal()));
    }

    /**
     *
     * @param multipartFile
     * @param file @@Validated({File.CreateFile.class, File.CreateFolder.class})设置验证组，验证规则为，不可创建重复的文件，可以创建重复的文件夹
     * @return
     * @throws IOException
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws NoSuchAlgorithmException
     * @throws InternalException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InvalidKeyException
     * @throws ErrorResponseException
     */
    @PostMapping
    public Result<File> createFile(@RequestParam(required = false) MultipartFile multipartFile, @Validated({File.CreateFile.class, File.CreateFolder.class}) File file)
            throws IOException, ServerException, InsufficientDataException, NoSuchAlgorithmException, InternalException,
            InvalidResponseException, XmlParserException, InvalidKeyException, ErrorResponseException {
        return Result.of(ResultStatus.SUCCESS, fileService.create(multipartFile, file));
    }

    @GetMapping("{id}")
    public void downloadFile(@PathVariable Long id, HttpServletResponse response) throws IOException, ServerException, InsufficientDataException,
            NoSuchAlgorithmException, InternalException, InvalidResponseException, XmlParserException, InvalidKeyException, ErrorResponseException {
        fileService.getById(id, response);
    }

    @PutMapping
    public Result<File> updateFile(@RequestBody @Validated({File.Update.class}) File file) {
        File updatedFile = fileService.update(file);
        return Result.of(ResultStatus.SUCCESS, updatedFile);
    }

    @DeleteMapping("{id}")
    public ResultStatus deleteFile(@PathVariable Long id) throws IOException, InvalidResponseException, InvalidKeyException,
            NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InsufficientDataException, InternalException {
        fileService.deleteById(id);
        return ResultStatus.SUCCESS;
    }

}