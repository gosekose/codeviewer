package resource.resource.application.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photo {

    private String userId;
    private String photoId;
    private String photoTitle;
    private String photoDescription;


}
