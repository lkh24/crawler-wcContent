package kh.science.entity;

import lombok.Data;

import java.util.List;

/**
 * @author ：Lkh
 * @date ：Created in 2023/8/22 10:07 PM
 */
@Data
public class UseSchoolItem {
    private int app_msg_cnt;
    private List<AppMsgItem> app_msg_list;
    private BaseResp base_resp;
}

@Data
class PayAlbumInfo {
    private List<Object> appmsg_album_infos;
}
@Data
class BaseResp {
    private String err_msg;
    private int ret;
}
