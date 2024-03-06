package doufen.work.oasys.file;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.github.pagehelper.PageInfo;
import doufen.work.oasys.common.entity.Page;
import doufen.work.oasys.common.entity.Result;
import doufen.work.oasys.file.controller.FileController;
import doufen.work.oasys.file.entity.File;
import doufen.work.oasys.file.service.FileService;
import org.apache.tomcat.jni.Time;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 文件服务测试类
 *
 * @author doufen
 * @since 2023/12/26
 */
@SpringBootTest
public class FileApplicationTests {
    @Autowired
    private FileService fileService;
    @Autowired
    private FileController fileController;



    @Test
    void contextLoads() {
    }
    @Test
    void updateFilesDate(){
        int n=1;
        File file=new File();
        PageInfo<File> filePageInfo = fileService.listByEntity(1, 10, file);
        List<File> list = filePageInfo.getList();
        for (File files : list) {
            if(files.getType().equals("文件夹"))
            files.setName("测试文件夹"+n++);
            if(files.getCreator().equals("张三"))
                try {
                    fileService.update(files);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
        }
        System.out.println(list);
//        for (File updateFiles : list) {
//            updateFiles.setUpdateTime(LocalDateTime.now());
//            fileController.updateFile(updateFiles);
//        }
//        System.out.println(categoryName);
    }
    @Test
    void DeleteFileById(){
        File file=new File();
        PageInfo<File> filePageInfo = fileService.listByEntity(1, 10, file);
        List<File> list = filePageInfo.getList();
        for (File files : list) {
                files.setCreateTime(LocalDateTime.now());
            try {
                fileService.update(files);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
