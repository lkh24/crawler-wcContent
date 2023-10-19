package kh.science.entity;

import lombok.Data;

import java.util.List;

@Data
public class AppMsgItem {
    private String aid;
    private String album_id;
    private List<Object> appmsg_album_infos;
    private long appmsgid;
    private int checking;
    private int copyright_type;
    private String cover;
    private long create_time;
    private String digest;
    private int has_red_packet_cover;
    private int is_pay_subscribe;
    private int item_show_type;
    private int itemidx;
    private String link;
    private String media_duration;
    private int mediaapi_publish_status;
    private PayAlbumInfo pay_album_info;
    private List<Object> tagid;
    private String title;
    private long update_time;
}
