package com.adrian.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    private Long senderId; 

    private String content; 
    
    private Long projectId;
    
}
