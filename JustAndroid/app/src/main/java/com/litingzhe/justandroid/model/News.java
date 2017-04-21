package com.litingzhe.justandroid.model;

import java.util.List;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/14 上午10:47.
 * 类描述：
 */


public class News {


    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2017-04-14","title":"情欲的忧伤，女人比男人更懂","description":"睡在时间里的人","picUrl":"http://mmbiz.qpic.cn/mmbiz_jpg/ibn26yj4dctjvqgmmfIOWKBrDJa23xmjSV3nNuTAJtF6G9uccZ5J888ISqHdiaUAUWSBrX3awIIIlyeZZWNjVrGw/0?wx_fmt=jpeg","url":"http://mp.weixin.qq.com/s/SCKtbO6IGgTPqaMpCWlAsw"},{"ctime":"2017-04-14","title":"一张照片竟售9000美金，这对情侣边旅行边拍照，不久前终于爆出背后的秘密","description":"欧美内参","picUrl":"http://mmbiz.qpic.cn/mmbiz_png/icdYno8lLDBxIz0m3JMuhyJSE5aCMnQgBIFh0wiceRv7vUf4fb7Qa2MR3w8mDiatTshX6daNIYVygt6mqc9NCScCQ/0?wx_fmt=png","url":"http://mp.weixin.qq.com/s/jAFr5j2zaCEcUTel6EdwVg"},{"ctime":"2017-04-14","title":"以后，我也要带儿子泡吧喝酒打游戏。","description":"我要WhatYouNeed","picUrl":"http://mmbiz.qpic.cn/mmbiz_png/zynprs47B4QxkJiclpicgDS4QXic5VrEB4IEmP0ficNjmYf78yzDTbJfMnoWnibrzYIePDiapleornr2DyN6dq2icnwpw/0?wx_fmt=png","url":"http://mp.weixin.qq.com/s/iFk3t4Hw5iaYi_qm49wxAQ"},{"ctime":"2017-04-14","title":"14天融资4个亿，共享充电宝火了！是真痛点还是伪需求？","description":"创伙伴","picUrl":"http://mmbiz.qpic.cn/mmbiz_jpg/QeO8tPHltJCz2LekTCfKzJo4jbKmQVHmyAlYXCZNx0O29lAJnzbFav2QvialUicxF4uYtUMyc5JibF6x5myQJOlwg/0?wx_fmt=jpeg","url":"http://mp.weixin.qq.com/s/T8Alns2-ksMUE7tIa8XHfQ"},{"ctime":"2017-04-14","title":"烧掉5亿美金就像烧纸，政府却表示强烈支持，好莱坞还把他拍成了电影！","description":"差评","picUrl":"http://mmbiz.qpic.cn/mmbiz_png/yZPTcMGWibvsOk5RmJicHkMiabauI1oIkbM8PDHgk8iaSvzPM8qNlXkOcRg1Ek9M8Oz6pia6ab99SMUSczSfS4Biagkg/0?wx_fmt=png","url":"http://mp.weixin.qq.com/s/sWXg_X0T6Q01YL2LI4LTGQ"},{"ctime":"2017-04-14","title":"共享单车时代，我用电锯行侠仗义 | 城市单车猎人故事集","description":"新世相","picUrl":"http://mmbiz.qpic.cn/mmbiz_jpg/5ROs96OaibImV0iadNwm9TSQO9EhBb7nqqFZjXHQth7B3ArIkSl3mSl8Z6JeFF0s8LLbdR6xgb6fspTnjgibvs4bw/0?wx_fmt=jpeg","url":"http://mp.weixin.qq.com/s/kXLgjSkW3ff_KArtIBu3WA"},{"ctime":"2017-04-12","title":"看到白百何出轨，我的内心毫无波动。","description":"我要WhatYouNeed","picUrl":"http://mmbiz.qpic.cn/mmbiz_png/zynprs47B4QxkJiclpicgDS4QXic5VrEB4IEmP0ficNjmYf78yzDTbJfMnoWnibrzYIePDiapleornr2DyN6dq2icnwpw/0?wx_fmt=png","url":"http://mp.weixin.qq.com/s/K1XGhEmV2wYWGk6XS-4UzA"},{"ctime":"2017-04-12","title":"这个超迷人的袖珍岛国也向中国人开放免签了，曾被评为&quot;一生必去的50个国家&quot;！","description":"欧美内参","picUrl":"http://mmbiz.qpic.cn/mmbiz_png/icdYno8lLDBxtkP7u4JdlrLdJMNwIMhBKia6fXF9bVyBfMpYicdwm53fEuQ7lMllF6lpU4wO2yiaDwPEAmufFX1S4g/0?wx_fmt=png","url":"http://mp.weixin.qq.com/s/LhyA3eCeW7egkVH5POGyeg"},{"ctime":"2017-04-10","title":"设计师为iPhone 8设计新概念：这两种虚拟 HOME 键方案，你满意吗？","description":"同步推","picUrl":"http://mmbiz.qpic.cn/mmbiz_jpg/S67ZPlGTOy3vRwab0ibv79gGkPsqHvX8XEY64zZf6CAUfu7WibH2iaE2ETZs3piaDZicuUhv6L4EmGqOYJkfqML3z0w/0?wx_fmt=jpeg","url":"http://mp.weixin.qq.com/s/5D8bSw3nImq0odfNPQQb1Q"},{"ctime":"2017-04-10","title":"赶超papi酱，这几个短视频公众号不仅吸粉千万还变现不断","description":"微果酱","picUrl":"http://mmbiz.qpic.cn/mmbiz_png/52wy0FK91vibakCqVoy1LCXKx9EoHyiab9lLclC49UNCAibF5l5X9WCObCIexSC1cd9xdjn7rWDaTOW8pZWGFJ0sg/0?wx_fmt=png","url":"http://mp.weixin.qq.com/s/POI7CP8OrZKHvQO46zeElw"}]
     */

    private int code;
    private String msg;
    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        /**
         * ctime : 2017-04-14
         * title : 情欲的忧伤，女人比男人更懂
         * description : 睡在时间里的人
         * picUrl : http://mmbiz.qpic.cn/mmbiz_jpg/ibn26yj4dctjvqgmmfIOWKBrDJa23xmjSV3nNuTAJtF6G9uccZ5J888ISqHdiaUAUWSBrX3awIIIlyeZZWNjVrGw/0?wx_fmt=jpeg
         * url : http://mp.weixin.qq.com/s/SCKtbO6IGgTPqaMpCWlAsw
         */

        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
