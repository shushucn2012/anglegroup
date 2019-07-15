package com.asuper.angelgroup.moduel.me.bean;

import com.asuper.angelgroup.common.set.GlobalParam;
import com.asuper.angelgroup.moduel.login.bean.RelationItem;

/**
 * Created by shubei on 2017/11/18.
 */

public class MockWords {

   public static String words0 = "与怪兽搏斗的人要谨防自己因此而变成怪兽。如果阁下长时间的盯着深渊，那么，深渊也会同样回望着阁下";

    public static String words1 = "对有些人而言，相遇即是告别。就像流星 划过天际，发出耀眼光芒的同时，也燃烧 殆尽。他们唯一能做的，就是让那道划痕 尽可能地浅";

    public static String words2 = "生活在这个城市中的人，在其或漫长或短暂的生命中，多少都受过他人的恶行相待。其中相当一部分恶行，仅能通过道德加以苛责。彼时彼地，法律显得既苍白又无力。" +
            "我们也许会同情，会愤怒，但不会想到去\t击杀那\t些原本与我们无关的作恶者。别人的苦难，终究是别人的，我们的克制，多半源自于不曾感同身受。然而，一旦有人这么做了，我们的内心却难免会感到快慰。民众如是，警察亦如是";

    public static String words3 = "每个人都被不同的记忆所束缚，你会发现，所有的记忆都会一次次地重现，其实，根本就逃避不了";

    public static String words4 = "沉沦可以，跌倒可以，但在那些人间恶魔再次现身时，我必须跌倒在高于他们的上方，我看到的强光必将紧接着他们的黑暗";

    public static void initProvince(){
        GlobalParam.provinceList.clear();
        GlobalParam.provinceList.add(new ProvinceBean(137933, "江苏"));
        GlobalParam.provinceList.add(new ProvinceBean(137948, "新疆"));
        GlobalParam.provinceList.add(new ProvinceBean(137947, "宁夏"));
        GlobalParam.provinceList.add(new ProvinceBean(137946, "青海"));
        GlobalParam.provinceList.add(new ProvinceBean(137945, "甘肃"));
        GlobalParam.provinceList.add(new ProvinceBean(137944, "陕西"));
        GlobalParam.provinceList.add(new ProvinceBean(137943, "西藏"));
        GlobalParam.provinceList.add(new ProvinceBean(137942, "云南"));
        GlobalParam.provinceList.add(new ProvinceBean(137941, "贵州"));
        GlobalParam.provinceList.add(new ProvinceBean(137940, "海南"));
        GlobalParam.provinceList.add(new ProvinceBean(137939, "江西"));
        GlobalParam.provinceList.add(new ProvinceBean(137938, "广东"));
        GlobalParam.provinceList.add(new ProvinceBean(137937, "湖南"));
        GlobalParam.provinceList.add(new ProvinceBean(137936, "福建"));
        GlobalParam.provinceList.add(new ProvinceBean(137935, "安徽"));
        GlobalParam.provinceList.add(new ProvinceBean(137934, "山东"));
        GlobalParam.provinceList.add(new ProvinceBean(8, "吉林"));
        GlobalParam.provinceList.add(new ProvinceBean(137932, "内蒙古"));
        GlobalParam.provinceList.add(new ProvinceBean(137931, "辽宁"));
        GlobalParam.provinceList.add(new ProvinceBean(137930, "山西"));
        GlobalParam.provinceList.add(new ProvinceBean(137929, "河北"));
        GlobalParam.provinceList.add(new ProvinceBean(137928, "重庆"));
        GlobalParam.provinceList.add(new ProvinceBean(137927, "天津"));
        GlobalParam.provinceList.add(new ProvinceBean(137926, "上海"));
        GlobalParam.provinceList.add(new ProvinceBean(137925, "北京"));
        GlobalParam.provinceList.add(new ProvinceBean(24, "四川"));
        GlobalParam.provinceList.add(new ProvinceBean(21, "广西壮族自治区"));
        GlobalParam.provinceList.add(new ProvinceBean(18, "湖北"));
        GlobalParam.provinceList.add(new ProvinceBean(17, "河南"));
        GlobalParam.provinceList.add(new ProvinceBean(12, "浙江"));
        GlobalParam.provinceList.add(new ProvinceBean(9, "黑龙江"));
    }

    public static void initRelations(){
        GlobalParam.relationList.clear();
        GlobalParam.relationList.add(new RelationItem("1", "妈妈"));
        GlobalParam.relationList.add(new RelationItem("2", "爸爸"));
        GlobalParam.relationList.add(new RelationItem("3", "爷爷"));
        GlobalParam.relationList.add(new RelationItem("4", "奶奶"));
        GlobalParam.relationList.add(new RelationItem("5", "外公"));
        GlobalParam.relationList.add(new RelationItem("6", "外婆"));
        GlobalParam.relationList.add(new RelationItem("7", "其他"));
    }

}
