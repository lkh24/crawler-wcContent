package kh.science.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author ：Lkh
 * @date ：Created in 2023/8/25 12:35 AM
 */
@Data
@Builder
public class Content {
    private String href;
    private String title;
    private String img;
    private String time;
    private Integer view;
    private String content;
    private String id;
}
