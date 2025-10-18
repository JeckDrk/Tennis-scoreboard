package TennisScoreboard.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchDTO {

    int page;

    int size;

    String search;


    public SearchDTO(int page, int size) {
        this.size = size;
        this.page = page;
    }
}
