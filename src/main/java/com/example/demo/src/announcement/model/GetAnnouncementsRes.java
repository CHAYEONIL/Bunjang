package com.example.demo.src.announcement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetAnnouncementsRes {
    private int announcementId;
    private String title;
    private String updatedAt;
    private String annoStatus;
}
