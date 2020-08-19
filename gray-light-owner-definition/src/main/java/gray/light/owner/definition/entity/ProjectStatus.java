package gray.light.owner.definition.entity;


/**
 * 表示Git项目的状态
 *
 * @author XyParaCrim
 */
public enum ProjectStatus {

    /**
     * 等待持久化，即数据只有项目信息，没有结构信息及其文件数据
     */
    WAIT_PERSISTENCE,

    /**
     * 无法获取更新信息，但是是从WAIT_PERSISTENCE的状态来的，说明之前的版本依然不可用
     */
    RETRY_PERSISTENCE,

    /**
     * 仓库已经上传到文件服务器
     */
    SYNC,

    /**
     * 有新的更新需要同步，但是此时的版本也是可用的
     */
    PENDING,

    /**
     * 无法获取更新信息，可能因为Git服务没有了，又或者本地文件系统无法缓存，但是之前的版本
     * 完全可用
     */
    FAILURE_CHECK,


    /**
     * 仓库无效状态，可能在上传是发生了错误
     */
    INVALID,
}
