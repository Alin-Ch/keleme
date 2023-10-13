package com.water.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageInfo;
import com.water.common.R;
import com.water.exception.CustomException;
import com.water.pojo.Params;
import com.water.pojo.Type;
import com.water.service.TypeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/09/10/15:51
 * @Description:    TODO:商品分类控制层
 */
@RestController
@RequestMapping("/type")
public class TypeController {

    @Resource
    private TypeService typeService;

    /**
     * 查询全部把商品的分类也查询出来
     * @return
     */
    @GetMapping
    public R findAll(){
        return R.success(typeService.findAll());
    }

    /**
     * 新增和修改分类
     * @param type
     * @return
     */
    @PostMapping
    public R save(@RequestBody Type type){
        //判断用户id是否为空，为空则新增，否则为修改
        if (type.getTypeid() == null){
            typeService.add(type);
        }else{
            typeService.update(type);
        }
        return R.success();
    }

    /**
     * 分页查询/查询全部/模糊查询分类
     * @param params
     * @return
     */
    @GetMapping("/search")
    public R findBySearch(Params params){
        PageInfo<Type> info = typeService.findBySearch(params);
        return R.success(info);
    }

    /**
     * 根据id删除分类
     * @param typeId
     * @return
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Integer typeId){
        typeService.delete(typeId);
        return R.success();
    }

    /**
     * 批量删除   PUT /delBatch
     * @param list
     * @return
     */
    @PutMapping("/delBatch")
    public R delBatch(@RequestBody List<Type> list){
        for (Type type : list) {
            typeService.delete(type.getTypeid());
        }
        return R.success();
    }

    /**
     * 导出报表   GET /export
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/export")
    public R export(HttpServletResponse response) throws IOException {
        //1.从数据库中查询出所有数据
        List<Type> all = typeService.findAll();
        if (CollectionUtil.isEmpty(all)){
            throw new CustomException("未找到数据");
        }
        //2.定义一个list和Map<key,value>出来，存储处理之后得数据，然后塞到list里
        List<Map<String,Object>> list = new ArrayList<>(all.size());
        //3.遍历每一条数据，然后封装到Map<key,value>里，把这个map塞到list里面
        for (Type type : all) {
            Map<String,Object> row = new HashMap<>();
            row.put("分类名称",type.getTypeName());
            row.put("分类描述",type.getTypeDescription());

            list.add(row);
        }
        //4.Hutool里面的一个工具类创建一个 ExcelWriter,把list数据用这个 write写 出来
        ExcelWriter wr = ExcelUtil.getWriter(true);
        wr.write(list,true);
        //5.把这个excel下载下来
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposiyion","attachment;filename=type.xlsx");
        ServletOutputStream out = response.getOutputStream();
        //是否将这个key作为头即分类名称和分类描述
        wr.flush(out,true);
        wr.close();
        IoUtil.close(System.out);
        return R.success();
    }

    /**
     * 批量导入 POST /upload
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public R upload(MultipartFile file) throws IOException {
        //getReader读取excel全部数据后转换成type对象，然后用List<Type>接收
        List<Type> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(Type.class);
        //拿到所有数据后，只要不等于空就遍历然后调用持久层存入数据库
        if (!CollectionUtil.isEmpty(infoList)){
            for (Type type : infoList) {
                try {
                    //调用新增方法
                    typeService.add(type);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return R.success();
    }
}











