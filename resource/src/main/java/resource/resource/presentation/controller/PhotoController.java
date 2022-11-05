package resource.resource.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import resource.resource.application.dto.Photo;

@Controller
public class PhotoController {

    @GetMapping("/photos/1")
    public Photo photo() {
        return Photo.builder()
                .photoId("user1")
                .photoId("1")
                .photoTitle("Photo 1 Title")
                .photoDescription("Photo is nice")
                .build();
    }

    @GetMapping("/photos/2")
    public Photo photo2() {
        return Photo.builder()
                .photoId("user2")
                .photoId("2")
                .photoTitle("Photo 2 Title")
                .photoDescription("Photo is beautiful")
                .build();
    }

}
