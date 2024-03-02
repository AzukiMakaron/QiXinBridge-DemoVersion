package doufen.work.oasys.search.service.impl;

import doufen.work.oasys.search.entity.File;
import doufen.work.oasys.search.repository.FileSearchRepository;
import doufen.work.oasys.search.service.FileSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 文件搜索服务实现类
 *
 * @author doufen
 * @since 2023/12/30
 */
@Service
public class FileSearchServiceImpl implements FileSearchService {

    private final FileSearchRepository fileSearchRepository;

    public FileSearchServiceImpl(FileSearchRepository fileSearchRepository) {
        this.fileSearchRepository = fileSearchRepository;
    }

    @Override
    public void saveFile(File file) {
        fileSearchRepository.save(file);
    }

    @Override
    public void deleteFileById(Long id) {
        fileSearchRepository.deleteById(id);
    }

    @Override
    public Page<File> findAllByName(String name, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return fileSearchRepository.findAllByName(name, pageable);
    }

}
