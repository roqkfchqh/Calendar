package com.calendar.controller.comment.controller;

import com.calendar.controller.comment.dto.CommentRequestDto;
import com.calendar.controller.comment.dto.CommentResponseDto;
import com.calendar.controller.comment.service.CommentService;
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

    @PostMapping("/{calendarId}")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long calendarId,
            @Valid @RequestBody CommentRequestDto dto,
            HttpServletRequest req){
        return ResponseEntity.ok(commentService.createComment(calendarId, dto, req));
    }

    @GetMapping("/{calendarId}")
    public ResponseEntity<Page<CommentResponseDto>> readCommentsCalendar(
            @PathVariable Long calendarId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = PAGE_SIZE) int size){
        Page<CommentResponseDto> commentsPage = commentService.readCommentsCalender(calendarId, page, size);
        return ResponseEntity.ok(commentsPage);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto dto,
            HttpServletRequest req){
        return ResponseEntity.ok(commentService.updateComment(commentId, dto, req));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId,
            HttpServletRequest req){
        commentService.deleteComment(commentId, req);
        return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
    }
}
