package doufen.work.oasys.search.repository;

import doufen.work.oasys.search.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 文件搜索数据库访问层
 *
 * @author doufen
 * @since 2023/12/29
 */
@Repository
public interface FileSearchRepository extends ElasticsearchRepository<File, Long> {

    /**
     * 通过文件名搜索文件列表
     *
     * @param name     文件名
     * @param pageable 分页条件
     * @return 文件列表
     */
    Page<File> findAllByName(String name, Pageable pageable);

}
