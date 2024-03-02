package doufen.work.oasys.user.dao;

import doufen.work.oasys.user.entity.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 部门数据库访问层
 *
 * @author doufen
 * @since 2024/1/3
 */
@Mapper
public interface DeptDao {

    /**
     * 通过ID查询单个部门
     *
     * @param id 部门ID
     * @return 单个部门
     */
    Dept selectById(Integer id);

    /**
     * 查询所有部门
     *
     * @return 部门列表
     */
    List<Dept> list();

    /**
     * 通过部门实例查询所有部门
     *
     * @param dept 部门
     * @return 部门列表
     */
    List<Dept> listByEntity(Dept dept);

    /**
     * 新增部门
     *
     * @param dept 部门实例
     * @return 部门实例
     */
    int insert(Dept dept);

    /**
     * 更新部门
     *
     * @param dept 部门实例
     * @return 影响行数
     */
    int update(Dept dept);

    /**
     * 删除部门
     *
     * @param id 部门ID
     * @return 影响行数
     */
    int delete(Integer id);

}