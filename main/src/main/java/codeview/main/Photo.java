package codeview.main;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Photo {

    private String photoId;
    private String photoTitle;
    private String photoDescription;
    private String userId;

}
