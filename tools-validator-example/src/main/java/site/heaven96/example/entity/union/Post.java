package site.heaven96.example.entity.union;

import lombok.Data;

/**
 * 岗位
 *
 * @author lgw3488
 * @date 2021/10/13
 */
@Data
public class Post {
    /**
     * ID号
     */
    private int id;
    /**
     * 名字
     */
    private String name;
    /**
     * 简介
     */
    private String intro;
}
