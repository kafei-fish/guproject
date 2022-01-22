package com.lxl.serviceedu.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxl.commonutils.JwtUtils;
import com.lxl.commonutils.R;
import com.lxl.servicebase.exceptionhandler.GuliException;
import com.lxl.serviceedu.entity.EduComment;
import com.lxl.serviceedu.service.EduCommentService;
import com.lxl.serviceedu.client.UcClient;
import com.lxl.serviceedu.entity.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author lxl
 * @since 2022-01-15
 */
@RestController
@RequestMapping("/serviceedu/edu-comment")

public class CommentFromController {
    @Autowired
    private EduCommentService commentService;
    @Autowired
    private UcClient ucClient;
    /**
     * 评论分页
     * @return
     */
    @PostMapping("queryList/{page}/{limit}")
    public R queryList(@PathVariable Long page ,@PathVariable Long limit , @RequestBody(required = false) String courseId){
        Page<EduComment> pram=new Page<>(page,limit);
        String id = courseId.substring(0, courseId.length() - 1);
        Map<String,Object> map=commentService.queryWebList(pram,id);
        return R.ok().data(map);
    }

    /**
     *
     * @param comment
     * @return
     */
    @PostMapping("addComment")
    public R addComment(@RequestBody EduComment comment, HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(id)){
            throw new GuliException(20001,"请先登录");
        }

        comment.setMemberId(id);
        //远程调用
        MemberVo menber = ucClient.getMenber(id);
        comment.setAvatar(menber.getAvatar());
        comment.setNickname(menber.getNickname());
        boolean save = commentService.save(comment);
        if(save){
            return R.ok().message("发布成功");
        }else {
            throw new GuliException(2001,"发布失败");
        }

    }
}

