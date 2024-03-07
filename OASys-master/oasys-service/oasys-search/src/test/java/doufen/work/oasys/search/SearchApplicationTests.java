package doufen.work.oasys.search;

import doufen.work.oasys.search.entity.File;
import doufen.work.oasys.search.service.FileSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 搜索服务测试类
 *
 * @author doufen
 * @since 2023/12/29
 */
@SpringBootTest
public class SearchApplicationTests {
    @Autowired
    FileSearchService fileSearchService;
    @Test
    void contextLoads() {
    }
    @Test
    void searchFile(){
        Page<File> filesPage = fileSearchService.findAllByName("测试文件夹1", 1, 10);
        List<File> content =filesPage.getContent();
        for (File files : content) {
            System.out.println(files);
        }
    }
}
