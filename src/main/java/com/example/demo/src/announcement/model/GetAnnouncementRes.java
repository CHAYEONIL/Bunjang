package com.example.demo.src.announcement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetAnnouncementRes {
    private int announcementId;
    private String title;
    private String content;
    private String updatedAt;
    private String annoStatus;

}
