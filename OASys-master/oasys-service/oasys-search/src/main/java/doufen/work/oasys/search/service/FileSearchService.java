package doufen.work.oasys.search.service;

import doufen.work.oasys.search.entity.File;
import org.springframework.data.domain.Page;

/**
 * 文件服务搜索服务接口
 *
 * @author doufen
 * @since 2023/12/29
 */
public interface FileSearchService {

    /**
     * 保存文件到 ES
     *
     * @param file 文件实体
     */
    void saveFile(File file);

    /**
     * 通过 ID 从 ES 删除文件
     *
     * @param id 文件ID
     */
    void deleteFileById(Long id);

    /**
     * 通过文件名搜索文件列表
     *
     * @param name       文件名
     * @param pageNumber 页数
     * @param pageSize   页大小
     * @return 文件列表
     */
    Page<File> findAllByName(String name, int pageNumber, int pageSize);

}
