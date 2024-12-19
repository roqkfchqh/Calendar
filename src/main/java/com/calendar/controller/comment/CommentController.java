package com.calendar.controller.comment;

import com.calendar.dto.request.comment.CommentRequestDto;
import com.calendar.dto.response.CommentResponseDto;
import com.calendar.service.paging.CommentPagingService;
import com.calendar.service.crud.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private static final String PAGE_SIZE = "10";

    private final CommentService commentService;
    private final CommentPagingService commentPagingService;

    //create
    @PostMapping("/{calendarId}")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long calendarId,
            @Valid @RequestBody CommentRequestDto dto,
            HttpServletRequest req){
        Long userId = (Long) req.getSession().getAttribute("userId");
        return ResponseEntity.ok(commentService.createComment(calendarId, dto, userId));
    }

    //read
    @GetMapping("/{calendarId}")
    public ResponseEntity<Page<CommentResponseDto>> readCommentsCalendar(
            @PathVariable Long calendarId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = PAGE_SIZE) int size){
        Page<CommentResponseDto> commentsPage = commentPagingService.readCommentsCalender(calendarId, page, size);
        return ResponseEntity.ok(commentsPage);
    }

    //update
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto dto,
            HttpServletRequest req){
        Long userId = (Long) req.getSession().getAttribute("userId");
        return ResponseEntity.ok(commentService.updateComment(commentId, dto, userId));
    }

    //delete
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId,
            HttpServletRequest req){
        Long userId = (Long) req.getSession().getAttribute("userId");
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
    }
}
