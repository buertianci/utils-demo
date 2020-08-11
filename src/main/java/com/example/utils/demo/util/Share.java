package com.example.utils.demo.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class Share {

    private static final int TYPE_LINK = 0;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_TEXT = 2;
    private static final int TYPE_IMAGE_TEXT = 3;

    public abstract class ShareItem {
        int type;

        public ShareItem(int type) {
            this.type = type;
        }

        public abstract void doShare(ShareListener listener);
    }

    //链接
    public class Link extends ShareItem {

        String title;
        String content;
        String link;

        public Link(String link, String title, String content) {
            super(TYPE_LINK);
            this.link = link == null || link.trim().isEmpty() ? "default" : link;
            this.title = title == null || title.trim().isEmpty() ? "default" : title;
            this.content = content == null || content.trim().isEmpty() ? "default" : content;
        }

        @Override
        public void doShare(ShareListener listener) {
            log.info("this is share link!");
        }
    }

    //图片
    public class Image extends ShareItem {

        String imagePath;

        public Image(String imagePath) {
            super(TYPE_IMAGE);
            this.imagePath = imagePath == null || imagePath.trim().isEmpty() ? "default" : imagePath;
        }

        @Override
        public void doShare(ShareListener listener) {
            log.info("this is share image!");
        }
    }

    //文档
    public class Text extends ShareItem {

        String content;

        public Text(String content) {
            super(TYPE_TEXT);
            this.content = content == null || content.trim().isEmpty() ? "default" : content;
        }

        @Override
        public void doShare(ShareListener listener) {
            log.info("this is share text!");
        }
    }

    //图片文档
    public class ImageText extends ShareItem {

        String content;
        String imagePath;

        public ImageText(String content, String imagePath) {
            super(TYPE_IMAGE_TEXT);
            this.content = content == null || content.trim().isEmpty() ? "default" : content;
            this.imagePath = imagePath == null || imagePath.trim().isEmpty() ? "default" : imagePath;
        }

        @Override
        public void doShare(ShareListener listener) {
            log.info("this is share imageText!");
        }
    }

    public interface ShareListener {
        int STATE_SUCC = 0;
        int STATE_FAIL = 0;

        void onCallback(int state, String msg);
    }

    public void share(ShareItem item, ShareListener listener) {
        if (item != null) {
            if (listener != null) {
                listener.onCallback(ShareListener.STATE_FAIL, "ShareItem is null");
            }
            return;
        }

        if (listener == null) {
            listener = new ShareListener() {
                @Override
                public void onCallback(int state, String msg) {
                    log.error("ShareListener is null");
                }
            };
        }

        shareImpl(item, listener);
    }

    private void shareImpl(ShareItem item, ShareListener listener) {
        item.doShare(listener);
    }

    @Data
    public static class Tttt{
        private Long shopId;
        private String shopName;
        private Date createTime;
        private List<Tooo> list;
    }

    @Data
    public static class Tooo{
        private Long shopId;
        private String shopName;
    }

    public static void main(String[] args) {
        Tttt t = new Tttt();
        List<Tooo> list = new ArrayList();
        //list.add(1);
        t.setList(list);
        if (CommonUtil.objIsNull(t)) {
            System.out.println("t is null");
        } else {
            System.out.println("t is not null");
        }

        if (CommonUtil.isNullOrEmpty(list)) {
            System.out.println("list is null");
        } else {
            System.out.println("list is not null");
        }
    }

}
