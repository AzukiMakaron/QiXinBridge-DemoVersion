package doufen.work.oasys.file.client;

import doufen.work.oasys.file.entity.File;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 搜索服务接口
 *
 * @author doufen
 * @date 2023/12/20
 */
@FeignClient(name = "search-service")
public interface SearchClient {

    /**
     * 保存文件到 ES
     *
     * @param file 文件实体
     */
    @PostMapping("searches/files")
    void saveFile(@RequestBody File file);

    /**
     * 通过 ID 从 ES 删除文件
     *
     * @param id 文件ID
     */
    @DeleteMapping("searches/files/{id}")
    void deleteFileById(@PathVariable Long id);

}
