package com.example.easypoi.controller;

import cn.afterturn.easypoi.entity.ImageEntity;
import com.example.easypoi.service.EasyPoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 十玖八柒
 * @create 2022/6/23
 * @desc
 */
@RestController
public class EasyPoiController {
    @Autowired
    EasyPoiService easyPoiService;

    /**
     * 导出简单word
     *
     * @param response
     */
    @RequestMapping("/export")
    public void toExport(HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "华为P50");
        map.put("price", "5999");
        map.put("code", String.valueOf(System.currentTimeMillis()));
        map.put("stock", "999");
        map.put("msg", "ahzoo");

        try {
            easyPoiService.exportWord(response, map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 导出带图片word
     *
     * @param response
     */
    @RequestMapping("/exportImg")
    public void toExportImg(HttpServletResponse response) {
//        添加图片
        String imgPath = "static/img.png";
        int width = 200;
        int height = 100;
        ImageEntity img = new ImageEntity(imgPath, width, height);
        img.setType(ImageEntity.URL);

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "华为P50");
        map.put("price", "5999");
        map.put("code", String.valueOf(System.currentTimeMillis()));
        map.put("stock2", "999");

        map.put("msg", img);


        try {
            easyPoiService.exportWord(response, map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 导出列表word，参数modify为true时表示修改样式（可导出特殊符号）
     *
     * @param response
     */
    @RequestMapping("/exportList/{modify}")
    public void toExportList(HttpServletResponse response, @PathVariable String modify) {
        HashMap<String, Object> map = new HashMap<>();
        List<Map<String, Object>> lists = new ArrayList<>();
        HashMap<String, Object> temp;
        for (int i = 0; i < 7; i++) {
            temp = new HashMap<>();
            temp.put("id", String.valueOf(i));
            temp.put("name", i + "行");

            lists.add(temp);
        }
        map.put("lists", lists);
        try {
            easyPoiService.exportListWord(response, map, modify);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
