package site.heaven96.example.entity.union;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 岗位
 *
 * @author Heaven96
 * @date 2021/10/13
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
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
