package org.example.demo.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.entity.Item;
import org.example.demo.entity.ResResult;
import org.example.demo.enums.CommonEnum;
import org.example.demo.enums.ItemEnum;
import org.example.demo.service.ItemService;
import org.example.demo.service.PostService;
import org.example.demo.service.UserService;
import org.example.demo.util.JwtUtil;
import org.example.demo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.example.demo.util.JwtUtil.verifyToken;

@Tag(name = "资助项目相关请求")
@RestController
@Slf4j
public class ItemController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private ItemService itemService;


    @Operation(summary = "提交资助项目")
    @Parameters({@Parameter(name = "item", description = "资助项目表单信息")})
    @PostMapping("/applyItem")
    @Transactional(rollbackFor = Exception.class)
    public ResResult<String> applyItem(@RequestBody Item item, @RequestHeader("token") String token) {
        ResResult res = new ResResult<String>();
        if (item == null || token == null) {
            Utils.setResponseEnum(res, CommonEnum.INPUT_EMPTY);
        } else {
            try {
                //filer已经对token做了判断,不需要再次判断是否有效
                DecodedJWT jwt = JwtUtil.parseJwt(token);
                String email = jwt.getClaim("email").asString();
                //上传压缩包文件
                String FileUrl = itemService.uploadFile(item.getBlFile());
                Integer userId = userService.getUserId(email);
                //插入post(资助项目发布状态),并且发布资助项目
                Integer postId = postService.insertPost();
                //与发布者、发布状态关联
                item.setPostId(postId);
                item.setUserId(userId);
                item.setFileUrl(FileUrl);
                //已作异常处理,不需要返回结果
                itemService.insertItem(item);
                Utils.setResponseEnum(res, ItemEnum.APPLY_ITEM_SUCCESS);
            } catch (Exception e) {
                log.error("插入post和item错误,信息为:{}", e.getMessage());
                Utils.setResponseEnum(res, ItemEnum.APPLY_ITEM_ERROR);
                //两个表插入失败即回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        return res;
    }
}

