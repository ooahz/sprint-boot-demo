package com.example.easypoi.service;

import cn.afterturn.easypoi.word.WordExportUtil;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;

/**
* @author 十玖八柒
* @create 2022/6/23
* @desc
*/
@Service
public class EasyPoiService {
    public void exportWord(HttpServletResponse response, HashMap<String, Object> map) throws Exception {
        String fileName = "test.docx";

//        设置格式
        response.setContentType("application/octet-stream");
//        设置导出文件名
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
//        刷新缓冲
        response.flushBuffer();
//        写入到流
        XWPFDocument xwpfDocument = WordExportUtil.exportWord07("templates/template.docx", map);
//        输出
        xwpfDocument.write(response.getOutputStream());
//        关闭流
        xwpfDocument.close();
    }

    public void exportListWord(HttpServletResponse response, HashMap<String, Object> map, String modifyStyle) throws Exception {
        String fileName = "列表.docx";

//        设置格式
        response.setContentType("application/octet-stream");
//        设置导出文件名
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//        刷新缓冲
        response.flushBuffer();
//        写入到流
        XWPFDocument xwpfDocument = WordExportUtil.exportWord07("templates/listTemplate.docx", map);

//        样式修改
        if("true".equals(modifyStyle)) {
            modifyStyle(xwpfDocument);
        }
//        输出
        xwpfDocument.write(response.getOutputStream());
//        关闭流
        xwpfDocument.close();
    }

    /**
     * 修改单元格样式，这部分和原生poi一样都是对table对象进行修改操作
     * 下面示例中，为了方便，全是获取的第一个元素，实际可根据需要调整
     * @param xwpfDocument
     */
    public void modifyStyle(XWPFDocument xwpfDocument){
//        获取第一个table对象
        XWPFTable table = xwpfDocument.getTables().get(0);

//        获取第一个row对象
        XWPFTableRow row = table.getRow(0);

//        获取第一个cell对象
        XWPFTableCell cell = row.getCell(0);

//        获取第一行对象
        XWPFParagraph para = cell.getParagraphs().get(0);

//        获取第一个run对象
        XWPFRun run = para.getRuns().get(0);

//        设置列宽
        cell .setWidth(String.valueOf(row.getCell(0).getWidth()));
        cell .setWidthType(row.getCell(0).getWidthType());
//        设置水平对齐方式
        para .setAlignment(ParagraphAlignment.CENTER);
//        设置垂直对齐方式
        cell .setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

//        设置一些字体样式，见名知意，不一一说明，这里只列出一些常用样式
        run.setFontSize(14);
        run.setColor("7999FF");
        run.setBold(true);
        run.setItalic(true);
        run.setShadow(true);

//        如果要设置特殊字体符号的话
        run.setFontFamily("Wingdings 2");
//        run.setFontFamily("黑体", XWPFRun.FontCharRange.cs);

//      ......

//        设置文字，文件会在原来基础上追加，如果要删除之前文字，可以删除run对象，重新创建，并设置样式
        run.setText(" 是\u0052 否\u00A3");
//        run.setText(String.valueOf(new HSSFRichTextString(" 是\u0052 否\u00A3")));
//        对勾方框 在 world文档 的 符号 所对应的 Wingdings 2 字体下的字符代码为 0052
//        同理，00A3（或25A1）为未打钩方框

//        0052编码默认的unicode对应的符号为 R，同理00A3对应的为 £，所以这里也可以直接用 R 和 £ 转为 Wingdings 2 字体
//        run.setText(" 是R 否£");

    }

}
