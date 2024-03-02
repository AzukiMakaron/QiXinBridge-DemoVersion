package doufen.work.oasys.search.controller;

import doufen.work.oasys.common.entity.Page;
import doufen.work.oasys.common.entity.Result;
import doufen.work.oasys.common.entity.ResultStatus;
import doufen.work.oasys.search.entity.File;
import doufen.work.oasys.search.service.FileSearchService;
import org.springframework.web.bind.annotation.*;

/**
 * 文件搜索控制器
 *
 * @author doufen
 * @since 2023/12/29
 */
@RestController
@RequestMapping("searches/files")
public class FileSearchController {

    private final FileSearchService fileSearchService;

    public FileSearchController(FileSearchService fileSearchService) {
        this.fileSearchService = fileSearchService;
    }

    @PostMapping
    public void saveFile(@RequestBody File file) {
        fileSearchService.saveFile(file);
    }

    @DeleteMapping("{id}")
    public void deleteFileById(@PathVariable Long id) {
        fileSearchService.deleteFileById(id);
    }

    @GetMapping
    public Result<Page<File>> queryFile(@RequestParam String name, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
        org.springframework.data.domain.Page<File> files = fileSearchService.findAllByName(name, pageNumber, pageSize);
        return Result.of(ResultStatus.SUCCESS, Page.of(files.getContent(), files.getTotalElements()));
    }

}
