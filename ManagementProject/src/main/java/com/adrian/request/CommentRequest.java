package com.adrian.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    private Long issueId;
    
    private String content;
    
}
